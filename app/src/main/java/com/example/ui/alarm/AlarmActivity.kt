package com.example.ui.alarm

import android.app.KeyguardManager
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarm.AlarmHelper
import com.example.alarm.AlarmRingtoneService
import com.example.data.local.HabitPreferences
import com.example.data.remote.FirestoreClient
import com.example.service.AiWallpaperManager
import com.example.service.HabitNotificationHelper
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitError
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitTheme
import com.example.widget.HabitAppWidgetProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AlarmActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TASK_ID = "extra_task_id"
        const val EXTRA_TASK_TITLE = "extra_task_title"
        const val EXTRA_TASK_CATEGORY = "extra_task_category"
        const val EXTRA_TASK_END = "extra_task_end"
        const val EXTRA_TASK_NOTE = "extra_task_note"
        private const val TAG = "AlarmActivity"
    }

    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    private lateinit var prefs: HabitPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = HabitPreferences(this)

        configureWindowForLockScreen()
        startAlarmAudioAndVibration()

        val taskId = intent.getStringExtra(EXTRA_TASK_ID) ?: ""
        val taskTitle = intent.getStringExtra(EXTRA_TASK_TITLE)?.ifBlank { "Kun tartibi vazifasi" } ?: "Kun tartibi vazifasi"
        val category = intent.getStringExtra(EXTRA_TASK_CATEGORY) ?: ""
        val endTime = intent.getStringExtra(EXTRA_TASK_END) ?: ""
        val note = intent.getStringExtra(EXTRA_TASK_NOTE) ?: ""

        setContent {
            HabitTheme {
                AlarmScreen(
                    title = taskTitle,
                    category = category,
                    endTime = endTime,
                    note = note,
                    isMuted = prefs.shouldMuteAlarm(),
                    isAtSchool = prefs.isAtSchool || prefs.isSchoolMuted,
                    onDone = {
                        handleAnswerDone(taskId, taskTitle, endTime)
                    },
                    onStopRingtone = {
                        stopAlarmAudioAndVibration()
                    },
                    onSnooze = { delayMinutes ->
                        handleAnswerSnooze(taskId, taskTitle, category, note, delayMinutes)
                    },
                    onDismiss = {
                        handleAnswerDismiss(taskTitle, endTime)
                    }
                )
            }
        }
    }

    private fun configureWindowForLockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
            keyguardManager?.requestDismissKeyguard(this, null)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun startAlarmAudioAndVibration() {
        val shouldMute = prefs.shouldMuteAlarm()
        if (shouldMute) {
            Log.d(TAG, "AlarmActivity: Signal ovozsiz holatda (shouldMuteAlarm=true, isAlarmMuted=${prefs.isAlarmMuted}, isAtSchool=${prefs.isAtSchool})")
        } else {
            // Only start our own MediaPlayer if AlarmRingtoneService is not already playing audio
            if (!AlarmRingtoneService.isServiceRunning()) {
                try {
                    val alertUri: Uri = when (prefs.alarmSoundTone) {
                        "NOTIFICATION" -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                        "RINGTONE" -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                        else -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    }

                    val vol = prefs.alarmVolume.coerceIn(0.05f, 1f)

                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .setLegacyStreamType(AudioManager.STREAM_ALARM)
                                .build()
                        )
                        setDataSource(applicationContext, alertUri)
                        setVolume(vol, vol)
                        isLooping = true
                        prepare()
                        start()
                    }
                    Log.d(TAG, "AlarmActivity: Audio chalish boshlandi (vol=$vol)")
                } catch (e: Exception) {
                    Log.e(TAG, "MediaPlayer xatolik: ${e.message}", e)
                }
            } else {
                Log.d(TAG, "AlarmActivity: AlarmRingtoneService allaqachon audio chalmoqda")
            }
        }

        try {
            vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            val pattern = longArrayOf(0, 800, 400, 800, 400)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createWaveform(pattern, 0)
                vibrator?.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(pattern, 0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Vibrator xatolik: ${e.message}", e)
        }
    }

    private fun stopAlarmAudioAndVibration() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e(TAG, "mediaPlayer to'xtatishda xatolik: ${e.message}")
        }
        try {
            vibrator?.cancel()
            vibrator = null
        } catch (e: Exception) {
            Log.e(TAG, "vibrator to'xtatishda xatolik: ${e.message}")
        }
        AlarmRingtoneService.stopAlarm(this)
        AlarmHelper.cancelAlarmNotification(this)
    }

    private fun handleAnswerDone(taskId: String, taskTitle: String, endTime: String) {
        stopAlarmAudioAndVibration()

        // 1. Mark task completed locally (bulletproof by ID, title, and time)
        prefs.markTaskCompletedByTitleOrId(taskId, taskTitle, endTime)
        prefs.markAlarmAnswered(taskTitle, endTime)

        // 2. Synchronize visual components (Widgets, Ongoing Notification, AI Wallpaper)
        try {
            HabitAppWidgetProvider.updateAllWidgets(this)
            HabitNotificationHelper.showActiveTaskNotification(this)
            if (prefs.isAiWallpaperEnabled) {
                CoroutineScope(Dispatchers.IO).launch {
                    AiWallpaperManager.updateWallpaperForCurrentTask(this@AlarmActivity)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Visual sync error: ${e.message}")
        }

        // 3. Sync to Firestore in background
        val isoTimestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())
        CoroutineScope(Dispatchers.IO).launch {
            FirestoreClient.patchAlarmAnswer(
                answer = "done",
                taskTitle = taskTitle,
                isoTimestamp = isoTimestamp
            )
        }

        Toast.makeText(this, "🎉 Barakalla! «$taskTitle» bajarildi deb belgilandi!", Toast.LENGTH_LONG).show()
        finishAndRemoveTask()
    }

    private fun handleAnswerSnooze(
        taskId: String,
        taskTitle: String,
        category: String,
        note: String,
        delayMinutes: Int
    ) {
        stopAlarmAudioAndVibration()

        val scheduled = AlarmHelper.scheduleSnoozeAlarm(
            context = this,
            title = taskTitle,
            category = category,
            note = note,
            delayMinutes = delayMinutes,
            taskId = taskId
        )

        val isoTimestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())
        CoroutineScope(Dispatchers.IO).launch {
            FirestoreClient.patchAlarmAnswer(
                answer = "snoozed_${delayMinutes}m",
                taskTitle = taskTitle,
                isoTimestamp = isoTimestamp
            )
        }

        val msg = if (scheduled) {
            "⏱️ «$taskTitle» eslatmasi $delayMinutes daqiqaga qoldirildi. Vaqt tugagach yana to'liq ekranli so'rov yuboriladi!"
        } else {
            "⏱️ Eslatma $delayMinutes daqiqadan so'ng qayta yuboriladi."
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        finishAndRemoveTask()
    }

    private fun handleAnswerDismiss(taskTitle: String, endTime: String) {
        stopAlarmAudioAndVibration()
        prefs.markAlarmAnswered(taskTitle, endTime)

        val isoTimestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())
        CoroutineScope(Dispatchers.IO).launch {
            FirestoreClient.patchAlarmAnswer(
                answer = "not_done",
                taskTitle = taskTitle,
                isoTimestamp = isoTimestamp
            )
        }

        Toast.makeText(this, "Vazifa eslatmasi yakunlandi.", Toast.LENGTH_SHORT).show()
        finishAndRemoveTask()
    }

    override fun onDestroy() {
        stopAlarmAudioAndVibration()
        super.onDestroy()
    }
}

@Composable
fun AlarmScreen(
    title: String,
    category: String,
    endTime: String,
    note: String,
    isMuted: Boolean = false,
    isAtSchool: Boolean = false,
    onDone: () -> Unit,
    onStopRingtone: () -> Unit,
    onSnooze: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var showSnoozeSelection by remember { mutableStateOf(false) }
    var selectedMinutes by remember { mutableIntStateOf(10) }
    var isSubmitting by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alarmScale"
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HabitDarkBg, Color(0xFF0C0E1B))
                )
            ),
        color = Color.Transparent
    ) {
        AnimatedContent(
            targetState = showSnoozeSelection,
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(200)) },
            label = "AlarmViewSwitcher"
        ) { inSnoozeMode ->
            if (!inSnoozeMode) {
                // --- PRIMARY ALARM VIEW ---
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Pulse header
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .scale(scale)
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(HabitGold.copy(alpha = 0.15f))
                                .border(2.dp, HabitGold, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Alarm,
                                contentDescription = "Alarm signali",
                                tint = HabitGold,
                                modifier = Modifier.size(56.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "VAQTI TUGADI!",
                            style = MaterialTheme.typography.labelLarge.copy(
                                letterSpacing = 3.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = HabitGold
                        )

                        if (endTime.isNotBlank()) {
                            Text(
                                text = "Rejalashtirilgan yakun: $endTime",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        if (isMuted) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFEF5350).copy(alpha = 0.18f),
                                border = BorderStroke(1.dp, Color(0xFFEF5350).copy(alpha = 0.45f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(if (isAtSchool) "🏫" else "🔕", fontSize = 13.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isAtSchool) "Maktab hududida: ovoz o'chirilgan (tebranishda)" else "Ovozsiz rejim: faqat tebranish",
                                        color = Color(0xFFFFCDD2),
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Task information card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (category.isNotBlank()) {
                                Text(
                                    text = category.uppercase(Locale.getDefault()),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = HabitGold,
                                    modifier = Modifier
                                        .background(HabitGold.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )

                            if (note.isNotBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "“$note”",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.8f),
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Ushbu vazifani bajardingizmi?",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                                color = HabitGold.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Action Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 1. HA / BAJARDIM BUTTON
                        Button(
                            onClick = {
                                if (!isSubmitting) {
                                    isSubmitting = true
                                    onDone()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .testTag("alarm_btn_done"),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HabitGold,
                                contentColor = HabitDarkBg
                            )
                        ) {
                            if (isSubmitting) {
                                CircularProgressIndicator(
                                    color = HabitDarkBg,
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = HabitDarkBg,
                                        modifier = Modifier.size(26.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "✅ Ha, bajardim!",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }

                        // 2. YO'Q / BAJARMADIM BUTTON (Opens Snooze dialog)
                        OutlinedButton(
                            onClick = {
                                onStopRingtone() // Stop audio so user can comfortably choose snooze duration
                                showSnoozeSelection = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .testTag("alarm_btn_not_done"),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, HabitError),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = HabitError
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HourglassTop,
                                    contentDescription = null,
                                    tint = HabitError,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "⏳ Yo'q, hali bajarmadim",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                // --- SNOOZE / KECHIKTIRISH SELECTION VIEW ---
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(HabitGold.copy(alpha = 0.15f))
                                .border(2.dp, HabitGold, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassTop,
                                contentDescription = "Kechiktirish",
                                tint = HabitGold,
                                modifier = Modifier.size(44.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Qancha vaqtdan keyin qayta so'raylik?",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Vazifani tugatish uchun qancha vaqt kerak? Shu vaqt o'tgach yana to'liq ekranli so'rov yuboriladi.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.75f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Quick duration preset chips
                        val presets = listOf(5, 10, 15, 20, 30, 45, 60)
                        Text(
                            text = "Tezkor tanlov:",
                            style = MaterialTheme.typography.labelMedium,
                            color = HabitGold,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            presets.take(4).forEach { min ->
                                val isSelected = selectedMinutes == min
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isSelected) HabitGold else HabitCardBg
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) HabitGold else Color.White.copy(alpha = 0.15f),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .clickable { selectedMinutes = min }
                                ) {
                                    Text(
                                        text = "${min}m",
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) HabitDarkBg else Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            presets.drop(4).forEach { min ->
                                val isSelected = selectedMinutes == min
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isSelected) HabitGold else HabitCardBg
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) HabitGold else Color.White.copy(alpha = 0.15f),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .clickable { selectedMinutes = min }
                                ) {
                                    Text(
                                        text = if (min == 60) "1 soat" else "${min}m",
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) HabitDarkBg else Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Stepper row for custom minutes
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = HabitCardBg)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Aniq vaqt:",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            if (selectedMinutes > 5) selectedMinutes -= 5
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Kamaytirish", tint = Color.White)
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = "$selectedMinutes daqiqa",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = HabitGold
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    IconButton(
                                        onClick = {
                                            if (selectedMinutes < 180) selectedMinutes += 5
                                        },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Ko'paytirish", tint = Color.White)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Projected reminder time calculation
                        val cal = Calendar.getInstance().apply {
                            add(Calendar.MINUTE, selectedMinutes)
                        }
                        val reminderTargetStr = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(HabitGold.copy(alpha = 0.1f))
                                .border(1.dp, HabitGold.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = HabitGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Qayta so'rash vaqti: $reminderTargetStr ($selectedMinutes daqiqadan so'ng)",
                                color = HabitGold,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Buttons for snooze confirmation or dismissing
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                if (!isSubmitting) {
                                    isSubmitting = true
                                    onSnooze(selectedMinutes)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .testTag("alarm_btn_confirm_snooze"),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HabitGold,
                                contentColor = HabitDarkBg
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = HabitDarkBg,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "⏰ Eslatmani o'rnatish ($selectedMinutes daqiqa)",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    fontSize = 16.sp
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                if (!isSubmitting) {
                                    isSubmitting = true
                                    onDismiss()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("alarm_btn_skip_snooze"),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White.copy(alpha = 0.8f)
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "❌ Qayta so'ralmasin (Vazifani yakunlash)",
                                    fontSize = 14.sp
                                )
                            }
                        }

                        TextButton(
                            onClick = { showSnoozeSelection = false },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = HabitGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Orqaga (Ha, bajardim deb belgilash)",
                                    color = HabitGold,
                                    fontSize = 13.5.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
