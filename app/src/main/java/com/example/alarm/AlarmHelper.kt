package com.example.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.R
import com.example.receiver.AlarmReceiver
import com.example.ui.alarm.AlarmActivity
import java.util.Calendar
import java.util.Locale

object AlarmHelper {
    private const val TAG = "AlarmHelper"
    const val CHANNEL_ID_ALARM = "habit_alarm_channel"
    const val NOTIFICATION_ID_ALARM = 7788
    const val TASK_ALARM_REQ_CODE = 8899
    const val TEST_ALARM_REQ_CODE = 8877
    const val SNOOZE_ALARM_REQ_CODE = 8866

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val alarmChannel = NotificationChannel(
                CHANNEL_ID_ALARM,
                context.getString(R.string.notification_channel_alarm),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_alarm_desc)
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 800, 400, 800)
                setSound(null, null)
                setBypassDnd(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }

            notificationManager.createNotificationChannel(alarmChannel)
        }
    }

    fun calculateTargetAlarmTime(startTimeStr: String, endTimeStr: String): Long? {
        if (endTimeStr.isBlank()) return null
        val parts = endTimeStr.trim().split(":")
        if (parts.size < 2) return null
        val endHour = parts[0].toIntOrNull() ?: return null
        val endMinute = parts[1].toIntOrNull() ?: return null

        val startHour = startTimeStr.trim().split(":").getOrNull(0)?.toIntOrNull()

        val now = java.util.Calendar.getInstance()
        val target = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, endHour)
            set(java.util.Calendar.MINUTE, endMinute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }

        // Overnight task check: e.g. start=22:00, end=04:05
        if (startHour != null && startHour > endHour) {
            val currentHour = now.get(java.util.Calendar.HOUR_OF_DAY)
            if (currentHour >= startHour) {
                // Started today, ends tomorrow morning
                target.add(java.util.Calendar.DAY_OF_YEAR, 1)
            }
        }

        return target.timeInMillis
    }

    fun scheduleTaskAlarm(
        context: Context,
        title: String,
        category: String,
        startTime: String,
        endTime: String,
        note: String,
        taskId: String = ""
    ): String {
        if (title.isBlank() || endTime.isBlank()) {
            cancelTaskAlarm(context)
            return "Vazifa belgilanmagan"
        }

        val targetMillis = calculateTargetAlarmTime(startTime, endTime)
            ?: return "Vaqt formati noto'g'ri ($endTime)"

        val now = System.currentTimeMillis()
        val prefs = com.example.data.local.HabitPreferences(context)

        // Check if already answered today
        if (prefs.isAlarmAnswered(title, endTime)) {
            Log.d(TAG, "Vazifa '$title' uchun bugun allaqachon javob berilgan.")
            return "Bu vazifaga allaqachon javob berilgan"
        }

        // If target was within the last 20 minutes and not answered yet, trigger right now
        if (now >= targetMillis && (now - targetMillis) < 20 * 60 * 1000L) {
            Log.d(TAG, "Vazifa vaqti hozir yetib kelgan! Zudlik bilan chalinadi.")
            triggerAlarmNow(context, title, category, endTime, note, taskId)
            return "Vazifa vaqti yetib keldi, signal chalindi!"
        }

        // If target already passed by more than 20 minutes and not overnight, don't ring past alarm
        if (targetMillis <= now) {
            return "Vazifa vaqti ($endTime) o'tib ketgan"
        }

        // Target is in future, schedule exact alarm with AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_FIRE_ALARM
            putExtra(AlarmActivity.EXTRA_TASK_ID, taskId)
            putExtra(AlarmActivity.EXTRA_TASK_TITLE, title)
            putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, category)
            putExtra(AlarmActivity.EXTRA_TASK_END, endTime)
            putExtra(AlarmActivity.EXTRA_TASK_NOTE, note)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            TASK_ALARM_REQ_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    targetMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    targetMillis,
                    pendingIntent
                )
            }
            val diffMinutes = (targetMillis - now) / 60_000L
            Log.d(TAG, "Exact alarm rejalashtirildi: $title soat $endTime ($diffMinutes daqiqadan so'ng)")
            return "Signal $endTime da rejalashtirildi ($diffMinutes daqiqadan so'ng)"
        } catch (e: SecurityException) {
            Log.w(TAG, "setExact ruxsati yo'q: ${e.message}")
            return "Signal uchun Exact Alarm ruxsatini bering"
        } catch (e: Exception) {
            Log.e(TAG, "setExact xatolik: ${e.message}")
            return "Alarm rejalashtirishda xatolik: ${e.message}"
        }
    }

    fun scheduleTestAlarm(context: Context, delaySeconds: Int = 10): String {
        createNotificationChannels(context)
        val triggerAtMillis = System.currentTimeMillis() + (delaySeconds * 1000L)

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_FIRE_ALARM
            putExtra(AlarmActivity.EXTRA_TASK_TITLE, "Sinov: Vazifa yakuni")
            putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, "Test rejimi")
            putExtra(AlarmActivity.EXTRA_TASK_END, "Hozir")
            putExtra(AlarmActivity.EXTRA_TASK_NOTE, "10 soniyalik test signali muvaffaqiyatli chalindi!")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            TEST_ALARM_REQ_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
            Log.d(TAG, "Test signal $delaySeconds soniyadan so'ng rejalashtirildi")
            return "Test signali $delaySeconds soniyadan so'ng chalinadi! Telefonni qulflab tekshirishingiz mumkin."
        } catch (e: Exception) {
            Log.e(TAG, "scheduleTestAlarm xatolik: ${e.message}")
            return "Test signalini rejalashtirib bo'lmadi: ${e.message}"
        }
    }

    fun cancelTaskAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_FIRE_ALARM
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            TASK_ALARM_REQ_CODE,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pendingIntent != null) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            Log.d(TAG, "Rejalashtirilgan vazifa signali bekor qilindi")
        }
    }

    fun triggerAlarmNow(
        context: Context,
        title: String,
        category: String,
        endTime: String,
        note: String,
        taskId: String = ""
    ) {
        Log.d(TAG, "triggerAlarmNow chaqirildi: title=$title, end=$endTime")
        createNotificationChannels(context)

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_FIRE_ALARM
            putExtra(AlarmActivity.EXTRA_TASK_ID, taskId)
            putExtra(AlarmActivity.EXTRA_TASK_TITLE, title)
            putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, category)
            putExtra(AlarmActivity.EXTRA_TASK_END, endTime)
            putExtra(AlarmActivity.EXTRA_TASK_NOTE, note)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            Log.w(TAG, "setExact ruxsati yo'q, zudlik bilan broadcast yuboriladi: ${e.message}")
            context.sendBroadcast(intent)
        } catch (e: Exception) {
            Log.e(TAG, "AlarmManager xatolik: ${e.message}")
            context.sendBroadcast(intent)
        }
    }

    /**
     * Schedules a Snooze (Kechiktirish) alarm after selected minutes.
     * When it triggers, AlarmActivity will show the full-screen prompt again.
     */
    fun scheduleSnoozeAlarm(
        context: Context,
        title: String,
        category: String,
        note: String,
        delayMinutes: Int,
        taskId: String = ""
    ): Boolean {
        createNotificationChannels(context)
        val triggerAtMillis = System.currentTimeMillis() + (delayMinutes * 60 * 1000L)
        val cal = Calendar.getInstance().apply { timeInMillis = triggerAtMillis }
        val newEndTime = String.format(Locale.getDefault(), "%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))

        val prefs = com.example.data.local.HabitPreferences(context)
        prefs.unmarkAlarmAnswered(title, newEndTime)

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_FIRE_ALARM
            putExtra(AlarmActivity.EXTRA_TASK_ID, taskId)
            putExtra(AlarmActivity.EXTRA_TASK_TITLE, title)
            putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, category)
            putExtra(AlarmActivity.EXTRA_TASK_END, newEndTime)
            putExtra(AlarmActivity.EXTRA_TASK_NOTE, if (note.isNotBlank()) note else "$delayMinutes daqiqa kechiktirilgan eslatma")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            SNOOZE_ALARM_REQ_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return false
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
            Log.d(TAG, "Snooze muvaffaqiyatli rejalashtirildi: $title -> $delayMinutes daqiqa ($newEndTime)")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Snooze rejalashtirishda xatolik: ${e.message}")
            false
        }
    }

    fun showAlarmNotification(
        context: Context,
        title: String,
        category: String,
        endTime: String,
        note: String
    ) {
        createNotificationChannels(context)

        val fullScreenIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NO_USER_ACTION
            putExtra(AlarmActivity.EXTRA_TASK_TITLE, title)
            putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, category)
            putExtra(AlarmActivity.EXTRA_TASK_END, endTime)
            putExtra(AlarmActivity.EXTRA_TASK_NOTE, note)
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID_ALARM,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID_ALARM)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("⏰ Vazifa vaqti tugadi: $title")
            .setContentText("Kategoriya: $category | Vaqt: $endTime. Bajarildimi?")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setContentIntent(fullScreenPendingIntent)
            .setAutoCancel(true)
            .setOngoing(true)

        notificationManager.notify(NOTIFICATION_ID_ALARM, builder.build())

        // Also launch AlarmActivity directly so the screen turns on even if app was in background
        try {
            context.startActivity(fullScreenIntent)
        } catch (e: Exception) {
            Log.e(TAG, "startActivity xatolik: ${e.message}")
        }
    }

    fun cancelAlarmNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID_ALARM)
    }

    /**
     * Schedules alarms for all tasks of the day:
     * 1. At task START: fires ACTION_TASK_START so widgets and AI Wallpaper automatically update to the new task.
     * 2. At task END: fires ACTION_FIRE_ALARM for uncompleted tasks so user is notified on time.
     */
    fun scheduleAllTasksForToday(context: Context, tasks: List<com.example.data.model.ScheduleItem>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val now = System.currentTimeMillis()

        tasks.forEachIndexed { index, task ->
            // 1. Schedule Task Start (for automatic Wallpaper and Widget transition)
            val startMillis = calculateTargetAlarmTime("", task.start)
            if (startMillis != null && startMillis > now) {
                val startIntent = Intent(context, AlarmReceiver::class.java).apply {
                    action = AlarmReceiver.ACTION_TASK_START
                    putExtra(AlarmActivity.EXTRA_TASK_TITLE, task.title)
                    putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, task.category)
                    putExtra(AlarmActivity.EXTRA_TASK_END, task.end)
                }
                val startPendingIntent = PendingIntent.getBroadcast(
                    context,
                    10000 + index,
                    startIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startMillis, startPendingIntent)
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startMillis, startPendingIntent)
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to schedule start alarm for ${task.title}: ${e.message}")
                }
            }

            // 2. Schedule Task End Alarm (if not yet marked done)
            if (!task.isDone) {
                val endMillis = calculateTargetAlarmTime(task.start, task.end)
                if (endMillis != null && endMillis > now) {
                    val endIntent = Intent(context, AlarmReceiver::class.java).apply {
                        action = AlarmReceiver.ACTION_FIRE_ALARM
                        putExtra(AlarmActivity.EXTRA_TASK_ID, task.id)
                        putExtra(AlarmActivity.EXTRA_TASK_TITLE, task.title)
                        putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, task.category)
                        putExtra(AlarmActivity.EXTRA_TASK_END, task.end)
                        putExtra(AlarmActivity.EXTRA_TASK_NOTE, task.note)
                    }
                    val endPendingIntent = PendingIntent.getBroadcast(
                        context,
                        20000 + index,
                        endIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, endMillis, endPendingIntent)
                        } else {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, endMillis, endPendingIntent)
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Failed to schedule end alarm for ${task.title}: ${e.message}")
                    }
                }
            }
        }
    }
}
