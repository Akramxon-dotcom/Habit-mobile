package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.HabitState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HabitPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("habit_local_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_RTM_LAT = "rtm_lat"
        private const val KEY_RTM_LNG = "rtm_lng"
        private const val KEY_MAKTAB_LAT = "maktab_lat"
        private const val KEY_MAKTAB_LNG = "maktab_lng"
        private const val KEY_RADIUS_METERS = "radius_meters"

        private const val KEY_LAST_ARRIVAL_RTM_TIME = "last_arrival_rtm_time"
        private const val KEY_LAST_ARRIVAL_MAKTAB_TIME = "last_arrival_maktab_time"

        private const val KEY_BLOCKED_PACKAGES = "blocked_packages"
        private const val KEY_IS_BLOCKING_ACTIVE = "is_blocking_active"

        private const val KEY_LOCATION_SERVICE_ENABLED = "location_service_enabled"

        // Cached Firestore State
        private const val KEY_CACHED_TITLE = "cached_title"
        private const val KEY_CACHED_CATEGORY = "cached_category"
        private const val KEY_CACHED_START = "cached_start"
        private const val KEY_CACHED_END = "cached_end"
        private const val KEY_CACHED_NOTE = "cached_note"
        private const val KEY_CACHED_LAST_ARRIVAL_PLACE = "cached_last_arrival_place"
        private const val KEY_CACHED_LAST_ARRIVAL = "cached_last_arrival"
        private const val KEY_CACHED_LAST_ANSWER = "cached_last_answer"
        private const val KEY_CACHED_LAST_ANSWER_TITLE = "cached_last_answer_title"
        private const val KEY_CACHED_TIME_MS = "cached_time_ms"

        const val DEFAULT_BLOCKED_PACKAGES = "com.instagram.android,com.zhiliaoapp.musically,com.ss.android.ugc.trill,com.google.android.youtube"
        const val COOLDOWN_MINUTES = 30
        const val COOLDOWN_MS = COOLDOWN_MINUTES * 60 * 1000L
        const val DEFAULT_RADIUS_METERS = 150f
    }

    // --- Coordinates ---
    var rtmLat: Double
        get() = prefs.getString(KEY_RTM_LAT, "41.311081")?.toDoubleOrNull() ?: 41.311081
        set(value) = prefs.edit().putString(KEY_RTM_LAT, value.toString()).apply()

    var rtmLng: Double
        get() = prefs.getString(KEY_RTM_LNG, "69.240562")?.toDoubleOrNull() ?: 69.240562
        set(value) = prefs.edit().putString(KEY_RTM_LNG, value.toString()).apply()

    var maktabLat: Double
        get() = prefs.getString(KEY_MAKTAB_LAT, "41.2995")?.toDoubleOrNull() ?: 41.2995
        set(value) = prefs.edit().putString(KEY_MAKTAB_LAT, value.toString()).apply()

    var maktabLng: Double
        get() = prefs.getString(KEY_MAKTAB_LNG, "69.2401")?.toDoubleOrNull() ?: 69.2401
        set(value) = prefs.edit().putString(KEY_MAKTAB_LNG, value.toString()).apply()

    var radiusMeters: Float
        get() = prefs.getFloat(KEY_RADIUS_METERS, DEFAULT_RADIUS_METERS)
        set(value) = prefs.edit().putFloat(KEY_RADIUS_METERS, value).apply()

    // --- Cooldown checking ---
    fun canRecordArrivalRtm(nowMs: Long = System.currentTimeMillis()): Boolean {
        val lastTime = prefs.getLong(KEY_LAST_ARRIVAL_RTM_TIME, 0L)
        return (nowMs - lastTime) >= COOLDOWN_MS
    }

    fun markArrivalRtm(nowMs: Long = System.currentTimeMillis()) {
        prefs.edit().putLong(KEY_LAST_ARRIVAL_RTM_TIME, nowMs).apply()
    }

    fun canRecordArrivalMaktab(nowMs: Long = System.currentTimeMillis()): Boolean {
        val lastTime = prefs.getLong(KEY_LAST_ARRIVAL_MAKTAB_TIME, 0L)
        return (nowMs - lastTime) >= COOLDOWN_MS
    }

    fun markArrivalMaktab(nowMs: Long = System.currentTimeMillis()) {
        prefs.edit().putLong(KEY_LAST_ARRIVAL_MAKTAB_TIME, nowMs).apply()
    }

    // --- Blocked Packages ---
    var blockedPackages: String
        get() = prefs.getString(KEY_BLOCKED_PACKAGES, DEFAULT_BLOCKED_PACKAGES) ?: DEFAULT_BLOCKED_PACKAGES
        set(value) = prefs.edit().putString(KEY_BLOCKED_PACKAGES, value).apply()

    fun getBlockedPackagesList(): List<String> {
        return blockedPackages
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    // --- Blocking Active (cached from Firestore) ---
    var isBlockingActive: Boolean
        get() = prefs.getBoolean(KEY_IS_BLOCKING_ACTIVE, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_BLOCKING_ACTIVE, value).apply()

    var isLocationServiceEnabled: Boolean
        get() = prefs.getBoolean(KEY_LOCATION_SERVICE_ENABLED, false)
        set(value) = prefs.edit().putBoolean(KEY_LOCATION_SERVICE_ENABLED, value).apply()

    // --- Alarm Answer Tracking ---
    private fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun isAlarmAnswered(taskTitle: String, endTime: String): Boolean {
        if (taskTitle.isBlank()) return true
        val key = "alarm_answered_${getTodayDateString()}_${taskTitle.trim()}_${endTime.trim()}"
        return prefs.getBoolean(key, false)
    }

    fun markAlarmAnswered(taskTitle: String, endTime: String) {
        if (taskTitle.isBlank()) return
        val key = "alarm_answered_${getTodayDateString()}_${taskTitle.trim()}_${endTime.trim()}"
        prefs.edit().putBoolean(key, true).apply()
    }

    // --- State Cache ---
    fun saveCachedState(state: HabitState) {
        prefs.edit()
            .putString(KEY_CACHED_TITLE, state.title)
            .putString(KEY_CACHED_CATEGORY, state.category)
            .putString(KEY_CACHED_START, state.start)
            .putString(KEY_CACHED_END, state.end)
            .putString(KEY_CACHED_NOTE, state.note)
            .putBoolean(KEY_IS_BLOCKING_ACTIVE, state.blocking)
            .putString(KEY_CACHED_LAST_ARRIVAL_PLACE, state.lastArrivalPlace)
            .putString(KEY_CACHED_LAST_ARRIVAL, state.lastArrival)
            .putString(KEY_CACHED_LAST_ANSWER, state.lastAnswer)
            .putString(KEY_CACHED_LAST_ANSWER_TITLE, state.lastAnsweredTitle)
            .putLong(KEY_CACHED_TIME_MS, state.lastUpdatedEpochMs)
            .apply()
    }

    fun getCachedState(): HabitState {
        return HabitState(
            title = prefs.getString(KEY_CACHED_TITLE, "") ?: "",
            category = prefs.getString(KEY_CACHED_CATEGORY, "") ?: "",
            start = prefs.getString(KEY_CACHED_START, "") ?: "",
            end = prefs.getString(KEY_CACHED_END, "") ?: "",
            note = prefs.getString(KEY_CACHED_NOTE, "") ?: "",
            blocking = prefs.getBoolean(KEY_IS_BLOCKING_ACTIVE, false),
            lastArrivalPlace = prefs.getString(KEY_CACHED_LAST_ARRIVAL_PLACE, "") ?: "",
            lastArrival = prefs.getString(KEY_CACHED_LAST_ARRIVAL, "") ?: "",
            lastAnswer = prefs.getString(KEY_CACHED_LAST_ANSWER, "") ?: "",
            lastAnsweredTitle = prefs.getString(KEY_CACHED_LAST_ANSWER_TITLE, "") ?: "",
            lastUpdatedEpochMs = prefs.getLong(KEY_CACHED_TIME_MS, 0L)
        )
    }
}
