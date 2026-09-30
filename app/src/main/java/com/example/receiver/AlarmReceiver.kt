package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.alarm.AlarmHelper
import com.example.alarm.AlarmRingtoneService
import com.example.ui.alarm.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_FIRE_ALARM = "com.example.habit.ACTION_FIRE_ALARM"
        const val ACTION_TASK_START = "com.example.habit.ACTION_TASK_START"
        private const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action
        Log.d(TAG, "AlarmReceiver chaqirildi: action=$action")

        if (action == ACTION_FIRE_ALARM) {
            val title = intent.getStringExtra(AlarmActivity.EXTRA_TASK_TITLE) ?: "Vazifa"
            val category = intent.getStringExtra(AlarmActivity.EXTRA_TASK_CATEGORY) ?: ""
            val endTime = intent.getStringExtra(AlarmActivity.EXTRA_TASK_END) ?: ""
            val note = intent.getStringExtra(AlarmActivity.EXTRA_TASK_NOTE) ?: ""
            val taskId = intent.getStringExtra(AlarmActivity.EXTRA_TASK_ID) ?: ""

            val prefs = com.example.data.local.HabitPreferences(context)

            // Check if this task is linked to an external app (Ibrat / Cake) and already fulfilled today
            if (prefs.isAppUsageTrackingEnabled) {
                val usageStatus = com.example.service.AppUsageTracker.checkStatusForTask(context, title, taskId)
                if (usageStatus != null && usageStatus.isFulfilled) {
                    Log.d(TAG, "Task «$title» was already fulfilled via app usage (${usageStatus.usedMinutes}/${usageStatus.requiredMinutes} min). Marking done automatically without alarm!")
                    prefs.markTaskCompletedByTitleOrId(taskId, title, endTime)
                    prefs.markAlarmAnswered(title, endTime)
                    com.example.service.AppUsageTracker.showAutoCompletedNotification(
                        context = context,
                        taskTitle = title,
                        usedMinutes = usageStatus.usedMinutes,
                        requiredMinutes = usageStatus.requiredMinutes
                    )
                    try {
                        com.example.widget.HabitAppWidgetProvider.updateAllWidgets(context)
                        com.example.service.HabitNotificationHelper.showActiveTaskNotification(context)
                    } catch (_: Exception) {}
                    return
                }
            }

            if (prefs.isNotificationsEnabled && !prefs.isTaskNotificationMuted(taskId)) {
                AlarmRingtoneService.startAlarm(
                    context = context,
                    title = title,
                    category = category,
                    end = endTime,
                    note = note,
                    taskId = taskId
                )
            } else {
                Log.d(TAG, "Bildirishnoma o'chirilgan (isNotificationsEnabled=${prefs.isNotificationsEnabled}, isTaskMuted=${prefs.isTaskNotificationMuted(taskId)})")
            }
        }

        // Always refresh Widget, Notification, and AI Wallpaper when task transitions occur
        try {
            com.example.widget.HabitAppWidgetProvider.updateAllWidgets(context)
            com.example.service.HabitNotificationHelper.showActiveTaskNotification(context)
            val prefs = com.example.data.local.HabitPreferences(context)
            if (prefs.isAiWallpaperEnabled) {
                kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                    com.example.service.AiWallpaperManager.updateWallpaperForCurrentTask(context)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating widget/notification/wallpaper", e)
        }
    }
}
