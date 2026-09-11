package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.HabitState
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskTimeEngine
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
    fun getSchedule(): List<ScheduleItem> {
        val todayStr = getTodayDateString()
        val savedDay = prefs.getString(KEY_SCHEDULE_DAY_KEY, null)
        val rawJson = prefs.getString(KEY_SCHEDULE_JSON, null)

        // If new day or empty schedule, load the actual day's plan from TaskTimeEngine
        if (savedDay != todayStr || rawJson.isNullOrBlank()) {
            val defaultList = TaskTimeEngine.getTodayPlanInfo().first
            saveSchedule(defaultList)
            prefs.edit().putString(KEY_SCHEDULE_DAY_KEY, todayStr).apply()
            return defaultList
        }

        return try {
            val jsonArray = org.json.JSONArray(rawJson)
            val list = mutableListOf<ScheduleItem>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                val title = obj.optString("title", "")
                val isDone = isTaskDone(id) || isTaskDoneByTitle(title) || obj.optBoolean("isDone", false)
                list.add(
                    ScheduleItem(
                        id = id,
                        title = title,
                        category = obj.optString("category", "general"),
                        start = obj.optString("start", "00:00"),
                        end = obj.optString("end", "00:00"),
                        note = obj.optString("note", ""),
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
                val hasPeshin = list.any {
                    it.title.contains("Peshin", ignoreCase = true) ||
                    (it.category.equals("prayer", ignoreCase = true) && (it.start.startsWith("12:") || it.start.startsWith("13:")))
                }
                if (!hasPeshin) {
                    val cal = java.util.Calendar.getInstance()
                    val isSunday = cal.get(java.util.Calendar.DAY_OF_WEEK) == java.util.Calendar.SUNDAY
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
        if (raw.isNullOrBlank()) {
            val defaults = listOf(
                com.example.data.model.VocabCard(
                    word = "Perseverance",
                    translation = "Sabr-matonat, qat'iyat",
                    phonetic = "/ˌpɜːrsəˈvɪrəns/",
                    partOfSpeech = "noun",
                    definition = "Persistence in doing something despite difficulty or delay in achieving success.",
                    example = "His perseverance in studying English helped him achieve his goals.",
                    mnemonic = "Sabr qilib har kuni davom ettirish",
                    boxLevel = 1
                ),
                com.example.data.model.VocabCard(
                    word = "Punctuality",
                    translation = "Vaqtga rioya qilish, aniqlik",
                    phonetic = "/ˌpʌŋktʃuˈæləti/",
                    partOfSpeech = "noun",
                    definition = "The quality of being on time.",
                    example = "Punctuality is a crucial habit for daily success.",
                    mnemonic = "Nuqta kabi har daqiqani aniq qilish",
                    boxLevel = 2
                ),
                com.example.data.model.VocabCard(
                    word = "Resilience",
                    translation = "Chidamlilik, bardoshlik",
                    phonetic = "/rɪˈzɪliəns/",
                    partOfSpeech = "noun",
                    definition = "The capacity to recover quickly from difficulties; toughness.",
                    example = "Mental resilience allows you to stick to your schedule even when tired.",
                    mnemonic = "Qiyinchilikdan tez tiklanish",
                    boxLevel = 1
                )
            )
            saveVocabCards(defaults)
            return defaults
        }
        return try {
            val array = org.json.JSONArray(raw)
            val list = mutableListOf<com.example.data.model.VocabCard>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    com.example.data.model.VocabCard(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        word = obj.optString("word", ""),
                        translation = obj.optString("translation", ""),
                        phonetic = obj.optString("phonetic", ""),
                        partOfSpeech = obj.optString("partOfSpeech", "noun"),
                        definition = obj.optString("definition", ""),
                        example = obj.optString("example", ""),
                        mnemonic = obj.optString("mnemonic", ""),
                        boxLevel = obj.optInt("boxLevel", 1),
                        level = obj.optString("level", "A1"),
                        sourceDocName = obj.optString("sourceDocName", ""),
                        isMastered = obj.optBoolean("isMastered", false),
                        reviewCount = obj.optInt("reviewCount", 0),
                        lastReviewedEpochMs = obj.optLong("lastReviewedEpochMs", System.currentTimeMillis())
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
                    put("mnemonic", c.mnemonic)
                    put("boxLevel", c.boxLevel)
                    put("level", c.level)
                    put("sourceDocName", c.sourceDocName)
                    put("isMastered", c.isMastered)
                    put("reviewCount", c.reviewCount)
                    put("lastReviewedEpochMs", c.lastReviewedEpochMs)
                }
                array.put(obj)
            }
            prefs.edit().putString(KEY_VOCAB_JSON, array.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- Alarm Sound & Volume ---
    var isAlarmMuted: Boolean
        get() = prefs.getBoolean("key_alarm_muted", false)
        set(value) = prefs.edit().putBoolean("key_alarm_muted", value).apply()

    var alarmVolume: Float
        get() = prefs.getFloat("key_alarm_volume", 0.85f)
        set(value) = prefs.edit().putFloat("key_alarm_volume", value.coerceIn(0f, 1f)).apply()

    var alarmSoundTone: String
        get() = prefs.getString("key_alarm_sound_tone", "STANDARD") ?: "STANDARD"
        set(value) = prefs.edit().putString("key_alarm_sound_tone", value).apply()

    // --- Daily Vocabulary Target & Goal ---
    var dailyVocabGoal: Int
        get() = prefs.getInt("key_daily_vocab_goal", 10).coerceAtLeast(10)
        set(value) = prefs.edit().putInt("key_daily_vocab_goal", value.coerceAtLeast(10)).apply()

    var vocabLearnedTodayCount: Int
        get() = prefs.getInt("key_vocab_learned_${getTodayDateString()}", 0)
        set(value) = prefs.edit().putInt("key_vocab_learned_${getTodayDateString()}", value).apply()

    fun incrementVocabLearnedToday() {
        vocabLearnedTodayCount = vocabLearnedTodayCount + 1
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
}
