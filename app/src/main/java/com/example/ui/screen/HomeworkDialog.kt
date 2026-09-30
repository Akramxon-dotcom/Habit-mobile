package com.example.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.HomeworkEntry
import com.example.ui.theme.LocalLiquidTheme

/**
 * Main Homework modal sheet with Today's tasks, History tab, and prompt configuration.
 */
@Composable
fun HomeworkMainDialog(
    isOpen: Boolean,
    todayHomework: HomeworkEntry?,
    homeworkHistory: List<HomeworkEntry>,
    promptTime: String,
    onDismiss: () -> Unit,
    onOpenPromptDialog: () -> Unit,
    onOpenTimePickerDialog: () -> Unit,
    onToggleSubTask: (String, String) -> Unit,
    onToggleCompleted: (String) -> Unit,
    onDeleteEntry: (String) -> Unit
) {
    if (!isOpen) return

    val theme = LocalLiquidTheme.current
    var selectedTab by remember { mutableStateOf(0) } // 0: Bugun, 1: Tarix

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.88f)
                .clip(RoundedCornerShape(26.dp)),
            color = theme.dialogSurface,
            border = BorderStroke(1.2.dp, theme.primaryAccent.copy(alpha = 0.45f)),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = theme.primaryAccent.copy(alpha = 0.18f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("📝", fontSize = 22.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Uyga vazifalar",
                                color = theme.textPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                text = "Dars va markaz topshiriqlari",
                                color = theme.textSecondary,
                                fontSize = 11.5.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(34.dp)
                            .background(theme.glassSurfaceElevated, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Yopish",
                            tint = theme.textSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tab Switch: Bugun vs Tarix
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.glassSurface, RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val bugunSelected = selectedTab == 0
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTab = 0 },
                        color = if (bugunSelected) theme.primaryAccent else Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "📌 Bugun",
                                    fontSize = 13.sp,
                                    fontWeight = if (bugunSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (bugunSelected) Color.Black else theme.textPrimary
                                )
                                if (todayHomework != null && !todayHomework.isCompleted) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(Color(0xFFEF5350), CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    val tarixSelected = selectedTab == 1
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedTab = 1 },
                        color = if (tarixSelected) theme.primaryAccent else Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "📜 Tarix (${homeworkHistory.size})",
                                fontSize = 13.sp,
                                fontWeight = if (tarixSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (tarixSelected) Color.Black else theme.textPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tab Content
                if (selectedTab == 0) {
                    // BUGUN TAB
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Prompt time settings pill
                        item {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = theme.primaryAccent.copy(alpha = 0.10f),
                                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.30f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onOpenTimePickerDialog() }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("⏰", fontSize = 16.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                "Avtomatik so'rov vaqti: $promptTime",
                                                color = theme.textPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.5.sp
                                            )
                                            Text(
                                                "Dars tugaganda so'rov oynasi ochiladi",
                                                color = theme.textSecondary,
                                                fontSize = 10.5.sp
                                            )
                                        }
                                    }
                                    Text(
                                        "O'zgartirish",
                                        color = theme.primaryAccent,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        if (todayHomework != null) {
                            val doneCount = todayHomework.tasks.count { it.isDone }
                            val totalCount = todayHomework.tasks.size.coerceAtLeast(1)

                            item {
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = theme.glassSurfaceElevated,
                                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = if (todayHomework.topic.isNotBlank()) todayHomework.topic else "Bugungi dars mavzusi",
                                                    color = theme.textPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp
                                                )
                                                Text(
                                                    text = "📅 ${todayHomework.dateStr}",
                                                    color = theme.textSecondary,
                                                    fontSize = 11.5.sp
                                                )
                                            }

                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = if (todayHomework.isCompleted) Color(0xFF4CAF50).copy(alpha = 0.2f) else theme.primaryAccent.copy(alpha = 0.2f)
                                            ) {
                                                Text(
                                                    text = if (todayHomework.isCompleted) "✅ Bajarildi" else "⏳ $doneCount/$totalCount",
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (todayHomework.isCompleted) Color(0xFF4CAF50) else theme.primaryAccent
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))

                                        // Progress bar
                                        LinearProgressIndicator(
                                            progress = { doneCount.toFloat() / totalCount.toFloat() },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(6.dp)
                                                .clip(RoundedCornerShape(3.dp)),
                                            color = if (todayHomework.isCompleted) Color(0xFF4CAF50) else theme.primaryAccent,
                                            trackColor = theme.primaryAccent.copy(alpha = 0.15f)
                                        )

                                        Spacer(modifier = Modifier.height(14.dp))

                                        Text(
                                            "Bajarilishi kerak bo'lgan vazifalar:",
                                            color = theme.textSecondary,
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        // Subtasks Checklist
                                        todayHomework.tasks.forEach { task ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .clickable { onToggleSubTask(todayHomework.id, task.id) }
                                                    .padding(vertical = 4.dp, horizontal = 2.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Checkbox(
                                                    checked = task.isDone,
                                                    onCheckedChange = { onToggleSubTask(todayHomework.id, task.id) },
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = theme.primaryAccent,
                                                        checkmarkColor = Color.Black
                                                    )
                                                )
                                                Text(
                                                    text = task.text,
                                                    color = if (task.isDone) theme.textMuted else theme.textPrimary,
                                                    fontSize = 13.sp,
                                                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        // Action buttons
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            OutlinedButton(
                                                onClick = onOpenPromptDialog,
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(12.dp),
                                                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f))
                                            ) {
                                                Text("✏️ Tahrirlash", fontSize = 12.sp, color = theme.primaryAccent)
                                            }

                                            Button(
                                                onClick = { onToggleCompleted(todayHomework.id) },
                                                modifier = Modifier.weight(1f),
                                                shape = RoundedCornerShape(12.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (todayHomework.isCompleted) Color(0xFFEF5350).copy(alpha = 0.85f) else Color(0xFF4CAF50)
                                                )
                                            ) {
                                                Text(
                                                    if (todayHomework.isCompleted) "Qaytarish" else "Barchasi tayyor!",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // Empty State for Today
                            item {
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = theme.glassSurfaceElevated,
                                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 32.dp, horizontal = 20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("📚", fontSize = 42.sp)
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            "Bugungi uyga vazifalar kiritilmagan",
                                            color = theme.textPrimary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            "Darsdan so'ng berilgan vazifalarni hoziroq belgilab oling va kechga qoldirmang.",
                                            color = theme.textSecondary,
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(18.dp))
                                        Button(
                                            onClick = onOpenPromptDialog,
                                            shape = RoundedCornerShape(14.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                                        ) {
                                            Text("➕ Bugungi vazifalarni yozish", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // TARIX TAB
                    if (homeworkHistory.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📜", fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text("Avvalgi kunlar tarixi bo'sh", color = theme.textSecondary, fontSize = 14.sp)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(homeworkHistory, key = { it.id }) { entry ->
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = theme.glassSurfaceElevated,
                                    border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text("📅", fontSize = 14.sp)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    entry.dateStr,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 13.sp,
                                                    color = theme.primaryAccent
                                                )
                                            }

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                if (entry.isCompleted) {
                                                    Surface(
                                                        shape = RoundedCornerShape(6.dp),
                                                        color = Color(0xFF4CAF50).copy(alpha = 0.2f)
                                                    ) {
                                                        Text(
                                                            "Bajarilgan",
                                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                            fontSize = 10.sp,
                                                            color = Color(0xFF4CAF50),
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                }
                                                IconButton(
                                                    onClick = { onDeleteEntry(entry.id) },
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "O'chirish",
                                                        tint = Color(0xFFEF5350).copy(alpha = 0.7f),
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }

                                        if (entry.topic.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                "Mavzu: ${entry.topic}",
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.5.sp,
                                                color = theme.textPrimary
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        entry.tasks.forEach { task ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable { onToggleSubTask(entry.id, task.id) }
                                                    .padding(vertical = 2.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Checkbox(
                                                    checked = task.isDone,
                                                    onCheckedChange = { onToggleSubTask(entry.id, task.id) },
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = theme.primaryAccent,
                                                        checkmarkColor = Color.Black
                                                    )
                                                )
                                                Text(
                                                    task.text,
                                                    fontSize = 12.5.sp,
                                                    color = if (task.isDone) theme.textMuted else theme.textPrimary,
                                                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
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
        }
    }
}

/**
 * Prompt Dialog: "Bugungilik uyga vazifalar nima bo'ldi?"
 * Features text field + customizable preset variants with persistent '+' button.
 */
@Composable
fun HomeworkPromptDialog(
    isOpen: Boolean,
    presets: List<String>,
    initialTopic: String = "",
    initialText: String = "",
    onDismiss: () -> Unit,
    onSave: (topic: String, text: String) -> Unit,
    onAddPreset: (String) -> Unit,
    onRemovePreset: (String) -> Unit
) {
    if (!isOpen) return

    val theme = LocalLiquidTheme.current
    var topicInput by remember(initialTopic) { mutableStateOf(initialTopic) }
    var rawTextInput by remember(initialText) { mutableStateOf(initialText) }

    var showAddPresetDialog by remember { mutableStateOf(false) }
    var newPresetInput by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(26.dp)),
            color = theme.dialogSurface,
            border = BorderStroke(1.2.dp, theme.primaryAccent.copy(alpha = 0.5f)),
            shadowElevation = 20.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📝", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                "Bugungilik uyga vazifalar",
                                color = theme.textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.5.sp,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                "Dars tugadi. Bugungi vazifalar nima bo'ldi?",
                                color = theme.textSecondary,
                                fontSize = 11.5.sp
                            )
                        }
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Yopish", tint = theme.textSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Topic Input
                OutlinedTextField(
                    value = topicInput,
                    onValueChange = { topicInput = it },
                    label = { Text("Mavzu / Fan (masalan: Ingliz tili Unit 5 & Fizika)", fontSize = 12.sp) },
                    placeholder = { Text("Qaysi fan va mavzudan vazifa berildi?", fontSize = 12.sp) },
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

                Spacer(modifier = Modifier.height(10.dp))

                // Detailed Tasks Input
                OutlinedTextField(
                    value = rawTextInput,
                    onValueChange = { rawTextInput = it },
                    label = { Text("Vazifalar matni (har bir qator alohida vazifa)", fontSize = 12.sp) },
                    placeholder = { Text("1) Workbook 45-bet\n2) 10 ta yangi so'z\n3) Qoidani yodlash", fontSize = 12.sp) },
                    minLines = 4,
                    maxLines = 7,
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

                // Quick Presets Row (Tayyor variantlar)
                Text(
                    "💡 Tayyor variantlar (bosing va matnga qo'shing):",
                    fontSize = 11.sp,
                    color = theme.textSecondary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Constant '+' button to add new preset
                    item {
                        Surface(
                            onClick = { showAddPresetDialog = true },
                            shape = RoundedCornerShape(12.dp),
                            color = theme.primaryAccent.copy(alpha = 0.18f),
                            border = BorderStroke(1.dp, theme.primaryAccent)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Yangi variant qo'shish",
                                    tint = theme.primaryAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "Yangi variant",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryAccent
                                )
                            }
                        }
                    }

                    // User presets
                    items(presets) { preset ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = theme.glassSurfaceElevated,
                            border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        rawTextInput = if (rawTextInput.isBlank()) {
                                            preset
                                        } else {
                                            "$rawTextInput\n$preset"
                                        }
                                    }
                                    .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = preset,
                                    fontSize = 11.5.sp,
                                    color = theme.textPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(
                                    onClick = { onRemovePreset(preset) },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Variantni o'chirish",
                                        tint = theme.textSecondary.copy(alpha = 0.6f),
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Keyinroq", color = theme.textSecondary, fontSize = 12.5.sp)
                    }

                    Button(
                        onClick = {
                            if (rawTextInput.isNotBlank() || topicInput.isNotBlank()) {
                                onSave(topicInput, rawTextInput)
                            }
                        },
                        modifier = Modifier.weight(1.3f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        enabled = rawTextInput.isNotBlank() || topicInput.isNotBlank()
                    ) {
                        Text("💾 Saqlash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }

    // Sub-dialog to add a new preset option
    if (showAddPresetDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddPresetDialog = false
                newPresetInput = ""
            },
            title = { Text("Yangi tayyor variant", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        "Kelgusida bir bosishda qo'shish uchun variant nomini kiriting:",
                        fontSize = 12.sp,
                        color = theme.textSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPresetInput,
                        onValueChange = { newPresetInput = it },
                        placeholder = { Text("masalan: Ingliz tili Workbook mashq", fontSize = 12.sp) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPresetInput.isNotBlank()) {
                            onAddPreset(newPresetInput.trim())
                            newPresetInput = ""
                            showAddPresetDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAddPresetDialog = false
                    newPresetInput = ""
                }) {
                    Text("Bekor qilish")
                }
            }
        )
    }
}

/**
 * Time Picker Dialog to customize homework prompt time (default 17:00).
 */
@Composable
fun HomeworkPromptTimeDialog(
    isOpen: Boolean,
    currentTime: String,
    onDismiss: () -> Unit,
    onSaveTime: (String) -> Unit
) {
    if (!isOpen) return

    val theme = LocalLiquidTheme.current
    var selectedHour by remember { mutableStateOf(currentTime.split(":").firstOrNull() ?: "17") }
    var selectedMinute by remember { mutableStateOf(currentTime.split(":").getOrNull(1) ?: "00") }

    val quickTimes = listOf("16:30", "17:00", "17:30", "18:00", "18:30")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⏰", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("So'rov vaqtini sozlash", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Har kuni offlayn darsingiz tugagach, ilova sizdan uyga vazifalarni so'raydigan vaqt:",
                    fontSize = 12.5.sp,
                    color = theme.textSecondary
                )

                // Quick selector chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickTimes.forEach { qTime ->
                        val isSel = "$selectedHour:$selectedMinute" == qTime
                        Surface(
                            onClick = {
                                val parts = qTime.split(":")
                                selectedHour = parts[0]
                                selectedMinute = parts[1]
                            },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSel) theme.primaryAccent else theme.glassSurfaceElevated,
                            border = BorderStroke(1.dp, if (isSel) theme.primaryAccent else theme.glassBorderSubtleColor)
                        ) {
                            Text(
                                text = qTime,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                fontSize = 11.5.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSel) Color.Black else theme.textPrimary
                            )
                        }
                    }
                }

                // Custom Hour & Minute inputs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = selectedHour,
                        onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) selectedHour = it },
                        label = { Text("Soat") },
                        modifier = Modifier.width(75.dp),
                        singleLine = true
                    )
                    Text(" : ", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp))
                    OutlinedTextField(
                        value = selectedMinute,
                        onValueChange = { if (it.length <= 2 && it.all { c -> c.isDigit() }) selectedMinute = it },
                        label = { Text("Daqiqa") },
                        modifier = Modifier.width(75.dp),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val h = selectedHour.padStart(2, '0').toIntOrNull()?.coerceIn(0, 23) ?: 17
                    val m = selectedMinute.padStart(2, '0').toIntOrNull()?.coerceIn(0, 59) ?: 0
                    val formatted = String.format("%02d:%02d", h, m)
                    onSaveTime(formatted)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
            ) {
                Text("Saqlash", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Bekor qilish")
            }
        }
    )
}
