package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.DailyScheduleMapping
import com.example.data.model.EnglishPlanTask
import com.example.data.model.EnglishResourceItem
import com.example.data.model.MonthlyEnglishPlanData
import com.example.ui.theme.HabitAmber
import com.example.ui.theme.HabitBlue
import com.example.ui.theme.HabitIndigo
import com.example.ui.theme.HabitPurple
import com.example.ui.theme.HabitRose
import com.example.ui.theme.HabitSage
import com.example.ui.theme.LiquidGlassStyle
import com.example.ui.theme.LocalLiquidTheme

/**
 * Compact preview card of the 1-Month English Plan for the Bugun (Home) screen.
 */
@Composable
fun MonthlyEnglishPlanHomeCard(
    activeWeek: Int,
    completedTaskIds: Set<String>,
    onOpenFullPlan: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val allTasks = MonthlyEnglishPlanData.getAllTasks()
    val totalCount = allTasks.size
    val completedCount = allTasks.count { it.id in completedTaskIds }
    val progressPercent = if (totalCount > 0) (completedCount * 100) / totalCount else 0

    val currentWeekPlan = MonthlyEnglishPlanData.WEEKS.find { it.weekNumber == activeWeek }
        ?: MonthlyEnglishPlanData.WEEKS.first()
    val currentWeekDone = currentWeekPlan.tasks.count { it.id in completedTaskIds }
    val currentWeekTotal = currentWeekPlan.tasks.size

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("card_monthly_english_plan_home"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.35f))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(theme.primaryAccent.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "1 Oylik Reja",
                                color = theme.textPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = theme.primaryAccent.copy(alpha = 0.15f),
                                border = BorderStroke(0.5.dp, theme.primaryAccent.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "A2 Elementar",
                                    color = theme.primaryAccent,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "${currentWeekPlan.title} · $currentWeekDone/$currentWeekTotal bajarildi",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = theme.glassSurface,
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Text(
                        text = "$progressPercent%",
                        color = theme.primaryAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { (completedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = theme.primaryAccent,
                trackColor = theme.primaryAccent.copy(alpha = 0.12f),
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "📌 ${currentWeekPlan.focus}",
                color = theme.textSecondary,
                fontSize = 11.5.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onOpenFullPlan,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .testTag("btn_open_monthly_plan_full"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent.copy(alpha = 0.18f)),
                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.45f))
            ) {
                Text(
                    text = "🎯 To'liq 1 oylik rejani ochish & belgilash",
                    color = theme.textPrimary,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * LazyListScope extension to display the 1-Month English Plan inside VocabScreen's LazyColumn.
 */
fun LazyListScope.monthlyPlanLazyItems(
    activeWeek: Int,
    completedTaskIds: Set<String>,
    selectedSubSection: String,
    onSelectSubSection: (String) -> Unit,
    onSelectWeek: (Int) -> Unit,
    onToggleTask: (String) -> Unit,
    theme: LiquidGlassStyle
) {
    val allTasks = MonthlyEnglishPlanData.getAllTasks()
    val totalTasks = allTasks.size
    val totalDone = allTasks.count { it.id in completedTaskIds }
    val totalPercent = if (totalTasks > 0) (totalDone * 100) / totalTasks else 0

    val currentWeekPlan = MonthlyEnglishPlanData.WEEKS.find { it.weekNumber == activeWeek }
        ?: MonthlyEnglishPlanData.WEEKS.first()
    val weekTasksDone = currentWeekPlan.tasks.count { it.id in completedTaskIds }
    val weekTasksTotal = currentWeekPlan.tasks.size
    val weekPercent = if (weekTasksTotal > 0) (weekTasksDone * 100) / weekTasksTotal else 0

    // 1. HERO BANNER
    item {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
            border = BorderStroke(1.dp, theme.glassBorderSubtle)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "1 Oylik Ingliz Tili Rejasi",
                            color = theme.textPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = theme.primaryAccent.copy(alpha = 0.15f),
                            border = BorderStroke(0.5.dp, theme.primaryAccent.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "🎯 Elementar (A2) Daraja",
                                color = theme.primaryAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "$totalDone / $totalTasks",
                            color = theme.primaryAccent,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "bajarildi ($totalPercent%)",
                            color = theme.textSecondary,
                            fontSize = 10.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { (totalDone.toFloat() / totalTasks.toFloat()).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = theme.primaryAccent,
                    trackColor = theme.primaryAccent.copy(alpha = 0.12f),
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Kun tartibingizdagi barcha Ingliz tili bandlariga aniq ko'rsatmalar. Na juda oson (zerikarli), na juda qiyin (tashlab qo'yasan). Bir oydan keyin darajangiz qayta baholanib, 2-oylik rejaga o'tiladi.",
                    color = theme.textSecondary,
                    fontSize = 11.5.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }

    // 2. SUB-SECTION SELECTOR (4 Hafta / 8 Resurs / Jadval)
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(theme.glassSurface)
                .padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val tabs = listOf(
                "TASKS" to "🗓️ 4 Hafta",
                "RESOURCES" to "📚 8 Resurs",
                "SCHEDULE" to "📌 Kun tartibi"
            )
            tabs.forEach { (mode, label) ->
                val isSelected = selectedSubSection == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(11.dp))
                        .background(if (isSelected) theme.primaryAccent else Color.Transparent)
                        .clickable { onSelectSubSection(mode) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
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

    when (selectedSubSection) {
        "TASKS" -> {
            // Week selector row
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(MonthlyEnglishPlanData.WEEKS) { week ->
                        val isSelected = activeWeek == week.weekNumber
                        val doneCount = week.tasks.count { it.id in completedTaskIds }
                        val totalWeekTasks = week.tasks.size
                        val isAllDone = doneCount == totalWeekTasks && totalWeekTasks > 0

                        Surface(
                            onClick = { onSelectWeek(week.weekNumber) },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) theme.primaryAccent.copy(alpha = 0.2f) else theme.glassSurface,
                            border = BorderStroke(
                                if (isSelected) 1.5.dp else 1.dp,
                                if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor
                            ),
                            modifier = Modifier.testTag("tab_plan_week_${week.weekNumber}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isAllDone) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                                Column {
                                    Text(
                                        text = "${week.weekNumber}-hafta",
                                        color = if (isSelected) theme.primaryAccent else theme.textPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "$doneCount / $totalWeekTasks bajarildi",
                                        color = theme.textSecondary,
                                        fontSize = 9.5.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Current week focus summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = currentWeekPlan.title,
                                    color = theme.textPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = currentWeekPlan.subtitle,
                                    color = theme.primaryAccent,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = theme.glassSurface,
                                border = BorderStroke(1.dp, theme.glassBorderSubtle)
                            ) {
                                Text(
                                    text = "$weekPercent%",
                                    color = theme.primaryAccent,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "🎯 ${currentWeekPlan.focus}",
                            color = theme.textSecondary,
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Tasks list for current week
            items(currentWeekPlan.tasks, key = { it.id }) { task ->
                val isCompleted = task.id in completedTaskIds

                PlanTaskRow(
                    task = task,
                    isCompleted = isCompleted,
                    onToggle = { onToggleTask(task.id) }
                )
            }
        }

        "RESOURCES" -> {
            item {
                Text(
                    text = "📚 Asosiy Resurslar (8 ta ko'nikma)",
                    color = theme.textPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Telegram kanallarida bepul PDF/audio yoki YouTube'da mavjud.",
                    color = theme.textSecondary,
                    fontSize = 11.sp
                )
            }

            items(MonthlyEnglishPlanData.RESOURCES) { resource ->
                ResourceCard(resource = resource)
            }
        }

        "SCHEDULE" -> {
            item {
                Text(
                    text = "📌 Kun tartibingga qanday joylashadi?",
                    color = theme.textPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Ilovadagi har bir 'Ingliz' soatida aynan nima qilish ko'rsatilgan.",
                    color = theme.textSecondary,
                    fontSize = 11.sp
                )
            }

            items(MonthlyEnglishPlanData.SCHEDULE_MAPPINGS) { mapping ->
                ScheduleMappingCard(mapping = mapping)
            }
        }
    }
}

/**
 * Full interactive 1-Month English Plan view (used in Dialog or standalone).
 */
@Composable
fun MonthlyEnglishPlanView(
    activeWeek: Int,
    completedTaskIds: Set<String>,
    onSelectWeek: (Int) -> Unit,
    onToggleTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    var currentActiveWeek by remember(activeWeek) { mutableIntStateOf(activeWeek) }
    var selectedSubSection by remember { mutableStateOf("TASKS") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        monthlyPlanLazyItems(
            activeWeek = currentActiveWeek,
            completedTaskIds = completedTaskIds,
            selectedSubSection = selectedSubSection,
            onSelectSubSection = { selectedSubSection = it },
            onSelectWeek = {
                currentActiveWeek = it
                onSelectWeek(it)
            },
            onToggleTask = onToggleTask,
            theme = theme
        )
    }
}

@Composable
fun PlanTaskRow(
    task: EnglishPlanTask,
    isCompleted: Boolean,
    onToggle: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    val categoryColor = when (task.category.lowercase()) {
        "grammatika" -> HabitIndigo
        "lug'at" -> theme.primaryAccent
        "kitob" -> HabitAmber
        "audio" -> HabitBlue
        "serial", "kino" -> HabitPurple
        "gapirish" -> HabitRose
        "yozish" -> HabitSage
        else -> theme.primaryAccent
    }

    val icon: ImageVector = when (task.category.lowercase()) {
        "grammatika" -> Icons.Default.School
        "lug'at" -> Icons.Default.Translate
        "kitob" -> Icons.Default.Book
        "audio" -> Icons.Default.Headphones
        "serial", "kino" -> Icons.Default.LiveTv
        "gapirish" -> Icons.Default.Mic
        "yozish" -> Icons.Default.BookmarkBorder
        else -> Icons.Default.CheckCircle
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("task_item_${task.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) theme.glassSurface.copy(alpha = 0.4f) else theme.glassSurfaceElevated
        ),
        border = BorderStroke(
            1.dp,
            if (isCompleted) theme.primaryAccent.copy(alpha = 0.4f) else theme.glassBorderSubtleColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox indicator
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(if (isCompleted) theme.primaryAccent else theme.glassSurface)
                    .border(
                        1.5.dp,
                        if (isCompleted) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.4f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Bajarildi",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Task content
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        color = categoryColor.copy(alpha = 0.15f),
                        border = BorderStroke(0.5.dp, categoryColor.copy(alpha = 0.35f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = categoryColor,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = task.category,
                                color = categoryColor,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.title,
                    color = if (isCompleted) theme.textSecondary else theme.textPrimary,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = task.detail,
                    color = theme.textSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
            }
        }
    }
}

@Composable
fun ResourceCard(resource: EnglishResourceItem) {
    val theme = LocalLiquidTheme.current
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtle)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = resource.title,
                        color = theme.primaryAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = resource.resource,
                        color = theme.textPrimary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = theme.textSecondary
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = theme.glassSurface,
                        border = BorderStroke(0.5.dp, theme.glassBorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "💡 Nega aynan shu:",
                                color = theme.textPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = resource.why,
                                color = theme.textSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "🔍 Qayerdan topish mumkin:",
                                color = theme.primaryAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = resource.howToFind,
                                color = theme.textSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleMappingCard(mapping: DailyScheduleMapping) {
    val theme = LocalLiquidTheme.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtle)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = theme.primaryAccent.copy(alpha = 0.15f),
                border = BorderStroke(0.5.dp, theme.primaryAccent.copy(alpha = 0.35f))
            ) {
                Text(
                    text = mapping.timeSlot,
                    color = theme.primaryAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = mapping.scheduleTitle,
                    color = theme.textPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = mapping.whatToDo,
                    color = theme.textSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

/**
 * Dialog overlay to view the full 1-month plan from anywhere (Home card click, etc.).
 */
@Composable
fun MonthlyEnglishPlanDialog(
    activeWeek: Int,
    completedTaskIds: Set<String>,
    onSelectWeek: (Int) -> Unit,
    onToggleTask: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.92f),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.School,
                                contentDescription = null,
                                tint = theme.primaryAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "1 Oylik Ingliz Tili Rejasi",
                                color = theme.textPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Yopish", tint = theme.textSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MonthlyEnglishPlanView(
                        activeWeek = activeWeek,
                        completedTaskIds = completedTaskIds,
                        onSelectWeek = onSelectWeek,
                        onToggleTask = onToggleTask,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
