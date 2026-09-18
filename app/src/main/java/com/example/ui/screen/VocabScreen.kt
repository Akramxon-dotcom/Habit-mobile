package com.example.ui.screen

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val context = LocalContext.current

    // View tab: "TODAY" (Bugungi me'yor), "PLAN" (1 Oylik Reja), "VAULT" (Sandiqdagi barcha so'zlar), "MASTERED" (Yodlanganlar)
    var selectedViewMode by remember { mutableStateOf("TODAY") }
    var selectedLevelFilter by remember { mutableStateOf("ALL") }
    var selectedMasteredDateOffset by remember { mutableIntStateOf(0) } // 0 = Bugun, -1 = Kecha, etc., 999 = Barchasi
    var selectedPlanSubSection by remember { mutableStateOf("TASKS") }

    // Dialog states
    var showUploadModal by remember { mutableStateOf(false) }
    var showAddSingleDialog by remember { mutableStateOf(false) }
    var showManualTextDialog by remember { mutableStateOf(false) }
    var showQuizDialog by remember { mutableStateOf(false) }

    var singleWordInput by remember { mutableStateOf("") }
    var manualTextInput by remember { mutableStateOf("") }

    // Document picker
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
        }
    }

    val unmasteredCards = cards.filter { !it.isMastered }
    val masteredCards = cards.filter { it.isMastered }
    val totalCount = cards.size
    val masteredCount = masteredCards.size

    // Today's active cards based on current batch tracking
    val todayCards = if (todayBatchCardIds.isNotEmpty()) {
        val batchSet = todayBatchCardIds.toSet()
        val inBatch = unmasteredCards.filter { it.id in batchSet }
        if (inBatch.isNotEmpty()) inBatch else unmasteredCards.take(dailyGoal)
    } else {
        unmasteredCards.take(dailyGoal)
    }

    // Mastered cards filtered by selected date
    val filteredMasteredCards = if (selectedMasteredDateOffset == 999) {
        masteredCards
    } else {
        val targetIso = TaskTimeEngine.getIsoDateForOffset(selectedMasteredDateOffset)
        masteredCards.filter { it.learnedDate == targetIso }
    }

    val activeDisplayCards = when (selectedViewMode) {
        "TODAY" -> todayCards
        "MASTERED" -> filteredMasteredCards
        else -> {
            // "VAULT" (Sandiqdagi so'zlar) with level filter
            if (selectedLevelFilter == "ALL") unmasteredCards
            else unmasteredCards.filter { it.level.equals(selectedLevelFilter, ignoreCase = true) }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. TOP HEADER & ACTION BUTTONS (Sinov & ➕ Yuklash mini-window)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "📚 Lug'at",
                        color = theme.textPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                    )
                    Text(
                        text = "A1–C1 darajalar · Sandiq tizimi",
                        color = theme.textSecondary,
                        fontSize = 12.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // SINOV / QAYTA YECHISH TUGMASI
                    if (isQuizPassedToday) {
                        Button(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "✅ Bugungi sinov muvaffaqiyatli topshirilgan (90%+). Yangi sinov ertaga ochiladi!",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32).copy(alpha = 0.22f)),
                            border = BorderStroke(1.dp, Color(0xFF4CAF50)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("btn_quiz_passed")
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = "Sinov topshirildi",
                                    color = Color(0xFF4CAF50),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Ertaga yangi sinov",
                                    color = theme.textSecondary,
                                    fontSize = 8.5.sp
                                )
                            }
                        }
                    } else if (quizFailedWordIds.isNotEmpty()) {
                        Button(
                            onClick = {
                                showQuizDialog = true
                                onGenerateQuiz(true)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100).copy(alpha = 0.25f)),
                            border = BorderStroke(1.dp, Color(0xFFFF9800)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("btn_retry_quiz")
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = "Qayta yechish",
                                    color = Color(0xFFFF9800),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${quizFailedWordIds.size} ta xato so'z",
                                    color = theme.textSecondary,
                                    fontSize = 8.5.sp
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = {
                                showQuizDialog = true
                                onGenerateQuiz(false)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurfaceElevated),
                            border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("btn_start_quiz")
                        ) {
                            Icon(
                                Icons.Default.Psychology,
                                contentDescription = null,
                                tint = theme.primaryAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Sinov",
                                color = theme.textPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // YUKLASH TUGMASI (Opens upload mini-window in top right)
                    Button(
                        onClick = { showUploadModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("btn_open_upload_modal")
                    ) {
                        Icon(
                            Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Yuklash",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 2. ENG YUQORIDA: KUNLIK YODLASH ME'YORI & SANDIQ PROGRESSI
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "🎯 Kunlik yodlash me'yori",
                                    color = theme.textPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                )
                                if (learnedToday >= dailyGoal && dailyGoal > 0) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("✅ Bajarildi!", color = theme.primaryAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(
                                text = "Bugun yodlandi: $learnedToday / $dailyGoal ta so'z",
                                color = theme.textSecondary,
                                fontSize = 12.sp
                            )
                        }

                        // Stepper: [-] and [+] with immediate real-time updates (from 1 up to 200)
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
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
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
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("+", color = theme.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val progress = if (dailyGoal > 0) (learnedToday.toFloat() / dailyGoal).coerceIn(0f, 1f) else 0f
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = theme.primaryAccent,
                        trackColor = theme.primaryAccent.copy(alpha = 0.15f),
                        strokeCap = StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sandiq holati: displays vault status and distribution
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "📦 Sandiqda: ${unmasteredCards.size} ta so'z",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                        Text(
                            text = "✅ Jami yodlangan: $masteredCount / $totalCount",
                            color = theme.primaryAccent,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // 3. MAIN SECTION SELECTOR TABS: "Bugungi so'zlar", "1 Oylik Reja", "Sandiq", "Yodlanganlar"
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val tabs = listOf(
                    "TODAY" to "🎯 Bugun (${todayCards.size})",
                    "PLAN" to "🗓️ 1 Oylik Reja",
                    "VAULT" to "📦 Sandiq (${unmasteredCards.size})",
                    "MASTERED" to "✅ Yodlangan (${masteredCards.size})"
                )

                tabs.forEach { (mode, label) ->
                    val isSelected = selectedViewMode == mode
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) theme.primaryAccent else theme.glassSurface)
                            .border(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(12.dp))
                            .clickable { selectedViewMode = mode }
                            .padding(vertical = 9.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.Black else theme.textPrimary,
                            fontSize = 10.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        if (selectedViewMode == "PLAN") {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("⚡", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "Interaktiv Amaliyot Modullari:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.primaryAccent
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
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
                                Text("🎙️ Speaking", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
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
                        }
                    }
                }
            }

            monthlyPlanLazyItems(
                activeWeek = activeEnglishPlanWeek,
                completedTaskIds = completedEnglishPlanTaskIds,
                selectedSubSection = selectedPlanSubSection,
                onSelectSubSection = { selectedPlanSubSection = it },
                onSelectWeek = onSelectEnglishPlanWeek,
                onToggleTask = onToggleEnglishPlanTask,
                theme = theme
            )
        } else {
            // 4. CEFR LEVEL FILTER CHIPS (Visible in Sandiq mode)
            if (selectedViewMode == "VAULT") {
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val levels = listOf(
                        "ALL" to "Barchasi (${unmasteredCards.size})",
                        "A1" to "A1 (${unmasteredCards.count { it.level.equals("A1", true) }})",
                        "A2" to "A2 (${unmasteredCards.count { it.level.equals("A2", true) }})",
                        "B1" to "B1 (${unmasteredCards.count { it.level.equals("B1", true) }})",
                        "B2" to "B2 (${unmasteredCards.count { it.level.equals("B2", true) }})",
                        "C1" to "C1 (${unmasteredCards.count { it.level.equals("C1", true) }})"
                    )
                    items(levels) { (lvlKey, label) ->
                        val isSelected = selectedLevelFilter == lvlKey
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) theme.primaryAccent.copy(alpha = 0.2f) else theme.glassSurface)
                                .border(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(10.dp))
                                .clickable { selectedLevelFilter = lvlKey }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) theme.primaryAccent else theme.textSecondary,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // 4b. DATE FILTER CHIPS (Visible in Yodlanganlar / Mastered mode)
        if (selectedViewMode == "MASTERED") {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "📅 Yodlangan so'zlar tarixi (Kunlar bo'yicha):",
                        color = theme.textSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val todayIso = TaskTimeEngine.getIsoDateForOffset(0)
                        val yesterdayIso = TaskTimeEngine.getIsoDateForOffset(-1)
                        val dateOptions = listOf(
                            0 to "Bugun (${masteredCards.count { it.learnedDate == todayIso }})",
                            -1 to "Kecha (${masteredCards.count { it.learnedDate == yesterdayIso }})",
                            -2 to "${TaskTimeEngine.getDisplayDateForOffset(-2)} (${masteredCards.count { it.learnedDate == TaskTimeEngine.getIsoDateForOffset(-2) }})",
                            -3 to "${TaskTimeEngine.getDisplayDateForOffset(-3)} (${masteredCards.count { it.learnedDate == TaskTimeEngine.getIsoDateForOffset(-3) }})",
                            999 to "Barchasi ($masteredCount)"
                        )
                        items(dateOptions) { (offset, label) ->
                            val isDateSelected = selectedMasteredDateOffset == offset
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isDateSelected) theme.primaryAccent.copy(alpha = 0.25f) else theme.glassSurface)
                                    .border(1.dp, if (isDateSelected) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(10.dp))
                                    .clickable { selectedMasteredDateOffset = offset }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = label,
                                    color = if (isDateSelected) theme.primaryAccent else theme.textSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = if (isDateSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. VOCABULARY CARDS LIST
        if (activeDisplayCards.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📦", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = when (selectedViewMode) {
                                "TODAY" -> if (unmasteredCards.isEmpty()) "Sandiqda barcha so'zlar yodlab bo'lingan!" else "Bugungi faol so'zlar tugadi"
                                "MASTERED" -> if (selectedMasteredDateOffset == 999) "Hali yodlangan so'zlar yo'q" else "Ushbu kunda yodlangan so'zlar topilmadi"
                                else -> "Sandiqda so'zlar topilmadi"
                            },
                            color = theme.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (selectedViewMode == "TODAY")
                                "Yangi so'zlar qo'shish uchun quyidagi 'Yana yodlash' tugmasini bosing."
                            else
                                "Yuqoridagi 'Yuklash' tugmasi orqali yangi so'zlar yuklang.",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        if (selectedViewMode == "TODAY" && unmasteredCards.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = onAddMoreDailyWords,
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("btn_empty_add_more_daily_words")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("➕ Yana yodlash (+$dailyGoal)", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        } else if (selectedViewMode != "MASTERED") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { showUploadModal = true },
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.CloudUpload, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("So'zlar yuklash", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        } else {
            items(activeDisplayCards, key = { it.id }) { card ->
                CefrVocabCardItem(
                    card = card,
                    onDelete = { onDeleteWord(card.id) },
                    onMarkMastered = { onMarkMastered(card.id) },
                    onResetForReview = { onResetForReview(card.id) }
                )
            }
        }

        // 6. ACTION PANEL AT BOTTOM OF TODAY LIST
        if (selectedViewMode == "TODAY") {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (todayCards.isEmpty()) "🎉 Bugungi barcha so'zlar yodlandi!" else "💡 Bugungi qolgan so'zlar: ${todayCards.size} ta",
                            color = theme.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (todayCards.isEmpty())
                                "Ajoyib natija! Yana yangi so'zlarni hoziroq o'rganishni istasangiz:"
                            else
                                "Qolgan so'zlar bilan birga navbatdagi so'zlarni ham qo'shib yodlash:",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onAddMoreDailyWords,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                modifier = Modifier
                                    .weight(1.3f)
                                    .testTag("btn_bottom_add_more_daily_words")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("➕ Yana yodlash (+$dailyGoal)", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    showQuizDialog = true
                                    onGenerateQuiz(quizFailedWordIds.isNotEmpty())
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurface),
                                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_bottom_quiz")
                            ) {
                                Icon(Icons.Default.Psychology, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (quizFailedWordIds.isNotEmpty()) "Qayta sinov" else "Sinov",
                                    color = theme.textPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // ==========================================
    // MINI-WINDOW: YUKLASH OYNACHASI (Upload Dialog)
    // ==========================================
    if (showUploadModal) {
        AlertDialog(
            onDismissRequest = { showUploadModal = false },
            containerColor = theme.dialogSurface,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Inventory2, contentDescription = null, tint = theme.primaryAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "📥 Sandiqqa so'zlar yuklash",
                        color = theme.textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PDF, DOCX yoki matn faylini yuklang. So'zlar A1–C1 darajalariga ajratilib sandiqqa joylanadi.",
                        color = theme.textSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // File Tap / Drop Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                docPickerLauncher.launch(
                                    arrayOf(
                                        "application/pdf",
                                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                        "application/msword",
                                        "text/plain",
                                        "*/*"
                                    )
                                )
                            }
                            .testTag("btn_modal_file_picker"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                        border = BorderStroke(1.5.dp, theme.primaryAccent.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(theme.primaryAccent.copy(alpha = 0.15f))
                                    .border(1.dp, theme.primaryAccent.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDocumentParsing) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = theme.primaryAccent,
                                        strokeWidth = 2.5.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.CloudUpload,
                                        contentDescription = "Fayl yuklash",
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = if (isDocumentParsing) "Hujjat o'rganilmoqda..." else "Faylni tanlash uchun bosing",
                                color = theme.textPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isDocumentParsing) documentStatus.ifBlank { "So'zlar tahlil qilinmoqda..." } else "PDF, DOCX, TXT formatlar qo'llab-quvvatlanadi",
                                color = theme.textSecondary,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Secondary Quick-Options
                    OutlinedButton(
                        onClick = {
                            onLoadSampleCefr()
                            showUploadModal = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.4f)),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
                    ) {
                        Icon(Icons.Default.School, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Oksford 3000™ (1500+ so'z) to'plamini sandiqqa yuklash", color = theme.textPrimary, fontSize = 11.5.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                showUploadModal = false
                                showManualTextDialog = true
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, theme.glassBorderSubtle),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Matn qo'yish", color = theme.textSecondary, fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                showUploadModal = false
                                showAddSingleDialog = true
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, theme.glassBorderSubtle),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Yagona so'z", color = theme.textPrimary, fontSize = 11.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showUploadModal = false }) {
                    Text("Yopish", color = theme.primaryAccent, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Modal Dialog: Single Word Add (AI-powered)
    if (showAddSingleDialog) {
        AlertDialog(
            onDismissRequest = { showAddSingleDialog = false },
            containerColor = theme.dialogSurface,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = theme.primaryAccent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Yagona so'z qo'shish (AI)", color = theme.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "Inglizcha so'z kiriting. Tizim uning tarjimasi, CEFR darajasi va misolini tayyorlaydi.",
                        color = theme.textSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = singleWordInput,
                        onValueChange = { singleWordInput = it },
                        placeholder = { Text("Masalan: Diligent", color = theme.textSecondary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_new_word"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = theme.textPrimary,
                            unfocusedTextColor = theme.textPrimary,
                            focusedBorderColor = theme.primaryAccent,
                            unfocusedBorderColor = theme.glassBorderSubtleColor,
                            cursorColor = theme.primaryAccent
                        )
                    )
                    if (isLoading) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(color = theme.primaryAccent, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tahlil qilinmoqda...", color = theme.primaryAccent, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (singleWordInput.isNotBlank()) {
                            onAddWordWithAi(singleWordInput.trim())
                            singleWordInput = ""
                            showAddSingleDialog = false
                        }
                    },
                    enabled = singleWordInput.isNotBlank() && !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sandiqqa qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddSingleDialog = false }) {
                    Text("Bekor qilish", color = theme.textSecondary)
                }
            }
        )
    }

    // Modal Dialog: Raw Text Paste
    if (showManualTextDialog) {
        AlertDialog(
            onDismissRequest = { showManualTextDialog = false },
            containerColor = theme.dialogSurface,
            title = {
                Text("📋 Matndan so'zlar yuklash", color = theme.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        text = "So'zlar ro'yxatini yoki CEFR darajali matnni joylashtiring (masalan: 'abandon v. B2', 'ability n. A2').",
                        color = theme.textSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = manualTextInput,
                        onValueChange = { manualTextInput = it },
                        placeholder = { Text("abandon v. B2\nability n. A2\nperseverance n. C1", color = theme.textSecondary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = theme.textPrimary,
                            unfocusedTextColor = theme.textPrimary,
                            focusedBorderColor = theme.primaryAccent,
                            unfocusedBorderColor = theme.glassBorderSubtleColor,
                            cursorColor = theme.primaryAccent
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (manualTextInput.isNotBlank()) {
                            onImportText(manualTextInput.trim(), "Qo'lda kiritilgan")
                            manualTextInput = ""
                            showManualTextDialog = false
                        }
                    },
                    enabled = manualTextInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sandiqqa qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualTextDialog = false }) {
                    Text("Yopish", color = theme.textSecondary)
                }
            }
        )
    }

    // SINOV MODAL DIALOGI
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

@Composable
fun CefrVocabCardItem(
    card: VocabCard,
    onDelete: () -> Unit,
    onMarkMastered: () -> Unit,
    onResetForReview: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var isExpanded by remember { mutableStateOf(false) }

    val levelColor = when (card.level.uppercase()) {
        "A1" -> Color(0xFF4CAF50)
        "A2" -> Color(0xFF009688)
        "B1" -> Color(0xFF2196F3)
        "B2" -> Color(0xFFFF9800)
        "C1" -> Color(0xFF9C27B0)
        "C2" -> Color(0xFFE91E63)
        else -> theme.primaryAccent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
        border = BorderStroke(1.dp, if (card.isMastered) theme.primaryAccent.copy(alpha = 0.4f) else theme.glassBorderSubtleColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // CEFR Level Badge
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

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = card.word,
                                color = theme.textPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            )
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

                        if (card.phonetic.isNotBlank()) {
                            Text(
                                text = card.phonetic,
                                color = theme.textSecondary,
                                fontSize = 11.5.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (card.isMastered) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = theme.primaryAccent.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Yodlandi", color = theme.primaryAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "O'chirish", tint = theme.textSecondary.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Uzbek Translation
            Text(
                text = card.translation,
                color = theme.primaryAccent,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Example English sentence
            if (card.example.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Misol: \"${card.example}\"",
                    color = theme.textPrimary.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            // Uzbek Translation of the example sentence
            if (card.exampleTranslation.isNotBlank()) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Tarjimasi: ${card.exampleTranslation}",
                    color = theme.textSecondary,
                    fontSize = 11.5.sp
                )
            }

            // Expanded details: Synonym, Definition, Action
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    if (card.synonym.isNotBlank()) {
                        Text(
                            text = "Sinonim: ${card.synonym}",
                            color = theme.textSecondary,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    if (card.definition.isNotBlank()) {
                        Text(
                            text = "Ta'rif: ${card.definition}",
                            color = theme.textSecondary,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    if (card.sourceDocName.isNotBlank()) {
                        Text(
                            text = "Manba: ${card.sourceDocName}",
                            color = theme.textSecondary.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (card.isMastered) {
                            OutlinedButton(
                                onClick = onResetForReview,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, theme.glassBorderSubtle)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Qayta takrorlash", color = theme.textSecondary, fontSize = 11.5.sp)
                            }
                        } else {
                            Button(
                                onClick = onMarkMastered,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("✅ Yodlandi deb belgilash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

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
            Text("🧠 Lug'at Sinovi", color = theme.primaryAccent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                    Text("⚠️ Sinov uchun so'zlar topilmadi.", color = theme.textSecondary, fontSize = 12.sp, textAlign = TextAlign.Center)
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
                    Text(if (isPassed) "🎉" else "📝", fontSize = 38.sp)
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
                        color = if (isPassed) Color(0xFF4CAF50).copy(alpha = 0.2f) else Color(0xFFFF9800).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, if (isPassed) Color(0xFF4CAF50) else Color(0xFFFF9800)),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Natija: $score / $total ($percent%)",
                            color = if (isPassed) Color(0xFF4CAF50) else Color(0xFFFF9800),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (isPassed) {
                        Text(
                            text = "✅ 90% dan yuqori natija ko'rsatdingiz! Barcha to'g'ri topilgan so'zlar yodlanganlar safiga o'tkazildi va sandiqdan tozalandi. Bugungi sinov to'liq yakunlandi (Ertaga yangi sinov ochiladi).",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "⚠️ Talab: kamida 90% to'g'ri bo'lishi kerak.\nTo'g'ri topilgan ${correctCardIds.size} ta so'z yodlanganlar safiga o'tdi va sandiqdan tozalandi. Topa olmagan ${failedCardIds.size} ta so'zni esa 'Qayta yechish' orqali topshirishingiz mumkin.",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                val q = questions[currentIndex]
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Savol ${currentIndex + 1} / ${questions.size}",
                        color = theme.textSecondary,
                        fontSize = 11.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = q.question,
                        color = theme.textPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    q.options.forEachIndexed { optIndex, optionText ->
                        val isCorrect = optIndex == q.correctIndex
                        val isUserChoice = optIndex == selectedOption

                        val optBorderColor = when {
                            !isAnswerSubmitted -> if (isUserChoice) theme.primaryAccent else theme.glassBorderSubtleColor
                            isCorrect -> Color(0xFF4CAF50)
                            isUserChoice -> Color(0xFFE53935)
                            else -> theme.glassBorderSubtleColor
                        }

                        val optBgColor = when {
                            !isAnswerSubmitted -> if (isUserChoice) theme.primaryAccent.copy(alpha = 0.15f) else theme.glassSurface
                            isCorrect -> Color(0xFF4CAF50).copy(alpha = 0.18f)
                            isUserChoice -> Color(0xFFE53935).copy(alpha = 0.18f)
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
                            text = q.explanation,
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
                    Text("Natijani saqlash va tugatish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            if (currentIndex < questions.size) {
                TextButton(onClick = onDismiss) {
                    Text("Bekor qilish", color = theme.textSecondary)
                }
            }
        }
    )
}
