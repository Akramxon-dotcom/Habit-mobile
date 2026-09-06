package com.example.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.example.data.local.HabitPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitBlockerService : AccessibilityService() {

    companion object {
        private const val TAG = "HabitBlockerService"

        private val _isServiceConnected = MutableStateFlow(false)
        val isServiceConnected: StateFlow<Boolean> = _isServiceConnected.asStateFlow()

        private var lastToastTime = 0L
        private const val TOAST_COOLDOWN_MS = 2500L

        fun isAccessibilityEnabled(context: Context): Boolean {
            val expectedComponentName = ComponentName(context, HabitBlockerService::class.java).flattenToString()
            val enabledServicesSetting = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            ) ?: return false

            val colonSplitter = TextUtils.SimpleStringSplitter(':')
            colonSplitter.setString(enabledServicesSetting)

            while (colonSplitter.hasNext()) {
                val componentName = colonSplitter.next()
                if (componentName.equals(expectedComponentName, ignoreCase = true)) {
                    return true
                }
            }
            return false
        }

        fun openAccessibilitySettings(context: Context) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    private lateinit var prefs: HabitPreferences
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onServiceConnected() {
        super.onServiceConnected()
        prefs = HabitPreferences(this)
        _isServiceConnected.value = true
        Log.d(TAG, "HabitBlockerService ulandi!")

        serviceInfo = serviceInfo.apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
            notificationTimeout = 100
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        // Skip system and self
        if (packageName == applicationContext.packageName ||
            packageName == "android" ||
            packageName == "com.android.systemui" ||
            packageName.contains("launcher")
        ) {
            return
        }

        // Check if blocking is active in Firestore
        val isBlocking = prefs.isBlockingActive
        if (!isBlocking) return

        val blockedPackages = prefs.getBlockedPackagesList()
        val isBlockedApp = blockedPackages.any { it.equals(packageName, ignoreCase = true) }

        if (isBlockedApp) {
            Log.w(TAG, "Taqiqlangan ilova ochildi ($packageName)! Uy ekraniga qaytarilmoqda.")
            // Redirect to home screen immediately
            performGlobalAction(GLOBAL_ACTION_HOME)

            // Show Toast with cooldown
            val now = System.currentTimeMillis()
            if (now - lastToastTime > TOAST_COOLDOWN_MS) {
                lastToastTime = now
                mainHandler.post {
                    Toast.makeText(
                        applicationContext,
                        "🚫 Habit: Hozir band vaqtingiz! Chalg'ituvchi ilova bloklandi.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "HabitBlockerService to'xtatildi")
        _isServiceConnected.value = false
    }

    override fun onDestroy() {
        _isServiceConnected.value = false
        super.onDestroy()
    }
}
