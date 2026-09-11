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

class HabitLargeWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateLargeWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: android.os.Bundle
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        updateLargeWidget(context, appWidgetManager, appWidgetId)
    }

    companion object {
        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, HabitLargeWidgetProvider::class.java)
            val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            for (id in allWidgetIds) {
                updateLargeWidget(context, appWidgetManager, id)
            }
        }

        fun updateLargeWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
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

            val views = RemoteViews(context.packageName, R.layout.widget_habit_large)

            if (displayItem != null) {
                val isActive = active != null || (cached.title.isNotBlank() && cached.title == displayItem.title)
                views.setTextViewText(
                    R.id.widget_status_badge,
                    if (isActive) "${displayItem.getCategoryIcon()} HOZIRGI FAOL VAZIFA" else "⏳ NAVBATDAGI VAZIFA"
                )
                views.setTextViewText(R.id.widget_task_title, displayItem.title)
                views.setTextViewText(R.id.widget_task_time, "${displayItem.start} – ${displayItem.end}")

                val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
                val endMin = TaskTimeEngine.parseMinuteOfDay(displayItem.end) ?: (nowMin + 30)
                val diff = endMin - nowMin
                val remainingStr = if (diff > 0) "$diff daqiqa qoldi" else "Vaqt yakunlandi"
                views.setTextViewText(R.id.widget_time_remaining, remainingStr)

                val upcoming = if (active != null) next else null
                if (upcoming != null) {
                    views.setTextViewText(R.id.widget_next_task_text, "${upcoming.start} — ${upcoming.title}")
                } else {
                    views.setTextViewText(R.id.widget_next_task_text, "Keyingi reja belgilanmagan")
                }
            } else {
                views.setTextViewText(R.id.widget_status_badge, "🌟 BO'SH VAQT")
                views.setTextViewText(R.id.widget_task_title, "Barcha rejalar muvaffaqiyatli yakunlandi")
                views.setTextViewText(R.id.widget_task_time, TaskTimeEngine.getFormattedCurrentTime())
                views.setTextViewText(R.id.widget_time_remaining, "Dam olish vaqti")
                views.setTextViewText(R.id.widget_next_task_text, "Yangi vazifalar yo'q")
            }

            // Click opens app
            val appIntent = Intent(context, MainActivity::class.java)
            val appPendingIntent = PendingIntent.getActivity(
                context, 300, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, appPendingIntent)

            // Done action
            val doneIntent = Intent(context, HabitAppWidgetProvider::class.java).apply {
                action = HabitAppWidgetProvider.ACTION_WIDGET_DONE
            }
            val donePendingIntent = PendingIntent.getBroadcast(
                context, 301, doneIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_done, donePendingIntent)

            // Delay action
            val delayIntent = Intent(context, HabitAppWidgetProvider::class.java).apply {
                action = HabitAppWidgetProvider.ACTION_WIDGET_DELAY
            }
            val delayPendingIntent = PendingIntent.getBroadcast(
                context, 302, delayIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_delay, delayPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
