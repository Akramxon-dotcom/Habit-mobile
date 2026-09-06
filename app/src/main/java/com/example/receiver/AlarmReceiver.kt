package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.alarm.AlarmHelper
import com.example.ui.alarm.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_FIRE_ALARM = "com.example.habit.ACTION_FIRE_ALARM"
        private const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d(TAG, "AlarmReceiver chaqirildi: action=${intent?.action}")
        val title = intent?.getStringExtra(AlarmActivity.EXTRA_TASK_TITLE) ?: "Vazifa"
        val category = intent?.getStringExtra(AlarmActivity.EXTRA_TASK_CATEGORY) ?: ""
        val endTime = intent?.getStringExtra(AlarmActivity.EXTRA_TASK_END) ?: ""
        val note = intent?.getStringExtra(AlarmActivity.EXTRA_TASK_NOTE) ?: ""

        AlarmHelper.showAlarmNotification(
            context = context,
            title = title,
            category = category,
            endTime = endTime,
            note = note
        )
    }
}
