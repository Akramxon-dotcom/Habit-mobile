package com.example.data.model

data class EnglishPlanTask(
    val id: String,
    val week: Int, // 1, 2, 3, 4
    val category: String, // Grammatika, Lug'at, Kitob, Audio, Serial, Gapirish, Yozish
    val title: String,
    val detail: String,
    val iconName: String = "menu_book"
)

data class EnglishResourceItem(
    val title: String,
    val skill: String,
    val resource: String,
    val why: String,
    val howToFind: String
)

data class DailyScheduleMapping(
    val timeSlot: String,
    val scheduleTitle: String,
    val whatToDo: String
)

data class WeekPlan(
    val weekNumber: Int,
    val title: String,
    val subtitle: String,
    val focus: String,
    val tasks: List<EnglishPlanTask>
)

object MonthlyEnglishPlanData {

    val RESOURCES = listOf(
        EnglishResourceItem(
            title = "Grammatika",
            skill = "Grammar & Structure",
            resource = "Essential Grammar in Use — Raymond Murphy (Elementary)",
            why = "Dunyodagi eng mashhur o'zi-o'zini o'rgatuvchi grammatika kitobi, har bir unit 1 sahifa qoida + 1 sahifa mashq.",
            howToFind = "Telegramda 'Essential Grammar in Use Murphy pdf' yoki kitob do'konlaridan 'Qizil Murphy'."
        ),
        EnglishResourceItem(
            title = "Lug'at",
            skill = "Vocabulary in Context",
            resource = "4000 Essential English Words — Book 1 va 2",
            why = "So'zlar darajama-daraja beriladi, har biri hikoya ichida, yodda qolishi oson.",
            howToFind = "Telegramda '4000 essential words pdf' deb qidiring (PDF + audio mavjud)."
        ),
        EnglishResourceItem(
            title = "Kitob o'qish (Reading)",
            skill = "Graded Readers",
            resource = "Oxford Bookworms Library — avval Starter, keyin Stage 1",
            why = "Maxsus til o'rganuvchilar uchun soddalashtirilgan asl asarlar (masalan 'The Elephant Man', 'Sherlock Holmes: Short Stories').",
            howToFind = "Telegramda 'Oxford Bookworms Starter pdf' deb qidirilsa barcha audio va kitoblari bepul chiqadi."
        ),
        EnglishResourceItem(
            title = "Tinglash (Audio)",
            skill = "Listening & Pronunciation",
            resource = "BBC Learning English — 6 Minute English; VOA Learning English — Words and Their Stories",
            why = "Sekin, aniq talaffuz, maxsus o'rganuvchilar uchun mo'ljallangan, transkripti ham bor.",
            howToFind = "YouTube, Spotify yoki BBC Learning English veb-sayti va mobil ilovasi."
        ),
        EnglishResourceItem(
            title = "Serial / Kino",
            skill = "Immersion & Natural Flow",
            resource = "Extra English (30 daqiqa) → Zootopia / Nemo → Friends (1-mavsum 1-qism)",
            why = "Boshqa filmlardan farqli — maxsus soddalashtirilgan tilda, subtitr bilan suratga olingan sitkom.",
            howToFind = "YouTube'da 'Extra English with subtitles' deb qidirilsa barcha 30 ta qismi mavjud."
        ),
        EnglishResourceItem(
            title = "Gapirish (Speaking)",
            skill = "Active Shadowing",
            resource = "Cake ilovasi (bepul, qisqa video + shadowing)",
            why = "Video ichidagi jumlani takrorlab, talaffuzni mashq qilasan.",
            howToFind = "Google Play do'konidan 'Cake' ilovasini bepul yuklab oling."
        ),
        EnglishResourceItem(
            title = "Yozish (Writing)",
            skill = "Daily Journaling",
            resource = "Kundalik — har kuni 5 ta jumla, keyin Grammarly orqali tekshirish",
            why = "Xatoingni darhol ko'rasan va tuzatasan.",
            howToFind = "Ilovadagi 'Kundalik' bo'limi yoki grammarly.com bepul versiyasi."
        ),
        EnglishResourceItem(
            title = "Video dars + testlar",
            skill = "Reinforcement",
            resource = "IBRAT Academy (bepul mobil ilova)",
            why = "Davom ettir — bu offline darsingni mustahkamlaydi.",
            howToFind = "IBRAT Academy ilovasi orqali bepul darslar."
        )
    )

    val SCHEDULE_MAPPINGS = listOf(
        DailyScheduleMapping("04:45 — 05:15", "Ingliz — yangi so'z yodlash", "4000 Essential Words — kunlik 5–8 ta so'z (yozib, talaffuz qilib)"),
        DailyScheduleMapping("06:55 — 07:35", "Grammatika mashqi", "Essential Grammar in Use — kunlik 1 unit (qoida + mashq)"),
        DailyScheduleMapping("20:35 — 21:05 (Dars kuni)", "Ingliz — Reading", "Oxford Bookworms — kuniga 1 bob (chapter)"),
        DailyScheduleMapping("05:15 / 16:15", "Ingliz — Listening", "6 Minute English yoki VOA — 1 epizod (transkript bilan)"),
        DailyScheduleMapping("15:30 — 16:15 (Bo'sh kun)", "Ingliz — Speaking", "Cake shadowing + IBRAT suhbat mashqlari"),
        DailyScheduleMapping("20:35 — 21:05 (Bo'sh kun)", "Ingliz — Yozish", "Kundalik 5 jumla (o'tgan zamonda) + Grammarly tekshiruvi"),
        DailyScheduleMapping("17:43 — 18:40 (Bo'sh kun)", "Ingliz — Film/Serial", "Extra English (1-2 hafta) → Zootopia/Nemo (3-hafta) → Friends (4-hafta)"),
        DailyScheduleMapping("19:40 — 20:00", "IBRAT Academy", "Rejadagidek davom — offline darsni mustahkamlaydi")
    )

    val WEEKS = listOf(
        WeekPlan(
            weekNumber = 1,
            title = "1-hafta — Asos",
            subtitle = "Present Simple & Continuous, 4000 Words boshlanishi",
            focus = "Tizimli odat shakllantirish, hozirgi zamon grammatikasi, Bookworms Starter 1-kitob va Extra English sitkomi bilan tanishuv",
            tasks = listOf(
                EnglishPlanTask("w1_gram", 1, "Grammatika", "Essential Grammar in Use — 1-15 unit", "Present Simple/Continuous mavzulari (har kuni 1 unit qoida + 1 unit mashq)"),
                EnglishPlanTask("w1_vocab", 1, "Lug'at", "4000 Words — 1-50-so'zlar", "Kuniga 7-8 ta yangi so'zni ovozli o'qib, daftarga yozib yodlash"),
                EnglishPlanTask("w1_book", 1, "Kitob", "Oxford Bookworms Starter — 1-kitob boshlash", "Masalan 'The Elephant Man' yoki 'Drive into Danger' — har kuni 1 bob"),
                EnglishPlanTask("w1_audio", 1, "Audio", "6 Minute English — 3 ta epizod", "Sekin tinglab, transkripti bilan birgalikda kuzatib borish"),
                EnglishPlanTask("w1_film", 1, "Serial", "Extra English — 1-4 qismlar", "30 daqiqalik qismlar, ingliz subtitr bilan, dialoglarni kuzatish"),
                EnglishPlanTask("w1_speak", 1, "Gapirish", "Cake — kuniga 2-3 ta video shadowing", "Video ichidagi jumlalarni ovoz chiqarib aynan takrorlash"),
                EnglishPlanTask("w1_write", 1, "Yozish", "Kundalik — har kuni 5 ta jumla", "Kechqurun bugungi kuningiz haqida 5 gap yozib, Grammarly bilan tekshirish")
            )
        ),
        WeekPlan(
            weekNumber = 2,
            title = "2-hafta — O'tmish zamon",
            subtitle = "Past Simple & Continuous, VOA audio, Starter 2-kitob",
            focus = "O'tgan zamon fe'llari (to'g'ri va noto'g'ri fe'llar), 51-100 so'zlar va Extra English 5-8 qismlar",
            tasks = listOf(
                EnglishPlanTask("w2_gram", 2, "Grammatika", "Essential Grammar in Use — Unit 16-30", "Past Simple/Continuous zamonlari (was/were, regular & irregular verbs)"),
                EnglishPlanTask("w2_vocab", 2, "Lug'at", "4000 Words — 51-100-so'zlar", "Yangi 50 ta so'z + avvalgi 1-50 so'zlarni qayta ko'rib chiqish"),
                EnglishPlanTask("w2_book", 2, "Kitob", "Bookworms Starter — 1-kitobni tugat, 2-kitobni boshla", "Starter darajadagi 2-qiziqarli hikoyaga o'tish"),
                EnglishPlanTask("w2_audio", 2, "Audio", "VOA — Words and Their Stories, 3 ta epizod", "Amerika ingliz tili iboralari va qiziqarli so'zlar tarixi"),
                EnglishPlanTask("w2_film", 2, "Serial", "Extra English — 5-8 qismlar", "Inglizcha subtitr bilan, hazillar va qahramonlar replikalarini tushunish"),
                EnglishPlanTask("w2_speak", 2, "Gapirish", "Cake davom + IBRAT ilovasidagi suhbat o'yinlari", "Suhbat mashqlari orqali nutqni erkinlashtirish"),
                EnglishPlanTask("w2_write", 2, "Yozish", "Kundalik — o'tgan zamonda 5 ta jumla", "Kecha va bugun nima bo'lganini faqat Past Simple'da yozish")
            )
        ),
        WeekPlan(
            weekNumber = 3,
            title = "3-hafta — Kelasi zamon va modal so'zlar",
            subtitle = "Future, can/must/should, Bookworms Stage 1, Zootopia/Nemo",
            focus = "Kelasi zamon rejalari, modal fe'llar, Stage 1 kitoblar, transkriptsiz listening va 1 daqiqalik video nutq",
            tasks = listOf(
                EnglishPlanTask("w3_gram", 3, "Grammatika", "Essential Grammar in Use — Unit 31-45", "Future (will, going to), can, could, must, should modal fe'llari"),
                EnglishPlanTask("w3_vocab", 3, "Lug'at", "4000 Words — 101-150-so'zlar", "So'zlarni shaxsiy misollar bilan mustahkamlash"),
                EnglishPlanTask("w3_book", 3, "Kitob", "Oxford Bookworms Stage 1 — 1-kitob", "Masalan 'Sherlock Holmes: Short Stories' yoki 'The Little Prince'"),
                EnglishPlanTask("w3_audio", 3, "Audio", "6 Minute English davom (transkriptsiz tinglash)", "Endi audioni matnga qaramay, quloq orqali tushunishga harakat qilish"),
                EnglishPlanTask("w3_film", 3, "Kino", "Zootopia yoki Finding Nemo (ingliz subtitr)", "Aniq talaffuzli, sodda va maroqli multfilm/kino"),
                EnglishPlanTask("w3_speak", 3, "Gapirish", "1 daqiqalik o'zing haqingda video/audio yoz", "Yozuvsiz, qog'ozga qaramasdan, erkin ovoz chiqarib gapirish"),
                EnglishPlanTask("w3_write", 3, "Yozish", "Kelasi zamon rejalari (5 ta jumla)", "Kelajakdagi maqsadlaringiz haqida 'will' va 'going to' bilan yozish")
            )
        ),
        WeekPlan(
            weekNumber = 4,
            title = "4-hafta — Mustahkamlash",
            subtitle = "Comparatives, prepositions, Friends (1-mavsum), Stage 1 2-kitob",
            focus = "Sifat darajalari, predloglar, 1-45 unitlarni umumiy tezkor takrorlash, Friends seriali va 2 daqiqalik erkin nutq",
            tasks = listOf(
                EnglishPlanTask("w4_gram", 4, "Grammatika", "Unit 46-60 + 1-45 ni tezkor takrorlash", "Comparatives, superlatives, prepositions va o'tilgan 60 ta unit yakuni"),
                EnglishPlanTask("w4_vocab", 4, "Lug'at", "4000 Words: 151-200 + avvalgi 150 tani takrorlash", "Ilovaning Sandiq (Vault) va test funksiyasi orqali mustahkamlash"),
                EnglishPlanTask("w4_book", 4, "Kitob", "Bookworms Stage 1 — 2-kitob", "Stage 1 darajasidagi ikkinchi to'liq asarni mutolaa qilish"),
                EnglishPlanTask("w4_audio", 4, "Audio", "Tanlagan podkastingdan 4-5 epizod", "Endi tabiiy va tezroq gapirish ritmiga ko'nikish"),
                EnglishPlanTask("w4_film", 4, "Serial", "Friends — 1-mavsum 1-qism (subtitr bilan)", "Birinchi haqiqiy serial tajribasi (qiyin bo'lsa Extra Englishga qaytiladi)"),
                EnglishPlanTask("w4_speak", 4, "Gapirish", "O'zing haqingda 2 daqiqalik video/audio yoz", "Hech qanday notasiz, erkin inglizcha fikr bayon qilish"),
                EnglishPlanTask("w4_review", 4, "Yakuniy xulosa", "1 oylik natijalarni sarhisob qilish", "Elementar (A2) darajaga erishilganini baholash va 2-oyga tayyorgarlik")
            )
        )
    )

    fun getAllTasks(): List<EnglishPlanTask> {
        return WEEKS.flatMap { it.tasks }
    }
}
