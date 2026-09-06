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

object AlarmHelper {
    private const val TAG = "AlarmHelper"
    const val CHANNEL_ID_ALARM = "habit_alarm_channel"
    const val NOTIFICATION_ID_ALARM = 7788

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            val alarmChannel = NotificationChannel(
                CHANNEL_ID_ALARM,
                context.getString(R.string.notification_channel_alarm),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_alarm_desc)
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 800, 400, 800)
                setSound(alarmSound, audioAttributes)
                setBypassDnd(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }

            notificationManager.createNotificationChannel(alarmChannel)
        }
    }

    fun triggerAlarmNow(
        context: Context,
        title: String,
        category: String,
        endTime: String,
        note: String
    ) {
        Log.d(TAG, "triggerAlarmNow chaqirildi: title=$title, end=$endTime")
        createNotificationChannels(context)

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ACTION_FIRE_ALARM
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
}
