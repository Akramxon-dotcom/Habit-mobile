package com.example.data.discipline

import java.util.UUID

enum class PunishmentCategory(val label: String, val icon: String) {
    PHYSICAL("Jismoniy Tarbiya", "💪"),
    ENGLISH("Ingliz Tili Intellekti", "🇬🇧"),
    SPIRITUAL("Ruhiy & Diniy Mas'uliyat", "🕌"),
    COGNITIVE_TIME("Vaqt & Iroda Cheklovi", "⏳")
}

enum class PunishmentType(
    val category: PunishmentCategory,
    val title: String,
    val icon: String,
    val unit: String
) {
    PUSH_UP(PunishmentCategory.PHYSICAL, "Atjimaniya (Push-up)", "🦾", "ta"),
    SQUAT(PunishmentCategory.PHYSICAL, "Squat (O'tirib-turish)", "🦵", "ta"),
    PLANK(PunishmentCategory.PHYSICAL, "Planka (Statik ushlash)", "🧘", "soniya"),
    RUNNING_ON_SPOT(PunishmentCategory.PHYSICAL, "Joyida tezkor yugurish", "🏃", "soniya"),

    VOCAB_DRILL(PunishmentCategory.ENGLISH, "Leksika terish (Aniq yozuv)", "✍️", "so'z"),
    GRAMMAR_TEST(PunishmentCategory.ENGLISH, "Grammatika qat'iy testi", "📝", "savol"),
    COPY_PARAGRAPH(PunishmentCategory.ENGLISH, "Akademik matndan ko'chirish", "📖", "gap"),

    QURAN_TILOVAT(PunishmentCategory.SPIRITUAL, "Qur'on tilovati & Tadabbur", "📖", "daqiqa"),
    TASBEH(PunishmentCategory.SPIRITUAL, "Istig'for / Tasbeh", "📿", "marta"),

    TIME_FORFEIT(PunishmentCategory.COGNITIVE_TIME, "Vaqt sandig'i jarimasi", "⏳", "daqiqa"),
    SILENCE_MEDITATION(PunishmentCategory.COGNITIVE_TIME, "Monk Mode Sukunat", "🤫", "daqiqa")
}

data class PunishmentSetStructure(
    val totalReps: Int,
    val setsCount: Int,
    val repsPerSet: Int,
    val restSecondsBetweenSets: Int
)

data class PunishmentPlan(
    val id: String = UUID.randomUUID().toString(),
    val type: PunishmentType,
    val targetAmount: Int,
    val setStructure: PunishmentSetStructure,
    val taskTitle: String,
    val taskCategory: String,
    val instruction: String,
    val antiCheatRule: String,
    val startEpochMs: Long = System.currentTimeMillis()
)

object PunishmentPlanner {

    /**
     * Vazifaning toifasiga qarab mos 2 ta jazo variantini tanlab beradi.
     */
    fun getEligibleOptionsForCategory(taskCategory: String): List<PunishmentType> {
        val cat = taskCategory.lowercase().trim()
        return when {
            cat == "english" -> listOf(PunishmentType.VOCAB_DRILL, PunishmentType.GRAMMAR_TEST, PunishmentType.COPY_PARAGRAPH)
            cat == "prayer" -> listOf(PunishmentType.TASBEH, PunishmentType.QURAN_TILOVAT)
            cat in listOf("sport", "other", "food", "night") -> listOf(PunishmentType.PUSH_UP, PunishmentType.SQUAT, PunishmentType.PLANK)
            cat in listOf("school", "rtm") -> listOf(PunishmentType.TIME_FORFEIT, PunishmentType.SILENCE_MEDITATION, PunishmentType.PUSH_UP)
            else -> listOf(PunishmentType.PUSH_UP, PunishmentType.VOCAB_DRILL, PunishmentType.TASBEH)
        }
    }

    /**
     * Eskalatsiya va foydalanuvchi quvvatiga mos Setlar va Tanaffuslar tuzilmasi.
     * Foydalanuvchi so'roviga binoan: 30 tadan oshib ketsa ham ortiqcha himoyalanmaydi,
     * ammo yurakka va mushaklarga zarar yetkazmaslik uchun to'plamlar (setlar) va tanaffus bilan hisoblanadi.
     */
    fun buildPlan(
        type: PunishmentType,
        dailyMissCount: Int,
        taskTitle: String,
        taskCategory: String
    ): PunishmentPlan {
        val multiplier = dailyMissCount.coerceAtLeast(1)

        val setStructure = when (type) {
            PunishmentType.PUSH_UP -> {
                // 1-xato: 15 ta (1x15)
                // 2-xato: 24 ta (2x12, 30s rest)
                // 3-xato: 36 ta (3x12, 35s rest)
                // 4+ xato: 50 ta (3x17 yoki 2x25, 45s rest)
                when (multiplier) {
                    1 -> PunishmentSetStructure(totalReps = 15, setsCount = 1, repsPerSet = 15, restSecondsBetweenSets = 0)
                    2 -> PunishmentSetStructure(totalReps = 24, setsCount = 2, repsPerSet = 12, restSecondsBetweenSets = 30)
                    3 -> PunishmentSetStructure(totalReps = 36, setsCount = 3, repsPerSet = 12, restSecondsBetweenSets = 35)
                    else -> PunishmentSetStructure(totalReps = 48 + (multiplier - 4) * 6, setsCount = 3, repsPerSet = 16 + (multiplier - 4) * 2, restSecondsBetweenSets = 40)
                }
            }

            PunishmentType.SQUAT -> {
                // 1-xato: 20 ta (1x20)
                // 2-xato: 32 ta (2x16, 25s rest)
                // 3-xato: 45 ta (3x15, 30s rest)
                // 4+ xato: 60+ ta (3x20, 35s rest)
                when (multiplier) {
                    1 -> PunishmentSetStructure(totalReps = 20, setsCount = 1, repsPerSet = 20, restSecondsBetweenSets = 0)
                    2 -> PunishmentSetStructure(totalReps = 32, setsCount = 2, repsPerSet = 16, restSecondsBetweenSets = 25)
                    3 -> PunishmentSetStructure(totalReps = 45, setsCount = 3, repsPerSet = 15, restSecondsBetweenSets = 30)
                    else -> PunishmentSetStructure(totalReps = 60 + (multiplier - 4) * 10, setsCount = 3, repsPerSet = 20 + (multiplier - 4) * 3, restSecondsBetweenSets = 35)
                }
            }

            PunishmentType.PLANK -> {
                // Soniyalar: 30s, 45s, 60s, 90s, 120s (setlarga bo'linishi mumkin)
                when (multiplier) {
                    1 -> PunishmentSetStructure(totalReps = 35, setsCount = 1, repsPerSet = 35, restSecondsBetweenSets = 0)
                    2 -> PunishmentSetStructure(totalReps = 50, setsCount = 2, repsPerSet = 25, restSecondsBetweenSets = 20)
                    3 -> PunishmentSetStructure(totalReps = 75, setsCount = 3, repsPerSet = 25, restSecondsBetweenSets = 25)
                    else -> PunishmentSetStructure(totalReps = 100, setsCount = 2, repsPerSet = 50, restSecondsBetweenSets = 30)
                }
            }

            PunishmentType.RUNNING_ON_SPOT -> {
                when (multiplier) {
                    1 -> PunishmentSetStructure(totalReps = 45, setsCount = 1, repsPerSet = 45, restSecondsBetweenSets = 0)
                    2 -> PunishmentSetStructure(totalReps = 70, setsCount = 2, repsPerSet = 35, restSecondsBetweenSets = 20)
                    3 -> PunishmentSetStructure(totalReps = 90, setsCount = 3, repsPerSet = 30, restSecondsBetweenSets = 25)
                    else -> PunishmentSetStructure(totalReps = 120, setsCount = 3, repsPerSet = 40, restSecondsBetweenSets = 30)
                }
            }

            PunishmentType.VOCAB_DRILL -> {
                // 1-xato: 15 ta so'z, 2-xato: 20 ta, 3-xato: 25 ta, 4+ xato: 30 ta
                val total = (12 + multiplier * 4).coerceAtMost(35)
                PunishmentSetStructure(totalReps = total, setsCount = 1, repsPerSet = total, restSecondsBetweenSets = 0)
            }

            PunishmentType.GRAMMAR_TEST -> {
                val total = (8 + multiplier * 2).coerceAtMost(16)
                PunishmentSetStructure(totalReps = total, setsCount = 1, repsPerSet = total, restSecondsBetweenSets = 0)
            }

            PunishmentType.COPY_PARAGRAPH -> {
                val total = (3 + multiplier).coerceAtMost(6)
                PunishmentSetStructure(totalReps = total, setsCount = 1, repsPerSet = total, restSecondsBetweenSets = 0)
            }

            PunishmentType.QURAN_TILOVAT -> {
                val mins = (5 + (multiplier - 1) * 3).coerceAtMost(20)
                PunishmentSetStructure(totalReps = mins, setsCount = 1, repsPerSet = mins, restSecondsBetweenSets = 0)
            }

            PunishmentType.TASBEH -> {
                // 100, 150, 200, 300
                val total = when (multiplier) {
                    1 -> 100
                    2 -> 150
                    3 -> 200
                    else -> 300
                }
                PunishmentSetStructure(totalReps = total, setsCount = 1, repsPerSet = total, restSecondsBetweenSets = 0)
            }

            PunishmentType.TIME_FORFEIT -> {
                val mins = (15 + (multiplier - 1) * 10).coerceAtMost(60)
                PunishmentSetStructure(totalReps = mins, setsCount = 1, repsPerSet = mins, restSecondsBetweenSets = 0)
            }

            PunishmentType.SILENCE_MEDITATION -> {
                val mins = (5 + multiplier * 2).coerceAtMost(15)
                PunishmentSetStructure(totalReps = mins, setsCount = 1, repsPerSet = mins, restSecondsBetweenSets = 0)
            }
        }

        val instruction = when (type) {
            PunishmentType.PUSH_UP -> "Telefonni polga, ko'kragingiz ostiga qo'ying. Har bir tushishda ko'krak 3-5 sm yaqinlashishi shart. Sensor avtomatik sanaydi."
            PunishmentType.SQUAT -> "Telefonni cho'ntakka soling yoki ko'kragingizga bosib turing. To'liq chuqur o'tirib turing — akselerometr to'liq amplitudani hisoblaydi."
            PunishmentType.PLANK -> "Planka holatida turing, telefon yerda ko'z o'ngingizda bo'lsin. Datchik inson tanasining tabiiy mikrotebranishini tekshiradi (stolga tashlab ketilsa taymer to'xtaydi)."
            PunishmentType.RUNNING_ON_SPOT -> "Telefonni cho'ntakka solib yoki qo'lda ushlab joyingizda ritmik va tezkor yuguring."
            PunishmentType.VOCAB_DRILL -> "Ekranda chiqqan inglizcha so'zlarni to'liq, harfma-harf aniq yozing. Xato bo'lsa qabul qilinmaydi."
            PunishmentType.GRAMMAR_TEST -> "Berilgan grammatik savollarga to'g'ri javob bering. Natija 80% dan past bo'lsa, qaytadan topshirasiz."
            PunishmentType.COPY_PARAGRAPH -> "Ekranda taqdim etilgan murakkab inglizcha matnni diqqat bilan terib chiqing."
            PunishmentType.QURAN_TILOVAT -> "Qur'on oyatlarini tartil bilan o'qing. Ekran faol qolishi va muttasil tilovat holati kuzatiladi."
            PunishmentType.TASBEH -> "Astag'firulloh tasbehini sanang. Har bir tap orasida kamida 0.4 soniya bo'lishi shart, qalloblik sanalmaydi."
            PunishmentType.TIME_FORFEIT -> "Kechikkanlik uchun Vaqt sandig'ingizdan ushbu vaqt ayirib tashlanadi."
            PunishmentType.SILENCE_MEDITATION -> "Mutlaq sukunatda, harakatsiz o'tiring. Shovqin yoki harakat sezilsa taymer yangilanadi."
        }

        val antiCheatRule = when (type) {
            PunishmentType.PUSH_UP -> "Ko'krak datchikka yetmasa yoki 0.9s dan tez teginilsa — hisoblanmaydi."
            PunishmentType.SQUAT -> "Chala bukilishlar akselerometr tomonidan rad etiladi."
            PunishmentType.PLANK -> "Tananing mikrotebranishi yo'qolsa (telefon qo'yib ketilsa) taymer darhol to'xtatiladi."
            PunishmentType.RUNNING_ON_SPOT -> "Sekin tebranish qadam deb qabul qilinmaydi."
            PunishmentType.TASBEH -> "0.4 soniyadan tez ketma-ket bosishlar avtomatik bloklanadi."
            else -> "Real vaqtli tekshiruv dasturi faol."
        }

        return PunishmentPlan(
            type = type,
            targetAmount = setStructure.totalReps,
            setStructure = setStructure,
            taskTitle = taskTitle,
            taskCategory = taskCategory,
            instruction = instruction,
            antiCheatRule = antiCheatRule
        )
    }
}
