package com.example.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalLiquidTheme

@Composable
fun AiWallpaperSection(
    isEnabled: Boolean,
    isUpdating: Boolean,
    statusMessage: String,
    explanation: String = "",
    wallpaperTarget: String = "BOTH", // "BOTH", "HOME", "LOCK"
    hasCustomWallpaper: Boolean = false,
    currentTaskTitle: String,
    currentTaskTime: String,
    onToggle: (Boolean) -> Unit,
    onSetTarget: (String) -> Unit = {},
    onPickImageUri: (Uri) -> Unit = {},
    onClearCustomWallpaper: () -> Unit = {},
    onApplyNow: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onPickImageUri(uri)
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtle)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(theme.primaryAccent.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Wallpaper,
                            contentDescription = null,
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "AI Bosh Ekran Fon Integratsiyasi",
                            color = theme.textPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Bo'sh katakka aqlli joylashtirish (Grid-Aware)",
                            color = theme.textSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                Switch(
                    checked = isEnabled,
                    onCheckedChange = onToggle,
                    colors = switchColorsTheme(),
                    modifier = Modifier.testTag("switch_ai_wallpaper")
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Explanation Card
            Text(
                text = "✨ Gemini Vision orqali bosh ekranning haqiqiy tuzilishi o'rganiladi: fonga chizilgan gul yoki tasvirlarga tegilmaydi, ilovalar turgan joylar aniqlanib, faqatgina bo'sh turgan oraliq yoki katakka Liquid Glass formatida vazifa yoziladi.",
                color = theme.textSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Screen Target Selector: Bosh ekran, Qulf ekrani, Har ikkisi
            Text(
                text = "🎯 Qaysi ekranga o'rnatilsin?",
                color = theme.textPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TargetChip(
                    title = "Bosh ekran",
                    icon = Icons.Default.PhoneAndroid,
                    selected = wallpaperTarget == "HOME",
                    onClick = { onSetTarget("HOME") },
                    modifier = Modifier.weight(1f)
                )
                TargetChip(
                    title = "Qulf ekrani",
                    icon = Icons.Default.Lock,
                    selected = wallpaperTarget == "LOCK",
                    onClick = { onSetTarget("LOCK") },
                    modifier = Modifier.weight(1f)
                )
                TargetChip(
                    title = "Ikkalasi",
                    icon = Icons.Default.Check,
                    selected = wallpaperTarget == "BOTH",
                    onClick = { onSetTarget("BOTH") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Custom Wallpaper / Screenshot Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(theme.glassSurface)
                    .border(1.dp, theme.glassBorderSubtle, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (hasCustomWallpaper) "📸 Shaxsiy fon/skrinshot yuklangan" else "📸 Bosh ekran skrinshoti / Fon rasmi",
                        color = if (hasCustomWallpaper) theme.primaryAccent else theme.textPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (hasCustomWallpaper)
                            "AI bu rasmdagi ilovalar va bo'sh kataklarni tahlil qiladi"
                        else
                            "Bosh ekraningiz skrinshotini tanlasangiz, AI ilovalar orasidagi bo'sh katakni o'ta aniq topadi",
                        color = theme.textSecondary,
                        fontSize = 11.sp
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (hasCustomWallpaper) {
                        IconButton(
                            onClick = onClearCustomWallpaper,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "O'chirish",
                                tint = Color(0xFFEF5350),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = theme.primaryAccent),
                        border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (hasCustomWallpaper) "O'zgartirish" else "Tanlash", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Real-time Visual Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(theme.glassSurface)
                    .border(1.dp, theme.glassBorderSubtle, RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PREVIEW · BO'SH KATAKKA MOSLASHTIRILGAN",
                            color = theme.primaryAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = currentTaskTime.ifBlank { "08:00 – 09:30" },
                            color = theme.textSecondary,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentTaskTitle.ifBlank { "Ingliz tili - Lug'at va Grammar" },
                        color = theme.textPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "💡 Liquid Glass kapsulasi fon elementlarini to'smagan holda joylashadi",
                        color = theme.textSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            if (explanation.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "🎯 AI qarori: $explanation",
                    color = theme.primaryAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Apply Now Button
            Button(
                onClick = onApplyNow,
                enabled = !isUpdating,
                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_apply_ai_wallpaper")
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AI bo'sh joylarni tahlil qilmoqda...", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                } else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("🎨 Hozir tahlil qilish va fonga o'rnatish", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (statusMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = statusMessage,
                    color = theme.textSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun TargetChip(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val bg = if (selected) theme.primaryAccent.copy(alpha = 0.2f) else theme.glassSurface
    val borderCol = if (selected) theme.primaryAccent else theme.glassBorderSubtleColor
    val textCol = if (selected) theme.primaryAccent else theme.textSecondary

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, borderCol, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = textCol, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(title, color = textCol, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
        }
    }
}

@Composable
private fun switchColorsTheme() = LocalLiquidTheme.current.let { theme ->
    androidx.compose.material3.SwitchDefaults.colors(
        checkedThumbColor = Color.Black,
        checkedTrackColor = theme.primaryAccent,
        uncheckedThumbColor = theme.textSecondary,
        uncheckedTrackColor = theme.glassSurface
    )
}
