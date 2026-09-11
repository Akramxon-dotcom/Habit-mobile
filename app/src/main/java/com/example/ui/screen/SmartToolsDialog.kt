package com.example.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PrayerTimeEngine
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardSoft
import com.example.ui.theme.HabitIndigo
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitLine
import com.example.ui.theme.HabitSage
import kotlinx.coroutines.delay

@Composable
fun SmartToolsBar(
    onOpenQibla: () -> Unit,
    onOpenBreath: () -> Unit,
    onOpenMotion: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = HabitCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, HabitLine),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Aqlli kun yordamchilari",
                    color = HabitInk,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "BHabits Smart",
                    color = HabitSage,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "Qibla, nafas mashqi va GPS avto-aniqlash",
                color = HabitInkSoft,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SmartToolButton(
                    label = "🧭 Qibla",
                    color = HabitSage,
                    onClick = onOpenQibla,
                    modifier = Modifier.weight(1f)
                )
                SmartToolButton(
                    label = "🫁 Nafas",
                    color = HabitIndigo,
                    onClick = onOpenBreath,
                    modifier = Modifier.weight(1f)
                )
                SmartToolButton(
                    label = "🏃 Harakat",
                    color = Color(0xFFF59E0B),
                    onClick = onOpenMotion,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SmartToolButton(
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun QiblaDialog(onDismiss: () -> Unit) {
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
                    Icon(imageVector = Icons.Default.Explore, contentDescription = null, tint = HabitSage)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Qibla Kompasi", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = HabitInkSoft)
                }
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Marg'ilon shahridan Ka'baga yo'nalish",
                    color = HabitInkSoft,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Compass Circle Visual
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(HabitCardSoft)
                        .border(2.dp, HabitSage.copy(alpha = 0.4f), CircleShape)
                ) {
                    Canvas(modifier = Modifier.size(140.dp)) {
                        drawCircle(
                            color = Color(0x2222C55E),
                            radius = size.minDimension / 2
                        )
                    }

                    // Rotating needle
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(120.dp)
                            .rotate(PrayerTimeEngine.QIBLA_BEARING_DEGREES)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(HabitSage)
                        )
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(46.dp)
                                .background(Brush.verticalGradient(listOf(HabitSage, Color.Transparent)))
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${PrayerTimeEngine.QIBLA_BEARING_DEGREES.toInt()}°",
                            color = HabitInk,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Janubi-G'arb",
                            color = HabitSage,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Masofa: ~${PrayerTimeEngine.DISTANCE_TO_MAKKAH_KM} km · Marg'ilon",
                    color = HabitInkSoft,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = HabitSage),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Tushunarli", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun BreathDialog(onDismiss: () -> Unit) {
    var phase by remember { mutableStateOf("Nafas oling") }
    var secondsLeft by remember { mutableStateOf(4) }

    LaunchedEffect(Unit) {
        while (true) {
            phase = "Nafas oling (Inhale)"
            for (i in 4 downTo 1) { secondsLeft = i; delay(1000) }
            phase = "Ushlab turing (Hold)"
            for (i in 4 downTo 1) { secondsLeft = i; delay(1000) }
            phase = "Nafas chiqaring (Exhale)"
            for (i in 4 downTo 1) { secondsLeft = i; delay(1000) }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "BreathAnim")
    val animScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "animScale"
    )

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
                    Icon(imageVector = Icons.Default.SelfImprovement, contentDescription = null, tint = HabitIndigo)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Nafas Mashqi (4-4-4)", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = HabitInkSoft)
                }
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Diqqatni jamlash va stressni kamaytirish uchun",
                    color = HabitInkSoft,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size((140 * animScale).dp)
                        .clip(CircleShape)
                        .background(HabitIndigo.copy(alpha = 0.2f))
                        .border(2.dp, HabitIndigo.copy(alpha = 0.7f), CircleShape)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$secondsLeft",
                            color = HabitInk,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = phase,
                            color = HabitIndigo,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Chuqur va erkin nafas oling. O'zingizni taskin topgan his qilasiz.",
                    color = HabitInkSoft,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = HabitIndigo),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Tugatish", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun MotionDialog(
    currentLocation: String,
    onDismiss: () -> Unit
) {
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
                    Icon(imageVector = Icons.Default.DirectionsRun, contentDescription = null, tint = Color(0xFFF59E0B))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Harakat & Geofence", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = HabitInkSoft)
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "GPS Geofence: Siz belgilangan joyga yetib borganingizda, tegishli vazifa avtomatik 'Bajardim' deb belgilanadi.",
                    color = HabitInkSoft,
                    fontSize = 12.sp
                )

                // Place 1: RTM
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(HabitCardSoft)
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("🏫 RTM O'quv Markazi", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("150m radius", color = HabitSage, fontSize = 11.sp)
                        }
                        Text("Vazifa: RTM Darsi va amaliyoti", color = HabitInkSoft, fontSize = 11.sp)
                    }
                }

                // Place 2: Maktab
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(HabitCardSoft)
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("🎒 Maktab", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("150m radius", color = HabitSage, fontSize = 11.sp)
                        }
                        Text("Vazifa: Maktab darslari", color = HabitInkSoft, fontSize = 11.sp)
                    }
                }

                Text(
                    text = "Joriy joylashuv holati: $currentLocation",
                    color = HabitSage,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Tushunarli", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    )
}
