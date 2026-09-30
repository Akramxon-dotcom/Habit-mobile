package com.example.ui.screen

import android.net.Uri
import android.provider.OpenableColumns
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizQuestion
import com.example.data.model.TaskTimeEngine
import com.example.data.model.VocabCard
import com.example.ui.theme.LocalLiquidTheme
import java.util.Locale
import kotlinx.coroutines.launch

/**
 * 🌟 Butunlay yangilangan, ultra-qulay va zamonaviy Lug'at Markazi (Vocab Studio).
 * - 4 xil qulay rejim: Fleshkarta (Study Mode), Sandiq (Ro'yxat va qidiruv), Sinov (Quiz Arena), O'quv Rejasi (Curriculum).
 * - Ovozli talaffuz (TTS Text-To-Speech) o'rnatilgan — har bir so'zni bitta bosishda eshitish mumkin!
 * - Tezkor qidiruv va CEFR (A1-C1) hamda Sevimlilar filtratsiyasi.
 * - Yagona, nihoyatda qulay qo'shish va import paneli.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabScreen(
    cards: List<VocabCard>,
    isLoading: Boolean,
    todayBatchCardIds: List<String> = emptyList(),
    dailyGoal: Int = 10,
    learnedToday: Int = 0,
    isDocumentParsing: Boolean = false,
    documentStatus: String = "",
    onSetDailyGoal: (Int) -> Unit = {},
    onAddMoreDailyWords: () -> Unit = {},
    onImportDocument: (Uri, String) -> Unit = { _, _ -> },
    onImportText: (String, String) -> Unit = { _, _ -> },
    onLoadSampleCefr: () -> Unit = {},
    onMarkMastered: (String) -> Unit = {},
    onResetForReview: (String) -> Unit = {},
    onAddWordWithAi: (String) -> Unit,
    onManualAddWord: (VocabCard) -> Unit = {},
    onDeleteWord: (String) -> Unit,
    onToggleFavorite: (String) -> Unit = {},
    onUpdateBoxLevel: (String, Int) -> Unit,
    onGenerateQuiz: (retryOnly: Boolean) -> Unit,
    quizQuestions: List<QuizQuestion>,
    isQuizLoading: Boolean,
    isQuizPassedToday: Boolean = false,
    quizFailedWordIds: Set<String> = emptySet(),
    onSubmitQuizResults: (correctCardIds: List<String>, failedCardIds: List<String>) -> Unit = { _, _ -> },
    onCloseQuiz: () -> Unit,
    activeEnglishPlanWeek: Int = 1,
    completedEnglishPlanTaskIds: Set<String> = emptySet(),
    onSelectEnglishPlanWeek: (Int) -> Unit = {},
    onToggleEnglishPlanTask: (String) -> Unit = {},
    onOpenReader: () -> Unit = {},
    onOpenSpeakingRoom: () -> Unit = {},
    onOpenEveningCoach: () -> Unit = {},
    onOpenMurphy: () -> Unit = {},
    onOpenScriptStudio: () -> Unit = {},
    onOpenPictureChallenge: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 🔊 Text-To-Speech Engine (Real native voice pronunciation)
    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }
    var isTtsReady by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsEngine?.language = Locale.US
                isTtsReady = true
            }
        }
        ttsEngine = tts
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    fun isUzbekText(text: String): Boolean {
        val clean = text.lowercase(Locale.ROOT)
        if (clean.any { it in '\u0400'..'\u04FF' }) return true
        if (clean.contains("o'") || clean.contains("g'") ||
            clean.contains("oʻ") || clean.contains("gʻ") ||
            clean.contains("o’") || clean.contains("g’") ||
            clean.contains("o‘") || clean.contains("g‘")
        ) return true
        val uzbekKeywords = listOf(
            "va", "bir", "bu", "uchun", "bilan", "ham", "emas", "kerak", "bo'lib", "bo'lgan",
            "qilish", "deb", "lekin", "har", "o'z", "gap", "so'z", "kun", "ish", "tarjima",
            "qiling", "aytmoq", "bering", "bo'lmoq", "yordam", "maqsad", "vazifa", "dars",
            "yaxshi", "katta", "kichik", "ko'p", "kam", "inson", "odam", "til", "ingliz",
            "o'zbek", "to'g'ri", "xato", "ta'rif", "misol"
        )
        return uzbekKeywords.any { clean.contains(it) }
    }

    fun speakText(text: String, isUzbek: Boolean? = null) {
        try {
            val engine = ttsEngine ?: return
            if (text.isBlank()) return
            val uzbek = isUzbek ?: isUzbekText(text)
            if (uzbek) {
                val uzbekLocales = listOf(
                    Locale("uz", "UZ"),
                    Locale("uz"),
                    Locale("tr", "TR"), // Turkish Latin phonetics provide a natural, clear Turkic/Uzbek accent
                    Locale("tr"),
                    Locale("az", "AZ")
                )
                var matched = false
                for (loc in uzbekLocales) {
                    val res = engine.setLanguage(loc)
                    if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) {
                        matched = true
                        break
                    }
                }
                if (!matched) engine.setLanguage(Locale.getDefault())
                engine.setPitch(1.05f)
                engine.setSpeechRate(0.90f)
            } else {
                engine.setLanguage(Locale.US)
                engine.setPitch(1.0f)
                engine.setSpeechRate(0.85f)
            }
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "vocab_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 🎯 Mode Switcher: "MEMORIZE" (Yodlash: 7 ta usul), "VAULT" (Sandiq Ro'yxati), "PLAN" (1 Oylik Reja)
    var currentMode by remember { mutableStateOf("MEMORIZE") }

    // Search & Filter States
    var searchQuery by remember { mutableStateOf("") }
    var selectedLevelFilter by remember { mutableStateOf("ALL") } // ALL, FAVORITE, A1, A2, B1, B2, C1, MASTERED
    var selectedMasteredDateOffset by remember { mutableIntStateOf(0) } // 0 = Bugun, -1 = Kecha, etc.

    // Modal Bottom Sheet State for adding & importing words
    var showAddImportSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Quiz Dialog State
    var showQuizDialog by remember { mutableStateOf(false) }

    // Document Picker
    val docPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            var fileName = "lugat_hujjati"
            try {
                context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1 && cursor.moveToFirst()) {
                        fileName = cursor.getString(nameIndex) ?: fileName
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            onImportDocument(uri, fileName)
            showAddImportSheet = false
        }
    }

    // Calculations
    val unmasteredCards = remember(cards) { cards.filter { !it.isMastered } }
    val masteredCards = remember(cards) { cards.filter { it.isMastered } }
    val totalCount = cards.size
    val masteredCount = masteredCards.size

    // Today's batch
    val todayCards = remember(todayBatchCardIds, unmasteredCards, dailyGoal) {
        if (todayBatchCardIds.isNotEmpty()) {
            val batchSet = todayBatchCardIds.toSet()
            val inBatch = unmasteredCards.filter { it.id in batchSet }
            if (inBatch.isNotEmpty()) inBatch else unmasteredCards.take(dailyGoal)
        } else {
            unmasteredCards.take(dailyGoal)
        }
    }

    // Filtered list for "VAULT" mode
    val vaultDisplayCards = remember(cards, searchQuery, selectedLevelFilter, selectedMasteredDateOffset) {
        var baseList = when (selectedLevelFilter) {
            "MASTERED" -> {
                if (selectedMasteredDateOffset == 999) {
                    masteredCards
                } else {
                    val targetIso = TaskTimeEngine.getIsoDateForOffset(selectedMasteredDateOffset)
                    masteredCards.filter { it.learnedDate == targetIso }
                }
            }
            "FAVORITE" -> unmasteredCards.filter { it.isFavorite }
            "TODAY" -> todayCards
            "ALL" -> unmasteredCards
            else -> unmasteredCards.filter { it.level.equals(selectedLevelFilter, ignoreCase = true) }
        }

        if (searchQuery.isNotBlank()) {
            val q = searchQuery.trim().lowercase()
            baseList = baseList.filter {
                it.word.lowercase().contains(q) ||
                    it.translation.lowercase().contains(q) ||
                    it.example.lowercase().contains(q) ||
                    it.definition.lowercase().contains(q)
            }
        }

        baseList.sortedBy { it.importanceRank }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. TOP HERO BAR: Title, Live Stats, and Quick Add Action
        item {
            Spacer(modifier = Modifier.height(14.dp))
            VocabHeroHeader(
                totalCount = totalCount,
                masteredCount = masteredCount,
                learnedToday = learnedToday,
                dailyGoal = dailyGoal,
                onOpenAddImport = { showAddImportSheet = true },
                onTtsClick = {
                    Toast.makeText(context, "🔊 Ovozli talaffuz tayyor!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // 2. DAILY GOAL PROGRESS CARD (Compact, ergonomic stepper with instant update)
        item {
            VocabDailyGoalBanner(
                learnedToday = learnedToday,
                dailyGoal = dailyGoal,
                onSetDailyGoal = onSetDailyGoal,
                onAddMoreDailyWords = onAddMoreDailyWords
            )
        }

        // 3. SEGMENTED NAVIGATION BAR: 🧠 Yodlash | 📖 Sandiq | 🌐 Reja
        item {
            VocabModeSelector(
                currentMode = currentMode,
                todayCount = todayCards.size,
                vaultCount = unmasteredCards.size,
                onSelectMode = { currentMode = it }
            )
        }

        // 4. CONTENT ACCORDING TO SELECTED MODE
        when (currentMode) {
            "MEMORIZE" -> {
                item {
                    VocabMemorizeStudio(
                        todayCards = todayCards,
                        allCards = cards,
                        onSpeakWord = { speakText(it) },
                        onMarkMastered = onMarkMastered,
                        onResetForReview = onResetForReview,
                        onToggleFavorite = onToggleFavorite,
                        onAddMoreDailyWords = onAddMoreDailyWords,
                        dailyGoal = dailyGoal,
                        isQuizPassedToday = isQuizPassedToday,
                        quizFailedWordIds = quizFailedWordIds,
                        onOpenFullQuiz = { retryOnly ->
                            showQuizDialog = true
                            onGenerateQuiz(retryOnly)
                        }
                    )
                }
            }

            "VAULT" -> {
                // Search & Filter header
                item {
                    VocabSearchAndFilterHeader(
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        selectedFilter = selectedLevelFilter,
                        onSelectFilter = { selectedLevelFilter = it },
                        unmasteredCount = unmasteredCards.size,
                        favCount = unmasteredCards.count { it.isFavorite },
                        masteredCount = masteredCards.size,
                        todayCount = todayCards.size,
                        selectedMasteredOffset = selectedMasteredDateOffset,
                        onSelectMasteredOffset = { selectedMasteredDateOffset = it }
                    )
                }

                if (vaultDisplayCards.isEmpty()) {
                    item {
                        VocabEmptyState(
                            query = searchQuery,
                            filter = selectedLevelFilter,
                            onClearFilter = {
                                searchQuery = ""
                                selectedLevelFilter = "ALL"
                            },
                            onAddWords = { showAddImportSheet = true }
                        )
                    }
                } else {
                    items(vaultDisplayCards, key = { it.id }) { card ->
                        ModernVocabCardItem(
                            card = card,
                            onSpeak = { speakText(card.word) },
                            onMarkMastered = { onMarkMastered(card.id) },
                            onResetForReview = { onResetForReview(card.id) },
                            onToggleFavorite = { onToggleFavorite(card.id) },
                            onDelete = { onDeleteWord(card.id) }
                        )
                    }
                }
            }

            "PLAN" -> {
                // Quick Modules Shortcuts + Monthly English Plan Tasks
                item {
                    VocabEnglishModulesBar(
                        onOpenReader = onOpenReader,
                        onOpenSpeakingRoom = onOpenSpeakingRoom,
                        onOpenEveningCoach = onOpenEveningCoach,
                        onOpenMurphy = onOpenMurphy,
                        onOpenScriptStudio = onOpenScriptStudio,
                        onOpenPictureChallenge = onOpenPictureChallenge
                    )
                }

                // Call existing monthly plan tasks extension
                monthlyPlanLazyItems(
                    activeWeek = activeEnglishPlanWeek,
                    completedTaskIds = completedEnglishPlanTaskIds,
                    selectedSubSection = "TASKS",
                    onSelectSubSection = { },
                    onSelectWeek = onSelectEnglishPlanWeek,
                    onToggleTask = onToggleEnglishPlanTask,
                    theme = theme
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }

    // ==========================================
    // 5. ULTRA-CONVENIENT ADD & IMPORT SHEET
    // ==========================================
    if (showAddImportSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAddImportSheet = false },
            sheetState = sheetState,
            containerColor = theme.dialogSurface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            VocabAddImportSheetContent(
                isLoading = isLoading,
                isDocumentParsing = isDocumentParsing,
                documentStatus = documentStatus,
                onAddWordWithAi = { word ->
                    onAddWordWithAi(word)
                    showAddImportSheet = false
                },
                onPickFile = {
                    docPickerLauncher.launch(
                        arrayOf(
                            "application/pdf",
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                            "application/msword",
                            "text/plain",
                            "*/*"
                        )
                    )
                },
                onLoadSampleCefr = {
                    onLoadSampleCefr()
                    showAddImportSheet = false
                },
                onImportText = { text ->
                    onImportText(text, "Qo'lda kiritilgan")
                    showAddImportSheet = false
                },
                onClose = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        showAddImportSheet = false
                    }
                }
            )
        }
    }

    // ==========================================
    // 6. QUIZ DIALOG
    // ==========================================
    if (showQuizDialog) {
        AiQuizDialog(
            questions = quizQuestions,
            isLoading = isQuizLoading,
            onCompleteQuiz = { correctIds, failedIds ->
                onSubmitQuizResults(correctIds, failedIds)
            },
            onDismiss = {
                showQuizDialog = false
                onCloseQuiz()
            }
        )
    }
}

/**
 * Top Hero Header with aesthetic balance and high-clarity stats.
 */
@Composable
private fun VocabHeroHeader(
    totalCount: Int,
    masteredCount: Int,
    learnedToday: Int,
    dailyGoal: Int,
    onOpenAddImport: () -> Unit,
    onTtsClick: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Lug'at Markazi",
                    color = theme.textPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = theme.primaryAccent.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable { onTtsClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "TTS Ready",
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Audio",
                            color = theme.primaryAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Jami: $totalCount ta so'z · $masteredCount ta yodlangan",
                color = theme.textSecondary,
                fontSize = 12.sp
            )
        }

        // Quick Add Button
        Button(
            onClick = onOpenAddImport,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
            modifier = Modifier.testTag("btn_open_add_vocab_sheet")
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Qo'shish",
                color = Color.Black,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Clean, modern Daily Goal Banner with progress bar and stepper controls.
 */
@Composable
private fun VocabDailyGoalBanner(
    learnedToday: Int,
    dailyGoal: Int,
    onSetDailyGoal: (Int) -> Unit,
    onAddMoreDailyWords: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    val progress = if (dailyGoal > 0) (learnedToday.toFloat() / dailyGoal).coerceIn(0f, 1f) else 0f
    val isGoalCompleted = learnedToday >= dailyGoal && dailyGoal > 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, if (isGoalCompleted) theme.primaryAccent.copy(alpha = 0.5f) else theme.glassBorderSubtleColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🎯 Bugungi me'yor",
                            color = theme.textPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (isGoalCompleted) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF10B981).copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, Color(0xFF10B981))
                            ) {
                                Text(
                                    text = "🔥 Bajarildi!",
                                    color = Color(0xFF10B981),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Bugun o'zlashtirildi: $learnedToday / $dailyGoal ta so'z",
                        color = theme.textSecondary,
                        fontSize = 12.sp
                    )
                }

                // Stepper [-] Goal [+]
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        onClick = {
                            val newGoal = when {
                                dailyGoal > 10 -> dailyGoal - 5
                                dailyGoal > 5 -> 5
                                dailyGoal > 1 -> dailyGoal - 1
                                else -> 1
                            }
                            onSetDailyGoal(newGoal)
                        },
                        shape = CircleShape,
                        color = theme.glassSurface,
                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                "−",
                                color = if (dailyGoal > 1) theme.textPrimary else theme.textSecondary.copy(alpha = 0.3f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "$dailyGoal",
                        color = theme.primaryAccent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Surface(
                        onClick = {
                            val newGoal = if (dailyGoal < 5) 5 else (dailyGoal + 5).coerceAtMost(200)
                            onSetDailyGoal(newGoal)
                        },
                        shape = CircleShape,
                        color = theme.glassSurface,
                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("+", color = theme.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (isGoalCompleted) Color(0xFF10B981) else theme.primaryAccent,
                trackColor = theme.primaryAccent.copy(alpha = 0.15f),
                strokeCap = StrokeCap.Round
            )
        }
    }
}

/**
 * 3-Tab Mode Selector: Yodlash (Memorize Hub), Sandiq (Vault), Reja (Plan).
 * "Sinov" is integrated directly inside "Yodlash" as one of its 7 training methods.
 */
@Composable
private fun VocabModeSelector(
    currentMode: String,
    todayCount: Int,
    vaultCount: Int,
    onSelectMode: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current

    val modes = listOf(
        Triple("MEMORIZE", "🧠 Yodlash", todayCount),
        Triple("VAULT", "📖 Sandiq", vaultCount),
        Triple("PLAN", "🌐 Reja", null)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        modes.forEach { (modeKey, title, badge) ->
            val isSelected = currentMode == modeKey
            val bg = if (isSelected) theme.primaryAccent else theme.glassSurface
            val contentColor = if (isSelected) Color.Black else theme.textPrimary
            val border = if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bg)
                    .border(1.dp, border, RoundedCornerShape(12.dp))
                    .clickable { onSelectMode(modeKey) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        color = contentColor,
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Helper to ensure a reliable pool of cards for training exercises.
 */
private fun getPlayableVocabCards(todayCards: List<VocabCard>, allCards: List<VocabCard>): List<VocabCard> {
    if (todayCards.isNotEmpty()) return todayCards
    val unmastered = allCards.filter { !it.isMastered }
    if (unmastered.isNotEmpty()) return unmastered.take(15)
    if (allCards.isNotEmpty()) return allCards.take(15)
    return listOf(
        VocabCard(id = "fallback_1", word = "Achieve", translation = "Erishmoq", phonetic = "/əˈtʃiːv/", level = "B1", definition = "To successfully finish or reach a goal", example = "He worked hard to achieve success."),
        VocabCard(id = "fallback_2", word = "Opportunity", translation = "Imkoniyat", phonetic = "/ˌɒp.əˈtjuː.nə.ti/", level = "B1", definition = "A chance for improvement or success", example = "This job is a great opportunity."),
        VocabCard(id = "fallback_3", word = "Knowledge", translation = "Bilim", phonetic = "/ˈnɒl.ɪdʒ/", level = "A2", definition = "Understanding of information gained by study", example = "Knowledge is the key to progress."),
        VocabCard(id = "fallback_4", word = "Resilient", translation = "Chidamli", phonetic = "/rɪˈzɪl.jənt/", level = "B2", definition = "Able to bounce back after tough times", example = "She proved to be remarkably resilient."),
        VocabCard(id = "fallback_5", word = "Determine", translation = "Belgilamoq", phonetic = "/dɪˈtɜː.mɪn/", level = "B1", definition = "To decide or control what will happen", example = "Your actions determine your future.")
    )
}

/**
 * 🧠 YODLASH STUDIYASI (Memorization Hub):
 * Offers 7 distinct, highly effective learning methods for today's words:
 * 1. 🃏 Fleshkard (Flip cards & audio)
 * 2. 🎯 Sinov & 4-Variant (Multiple choice test + AI Arena)
 * 3. 🧩 Juftliklar (Match pairs challenge)
 * 4. ✍️ Yozma Diktant (Spelling & Writing)
 * 5. 🔤 Harflardan Terish (Letter Scramble builder)
 * 6. 🎧 Audio Trenajor (Listening ear-training)
 * 7. ⚡ Tezkor Blits (True/False reflex flash)
 */
@Composable
private fun VocabMemorizeStudio(
    todayCards: List<VocabCard>,
    allCards: List<VocabCard>,
    onSpeakWord: (String) -> Unit,
    onMarkMastered: (String) -> Unit,
    onResetForReview: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onAddMoreDailyWords: () -> Unit,
    dailyGoal: Int,
    isQuizPassedToday: Boolean,
    quizFailedWordIds: Set<String>,
    onOpenFullQuiz: (Boolean) -> Unit
) {
    val theme = LocalLiquidTheme.current
    val playableCards = remember(todayCards, allCards) { getPlayableVocabCards(todayCards, allCards) }
    var activeMethod by remember { mutableStateOf("FLASHCARD") }

    val methods = listOf(
        Triple("FLASHCARD", "🃏 Fleshkard", "3D karta & talaffuz"),
        Triple("QUIZ", "🎯 Sinov", "4-variantli test"),
        Triple("MATCH_PAIRS", "🧩 Juftliklar", "So'z va tarjimani top"),
        Triple("SPELLING", "✍️ Diktant", "Imlo & yozish"),
        Triple("SCRAMBLE", "🔤 Harflar", "Harflardan terish"),
        Triple("LISTENING", "🎧 Audio", "Eshitib topish"),
        Triple("BLITZ", "⚡ Tezkor Blits", "To'g'ri / Noto'g'ri")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Horizontal Method Selector Bar
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
            border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚡ Yodlash usulini tanlang (7 ta usul):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primaryAccent
                    )
                    Text(
                        text = "${playableCards.size} ta so'z",
                        fontSize = 11.sp,
                        color = theme.textSecondary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(methods) { (methodKey, title, _) ->
                        val isSelected = activeMethod == methodKey
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) theme.primaryAccent else theme.glassSurfaceElevated)
                                .border(
                                    1.dp,
                                    if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { activeMethod = methodKey }
                                .padding(horizontal = 12.dp, vertical = 7.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                color = if (isSelected) Color.Black else theme.textPrimary,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Active Method Render
        when (activeMethod) {
            "FLASHCARD" -> {
                VocabFlashcardSection(
                    todayCards = todayCards,
                    playableCards = playableCards,
                    onSpeakWord = onSpeakWord,
                    onMarkMastered = onMarkMastered,
                    onResetForReview = onResetForReview,
                    onToggleFavorite = onToggleFavorite,
                    onAddMoreDailyWords = onAddMoreDailyWords,
                    dailyGoal = dailyGoal
                )
            }

            "QUIZ" -> {
                VocabInteractiveQuizMode(
                    cards = playableCards,
                    allCards = allCards,
                    isQuizPassedToday = isQuizPassedToday,
                    quizFailedWordIds = quizFailedWordIds,
                    onSpeakWord = onSpeakWord,
                    onOpenFullQuiz = onOpenFullQuiz
                )
            }

            "MATCH_PAIRS" -> {
                VocabMatchPairsMode(
                    cards = playableCards,
                    onSpeakWord = onSpeakWord
                )
            }

            "SPELLING" -> {
                VocabSpellingMode(
                    cards = playableCards,
                    onSpeakWord = onSpeakWord,
                    onMarkMastered = onMarkMastered
                )
            }

            "SCRAMBLE" -> {
                VocabWordScrambleMode(
                    cards = playableCards,
                    onSpeakWord = onSpeakWord
                )
            }

            "LISTENING" -> {
                VocabListeningMode(
                    cards = playableCards,
                    allCards = allCards,
                    onSpeakWord = onSpeakWord
                )
            }

            "BLITZ" -> {
                VocabSpeedBlitzMode(
                    cards = playableCards,
                    allCards = allCards,
                    onSpeakWord = onSpeakWord
                )
            }
        }
    }
}

/**
 * 🃏 USUL 1: FLASHCARD STUDY MODE
 * 3D flip card presentation with flip reveal, pronunciation, and mastery status.
 */
@Composable
private fun VocabFlashcardSection(
    todayCards: List<VocabCard>,
    playableCards: List<VocabCard>,
    onSpeakWord: (String) -> Unit,
    onMarkMastered: (String) -> Unit,
    onResetForReview: (String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onAddMoreDailyWords: () -> Unit,
    dailyGoal: Int
) {
    val theme = LocalLiquidTheme.current
    val activeDeck = if (todayCards.isNotEmpty()) todayCards else playableCards

    if (todayCards.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
            border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🎉", fontSize = 40.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Bugungi dasta so'zlari yodlandi!",
                    color = theme.textPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Yana yangi so'zlarni hoziroq yuklab o'rganishingiz mumkin.",
                    color = theme.textSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onAddMoreDailyWords,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "➕ Navbatdagi $dailyGoal ta so'zni yuklash",
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (activeDeck.isEmpty()) return

    // Active Card Index State
    var currentIndex by remember(activeDeck) { mutableIntStateOf(0) }
    val safeIndex = currentIndex.coerceIn(0, activeDeck.size - 1)
    val card = activeDeck[safeIndex]
    var isFlipped by remember(card.id) { mutableStateOf(false) }

    val levelColor = when (card.level.uppercase()) {
        "A1" -> Color(0xFF4CAF50)
        "A2" -> Color(0xFF009688)
        "B1" -> Color(0xFF2196F3)
        "B2" -> Color(0xFFFF9800)
        "C1" -> Color(0xFF9C27B0)
        else -> theme.primaryAccent
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Navigation & Counter Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Karta: ${safeIndex + 1} / ${activeDeck.size}",
                color = theme.textSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(
                    onClick = {
                        if (safeIndex > 0) currentIndex = safeIndex - 1
                    },
                    enabled = safeIndex > 0,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Oldingi",
                        tint = if (safeIndex > 0) theme.textPrimary else theme.textSecondary.copy(alpha = 0.3f),
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = {
                        if (safeIndex < activeDeck.size - 1) currentIndex = safeIndex + 1
                    },
                    enabled = safeIndex < activeDeck.size - 1,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Keyingi",
                        tint = if (safeIndex < activeDeck.size - 1) theme.textPrimary else theme.textSecondary.copy(alpha = 0.3f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // THE INTERACTIVE 3D-STYLE FLASHCARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isFlipped = !isFlipped }
                .testTag("flashcard_body"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
            border = BorderStroke(1.5.dp, if (isFlipped) theme.primaryAccent.copy(alpha = 0.7f) else theme.glassBorderSubtleColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Card Top Meta: Level Badge, Rank, and Favorite
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = levelColor.copy(alpha = 0.18f),
                            border = BorderStroke(1.dp, levelColor.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = card.level.ifBlank { "A1" }.uppercase(),
                                color = levelColor,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        if (card.importanceRank in 1..5020) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFB800).copy(alpha = 0.18f),
                                border = BorderStroke(1.dp, Color(0xFFFFB800).copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "#${card.importanceRank}",
                                    color = Color(0xFFFFB800),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        if (card.partOfSpeech.isNotBlank()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = card.partOfSpeech,
                                color = theme.textSecondary,
                                fontSize = 11.5.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { onToggleFavorite(card.id) },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = if (card.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (card.isFavorite) Color(0xFFFF4D4D) else theme.textSecondary.copy(alpha = 0.5f),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { onSpeakWord(card.word) },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Listen pronunciation",
                                tint = theme.primaryAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // FRONT SIDE: WORD & PHONETIC
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = card.word,
                        color = theme.textPrimary,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )

                    if (card.phonetic.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = card.phonetic,
                            color = theme.textSecondary,
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // FLIP / REVEAL CONTENT
                    AnimatedVisibility(visible = isFlipped) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(theme.glassSurface, RoundedCornerShape(16.dp))
                                .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = card.translation,
                                color = theme.primaryAccent,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            if (card.example.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "“${card.example}”",
                                    color = theme.textPrimary.copy(alpha = 0.9f),
                                    fontSize = 13.sp,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                            }

                            if (card.exampleTranslation.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = card.exampleTranslation,
                                    color = theme.textSecondary,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            if (card.definition.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "💡 ${card.definition}",
                                    color = theme.textSecondary.copy(alpha = 0.8f),
                                    fontSize = 11.5.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Flip Prompt
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Flip,
                            contentDescription = null,
                            tint = theme.primaryAccent.copy(alpha = 0.7f),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isFlipped) "Kartani yopish" else "Tarjimani ko'rish uchun bosing",
                            color = theme.primaryAccent.copy(alpha = 0.8f),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // FLASHCARD ACTION BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Repeat / Next button
            OutlinedButton(
                onClick = {
                    if (safeIndex < activeDeck.size - 1) {
                        currentIndex = safeIndex + 1
                    } else {
                        currentIndex = 0
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Qaytarish", color = theme.textPrimary, fontSize = 13.sp)
            }

            // Mastered Button
            Button(
                onClick = {
                    onMarkMastered(card.id)
                    if (safeIndex < activeDeck.size - 1) {
                        currentIndex = safeIndex
                    } else if (safeIndex > 0) {
                        currentIndex = safeIndex - 1
                    }
                },
                modifier = Modifier
                    .weight(1.4f)
                    .height(48.dp)
                    .testTag("btn_flashcard_mark_mastered"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("✅ Yodlandi!", color = Color.Black, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

/**
 * 🎯 USUL 2: SINOV & 4-VARIANT TEST MODE
 * Direct 4-choice interactive quiz on active vocabulary + AI Arena triggers.
 */
@Composable
private fun VocabInteractiveQuizMode(
    cards: List<VocabCard>,
    allCards: List<VocabCard>,
    isQuizPassedToday: Boolean,
    quizFailedWordIds: Set<String>,
    onSpeakWord: (String) -> Unit,
    onOpenFullQuiz: (Boolean) -> Unit
) {
    val theme = LocalLiquidTheme.current
    if (cards.isEmpty()) return

    var currentIndex by remember(cards) { mutableIntStateOf(0) }
    val safeIndex = currentIndex.coerceIn(0, cards.size - 1)
    val currentCard = cards[safeIndex]

    var selectedOptionIndex by remember(currentCard.id) { mutableIntStateOf(-1) }
    var isSubmitted by remember(currentCard.id) { mutableStateOf(false) }
    var quizScore by remember { mutableIntStateOf(0) }

    // Generate 4 choices (1 correct, 3 distractors)
    val options = remember(currentCard.id) {
        val distractorTranslations = (allCards + cards)
            .filter { it.word.lowercase() != currentCard.word.lowercase() }
            .map { it.translation }
            .distinct()
            .shuffled()
            .take(3)
        val list = (distractorTranslations + currentCard.translation).shuffled()
        list
    }
    val correctIndex = options.indexOf(currentCard.translation)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // AI Arena Launcher Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
            border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.35f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🏆 Kundalik 10 Savolli Sinov",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                    Text(
                        text = if (isQuizPassedToday) "Bugungi test muvaffaqiyatli topshirildi (90%+)" else "90%+ ball to'plab so'zlarni avtomatik o'zlashtiring.",
                        fontSize = 11.5.sp,
                        color = theme.textSecondary
                    )
                }

                Button(
                    onClick = { onOpenFullQuiz(false) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Boshlash", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Retry Mistakes Card (if failed words exist)
        if (quizFailedWordIds.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800).copy(alpha = 0.12f)),
                border = BorderStroke(1.dp, Color(0xFFFF9800).copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "⚠️ Xato qilingan: ${quizFailedWordIds.size} ta so'z",
                        color = Color(0xFFFF9800),
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { onOpenFullQuiz(true) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Text("Xatolarni qayta yechish", color = Color.Black, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Quick Interactive 4-Choice Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
            border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Savol ${safeIndex + 1} / ${cards.size}",
                        color = theme.textSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Ball: $quizScore",
                        color = theme.primaryAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Prompt Word
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentCard.word,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary,
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { onSpeakWord(currentCard.word) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Speak",
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Text(
                    text = "Qaysi biri ushbu so'zning to'g'ri tarjimasi?",
                    fontSize = 12.sp,
                    color = theme.textSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 4 Options
                options.forEachIndexed { optIndex, optionText ->
                    val isChosen = optIndex == selectedOptionIndex
                    val isCorrectOption = optIndex == correctIndex

                    val borderColor = when {
                        !isSubmitted -> if (isChosen) theme.primaryAccent else theme.glassBorderSubtleColor
                        isCorrectOption -> Color(0xFF10B981)
                        isChosen -> Color(0xFFEF4444)
                        else -> theme.glassBorderSubtleColor
                    }

                    val bgColor = when {
                        !isSubmitted -> if (isChosen) theme.primaryAccent.copy(alpha = 0.15f) else theme.glassSurface
                        isCorrectOption -> Color(0xFF10B981).copy(alpha = 0.2f)
                        isChosen -> Color(0xFFEF4444).copy(alpha = 0.2f)
                        else -> theme.glassSurface
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(bgColor)
                            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                            .clickable(enabled = !isSubmitted) {
                                selectedOptionIndex = optIndex
                            }
                            .padding(horizontal = 14.dp, vertical = 11.dp)
                    ) {
                        Text(
                            text = optionText,
                            color = theme.textPrimary,
                            fontSize = 13.sp,
                            fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action button
                if (!isSubmitted) {
                    Button(
                        onClick = {
                            if (selectedOptionIndex != -1) {
                                isSubmitted = true
                                if (selectedOptionIndex == correctIndex) {
                                    quizScore += 10
                                }
                            }
                        },
                        enabled = selectedOptionIndex != -1,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Tekshirish", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = {
                            if (safeIndex < cards.size - 1) {
                                currentIndex = safeIndex + 1
                            } else {
                                currentIndex = 0
                            }
                            selectedOptionIndex = -1
                            isSubmitted = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Keyingi savol ➔", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * 🧩 USUL 3: JUFTLIKLAR (MATCH PAIRS) MODE
 * Match 4 English words with their Uzbek translations.
 */
@Composable
private fun VocabMatchPairsMode(
    cards: List<VocabCard>,
    onSpeakWord: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    if (cards.isEmpty()) return

    var roundIndex by remember { mutableIntStateOf(0) }
    val roundCards = remember(roundIndex, cards) {
        val start = (roundIndex * 4) % cards.size
        cards.drop(start).take(4).let { if (it.size < 4) cards.take(4) else it }
    }

    val englishList = remember(roundCards) { roundCards.shuffled() }
    val uzbekList = remember(roundCards) { roundCards.shuffled() }

    var selectedEnglishId by remember(roundCards) { mutableStateOf<String?>(null) }
    var selectedUzbekId by remember(roundCards) { mutableStateOf<String?>(null) }
    var matchedCardIds by remember(roundCards) { mutableStateOf<Set<String>>(emptySet()) }
    var mismatchPair by remember(roundCards) { mutableStateOf<Pair<String, String>?>(null) }
    var score by remember { mutableIntStateOf(0) }

    val isRoundComplete = matchedCardIds.size >= roundCards.size && roundCards.isNotEmpty()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header info
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
            border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🧩 So'z va Tarjima Juftligi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primaryAccent
                    )
                    Text(
                        text = "Inglizcha so'z va uning tarjimasini tanlab birlashtiring",
                        fontSize = 11.5.sp,
                        color = theme.textSecondary
                    )
                }
                Text(
                    text = "$score Ball",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981)
                )
            }
        }

        if (isRoundComplete) {
            // Round victory card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981).copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎉", fontSize = 38.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Barcha juftliklar topildi!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "+40 XP qo'shildi. Keyingi bosqichga o'tishga tayyormisiz?",
                        fontSize = 12.sp,
                        color = theme.textSecondary
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { roundIndex++ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Keyingi juftliklar ➔", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Side-by-side matching columns
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // English Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🇬🇧 Inglizcha", fontSize = 11.5.sp, color = theme.textSecondary, fontWeight = FontWeight.Bold)
                    englishList.forEach { card ->
                        val isMatched = matchedCardIds.contains(card.id)
                        val isSelected = selectedEnglishId == card.id
                        val isMismatched = mismatchPair?.first == card.id

                        val bg = when {
                            isMatched -> Color(0xFF10B981).copy(alpha = 0.2f)
                            isMismatched -> Color(0xFFEF4444).copy(alpha = 0.2f)
                            isSelected -> theme.primaryAccent.copy(alpha = 0.2f)
                            else -> theme.glassSurface
                        }
                        val border = when {
                            isMatched -> Color(0xFF10B981)
                            isMismatched -> Color(0xFFEF4444)
                            isSelected -> theme.primaryAccent
                            else -> theme.glassBorderSubtleColor
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bg)
                                .border(1.5.dp, border, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isMatched) {
                                    if (selectedUzbekId != null) {
                                        if (card.id == selectedUzbekId) {
                                            matchedCardIds = matchedCardIds + card.id
                                            score += 10
                                            onSpeakWord(card.word)
                                            selectedEnglishId = null
                                            selectedUzbekId = null
                                            mismatchPair = null
                                        } else {
                                            mismatchPair = Pair(card.id, selectedUzbekId!!)
                                            selectedEnglishId = null
                                            selectedUzbekId = null
                                        }
                                    } else {
                                        selectedEnglishId = card.id
                                        mismatchPair = null
                                    }
                                }
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = card.word,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isMatched) Color(0xFF10B981) else theme.textPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Uzbek Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🇺🇿 O'zbekcha", fontSize = 11.5.sp, color = theme.textSecondary, fontWeight = FontWeight.Bold)
                    uzbekList.forEach { card ->
                        val isMatched = matchedCardIds.contains(card.id)
                        val isSelected = selectedUzbekId == card.id
                        val isMismatched = mismatchPair?.second == card.id

                        val bg = when {
                            isMatched -> Color(0xFF10B981).copy(alpha = 0.2f)
                            isMismatched -> Color(0xFFEF4444).copy(alpha = 0.2f)
                            isSelected -> theme.primaryAccent.copy(alpha = 0.2f)
                            else -> theme.glassSurface
                        }
                        val border = when {
                            isMatched -> Color(0xFF10B981)
                            isMismatched -> Color(0xFFEF4444)
                            isSelected -> theme.primaryAccent
                            else -> theme.glassBorderSubtleColor
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bg)
                                .border(1.5.dp, border, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isMatched) {
                                    if (selectedEnglishId != null) {
                                        if (card.id == selectedEnglishId) {
                                            matchedCardIds = matchedCardIds + card.id
                                            score += 10
                                            onSpeakWord(card.word)
                                            selectedEnglishId = null
                                            selectedUzbekId = null
                                            mismatchPair = null
                                        } else {
                                            mismatchPair = Pair(selectedEnglishId!!, card.id)
                                            selectedEnglishId = null
                                            selectedUzbekId = null
                                        }
                                    } else {
                                        selectedUzbekId = card.id
                                        mismatchPair = null
                                    }
                                }
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = card.translation,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isMatched) Color(0xFF10B981) else theme.textPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * ✍️ USUL 4: YOZMA DIKTANT & IMLO (SPELLING) MODE
 * Audio + definition prompt, user writes the English spelling.
 */
@Composable
private fun VocabSpellingMode(
    cards: List<VocabCard>,
    onSpeakWord: (String) -> Unit,
    onMarkMastered: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    if (cards.isEmpty()) return

    var currentIndex by remember(cards) { mutableIntStateOf(0) }
    val safeIndex = currentIndex.coerceIn(0, cards.size - 1)
    val card = cards[safeIndex]

    var typedText by remember(card.id) { mutableStateOf("") }
    var isSubmitted by remember(card.id) { mutableStateOf(false) }
    var hintLettersCount by remember(card.id) { mutableIntStateOf(0) }

    val isCorrect = typedText.trim().equals(card.word.trim(), ignoreCase = true)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Diktant: ${safeIndex + 1} / ${cards.size}", fontSize = 12.sp, color = theme.textSecondary)
                Text(card.level.uppercase(), fontSize = 11.sp, color = theme.primaryAccent, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Meaning Prompt
            Text(
                text = card.translation,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = theme.textPrimary,
                textAlign = TextAlign.Center
            )

            if (card.definition.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "💡 ${card.definition}",
                    fontSize = 12.sp,
                    color = theme.textSecondary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Audio button
            OutlinedButton(
                onClick = { onSpeakWord(card.word) },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f))
            ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("🔊 Talaffuzni eshitish", color = theme.primaryAccent, fontSize = 12.5.sp)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Hint display if requested
            if (hintLettersCount > 0) {
                val hint = card.word.take(hintLettersCount)
                Text(
                    text = "Maslahat: $hint...",
                    color = Color(0xFFFFB800),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Input field
            OutlinedTextField(
                value = typedText,
                onValueChange = { if (!isSubmitted) typedText = it },
                placeholder = { Text("Inglizcha to'g'ri yozilishini kiriting...", color = theme.textSecondary, fontSize = 12.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = theme.primaryAccent,
                    unfocusedBorderColor = theme.glassBorderSubtleColor,
                    focusedTextColor = theme.textPrimary,
                    unfocusedTextColor = theme.textPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Feedback Banner
            if (isSubmitted) {
                if (isCorrect) {
                    Surface(
                        color = Color(0xFF10B981).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFF10B981))
                    ) {
                        Text(
                            text = "✅ Barakalla! To'g'ri yozdingiz: ${card.word}",
                            color = Color(0xFF10B981),
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                } else {
                    Surface(
                        color = Color(0xFFEF4444).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFFEF4444))
                    ) {
                        Text(
                            text = "❌ To'g'ri yozilishi: ${card.word}",
                            color = Color(0xFFEF4444),
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isSubmitted) {
                    if (hintLettersCount < card.word.length - 1) {
                        OutlinedButton(
                            onClick = { hintLettersCount++ },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                        ) {
                            Text("💡 Harf ochish", color = theme.textSecondary, fontSize = 12.sp)
                        }
                    }

                    Button(
                        onClick = { isSubmitted = true },
                        enabled = typedText.isNotBlank(),
                        modifier = Modifier.weight(1.3f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Tekshirish", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                    }
                } else {
                    if (isCorrect) {
                        Button(
                            onClick = {
                                onMarkMastered(card.id)
                                if (safeIndex < cards.size - 1) currentIndex = safeIndex + 1 else currentIndex = 0
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                        ) {
                            Text("✅ Yodlandi deb belgilash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    Button(
                        onClick = {
                            if (safeIndex < cards.size - 1) currentIndex = safeIndex + 1 else currentIndex = 0
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Keyingi so'z ➔", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                    }
                }
            }
        }
    }
}

/**
 * 🔤 USUL 5: HARFLARDAN TERISH (WORD SCRAMBLE) MODE
 * Shuffled letter chips to assemble the word in order.
 */
private data class ScrambleTile(val id: Int, val char: Char)

@Composable
private fun VocabWordScrambleMode(
    cards: List<VocabCard>,
    onSpeakWord: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    if (cards.isEmpty()) return

    var currentIndex by remember(cards) { mutableIntStateOf(0) }
    val safeIndex = currentIndex.coerceIn(0, cards.size - 1)
    val card = cards[safeIndex]

    val targetWord = remember(card.id) { card.word.filter { it.isLetter() }.uppercase() }
    val tiles = remember(card.id) {
        targetWord.mapIndexed { idx, c -> ScrambleTile(idx, c) }.shuffled()
    }

    var selectedTileIds by remember(card.id) { mutableStateOf<List<Int>>(emptyList()) }
    val assembledWord = selectedTileIds.map { id -> tiles.first { it.id == id }.char }.joinToString("")
    val isFilled = selectedTileIds.size == tiles.size
    val isCorrect = isFilled && assembledWord == targetWord

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Harflar: ${safeIndex + 1} / ${cards.size}", fontSize = 12.sp, color = theme.textSecondary)
                Text("O'zbekcha: ${card.translation}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Assembled Word Slot Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in targetWord.indices) {
                    val tileId = selectedTileIds.getOrNull(i)
                    val char = if (tileId != null) tiles.first { it.id == tileId }.char else ' '

                    val slotBorder = when {
                        !isFilled -> if (tileId != null) theme.primaryAccent else theme.glassBorderSubtleColor
                        isCorrect -> Color(0xFF10B981)
                        else -> Color(0xFFEF4444)
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (tileId != null) theme.primaryAccent.copy(alpha = 0.15f) else theme.glassSurface)
                            .border(1.5.dp, slotBorder, RoundedCornerShape(8.dp))
                            .clickable(enabled = tileId != null) {
                                if (tileId != null) {
                                    selectedTileIds = selectedTileIds - tileId
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char.toString(),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Available Letter Tiles Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tiles.forEach { tile ->
                    val isUsed = selectedTileIds.contains(tile.id)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isUsed) theme.glassSurface.copy(alpha = 0.3f) else theme.primaryAccent)
                            .border(
                                1.dp,
                                if (isUsed) theme.glassBorderSubtleColor else theme.primaryAccent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = !isUsed) {
                                selectedTileIds = selectedTileIds + tile.id
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tile.char.toString(),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUsed) theme.textSecondary.copy(alpha = 0.4f) else Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action Buttons
            if (isFilled) {
                if (isCorrect) {
                    Text("🎉 Ajoyib! So'z to'g'ri tuzildi!", color = Color(0xFF10B981), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            onSpeakWord(card.word)
                            if (safeIndex < cards.size - 1) currentIndex = safeIndex + 1 else currentIndex = 0
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Keyingi so'z ➔", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text("Noto'g'ri tuzildi. Qayta urinib ko'ring.", color = Color(0xFFEF4444), fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { selectedTileIds = emptyList() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Qayta tozalash", color = theme.textPrimary, fontSize = 12.5.sp)
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            if (selectedTileIds.isNotEmpty()) {
                                selectedTileIds = selectedTileIds.dropLast(1)
                            }
                        },
                        enabled = selectedTileIds.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                    ) {
                        Text("Orqaga", color = theme.textSecondary, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { selectedTileIds = emptyList() },
                        enabled = selectedTileIds.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                    ) {
                        Text("Tozalash", color = theme.textSecondary, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

/**
 * 🎧 USUL 6: AUDIO TRENAJOR (LISTENING) MODE
 * Word text is hidden. TTS plays the sound; user selects the correct word or meaning.
 */
@Composable
private fun VocabListeningMode(
    cards: List<VocabCard>,
    allCards: List<VocabCard>,
    onSpeakWord: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    if (cards.isEmpty()) return

    var currentIndex by remember(cards) { mutableIntStateOf(0) }
    val safeIndex = currentIndex.coerceIn(0, cards.size - 1)
    val currentCard = cards[safeIndex]

    var selectedIndex by remember(currentCard.id) { mutableIntStateOf(-1) }
    var isAnswered by remember(currentCard.id) { mutableStateOf(false) }

    // 4 Word Options (1 target, 3 distractors)
    val wordOptions = remember(currentCard.id) {
        val distractors = (allCards + cards)
            .filter { it.word.lowercase() != currentCard.word.lowercase() }
            .map { it.word }
            .distinct()
            .shuffled()
            .take(3)
        (distractors + currentCard.word).shuffled()
    }
    val correctIndex = wordOptions.indexOf(currentCard.word)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Audio: ${safeIndex + 1} / ${cards.size}", fontSize = 12.sp, color = theme.textSecondary)
                Text("🎧 Eshitib topish", fontSize = 12.sp, color = theme.primaryAccent, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Big Audio Play Button
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(theme.primaryAccent.copy(alpha = 0.2f))
                    .border(2.dp, theme.primaryAccent, CircleShape)
                    .clickable { onSpeakWord(currentCard.word) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Play Audio",
                    tint = theme.primaryAccent,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Ovozni eshitish uchun bosing", fontSize = 12.sp, color = theme.textSecondary)

            Spacer(modifier = Modifier.height(18.dp))

            // 4 Choices
            wordOptions.forEachIndexed { optIndex, wordText ->
                val isChosen = optIndex == selectedIndex
                val isCorrect = optIndex == correctIndex

                val borderColor = when {
                    !isAnswered -> if (isChosen) theme.primaryAccent else theme.glassBorderSubtleColor
                    isCorrect -> Color(0xFF10B981)
                    isChosen -> Color(0xFFEF4444)
                    else -> theme.glassBorderSubtleColor
                }

                val bgColor = when {
                    !isAnswered -> if (isChosen) theme.primaryAccent.copy(alpha = 0.15f) else theme.glassSurface
                    isCorrect -> Color(0xFF10B981).copy(alpha = 0.2f)
                    isChosen -> Color(0xFFEF4444).copy(alpha = 0.2f)
                    else -> theme.glassSurface
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgColor)
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .clickable(enabled = !isAnswered) {
                            selectedIndex = optIndex
                            isAnswered = true
                        }
                        .padding(horizontal = 14.dp, vertical = 11.dp)
                ) {
                    Text(
                        text = wordText,
                        fontSize = 14.sp,
                        fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Medium,
                        color = theme.textPrimary
                    )
                }
            }

            if (isAnswered) {
                Spacer(modifier = Modifier.height(12.dp))
                // Reveal translation & phonetic
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.glassSurface, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${currentCard.word} ${currentCard.phonetic}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primaryAccent
                    )
                    Text(
                        text = "Tarjima: ${currentCard.translation}",
                        fontSize = 12.5.sp,
                        color = theme.textPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (safeIndex < cards.size - 1) currentIndex = safeIndex + 1 else currentIndex = 0
                        selectedIndex = -1
                        isAnswered = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Keyingi so'z ➔", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * ⚡ USUL 7: TEZKOR BLITS (TRUE / FALSE) MODE
 * Rapid 10-round reflex check: True or False translation.
 */
@Composable
private fun VocabSpeedBlitzMode(
    cards: List<VocabCard>,
    allCards: List<VocabCard>,
    onSpeakWord: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    if (cards.isEmpty()) return

    var roundIndex by remember { mutableIntStateOf(0) }
    var blitzScore by remember { mutableIntStateOf(0) }
    var isSubmitted by remember { mutableStateOf(false) }
    var userChoice by remember { mutableStateOf<Boolean?>(null) }

    val safeIndex = roundIndex.coerceIn(0, cards.size - 1)
    val card = cards[safeIndex]

    // 50% chance real translation, 50% distractor
    val isTargetTrue = remember(roundIndex) { (roundIndex % 2 == 0) }
    val candidateTranslation = remember(roundIndex) {
        if (isTargetTrue) {
            card.translation
        } else {
            (allCards + cards)
                .filter { it.word != card.word }
                .map { it.translation }
                .firstOrNull() ?: "Kitob"
        }
    }

    val isFinished = roundIndex >= 10 || roundIndex >= cards.size

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isFinished) {
                Text("⚡", fontSize = 42.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Blits Sinovi Yakunlandi!", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Sizning natijangiz: $blitzScore ball", fontSize = 14.sp, color = theme.primaryAccent, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        roundIndex = 0
                        blitzScore = 0
                        isSubmitted = false
                        userChoice = null
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Qayta boshlash", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Blits: ${roundIndex + 1} / 10", fontSize = 12.sp, color = theme.textSecondary)
                    Text("Ball: $blitzScore", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = card.word,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.textPrimary,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text("ushbu ma'noni bildiradimi?", fontSize = 12.sp, color = theme.textSecondary)

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“$candidateTranslation”",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.primaryAccent
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!isSubmitted) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                userChoice = false
                                isSubmitted = true
                                if (!isTargetTrue) blitzScore += 10
                            },
                            modifier = Modifier.weight(1f).height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
                        ) {
                            Text("❌ Noto'g'ri", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                userChoice = true
                                isSubmitted = true
                                if (isTargetTrue) blitzScore += 10
                            },
                            modifier = Modifier.weight(1f).height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                        ) {
                            Text("✅ To'g'ri", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    val wasUserCorrect = (userChoice == isTargetTrue)
                    Surface(
                        color = if (wasUserCorrect) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, if (wasUserCorrect) Color(0xFF10B981) else Color(0xFFEF4444))
                    ) {
                        Text(
                            text = if (wasUserCorrect) "✅ To'g'ri topdingiz!" else "❌ Noto'g'ri! Haqiqiy tarjimasi: ${card.translation}",
                            color = if (wasUserCorrect) Color(0xFF10B981) else Color(0xFFEF4444),
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            roundIndex++
                            isSubmitted = false
                            userChoice = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                    ) {
                        Text("Keyingisi ➔", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * 📖 SEARCH & FILTER BAR FOR VAULT:
 * Real-time instant search with quick CEFR and Favorite filter chips.
 */
@Composable
private fun VocabSearchAndFilterHeader(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedFilter: String,
    onSelectFilter: (String) -> Unit,
    unmasteredCount: Int,
    favCount: Int,
    masteredCount: Int,
    todayCount: Int,
    selectedMasteredOffset: Int,
    onSelectMasteredOffset: (Int) -> Unit
) {
    val theme = LocalLiquidTheme.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Modern Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Inglizcha yoki o'zbekcha so'z qidiring...", color = theme.textSecondary, fontSize = 13.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = theme.textSecondary, modifier = Modifier.size(16.dp))
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_vocab_search"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = theme.glassSurface,
                unfocusedContainerColor = theme.glassSurface,
                focusedBorderColor = theme.primaryAccent,
                unfocusedBorderColor = theme.glassBorderSubtleColor,
                focusedTextColor = theme.textPrimary,
                unfocusedTextColor = theme.textPrimary
            )
        )

        // Filter Chips Row
        val filters = listOf(
            "ALL" to "Barchasi ($unmasteredCount)",
            "TODAY" to "🎯 Bugun ($todayCount)",
            "FAVORITE" to "⭐ Sevimlilar ($favCount)",
            "A1" to "A1",
            "A2" to "A2",
            "B1" to "B1",
            "B2" to "B2",
            "C1" to "C1",
            "MASTERED" to "✅ Yodlanganlar ($masteredCount)"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filters) { (key, title) ->
                val isSelected = selectedFilter == key
                val isFav = key == "FAVORITE"
                val isMaster = key == "MASTERED"

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            when {
                                isSelected && isFav -> Color(0xFFFFB800).copy(alpha = 0.25f)
                                isSelected && isMaster -> Color(0xFF10B981).copy(alpha = 0.25f)
                                isSelected -> theme.primaryAccent.copy(alpha = 0.25f)
                                else -> theme.glassSurface
                            }
                        )
                        .border(
                            1.dp,
                            when {
                                isSelected && isFav -> Color(0xFFFFB800)
                                isSelected && isMaster -> Color(0xFF10B981)
                                isSelected -> theme.primaryAccent
                                else -> theme.glassBorderSubtleColor
                            },
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelectFilter(key) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = title,
                        color = when {
                            isSelected && isFav -> Color(0xFFFFB800)
                            isSelected && isMaster -> Color(0xFF10B981)
                            isSelected -> theme.primaryAccent
                            else -> theme.textSecondary
                        },
                        fontSize = 11.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        // Sub-filter for Mastered by Date
        if (selectedFilter == "MASTERED") {
            val dateOptions = listOf(
                0 to "Bugun",
                -1 to "Kecha",
                -2 to TaskTimeEngine.getDisplayDateForOffset(-2),
                999 to "Barcha vaqt"
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(dateOptions) { (offset, label) ->
                    val isSel = selectedMasteredOffset == offset
                    Surface(
                        onClick = { onSelectMasteredOffset(offset) },
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSel) Color(0xFF10B981).copy(alpha = 0.2f) else theme.glassSurface,
                        border = BorderStroke(1.dp, if (isSel) Color(0xFF10B981) else theme.glassBorderSubtleColor)
                    ) {
                        Text(
                            text = label,
                            color = if (isSel) Color(0xFF10B981) else theme.textSecondary,
                            fontSize = 10.5.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Modern, High-Density Vocabulary Card Item in List.
 */
@Composable
private fun ModernVocabCardItem(
    card: VocabCard,
    onSpeak: () -> Unit,
    onMarkMastered: () -> Unit,
    onResetForReview: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var isExpanded by remember { mutableStateOf(false) }

    val levelColor = when (card.level.uppercase()) {
        "A1" -> Color(0xFF4CAF50)
        "A2" -> Color(0xFF009688)
        "B1" -> Color(0xFF2196F3)
        "B2" -> Color(0xFFFF9800)
        "C1" -> Color(0xFF9C27B0)
        else -> theme.primaryAccent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
        border = BorderStroke(1.dp, if (card.isMastered) Color(0xFF10B981).copy(alpha = 0.4f) else theme.glassBorderSubtleColor)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Level badge + Word + Phonetic
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = levelColor.copy(alpha = 0.16f),
                        border = BorderStroke(0.5.dp, levelColor.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = card.level.ifBlank { "A1" }.uppercase(),
                            color = levelColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    if (card.importanceRank in 1..5020) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "#${card.importanceRank}",
                            color = Color(0xFFFFB800),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = card.word,
                                color = theme.textPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            if (card.partOfSpeech.isNotBlank()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = card.partOfSpeech,
                                    color = theme.textSecondary,
                                    fontSize = 10.5.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                        if (card.phonetic.isNotBlank()) {
                            Text(
                                text = card.phonetic,
                                color = theme.textSecondary,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }

                // Right: Quick Actions
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Speak Audio
                    IconButton(
                        onClick = onSpeak,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Pronounce",
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Favorite
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = if (card.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (card.isFavorite) Color(0xFFFF4D4D) else theme.textSecondary.copy(alpha = 0.4f),
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Expand indicator
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = theme.textSecondary.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Uzbek Translation (Always visible for scanning)
            Text(
                text = card.translation,
                color = theme.primaryAccent,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Expanded Details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    if (card.example.isNotBlank()) {
                        Text(
                            text = "“${card.example}”",
                            color = theme.textPrimary.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }

                    if (card.exampleTranslation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Tarjima: ${card.exampleTranslation}",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                    }

                    if (card.definition.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ta'rif: ${card.definition}",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                    }

                    if (card.synonym.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Sinonim: ${card.synonym}",
                            color = theme.textSecondary.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Action buttons in expanded card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (card.isMastered) {
                            OutlinedButton(
                                onClick = onResetForReview,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Qayta o'rganish", color = theme.textSecondary, fontSize = 11.5.sp)
                            }
                        } else {
                            Button(
                                onClick = onMarkMastered,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("✅ Yodlandi", color = Color.Black, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = theme.textSecondary.copy(alpha = 0.5f),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Empty State for Search or Filters.
 */
@Composable
private fun VocabEmptyState(
    query: String,
    filter: String,
    onClearFilter: () -> Unit,
    onAddWords: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🔍", fontSize = 34.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (query.isNotBlank()) "Qidiruv bo'yicha so'z topilmadi" else "Bu toifada so'zlar mavjud emas",
                color = theme.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Filtrni tozalang yoki yangi so'zlar yuklang.",
                color = theme.textSecondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onClearFilter,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                ) {
                    Text("Filtrni tozalash", color = theme.textPrimary, fontSize = 12.sp)
                }

                Button(
                    onClick = onAddWords,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("➕ So'z qo'shish", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * 🎯 QUIZ ARENA SECTION:
 * Dedicated, beautifully gamified quiz hub.
 */
@Composable
private fun VocabQuizArenaSection(
    isQuizPassedToday: Boolean,
    quizFailedWordIds: Set<String>,
    onStartQuiz: (retryOnly: Boolean) -> Unit
) {
    val theme = LocalLiquidTheme.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Status Card
        if (isQuizPassedToday) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981).copy(alpha = 0.12f)),
                border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🏆", fontSize = 26.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Bugungi sinov muvaffaqiyatli topshirildi!",
                                color = Color(0xFF10B981),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Natija 90%+ ko'rsatkichga yetdi. Yangi sinov ertaga ochiladi.",
                                color = theme.textSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Retry Mistakes Card (if user has failed words)
        if (quizFailedWordIds.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF9800).copy(alpha = 0.12f)),
                border = BorderStroke(1.dp, Color(0xFFFF9800).copy(alpha = 0.45f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⚠️", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Xato qilingan so'zlar: ${quizFailedWordIds.size} ta",
                                color = Color(0xFFFF9800),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Ushbu so'zlarni qayta mustahkamlash uchun maxsus sinov.",
                                color = theme.textSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { onStartQuiz(true) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Xatolarni qayta yechish", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Main Quiz Launcher Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
            border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.35f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🧠", fontSize = 38.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kundalik AI Leksika Sinovi",
                    color = theme.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Bugungi o'rganilgan so'zlar va sandiqdagi faol kartalar bo'yicha 10 ta tezkor savol. 90%+ to'plagan so'zlar avtomatik yodlanganlar safiga o'tadi.",
                    color = theme.textSecondary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = { onStartQuiz(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_arena_start_quiz"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Icon(Icons.Default.Psychology, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sinovni boshlash (10 ta savol)", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * 🌐 INTERACTIVE MODULES BAR:
 * Reader, Speaking Room, 5 Jumla, and Murphy Grammar.
 */
@Composable
private fun VocabEnglishModulesBar(
    onOpenReader: () -> Unit,
    onOpenSpeakingRoom: () -> Unit,
    onOpenEveningCoach: () -> Unit,
    onOpenMurphy: () -> Unit,
    onOpenScriptStudio: () -> Unit = {},
    onOpenPictureChallenge: () -> Unit = {}
) {
    val theme = LocalLiquidTheme.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "⚡ Amaliyot Modullari:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = theme.primaryAccent
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(theme.primaryAccent.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .clickable { onOpenReader() }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📖 Reader", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(theme.accentSecondary.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                        .border(1.dp, theme.accentSecondary.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .clickable { onOpenSpeakingRoom() }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎙️ Speak", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF10B981).copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFF10B981).copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .clickable { onOpenEveningCoach() }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✍️ 5 Jumla", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF8B5CF6).copy(alpha = 0.15f), RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFF8B5CF6).copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .clickable { onOpenMurphy() }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🧩 Murphy", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFF59E0B).copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .clickable { onOpenScriptStudio() }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎧 Script", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                }
                Box(
                    modifier = Modifier
                        .weight(1.2f)
                        .background(Color(0xFFEC4899).copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0xFFEC4899).copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .clickable { onOpenPictureChallenge() }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🖼️ Rasm O'yini", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEC4899))
                }
            }
        }
    }
}

/**
 * 📥 UNIFIED ADD & IMPORT BOTTOM SHEET CONTENT:
 * 4 clear tabs: Single AI Word, File Upload, Oxford 3000 Curated, Raw Text.
 */
@Composable
private fun VocabAddImportSheetContent(
    isLoading: Boolean,
    isDocumentParsing: Boolean,
    documentStatus: String,
    onAddWordWithAi: (String) -> Unit,
    onPickFile: () -> Unit,
    onLoadSampleCefr: () -> Unit,
    onImportText: (String) -> Unit,
    onClose: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var selectedTab by remember { mutableStateOf("AI") } // AI, FILE, OXFORD, TEXT

    var singleInput by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📥 Sandiqqa so'zlar qo'shish",
                color = theme.textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Clear, contentDescription = "Close", tint = theme.textSecondary)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tabs
        val tabs = listOf(
            "AI" to "⚡ AI Tezkor",
            "FILE" to "📄 Fayl",
            "OXFORD" to "🎓 Oksford 3000",
            "TEXT" to "📋 Matn"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tabs.forEach { (tabKey, label) ->
                val isSel = selectedTab == tabKey
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSel) theme.primaryAccent else theme.glassSurface)
                        .border(1.dp, if (isSel) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(10.dp))
                        .clickable { selectedTab = tabKey }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isSel) Color.Black else theme.textPrimary,
                        fontSize = 11.sp,
                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            "AI" -> {
                Text(
                    text = "Istalgan inglizcha so'z kiriting. Tizim uning aniq tarjimasi, CEFR darajasi va misolini tayyorlaydi:",
                    color = theme.textSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = singleInput,
                    onValueChange = { singleInput = it },
                    placeholder = { Text("Masalan: Diligent, Resilient, Overcome...", color = theme.textSecondary) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_add_single_word"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = theme.primaryAccent,
                        unfocusedBorderColor = theme.glassBorderSubtleColor,
                        focusedTextColor = theme.textPrimary,
                        unfocusedTextColor = theme.textPrimary
                    )
                )
                if (isLoading) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(color = theme.primaryAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI tahlil qilmoqda...", color = theme.primaryAccent, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (singleInput.isNotBlank()) {
                            onAddWordWithAi(singleInput.trim())
                            singleInput = ""
                        }
                    },
                    enabled = singleInput.isNotBlank() && !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Sandiqqa qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            "FILE" -> {
                Text(
                    text = "PDF, DOCX yoki TXT hujjatini yuklang. So'zlar avtomatik A1–C1 darajalariga ajratiladi:",
                    color = theme.textSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPickFile() }
                        .testTag("btn_upload_file_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    border = BorderStroke(1.5.dp, theme.primaryAccent.copy(alpha = 0.6f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isDocumentParsing) {
                            CircularProgressIndicator(color = theme.primaryAccent, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = documentStatus.ifBlank { "Hujjat tahlil qilinmoqda..." },
                                color = theme.primaryAccent,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Icon(Icons.Default.CloudUpload, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Faylni tanlash uchun bosing", color = theme.textPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("PDF, Word (DOCX), TXT formatlari", color = theme.textSecondary, fontSize = 11.sp)
                        }
                    }
                }
            }

            "OXFORD" -> {
                Text(
                    text = "Oksford 3000™ va 5000™ lug'at fondi (1500+ so'z) — kundalik hayotda eng ko'p ishlatiladigan CEFR standartidagi barcha leksika.",
                    color = theme.textSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onLoadSampleCefr,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Icon(Icons.Default.School, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Oksford to'plamini sandiqqa yuklash", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            "TEXT" -> {
                Text(
                    text = "So'zlar ro'yxatini yoki maqola matnini joylashtiring:",
                    color = theme.textSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = { Text("abandon v. B2\nability n. A2\nperseverance n. C1", color = theme.textSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = theme.primaryAccent,
                        unfocusedBorderColor = theme.glassBorderSubtleColor,
                        focusedTextColor = theme.textPrimary,
                        unfocusedTextColor = theme.textPrimary
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (textInput.isNotBlank()) {
                            onImportText(textInput.trim())
                            textInput = ""
                        }
                    },
                    enabled = textInput.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Matnni import qilish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Modern AI Quiz Dialog with interactive option selection, real-time feedback, and score calculations.
 */
@Composable
fun AiQuizDialog(
    questions: List<QuizQuestion>,
    isLoading: Boolean,
    onCompleteQuiz: (correctCardIds: List<String>, failedCardIds: List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }

    val correctCardIds = remember { mutableStateListOf<String>() }
    val failedCardIds = remember { mutableStateListOf<String>() }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = theme.dialogSurface,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🧠", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Lug'at Sinovi", color = theme.primaryAccent, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = theme.primaryAccent)
                    Spacer(modifier = Modifier.height(14.dp))
                    Text("Sinov savollari tayyorlanmoqda...", color = theme.textSecondary, fontSize = 12.sp)
                }
            } else if (questions.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("⚠️ Sinov uchun yetarli so'zlar topilmadi.", color = theme.textSecondary, fontSize = 12.sp, textAlign = TextAlign.Center)
                }
            } else if (currentIndex >= questions.size) {
                // Results screen
                val total = questions.size
                val percent = if (total > 0) (score * 100) / total else 0
                val isPassed = percent >= 90

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(if (isPassed) "🎉" else "📝", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isPassed) "Tabriklaymiz! Sinov topshirildi!" else "Sinov yakunlandi",
                        color = theme.textPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        color = if (isPassed) Color(0xFF10B981).copy(alpha = 0.2f) else Color(0xFFFF9800).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, if (isPassed) Color(0xFF10B981) else Color(0xFFFF9800)),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Natija: $score / $total ($percent%)",
                            color = if (isPassed) Color(0xFF10B981) else Color(0xFFFF9800),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (isPassed) {
                        Text(
                            text = "✅ 90% dan yuqori natija! To'g'ri topilgan so'zlar yodlanganlar safiga o'tkazildi.",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "⚠️ Kamida 90% to'g'ri bo'lishi lozim.\nTopa olmagan ${failedCardIds.size} ta so'zni 'Xatolarni qayta yechish' orqali topshirishingiz mumkin.",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                val q = questions[currentIndex]
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Savol ${currentIndex + 1} / ${questions.size}",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                        Text(
                            text = "Ball: $score",
                            color = theme.primaryAccent,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = q.question,
                        color = theme.textPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    q.options.forEachIndexed { optIndex, optionText ->
                        val isCorrect = optIndex == q.correctIndex
                        val isUserChoice = optIndex == selectedOption

                        val optBorderColor = when {
                            !isAnswerSubmitted -> if (isUserChoice) theme.primaryAccent else theme.glassBorderSubtleColor
                            isCorrect -> Color(0xFF10B981)
                            isUserChoice -> Color(0xFFE53935)
                            else -> theme.glassBorderSubtleColor
                        }

                        val optBgColor = when {
                            !isAnswerSubmitted -> if (isUserChoice) theme.primaryAccent.copy(alpha = 0.15f) else theme.glassSurface
                            isCorrect -> Color(0xFF10B981).copy(alpha = 0.2f)
                            isUserChoice -> Color(0xFFE53935).copy(alpha = 0.2f)
                            else -> theme.glassSurface
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(optBgColor)
                                .border(1.dp, optBorderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswerSubmitted) {
                                    selectedOption = optIndex
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = optionText,
                                color = theme.textPrimary,
                                fontSize = 13.sp,
                                fontWeight = if (isUserChoice) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    if (isAnswerSubmitted && q.explanation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "💡 ${q.explanation}",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (questions.isNotEmpty() && currentIndex < questions.size) {
                if (!isAnswerSubmitted) {
                    Button(
                        onClick = {
                            if (selectedOption != -1) {
                                isAnswerSubmitted = true
                                val currentQ = questions[currentIndex]
                                if (selectedOption == currentQ.correctIndex) {
                                    score++
                                    if (currentQ.cardId.isNotBlank() && !correctCardIds.contains(currentQ.cardId)) {
                                        correctCardIds.add(currentQ.cardId)
                                    }
                                } else {
                                    if (currentQ.cardId.isNotBlank() && !failedCardIds.contains(currentQ.cardId)) {
                                        failedCardIds.add(currentQ.cardId)
                                    }
                                }
                            }
                        },
                        enabled = selectedOption != -1,
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Tekshirish", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = {
                            currentIndex++
                            selectedOption = -1
                            isAnswerSubmitted = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Keyingisi", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Button(
                    onClick = {
                        onCompleteQuiz(correctCardIds.toList(), failedCardIds.toList())
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Natijani saqlash", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            if (currentIndex < questions.size) {
                TextButton(onClick = onDismiss) {
                    Text("Chiqish", color = theme.textSecondary)
                }
            }
        }
    )
}
