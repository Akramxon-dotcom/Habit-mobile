package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.MainActivity
import com.example.R
import com.example.data.local.HabitPreferences
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskTimeEngine
import com.example.service.AiWallpaperManager
import com.example.service.HabitNotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class HabitAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: android.os.Bundle
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        updateAppWidget(context, appWidgetManager, appWidgetId)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val prefs = HabitPreferences(context)

        when (intent.action) {
            ACTION_WIDGET_DONE -> {
                val schedule = prefs.getSchedule()
                val active = TaskTimeEngine.findActiveScheduleItem(schedule)
                if (active != null) {
                    val updated = schedule.map {
                        if (it.id == active.id) it.copy(isDone = true) else it
                    }
                    prefs.saveSchedule(updated)
                    // Calculate saved time
                    val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
                    val endMin = TaskTimeEngine.parseMinuteOfDay(active.end) ?: nowMin
                    if (endMin > nowMin) {
                        prefs.addTimeBankMinutes(endMin - nowMin)
                    }
                }
                updateAllWidgets(context)
                HabitNotificationHelper.showActiveTaskNotification(context)
                // Trigger AI Wallpaper update
                if (prefs.isAiWallpaperEnabled) {
                    CoroutineScope(Dispatchers.IO).launch {
                        AiWallpaperManager.updateWallpaperForCurrentTask(context)
                    }
                }
            }

            ACTION_WIDGET_DELAY -> {
                val schedule = prefs.getSchedule()
                val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
                val updated = schedule.map { item ->
                    val startMin = TaskTimeEngine.parseMinuteOfDay(item.start) ?: 0
                    val endMin = TaskTimeEngine.parseMinuteOfDay(item.end) ?: 0
                    if (startMin >= nowMin) {
                        val s = (startMin + 15) % 1440
                        val e = (endMin + 15) % 1440
                        val sStr = String.format(Locale.getDefault(), "%02d:%02d", s / 60, s % 60)
                        val eStr = String.format(Locale.getDefault(), "%02d:%02d", e / 60, e % 60)
                        item.copy(start = sStr, end = eStr)
                    } else {
                        item
                    }
                }
                prefs.saveSchedule(updated)
                updateAllWidgets(context)
                HabitNotificationHelper.showActiveTaskNotification(context)
            }
        }
    }

    companion object {
        const val ACTION_WIDGET_DONE = "com.example.ACTION_WIDGET_DONE"
        const val ACTION_WIDGET_DELAY = "com.example.ACTION_WIDGET_DELAY"

        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, HabitAppWidgetProvider::class.java)
            val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            for (id in allWidgetIds) {
                updateAppWidget(context, appWidgetManager, id)
            }
            // Also update the other dedicated widgets
            HabitCompactWidgetProvider.updateAllWidgets(context)
            HabitVerticalWidgetProvider.updateAllWidgets(context)
            HabitLargeWidgetProvider.updateAllWidgets(context)
        }

        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val prefs = HabitPreferences(context)
            val schedule = prefs.getSchedule()
            val cached = prefs.getCachedState()
            val active = TaskTimeEngine.findActiveScheduleItem(schedule)
            val next = TaskTimeEngine.findNextUpcomingScheduleItem(schedule)

            val displayItem = when {
                active != null -> active
                cached.title.isNotBlank() -> ScheduleItem(
                    title = cached.title,
                    category = cached.category,
                    start = cached.start,
                    end = cached.end,
                    note = cached.note,
                    blocking = cached.blocking
                )
                next != null -> next
                else -> null
            }

            // Inspect widget width & height to provide the optimal layout (4x1, 3x1, 1x3, 3x2, 4x2, 4x3, 4x4)
            val options = appWidgetManager.getAppWidgetOptions(appWidgetId)
            val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 200)
            val minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 100)

            val layoutId = when {
                minHeight < 80 -> R.layout.widget_habit_compact // 4x1, 3x1, 2x1 slim bar
                minWidth < 140 && minHeight >= 100 -> R.layout.widget_habit_vertical // 1x3, 1x4, 2x3 vertical
                minHeight >= 160 && minWidth >= 200 -> R.layout.widget_habit_large // 4x3, 4x4, 3x3 expanded
                else -> R.layout.widget_habit // 3x2, 4x2 standard
            }

            val views = RemoteViews(context.packageName, layoutId)

            if (displayItem != null) {
                val isActive = active != null || (cached.title.isNotBlank() && cached.title == displayItem.title)
                val badgeText = if (isActive) "${displayItem.getCategoryIcon()} FAOL" else "⏳ NAVBATDAGI"
                views.setTextViewText(R.id.widget_status_badge, badgeText)
                views.setTextViewText(R.id.widget_task_title, displayItem.title)

                val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
                val endMin = TaskTimeEngine.parseMinuteOfDay(displayItem.end) ?: (nowMin + 30)
                val diff = endMin - nowMin
                val remainingStr = if (diff > 0) "$diff daqiqa qoldi" else "Vaqt yakunlandi"

                if (layoutId == R.layout.widget_habit_compact) {
                    views.setTextViewText(R.id.widget_task_time, "${displayItem.start}–${displayItem.end} · $remainingStr")
                } else {
                    views.setTextViewText(R.id.widget_task_time, "${displayItem.start} – ${displayItem.end}")
                    views.setTextViewText(R.id.widget_time_remaining, remainingStr)
                }

                // If large layout, bind the upcoming task
                if (layoutId == R.layout.widget_habit_large) {
                    val upcoming = if (active != null) next else null
                    if (upcoming != null) {
                        views.setTextViewText(R.id.widget_next_task_text, "${upcoming.start} — ${upcoming.title}")
                    } else {
                        views.setTextViewText(R.id.widget_next_task_text, "Keyingi reja belgilanmagan")
                    }
                }
            } else {
                views.setTextViewText(R.id.widget_status_badge, "🌟 BO'SH VAQT")
                views.setTextViewText(R.id.widget_task_title, "Rejada keyingi vazifalar yo'q")
                if (layoutId == R.layout.widget_habit_compact) {
                    views.setTextViewText(R.id.widget_task_time, "${TaskTimeEngine.getFormattedCurrentTime()} · Dam olish vaqti")
                } else {
                    views.setTextViewText(R.id.widget_task_time, TaskTimeEngine.getFormattedCurrentTime())
                    views.setTextViewText(R.id.widget_time_remaining, "Dam olish vaqti")
                }
                if (layoutId == R.layout.widget_habit_large) {
                    views.setTextViewText(R.id.widget_next_task_text, "Barcha vazifalar bajarildi!")
                }
            }

            // Main click intent -> open MainActivity
            val appIntent = Intent(context, MainActivity::class.java)
            val appPendingIntent = PendingIntent.getActivity(
                context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, appPendingIntent)

            // Done action intent
            val doneIntent = Intent(context, HabitAppWidgetProvider::class.java).apply {
                action = ACTION_WIDGET_DONE
            }
            val donePendingIntent = PendingIntent.getBroadcast(
                context, 1, doneIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_done, donePendingIntent)

            // Delay action intent (if present in layout)
            if (layoutId != R.layout.widget_habit_compact && layoutId != R.layout.widget_habit_vertical) {
                val delayIntent = Intent(context, HabitAppWidgetProvider::class.java).apply {
                    action = ACTION_WIDGET_DELAY
                }
                val delayPendingIntent = PendingIntent.getBroadcast(
                    context, 2, delayIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                views.setOnClickPendingIntent(R.id.widget_btn_delay, delayPendingIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
