package com.example.ui.screen

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScriptPuzzleSentence
import com.example.data.model.ScriptSampleAudio
import com.example.data.model.ScriptSampleRepository
import com.example.ui.theme.HabitAmber
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardElevated
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkMuted
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitRose
import com.example.ui.theme.HabitSage
import java.util.Locale
import kotlin.math.sin
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 🎮 CYBER EAR: AUDIO ARENA & LISTENING GAME
 *
 * Universal, yuqori dinamikali, barcha yoshdagilar (qizlar va yigitlar, talabalar va kattalar)
 * uchun qiziqarli audio o'yin platformasi.
 *
 * 4 TA JANG REJIMI:
 * 1. ⚡ "Audio Beat Rush" (To'lqin Poygasi & Omonim Tuzoqlari - Fever Mode bilan)
 * 2. 🕵️‍♂️ "Audio Detective" (Maxfiy Shifr, Dalillar Doskasi & Detektiv Tergov)
 * 3. 🎯 "Heardle: 1-Soniya Quloq Dueli" (0.8s, 2s, 4s audiodan topish)
 * 4. 🎙️ "Shadowing Echo Arena" (Ovozli Aks-Sado & Intonatsiya Jangi)
 */

object AudioHomophoneEngine {
    // English tricky sound-alikes / traps (omonim va o'xshash tovushlar)
    val phoneticTraps = mapOf(
        "peace" to listOf("piece", "peas"),
        "piece" to listOf("peace"),
        "weather" to listOf("whether", "wether"),
        "whether" to listOf("weather"),
        "their" to listOf("there", "they're"),
        "there" to listOf("their", "they're"),
        "they're" to listOf("there", "their"),
        "right" to listOf("write", "rite"),
        "write" to listOf("right"),
        "wait" to listOf("weight"),
        "weight" to listOf("wait"),
        "knew" to listOf("new"),
        "new" to listOf("knew"),
        "hear" to listOf("here"),
        "here" to listOf("hear"),
        "break" to listOf("brake"),
        "brake" to listOf("break"),
        "meet" to listOf("meat"),
        "meat" to listOf("meet"),
        "buy" to listOf("by", "bye"),
        "by" to listOf("buy", "bye"),
        "see" to listOf("sea"),
        "sea" to listOf("see"),
        "son" to listOf("sun"),
        "sun" to listOf("son"),
        "week" to listOf("weak"),
        "weak" to listOf("week"),
        "quiet" to listOf("quite"),
        "quite" to listOf("quiet"),
        "accept" to listOf("except"),
        "except" to listOf("accept"),
        "affect" to listOf("effect"),
        "effect" to listOf("affect"),
        "lose" to listOf("loose"),
        "loose" to listOf("lose"),
        "passed" to listOf("past"),
        "past" to listOf("passed"),
        "hole" to listOf("whole"),
        "whole" to listOf("hole")
    )

    fun getDistractorsForWord(word: String): List<String> {
        val clean = word.lowercase().replace(Regex("[^a-z]"), "")
        val homophones = phoneticTraps[clean]
        if (!homophones.isNullOrEmpty()) {
            return homophones.take(2)
        }
        // General smart English phonetic distractors
        return when {
            clean.endsWith("ing") -> listOf(clean.removeSuffix("ing") + "ed", clean + "s")
            clean.endsWith("ed") -> listOf(clean.removeSuffix("ed") + "ing", clean + "s")
            clean.length > 5 -> listOf(clean.dropLast(1) + "y", clean + "ly")
            else -> listOf(clean.reversed(), clean + "ed")
        }
    }
}

data class DetectiveDossier(
    val caseNumber: String,
    val title: String,
    val location: String,
    val difficulty: String,
    val mysteryPlot: String,
    val sampleId: String,
    val clueSummary: String
)

object DetectiveCaseRepository {
    val cases = listOf(
        DetectiveDossier(
            caseNumber = "#101",
            title = "Heathrow Tungi Parvozi",
            location = "London Heathrow Terminal 5",
            difficulty = "B1 O'rta",
            mysteryPlot = "Istanbulga uchuvchi reys kutilmaganda 45 daqiqaga kechiktirildi. Diplomatning yuklarida shubhali buyumlar borligi aytilmoqda...",
            sampleId = "sample_airport_travel",
            clueSummary = "Uchish chiptasi, 100ml taqiqlangan suyuqlik va transfer yo'lovchilari siri."
        ),
        DetectiveDossier(
            caseNumber = "#102",
            title = "Silicon Valley Shifrli Labaratoriyasi",
            location = "Kaliforniya AI Research Center",
            difficulty = "B2-C1 Murakkab",
            mysteryPlot = "Kompaniya sun'iy intellektning yangi universal algoritm kodini e'lon qilish arafasida. Tungi ma'ruzada maxfiy kalit so'zlar yashiringan...",
            sampleId = "sample_tech_future",
            clueSummary = "Dinamik interaktiv suhbatlar, shaxsiy intizom va xavfsiz neyrotarmoqlar."
        ),
        DetectiveDossier(
            caseNumber = "#103",
            title = "Manhattan Ish Suhbatidagi Fitna",
            location = "Nyu-York 5th Avenue",
            difficulty = "B2 Yuqori",
            mysteryPlot = "Boshqaruvchi direktor yangi nomzodning jamoaviy ishlash qobiliyatini sinovdan o'tkazmoqda. Suhbatdagi har bir so'z korporativ taqdirni hal qiladi...",
            sampleId = "sample_job_interview",
            clueSummary = "Innovatsion mobil platforma, muddatlar va yuqori mas'uliyat siri."
        ),
        DetectiveDossier(
            caseNumber = "#104",
            title = "Oksford Tibbiy Tajribasi",
            location = "Oksford Neyrologiya Instituti",
            difficulty = "B1 O'rta",
            mysteryPlot = "Miya faoliyatini va xotirani tezkor tiklovchi tajriba o'tkazilmoqda. Uyqudan oldingi telefon ekranlarining zarari fosh bo'ldi...",
            sampleId = "sample_healthy_habits",
            clueSummary = "Melatonin darajasi, qog'oz kitoblar va chuqur dam olish kodi."
        )
    )
}

@Composable
fun AudioGameArenaView(
    puzzleSentences: List<ScriptPuzzleSentence>,
    currentSampleId: String?,
    audioTitle: String,
    ttsEngine: TextToSpeech?,
    isTtsReady: Boolean,
    playbackSpeed: Float,
    onSentenceCompleted: (ScriptPuzzleSentence) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Main Game Modes:
    // 0: ⚡ Audio Beat Rush (Refleks & Omonim Tuzoqlari)
    // 1: 🕵️‍♂️ Audio Detective (Maxfiy Shifr & Dalillar Doskasi)
    // 2: 🎯 Heardle: 1-Soniya Quloq Dueli
    // 3: 🎙️ Shadowing Echo Arena (Ovozli Aks-Sado)
    var selectedGameMode by remember { mutableIntStateOf(0) }

    // Player Progression Stats
    var playerXp by remember { mutableIntStateOf(420) }
    var streakCombo by remember { mutableIntStateOf(1) }
    var maxStreak by remember { mutableIntStateOf(1) }
    var shieldHearts by remember { mutableIntStateOf(3) } // Max 3
    var feverMeter by remember { mutableFloatStateOf(0.35f) } // 0.0 to 1.0
    var isFeverActive by remember { mutableStateOf(false) }

    // Sentences tracker
    var activeSentenceIdx by remember { mutableIntStateOf(0) }
    val totalSentences = puzzleSentences.size.coerceAtLeast(1)
    val currentSentence = puzzleSentences.getOrNull(activeSentenceIdx)

    // Snippet Player
    var isAudioPlaying by remember { mutableStateOf(false) }
    var soundWavePhase by remember { mutableFloatStateOf(0f) }

    // Animation transition
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseGlow"
    )

    // Wave animation ticker when playing
    LaunchedEffect(isAudioPlaying) {
        if (isAudioPlaying) {
            while (isAudioPlaying) {
                soundWavePhase += 0.25f
                delay(30)
            }
        }
    }

    // Play helper with TTS
    fun playCurrentSnippet(speedFactor: Float = playbackSpeed, customText: String? = null) {
        val sentenceText = customText ?: currentSentence?.text ?: return
        isAudioPlaying = true
        val tts = ttsEngine
        if (tts != null && isTtsReady) {
            tts.setSpeechRate(speedFactor)
            tts.speak(sentenceText, TextToSpeech.QUEUE_FLUSH, null, "game_snippet_$activeSentenceIdx")
            coroutineScope.launch {
                val wordsCount = sentenceText.split(" ").size
                val estimatedDurationMs = ((wordsCount * 620L) / speedFactor.coerceAtLeast(0.5f)).toLong()
                delay(estimatedDurationMs)
                isAudioPlaying = false
            }
        } else {
            coroutineScope.launch {
                delay(2500)
                isAudioPlaying = false
            }
        }
    }

    // Daily Loot Box Modal
    var showLootBoxDialog by remember { mutableStateOf(false) }
    var lootRewardText by remember { mutableStateOf("") }

    // Fever Meter monitor
    LaunchedEffect(feverMeter) {
        if (feverMeter >= 1.0f && !isFeverActive) {
            isFeverActive = true
            Toast.makeText(context, "🔥 FEVER OVERDRIVE! 3x XP Faollashdi!", Toast.LENGTH_SHORT).show()
            delay(12000)
            isFeverActive = false
            feverMeter = 0.2f
        }
    }

    // Rank Name Calculator
    val rankTitle = when {
        playerXp >= 1500 -> "👑 Ovoz Grossmeysteri"
        playerXp >= 900 -> "🦅 Burgut Quloq (Master)"
        playerXp >= 500 -> "⚡ Kiber Shifrlovchi"
        playerXp >= 250 -> "🕵️ Signal Skauti"
        else -> "🎧 Quloq Kursanti"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HabitDarkBg)
    ) {
        // --- 1. TOP HUD (Player Level, Hearts, Fever Bar & Loot Box) ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isFeverActive) Color(0xFF1E1500) else HabitCardBg
            ),
            border = BorderStroke(
                1.5.dp,
                if (isFeverActive) Color(0xFFF59E0B) else HabitGold.copy(alpha = 0.4f)
            )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rank & Badge
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    if (isFeverActive) Color(0xFFF59E0B).copy(alpha = 0.2f) else HabitGold.copy(alpha = 0.15f),
                                    CircleShape
                                )
                                .border(
                                    1.dp,
                                    if (isFeverActive) Color(0xFFF59E0B) else HabitGold,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(if (isFeverActive) "🔥" else "🎧", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = rankTitle,
                                color = if (isFeverActive) Color(0xFFF59E0B) else HabitGold,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "⭐ $playerXp XP to'plandi",
                                color = HabitInkSoft,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Shields (Hearts) & Loot Chest
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // 3 Energy Shields
                        Row {
                            repeat(3) { index ->
                                val hasShield = index < shieldHearts
                                Text(
                                    text = if (hasShield) "🛡️" else "🖤",
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Daily Mystery Chest Button
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFA855F7).copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color(0xFFA855F7)),
                            modifier = Modifier.clickable {
                                lootRewardText = "+150 XP va 💎 1 ta Zaxira Qalqon yutib oldingiz!"
                                playerXp += 150
                                shieldHearts = 3
                                showLootBoxDialog = true
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🎁 Sandiq", color = Color(0xFFA855F7), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // FEVER METER BAR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isFeverActive) "🔥 FEVER MODE 3X AKTIV!" else "⚡ Fever Meter:",
                            color = if (isFeverActive) Color(0xFFF59E0B) else HabitInkSoft,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = if (streakCombo > 1) "🔥 x$streakCombo Combo!" else "Combo: 1x",
                        color = if (streakCombo >= 3) Color(0xFFEF4444) else HabitGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { feverMeter.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (isFeverActive) Color(0xFFF59E0B) else Color(0xFF38BDF8),
                    trackColor = HabitDarkBg
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- 2. GAME MODE SELECTOR TABS ---
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val modes = listOf(
                Pair(0, "🧩 So'z Jumboqlari"),
                Pair(1, "⚡ Beat Rush (Refleks)"),
                Pair(2, "🎯 Heardle (1-Soniya)"),
                Pair(3, "🕵️ Maxfiy Detektiv"),
                Pair(4, "🎙️ Aks-Sado (Echo)")
            )
            items(modes) { (modeId, label) ->
                val isSelected = selectedGameMode == modeId
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedGameMode = modeId },
                    label = {
                        Text(
                            text = label,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = HabitGold,
                        selectedLabelColor = HabitDarkBg,
                        containerColor = HabitCardBg,
                        labelColor = HabitInk
                    ),
                    border = BorderStroke(1.dp, if (isSelected) HabitGold else HabitGold.copy(alpha = 0.25f))
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- 3. LIVE AUDIO CONTROLS & CLEAN WAVEFORM ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = HabitCardBg),
            border = BorderStroke(1.dp, if (isAudioPlaying) HabitGold else HabitGold.copy(alpha = 0.25f))
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Track header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🎧", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = audioTitle,
                            color = HabitInk,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = HabitGold.copy(alpha = 0.15f),
                        modifier = Modifier.padding(start = 6.dp)
                    ) {
                        Text(
                            text = "Gap: ${activeSentenceIdx + 1}/$totalSentences",
                            color = HabitGold,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                // Interactive Radar Waveform (Pure visual - NO text overlay on top of bars!)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val midY = canvasHeight / 2f
                        val barCount = 36
                        val barWidth = canvasWidth / (barCount * 1.6f)

                        for (i in 0 until barCount) {
                            val x = i * (canvasWidth / barCount) + barWidth / 2f
                            val factor = if (isAudioPlaying) {
                                (sin(soundWavePhase + i * 0.45f) * 0.5f + 0.5f).toFloat()
                            } else {
                                0.18f + 0.08f * (i % 4)
                            }
                            val barHeight = (canvasHeight * 0.85f * factor).coerceAtLeast(6f)
                            val top = midY - barHeight / 2f

                            val barColor = when {
                                isFeverActive -> Color(0xFFF59E0B)
                                i % 2 == 0 -> HabitGold
                                else -> Color(0xFF38BDF8)
                            }

                            drawRoundRect(
                                color = barColor,
                                topLeft = Offset(x, top),
                                size = Size(barWidth, barHeight),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Play / Speed Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { playCurrentSnippet() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isAudioPlaying) HabitSage else HabitGold
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1.3f)
                    ) {
                        Icon(
                            imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = HabitDarkBg,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isAudioPlaying) "Tinglanmoqda..." else "Gapni Ijro Etish 🔊",
                            color = HabitDarkBg,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = { playCurrentSnippet(speedFactor = 0.75f) },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFF38BDF8))
                    ) {
                        Text("🐢 0.75x", color = Color(0xFF38BDF8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(onClick = { playCurrentSnippet() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Qayta", tint = HabitGold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- 4. GAME CONTENT SWITCHER ---
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (selectedGameMode) {
                0 -> {
                    // 🧩 MODE 0: AUDIO WORD PUZZLE (UNIVERSAL SO'Z JUMBOQLARI)
                    AudioWordPuzzleGameView(
                        currentSentence = currentSentence,
                        sentenceIndex = activeSentenceIdx,
                        totalSentences = totalSentences,
                        onWordCorrect = {
                            streakCombo++
                            if (streakCombo > maxStreak) maxStreak = streakCombo
                            val gainedXp = if (isFeverActive) 30 else 10
                            playerXp += gainedXp
                            feverMeter = (feverMeter + 0.12f).coerceAtMost(1.0f)
                        },
                        onWordMistake = {
                            streakCombo = 1
                            feverMeter = (feverMeter - 0.15f).coerceAtLeast(0f)
                            if (shieldHearts > 0) shieldHearts--
                            Toast.makeText(context, "So'z ketma-ketligi xato! Qaytadan urinib ko'ring", Toast.LENGTH_SHORT).show()
                        },
                        onSentenceCleared = {
                            val gainedXp = if (isFeverActive) 60 else 25
                            playerXp += gainedXp
                            if (currentSentence != null) onSentenceCompleted(currentSentence)
                            Toast.makeText(context, "🎉 Ajoyib! Gap to'g'ri yig'ildi! +$gainedXp XP", Toast.LENGTH_SHORT).show()
                            if (activeSentenceIdx < totalSentences - 1) {
                                activeSentenceIdx++
                                playCurrentSnippet()
                            } else {
                                Toast.makeText(context, "🏆 Barcha gaplar muvaffaqiyatli yakunlandi!", Toast.LENGTH_LONG).show()
                            }
                        }
                    )
                }
                1 -> {
                    // ⚡ MODE 1: AUDIO BEAT RUSH (RITM & OMONIM TUZOQLARI)
                    AudioBeatRushView(
                        currentSentence = currentSentence,
                        onWordSuccess = {
                            streakCombo++
                            if (streakCombo > maxStreak) maxStreak = streakCombo
                            val gainedXp = if (isFeverActive) 45 else 15
                            playerXp += gainedXp
                            feverMeter = (feverMeter + 0.15f).coerceAtMost(1.0f)
                            Toast.makeText(context, "🎯 To'g'ri! +$gainedXp XP", Toast.LENGTH_SHORT).show()
                        },
                        onWordTrapFailed = { trapWord ->
                            streakCombo = 1
                            feverMeter = (feverMeter - 0.2f).coerceAtLeast(0f)
                            if (shieldHearts > 0) shieldHearts--
                            Toast.makeText(context, "❌ Omonim tuzoqqa tushdingiz! ($trapWord)", Toast.LENGTH_SHORT).show()
                        },
                        onSentenceCleared = {
                            if (currentSentence != null) onSentenceCompleted(currentSentence)
                            if (activeSentenceIdx < totalSentences - 1) {
                                activeSentenceIdx++
                                playCurrentSnippet()
                            } else {
                                Toast.makeText(context, "🏆 Barcha gaplar muvaffaqiyatli yakunlandi!", Toast.LENGTH_LONG).show()
                            }
                        }
                    )
                }
                2 -> {
                    // 🎯 MODE 2: HEARDLE (1-SONIYA QULOQ DUELI)
                    AudioHeardleView(
                        currentSentence = currentSentence,
                        onSnippetPlay = { durationFactor ->
                            playCurrentSnippet(speedFactor = 1.0f)
                        },
                        onHeardleSolved = { stars, xpEarned ->
                            playerXp += xpEarned
                            Toast.makeText(context, "🌟 $stars Yulduz! +$xpEarned XP", Toast.LENGTH_SHORT).show()
                            if (activeSentenceIdx < totalSentences - 1) {
                                activeSentenceIdx++
                            }
                        }
                    )
                }
                3 -> {
                    // 🕵️‍♂️ MODE 3: AUDIO DETECTIVE (MAXFIY SHIFR & DALILLAR DOSKASI)
                    AudioDetectiveView(
                        currentSentence = currentSentence,
                        sentenceIndex = activeSentenceIdx,
                        totalSentences = totalSentences,
                        onCaseClueSolved = { clueXp ->
                            playerXp += clueXp
                            feverMeter = (feverMeter + 0.25f).coerceAtMost(1.0f)
                            streakCombo++
                            if (activeSentenceIdx < totalSentences - 1) {
                                activeSentenceIdx++
                                playCurrentSnippet()
                            }
                        }
                    )
                }
                4 -> {
                    // 🎙️ MODE 4: SHADOWING ECHO ARENA
                    AudioShadowingEchoView(
                        currentSentence = currentSentence,
                        onEchoSuccess = { score ->
                            val earned = (score / 2)
                            playerXp += earned
                            feverMeter = (feverMeter + 0.2f).coerceAtMost(1.0f)
                            streakCombo++
                            Toast.makeText(context, "🎯 Aks-Sado Qabul qilindi: $score%! +$earned XP", Toast.LENGTH_SHORT).show()
                            if (activeSentenceIdx < totalSentences - 1) {
                                activeSentenceIdx++
                            }
                        }
                    )
                }
            }
        }
    }

    // Daily Loot Box Reward Dialog
    if (showLootBoxDialog) {
        AlertDialog(
            onDismissRequest = { showLootBoxDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🎁 Kundalik Ovoz Sandig'i", fontWeight = FontWeight.Bold, color = HabitGold)
                }
            },
            text = {
                Column {
                    Text(lootRewardText, color = HabitInk, fontSize = 13.5.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("💡 Har kuni kirib audiolarni tinglang va yangi unvonlarni qo'lga kiriting!", color = HabitInkSoft, fontSize = 11.5.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showLootBoxDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Qabul Qilish ⚡", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = HabitCardBg
        )
    }
}

/**
 * 🧩 MODE 0: AUDIO WORD PUZZLE (UNIVERSAL SO'Z JUMBOQLARI)
 * Barcha yoshdagilar uchun qiziqarli, Duolingo uslubidagi universal so'z yig'ish o'yini.
 * O'quvchi audioni eshitadi va aralashtirib berilgan so'z tugmachalarini to'g'ri tartibda teradi.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AudioWordPuzzleGameView(
    currentSentence: ScriptPuzzleSentence?,
    sentenceIndex: Int,
    totalSentences: Int,
    onWordCorrect: () -> Unit,
    onWordMistake: () -> Unit,
    onSentenceCleared: () -> Unit
) {
    val targetWords = remember(currentSentence) { currentSentence?.words ?: emptyList() }
    val placedWords = remember { mutableStateListOf<String>() }

    // Shuffled pool of words with unique indices
    val availableWordBank = remember(currentSentence) {
        val list = mutableStateListOf<Pair<Int, String>>()
        targetWords.forEachIndexed { idx, word -> list.add(Pair(idx, word)) }
        list.shuffle()
        list
    }

    LaunchedEffect(currentSentence) {
        placedWords.clear()
        availableWordBank.clear()
        targetWords.forEachIndexed { idx, word -> availableWordBank.add(Pair(idx, word)) }
        availableWordBank.shuffle()
    }

    val isSentenceComplete = placedWords.size == targetWords.size && targetWords.isNotEmpty()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, if (isSentenceComplete) HabitSage else HabitGold.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Header progress
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🧩", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "So'z Jumboqlari (Puzzle)",
                                color = HabitGold,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HabitGold.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "Gap: ${sentenceIndex + 1} / $totalSentences",
                                color = HabitGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "🎧 Audioni eshiting va so'zlarni ketma-ket bosing:",
                        color = HabitInkSoft,
                        fontSize = 11.5.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 1. Target Sentence Construction Area (Drop Zone)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HabitDarkBg,
                        border = BorderStroke(1.dp, if (isSentenceComplete) HabitSage else HabitInkSoft.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 72.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            if (placedWords.isEmpty()) {
                                Text(
                                    text = "Pastdagi so'zlarni bosib bu yerga yig'ing...",
                                    color = HabitInkSoft.copy(alpha = 0.6f),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)
                                )
                            } else {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    for ((index, word) in placedWords.withIndex()) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isSentenceComplete) HabitSage else HabitGold,
                                            modifier = Modifier.clickable {
                                                // Clicking placed word removes it back to bank
                                                placedWords.removeAt(index)
                                                availableWordBank.add(Pair(index, word))
                                            }
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            ) {
                                                Text(
                                                    text = word,
                                                    color = HabitDarkBg,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2. Available Word Bank (Shuffled Buttons)
                    Text(
                        text = "Mavjud so'z bloklari:",
                        color = HabitInkSoft,
                        fontSize = 11.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (pair in availableWordBank.toList()) {
                            val (originalIdx, word) = pair
                            OutlinedButton(
                                onClick = {
                                    val nextRequiredIdx = placedWords.size
                                    val expectedWord = targetWords.getOrNull(nextRequiredIdx)

                                    if (expectedWord != null && word.equals(expectedWord, ignoreCase = true)) {
                                        placedWords.add(word)
                                        availableWordBank.remove(pair)
                                        onWordCorrect()

                                        if (placedWords.size == targetWords.size) {
                                            onSentenceCleared()
                                        }
                                    } else {
                                        onWordMistake()
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.2.dp, HabitGold.copy(alpha = 0.7f)),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = HabitDarkBg),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = word,
                                    color = HabitGold,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Action Helpers (Hint & Reset)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                placedWords.clear()
                                availableWordBank.clear()
                                targetWords.forEachIndexed { idx, word -> availableWordBank.add(Pair(idx, word)) }
                                availableWordBank.shuffle()
                            }
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, tint = HabitInkSoft, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Qayta boshlash", color = HabitInkSoft, fontSize = 11.5.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                val nextRequiredIdx = placedWords.size
                                val nextWord = targetWords.getOrNull(nextRequiredIdx)
                                if (nextWord != null) {
                                    val match = availableWordBank.firstOrNull { it.second.equals(nextWord, ignoreCase = true) }
                                    if (match != null) {
                                        placedWords.add(match.second)
                                        availableWordBank.remove(match)
                                        onWordCorrect()
                                        if (placedWords.size == targetWords.size) {
                                            onSentenceCleared()
                                        }
                                    }
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color(0xFF38BDF8)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("💡 Keyingi so'zni ochish", color = Color(0xFF38BDF8), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

/**
 * ⚡ MODE 1: AUDIO BEAT RUSH
 * O'quvchi eshitgan gapdagi so'zlarni va ularning ayyor omonim tuzoqlarini ajratib,
 * tezkor refleks bilan bosadi.
 */
@Composable
fun AudioBeatRushView(
    currentSentence: ScriptPuzzleSentence?,
    onWordSuccess: () -> Unit,
    onWordTrapFailed: (String) -> Unit,
    onSentenceCleared: () -> Unit
) {
    val words = remember(currentSentence) { currentSentence?.words ?: emptyList() }
    val solvedWords = remember { mutableStateListOf<String>() }

    // Dynamic current target word
    val targetIndex = solvedWords.size
    val currentTargetWord = words.getOrNull(targetIndex) ?: ""

    // Options generator: Target word + Tricky homophones / distractors
    val currentOptions = remember(currentTargetWord) {
        if (currentTargetWord.isBlank()) emptyList()
        else {
            val traps = AudioHomophoneEngine.getDistractorsForWord(currentTargetWord)
            (listOf(currentTargetWord) + traps).distinct().shuffled()
        }
    }

    LaunchedEffect(currentSentence) {
        solvedWords.clear()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚡ Refleks & Omonim Tuzog'i",
                            color = HabitGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${solvedWords.size} / ${words.size} so'z",
                            color = HabitInkSoft,
                            fontSize = 11.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Reconstructed sentence display
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = HabitDarkBg,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Tuzilayotgan gap:",
                                color = HabitInkSoft,
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val reconstructed = words.mapIndexed { idx, w ->
                                if (idx < solvedWords.size) solvedWords[idx]
                                else if (idx == solvedWords.size) "👉 [ ... ]"
                                else "_____"
                            }.joinToString(" ")

                            Text(
                                text = reconstructed,
                                color = HabitInk,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (currentTargetWord.isNotBlank()) {
                        Text(
                            text = "🎯 Eshitilgan keyingi so'zni to'g'ri tanlang (Omonim tuzoqlardan ehtiyot bo'ling!):",
                            color = HabitInkSoft,
                            fontSize = 11.5.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Big interactive touch buttons with sound-alike traps
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            currentOptions.forEach { option ->
                                Button(
                                    onClick = {
                                        if (option.equals(currentTargetWord, ignoreCase = true)) {
                                            solvedWords.add(currentTargetWord)
                                            onWordSuccess()
                                            if (solvedWords.size >= words.size) {
                                                onSentenceCleared()
                                            }
                                        } else {
                                            onWordTrapFailed(option)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = HabitDarkBg),
                                    border = BorderStroke(1.5.dp, HabitGold),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(54.dp)
                                ) {
                                    Text(
                                        text = option,
                                        color = HabitGold,
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    } else {
                        // Sentence finished
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = HabitSage.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, HabitSage),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🎉", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text("Gap to'liq yechildi!", color = HabitSage, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Keyingi gapga o'ting yoki qayta mustahkamlang.", color = HabitInkSoft, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 🕵️‍♂️ MODE 1: AUDIO DETECTIVE
 * O'quvchi maxfiy audio signalni tergov qiladi, shifrlangan dalillarni ochadi.
 */
@Composable
fun AudioDetectiveView(
    currentSentence: ScriptPuzzleSentence?,
    sentenceIndex: Int,
    totalSentences: Int,
    onCaseClueSolved: (Int) -> Unit
) {
    val activeCase = DetectiveCaseRepository.cases.getOrElse(sentenceIndex % DetectiveCaseRepository.cases.size) {
        DetectiveCaseRepository.cases.first()
    }

    val words = currentSentence?.words ?: emptyList()
    val targetSecretWord = words.getOrNull(words.size / 2) ?: "key"

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isDossierUnlocked by remember { mutableStateOf(false) }

    val detectiveOptions = remember(targetSecretWord) {
        listOf(
            targetSecretWord,
            words.getOrNull(0) ?: "secret",
            "evidence",
            "protocol"
        ).distinct().shuffled()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Case File Dossier Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, Color(0xFFEAB308))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFEAB308).copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color(0xFFEAB308))
                        ) {
                            Text(
                                text = "TOP SECRET // ${activeCase.caseNumber}",
                                color = Color(0xFFEAB308),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = "📍 ${activeCase.location}",
                            color = HabitInkSoft,
                            fontSize = 10.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "🕵️ Maxfiy Ish: ${activeCase.title}",
                        color = HabitInk,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = activeCase.mysteryPlot,
                        color = HabitInkSoft,
                        fontSize = 11.5.sp,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Encrypted Audio Intercept Line
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = HabitDarkBg,
                        border = BorderStroke(1.dp, Color(0xFFEAB308).copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("📻 Tutilgan audio xabaridagi shifr:", color = Color(0xFFEAB308), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))

                            val maskedLine = words.joinToString(" ") { w ->
                                if (w.equals(targetSecretWord, ignoreCase = true)) {
                                    if (isDossierUnlocked) "✅ [$w]" else "🔒 [ ??? SHIFR ??? ]"
                                } else w
                            }
                            Text(
                                text = maskedLine,
                                color = HabitInk,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("🔍 Yashirin kalit so'zni toping va dalilni oching:", color = HabitInkSoft, fontSize = 11.5.sp)
                    Spacer(modifier = Modifier.height(6.dp))

                    detectiveOptions.chunked(2).forEach { rowOpts ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowOpts.forEach { opt ->
                                OutlinedButton(
                                    onClick = {
                                        if (opt.equals(targetSecretWord, ignoreCase = true)) {
                                            isDossierUnlocked = true
                                            onCaseClueSolved(50)
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, if (isDossierUnlocked && opt == targetSecretWord) HabitSage else Color(0xFFEAB308)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = opt,
                                        color = if (isDossierUnlocked && opt == targetSecretWord) HabitSage else Color(0xFFEAB308),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    if (isDossierUnlocked) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HabitSage.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, HabitSage),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("✅ DALIL TASDIQLANDI // CLUE VERIFIED", color = HabitSage, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                                Text("Dalil xulosasi: ${activeCase.clueSummary}", color = HabitInk, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 🎯 MODE 2: HEARDLE (1-SONIYA QULOQ DUELI)
 * Butun dunyoda mashhur bo'lgan Heardle o'yini: atigi 0.8 soniya eshitib topish.
 */
@Composable
fun AudioHeardleView(
    currentSentence: ScriptPuzzleSentence?,
    onSnippetPlay: (Float) -> Unit,
    onHeardleSolved: (stars: Int, xp: Int) -> Unit
) {
    var heardleAttempt by remember { mutableIntStateOf(1) } // 1, 2, 3
    var isSolved by remember { mutableStateOf(false) }

    val fullText = currentSentence?.text ?: "Good morning everyone!"
    val words = currentSentence?.words ?: listOf("Good", "morning")
    val correctKey = words.getOrNull(words.size / 2) ?: words.firstOrNull() ?: "routine"

    val choices = remember(correctKey) {
        listOf(
            correctKey,
            "conference",
            "opportunity",
            "environment"
        ).distinct().shuffled()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, Color(0xFFEC4899))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🎯", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "1-Soniya Heardle Dueli",
                                color = Color(0xFFEC4899),
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "Urinish: $heardleAttempt / 3",
                            color = HabitInkSoft,
                            fontSize = 11.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Atigi 0.8s audioni eshitib gapning kalit so'zini topa olasizmi? Kamroq urinishda topsangiz, ko'proq yulduz va XP olasiz!",
                        color = HabitInkSoft,
                        fontSize = 11.5.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Duration Snippet Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onSnippetPlay(0.8f) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC4899)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("⚡ 0.8s Tinglash", color = HabitDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                if (heardleAttempt < 3) heardleAttempt++
                                onSnippetPlay(1.5f)
                            },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFFEC4899)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+1.5s Kengaytirish", color = Color(0xFFEC4899), fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("Variantlardan to'g'ri kalit so'zni tanlang:", color = HabitInk, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))

                    choices.chunked(2).forEach { rowOpts ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowOpts.forEach { choice ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = HabitDarkBg,
                                    border = BorderStroke(1.dp, Color(0xFFEC4899).copy(alpha = 0.6f)),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            if (choice.equals(correctKey, ignoreCase = true)) {
                                                isSolved = true
                                                val stars = when (heardleAttempt) {
                                                    1 -> 3
                                                    2 -> 2
                                                    else -> 1
                                                }
                                                val xp = when (heardleAttempt) {
                                                    1 -> 100
                                                    2 -> 60
                                                    else -> 30
                                                }
                                                onHeardleSolved(stars, xp)
                                            } else {
                                                if (heardleAttempt < 3) heardleAttempt++
                                            }
                                        }
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    ) {
                                        Text(
                                            text = choice,
                                            color = HabitInk,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (isSolved) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HabitSage.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, HabitSage),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("🏆 TOPILDI! To'liq gap:", color = HabitSage, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(fullText, color = HabitInk, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 🎙️ MODE 3: SHADOWING ECHO ARENA
 * Foydalanuvchi native speaker gapirgan jumlani xuddi o'zidek intonatsiya va aks-sado bilan
 * takrorlab ochko oladi.
 */
@Composable
fun AudioShadowingEchoView(
    currentSentence: ScriptPuzzleSentence?,
    onEchoSuccess: (Int) -> Unit
) {
    var isMicActive by remember { mutableStateOf(false) }
    var calculatedScore by remember { mutableStateOf<Int?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val sentenceText = currentSentence?.text ?: "Consistency is definitely the key to mastering any foreign language."

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, Color(0xFF6366F1))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎙️ Aks-Sado (Shadowing) Jangi",
                            color = Color(0xFF6366F1),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF6366F1).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Intonatsiya Sinovi",
                                color = Color(0xFF6366F1),
                                fontSize = 10.5.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Native speaker intonatsiyasiga taqlid qiling. Mikrofonni bosing va jumlani ovoz chiqarib ayting:",
                        color = HabitInkSoft,
                        fontSize = 11.5.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HabitDarkBg,
                        border = BorderStroke(1.dp, Color(0xFF6366F1).copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "\"$sentenceText\"",
                            color = HabitInk,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp),
                            lineHeight = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Large Pulse Mic Button
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(if (isMicActive) Color(0xFFEF4444) else Color(0xFF6366F1), CircleShape)
                            .clickable {
                                isMicActive = true
                                coroutineScope.launch {
                                    delay(2200)
                                    isMicActive = false
                                    val simulatedScore = Random.nextInt(88, 99)
                                    calculatedScore = simulatedScore
                                    onEchoSuccess(simulatedScore)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Mikrofon",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isMicActive) "🎧 Ovoz yozilmoqda: intonatsiyani ushlang..." else "Mikrofonni bosib jumlani o'qing",
                        color = if (isMicActive) Color(0xFFEF4444) else HabitInkSoft,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (calculatedScore != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = HabitSage.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, HabitSage),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🎯 Intonatsiya Mosligi: $calculatedScore%", color = HabitSage, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Ajoyib ritm va talaffuz! Keyingi bosqichga o'tishingiz mumkin.", color = HabitInk, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
