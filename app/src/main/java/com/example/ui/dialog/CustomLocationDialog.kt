package com.example.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalLiquidTheme
import java.util.Locale

@Composable
fun CustomLocationDialog(
    isCapturingLocation: Boolean,
    onCaptureCurrentLocation: ((Double, Double) -> Unit) -> Unit,
    onSaveLocation: (name: String, lat: Double, lng: Double, radius: Float, actionType: String, targetHabit: String) -> Unit,
    onDismiss: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    var name by remember { mutableStateOf("") }
    var latStr by remember { mutableStateOf("") }
    var lngStr by remember { mutableStateOf("") }
    var radiusStr by remember { mutableStateOf("150") }
    var targetHabit by remember { mutableStateOf("") }
    var actionType by remember { mutableStateOf("NOTIFY_AND_SET_HABIT") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = theme.dialogSurface,
        shape = RoundedCornerShape(26.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📍 Yangi joylashuv qo'shish",
                    color = theme.textPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Yopish", tint = theme.textSecondary)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Joylashuv nomi (masalan: Sport zal, Kutubxona)") },
                    singleLine = true,
                    colors = customDialogTextFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // GPS Capture Current Location Button
                OutlinedButton(
                    onClick = {
                        onCaptureCurrentLocation { lat, lng ->
                            latStr = String.format(Locale.US, "%.6f", lat)
                            lngStr = String.format(Locale.US, "%.6f", lng)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, theme.primaryAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isCapturingLocation) {
                        CircularProgressIndicator(color = theme.primaryAccent, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GPS aniqlanmoqda...", color = theme.primaryAccent, fontSize = 13.sp)
                    } else {
                        Icon(imageVector = Icons.Default.MyLocation, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("📍 Hozirgi joylashuvimni olish", color = theme.primaryAccent, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = latStr,
                        onValueChange = { latStr = it },
                        label = { Text("Kenglik (Lat)") },
                        singleLine = true,
                        colors = customDialogTextFieldColors(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lngStr,
                        onValueChange = { lngStr = it },
                        label = { Text("Uzunlik (Lng)") },
                        singleLine = true,
                        colors = customDialogTextFieldColors(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = radiusStr,
                    onValueChange = { radiusStr = it },
                    label = { Text("Radius (metrda, tavsiya: 150)") },
                    singleLine = true,
                    colors = customDialogTextFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = targetHabit,
                    onValueChange = { targetHabit = it },
                    label = { Text("Kelganda faollashadigan vazifa (ixtiyoriy)") },
                    singleLine = true,
                    colors = customDialogTextFieldColors(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val lat = latStr.toDoubleOrNull() ?: 0.0
                    val lng = lngStr.toDoubleOrNull() ?: 0.0
                    val rad = radiusStr.toFloatOrNull() ?: 150f
                    if (name.isNotBlank() && lat != 0.0 && lng != 0.0) {
                        onSaveLocation(name, lat, lng, rad, actionType, targetHabit)
                        onDismiss()
                    }
                },
                enabled = name.isNotBlank() && latStr.isNotBlank() && lngStr.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Saqlash", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
private fun customDialogTextFieldColors() = LocalLiquidTheme.current.let { theme ->
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = theme.textPrimary,
        unfocusedTextColor = theme.textPrimary,
        focusedBorderColor = theme.primaryAccent,
        unfocusedBorderColor = theme.glassBorderSubtleColor,
        focusedLabelColor = theme.primaryAccent,
        unfocusedLabelColor = theme.textSecondary,
        focusedContainerColor = theme.glassSurface,
        unfocusedContainerColor = theme.glassSurface
    )
}
