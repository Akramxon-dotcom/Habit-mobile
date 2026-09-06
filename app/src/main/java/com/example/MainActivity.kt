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
            HabitTheme {
                val uiState by viewModel.uiState.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }
                var showPermissionsDialog by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    viewModel.userMessage.collect { msg ->
                        snackbarHostState.showSnackbar(msg)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = HabitDarkBg,
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    HomeScreen(
                        state = uiState,
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
                        onSaveGeofence = { rtmLat, rtmLng, maktabLat, maktabLng, radius ->
                            viewModel.saveGeofenceSettings(rtmLat, rtmLng, maktabLat, maktabLng, radius)
                        },
                        onSaveBlockedPackages = { packages ->
                            viewModel.saveBlockedApps(packages)
                        }
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
