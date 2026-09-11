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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NextPrayerInfo
import com.example.data.model.PrayerTime
import com.example.ui.theme.LocalLiquidTheme

@Composable
fun PrayerInfoBar(
    hijriDate: String,
    nextPrayer: NextPrayerInfo,
    prayers: List<PrayerTime>,
    onOpenQibla: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtle),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top row: Marg'ilon & Hijri
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(theme.primaryAccent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Marg'ilon namoz vaqtlari",
                        color = theme.textPrimary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(theme.primaryAccent.copy(alpha = 0.15f))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                        .clickable(onClick = onOpenQibla)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "Qibla",
                        tint = theme.primaryAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Qibla (247°)",
                        color = theme.primaryAccent,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Next Prayer Banner
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(theme.glassSurface)
                    .border(1.dp, theme.glassBorderSubtle, RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Navbatdagi: ${nextPrayer.currentOrNext.name} (${nextPrayer.currentOrNext.time})",
                            color = theme.primaryAccent,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = hijriDate,
                            color = theme.textSecondary,
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = nextPrayer.remainingFormatted + " qoldi",
                        color = theme.textPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // 5 Prayers Row
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                prayers.forEach { p ->
                    val isNext = p.name.equals(nextPrayer.currentOrNext.name, ignoreCase = true)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isNext) theme.primaryAccent.copy(alpha = 0.2f) else Color.Transparent)
                            .border(
                                1.dp,
                                if (isNext) SolidColor(theme.primaryAccent.copy(alpha = 0.6f)) else theme.glassBorderSubtle,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = p.name,
                            color = if (isNext) theme.primaryAccent else theme.textSecondary,
                            fontSize = 10.5.sp,
                            fontWeight = if (isNext) FontWeight.Bold else FontWeight.Normal
                        )
                        Text(
                            text = p.time,
                            color = if (isNext) theme.primaryAccent else theme.textPrimary,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
