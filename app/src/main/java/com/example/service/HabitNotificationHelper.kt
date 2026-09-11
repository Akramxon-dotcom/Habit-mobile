package com.example.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.local.HabitPreferences
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskTimeEngine
import com.example.receiver.NotificationActionReceiver

object HabitNotificationHelper {

    private const val CHANNEL_ID = "habit_active_task_channel"
    private const val NOTIFICATION_ID = 2001

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Faol vazifa eslatmasi"
            val desc = "Hozirgi bajarilishi kerak bo'lgan vazifani ko'rsatadi"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
                setShowBadge(false)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showActiveTaskNotification(context: Context) {
        createNotificationChannel(context)
        val prefs = HabitPreferences(context)
        val schedule = prefs.getSchedule()
        val cached = prefs.getCachedState()
        val active = TaskTimeEngine.findActiveScheduleItem(schedule)
        val next = TaskTimeEngine.findNextUpcomingScheduleItem(schedule)

        val item = when {
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

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (item == null) {
            manager.cancel(NOTIFICATION_ID)
            return
        }

        val title = "${item.getCategoryIcon()} ${item.title}"
        val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
        val endMin = TaskTimeEngine.parseMinuteOfDay(item.end) ?: (nowMin + 30)
        val diff = endMin - nowMin
        val timeSub = if (diff > 0) "${item.start}–${item.end} · $diff daqiqa qoldi" else "${item.start}–${item.end} · Vaqti yakunlandi"

        val openIntent = Intent(context, MainActivity::class.java)
        val openPending = PendingIntent.getActivity(
            context, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val doneIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_NOTIFICATION_DONE
        }
        val donePending = PendingIntent.getBroadcast(
            context, 10, doneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val delayIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = NotificationActionReceiver.ACTION_NOTIFICATION_DELAY_15
        }
        val delayPending = PendingIntent.getBroadcast(
            context, 11, delayIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(timeSub)
            .setSubText(item.getCategoryLabel())
            .setContentIntent(openPending)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .addAction(0, "✅ Bajardim", donePending)
            .addAction(0, "⏱ +15 min", delayPending)
            .build()

        manager.notify(NOTIFICATION_ID, notification)
    }

    fun cancelNotification(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(NOTIFICATION_ID)
    }
}
