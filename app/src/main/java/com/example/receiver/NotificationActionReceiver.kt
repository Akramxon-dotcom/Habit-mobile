package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.local.HabitPreferences
import com.example.data.model.TaskTimeEngine
import com.example.service.AiWallpaperManager
import com.example.service.HabitNotificationHelper
import com.example.widget.HabitAppWidgetProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val prefs = HabitPreferences(context)
        when (intent.action) {
            ACTION_NOTIFICATION_DONE -> {
                val schedule = prefs.getSchedule()
                val active = TaskTimeEngine.findActiveScheduleItem(schedule)
                if (active != null) {
                    val updated = schedule.map {
                        if (it.id == active.id) it.copy(isDone = true) else it
                    }
                    prefs.saveSchedule(updated)
                    val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
                    val endMin = TaskTimeEngine.parseMinuteOfDay(active.end) ?: nowMin
                    if (endMin > nowMin) {
                        prefs.addTimeBankMinutes(endMin - nowMin)
                    }
                }
                HabitNotificationHelper.showActiveTaskNotification(context)
                HabitAppWidgetProvider.updateAllWidgets(context)
                if (prefs.isAiWallpaperEnabled) {
                    CoroutineScope(Dispatchers.IO).launch {
                        AiWallpaperManager.updateWallpaperForCurrentTask(context)
                    }
                }
            }

            ACTION_NOTIFICATION_DELAY_15 -> {
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
                HabitNotificationHelper.showActiveTaskNotification(context)
                HabitAppWidgetProvider.updateAllWidgets(context)
            }
        }
    }

    companion object {
        const val ACTION_NOTIFICATION_DONE = "com.example.ACTION_NOTIFICATION_DONE"
        const val ACTION_NOTIFICATION_DELAY_15 = "com.example.ACTION_NOTIFICATION_DELAY_15"
    }
}
