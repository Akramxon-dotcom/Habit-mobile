package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitSage

@Composable
fun AppUsageSettingsDialog(
    isEnabled: Boolean,
    ibratMinutes: Int,
    cakeMinutes: Int,
    hasPermission: Boolean,
    onToggleEnabled: (Boolean) -> Unit,
    onUpdateIbratMinutes: (Int) -> Unit,
    onUpdateCakeMinutes: (Int) -> Unit,
    onOpenPermissionSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    var ibratSliderValue by remember(ibratMinutes) { mutableFloatStateOf(ibratMinutes.toFloat()) }
    var cakeSliderValue by remember(cakeMinutes) { mutableFloatStateOf(cakeMinutes.toFloat()) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp)),
            color = HabitCardBg,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(HabitGold.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📱", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Ilova me'yorlari",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = HabitInk
                            )
                            Text(
                                text = "Avtomatik tekshirish va monitoring",
                                style = MaterialTheme.typography.bodySmall,
                                color = HabitInkSoft
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Yopish", tint = HabitInkSoft)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Permission Alert Banner
                if (!hasPermission) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFFF3E0),
                        border = BorderStroke(1.dp, Color(0xFFFFB74D)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFE65100), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Tizim ruxsati zarur",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.5.sp,
                                    color = Color(0xFFE65100)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Ibrat Farzandlari va Cake ilovalaridan necha daqiqa foydalanganingizni avtomatik hisoblash uchun «Ilovalardan foydalanish ruxsati»ni yoqishingiz kerak.",
                                fontSize = 12.sp,
                                color = HabitInk,
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = onOpenPermissionSettings,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ruxsat berish (Sozlamalar)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFE8F5E9),
                        border = BorderStroke(1.dp, Color(0xFFA5D6A7)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Foydalanish statistikasi ruxsati faol ✅",
                                color = Color(0xFF1B5E20),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.5.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Master Toggle Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.08f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Avtomatik vazifa belgilash",
                                fontWeight = FontWeight.Bold,
                                color = HabitInk,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Ilovada me'yor bajarilsa, so'rovsiz avtomatik «Bajarildi» qilinadi",
                                fontSize = 11.5.sp,
                                color = HabitInkSoft
                            )
                        }
                        Switch(
                            checked = isEnabled,
                            onCheckedChange = onToggleEnabled,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = HabitGold,
                                checkedTrackColor = HabitGold.copy(alpha = 0.3f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(visible = isEnabled) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // 1. Ibrat Farzandlari Goal
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.08f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("📚", fontSize = 18.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "IBRAT Farzandlari",
                                            fontWeight = FontWeight.Bold,
                                            color = HabitInk,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = HabitGold.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "${ibratSliderValue.toInt()} daqiqa",
                                            fontWeight = FontWeight.Bold,
                                            color = HabitInk,
                                            fontSize = 12.5.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Slider(
                                    value = ibratSliderValue,
                                    onValueChange = { ibratSliderValue = it },
                                    onValueChangeFinished = {
                                        onUpdateIbratMinutes(ibratSliderValue.toInt())
                                    },
                                    valueRange = 5f..90f,
                                    steps = 16,
                                    colors = SliderDefaults.colors(
                                        thumbColor = HabitGold,
                                        activeTrackColor = HabitGold
                                    )
                                )

                                Text(
                                    text = "Standard me'yor: 20 daqiqa (Kechki soat 19:40 - 20:00 yoki yakshanba)",
                                    fontSize = 11.sp,
                                    color = HabitInkSoft
                                )
                            }
                        }

                        // 2. Cake (Shadowing) Goal
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.08f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("🎙️", fontSize = 18.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Cake (Shadowing)",
                                            fontWeight = FontWeight.Bold,
                                            color = HabitInk,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = HabitSage.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "${cakeSliderValue.toInt()} daqiqa",
                                            fontWeight = FontWeight.Bold,
                                            color = HabitInk,
                                            fontSize = 12.5.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Slider(
                                    value = cakeSliderValue,
                                    onValueChange = { cakeSliderValue = it },
                                    onValueChangeFinished = {
                                        onUpdateCakeMinutes(cakeSliderValue.toInt())
                                    },
                                    valueRange = 5f..90f,
                                    steps = 16,
                                    colors = SliderDefaults.colors(
                                        thumbColor = HabitSage,
                                        activeTrackColor = HabitSage
                                    )
                                )

                                Text(
                                    text = "Standard me'yor: 20 daqiqa (Dars yo'q kunlar: 15:30 yoki yakshanba)",
                                    fontSize = 11.sp,
                                    color = HabitInkSoft
                                )
                            }
                        }

                        // Info Explanation Card
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFFF5F5F5),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "💡 Qanday ishlaydi?",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.5.sp,
                                    color = HabitInk
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "• Agar bugun belgilangan daqiqadan ko'proq shug'ullansangiz, vazifa vaqti kelganda hech qanday bezovta qiluvchi signal yoki so'rovsiz avtomatik «Bajarildi» deb belgilanadi.\n" +
                                            "• Agar kamroq (masalan 19 daqiqa) shug'ullangan bo'lsangiz, «Yana 1 daqiqa qoldi» deb eslatadi.\n" +
                                            "• Agar telefondan umuman foydalanilmagan bo'lsa (0 daqiqa), noutbuk yoki boshqa telefonda bajargan bo'lsangiz bitta tugma bilan tasdiqlash imkoni beriladi.",
                                    fontSize = 11.sp,
                                    color = HabitInkSoft,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold, contentColor = HabitDarkBg)
                ) {
                    Text("Tayyor (Saqlash)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
