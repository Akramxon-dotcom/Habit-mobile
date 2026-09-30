package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import com.example.data.model.HabitState
import com.example.data.model.HomeworkEntry
import com.example.data.model.HomeworkSubTask
import com.example.data.model.ScheduleItem
import com.example.data.model.ScriptDocument
import com.example.data.model.ScriptSentenceChunk
import com.example.data.model.TaskTimeEngine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HabitPreferences(val context: Context) {
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

        private const val KEY_SCHEDULE_JSON = "schedule_items_json"
        private const val KEY_SCHEDULE_DAY_KEY = "schedule_day_key"
        private const val KEY_TIME_BANK = "time_bank_minutes"
        private const val KEY_STREAK = "streak_days"
        private const val KEY_AI_WALLPAPER_ENABLED = "ai_wallpaper_enabled"
        private const val KEY_CUSTOM_WALLPAPER_PATH = "custom_wallpaper_path"
        private const val KEY_WALLPAPER_TARGET = "wallpaper_target"
        private const val KEY_LAST_AI_WALLPAPER_EXPLANATION = "last_ai_wallpaper_explanation"
        private const val KEY_LAST_AI_DECISION_JSON = "last_ai_decision_json"
        private const val KEY_TELEGRAM_BOT_TOKEN = "telegram_bot_token"
        private const val KEY_TELEGRAM_CHAT_ID = "telegram_chat_id"
        private const val KEY_VOCAB_JSON = "vocab_cards_json"
        private const val KEY_JOURNAL_JSON = "journal_entries_json"
        private const val KEY_SELECTED_THEME = "selected_liquid_theme"
        private const val KEY_CUSTOM_LOCATIONS_JSON = "custom_locations_json"
        private const val KEY_IS_BLOCKER_PAUSED = "is_blocker_paused"
        private const val KEY_USER_GEMINI_API_KEY = "user_gemini_api_key"
        private const val KEY_LAST_APPLIED_WALLPAPER_TASK_ID = "last_applied_wallpaper_task_id"
        private const val KEY_VOCAB_VERSION = "vocab_cards_version_v4"
        private const val CURRENT_VOCAB_VERSION = 4
        private const val KEY_SCHEDULE_VERSION = "schedule_version_v6"
        private const val CURRENT_SCHEDULE_VERSION = 6

        private const val KEY_HOMEWORK_PROMPT_TIME = "key_homework_prompt_time"
        private const val KEY_HOMEWORK_PRESETS_JSON = "key_homework_presets_json"
        private const val KEY_HOMEWORK_ENTRIES_JSON = "key_homework_entries_json"

        private const val KEY_APP_USAGE_TRACKING_ENABLED = "key_app_usage_tracking_enabled"
        private const val KEY_IBRAT_REQUIRED_MINUTES = "key_ibrat_required_minutes"
        private const val KEY_CAKE_REQUIRED_MINUTES = "key_cake_required_minutes"
        private const val KEY_SCRIPT_DOCS_JSON = "key_script_docs_json"
        private const val KEY_COURSERA_CERTS_COUNT = "key_coursera_certs_count"
        private const val KEY_COURSERA_CERTS_DATE = "key_coursera_certs_date"
        private const val KEY_COURSERA_TARGET_DAILY = "key_coursera_target_daily"
        private const val KEY_IS_SCHOOL_DAY = "key_is_school_day"
        private const val KEY_LAST_MORNING_PROMPT_DATE = "key_last_morning_prompt_date"

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
    fun getTodayDateString(): String {
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

    fun unmarkAlarmAnswered(taskTitle: String, endTime: String) {
        if (taskTitle.isBlank()) return
        val key = "alarm_answered_${getTodayDateString()}_${taskTitle.trim()}_${endTime.trim()}"
        prefs.edit().remove(key).apply()
    }

    // --- Task Status Tracking (Done / Missed) ---
    fun setTaskDone(taskId: String, isDone: Boolean) {
        val key = "task_done_${getTodayDateString()}_$taskId"
        prefs.edit().putBoolean(key, isDone).apply()
    }

    fun isTaskDone(taskId: String): Boolean {
        val key = "task_done_${getTodayDateString()}_$taskId"
        return prefs.getBoolean(key, false)
    }

    fun setTaskDoneByTitle(title: String, isDone: Boolean) {
        val clean = title.trim().lowercase()
        if (clean.isBlank()) return
        val key = "task_done_title_${getTodayDateString()}_$clean"
        prefs.edit().putBoolean(key, isDone).apply()
    }

    fun isTaskDoneByTitle(title: String): Boolean {
        val clean = title.trim().lowercase()
        if (clean.isBlank()) return false
        val key = "task_done_title_${getTodayDateString()}_$clean"
        return prefs.getBoolean(key, false)
    }

    /**
     * Marks a task as completed in the local schedule, calculates early completion minutes for Time Bank,
     * updates streak, and persists the changes immediately.
     */
    fun markTaskCompletedByTitleOrId(taskId: String?, taskTitle: String, endTime: String? = null): ScheduleItem? {
        val schedule = getSchedule().toMutableList()
        val cleanTitle = taskTitle.trim().lowercase()

        var target: ScheduleItem? = null

        // 1. Match by exact ID
        if (!taskId.isNullOrBlank()) {
            target = schedule.find { it.id == taskId }
        }

        // 2. Match by exact title
        if (target == null && cleanTitle.isNotBlank()) {
            target = schedule.find { it.title.trim().lowercase() == cleanTitle }
        }

        // 3. Match by partial title
        if (target == null && cleanTitle.isNotBlank()) {
            target = schedule.find {
                it.title.lowercase().contains(cleanTitle) || cleanTitle.contains(it.title.lowercase())
            }
        }

        // 3b. Match by alphanumeric stripped representation (handles 'yoʻl' vs 'yo\'l' vs 'yo l', etc.)
        if (target == null && cleanTitle.isNotBlank()) {
            val strippedClean = cleanTitle.replace(Regex("[^a-z0-9]"), "")
            if (strippedClean.length >= 3) {
                target = schedule.find {
                    val strippedItem = it.title.lowercase().replace(Regex("[^a-z0-9]"), "")
                    strippedItem == strippedClean || strippedItem.contains(strippedClean) || strippedClean.contains(strippedItem)
                }
            }
        }

        // 4. Match by end time
        if (target == null && !endTime.isNullOrBlank()) {
            target = schedule.find { it.end == endTime }
        }

        if (target != null) {
            setTaskDone(target.id, true)
            setTaskDoneByTitle(target.title, true)
            val updated = schedule.map {
                if (it.id == target.id || it.title.equals(target.title, ignoreCase = true)) {
                    it.copy(isDone = true)
                } else {
                    it
                }
            }
            saveSchedule(updated)

            val savedMin = TaskTimeEngine.calculateEarlyCompletionMinutes(
                task = target,
                currentTime = TaskTimeEngine.getFormattedCurrentTime()
            )
            if (savedMin > 0) {
                addTimeBankMinutes(savedMin)
            }

            val cached = getCachedState()
            if (cached.title.equals(target.title, ignoreCase = true)) {
                saveCachedState(HabitState())
            }
            return target
        } else {
            if (!taskId.isNullOrBlank()) {
                setTaskDone(taskId, true)
            }
            if (taskTitle.isNotBlank()) {
                setTaskDoneByTitle(taskTitle, true)
            }
        }
        return null
    }

    /**
     * Checks if Peshin prayer is in the schedule. If missing, restores it to the exact chronological place.
     */
    fun restorePeshinPrayerIfMissing(): Boolean {
        val schedule = getSchedule().toMutableList()
        val hasPeshin = schedule.any {
            it.title.contains("Peshin", ignoreCase = true) ||
            (it.category.equals("prayer", ignoreCase = true) && (it.start.startsWith("12:") || it.start.startsWith("13:")))
        }
        if (!hasPeshin) {
            val cal = java.util.Calendar.getInstance()
            val isSunday = cal.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY
            val peshin = if (isSunday) {
                ScheduleItem(
                    id = "prayer_peshin_sunday",
                    title = "Tahorat → Peshin namozi → dua",
                    category = "prayer",
                    start = "12:20",
                    end = "12:52",
                    note = "Peshin ibodati",
                    blocking = true
                )
            } else {
                ScheduleItem(
                    id = "prayer_peshin_weekday",
                    title = "Yo'l → Tahorat → Peshin namozi → dua",
                    category = "prayer",
                    start = "13:00",
                    end = "13:35",
                    note = "Peshin ibodati",
                    blocking = true
                )
            }
            schedule.add(peshin)
            schedule.sortBy { TaskTimeEngine.parseMinuteOfDay(it.start) ?: 0 }
            saveSchedule(schedule)
            return true
        }
        return false
    }

    /**
     * Explicitly adds/restores Peshin prayer, replacing any conflicting duplicate.
     */
    fun forceAddPeshinPrayer(): ScheduleItem {
        val schedule = getSchedule().toMutableList()
        schedule.removeAll { it.title.contains("Peshin", ignoreCase = true) }
        val cal = java.util.Calendar.getInstance()
        val isSunday = cal.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY
        val peshin = if (isSunday) {
            ScheduleItem(
                id = "prayer_peshin_${System.currentTimeMillis()}",
                title = "Tahorat → Peshin namozi → dua",
                category = "prayer",
                start = "12:20",
                end = "12:52",
                note = "Peshin ibodati",
                blocking = true
            )
        } else {
            ScheduleItem(
                id = "prayer_peshin_${System.currentTimeMillis()}",
                title = "Yo'l → Tahorat → Peshin namozi → dua",
                category = "prayer",
                start = "13:00",
                end = "13:35",
                note = "Peshin ibodati",
                blocking = true
            )
        }
        schedule.add(peshin)
        schedule.sortBy { TaskTimeEngine.parseMinuteOfDay(it.start) ?: 0 }
        saveSchedule(schedule)
        return peshin
    }

    fun getTimeBankMinutes(): Int {
        return prefs.getInt(KEY_TIME_BANK, 0)
    }

    fun addTimeBankMinutes(minutes: Int) {
        val cur = getTimeBankMinutes()
        prefs.edit().putInt(KEY_TIME_BANK, (cur + minutes).coerceAtLeast(0)).apply()
    }

    fun saveTimeBankMinutes(minutes: Int) {
        prefs.edit().putInt(KEY_TIME_BANK, minutes.coerceAtLeast(0)).apply()
    }

    fun getStreak(): Int {
        return prefs.getInt(KEY_STREAK, 3)
    }

    fun setStreak(streak: Int) {
        prefs.edit().putInt(KEY_STREAK, streak).apply()
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

    // --- Schedule Storage ---
    fun resetToDefaultSchedule(): List<ScheduleItem> {
        val todayStr = getTodayDateString()
        val defaultList = if (getIsSchoolDay()) {
            com.example.data.model.MorningSchoolScheduleEngine.getSchoolDayPlan()
        } else {
            com.example.data.model.MorningSchoolScheduleEngine.getNoSchoolDayPlan()
        }
        saveSchedule(defaultList)
        prefs.edit()
            .putString(KEY_SCHEDULE_DAY_KEY, todayStr)
            .putInt(KEY_SCHEDULE_VERSION, CURRENT_SCHEDULE_VERSION)
            .apply()
        return defaultList
    }

    fun getSchedule(): List<ScheduleItem> {
        val todayStr = getTodayDateString()
        val savedDay = prefs.getString(KEY_SCHEDULE_DAY_KEY, null)
        val rawJson = prefs.getString(KEY_SCHEDULE_JSON, null)
        val savedVersion = prefs.getInt(KEY_SCHEDULE_VERSION, 0)

        // If new day, empty schedule, or updated schedule engine version, load the actual day's plan from TaskTimeEngine
        if (savedDay != todayStr || rawJson.isNullOrBlank() || savedVersion < CURRENT_SCHEDULE_VERSION) {
            return resetToDefaultSchedule()
        }

        return try {
            val jsonArray = org.json.JSONArray(rawJson)
            val list = mutableListOf<ScheduleItem>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                var title = obj.optString("title", "")
                var category = obj.optString("category", "general")
                var note = obj.optString("note", "")

                // Migrate legacy 4-guruh task to Uyga vazifalar
                if (title.contains("4 guruh", ignoreCase = true) || title.contains("4-guruh", ignoreCase = true)) {
                    title = "Uyga vazifalar"
                    category = "homework"
                    note = "Bugungi barcha dars va markaz uyga vazifalarini bajarish"
                }

                val isDone = isTaskDone(id) || isTaskDoneByTitle(title) || obj.optBoolean("isDone", false)
                list.add(
                    ScheduleItem(
                        id = id,
                        title = title,
                        category = category,
                        start = obj.optString("start", "00:00"),
                        end = obj.optString("end", "00:00"),
                        note = note,
                        blocking = obj.optBoolean("blocking", true),
                        isDone = isDone
                    )
                )
            }
            if (list.isEmpty()) {
                val defaultList = TaskTimeEngine.getTodayPlanInfo().first
                saveSchedule(defaultList)
                defaultList
            } else {
                val cal = java.util.Calendar.getInstance()
                val isSunday = cal.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY
                val hasSchool = list.any {
                    it.category.equals("school", ignoreCase = true) ||
                    it.category.equals("rtm", ignoreCase = true) ||
                    it.title.contains("Maktab", ignoreCase = true)
                }

                // If Sunday has school tasks, or weekday is missing school tasks, reset to today's exact plan
                if ((isSunday && hasSchool) || (!isSunday && !hasSchool)) {
                    val defaultList = TaskTimeEngine.getTodayPlanInfo().first
                    saveSchedule(defaultList)
                    return defaultList
                }

                val hasPeshin = list.any {
                    it.title.contains("Peshin", ignoreCase = true) ||
                    (it.category.equals("prayer", ignoreCase = true) && (it.start.startsWith("12:") || it.start.startsWith("13:")))
                }
                if (!hasPeshin) {
                    val peshin = if (isSunday) {
                        ScheduleItem(
                            id = "prayer_peshin_auto_restored",
                            title = "Tahorat → Peshin namozi → dua",
                            category = "prayer",
                            start = "12:20",
                            end = "12:52",
                            note = "Peshin ibodati",
                            blocking = true
                        )
                    } else {
                        ScheduleItem(
                            id = "prayer_peshin_auto_restored",
                            title = "Yo'l → Tahorat → Peshin namozi → dua",
                            category = "prayer",
                            start = "13:00",
                            end = "13:35",
                            note = "Peshin ibodati",
                            blocking = true
                        )
                    }
                    list.add(peshin)
                    list.sortBy { TaskTimeEngine.parseMinuteOfDay(it.start) ?: 0 }
                    saveSchedule(list)
                }
                list
            }
        } catch (e: Exception) {
            val defaultList = TaskTimeEngine.getTodayPlanInfo().first
            saveSchedule(defaultList)
            defaultList
        }
    }

    fun saveSchedule(items: List<ScheduleItem>) {
        try {
            val jsonArray = org.json.JSONArray()
            for (item in items) {
                val obj = org.json.JSONObject().apply {
                    put("id", item.id)
                    put("title", item.title)
                    put("category", item.category)
                    put("start", item.start)
                    put("end", item.end)
                    put("note", item.note)
                    put("blocking", item.blocking)
                    put("isDone", item.isDone)
                }
                jsonArray.put(obj)
            }
            prefs.edit().putString(KEY_SCHEDULE_JSON, jsonArray.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addScheduleItem(item: ScheduleItem) {
        val current = getSchedule().toMutableList()
        current.add(item)
        current.sortBy { TaskTimeEngine.parseMinuteOfDay(it.start) ?: 0 }
        saveSchedule(current)
    }

    fun deleteScheduleItem(id: String) {
        val current = getSchedule().filter { it.id != id }
        saveSchedule(current)
    }

    fun updateScheduleItem(item: ScheduleItem) {
        val current = getSchedule().map { if (it.id == item.id) item else it }
        saveSchedule(current)
    }

    // --- Aileaders.uz Coursera Certificate & Morning School Tracking ---
    fun getCourseraCertsDoneToday(): Int {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val savedDate = prefs.getString(KEY_COURSERA_CERTS_DATE, "")
        if (savedDate != today) {
            return 0
        }
        return prefs.getInt(KEY_COURSERA_CERTS_COUNT, 0)
    }

    fun setCourseraCertsDoneToday(count: Int) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        prefs.edit()
            .putInt(KEY_COURSERA_CERTS_COUNT, count.coerceAtLeast(0))
            .putString(KEY_COURSERA_CERTS_DATE, today)
            .apply()
    }

    fun getCourseraTargetDaily(): Int {
        return prefs.getInt(KEY_COURSERA_TARGET_DAILY, 50)
    }

    fun setCourseraTargetDaily(target: Int) {
        prefs.edit().putInt(KEY_COURSERA_TARGET_DAILY, target).apply()
    }

    fun getIsSchoolDay(): Boolean {
        return prefs.getBoolean(KEY_IS_SCHOOL_DAY, true)
    }

    fun setIsSchoolDay(isSchool: Boolean) {
        prefs.edit().putBoolean(KEY_IS_SCHOOL_DAY, isSchool).apply()
    }

    fun getLastMorningPromptDate(): String {
        return prefs.getString(KEY_LAST_MORNING_PROMPT_DATE, "") ?: ""
    }

    fun setLastMorningPromptDate(dateStr: String) {
        prefs.edit().putString(KEY_LAST_MORNING_PROMPT_DATE, dateStr).apply()
    }

    fun shouldShowMorningSchoolPrompt(): Boolean {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val lastDate = getLastMorningPromptDate()
        val cal = java.util.Calendar.getInstance()
        val hour = cal.get(java.util.Calendar.HOUR_OF_DAY)
        return lastDate != today && hour >= 6
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

    // --- AI Wallpaper ---
    var isAiWallpaperEnabled: Boolean
        get() = prefs.getBoolean(KEY_AI_WALLPAPER_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_AI_WALLPAPER_ENABLED, value).apply()

    var customWallpaperPath: String
        get() = prefs.getString(KEY_CUSTOM_WALLPAPER_PATH, "") ?: ""
        set(value) = prefs.edit().putString(KEY_CUSTOM_WALLPAPER_PATH, value).apply()

    var wallpaperTarget: String
        get() = prefs.getString(KEY_WALLPAPER_TARGET, "BOTH") ?: "BOTH" // "BOTH", "HOME", "LOCK"
        set(value) = prefs.edit().putString(KEY_WALLPAPER_TARGET, value).apply()

    var lastAiWallpaperExplanation: String
        get() = prefs.getString(KEY_LAST_AI_WALLPAPER_EXPLANATION, "AI bo'sh joyga moslashtirdi") ?: "AI bo'sh joyga moslashtirdi"
        set(value) = prefs.edit().putString(KEY_LAST_AI_WALLPAPER_EXPLANATION, value).apply()

    var lastAiWallpaperDecisionJson: String
        get() = prefs.getString(KEY_LAST_AI_DECISION_JSON, "") ?: ""
        set(value) = prefs.edit().putString(KEY_LAST_AI_DECISION_JSON, value).apply()

    // --- Telegram Reporting ---
    var telegramBotToken: String
        get() = prefs.getString(KEY_TELEGRAM_BOT_TOKEN, "") ?: ""
        set(value) = prefs.edit().putString(KEY_TELEGRAM_BOT_TOKEN, value).apply()

    var telegramChatId: String
        get() = prefs.getString(KEY_TELEGRAM_CHAT_ID, "") ?: ""
        set(value) = prefs.edit().putString(KEY_TELEGRAM_CHAT_ID, value).apply()

    // --- Vocab Cards ---
    fun getVocabCards(): List<com.example.data.model.VocabCard> {
        val raw = prefs.getString(KEY_VOCAB_JSON, null)
        val savedVersion = prefs.getInt(KEY_VOCAB_VERSION, 0)

        if (raw.isNullOrBlank() || savedVersion < CURRENT_VOCAB_VERSION) {
            val defaults = com.example.data.util.Oxford3000Database.getAllOxfordCards(context)
            if (raw.isNullOrBlank()) {
                saveVocabCards(defaults)
                prefs.edit().putInt(KEY_VOCAB_VERSION, CURRENT_VOCAB_VERSION).apply()
                return defaults
            }
            // Merge existing cards to retain user mastery & reviews
            val existing = parseVocabCardsJson(raw)
            val existingByWord = existing.associateBy { it.word.lowercase(java.util.Locale.ROOT) }
            val merged = mutableListOf<com.example.data.model.VocabCard>()

            for (defCard in defaults) {
                val matched = existingByWord[defCard.word.lowercase(java.util.Locale.ROOT)]
                if (matched != null) {
                    // Retain user's mastery, reviewCount, boxLevel, learnedDate, favorite
                    merged.add(
                        defCard.copy(
                            id = matched.id,
                            isMastered = matched.isMastered,
                            boxLevel = matched.boxLevel,
                            learnedDate = matched.learnedDate,
                            reviewCount = matched.reviewCount,
                            lastReviewedEpochMs = matched.lastReviewedEpochMs,
                            isFavorite = matched.isFavorite,
                            importanceRank = if (matched.importanceRank < 9999) matched.importanceRank else defCard.importanceRank
                        )
                    )
                } else {
                    merged.add(defCard)
                }
            }

            // Also keep any custom words the user added that aren't in Oxford database
            val defaultWords = defaults.map { it.word.lowercase(java.util.Locale.ROOT) }.toSet()
            for (ex in existing) {
                if (!defaultWords.contains(ex.word.lowercase(java.util.Locale.ROOT))) {
                    merged.add(ex)
                }
            }

            val sorted = merged.sortedWith(
                compareBy<com.example.data.model.VocabCard> { it.importanceRank }
                    .thenBy { com.example.data.util.VocabDocumentParser.getLevelWeight(it.level) }
                    .thenBy { it.word.lowercase(java.util.Locale.ROOT) }
            )

            saveVocabCards(sorted)
            prefs.edit().putInt(KEY_VOCAB_VERSION, CURRENT_VOCAB_VERSION).apply()
            return sorted
        }

        return parseVocabCardsJson(raw)
    }

    private fun parseVocabCardsJson(raw: String): List<com.example.data.model.VocabCard> {
        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<com.example.data.model.VocabCard>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val isMastered = obj.optBoolean("isMastered", false)
                var lDate = obj.optString("learnedDate", "")
                if (isMastered && lDate.isBlank()) {
                    lDate = com.example.data.model.TaskTimeEngine.getIsoDateForOffset(0)
                }
                list.add(
                    com.example.data.model.VocabCard(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        word = obj.optString("word", ""),
                        translation = obj.optString("translation", ""),
                        phonetic = obj.optString("phonetic", ""),
                        partOfSpeech = obj.optString("partOfSpeech", "noun"),
                        definition = obj.optString("definition", ""),
                        example = obj.optString("example", ""),
                        exampleTranslation = obj.optString("exampleTranslation", ""),
                        synonym = obj.optString("synonym", ""),
                        mnemonic = obj.optString("mnemonic", ""),
                        boxLevel = obj.optInt("boxLevel", 1),
                        level = obj.optString("level", "A1"),
                        sourceDocName = obj.optString("sourceDocName", ""),
                        isMastered = isMastered,
                        learnedDate = lDate,
                        reviewCount = obj.optInt("reviewCount", 0),
                        lastReviewedEpochMs = obj.optLong("lastReviewedEpochMs", System.currentTimeMillis()),
                        importanceRank = obj.optInt("importanceRank", 9999),
                        isFavorite = obj.optBoolean("isFavorite", false)
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveVocabCards(cards: List<com.example.data.model.VocabCard>) {
        try {
            val array = org.json.JSONArray()
            for (c in cards) {
                val obj = org.json.JSONObject().apply {
                    put("id", c.id)
                    put("word", c.word)
                    put("translation", c.translation)
                    put("phonetic", c.phonetic)
                    put("partOfSpeech", c.partOfSpeech)
                    put("definition", c.definition)
                    put("example", c.example)
                    put("exampleTranslation", c.exampleTranslation)
                    put("synonym", c.synonym)
                    put("mnemonic", c.mnemonic)
                    put("boxLevel", c.boxLevel)
                    put("level", c.level)
                    put("sourceDocName", c.sourceDocName)
                    put("isMastered", c.isMastered)
                    put("learnedDate", c.learnedDate)
                    put("reviewCount", c.reviewCount)
                    put("lastReviewedEpochMs", c.lastReviewedEpochMs)
                    put("importanceRank", c.importanceRank)
                    put("isFavorite", c.isFavorite)
                }
                array.put(obj)
            }
            prefs.edit().putString(KEY_VOCAB_JSON, array.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleVocabCardFavorite(cardId: String): Boolean {
        val cards = getVocabCards().toMutableList()
        val idx = cards.indexOfFirst { it.id == cardId }
        if (idx != -1) {
            val current = cards[idx]
            val updated = current.copy(isFavorite = !current.isFavorite)
            cards[idx] = updated
            saveVocabCards(cards)
            return updated.isFavorite
        }
        return false
    }

    // --- Daily Active Vocabulary Batch Persistence ---
    var todayVocabBatchDate: String
        get() = prefs.getString("key_today_vocab_batch_date", "") ?: ""
        set(value) = prefs.edit().putString("key_today_vocab_batch_date", value).apply()

    fun getTodayVocabBatchIds(): List<String> {
        val raw = prefs.getString("key_today_vocab_batch_ids", null) ?: return emptyList()
        return try {
            val arr = org.json.JSONArray(raw)
            (0 until arr.length()).map { arr.getString(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveTodayVocabBatchIds(ids: List<String>) {
        val arr = org.json.JSONArray()
        ids.distinct().forEach { arr.put(it) }
        prefs.edit().putString("key_today_vocab_batch_ids", arr.toString()).apply()
    }

    // --- Notifications Master & Per-Task Controls ---
    var isNotificationsEnabled: Boolean
        get() = prefs.getBoolean("key_notifications_master_enabled", true)
        set(value) = prefs.edit().putBoolean("key_notifications_master_enabled", value).apply()

    fun getMutedTaskNotificationIds(): Set<String> {
        return prefs.getStringSet("key_muted_task_notifications", emptySet()) ?: emptySet()
    }

    fun isTaskNotificationMuted(taskId: String): Boolean {
        if (taskId.isBlank()) return false
        val set = getMutedTaskNotificationIds()
        return set.contains(taskId)
    }

    fun setTaskNotificationMuted(taskId: String, muted: Boolean) {
        if (taskId.isBlank()) return
        val current = getMutedTaskNotificationIds().toMutableSet()
        if (muted) {
            current.add(taskId)
        } else {
            current.remove(taskId)
        }
        prefs.edit().putStringSet("key_muted_task_notifications", current).apply()
    }

    fun toggleTaskNotificationMuted(taskId: String): Boolean {
        if (taskId.isBlank()) return false
        val current = getMutedTaskNotificationIds().toMutableSet()
        val willBeMuted = !current.contains(taskId)
        if (willBeMuted) {
            current.add(taskId)
        } else {
            current.remove(taskId)
        }
        prefs.edit().putStringSet("key_muted_task_notifications", current).apply()
        return willBeMuted
    }

    // --- Alarm Sound & Volume ---
    var isAlarmMuted: Boolean
        get() = prefs.getBoolean("key_alarm_muted", false)
        set(value) = prefs.edit().putBoolean("key_alarm_muted", value).apply()

    var isSchoolMuted: Boolean
        get() = prefs.getBoolean("key_is_school_muted", false)
        set(value) = prefs.edit().putBoolean("key_is_school_muted", value).apply()

    var isAtSchool: Boolean
        get() = prefs.getBoolean("key_is_at_school", false)
        set(value) = prefs.edit().putBoolean("key_is_at_school", value).apply()

    var wasMutedBeforeSchool: Boolean
        get() = prefs.getBoolean("key_was_muted_before_school", false)
        set(value) = prefs.edit().putBoolean("key_was_muted_before_school", value).apply()

    var previousRingerMode: Int
        get() = prefs.getInt("key_previous_ringer_mode", -1)
        set(value) = prefs.edit().putInt("key_previous_ringer_mode", value).apply()

    /**
     * Checks if phone system ringer is currently set to SILENT, VIBRATE, or volume is 0
     */
    fun isPhoneInSilentOrVibrateMode(ctx: Context? = null): Boolean {
        val c = ctx ?: context
        return try {
            val audioManager = c.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return false
            when (audioManager.ringerMode) {
                AudioManager.RINGER_MODE_SILENT, AudioManager.RINGER_MODE_VIBRATE -> true
                else -> {
                    val ringVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING)
                    val alarmVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM)
                    ringVolume == 0 || alarmVolume == 0
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Checks if phone system ringer is currently set to completely SILENT
     */
    fun isPhoneInCompleteSilentMode(ctx: Context? = null): Boolean {
        val c = ctx ?: context
        return try {
            val audioManager = c.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return false
            audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Absolute check whether any alarm, ringtone, or test sound must be completely muted:
     * - Returns true if user muted sounds in top bar or settings (isAlarmMuted)
     * - OR if user is at school (isSchoolMuted or isAtSchool)
     * - OR if user set their phone itself to silent / vibrate / mute
     */
    fun shouldMuteAlarm(ctx: Context? = null): Boolean {
        if (isAlarmMuted || isSchoolMuted || isAtSchool) return true
        return isPhoneInSilentOrVibrateMode(ctx)
    }

    /**
     * Check whether vibration should also be silenced (e.g. phone in completely silent mode)
     */
    fun shouldMuteVibration(ctx: Context? = null): Boolean {
        return isPhoneInCompleteSilentMode(ctx)
    }

    /**
     * Checks whether given coordinates fall inside the designated school (Maktab) zone.
     */
    fun checkIsAtSchool(lat: Double, lng: Double): Boolean {
        val results = FloatArray(1)
        val schoolLoc = getCustomLocations().firstOrNull {
            it.isEnabled && (it.id == "maktab" || it.name.contains("maktab", ignoreCase = true) || it.actionType == "SCHOOL_MUTE")
        }
        val targetLat = schoolLoc?.lat ?: maktabLat
        val targetLng = schoolLoc?.lng ?: maktabLng
        val effectiveRadius = (schoolLoc?.radiusMeters ?: radiusMeters).coerceAtLeast(60f)
        android.location.Location.distanceBetween(lat, lng, targetLat, targetLng, results)
        return results[0] <= effectiveRadius
    }

    var alarmVolume: Float
        get() = prefs.getFloat("key_alarm_volume", 0.85f)
        set(value) = prefs.edit().putFloat("key_alarm_volume", value.coerceIn(0f, 1f)).apply()

    var alarmSoundTone: String
        get() = prefs.getString("key_alarm_sound_tone", "STANDARD") ?: "STANDARD"
        set(value) = prefs.edit().putString("key_alarm_sound_tone", value).apply()

    // --- Daily Vocabulary Target & Goal ---
    var dailyVocabGoal: Int
        get() = prefs.getInt("key_daily_vocab_goal", 10).coerceIn(1, 200)
        set(value) = prefs.edit().putInt("key_daily_vocab_goal", value.coerceIn(1, 200)).apply()

    var vocabLearnedTodayCount: Int
        get() = prefs.getInt("key_vocab_learned_${getTodayDateString()}", 0)
        set(value) = prefs.edit().putInt("key_vocab_learned_${getTodayDateString()}", value).apply()

    fun incrementVocabLearnedToday() {
        vocabLearnedTodayCount = vocabLearnedTodayCount + 1
    }

    // --- Daily Vocab Quiz State (≥90% pass rule & retry failed words) ---
    fun isQuizPassedToday(): Boolean {
        return prefs.getBoolean("key_quiz_passed_${getTodayDateString()}", false)
    }

    fun markQuizPassedToday() {
        prefs.edit()
            .putBoolean("key_quiz_passed_${getTodayDateString()}", true)
            .remove("key_quiz_failed_ids_${getTodayDateString()}")
            .apply()
    }

    fun getQuizFailedWordIds(): Set<String> {
        return prefs.getStringSet("key_quiz_failed_ids_${getTodayDateString()}", emptySet()) ?: emptySet()
    }

    fun setQuizFailedWordIds(ids: Set<String>) {
        prefs.edit().putStringSet("key_quiz_failed_ids_${getTodayDateString()}", ids).apply()
    }

    fun clearQuizFailedWordIds() {
        prefs.edit().remove("key_quiz_failed_ids_${getTodayDateString()}").apply()
    }

    // --- Journal Entries ---
    fun getJournalEntries(): List<com.example.data.model.JournalEntry> {
        val raw = prefs.getString(KEY_JOURNAL_JSON, null) ?: return emptyList()
        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<com.example.data.model.JournalEntry>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    com.example.data.model.JournalEntry(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        dateStr = obj.optString("dateStr", ""),
                        stars = obj.optInt("stars", 5),
                        highlights = obj.optString("highlights", ""),
                        challenges = obj.optString("challenges", ""),
                        reflections = obj.optString("reflections", ""),
                        savedTimeMinutes = obj.optInt("savedTimeMinutes", 0),
                        completedTasksCount = obj.optInt("completedTasksCount", 0)
                    )
                )
            }
            list.sortedByDescending { it.dateStr }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveJournalEntries(entries: List<com.example.data.model.JournalEntry>) {
        try {
            val array = org.json.JSONArray()
            for (entry in entries) {
                val obj = org.json.JSONObject().apply {
                    put("id", entry.id)
                    put("dateStr", entry.dateStr)
                    put("stars", entry.stars)
                    put("highlights", entry.highlights)
                    put("challenges", entry.challenges)
                    put("reflections", entry.reflections)
                    put("savedTimeMinutes", entry.savedTimeMinutes)
                    put("completedTasksCount", entry.completedTasksCount)
                }
                array.put(obj)
            }
            prefs.edit().putString(KEY_JOURNAL_JSON, array.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSelectedTheme(): String {
        return prefs.getString(KEY_SELECTED_THEME, "emerald") ?: "emerald"
    }

    fun saveSelectedTheme(themeId: String) {
        prefs.edit().putString(KEY_SELECTED_THEME, themeId).apply()
    }

    fun getLong(key: String, defValue: Long = 0L): Long = prefs.getLong(key, defValue)
    fun putLong(key: String, value: Long) = prefs.edit().putLong(key, value).apply()

    // --- Master Blocker Pause Toggle ---
    var isBlockerPaused: Boolean
        get() = prefs.getBoolean(KEY_IS_BLOCKER_PAUSED, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_BLOCKER_PAUSED, value).apply()

    // --- Custom User Gemini API Key ---
    var userGeminiApiKey: String
        get() = prefs.getString(KEY_USER_GEMINI_API_KEY, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_GEMINI_API_KEY, value.trim()).apply()

    // --- Last Applied Wallpaper Task ID ---
    var lastAppliedWallpaperTaskId: String
        get() = prefs.getString(KEY_LAST_APPLIED_WALLPAPER_TASK_ID, "") ?: ""
        set(value) = prefs.edit().putString(KEY_LAST_APPLIED_WALLPAPER_TASK_ID, value).apply()

    // --- Custom Locations Management ---
    fun getCustomLocations(): List<com.example.data.model.CustomLocation> {
        val raw = prefs.getString(KEY_CUSTOM_LOCATIONS_JSON, null)
        if (raw.isNullOrBlank()) {
            val defaults = listOf(
                com.example.data.model.CustomLocation(
                    id = "rtm",
                    name = "RTM",
                    lat = rtmLat,
                    lng = rtmLng,
                    radiusMeters = radiusMeters,
                    actionType = "AUTO_HABIT",
                    targetHabitTitle = "RTM Mashg'uloti",
                    isEnabled = true
                ),
                com.example.data.model.CustomLocation(
                    id = "maktab",
                    name = "Maktab",
                    lat = maktabLat,
                    lng = maktabLng,
                    radiusMeters = radiusMeters,
                    actionType = "AUTO_HABIT",
                    targetHabitTitle = "Maktab Darslari",
                    isEnabled = true
                )
            )
            saveCustomLocations(defaults)
            return defaults
        }

        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<com.example.data.model.CustomLocation>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    com.example.data.model.CustomLocation(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        name = obj.optString("name", "Joy"),
                        lat = obj.optDouble("lat", 41.311081),
                        lng = obj.optDouble("lng", 69.240562),
                        radiusMeters = obj.optDouble("radiusMeters", 150.0).toFloat(),
                        actionType = obj.optString("actionType", "AUTO_HABIT"),
                        targetHabitTitle = obj.optString("targetHabitTitle", ""),
                        isEnabled = obj.optBoolean("isEnabled", true)
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveCustomLocations(locations: List<com.example.data.model.CustomLocation>) {
        try {
            val array = org.json.JSONArray()
            for (loc in locations) {
                val obj = org.json.JSONObject().apply {
                    put("id", loc.id)
                    put("name", loc.name)
                    put("lat", loc.lat)
                    put("lng", loc.lng)
                    put("radiusMeters", loc.radiusMeters.toDouble())
                    put("actionType", loc.actionType)
                    put("targetHabitTitle", loc.targetHabitTitle)
                    put("isEnabled", loc.isEnabled)
                }
                array.put(obj)
            }
            prefs.edit().putString(KEY_CUSTOM_LOCATIONS_JSON, array.toString()).apply()

            // Also keep legacy RTM and Maktab coordinates in sync
            locations.firstOrNull { it.id == "rtm" }?.let {
                rtmLat = it.lat
                rtmLng = it.lng
            }
            locations.firstOrNull { it.id == "maktab" }?.let {
                maktabLat = it.lat
                maktabLng = it.lng
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun syncGeofenceCoords(rLat: Double, rLng: Double, mLat: Double, mLng: Double, rad: Float) {
        rtmLat = rLat
        rtmLng = rLng
        maktabLat = mLat
        maktabLng = mLng
        radiusMeters = rad

        val list = getCustomLocations().map { loc ->
            when (loc.id) {
                "rtm" -> loc.copy(lat = rLat, lng = rLng, radiusMeters = rad)
                "maktab" -> loc.copy(lat = mLat, lng = mLng, radiusMeters = rad)
                else -> loc
            }
        }
        saveCustomLocations(list)
    }

    fun addCustomLocation(location: com.example.data.model.CustomLocation) {
        val current = getCustomLocations().toMutableList()
        current.removeAll { it.id == location.id }
        current.add(location)
        saveCustomLocations(current)
    }

    fun deleteCustomLocation(id: String) {
        val current = getCustomLocations().toMutableList()
        current.removeAll { it.id == id }
        saveCustomLocations(current)
    }

    fun getBlockedPackageSet(): Set<String> {
        val raw = blockedPackages
        return raw.split(",").map { it.trim() }.filter { it.isNotBlank() }.toSet()
    }

    fun saveBlockedPackageSet(packages: Set<String>) {
        blockedPackages = packages.joinToString(",")
    }

    // --- Temporary Emergency Bypass (Favqulodda ruxsat) ---
    fun setTemporaryEmergencyBypass(durationMinutes: Int = 5) {
        val expiry = System.currentTimeMillis() + (durationMinutes * 60 * 1000L)
        prefs.edit().putLong("key_emergency_bypass_until_ms", expiry).apply()
    }

    fun isEmergencyBypassActive(): Boolean {
        val expiry = prefs.getLong("key_emergency_bypass_until_ms", 0L)
        return System.currentTimeMillis() < expiry
    }

    fun getEmergencyBypassRemainingSeconds(): Int {
        val expiry = prefs.getLong("key_emergency_bypass_until_ms", 0L)
        val diff = expiry - System.currentTimeMillis()
        return if (diff > 0) (diff / 1000L).toInt() else 0
    }

    fun clearEmergencyBypass() {
        prefs.edit().remove("key_emergency_bypass_until_ms").apply()
    }

    // --- 1 Oylik Ingliz Tili Rejasi (A2) ---
    fun getActiveEnglishPlanWeek(): Int {
        return prefs.getInt("key_active_english_plan_week", 1).coerceIn(1, 4)
    }

    fun setActiveEnglishPlanWeek(week: Int) {
        prefs.edit().putInt("key_active_english_plan_week", week.coerceIn(1, 4)).apply()
    }

    fun getCompletedEnglishPlanTaskIds(): Set<String> {
        val raw = prefs.getString("key_completed_english_plan_task_ids", "") ?: ""
        if (raw.isBlank()) return emptySet()
        return raw.split(",").map { it.trim() }.filter { it.isNotBlank() }.toSet()
    }

    fun toggleEnglishPlanTaskId(taskId: String): Boolean {
        val current = getCompletedEnglishPlanTaskIds().toMutableSet()
        val isNowCompleted = if (current.contains(taskId)) {
            current.remove(taskId)
            false
        } else {
            current.add(taskId)
            true
        }
        prefs.edit().putString("key_completed_english_plan_task_ids", current.joinToString(",")).apply()
        return isNowCompleted
    }

    fun setEnglishPlanTaskCompleted(taskId: String, isCompleted: Boolean) {
        val current = getCompletedEnglishPlanTaskIds().toMutableSet()
        if (isCompleted) current.add(taskId) else current.remove(taskId)
        prefs.edit().putString("key_completed_english_plan_task_ids", current.joinToString(",")).apply()
    }

    // --- Speaking: Yodlash Shart Bo'lgan Oltin Lug'at (Essential Words) ---
    private val KEY_SPEAKING_ESSENTIAL_WORDS = "key_speaking_essential_words_v1"

    fun getSpeakingEssentialWords(): List<com.example.data.model.SpeakingEssentialWord> {
        val raw = prefs.getString(KEY_SPEAKING_ESSENTIAL_WORDS, null) ?: return emptyList()
        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<com.example.data.model.SpeakingEssentialWord>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    com.example.data.model.SpeakingEssentialWord(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        word = obj.optString("word", ""),
                        translation = obj.optString("translation", ""),
                        phonetic = obj.optString("phonetic", ""),
                        partOfSpeech = obj.optString("partOfSpeech", "noun"),
                        level = obj.optString("level", "B1"),
                        usageRule = obj.optString("usageRule", ""),
                        dialogueExample = obj.optString("dialogueExample", ""),
                        dialogueTranslation = obj.optString("dialogueTranslation", ""),
                        synonyms = obj.optString("synonyms", ""),
                        spokenTip = obj.optString("spokenTip", ""),
                        sourceMode = obj.optString("sourceMode", "Speaking Room"),
                        addedAtEpochMs = obj.optLong("addedAtEpochMs", System.currentTimeMillis()),
                        isLearned = obj.optBoolean("isLearned", false),
                        reviewCount = obj.optInt("reviewCount", 0)
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveSpeakingEssentialWords(list: List<com.example.data.model.SpeakingEssentialWord>) {
        try {
            val array = org.json.JSONArray()
            for (item in list) {
                val obj = org.json.JSONObject()
                obj.put("id", item.id)
                obj.put("word", item.word)
                obj.put("translation", item.translation)
                obj.put("phonetic", item.phonetic)
                obj.put("partOfSpeech", item.partOfSpeech)
                obj.put("level", item.level)
                obj.put("usageRule", item.usageRule)
                obj.put("dialogueExample", item.dialogueExample)
                obj.put("dialogueTranslation", item.dialogueTranslation)
                obj.put("synonyms", item.synonyms)
                obj.put("spokenTip", item.spokenTip)
                obj.put("sourceMode", item.sourceMode)
                obj.put("addedAtEpochMs", item.addedAtEpochMs)
                obj.put("isLearned", item.isLearned)
                obj.put("reviewCount", item.reviewCount)
                array.put(obj)
            }
            prefs.edit().putString(KEY_SPEAKING_ESSENTIAL_WORDS, array.toString()).apply()
        } catch (e: Exception) {
            // ignore
        }
    }

    /**
     * Qoidaga binoan bir marta qo'shilgan so'zni qayta qo'shib bo'lmaydi!
     * @return true agar muvaffaqiyatli yangi so'z qo'shilgan bo'lsa, false agar so'z allaqachon mavjud bo'lsa.
     */
    fun addSpeakingEssentialWord(word: com.example.data.model.SpeakingEssentialWord): Boolean {
        val current = getSpeakingEssentialWords().toMutableList()
        val alreadyExists = current.any { it.word.equals(word.word.trim(), ignoreCase = true) }
        if (alreadyExists) {
            return false // Qayta qo'shib bo'lmaydi
        }

        current.add(0, word)
        saveSpeakingEssentialWords(current)

        // Umumiy lug'at (VocabCard) ga ham maxsus "⭐ Nutq: Yodlash Shart" belgisi bilan sinxron qo'shamiz
        try {
            val allVocab = getVocabCards().toMutableList()
            if (!allVocab.any { it.word.equals(word.word.trim(), ignoreCase = true) }) {
                allVocab.add(
                    0,
                    com.example.data.model.VocabCard(
                        id = word.id,
                        word = word.word.trim(),
                        translation = word.translation,
                        phonetic = word.phonetic,
                        partOfSpeech = word.partOfSpeech,
                        level = word.level,
                        definition = word.usageRule,
                        example = word.dialogueExample,
                        exampleTranslation = word.dialogueTranslation,
                        synonym = word.synonyms,
                        mnemonic = word.spokenTip,
                        sourceDocName = "⭐ Nutq: Yodlash Shart",
                        boxLevel = 1
                    )
                )
                saveVocabCards(allVocab)
            }
        } catch (e: Exception) {
            // ignore
        }

        return true
    }

    fun isSpeakingWordAlreadySaved(targetWord: String): Boolean {
        val clean = targetWord.trim().lowercase(java.util.Locale.ROOT)
        return getSpeakingEssentialWords().any { it.word.trim().lowercase(java.util.Locale.ROOT) == clean }
    }

    fun toggleSpeakingEssentialWordLearned(id: String): Boolean {
        val list = getSpeakingEssentialWords().toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = list[index]
            val newStatus = !item.isLearned
            list[index] = item.copy(isLearned = newStatus)
            saveSpeakingEssentialWords(list)
            return newStatus
        }
        return false
    }

    fun deleteSpeakingEssentialWord(id: String) {
        val list = getSpeakingEssentialWords().filterNot { it.id == id }
        saveSpeakingEssentialWords(list)
    }

    // =========================================================
    // DISCIPLINE & PUNISHMENT SYSTEM (Murosasiz Jazo va Statistika)
    // =========================================================
    private val KEY_PUNISHMENT_LAST_DATE = "key_punishment_last_date"
    private val KEY_PUNISHMENT_DAILY_MISS_COUNT = "key_punishment_daily_miss_count"

    // Cumulative stats
    private val KEY_TOTAL_PUSHUPS = "key_stat_total_pushups"
    private val KEY_TOTAL_SQUATS = "key_stat_total_squats"
    private val KEY_TOTAL_PLANK_SECONDS = "key_stat_total_plank_sec"
    private val KEY_TOTAL_RUN_SECONDS = "key_stat_total_run_sec"
    private val KEY_TOTAL_WORDS_TYPED = "key_stat_total_words_typed"
    private val KEY_TOTAL_GRAMMAR_PASSED = "key_stat_total_grammar_passed"
    private val KEY_TOTAL_TILOVAT_MINUTES = "key_stat_total_tilovat_min"
    private val KEY_TOTAL_TASBEH_COUNT = "key_stat_total_tasbeh_cnt"
    private val KEY_TOTAL_TIME_FORFEITED = "key_stat_total_time_forfeited_min"

    fun getTodayMissCount(): Int {
        val today = getTodayDateString()
        val lastDate = prefs.getString(KEY_PUNISHMENT_LAST_DATE, "")
        if (lastDate != today) {
            prefs.edit()
                .putString(KEY_PUNISHMENT_LAST_DATE, today)
                .putInt(KEY_PUNISHMENT_DAILY_MISS_COUNT, 0)
                .apply()
            return 0
        }
        return prefs.getInt(KEY_PUNISHMENT_DAILY_MISS_COUNT, 0)
    }

    fun incrementTodayMissCount(): Int {
        val today = getTodayDateString()
        val lastDate = prefs.getString(KEY_PUNISHMENT_LAST_DATE, "")
        var current = if (lastDate == today) prefs.getInt(KEY_PUNISHMENT_DAILY_MISS_COUNT, 0) else 0
        current += 1
        prefs.edit()
            .putString(KEY_PUNISHMENT_LAST_DATE, today)
            .putInt(KEY_PUNISHMENT_DAILY_MISS_COUNT, current)
            .apply()
        return current
    }

    fun recordPunishmentCompleted(type: com.example.data.discipline.PunishmentType, amount: Int) {
        val editor = prefs.edit()
        when (type) {
            com.example.data.discipline.PunishmentType.PUSH_UP -> {
                val prev = prefs.getInt(KEY_TOTAL_PUSHUPS, 0)
                editor.putInt(KEY_TOTAL_PUSHUPS, prev + amount)
            }
            com.example.data.discipline.PunishmentType.SQUAT -> {
                val prev = prefs.getInt(KEY_TOTAL_SQUATS, 0)
                editor.putInt(KEY_TOTAL_SQUATS, prev + amount)
            }
            com.example.data.discipline.PunishmentType.PLANK -> {
                val prev = prefs.getInt(KEY_TOTAL_PLANK_SECONDS, 0)
                editor.putInt(KEY_TOTAL_PLANK_SECONDS, prev + amount)
            }
            com.example.data.discipline.PunishmentType.RUNNING_ON_SPOT -> {
                val prev = prefs.getInt(KEY_TOTAL_RUN_SECONDS, 0)
                editor.putInt(KEY_TOTAL_RUN_SECONDS, prev + amount)
            }
            com.example.data.discipline.PunishmentType.VOCAB_DRILL,
            com.example.data.discipline.PunishmentType.COPY_PARAGRAPH -> {
                val prev = prefs.getInt(KEY_TOTAL_WORDS_TYPED, 0)
                editor.putInt(KEY_TOTAL_WORDS_TYPED, prev + amount)
            }
            com.example.data.discipline.PunishmentType.GRAMMAR_TEST -> {
                val prev = prefs.getInt(KEY_TOTAL_GRAMMAR_PASSED, 0)
                editor.putInt(KEY_TOTAL_GRAMMAR_PASSED, prev + amount)
            }
            com.example.data.discipline.PunishmentType.QURAN_TILOVAT -> {
                val prev = prefs.getInt(KEY_TOTAL_TILOVAT_MINUTES, 0)
                editor.putInt(KEY_TOTAL_TILOVAT_MINUTES, prev + amount)
            }
            com.example.data.discipline.PunishmentType.TASBEH -> {
                val prev = prefs.getInt(KEY_TOTAL_TASBEH_COUNT, 0)
                editor.putInt(KEY_TOTAL_TASBEH_COUNT, prev + amount)
            }
            com.example.data.discipline.PunishmentType.TIME_FORFEIT,
            com.example.data.discipline.PunishmentType.SILENCE_MEDITATION -> {
                val prev = prefs.getInt(KEY_TOTAL_TIME_FORFEITED, 0)
                editor.putInt(KEY_TOTAL_TIME_FORFEITED, prev + amount)
                if (type == com.example.data.discipline.PunishmentType.TIME_FORFEIT) {
                    val currentBank = getTimeBankMinutes()
                    saveTimeBankMinutes((currentBank - amount).coerceAtLeast(0))
                }
            }
        }
        editor.apply()
    }

    data class PunishmentCumulativeStats(
        val totalPushups: Int,
        val totalSquats: Int,
        val totalPlankSec: Int,
        val totalRunSec: Int,
        val totalWordsTyped: Int,
        val totalGrammarPassed: Int,
        val totalTilovatMin: Int,
        val totalTasbehCount: Int,
        val totalTimeForfeitedMin: Int
    )

    fun getCumulativePunishmentStats(): PunishmentCumulativeStats {
        return PunishmentCumulativeStats(
            totalPushups = prefs.getInt(KEY_TOTAL_PUSHUPS, 0),
            totalSquats = prefs.getInt(KEY_TOTAL_SQUATS, 0),
            totalPlankSec = prefs.getInt(KEY_TOTAL_PLANK_SECONDS, 0),
            totalRunSec = prefs.getInt(KEY_TOTAL_RUN_SECONDS, 0),
            totalWordsTyped = prefs.getInt(KEY_TOTAL_WORDS_TYPED, 0),
            totalGrammarPassed = prefs.getInt(KEY_TOTAL_GRAMMAR_PASSED, 0),
            totalTilovatMin = prefs.getInt(KEY_TOTAL_TILOVAT_MINUTES, 0),
            totalTasbehCount = prefs.getInt(KEY_TOTAL_TASBEH_COUNT, 0),
            totalTimeForfeitedMin = prefs.getInt(KEY_TOTAL_TIME_FORFEITED, 0)
        )
    }

    // =========================================================
    // HOMEWORK (UYGA VAZIFALAR) PERSISTENCE & CONFIGURATION
    // =========================================================

    fun getHomeworkPromptTime(): String {
        return prefs.getString(KEY_HOMEWORK_PROMPT_TIME, "17:00") ?: "17:00"
    }

    fun setHomeworkPromptTime(time: String) {
        val clean = time.trim()
        if (clean.matches(Regex("""^([01]\d|2[0-3]):[0-5]\d$"""))) {
            prefs.edit().putString(KEY_HOMEWORK_PROMPT_TIME, clean).apply()
        }
    }

    fun hasPromptedHomeworkToday(): Boolean {
        val key = "hw_prompt_answered_${getTodayDateString()}"
        return prefs.getBoolean(key, false)
    }

    fun setHomeworkPromptAnsweredToday(answered: Boolean = true) {
        val key = "hw_prompt_answered_${getTodayDateString()}"
        prefs.edit().putBoolean(key, answered).apply()
    }

    fun resetHomeworkPromptToday() {
        val key = "hw_prompt_answered_${getTodayDateString()}"
        prefs.edit().remove(key).apply()
    }

    fun getHomeworkPresets(): List<String> {
        val raw = prefs.getString(KEY_HOMEWORK_PRESETS_JSON, null)
        if (raw.isNullOrBlank()) return emptyList()
        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<String>()
            for (i in 0 until array.length()) {
                val str = array.getString(i).trim()
                if (str.isNotBlank() && !list.contains(str)) {
                    list.add(str)
                }
            }
            list
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun addHomeworkPreset(preset: String): List<String> {
        val clean = preset.trim()
        if (clean.isBlank()) return getHomeworkPresets()
        val current = getHomeworkPresets().toMutableList()
        if (!current.contains(clean)) {
            current.add(clean)
            val array = org.json.JSONArray()
            current.forEach { array.put(it) }
            prefs.edit().putString(KEY_HOMEWORK_PRESETS_JSON, array.toString()).apply()
        }
        return current
    }

    fun removeHomeworkPreset(preset: String): List<String> {
        val clean = preset.trim()
        val current = getHomeworkPresets().toMutableList()
        if (current.remove(clean)) {
            val array = org.json.JSONArray()
            current.forEach { array.put(it) }
            prefs.edit().putString(KEY_HOMEWORK_PRESETS_JSON, array.toString()).apply()
        }
        return current
    }

    fun getHomeworkEntries(): List<HomeworkEntry> {
        val raw = prefs.getString(KEY_HOMEWORK_ENTRIES_JSON, null)
        if (raw.isNullOrBlank()) return emptyList()
        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<HomeworkEntry>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                val dateStr = obj.optString("dateStr", getTodayDateString())
                val topic = obj.optString("topic", "")
                val rawText = obj.optString("rawText", "")
                val isCompleted = obj.optBoolean("isCompleted", false)
                val createdAt = obj.optLong("createdAtEpochMs", System.currentTimeMillis())

                val tasksList = mutableListOf<HomeworkSubTask>()
                val tasksArray = obj.optJSONArray("tasks")
                if (tasksArray != null) {
                    for (j in 0 until tasksArray.length()) {
                        val tObj = tasksArray.getJSONObject(j)
                        tasksList.add(
                            HomeworkSubTask(
                                id = tObj.optString("id", java.util.UUID.randomUUID().toString()),
                                text = tObj.optString("text", ""),
                                isDone = tObj.optBoolean("isDone", false)
                            )
                        )
                    }
                }

                list.add(
                    HomeworkEntry(
                        id = id,
                        dateStr = dateStr,
                        topic = topic,
                        rawText = rawText,
                        tasks = tasksList,
                        isCompleted = isCompleted,
                        createdAtEpochMs = createdAt
                    )
                )
            }
            list.sortedByDescending { it.createdAtEpochMs }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun persistHomeworkEntries(entries: List<HomeworkEntry>) {
        try {
            val array = org.json.JSONArray()
            for (entry in entries) {
                val obj = org.json.JSONObject()
                obj.put("id", entry.id)
                obj.put("dateStr", entry.dateStr)
                obj.put("topic", entry.topic)
                obj.put("rawText", entry.rawText)
                obj.put("isCompleted", entry.isCompleted)
                obj.put("createdAtEpochMs", entry.createdAtEpochMs)

                val tasksArray = org.json.JSONArray()
                for (task in entry.tasks) {
                    val tObj = org.json.JSONObject()
                    tObj.put("id", task.id)
                    tObj.put("text", task.text)
                    tObj.put("isDone", task.isDone)
                    tasksArray.put(tObj)
                }
                obj.put("tasks", tasksArray)
                array.put(obj)
            }
            prefs.edit().putString(KEY_HOMEWORK_ENTRIES_JSON, array.toString()).apply()
        } catch (_: Exception) {
        }
    }

    fun saveHomeworkEntry(entry: HomeworkEntry) {
        val current = getHomeworkEntries().toMutableList()
        val index = current.indexOfFirst { it.id == entry.id }
        if (index >= 0) {
            current[index] = entry
        } else {
            current.add(0, entry)
        }
        persistHomeworkEntries(current)
    }

    fun deleteHomeworkEntry(id: String) {
        val current = getHomeworkEntries().toMutableList()
        current.removeAll { it.id == id }
        persistHomeworkEntries(current)
    }

    fun toggleHomeworkSubTask(entryId: String, subTaskId: String) {
        val current = getHomeworkEntries().toMutableList()
        val entryIndex = current.indexOfFirst { it.id == entryId }
        if (entryIndex >= 0) {
            val entry = current[entryIndex]
            val updatedTasks = entry.tasks.map {
                if (it.id == subTaskId) it.copy(isDone = !it.isDone) else it
            }
            val allDone = updatedTasks.isNotEmpty() && updatedTasks.all { it.isDone }
            current[entryIndex] = entry.copy(tasks = updatedTasks, isCompleted = allDone)
            persistHomeworkEntries(current)
        }
    }

    fun toggleHomeworkCompleted(entryId: String) {
        val current = getHomeworkEntries().toMutableList()
        val entryIndex = current.indexOfFirst { it.id == entryId }
        if (entryIndex >= 0) {
            val entry = current[entryIndex]
            val newCompleted = !entry.isCompleted
            val updatedTasks = entry.tasks.map { it.copy(isDone = newCompleted) }
            current[entryIndex] = entry.copy(isCompleted = newCompleted, tasks = updatedTasks)
            persistHomeworkEntries(current)
        }
    }

    fun getTodayHomework(): HomeworkEntry? {
        val todayStr = getTodayDateString()
        return getHomeworkEntries().firstOrNull { it.dateStr == todayStr }
    }

    // --- App Usage Tracking Settings ---
    var isAppUsageTrackingEnabled: Boolean
        get() = prefs.getBoolean(KEY_APP_USAGE_TRACKING_ENABLED, true)
        set(value) = prefs.edit().putBoolean(KEY_APP_USAGE_TRACKING_ENABLED, value).apply()

    var ibratRequiredMinutes: Int
        get() = prefs.getInt(KEY_IBRAT_REQUIRED_MINUTES, 20)
        set(value) = prefs.edit().putInt(KEY_IBRAT_REQUIRED_MINUTES, value.coerceAtLeast(1)).apply()

    var cakeRequiredMinutes: Int
        get() = prefs.getInt(KEY_CAKE_REQUIRED_MINUTES, 20)
        set(value) = prefs.edit().putInt(KEY_CAKE_REQUIRED_MINUTES, value.coerceAtLeast(1)).apply()

    // --- Script Studio (Ingliz tili diktant / transkripsiya) ---
    fun getScriptDocuments(): List<ScriptDocument> {
        val json = prefs.getString(KEY_SCRIPT_DOCS_JSON, null) ?: return emptyList()
        return try {
            val array = org.json.JSONArray(json)
            val list = mutableListOf<ScriptDocument>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                val title = obj.optString("title", "Yangi Script")
                val dateStr = obj.optString("dateStr", getTodayDateString())
                val audioUriStr = if (obj.has("audioUriStr") && !obj.isNull("audioUriStr")) obj.getString("audioUriStr") else null
                val sampleAudioId = if (obj.has("sampleAudioId") && !obj.isNull("sampleAudioId")) obj.getString("sampleAudioId") else null
                val audioTitle = obj.optString("audioTitle", "Audio")
                val audioDurationMs = obj.optLong("audioDurationMs", 120_000L)
                val rawText = obj.optString("rawText", "")
                val isCompleted = obj.optBoolean("isCompleted", false)
                val lastPositionMs = obj.optLong("lastPositionMs", 0L)
                val targetDurationCoverageMs = obj.optLong("targetDurationCoverageMs", 0L)
                val createdAt = obj.optLong("createdAtEpochMs", System.currentTimeMillis())
                val updatedAt = obj.optLong("updatedAtEpochMs", System.currentTimeMillis())

                val chunksList = mutableListOf<ScriptSentenceChunk>()
                if (obj.has("chunks")) {
                    val cArray = obj.getJSONArray("chunks")
                    for (j in 0 until cArray.length()) {
                        val cObj = cArray.getJSONObject(j)
                        chunksList.add(
                            ScriptSentenceChunk(
                                id = cObj.optString("id", java.util.UUID.randomUUID().toString()),
                                startTimeMs = cObj.optLong("startTimeMs", 0L),
                                endTimeMs = cObj.optLong("endTimeMs", 0L),
                                userText = cObj.optString("userText", ""),
                                note = cObj.optString("note", "")
                            )
                        )
                    }
                }

                list.add(
                    ScriptDocument(
                        id = id,
                        title = title,
                        dateStr = dateStr,
                        audioUriStr = audioUriStr,
                        sampleAudioId = sampleAudioId,
                        audioTitle = audioTitle,
                        audioDurationMs = audioDurationMs,
                        rawText = rawText,
                        chunks = chunksList,
                        isCompleted = isCompleted,
                        lastPositionMs = lastPositionMs,
                        targetDurationCoverageMs = targetDurationCoverageMs,
                        createdAtEpochMs = createdAt,
                        updatedAtEpochMs = updatedAt
                    )
                )
            }
            list.sortedByDescending { it.updatedAtEpochMs }
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun saveScriptDocument(doc: ScriptDocument) {
        val current = getScriptDocuments().toMutableList()
        val index = current.indexOfFirst { it.id == doc.id }
        if (index >= 0) {
            current[index] = doc
        } else {
            current.add(0, doc)
        }
        persistScriptDocuments(current)
    }

    fun deleteScriptDocument(id: String) {
        val current = getScriptDocuments().toMutableList()
        current.removeAll { it.id == id }
        persistScriptDocuments(current)
    }

    private fun persistScriptDocuments(docs: List<ScriptDocument>) {
        try {
            val array = org.json.JSONArray()
            for (doc in docs) {
                val obj = org.json.JSONObject()
                obj.put("id", doc.id)
                obj.put("title", doc.title)
                obj.put("dateStr", doc.dateStr)
                if (doc.audioUriStr != null) obj.put("audioUriStr", doc.audioUriStr)
                if (doc.sampleAudioId != null) obj.put("sampleAudioId", doc.sampleAudioId)
                obj.put("audioTitle", doc.audioTitle)
                obj.put("audioDurationMs", doc.audioDurationMs)
                obj.put("rawText", doc.rawText)
                obj.put("isCompleted", doc.isCompleted)
                obj.put("lastPositionMs", doc.lastPositionMs)
                obj.put("targetDurationCoverageMs", doc.targetDurationCoverageMs)
                obj.put("createdAtEpochMs", doc.createdAtEpochMs)
                obj.put("updatedAtEpochMs", doc.updatedAtEpochMs)

                val cArray = org.json.JSONArray()
                for (chunk in doc.chunks) {
                    val cObj = org.json.JSONObject()
                    cObj.put("id", chunk.id)
                    cObj.put("startTimeMs", chunk.startTimeMs)
                    cObj.put("endTimeMs", chunk.endTimeMs)
                    cObj.put("userText", chunk.userText)
                    cObj.put("note", chunk.note)
                    cArray.put(cObj)
                }
                obj.put("chunks", cArray)
                array.put(obj)
            }
            prefs.edit().putString(KEY_SCRIPT_DOCS_JSON, array.toString()).apply()
        } catch (_: Exception) {
        }
    }
}
