package com.example.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskTimeEngine
import com.example.ui.theme.LocalLiquidTheme

data class ShiftedScheduleItem(
    val original: ScheduleItem,
    val newStart: String,
    val newEnd: String,
    val isCompressed: Boolean,
    val isShifted: Boolean
)

object DynamicRescheduleEngine {

    private fun minutesToString(totalMinutes: Int): String {
        val normalized = (totalMinutes % 1440 + 1440) % 1440
        val h = normalized / 60
        val m = normalized % 60
        return String.format("%02d:%02d", h, m)
    }

    fun computeSmartReschedule(
        items: List<ScheduleItem>,
        delayMinutes: Int,
        currentTaskTitle: String?
    ): List<ShiftedScheduleItem> {
        if (delayMinutes == 0 || items.isEmpty()) {
            return items.map {
                ShiftedScheduleItem(
                    original = it,
                    newStart = it.start,
                    newEnd = it.end,
                    isCompressed = false,
                    isShifted = false
                )
            }
        }

        var accumulatedDelay = delayMinutes
        val currentMin = TaskTimeEngine.getCurrentMinuteOfDay()

        val results = mutableListOf<ShiftedScheduleItem>()

        for (item in items) {
            val startMin = TaskTimeEngine.parseMinuteOfDay(item.start) ?: 0
            val endMin = TaskTimeEngine.parseMinuteOfDay(item.end) ?: 0
            val duration = (endMin - startMin).coerceAtLeast(1)

            // If task is in the past before current time, keep as is
            if (endMin <= currentMin && item.title != currentTaskTitle) {
                results.add(
                    ShiftedScheduleItem(
                        original = item,
                        newStart = item.start,
                        newEnd = item.end,
                        isCompressed = false,
                        isShifted = false
                    )
                )
                continue
            }

            // Check if this task is compressible (breaks, free time, other non-prayer non-core)
            val isCompressible = item.category == "other" || item.category == "night" && item.title.contains("tayyorgarlik") || item.title.contains("tanaffus") || item.title.contains("Zaxira")

            if (isCompressible && accumulatedDelay > 0 && duration > 15) {
                // Compress this task to absorb delay
                val compressionAmount = minOf(accumulatedDelay, duration - 10)
                val newDuration = duration - compressionAmount
                val newStartMin = startMin + (delayMinutes - accumulatedDelay)
                val newEndMin = newStartMin + newDuration
                accumulatedDelay -= compressionAmount

                results.add(
                    ShiftedScheduleItem(
                        original = item,
                        newStart = minutesToString(newStartMin),
                        newEnd = minutesToString(newEndMin),
                        isCompressed = true,
                        isShifted = true
                    )
                )
            } else {
                // Shift normally
                val newStartMin = startMin + accumulatedDelay
                val newEndMin = newStartMin + duration
                results.add(
                    ShiftedScheduleItem(
                        original = item,
                        newStart = minutesToString(newStartMin),
                        newEnd = minutesToString(newEndMin),
                        isCompressed = false,
                        isShifted = accumulatedDelay > 0
                    )
                )
            }
        }

        return results
    }
}

@Composable
fun SmartRescheduleDialog(
    currentSchedule: List<ScheduleItem>,
    currentTaskTitle: String?,
    onApplyNewSchedule: (List<ScheduleItem>) -> Unit,
    onDismiss: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var selectedDelayMinutes by remember { mutableIntStateOf(30) }

    val rescheduledList = remember(selectedDelayMinutes, currentSchedule) {
        DynamicRescheduleEngine.computeSmartReschedule(
            items = currentSchedule,
            delayMinutes = selectedDelayMinutes,
            currentTaskTitle = currentTaskTitle
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.88f)
                .background(Color(0xFF0F1713), RoundedCornerShape(22.dp))
                .background(theme.glassSurfaceElevated.copy(alpha = 0.95f), RoundedCornerShape(22.dp))
                .border(1.2.dp, theme.primaryAccent.copy(alpha = 0.5f), RoundedCornerShape(22.dp))
                .padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "⚡ Aqlli Qayta Taqsimlash",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                        Text(
                            "Kechikishni darslar va namozlarni buzmasdan taqsimlash",
                            fontSize = 11.sp,
                            color = theme.primaryAccent
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Yopish", tint = theme.textPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Delay selector chips (15, 30, 45, 60 min)
                Text(
                    "Kechikish vaqtini tanlang:",
                    fontSize = 11.sp,
                    color = theme.textSecondary,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(15, 30, 45, 60).forEach { mins ->
                        val isSelected = selectedDelayMinutes == mins
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isSelected) theme.primaryAccent else theme.glassSurface,
                                    RoundedCornerShape(10.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedDelayMinutes = mins }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+$mins daq",
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.Black else theme.textPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Algorithm logic explanation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.primaryAccent.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🛡️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Algoritm namoz va ingliz tili darslarini saqlab qoladi, kechikishni esa tanaffuslar va bo'sh vaqtlardan qisqartirib yopadi.",
                            fontSize = 11.sp,
                            color = theme.textPrimary,
                            lineHeight = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Preview list
                Text(
                    "Jadval o'zgarishi ko'rinishi (Oldin ➔ Keyin):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.textPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(rescheduledList) { item ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.isCompressed) Color(0xFFF59E0B).copy(alpha = 0.12f)
                                else if (item.isShifted) theme.glassSurface
                                else theme.glassSurface.copy(alpha = 0.5f)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (item.isCompressed) Color(0xFFF59E0B).copy(alpha = 0.4f)
                                else theme.glassBorderSubtleColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        item.original.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = theme.textPrimary,
                                        maxLines = 1
                                    )
                                    if (item.isCompressed) {
                                        Text(
                                            "⏳ Tanaffus qisqartirildi (Darslar vaqtini saqlash uchun)",
                                            fontSize = 9.sp,
                                            color = Color(0xFFF59E0B)
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (item.isShifted) {
                                        Text(
                                            "${item.original.start}",
                                            fontSize = 10.sp,
                                            color = theme.textSecondary,
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                        Text("➔ ", fontSize = 10.sp, color = theme.primaryAccent)
                                        Text(
                                            "${item.newStart} - ${item.newEnd}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = theme.primaryAccent
                                        )
                                    } else {
                                        Text(
                                            "${item.original.start} - ${item.original.end}",
                                            fontSize = 11.sp,
                                            color = theme.textSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Apply button
                Button(
                    onClick = {
                        val newItems = rescheduledList.map {
                            it.original.copy(
                                start = it.newStart,
                                end = it.newEnd
                            )
                        }
                        onApplyNewSchedule(newItems)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "✅ Yangi jadvalni tasdiqlash va faollashtirish",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
