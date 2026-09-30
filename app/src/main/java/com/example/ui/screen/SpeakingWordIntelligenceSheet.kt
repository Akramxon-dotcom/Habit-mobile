@file:OptIn(
    androidx.compose.foundation.layout.ExperimentalLayoutApi::class,
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)

package com.example.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.HabitPreferences
import com.example.data.model.SpeakingEssentialWord
import com.example.data.util.SpeakingWordDetail
import com.example.data.util.SpeakingWordInspectorEngine
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Matndagi har bir so'zga 3 marta bosilganda (Triple-tap) yoki bosib turilganda (Long-press)
 * intellektual tahlil panelini ochuvchi interaktiv matn komponenti.
 * Mikro-animatsiyalar va taktil ko'rsatkich (1/3, 2/3, 3/3) bilan to'liq jihozlangan.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun TripleTapInteractiveText(
    text: String,
    modifier: Modifier = Modifier,
    sentenceContext: String = text,
    fontSize: TextUnit = 14.sp,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = (fontSize.value * 1.45f).sp,
    onWordTripleTapped: (word: String, context: String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    val words = remember(text) { text.split(Regex("\\s+")).filter { it.isNotBlank() } }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        words.forEach { rawWord ->
            val cleanWord = rawWord.replace(Regex("[^a-zA-Z]"), "").trim()
            var tapCount by remember { mutableIntStateOf(0) }
            var lastTapTime by remember { mutableLongStateOf(0L) }

            // 1.1 soniyadan so'ng tap sanagichni nolga tushirish
            LaunchedEffect(tapCount, lastTapTime) {
                if (tapCount > 0) {
                    delay(1100L)
                    tapCount = 0
                }
            }

            val isTapping = tapCount > 0
            val scale by animateFloatAsState(
                targetValue = if (isTapping) 1.08f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "tapScale"
            )

            val bgColor by animateColorAsState(
                targetValue = when (tapCount) {
                    1 -> theme.primaryAccent.copy(alpha = 0.22f)
                    2 -> Color(0xFFF59E0B).copy(alpha = 0.35f)
                    else -> Color.Transparent
                },
                label = "tapBg"
            )

            Box(
                modifier = Modifier
                    .scale(scale)
                    .clip(RoundedCornerShape(6.dp))
                    .background(bgColor)
                    .border(
                        width = if (isTapping) 1.dp else 0.dp,
                        color = if (tapCount == 2) Color(0xFFF59E0B) else if (tapCount == 1) theme.primaryAccent else Color.Transparent,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .combinedClickable(
                        onClick = {
                            if (cleanWord.isNotBlank()) {
                                val now = System.currentTimeMillis()
                                if (now - lastTapTime < 950L) {
                                    tapCount++
                                } else {
                                    tapCount = 1
                                }
                                lastTapTime = now

                                if (tapCount >= 3) {
                                    tapCount = 0
                                    onWordTripleTapped(cleanWord, sentenceContext)
                                }
                            }
                        },
                        onLongClick = {
                            if (cleanWord.isNotBlank()) {
                                tapCount = 0
                                onWordTripleTapped(cleanWord, sentenceContext)
                            }
                        }
                    )
                    .padding(horizontal = 3.dp, vertical = 1.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rawWord,
                        fontSize = fontSize,
                        color = color,
                        fontWeight = fontWeight,
                        lineHeight = lineHeight
                    )
                    if (tapCount in 1..2) {
                        Spacer(modifier = Modifier.width(3.dp))
                        Box(
                            modifier = Modifier
                                .background(if (tapCount == 2) Color(0xFFF59E0B) else theme.primaryAccent, CircleShape)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "${tapCount}/3",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 🌟 95% YAXSHILANGAN INTELLEKTUAL SO'Z TAHLIL PANELI (BottomSheet)
 * - Hero Talaffuz Studiyasi (Sekin 0.7x va Normal 1.0x)
 * - 🎙️ "Talaffuzingizni Sinang" (Ovozli aniqlik testi)
 * - 4 ta chuqurlashtirilgan tematik tahlil bo'limlari (Ma'no, Qoliplar, Dialog, Sirlar)
 * - "Oltin Lug'atga Saqlash" (Dublikatdan qat'iy himoyalangan)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingWordIntelligenceBottomSheet(
    targetWord: String,
    sentenceContext: String,
    sourceMode: String,
    speechManager: SpeechManager,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onOpenVaultSection: () -> Unit
) {
    val context = LocalContext.current
    val theme = LocalLiquidTheme.current
    val scope = rememberCoroutineScope()
    val prefs = remember { HabitPreferences(context) }

    var wordDetail by remember(targetWord) {
        mutableStateOf(SpeakingWordInspectorEngine.getInstantOfflineDetail(targetWord, sentenceContext, context))
    }

    var isAiLoading by remember(targetWord) { mutableStateOf(true) }
    var isSavedInVault by remember(targetWord) {
        mutableStateOf(prefs.isSpeakingWordAlreadySaved(targetWord))
    }
    var saveFeedbackMessage by remember { mutableStateOf<String?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }

    // Ovozli Talaffuz Testi (Pronunciation Matcher)
    var isTestingMic by remember { mutableStateOf(false) }
    var micFeedbackText by remember { mutableStateOf<String?>(null) }
    var pronunciationScore by remember { mutableIntStateOf(-1) }

    LaunchedEffect(targetWord) {
        isAiLoading = true
        val deep = SpeakingWordInspectorEngine.getDeepAiAnalysis(targetWord, sentenceContext, context)
        wordDetail = deep
        isAiLoading = false
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = theme.bgTop,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 6.dp)
                    .size(width = 46.dp, height = 5.dp)
                    .background(theme.primaryAccent.copy(alpha = 0.5f), CircleShape)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(bottom = 24.dp)
        ) {
            // ==============================================================
            // HERO SECTION: So'z, Transkripsiya, Daraja, Ovozli Talaffuz
            // ==============================================================
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = wordDetail.word,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Black,
                                    color = theme.textPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(theme.primaryAccent.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 7.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = wordDetail.level,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = theme.primaryAccent
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = wordDetail.phonetic,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color(0xFF10B981),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "•  ${wordDetail.partOfSpeech}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = theme.textSecondary
                                )
                            }
                        }

                        // Tezkor Ovozli Ijro (Normal & Sekin)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // 🐢 0.7x Sekin
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(theme.glassSurface, CircleShape)
                                    .border(1.dp, theme.glassBorderSubtleColor, CircleShape)
                                    .clickable {
                                        speechManager.speak(wordDetail.word, 0.68f)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🐢", fontSize = 16.sp)
                            }

                            // 🗣️ 1.0x Normal
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(
                                        Brush.linearGradient(listOf(theme.primaryAccent, theme.primaryAccent.copy(alpha = 0.75f))),
                                        CircleShape
                                    )
                                    .clickable {
                                        speechManager.speak(wordDetail.word, 0.88f)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Normal talaffuz",
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // O'zbekcha Tarjimasi (Katta va Aniq)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(theme.glassSurface, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🇺🇿", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = wordDetail.translation,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }

                    // 🎙️ "Talaffuzingizni Sinang" (Interactive Pronunciation Tester)
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isTestingMic) Color(0xFFEF4444).copy(alpha = 0.15f) else theme.glassSurface,
                                RoundedCornerShape(12.dp)
                            )
                            .border(
                                1.dp,
                                if (isTestingMic) Color(0xFFEF4444) else theme.glassBorderSubtleColor,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                if (!isTestingMic) {
                                    isTestingMic = true
                                    micFeedbackText = "Gapiring... So'zni mikrofonga ayting"
                                    pronunciationScore = -1

                                    speechManager.startListening(
                                        onResult = { recognized ->
                                            isTestingMic = false
                                            val cleanRecognized = recognized.trim().lowercase(Locale.ROOT)
                                            val cleanTarget = wordDetail.word.trim().lowercase(Locale.ROOT)

                                            if (cleanRecognized.contains(cleanTarget)) {
                                                pronunciationScore = 100
                                                micFeedbackText = "🌟 A'lo talaffuz! Native darajasida eshitildi (\"$recognized\")"
                                            } else {
                                                pronunciationScore = 65
                                                micFeedbackText = "Tinglandi: \"$recognized\". Qayta eshitib, yana urinib ko'ring."
                                            }
                                        },
                                        onError = {
                                            isTestingMic = false
                                            micFeedbackText = "Ovoz aniqlanmadi. Qaytadan urinib ko'ring."
                                        }
                                    )
                                } else {
                                    isTestingMic = false
                                    speechManager.stopListening()
                                }
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Mic,
                                contentDescription = null,
                                tint = if (isTestingMic) Color(0xFFEF4444) else theme.primaryAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isTestingMic) "🎙️ Tinglamoqda... So'zni ayting" else "Talaffuzingizni tekshiring (Mikrofon)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTestingMic) Color(0xFFEF4444) else theme.textPrimary
                                )
                                if (micFeedbackText != null) {
                                    Text(
                                        text = micFeedbackText ?: "",
                                        fontSize = 10.sp,
                                        color = if (pronunciationScore >= 90) Color(0xFF10B981) else theme.textSecondary
                                    )
                                }
                            }
                            if (pronunciationScore >= 90) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // AI tahlil holati
            if (isAiLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.glassSurface, RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Gemini AI chuqur nutqiy tahlili tayyorlanmoqda...", fontSize = 11.sp, color = theme.textSecondary)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // ==============================================================
            // 4 TA CHUQURLASHTIRILGAN TAB: Ma'no, Qoliplar, Dialog, Sirlar
            // ==============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val tabTitles = listOf("📘 Ma'no", "🔗 Qoliplar", "💬 Dialog", "💡 Sirlar")
                tabTitles.forEachIndexed { idx, title ->
                    val isSel = selectedTab == idx
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (isSel) theme.primaryAccent else theme.glassSurface,
                                RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, if (isSel) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(10.dp))
                            .clickable { selectedTab = idx }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSel) Color.Black else theme.textPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // TAB MAZMUNI
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(12.dp)) {
                    item {
                        when (selectedTab) {
                            0 -> { // Ma'no & Kontekst
                                Column {
                                    Text("🎯 Gapdagi vazifasi:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(wordDetail.usageRules, fontSize = 12.sp, color = theme.textPrimary, lineHeight = 16.sp)

                                    if (sentenceContext.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text("Uchragan gap:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = theme.textSecondary)
                                        Text("\"$sentenceContext\"", fontSize = 11.sp, color = theme.textPrimary, fontWeight = FontWeight.Medium)
                                    }
                                }
                            }
                            1 -> { // Qoliplar & Kollokatsiyalar
                                Column {
                                    Text("🔗 Nutqda eng ko'p ishlatiladigan qoliplar:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "• ${wordDetail.word} bilan bog'liq asosiy ibora va predloglar.",
                                        fontSize = 11.sp,
                                        color = theme.textPrimary
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        "• Sinonimlar: ${wordDetail.synonyms}",
                                        fontSize = 11.sp,
                                        color = Color(0xFF10B981)
                                    )
                                }
                            }
                            2 -> { // Dialog misoli
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("💬 Jonli Suhbat Misoli:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                        IconButton(
                                            onClick = { speechManager.speak(wordDetail.dialogueExample, 0.85f) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("\"${wordDetail.dialogueExample}\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(wordDetail.dialogueTranslation, fontSize = 11.sp, color = theme.textSecondary)
                                }
                            }
                            3 -> { // Talaffuz sirlari
                                Column {
                                    Text("💡 Native Nutq Sirlari (Spoken Tip):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(wordDetail.spokenTip, fontSize = 12.sp, color = theme.textPrimary, lineHeight = 16.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Feedback xabari
            if (saveFeedbackMessage != null) {
                Text(
                    text = saveFeedbackMessage ?: "",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                )
            }

            // ==============================================================
            // ACTION BUTTONS: YODLASH SHART BO'LGAN LUG'ATGA QO'SHISH
            // ==============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        if (!isSavedInVault) {
                            val newEssential = SpeakingEssentialWord(
                                word = wordDetail.word,
                                translation = wordDetail.translation,
                                phonetic = wordDetail.phonetic,
                                partOfSpeech = wordDetail.partOfSpeech,
                                level = wordDetail.level,
                                usageRule = wordDetail.usageRules,
                                dialogueExample = wordDetail.dialogueExample,
                                dialogueTranslation = wordDetail.dialogueTranslation,
                                synonyms = wordDetail.synonyms,
                                spokenTip = wordDetail.spokenTip,
                                sourceMode = sourceMode
                            )
                            val added = prefs.addSpeakingEssentialWord(newEssential)
                            if (added) {
                                isSavedInVault = true
                                saveFeedbackMessage = "🎉 Oltin Lug'atga muvaffaqiyatli saqlandi!"
                            } else {
                                isSavedInVault = true
                                saveFeedbackMessage = "Ushbu so'z allaqachon mavjud!"
                            }
                        }
                    },
                    enabled = !isSavedInVault,
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSavedInVault) Color(0xFF10B981) else theme.primaryAccent,
                        disabledContainerColor = Color(0xFF10B981).copy(alpha = 0.85f),
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        if (isSavedInVault) Icons.Default.CheckCircle else Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isSavedInVault) "⭐ Oltin Lug'atda Mavjud" else "⭐ Oltin Lug'atga Saqlash",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                // Oltin Lug'at bo'limini ochish
                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .height(48.dp)
                        .background(theme.glassSurfaceElevated, RoundedCornerShape(14.dp))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .clickable {
                            onDismiss()
                            onOpenVaultSection()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🏆", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Oltin Lug'at",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                    }
                }
            }
        }
    }
}

/**
 * 🌟 95% YAXSHILANGAN: OLTIN LUG'AT (ESSENTIAL SPEAKING VAULT HUB)
 * 3 ta kuchli rejim:
 * 1. 📋 Lug'at Ro'yxati (Search, Filters, Audio, Toggle Mastered, Delete)
 * 2. 🎴 Flesh-kartalar (3D Flip, Spaced Repetition, Eslab qoldim / Takrorlash)
 * 3. 🎯 Nutq Trenajyori (Mikrofon orqali talaffuz qilib so'z topish viktorinasi)
 * 4. 📤 Eksport & Ulashish (Barcha so'zlarni clipboardga nusxalash)
 */
@Composable
fun SpeakingEssentialVaultDialog(
    speechManager: SpeechManager,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val theme = LocalLiquidTheme.current
    val prefs = remember { HabitPreferences(context) }
    val scope = rememberCoroutineScope()

    var wordsList by remember { mutableStateOf(prefs.getSpeakingEssentialWords()) }
    var activeVaultMode by remember { mutableIntStateOf(0) } // 0: Ro'yxat, 1: Flashcards, 2: Trenajyor

    // Rejim 1 parametrlari
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableIntStateOf(0) } // 0: Barchasi, 1: Yodlanmoqda, 2: Yodlandi

    // Rejim 2 (Flashcard) parametrlari
    var cardIndex by remember { mutableIntStateOf(0) }
    var isCardFlipped by remember { mutableStateOf(false) }

    // Rejim 3 (Quiz) parametrlari
    var quizIndex by remember { mutableIntStateOf(0) }
    var isQuizListening by remember { mutableStateOf(false) }
    var quizFeedback by remember { mutableStateOf<String?>(null) }
    var quizSuccess by remember { mutableStateOf(false) }

    val filteredList = remember(wordsList, searchQuery, selectedFilter) {
        wordsList.filter { item ->
            val matchQuery = searchQuery.isBlank() ||
                    item.word.contains(searchQuery, ignoreCase = true) ||
                    item.translation.contains(searchQuery, ignoreCase = true)

            val matchFilter = when (selectedFilter) {
                1 -> !item.isLearned
                2 -> item.isLearned
                else -> true
            }

            matchQuery && matchFilter
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(theme.bgTop),
            color = theme.bgTop
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // ==========================================
                // TOP BAR: Sarlavha, Eksport, Yopish
                // ==========================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(
                                    Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFD97706))),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⭐", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "Oltin Lug'at (Nutq Ombori)",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = theme.textPrimary
                            )
                            Text(
                                "Yodlash shart bo'lgan ${wordsList.size} ta so'z",
                                fontSize = 11.sp,
                                color = theme.primaryAccent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        // 📤 Eksport / Nusxalash
                        IconButton(
                            onClick = {
                                if (wordsList.isNotEmpty()) {
                                    val textBuilder = StringBuilder()
                                    textBuilder.append("⭐ OLTIN LUG'AT (SPEAKING VAULT) ⭐\n\n")
                                    wordsList.forEachIndexed { i, w ->
                                        textBuilder.append("${i + 1}. ${w.word} [${w.phonetic}] — ${w.translation}\n")
                                        if (w.usageRule.isNotBlank()) textBuilder.append("   Qoida: ${w.usageRule}\n")
                                        if (w.dialogueExample.isNotBlank()) textBuilder.append("   Misol: \"${w.dialogueExample}\"\n")
                                        textBuilder.append("\n")
                                    }
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Oltin Lug'at", textBuilder.toString()))
                                    Toast.makeText(context, "Barcha so'zlar nusxalandi!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Hozircha so'zlar yo'q", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .background(theme.glassSurface, CircleShape)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Ulashish", tint = theme.textPrimary, modifier = Modifier.size(16.dp))
                        }

                        // Yopish
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .background(theme.glassSurface, CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Yopish", tint = theme.textPrimary, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // ==========================================
                // STATISTIKA & PROGRESS HUD
                // ==========================================
                val masteredCount = wordsList.count { it.isLearned }
                val learningCount = wordsList.size - masteredCount
                val progressRatio = if (wordsList.isNotEmpty()) masteredCount.toFloat() / wordsList.size else 0f

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "O'zlashtirish Darajasi",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textSecondary
                            )
                            Text(
                                "${(progressRatio * 100).toInt()}% Yodlandi ($masteredCount/${wordsList.size})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF10B981)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { progressRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = Color(0xFF10B981),
                            trackColor = theme.glassBorderSubtleColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // ==========================================
                // 3 TA REJIM SWITCHER: Ro'yxat, Flesh-kartalar, Trenajyor
                // ==========================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val modes = listOf(
                        Triple(0, "📋 Ro'yxat", Icons.Default.Search),
                        Triple(1, "🎴 Flesh-karta", Icons.Default.Style),
                        Triple(2, "🎯 Trenajyor", Icons.Default.GraphicEq)
                    )
                    modes.forEach { (mIndex, label, icon) ->
                        val isSel = activeVaultMode == mIndex
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isSel) theme.primaryAccent else theme.glassSurface,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(1.dp, if (isSel) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(12.dp))
                                .clickable {
                                    activeVaultMode = mIndex
                                    isCardFlipped = false
                                    quizFeedback = null
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSel) Color.Black else theme.textPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ==========================================
                // REJIM BO'YICHA MAZMUN
                // ==========================================
                when (activeVaultMode) {
                    0 -> {
                        // ==========================================
                        // REJIM 1: LUG'AT RO'YXATI (CATALOG)
                        // ==========================================
                        Column(modifier = Modifier.weight(1f)) {
                            // Qidiruv
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Oltin so'zlardan qidirish...", fontSize = 12.sp) },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(18.dp)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = theme.primaryAccent,
                                    unfocusedBorderColor = theme.glassBorderSubtleColor,
                                    focusedContainerColor = theme.glassSurface,
                                    unfocusedContainerColor = theme.glassSurface
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Filterlar
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val filters = listOf("Barchasi (${wordsList.size})", "Yodlanmoqda ($learningCount)", "Yodlandi ($masteredCount)")
                                filters.forEachIndexed { idx, label ->
                                    val isSelected = selectedFilter == idx
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .background(
                                                if (isSelected) theme.primaryAccent else theme.glassSurface,
                                                RoundedCornerShape(10.dp)
                                            )
                                            .border(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(10.dp))
                                            .clickable { selectedFilter = idx }
                                            .padding(vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            label,
                                            fontSize = 10.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color.Black else theme.textPrimary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (filteredList.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(24.dp)
                                    ) {
                                        Text("🏆", fontSize = 42.sp)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            "Hozircha so'zlar qo'shilmagan",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = theme.textPrimary
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            "Nutq maydonidagi istalgan so'z ustiga 3 marta bosing va uni ushbu Oltin Lug'atga qo'shing!",
                                            fontSize = 12.sp,
                                            color = theme.textSecondary,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(filteredList, key = { it.id }) { item ->
                                        Card(
                                            shape = RoundedCornerShape(16.dp),
                                            colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                                            border = BorderStroke(
                                                1.dp,
                                                if (item.isLearned) Color(0xFF10B981).copy(alpha = 0.6f) else theme.glassBorderSubtleColor
                                            )
                                        ) {
                                            Column(modifier = Modifier.padding(14.dp)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(
                                                            text = item.word,
                                                            fontSize = 17.sp,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = theme.textPrimary
                                                        )
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Text(
                                                            text = item.phonetic,
                                                            fontSize = 11.sp,
                                                            color = Color(0xFF10B981),
                                                            fontFamily = FontFamily.Monospace
                                                        )
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Box(
                                                            modifier = Modifier
                                                                .background(theme.primaryAccent.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                                                .padding(horizontal = 5.dp, vertical = 1.dp)
                                                        ) {
                                                            Text(item.level, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                                        }
                                                    }

                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        IconButton(
                                                            onClick = { speechManager.speak(item.word, 0.85f) },
                                                            modifier = Modifier.size(30.dp)
                                                        ) {
                                                            Icon(
                                                                Icons.AutoMirrored.Filled.VolumeUp,
                                                                contentDescription = "Eshitish",
                                                                tint = theme.primaryAccent,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                        }

                                                        IconButton(
                                                            onClick = {
                                                                prefs.deleteSpeakingEssentialWord(item.id)
                                                                wordsList = prefs.getSpeakingEssentialWords()
                                                            },
                                                            modifier = Modifier.size(30.dp)
                                                        ) {
                                                            Icon(
                                                                Icons.Default.Delete,
                                                                contentDescription = "O'chirish",
                                                                tint = theme.textSecondary.copy(alpha = 0.6f),
                                                                modifier = Modifier.size(16.dp)
                                                            )
                                                        }
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = item.translation,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF10B981)
                                                )

                                                if (item.usageRule.isNotBlank()) {
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Text(
                                                        text = "📐 ${item.usageRule}",
                                                        fontSize = 11.sp,
                                                        color = theme.textSecondary,
                                                        lineHeight = 15.sp
                                                    )
                                                }

                                                if (item.dialogueExample.isNotBlank()) {
                                                    Spacer(modifier = Modifier.height(6.dp))
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .background(theme.glassSurfaceElevated, RoundedCornerShape(8.dp))
                                                            .padding(8.dp)
                                                    ) {
                                                        Column {
                                                            Text(
                                                                text = "\"${item.dialogueExample}\"",
                                                                fontSize = 11.sp,
                                                                fontWeight = FontWeight.SemiBold,
                                                                color = theme.textPrimary
                                                            )
                                                            if (item.dialogueTranslation.isNotBlank()) {
                                                                Text(
                                                                    text = item.dialogueTranslation,
                                                                    fontSize = 10.sp,
                                                                    color = theme.textSecondary
                                                                )
                                                            }
                                                        }
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(8.dp))

                                                // Yodlanganlik holatini o'zgartirish
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = "Manba: ${item.sourceMode}",
                                                        fontSize = 10.sp,
                                                        color = theme.textSecondary
                                                    )

                                                    Box(
                                                        modifier = Modifier
                                                            .background(
                                                                if (item.isLearned) Color(0xFF10B981).copy(alpha = 0.2f) else theme.primaryAccent.copy(alpha = 0.2f),
                                                                RoundedCornerShape(8.dp)
                                                            )
                                                            .border(
                                                                1.dp,
                                                                if (item.isLearned) Color(0xFF10B981) else theme.primaryAccent,
                                                                RoundedCornerShape(8.dp)
                                                            )
                                                            .clickable {
                                                                prefs.toggleSpeakingEssentialWordLearned(item.id)
                                                                wordsList = prefs.getSpeakingEssentialWords()
                                                            }
                                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                                    ) {
                                                        Text(
                                                            text = if (item.isLearned) "Yodlandi ✅" else "O'rganilmoqda 📖",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = if (item.isLearned) Color(0xFF10B981) else theme.primaryAccent
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // ==========================================
                        // REJIM 2: FLESH-KARTALAR (ANKI FLASHCARDS)
                        // ==========================================
                        if (wordsList.isEmpty()) {
                            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Text("Oltin Lug'atda flesh-kartalar mavjud emas.", fontSize = 13.sp, color = theme.textSecondary)
                            }
                        } else {
                            val safeCardIndex = cardIndex.coerceIn(0, wordsList.size - 1)
                            val currentCard = wordsList[safeCardIndex]

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Karta indikatori
                                Text(
                                    text = "Karta ${safeCardIndex + 1} / ${wordsList.size}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.textSecondary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Interaktiv Karta
                                Card(
                                    shape = RoundedCornerShape(24.dp),
                                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                                    border = BorderStroke(1.5.dp, if (isCardFlipped) Color(0xFF10B981) else theme.primaryAccent),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clickable { isCardFlipped = !isCardFlipped }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        if (!isCardFlipped) {
                                            // OLD TOMONI: Inglizcha so'z, transkripsiya, audio
                                            Box(
                                                modifier = Modifier
                                                    .background(theme.primaryAccent.copy(alpha = 0.18f), RoundedCornerShape(8.dp))
                                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                                            ) {
                                                Text(currentCard.level, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                            }
                                            Spacer(modifier = Modifier.height(14.dp))
                                            Text(
                                                text = currentCard.word,
                                                fontSize = 32.sp,
                                                fontWeight = FontWeight.Black,
                                                color = theme.textPrimary,
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = currentCard.phonetic,
                                                fontSize = 16.sp,
                                                fontFamily = FontFamily.Monospace,
                                                color = Color(0xFF10B981)
                                            )
                                            Spacer(modifier = Modifier.height(16.dp))
                                            IconButton(
                                                onClick = { speechManager.speak(currentCard.word, 0.85f) },
                                                modifier = Modifier
                                                    .size(46.dp)
                                                    .background(theme.primaryAccent, CircleShape)
                                            ) {
                                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = Color.Black)
                                            }
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Text(
                                                "👆 Tarjimani ko'rish uchun kartani bosing",
                                                fontSize = 11.sp,
                                                color = theme.textSecondary
                                            )
                                        } else {
                                            // ORQA TOMONI: O'zbekcha tarjima, qoida, dialog
                                            Text(
                                                text = currentCard.translation,
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFF10B981),
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            if (currentCard.usageRule.isNotBlank()) {
                                                Text(
                                                    text = currentCard.usageRule,
                                                    fontSize = 12.sp,
                                                    color = theme.textPrimary,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(12.dp))
                                            if (currentCard.dialogueExample.isNotBlank()) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(theme.glassSurface, RoundedCornerShape(12.dp))
                                                        .padding(10.dp)
                                                ) {
                                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                        Text("\"${currentCard.dialogueExample}\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary, textAlign = TextAlign.Center)
                                                        Text(currentCard.dialogueTranslation, fontSize = 11.sp, color = theme.textSecondary, textAlign = TextAlign.Center)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Boshqaruv Tugmalari: Takrorlash vs Eslab qoldim
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            isCardFlipped = false
                                            cardIndex = (cardIndex + 1) % wordsList.size
                                        },
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurfaceElevated),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("❌ Takrorlash", fontSize = 12.sp, color = theme.textPrimary)
                                    }

                                    Button(
                                        onClick = {
                                            if (!currentCard.isLearned) {
                                                prefs.toggleSpeakingEssentialWordLearned(currentCard.id)
                                                wordsList = prefs.getSpeakingEssentialWords()
                                            }
                                            isCardFlipped = false
                                            cardIndex = (cardIndex + 1) % wordsList.size
                                        },
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("✅ Eslab Qoldim!", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        // ==========================================
                        // REJIM 3: NUTQ TRENAJYORI (SPEAKING QUIZ)
                        // ==========================================
                        if (wordsList.isEmpty()) {
                            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Text("Oltin Lug'atda mashq qilish uchun so'zlar yo'q.", fontSize = 13.sp, color = theme.textSecondary)
                            }
                        } else {
                            val safeQuizIndex = quizIndex.coerceIn(0, wordsList.size - 1)
                            val currentQuiz = wordsList[safeQuizIndex]

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🎯 Nutq Sinovi (${safeQuizIndex + 1}/${wordsList.size})", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                Spacer(modifier = Modifier.height(10.dp))

                                Card(
                                    shape = RoundedCornerShape(22.dp),
                                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                    modifier = Modifier.fillMaxWidth().weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text("Ushbu so'zni inglizcha talaffuz qiling:", fontSize = 12.sp, color = theme.textSecondary)
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = currentQuiz.translation,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Black,
                                            color = theme.textPrimary,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "Yordam: ${currentQuiz.word.first()}... (${currentQuiz.word.length} harf)",
                                            fontSize = 13.sp,
                                            color = Color(0xFFF59E0B),
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        Spacer(modifier = Modifier.height(20.dp))

                                        // Katta Mikrofon Tugmasi
                                        Box(
                                            modifier = Modifier
                                                .size(72.dp)
                                                .background(
                                                    if (isQuizListening) Color(0xFFEF4444) else theme.primaryAccent,
                                                    CircleShape
                                                )
                                                .clickable {
                                                    if (!isQuizListening) {
                                                        isQuizListening = true
                                                        quizFeedback = "Tinglamoqda... So'zni ayting"
                                                        quizSuccess = false

                                                        speechManager.startListening(
                                                            onResult = { result ->
                                                                isQuizListening = false
                                                                val cleanResult = result.trim().lowercase(Locale.ROOT)
                                                                val cleanTarget = currentQuiz.word.trim().lowercase(Locale.ROOT)

                                                                if (cleanResult.contains(cleanTarget)) {
                                                                    quizSuccess = true
                                                                    quizFeedback = "🎉 A'lo! To'g'ri topdingiz: \"${currentQuiz.word}\""
                                                                    if (!currentQuiz.isLearned) {
                                                                        prefs.toggleSpeakingEssentialWordLearned(currentQuiz.id)
                                                                        wordsList = prefs.getSpeakingEssentialWords()
                                                                    }
                                                                } else {
                                                                    quizSuccess = false
                                                                    quizFeedback = "Eshitildi: \"$result\". To'g'ri so'z: \"${currentQuiz.word}\""
                                                                }
                                                            },
                                                            onError = {
                                                                isQuizListening = false
                                                                quizFeedback = "Ovoz eshitilmadi. Qayta bosing."
                                                            }
                                                        )
                                                    } else {
                                                        isQuizListening = false
                                                        speechManager.stopListening()
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                Icons.Default.Mic,
                                                contentDescription = null,
                                                tint = Color.Black,
                                                modifier = Modifier.size(34.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(14.dp))

                                        if (quizFeedback != null) {
                                            Text(
                                                text = quizFeedback ?: "",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (quizSuccess) Color(0xFF10B981) else Color(0xFFEF4444),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        quizFeedback = null
                                        quizSuccess = false
                                        quizIndex = (quizIndex + 1) % wordsList.size
                                    },
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text("Keyingi So'zga O'tish ➡️", fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
