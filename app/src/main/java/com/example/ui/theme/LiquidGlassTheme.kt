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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 6 Fundamentally Distinct Habit Design Archetypes
 */
enum class DesignArchetype {
    NEO_GLASS,        // Apple iOS 18 / Concentric Fitness Rings / Fluid glass
    BENTO_BRUTALIST,  // Linear & Notion Bento Box Grid (8dp sharp, technical borders)
    CYBERPUNK_HUD,    // Sci-Fi Terminal HUD & Matrix (Chamfered cut corners, scanlines)
    ZEN_ORGANIC,      // Fabulous / Headspace Zen Botanical (32dp pill, soft pastel)
    SOLAR_FLAME,      // Streaks / Athlete High-Energy (Fiery gradients, bold action)
    OLED_MINIMAL      // Nothing OS / Blloc Minimalist (Pure #000000, dot-matrix)
}

enum class BackgroundPattern {
    LIQUID_ORBS,
    BENTO_GRID,
    SCANLINES,
    ORGANIC_AURA,
    SOLAR_PLASMA,
    DOT_MATRIX
}

/**
 * Complete Pro Design Archetype System
 */
data class LiquidGlassStyle(
    val id: String,
    val displayName: String,
    val shortName: String,
    val subtitle: String,
    val badgeIcon: String,
    val archetype: DesignArchetype = DesignArchetype.NEO_GLASS,
    val cardShape: Shape = RoundedCornerShape(24.dp),
    val buttonShape: Shape = RoundedCornerShape(16.dp),
    val badgeShape: Shape = CircleShape,
    val borderWidth: Dp = 1.dp,
    val backgroundPattern: BackgroundPattern = BackgroundPattern.LIQUID_ORBS,
    val fontMood: String = "Sleek Modern",
    val progressStyleName: String = "Concentric Ring",
    val styleTagline: String = "Apple Fitness shisha halqalari",
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
 * 6 Fundamentally Distinct Habit App Themes
 */
object LiquidGlassStyles {

    // 1. NEO-GLASS RINGS (Apple Fitness & iOS 18 Liquid Focus)
    val NeoGlass = LiquidGlassStyle(
        id = "neo_glass",
        displayName = "Neo-Glass Rings",
        shortName = "Neo-Glass",
        subtitle = "Apple Fitness halqalari",
        badgeIcon = "🍏",
        archetype = DesignArchetype.NEO_GLASS,
        cardShape = RoundedCornerShape(26.dp),
        buttonShape = RoundedCornerShape(18.dp),
        badgeShape = CircleShape,
        borderWidth = 1.dp,
        backgroundPattern = BackgroundPattern.LIQUID_ORBS,
        fontMood = "iOS 18 Liquid",
        progressStyleName = "Concentric Fitness Rings",
        styleTagline = "Apple iOS 18 suyuq shisha va 3D halqalar",
        primaryAccent = Color(0xFF10B981),
        accentSecondary = Color(0xFF059669),
        accentTertiary = Color(0xFF34D399),
        accentGlow = Color(0x4010B981),
        bgTop = Color(0xFF06130C),
        bgBottom = Color(0xFF020704),
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

    // 2. LINEAR BENTO GRID (Notion, Linear & Raycast Pro)
    val BentoBrutalist = LiquidGlassStyle(
        id = "bento_brutalist",
        displayName = "Linear Bento Grid",
        shortName = "Bento Grid",
        subtitle = "Notion & Linear texnik bento",
        badgeIcon = "🍱",
        archetype = DesignArchetype.BENTO_BRUTALIST,
        cardShape = RoundedCornerShape(10.dp),
        buttonShape = RoundedCornerShape(8.dp),
        badgeShape = RoundedCornerShape(6.dp),
        borderWidth = 1.5.dp,
        backgroundPattern = BackgroundPattern.BENTO_GRID,
        fontMood = "Linear Monospace",
        progressStyleName = "Segmented Bento Blocks",
        styleTagline = "Qat'iy 2x2 bento-box va texnik grid",
        primaryAccent = Color(0xFF00E5FF),
        accentSecondary = Color(0xFF0091EA),
        accentTertiary = Color(0xFF80D8FF),
        accentGlow = Color(0x4400E5FF),
        bgTop = Color(0xFF070C12),
        bgBottom = Color(0xFF020406),
        orb1Color = Color(0x2800E5FF),
        orb2Color = Color(0x200091EA),
        orb3Color = Color(0x1580D8FF),
        glassSurface = Color(0x1800E5FF),
        glassSurfaceElevated = Color(0x240A1828),
        glassSurfaceSoft = Color(0x1000E5FF),
        glassBorderColor1 = Color(0x8800E5FF),
        glassBorderColor2 = Color(0x400091EA),
        dialogSurface = Color(0xFF08121E),
        dialogSurfaceElevated = Color(0xFF101C2B),
        timelineTrackColor = Color(0xFF0B1F35)
    )

    // 3. CYBERPUNK HUD (Sci-Fi Console & Matrix Terminal)
    val CyberpunkHud = LiquidGlassStyle(
        id = "cyberpunk_hud",
        displayName = "Cyber Matrix HUD",
        shortName = "Cyber Matrix",
        subtitle = "Sci-Fi HUD konsol & Asimmetrik",
        badgeIcon = "⚡",
        archetype = DesignArchetype.CYBERPUNK_HUD,
        cardShape = RoundedCornerShape(topStart = 0.dp, bottomEnd = 0.dp, topEnd = 20.dp, bottomStart = 20.dp),
        buttonShape = RoundedCornerShape(topStart = 0.dp, bottomEnd = 0.dp, topEnd = 12.dp, bottomStart = 12.dp),
        badgeShape = RoundedCornerShape(4.dp),
        borderWidth = 1.5.dp,
        backgroundPattern = BackgroundPattern.SCANLINES,
        fontMood = "Cyber Terminal",
        progressStyleName = "Matrix Console Bar",
        styleTagline = "Kesilgan burchaklar & CRT scanline",
        primaryAccent = Color(0xFF00FF66),
        accentSecondary = Color(0xFF00B0FF),
        accentTertiary = Color(0xFFCCFF00),
        accentGlow = Color(0x5500FF66),
        bgTop = Color(0xFF051008),
        bgBottom = Color(0xFF010402),
        orb1Color = Color(0x3500FF66),
        orb2Color = Color(0x2500B0FF),
        orb3Color = Color(0x20CCFF00),
        glassSurface = Color(0x2000FF66),
        glassSurfaceElevated = Color(0x30092212),
        glassSurfaceSoft = Color(0x1400FF66),
        glassBorderColor1 = Color(0x8800FF66),
        glassBorderColor2 = Color(0x3000B0FF),
        dialogSurface = Color(0xFF07180D),
        dialogSurfaceElevated = Color(0xFF0D2A18),
        timelineTrackColor = Color(0xFF0C2B16)
    )

    // 4. ZEN BOTANICAL (Fabulous & Headspace Calm Mind)
    val ZenOrganic = LiquidGlassStyle(
        id = "zen_organic",
        displayName = "Zen Botanical",
        shortName = "Zen Mind",
        subtitle = "Fabulous & Headspace xotirjamlik",
        badgeIcon = "🌿",
        archetype = DesignArchetype.ZEN_ORGANIC,
        cardShape = RoundedCornerShape(32.dp),
        buttonShape = RoundedCornerShape(24.dp),
        badgeShape = CircleShape,
        borderWidth = 0.8.dp,
        backgroundPattern = BackgroundPattern.ORGANIC_AURA,
        fontMood = "Calm Serif & Organic",
        progressStyleName = "Serene Wave Meter",
        styleTagline = "Yumshoq toshdek kapsulalar & tabiiy aura",
        primaryAccent = Color(0xFF14B8A6),
        accentSecondary = Color(0xFF0D9488),
        accentTertiary = Color(0xFF5EEAD4),
        accentGlow = Color(0x3514B8A6),
        bgTop = Color(0xFF0C1412),
        bgBottom = Color(0xFF040807),
        orb1Color = Color(0x3014B8A6),
        orb2Color = Color(0x200D9488),
        orb3Color = Color(0x20FDE047),
        glassSurface = Color(0x1E14B8A6),
        glassSurfaceElevated = Color(0x2B122822),
        glassSurfaceSoft = Color(0x1214B8A6),
        glassBorderColor1 = Color(0x555EEAD4),
        glassBorderColor2 = Color(0x2014B8A6),
        dialogSurface = Color(0xFF0E1A17),
        dialogSurfaceElevated = Color(0xFF162723),
        timelineTrackColor = Color(0xFF132B25)
    )

    // 5. SOLAR FLAME & STREAKS (Streaks & Nike Training Athlete Energy)
    val SolarFlame = LiquidGlassStyle(
        id = "solar_flame",
        displayName = "Solar Flame & Streaks",
        shortName = "Solar Flame",
        subtitle = "Streaks olovli energiya",
        badgeIcon = "🔥",
        archetype = DesignArchetype.SOLAR_FLAME,
        cardShape = RoundedCornerShape(18.dp),
        buttonShape = RoundedCornerShape(14.dp),
        badgeShape = RoundedCornerShape(10.dp),
        borderWidth = 1.2.dp,
        backgroundPattern = BackgroundPattern.SOLAR_PLASMA,
        fontMood = "Athletic Bold",
        progressStyleName = "Dynamic Energy Flame",
        styleTagline = "Olovli plazma & yuqori intizom",
        primaryAccent = Color(0xFFFF5722),
        accentSecondary = Color(0xFFE91E63),
        accentTertiary = Color(0xFFFF9800),
        accentGlow = Color(0x50FF5722),
        bgTop = Color(0xFF180806),
        bgBottom = Color(0xFF070101),
        orb1Color = Color(0x3AFA5252),
        orb2Color = Color(0x30E91E63),
        orb3Color = Color(0x25FF9800),
        glassSurface = Color(0x22FF5722),
        glassSurfaceElevated = Color(0x322D0F0A),
        glassSurfaceSoft = Color(0x16FF5722),
        glassBorderColor1 = Color(0x88FF9800),
        glassBorderColor2 = Color(0x30FF5722),
        dialogSurface = Color(0xFF1B0C08),
        dialogSurfaceElevated = Color(0xFF2B140E),
        timelineTrackColor = Color(0xFF33130C)
    )

    // 6. NOTHING OS MINIMAL (Nothing Phone & Blloc Stealth OLED)
    val OledMinimal = LiquidGlassStyle(
        id = "oled_minimal",
        displayName = "Nothing OS Minimal",
        shortName = "Nothing OS",
        subtitle = "Nothing Phone monoxrom",
        badgeIcon = "⚫",
        archetype = DesignArchetype.OLED_MINIMAL,
        cardShape = RoundedCornerShape(6.dp),
        buttonShape = RoundedCornerShape(4.dp),
        badgeShape = RoundedCornerShape(4.dp),
        borderWidth = 0.8.dp,
        backgroundPattern = BackgroundPattern.DOT_MATRIX,
        fontMood = "Nothing Dot-Matrix",
        progressStyleName = "Dot-Matrix Indicator",
        styleTagline = "100% OLED qora & nozik nuqtali matritsa",
        primaryAccent = Color(0xFFF4F4F8),
        accentSecondary = Color(0xFF9E9EB8),
        accentTertiary = Color(0xFFD4D4D8),
        accentGlow = Color(0x30FFFFFF),
        bgTop = Color(0xFF000000),
        bgBottom = Color(0xFF000000),
        orb1Color = Color(0x12FFFFFF),
        orb2Color = Color(0x0CFFFFFF),
        orb3Color = Color(0x08FFFFFF),
        glassSurface = Color(0x10FFFFFF),
        glassSurfaceElevated = Color(0x18181818),
        glassSurfaceSoft = Color(0x0AFFFFFF),
        glassBorderColor1 = Color(0x50D4D4D8),
        glassBorderColor2 = Color(0x20888888),
        dialogSurface = Color(0xFF0A0A0A),
        dialogSurfaceElevated = Color(0xFF141414),
        timelineTrackColor = Color(0xFF1E1E1E)
    )

    // Backwards compatibility alias
    val Emerald = NeoGlass
    val Aurora = CyberpunkHud
    val Obsidian = OledMinimal
    val Ocean = BentoBrutalist
    val Amber = SolarFlame

    val AllThemes = listOf(NeoGlass, BentoBrutalist, CyberpunkHud, ZenOrganic, SolarFlame, OledMinimal)

    fun getById(id: String): LiquidGlassStyle {
        return when (id.lowercase()) {
            "neo_glass", "emerald" -> NeoGlass
            "bento_brutalist", "ocean" -> BentoBrutalist
            "cyberpunk_hud", "aurora" -> CyberpunkHud
            "zen_organic" -> ZenOrganic
            "solar_flame", "amber" -> SolarFlame
            "oled_minimal", "obsidian" -> OledMinimal
            else -> NeoGlass
        }
    }
}

val LocalLiquidTheme = compositionLocalOf { LiquidGlassStyles.NeoGlass }

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
 * Renders the unique background pattern for each of the 6 design archetypes.
 */
fun Modifier.liquidMeshBackground(theme: LiquidGlassStyle): Modifier = this.drawBehind {
    // 1. Base vertical gradient
    val baseBrush = Brush.verticalGradient(
        colors = listOf(theme.bgTop, theme.bgBottom)
    )
    drawRect(brush = baseBrush)

    when (theme.backgroundPattern) {
        BackgroundPattern.LIQUID_ORBS -> {
            // Smooth Apple-style floating fluid orbs
            val orb1Center = Offset(x = size.width * 0.2f, y = size.height * 0.15f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.orb1Color, Color.Transparent),
                    center = orb1Center,
                    radius = size.width * 0.8f
                ),
                center = orb1Center,
                radius = size.width * 0.8f
            )

            val orb2Center = Offset(x = size.width * 0.85f, y = size.height * 0.45f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.orb2Color, Color.Transparent),
                    center = orb2Center,
                    radius = size.width * 0.75f
                ),
                center = orb2Center,
                radius = size.width * 0.75f
            )

            val orb3Center = Offset(x = size.width * 0.5f, y = size.height * 0.85f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.orb3Color, Color.Transparent),
                    center = orb3Center,
                    radius = size.width * 0.85f
                ),
                center = orb3Center,
                radius = size.width * 0.85f
            )
        }

        BackgroundPattern.BENTO_GRID -> {
            // Linear / Notion technical grid lines
            val gridStep = 42f
            val gridColor = theme.primaryAccent.copy(alpha = 0.045f)
            var x = 0f
            while (x < size.width) {
                drawLine(color = gridColor, start = Offset(x, 0f), end = Offset(x, size.height), strokeWidth = 1f)
                x += gridStep
            }
            var y = 0f
            while (y < size.height) {
                drawLine(color = gridColor, start = Offset(0f, y), end = Offset(size.width, y), strokeWidth = 1f)
                y += gridStep
            }

            // Top technical glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.orb1Color.copy(alpha = 0.25f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.15f),
                    radius = size.width * 0.6f
                ),
                center = Offset(size.width * 0.8f, size.height * 0.15f),
                radius = size.width * 0.6f
            )
        }

        BackgroundPattern.SCANLINES -> {
            // Cyberpunk retro CRT scanlines
            val scanlineStep = 6f
            val scanlineColor = theme.primaryAccent.copy(alpha = 0.035f)
            var y = 0f
            while (y < size.height) {
                drawLine(color = scanlineColor, start = Offset(0f, y), end = Offset(size.width, y), strokeWidth = 1f)
                y += scanlineStep
            }

            // High-voltage corner glows
            val neonTop = Offset(x = size.width * 0.1f, y = size.height * 0.1f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.primaryAccent.copy(alpha = 0.25f), Color.Transparent),
                    center = neonTop,
                    radius = size.width * 0.7f
                ),
                center = neonTop,
                radius = size.width * 0.7f
            )
        }

        BackgroundPattern.ORGANIC_AURA -> {
            // Warm calming sunrise botanical aura
            val auraCenter = Offset(x = size.width * 0.5f, y = size.height * 0.25f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.primaryAccent.copy(alpha = 0.22f), theme.orb2Color, Color.Transparent),
                    center = auraCenter,
                    radius = size.width * 0.95f
                ),
                center = auraCenter,
                radius = size.width * 0.95f
            )
        }

        BackgroundPattern.SOLAR_PLASMA -> {
            // Energetic fiery plasma core
            val coreCenter = Offset(x = size.width * 0.85f, y = size.height * 0.18f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.primaryAccent.copy(alpha = 0.35f), theme.accentSecondary.copy(alpha = 0.18f), Color.Transparent),
                    center = coreCenter,
                    radius = size.width * 0.85f
                ),
                center = coreCenter,
                radius = size.width * 0.85f
            )
            val flameBottom = Offset(x = size.width * 0.15f, y = size.height * 0.8f)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(theme.accentTertiary.copy(alpha = 0.22f), Color.Transparent),
                    center = flameBottom,
                    radius = size.width * 0.7f
                ),
                center = flameBottom,
                radius = size.width * 0.7f
            )
        }

        BackgroundPattern.DOT_MATRIX -> {
            // Nothing OS clean dot-matrix grid
            val dotSpacing = 28f
            val dotRadius = 1.1f
            val dotColor = Color(0x35FFFFFF)
            var x = dotSpacing / 2
            while (x < size.width) {
                var y = dotSpacing / 2
                while (y < size.height) {
                    drawCircle(color = dotColor, radius = dotRadius, center = Offset(x, y))
                    y += dotSpacing
                }
                x += dotSpacing
            }
        }
    }
}

/**
 * Universal Archetype Card Container
 */
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    elevated: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val theme = LocalLiquidTheme.current
    val effectiveShape = shape ?: theme.cardShape
    val surfaceColor = if (elevated) theme.glassSurfaceElevated else theme.glassSurface

    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Box(
        modifier = clickableModifier
            .clip(effectiveShape)
            .background(surfaceColor)
            .border(theme.borderWidth, theme.glassBorder, effectiveShape)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        content()
    }
}

/**
 * Glowing Archetype Pill for Status / Category Badges
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
            .clip(theme.badgeShape)
            .background(bg)
            .border(theme.borderWidth, borderBrush, theme.badgeShape)
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
 * Displays 6 Fundamentally Distinct Habit App Archetypes.
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
            .clip(currentTheme.cardShape)
            .background(currentTheme.glassSurfaceElevated.copy(alpha = 0.70f))
            .border(currentTheme.borderWidth, currentTheme.glassBorderSubtle, currentTheme.cardShape)
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
                Text(text = "🎨", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "HABIT DIZAYN USLUBLARI (6 XIL ARXETIP)",
                    color = currentTheme.textPrimary,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
            Text(
                text = "${currentTheme.badgeIcon} ${currentTheme.shortName}",
                color = currentTheme.primaryAccent,
                fontSize = 11.5.sp,
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
                val isSelected = themeItem.id.equals(selectedThemeId, ignoreCase = true) ||
                        (themeItem.id == "neo_glass" && selectedThemeId == "emerald") ||
                        (themeItem.id == "cyberpunk_hud" && selectedThemeId == "aurora") ||
                        (themeItem.id == "oled_minimal" && selectedThemeId == "obsidian") ||
                        (themeItem.id == "bento_brutalist" && selectedThemeId == "ocean") ||
                        (themeItem.id == "solar_flame" && selectedThemeId == "amber")

                val animatedBorderColor by animateColorAsState(
                    targetValue = if (isSelected) themeItem.primaryAccent else Color.Transparent,
                    animationSpec = tween(300, easing = FastOutSlowInEasing),
                    label = "archetype_border"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(themeItem.buttonShape)
                        .background(
                            if (isSelected) themeItem.glassSurfaceElevated else Color(0x12FFFFFF)
                        )
                        .border(
                            width = if (isSelected) 1.8.dp else 1.dp,
                            brush = if (isSelected) {
                                Brush.linearGradient(
                                    listOf(themeItem.primaryAccent, themeItem.accentTertiary)
                                )
                            } else {
                                Brush.linearGradient(listOf(Color(0x25FFFFFF), Color.Transparent))
                            },
                            shape = themeItem.buttonShape
                        )
                        .clickable { onSelectTheme(themeItem.id) }
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(themeItem.badgeShape)
                            .background(themeItem.primaryAccent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "${themeItem.badgeIcon} ${themeItem.shortName}",
                            color = if (isSelected) themeItem.textPrimary else themeItem.textSecondary,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                        Text(
                            text = themeItem.fontMood,
                            color = if (isSelected) themeItem.primaryAccent else themeItem.textMuted,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}
