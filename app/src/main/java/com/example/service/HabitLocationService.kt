package com.example.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.alarm.AlarmHelper
import com.example.data.local.HabitPreferences
import com.example.data.remote.FirestoreClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HabitLocationService : Service() {

    companion object {
        private const val TAG = "HabitLocationService"
        const val CHANNEL_ID_LOCATION = "habit_location_channel"
        const val NOTIFICATION_ID_LOCATION = 1001

        const val ACTION_START_SERVICE = "ACTION_START_HABIT_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_HABIT_SERVICE"

        private val _isRunning = MutableStateFlow(false)
        val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

        private val _lastDetectedLocation = MutableStateFlow<String?>(null)
        val lastDetectedLocation: StateFlow<String?> = _lastDetectedLocation.asStateFlow()

        fun startService(context: Context) {
            val intent = Intent(context, HabitLocationService::class.java).apply {
                action = ACTION_START_SERVICE
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stopService(context: Context) {
            val intent = Intent(context, HabitLocationService::class.java).apply {
                action = ACTION_STOP_SERVICE
            }
            context.startService(intent)
        }
    }

    private lateinit var prefs: HabitPreferences
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback? = null

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    private var pollerJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        prefs = HabitPreferences(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP_SERVICE -> {
                Log.d(TAG, "Xizmatni to'xtatish so'rovi qabul qilindi")
                stopForegroundService()
                return START_NOT_STICKY
            }
            else -> {
                Log.d(TAG, "Xizmat ishga tushirilmoqda")
                startAsForeground()
                startLocationTracking()
                startFirestorePoller()
                _isRunning.value = true
                prefs.isLocationServiceEnabled = true
                return START_STICKY
            }
        }
    }

    private fun startAsForeground() {
        val notification = buildForegroundNotification(getString(R.string.service_location_running_text))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID_LOCATION,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            )
        } else {
            startForeground(NOTIFICATION_ID_LOCATION, notification)
        }
    }

    private fun buildForegroundNotification(statusText: String): Notification {
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, HabitLocationService::class.java).apply {
            action = ACTION_STOP_SERVICE
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            1,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID_LOCATION)
            .setContentTitle(getString(R.string.service_location_running_title))
            .setContentText(statusText)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(mainPendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .addAction(R.drawable.ic_launcher_foreground, "To'xtatish", stopPendingIntent)
            .build()
    }

    private fun updateNotificationText(statusText: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID_LOCATION, buildForegroundNotification(statusText))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_LOCATION,
                getString(R.string.notification_channel_location),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.notification_channel_location_desc)
                setShowBadge(false)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationTracking() {
        if (locationCallback != null) return

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 30_000L)
            .setMinUpdateIntervalMillis(15_000L)
            .setMinUpdateDistanceMeters(15f)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val lastLoc = result.lastLocation ?: return
                onLocationChanged(lastLoc)
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback!!,
                Looper.getMainLooper()
            )
            Log.d(TAG, "Joylashuv yangilanishlari so'raldi")
        } catch (e: SecurityException) {
            Log.e(TAG, "Joylashuv ruxsati yo'q: ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "requestLocationUpdates xatolik: ${e.message}")
        }
    }

    private fun onLocationChanged(location: Location) {
        val currentLat = location.latitude
        val currentLng = location.longitude
        val nowMs = System.currentTimeMillis()
        val isoTimestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date(nowMs))

        _lastDetectedLocation.value = String.format(Locale.US, "%.5f, %.5f", currentLat, currentLng)

        val radius = prefs.radiusMeters
        val distanceResults = FloatArray(1)

        // Check RTM
        val rtmLat = prefs.rtmLat
        val rtmLng = prefs.rtmLng
        Location.distanceBetween(currentLat, currentLng, rtmLat, rtmLng, distanceResults)
        val distanceToRtm = distanceResults[0]

        if (distanceToRtm <= radius) {
            Log.d(TAG, "RTM hududiga kirildi! Masofa: $distanceToRtm m")
            if (prefs.canRecordArrivalRtm(nowMs)) {
                prefs.markArrivalRtm(nowMs)
                updateNotificationText("RTM ga kelindi ($isoTimestamp)")
                serviceScope.launch {
                    val res = FirestoreClient.patchArrival("RTM", isoTimestamp)
                    if (res.isSuccess) {
                        Log.d(TAG, "Firestore'ga RTM kelishi muvaffaqiyatli saqlandi")
                    }
                }
            } else {
                Log.d(TAG, "RTM: 30 daqiqalik oraliq hali tugamagan.")
            }
        }

        // Check Maktab
        val maktabLat = prefs.maktabLat
        val maktabLng = prefs.maktabLng
        Location.distanceBetween(currentLat, currentLng, maktabLat, maktabLng, distanceResults)
        val distanceToMaktab = distanceResults[0]

        if (distanceToMaktab <= radius) {
            Log.d(TAG, "Maktab hududiga kirildi! Masofa: $distanceToMaktab m")
            if (prefs.canRecordArrivalMaktab(nowMs)) {
                prefs.markArrivalMaktab(nowMs)
                updateNotificationText("Maktabga kelindi ($isoTimestamp)")
                serviceScope.launch {
                    val res = FirestoreClient.patchArrival("Maktab", isoTimestamp)
                    if (res.isSuccess) {
                        Log.d(TAG, "Firestore'ga Maktab kelishi muvaffaqiyatli saqlandi")
                    }
                }
            } else {
                Log.d(TAG, "Maktab: 30 daqiqalik oraliq hali tugamagan.")
            }
        }
    }

    private fun startFirestorePoller() {
        if (pollerJob != null) return

        pollerJob = serviceScope.launch {
            while (isActive) {
                try {
                    checkFirestoreStateAndAlarm()
                } catch (e: Exception) {
                    Log.e(TAG, "Firestore poller xatolik: ${e.message}")
                }
                delay(60_000L) // Polling every 60 seconds
            }
        }
    }

    private suspend fun checkFirestoreStateAndAlarm() {
        val result = FirestoreClient.getState()
        if (result.isSuccess) {
            val state = result.getOrThrow()
            prefs.saveCachedState(state)

            // Check if end time passed and alarm hasn't been answered yet
            if (state.title.isNotBlank() && isPastEndTime(state.end)) {
                val answered = prefs.isAlarmAnswered(state.title, state.end)
                if (!answered) {
                    Log.d(TAG, "Vazifa muddati o'tgan va javob berilmagan: ${state.title} (${state.end})")
                    AlarmHelper.triggerAlarmNow(
                        context = this@HabitLocationService,
                        title = state.title,
                        category = state.category,
                        endTime = state.end,
                        note = state.note
                    )
                }
            }
        }
    }

    private fun isPastEndTime(endTimeStr: String): Boolean {
        if (endTimeStr.isBlank()) return false
        val parts = endTimeStr.trim().split(":")
        if (parts.size != 2) return false
        val endHour = parts[0].toIntOrNull() ?: return false
        val endMinute = parts[1].toIntOrNull() ?: return false

        val cal = Calendar.getInstance()
        val nowHour = cal.get(Calendar.HOUR_OF_DAY)
        val nowMinute = cal.get(Calendar.MINUTE)

        return (nowHour > endHour) || (nowHour == endHour && nowMinute >= endMinute)
    }

    private fun stopForegroundService() {
        _isRunning.value = false
        prefs.isLocationServiceEnabled = false

        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
            locationCallback = null
        }

        pollerJob?.cancel()
        pollerJob = null

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        _isRunning.value = false
        prefs.isLocationServiceEnabled = false

        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
        serviceJob.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
