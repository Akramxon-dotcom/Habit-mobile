package com.example.service

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import com.example.data.local.HabitPreferences
import com.example.data.model.ScheduleItem
import com.example.data.model.TaskTimeEngine
import com.example.data.remote.GeminiClient
import com.example.data.remote.WallpaperAiDecision
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object AiWallpaperManager {

    fun saveCustomWallpaperFromUri(context: Context, uri: Uri): Boolean {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return false
            val file = File(context.filesDir, "user_base_wallpaper.png")
            file.outputStream().use { out ->
                inputStream.copyTo(out)
            }
            HabitPreferences(context).customWallpaperPath = file.absolutePath
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun hasCustomWallpaper(context: Context): Boolean {
        val path = HabitPreferences(context).customWallpaperPath
        return path.isNotBlank() && File(path).exists()
    }

    fun clearCustomWallpaper(context: Context) {
        val prefs = HabitPreferences(context)
        val file = File(prefs.customWallpaperPath)
        if (file.exists()) {
            file.delete()
        }
        prefs.customWallpaperPath = ""
    }

    suspend fun updateWallpaperForCurrentTask(context: Context): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prefs = HabitPreferences(context)
            val schedule = prefs.getSchedule()
            val effective = TaskTimeEngine.findEffectiveHeroTask(schedule)
            val next = TaskTimeEngine.findNextUpcomingScheduleItem(schedule)

            val displayItem = when {
                effective != null -> effective
                next != null -> next
                else -> null
            }

            val title = displayItem?.title ?: "Kun tartibi yakunlandi"
            val timeStr = if (displayItem != null) "${displayItem.start} – ${displayItem.end}" else TaskTimeEngine.getFormattedCurrentTime()
            val cat = displayItem?.category ?: "dam_olish"
            val icon = displayItem?.getCategoryIcon() ?: "🌙"

            val wallpaperManager = WallpaperManager.getInstance(context)

            // Step 1: Obtain base bitmap. Always preserve the user's base image/photo/screenshot!
            val customPath = prefs.customWallpaperPath
            val customFile = if (customPath.isNotBlank()) File(customPath) else null

            val baseBitmap: Bitmap = when {
                customFile != null && customFile.exists() -> {
                    val decoded = BitmapFactory.decodeFile(customFile.absolutePath)
                    decoded?.copy(Bitmap.Config.ARGB_8888, true) ?: createDefaultWallpaperBitmap(1080, 2400)
                }
                else -> {
                    val currentDrawable = try { wallpaperManager.drawable } catch (e: Exception) { null }
                    if (currentDrawable is BitmapDrawable && currentDrawable.bitmap != null) {
                        currentDrawable.bitmap.copy(Bitmap.Config.ARGB_8888, true)
                    } else {
                        createDefaultWallpaperBitmap(1080, 2400)
                    }
                }
            }

            // Step 2: Ask Gemini AI to detect the empty grid slots and avoid visual subjects (like flowers, icons)
            val aiDecision = GeminiClient.analyzeWallpaperForPlacement(
                bitmap = baseBitmap,
                taskTitle = title,
                taskTime = timeStr,
                taskCategory = cat
            ).getOrElse {
                WallpaperAiDecision(
                    placement = "EMPTY_SLOT",
                    cardStyle = "LIQUID_GLASS",
                    accentColor = "#D9A954",
                    headline = title,
                    subtext = "$timeStr · Reja",
                    quote = "Har daqiqa — yangi imkoniyat",
                    cardWidthPercent = 88,
                    cornerRadius = 24,
                    normalizedX = 0.06f,
                    normalizedY = 0.36f,
                    normalizedWidth = 0.88f,
                    normalizedHeight = 0.12f,
                    explanation = "Markaziy bo'sh katakka Liquid Glass formatida joylashtirildi"
                )
            }

            prefs.lastAiWallpaperExplanation = aiDecision.explanation

            // Step 3: Draw the Liquid Glass task card precisely into the empty detected slot
            drawTaskOverlay(
                bitmap = baseBitmap,
                title = title,
                timeStr = timeStr,
                categoryIcon = icon,
                decision = aiDecision
            )

            // Step 4: Apply to selected screen target (Home Screen, Lock Screen, or Both)
            val target = prefs.wallpaperTarget
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val flags = when (target) {
                    "HOME" -> WallpaperManager.FLAG_SYSTEM
                    "LOCK" -> WallpaperManager.FLAG_LOCK
                    else -> WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                }
                wallpaperManager.setBitmap(baseBitmap, null, true, flags)
            } else {
                wallpaperManager.setBitmap(baseBitmap)
            }

            val targetDesc = when (target) {
                "HOME" -> "Bosh ekranga (Home)"
                "LOCK" -> "Qulf ekraniga (Lock)"
                else -> "Bosh va Qulf ekraniga (Home & Lock)"
            }
            Result.success("$targetDesc o'rnatildi! ${aiDecision.explanation}")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun drawTaskOverlay(
        bitmap: Bitmap,
        title: String,
        timeStr: String,
        categoryIcon: String,
        decision: WallpaperAiDecision
    ) {
        val canvas = Canvas(bitmap)
        val w = bitmap.width.toFloat()
        val h = bitmap.height.toFloat()

        // Dynamic coordinate calculation based on Gemini empty slot detection
        val cardWidth = (w * decision.normalizedWidth).coerceIn(240f, w - 32f)
        val cardHeight = (h * decision.normalizedHeight).coerceIn(160f, 320f)

        val cardX = (decision.normalizedX * w).coerceIn(16f, (w - cardWidth - 16f).coerceAtLeast(16f))
        val cardY = (decision.normalizedY * h).coerceIn(40f, (h - cardHeight - 40f).coerceAtLeast(40f))

        val cardRect = RectF(cardX, cardY, cardX + cardWidth, cardY + cardHeight)
        val radius = (decision.cornerRadius * (w / 1080f)).coerceIn(16f, 36f)

        // 1. Ambient Soft Glow Blob behind glass card
        val accentColorInt = try {
            Color.parseColor(decision.accentColor)
        } catch (e: Exception) {
            Color.parseColor("#D9A954")
        }

        val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(40, Color.red(accentColorInt), Color.green(accentColorInt), Color.blue(accentColorInt))
            style = Paint.Style.FILL
        }
        val glowRect = RectF(cardRect.left - 10f, cardRect.top - 10f, cardRect.right + 10f, cardRect.bottom + 12f)
        canvas.drawRoundRect(glowRect, radius + 8f, radius + 8f, glowPaint)

        // 2. Liquid Glass Translucent Body (Preserves underlying artwork)
        val glassBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                cardRect.left, cardRect.top,
                cardRect.right, cardRect.bottom,
                Color.parseColor("#E00C1322"), // High quality deep glass
                Color.parseColor("#C4141E34"),
                Shader.TileMode.CLAMP
            )
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(cardRect, radius, radius, glassBgPaint)

        // 3. Specular Liquid Glass Edge Rim
        val rimPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                cardRect.left, cardRect.top,
                cardRect.right, cardRect.bottom,
                Color.parseColor("#90FFFFFF"), // Specular highlight
                Color.parseColor("#15FFFFFF"),
                Shader.TileMode.CLAMP
            )
            style = Paint.Style.STROKE
            strokeWidth = (2.5f * (w / 1080f)).coerceIn(1.5f, 4f)
        }
        canvas.drawRoundRect(cardRect, radius, radius, rimPaint)

        // 4. Status Badge Pill
        val pillHeight = (32f * (w / 1080f)).coerceIn(24f, 40f)
        val pillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(45, Color.red(accentColorInt), Color.green(accentColorInt), Color.blue(accentColorInt))
            style = Paint.Style.FILL
        }
        val pillStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(140, Color.red(accentColorInt), Color.green(accentColorInt), Color.blue(accentColorInt))
            style = Paint.Style.STROKE
            strokeWidth = 1.2f
        }

        val badgeText = "$categoryIcon FAOL REJA"
        val badgeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = accentColorInt
            textSize = (20f * (w / 1080f)).coerceIn(15f, 26f)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val pillWidth = badgeTextPaint.measureText(badgeText) + 28f
        val pillLeft = cardRect.left + 24f
        val pillTop = cardRect.top + 20f
        val pillRect = RectF(pillLeft, pillTop, pillLeft + pillWidth, pillTop + pillHeight)
        canvas.drawRoundRect(pillRect, pillHeight / 2f, pillHeight / 2f, pillPaint)
        canvas.drawRoundRect(pillRect, pillHeight / 2f, pillHeight / 2f, pillStrokePaint)
        canvas.drawText(badgeText, pillLeft + 14f, pillTop + pillHeight * 0.72f, badgeTextPaint)

        // 5. Time text
        val timePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#CBD5E1")
            textSize = (20f * (w / 1080f)).coerceIn(14f, 26f)
            textAlign = Paint.Align.RIGHT
        }
        canvas.drawText(timeStr, cardRect.right - 24f, pillTop + pillHeight * 0.72f, timePaint)

        // 6. Task Title
        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = (32f * (w / 1080f)).coerceIn(22f, 42f)
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }
        val maxChars = if (cardWidth < 500f) 18 else 32
        val displayTitle = if (title.length > maxChars) title.take(maxChars - 2) + "..." else title
        canvas.drawText(displayTitle, cardRect.left + 24f, cardRect.top + cardHeight * 0.58f, titlePaint)

        // 7. Micro Quote / Note
        val quoteText = if (decision.quote.isNotBlank()) "✦ ${decision.quote}" else "✦ Vaqtni qadrla, natija o'zi keladi"
        val quotePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#94A3B8")
            textSize = (18f * (w / 1080f)).coerceIn(13f, 22f)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }
        val maxQuoteChars = if (cardWidth < 500f) 24 else 45
        val displayQuote = if (quoteText.length > maxQuoteChars) quoteText.take(maxQuoteChars - 2) + "..." else quoteText
        canvas.drawText(displayQuote, cardRect.left + 24f, cardRect.bottom - 22f, quotePaint)
    }

    private fun createDefaultWallpaperBitmap(width: Int, height: Int): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)

        // High quality dark slate aesthetic
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                0f, 0f, width.toFloat(), height.toFloat(),
                Color.parseColor("#090D16"),
                Color.parseColor("#131B2E"),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // Elegant subtle radial glow
        val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#15D9A954")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(width * 0.82f, height * 0.18f, width * 0.55f, glowPaint)

        return bmp
    }
}

