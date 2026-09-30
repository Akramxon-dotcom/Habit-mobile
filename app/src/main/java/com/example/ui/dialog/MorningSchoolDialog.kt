package com.example.ui.dialog

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun MorningSchoolDialog(
    onSelectSchoolDay: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = HabitCardBg,
        shape = RoundedCornerShape(20.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(HabitGold.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌅", fontSize = 22.sp)
                }
                Column {
                    Text(
                        text = "Tonggi Kun Tartibi",
                        color = HabitInk,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Aileaders.uz & Namoz & Ingliz Tili",
                        color = HabitGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Assalomu alaykum! Bugun maktabga borasizmi? Shunga qarab sun'iy intellekt kun tartibingizni avtomatik moslashtiradi:",
                    color = HabitInkSoft,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                // Overview highlights
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = HabitCardSoft),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🕌 ", fontSize = 14.sp)
                            Text(
                                "5 Vaqt Namoz (Bomdod 05:00, Peshin 12:25, Asr 16:15, Shom 17:59, Xufton 19:13)",
                                color = HabitInk,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("⚡ ", fontSize = 14.sp)
                            Text(
                                "Coursera: 40-50 ta sertifikat (Kunlik 200,000 - 250,000 so'm)",
                                color = HabitAmber,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🇬🇧 ", fontSize = 14.sp)
                            Text(
                                "Offline Ingliz tili darsi (16:45 - 19:13) uzluksiz",
                                color = HabitBlue,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌙 ", fontSize = 14.sp)
                            Text(
                                "Uyqu: 22:45 da yotish, 05:00 da tetik uyg'onish",
                                color = HabitPurple,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                HorizontalDivider(color = HabitLine)

                // Option 1: Maktabga boraman
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, HabitGold.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .clickable { onSelectSchoolDay(true) },
                    colors = CardDefaults.cardColors(containerColor = HabitCardElevated),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "🏫 Ha, bugun maktabga boraman",
                                color = HabitInk,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(HabitGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    "TAVSIYA ETILADI",
                                    color = HabitGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "• 08:00 - 12:25 Maktab darslari\n• 12:25 Peshin namozi & 13:00 Tushlik\n• 3 ta Coursera sprinti: 06:10 (12 ta), 14:15 (20 ta), 20:25 (18 ta)\n• Jami: 50 ta sertifikat (250,000 so'm)\n• 16:15 Asr, 16:45 Offline dars, 17:59 Shom, 19:13 Xufton",
                            color = HabitInkSoft,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Option 2: Uyda qolaman
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, HabitAmber.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .clickable { onSelectSchoolDay(false) },
                    colors = CardDefaults.cardColors(containerColor = HabitCardElevated),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "🏠 Yo'q, bugun uyda qolaman",
                                color = HabitInk,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(HabitAmber.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    "TURBO SPRINT",
                                    color = HabitAmber,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "• Katta Coursera sprintlari: 06:10, 10:00, 20:25\n• Jami: 55-60 ta sertifikat (275,000 - 300,000 so'm)\n• 5 vaqt namoz (12:25 Peshin, 16:15 Asr, 17:59 Shom, 19:13 Xufton)\n• 16:45 Offline Ingliz tili darsi qat'iy saqlanadi",
                            color = HabitInkSoft,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Keyinroq tanlash", color = HabitInkMuted, fontSize = 13.sp)
            }
        }
    )
}
