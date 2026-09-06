package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HabitColorScheme = darkColorScheme(
  primary = HabitGold,
  onPrimary = HabitDarkBg,
  primaryContainer = HabitGoldDark,
  onPrimaryContainer = HabitGoldLight,
  secondary = HabitGoldLight,
  onSecondary = HabitDarkBg,
  background = HabitDarkBg,
  onBackground = HabitTextPrimary,
  surface = HabitCardBg,
  onSurface = HabitTextPrimary,
  surfaceVariant = HabitCardElevated,
  onSurfaceVariant = HabitTextSecondary,
  outline = HabitBorder,
  error = HabitError,
  onError = Color.White
)

@Composable
fun HabitTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = HabitColorScheme,
    typography = Typography,
    content = content
  )
}

