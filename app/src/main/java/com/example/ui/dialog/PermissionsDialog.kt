package com.example.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.permissions.PermissionUtils
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardElevated
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitError
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitSuccess

@Composable
fun PermissionsDialog(
    status: PermissionUtils.PermissionStatus,
    onDismiss: () -> Unit,
    onRequestForegroundLocation: () -> Unit,
    onRequestBackgroundLocation: () -> Unit,
    onRequestNotification: () -> Unit,
    onOpenExactAlarm: () -> Unit,
    onOpenAccessibility: () -> Unit,
    onRequestBatteryOptimization: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = HabitCardBg,
        titleContentColor = HabitGold,
        textContentColor = Color.White,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = HabitGold,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Tizim Ruxsatlari",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Habit barcha 3 ta funksiyani uzluksiz bajarishi uchun quyidagi ruxsatlar zarur:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 1. Fine Location
                PermissionItemCard(
                    title = "GPS Joylashuv",
                    subtitle = "RTM va Maktabga kelishni aniqlash uchun",
                    icon = Icons.Default.LocationOn,
                    isGranted = status.hasFineLocation,
                    actionText = "Ruxsat berish",
                    onAction = onRequestForegroundLocation
                )

                // 2. Background Location
                PermissionItemCard(
                    title = "Fonda Joylashuv (Har doim)",
                    subtitle = "Ilova yopiq bo'lganda ham kelishni aniqlash uchun",
                    icon = Icons.Default.LocationOn,
                    isGranted = status.hasBackgroundLocation,
                    actionText = "Yoqish",
                    onAction = onRequestBackgroundLocation
                )

                // 3. Notification
                PermissionItemCard(
                    title = "Bildirishnomalar",
                    subtitle = "Doimiy xizmat va signallar uchun",
                    icon = Icons.Default.Notifications,
                    isGranted = status.hasNotification,
                    actionText = "Ruxsat berish",
                    onAction = onRequestNotification
                )

                // 4. Exact Alarm
                PermissionItemCard(
                    title = "Aniq Vaqtli Alarm (Exact Alarm)",
                    subtitle = "Vazifa tugashi bilan zudlikda signal chalish",
                    icon = Icons.Default.Alarm,
                    isGranted = status.hasExactAlarm,
                    actionText = "Sozlash",
                    onAction = onOpenExactAlarm
                )

                // 5. Accessibility Service
                PermissionItemCard(
                    title = "Maxsus Imkoniyatlar (Accessibility)",
                    subtitle = "Band vaqtda chalg'ituvchi ilovalarni bloklash",
                    icon = Icons.Default.Shield,
                    isGranted = status.hasAccessibility,
                    actionText = "Sozlamalarni ochish",
                    onAction = onOpenAccessibility
                )

                // 6. Battery Optimization
                PermissionItemCard(
                    title = "Batareya Cheklovlarisiz",
                    subtitle = "Tizim fon xizmatini o'chirib qo'ymasligi uchun",
                    icon = Icons.Default.BatteryAlert,
                    isGranted = status.isBatteryOptimizedIgnored,
                    actionText = "Ruxsat berish",
                    onAction = onRequestBatteryOptimization
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = HabitGold,
                    contentColor = HabitDarkBg
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Tushunarli", fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
private fun PermissionItemCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isGranted: Boolean,
    actionText: String,
    onAction: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = HabitCardElevated),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    color = if (isGranted) HabitSuccess.copy(alpha = 0.2f) else HabitError.copy(alpha = 0.2f)
                ) {
                    Icon(
                        imageVector = if (isGranted) Icons.Default.Check else icon,
                        contentDescription = null,
                        tint = if (isGranted) HabitSuccess else HabitGold,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.65f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isGranted) {
                Text(
                    text = "Berilgan",
                    color = HabitSuccess,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            } else {
                OutlinedButton(
                    onClick = onAction,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = HabitGold),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HabitGold),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = actionText,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
