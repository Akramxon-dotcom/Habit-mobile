package com.example.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Liquid Glass Theme Model
 * Defines a complete pro-tier "Liquid Glass" visual aesthetic.
 */
data class LiquidGlassStyle(
    val id: String,
    val displayName: String,
    val shortName: String,
    val subtitle: String,
    val badgeIcon: String,
    val primaryAccent: Color,
    val accentSecondary: Color,
    val accentTertiary: Color,
    val accentGlow: Color,
    val bgTop: Color,
    val bgBottom: Color,
    val orb1Color: Color,
    val orb2Color: Color,
    val orb3Color: Color,
    val glassSurface: Color,
    val glassSurfaceElevated: Color,
    val glassSurfaceSoft: Color,
    val glassBorderColor1: Color,
    val glassBorderColor2: Color,
    val dialogSurface: Color = Color(0xFF0E1712),
    val dialogSurfaceElevated: Color = Color(0xFF15251C),
    val textPrimary: Color = Color(0xFFF4F4F8),
    val textSecondary: Color = Color(0xFF9E9EB8),
    val textMuted: Color = Color(0xFF636380),
    val timelineTrackColor: Color = Color(0xFF1E1E2C)
) {
    val glassBorderColor: Color
        get() = glassBorderColor1

    val glassBorderSubtleColor: Color
        get() = glassBorderColor1.copy(alpha = 0.35f)

    val glassBorder: Brush
        get() = Brush.linearGradient(
            colors = listOf(glassBorderColor1, glassBorderColor2, Color.Transparent),
            start = Offset(0f, 0f),
            end = Offset(400f, 600f)
        )

    val glassBorderSubtle: Brush
        get() = Brush.linearGradient(
            colors = listOf(glassBorderColor1.copy(alpha = 0.35f), Color.Transparent),
            start = Offset(0f, 0f),
            end = Offset(300f, 400f)
        )

    val heroGradient: Brush
        get() = Brush.linearGradient(
            colors = listOf(primaryAccent, accentSecondary)
        )
}

/**
 * 5 Distinct Pro Liquid Glass Themes
 */
object LiquidGlassStyles {
    // 1. LIQUID EMERALD (Original Web Dashboard style: obsidian, jade, mint glow)
    val Emerald = LiquidGlassStyle(
        id = "emerald",
        displayName = "Liquid Emerald",
        shortName = "Emerald",
        subtitle = "Web bhabit uslubi (Nefrit)",
        badgeIcon = "💎",
        primaryAccent = Color(0xFF10B981),
        accentSecondary = Color(0xFF059669),
        accentTertiary = Color(0xFF34D399),
        accentGlow = Color(0x4010B981),
        bgTop = Color(0xFF06110B),
        bgBottom = Color(0xFF020604),
        orb1Color = Color(0x3510B981),
        orb2Color = Color(0x25059669),
        orb3Color = Color(0x1E34D399),
        glassSurface = Color(0x1C10B981),
        glassSurfaceElevated = Color(0x2E092116),
        glassSurfaceSoft = Color(0x1410B981),
        glassBorderColor1 = Color(0x6634D399),
        glassBorderColor2 = Color(0x2010B981),
        dialogSurface = Color(0xFF0C1611),
        dialogSurfaceElevated = Color(0xFF14241B),
        timelineTrackColor = Color(0xFF0D2418)
    )

    // 2. LIQUID AURORA (Cosmic Twilight: electric violet, magenta, deep cosmic nebula)
    val Aurora = LiquidGlassStyle(
        id = "aurora",
        displayName = "Liquid Aurora",
        shortName = "Aurora",
        subtitle = "Kosmik shafaq (Nilufar)",
        badgeIcon = "🔮",
        primaryAccent = Color(0xFFA855F7),
        accentSecondary = Color(0xFFEC4899),
        accentTertiary = Color(0xFFC084FC),
        accentGlow = Color(0x44A855F7),
        bgTop = Color(0xFF11071F),
        bgBottom = Color(0xFF05020A),
        orb1Color = Color(0x3E8B5CF6),
        orb2Color = Color(0x32EC4899),
        orb3Color = Color(0x24C084FC),
        glassSurface = Color(0x228B5CF6),
        glassSurfaceElevated = Color(0x301D0F35),
        glassSurfaceSoft = Color(0x18A855F7),
        glassBorderColor1 = Color(0x66E879F9),
        glassBorderColor2 = Color(0x258B5CF6),
        dialogSurface = Color(0xFF140D22),
        dialogSurfaceElevated = Color(0xFF201535),
        timelineTrackColor = Color(0xFF22133E)
    )

    // 3. LIQUID OBSIDIAN (Pure OLED Luxury: deep black, champagne gold, liquid platinum)
    val Obsidian = LiquidGlassStyle(
        id = "obsidian",
        displayName = "Liquid Obsidian",
        shortName = "Obsidian",
        subtitle = "OLED qora va oltin",
        badgeIcon = "👑",
        primaryAccent = Color(0xFFF59E0B),
        accentSecondary = Color(0xFFD97706),
        accentTertiary = Color(0xFFFDE68A),
        accentGlow = Color(0x40F59E0B),
        bgTop = Color(0xFF0A0907),
        bgBottom = Color(0xFF000000),
        orb1Color = Color(0x30F59E0B),
        orb2Color = Color(0x20D97706),
        orb3Color = Color(0x22FFFFFF),
        glassSurface = Color(0x1CFFFFFF),
        glassSurfaceElevated = Color(0x2A1C180E),
        glassSurfaceSoft = Color(0x12FFFFFF),
        glassBorderColor1 = Color(0x66FDE68A),
        glassBorderColor2 = Color(0x28F59E0B),
        dialogSurface = Color(0xFF12100E),
        dialogSurfaceElevated = Color(0xFF1C1814),
        timelineTrackColor = Color(0xFF241D12)
    )

    // 4. LIQUID OCEAN (Frost Cyan: deep abyss marine, electric cyan, cobalt ice)
    val Ocean = LiquidGlassStyle(
        id = "ocean",
        displayName = "Liquid Ocean",
        shortName = "Ocean",
        subtitle = "Moviy ummon (Kobalt)",
        badgeIcon = "🌊",
        primaryAccent = Color(0xFF06B6D4),
        accentSecondary = Color(0xFF2563EB),
        accentTertiary = Color(0xFF67E8F9),
        accentGlow = Color(0x4006B6D4),
        bgTop = Color(0xFF051221),
        bgBottom = Color(0xFF01060E),
        orb1Color = Color(0x3B06B6D4),
        orb2Color = Color(0x322563EB),
        orb3Color = Color(0x2067E8F9),
        glassSurface = Color(0x2006B6D4),
        glassSurfaceElevated = Color(0x2D072138),
        glassSurfaceSoft = Color(0x1406B6D4),
        glassBorderColor1 = Color(0x6667E8F9),
        glassBorderColor2 = Color(0x2506B6D4),
        dialogSurface = Color(0xFF091420),
        dialogSurfaceElevated = Color(0xFF102133),
        timelineTrackColor = Color(0xFF0E253F)
    )

    // 5. LIQUID AMBER (Solar Molten: warm charcoal, molten orange, radiant fire)
    val Amber = LiquidGlassStyle(
        id = "amber",
        displayName = "Liquid Amber",
        shortName = "Amber",
        subtitle = "Quyosh nuri (Olovli)",
        badgeIcon = "🔥",
        primaryAccent = Color(0xFFF97316),
        accentSecondary = Color(0xFFDC2626),
        accentTertiary = Color(0xFFFB923C),
        accentGlow = Color(0x44F97316),
        bgTop = Color(0xFF160A03),
        bgBottom = Color(0xFF070200),
        orb1Color = Color(0x38F97316),
        orb2Color = Color(0x30DC2626),
        orb3Color = Color(0x20FBBF24),
        glassSurface = Color(0x22F97316),
        glassSurfaceElevated = Color(0x30271307),
        glassSurfaceSoft = Color(0x16F97316),
        glassBorderColor1 = Color(0x66FB923C),
        glassBorderColor2 = Color(0x25F97316),
        dialogSurface = Color(0xFF16110D),
        dialogSurfaceElevated = Color(0xFF231B14),
        timelineTrackColor = Color(0xFF331608)
    )

    val AllThemes = listOf(Emerald, Aurora, Obsidian, Ocean, Amber)

    fun getById(id: String): LiquidGlassStyle {
        return AllThemes.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: Emerald
    }
}

val LocalLiquidTheme = compositionLocalOf { LiquidGlassStyles.Emerald }

@Composable
fun ProvideLiquidTheme(
    theme: LiquidGlassStyle,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLiquidTheme provides theme) {
        content()
    }
}

/**
 * Modifier to render a fluid ambient mesh background with 3 soft, glowing liquid orbs.
 * Produces the realistic liquid glass refraction environment.
 */
fun Modifier.liquidMeshBackground(theme: LiquidGlassStyle): Modifier = this.drawBehind {
    // 1. Base vertical gradient
    val baseBrush = Brush.verticalGradient(
        colors = listOf(theme.bgTop, theme.bgBottom)
    )
    drawRect(brush = baseBrush)

    // 2. Liquid Orb 1 (Top Left / Center)
    val orb1Center = Offset(x = size.width * 0.2f, y = size.height * 0.15f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(theme.orb1Color, Color.Transparent),
            center = orb1Center,
            radius = size.width * 0.75f
        ),
        center = orb1Center,
        radius = size.width * 0.75f
    )

    // 3. Liquid Orb 2 (Middle Right / Accent)
    val orb2Center = Offset(x = size.width * 0.85f, y = size.height * 0.45f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(theme.orb2Color, Color.Transparent),
            center = orb2Center,
            radius = size.width * 0.7f
        ),
        center = orb2Center,
        radius = size.width * 0.7f
    )

    // 4. Liquid Orb 3 (Bottom Center / Ambient depth)
    val orb3Center = Offset(x = size.width * 0.5f, y = size.height * 0.85f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(theme.orb3Color, Color.Transparent),
            center = orb3Center,
            radius = size.width * 0.8f
        ),
        center = orb3Center,
        radius = size.width * 0.8f
    )
}

/**
 * Universal Liquid Glass Container
 */
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(18.dp),
    elevated: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val theme = LocalLiquidTheme.current
    val surfaceColor = if (elevated) theme.glassSurfaceElevated else theme.glassSurface

    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Box(
        modifier = clickableModifier
            .clip(shape)
            .background(surfaceColor)
            .border(1.dp, theme.glassBorder, shape)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        content()
    }
}

/**
 * Glowing Liquid Pill for Status / Category Badges
 */
@Composable
fun LiquidGlassPill(
    text: String,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    icon: String? = null,
    onClick: (() -> Unit)? = null
) {
    val theme = LocalLiquidTheme.current
    val bg = if (active) theme.primaryAccent.copy(alpha = 0.22f) else theme.glassSurfaceSoft
    val borderBrush = if (active) {
        Brush.horizontalGradient(listOf(theme.primaryAccent, theme.accentTertiary))
    } else {
        theme.glassBorderSubtle
    }
    val textColor = if (active) theme.primaryAccent else theme.textSecondary

    val baseModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = baseModifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, borderBrush, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        if (icon != null) {
            Text(text = icon, fontSize = 11.sp, modifier = Modifier.padding(end = 4.dp))
        }
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
        )
    }
}

/**
 * Interactive Pro Theme Selector Bar
 * Shows all 5 Liquid Glass styles with real-time feedback.
 */
@Composable
fun LiquidThemeSelectorBar(
    selectedThemeId: String,
    onSelectTheme: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTheme = LocalLiquidTheme.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(currentTheme.glassSurfaceElevated.copy(alpha = 0.65f))
            .border(1.dp, currentTheme.glassBorderSubtle, RoundedCornerShape(20.dp))
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "✨", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "LIQUID GLASS DIZAYN (5 XIL USLUB)",
                    color = currentTheme.textPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
            Text(
                text = currentTheme.shortName,
                color = currentTheme.primaryAccent,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LiquidGlassStyles.AllThemes.forEach { themeItem ->
                val isSelected = themeItem.id.equals(selectedThemeId, ignoreCase = true)
                val animatedAccent by animateColorAsState(
                    targetValue = if (isSelected) themeItem.primaryAccent else Color.Transparent,
                    animationSpec = tween(300, easing = FastOutSlowInEasing),
                    label = "theme_border"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (isSelected) themeItem.glassSurfaceElevated else Color(0x10FFFFFF)
                        )
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            brush = if (isSelected) {
                                Brush.linearGradient(
                                    listOf(themeItem.primaryAccent, themeItem.accentTertiary)
                                )
                            } else {
                                Brush.linearGradient(listOf(Color(0x25FFFFFF), Color.Transparent))
                            },
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { onSelectTheme(themeItem.id) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    // Small glowing dot / icon
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(themeItem.primaryAccent)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Column {
                        Text(
                            text = "${themeItem.badgeIcon} ${themeItem.shortName}",
                            color = if (isSelected) themeItem.textPrimary else themeItem.textSecondary,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                        Text(
                            text = themeItem.subtitle,
                            color = if (isSelected) themeItem.primaryAccent else themeItem.textMuted,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}
