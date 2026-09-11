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
            Calendar.JUNE -> "iyul"
            Calendar.JULY -> "iyun"
            Calendar.AUGUST -> "avgust"
            Calendar.SEPTEMBER -> "sentabr"
            Calendar.OCTOBER -> "oktabr"
            Calendar.NOVEMBER -> "noyabr"
            Calendar.DECEMBER -> "dekabr"
            else -> ""
        }
        return "$dayOfWeek, $dayOfMonth-$monthName"
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

    // Default schedules matching the user's website exact plans
    val LESSON_PLAN = listOf(
        ScheduleItem(title = "Uyg'onish → Tahorat → Bomdod namozi → dua", category = "prayer", start = "04:05", end = "04:40", note = "Kun boshidagi eng muhim ustuvorlik", blocking = true),
        ScheduleItem(title = "Ingliz — yangi 15-20 so'z yodlash", category = "english", start = "04:40", end = "05:10", note = "Yangi so'zlar", blocking = true),
        ScheduleItem(title = "Ingliz — avvalgi so'zlarni takrorlash", category = "english", start = "05:10", end = "05:30", note = "Unutmaslik uchun shart", blocking = true),
        ScheduleItem(title = "Sport + ingliz audio tinglash", category = "sport", start = "05:30", end = "06:05", note = "Jismoniy chiniqish", blocking = false),
        ScheduleItem(title = "Dush, tayyorgarlik", category = "other", start = "06:05", end = "06:35", note = "Kiyinish va tayyorgarlik", blocking = false),
        ScheduleItem(title = "Nonushta", category = "food", start = "06:35", end = "07:00", note = "Quvvat to'plash", blocking = false),
        ScheduleItem(title = "Ingliz — Grammatika mashqi", category = "english", start = "07:00", end = "07:40", note = "Grammatika qoidalari", blocking = true),
        ScheduleItem(title = "Maktabga yo'l (+ audio tinglash)", category = "other", start = "07:40", end = "08:00", note = "Yo'lda foydali audio", blocking = false),
        ScheduleItem(title = "Maktab", category = "school", start = "08:00", end = "13:00", note = "Darslar jarayoni", blocking = true),
        ScheduleItem(title = "Yo'l → Tahorat → Peshin namozi → dua", category = "prayer", start = "13:00", end = "13:35", note = "Tanaffusda imkon bo'lsa maktabda o'qi", blocking = true),
        ScheduleItem(title = "Tushlik", category = "food", start = "13:35", end = "13:55", note = "Tushlik vaqti", blocking = false),
        ScheduleItem(title = "RTM — 4 guruh vazifalari / o'z ishlaring", category = "rtm", start = "13:55", end = "15:00", note = "Loyiha va kodlash", blocking = true),
        ScheduleItem(title = "🇬🇧 Ingliz tili darsi (offline)", category = "english", start = "15:00", end = "17:00", note = "Asosiy dars mashg'uloti", blocking = true),
        ScheduleItem(title = "Tahorat → Asr namozi → dua", category = "prayer", start = "17:00", end = "17:25", note = "Darsda tanaffus bo'lmasa, dars tugagach", blocking = true),
        ScheduleItem(title = "Erkin tanaffus — telefon emas", category = "other", start = "17:25", end = "17:55", note = "Miyani dam oldirish", blocking = false),
        ScheduleItem(title = "Ingliz — dars uy vazifasi (yozma + takror)", category = "english", start = "17:55", end = "18:40", note = "Yozma vazifalar", blocking = true),
        ScheduleItem(title = "Tahorat → Shom namozi → dua", category = "prayer", start = "18:40", end = "19:08", note = "Shom ibodati", blocking = true),
        ScheduleItem(title = "Kechki ovqat", category = "food", start = "19:08", end = "19:40", note = "Oila bilan birga", blocking = false),
        ScheduleItem(title = "IBRAT Academy", category = "english", start = "19:40", end = "20:00", note = "Ilova orqali o'rganish", blocking = true),
        ScheduleItem(title = "Tahorat → Xufton namozi → dua", category = "prayer", start = "20:00", end = "20:35", note = "Xufton ibodati", blocking = true),
        ScheduleItem(title = "Ingliz — Reading (kitob)", category = "english", start = "20:35", end = "21:05", note = "Inglizcha mutolaa", blocking = true),
        ScheduleItem(title = "📖 O'zbek tilida kitob o'qish", category = "other", start = "21:05", end = "21:30", note = "Foydali kitob", blocking = false),
        ScheduleItem(title = "Yotishga tayyorgarlik — telefon boshqa xonaga", category = "night", start = "21:30", end = "22:00", note = "Telefonni boshqa xonaga qo'yish", blocking = false),
        ScheduleItem(title = "Uxlash", category = "night", start = "22:00", end = "04:05", note = "Tetik uyqu", blocking = false)
    )

    val FREE_PLAN = listOf(
        ScheduleItem(title = "Uyg'onish → Tahorat → Bomdod namozi → dua", category = "prayer", start = "04:05", end = "04:40", note = "Kun boshidagi ibodat", blocking = true),
        ScheduleItem(title = "Ingliz — yangi so'z yodlash", category = "english", start = "04:40", end = "05:10", note = "Yangi so'zlar", blocking = true),
        ScheduleItem(title = "Ingliz — so'zlarni takrorlash", category = "english", start = "05:10", end = "05:30", note = "Avvalgi so'zlar", blocking = true),
        ScheduleItem(title = "Sport + audio", category = "sport", start = "05:30", end = "06:05", note = "Jismoniy tarbiya", blocking = false),
        ScheduleItem(title = "Dush", category = "other", start = "06:05", end = "06:35", note = "Tetiklanish", blocking = false),
        ScheduleItem(title = "Nonushta", category = "food", start = "06:35", end = "07:00", note = "Nonushta", blocking = false),
        ScheduleItem(title = "Ingliz — Grammatika mashqi", category = "english", start = "07:00", end = "07:40", note = "Grammatika", blocking = true),
        ScheduleItem(title = "Maktabga yo'l (+ audio)", category = "other", start = "07:40", end = "08:00", note = "Yo'lda tinglash", blocking = false),
        ScheduleItem(title = "Maktab", category = "school", start = "08:00", end = "13:00", note = "Darslar", blocking = true),
        ScheduleItem(title = "Yo'l → Tahorat → Peshin namozi → dua", category = "prayer", start = "13:00", end = "13:35", note = "Peshin ibodati", blocking = true),
        ScheduleItem(title = "Tushlik", category = "food", start = "13:35", end = "13:55", note = "Tushlik", blocking = false),
        ScheduleItem(title = "RTM — 4 guruhning ortda qolgan vazifalari", category = "rtm", start = "13:55", end = "15:30", note = "Amaliy dasturlash", blocking = true),
        ScheduleItem(title = "Ingliz — Speaking (IBRAT / Cake shadowing)", category = "english", start = "15:30", end = "16:15", note = "Nutqni o'stirish", blocking = true),
        ScheduleItem(title = "Ingliz — Listening (podkast)", category = "english", start = "16:15", end = "16:45", note = "Eshitib tushunish", blocking = true),
        ScheduleItem(title = "Tahorat → Asr namozi → dua", category = "prayer", start = "16:45", end = "17:13", note = "Asr ibodati", blocking = true),
        ScheduleItem(title = "Erkin tanaffus", category = "other", start = "17:13", end = "17:43", note = "Dam olish", blocking = false),
        ScheduleItem(title = "🎬 Ingliz — film/serial", category = "english", start = "17:43", end = "18:40", note = "Film orqali o'rganish", blocking = true),
        ScheduleItem(title = "Tahorat → Shom namozi → dua", category = "prayer", start = "18:40", end = "19:08", note = "Shom ibodati", blocking = true),
        ScheduleItem(title = "Kechki ovqat", category = "food", start = "19:08", end = "19:40", note = "Ovqatlanish", blocking = false),
        ScheduleItem(title = "IBRAT Academy", category = "english", start = "19:40", end = "20:00", note = "Mashqlar", blocking = true),
        ScheduleItem(title = "Tahorat → Xufton namozi → dua", category = "prayer", start = "20:00", end = "20:35", note = "Xufton ibodati", blocking = true),
        ScheduleItem(title = "Ingliz — Writing mashqi", category = "english", start = "20:35", end = "21:05", note = "Insho va yozish", blocking = true),
        ScheduleItem(title = "📖 O'zbek tilida kitob o'qish", category = "other", start = "21:05", end = "21:30", note = "Kitob mutolaasi", blocking = false),
        ScheduleItem(title = "Yotishga tayyorgarlik", category = "night", start = "21:30", end = "22:00", note = "Telefon boshqa xonaga", blocking = false),
        ScheduleItem(title = "Uxlash", category = "night", start = "22:00", end = "04:05", note = "Uyqu", blocking = false)
    )

    val SUNDAY_PLAN = listOf(
        ScheduleItem(title = "Uyg'onish → Tahorat → Bomdod namozi → dua", category = "prayer", start = "04:05", end = "04:40", note = "Bomdod ibodati", blocking = true),
        ScheduleItem(title = "Ingliz — yangi so'z (yengil)", category = "english", start = "04:40", end = "05:10", note = "Yengil takror", blocking = true),
        ScheduleItem(title = "So'z takrori", category = "english", start = "05:10", end = "05:30", note = "Takrorlash", blocking = true),
        ScheduleItem(title = "Sport", category = "sport", start = "05:30", end = "06:00", note = "Yengil yugurish", blocking = false),
        ScheduleItem(title = "Dush", category = "other", start = "06:00", end = "06:30", note = "Tetiklanish", blocking = false),
        ScheduleItem(title = "Nonushta", category = "food", start = "06:30", end = "07:00", note = "Nonushta", blocking = false),
        ScheduleItem(title = "IBRAT Academy", category = "english", start = "07:00", end = "08:00", note = "O'rganish", blocking = true),
        ScheduleItem(title = "Ingliz — Reading", category = "english", start = "08:00", end = "09:00", note = "Mutolaa", blocking = true),
        ScheduleItem(title = "Tanaffus", category = "other", start = "09:00", end = "09:15", note = "Dam olish", blocking = false),
        ScheduleItem(title = "Ingliz — Speaking", category = "english", start = "09:15", end = "10:15", note = "Og'zaki nutq", blocking = true),
        ScheduleItem(title = "Erkin vaqt / loyihalar", category = "other", start = "10:15", end = "12:20", note = "Shaxsiy loyihalar", blocking = false),
        ScheduleItem(title = "Tahorat → Peshin namozi → dua", category = "prayer", start = "12:20", end = "12:52", note = "Peshin ibodati", blocking = true),
        ScheduleItem(title = "Tushlik", category = "food", start = "12:52", end = "13:30", note = "Tushlik", blocking = false),
        ScheduleItem(title = "Qaylula", category = "night", start = "13:30", end = "14:15", note = "Kechki quvvat uchun kunduzgi uyqu", blocking = false),
        ScheduleItem(title = "Ingliz — Listening", category = "english", start = "14:15", end = "15:15", note = "Audio tinglash", blocking = true),
        ScheduleItem(title = "🎬 Ingliz — Film", category = "english", start = "15:15", end = "16:15", note = "Film", blocking = true),
        ScheduleItem(title = "Erkin / Oila", category = "other", start = "16:15", end = "16:45", note = "Oila davrasida", blocking = false),
        ScheduleItem(title = "Tahorat → Asr namozi → dua", category = "prayer", start = "16:45", end = "17:13", note = "Asr ibodati", blocking = true),
        ScheduleItem(title = "Erkin tanaffus", category = "other", start = "17:13", end = "18:00", note = "Erkin vaqt", blocking = false),
        ScheduleItem(title = "Haftalik xulosa — nima o'rgandim, keyingi hafta rejasi", category = "english", start = "18:00", end = "18:40", note = "Hafta tahlili", blocking = true),
        ScheduleItem(title = "Tahorat → Shom namozi → dua", category = "prayer", start = "18:40", end = "19:08", note = "Shom ibodati", blocking = true),
        ScheduleItem(title = "Kechki ovqat", category = "food", start = "19:08", end = "19:40", note = "Kechki ovqat", blocking = false),
        ScheduleItem(title = "📖 O'zbek tilida kitob o'qish", category = "other", start = "19:40", end = "20:00", note = "Kitob mutolaasi", blocking = false),
        ScheduleItem(title = "Tahorat → Xufton namozi → dua", category = "prayer", start = "20:00", end = "20:35", note = "Xufton ibodati", blocking = true),
        ScheduleItem(title = "Erkin / Oila", category = "other", start = "20:35", end = "21:30", note = "Oila davrasi", blocking = false),
        ScheduleItem(title = "Yotishga tayyorgarlik", category = "night", start = "21:30", end = "22:00", note = "Dam olish", blocking = false),
        ScheduleItem(title = "Uxlash", category = "night", start = "22:00", end = "04:05", note = "Uyqu", blocking = false)
    )

    fun getTodayPlanInfo(): Pair<List<ScheduleItem>, String> {
        val cal = Calendar.getInstance()
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> Pair(SUNDAY_PLAN, "Yengil kun — Yakshanba")
            Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY -> Pair(LESSON_PLAN, "Dars kuni")
            else -> Pair(FREE_PLAN, "Dars yo'q kun")
        }
    }
}
