package com.example.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.R
import com.example.ui.alarm.AlarmActivity

class AlarmRingtoneService : Service() {

    companion object {
        private const val TAG = "AlarmRingtoneService"
        const val CHANNEL_ID_ALARM_RINGING = "habit_alarm_ringing_channel"
        const val NOTIFICATION_ID_RINGING = 9988

        const val ACTION_START_RINGING = "com.example.habit.ACTION_START_RINGING"
        const val ACTION_STOP_RINGING = "com.example.habit.ACTION_STOP_RINGING"

        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_END = "extra_end"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_TASK_ID = "extra_task_id"

        private var _isRingingActive: Boolean = false
        fun isServiceRunning(): Boolean = _isRingingActive

        fun startAlarm(
            context: Context,
            title: String,
            category: String,
            end: String,
            note: String,
            taskId: String = ""
        ) {
            val intent = Intent(context, AlarmRingtoneService::class.java).apply {
                action = ACTION_START_RINGING
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_CATEGORY, category)
                putExtra(EXTRA_END, end)
                putExtra(EXTRA_NOTE, note)
                putExtra(EXTRA_TASK_ID, taskId)
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (e: Exception) {
                Log.e(TAG, "startForegroundService xatolik: ${e.message}")
            }
        }

        fun stopAlarm(context: Context) {
            val intent = Intent(context, AlarmRingtoneService::class.java).apply {
                action = ACTION_STOP_RINGING
            }
            try {
                context.startService(intent)
            } catch (e: Exception) {
                Log.e(TAG, "stopAlarm xatolik: ${e.message}")
            }
        }
    }

    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP_RINGING -> {
                Log.d(TAG, "Signal to'xtatildi")
                stopForegroundAndSelf()
                return START_NOT_STICKY
            }
            else -> {
                val title = intent?.getStringExtra(EXTRA_TITLE) ?: "Vazifa"
                val category = intent?.getStringExtra(EXTRA_CATEGORY) ?: ""
                val end = intent?.getStringExtra(EXTRA_END) ?: ""
                val note = intent?.getStringExtra(EXTRA_NOTE) ?: ""
                val taskId = intent?.getStringExtra(EXTRA_TASK_ID) ?: ""

                acquireWakeLock()
                _isRingingActive = true
                startForegroundRinging(title, category, end, note, taskId)
                startAudioAndVibration()
                launchAlarmActivity(title, category, end, note, taskId)
                return START_NOT_STICKY
            }
        }
    }

    private fun acquireWakeLock() {
        try {
            val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            @Suppress("DEPRECATION")
            wakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        PowerManager.ON_AFTER_RELEASE,
                "habit:AlarmRingtoneWakeLock"
            ).apply {
                acquire(3 * 60 * 1000L) // Max 3 minutes
            }
        } catch (e: Exception) {
            Log.e(TAG, "WakeLock olishda xatolik: ${e.message}")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_ALARM_RINGING,
                getString(R.string.notification_channel_alarm),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.notification_channel_alarm_desc)
                enableLights(true)
                enableVibration(true)
                setBypassDnd(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    private fun startForegroundRinging(
        title: String,
        category: String,
        end: String,
        note: String,
        taskId: String = ""
    ) {
        val fullScreenIntent = Intent(this, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NO_USER_ACTION
            putExtra(AlarmActivity.EXTRA_TASK_ID, taskId)
            putExtra(AlarmActivity.EXTRA_TASK_TITLE, title)
            putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, category)
            putExtra(AlarmActivity.EXTRA_TASK_END, end)
            putExtra(AlarmActivity.EXTRA_TASK_NOTE, note)
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID_RINGING,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID_ALARM_RINGING)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("⏰ Vazifa vaqti yetib keldi: $title")
            .setContentText("Kategoriya: $category | Vaqt: $end. Bajarildimi?")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setContentIntent(fullScreenPendingIntent)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID_RINGING,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID_RINGING, notification)
        }
    }

    private fun startAudioAndVibration() {
        val prefs = com.example.data.local.HabitPreferences(applicationContext)

        // Start Audio (if not muted by user, school geofence, or silent mode)
        if (prefs.shouldMuteAlarm()) {
            Log.d(TAG, "Eslatma signali ovozsiz qilingan (shouldMuteAlarm=true, isAlarmMuted=${prefs.isAlarmMuted}, isAtSchool=${prefs.isAtSchool})")
        } else {
            try {
                val alarmUri: Uri = when (prefs.alarmSoundTone) {
                    "NOTIFICATION" -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    "RINGTONE" -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                    else -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                        ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                        ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                }

                val vol = prefs.alarmVolume.coerceIn(0f, 1f)

                mediaPlayer = MediaPlayer().apply {
                    setDataSource(applicationContext, alarmUri)
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setLegacyStreamType(AudioManager.STREAM_ALARM)
                            .build()
                    )
                    setVolume(vol, vol)
                    isLooping = true
                    prepare()
                    start()
                }
                Log.d(TAG, "Audio chalish boshlandi: vol=$vol, tone=${prefs.alarmSoundTone}")
            } catch (e: Exception) {
                Log.e(TAG, "Audio chalishda xatolik: ${e.message}")
            }
        }

        // Start Vibration
        try {
            vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            val pattern = longArrayOf(0, 800, 400, 800, 400)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createWaveform(pattern, 0))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(pattern, 0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Tebranishda xatolik: ${e.message}")
        }
    }

    private fun launchAlarmActivity(
        title: String,
        category: String,
        end: String,
        note: String,
        taskId: String = ""
    ) {
        try {
            val intent = Intent(this, AlarmActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NO_USER_ACTION
                putExtra(AlarmActivity.EXTRA_TASK_ID, taskId)
                putExtra(AlarmActivity.EXTRA_TASK_TITLE, title)
                putExtra(AlarmActivity.EXTRA_TASK_CATEGORY, category)
                putExtra(AlarmActivity.EXTRA_TASK_END, end)
                putExtra(AlarmActivity.EXTRA_TASK_NOTE, note)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "AlarmActivity ni to'g'ridan-to'g'ri ochishda xatolik: ${e.message}")
        }
    }

    private fun stopForegroundAndSelf() {
        _isRingingActive = false
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e(TAG, "mediaPlayer to'xtatishda xatolik: ${e.message}")
        }

        try {
            vibrator?.cancel()
            vibrator = null
        } catch (e: Exception) {
            Log.e(TAG, "vibrator bekor qilishda xatolik: ${e.message}")
        }

        try {
            if (wakeLock?.isHeld == true) {
                wakeLock?.release()
            }
            wakeLock = null
        } catch (e: Exception) {
            Log.e(TAG, "wakeLock ozod qilishda xatolik: ${e.message}")
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        stopForegroundAndSelf()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
