package com.example.data.model

import java.time.LocalDate
import java.time.LocalTime
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

data class PrayerTime(
    val id: String,
    val name: String,
    val time: String, // HH:mm
    val icon: String = "🕌"
)

data class NextPrayerInfo(
    val currentOrNext: PrayerTime,
    val isOngoing: Boolean,
    val minutesLeft: Long,
    val remainingFormatted: String
)

object PrayerTimeEngine {
    // Marg'ilon default prayer times
    fun getMargilonPrayers(): List<PrayerTime> {
        return listOf(
            PrayerTime("fajr", "Bomdod", "04:45", "🌅"),
            PrayerTime("sunrise", "Quyosh", "06:10", "☀️"),
            PrayerTime("dhuhr", "Peshin", "12:35", "☀️"),
            PrayerTime("asr", "Asr", "16:45", "🌤️"),
            PrayerTime("maghrib", "Shom", "18:50", "🌇"),
            PrayerTime("isha", "Xufton", "20:20", "🌙")
        )
    }

    // Hijriy sana (Islomiy taqvim, e.g. 20-Safar, 1448-yil)
    fun getHijriDateFormatted(): String {
        return try {
            val hijrahDate = HijrahDate.now()
            val monthNames = listOf(
                "Muharram", "Safar", "Rabi'ul-avval", "Rabi'us-soniy",
                "Jumodul-avval", "Jumodus-soniy", "Rajab", "Sha'bon",
                "Ramazon", "Shavvol", "Zulqa'da", "Zulhijja"
            )
            val monthIndex = hijrahDate.get(java.time.temporal.ChronoField.MONTH_OF_YEAR) - 1
            val monthName = if (monthIndex in monthNames.indices) monthNames[monthIndex] else "Safar"
            val day = hijrahDate.get(java.time.temporal.ChronoField.DAY_OF_MONTH)
            val year = hijrahDate.get(java.time.temporal.ChronoField.YEAR)
            "$day-$monthName, $year-yil"
        } catch (e: Exception) {
            "20-Safar, 1448-yil"
        }
    }

    fun getNextPrayer(now: LocalTime = LocalTime.now()): NextPrayerInfo {
        val prayers = getMargilonPrayers().filter { it.id != "sunrise" } // 5 vaqt namoz
        val nowMin = now.hour * 60 + now.minute

        for (p in prayers) {
            val parts = p.time.split(":")
            if (parts.size == 2) {
                val pMin = parts[0].toIntOrNull()?.times(60)?.plus(parts[1].toIntOrNull() ?: 0) ?: 0
                if (pMin > nowMin) {
                    val diff = (pMin - nowMin).toLong()
                    val h = diff / 60
                    val m = diff % 60
                    val text = if (h > 0) "$h soat $m daq" else "$m daqiqa"
                    return NextPrayerInfo(p, false, diff, text)
                }
            }
        }

        // Agar barchasi o'tgan bo'lsa, ertangi bomdod
        val fajr = prayers.first()
        val parts = fajr.time.split(":")
        val pMin = (parts.getOrNull(0)?.toIntOrNull() ?: 4) * 60 + (parts.getOrNull(1)?.toIntOrNull() ?: 45)
        val diff = ((24 * 60 - nowMin) + pMin).toLong()
        val h = diff / 60
        val m = diff % 60
        val text = if (h > 0) "$h soat $m daq" else "$m daqiqa"
        return NextPrayerInfo(fajr, false, diff, text)
    }

    // Marg'ilon koordinatasi: 40.4714° N, 71.7247° E
    // Ka'ba (Makka): 21.4225° N, 39.8262° E
    // Qibla burchagi: ~241° (Janubi-G'arb)
    const val QIBLA_BEARING_DEGREES = 241f
    const val DISTANCE_TO_MAKKAH_KM = 3650
}
