package com.example.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScheduleItem
import com.example.data.remote.GeminiClient
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardSoft
import com.example.ui.theme.HabitIndigo
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitLine
import com.example.ui.theme.HabitSage
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun SmartAddDialog(
    onDismiss: () -> Unit,
    onTaskAdded: (ScheduleItem) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("09:00") }
    var endTime by remember { mutableStateOf("10:00") }
    var category by remember { mutableStateOf("work") }
    var priority by remember { mutableStateOf("orta") }
    var note by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = HabitCardBg,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = HabitSage,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Aqlli Vazifa Qo'shish",
                        color = HabitInk,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = HabitInkSoft)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Vazifa nomini yozing, Gemini AI esa mos vaqt, toifa va eslatmani tavsiya qiladi:",
                    color = HabitInkSoft,
                    fontSize = 12.sp
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Vazifa nomi", color = HabitInkSoft) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = HabitCardSoft,
                        unfocusedContainerColor = HabitCardSoft,
                        focusedTextColor = HabitInk,
                        unfocusedTextColor = HabitInk,
                        focusedIndicatorColor = HabitSage,
                        unfocusedIndicatorColor = HabitLine
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // AI Suggest Button
                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            isGenerating = true
                            scope.launch {
                                val res = GeminiClient.suggestTask(title, context = context)
                                isGenerating = false
                                res.onSuccess { suggestion ->
                                    startTime = suggestion.startTime
                                    endTime = suggestion.endTime
                                    category = suggestion.category
                                    priority = suggestion.priority
                                    note = suggestion.note
                                }
                            }
                        }
                    },
                    enabled = title.isNotBlank() && !isGenerating,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HabitIndigo,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI tahlil qilmoqda…", fontSize = 12.sp)
                    } else {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("✨ AI Tavsiya olish", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                // Time Inputs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text("Boshlanish", color = HabitInkSoft, fontSize = 11.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = HabitCardSoft,
                            unfocusedContainerColor = HabitCardSoft,
                            focusedTextColor = HabitInk,
                            unfocusedTextColor = HabitInk,
                            focusedIndicatorColor = HabitSage,
                            unfocusedIndicatorColor = HabitLine
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("Tugash", color = HabitInkSoft, fontSize = 11.sp) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = HabitCardSoft,
                            unfocusedContainerColor = HabitCardSoft,
                            focusedTextColor = HabitInk,
                            unfocusedTextColor = HabitInk,
                            focusedIndicatorColor = HabitSage,
                            unfocusedIndicatorColor = HabitLine
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                // Category & Priority Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(HabitCardSoft)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Toifa: ${category.uppercase()}", color = HabitSage, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("Muhimlik: ${priority.uppercase()}", color = HabitInkSoft, fontSize = 11.sp)
                    }
                }

                if (note.isNotBlank()) {
                    Text(
                        text = "💡 Maslahat: $note",
                        color = HabitInkSoft,
                        fontSize = 11.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val newItem = ScheduleItem(
                            id = "task_" + UUID.randomUUID().toString().take(8),
                            title = title,
                            start = startTime,
                            end = endTime,
                            category = category,
                            priority = priority,
                            note = note
                        )
                        onTaskAdded(newItem)
                        onDismiss()
                    }
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = HabitSage),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Rejaga qo'shish", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Bekor qilish", color = HabitInkSoft)
            }
        }
    )
}
