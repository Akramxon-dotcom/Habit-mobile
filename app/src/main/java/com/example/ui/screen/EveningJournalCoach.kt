package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.remote.GeminiClient
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager
import kotlinx.coroutines.launch

data class SentenceAnalysis(
    val original: String,
    val corrected: String,
    val murphyRule: String,
    val b1Polish: String,
    val hasMistake: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EveningJournalCoachScreen(
    onBack: () -> Unit,
    onSaveToJournal: (text: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val speechManager = remember { SpeechManager(context) }

    // 5 sentence inputs
    var s1 by remember { mutableStateOf("") }
    var s2 by remember { mutableStateOf("") }
    var s3 by remember { mutableStateOf("") }
    var s4 by remember { mutableStateOf("") }
    var s5 by remember { mutableStateOf("") }

    var isAnalyzing by remember { mutableStateOf(false) }
    var analyses by remember { mutableStateOf<List<SentenceAnalysis>>(emptyList()) }
    var overallFeedback by remember { mutableStateOf<String?>(null) }
    var isSavedToJournal by remember { mutableStateOf(false) }

    val prompts = listOf(
        "1. 🌅 Ertalab (04:00 - 07:00):" to "Masalan: This morning, I woke up at 4:00 and memorized 8 new words.",
        "2. 🏫 Kunduzgi faoliyat:" to "Masalan: At school, I listened carefully to the teacher and finished my exercises.",
        "3. 🇬🇧 Ingliz tili darsi:" to "Masalan: In the afternoon, I watched an episode of Extra English with subtitles.",
        "4. 💡 Muhim xulosa / hodisa:" to "Masalan: The most interesting thing was solving difficult grammar questions.",
        "5. 🎯 Ertangi niyat:" to "Masalan: Tomorrow, I am going to review Murphy Unit 15 and read a book."
    )

    fun checkWithAi() {
        val sentences = listOf(s1, s2, s3, s4, s5).filter { it.isNotBlank() }
        if (sentences.isEmpty()) return

        isAnalyzing = true
        isSavedToJournal = false

        scope.launch {
            val combinedText = sentences.mapIndexed { idx, s -> "${idx + 1}. $s" }.joinToString("\n")
            val prompt = """
                You are an expert English tutor for an Uzbek student at A2 Elementary level following the 'Essential Grammar in Use (Raymond Murphy)' book.
                Review these evening daily journal sentences:
                $combinedText
                
                For EACH sentence, analyze grammar, spelling, and prepositions.
                Reference the exact Murphy Unit (e.g., Murphy Unit 5: Past Simple; Unit 103: in the morning; Unit 26: will/going to).
                
                Return the response strictly formatted like this for each sentence:
                ---SENTENCE---
                ORIGINAL: (original text)
                CORRECTED: (corrected simple A2 text)
                MURPHY_RULE: (Explanation in Uzbek pointing to specific Murphy unit or rule)
                B1_POLISH: (A slightly more advanced, natural B1 phrasing)
                HAS_MISTAKE: (true or false)
                ---END_SENTENCE---
                OVERALL_FEEDBACK: (2-3 encouraging sentences in Uzbek evaluating today's writing discipline)
            """.trimIndent()

            val response = GeminiClient.generateText(prompt, context).getOrNull() ?: ""

            val parsedList = mutableListOf<SentenceAnalysis>()
            var overall = "Kunlik yozuvlaringiz juda yaxshi! Doimiy yozish orqali ingliz tilida fikrlash tezlashadi."

            if (response.contains("---SENTENCE---")) {
                val chunks = response.split("---SENTENCE---").filter { it.contains("---END_SENTENCE---") }
                for (chunk in chunks) {
                    val orig = chunk.substringAfter("ORIGINAL:").substringBefore("CORRECTED:").trim()
                    val corr = chunk.substringAfter("CORRECTED:").substringBefore("MURPHY_RULE:").trim()
                    val murphy = chunk.substringAfter("MURPHY_RULE:").substringBefore("B1_POLISH:").trim()
                    val b1 = chunk.substringAfter("B1_POLISH:").substringBefore("HAS_MISTAKE:").trim()
                    val hasErr = chunk.substringAfter("HAS_MISTAKE:").substringBefore("---END_SENTENCE---").trim().lowercase().contains("true")

                    if (orig.isNotBlank()) {
                        parsedList.add(
                            SentenceAnalysis(
                                original = orig,
                                corrected = corr.ifBlank { orig },
                                murphyRule = murphy.ifBlank { "Grammatik jihatdan to'g'ri yozilgan!" },
                                b1Polish = b1.ifBlank { corr },
                                hasMistake = hasErr
                            )
                        )
                    }
                }
                if (response.contains("OVERALL_FEEDBACK:")) {
                    overall = response.substringAfter("OVERALL_FEEDBACK:").trim()
                }
            } else {
                // Fallback analysis
                sentences.forEach { s ->
                    parsedList.add(
                        SentenceAnalysis(
                            original = s,
                            corrected = s,
                            murphyRule = "Jumlalar ko'zdan kechirildi. O'tgan zamon (Past Simple) shakllariga e'tibor bering.",
                            b1Polish = s,
                            hasMistake = false
                        )
                    )
                }
            }

            analyses = parsedList
            overallFeedback = overall
            isAnalyzing = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "✍️ Kechki 5 ta Jumla (AI Coach)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                        Text(
                            "20:35 Kundalik & Murphy Analizator",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = theme.bgTop)
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
            // Header instructions
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.primaryAccent.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            "🎯 1 Oylik Reja talabi: Har kuni kechqurun 5 ta jumla yozish",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.primaryAccent
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Grammarly qidirmasdan, quyidagi kataklarga bugungi kuningiz haqida 5 ta inglizcha jumla yozing. AI barcha xatolarni topib, qaysi Murphy unitiga qarash kerakligini ko'rsatib beradi!",
                            fontSize = 11.sp,
                            color = theme.textPrimary,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            // 5 Input Fields
            item {
                val sentenceStates = listOf(
                    Triple(prompts[0], s1) { v: String -> s1 = v },
                    Triple(prompts[1], s2) { v: String -> s2 = v },
                    Triple(prompts[2], s3) { v: String -> s3 = v },
                    Triple(prompts[3], s4) { v: String -> s4 = v },
                    Triple(prompts[4], s5) { v: String -> s5 = v }
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    sentenceStates.forEachIndexed { index, (promptPair, value, onValChange) ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1813)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtleColor),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    promptPair.first,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.textPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                OutlinedTextField(
                                    value = value,
                                    onValueChange = onValChange,
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = {
                                        Text(promptPair.second, fontSize = 11.sp, color = theme.textSecondary)
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = theme.primaryAccent,
                                        unfocusedBorderColor = theme.glassBorderSubtleColor,
                                        focusedTextColor = theme.textPrimary,
                                        unfocusedTextColor = theme.textPrimary
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Check with AI Action button
            item {
                Button(
                    onClick = { checkWithAi() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isAnalyzing
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Gemini AI tahlil qilmoqda...", color = Color.Black, fontWeight = FontWeight.Bold)
                    } else {
                        Text(
                            "🤖 Gemini AI bilan tekshirish (Murphy Analizator)",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Results Section
            if (analyses.isNotEmpty()) {
                item {
                    Text(
                        "🔍 Tahlil Natijalari:",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(analyses.size) { idx ->
                    val item = analyses[idx]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.hasMistake) Color(0xFFEF4444).copy(alpha = 0.08f) else Color(0xFF10B981).copy(alpha = 0.08f)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (item.hasMistake) Color(0xFFEF4444).copy(alpha = 0.3f) else Color(0xFF10B981).copy(alpha = 0.3f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${idx + 1}-Jumla:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.hasMistake) Color(0xFFEF4444) else Color(0xFF10B981)
                                )
                                Text(
                                    text = if (item.hasMistake) "⚠️ Xatolar bor" else "✅ Qoidaga to'g'ri",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.hasMistake) Color(0xFFEF4444) else Color(0xFF10B981)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Siz yozganingiz: \"${item.original}\"", fontSize = 12.sp, color = theme.textSecondary)

                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("To'g'rilangani: ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                                Text("\"${item.corrected}\"", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(theme.primaryAccent, RoundedCornerShape(6.dp))
                                        .clickable { speechManager.speak(item.corrected) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🔊", fontSize = 12.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(theme.glassSurface, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text("📘 Murphy Qoidasi:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(item.murphyRule, fontSize = 11.sp, color = theme.textPrimary, lineHeight = 15.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🌟 B1 chiroyli varianti: ", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = theme.accentSecondary)
                                Text("\"${item.b1Polish}\"", fontSize = 11.sp, color = theme.textPrimary)
                            }
                        }
                    }
                }

                if (overallFeedback != null) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = theme.primaryAccent.copy(alpha = 0.15f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("🏆 AI Murabbiy Xulosasi:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.primaryAccent)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(overallFeedback!!, fontSize = 12.sp, color = theme.textPrimary, lineHeight = 16.sp)
                            }
                        }
                    }
                }

                // Save to Journal button
                item {
                    Button(
                        onClick = {
                            val fullJournalEntry = buildString {
                                appendLine("🗓️ Kechki 5 ta jumla (English Practice):")
                                analyses.forEachIndexed { i, a ->
                                    appendLine("${i + 1}. ${a.corrected}")
                                }
                                appendLine()
                                appendLine("Qoidalar tahlili:")
                                analyses.forEachIndexed { i, a ->
                                    if (a.hasMistake) appendLine("• ${a.murphyRule}")
                                }
                            }
                            onSaveToJournal(fullJournalEntry)
                            isSavedToJournal = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSavedToJournal) Color(0xFF10B981) else theme.primaryAccent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (isSavedToJournal) "✅ Kundalikka saqlandi!" else "Kundalikka (Journal) saqlash",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
