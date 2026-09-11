package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarm.AlarmHelper
import com.example.data.local.HabitPreferences
import com.example.service.HabitLocationService

class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BootReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED ||
            intent?.action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            Log.d(TAG, "Qurilma qayta yoqildi, Habit xizmatlari va signallari tiklanmoqda...")
            val prefs = HabitPreferences(context)
            val cached = prefs.getCachedState()

            // Reschedule alarm
            if (cached.title.isNotBlank() && cached.end.isNotBlank()) {
                AlarmHelper.scheduleTaskAlarm(
                    context = context,
                    title = cached.title,
                    category = cached.category,
                    startTime = cached.start,
                    endTime = cached.end,
                    note = cached.note
                )
            }

            // Restore location service if enabled
            if (prefs.isLocationServiceEnabled) {
                HabitLocationService.startService(context)
            }
        }
    }
}
