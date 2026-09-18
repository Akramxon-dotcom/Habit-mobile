package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.util.Oxford3000Database
import com.example.data.util.UniversalDictionary
import com.example.data.model.BookChapter
import com.example.data.model.GradedBook
import com.example.data.model.GradedReaderRepository
import com.example.data.remote.GeminiClient
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GradedReaderScreen(
    onBack: () -> Unit,
    onAddWordToVault: (word: String, uzbek: String, pos: String) -> Unit = { _, _, _ -> },
    onChapterCompleted: (bookTitle: String, chapterTitle: String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val speechManager = remember { SpeechManager(context) }

    var selectedBookIndex by remember { mutableIntStateOf(0) }
    var selectedChapterIndex by remember { mutableIntStateOf(0) }
    var fontSizeMultiplier by remember { mutableFloatStateOf(16f) }

    val currentBook = GradedReaderRepository.BOOKS.getOrElse(selectedBookIndex) { GradedReaderRepository.BOOKS[0] }
    val currentChapter = currentBook.chapters.getOrElse(selectedChapterIndex) { currentBook.chapters[0] }

    // Word bottom sheet lookup state
    var selectedWordForLookup by remember { mutableStateOf<String?>(null) }
    var wordPhonetic by remember { mutableStateOf("") }
    var wordUzbekDef by remember { mutableStateOf("") }
    var wordPartOfSpeech by remember { mutableStateOf("") }
    var aiExplanation by remember { mutableStateOf<String?>(null) }
    var isAiLoading by remember { mutableStateOf(false) }
    var isWordAddedToVault by remember { mutableStateOf(false) }

    var isChapterMarkedDone by remember { mutableStateOf(false) }

    fun lookupWord(rawWord: String, sentenceContext: String = "") {
        val cleanWord = rawWord.replace(Regex("[^a-zA-Z]"), "").lowercase()
        if (cleanWord.isBlank()) return

        selectedWordForLookup = cleanWord
        isWordAddedToVault = false
        aiExplanation = null

        // 1. Instant offline lookup in UniversalDictionary (Oxford 3000 + Irregular verbs + 1000+ words + lemmatizer)
        val match = UniversalDictionary.lookup(cleanWord, context)
        if (match != null) {
            wordUzbekDef = match.translationUz
            wordPhonetic = match.phonetic.ifBlank { "[${cleanWord}]" }
            wordPartOfSpeech = match.partOfSpeech.ifBlank { "vocabulary" }
            if (match.exampleSentence.isNotBlank()) {
                aiExplanation = "Misol: ${match.exampleSentence}\n${match.exampleTranslation}"
            }
            return
        }

        // 2. Also check Quick Dictionary from repository
        val qk = GradedReaderRepository.QUICK_DICTIONARY[cleanWord]
        if (qk != null) {
            wordUzbekDef = qk.first
            wordPhonetic = qk.second
            wordPartOfSpeech = "vocabulary"
            return
        }

        // 3. If word is not in static offline database, fetch instant translation via Gemini
        wordUzbekDef = "Ma'nosi aniqlanmoqda..."
        wordPhonetic = "[${cleanWord}]"
        wordPartOfSpeech = "English"

        scope.launch {
            val vocab = GeminiClient.lookupWordContextual(cleanWord, sentenceContext, context)
            wordUzbekDef = vocab.uzbekTranslation
            wordPhonetic = vocab.phonetic
            wordPartOfSpeech = vocab.partOfSpeech
            if (vocab.exampleSentence.isNotBlank()) {
                aiExplanation = "${vocab.definition}\n\nMisol: ${vocab.exampleSentence}\n💡 Maslahat: ${vocab.mnemonicTip}"
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "📖 Oxford Bookworms Reader",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                        Text(
                            text = "${currentBook.title} • ${currentBook.level}",
                            fontSize = 11.sp,
                            color = theme.primaryAccent
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Orqaga", tint = theme.textPrimary)
                    }
                },
                actions = {
                    // Font size toggle (14 -> 17 -> 20)
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(theme.glassSurface, RoundedCornerShape(8.dp))
                            .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(8.dp))
                            .clickable {
                                fontSizeMultiplier = when (fontSizeMultiplier) {
                                    15f -> 18f
                                    18f -> 21f
                                    else -> 15f
                                }
                            }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    ) {
                        Text("Aa ${fontSizeMultiplier.toInt()}sp", fontSize = 11.sp, color = theme.textPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = theme.bgTop
                )
            )
        },
        containerColor = theme.bgTop,
        modifier = modifier
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Books Carousel
            item {
                Text(
                    text = "Kitobni tanlang (Starter & Stage 1):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = theme.textSecondary,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(GradedReaderRepository.BOOKS.indices.toList()) { idx ->
                        val book = GradedReaderRepository.BOOKS[idx]
                        val isSelected = idx == selectedBookIndex
                        Card(
                            modifier = Modifier
                                .width(220.dp)
                                .clickable {
                                    selectedBookIndex = idx
                                    selectedChapterIndex = 0
                                    isChapterMarkedDone = false
                                },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) theme.primaryAccent.copy(alpha = 0.18f) else theme.glassSurface
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(book.iconEmoji, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            book.title,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = theme.textPrimary,
                                            maxLines = 1
                                        )
                                        Text(
                                            book.author,
                                            fontSize = 10.sp,
                                            color = theme.textSecondary,
                                            maxLines = 1
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(theme.accentSecondary.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(book.level, fontSize = 9.sp, color = theme.accentSecondary, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // 2. Chapter selector tabs
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentBook.chapters.forEachIndexed { chIdx, chapter ->
                        val isChSelected = chIdx == selectedChapterIndex
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isChSelected) theme.primaryAccent else theme.glassSurface,
                                    RoundedCornerShape(10.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isChSelected) theme.primaryAccent else theme.glassBorderSubtleColor,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    selectedChapterIndex = chIdx
                                    isChapterMarkedDone = false
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Bob ${chapter.chapterNumber}",
                                fontSize = 11.sp,
                                fontWeight = if (isChSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isChSelected) Color.Black else theme.textPrimary
                            )
                        }
                    }
                }
            }

            // 3. Instructions hint bar
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.primaryAccent.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💡", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Matndagi istalgan notanish so'z ustiga bosing — tarjimasi, talaffuzi chiqadi va shaxsiy lug'atingizga saqlanadi!",
                            fontSize = 11.sp,
                            color = theme.textPrimary,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // 4. Chapter Title & Synopsis
            item {
                Column {
                    Text(
                        text = currentChapter.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentBook.synopsis,
                        fontSize = 11.sp,
                        color = theme.textSecondary,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // 5. Interactive Paragraphs (clickable words)
            items(currentChapter.contentParagraphs) { paragraph ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtleColor)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val words = paragraph.split(" ")
                            words.forEach { word ->
                                val clean = word.replace(Regex("[^a-zA-Z]"), "").lowercase()
                                val isKeyVocab = currentChapter.targetVocabulary.contains(clean)

                                Text(
                                    text = "$word ",
                                    fontSize = fontSizeMultiplier.sp,
                                    lineHeight = (fontSizeMultiplier * 1.5).sp,
                                    color = if (isKeyVocab) theme.primaryAccent else theme.textPrimary,
                                    fontWeight = if (isKeyVocab) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier
                                        .clickable {
                                            lookupWord(word, paragraph)
                                        }
                                        .padding(vertical = 1.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        // Paragraph audio listen button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(theme.glassBorderSubtleColor, RoundedCornerShape(8.dp))
                                    .clickable {
                                        speechManager.speak(paragraph)
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = "Tinglash",
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Ovozli eshitish", fontSize = 10.sp, color = theme.textPrimary)
                                }
                            }
                        }
                    }
                }
            }

            // 6. Complete Chapter Action
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        isChapterMarkedDone = true
                        onChapterCompleted(currentBook.title, currentChapter.title)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isChapterMarkedDone) Color(0xFF10B981) else theme.primaryAccent
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        if (isChapterMarkedDone) Icons.Default.CheckCircle else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isChapterMarkedDone) "✅ Bob muvaffaqiyatli yakunlandi!" else "Ushbu bobni o'qib tugatdim (+XP)",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    // Modal Bottom Sheet for 1-Tap Dictionary Lookup
    if (selectedWordForLookup != null) {
        val word = selectedWordForLookup!!
        ModalBottomSheet(
            onDismissRequest = { selectedWordForLookup = null },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color(0xFF111B15),
            scrimColor = Color.Black.copy(alpha = 0.65f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = word.replaceFirstChar { it.uppercase() },
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.primaryAccent
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = wordPhonetic,
                                fontSize = 13.sp,
                                color = theme.textSecondary,
                                fontStyle = FontStyle.Italic
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .background(theme.glassBorderSubtleColor, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(wordPartOfSpeech, fontSize = 10.sp, color = theme.textPrimary)
                            }
                        }
                    }

                    // Audio pronounce button
                    IconButton(
                        onClick = { speechManager.speak(word) },
                        modifier = Modifier
                            .background(theme.primaryAccent, CircleShape)
                            .size(42.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Talaffuz", tint = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Meaning in Uzbek
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("O'zbekcha ma'nosi:", fontSize = 11.sp, color = theme.textSecondary)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = wordUzbekDef,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = theme.textPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action buttons: Add to Vault & Ask Gemini AI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onAddWordToVault(word, wordUzbekDef, wordPartOfSpeech)
                            isWordAddedToVault = true
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isWordAddedToVault) Color(0xFF10B981) else theme.primaryAccent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            if (isWordAddedToVault) Icons.Default.CheckCircle else Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            if (isWordAddedToVault) "Lug'atga qo'shildi!" else "Lug'atga qo'shish",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            isAiLoading = true
                            scope.launch {
                                val prompt = "Explain the English word '$word' for an A2 elementary learner in simple Uzbek language. Give 1 very simple example sentence with translation."
                                val result = GeminiClient.generateText(prompt, context)
                                aiExplanation = result.getOrNull() ?: "Izoh olib bo'lmadi."
                                isAiLoading = false
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtleColor),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("🤖 AI izohi", color = theme.textPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                AnimatedVisibility(visible = isAiLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = theme.primaryAccent, modifier = Modifier.size(24.dp))
                    }
                }

                if (aiExplanation != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = theme.primaryAccent.copy(alpha = 0.08f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.2f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("🤖 Gemini AI Tushuntirishi (A2):", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(aiExplanation!!, fontSize = 12.sp, color = theme.textPrimary, lineHeight = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
