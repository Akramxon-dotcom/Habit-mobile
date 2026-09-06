package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.AlarmHelper
import com.example.data.local.HabitPreferences
import com.example.data.model.HabitState
import com.example.data.remote.FirestoreClient
import com.example.service.HabitBlockerService
import com.example.service.HabitLocationService
import com.example.ui.permissions.PermissionUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HabitUiState(
    val habitState: HabitState = HabitState(),
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
    val lastDetectedLocation: String? = null
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

    fun loadSettingsFromPrefs() {
        val cached = prefs.getCachedState()
        _uiState.update {
            it.copy(
                habitState = cached,
                rtmLat = prefs.rtmLat.toString(),
                rtmLng = prefs.rtmLng.toString(),
                maktabLat = prefs.maktabLat.toString(),
                maktabLng = prefs.maktabLng.toString(),
                radiusMeters = prefs.radiusMeters.toInt().toString(),
                blockedPackages = prefs.blockedPackages,
                isLocationServiceRunning = prefs.isLocationServiceEnabled,
                isAccessibilityConnected = HabitBlockerService.isAccessibilityEnabled(getApplication())
            )
        }
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

    fun refreshFirestoreState() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = FirestoreClient.getState()
            if (result.isSuccess) {
                val state = result.getOrThrow()
                prefs.saveCachedState(state)
                val timeStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                _uiState.update {
                    it.copy(
                        habitState = state,
                        isLoading = false,
                        lastSyncFormatted = "Bugun $timeStr"
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
                _userMessage.emit("Firestore bilan ulanishda xatolik: ${result.exceptionOrNull()?.message}")
            }
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
}
