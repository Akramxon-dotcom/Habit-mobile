package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun HabitTheme(
  themeStyle: LiquidGlassStyle = LiquidGlassStyles.Emerald,
  content: @Composable () -> Unit,
) {
  val dynamicScheme = darkColorScheme(
    primary = themeStyle.primaryAccent,
    onPrimary = Color.Black,
    primaryContainer = themeStyle.accentSecondary,
    onPrimaryContainer = Color.White,
    secondary = themeStyle.accentTertiary,
    onSecondary = Color.Black,
    background = themeStyle.bgBottom,
    onBackground = themeStyle.textPrimary,
    surface = themeStyle.glassSurfaceElevated,
    onSurface = themeStyle.textPrimary,
    surfaceVariant = themeStyle.glassSurface,
    onSurfaceVariant = themeStyle.textSecondary,
    outline = themeStyle.glassBorderColor1,
    error = Color(0xFFEF4444),
    onError = Color.White
  )

  ProvideLiquidTheme(theme = themeStyle) {
    MaterialTheme(
      colorScheme = dynamicScheme,
      typography = Typography,
      content = content
    )
  }
}

