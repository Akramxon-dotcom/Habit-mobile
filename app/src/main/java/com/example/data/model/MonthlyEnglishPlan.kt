package com.example.data.model

data class EnglishPlanTask(
    val id: String,
    val week: Int, // 1, 2, 3, 4
    val category: String, // Grammatika, Lug'at, Kitob, Audio, Serial, Gapirish, Yozish, Sport
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

data class SportExercise(
    val name: String,
    val setsAndReps: String,
    val technique: String,
    val targetMuscle: String
)

data class SportWorkoutDay(
    val id: String,
    val daysLabel: String,
    val title: String,
    val focus: String,
    val durationMinutes: Int,
    val warmUp: String,
    val exercises: List<SportExercise>,
    val coolDown: String,
    val audioCompanion: String
)

data class FilmSeriesPlan(
    val weekNumber: Int,
    val title: String,
    val episodes: String,
    val platform: String,
    val watchMethod: String,
    val whyThisChoice: String,
    val taskForToday: String
)

data class VocabPlanDetail(
    val weekNumber: Int,
    val sourceBook: String,
    val targetWordsCount: String,
    val dailyQuota: String,
    val keyWordsSample: List<String>,
    val retentionMethod: String
)

data class WeekPlan(
    val weekNumber: Int,
    val title: String,
    val subtitle: String,
    val focus: String,
    val tasks: List<EnglishPlanTask>
)

object MonthlyEnglishPlanData {

    val SPORT_PROGRAM = listOf(
        SportWorkoutDay(
            id = "sport_upper_body",
            daysLabel = "Dushanba, Chorshanba, Juma",
            title = "Yuqori tana, Kuch & Korpus (Turnik, Otjimaniya, Plank)",
            focus = "Yelka, ko'krak, qo'llar va kuchli qorin matrisi",
            durationMinutes = 35,
            warmUp = "5 daqiqa: Bo'yin, yelkalar, tirsaklar va bel bo'g'inlarini to'liq aylantirib qizdirish",
            exercises = listOf(
                SportExercise(
                    name = "Turnikda tortilish (Pull-ups)",
                    setsAndReps = "4 set × 6-10 marta (keng yoki o'rtacha ushlash)",
                    technique = "Iyakni turnikdan balandga ko'taring, pastga sekin tushing. Kuch yetmasa sakrab sekin tushing (negative pull-ups)",
                    targetMuscle = "Keng orqa (latissimus), kurak va bilak"
                ),
                SportExercise(
                    name = "Yerdan otjimaniya (Push-ups)",
                    setsAndReps = "4 set × 15-20 marta",
                    technique = "Tana to'g'ri taxtadek, tirsaklar 45 darajada orqaga ochiladi. Ko'krak polga 2 sm qolguncha tushadi",
                    targetMuscle = "Ko'krak mushaklari, triceps va old yelka"
                ),
                SportExercise(
                    name = "Brusda ko'tarilish (Dips) yoki stulda teskari otjimaniya",
                    setsAndReps = "3 set × 10-12 marta",
                    technique = "Tirsaklar 90 gradusgacha bukiladi, tana tebranmaydi",
                    targetMuscle = "Triceps va ko'krakning pastki qismi"
                ),
                SportExercise(
                    name = "Tirsakda Plank (Korpus mustahkamligi)",
                    setsAndReps = "3 set × 45-60 soniya (orasi 30 soniya dam)",
                    technique = "Qorinni ichga tortib, dumg'azani orqaga chiqarmang, to'liq bir chiziqda turing",
                    targetMuscle = "To'g'ri qorin mushagi (press), korpus"
                )
            ),
            coolDown = "5 daqiqa: Qo'llarni cho'zish (stretching), chuqur nafas rostlash va suv ichish",
            audioCompanion = "🎧 Mashg'ulot davomida quloqda: BBC 6 Minute English yangi epizodi (1-tinglash)"
        ),
        SportWorkoutDay(
            id = "sport_legs_cardio",
            daysLabel = "Seshanba, Payshanba, Shanba",
            title = "Oyoq, Kardio & Chidamlilik (Yugurish, Prisidaniya, Lunges)",
            focus = "Oyoq kuchi, yurak-qon tomir tizimi va yuqori energiya",
            durationMinutes = 35,
            warmUp = "5 daqiqa: Tizzalar, to'piq va belni qizdirish, 50 ta joyida sakrash (Jumping Jacks)",
            exercises = listOf(
                SportExercise(
                    name = "Ertalabki yengil yugurish yoki Arqon sakrash",
                    setsAndReps = "12-15 daqiqa (2-3 km toza havoda yugurish YOKI 500 ta arqon sakrash)",
                    technique = "Burun orqali chuqur nafas oling, tovonni emas, oyoq kaftini yumshoq bosing",
                    targetMuscle = "Yurak, o'pka, umumiy chidamlilik va oyoqlar"
                ),
                SportExercise(
                    name = "Chuqur prisidaniya (Bodyweight Squats)",
                    setsAndReps = "4 set × 20-25 marta",
                    technique = "Oyoqlar yelka kengligida, orqani tik tutib go'yo stulga o'tirgandek pastga tushing, tovon poldan uzilmasin",
                    targetMuscle = "Son to'rt boshli mushagi (quads) va dumba"
                ),
                SportExercise(
                    name = "Oldinga qadam tashlash (Walking Lunges)",
                    setsAndReps = "Har bir oyoqqa 3 set × 12 marta (jami 24 qadam)",
                    technique = "Oldingi tizza 90 daraja, orqa tizza yerga deyarli tegadi, gavda tik",
                    targetMuscle = "Sonning orqa qismi va muvozanat"
                ),
                SportExercise(
                    name = "Boldir mashqi (Calf Raises) + Qorin pressi (Crunches)",
                    setsAndReps = "Boldir: 3 set × 25 marta | Press: 3 set × 20 marta",
                    technique = "Oyoq uchiga baland ko'tarilib 1 soniya to'xtang. So'ng yotgan holda kurakni poldan uzib pressni siqing",
                    targetMuscle = "Boldir va qorin yuqori qismi"
                )
            ),
            coolDown = "5 daqiqa: Oyoq mushaklarini cho'zish (hamstring & quad stretch) va dushga tayyorgarlik",
            audioCompanion = "🎧 Mashg'ulot davomida quloqda: VOA Words and Their Stories yoki BBC audio"
        ),
        SportWorkoutDay(
            id = "sport_sunday_mobility",
            daysLabel = "Yakshanba (Yengil Tiklanish)",
            title = "Moslashuvchanlik, Umurtqa Pog'onasi & Tiklanish",
            focus = "Tana bo'g'inlarini bo'shatish, charchoqni chiqarish va yangi haftaga quvvat",
            durationMinutes = 30,
            warmUp = "5 daqiqa: Chuqur diafragmal nafas olish va bo'yin-yelka aylantirish",
            exercises = listOf(
                SportExercise(
                    name = "Turnikda erkin osilib turish (Dead Hang)",
                    setsAndReps = "3-4 set × 45-60 soniya",
                    technique = "Turnikda butun tana og'irligini bo'sh qo'yib osiling. Umurtqalararo disklar ochiladi va bo'shashadi",
                    targetMuscle = "Umurtqa pog'onasi, qomat va bilak kuchi"
                ),
                SportExercise(
                    name = "Kobra va Mushuk-Sigir cho'zilishlari (Yoga)",
                    setsAndReps = "3 set × 10 marta sokin o'tishlar",
                    technique = "Nafas olganda bosh yuqoriga va bel bukiladi, nafas chiqarganda orqa gumbaz qilinadi",
                    targetMuscle = "Bel va bo'yin yengilligi"
                ),
                SportExercise(
                    name = "Toza havoda osoyishta sayr",
                    setsAndReps = "15 daqiqa sokin qadamlar",
                    technique = "Telefonsiz, toza havodan to'yib nafas olib yurish",
                    targetMuscle = "Nerv tizimini tinchlantirish va tiklanish"
                )
            ),
            coolDown = "5 daqiqa: Issiq dush va to'yimli nonushta",
            audioCompanion = "🎧 Tinchlantiruvchi inglizcha podkast yoki sokin inglizcha audio kitob"
        )
    )

    val FILM_PROGRAM = listOf(
        FilmSeriesPlan(
            weekNumber = 1,
            title = "Extra English (1–4 qismlar, YouTube)",
            episodes = "Ep 1: 'Hector's Arrival' • Ep 2: 'Hector Goes Shopping' • Ep 3: 'Hector Has a Date' • Ep 4: 'Hector Looks for a Job'",
            platform = "YouTube (qidiruv: 'Extra English with subtitles')",
            watchMethod = "Kuniga 1 qism (25 daqiqa). Faqat inglizcha subtitr bilan ko'ring. O'zbekcha subtitr man etiladi!",
            whyThisChoice = "A1-A2 daraja uchun maxsus soddalashtirilgan tilda olingan mashhur Britaniya sitkomi. Diksiya aniq, so'zlar sekin va dialoglar jonli hayotiy.",
            taskForToday = "Epizod davomida yoqqan 3 ta jonli iborani daftarga yozib olib, aktyorning intonatsiyasi bilan 3 marta takrorlang."
        ),
        FilmSeriesPlan(
            weekNumber = 2,
            title = "Extra English (5–8 qismlar, YouTube)",
            episodes = "Ep 5: 'A Star Is Born' • Ep 6: 'Well Done, Bridget!' • Ep 7: 'Archaeology' • Ep 8: 'The Love Bug'",
            platform = "YouTube (qidiruv: 'Extra English episode 5 with english subtitles')",
            watchMethod = "Kuniga 1 qism (25 daqiqa). Qahramonlarning harakatlari orqali yangi so'zlarni kontekstdan anglash.",
            whyThisChoice = "Do'kon, restoran, ish qidirish va do'stona hazillar bo'yicha eng zarur kundalik A2 leksikani beradi.",
            taskForToday = "Hector va Nick ning hazillaridagi fe'llarni ilg'ang va 2 ta gapni o'zingizcha aytib ko'ring."
        ),
        FilmSeriesPlan(
            weekNumber = 3,
            title = "Zootopia (Disney) yoki Finding Nemo (Pixar)",
            episodes = "To'liq multfilm / kino — har kuni 25-30 daqiqalik qismi (Hafta davomida to'liq tugatiladi)",
            platform = "Telegram yoki onlayn kinoteatrlar (inglizcha audio + inglizcha subtitr)",
            watchMethod = "Har kuni 25-30 daqiqa: Judy Hopps va Nick Wilde dialoglarini diqqat bilan eshitish.",
            whyThisChoice = "Animatsion filmlarda diksiya studiyada yozilgani uchun har bir tovush toza va tushunarli chiqadi. Lug'at boyligi va hissiy nutq rivojlanadi.",
            taskForToday = "Judy Hopps ning orzusi va politsiyadagi mashg'ulotlari haqidagi 3 ta yangi so'zni ilova lug'atiga qo'shing."
        ),
        FilmSeriesPlan(
            weekNumber = 4,
            title = "Friends (Do'stlar) — 1-mavsum (1–3 qismlar)",
            episodes = "Ep 1: 'The One Where Monica Gets a Roommate' • Ep 2: 'The One with the Sonogram' • Ep 3: 'The One with the Thumb'",
            platform = "Telegram yoki onlayn servislar (inglizcha audio + inglizcha subtitr)",
            watchMethod = "Kuniga 1 qism (20-22 daqiqa). Subtitr bilan ko'ring. Agar juda tez tuyulsa, 0.9x tezlikka qo'ying.",
            whyThisChoice = "Dunyodagi eng mashhur Amerika komediyasi. Haqiqiy do'stlar suhbati, tezkor savol-javoblar va jarangdor kundalik inglizcha.",
            taskForToday = "Qahramonlar kafe (Central Perk) da ishlatgan 3 ta qisqa replikasini yodlab oling."
        )
    )

    val VOCAB_PROGRAM = listOf(
        VocabPlanDetail(
            weekNumber = 1,
            sourceBook = "4000 Essential English Words (Book 1) — Unit 1–3 & Oxford 3000 (A1)",
            targetWordsCount = "1–50-so'zlar (Haftasiga 50 ta yangi so'z)",
            dailyQuota = "Har kuni 04:45 da: 7-8 ta yangi so'z + kechagi 8 ta so'zni 5 daqiqada takrorlash",
            keyWordsSample = listOf(
                "afraid (qo'rqqan)", "agree (rozi bo'lmoq)", "angry (jahldor)", "arrive (yetib kelmoq)",
                "attack (hujum qilmoq)", "bottom (tag, tub)", "clever (aqlli)", "cruel (shafqatsiz)",
                "finally (nihoyat)", "hide (yashirinmoq)", "hunt (ov qilmoq)", "lot (ko'p)",
                "middle (o'rta)", "moment (lahza)", "pleased (mamnun)", "promise (va'da bermoq)",
                "reply (javob bermoq)", "safe (xavfsiz)", "trick (hiyla)", "well (yaxshi)"
            ),
            retentionMethod = "Har bir so'z bilan 1 ta sodda shaxsiy gap tuzing va 3 marta ovoz chiqarib ayting. Ilovadagi kartochkada tekshiring."
        ),
        VocabPlanDetail(
            weekNumber = 2,
            sourceBook = "4000 Essential English Words (Book 1) — Unit 4–6 & Oxford 3000 (A1–A2)",
            targetWordsCount = "51–100-so'zlar (Haftasiga 50 ta yangi so'z)",
            dailyQuota = "Har kuni 04:45 da: 7-8 ta yangi so'z + 1-50 so'zlarni tezkor qayta varaqlash",
            keyWordsSample = listOf(
                "appropriate (munosib)", "avoid (o'zini olib qochmoq)", "behave (o'zini tutmoq)", "calm (xotirjam)",
                "concern (tashvish)", "content (mamnun)", "expect (kutmoq)", "frequently (tez-tez)",
                "habit (odat)", "instruct (ko'rsatma bermoq)", "issue (muammo)", "none (hech biri)",
                "patient (sabrli)", "positive (ijobiy)", "punish (jazolamoq)", "represent (vakillik qilmoq)",
                "shake (silkitmoq)", "spread (tarqalmoq)", "stroll (sayr qilmoq)", "village (qishloq)"
            ),
            retentionMethod = "O'tilgan so'zlarni ilovaning 'Sandiq (Vault)' bo'limiga kiritib, test savollarida tekshirib o'ting."
        ),
        VocabPlanDetail(
            weekNumber = 3,
            sourceBook = "4000 Essential English Words (Book 1) — Unit 7–10 & Zootopia lug'ati",
            targetWordsCount = "101–150-so'zlar (Haftasiga 50 ta yangi so'z)",
            dailyQuota = "Har kuni 04:45 da: 7-8 ta yangi so'z + Zootopia filmidan 3 ta yangi ibora",
            keyWordsSample = listOf(
                "aware (xabardor)", "badly (yomon tarzda)", "belong (tegishli bo'lmoq)", "continue (davom ettirmoq)",
                "error (xato)", "experience (tajriba)", "field (dala, soha)", "hurt (og'rimoq)",
                "judgment (hukm, fikr)", "likely (ehtimolli)", "normal (odatiy)", "rare (noyob)",
                "relax (dam olmoq)", "request (iltimos qilmoq)", "reside (istiqomat qilmoq)", "result (natija)",
                "roll (dumalatmoq)", "since (chunki, beri)", "visible (ko'rinib turgan)", "wild (yovvoyi)"
            ),
            retentionMethod = "Yangi so'zlarni kechki '5 Jumla' kundaligingizda ishlatishga majbur qiling — shunda so'z faol xotiraga o'tadi."
        ),
        VocabPlanDetail(
            weekNumber = 4,
            sourceBook = "4000 Essential English Words (Book 1) — Unit 11–14 & Umumiy Takrorlash",
            targetWordsCount = "151–200-so'zlar + 1–150 gacha to'liq takror",
            dailyQuota = "Har kuni 04:45 da: 7-8 ta so'z + avvalgi barcha haftalarning so'zlarini qayta tekshirish",
            keyWordsSample = listOf(
                "advantage (afzallik)", "cause (sabab bo'lmoq)", "choice (tanlov)", "community (jamoa)",
                "distance (masofa)", "escape (qochib qutulmoq)", "face (ro'para bo'lmoq)", "follow (ergashmoq)",
                "fright (qo'rquv)", "ghost (arvoh)", "individual (alohida shaxs)", "pet (uy hayvoni)",
                "reach (yetib bormoq)", "return (qaytmoq)", "survive (omon qolmoq)", "upset (xafa)",
                "voice (ovoz)", "weather (ob-havo)", "wise (dono)", "allow (ruxsat bermoq)"
            ),
            retentionMethod = "1 oylik 200 ta so'zni to'liq ilovadagi imtihon orqali sinovdan o'tkazish. 85% dan yuqori natija = A2 sertifikati."
        )
    )

    val RESOURCES = listOf(
        EnglishResourceItem(
            title = "Grammatika",
            skill = "Grammar & Structure",
            resource = "Essential Grammar in Use — Raymond Murphy (Elementary, Qizil Murphy)",
            why = "Dunyodagi eng mashhur va sinalgan qo'llanma. Har bir mavzu: chap sahifa aniq tushuntirish, o'ng sahifa amaliy mashq.",
            howToFind = "Telegramda 'Essential Grammar in Use Murphy pdf' yoki kitob do'konlaridan 'Qizil Murphy'."
        ),
        EnglishResourceItem(
            title = "Lug'at",
            skill = "Vocabulary in Context",
            resource = "4000 Essential English Words — Book 1 (Paul Nation)",
            why = "So'zlar chastotasi bo'yicha saralangan, qiziq hikoyalar va mashqlar bilan berilgan. 200 ta so'z bir oyda faol nutqingizga aylanadi.",
            howToFind = "Telegramda '4000 essential words book 1 pdf audio' deb qidiring."
        ),
        EnglishResourceItem(
            title = "Kitob o'qish (Reading)",
            skill = "Graded Readers",
            resource = "Oxford Bookworms Library: Starter ('The Elephant Man', 'Drive into Danger') → Stage 1 ('Sherlock Holmes')",
            why = "Asl adabiyot soddalashtirilgan tilda. O'qiganingiz sari grammatik qoidalar miyaga avtomatik singib boradi.",
            howToFind = "Telegramda 'Oxford Bookworms Starter pdf audio' deb qidiring."
        ),
        EnglishResourceItem(
            title = "Tinglash (Audio)",
            skill = "Listening & Pronunciation",
            resource = "BBC 6 Minute English va VOA Learning English (Words and Their Stories)",
            why = "Sekin, aniq Britaniya va Amerika talaffuzi. Har bir epizodda yangi iboralar tushuntiriladi, transkripti bilan eshitiladi.",
            howToFind = "YouTube yoki Spotify'da 'BBC 6 Minute English' deb qidiring."
        ),
        EnglishResourceItem(
            title = "Serial / Kino",
            skill = "Immersion & Natural Flow",
            resource = "Extra English (1-8 qismlar, YouTube) → Zootopia (Disney) → Friends (1-mavsum)",
            why = "Tilingizni tirik nutqqa moslashtiradi. Faqat inglizcha subtitr bilan ko'riladi.",
            howToFind = "YouTube'da 'Extra English with english subtitles episode 1' deb qidirilsa chiqadi."
        ),
        EnglishResourceItem(
            title = "Sport Dasturi",
            skill = "Body Discipline & Energy",
            resource = "Kalistenika & Kardio: Turnik, Otjimaniya, Prisidaniya, Plank, 2-3 km Yugurish",
            why = "Ertalabki 35 daqiqalik aniq mashq miyani kislorod bilan to'yintiradi, diqqatni 3 barobar oshiradi va kunlik intizomni ushlaydi.",
            howToFind = "Ilovadagi 'Sport Dasturi' bo'limida har bir mashqning aniq set va takrorlari belgilangan."
        ),
        EnglishResourceItem(
            title = "Gapirish (Speaking)",
            skill = "Active Shadowing",
            resource = "Cake ilovasi (bepul video shadowing) + Ovoz yozish",
            why = "Videodagi aktyor aytgan jumlani 3 marta baland ovozda takrorlash orqali artikulyatsiya to'g'rilanadi.",
            howToFind = "Play Market'dan 'Cake' ilovasini bepul o'rnating."
        ),
        EnglishResourceItem(
            title = "Yozish (Writing)",
            skill = "Daily Journaling",
            resource = "Ilovadagi 'Kundalik' (5 Jumla) + Grammarly tekshiruvi",
            why = "Kechqurun bugun nima qilganingiz haqida 5 ta gap yozasiz. Xatolaringizni darhol ko'rib to'g'rilaysiz.",
            howToFind = "Ilovadagi 'Kundalik' bo'limi yoki grammarly.com sayti."
        )
    )

    val SCHEDULE_MAPPINGS = listOf(
        DailyScheduleMapping("04:45 — 05:15", "📚 4000 Words & Oxford Lug'at", "4000 Essential Words — kunlik 8 ta yangi so'zni daftarga yozib, talaffuz qilib yodlash + kechagi 8 tani qaytarish"),
        DailyScheduleMapping("05:15 — 05:50", "🏃‍♂️ Sport (Turnik, Otjimaniya, Yugurish) & BBC", "Dushanba-Chorshanba-Juma: Turnik (4x8), Otjimaniya (4x20), Plank (3x60s) | Seshanba-Payshanba-Shanba: 2-3 km Yugurish, Prisidaniya (4x25), Press | Quloqda: BBC 6 Minute English"),
        DailyScheduleMapping("06:55 — 07:35", "🧩 Essential Grammar (Murphy)", "Essential Grammar in Use — kunlik aniq 1 ta Unit: chap sahifadagi qoidani o'qib, o'ng sahifadagi mashqlarni daftarga yozish"),
        DailyScheduleMapping("20:35 — 21:05 (Dars kuni)", "📖 Kitob (Oxford Bookworms)", "1-2 hafta: 'The Elephant Man' (kuniga 1 bob) | 3-4 hafta: 'Sherlock Holmes' — yangi so'zlarni ilovaga yozib borish"),
        DailyScheduleMapping("17:43 — 18:40 (Dars yo'q kun)", "🎬 Aniq Film/Serial (Extra English / Zootopia / Friends)", "1-2 hafta: Extra English (kuniga 1 qism, YouTube) | 3-hafta: Zootopia (kuniga 30 daq) | 4-hafta: Friends (1-mavsum). Ingliz subtitr bilan, 3 ta yangi iborani yozib olish"),
        DailyScheduleMapping("15:30 — 16:15 (Dars yo'q kun)", "🎙️ Cake Shadowing & Speaking", "Cake ilovasida 3 ta qisqa video orqali gaplarni aktyor bilan teng takrorlash (Shadowing)"),
        DailyScheduleMapping("20:35 — 21:05 (Dars yo'q kun)", "✍️ Yozish (5 Jumla Kundalik)", "Bugungi kuningiz haqida Past Simple zamonida 5 ta to'liq gap yozish va xatolarni tekshirish"),
        DailyScheduleMapping("19:40 — 20:00", "📱 IBRAT Academy", "Navbatdagi video darsni ko'rib, testlarini ishlash — darslarni mustahkamlash")
    )

    val WEEKS = listOf(
        WeekPlan(
            weekNumber = 1,
            title = "1-hafta — Mustahkam Asos",
            subtitle = "Present Simple & Continuous, 4000 Words 1–50, Extra English 1–4, Turnik & Otjimaniya",
            focus = "Tizimli intizom o'rnatish, hozirgi zamon qoidalari, kunlik 8 tadan yangi so'z, 'The Elephant Man' 1-kitob va 'Extra English' sitkomi",
            tasks = listOf(
                EnglishPlanTask("w1_gram", 1, "Grammatika", "Essential Grammar in Use — Unit 1–15", "Present Simple va Present Continuous mavzulari (har kuni 1 unit qoida + 1 unit mashq daftariga yoziladi)"),
                EnglishPlanTask("w1_vocab", 1, "Lug'at", "4000 Words (1-kitob) — Unit 1–3 (1–50-so'zlar)", "Har kuni 04:45 da 7-8 ta so'zni ovoz chiqarib o'qish, misol tuzish va kechagi so'zlarni 5 daqiqada qaytarish"),
                EnglishPlanTask("w1_sport", 1, "Sport", "Aniq Sport Dasturi: Turnik (4x8) & Otjimaniya (4x20) & Yugurish", "Dush/Chor/Juma: Turnik, Otjimaniya, Plank | Sesh/Pay/Shan: 2-3 km Yugurish, Prisidaniya (4x25), Press. Quloqda BBC audio"),
                EnglishPlanTask("w1_book", 1, "Kitob", "Oxford Bookworms Starter — 'The Elephant Man' (Tim Vicary)", "Har kuni 1 ta bob (chapter) mutolaa qilinadi, tushunarsiz so'zlar ilova lug'atiga qo'shiladi"),
                EnglishPlanTask("w1_film", 1, "Serial", "Extra English — 1–4 qismlar (YouTube)", "Kuniga 1 qism (25 daqiqa) inglizcha subtitr bilan. Hector va Bridget dialoglaridan 3 ta iborani yozib olish"),
                EnglishPlanTask("w1_audio", 1, "Audio", "BBC 6 Minute English — 3 ta epizod", "Sekin tinglab, transkripti bilan birgalikda kuzatib borish"),
                EnglishPlanTask("w1_speak", 1, "Gapirish", "Cake — kuniga 3 ta video shadowing", "Video ichidagi jumlalarni ovoz chiqarib aynan takrorlash"),
                EnglishPlanTask("w1_write", 1, "Yozish", "Kundalik — har kuni 5 ta to'liq jumla", "Kechqurun bugungi kuningiz haqida 5 gap yozib, xatolarni tahlil qilish")
            )
        ),
        WeekPlan(
            weekNumber = 2,
            title = "2-hafta — O'tmish Zamon & Jonli Dialoglar",
            subtitle = "Past Simple & Continuous, 4000 Words 51–100, Extra English 5–8, Kardio & Kuch",
            focus = "O'tgan zamon fe'llari (to'g'ri va noto'g'ri fe'llar), 51-100 so'zlar, Bookworms Starter 2-kitob va jismoniy chidamlilik",
            tasks = listOf(
                EnglishPlanTask("w2_gram", 2, "Grammatika", "Essential Grammar in Use — Unit 16–30", "Past Simple/Continuous zamonlari (was/were, regular & irregular verbs, questions & negatives)"),
                EnglishPlanTask("w2_vocab", 2, "Lug'at", "4000 Words (1-kitob) — Unit 4–6 (51–100-so'zlar)", "Yangi 50 ta so'z + avvalgi 1-50 so'zlarni ilovaning 'Sandiq' bo'limida takrorlash"),
                EnglishPlanTask("w2_sport", 2, "Sport", "Intensiv Sport: Turnik (4x10), Otjimaniya (4x25), 3 km Yugurish", "Har tong 05:15 da sport rejimiga qat'iy rioya qilish va VOA audio podkastini eshitish"),
                EnglishPlanTask("w2_book", 2, "Kitob", "Bookworms Starter — 'Drive into Danger' (Rosemary Border)", "2-kitob boshlanadi: har kuni 1 bob, voqealar rivojini kuzatish"),
                EnglishPlanTask("w2_film", 2, "Serial", "Extra English — 5–8 qismlar (YouTube)", "Subtitr bilan ko'rish, kundalik hayotdagi hazillar va qahramonlar replikalarini tushunish"),
                EnglishPlanTask("w2_audio", 2, "Audio", "VOA — Words and Their Stories, 3 ta epizod", "Amerika ingliz tili iboralari va qiziqarli so'zlar tarixi"),
                EnglishPlanTask("w2_speak", 2, "Gapirish", "Cake davom + IBRAT ilovasidagi suhbat mashqlari", "Suhbat mashqlari orqali nutqni erkinlashtirish"),
                EnglishPlanTask("w2_write", 2, "Yozish", "Kundalik — o'tgan zamonda 5 ta jumla", "Kecha va bugun nima bo'lganini faqat Past Simple'da yozish")
            )
        ),
        WeekPlan(
            weekNumber = 3,
            title = "3-hafta — Kelasi Zamon & Katta Ekran",
            subtitle = "Future, Modal fe'llar, 4000 Words 101–150, Zootopia/Nemo filmi, Bookworms Stage 1",
            focus = "Kelasi zamon rejalari (will, going to), modal fe'llar (can, must, should), Zootopia filmi va Stage 1 darajasidagi mutolaa",
            tasks = listOf(
                EnglishPlanTask("w3_gram", 3, "Grammatika", "Essential Grammar in Use — Unit 31–45", "Future (will, going to), can, could, must, should modal fe'llari"),
                EnglishPlanTask("w3_vocab", 3, "Lug'at", "4000 Words (1-kitob) — Unit 7–10 (101–150-so'zlar)", "So'zlarni shaxsiy misollar bilan mustahkamlash + Zootopia yangi so'zlari"),
                EnglishPlanTask("w3_sport", 3, "Sport", "Kuch & Kardio uyg'unligi: Turnik (4x10), Prisidaniya (4x25), Plank (3x60s)", "Intizomli ertalabki sport + BBC 6 Minute English (transkriptsiz tinglash)"),
                EnglishPlanTask("w3_book", 3, "Kitob", "Oxford Bookworms Stage 1 — 'Sherlock Holmes: Short Stories'", "Stage 1 darajaga o'tish: detektiv hikoyalarni o'qib, voqealarni tushunish"),
                EnglishPlanTask("w3_film", 3, "Kino", "Zootopia (Hayvonlar shahri) — har kuni 25-30 daqiqa", "Disney filmini ingliz subtitr bilan ko'rish. Judy va Nick nutqidagi iboralarni o'rganish"),
                EnglishPlanTask("w3_audio", 3, "Audio", "6 Minute English davom (transkriptsiz eshitish)", "Endi audioni matnga qaramay, to'g'ridan-to'g'ri quloq orqali ilg'ash"),
                EnglishPlanTask("w3_speak", 3, "Gapirish", "1 daqiqalik o'zingiz haqingizda video/audio yozish", "Yozuvsiz, qog'ozga qaramasdan, erkin ovoz chiqarib gapirish"),
                EnglishPlanTask("w3_write", 3, "Yozish", "Kelasi zamon rejalari (5 ta jumla)", "Kelajakdagi maqsadlaringiz haqida 'will' va 'going to' bilan yozish")
            )
        ),
        WeekPlan(
            weekNumber = 4,
            title = "4-hafta — Mustahkamlash & Haqiqiy Serial",
            subtitle = "Comparatives, 4000 Words 151–200, Friends seriali (1-mavsum), Yakuniy sarhisob",
            focus = "Sifat darajalari, predloglar, 1-45 unitlarni umumiy takrorlash, Friends seriali va 200 ta so'z bo'yicha yakuniy imtihon",
            tasks = listOf(
                EnglishPlanTask("w4_gram", 4, "Grammatika", "Unit 46–60 + 1–45 ni tezkor takrorlash", "Comparatives, superlatives, prepositions va o'tilgan 60 ta unit yakuni"),
                EnglishPlanTask("w4_vocab", 4, "Lug'at", "4000 Words: 151–200 + avvalgi 150 tani takrorlash", "Ilovaning 'Sandiq' bo'limi va test funksiyasi orqali 200 ta so'zdan yakuniy sinov"),
                EnglishPlanTask("w4_sport", 4, "Sport", "O'z rekordlaringizni yangilang: Turnik + Otjimaniya + 3 km yugurish", "O'tgan 3 haftadagi jismoniy o'sishni sarhisob qilish va yangi darajaga chiqish"),
                EnglishPlanTask("w4_book", 4, "Kitob", "Bookworms Stage 1 — 2-to'liq hikoyani yakunlash", "Stage 1 darajasidagi ikkinchi to'liq asar mutolaasini tugatish"),
                EnglishPlanTask("w4_film", 4, "Serial", "Friends (Do'stlar) — 1-mavsum (1–3 qismlar)", "Haqiqiy jonli Amerika sitkomi tajribasi, kundalik replikalar va do'stona iboralar"),
                EnglishPlanTask("w4_audio", 4, "Audio", "Tanlangan podkastdan 4–5 epizod", "Endi tabiiy va tezroq gapirish ritmiga to'liq ko'nikish"),
                EnglishPlanTask("w4_speak", 4, "Gapirish", "O'zingiz haqingizda 2 daqiqalik video/audio yozish", "Hech qanday notasiz, erkin inglizcha fikr bayon qilish"),
                EnglishPlanTask("w4_review", 4, "Yakuniy xulosa", "1 oylik natijalarni sarhisob qilish & A2 sertifikati", "Elementar (A2) darajaga erishilganini baholash va 2-oylik rejaga o'tish")
            )
        )
    )

    fun getAllTasks(): List<EnglishPlanTask> {
        return WEEKS.flatMap { it.tasks }
    }
}
