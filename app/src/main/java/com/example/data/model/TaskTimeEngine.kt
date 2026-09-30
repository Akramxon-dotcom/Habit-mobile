package com.example.data.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class TaskStatus {
    ACTIVE,     // Hozir davom etayotgan vazifa
    UPCOMING,   // Hali boshlanmagan, yaqinlashayotgan vazifa
    COMPLETED,  // Bugun yakunlangan vazifa
    FREE_TIME   // Ayni paytda rejalashtirilgan vazifa yo'q (bo'sh vaqt)
}

data class RealtimeTaskInfo(
    val status: TaskStatus,
    val statusLabel: String,
    val progress: Float, // 0.0f to 1.0f
    val progressPercent: Int, // 0 to 100
    val timeRemainingText: String,
    val isBlockingActiveNow: Boolean
)

object TaskTimeEngine {

    fun parseMinuteOfDay(timeStr: String): Int? {
        if (timeStr.isBlank()) return null
        val parts = timeStr.trim().split(":")
        if (parts.size != 2) return null
        val h = parts[0].toIntOrNull() ?: return null
        val m = parts[1].toIntOrNull() ?: return null
        if (h !in 0..23 || m !in 0..59) return null
        return h * 60 + m
    }

    fun getCurrentMinuteOfDay(): Int {
        val cal = Calendar.getInstance()
        return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
    }

    fun isTaskActiveNow(startStr: String, endStr: String): Boolean {
        val startMin = parseMinuteOfDay(startStr) ?: return false
        val endMin = parseMinuteOfDay(endStr) ?: return false
        val nowMin = getCurrentMinuteOfDay()

        return if (endMin > startMin) {
            nowMin in startMin until endMin
        } else {
            // Overnight task, e.g. 22:00 to 04:05
            nowMin >= startMin || nowMin < endMin
        }
    }

    fun calculateTaskInfo(habit: HabitState): RealtimeTaskInfo {
        if (habit.title.isBlank() || habit.start.isBlank() || habit.end.isBlank()) {
            return RealtimeTaskInfo(
                status = TaskStatus.FREE_TIME,
                statusLabel = "Bo'sh vaqt",
                progress = 0f,
                progressPercent = 0,
                timeRemainingText = "Rejalashtirilgan vazifa yo'q",
                isBlockingActiveNow = false
            )
        }

        val startMin = parseMinuteOfDay(habit.start)
        val endMin = parseMinuteOfDay(habit.end)

        if (startMin == null || endMin == null) {
            return RealtimeTaskInfo(
                status = TaskStatus.FREE_TIME,
                statusLabel = "Noma'lum vaqt",
                progress = 0f,
                progressPercent = 0,
                timeRemainingText = "--",
                isBlockingActiveNow = false
            )
        }

        val nowMin = getCurrentMinuteOfDay()

        val isOvernight = endMin < startMin
        val isActive = if (!isOvernight) {
            nowMin in startMin until endMin
        } else {
            nowMin >= startMin || nowMin < endMin
        }

        if (isActive) {
            val totalDuration = if (!isOvernight) {
                (endMin - startMin).coerceAtLeast(1)
            } else {
                ((1440 - startMin) + endMin).coerceAtLeast(1)
            }

            val elapsed = if (!isOvernight) {
                (nowMin - startMin).coerceAtLeast(0)
            } else {
                if (nowMin >= startMin) {
                    nowMin - startMin
                } else {
                    (1440 - startMin) + nowMin
                }
            }

            val remainingMin = (totalDuration - elapsed).coerceAtLeast(0)
            val progress = (elapsed.toFloat() / totalDuration.toFloat()).coerceIn(0f, 1f)
            val progressPercent = (progress * 100).toInt()

            val remainingText = formatMinutes(remainingMin) + " qoldi"

            return RealtimeTaskInfo(
                status = TaskStatus.ACTIVE,
                statusLabel = "HOZIR",
                progress = progress,
                progressPercent = progressPercent,
                timeRemainingText = remainingText,
                isBlockingActiveNow = habit.blocking
            )
        }

        val isUpcoming = if (!isOvernight) {
            nowMin < startMin
        } else {
            nowMin in endMin until startMin
        }

        if (isUpcoming) {
            val waitMin = if (startMin >= nowMin) {
                startMin - nowMin
            } else {
                (1440 - nowMin) + startMin
            }
            return RealtimeTaskInfo(
                status = TaskStatus.UPCOMING,
                statusLabel = "KEYINGI",
                progress = 0f,
                progressPercent = 0,
                timeRemainingText = "${formatMinutes(waitMin)}dan so'ng boshlanadi",
                isBlockingActiveNow = false
            )
        } else {
            return RealtimeTaskInfo(
                status = TaskStatus.COMPLETED,
                statusLabel = "YAKUNLANDI",
                progress = 1f,
                progressPercent = 100,
                timeRemainingText = "${habit.end} da yakunlangan",
                isBlockingActiveNow = false
            )
        }
    }

    private fun formatMinutes(minutes: Int): String {
        val h = minutes / 60
        val m = minutes % 60
        return when {
            h > 0 && m > 0 -> "$h soat $m daqiqa"
            h > 0 -> "$h soat"
            else -> "$m daqiqa"
        }
    }

    fun calculateEarlyCompletionMinutes(task: ScheduleItem, currentTime: String = getFormattedCurrentTime()): Int {
        val nowMin = parseMinuteOfDay(currentTime) ?: return 0
        val endMin = parseMinuteOfDay(task.end) ?: return 0
        return if (endMin > nowMin) (endMin - nowMin).coerceAtLeast(0) else 0
    }

    fun findActiveScheduleItem(items: List<ScheduleItem>): ScheduleItem? {
        val nowMin = getCurrentMinuteOfDay()
        // First look for currently running task that is NOT marked done yet
        val activeNotDone = items.firstOrNull { item ->
            if (item.isDone) return@firstOrNull false
            val startMin = parseMinuteOfDay(item.start) ?: return@firstOrNull false
            val endMin = parseMinuteOfDay(item.end) ?: return@firstOrNull false
            if (endMin > startMin) {
                nowMin in startMin until endMin
            } else {
                nowMin >= startMin || nowMin < endMin
            }
        }
        if (activeNotDone != null) return activeNotDone

        // If no active uncompleted task, check if there's any active task even if done
        return items.firstOrNull { item ->
            val startMin = parseMinuteOfDay(item.start) ?: return@firstOrNull false
            val endMin = parseMinuteOfDay(item.end) ?: return@firstOrNull false
            if (endMin > startMin) {
                nowMin in startMin until endMin
            } else {
                nowMin >= startMin || nowMin < endMin
            }
        }
    }

    /**
     * Determines which task MUST be displayed in the primary Hero panel:
     * 1. Currently active task (if not yet marked done).
     * 2. If the current task is completed or its time expired, immediately show the NEXT upcoming uncompleted task.
     * 3. Never freezes on an expired "Yakunlandi" task.
     */
    fun findEffectiveHeroTask(items: List<ScheduleItem>): ScheduleItem? {
        val nowMin = getCurrentMinuteOfDay()

        // 1. Check for active uncompleted task
        val activeNotDone = items.firstOrNull { item ->
            if (item.isDone) return@firstOrNull false
            val startMin = parseMinuteOfDay(item.start) ?: return@firstOrNull false
            val endMin = parseMinuteOfDay(item.end) ?: return@firstOrNull false
            if (endMin > startMin) {
                nowMin in startMin until endMin
            } else {
                nowMin >= startMin || nowMin < endMin
            }
        }
        if (activeNotDone != null) return activeNotDone

        // 2. Next uncompleted task today that starts after current time
        val nextUpcoming = items.filter { item ->
            if (item.isDone) return@filter false
            val startMin = parseMinuteOfDay(item.start) ?: return@filter false
            startMin >= nowMin
        }.minByOrNull { parseMinuteOfDay(it.start) ?: Int.MAX_VALUE }

        if (nextUpcoming != null) return nextUpcoming

        // 3. Any uncompleted task today (even if scheduled earlier, as pending)
        val pendingUncompleted = items.filter { !it.isDone }
            .minByOrNull { parseMinuteOfDay(it.start) ?: Int.MAX_VALUE }

        return pendingUncompleted
    }

    /**
     * Calculates how many minutes ahead of time the user completed the task.
     * If user completes before task endTime, these early minutes go to the Time Bank.
     */
    fun calculateEarlyCompletionMinutes(task: ScheduleItem): Int {
        val endMin = parseMinuteOfDay(task.end) ?: return 0
        val nowMin = getCurrentMinuteOfDay()
        return (endMin - nowMin).coerceAtLeast(0)
    }

    fun findNextUpcomingScheduleItem(items: List<ScheduleItem>): ScheduleItem? {
        val nowMin = getCurrentMinuteOfDay()
        val upcoming = items.filter { item ->
            if (item.isDone) return@filter false
            val startMin = parseMinuteOfDay(item.start) ?: return@filter false
            startMin > nowMin
        }.minByOrNull { parseMinuteOfDay(it.start) ?: Int.MAX_VALUE }

        return upcoming ?: items.filter { !it.isDone }.minByOrNull { parseMinuteOfDay(it.start) ?: Int.MAX_VALUE }
    }

    fun getFormattedCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }

    fun getFormattedCurrentDate(): String {
        val cal = Calendar.getInstance()
        val dayOfWeek = when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Dushanba"
            Calendar.TUESDAY -> "Seshanba"
            Calendar.WEDNESDAY -> "Chorshanba"
            Calendar.THURSDAY -> "Payshanba"
            Calendar.FRIDAY -> "Juma"
            Calendar.SATURDAY -> "Shanba"
            Calendar.SUNDAY -> "Yakshanba"
            else -> ""
        }
        val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        val monthName = when (cal.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "yanvar"
            Calendar.FEBRUARY -> "fevral"
            Calendar.MARCH -> "mart"
            Calendar.APRIL -> "aprel"
            Calendar.MAY -> "may"
            Calendar.JUNE -> "iyun"
            Calendar.JULY -> "iyul"
            Calendar.AUGUST -> "avgust"
            Calendar.SEPTEMBER -> "sentabr"
            Calendar.OCTOBER -> "oktabr"
            Calendar.NOVEMBER -> "noyabr"
            Calendar.DECEMBER -> "dekabr"
            else -> ""
        }
        return "$dayOfWeek, $dayOfMonth-$monthName"
    }

    fun getIsoDateForOffset(offsetDays: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, offsetDays)
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.time)
    }

    fun getDisplayDateForOffset(offsetDays: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, offsetDays)
        val dayOfWeek = when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Dushanba"
            Calendar.TUESDAY -> "Seshanba"
            Calendar.WEDNESDAY -> "Chorshanba"
            Calendar.THURSDAY -> "Payshanba"
            Calendar.FRIDAY -> "Juma"
            Calendar.SATURDAY -> "Shanba"
            Calendar.SUNDAY -> "Yakshanba"
            else -> ""
        }
        val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
        val monthName = when (cal.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "yanvar"
            Calendar.FEBRUARY -> "fevral"
            Calendar.MARCH -> "mart"
            Calendar.APRIL -> "aprel"
            Calendar.MAY -> "may"
            Calendar.JUNE -> "iyun"
            Calendar.JULY -> "iyul"
            Calendar.AUGUST -> "avgust"
            Calendar.SEPTEMBER -> "sentabr"
            Calendar.OCTOBER -> "oktabr"
            Calendar.NOVEMBER -> "noyabr"
            Calendar.DECEMBER -> "dekabr"
            else -> ""
        }
        return when (offsetDays) {
            0 -> "Bugun ($dayOfWeek, $dayOfMonth-$monthName)"
            -1 -> "Kecha ($dayOfWeek, $dayOfMonth-$monthName)"
            else -> "$dayOfWeek, $dayOfMonth-$monthName"
        }
    }

    fun getGreeting(segment: String): String {
        return when (segment) {
            "Tong" -> "Xayrli tong"
            "Kunduz" -> "Xayrli kun"
            "Kech" -> "Xayrli kech"
            "Kechqurun" -> "Xayrli oqshom"
            "Tun" -> "Tinch tun"
            else -> "Salom"
        }
    }

    // Arc track position calculation (04:00 to 22:00 -> 240 to 1320 min)
    fun getDayArcPercent(): Float {
        val nowMin = getCurrentMinuteOfDay()
        val dayStart = 240 // 04:00
        val dayEnd = 1320 // 22:00
        if (nowMin <= dayStart) return 0f
        if (nowMin >= dayEnd) return 1f
        return (nowMin - dayStart).toFloat() / (dayEnd - dayStart).toFloat()
    }

    // Default schedules matching the user's exact schedule
    val LESSON_PLAN = listOf(
        ScheduleItem(title = "Bomdod namozi + dua", category = "prayer", start = "05:00", end = "05:35", note = "Kun boshidagi eng muhim ustuvorlik (05:00)", blocking = true),
        ScheduleItem(title = "📚 4000 Words (1-kitob) & Oxford Lug'at", category = "english", start = "05:35", end = "06:05", note = "Kunlik 8 ta yangi so'z: daftarga tarjima va misoli bilan yozish, ovoz chiqarib 3 marta o'qish + takrorlash", blocking = true),
        ScheduleItem(title = "🏃‍♂️ Sport (Turnik, Otjimaniya, Plank) & BBC", category = "sport", start = "06:05", end = "06:40", note = "5 daq qizdirish → Turnik (4x8), Otjimaniya (4x20), Plank (3x60s). Quloqda: BBC 6 Minute English", blocking = false),
        ScheduleItem(title = "Dush, tayyorgarlik", category = "other", start = "06:40", end = "07:00", note = "Dush va tayyorgarlik", blocking = false),
        ScheduleItem(title = "Nonushta", category = "food", start = "07:00", end = "07:25", note = "Quvvat to'plash", blocking = false),
        ScheduleItem(title = "Essential Grammar in Use (Murphy)", category = "english", start = "07:25", end = "07:45", note = "Navbatdagi 1 Unit qoidani o'qib, mashqlarni bajarish", blocking = true),
        ScheduleItem(title = "Maktabga yo'l", category = "other", start = "07:45", end = "08:00", note = "BBC podkastni tinglashda davom et", blocking = false),
        ScheduleItem(title = "Maktab darslari", category = "school", start = "08:00", end = "12:25", note = "Maktab darslari jarayoni", blocking = true),
        ScheduleItem(title = "Yo'l → Tahorat → Peshin namozi → dua", category = "prayer", start = "12:25", end = "13:00", note = "Peshin ibodati va duo (12:25)", blocking = true),
        ScheduleItem(title = "Issiq Tushlik", category = "food", start = "13:00", end = "13:30", note = "Tushlik vaqti", blocking = false),
        ScheduleItem(title = "Uyga vazifalar", category = "homework", start = "13:30", end = "14:45", note = "Bugungi barcha dars va markaz uyga vazifalarini bajarish", blocking = true),
        ScheduleItem(title = "🇬🇧 Ingliz tili darsi", category = "english", start = "14:45", end = "16:15", note = "O'qituvchi bilan, mavzu darsda beriladi", blocking = true),
        ScheduleItem(title = "Tahorat → Asr namozi → dua", category = "prayer", start = "16:15", end = "16:45", note = "Asr ibodati va duo (16:15)", blocking = true),
        ScheduleItem(title = "Erkin tanaffus", category = "other", start = "16:45", end = "17:20", note = "Miyani dam oldirish (telefon emas)", blocking = false),
        ScheduleItem(title = "Bugungi dars uy vazifasi", category = "english", start = "17:20", end = "17:59", note = "Bugungi dars uy vazifasini yoz, so'ng ovoz chiqarib o'qib chiq", blocking = true),
        ScheduleItem(title = "Tahorat → Shom namozi → dua", category = "prayer", start = "17:59", end = "18:30", note = "Shom ibodati va duo (17:59)", blocking = true),
        ScheduleItem(title = "Kechki ovqat & IBRAT Academy", category = "food", start = "18:30", end = "19:13", note = "Oila bilan birga kechki ovqat va video dars", blocking = false),
        ScheduleItem(title = "Tahorat → Xufton namozi → dua", category = "prayer", start = "19:13", end = "19:45", note = "Xufton ibodati va duo (19:13)", blocking = true),
        ScheduleItem(title = "Oxford Bookworms (Elephant Man / Sherlock)", category = "english", start = "19:45", end = "20:30", note = "Kitob mutolaasi va lug'atga yozish", blocking = true),
        ScheduleItem(title = "⚡ Coursera (Aileaders.uz sertifikatlari)", category = "rtm", start = "20:30", end = "21:15", note = "Kunlik sertifikatlar sprinti", blocking = true),
        ScheduleItem(title = "📖 O'zbek tilida kitob o'qish", category = "other", start = "21:15", end = "21:45", note = "Foydali kitobdan 15-20 bet o'qish", blocking = false),
        ScheduleItem(title = "Yotishga tayyorgarlik", category = "night", start = "21:45", end = "22:15", note = "Telefonni boshqa xonaga qo'yish, uyquga hozirlik", blocking = false),
        ScheduleItem(title = "Uxlash", category = "night", start = "22:15", end = "05:00", note = "Tetik uyqu (22:15 — 05:00)", blocking = false)
    )

    val FREE_PLAN = listOf(
        ScheduleItem(title = "Bomdod namozi + dua", category = "prayer", start = "05:00", end = "05:35", note = "Kun boshidagi eng muhim ustuvorlik (05:00)", blocking = true),
        ScheduleItem(title = "📚 4000 Words (1-kitob) & Oxford Lug'at", category = "english", start = "05:35", end = "06:05", note = "Kunlik 8 ta yangi so'z: daftarga tarjima va misoli bilan yozish", blocking = true),
        ScheduleItem(title = "🏃‍♂️ Sport (Kardio, Yugurish, Press) & BBC", category = "sport", start = "06:05", end = "06:40", note = "2-3 km Yugurish / Arqon, Prisidaniya, Press. Quloqda: BBC", blocking = false),
        ScheduleItem(title = "Dush, tayyorgarlik", category = "other", start = "06:40", end = "07:05", note = "Dush va tayyorgarlik", blocking = false),
        ScheduleItem(title = "Nonushta", category = "food", start = "07:05", end = "07:35", note = "Quvvat to'plash", blocking = false),
        ScheduleItem(title = "Essential Grammar (Takrorlash)", category = "english", start = "07:35", end = "08:00", note = "Grammatikani mustahkamlash", blocking = true),
        ScheduleItem(title = "Maktab / Loyiha", category = "school", start = "08:00", end = "12:25", note = "Maktab darslari yoki amaliy loyihalar", blocking = true),
        ScheduleItem(title = "Yo'l → Tahorat → Peshin namozi → dua", category = "prayer", start = "12:25", end = "13:00", note = "Peshin ibodati va duo (12:25)", blocking = true),
        ScheduleItem(title = "Issiq Tushlik", category = "food", start = "13:00", end = "13:30", note = "Tushlik vaqti", blocking = false),
        ScheduleItem(title = "Uyga vazifalar", category = "homework", start = "13:30", end = "14:45", note = "Bugungi barcha dars va markaz uyga vazifalarini bajarish", blocking = true),
        ScheduleItem(title = "Cake ilovasi (Shadowing)", category = "english", start = "14:45", end = "15:30", note = "Shadowing videosini ishla", blocking = true),
        ScheduleItem(title = "Listening (BBC / VOA)", category = "english", start = "15:30", end = "16:15", note = "BBC 6 Minute English / VOA — transkript bilan tingla", blocking = true),
        ScheduleItem(title = "Tahorat → Asr namozi → dua", category = "prayer", start = "16:15", end = "16:45", note = "Asr ibodati va duo (16:15)", blocking = true),
        ScheduleItem(title = "Erkin tanaffus", category = "other", start = "16:45", end = "17:20", note = "Dam olish (telefon emas)", blocking = false),
        ScheduleItem(title = "🎬 Extra English / Serial tahlili", category = "english", start = "17:20", end = "17:59", note = "Ingliz subtitr bilan tomosha, 3 ta yangi ibora", blocking = true),
        ScheduleItem(title = "Tahorat → Shom namozi → dua", category = "prayer", start = "17:59", end = "18:30", note = "Shom ibodati va duo (17:59)", blocking = true),
        ScheduleItem(title = "Kechki ovqat & IBRAT Academy", category = "food", start = "18:30", end = "19:13", note = "Oila davrasida ovqatlanish va dars", blocking = false),
        ScheduleItem(title = "Tahorat → Xufton namozi → dua", category = "prayer", start = "19:13", end = "19:45", note = "Xufton ibodati va duo (19:13)", blocking = true),
        ScheduleItem(title = "Writing (Yozish) + Grammarly", category = "english", start = "19:45", end = "20:30", note = "Bugun haqida 5 ta jumla yozish va xatolarni tuzatish", blocking = true),
        ScheduleItem(title = "⚡ Coursera (Aileaders.uz sertifikatlari)", category = "rtm", start = "20:30", end = "21:15", note = "Sertifikatlar bo'yicha amaliy ish", blocking = true),
        ScheduleItem(title = "📖 O'zbek tilida kitob o'qish", category = "other", start = "21:15", end = "21:45", note = "Kitob mutolaasi", blocking = false),
        ScheduleItem(title = "Yotishga tayyorgarlik", category = "night", start = "21:45", end = "22:15", note = "Telefonni boshqa xonaga qo'yish", blocking = false),
        ScheduleItem(title = "Uxlash", category = "night", start = "22:15", end = "05:00", note = "Tetik uyqu (22:15 — 05:00)", blocking = false)
    )

enum class DayType(val label: String) {
    LESSON_DAY("DARS KUNI"),
    NO_LESSON_DAY("DARS YO'Q KUN"),
    SUNDAY_LIGHT_DAY("YAKSHANBA — YENGIL KUN")
}

    val SUNDAY_PLAN = listOf(
        ScheduleItem(title = "Bomdod namozi + dua", category = "prayer", start = "05:00", end = "05:35", note = "Bomdod namozi va duo (05:00)", blocking = true),
        ScheduleItem(title = "📚 4000 Words: Yengil takrorlash & Sandiq", category = "english", start = "05:35", end = "06:05", note = "Haftalik so'zlarni qayta tekshirish", blocking = true),
        ScheduleItem(title = "🏃‍♂️ Sport (Tiklanish & Cho'zilish)", category = "sport", start = "06:05", end = "06:40", note = "Cho'zilish (stretching), Turnikda osilish", blocking = false),
        ScheduleItem(title = "Dush, nonushta", category = "food", start = "06:40", end = "07:15", note = "Dush va to'yimli nonushta", blocking = false),
        ScheduleItem(title = "IBRAT Academy", category = "english", start = "07:15", end = "08:15", note = "Navbatdagi 2 ta dars + testlar", blocking = true),
        ScheduleItem(title = "Oxford Bookworms", category = "english", start = "08:15", end = "09:15", note = "Navbatdagi 2 bob", blocking = true),
        ScheduleItem(title = "Cake + IBRAT suhbat mashqi", category = "english", start = "09:15", end = "10:15", note = "Shadowing video + suhbat mashqi", blocking = true),
        ScheduleItem(title = "Erkin vaqt (Loyiha / Bot / Sayt)", category = "other", start = "10:15", end = "12:25", note = "Xohlagan loyihang ustida ishlash", blocking = false),
        ScheduleItem(title = "Tahorat → Peshin namozi → dua", category = "prayer", start = "12:25", end = "13:00", note = "Peshin ibodati va duo (12:25)", blocking = true),
        ScheduleItem(title = "Tushlik vaqti", category = "food", start = "13:00", end = "13:45", note = "Tushlik vaqti", blocking = false),
        ScheduleItem(title = "Qaylula", category = "night", start = "13:45", end = "14:30", note = "Kunduzgi dam olish", blocking = false),
        ScheduleItem(title = "BBC 6 Minute English", category = "english", start = "14:30", end = "15:30", note = "2 ta epizod ketma-ket", blocking = true),
        ScheduleItem(title = "🎬 Extra English / Film tomosha", category = "english", start = "15:30", end = "16:15", note = "Ingliz subtitr bilan tomosha qilish", blocking = true),
        ScheduleItem(title = "Tahorat → Asr namozi → dua", category = "prayer", start = "16:15", end = "16:45", note = "Asr ibodati va duo (16:15)", blocking = true),
        ScheduleItem(title = "Erkin / Oila davrasi", category = "other", start = "16:45", end = "17:59", note = "Dam olish va oila davrasi", blocking = false),
        ScheduleItem(title = "Tahorat → Shom namozi → dua", category = "prayer", start = "17:59", end = "18:30", note = "Shom ibodati va duo (17:59)", blocking = true),
        ScheduleItem(title = "Kechki ovqat & Haftalik xulosa", category = "food", start = "18:30", end = "19:13", note = "Kechki taom va hafta sarhisobi", blocking = false),
        ScheduleItem(title = "Tahorat → Xufton namozi → dua", category = "prayer", start = "19:13", end = "19:45", note = "Xufton ibodati va duo (19:13)", blocking = true),
        ScheduleItem(title = "⚡ Aileaders.uz Coursera sertifikatlari", category = "rtm", start = "19:45", end = "20:45", note = "Sertifikatlar ustida ishlash", blocking = true),
        ScheduleItem(title = "📖 O'zbek tilida kitob o'qish", category = "other", start = "20:45", end = "21:30", note = "Foydali kitob mutolaasi", blocking = false),
        ScheduleItem(title = "Yotishga tayyorgarlik", category = "night", start = "21:30", end = "22:00", note = "Telefonni boshqa xonaga qo'yish", blocking = false),
        ScheduleItem(title = "Uxlash", category = "night", start = "22:00", end = "05:00", note = "Tetik uyqu (22:00 — 05:00)", blocking = false)
    )

    fun getDayType(cal: Calendar = Calendar.getInstance()): DayType {
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> DayType.SUNDAY_LIGHT_DAY // 1 - Yakshanba
            Calendar.MONDAY -> DayType.LESSON_DAY // 2 - Dushanba (Maktab + 15:00-17:00 Ingliz)
            Calendar.TUESDAY -> DayType.NO_LESSON_DAY // 3 - Seshanba (Maktab, lekin dars yo'q)
            Calendar.WEDNESDAY -> DayType.LESSON_DAY // 4 - Chorshanba (Maktab + 15:00-17:00 Ingliz)
            Calendar.THURSDAY -> DayType.NO_LESSON_DAY // 5 - Payshanba (Maktab, lekin dars yo'q)
            Calendar.FRIDAY -> DayType.LESSON_DAY // 6 - Juma (Maktab + 15:00-17:00 Ingliz)
            Calendar.SATURDAY -> DayType.NO_LESSON_DAY // 7 - Shanba (Maktab, lekin dars yo'q)
            else -> DayType.NO_LESSON_DAY
        }
    }

    fun getDayTypeByCalendarDay(dayOfWeek: Int): DayType {
        return when (dayOfWeek) {
            Calendar.SUNDAY -> DayType.SUNDAY_LIGHT_DAY
            Calendar.MONDAY -> DayType.LESSON_DAY
            Calendar.TUESDAY -> DayType.NO_LESSON_DAY
            Calendar.WEDNESDAY -> DayType.LESSON_DAY
            Calendar.THURSDAY -> DayType.NO_LESSON_DAY
            Calendar.FRIDAY -> DayType.LESSON_DAY
            Calendar.SATURDAY -> DayType.NO_LESSON_DAY
            else -> DayType.NO_LESSON_DAY
        }
    }

    fun getTodayPlanInfo(cal: Calendar = Calendar.getInstance()): Pair<List<ScheduleItem>, String> {
        val dayType = getDayType(cal)
        val plan = when (dayType) {
            DayType.SUNDAY_LIGHT_DAY -> SUNDAY_PLAN
            DayType.LESSON_DAY -> LESSON_PLAN
            DayType.NO_LESSON_DAY -> FREE_PLAN
        }
        return Pair(plan, dayType.label)
    }
}
