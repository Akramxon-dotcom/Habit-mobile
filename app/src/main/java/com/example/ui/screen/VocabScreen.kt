package com.example.ui.screen

import android.net.Uri
import android.provider.OpenableColumns
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.data.model.VocabCard
import com.example.ui.theme.LocalLiquidTheme

@Composable
fun VocabScreen(
    cards: List<VocabCard>,
    isLoading: Boolean,
    dailyGoal: Int = 10,
    learnedToday: Int = 0,
    isDocumentParsing: Boolean = false,
    documentStatus: String = "",
    onSetDailyGoal: (Int) -> Unit = {},
    onImportDocument: (Uri, String) -> Unit = { _, _ -> },
    onImportText: (String, String) -> Unit = { _, _ -> },
    onLoadSampleCefr: () -> Unit = {},
    onMarkMastered: (String) -> Unit = {},
    onResetForReview: (String) -> Unit = {},
    onAddWordWithAi: (String) -> Unit,
    onManualAddWord: (VocabCard) -> Unit = {},
    onDeleteWord: (String) -> Unit,
    onUpdateBoxLevel: (String, Int) -> Unit,
    onGenerateQuiz: () -> Unit,
    quizQuestions: List<QuizQuestion>,
    isQuizLoading: Boolean,
    onCloseQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val context = LocalContext.current

    var selectedLevelFilter by remember { mutableStateOf("ALL") }
    var showAddDialog by remember { mutableStateOf(false) }
    var showManualTextDialog by remember { mutableStateOf(false) }
    var wordInput by remember { mutableStateOf("") }
    var manualTextInput by remember { mutableStateOf("") }
    var manualDocTitle by remember { mutableStateOf("Mening lug'atim") }
    var showQuizDialog by remember { mutableStateOf(false) }

    // File picker launcher for document upload (PDF, DOCX, TXT, CSV)
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

    val masteredCount = cards.count { it.isMastered }
    val totalCount = cards.size
    val allCompleted = totalCount > 0 && masteredCount == totalCount

    // Filter cards by CEFR level
    val filteredCards = when (selectedLevelFilter) {
        "ALL" -> cards
        "UNLEARNED" -> cards.filter { !it.isMastered }
        "MASTERED" -> cards.filter { it.isMastered }
        else -> cards.filter { it.level.equals(selectedLevelFilter, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Header
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "📚 Lug'at & CEFR Tizimi",
                        color = theme.textPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                    )
                    Text(
                        text = "A1–C2 darajalar bo'yicha intizomli so'z yodlash",
                        color = theme.textSecondary,
                        fontSize = 12.5.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            showQuizDialog = true
                            onGenerateQuiz()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurfaceElevated),
                        border = BorderStroke(1.dp, theme.glassBorderSubtle),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("btn_start_quiz")
                    ) {
                        Icon(Icons.Default.Psychology, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Quiz", color = theme.textPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 1. PRIMARY HERO COMPONENT: DOCUMENT UPLOAD DROP/TAP ZONE
        item {
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
                    .testTag("btn_upload_document_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.5.dp, theme.primaryAccent.copy(alpha = 0.55f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
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
                                contentDescription = "Hujjat yuklash",
                                tint = theme.primaryAccent,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (isDocumentParsing) "Hujjat o'rganilmoqda..." else "Hujjatni yuklash",
                        color = theme.textPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isDocumentParsing) "So'zlar va A1–C2 darajalari ajratilmoqda..." else "Faylni tanlash yoki shu yerga tashlash uchun bosing",
                        color = theme.textSecondary,
                        fontSize = 12.5.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Document formats chip & Sample loader
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = theme.glassSurface,
                            border = BorderStroke(1.dp, theme.glassBorderSubtle)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Description, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("PDF, DOCX, TXT", color = theme.textSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            onClick = onLoadSampleCefr,
                            shape = RoundedCornerShape(10.dp),
                            color = theme.primaryAccent.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.35f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.School, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("CEFR to'plami (Demo)", color = theme.primaryAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Secondary / Small input methods bar (As user requested: "kichik usul bo'lsin")
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showManualTextDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Matn nusxasini qo'yish", color = theme.textSecondary, fontSize = 11.5.sp)
                }

                OutlinedButton(
                    onClick = { showAddDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = theme.glassSurface)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Yagona so'z kiritish", color = theme.textPrimary, fontSize = 11.5.sp)
                }
            }
        }

        // 2. DAILY GOAL & LEARNING PROGRESS PANEL (MINIMUM 10 TA)
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
                                text = "Bugun: $learnedToday / $dailyGoal ta so'z (kamida 10 ta)",
                                color = theme.textSecondary,
                                fontSize = 12.sp
                            )
                        }

                        // Stepper: [-] and [+]
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                onClick = {
                                    if (dailyGoal > 10) onSetDailyGoal(dailyGoal - 5)
                                },
                                shape = CircleShape,
                                color = theme.glassSurface,
                                border = BorderStroke(1.dp, theme.glassBorderSubtle),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("−", color = if (dailyGoal > 10) theme.textPrimary else theme.textSecondary.copy(alpha = 0.3f), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Text(
                                text = "$dailyGoal",
                                color = theme.primaryAccent,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )

                            Surface(
                                onClick = { onSetDailyGoal(dailyGoal + 5) },
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "O'rganilishi lozim: ${(totalCount - masteredCount).coerceAtLeast(0)} ta so'z",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                        Text(
                            text = "Yodlangan: $masteredCount / $totalCount",
                            color = theme.primaryAccent,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // 3. ALL WORDS MASTERED NOTIFICATION CARD
        if (allCompleted) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.dialogSurface),
                    border = BorderStroke(1.5.dp, theme.primaryAccent)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🎉", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Siz barcha so'zlarni yodlab bo'ldingiz!",
                            color = theme.primaryAccent,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Hujjatdagi hamma so'zlar to'liq o'zlashtirildi. Bilimingizni yanada oshirish uchun yangi hujjat yuklang yoki yangi so'zlar qo'shing.",
                            color = theme.textSecondary,
                            fontSize = 12.5.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = {
                                docPickerLauncher.launch(arrayOf("*/*"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.CloudUpload, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Yangi so'zlarni qo'shing", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.5.sp)
                        }
                    }
                }
            }
        }

        // 4. CEFR LEVEL FILTER CHIPS
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val levels = listOf(
                    "ALL" to "Barchasi (${cards.size})",
                    "UNLEARNED" to "O'rganishda (${cards.count { !it.isMastered }})",
                    "A1" to "A1 (${cards.count { it.level.equals("A1", true) }})",
                    "A2" to "A2 (${cards.count { it.level.equals("A2", true) }})",
                    "B1" to "B1 (${cards.count { it.level.equals("B1", true) }})",
                    "B2" to "B2 (${cards.count { it.level.equals("B2", true) }})",
                    "C1" to "C1 (${cards.count { it.level.equals("C1", true) }})",
                    "C2" to "C2 (${cards.count { it.level.equals("C2", true) }})",
                    "MASTERED" to "Yodlangan (${masteredCount})"
                )
                items(levels) { (lvlKey, label) ->
                    val isSelected = selectedLevelFilter == lvlKey
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) theme.primaryAccent else theme.glassSurface)
                            .border(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor, RoundedCornerShape(14.dp))
                            .clickable { selectedLevelFilter = lvlKey }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.Black else theme.textPrimary,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // 5. CARDS LIST
        if (filteredCards.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📄", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (cards.isEmpty()) "Hali lug'at yuklanmagan" else "Ushbu bo'limda so'zlar yo'q",
                            color = theme.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Hujjat yuklash orqali barcha so'zlarni CEFR (A1–C2) darajalari bilan avtomatik qo'shing.",
                            color = theme.textSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(filteredCards, key = { it.id }) { card ->
                CefrVocabCardItem(
                    card = card,
                    onDelete = { onDeleteWord(card.id) },
                    onMarkMastered = { onMarkMastered(card.id) },
                    onResetForReview = { onResetForReview(card.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Modal Dialog: Single Word Add (AI-powered) - Uses SOLID OPAQUE dialogSurface
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
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
                        value = wordInput,
                        onValueChange = { wordInput = it },
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
                            Text("Gemini tahlil qilmoqda...", color = theme.primaryAccent, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (wordInput.isNotBlank()) {
                            onAddWordWithAi(wordInput.trim())
                            wordInput = ""
                            showAddDialog = false
                        }
                    },
                    enabled = wordInput.isNotBlank() && !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("AI bilan qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Bekor qilish", color = theme.textSecondary)
                }
            }
        )
    }

    // Modal Dialog: Raw Text Paste (Multiple words) - Uses SOLID OPAQUE dialogSurface
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
                        text = "So'zlar ro'yxatini yoki CEFR darajali matnni joylashtiring (masalan: 'can A1 qila olmoq', 'diligent B2 tirishqoq').",
                        color = theme.textSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = manualTextInput,
                        onValueChange = { manualTextInput = it },
                        placeholder = { Text("can A1\ndiligent B2\nperseverance C1", color = theme.textSecondary) },
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
                            onImportText(manualTextInput.trim(), manualDocTitle)
                            manualTextInput = ""
                            showManualTextDialog = false
                        }
                    },
                    enabled = manualTextInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Saralash & Qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualTextDialog = false }) {
                    Text("Yopish", color = theme.textSecondary)
                }
            }
        )
    }

    // Modal Dialog: AI Quiz - Uses SOLID OPAQUE dialogSurface
    if (showQuizDialog) {
        AiQuizDialog(
            questions = quizQuestions,
            isLoading = isQuizLoading,
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
                        Text(
                            text = card.word,
                            color = theme.textPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                        )
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

            // Expanded extra details
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    if (card.definition.isNotBlank()) {
                        Text(
                            text = "Ta'rif: ${card.definition}",
                            color = theme.textSecondary,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    if (card.example.isNotBlank()) {
                        Text(
                            text = "Misol: \"${card.example}\"",
                            color = theme.textPrimary.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
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

                    // Action buttons: Mastered vs Repeat
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
                                Text("Yodlandi deb belgilash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
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
    onDismiss: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }

    // SOLID OPAQUE DIALOG CONTAINER
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = theme.dialogSurface,
        title = {
            Text("🧠 Gemini AI Lug'at Quiz", color = theme.primaryAccent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = theme.primaryAccent)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Lug'atingiz asosida savollar tuzilmoqda...", color = theme.textSecondary, fontSize = 12.sp)
                    }
                }
            } else if (questions.isEmpty()) {
                Text("Savollar yuklanmadi. Lug'atingizga bir nechta so'z qo'shing va qayta urinib ko'ring.", color = theme.textSecondary, fontSize = 13.sp)
            } else if (currentIndex >= questions.size) {
                // Completed
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎉 Test yakunlandi!", color = theme.primaryAccent, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Natijangiz: $score / ${questions.size}", color = theme.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        if (score >= 4) "Ajoyib natija! Intizomingiz tahsinga loyiq." else "Yaxshi urinish! Qaytadan takrorlab chiqing.",
                        color = theme.textSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                val q = questions[currentIndex]
                Column {
                    Text(
                        text = "Savol ${currentIndex + 1}/${questions.size}:",
                        color = theme.primaryAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = q.question,
                        color = theme.textPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    q.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOption == optIndex
                        val isCorrect = optIndex == q.correctIndex
                        val btnBg = when {
                            !isAnswerSubmitted && isSelected -> theme.primaryAccent.copy(alpha = 0.2f)
                            isAnswerSubmitted && isCorrect -> Color(0xFF2E7D32).copy(alpha = 0.3f)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFC62828).copy(alpha = 0.3f)
                            else -> theme.glassSurface
                        }
                        val borderColor = when {
                            isAnswerSubmitted && isCorrect -> Color(0xFF4CAF50)
                            isAnswerSubmitted && isSelected && !isCorrect -> Color(0xFFEF5350)
                            isSelected -> theme.primaryAccent
                            else -> theme.glassBorderSubtleColor
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(btnBg)
                                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswerSubmitted) {
                                    selectedOption = optIndex
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = optionText,
                                color = theme.textPrimary,
                                fontSize = 13.sp
                            )
                        }
                    }

                    if (isAnswerSubmitted && q.explanation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Izoh: ${q.explanation}",
                            color = theme.primaryAccent,
                            fontSize = 11.5.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (!isLoading && questions.isNotEmpty()) {
                if (currentIndex < questions.size) {
                    if (!isAnswerSubmitted) {
                        Button(
                            onClick = {
                                if (selectedOption != -1) {
                                    isAnswerSubmitted = true
                                    if (selectedOption == questions[currentIndex].correctIndex) {
                                        score++
                                    }
                                }
                            },
                            enabled = selectedOption != -1,
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Javobni tekshirish", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = {
                                currentIndex++
                                selectedOption = -1
                                isAnswerSubmitted = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (currentIndex + 1 < questions.size) "Keyingi savol" else "Natijani ko'rish", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Tugatish", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Yopish", color = theme.textSecondary)
            }
        }
    )
}
