package com.example.data.model

import java.util.UUID

data class ScheduleItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String = "general",
    val start: String = "08:00", // HH:mm format, e.g. "07:00"
    val end: String = "09:00",   // HH:mm format, e.g. "07:40"
    val note: String = "",
    val priority: String = "orta",
    val blocking: Boolean = true,
    val isDone: Boolean = false,
    val isMissed: Boolean = false
) {
    val time: String
        get() = "$start - $end"

    constructor(
        id: String = UUID.randomUUID().toString(),
        title: String,
        time: String,
        category: String = "general",
        priority: String = "orta",
        note: String = "",
        blocking: Boolean = true,
        isDone: Boolean = false,
        isMissed: Boolean = false
    ) : this(
        id = id,
        title = title,
        category = category,
        start = time.split("-").firstOrNull()?.trim() ?: "08:00",
        end = time.split("-").getOrNull(1)?.trim() ?: "09:00",
        note = note,
        priority = priority,
        blocking = blocking,
        isDone = isDone,
        isMissed = isMissed
    )
    fun getCategoryLabel(): String {
        return when (category.lowercase()) {
            "prayer" -> "Namoz"
            "english" -> "Ingliz"
            "sport" -> "Sport"
            "food" -> "Ovqat"
            "school" -> "Maktab"
            "rtm" -> "RTM"
            "other" -> "Erkin"
            "night" -> "Tun"
            else -> category.replaceFirstChar { it.uppercase() }
        }
    }

    fun getCategoryIcon(): String {
        return when (category.lowercase()) {
            "prayer" -> "🕌"
            "english" -> "🇬🇧"
            "sport" -> "🏃"
            "food" -> "🍽️"
            "school" -> "🏫"
            "rtm" -> "💻"
            "other" -> "⏳"
            "night" -> "🌙"
            else -> "📌"
        }
    }

    fun getWhyText(): String {
        return when (category.lowercase()) {
            "prayer" -> "Kun davomidagi eng muhim ustuvorlik — hamma narsa shundan keyin quriladi."
            "english" -> "Bugun o'rgangan har bir so'z — ertangi imkoniyatlaring uchun zamin."
            "sport" -> "Tanang tetik bo'lmasa, miyang ham charchaydi — bu investitsiya, dam emas."
            "food" -> "Yaxshi ovqatlanmasang, keyingi 3 soat samarasiz o'tadi."
            "school" -> "Bu ham kelajaging — o'tkazib bo'lmaydi."
            "rtm" -> "Bu yerda orttirgan tajriba — hozir sen uchun eng katta boylik."
            "other" -> "Dam olish ham reja qismi — charchoqni to'plamaslik uchun shart."
            "night" -> "Yaxshi uyqu — ertangi kunning sifatini belgilaydi."
            else -> "Kun tartibiga sodiq qolish — intizom va muvaffaqiyat garovi."
        }
    }

    fun getSegment(): String {
        val t = title.lowercase()
        return when {
            t.contains("bomdod") -> "Tong"
            t.contains("peshin") -> "Kunduz"
            t.contains("asr") -> "Kech"
            t.contains("shom") -> "Kechqurun"
            t.contains("xufton") || t.contains("uxlash") -> "Tun"
            else -> {
                val sHour = start.split(":").firstOrNull()?.toIntOrNull() ?: 8
                when (sHour) {
                    in 4..7 -> "Tong"
                    in 8..14 -> "Kunduz"
                    in 15..17 -> "Kech"
                    in 18..20 -> "Kechqurun"
                    else -> "Tun"
                }
            }
        }
    }
}
