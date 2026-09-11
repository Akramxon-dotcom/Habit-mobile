package com.example.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.HabitBlue
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardSoft
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitLine
import com.example.ui.theme.HabitSage

@Composable
fun TelegramReportSection(
    botToken: String,
    chatId: String,
    isSending: Boolean,
    statusMessage: String,
    onSaveSettings: (String, String) -> Unit,
    onSendReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tokenInput by remember(botToken) { mutableStateOf(botToken) }
    var chatIdInput by remember(chatId) { mutableStateOf(chatId) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(HabitCardBg, HabitCardSoft)
                )
            )
            .border(1.dp, HabitLine, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E2D4A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✈️", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Telegram Hisobot Tizimi",
                        color = HabitGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Kunlik intizom va vazifalar hisobotini yuborish",
                        color = HabitInkSoft,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Kunlik bajarilgan vazifalar foizi, vaqt jamg'armasi va natijalar o'zingizga yoki ota-onangiz Telegramiga avtomatik xabar shaklida yuboriladi.",
                color = HabitInkSoft,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = tokenInput,
                onValueChange = { tokenInput = it },
                label = { Text("Telegram Bot Token (@BotFather dan)", color = HabitInkSoft, fontSize = 11.sp) },
                placeholder = { Text("123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11", color = HabitInkSoft) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_telegram_token"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = HabitInk,
                    unfocusedTextColor = HabitInk,
                    focusedBorderColor = HabitGold,
                    unfocusedBorderColor = HabitLine
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chatIdInput,
                onValueChange = { chatIdInput = it },
                label = { Text("Telegram Chat ID (@userinfobot dan)", color = HabitInkSoft, fontSize = 11.sp) },
                placeholder = { Text("Masalan: 123456789", color = HabitInkSoft) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_telegram_chat_id"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = HabitInk,
                    unfocusedTextColor = HabitInk,
                    focusedBorderColor = HabitGold,
                    unfocusedBorderColor = HabitLine
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSaveSettings(tokenInput.trim(), chatIdInput.trim()) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HabitCardSoft),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Saqlash", color = HabitInk, fontSize = 12.sp)
                }

                Button(
                    onClick = onSendReport,
                    enabled = !isSending,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HabitBlue),
                    modifier = Modifier
                        .weight(1.5f)
                        .testTag("btn_send_telegram_report")
                ) {
                    if (isSending) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Yuborilmoqda...", fontSize = 12.sp)
                    } else {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Hisobotni yuborish", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (statusMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = statusMessage,
                    color = HabitSage,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
