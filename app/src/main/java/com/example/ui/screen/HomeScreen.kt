package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskStatus
import com.example.data.model.TaskTimeEngine
import com.example.ui.HabitUiState
import com.example.ui.theme.HabitBg
import com.example.ui.theme.HabitBlue
import com.example.ui.theme.HabitBlueGlow
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardSoft
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitGoldGlow
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitLine
import com.example.ui.theme.HabitRose
import com.example.ui.theme.HabitSage
import com.example.ui.theme.HabitSageGlow
import com.example.ui.theme.HabitTlLine
import com.example.ui.theme.LocalLiquidTheme
import com.example.ui.theme.LiquidThemeSelectorBar
import com.example.ui.theme.liquidMeshBackground
import com.example.ui.theme.LiquidGlassStyle

@Composable
fun HomeScreen(
    state: HabitUiState,
    onSelectTheme: (String) -> Unit = {},
    onRefresh: () -> Unit,
    onToggleLocationService: () -> Unit,
    onOpenAccessibilitySettings: () -> Unit,
    onOpenPermissionsDialog: () -> Unit,
    onTestAlarm: () -> Unit,
    onScheduleTestAlarm: (Int) -> Unit = {},
    onSaveGeofence: (rtmLat: String, rtmLng: String, maktabLat: String, maktabLng: String, radius: String) -> Unit,
    onSaveBlockedPackages: (String) -> Unit,
    onActivateScheduleItem: (ScheduleItem) -> Unit = {},
    onAddScheduleItem: (title: String, category: String, start: String, end: String, note: String, blocking: Boolean) -> Unit = { _, _, _, _, _, _ -> },
    onDeleteScheduleItem: (String) -> Unit = {},
    onUpdateCurrentTask: (title: String, category: String, start: String, end: String, note: String, blocking: Boolean) -> Unit = { _, _, _, _, _, _ -> },
    onMarkTaskCompleted: (ScheduleItem) -> Unit = {},
    onDelaySchedule: (minutes: Int) -> Unit = {},
    onSelectTab: (tab: Int) -> Unit = {},
    onSelectDayOffset: (offset: Int) -> Unit = {},
    onRefreshWeather: () -> Unit = {},
    onToggleAiWallpaper: (Boolean) -> Unit = {},
    onSetWallpaperTarget: (String) -> Unit = {},
    onPickWallpaperImage: (android.net.Uri) -> Unit = {},
    onClearCustomWallpaper: () -> Unit = {},
    onApplyAiWallpaperNow: () -> Unit = {},
    onSaveTelegramSettings: (String, String) -> Unit = { _, _ -> },
    onSendTelegramReport: () -> Unit = {},
    onAddWordWithAi: (String) -> Unit = {},
    onDeleteVocabCard: (String) -> Unit = {},
    onUpdateVocabBoxLevel: (String, Int) -> Unit = { _, _ -> },
    onGenerateQuiz: () -> Unit = {},
    onCloseQuiz: () -> Unit = {},
    onSaveDailyJournal: (Int, String, String, String) -> Unit = { _, _, _, _ -> },
    onUnlockApp: () -> Unit = {},
    onSetSmartAddOpen: (Boolean) -> Unit = {},
    onSetQiblaOpen: (Boolean) -> Unit = {},
    onSetBreathOpen: (Boolean) -> Unit = {},
    onSetMotionOpen: (Boolean) -> Unit = {},
    onSyncSupabase: () -> Unit = {},
    onReplanWithAi: () -> Unit = {},
    onAddNewTask: (ScheduleItem) -> Unit = {},
    onDelayTaskWithReason: (ScheduleItem, String) -> Unit = { _, _ -> },
    onToggleBlockerPaused: (Boolean) -> Unit = {},
    onOpenAppPicker: () -> Unit = {},
    onCloseAppPicker: () -> Unit = {},
    onToggleAppBlocked: (String, Boolean) -> Unit = { _, _ -> },
    onCaptureCurrentLocation: (((Double, Double) -> Unit)) -> Unit = {},
    onAddCustomLocation: (String, Double, Double, Float, String, String) -> Unit = { _, _, _, _, _, _ -> },
    onDeleteCustomLocation: (String) -> Unit = {},
    onToggleCustomLocation: (String, Boolean) -> Unit = { _, _ -> },
    onSaveUserGeminiApiKey: (String) -> Unit = {},
    onToggleAlarmMute: (Boolean) -> Unit = {},
    onSetAlarmVolume: (Float) -> Unit = {},
    onSetAlarmSoundTone: (String) -> Unit = {},
    onPlayTestAlarmSound: () -> Unit = {},
    onSetDailyVocabGoal: (Int) -> Unit = {},
    onImportVocabDocument: (android.net.Uri, String) -> Unit = { _, _ -> },
    onImportVocabText: (String, String) -> Unit = { _, _ -> },
    onLoadSampleCefrVocab: () -> Unit = {},
    onMarkVocabMastered: (String) -> Unit = {},
    onResetVocabForReview: (String) -> Unit = {}
) {
    var showAddTaskDialog by remember { mutableStateOf(false) }
    var showDelayDialog by remember { mutableStateOf(false) }
    var showWhyDialog by remember { mutableStateOf(false) }
    var selectedItemForDetails by remember { mutableStateOf<ScheduleItem?>(null) }
    var showEditTaskDialog by remember { mutableStateOf(false) }
    var showCustomLocationDialog by remember { mutableStateOf(false) }

    // Form states for settings
    var rtmLatInput by remember(state.rtmLat) { mutableStateOf(state.rtmLat) }
    var rtmLngInput by remember(state.rtmLng) { mutableStateOf(state.rtmLng) }
    var maktabLatInput by remember(state.maktabLat) { mutableStateOf(state.maktabLat) }
    var maktabLngInput by remember(state.maktabLng) { mutableStateOf(state.maktabLng) }
    var radiusInput by remember(state.radiusMeters) { mutableStateOf(state.radiusMeters) }
    var blockedAppsInput by remember(state.blockedPackages) { mutableStateOf(state.blockedPackages) }
    var userApiKeyInput by remember(state.userGeminiApiKey) { mutableStateOf(state.userGeminiApiKey) }

    if (state.isLocked && state.isPinLockEnabled) {
        PinLockScreen(
            correctPin = state.pinCode,
            onUnlocked = onUnlockApp
        )
        return
    }

    val currentTheme = LocalLiquidTheme.current

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .liquidMeshBackground(currentTheme),
        bottomBar = {
            val navColors = NavigationBarItemDefaults.colors(
                selectedIconColor = currentTheme.primaryAccent,
                selectedTextColor = currentTheme.primaryAccent,
                unselectedIconColor = currentTheme.textSecondary,
                unselectedTextColor = currentTheme.textMuted,
                indicatorColor = currentTheme.primaryAccent.copy(alpha = 0.22f)
            )

            NavigationBar(
                containerColor = currentTheme.glassSurfaceElevated.copy(alpha = 0.90f),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .height(68.dp)
                    .border(BorderStroke(1.dp, currentTheme.glassBorderSubtle))
            ) {
                NavigationBarItem(
                    selected = state.selectedTab == 0,
                    onClick = { onSelectTab(0) },
                    icon = { Text("🏠", fontSize = 17.sp) },
                    label = { Text("Bugun", fontSize = 10.sp, fontWeight = if (state.selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = state.selectedTab == 1,
                    onClick = { onSelectTab(1) },
                    icon = { Text("📚", fontSize = 17.sp) },
                    label = { Text("Lug'at", fontSize = 10.sp, fontWeight = if (state.selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = state.selectedTab == 2,
                    onClick = { onSelectTab(2) },
                    icon = { Text("📓", fontSize = 17.sp) },
                    label = { Text("Kundalik", fontSize = 10.sp, fontWeight = if (state.selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = state.selectedTab == 3,
                    onClick = { onSelectTab(3) },
                    icon = { Text("📊", fontSize = 17.sp) },
                    label = { Text("Statistika", fontSize = 10.sp, fontWeight = if (state.selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                    colors = navColors
                )
                NavigationBarItem(
                    selected = state.selectedTab == 4,
                    onClick = { onSelectTab(4) },
                    icon = { Text("⚙️", fontSize = 17.sp) },
                    label = { Text("Tizim & AI", fontSize = 10.sp, fontWeight = if (state.selectedTab == 4) FontWeight.Bold else FontWeight.Normal) },
                    colors = navColors
                )
            }
        }
    ) { innerPadding ->
        when (state.selectedTab) {
            0 -> {
                // Bugun tab
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // 1. Clean Header (Date, Greeting, Day Type)
                    item {
                        HabitHeader(
                            dateString = state.currentDateString,
                            greeting = state.greetingText,
                            dayType = state.dayTypeLabel,
                            isLoading = state.isLoading,
                            isAlarmMuted = state.isAlarmMuted,
                            onToggleAlarmMute = { onToggleAlarmMute(!state.isAlarmMuted) },
                            onRefresh = onRefresh,
                            onOpenPermissions = onOpenPermissionsDialog
                        )
                    }

                    // 2. PRIMARY HERO TASK CARD - Placed prominently at the top so user immediately sees current task
                    item {
                        HabitHeroCard(
                            state = state,
                            onWhyClick = { showWhyDialog = true },
                            onDoneClick = {
                                val active = state.activeScheduleItem
                                if (active != null) {
                                    onMarkTaskCompleted(active)
                                } else if (state.habitState.title.isNotBlank()) {
                                    onMarkTaskCompleted(
                                        ScheduleItem(
                                            title = state.habitState.title,
                                            category = state.habitState.category,
                                            start = state.habitState.start,
                                            end = state.habitState.end,
                                            note = state.habitState.note,
                                            blocking = state.habitState.blocking
                                        )
                                    )
                                }
                            },
                            onDelayClick = { showDelayDialog = true },
                            onEditClick = { showEditTaskDialog = true }
                        )
                    }

                    // 3. Late Escalation Bar / Urgent Focus Card (if user is running late)
                    if (state.activeScheduleItem != null && state.isLate) {
                        item {
                            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                                FocusCard(
                                    currentTask = state.activeScheduleItem,
                                    progress = state.arcProgressPercent.toFloat() / 100f,
                                    isLate = state.isLate,
                                    onCompleteTask = { onMarkTaskCompleted(it) },
                                    onDelayTask = { task, reason ->
                                        onDelayTaskWithReason(task, reason)
                                    }
                                )
                            }
                        }
                    }

                    // 4. Marg'ilon Namoz Vaqtlari (Clean liquid glass bar)
                    item {
                        PrayerInfoBar(
                            hijriDate = state.hijriDate,
                            nextPrayer = state.nextPrayer,
                            prayers = state.prayers,
                            onOpenQibla = { onSetQiblaOpen(true) }
                        )
                    }

                    // 5. Streak & Level Bar
                    item {
                        HabitStreakBar(
                            streak = state.streak,
                            level = state.levelName,
                            timeBankMinutes = state.timeBankMinutes
                        )
                    }

                    // 6. Progress bar
                    item {
                        HabitProgressBar(progressPercent = state.todayProgressPercent)
                    }

                    // 7. Day tabs (Kecha / Bugun / Ertaga)
                    item {
                        HabitDayTabs(
                            selectedOffset = state.selectedDayOffset,
                            onSelectOffset = onSelectDayOffset
                        )
                    }

                    // Timeline Items
                    val items = state.scheduleItems
                    if (items.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, HabitLine)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Hozircha rejadagi vazifalar yo'q",
                                        color = HabitInkSoft,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { showAddTaskDialog = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                                    ) {
                                        Text("➕ Vazifa qo'shish", color = Color(0xFF241C08), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    } else {
                        var lastSegment = ""
                        items.forEach { item ->
                            val seg = item.getSegment()
                            if (seg != lastSegment) {
                                lastSegment = seg
                                item {
                                    HabitSegmentHeader(segmentName = seg)
                                }
                            }

                            val isCurrent = state.activeScheduleItem?.id == item.id ||
                                (state.habitState.title == item.title && state.habitState.start == item.start)

                            item(key = item.id) {
                                HabitTimelineRow(
                                    item = item,
                                    isCurrent = isCurrent,
                                    onClick = { selectedItemForDetails = item }
                                )
                            }
                        }
                    }

                    // Add Task Button
                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                            val theme = LocalLiquidTheme.current
                            OutlinedButton(
                                onClick = { showAddTaskDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .testTag("add_schedule_task_button"),
                                shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = theme.glassSurfaceElevated,
                                    contentColor = theme.textPrimary
                                )
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Bugunga yangi vazifa qo'shish", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = theme.textPrimary)
                            }
                        }
                    }

                    // Quick AI & Sync Action Buttons (Below tasks)
                    item {
                        val theme = LocalLiquidTheme.current
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onSetSmartAddOpen(true) },
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("✨ Aqlli", color = Color.Black, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = onReplanWithAi,
                                colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurface),
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("🤖 AI Reja", color = theme.textPrimary, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                            }

                            Button(
                                onClick = onSyncSupabase,
                                colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurface),
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("☁️ Bulut", color = theme.textPrimary, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    // Smart Helpers Bar (Qibla, Nafas, Harakat)
                    item {
                        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                            SmartToolsBar(
                                onOpenQibla = { onSetQiblaOpen(true) },
                                onOpenBreath = { onSetBreathOpen(true) },
                                onOpenMotion = { onSetMotionOpen(true) }
                            )
                        }
                    }

                    // Ob-havo Card (Clean summary at bottom)
                    item {
                        WeatherCard(
                            weather = state.weatherInfo,
                            isLoading = state.isWeatherLoading,
                            onRefresh = onRefreshWeather
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            1 -> {
                // Lug'at & CEFR Tizimi tab
                VocabScreen(
                    cards = state.vocabCards,
                    isLoading = state.isVocabLoading,
                    dailyGoal = state.dailyVocabGoal,
                    learnedToday = state.vocabLearnedTodayCount,
                    isDocumentParsing = state.isVocabDocumentParsing,
                    documentStatus = state.vocabDocumentStatus,
                    onSetDailyGoal = onSetDailyVocabGoal,
                    onImportDocument = onImportVocabDocument,
                    onImportText = onImportVocabText,
                    onLoadSampleCefr = onLoadSampleCefrVocab,
                    onMarkMastered = onMarkVocabMastered,
                    onResetForReview = onResetVocabForReview,
                    onAddWordWithAi = onAddWordWithAi,
                    onManualAddWord = {},
                    onDeleteWord = onDeleteVocabCard,
                    onUpdateBoxLevel = onUpdateVocabBoxLevel,
                    onGenerateQuiz = onGenerateQuiz,
                    quizQuestions = state.quizQuestions,
                    isQuizLoading = state.isQuizLoading,
                    onCloseQuiz = onCloseQuiz,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            2 -> {
                // Kechki Kundalik tab
                JournalScreen(
                    entries = state.journalEntries,
                    streak = state.streak,
                    timeBankMinutes = state.timeBankMinutes,
                    completedTasksCount = state.scheduleItems.count { it.isDone },
                    todayDateStr = state.currentDateString,
                    onSaveEntry = onSaveDailyJournal,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            3 -> {
                // Statistika tab
                StatistikaTab(
                    state = state,
                    innerPadding = innerPadding
                )
            }

            4 -> {
                // Tizim & AI tab
                TizimGpsTab(
                    state = state,
                    innerPadding = innerPadding,
                    onSelectTheme = onSelectTheme,
                    rtmLatInput = rtmLatInput,
                    onRtmLatChange = { rtmLatInput = it },
                    rtmLngInput = rtmLngInput,
                    onRtmLngChange = { rtmLngInput = it },
                    maktabLatInput = maktabLatInput,
                    onMaktabLatChange = { maktabLatInput = it },
                    maktabLngInput = maktabLngInput,
                    onMaktabLngChange = { maktabLngInput = it },
                    radiusInput = radiusInput,
                    onRadiusChange = { radiusInput = it },
                    blockedAppsInput = blockedAppsInput,
                    onBlockedAppsChange = { blockedAppsInput = it },
                    onSaveGeofence = onSaveGeofence,
                    onSaveBlockedPackages = onSaveBlockedPackages,
                    onToggleLocationService = onToggleLocationService,
                    onOpenAccessibilitySettings = onOpenAccessibilitySettings,
                    onOpenPermissionsDialog = onOpenPermissionsDialog,
                    onTestAlarm = onTestAlarm,
                    onScheduleTestAlarm = onScheduleTestAlarm,
                    onToggleAiWallpaper = onToggleAiWallpaper,
                    onSetWallpaperTarget = onSetWallpaperTarget,
                    onPickWallpaperImage = onPickWallpaperImage,
                    onClearCustomWallpaper = onClearCustomWallpaper,
                    onApplyAiWallpaperNow = onApplyAiWallpaperNow,
                    onSaveTelegramSettings = onSaveTelegramSettings,
                    onSendTelegramReport = onSendTelegramReport,
                    onToggleBlockerPaused = onToggleBlockerPaused,
                    onOpenAppPicker = onOpenAppPicker,
                    onToggleAppBlocked = onToggleAppBlocked,
                    onOpenAddCustomLocation = { showCustomLocationDialog = true },
                    onDeleteCustomLocation = onDeleteCustomLocation,
                    onToggleCustomLocation = onToggleCustomLocation,
                    onCaptureCurrentLocation = onCaptureCurrentLocation,
                    userApiKeyInput = userApiKeyInput,
                    onUserApiKeyChange = { userApiKeyInput = it },
                    onSaveUserGeminiApiKey = onSaveUserGeminiApiKey,
                    onToggleAlarmMute = onToggleAlarmMute,
                    onSetAlarmVolume = onSetAlarmVolume,
                    onSetAlarmSoundTone = onSetAlarmSoundTone,
                    onPlayTestAlarmSound = onPlayTestAlarmSound
                )
            }
        }
    }

    // Modal Dialogs
    if (state.isAppPickerOpen) {
        com.example.ui.dialog.AppPickerDialog(
            installedApps = state.installedApps,
            isLoading = state.isLoadingApps,
            isBlockerPaused = state.isBlockerPaused,
            onToggleBlockerPaused = onToggleBlockerPaused,
            onToggleAppBlocked = onToggleAppBlocked,
            onDismiss = onCloseAppPicker
        )
    }

    if (showCustomLocationDialog) {
        com.example.ui.dialog.CustomLocationDialog(
            isCapturingLocation = state.isCapturingLocation,
            onCaptureCurrentLocation = onCaptureCurrentLocation,
            onSaveLocation = { name, lat, lng, rad, act, habit ->
                onAddCustomLocation(name, lat, lng, rad, act, habit)
            },
            onDismiss = { showCustomLocationDialog = false }
        )
    }

    if (showWhyDialog) {
        val whyText = state.activeScheduleItem?.getWhyText() ?: "Kun tartibiga amal qilish — intizom va muvaffaqiyat garovi."
        AlertDialog(
            onDismissRequest = { showWhyDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = { Text("Nega bu vazifa muhim?", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif) },
            text = {
                Text(
                    text = whyText,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showWhyDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Tushunarli", color = Color(0xFF241C08), fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showDelayDialog) {
        AlertDialog(
            onDismissRequest = { showDelayDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = { Text("⏱ Rejani kechiktirish", fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif) },
            text = {
                Column {
                    Text(
                        "Necha daqiqa kechikdingiz? Bugungi qolgan barcha vazifalar shuncha vaqtga suriladi:",
                        fontSize = 13.sp,
                        color = HabitInkSoft
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(10, 15, 20).forEach { mins ->
                            Button(
                                onClick = {
                                    onDelaySchedule(mins)
                                    showDelayDialog = false
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = HabitCardSoft),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("+$mins", color = HabitInk, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(30, 45, 60).forEach { mins ->
                            Button(
                                onClick = {
                                    onDelaySchedule(mins)
                                    showDelayDialog = false
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = HabitCardSoft),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("+$mins", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDelayDialog = false }) {
                    Text("Yopish", color = HabitInkSoft)
                }
            }
        )
    }

    selectedItemForDetails?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedItemForDetails = null },
            containerColor = HabitCardBg,
            titleContentColor = HabitInk,
            textContentColor = HabitInkSoft,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.getCategoryIcon(), fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Vaqti: ${item.start} – ${item.end}", color = HabitGold, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text("Toifasi: ${item.getCategoryLabel()}", color = HabitInk, fontSize = 13.sp)
                    if (item.note.isNotBlank()) {
                        Text("Eslatma: ${item.note}", color = HabitInkSoft, fontSize = 13.sp)
                    }
                    Text(
                        if (item.blocking) "🛡️ Bloklash yoqilgan (chalg'ituvchi ilovalar yopiladi)" else "🔓 Bloklash yo'q",
                        color = if (item.blocking) HabitRose else HabitSage,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onActivateScheduleItem(item)
                        selectedItemForDetails = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Hozirgi qilib tanlash", color = Color(0xFF241C08), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = {
                            onMarkTaskCompleted(item)
                            selectedItemForDetails = null
                        }
                    ) {
                        Text("✅ Bajarildi", color = HabitSage)
                    }
                    TextButton(
                        onClick = {
                            onDeleteScheduleItem(item.id)
                            selectedItemForDetails = null
                        }
                    ) {
                        Text("O'chirish", color = HabitRose)
                    }
                }
            }
        )
    }

    if (showAddTaskDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newCategory by remember { mutableStateOf("english") }
        var newStart by remember { mutableStateOf("08:00") }
        var newEnd by remember { mutableStateOf("09:00") }
        var newNote by remember { mutableStateOf("") }
        var newBlocking by remember { mutableStateOf(true) }

        AlertDialog(
            onDismissRequest = { showAddTaskDialog = false },
            containerColor = HabitCardBg,
            title = { Text("➕ Yangi vazifa qo'shish", color = HabitInk, fontFamily = FontFamily.Serif, fontSize = 18.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Vazifa nomi") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedFieldColors()
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newStart,
                            onValueChange = { newStart = it },
                            label = { Text("Boshlanish (HH:mm)") },
                            modifier = Modifier.weight(1f),
                            colors = outlinedFieldColors()
                        )
                        OutlinedTextField(
                            value = newEnd,
                            onValueChange = { newEnd = it },
                            label = { Text("Tugash (HH:mm)") },
                            modifier = Modifier.weight(1f),
                            colors = outlinedFieldColors()
                        )
                    }
                    OutlinedTextField(
                        value = newNote,
                        onValueChange = { newNote = it },
                        label = { Text("Eslatma (ixtiyoriy)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedFieldColors()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ilovalarni bloklash", color = HabitInk, fontSize = 13.sp)
                        Switch(
                            checked = newBlocking,
                            onCheckedChange = { newBlocking = it },
                            colors = switchColors()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            onAddScheduleItem(newTitle, newCategory, newStart, newEnd, newNote, newBlocking)
                            showAddTaskDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Qo'shish", color = Color(0xFF241C08), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTaskDialog = false }) {
                    Text("Bekor qilish", color = HabitInkSoft)
                }
            }
        )
    }

    if (showEditTaskDialog) {
        var editTitle by remember(state.habitState.title) { mutableStateOf(state.habitState.title) }
        var editCategory by remember(state.habitState.category) { mutableStateOf(state.habitState.category) }
        var editStart by remember(state.habitState.start) { mutableStateOf(state.habitState.start) }
        var editEnd by remember(state.habitState.end) { mutableStateOf(state.habitState.end) }
        var editNote by remember(state.habitState.note) { mutableStateOf(state.habitState.note) }
        var editBlocking by remember(state.habitState.blocking) { mutableStateOf(state.habitState.blocking) }

        AlertDialog(
            onDismissRequest = { showEditTaskDialog = false },
            containerColor = HabitCardBg,
            title = { Text("✏️ Hozirgi vazifani tahrirlash", color = HabitInk, fontFamily = FontFamily.Serif, fontSize = 18.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = editTitle,
                        onValueChange = { editTitle = it },
                        label = { Text("Vazifa nomi") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedFieldColors()
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = editStart,
                            onValueChange = { editStart = it },
                            label = { Text("Boshlanish (HH:mm)") },
                            modifier = Modifier.weight(1f),
                            colors = outlinedFieldColors()
                        )
                        OutlinedTextField(
                            value = editEnd,
                            onValueChange = { editEnd = it },
                            label = { Text("Tugash (HH:mm)") },
                            modifier = Modifier.weight(1f),
                            colors = outlinedFieldColors()
                        )
                    }
                    OutlinedTextField(
                        value = editNote,
                        onValueChange = { editNote = it },
                        label = { Text("Eslatma") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedFieldColors()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ilovalarni bloklash", color = HabitInk, fontSize = 13.sp)
                        Switch(
                            checked = editBlocking,
                            onCheckedChange = { editBlocking = it },
                            colors = switchColors()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateCurrentTask(editTitle, editCategory, editStart, editEnd, editNote, editBlocking)
                        showEditTaskDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Saqlash", color = Color(0xFF241C08), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditTaskDialog = false }) {
                    Text("Bekor qilish", color = HabitInkSoft)
                }
            }
        )
    }

    if (state.isSmartAddOpen) {
        SmartAddDialog(
            onDismiss = { onSetSmartAddOpen(false) },
            onTaskAdded = onAddNewTask
        )
    }

    if (state.isQiblaOpen) {
        QiblaDialog(
            onDismiss = { onSetQiblaOpen(false) }
        )
    }

    if (state.isBreathOpen) {
        BreathDialog(
            onDismiss = { onSetBreathOpen(false) }
        )
    }

    if (state.isMotionOpen) {
        MotionDialog(
            currentLocation = state.lastDetectedLocation ?: if (state.habitState.lastArrivalPlace.isNotBlank()) state.habitState.lastArrivalPlace else "Toshkent / Marg'ilon (GPS faol)",
            onDismiss = { onSetMotionOpen(false) }
        )
    }
}

// -------------------------------------------------------------
// COMPOSABLE COMPONENTS (MATCHING THE WEBSITE'S EXACT DESIGN)
// -------------------------------------------------------------

@Composable
fun HabitHeader(
    dateString: String,
    greeting: String,
    dayType: String,
    isLoading: Boolean,
    isAlarmMuted: Boolean = false,
    onToggleAlarmMute: () -> Unit = {},
    onRefresh: () -> Unit,
    onOpenPermissions: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = dateString,
                color = theme.textSecondary,
                fontSize = 12.5.sp
            )
            Text(
                text = greeting,
                color = theme.textPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = dayType,
                color = theme.primaryAccent,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Master Alarm Speaker Mute/Unmute Toggle Button (Top of Home screen)
            Surface(
                onClick = onToggleAlarmMute,
                modifier = Modifier
                    .size(38.dp)
                    .testTag("alarm_mute_header_button"),
                shape = CircleShape,
                color = if (isAlarmMuted) Color(0xFFEF5350).copy(alpha = 0.18f) else theme.primaryAccent.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, if (isAlarmMuted) Color(0xFFEF5350) else theme.primaryAccent.copy(alpha = 0.45f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isAlarmMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = if (isAlarmMuted) "Signal ovozini yoqish" else "Signal ovozini o'chirish (Mute)",
                        tint = if (isAlarmMuted) Color(0xFFEF5350) else theme.primaryAccent,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }

            // Refresh Button
            Surface(
                onClick = onRefresh,
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = theme.glassSurfaceElevated,
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = theme.primaryAccent, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.Refresh, contentDescription = "Sinxronlash", tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
                    }
                }
            }

            // Permissions Button
            Surface(
                onClick = onOpenPermissions,
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = theme.glassSurfaceElevated,
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Security, contentDescription = "Ruxsatlar", tint = theme.textSecondary, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun HabitStreakBar(
    streak: Int,
    level: String,
    timeBankMinutes: Int
) {
    val theme = LocalLiquidTheme.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, theme.glassBorderSubtle)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🔥", fontSize = 22.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$streak ",
                        color = theme.primaryAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "kunlik ketma-ketlik · $level",
                        color = theme.textSecondary,
                        fontSize = 12.5.sp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Jamg'arma: $timeBankMinutes daq",
                color = theme.accentTertiary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun HabitArcTrack(
    arcProgress: Float,
    currentTime: String
) {
    val theme = LocalLiquidTheme.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.verticalGradient(listOf(theme.glassSurfaceElevated, theme.glassSurface)))
                .border(BorderStroke(1.dp, theme.glassBorderSubtle), RoundedCornerShape(16.dp))
        ) {
            // Filled portion with dynamic liquid glow
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = arcProgress.coerceIn(0f, 1f))
                    .height(46.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                theme.primaryAccent.copy(alpha = 0.40f),
                                theme.accentTertiary.copy(alpha = 0.25f)
                            )
                        )
                    )
            )

            // Current Time Marker Line
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = arcProgress.coerceIn(0.02f, 0.98f))
                    .height(46.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(3.dp)
                        .height(46.dp)
                        .background(theme.primaryAccent)
                )
            }

            // Mosque prayer markers (04:00, 13:00, 17:00, 18:40, 20:00)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🕌", fontSize = 12.sp)
                Text("🕌", fontSize = 12.sp)
                Text("🕌", fontSize = 12.sp)
                Text("🕌", fontSize = 12.sp)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("04:00", fontSize = 10.5.sp, color = HabitInkSoft)
            Text("10:00", fontSize = 10.5.sp, color = HabitInkSoft)
            Text("16:00", fontSize = 10.5.sp, color = HabitInkSoft)
            Text("22:00", fontSize = 10.5.sp, color = HabitInkSoft)
        }
    }
}

@Composable
fun HabitHeroCard(
    state: HabitUiState,
    onWhyClick: () -> Unit,
    onDoneClick: () -> Unit,
    onDelayClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    val habit = state.habitState
    val taskInfo = state.realtimeTaskInfo

    val title = if (habit.title.isNotBlank()) habit.title else "Hozircha rejalashtirilgan vazifa yo'q"
    val time = if (habit.start.isNotBlank() && habit.end.isNotBlank()) "${habit.start}–${habit.end}" else "--:--"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .testTag("hero_task_card"),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        shape = RoundedCornerShape(24.dp),
        border = if (taskInfo.status == TaskStatus.ACTIVE) BorderStroke(1.5.dp, theme.primaryAccent) else BorderStroke(1.dp, theme.glassBorderSubtle)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            theme.glassSurfaceElevated,
                            theme.bgTop.copy(alpha = 0.85f)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            // Top Row: Status badge and "Nega muhim?"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(if (taskInfo.status == TaskStatus.ACTIVE) theme.primaryAccent else theme.textSecondary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = taskInfo.statusLabel,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                }

                Surface(
                    onClick = onWhyClick,
                    shape = RoundedCornerShape(12.dp),
                    color = theme.glassSurface,
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Text(
                        text = "Nega muhim?",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.5.sp,
                        color = theme.textPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Task Title
            Text(
                text = title,
                fontSize = 22.sp,
                lineHeight = 29.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = theme.textPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Time and Countdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = time,
                    fontSize = 14.sp,
                    color = theme.textSecondary
                )

                Text(
                    text = taskInfo.timeRemainingText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (taskInfo.status == TaskStatus.ACTIVE) theme.primaryAccent else theme.textSecondary
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onDoneClick,
                    modifier = Modifier
                        .weight(1.6f)
                        .height(50.dp)
                        .testTag("hero_done_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text(
                        text = "✅ Bajardim",
                        color = Color.Black,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onDelayClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("hero_delay_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurface),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Text(
                        text = "⏱ Kechikdim",
                        color = theme.textPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .size(50.dp)
                        .background(theme.glassSurface, RoundedCornerShape(16.dp))
                        .border(1.dp, theme.glassBorderSubtle, RoundedCornerShape(16.dp))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Tahrirlash", tint = theme.textPrimary, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun HabitProgressBar(progressPercent: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Bugungi progress", fontSize = 12.sp, color = HabitInkSoft)
            Text("$progressPercent%", fontSize = 12.sp, color = HabitGold, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { (progressPercent / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = HabitGold,
            trackColor = HabitCardBg,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun HabitDayTabs(
    selectedOffset: Int,
    onSelectOffset: (Int) -> Unit
) {
    val labels = listOf(
        -3 to "3 kun oldin",
        -2 to "Kecha o'tgan",
        -1 to "Kecha",
        0 to "Bugun",
        1 to "Ertaga",
        2 to "Indinga",
        3 to "3 kundan so'ng"
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(labels) { (offset, label) ->
            val isSelected = offset == selectedOffset
            Surface(
                onClick = { onSelectOffset(offset) },
                shape = RoundedCornerShape(999.dp),
                color = if (isSelected) HabitGold else Color.Transparent,
                border = BorderStroke(1.dp, if (isSelected) HabitGold else HabitLine)
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                    fontSize = 12.5.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color(0xFF241C08) else HabitInkSoft
                )
            }
        }
    }
}

@Composable
fun HabitSegmentHeader(segmentName: String) {
    val icon = when (segmentName) {
        "Tong" -> "🌅"
        "Kunduz" -> "☀️"
        "Kech" -> "🌇"
        "Kechqurun" -> "🌆"
        "Tun" -> "🌙"
        else -> "📌"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = segmentName,
            color = HabitInkSoft,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.width(12.dp))
        HorizontalDivider(modifier = Modifier.weight(1f), color = HabitLine, thickness = 1.dp)
    }
}

@Composable
fun HabitTimelineRow(
    item: ScheduleItem,
    isCurrent: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 3.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.Top
    ) {
        // Left Column: Category Circle Icon and Vertical Connector Line
        val theme = LocalLiquidTheme.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            item.isDone -> Color(0xFF2E7D32).copy(alpha = 0.85f)
                            isCurrent -> theme.primaryAccent.copy(alpha = 0.25f)
                            else -> theme.glassSurface
                        }
                    )
                    .border(
                        1.5.dp,
                        when {
                            item.isDone -> Color(0xFF4CAF50)
                            isCurrent -> theme.primaryAccent
                            else -> theme.glassBorderSubtleColor
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (item.isDone) "✓" else item.getCategoryIcon(),
                    fontSize = if (item.isDone) 14.sp else 13.sp,
                    color = if (item.isDone) Color.White else theme.textPrimary
                )
            }

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .background(theme.glassBorderSubtleColor)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Right Card
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isCurrent) theme.glassSurfaceElevated else theme.glassSurface
            ),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(
                1.dp,
                if (isCurrent) theme.primaryAccent else theme.glassBorderSubtleColor
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.start}–${item.end} · ${item.getCategoryLabel()}",
                        fontSize = 11.5.sp,
                        color = theme.textSecondary
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (isCurrent) {
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = theme.primaryAccent.copy(alpha = 0.25f)
                            ) {
                                Text("Ayni vaqtda", modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp), fontSize = 10.sp, color = theme.primaryAccent, fontWeight = FontWeight.Bold)
                            }
                        }
                        if (item.blocking) {
                            Text("🛡️", fontSize = 11.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    color = theme.textPrimary,
                    lineHeight = 20.sp
                )

                if (item.note.isNotBlank()) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = item.note,
                        fontSize = 12.sp,
                        color = theme.textSecondary
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// STATISTIKA TAB
// -------------------------------------------------------------
@Composable
fun StatistikaTab(
    state: HabitUiState,
    innerPadding: PaddingValues
) {
    val theme = LocalLiquidTheme.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "📊 Statistika va intizom",
                color = theme.textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "So'nggi 7 kunlik ko'rsatkichlaringiz va o'sish dinamikasi",
                color = theme.textSecondary,
                fontSize = 13.sp
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("🔥 Streak ko'rsatkichi", color = theme.primaryAccent, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                    Spacer(modifier = Modifier.height(10.dp))
                    StatRow(label = "Ketma-ket intizomli kunlar", value = "${state.streak} kun")
                    StatRow(label = "Hozirgi unvoningiz", value = state.levelName)
                    StatRow(label = "Vaqt jamg'armasi", value = "${state.timeBankMinutes} daqiqa")
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("🕌 Namoz va ibodat vaqtlari", color = theme.primaryAccent, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                    Spacer(modifier = Modifier.height(10.dp))
                    StatRow(label = "Bugun o'qilgan namozlar", value = "5 / 5")
                    StatRow(label = "Vaqtida bajarish ko'rsatkichi", value = "100%")
                    StatRow(label = "Haftalik barqarorlik", value = "94%")
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("🏅 Erishilgan nishonlar", color = theme.primaryAccent, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BadgeItem("✓ 3 kunlik streak", true)
                        BadgeItem("✓ 7 kunlik streak", state.streak >= 7)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BadgeItem("✓ Bomdod sobitqadami", true)
                        BadgeItem("🔒 30 kunlik usta", state.streak >= 30)
                    }
                }
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    val theme = LocalLiquidTheme.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = theme.textSecondary, fontSize = 13.sp)
        Text(value, color = theme.textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
    HorizontalDivider(color = theme.glassBorderSubtleColor, thickness = 0.5.dp)
}

@Composable
fun BadgeItem(title: String, earned: Boolean) {
    val theme = LocalLiquidTheme.current
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = if (earned) theme.primaryAccent.copy(alpha = 0.2f) else theme.glassSurface,
        border = BorderStroke(1.dp, if (earned) theme.primaryAccent else theme.glassBorderSubtleColor)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 11.5.sp,
            color = if (earned) theme.primaryAccent else theme.textSecondary
        )
    }
}

// -------------------------------------------------------------
// TIZIM & GPS TAB (ORGANIZED SETTINGS, AUDIO, BLOCKING, GEOFENCE)
// -------------------------------------------------------------
@Composable
fun TizimGpsTab(
    state: HabitUiState,
    innerPadding: PaddingValues,
    onSelectTheme: (String) -> Unit = {},
    rtmLatInput: String,
    onRtmLatChange: (String) -> Unit,
    rtmLngInput: String,
    onRtmLngChange: (String) -> Unit,
    maktabLatInput: String,
    onMaktabLatChange: (String) -> Unit,
    maktabLngInput: String,
    onMaktabLngChange: (String) -> Unit,
    radiusInput: String,
    onRadiusChange: (String) -> Unit,
    blockedAppsInput: String,
    onBlockedAppsChange: (String) -> Unit,
    onSaveGeofence: (rtmLat: String, rtmLng: String, maktabLat: String, maktabLng: String, radius: String) -> Unit,
    onSaveBlockedPackages: (String) -> Unit,
    onToggleLocationService: () -> Unit,
    onOpenAccessibilitySettings: () -> Unit,
    onOpenPermissionsDialog: () -> Unit,
    onTestAlarm: () -> Unit,
    onScheduleTestAlarm: (Int) -> Unit,
    onToggleAiWallpaper: (Boolean) -> Unit = {},
    onSetWallpaperTarget: (String) -> Unit = {},
    onPickWallpaperImage: (android.net.Uri) -> Unit = {},
    onClearCustomWallpaper: () -> Unit = {},
    onApplyAiWallpaperNow: () -> Unit = {},
    onSaveTelegramSettings: (String, String) -> Unit = { _, _ -> },
    onSendTelegramReport: () -> Unit = {},
    onToggleBlockerPaused: (Boolean) -> Unit = {},
    onOpenAppPicker: () -> Unit = {},
    onToggleAppBlocked: (String, Boolean) -> Unit = { _, _ -> },
    onOpenAddCustomLocation: () -> Unit = {},
    onDeleteCustomLocation: (String) -> Unit = {},
    onToggleCustomLocation: (String, Boolean) -> Unit = { _, _ -> },
    onCaptureCurrentLocation: (((Double, Double) -> Unit)) -> Unit = {},
    userApiKeyInput: String = "",
    onUserApiKeyChange: (String) -> Unit = {},
    onSaveUserGeminiApiKey: (String) -> Unit = {},
    onToggleAlarmMute: (Boolean) -> Unit = {},
    onSetAlarmVolume: (Float) -> Unit = {},
    onSetAlarmSoundTone: (String) -> Unit = {},
    onPlayTestAlarmSound: () -> Unit = {}
) {
    val theme = LocalLiquidTheme.current
    var selectedCategory by remember { mutableStateOf("ALL") }

    val categories = listOf(
        "ALL" to "📌 Barchasi",
        "AUDIO" to "🔊 Ovoz & Eslatmalar",
        "BLOCKER" to "🛡️ Cheklovlar",
        "GPS" to "📍 Joylashuv",
        "DESIGN" to "🎨 Dizayn & Fon",
        "AI" to "🤖 AI & Bot"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Section Title
        item {
            Text(
                text = "⚙️ Sozlamalar va Tizim",
                color = theme.textPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = "Ovoz, ilovalar nazorati, GPS koordinatalari va shaxsiy uslub",
                color = theme.textSecondary,
                fontSize = 12.5.sp
            )
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { (catKey, catLabel) ->
                    val isSelected = selectedCategory == catKey
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) theme.primaryAccent else theme.glassSurface)
                            .border(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(14.dp))
                            .clickable { selectedCategory = catKey }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = catLabel,
                            color = if (isSelected) Color.Black else theme.textPrimary,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // =========================================================================
        // CATEGORY 1: 🔊 OVOZ VA TO'LIQ EKRAN BILDIRISHNOMASI (AUDIO & ALARM)
        // =========================================================================
        if (selectedCategory == "ALL" || selectedCategory == "AUDIO") {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.2.dp, theme.primaryAccent.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "🔊 Ovoz va Eslatmalar",
                                    color = theme.textPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = "Vazifa vaqti kelganda eslatadigan to'liq ekranli signal ovozi",
                                    color = theme.textSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // 1. Master Mute Toggle Switch
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (state.isAlarmMuted) Color(0xFFEF5350).copy(alpha = 0.12f) else theme.primaryAccent.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, if (state.isAlarmMuted) Color(0xFFEF5350).copy(alpha = 0.35f) else theme.primaryAccent.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Icon(
                                        imageVector = if (state.isAlarmMuted) Icons.AutoMirrored.Filled.VolumeOff else Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = null,
                                        tint = if (state.isAlarmMuted) Color(0xFFEF5350) else theme.primaryAccent,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = if (state.isAlarmMuted) "Signal ovozsiz (Muted)" else "Signal ovozi yoqilgan",
                                            color = theme.textPrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.5.sp
                                        )
                                        Text(
                                            text = if (state.isAlarmMuted) "To'liq ekran chiqadi, ammo ovoz chiqmaydi" else "Bosh sahifadagi karnay tugmasidan ham boshqariladi",
                                            color = theme.textSecondary,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                                Switch(
                                    checked = !state.isAlarmMuted,
                                    onCheckedChange = { isUnmuted -> onToggleAlarmMute(!isUnmuted) },
                                    colors = switchColors()
                                )
                            }
                        }

                        // 2. Alarm Volume Slider
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeDown,
                                        contentDescription = null,
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Signal balandligi:",
                                        color = theme.textPrimary,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp
                                    )
                                }
                                Text(
                                    text = "${(state.alarmVolume * 100).toInt()}%",
                                    color = theme.primaryAccent,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            Slider(
                                value = state.alarmVolume,
                                onValueChange = onSetAlarmVolume,
                                valueRange = 0.05f..1.0f,
                                enabled = !state.isAlarmMuted,
                                colors = SliderDefaults.colors(
                                    thumbColor = theme.primaryAccent,
                                    activeTrackColor = theme.primaryAccent,
                                    inactiveTrackColor = theme.primaryAccent.copy(alpha = 0.2f)
                                )
                            )
                        }

                        // 3. Sound Tone Selection (Standard, Ringtone, Notification)
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Signal ohangini tanlash:",
                                color = theme.textPrimary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val tones = listOf(
                                    "ALARM" to "🔔 Standart",
                                    "RINGTONE" to "🎵 Qo'ng'iroq",
                                    "NOTIFICATION" to "⚡ Bildirishnoma"
                                )
                                tones.forEach { (toneKey, toneLabel) ->
                                    val isSelected = state.alarmSoundTone.equals(toneKey, ignoreCase = true)
                                    Surface(
                                        onClick = { onSetAlarmSoundTone(toneKey) },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) theme.primaryAccent.copy(alpha = 0.2f) else theme.glassSurface,
                                        border = BorderStroke(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(vertical = 10.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = toneLabel,
                                                color = if (isSelected) theme.primaryAccent else theme.textSecondary,
                                                fontSize = 11.5.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // 4. Test Buttons (Play sound & Trigger full-screen activity)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onPlayTestAlarmSound,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ovozni sinash", color = theme.textPrimary, fontSize = 12.sp)
                            }

                            Button(
                                onClick = onTestAlarm,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                            ) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Black, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ekranni sinash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // =========================================================================
        // CATEGORY 2: 🛡️ CHEKLOVLAR (APP BLOCKER & ACCESSIBILITY)
        // =========================================================================
        if (selectedCategory == "ALL" || selectedCategory == "BLOCKER") {
            item {
                val blockedList = state.blockedPackages.split(",").filter { it.isNotBlank() }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("🛡️ Ilovalarni cheklash (App Blocker)", color = theme.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                                Text(
                                    text = if (state.isBlockerPaused) "Hozir to'xtatilgan (Pauza)" else "${blockedList.size} ta ilova cheklanmoqda",
                                    color = if (state.isBlockerPaused) Color(0xFFEF5350) else theme.primaryAccent,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Master Pause / Resume Quick Toggle
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (state.isBlockerPaused) Color(0xFFEF5350).copy(alpha = 0.12f) else theme.primaryAccent.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, if (state.isBlockerPaused) Color(0xFFEF5350).copy(alpha = 0.35f) else theme.primaryAccent.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (state.isBlockerPaused) "Barcha bloklashlar to'xtatilgan" else "Cheklov tizimi faol",
                                        color = theme.textPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = if (state.isBlockerPaused) "Ilovalar o'chirilmagan, shunchaki hozir bloklanmaydi" else "Bloklanadigan vazifa vaqtida ilovalar yopiladi",
                                        color = theme.textSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                                Switch(
                                    checked = !state.isBlockerPaused,
                                    onCheckedChange = { active -> onToggleBlockerPaused(!active) },
                                    colors = switchColors()
                                )
                            }
                        }

                        // Open App Picker Button
                        Button(
                            onClick = onOpenAppPicker,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "📱 Ilovalarni tanlash (${blockedList.size} ta tanlangan)",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        // Currently blocked apps list preview
                        if (blockedList.isNotEmpty()) {
                            Text(
                                text = "Tanlangan ilovalar:",
                                color = theme.textSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                blockedList.forEach { pkg ->
                                    val displayName = pkg.substringAfterLast(".").replaceFirstChar { it.uppercase() }
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = theme.glassSurface,
                                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 12.dp, vertical = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(displayName, color = theme.textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                                Text(pkg, color = theme.textMuted, fontSize = 10.5.sp)
                                            }
                                            IconButton(
                                                onClick = { onToggleAppBlocked(pkg, false) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.Close, contentDescription = "O'chirish", tint = theme.textSecondary, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Accessibility Service Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("🛡️ Ilovalarni bloklash (Accessibility)", color = theme.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                                Text(
                                    if (state.isAccessibilityConnected) "Ulangan — Chalg'ituvchi ilovalar yopiladi" else "Ulanmagan — Ruxsat berilishi kerak",
                                    color = if (state.isAccessibilityConnected) theme.primaryAccent else Color(0xFFEF5350),
                                    fontSize = 12.sp
                                )
                            }
                            IconButton(onClick = onOpenAccessibilitySettings) {
                                Icon(Icons.Default.Settings, contentDescription = null, tint = theme.primaryAccent)
                            }
                        }
                    }
                }
            }
        }

        // =========================================================================
        // CATEGORY 3: 📍 JOYLASHUV VA GEOFENCE (GPS & LOCATIONS)
        // =========================================================================
        if (selectedCategory == "ALL" || selectedCategory == "GPS") {
            // GPS Service Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("📍 GPS Geofence xizmati", color = theme.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                                Text(
                                    if (state.isLocationServiceRunning) "Aktiv — RTM va Maktab nazoratda" else "O'chiq",
                                    color = if (state.isLocationServiceRunning) theme.primaryAccent else theme.textSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Switch(
                                checked = state.isLocationServiceRunning,
                                onCheckedChange = { onToggleLocationService() },
                                colors = switchColors()
                            )
                        }

                        if (state.lastDetectedLocation != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Oxirgi aniqlangan joy: ${state.lastDetectedLocation}",
                                color = theme.primaryAccent,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Geofence Coordinates & Quick Capture Setting Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("🌐 RTM va Maktab Geofence", color = theme.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                        Text("Koordinatalarni qo'lda kiritishingiz yoki bitta tugma bilan hozirgi joylashuvingizni belgilashingiz mumkin:", color = theme.textSecondary, fontSize = 12.sp)

                        // RTM Coordinates with GPS button
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("RTM Markazi", color = theme.primaryAccent, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                TextButton(
                                    onClick = {
                                        onCaptureCurrentLocation { lat, lng ->
                                            onRtmLatChange(String.format(java.util.Locale.US, "%.6f", lat))
                                            onRtmLngChange(String.format(java.util.Locale.US, "%.6f", lng))
                                        }
                                    }
                                ) {
                                    Text("📍 Hozirgi joyni olish", fontSize = 11.5.sp, color = theme.primaryAccent)
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = rtmLatInput,
                                    onValueChange = onRtmLatChange,
                                    label = { Text("Kenglik (Lat)") },
                                    modifier = Modifier.weight(1f),
                                    colors = outlinedFieldColors(),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = rtmLngInput,
                                    onValueChange = onRtmLngChange,
                                    label = { Text("Uzunlik (Lng)") },
                                    modifier = Modifier.weight(1f),
                                    colors = outlinedFieldColors(),
                                    singleLine = true
                                )
                            }
                        }

                        // Maktab Coordinates with GPS button
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Maktab", color = theme.primaryAccent, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                TextButton(
                                    onClick = {
                                        onCaptureCurrentLocation { lat, lng ->
                                            onMaktabLatChange(String.format(java.util.Locale.US, "%.6f", lat))
                                            onMaktabLngChange(String.format(java.util.Locale.US, "%.6f", lng))
                                        }
                                    }
                                ) {
                                    Text("📍 Hozirgi joyni olish", fontSize = 11.5.sp, color = theme.primaryAccent)
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = maktabLatInput,
                                    onValueChange = onMaktabLatChange,
                                    label = { Text("Kenglik (Lat)") },
                                    modifier = Modifier.weight(1f),
                                    colors = outlinedFieldColors(),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = maktabLngInput,
                                    onValueChange = onMaktabLngChange,
                                    label = { Text("Uzunlik (Lng)") },
                                    modifier = Modifier.weight(1f),
                                    colors = outlinedFieldColors(),
                                    singleLine = true
                                )
                            }
                        }

                        OutlinedTextField(
                            value = radiusInput,
                            onValueChange = onRadiusChange,
                            label = { Text("Geofence radiusi (metr)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = outlinedFieldColors(),
                            singleLine = true
                        )

                        Button(
                            onClick = { onSaveGeofence(rtmLatInput, rtmLngInput, maktabLatInput, maktabLngInput, radiusInput) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                        ) {
                            Text("RTM & Maktab koordinatalarini saqlash", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Custom Locations Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("📍 Boshqa maxsus joylashuvlar", color = theme.textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp, fontFamily = FontFamily.Serif)
                                Text("Sport zal, kutubxona yoki masjidga borganda reja faollashadi", color = theme.textSecondary, fontSize = 12.sp)
                            }
                        }

                        if (state.customLocations.isEmpty()) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = theme.glassSurface,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Hali maxsus joylashuv qo'shilmagan. Quyidagi tugma orqali yangi joy qo'shishingiz mumkin.",
                                    color = theme.textMuted,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                        } else {
                            state.customLocations.forEach { loc ->
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = theme.glassSurface,
                                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = "📍 ${loc.name}", color = theme.textPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                            Text(
                                                text = "${String.format(java.util.Locale.US, "%.4f, %.4f", loc.lat, loc.lng)} (radius: ${loc.radiusMeters.toInt()}m)",
                                                color = theme.textSecondary,
                                                fontSize = 11.sp
                                            )
                                            if (loc.targetHabitTitle.isNotBlank()) {
                                                Text(
                                                    text = "Vazifa: «${loc.targetHabitTitle}»",
                                                    color = theme.primaryAccent,
                                                    fontSize = 11.5.sp
                                                )
                                            }
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Switch(
                                                checked = loc.isEnabled,
                                                onCheckedChange = { onToggleCustomLocation(loc.id, it) },
                                                colors = switchColors()
                                            )
                                            IconButton(onClick = { onDeleteCustomLocation(loc.id) }) {
                                                Icon(Icons.Default.Delete, contentDescription = "O'chirish", tint = Color(0xFFEF5350))
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = onOpenAddCustomLocation,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent.copy(alpha = 0.2f)),
                            border = BorderStroke(1.dp, theme.primaryAccent)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("+ Yangi joylashuv qo'shish", color = theme.primaryAccent, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // =========================================================================
        // CATEGORY 4: 🎨 DIZAYN VA FON (THEME & DYNAMIC WALLPAPER)
        // =========================================================================
        if (selectedCategory == "ALL" || selectedCategory == "DESIGN") {
            // 5-Variant Liquid Glass Theme Selector
            item {
                LiquidThemeSelectorBar(
                    selectedThemeId = state.selectedThemeId,
                    onSelectTheme = onSelectTheme
                )
            }

            // AI Dynamic Wallpaper Section
            item {
                AiWallpaperSection(
                    isEnabled = state.isAiWallpaperEnabled,
                    isUpdating = state.isWallpaperUpdating,
                    statusMessage = state.wallpaperStatusMessage,
                    explanation = state.wallpaperExplanation,
                    wallpaperTarget = state.wallpaperTarget,
                    hasCustomWallpaper = state.hasCustomWallpaper,
                    currentTaskTitle = state.activeScheduleItem?.title ?: state.habitState.title,
                    currentTaskTime = state.activeScheduleItem?.let { "${it.start} – ${it.end}" } ?: "${state.habitState.start} – ${state.habitState.end}",
                    onToggle = onToggleAiWallpaper,
                    onSetTarget = onSetWallpaperTarget,
                    onPickImageUri = onPickWallpaperImage,
                    onClearCustomWallpaper = onClearCustomWallpaper,
                    onApplyNow = onApplyAiWallpaperNow
                )
            }
        }

        // =========================================================================
        // CATEGORY 5: 🤖 AI VA BOT (GEMINI AI & TELEGRAM REPORT)
        // =========================================================================
        if (selectedCategory == "ALL" || selectedCategory == "AI") {
            // Google Gemini AI Configuration Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("✨", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Google Gemini AI",
                                    color = theme.textPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(999.dp),
                                color = if (state.userGeminiApiKey.isNotBlank()) Color(0xFF2E7D32).copy(alpha = 0.2f) else theme.primaryAccent.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = if (state.userGeminiApiKey.isNotBlank()) "API Faol" else "Lokal Aqlli Fallback",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    fontSize = 11.sp,
                                    color = if (state.userGeminiApiKey.isNotBlank()) Color(0xFF81C784) else theme.primaryAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = "Ilovada vazifalarni aqlli rejalashtirish, kunlik motivatsiya va fon rasmlari uchun Gemini AI ishlatiladi. Kalitsiz ham avtonom lokal aqlli rejim to'liq ishlaydi.",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )

                        OutlinedTextField(
                            value = userApiKeyInput,
                            onValueChange = onUserApiKeyChange,
                            label = { Text("Gemini API Kaliti (AI Studio)") },
                            placeholder = { Text("AIzaSy...", color = theme.textMuted) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = outlinedFieldColors(),
                            singleLine = true
                        )

                        Button(
                            onClick = { onSaveUserGeminiApiKey(userApiKeyInput) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                        ) {
                            Text("API Kalitini Saqlash", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Telegram Bot Report Section
            item {
                TelegramReportSection(
                    botToken = state.telegramBotToken,
                    chatId = state.telegramChatId,
                    isSending = state.isTelegramSending,
                    statusMessage = state.telegramStatusMessage,
                    onSaveSettings = onSaveTelegramSettings,
                    onSendReport = onSendTelegramReport
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// -------------------------------------------------------------
// HELPER STYLES
// -------------------------------------------------------------
@Composable
fun outlinedFieldColors(): androidx.compose.material3.TextFieldColors = LocalLiquidTheme.current.let { theme ->
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = theme.textPrimary,
        unfocusedTextColor = theme.textPrimary,
        focusedBorderColor = theme.primaryAccent,
        unfocusedBorderColor = theme.glassBorderSubtleColor,
        focusedLabelColor = theme.primaryAccent,
        unfocusedLabelColor = theme.textSecondary,
        cursorColor = theme.primaryAccent
    )
}

@Composable
fun switchColors() = LocalLiquidTheme.current.let { theme ->
    SwitchDefaults.colors(
        checkedThumbColor = Color.Black,
        checkedTrackColor = theme.primaryAccent,
        uncheckedThumbColor = theme.textSecondary,
        uncheckedTrackColor = theme.glassSurface
    )
}
