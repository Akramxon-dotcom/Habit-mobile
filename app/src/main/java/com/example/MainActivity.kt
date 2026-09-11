package com.example

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.alarm.AlarmHelper
import com.example.service.HabitBlockerService
import com.example.ui.HabitViewModel
import com.example.ui.dialog.PermissionsDialog
import com.example.ui.permissions.PermissionUtils
import com.example.ui.screen.HomeScreen
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HabitViewModel by viewModels()

    private val foregroundLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (fineGranted || coarseGranted) {
            Toast.makeText(this, "GPS ruxsati berildi!", Toast.LENGTH_SHORT).show()
            // Optionally ask background location
            promptBackgroundLocation()
        } else {
            Toast.makeText(this, "GPS ruxsati rad etildi. Joylashuvni aniqlab bo'lmaydi.", Toast.LENGTH_LONG).show()
        }
        viewModel.checkPermissions()
    }

    private val backgroundLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Fonda joylashuv ruxsati berildi!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Fonda joylashuv rad etildi.", Toast.LENGTH_LONG).show()
        }
        viewModel.checkPermissions()
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Bildirishnoma ruxsati berildi!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Bildirishnomalar rad etildi.", Toast.LENGTH_LONG).show()
        }
        viewModel.checkPermissions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AlarmHelper.createNotificationChannels(this)

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            val liquidTheme = com.example.ui.theme.LiquidGlassStyles.getById(uiState.selectedThemeId)

            HabitTheme(themeStyle = liquidTheme) {
                val snackbarHostState = remember { SnackbarHostState() }
                var showPermissionsDialog by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    viewModel.userMessage.collect { msg ->
                        snackbarHostState.showSnackbar(msg)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    HomeScreen(
                        state = uiState,
                        onSelectTheme = { themeId -> viewModel.setLiquidTheme(themeId) },
                        onRefresh = { viewModel.refreshFirestoreState() },
                        onToggleLocationService = {
                            if (!uiState.permissionStatus.hasFineLocation) {
                                requestForegroundLocation()
                            } else {
                                viewModel.toggleLocationService()
                            }
                        },
                        onOpenAccessibilitySettings = {
                            HabitBlockerService.openAccessibilitySettings(this@MainActivity)
                        },
                        onOpenPermissionsDialog = {
                            showPermissionsDialog = true
                        },
                        onTestAlarm = {
                            viewModel.testTriggerAlarm()
                        },
                        onScheduleTestAlarm = { seconds ->
                            viewModel.scheduleTestAlarm(seconds)
                        },
                        onSaveGeofence = { rtmLat, rtmLng, maktabLat, maktabLng, radius ->
                            viewModel.saveGeofenceSettings(rtmLat, rtmLng, maktabLat, maktabLng, radius)
                        },
                        onSaveBlockedPackages = { packages ->
                            viewModel.saveBlockedApps(packages)
                        },
                        onActivateScheduleItem = { item ->
                            viewModel.activateScheduleItem(item)
                        },
                        onAddScheduleItem = { title, cat, start, end, note, blocking ->
                            viewModel.addScheduleItem(title, cat, start, end, note, blocking)
                        },
                        onDeleteScheduleItem = { id ->
                            viewModel.deleteScheduleItem(id)
                        },
                        onUpdateCurrentTask = { title, cat, start, end, note, blocking ->
                            viewModel.updateCurrentTaskInFirestore(title, cat, start, end, note, blocking)
                        },
                        onMarkTaskCompleted = { item ->
                            viewModel.markTaskCompleted(item)
                        },
                        onDelaySchedule = { minutes ->
                            viewModel.delaySchedule(minutes)
                        },
                        onSelectTab = { tab ->
                            viewModel.selectTab(tab)
                        },
                        onSelectDayOffset = { offset ->
                            viewModel.selectDayOffset(offset)
                        },
                        onRefreshWeather = {
                            viewModel.refreshWeather()
                        },
                        onToggleAiWallpaper = { enabled ->
                            viewModel.toggleAiWallpaper(enabled)
                        },
                        onSetWallpaperTarget = { target ->
                            viewModel.setWallpaperTarget(target)
                        },
                        onPickWallpaperImage = { uri ->
                            viewModel.onCustomWallpaperSelected(uri)
                        },
                        onClearCustomWallpaper = {
                            viewModel.onClearCustomWallpaper()
                        },
                        onApplyAiWallpaperNow = {
                            viewModel.applyAiWallpaperNow()
                        },
                        onSaveTelegramSettings = { token, chatId ->
                            viewModel.saveTelegramSettings(token, chatId)
                        },
                        onSendTelegramReport = {
                            viewModel.sendTelegramReport()
                        },
                        onAddWordWithAi = { word ->
                            viewModel.addWordWithAi(word)
                        },
                        onDeleteVocabCard = { id ->
                            viewModel.deleteVocabCard(id)
                        },
                        onUpdateVocabBoxLevel = { id, level ->
                            viewModel.updateVocabBoxLevel(id, level)
                        },
                        onGenerateQuiz = {
                            viewModel.generateQuiz()
                        },
                        onCloseQuiz = {
                            viewModel.closeQuiz()
                        },
                        onSaveDailyJournal = { stars, highlights, challenges, reflections ->
                            viewModel.saveDailyJournal(stars, highlights, challenges, reflections)
                        },
                        onUnlockApp = {
                            viewModel.unlockApp()
                        },
                        onSetSmartAddOpen = { isOpen ->
                            viewModel.setSmartAddOpen(isOpen)
                        },
                        onSetQiblaOpen = { isOpen ->
                            viewModel.setQiblaOpen(isOpen)
                        },
                        onSetBreathOpen = { isOpen ->
                            viewModel.setBreathOpen(isOpen)
                        },
                        onSetMotionOpen = { isOpen ->
                            viewModel.setMotionOpen(isOpen)
                        },
                        onSyncSupabase = {
                            viewModel.syncWithSupabase()
                        },
                        onReplanWithAi = {
                            viewModel.replanWithAi()
                        },
                        onAddNewTask = { item ->
                            viewModel.addScheduleItem(
                                title = item.title,
                                category = item.category,
                                start = item.start,
                                end = item.end,
                                note = item.note,
                                blocking = item.blocking
                            )
                        },
                        onDelayTaskWithReason = { task, reason ->
                            viewModel.delayTaskWithReason(task, reason)
                        },
                        onToggleBlockerPaused = { paused ->
                            viewModel.toggleBlockerPaused(paused)
                        },
                        onOpenAppPicker = {
                            viewModel.setAppPickerOpen(true)
                        },
                        onCloseAppPicker = {
                            viewModel.setAppPickerOpen(false)
                        },
                        onToggleAppBlocked = { pkg, blocked ->
                            viewModel.toggleAppBlocked(pkg, blocked)
                        },
                        onCaptureCurrentLocation = { callback ->
                            viewModel.captureCurrentGpsLocation(callback)
                        },
                        onAddCustomLocation = { name, lat, lng, rad, act, habit ->
                            viewModel.addCustomLocation(name, lat, lng, rad, act, habit)
                        },
                        onDeleteCustomLocation = { id ->
                            viewModel.deleteCustomLocation(id)
                        },
                        onToggleCustomLocation = { id, enabled ->
                            viewModel.toggleCustomLocation(id, enabled)
                        },
                        onSaveUserGeminiApiKey = { key ->
                            viewModel.saveUserGeminiApiKey(key)
                        },
                        onToggleAlarmMute = { muted -> viewModel.toggleAlarmMute(muted) },
                        onSetAlarmVolume = { vol -> viewModel.setAlarmVolume(vol) },
                        onSetAlarmSoundTone = { tone -> viewModel.setAlarmSoundTone(tone) },
                        onPlayTestAlarmSound = { viewModel.playTestAlarmSound() },
                        onSetDailyVocabGoal = { goal -> viewModel.setDailyVocabGoal(goal) },
                        onImportVocabDocument = { uri, name -> viewModel.importVocabDocument(uri, name) },
                        onImportVocabText = { text, name -> viewModel.importVocabText(text, name) },
                        onLoadSampleCefrVocab = { viewModel.loadSampleCefrVocabulary() },
                        onMarkVocabMastered = { id -> viewModel.markVocabMastered(id) },
                        onResetVocabForReview = { id -> viewModel.resetVocabForReview(id) }
                    )

                    if (showPermissionsDialog) {
                        PermissionsDialog(
                            status = uiState.permissionStatus,
                            onDismiss = { showPermissionsDialog = false },
                            onRequestForegroundLocation = { requestForegroundLocation() },
                            onRequestBackgroundLocation = { promptBackgroundLocation() },
                            onRequestNotification = { requestNotification() },
                            onOpenExactAlarm = { PermissionUtils.openExactAlarmSettings(this@MainActivity) },
                            onOpenAccessibility = { HabitBlockerService.openAccessibilitySettings(this@MainActivity) },
                            onRequestBatteryOptimization = { PermissionUtils.requestBatteryOptimizationExemption(this@MainActivity) }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkPermissions()
        viewModel.refreshFirestoreState()
    }

    private fun requestForegroundLocation() {
        foregroundLocationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun promptBackgroundLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val status = PermissionUtils.checkPermissions(this)
            if (status.hasFineLocation) {
                backgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            } else {
                requestForegroundLocation()
            }
        }
    }

    private fun requestNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Toast.makeText(this, "Bildirishnomalar avtomatik faol", Toast.LENGTH_SHORT).show()
        }
    }
}
