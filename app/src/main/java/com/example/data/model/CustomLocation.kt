package com.example.data.model

import java.util.UUID

data class CustomLocation(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val lat: Double,
    val lng: Double,
    val radiusMeters: Float = 150f,
    val actionType: String = "AUTO_HABIT", // AUTO_HABIT, NOTIFICATION_ONLY, BLOCK_APPS
    val targetHabitTitle: String = "",
    val isEnabled: Boolean = true
)

data class InstalledAppInfo(
    val packageName: String,
    val appName: String,
    val isBlocked: Boolean = false
)
