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
import com.example.data.model.TaskTimeEngine
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

        // 1. Skip our own app completely (Habit app, settings, block activity) and Android core system UI
        val myPkg = applicationContext.packageName
        if (packageName.equals(myPkg, ignoreCase = true) ||
            packageName.contains("habit", ignoreCase = true) ||
            packageName.contains("kzrqom", ignoreCase = true) ||
            packageName.startsWith("com.example.") ||
            packageName == "com.example" ||
            packageName == "android" ||
            packageName == "com.android.systemui"
        ) {
            return
        }

        // 2. If FocusBlockActivity is already visible and active, ignore events from system
        if (com.example.ui.block.FocusBlockActivity.isVisible) {
            // If another third-party blocked app somehow tried to open, send HOME to dismiss it
            val blockedPkgs = prefs.getBlockedPackagesList()
            if (blockedPkgs.any { it.equals(packageName, ignoreCase = true) } || isCommonDistractionApp(packageName)) {
                performGlobalAction(GLOBAL_ACTION_HOME)
            }
            return
        }

        // Check if master blocker is paused by user
        if (prefs.isBlockerPaused) {
            return
        }

        // Check if temporary emergency bypass is active
        if (prefs.isEmergencyBypassActive()) {
            return
        }

        // Check if current app is launcher or dialer/call (phone calls are completely free)
        if (isLauncherApp(packageName) || isDialerOrCallApp(packageName)) {
            return
        }

        // Check if blocking is active in real time (Firestore state or local schedule)
        val cached = prefs.getCachedState()
        val isFirestoreActive = prefs.isBlockingActive && TaskTimeEngine.isTaskActiveNow(cached.start, cached.end)
        val activeScheduleItem = TaskTimeEngine.findActiveScheduleItem(prefs.getSchedule())
        val isScheduleActive = activeScheduleItem?.blocking == true

        if (!isFirestoreActive && !isScheduleActive) return

        val currentTaskTitle = when {
            activeScheduleItem != null && isScheduleActive -> activeScheduleItem.title
            isFirestoreActive -> cached.title
            else -> "Fokus vaqti"
        }
        val currentTaskStart = when {
            activeScheduleItem != null && isScheduleActive -> activeScheduleItem.start
            isFirestoreActive -> cached.start
            else -> ""
        }
        val currentTaskEnd = when {
            activeScheduleItem != null && isScheduleActive -> activeScheduleItem.end
            isFirestoreActive -> cached.end
            else -> ""
        }

        // If app is not system or dialer/launcher, block it!
        val blockedPackages = prefs.getBlockedPackagesList()
        val isExplicitlyBlocked = blockedPackages.any { it.equals(packageName, ignoreCase = true) }
        val shouldBlock = isExplicitlyBlocked || blockedPackages.isEmpty() || isCommonDistractionApp(packageName)

        if (shouldBlock) {
            Log.w(TAG, "Qat'iy blokirovka: $packageName ochildi! Chiqarib yuborish va FocusBlockActivity ko'rsatilmoqda.")

            // 1. Immediately kick the user out of the blocked app back to HOME
            performGlobalAction(GLOBAL_ACTION_HOME)

            val now = System.currentTimeMillis()
            if (now - lastBlockActivityLaunchTime > BLOCK_COOLDOWN_MS) {
                lastBlockActivityLaunchTime = now
                // 2. Launch FocusBlockActivity on top of Home with a short delay
                mainHandler.postDelayed({
                    try {
                        val blockIntent = Intent(applicationContext, com.example.ui.block.FocusBlockActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                            putExtra("task_title", currentTaskTitle)
                            putExtra("task_time", "$currentTaskStart — $currentTaskEnd")
                        }
                        startActivity(blockIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, 120L)
            }
        }
    }

    private var lastBlockActivityLaunchTime = 0L
    private val BLOCK_COOLDOWN_MS = 1000L

    private fun isDialerOrCallApp(packageName: String): Boolean {
        val lower = packageName.lowercase()
        if (lower.contains("dialer") ||
            lower.contains("incallui") ||
            lower.contains("telecom") ||
            lower.contains("phone") ||
            lower.contains("contacts")
        ) {
            return true
        }
        try {
            val telecomManager = getSystemService(Context.TELECOM_SERVICE) as? android.telecom.TelecomManager
            val defaultDialer = telecomManager?.defaultDialerPackage
            if (defaultDialer != null && defaultDialer.equals(packageName, ignoreCase = true)) {
                return true
            }
        } catch (e: Exception) {
            // ignore
        }
        return false
    }

    private fun isLauncherApp(packageName: String): Boolean {
        val lower = packageName.lowercase()
        if (lower.contains("launcher") || lower.contains("trebuchet") || lower.contains("home")) {
            return true
        }
        try {
            val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
            val resolveInfo = packageManager.resolveActivity(intent, 0)
            if (resolveInfo?.activityInfo?.packageName?.equals(packageName, ignoreCase = true) == true) {
                return true
            }
        } catch (e: Exception) {
            // ignore
        }
        return false
    }

    private fun isCommonDistractionApp(packageName: String): Boolean {
        val lower = packageName.lowercase()
        val distractKeywords = listOf(
            "telegram", "instagram", "tiktok", "youtube", "facebook", "twitter",
            "browser", "chrome", "firefox", "opera", "game", "pubg", "reels",
            "shorts", "vk", "whatsapp", "snapchat", "pinterest", "netflix"
        )
        return distractKeywords.any { lower.contains(it) }
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
