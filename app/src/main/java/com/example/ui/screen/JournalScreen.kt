package com.example.ui.screen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JournalEntry
import com.example.ui.theme.LocalLiquidTheme

@Composable
fun JournalScreen(
    entries: List<JournalEntry>,
    streak: Int,
    timeBankMinutes: Int,
    completedTasksCount: Int,
    todayDateStr: String,
    onSaveEntry: (stars: Int, highlights: String, challenges: String, reflections: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    var stars by remember { mutableIntStateOf(5) }
    var highlights by remember { mutableStateOf("") }
    var challenges by remember { mutableStateOf("") }
    var reflections by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "📓 Kechki Shaxsiy Kundalik",
                        color = theme.textPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sana: $todayDateStr · Refleksiya va xulosa",
                        color = theme.textSecondary,
                        fontSize = 12.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(theme.glassSurface)
                        .border(1.dp, theme.glassBorderSubtle, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "🔥 $streak kun streak",
                        color = theme.primaryAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Today's New Entry Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.dp, theme.glassBorderSubtle)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Bugungi kuningizni baholang:",
                        color = theme.textPrimary,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Star rating row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= stars) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "$i yulduz",
                                tint = if (i <= stars) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { stars = i }
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (stars) {
                                5 -> "A'lo darajada!"
                                4 -> "Yaxshi o'tdi"
                                3 -> "O'rtacha"
                                2 -> "Qiyin bo'ldi"
                                else -> "Ko'p chalg'idim"
                            },
                            color = theme.primaryAccent,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "🌟 Bugun nimalar juda yaxshi bajarildi?",
                        color = theme.textPrimary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = highlights,
                        onValueChange = { highlights = it },
                        placeholder = { Text("Masalan: 08:00 dagi rejamni kechikmasdan tugatdim...", color = theme.textSecondary, fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("journal_highlights"),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = theme.textPrimary,
                            unfocusedTextColor = theme.textPrimary,
                            focusedBorderColor = theme.primaryAccent,
                            unfocusedBorderColor = theme.glassBorderSubtleColor,
                            cursorColor = theme.primaryAccent
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "⚠️ Qaysi vazifalarda kechikish yoki xatolik bo'ldi?",
                        color = theme.textPrimary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = challenges,
                        onValueChange = { challenges = it },
                        placeholder = { Text("Masalan: Tushlikdan keyin biroz chalg'idim...", color = theme.textSecondary, fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("journal_challenges"),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = theme.textPrimary,
                            unfocusedTextColor = theme.textPrimary,
                            focusedBorderColor = theme.primaryAccent,
                            unfocusedBorderColor = theme.glassBorderSubtleColor,
                            cursorColor = theme.primaryAccent
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "📝 Ertangi kunga xulosa va reja:",
                        color = theme.textPrimary,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = reflections,
                        onValueChange = { reflections = it },
                        placeholder = { Text("Ertaga vaqt bankidan 20 daqiqa tejab, yangi dars o'rganaman...", color = theme.textSecondary, fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("journal_reflections"),
                        maxLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = theme.textPrimary,
                            unfocusedTextColor = theme.textPrimary,
                            focusedBorderColor = theme.primaryAccent,
                            unfocusedBorderColor = theme.glassBorderSubtleColor,
                            cursorColor = theme.primaryAccent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (highlights.isNotBlank() || challenges.isNotBlank() || reflections.isNotBlank()) {
                                onSaveEntry(stars, highlights, challenges, reflections)
                                highlights = ""
                                challenges = ""
                                reflections = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("btn_save_journal"),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Kundalikni saqlash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                    }
                }
            }
        }

        // Previous Entries History
        item {
            Text(
                text = "📜 O'tgan kunlar xulosalari (${entries.size})",
                color = theme.textPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (entries.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Hozircha saqlangan kundaliklar yo'q.\nBugungi kuningiz bo'yicha birinchi xulosani yozing!",
                        color = theme.textSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            items(entries, key = { it.id }) { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = BorderStroke(1.dp, theme.glassBorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📅 ${entry.dateStr}",
                                color = theme.primaryAccent,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                for (s in 1..entry.stars) {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        if (entry.highlights.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "🌟 ${entry.highlights}",
                                color = theme.textPrimary,
                                fontSize = 13.sp
                            )
                        }

                        if (entry.challenges.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "⚠️ ${entry.challenges}",
                                color = theme.textSecondary,
                                fontSize = 12.5.sp
                            )
                        }

                        if (entry.reflections.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📝 ${entry.reflections}",
                                color = theme.primaryAccent,
                                fontSize = 12.5.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
