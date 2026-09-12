package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.AlarmHelper
import com.example.data.local.HabitPreferences
import com.example.data.model.HabitState
import com.example.data.model.RealtimeTaskInfo
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskStatus
import com.example.data.model.TaskTimeEngine
import com.example.data.remote.FirestoreClient
import com.example.service.HabitBlockerService
import com.example.service.HabitLocationService
import com.example.ui.permissions.PermissionUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class HabitUiState(
    val habitState: HabitState = HabitState(),
    val realtimeTaskInfo: RealtimeTaskInfo = RealtimeTaskInfo(
        status = TaskStatus.FREE_TIME,
        statusLabel = "Tekshirilmoqda...",
        progress = 0f,
        progressPercent = 0,
        timeRemainingText = "--",
        isBlockingActiveNow = false
    ),
    val scheduleItems: List<ScheduleItem> = emptyList(),
    val activeScheduleItem: ScheduleItem? = null,
    val nextScheduleItem: ScheduleItem? = null,
    val currentTimeString: String = "00:00:00",
    val currentDateString: String = "",
    val dayTypeLabel: String = "Dars kuni",
    val greetingText: String = "Xayrli kun",
    val streak: Int = 3,
    val levelName: String = "Intizomli",
    val timeBankMinutes: Int = 0,
    val todayProgressPercent: Int = 0,
    val arcProgressPercent: Float = 0.5f,
    val selectedTab: Int = 0, // 0: Bugun, 1: Statistika, 2: Tizim
    val selectedDayOffset: Int = 0, // -3..+3
    val isLoading: Boolean = false,
    val isLocationServiceRunning: Boolean = false,
    val isAccessibilityConnected: Boolean = false,
    val permissionStatus: PermissionUtils.PermissionStatus = PermissionUtils.PermissionStatus(
        hasFineLocation = false,
        hasBackgroundLocation = false,
        hasNotification = false,
        hasExactAlarm = false,
        hasAccessibility = false,
        isBatteryOptimizedIgnored = false
    ),
    // Editable settings
    val rtmLat: String = "41.311081",
    val rtmLng: String = "69.240562",
    val maktabLat: String = "41.2995",
    val maktabLng: String = "69.2401",
    val radiusMeters: String = "150",
    val blockedPackages: String = HabitPreferences.DEFAULT_BLOCKED_PACKAGES,
    val lastSyncFormatted: String = "Hali yangilanmadi",
    val lastDetectedLocation: String? = null,
    val alarmStatusText: String = "Tayyor",

    // AI Wallpaper
    val isAiWallpaperEnabled: Boolean = true,
    val isWallpaperUpdating: Boolean = false,
    val wallpaperStatusMessage: String = "",
    val wallpaperTarget: String = "BOTH", // "BOTH", "HOME", "LOCK"
    val hasCustomWallpaper: Boolean = false,
    val wallpaperExplanation: String = "",

    // Weather
    val weatherInfo: com.example.data.remote.WeatherInfo = com.example.data.remote.WeatherInfo(),
    val isWeatherLoading: Boolean = false,

    // Telegram
    val telegramBotToken: String = "",
    val telegramChatId: String = "",
    val isTelegramSending: Boolean = false,
    val telegramStatusMessage: String = "",

    // Vocab & Quiz
    val vocabCards: List<com.example.data.model.VocabCard> = emptyList(),
    val isVocabLoading: Boolean = false,
    val quizQuestions: List<com.example.data.model.QuizQuestion> = emptyList(),
    val isQuizLoading: Boolean = false,

    // Journal
    val journalEntries: List<com.example.data.model.JournalEntry> = emptyList(),
    val isJournalSaving: Boolean = false,

    // BHabits Lock & PIN
    val isLocked: Boolean = false,
    val isPinLockEnabled: Boolean = false,
    val pinCode: String = "0000",

    // BHabits Prayer & Hijri
    val prayers: List<com.example.data.model.PrayerTime> = com.example.data.model.PrayerTimeEngine.getMargilonPrayers(),
    val nextPrayer: com.example.data.model.NextPrayerInfo = com.example.data.model.PrayerTimeEngine.getNextPrayer(),
    val hijriDate: String = com.example.data.model.PrayerTimeEngine.getHijriDateFormatted(),

    // BHabits Late & Focus
    val isLate: Boolean = false,

    // BHabits Supabase Sync
    val isSupabaseSyncing: Boolean = false,
    val supabaseStatus: String = "Ulandi (swpalasnevjudvvvmeio)",

    // BHabits Dialogs
    val isSmartAddOpen: Boolean = false,
    val isQiblaOpen: Boolean = false,
    val isBreathOpen: Boolean = false,
    val isMotionOpen: Boolean = false,

    // Liquid Glass Theme (5 variants)
    val selectedThemeId: String = "emerald",

    // Custom Locations & Geofences
    val customLocations: List<com.example.data.model.CustomLocation> = emptyList(),
    val isCapturingLocation: Boolean = false,

    // App Blocker
    val isBlockerPaused: Boolean = false,
    val installedApps: List<com.example.data.model.InstalledAppInfo> = emptyList(),
    val isAppPickerOpen: Boolean = false,
    val isLoadingApps: Boolean = false,

    // Gemini API
    val userGeminiApiKey: String = "",

    // Alarm Sound & Volume
    val isAlarmMuted: Boolean = false,
    val isAtSchool: Boolean = false,
    val isSchoolMuted: Boolean = false,
    val alarmVolume: Float = 0.85f,
    val alarmSoundTone: String = "STANDARD",

    // Vocabulary Target & Document Engine
    val dailyVocabGoal: Int = 10,
    val vocabLearnedTodayCount: Int = 0,
    val isVocabDocumentParsing: Boolean = false,
    val vocabDocumentStatus: String = ""
)

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = HabitPreferences(application)
    private val _uiState = MutableStateFlow(HabitUiState())
    val uiState: StateFlow<HabitUiState> = _uiState.asStateFlow()

    private val _userMessage = MutableSharedFlow<String>()
    val userMessage: SharedFlow<String> = _userMessage.asSharedFlow()

    init {
        loadSettingsFromPrefs()
        refreshFirestoreState()
        checkPermissions()
        startRealtimeTicker()

        // Monitor location service
        viewModelScope.launch {
            HabitLocationService.isRunning.collect { running ->
                _uiState.update { it.copy(isLocationServiceRunning = running) }
            }
        }

        // Monitor last detected location
        viewModelScope.launch {
            HabitLocationService.lastDetectedLocation.collect { loc ->
                _uiState.update { it.copy(lastDetectedLocation = loc) }
            }
        }

        // Monitor accessibility service
        viewModelScope.launch {
            HabitBlockerService.isServiceConnected.collect { connected ->
                _uiState.update { it.copy(isAccessibilityConnected = connected) }
            }
        }
    }

    private fun startRealtimeTicker() {
        viewModelScope.launch {
            var counter = 0
            while (isActive) {
                updateRealtimeMetrics()
                delay(1000L)
                counter++

                // Auto refresh from Firestore every 30 seconds
                if (counter % 30 == 0) {
                    refreshFirestoreSilently()
                }
            }
        }
    }

    private fun updateRealtimeMetrics() {
        val scheduleList = prefs.getSchedule()
        val curTime = TaskTimeEngine.getFormattedCurrentTime()
        val curDate = TaskTimeEngine.getFormattedCurrentDate()
        val dayInfo = TaskTimeEngine.getTodayPlanInfo()

        // Effective hero task prioritizes active uncompleted task, then next upcoming task
        val effectiveItem = TaskTimeEngine.findEffectiveHeroTask(scheduleList)
        val activeItem = TaskTimeEngine.findActiveScheduleItem(scheduleList)
        val nextItem = TaskTimeEngine.findNextUpcomingScheduleItem(scheduleList)

        // Calculate real-time task status based on effectiveItem
        val effectiveHabit = if (effectiveItem != null) {
            HabitState(
                title = effectiveItem.title,
                category = effectiveItem.category,
                start = effectiveItem.start,
                end = effectiveItem.end,
                note = effectiveItem.note,
                blocking = effectiveItem.blocking
            )
        } else {
            HabitState(
                title = "Kunlik reja yakunlandi",
                category = "rest",
                start = curTime.take(5),
                end = curTime.take(5),
                note = "Barcha vazifalar muvaffaqiyatli bajarildi!",
                blocking = false
            )
        }

        // Automatic Wallpaper update when active task changes in background or foreground
        val currentEffectiveTaskId = effectiveItem?.id ?: "empty"
        if (prefs.isAiWallpaperEnabled && currentEffectiveTaskId != prefs.lastAppliedWallpaperTaskId) {
            prefs.lastAppliedWallpaperTaskId = currentEffectiveTaskId
            viewModelScope.launch {
                com.example.service.AiWallpaperManager.updateWallpaperForCurrentTask(getApplication())
            }
        }

        val info = TaskTimeEngine.calculateTaskInfo(effectiveHabit)

        val segment = (effectiveItem ?: activeItem)?.getSegment() ?: "Kunduz"
        val greeting = TaskTimeEngine.getGreeting(segment)
        val arcPercent = TaskTimeEngine.getDayArcPercent()

        val doneCount = scheduleList.count { it.isDone }
        val totalCount = scheduleList.size.coerceAtLeast(1)
        val progressPct = ((doneCount.toFloat() / totalCount.toFloat()) * 100).toInt()

        val isLate = if (effectiveItem != null && !effectiveItem.isDone) {
            val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
            val startMin = TaskTimeEngine.parseMinuteOfDay(effectiveItem.start) ?: nowMin
            nowMin > (startMin + 10)
        } else false

        val nextPr = com.example.data.model.PrayerTimeEngine.getNextPrayer()
        val hijri = com.example.data.model.PrayerTimeEngine.getHijriDateFormatted()

        _uiState.update {
            it.copy(
                habitState = effectiveHabit,
                currentTimeString = curTime,
                currentDateString = curDate,
                dayTypeLabel = dayInfo.second,
                greetingText = greeting,
                arcProgressPercent = arcPercent,
                realtimeTaskInfo = info,
                scheduleItems = scheduleList,
                activeScheduleItem = effectiveItem ?: activeItem,
                nextScheduleItem = nextItem,
                todayProgressPercent = progressPct,
                streak = prefs.getStreak(),
                timeBankMinutes = prefs.getTimeBankMinutes(),
                isLate = isLate,
                nextPrayer = nextPr,
                hijriDate = hijri,
                isAlarmMuted = prefs.isAlarmMuted,
                isAtSchool = prefs.isAtSchool,
                isSchoolMuted = prefs.isSchoolMuted
            )
        }
    }

    fun loadSettingsFromPrefs() {
        val cached = prefs.getCachedState()
        val scheduleList = prefs.getSchedule()
        val dayInfo = TaskTimeEngine.getTodayPlanInfo()

        val alarmStatus = if (cached.title.isNotBlank() && cached.end.isNotBlank()) {
            AlarmHelper.scheduleTaskAlarm(
                context = getApplication(),
                title = cached.title,
                category = cached.category,
                startTime = cached.start,
                endTime = cached.end,
                note = cached.note
            )
        } else {
            "Vazifa mavjud emas"
        }

        val curTime = TaskTimeEngine.getFormattedCurrentTime()
        val curDate = TaskTimeEngine.getFormattedCurrentDate()
        val activeItem = TaskTimeEngine.findActiveScheduleItem(scheduleList)
        val nextItem = TaskTimeEngine.findNextUpcomingScheduleItem(scheduleList)
        val taskInfo = TaskTimeEngine.calculateTaskInfo(cached)

        val doneCount = scheduleList.count { it.isDone }
        val totalCount = scheduleList.size.coerceAtLeast(1)
        val progressPct = ((doneCount.toFloat() / totalCount.toFloat()) * 100).toInt()

        _uiState.update {
            it.copy(
                habitState = cached,
                scheduleItems = scheduleList,
                activeScheduleItem = activeItem,
                nextScheduleItem = nextItem,
                currentTimeString = curTime,
                currentDateString = curDate,
                dayTypeLabel = dayInfo.second,
                realtimeTaskInfo = taskInfo,
                todayProgressPercent = progressPct,
                streak = prefs.getStreak(),
                timeBankMinutes = prefs.getTimeBankMinutes(),
                rtmLat = prefs.rtmLat.toString(),
                rtmLng = prefs.rtmLng.toString(),
                maktabLat = prefs.maktabLat.toString(),
                maktabLng = prefs.maktabLng.toString(),
                radiusMeters = prefs.radiusMeters.toInt().toString(),
                blockedPackages = prefs.blockedPackages,
                isLocationServiceRunning = prefs.isLocationServiceEnabled,
                isAccessibilityConnected = HabitBlockerService.isAccessibilityEnabled(getApplication()),
                alarmStatusText = alarmStatus,
                isAiWallpaperEnabled = prefs.isAiWallpaperEnabled,
                wallpaperTarget = prefs.wallpaperTarget,
                hasCustomWallpaper = com.example.service.AiWallpaperManager.hasCustomWallpaper(getApplication()),
                wallpaperExplanation = prefs.lastAiWallpaperExplanation,
                telegramBotToken = prefs.telegramBotToken,
                telegramChatId = prefs.telegramChatId,
                vocabCards = prefs.getVocabCards(),
                journalEntries = prefs.getJournalEntries(),
                selectedThemeId = prefs.getSelectedTheme(),
                customLocations = prefs.getCustomLocations(),
                isBlockerPaused = prefs.isBlockerPaused,
                userGeminiApiKey = prefs.userGeminiApiKey,
                isAlarmMuted = prefs.isAlarmMuted,
                isAtSchool = prefs.isAtSchool,
                isSchoolMuted = prefs.isSchoolMuted,
                alarmVolume = prefs.alarmVolume,
                alarmSoundTone = prefs.alarmSoundTone,
                dailyVocabGoal = prefs.dailyVocabGoal,
                vocabLearnedTodayCount = prefs.vocabLearnedTodayCount
            )
        }
        refreshWeather()
        // Update widget, notification and alarms on load
        com.example.widget.HabitAppWidgetProvider.updateAllWidgets(getApplication())
        com.example.service.HabitNotificationHelper.showActiveTaskNotification(getApplication())
        AlarmHelper.scheduleAllTasksForToday(getApplication(), scheduleList)
    }

    fun checkPermissions() {
        val status = PermissionUtils.checkPermissions(getApplication())
        _uiState.update {
            it.copy(
                permissionStatus = status,
                isAccessibilityConnected = status.hasAccessibility
            )
        }
    }

    fun selectTab(tab: Int) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun selectDayOffset(offset: Int) {
        _uiState.update { it.copy(selectedDayOffset = offset) }
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, offset)
        val plan = when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> TaskTimeEngine.SUNDAY_PLAN
            Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY -> TaskTimeEngine.LESSON_PLAN
            else -> TaskTimeEngine.FREE_PLAN
        }
        _uiState.update { it.copy(scheduleItems = plan) }
    }

    fun markTaskCompleted(item: ScheduleItem) {
        prefs.markTaskCompletedByTitleOrId(item.id, item.title, item.end)
        val updated = prefs.getSchedule().map {
            if (it.id == item.id || it.title.trim().equals(item.title.trim(), ignoreCase = true)) it.copy(isDone = true) else it
        }
        prefs.saveSchedule(updated)

        // Clear any old stuck Firestore state if it was for this task
        val currentCached = prefs.getCachedState()
        if (currentCached.title.trim().equals(item.title.trim(), ignoreCase = true)) {
            prefs.saveCachedState(HabitState())
        }

        // Calculate early completion minutes for Time Bank
        val savedMin = TaskTimeEngine.calculateEarlyCompletionMinutes(
            task = item,
            currentTime = TaskTimeEngine.getFormattedCurrentTime()
        )
        if (savedMin > 0) {
            prefs.addTimeBankMinutes(savedMin)
            viewModelScope.launch {
                _userMessage.emit("✅ Bajarildi! Erta yakunlangan $savedMin daqiqa Vaqt sandig'iga tushdi! ⏳")
            }
        } else {
            viewModelScope.launch {
                _userMessage.emit("🎉 «${item.title}» muvaffaqiyatli bajarildi deb belgilandi!")
            }
        }
        updateRealtimeMetrics()
        triggerSystemVisualSync()
    }

    fun delaySchedule(minutes: Int) {
        val currentItems = prefs.getSchedule()
        val nowMin = TaskTimeEngine.getCurrentMinuteOfDay()
        val updated = currentItems.map { item ->
            val startMin = TaskTimeEngine.parseMinuteOfDay(item.start) ?: 0
            val endMin = TaskTimeEngine.parseMinuteOfDay(item.end) ?: 0
            if (startMin >= nowMin) {
                val newStart = (startMin + minutes) % 1440
                val newEnd = (endMin + minutes) % 1440
                val sStr = String.format(Locale.getDefault(), "%02d:%02d", newStart / 60, newStart % 60)
                val eStr = String.format(Locale.getDefault(), "%02d:%02d", newEnd / 60, newEnd % 60)
                item.copy(start = sStr, end = eStr)
            } else {
                item
            }
        }
        prefs.saveSchedule(updated)
        updateRealtimeMetrics()
        triggerSystemVisualSync()
        viewModelScope.launch {
            _userMessage.emit("⏱ Reja +$minutes daqiqaga surildi!")
        }
    }

    fun unlockApp() {
        _uiState.update { it.copy(isLocked = false) }
    }

    fun lockApp() {
        _uiState.update { it.copy(isLocked = true) }
    }

    fun setPinCode(pin: String) {
        _uiState.update { it.copy(pinCode = pin) }
    }

    fun togglePinLock(enabled: Boolean) {
        _uiState.update { it.copy(isPinLockEnabled = enabled) }
    }

    fun setSmartAddOpen(open: Boolean) {
        _uiState.update { it.copy(isSmartAddOpen = open) }
    }

    fun setQiblaOpen(open: Boolean) {
        _uiState.update { it.copy(isQiblaOpen = open) }
    }

    fun setBreathOpen(open: Boolean) {
        _uiState.update { it.copy(isBreathOpen = open) }
    }

    fun setMotionOpen(open: Boolean) {
        _uiState.update { it.copy(isMotionOpen = open) }
    }

    fun addNewTask(item: ScheduleItem) {
        val current = prefs.getSchedule().toMutableList()
        current.add(item)
        prefs.saveSchedule(current)
        _uiState.update { it.copy(scheduleItems = current) }
        updateRealtimeMetrics()
        triggerSystemVisualSync()
        viewModelScope.launch {
            com.example.data.remote.SupabaseClient.syncTask(item)
            _userMessage.emit("✨ «${item.title}» jadvalga qo'shildi va Supabase bulutiga saqlandi!")
        }
    }

    fun syncWithSupabase() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSupabaseSyncing = true) }
            val remoteTasks = com.example.data.remote.SupabaseClient.fetchTasks()
            if (remoteTasks.isNotEmpty()) {
                val localSchedule = prefs.getSchedule().toMutableList()
                val existingIds = localSchedule.map { it.id }.toSet()
                var addedCount = 0
                for (rt in remoteTasks) {
                    if (rt.id !in existingIds) {
                        localSchedule.add(rt)
                        addedCount++
                    }
                }
                if (addedCount > 0) {
                    prefs.saveSchedule(localSchedule)
                    _uiState.update { it.copy(scheduleItems = localSchedule) }
                    updateRealtimeMetrics()
                    triggerSystemVisualSync()
                }
                _uiState.update {
                    it.copy(
                        isSupabaseSyncing = false,
                        supabaseStatus = "Sinxronlandi (${remoteTasks.size} ta vazifa bulutda)"
                    )
                }
                _userMessage.emit("☁️ Supabase bilan muvaffaqiyatli sinxronlandi!")
            } else {
                val localTasks = prefs.getSchedule()
                for (lt in localTasks.take(10)) {
                    com.example.data.remote.SupabaseClient.syncTask(lt)
                }
                _uiState.update {
                    it.copy(
                        isSupabaseSyncing = false,
                        supabaseStatus = "Mahalliy vazifalar bulutga yuklandi"
                    )
                }
                _userMessage.emit("☁️ Vazifalar Supabase bulutiga zaxiralandi!")
            }
        }
    }

    fun replanScheduleWithAi() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _userMessage.emit("🤖 Gemini AI namoz vaqtlari bo'yicha jadvalni qayta rejalashtirmoqda...")
            val currentTasks = prefs.getSchedule()
            val prayers = mapOf(
                "fajr" to "04:45",
                "dhuhr" to "12:35",
                "asr" to "16:45",
                "maghrib" to "18:50",
                "isha" to "20:20"
            )
            val result = com.example.data.remote.GeminiClient.replanSchedule(currentTasks, prayers)
            _uiState.update { it.copy(isLoading = false) }
            result.onSuccess { replanList ->
                val replanMap = replanList.associate { it.id to (it.newStart to it.newEnd) }
                val updated = currentTasks.map { task ->
                    val pair = replanMap[task.id]
                    if (pair != null) {
                        task.copy(start = pair.first, end = pair.second)
                    } else task
                }
                prefs.saveSchedule(updated)
                _uiState.update { it.copy(scheduleItems = updated) }
                updateRealtimeMetrics()
                triggerSystemVisualSync()
                _userMessage.emit("✨ Kun tartibi AI tomonidan namoz va darslarga moslab qayta taqsimlandi!")
            }.onFailure { e ->
                _userMessage.emit("AI xatosi: ${e.localizedMessage ?: "qayta urinib ko'ring"}")
            }
        }
    }

    fun refreshFirestoreState() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = FirestoreClient.getState()
            if (result.isSuccess) {
                val state = result.getOrThrow()
                prefs.saveCachedState(state)
                val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

                val alarmStatus = AlarmHelper.scheduleTaskAlarm(
                    context = getApplication(),
                    title = state.title,
                    category = state.category,
                    startTime = state.start,
                    endTime = state.end,
                    note = state.note
                )

                val taskInfo = TaskTimeEngine.calculateTaskInfo(state)

                _uiState.update {
                    it.copy(
                        habitState = state,
                        isLoading = false,
                        lastSyncFormatted = "Bugun $timeStr",
                        alarmStatusText = alarmStatus,
                        realtimeTaskInfo = taskInfo
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
                _userMessage.emit("Firestore: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    private fun refreshFirestoreSilently() {
        viewModelScope.launch {
            val result = FirestoreClient.getState()
            if (result.isSuccess) {
                val state = result.getOrThrow()
                prefs.saveCachedState(state)
                val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val taskInfo = TaskTimeEngine.calculateTaskInfo(state)

                _uiState.update {
                    it.copy(
                        habitState = state,
                        lastSyncFormatted = "Bugun $timeStr",
                        realtimeTaskInfo = taskInfo
                    )
                }
            }
        }
    }

    fun activateScheduleItem(item: ScheduleItem) {
        val newHabit = HabitState(
            title = item.title,
            category = item.category,
            start = item.start,
            end = item.end,
            note = item.note,
            blocking = item.blocking
        )

        prefs.saveCachedState(newHabit)
        AlarmHelper.scheduleTaskAlarm(
            context = getApplication(),
            title = newHabit.title,
            category = newHabit.category,
            startTime = newHabit.start,
            endTime = newHabit.end,
            note = newHabit.note
        )

        val info = TaskTimeEngine.calculateTaskInfo(newHabit)
        _uiState.update {
            it.copy(
                habitState = newHabit,
                realtimeTaskInfo = info
            )
        }

        viewModelScope.launch {
            val syncResult = FirestoreClient.updateTaskState(
                title = newHabit.title,
                category = newHabit.category,
                start = newHabit.start,
                end = newHabit.end,
                note = newHabit.note,
                blocking = newHabit.blocking
            )
            if (syncResult.isSuccess) {
                _userMessage.emit("Tanlangan vazifa faollashtirildi va Firestore bilan sinxronlandi!")
            } else {
                _userMessage.emit("Lokal faollashtirildi (Firestore: ${syncResult.exceptionOrNull()?.message})")
            }
            triggerSystemVisualSync()
        }
    }

    fun updateCurrentTaskInFirestore(
        title: String,
        category: String,
        start: String,
        end: String,
        note: String,
        blocking: Boolean
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = FirestoreClient.updateTaskState(title, category, start, end, note, blocking)
            if (result.isSuccess) {
                val newState = HabitState(
                    title = title,
                    category = category,
                    start = start,
                    end = end,
                    note = note,
                    blocking = blocking
                )
                prefs.saveCachedState(newState)
                AlarmHelper.scheduleTaskAlarm(
                    context = getApplication(),
                    title = title,
                    category = category,
                    startTime = start,
                    endTime = end,
                    note = note
                )
                val info = TaskTimeEngine.calculateTaskInfo(newState)
                _uiState.update {
                    it.copy(
                        habitState = newState,
                        realtimeTaskInfo = info,
                        isLoading = false
                    )
                }
                triggerSystemVisualSync()
                _userMessage.emit("Vazifa muvaffaqiyatli saqlandi va sinxronlandi!")
            } else {
                _uiState.update { it.copy(isLoading = false) }
                _userMessage.emit("Xatolik: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    private fun triggerSystemVisualSync() {
        try {
            com.example.widget.HabitAppWidgetProvider.updateAllWidgets(getApplication())
            com.example.service.HabitNotificationHelper.showActiveTaskNotification(getApplication())
            if (prefs.isAiWallpaperEnabled) {
                viewModelScope.launch {
                    com.example.service.AiWallpaperManager.updateWallpaperForCurrentTask(getApplication())
                }
            }
            AlarmHelper.scheduleAllTasksForToday(getApplication(), prefs.getSchedule())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addScheduleItem(
        title: String,
        category: String,
        start: String,
        end: String,
        note: String,
        blocking: Boolean
    ) {
        if (title.isBlank() || start.isBlank() || end.isBlank()) {
            viewModelScope.launch { _userMessage.emit("Vazifa nomi va vaqtlarini to'ldiring") }
            return
        }

        val item = ScheduleItem(
            title = title.trim(),
            category = category.trim().lowercase(),
            start = start.trim(),
            end = end.trim(),
            note = note.trim(),
            blocking = blocking
        )

        prefs.addScheduleItem(item)
        val updatedList = prefs.getSchedule()
        _uiState.update { it.copy(scheduleItems = updatedList) }
        updateRealtimeMetrics()
        viewModelScope.launch { _userMessage.emit("Rejaga yangi vazifa qo'shildi!") }
    }

    fun deleteScheduleItem(id: String) {
        prefs.deleteScheduleItem(id)
        val updatedList = prefs.getSchedule()
        _uiState.update { it.copy(scheduleItems = updatedList) }
        updateRealtimeMetrics()
        viewModelScope.launch { _userMessage.emit("Vazifa rejadan o'chirildi") }
    }

    fun restorePeshinPrayer() {
        prefs.forceAddPeshinPrayer()
        val updatedList = prefs.getSchedule()
        _uiState.update { it.copy(scheduleItems = updatedList) }
        updateRealtimeMetrics()
        triggerSystemVisualSync()
        viewModelScope.launch {
            _userMessage.emit("🕌 Peshin namozi kun tartibiga qayta tiklandi!")
        }
    }

    fun toggleLocationService() {
        val current = _uiState.value.isLocationServiceRunning
        val context = getApplication<Application>()
        if (current) {
            HabitLocationService.stopService(context)
            _uiState.update { it.copy(isLocationServiceRunning = false) }
            viewModelScope.launch { _userMessage.emit("GPS xizmati to'xtatildi") }
        } else {
            val status = PermissionUtils.checkPermissions(context)
            if (!status.hasFineLocation) {
                viewModelScope.launch { _userMessage.emit("GPS ruxsati berilmagan! Avval ruxsatni bering.") }
                return
            }
            HabitLocationService.startService(context)
            _uiState.update { it.copy(isLocationServiceRunning = true) }
            viewModelScope.launch { _userMessage.emit("GPS xizmati ishga tushirildi") }
        }
    }

    fun saveGeofenceSettings(
        rtmLatStr: String,
        rtmLngStr: String,
        maktabLatStr: String,
        maktabLngStr: String,
        radiusStr: String
    ) {
        val rLat = rtmLatStr.toDoubleOrNull()
        val rLng = rtmLngStr.toDoubleOrNull()
        val mLat = maktabLatStr.toDoubleOrNull()
        val mLng = maktabLngStr.toDoubleOrNull()
        val rad = radiusStr.toFloatOrNull()

        if (rLat == null || rLng == null || mLat == null || mLng == null || rad == null) {
            viewModelScope.launch { _userMessage.emit("Koordinatalar noto'g'ri kiritildi") }
            return
        }

        prefs.rtmLat = rLat
        prefs.rtmLng = rLng
        prefs.maktabLat = mLat
        prefs.maktabLng = mLng
        prefs.radiusMeters = rad

        _uiState.update {
            it.copy(
                rtmLat = rtmLatStr,
                rtmLng = rtmLngStr,
                maktabLat = maktabLatStr,
                maktabLng = maktabLngStr,
                radiusMeters = radiusStr
            )
        }
        viewModelScope.launch { _userMessage.emit("Koordinatalar saqlandi!") }
    }

    fun saveBlockedApps(packagesStr: String) {
        val cleaned = packagesStr.trim()
        prefs.blockedPackages = cleaned
        _uiState.update { it.copy(blockedPackages = cleaned) }
        viewModelScope.launch { _userMessage.emit("Bloklangan ilovalar ro'yxati saqlandi!") }
    }

    fun testTriggerAlarm() {
        val context = getApplication<Application>()
        val state = _uiState.value.habitState
        val title = if (state.title.isNotBlank()) state.title else "Sinov: Dars qilish"
        val category = if (state.category.isNotBlank()) state.category else "Ta'lim"
        val end = if (state.end.isNotBlank()) state.end else "16:00"
        val note = if (state.note.isNotBlank()) state.note else "Test rejimida to'liq ekranli signal"

        AlarmHelper.triggerAlarmNow(
            context = context,
            title = title,
            category = category,
            endTime = end,
            note = note
        )
        viewModelScope.launch { _userMessage.emit("To'liq ekranli signal ishga tushirildi!") }
    }

    fun scheduleTestAlarm(delaySeconds: Int = 10) {
        val context = getApplication<Application>()
        val msg = AlarmHelper.scheduleTestAlarm(context, delaySeconds)
        viewModelScope.launch { _userMessage.emit(msg) }
    }

    // --- AI Wallpaper ---
    fun toggleAiWallpaper(enabled: Boolean) {
        prefs.isAiWallpaperEnabled = enabled
        _uiState.update { it.copy(isAiWallpaperEnabled = enabled) }
        viewModelScope.launch {
            _userMessage.emit(if (enabled) "AI Fon Rasmi faollashtirildi!" else "AI Fon Rasmi o'chirildi")
        }
    }

    fun setWallpaperTarget(target: String) {
        prefs.wallpaperTarget = target
        _uiState.update { it.copy(wallpaperTarget = target) }
    }

    fun onCustomWallpaperSelected(uri: android.net.Uri) {
        val success = com.example.service.AiWallpaperManager.saveCustomWallpaperFromUri(getApplication(), uri)
        if (success) {
            _uiState.update {
                it.copy(
                    hasCustomWallpaper = true,
                    wallpaperStatusMessage = "📸 Rasm saqlandi! Gemini bo'sh kataklarni tahlil qiladi."
                )
            }
            viewModelScope.launch {
                _userMessage.emit("📸 Bosh ekran rasmi yuklandi!")
            }
        }
    }

    fun onClearCustomWallpaper() {
        com.example.service.AiWallpaperManager.clearCustomWallpaper(getApplication())
        _uiState.update {
            it.copy(
                hasCustomWallpaper = false,
                wallpaperStatusMessage = "Asl tizim foni tiklandi"
            )
        }
    }

    fun applyAiWallpaperNow() {
        viewModelScope.launch {
            _uiState.update { it.copy(isWallpaperUpdating = true, wallpaperStatusMessage = "Gemini AI bo'sh kataklarni va elementlarni tahlil qilmoqda...") }
            val result = com.example.service.AiWallpaperManager.updateWallpaperForCurrentTask(getApplication())
            if (result.isSuccess) {
                val msg = result.getOrNull() ?: "Fon rasmi muvaffaqiyatli o'rnatildi!"
                val expl = prefs.lastAiWallpaperExplanation
                _uiState.update {
                    it.copy(
                        isWallpaperUpdating = false,
                        wallpaperStatusMessage = "✅ $msg",
                        wallpaperExplanation = expl
                    )
                }
                _userMessage.emit("🎨 $msg")
            } else {
                val err = result.exceptionOrNull()?.message ?: "Noma'lum xatolik"
                _uiState.update {
                    it.copy(
                        isWallpaperUpdating = false,
                        wallpaperStatusMessage = "Xatolik: $err"
                    )
                }
                _userMessage.emit("Fon rasmi xatosi: $err")
            }
        }
    }

    // --- Weather ---
    fun refreshWeather() {
        viewModelScope.launch {
            _uiState.update { it.copy(isWeatherLoading = true) }
            val res = com.example.data.remote.WeatherClient.fetchWeather(
                lat = prefs.rtmLat,
                lng = prefs.rtmLng,
                locationName = "Farg'ona / Toshkent"
            )
            val info = res.getOrDefault(com.example.data.remote.WeatherInfo())
            _uiState.update {
                it.copy(weatherInfo = info, isWeatherLoading = false)
            }
        }
    }

    // --- Telegram Reporting ---
    fun saveTelegramSettings(token: String, chatId: String) {
        prefs.telegramBotToken = token
        prefs.telegramChatId = chatId
        _uiState.update {
            it.copy(telegramBotToken = token, telegramChatId = chatId)
        }
        viewModelScope.launch { _userMessage.emit("Telegram sozlamalari saqlandi!") }
    }

    fun sendTelegramReport() {
        viewModelScope.launch {
            val token = _uiState.value.telegramBotToken
            val chatId = _uiState.value.telegramChatId
            if (token.isBlank() || chatId.isBlank()) {
                _userMessage.emit("Iltimos, Telegram Bot Token va Chat ID ni kiriting!")
                return@launch
            }

            _uiState.update { it.copy(isTelegramSending = true, telegramStatusMessage = "Yuborilmoqda...") }
            val schedule = prefs.getSchedule()
            val doneCount = schedule.count { it.isDone }
            val totalCount = schedule.size.coerceAtLeast(1)
            val pct = ((doneCount.toFloat() / totalCount.toFloat()) * 100).toInt()

            val msg = buildString {
                append("📊 <b>Habit — Kunlik Hisobot</b>\n\n")
                append("📅 <b>Sana:</b> ${TaskTimeEngine.getFormattedCurrentDate()}\n")
                append("⏰ <b>Vaqt:</b> ${TaskTimeEngine.getFormattedCurrentTime()}\n")
                append("🔥 <b>Ketma-ketlik (Streak):</b> ${prefs.getStreak()} kun\n")
                append("⏱ <b>Vaqt jamg'armasi:</b> +${prefs.getTimeBankMinutes()} daqiqa\n")
                append("🎯 <b>Bajarilgan vazifalar:</b> $doneCount / $totalCount ($pct%)\n\n")
                append("📋 <b>Vazifalar holati:</b>\n")
                schedule.forEach {
                    val mark = if (it.isDone) "✅" else "⏳"
                    append("$mark ${it.start}–${it.end}: ${it.title}\n")
                }
                append("\n<i>Habit ilovasi orqali avtomatik yuborildi.</i>")
            }

            val res = com.example.data.remote.TelegramClient.sendReport(token, chatId, msg)
            if (res.isSuccess) {
                _uiState.update {
                    it.copy(
                        isTelegramSending = false,
                        telegramStatusMessage = "✅ Hisobot Telegramga yuborildi!"
                    )
                }
                _userMessage.emit("Hisobot Telegramga muvaffaqiyatli yuborildi!")
            } else {
                val err = res.exceptionOrNull()?.message ?: "Xatolik"
                _uiState.update {
                    it.copy(
                        isTelegramSending = false,
                        telegramStatusMessage = "Xatolik: $err"
                    )
                }
                _userMessage.emit(err)
            }
        }
    }

    // --- Alarm Sound & Volume Controls ---
    fun toggleAlarmMute(muted: Boolean) {
        prefs.isAlarmMuted = muted
        _uiState.update { it.copy(isAlarmMuted = muted) }
        viewModelScope.launch {
            _userMessage.emit(if (muted) "🔕 Eslatmalar ovozsiz (Mute) qilindi" else "🔔 Eslatma ovozi yoqildi")
        }
    }

    fun setAlarmVolume(volume: Float) {
        val clamped = volume.coerceIn(0f, 1f)
        prefs.alarmVolume = clamped
        _uiState.update { it.copy(alarmVolume = clamped) }
    }

    fun setAlarmSoundTone(tone: String) {
        prefs.alarmSoundTone = tone
        _uiState.update { it.copy(alarmSoundTone = tone) }
    }

    private var testAudioPlayer: android.media.MediaPlayer? = null

    fun playTestAlarmSound() {
        val tone = _uiState.value.alarmSoundTone
        val volume = _uiState.value.alarmVolume
        val isMuted = prefs.shouldMuteAlarm()
        if (isMuted) {
            val reason = if (prefs.isAtSchool || prefs.isSchoolMuted) {
                "🏫 Maktab hududidasiz! Darsga xalaqit bermasligi uchun barcha tovushlar avtomatik o'chirilgan (telefon tebranishda)."
            } else {
                "⚠️ Signal ovozsiz (Muted) holatda! Avval yuqoridagi karnay tugmasini bosing yoki sozlamalardan yoqing."
            }
            viewModelScope.launch { _userMessage.emit(reason) }
            return
        }

        try {
            testAudioPlayer?.stop()
            testAudioPlayer?.release()
            testAudioPlayer = null

            val uri = when (tone) {
                "NOTIFICATION" -> android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
                "RINGTONE" -> android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_RINGTONE)
                else -> android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_ALARM)
                    ?: android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_RINGTONE)
                    ?: android.media.RingtoneManager.getDefaultUri(android.media.RingtoneManager.TYPE_NOTIFICATION)
            }

            testAudioPlayer = android.media.MediaPlayer().apply {
                setDataSource(getApplication(), uri)
                setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_ALARM)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                setVolume(volume, volume)
                prepare()
                start()
            }

            viewModelScope.launch {
                _userMessage.emit("🔊 Eslatma ovozi sinab ko'rilmoqda: ${(volume * 100).toInt()}%")
                kotlinx.coroutines.delay(2500)
                testAudioPlayer?.stop()
                testAudioPlayer?.release()
                testAudioPlayer = null
            }
        } catch (e: Exception) {
            viewModelScope.launch { _userMessage.emit("Ovoz sinashda xatolik: ${e.message}") }
        }
    }

    // --- Vocab & Document Engine ---
    fun setDailyVocabGoal(goal: Int) {
        val validGoal = goal.coerceAtLeast(10)
        prefs.dailyVocabGoal = validGoal
        _uiState.update { it.copy(dailyVocabGoal = validGoal) }
        viewModelScope.launch {
            _userMessage.emit("🎯 Kunlik me'yor: $validGoal ta so'z (kamida 10 ta)")
        }
    }

    fun importVocabDocument(uri: android.net.Uri, fileName: String) {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            _uiState.update { it.copy(isVocabDocumentParsing = true, vocabDocumentStatus = "Hujjat o'rganilmoqda...") }
            val parsedCards = com.example.data.util.VocabDocumentParser.parseDocument(getApplication(), uri, fileName)
            if (parsedCards.isEmpty()) {
                _uiState.update { it.copy(isVocabDocumentParsing = false, vocabDocumentStatus = "So'zlar topilmadi") }
                _userMessage.emit("⚠️ Hujjatdan so'zlar topilmadi. So'zlar va darajalar (A1-C2) yozilgan fayl yuklang.")
                return@launch
            }

            val current = prefs.getVocabCards().toMutableList()
            val existingWords = current.map { it.word.lowercase() }.toSet()
            val newCards = parsedCards.filter { !existingWords.contains(it.word.lowercase()) }
            current.addAll(newCards)

            val sorted = current.sortedWith(
                compareBy<com.example.data.model.VocabCard> { com.example.data.util.VocabDocumentParser.getLevelWeight(it.level) }
                    .thenBy { it.word.lowercase() }
            )

            prefs.saveVocabCards(sorted)
            _uiState.update {
                it.copy(
                    vocabCards = sorted,
                    isVocabDocumentParsing = false,
                    vocabDocumentStatus = "${newCards.size} ta yangi so'z qo'shildi"
                )
            }
            _userMessage.emit("✅ '$fileName' hujjatidan ${newCards.size} ta so'z CEFR darajalari (A1-C2) bo'yicha saralandi!")
        }
    }

    fun importVocabText(rawText: String, docName: String = "Qo'lda kiritilgan") {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            _uiState.update { it.copy(isVocabDocumentParsing = true) }
            val parsedCards = com.example.data.util.VocabDocumentParser.parseRawText(rawText, sourceName = docName)
            if (parsedCards.isEmpty()) {
                _uiState.update { it.copy(isVocabDocumentParsing = false) }
                _userMessage.emit("⚠️ Kiritilgan matndan so'zlar topilmadi")
                return@launch
            }

            val current = prefs.getVocabCards().toMutableList()
            val existingWords = current.map { it.word.lowercase() }.toSet()
            val newCards = parsedCards.filter { !existingWords.contains(it.word.lowercase()) }
            current.addAll(newCards)

            val sorted = current.sortedWith(
                compareBy<com.example.data.model.VocabCard> { com.example.data.util.VocabDocumentParser.getLevelWeight(it.level) }
                    .thenBy { it.word.lowercase() }
            )

            prefs.saveVocabCards(sorted)
            _uiState.update { it.copy(vocabCards = sorted, isVocabDocumentParsing = false) }
            _userMessage.emit("✅ ${newCards.size} ta so'z tartiblangan holda saqlandi!")
        }
    }

    fun loadSampleCefrVocabulary() {
        val sample = com.example.data.util.VocabDocumentParser.getSampleCefrProgression()
        val current = prefs.getVocabCards().toMutableList()
        val existingWords = current.map { it.word.lowercase() }.toSet()
        val newCards = sample.filter { !existingWords.contains(it.word.lowercase()) }
        current.addAll(newCards)

        val sorted = current.sortedWith(
            compareBy<com.example.data.model.VocabCard> { com.example.data.util.VocabDocumentParser.getLevelWeight(it.level) }
                .thenBy { it.word.lowercase() }
        )

        prefs.saveVocabCards(sorted)
        _uiState.update { it.copy(vocabCards = sorted) }
        viewModelScope.launch {
            _userMessage.emit("📚 Oxford/CEFR (A1-B2) tayyor lug'atlar to'plami muvaffaqiyatli yuklandi!")
        }
    }

    fun markVocabMastered(id: String) {
        val updated = prefs.getVocabCards().map {
            if (it.id == id) it.copy(isMastered = true, boxLevel = 4, reviewCount = it.reviewCount + 1, lastReviewedEpochMs = System.currentTimeMillis()) else it
        }
        prefs.saveVocabCards(updated)
        prefs.incrementVocabLearnedToday()
        val todayCount = prefs.vocabLearnedTodayCount
        val goal = prefs.dailyVocabGoal

        _uiState.update {
            it.copy(
                vocabCards = updated,
                vocabLearnedTodayCount = todayCount
            )
        }

        viewModelScope.launch {
            val unlearnedCount = updated.count { !it.isMastered }
            if (unlearnedCount == 0 && updated.isNotEmpty()) {
                _userMessage.emit("🏆 Tabriklaymiz! Siz yuklangan hujjatdagi barcha so'zlarni to'liq yodlab bo'ldingiz! Yangi so'zlarni qo'shing.")
            } else if (todayCount >= goal) {
                _userMessage.emit("🎉 Barakalla! Bugungi kunlik me'yoringiz ($goal ta so'z) to'liq bajarildi!")
            } else {
                _userMessage.emit("👏 Yodlandi! Bugun $todayCount / $goal ta so'z o'zlashtirildi.")
            }
        }
    }

    fun resetVocabForReview(id: String) {
        val updated = prefs.getVocabCards().map {
            if (it.id == id) it.copy(isMastered = false, boxLevel = 1) else it
        }
        prefs.saveVocabCards(updated)
        _uiState.update { it.copy(vocabCards = updated) }
        viewModelScope.launch { _userMessage.emit("🔄 So'z qayta o'rganish ro'yxatiga o'tkazildi") }
    }

    // --- Vocab & Quiz ---
    fun addWordWithAi(word: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isVocabLoading = true) }
            val res = com.example.data.remote.GeminiClient.generateVocabExplanation(word)
            val aiResult = res.getOrNull()
            val newCard = if (aiResult != null) {
                com.example.data.model.VocabCard(
                    word = aiResult.word,
                    translation = aiResult.uzbekTranslation,
                    phonetic = aiResult.phonetic,
                    partOfSpeech = aiResult.partOfSpeech,
                    definition = aiResult.definition,
                    example = aiResult.exampleSentence,
                    mnemonic = aiResult.mnemonicTip,
                    boxLevel = 1
                )
            } else {
                com.example.data.model.VocabCard(
                    word = word,
                    translation = "Yangi so'z",
                    boxLevel = 1
                )
            }

            val current = prefs.getVocabCards().toMutableList()
            current.add(0, newCard)
            prefs.saveVocabCards(current)
            _uiState.update {
                it.copy(vocabCards = current, isVocabLoading = false)
            }
            _userMessage.emit("✨ Yangi so'z Gemini AI orqali qo'shildi!")
        }
    }

    fun deleteVocabCard(id: String) {
        val updated = prefs.getVocabCards().filter { it.id != id }
        prefs.saveVocabCards(updated)
        _uiState.update { it.copy(vocabCards = updated) }
        viewModelScope.launch { _userMessage.emit("So'z o'chirildi") }
    }

    fun updateVocabBoxLevel(id: String, newLevel: Int) {
        val updated = prefs.getVocabCards().map {
            if (it.id == id) it.copy(boxLevel = newLevel, reviewCount = it.reviewCount + 1, lastReviewedEpochMs = System.currentTimeMillis()) else it
        }
        prefs.saveVocabCards(updated)
        _uiState.update { it.copy(vocabCards = updated) }
        viewModelScope.launch {
            val label = when (newLevel) {
                4 -> "4-qutiga (Yodlandi) o'tkazildi! 🎉"
                3 -> "3-qutiga (Mustahkam) o'tkazildi! 👍"
                2 -> "2-qutiga o'tkazildi! 📚"
                else -> "1-qutiga qaytarildi (Takrorlash) 🔄"
            }
            _userMessage.emit(label)
        }
    }

    fun generateQuiz() {
        viewModelScope.launch {
            _uiState.update { it.copy(isQuizLoading = true, quizQuestions = emptyList()) }
            val cards = prefs.getVocabCards()
            if (cards.isEmpty()) {
                _uiState.update { it.copy(isQuizLoading = false) }
                return@launch
            }

            val sampleWords = cards.shuffled().take(5)
            val prompt = buildString {
                append("Generate an interactive 5-question multiple choice vocabulary quiz in English & Uzbek based on these study words:\n")
                sampleWords.forEach { append("- ${it.word}: ${it.translation} (${it.definition})\n") }
                append("""
                    Return ONLY a JSON array with exactly 5 objects matching this schema:
                    [
                      {
                        "question": "What is the meaning of ...?",
                        "options": ["Option A", "Option B", "Option C", "Option D"],
                        "correctIndex": 0,
                        "explanation": "Short explanation in Uzbek"
                      }
                    ]
                """.trimIndent())
            }

            val textRes = com.example.data.remote.GeminiClient.generateText(prompt)
            if (textRes.isSuccess) {
                try {
                    val raw = textRes.getOrThrow()
                    val sIdx = raw.indexOf('[')
                    val eIdx = raw.lastIndexOf(']')
                    if (sIdx != -1 && eIdx != -1 && eIdx > sIdx) {
                        val jsonArr = org.json.JSONArray(raw.substring(sIdx, eIdx + 1))
                        val qList = mutableListOf<com.example.data.model.QuizQuestion>()
                        for (i in 0 until jsonArr.length()) {
                            val obj = jsonArr.getJSONObject(i)
                            val qText = obj.getString("question")
                            val optsArr = obj.getJSONArray("options")
                            val opts = mutableListOf<String>()
                            for (j in 0 until optsArr.length()) opts.add(optsArr.getString(j))
                            val cIdx = obj.getInt("correctIndex")
                            val exp = obj.optString("explanation", "")
                            qList.add(com.example.data.model.QuizQuestion(qText, opts, cIdx, exp))
                        }
                        _uiState.update { it.copy(quizQuestions = qList, isQuizLoading = false) }
                        return@launch
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // Fallback questions if offline or JSON parsing issue
            val fallback = sampleWords.mapIndexed { idx, card ->
                val otherTranslations = cards.filter { it.id != card.id }.map { it.translation }.shuffled().take(3)
                val allOpts = (listOf(card.translation) + otherTranslations).shuffled()
                com.example.data.model.QuizQuestion(
                    question = "\"${card.word}\" so'zining to'g'ri ma'nosi qaysi?",
                    options = if (allOpts.size >= 4) allOpts else listOf(card.translation, "Boshqa ma'no", "Qat'iy reja", "Vaqt qadri"),
                    correctIndex = allOpts.indexOf(card.translation).coerceAtLeast(0),
                    explanation = "${card.word} — ${card.translation}"
                )
            }
            _uiState.update { it.copy(quizQuestions = fallback, isQuizLoading = false) }
        }
    }

    fun closeQuiz() {
        _uiState.update { it.copy(quizQuestions = emptyList(), isQuizLoading = false) }
    }

    // --- Journal ---
    fun saveDailyJournal(stars: Int, highlights: String, challenges: String, reflections: String) {
        val entry = com.example.data.model.JournalEntry(
            dateStr = TaskTimeEngine.getFormattedCurrentDate(),
            stars = stars,
            highlights = highlights,
            challenges = challenges,
            reflections = reflections,
            savedTimeMinutes = prefs.getTimeBankMinutes(),
            completedTasksCount = prefs.getSchedule().count { it.isDone }
        )
        val current = prefs.getJournalEntries().toMutableList()
        current.add(0, entry)
        prefs.saveJournalEntries(current)
        _uiState.update { it.copy(journalEntries = current) }
        viewModelScope.launch { _userMessage.emit("📓 Kundalik muvaffaqiyatli saqlandi!") }
    }

    fun replanWithAi() {
        replanScheduleWithAi()
    }

    fun setLiquidTheme(themeId: String) {
        prefs.saveSelectedTheme(themeId)
        _uiState.update { it.copy(selectedThemeId = themeId) }
        viewModelScope.launch {
            val style = com.example.ui.theme.LiquidGlassStyles.getById(themeId)
            _userMessage.emit("✨ «${style.displayName}» liquid glass uslubi faollashtirildi!")
        }
    }

    fun delayTaskWithReason(task: ScheduleItem, reason: String) {
        viewModelScope.launch {
            delaySchedule(15)
            _userMessage.emit("⏱ Vazifa 15 daqiqaga kechiktirildi (Sabab: $reason)")
        }
    }

    // --- Custom Locations & Geofence Engine ---
    fun captureCurrentGpsLocation(onCaptured: (Double, Double) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCapturingLocation = true) }
            try {
                val fusedClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(getApplication<Application>())
                fusedClient.lastLocation.addOnSuccessListener { loc ->
                    _uiState.update { it.copy(isCapturingLocation = false) }
                    if (loc != null) {
                        onCaptured(loc.latitude, loc.longitude)
                        viewModelScope.launch { _userMessage.emit("📍 Hozirgi joylashuv aniqlandi: ${String.format(Locale.US, "%.5f, %.5f", loc.latitude, loc.longitude)}") }
                    } else {
                        viewModelScope.launch { _userMessage.emit("⚠️ GPS signalini aniqlab bo'lmadi. GPS yoqilganligini tekshiring.") }
                    }
                }.addOnFailureListener { e ->
                    _uiState.update { it.copy(isCapturingLocation = false) }
                    viewModelScope.launch { _userMessage.emit("GPS xatosi: ${e.message}") }
                }
            } catch (e: SecurityException) {
                _uiState.update { it.copy(isCapturingLocation = false) }
                _userMessage.emit("GPS ruxsati berilmagan!")
            } catch (e: Exception) {
                _uiState.update { it.copy(isCapturingLocation = false) }
                _userMessage.emit("Joylashuvni olishda xatolik: ${e.message}")
            }
        }
    }

    fun addCustomLocation(
        name: String,
        latitude: Double,
        longitude: Double,
        radiusMeters: Float = 150f,
        actionType: String = "NOTIFY_AND_SET_HABIT",
        targetHabitTitle: String = ""
    ) {
        val newLoc = com.example.data.model.CustomLocation(
            name = name,
            lat = latitude,
            lng = longitude,
            radiusMeters = radiusMeters,
            actionType = actionType,
            targetHabitTitle = targetHabitTitle
        )
        prefs.addCustomLocation(newLoc)
        _uiState.update { it.copy(customLocations = prefs.getCustomLocations()) }
        viewModelScope.launch { _userMessage.emit("📍 «$name» yangi joylashuvi muvaffaqiyatli saqlandi!") }
    }

    fun deleteCustomLocation(id: String) {
        prefs.deleteCustomLocation(id)
        _uiState.update { it.copy(customLocations = prefs.getCustomLocations()) }
        viewModelScope.launch { _userMessage.emit("Joylashuv o'chirildi") }
    }

    fun toggleCustomLocation(id: String, enabled: Boolean) {
        val list = prefs.getCustomLocations().map {
            if (it.id == id) it.copy(isEnabled = enabled) else it
        }
        prefs.saveCustomLocations(list)
        _uiState.update { it.copy(customLocations = list) }
    }

    // --- App Blocker with Visual App Picker & Pause Toggle ---
    fun toggleBlockerPaused(paused: Boolean) {
        prefs.isBlockerPaused = paused
        _uiState.update { it.copy(isBlockerPaused = paused) }
        viewModelScope.launch {
            _userMessage.emit(
                if (paused) "⏸ Barcha cheklovlar vaqtincha to'xtatildi (Ilovalardan erkin foydalanishingiz mumkin)"
                else "▶️ Ilovalarni cheklash qayta yoqildi!"
            )
        }
    }

    fun setAppPickerOpen(open: Boolean) {
        _uiState.update { it.copy(isAppPickerOpen = open) }
        if (open && _uiState.value.installedApps.isEmpty()) {
            loadInstalledApps()
        }
    }

    fun loadInstalledApps() {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            _uiState.update { it.copy(isLoadingApps = true) }
            val pm = getApplication<Application>().packageManager
            val intent = android.content.Intent(android.content.Intent.ACTION_MAIN).apply {
                addCategory(android.content.Intent.CATEGORY_LAUNCHER)
            }
            val resolveInfos = pm.queryIntentActivities(intent, 0)
            val blockedSet = prefs.getBlockedPackageSet()

            val apps = resolveInfos.mapNotNull { ri ->
                val pName = ri.activityInfo.packageName
                if (pName == getApplication<Application>().packageName) return@mapNotNull null
                val label = ri.loadLabel(pm).toString()
                val isBlocked = blockedSet.contains(pName)
                com.example.data.model.InstalledAppInfo(
                    packageName = pName,
                    appName = label,
                    isBlocked = isBlocked
                )
            }.distinctBy { it.packageName }.sortedBy { it.appName.lowercase() }

            _uiState.update {
                it.copy(
                    installedApps = apps,
                    isLoadingApps = false
                )
            }
        }
    }

    fun toggleAppBlocked(packageName: String, blocked: Boolean) {
        val currentSet = prefs.getBlockedPackageSet().toMutableSet()
        if (blocked) {
            currentSet.add(packageName)
        } else {
            currentSet.remove(packageName)
        }
        val joined = currentSet.joinToString(",")
        prefs.blockedPackages = joined

        val updatedApps = _uiState.value.installedApps.map {
            if (it.packageName == packageName) it.copy(isBlocked = blocked) else it
        }
        _uiState.update {
            it.copy(
                blockedPackages = joined,
                installedApps = updatedApps
            )
        }
    }

    // --- Gemini User API Key ---
    fun saveUserGeminiApiKey(key: String) {
        val trimmed = key.trim()
        prefs.userGeminiApiKey = trimmed
        _uiState.update { it.copy(userGeminiApiKey = trimmed) }
        viewModelScope.launch {
            _userMessage.emit("✨ Gemini API kaliti saqlandi va faollashtirildi!")
        }
    }
}
