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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HabitAmber
import com.example.ui.theme.HabitBlue
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardElevated
import com.example.ui.theme.HabitCardSoft
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkMuted
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitLine
import com.example.ui.theme.HabitPurple

@Composable
fun AileadersCourseraTrackerCard(
    certsDoneToday: Int,
    targetDaily: Int,
    pricePerCert: Int,
    isSchoolDay: Boolean,
    onIncrementCert: () -> Unit,
    onDecrementCert: () -> Unit,
    onOpenSchedulePrompt: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalEarned = certsDoneToday * pricePerCert
    val targetEarned = targetDaily * pricePerCert
    val progress = (certsDoneToday.toFloat() / targetDaily.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
    val estimatedMinutesSpent = certsDoneToday * 7

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = HabitCardBg),
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, HabitLine)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row: Title & School Day Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(HabitGold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎓", fontSize = 20.sp)
                    }
                    Column {
                        Text(
                            text = "Aileaders.uz Coursera",
                            color = HabitInk,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Besh Million Sun'iy Intellekt Yetakchilari",
                            color = HabitGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Interactive school day badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSchoolDay) HabitBlue.copy(alpha = 0.18f) else HabitAmber.copy(alpha = 0.18f))
                        .clickable { onOpenSchedulePrompt() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isSchoolDay) "🏫 Maktab kuni" else "🏠 Uy kuni (Turbo)",
                        color = if (isSchoolDay) HabitBlue else HabitAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Stats Card (Earned, Certs done, Minutes)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = HabitCardSoft),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$certsDoneToday / $targetDaily",
                            color = HabitInk,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Sertifikat",
                            color = HabitInkSoft,
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(30.dp)
                            .background(HabitLine)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${String.format("%,d", totalEarned)} so'm",
                            color = HabitGold,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Daromad (5k/dona)",
                            color = HabitInkSoft,
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(30.dp)
                            .background(HabitLine)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "~$estimatedMinutesSpent daq",
                            color = HabitPurple,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Sarflangan vaqt",
                            color = HabitInkSoft,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Progress bar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Kunlik reja: ${(progress * 100).toInt()}%",
                        color = HabitInkSoft,
                        fontSize = 11.sp
                    )
                    Text(
                        text = "Maqsad: ${String.format("%,d", targetEarned)} so'm",
                        color = HabitInkMuted,
                        fontSize = 11.sp
                    )
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = HabitGold,
                    trackColor = HabitLine
                )
            }

            // Action Buttons: (-) and (+1 Sertifikat olindi)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecrementCert,
                    enabled = certsDoneToday > 0,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(HabitCardElevated)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Bitta kamaytirish",
                        tint = if (certsDoneToday > 0) HabitInk else HabitInkMuted
                    )
                }

                Button(
                    onClick = onIncrementCert,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HabitGold,
                        contentColor = Color(0xFF0F2415)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+1 Sertifikat (+5,000 so'm)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                OutlinedButton(
                    onClick = onOpenSchedulePrompt,
                    modifier = Modifier.height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = HabitInkSoft),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HabitLine)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Jadvalni moslash",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
