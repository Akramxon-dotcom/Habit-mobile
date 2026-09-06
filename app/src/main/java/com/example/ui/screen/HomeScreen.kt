package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.HabitUiState
import com.example.ui.theme.HabitBorder
import com.example.ui.theme.HabitBorderStrong
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardElevated
import com.example.ui.theme.HabitContainerBg
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitError
import com.example.ui.theme.HabitErrorBg
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitGoldActive
import com.example.ui.theme.HabitOrange
import com.example.ui.theme.HabitSuccess
import com.example.ui.theme.HabitSuccessGlow
import com.example.ui.theme.HabitTextMuted
import com.example.ui.theme.HabitTextPrimary
import com.example.ui.theme.HabitTextSecondary
import com.example.ui.theme.HabitWarning

@Composable
fun HomeScreen(
    state: HabitUiState,
    onRefresh: () -> Unit,
    onToggleLocationService: () -> Unit,
    onOpenAccessibilitySettings: () -> Unit,
    onOpenPermissionsDialog: () -> Unit,
    onTestAlarm: () -> Unit,
    onSaveGeofence: (rtmLat: String, rtmLng: String, maktabLat: String, maktabLng: String, radius: String) -> Unit,
    onSaveBlockedPackages: (String) -> Unit
) {
    var showSettingsEditor by remember { mutableStateOf(false) }

    // Geofence local input states
    var rtmLatInput by remember(state.rtmLat) { mutableStateOf(state.rtmLat) }
    var rtmLngInput by remember(state.rtmLng) { mutableStateOf(state.rtmLng) }
    var maktabLatInput by remember(state.maktabLat) { mutableStateOf(state.maktabLat) }
    var maktabLngInput by remember(state.maktabLng) { mutableStateOf(state.maktabLng) }
    var radiusInput by remember(state.radiusMeters) { mutableStateOf(state.radiusMeters) }

    // Blocked apps input
    var blockedAppsInput by remember(state.blockedPackages) { mutableStateOf(state.blockedPackages) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HabitDarkBg)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Professional Polish App Header
        item {
            HeaderSection(
                lastSync = state.lastSyncFormatted,
                isLoading = state.isLoading,
                isAllGood = state.isLocationServiceRunning && state.isAccessibilityConnected,
                onRefresh = onRefresh
            )
        }

        // 2. Active Task Card with Accent Gold Left Stripe
        item {
            ProfessionalTaskCard(state = state)
        }

        // Section Title: ASOSIY BOSHQARUV
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ASOSIY BOSHQARUV",
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = HabitTextMuted
                )
                Text(
                    text = "3 ta funksiya",
                    style = MaterialTheme.typography.labelSmall,
                    color = HabitTextMuted,
                    fontSize = 10.sp
                )
            }
        }

        // 3 CORE ACTION BUTTONS (64dp height, rounded 16dp, matching Professional Polish theme)
        // BUTTON 1: GPS Service
        item {
            ProfessionalActionButton(
                title = if (state.isLocationServiceRunning) "GPS xizmatini to'xtatish" else "GPS xizmatini yoqish",
                subtitle = if (state.isLocationServiceRunning) {
                    "Faol kuzatilmoqda • RTM va Maktab radiusi (150m)"
                } else {
                    "RTM va Maktabga kelishni fonda aniqlash"
                },
                icon = Icons.Default.LocationOn,
                iconTint = if (state.isLocationServiceRunning) HabitTextPrimary else HabitDarkBg,
                iconBg = if (state.isLocationServiceRunning) HabitEmeraldGlowCustom else HabitDarkBg.copy(alpha = 0.12f),
                isPrimary = !state.isLocationServiceRunning,
                activeStatusColor = if (state.isLocationServiceRunning) HabitSuccess else null,
                testTag = "btn_gps_service",
                onClick = onToggleLocationService
            )
        }

        // BUTTON 2: Blocker Service (Accessibility)
        item {
            ProfessionalActionButton(
                title = if (state.isAccessibilityConnected) "Bloklashni sozlash (Faol)" else "Bloklash xizmatini yoq",
                subtitle = if (state.isAccessibilityConnected) {
                    "Band vaqtda Instagram, TikTok, YouTube cheklanadi"
                } else {
                    "Chalg'ituvchi ilovalarni yopish uchun yoqish kerak"
                },
                icon = Icons.Default.Shield,
                iconTint = if (state.isAccessibilityConnected) HabitSuccess else HabitError,
                iconBg = if (state.isAccessibilityConnected) HabitSuccess.copy(alpha = 0.15f) else HabitErrorBg,
                isPrimary = false,
                activeStatusColor = if (state.isAccessibilityConnected) HabitSuccess else HabitWarning,
                testTag = "btn_blocker_service",
                onClick = onOpenAccessibilitySettings
            )
        }

        // BUTTON 3: Permissions Check
        item {
            val grantedCount = listOf(
                state.permissionStatus.hasFineLocation,
                state.permissionStatus.hasBackgroundLocation,
                state.permissionStatus.hasNotification,
                state.permissionStatus.hasExactAlarm,
                state.permissionStatus.hasAccessibility,
                state.permissionStatus.isBatteryOptimizedIgnored
            ).count { it }

            ProfessionalActionButton(
                title = "Ruxsatlarni tekshirish",
                subtitle = "$grantedCount / 6 tasi berilgan • GPS, Alarm, Bildirishnoma",
                icon = Icons.Default.Security,
                iconTint = HabitGold,
                iconBg = HabitGold.copy(alpha = 0.15f),
                isPrimary = false,
                activeStatusColor = if (grantedCount == 6) HabitSuccess else HabitGold,
                testTag = "btn_check_permissions",
                onClick = onOpenPermissionsDialog
            )
        }

        // 4. Quick Test Trigger for Lock Screen Alarm
        item {
            ProfessionalAlarmTestCard(onTestAlarm = onTestAlarm)
        }

        // 5. Settings & Geofence Quick Glance Panel (2-column grid from Design HTML)
        item {
            QuickGlanceSettingsPanel(
                state = state,
                isExpanded = showSettingsEditor,
                onToggleExpand = { showSettingsEditor = !showSettingsEditor }
            )
        }

        // 6. Expandable Detailed Settings Editor
        if (showSettingsEditor) {
            item {
                SettingsEditorCard(
                    rtmLat = rtmLatInput,
                    onRtmLatChange = { rtmLatInput = it },
                    rtmLng = rtmLngInput,
                    onRtmLngChange = { rtmLngInput = it },
                    maktabLat = maktabLatInput,
                    onMaktabLatChange = { maktabLatInput = it },
                    maktabLng = maktabLngInput,
                    onMaktabLngChange = { maktabLngInput = it },
                    radius = radiusInput,
                    onRadiusChange = { radiusInput = it },
                    onSaveGeofence = {
                        onSaveGeofence(rtmLatInput, rtmLngInput, maktabLatInput, maktabLngInput, radiusInput)
                    },
                    blockedPackages = blockedAppsInput,
                    onBlockedPackagesChange = { blockedAppsInput = it },
                    onSaveBlockedPackages = {
                        onSaveBlockedPackages(blockedAppsInput)
                    }
                )
            }
        }

        // 7. Theme Footer (Micro status indicators & version)
        item {
            ThemeFooter(
                isGpsRunning = state.isLocationServiceRunning,
                isAccessibilityActive = state.isAccessibilityConnected,
                hasExactAlarm = state.permissionStatus.hasExactAlarm
            )
        }
    }
}

private val HabitEmeraldGlowCustom = Color(0x2E10B981)

@Composable
private fun HeaderSection(
    lastSync: String,
    isLoading: Boolean,
    isAllGood: Boolean,
    onRefresh: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp, start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Habit",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = HabitGold
            )
            Text(
                text = "NATIVE ANDROID COMPANION",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                ),
                fontSize = 10.sp,
                color = HabitTextSecondary
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Status Circle with Glowing Center Dot
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(HabitSuccess.copy(alpha = 0.10f))
                    .border(1.dp, HabitSuccess.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isAllGood) HabitSuccess else HabitWarning)
                )
            }

            // Refresh Button
            IconButton(
                onClick = onRefresh,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(HabitCardBg)
                    .border(1.dp, HabitBorder, CircleShape)
                    .testTag("btn_refresh_firestore")
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = HabitGold
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Yangilash ($lastSync)",
                        tint = HabitGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfessionalTaskCard(state: HabitUiState) {
    val habit = state.habitState

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = HabitCardBg),
        border = BorderStroke(1.dp, HabitBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Gold Left Accent Stripe (w-1.5 / 6.dp)
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .background(HabitGold)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
            ) {
                // Header with Category Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "HOZIRGI VAZIFA",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.2.sp
                            ),
                            fontSize = 11.sp,
                            color = HabitTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (habit.title.isNotBlank()) habit.title else "Hozirda bo'sh vaqt",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.White
                        )
                    }

                    // Category Pill
                    val categoryText = if (habit.category.isNotBlank()) habit.category.uppercase() else "ODDIY"
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = HabitGold.copy(alpha = 0.10f),
                        border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.25f))
                    ) {
                        Text(
                            text = categoryText,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            fontSize = 10.sp,
                            color = HabitGold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Time and Mode Metadata Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // End Time
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Tugaydi: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = HabitTextSecondary
                        )
                        Text(
                            text = if (habit.end.isNotBlank()) habit.end else "--:--",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = HabitGold
                        )
                    }

                    // Small vertical divider
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(12.dp)
                            .background(HabitBorderStrong)
                    )

                    // Blocking Mode Indicator
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Rejim: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (habit.blocking) HabitSuccess else HabitTextSecondary
                        )
                        Text(
                            text = if (habit.blocking) "Fokus (Bloklash)" else "Bo'sh vaqt",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = if (habit.blocking) Color.White else HabitTextSecondary
                        )
                    }
                }

                if (habit.note.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "“${habit.note}”",
                        style = MaterialTheme.typography.bodySmall,
                        color = HabitTextSecondary
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = HabitBorder
                )

                // Bottom sync metadata
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Oxirgi kelish (GPS):",
                            style = MaterialTheme.typography.labelSmall,
                            color = HabitTextMuted,
                            fontSize = 11.sp
                        )
                        Text(
                            text = if (habit.lastArrivalPlace.isNotBlank()) habit.lastArrivalPlace else "Hali qayd etilmagan",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = if (habit.lastArrivalPlace.isNotBlank()) HabitGold else HabitTextSecondary
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Oxirgi alarm javobi:",
                            style = MaterialTheme.typography.labelSmall,
                            color = HabitTextMuted,
                            fontSize = 11.sp
                        )
                        Text(
                            text = when (habit.lastAnswer) {
                                "done" -> "✅ Bajardim"
                                "not_done" -> "❌ Bajarmadim"
                                else -> "Kutilmoqda"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = if (habit.lastAnswer == "done") HabitSuccess else if (habit.lastAnswer == "not_done") HabitError else HabitTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfessionalActionButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    isPrimary: Boolean,
    activeStatusColor: Color?,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = if (isPrimary) HabitGold else HabitCardBg,
        border = if (isPrimary) null else BorderStroke(1.dp, HabitBorderStrong),
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Icon Box
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = if (isPrimary) HabitDarkBg else Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = if (isPrimary) HabitDarkBg.copy(alpha = 0.75f) else HabitTextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (activeStatusColor != null && !isPrimary) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(activeStatusColor)
                )
            }
        }
    }
}

@Composable
private fun ProfessionalAlarmTestCard(onTestAlarm: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = HabitCardBg,
        border = BorderStroke(1.dp, HabitBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(HabitGold.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = null,
                        tint = HabitGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "To'liq ekranli alarmni sinash",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White
                    )
                    Text(
                        text = "Qulf ekrani va ovoz/tebranishni tekshirish",
                        style = MaterialTheme.typography.labelSmall,
                        color = HabitTextSecondary
                    )
                }
            }

            Button(
                onClick = onTestAlarm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = HabitGold.copy(alpha = 0.20f),
                    contentColor = HabitGold
                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.40f)),
                modifier = Modifier.testTag("btn_test_alarm")
            ) {
                Text("Sinash", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun QuickGlanceSettingsPanel(
    state: HabitUiState,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = HabitContainerBg),
        border = BorderStroke(1.dp, HabitBorderStrong),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row: SOZLAMALAR and O'ZGARTIRISH
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SOZLAMALAR",
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = HabitTextMuted
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onToggleExpand() }
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (isExpanded) "Yopish" else "O'zgartirish",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = HabitGold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = HabitGold,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // 2-Column Grid: RTM & Maktab coordinates (Exact styling from Design HTML)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // RTM Coordinate Box
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = HabitDarkBg,
                    border = BorderStroke(1.dp, HabitBorderStrong),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "RTM KOORDINATA",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            fontSize = 10.sp,
                            color = HabitTextMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${state.rtmLat.take(7)}, ${state.rtmLng.take(7)}",
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            color = HabitTextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Maktab Coordinate Box
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = HabitDarkBg,
                    border = BorderStroke(1.dp, HabitBorderStrong),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "MAKTAB",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            fontSize = 10.sp,
                            color = HabitTextMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${state.maktabLat.take(7)}, ${state.maktabLng.take(7)}",
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            color = HabitTextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Blocked Apps list summary line
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Apps,
                    contentDescription = null,
                    tint = HabitTextMuted,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Bloklanadi: ",
                    style = MaterialTheme.typography.labelSmall,
                    color = HabitTextSecondary
                )
                Text(
                    text = "Instagram, TikTok, YouTube...",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = HabitTextPrimary
                )
            }
        }
    }
}

@Composable
private fun SettingsEditorCard(
    rtmLat: String,
    onRtmLatChange: (String) -> Unit,
    rtmLng: String,
    onRtmLngChange: (String) -> Unit,
    maktabLat: String,
    onMaktabLatChange: (String) -> Unit,
    maktabLng: String,
    onMaktabLngChange: (String) -> Unit,
    radius: String,
    onRadiusChange: (String) -> Unit,
    onSaveGeofence: () -> Unit,
    blockedPackages: String,
    onBlockedPackagesChange: (String) -> Unit,
    onSaveBlockedPackages: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = HabitCardElevated),
        border = BorderStroke(1.dp, HabitBorderStrong),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "GPS Koordinatalari va Radius",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = HabitGold
            )

            // RTM Inputs
            Text(
                text = "RTM koordinatalari (Lat, Lng):",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = rtmLat,
                    onValueChange = onRtmLatChange,
                    label = { Text("RTM Lat") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = customTextFieldColors()
                )
                OutlinedTextField(
                    value = rtmLng,
                    onValueChange = onRtmLngChange,
                    label = { Text("RTM Lng") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = customTextFieldColors()
                )
            }

            // Maktab Inputs
            Text(
                text = "Maktab koordinatalari (Lat, Lng):",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = maktabLat,
                    onValueChange = onMaktabLatChange,
                    label = { Text("Maktab Lat") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = customTextFieldColors()
                )
                OutlinedTextField(
                    value = maktabLng,
                    onValueChange = onMaktabLngChange,
                    label = { Text("Maktab Lng") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = customTextFieldColors()
                )
            }

            // Radius
            OutlinedTextField(
                value = radius,
                onValueChange = onRadiusChange,
                label = { Text("Aniqlash Radiusi (metr)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors()
            )

            Button(
                onClick = onSaveGeofence,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HabitGold,
                    contentColor = HabitDarkBg
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Koordinatalarni saqlash", fontWeight = FontWeight.Bold)
            }

            HorizontalDivider(color = HabitBorder, modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "Bloklanadigan Ilovalar Paket Nomlari",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = HabitGold
            )

            OutlinedTextField(
                value = blockedPackages,
                onValueChange = onBlockedPackagesChange,
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors()
            )

            Button(
                onClick = onSaveBlockedPackages,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HabitGold,
                    contentColor = HabitDarkBg
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ilovlar ro'yxatini saqlash", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ThemeFooter(
    isGpsRunning: Boolean,
    isAccessibilityActive: Boolean,
    hasExactAlarm: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // GPS Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(if (isGpsRunning) HabitSuccess else HabitError)
                )
                Text(
                    text = "GPS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    fontSize = 10.sp,
                    color = HabitTextSecondary
                )
            }

            // ACCESSIBILITY Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(if (isAccessibilityActive) HabitSuccess else HabitWarning)
                )
                Text(
                    text = "ACCESSIBILITY",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    fontSize = 10.sp,
                    color = HabitTextSecondary
                )
            }

            // ALARM Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(if (hasExactAlarm) HabitSuccess else HabitOrange)
                )
                Text(
                    text = "ALARM",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    fontSize = 10.sp,
                    color = HabitTextSecondary
                )
            }
        }

        Text(
            text = "v1.0.4",
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            color = HabitTextMuted
        )
    }
}

@Composable
private fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = HabitGold,
    unfocusedBorderColor = HabitBorderStrong,
    focusedLabelColor = HabitGold,
    unfocusedLabelColor = HabitTextSecondary,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White
)
