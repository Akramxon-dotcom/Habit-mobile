package com.example.service

import android.app.AppOpsManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.local.HabitPreferences
import java.util.Calendar

data class AppUsageGoal(
    val id: String,
    val displayName: String,
    val taskKeywords: List<String>,
    val packageKeywords: List<String>,
    val defaultPackages: List<String>,
    val iconEmoji: String,
    val defaultRequiredMinutes: Int
)

data class AppUsageStatus(
    val goal: AppUsageGoal,
    val targetPackageName: String?,
    val isAppInstalled: Boolean,
    val usedMinutes: Int,
    val requiredMinutes: Int,
    val isFulfilled: Boolean,
    val remainingMinutes: Int,
    val isNeverUsed: Boolean,
    val isPartiallyUsed: Boolean
)

object AppUsageTracker {
    private const val TAG = "AppUsageTracker"
    private const val CHANNEL_ID_AUTO_COMPLETE = "channel_task_auto_complete"

    // Supported predefined educational app goals
    val GOAL_IBRAT = AppUsageGoal(
        id = "ibrat",
        displayName = "Ibrat Farzandlari",
        taskKeywords = listOf("ibrat", "ibrat academy", "ibrat farzandlari", "ibrat academy darsi"),
        packageKeywords = listOf("uz.ibrat", "ibratfarzandlari", "ibrat"),
        defaultPackages = listOf("uz.ibrat.farzandlari", "uz.ibratfarzandlari", "uz.ibrat", "com.ibrat"),
        iconEmoji = "📚",
        defaultRequiredMinutes = 20
    )

    val GOAL_CAKE = AppUsageGoal(
        id = "cake",
        displayName = "Cake (Shadowing)",
        taskKeywords = listOf("cake", "cake ilovasi", "cake shadowing", "cake + ibrat"),
        packageKeywords = listOf("me.cake", "cake.english", "com.cake"),
        defaultPackages = listOf("me.cake.english", "me.cake", "com.cake.english", "com.cake.android"),
        iconEmoji = "🎙️",
        defaultRequiredMinutes = 20
    )

    val ALL_GOALS = listOf(GOAL_IBRAT, GOAL_CAKE)

    /**
     * Check if PACKAGE_USAGE_STATS permission is granted by user.
     */
    fun hasUsagePermission(context: Context): Boolean {
        return try {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
                ?: return false
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    context.packageName
                )
            } else {
                @Suppress("DEPRECATION")
                appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),
                    context.packageName
                )
            }
            mode == AppOpsManager.MODE_ALLOWED
        } catch (e: Exception) {
            Log.e(TAG, "Error checking usage permission: ${e.message}")
            false
        }
    }

    /**
     * Open system Settings page where user can grant usage access.
     */
    fun openUsageSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                // Direct package uri if supported
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val fallbackIntent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(fallbackIntent)
            } catch (e2: Exception) {
                Log.e(TAG, "Cannot open usage settings: ${e2.message}")
            }
        }
    }

    /**
     * Match a task title to an AppUsageGoal (e.g. "IBRAT Academy" or "Cake ilovasi").
     */
    fun findGoalForTask(taskTitle: String): AppUsageGoal? {
        val lower = taskTitle.lowercase()
        return ALL_GOALS.firstOrNull { goal ->
            goal.taskKeywords.any { keyword -> lower.contains(keyword) }
        }
    }

    /**
     * Find installed package for a goal by matching known package names or keywords.
     */
    fun findInstalledPackageForGoal(context: Context, goal: AppUsageGoal): String? {
        val pm = context.packageManager

        // 1. Direct check default packages
        for (pkg in goal.defaultPackages) {
            try {
                pm.getPackageInfo(pkg, 0)
                return pkg
            } catch (_: PackageManager.NameNotFoundException) {
            }
        }

        // 2. Scan installed applications
        try {
            val installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
            for (app in installedApps) {
                val pkgName = app.packageName.lowercase()
                val label = try {
                    pm.getApplicationLabel(app).toString().lowercase()
                } catch (_: Exception) {
                    ""
                }
                if (goal.packageKeywords.any { pkgName.contains(it) } ||
                    goal.taskKeywords.any { label.contains(it) }
                ) {
                    return app.packageName
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error scanning installed apps: ${e.message}")
        }

        return goal.defaultPackages.firstOrNull()
    }

    /**
     * Calculate foreground usage in minutes since midnight (00:00) today.
     */
    fun getTodayUsageMinutes(context: Context, targetPackage: String?): Int {
        if (targetPackage.isNullOrBlank()) return 0
        if (!hasUsagePermission(context)) return 0

        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
            ?: return 0

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startTime = calendar.timeInMillis
        val endTime = System.currentTimeMillis()

        if (startTime >= endTime) return 0

        var totalForegroundMs = 0L

        // Method A: Query Usage Events for pinpoint accurate session calculation
        try {
            val events = usageStatsManager.queryEvents(startTime, endTime)
            val event = UsageEvents.Event()
            var lastResumeTime = 0L

            while (events.hasNextEvent()) {
                events.getNextEvent(event)
                if (event.packageName == targetPackage) {
                    when (event.eventType) {
                        UsageEvents.Event.ACTIVITY_RESUMED -> {
                            lastResumeTime = event.timeStamp
                        }
                        UsageEvents.Event.ACTIVITY_PAUSED,
                        UsageEvents.Event.ACTIVITY_STOPPED -> {
                            if (lastResumeTime > 0) {
                                totalForegroundMs += (event.timeStamp - lastResumeTime).coerceAtLeast(0)
                                lastResumeTime = 0L
                            }
                        }
                    }
                }
            }
            // If app is currently in foreground
            if (lastResumeTime > 0) {
                totalForegroundMs += (endTime - lastResumeTime).coerceAtLeast(0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error querying usage events: ${e.message}")
        }

        // Method B: Fallback or verification using queryUsageStats
        if (totalForegroundMs <= 0L) {
            try {
                val statsList = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY,
                    startTime,
                    endTime
                )
                val appStat = statsList.firstOrNull { it.packageName == targetPackage }
                if (appStat != null && appStat.totalTimeInForeground > 0L) {
                    totalForegroundMs = appStat.totalTimeInForeground
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error querying usage stats: ${e.message}")
            }
        }

        val minutes = (totalForegroundMs / (1000 * 60)).toInt()
        Log.d(TAG, "Today usage for $targetPackage: $minutes min ($totalForegroundMs ms)")
        return minutes
    }

    /**
     * Check if a task is linked to an external app and return its detailed usage status.
     */
    fun checkStatusForTask(
        context: Context,
        taskTitle: String,
        taskId: String = ""
    ): AppUsageStatus? {
        val goal = findGoalForTask(taskTitle) ?: return null
        val prefs = HabitPreferences(context)

        // Determine required minutes:
        // Check if taskTitle contains explicit minutes, e.g. "20 daqiqa" or "20 min"
        val explicitMinutes = Regex("""(\d+)\s*(?:daq|min|daqiqa)""", RegexOption.IGNORE_CASE)
            .find(taskTitle)?.groupValues?.getOrNull(1)?.toIntOrNull()

        val requiredMinutes = explicitMinutes ?: when (goal.id) {
            GOAL_IBRAT.id -> prefs.ibratRequiredMinutes
            GOAL_CAKE.id -> prefs.cakeRequiredMinutes
            else -> goal.defaultRequiredMinutes
        }

        val targetPackage = findInstalledPackageForGoal(context, goal)
        val isInstalled = targetPackage != null && try {
            context.packageManager.getPackageInfo(targetPackage, 0)
            true
        } catch (_: Exception) {
            false
        }

        val usedMinutes = getTodayUsageMinutes(context, targetPackage)
        val isFulfilled = usedMinutes >= requiredMinutes
        val remainingMinutes = (requiredMinutes - usedMinutes).coerceAtLeast(0)
        val isNeverUsed = (usedMinutes == 0)
        val isPartiallyUsed = (usedMinutes in 1 until requiredMinutes)

        return AppUsageStatus(
            goal = goal,
            targetPackageName = targetPackage,
            isAppInstalled = isInstalled,
            usedMinutes = usedMinutes,
            requiredMinutes = requiredMinutes,
            isFulfilled = isFulfilled,
            remainingMinutes = remainingMinutes,
            isNeverUsed = isNeverUsed,
            isPartiallyUsed = isPartiallyUsed
        )
    }

    /**
     * Launch the target app directly or open Play Store if not installed.
     */
    fun launchApp(context: Context, targetPackage: String?): Boolean {
        if (targetPackage.isNullOrBlank()) return false
        val pm = context.packageManager
        val launchIntent = pm.getLaunchIntentForPackage(targetPackage)
        return if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
            true
        } else {
            // Open Play Store
            try {
                val storeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$targetPackage")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(storeIntent)
                true
            } catch (_: Exception) {
                try {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$targetPackage")).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(webIntent)
                    true
                } catch (_: Exception) {
                    false
                }
            }
        }
    }

    /**
     * Show a pleasant auto-completed notification when task is fulfilled automatically without disturbing alarm!
     */
    fun showAutoCompletedNotification(
        context: Context,
        taskTitle: String,
        usedMinutes: Int,
        requiredMinutes: Int
    ) {
        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID_AUTO_COMPLETE,
                    "Avtomatik bajarilgan vazifalar",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Ilovalardan foydalanish me'yori bajarilganda xabarnomalar"
                }
                notificationManager.createNotificationChannel(channel)
            }

            val openAppIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                taskTitle.hashCode(),
                openAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_AUTO_COMPLETE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("🎉 «$taskTitle» avtomatik bajarildi!")
                .setContentText("Bugun ilovada $usedMinutes/$requiredMinutes daqiqa shug'ullandingiz. Vazifa so'rovsiz bajarildi deb belgilandi.")
                .setStyle(NotificationCompat.BigTextStyle().bigText(
                    "🎉 Ajoyib natija!\n\n" +
                            "«$taskTitle» vazifasi uchun bugun ilovada $usedMinutes daqiqa (talab me'yori: $requiredMinutes daqiqa) faol bo'lganingiz aniqlandi.\n\n" +
                            "Shu sababli so'rov o'tkazilmasdan, vazifa avtomatik ravishda bajarildi deb hisobga olindi!"
                ))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(taskTitle.hashCode(), notification)
        } catch (e: Exception) {
            Log.e(TAG, "Error showing auto complete notification: ${e.message}")
        }
    }
}
