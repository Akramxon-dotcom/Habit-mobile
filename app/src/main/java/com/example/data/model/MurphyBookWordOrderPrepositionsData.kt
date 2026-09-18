package com.example.data.model

object MurphyBookWordOrderPrepositionsData {

    val UNITS_WORD_ORDER_PREPOSITIONS: List<MurphyUnit> = listOf(
        // UNIT 81
        MurphyUnit(
            unitNumber = 81,
            title = "Word order 1 (He speaks English very well / always, usually, often etc.)",
            subtitleUzbek = "Gapda so'z tartibi: Fe'l + to'ldiruvchi va payt/joy ravishlari o'rni",
            groupName = "14-Guruh: Word Order & Conjunctions (81–86)",
            keyTakeawaysUzbek = listOf(
                "OLTIN QOIDA: Ingliz tilida Fe'l (Verb) bilan To'ldiruvchi (Object) bir-biridan HECH QACHON ajralmaydi! 'He speaks English very well' (He speaks very well English deb aytib bo'lmaydi!).",
                "O'rin-joy (Where) va Vaqt (When) ketma-ket kelsa, DOIM birinchi O'RIN-JOY, keyin esa VAQT aytiladi: Joy + Vaqt ('to school on Monday').",
                "Takroriylik ravishlari (always, usually, often, never, sometimes): Asosiy fe'ldan OLDIN, lekin 'am/is/are/was/were' fe'lidan KEYIN qo'yiladi.",
                "Ko'makchi fe'llar (can, will, have, do) qatnashganda: always/never yordamchi va asosiy fe'l orasiga tushadi: 'I will always remember you'.",
                "Juda sodda qilib aytganda: Kim? + Nima qildi? + Kimni/Nimani? + Qayerda? + Qachon? ketma-ketligi buzilmasligi kerak!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Fe'l va To'ldiruvchi doim birga (Verb + Object)",
                    formula = "Ega (Subject) + Fe'l (Verb) + To'ldiruvchi (Object) + Boshqa so'zlar",
                    explanationUzbek = "O'zbek tilida 'Men inglizchani juda yaxshi gapiraman' deb, 'inglizchani' so'zini oldinga qo'yamiz. Lekin ingliz tilida kim nima qilayotgani va kimga/nimaga ta'sir qilayotgani birga turishi SHART:\n\nTo'g'ri: I like Italian food very much.\nNoto'g'ri: I like very much Italian food.\n\nTo'g'ri: Did you see David yesterday?\nNoto'g'ri: Did you see yesterday David?\n\nKo'rib turganingizdek, 'like' va 'food', 'see' va 'David' so'zlari orasiga hech narsa kiritib bo'lmaydi.",
                    examples = listOf(
                        MurphyExample("He speaks English very well.", "U ingliz tilida juda yaxshi gapiradi.", "speaks + English (orasiga 'very well' tushmaydi)"),
                        MurphyExample("I lost my keys yesterday.", "Men kecha kalitlarimni yo'qotib qo'ydim.", "lost + my keys (kecha so'zi oxirida)"),
                        MurphyExample("We enjoyed the party very much.", "Biz ziyofatdan juda ham zavqlandik.", "enjoyed + the party")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Joy (Where) va Vaqt (When) ketma-ketligi",
                    formula = "Joy (Qayerga/Qayerda?) + Vaqt (Qachon?)",
                    explanationUzbek = "Agar gapda ham qayerga/qayerda ekanligi, ham qachon bo'lganligi aytilsa, inglizlar doim birinchi BO'LIB O'RIN-JOYNI, oxirida esa VAQTNI aytishadi:\n\n• We went to the cinema (joy) on Sunday (vaqt).\n(Biz yakshanba kuni kinoga bordik).\n• She walked home (joy) last night (vaqt).\n• They have lived in London (joy) since 2015 (vaqt).\n\nEslab qoling: O'zbekchada 'Kechqurun mehmonga boramiz' (Vaqt+Joy) desak ham bo'ladi, lekin ingliz tilida faqat 'Mehmonga + kechqurun' shaklida aytiladi.",
                    examples = listOf(
                        MurphyExample("Tom arrived at the airport early.", "Tom aeroportga vaqtli yetib keldi.", "airport (joy) + early (vaqt)"),
                        MurphyExample("I will meet you at the station at 5 o'clock.", "Men siz bilan soat 5 da vokzalda uchrashaman.", "station (joy) + at 5 o'clock (vaqt)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Always, usually, often, never so'zlarining o'rni",
                    formula = "Fe'ldan OLDIN | BE (am/is/are) dan KEYIN",
                    explanationUzbek = "Bu so'zlar ish-harakat qanchalik tez-tez bo'lib turishini bildiradi:\n\n1) Oddiy fe'llar bilan — fe'ldan OLDIN keladi:\n• I always drink coffee in the morning. (Men doim ertalab kofe ichaman)\n• He never tells lies. (U hech qachon yolg'on gapirmaydi)\n• We often play football. (Biz tez-tez futbol o'ynaymiz)\n\n2) AM / IS / ARE / WAS / WERE bilan — ulardan KEYIN keladi:\n• She is always late. (U doim kechikadi — 'She always is late' EMAS!)\n• They were never happy. (Ular hech qachon baxtli bo'lishmagan)\n\n3) Ikki fe'l bo'lsa (can/will/have + fe'l) — ikkalasining O'RTASIGA tushadi:\n• I can never remember her name. (Men uning ismini hech eslay olmayman)\n• Have you ever been to Rome?",
                    examples = listOf(
                        MurphyExample("My brother always forgets his keys.", "Akam doim kalitlarini unutib qoldiradi.", "always + forgets (fe'ldan oldin)"),
                        MurphyExample("You are always so cheerful!", "Sen doim shunchalar xushchaqchaqsan!", "are + always (BE dan keyin)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u81_ex1",
                    exerciseNumber = "81.1",
                    taskType = "CHOICE",
                    question = "Qaysi gapda so'z tartibi 100% to'g'ri tuzilgan?",
                    options = listOf(
                        "He speaks very well English.",
                        "He speaks English very well.",
                        "He very well speaks English.",
                        "English speaks he very well."
                    ),
                    correctOptionIndex = 1,
                    correctAnswerText = "He speaks English very well.",
                    explanationUzbek = "Fe'l (speaks) va to'ldiruvchi (English) ajralmaydi: 'speaks English very well'."
                ),
                MurphyExerciseItem(
                    id = "u81_ex2",
                    exerciseNumber = "81.2",
                    taskType = "CHOICE",
                    question = "Nuqtalar o'rniga to'g'ri ketma-ketlikni tanlang: 'We went ______.'",
                    options = listOf(
                        "to London last week",
                        "last week to London",
                        "yesterday in London",
                        "last week in London"
                    ),
                    correctOptionIndex = 0,
                    correctAnswerText = "to London last week",
                    explanationUzbek = "Oltin qoida: Avval JOY ('to London'), keyin VAQT ('last week') keladi."
                ),
                MurphyExerciseItem(
                    id = "u81_ex3",
                    exerciseNumber = "81.3",
                    taskType = "CHOICE",
                    question = "Jane ______ for her morning classes.",
                    options = listOf("is always late", "always is late", "late is always", "always late is"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is always late",
                    explanationUzbek = "'am/is/are' fe'lidan keyin ravish qo'yiladi: 'is always late'."
                ),
                MurphyExerciseItem(
                    id = "u81_ex4",
                    exerciseNumber = "81.4",
                    taskType = "CHOICE",
                    question = "I ______ my grandmother at weekends.",
                    options = listOf("usually visit", "visit usually", "am usually visiting to", "usually visited to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "usually visit",
                    explanationUzbek = "Oddiy fe'llardan (visit) oldin 'usually' qo'yiladi: 'usually visit'."
                ),
                MurphyExerciseItem(
                    id = "u81_ex5",
                    exerciseNumber = "81.5",
                    taskType = "CHOICE",
                    question = "He has ______ to Paris.",
                    options = listOf("never been", "been never", "never be", "was never"),
                    correctOptionIndex = 0,
                    correctAnswerText = "never been",
                    explanationUzbek = "Yordamchi fe'l (has) va asosiy fe'l (been) orasiga tushadi: 'has never been'."
                )
            )
        ),

        // UNIT 82
        MurphyUnit(
            unitNumber = 82,
            title = "still, yet, already (give me that book / give it to me)",
            subtitleUzbek = "'Hali ham' (still), 'hali' (yet) va 'allaqachon' (already)",
            groupName = "14-Guruh: Word Order & Conjunctions (81–86)",
            keyTakeawaysUzbek = listOf(
                "STILL = 'Hali ham / hamon'. Kutilganidan ko'ra uzoq davom etayotgan ish: He is still sleeping (U hali ham uxlayapti).",
                "YET = 'Hali / hali ham'. Faqat INKOR (-) va SO'ROQ (?) gaplarda, gapning eng OXIRIDA keladi: The train hasn't arrived yet (Poyezd hali kelmadi).",
                "ALREADY = 'Allaqachon'. Kutilganidan oldinroq sodir bo'lgan ish: I have already finished my homework (Men allaqachon vazifamni qilib bo'ldim).",
                "IKKITA TO'LDIRUVCHI: Agar narsa olmosh bo'lsa (it, them) -> 'Give it to me' (Give me it deb aytilmaydi!). Ot bo'lsa: 'Give me the book' = 'Give the book to me'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Still, Yet va Already farqlari",
                    formula = "still (fe'ldan oldin) | yet (gap oxirida) | already (fe'llar orasida)",
                    explanationUzbek = "1) STILL (Hamon, hali ham):\nIsh-harakat hozir ham to'xtamaganini bildiradi. Odatda darak gapda fe'ldan oldin turadi:\n• It is 10 o'clock and he is still in bed. (Soat 10 bo'ldi, u hali ham to'shakda)\n• Do you still live in Tashkent? (Hali ham Toshkentda yashaysizmi?)\n\n2) YET (Hali):\nKutilayotgan ish ro'y bermaganini bildiradi. DOIM gapning oxiriga qo'yiladi:\n• Inkor: I haven't had breakfast yet. (Men hali nonushta qilmadim)\n• So'roq: Has it stopped raining yet? (Yomg'ir yog'ishdan to'xtadimi o'zi?)\n\n3) ALREADY (Allaqachon):\nIsh kutilganidan erta bitganida aytiladi:\n• What time is Mark coming? — He is already here! (U allaqachon shu yerda)\n• I've already seen this film. (Bu filmni allaqachon ko'rganman)",
                    examples = listOf(
                        MurphyExample("She hasn't called me yet.", "U hali menga qo'ng'iroq qilmadi.", "yet (oxirida va inkor gapda)"),
                        MurphyExample("I am still waiting for the bus.", "Men hali ham avtobus kutyapman.", "still waiting"),
                        MurphyExample("Don't tell him, he already knows.", "Unga aytmang, u allaqachon biladi.", "already knows")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Give me that book vs Give it to me (Ikki to'ldiruvchi)",
                    formula = "give + KIMGA + NIMA | give + NIMA + TO + KIMGA",
                    explanationUzbek = "Birovga biror narsani berish, ko'rsatish, yuborish (give, send, show, lend, buy):\n\n1-usul (predlogsiz): Fe'l + Shaxs + Narsa\n• Give me the keys. (Menga kalitlarni ber)\n• She sent him a message. (U unga xabar yubordi)\n\n2-usul (TO / FOR predlogi bilan): Fe'l + Narsa + TO + Shaxs\n• Give the keys to me. (Kalitlarni menga ber)\n• She sent a message to him.\n\n⚠️ MUHIM QOIDA: Agar berilayotgan narsa 'it' yoki 'them' olmoshi bo'lsa, FAQAT 2-usul ishlatiladi:\n• Give it to me! (To'g'ri)\n• Give me it! (Noto'g'ri!)",
                    examples = listOf(
                        MurphyExample("Can you pass me the salt?", "Tuzni uzatib yubora olasizmi?", "pass + me + the salt"),
                        MurphyExample("I have your book. I'll give it to you tomorrow.", "Kitobingiz menda. Ertaga uni sizga beraman.", "give it to you")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u82_ex1",
                    exerciseNumber = "82.1",
                    taskType = "CHOICE",
                    question = "Has the postman come ______?",
                    options = listOf("yet", "still", "already", "ever"),
                    correctOptionIndex = 0,
                    correctAnswerText = "yet",
                    explanationUzbek = "So'roq gap oxirida kutilayotgan harakat uchun 'yet' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u82_ex2",
                    exerciseNumber = "82.2",
                    taskType = "CHOICE",
                    question = "I'm not hungry. I have ______ eaten lunch.",
                    options = listOf("already", "yet", "still", "any"),
                    correctOptionIndex = 0,
                    correctAnswerText = "already",
                    explanationUzbek = "Allaqachon tushlik qilib bo'lganlikni bildirish uchun 'already' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u82_ex3",
                    exerciseNumber = "82.3",
                    taskType = "CHOICE",
                    question = "It's midnight, but he is ______ working on his computer.",
                    options = listOf("still", "yet", "already", "never"),
                    correctOptionIndex = 0,
                    correctAnswerText = "still",
                    explanationUzbek = "Hali ham davom etayotgan harakat uchun 'still' keladi."
                ),
                MurphyExerciseItem(
                    id = "u82_ex4",
                    exerciseNumber = "82.4",
                    taskType = "CHOICE",
                    question = "These flowers are beautiful. Please ______.",
                    options = listOf("give them to her", "give her them", "give to her them", "them give to her"),
                    correctOptionIndex = 0,
                    correctAnswerText = "give them to her",
                    explanationUzbek = "'them' olmoshi bilan 'give them to her' shakli to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u82_ex5",
                    exerciseNumber = "82.5",
                    taskType = "CHOICE",
                    question = "I haven't finished reading the newspaper ______.",
                    options = listOf("yet", "already", "still", "ago"),
                    correctOptionIndex = 0,
                    correctAnswerText = "yet",
                    explanationUzbek = "Inkor gap oxirida 'hali' ma'nosida 'yet' qo'yiladi."
                )
            )
        ),

        // UNIT 83
        MurphyUnit(
            unitNumber = 83,
            title = "at 8 o'clock / on Monday / in April (Prepositions of time 1)",
            subtitleUzbek = "Vaqt predloglari: AT, ON va IN (soatlar, kunlar, fasl va yillar)",
            groupName = "14-Guruh: Word Order & Conjunctions (81–86)",
            keyTakeawaysUzbek = listOf(
                "AT = Aniq soatlar va aniq nuqtalar: at 5 o'clock, at midnight, at night, at lunchtime, at the weekend.",
                "ON = KUNLAR va SANALAR: on Monday, on Friday evening, on 15 March, on my birthday, on Christmas Day.",
                "IN = KATTA VAQT ORALIG'I (oylar, fasllar, yillar, asrlar): in April, in summer, in 2024, in the 21st century.",
                "KUNNING QISMLARI: in the morning, in the afternoon, in the evening — LEKIN: at night!",
                "MUHIM: this, last, next, every so'zlaridan oldin AT, ON, IN UMUMAN QO'YILMAYDI! (next Monday, last night, this morning)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "AT, ON, IN piramidasi (Aniqdan umumiyga)",
                    formula = "AT (soatlar) -> ON (kunlar) -> IN (oylar/yillar)",
                    explanationUzbek = "Buni oson eslab qolish yo'li — piramida qoidasi:\n\n1) AT (Eng aniq, tor vaqtlar):\n• at 8:30 (soat 8:30 da)\n• at midnight (yarim tunda)\n• at lunchtime (tushlik paytida)\n• at night (tunda)\n• at the weekend (dam olish kunlarida)\n\n2) ON (Bir kunlik vaqtlar, kalendardagi bitta katak):\n• on Sunday (yakshanba kuni)\n• on Mondays (dushanba kunlari)\n• on 25 May (25-mayda)\n• on New Year's Day (Yangi yil kuni)\n• on my birthday (tug'ilgan kunimda)\n\n3) IN (Uzoq vaqt oralig'i, hafta, oy, yil):\n• in October (oktabrda)\n• in winter (qishda)\n• in 2025 (2025-yilda)\n• in the morning / afternoon / evening",
                    examples = listOf(
                        MurphyExample("The movie starts at 7:30.", "Film soat 7:30 da boshlanadi.", "at + soat"),
                        MurphyExample("I will see you on Saturday.", "Shanba kuni ko'rishamiz.", "on + hafta kuni"),
                        MurphyExample("My sister was born in 2002.", "Singlim 2002-yilda tug'ilgan.", "in + yil")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Predlog ishlatilmaydigan holatlar va IN (vaqt ichida)",
                    formula = "this / next / last / every + vaqt (PREDLOGSIZ!)",
                    explanationUzbek = "O'quvchilar eng ko'p qiladigan xato — 'on next Monday' yoki 'in last year' deb yozishdir. \n\nQuyidagi so'zlar kelsa, hech qanday AT, ON, IN kerak emas:\n• this morning (bugun ertalab)\n• next week (keyingi hafta)\n• last year (o'tgan yili)\n• every day (har kuni)\n\nIN ning ikkinchi ma'nosi: '...dan keyin / ...vaqt ichida':\n• in five minutes (besh daqiqadan keyin / besh daqiqa ichida)\n• in two weeks (ikki haftadan keyin)",
                    examples = listOf(
                        MurphyExample("We are travelling to Samarkand next month.", "Biz keyingi oyda Samarqandga boramiz.", "next month (predlogsiz!)"),
                        MurphyExample("I will be ready in ten minutes.", "Men o'n daqiqada tayyor bo'laman.", "in ten minutes (vaqt ichida)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u83_ex1",
                    exerciseNumber = "83.1",
                    taskType = "CHOICE",
                    question = "My English lesson starts ______ 9 o'clock in the morning.",
                    options = listOf("at", "on", "in", "to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "at",
                    explanationUzbek = "Aniq soat ko'rsatilganda 'at 9 o'clock' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u83_ex2",
                    exerciseNumber = "83.2",
                    taskType = "CHOICE",
                    question = "We always visit our grandparents ______ Sundays.",
                    options = listOf("on", "in", "at", "with"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Hafta kunlari bilan 'on Sundays' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u83_ex3",
                    exerciseNumber = "83.3",
                    taskType = "CHOICE",
                    question = "Leaves fall from trees ______ autumn.",
                    options = listOf("in", "on", "at", "for"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in",
                    explanationUzbek = "Fasllar (autumn, winter, spring, summer) oldidan 'in' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u83_ex4",
                    exerciseNumber = "83.4",
                    taskType = "CHOICE",
                    question = "I will call you ______ next Friday.",
                    options = listOf("— (hech qanday predlog)", "on", "at", "in"),
                    correctOptionIndex = 0,
                    correctAnswerText = "— (hech qanday predlog)",
                    explanationUzbek = "'next', 'last', 'this' so'zlari oldidan predlog ishlatilmaydi!"
                ),
                MurphyExerciseItem(
                    id = "u83_ex5",
                    exerciseNumber = "83.5",
                    taskType = "CHOICE",
                    question = "The train leaves ______ five minutes. Hurry up!",
                    options = listOf("in", "at", "on", "by"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in",
                    explanationUzbek = "'in five minutes' - besh daqiqadan keyin degan ma'noni beradi."
                )
            )
        ),

        // UNIT 84
        MurphyUnit(
            unitNumber = 84,
            title = "from ... to, until, since, for (Prepositions of time 2)",
            subtitleUzbek = "Vaqt oraliqlari: ...dan ...gacha (until), ...dan beri (since), ...davomida (for)",
            groupName = "14-Guruh: Word Order & Conjunctions (81–86)",
            keyTakeawaysUzbek = listOf(
                "FROM ... TO ... = '...dan ...gacha': from 9 to 5, from Monday to Friday.",
                "UNTIL (yoki TILL) = '...gacha'. Harakat shu vaqtgacha davom etadi va to'xtaydi: Wait until tomorrow (Ertagacha kuting).",
                "SINCE = '...dan beri'. Harakat o'tmishdagi ANIQ bir paytda boshlangan va hozir ham davom etmoqda (Present Perfect bilan): since Monday, since 2010.",
                "FOR = '...davomida / mobaynida'. Vaqtning umumiy UZUNLIGINI bildiradi: for two hours, for three days, for ten years.",
                "ENG KATTA FARQ: since + boshlanish nuqtasi (since 2 o'clock) vs for + davomiylik (for two hours)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "From ... to va Until (gacha)",
                    formula = "from ... to ... | until + vaqt nuqtasi",
                    explanationUzbek = "1) FROM ... TO ... (Boshlanishi va tugashi ma'lum bo'lsa):\n• I work from Monday to Friday. (Men dushanbadan jumagacha ishlayman)\n• We lived in Bukhara from 2010 to 2018.\n\n2) UNTIL / TILL (Biror vaqtgacha):\n• Let's wait until the rain stops. (Yomg'ir to'xtaguncha kutaylik)\n• I stayed in bed until 10 o'clock. (Soat 10 gacha o'rinda yotdim)\n• You can't leave until you finish. (Tugamaguningizcha keta olmaysiz)",
                    examples = listOf(
                        MurphyExample("The shop is open from 8 am to 9 pm.", "Do'kon ertalab 8 dan kechki 9 gacha ochiq.", "from ... to ..."),
                        MurphyExample("Goodbye, see you until Friday!", "Xayr, jumagacha!", "until Friday")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Since (dan beri) vs For (davomida)",
                    formula = "SINCE + Boshlanish nuqtasi | FOR + Vaqt miqdori",
                    explanationUzbek = "Bu ikkisini solishtirish juda oson:\n\n1) SINCE (Ish qachon boshlangan?):\n• since Monday (dushanbadan beri)\n• since 9 o'clock (soat 9 dan beri)\n• since I was a child (bolaligimdan beri)\nMisol: I have lived here since 2015. (2015-yildan beri shu yerda yashayman)\n\n2) FOR (Ish qancha vaqt davom etdi?):\n• for two hours (ikki soat davomida)\n• for three days (uch kun davomida)\n• for ten years (o'n yil davomida)\nMisol: I have lived here for 10 years. (10 yil davomida shu yerda yashaganman)",
                    examples = listOf(
                        MurphyExample("It has been raining since yesterday.", "Kechaning o'zidan beri yomg'ir yog'yapti.", "since yesterday"),
                        MurphyExample("They walked in the park for two hours.", "Ular parkda ikki soat davomida sayr qilishdi.", "for two hours")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u84_ex1",
                    exerciseNumber = "84.1",
                    taskType = "CHOICE",
                    question = "I have been waiting for you ______ 8 o'clock!",
                    options = listOf("since", "for", "until", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "since",
                    explanationUzbek = "'8 o'clock' aniq boshlanish vaqti bo'lgani uchun 'since' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u84_ex2",
                    exerciseNumber = "84.2",
                    taskType = "CHOICE",
                    question = "We watched television ______ three hours last night.",
                    options = listOf("for", "since", "until", "during"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "'three hours' vaqt davomiyligi (uzunligi), shuning uchun 'for' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u84_ex3",
                    exerciseNumber = "84.3",
                    taskType = "CHOICE",
                    question = "Don't open the door ______ the bus stops completely.",
                    options = listOf("until", "since", "for", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "until",
                    explanationUzbek = "Avtobus to'xtaguncha: 'until the bus stops'."
                ),
                MurphyExerciseItem(
                    id = "u84_ex4",
                    exerciseNumber = "84.4",
                    taskType = "CHOICE",
                    question = "He worked in this company ______ 2018 to 2022.",
                    options = listOf("from", "since", "until", "for"),
                    correctOptionIndex = 0,
                    correctAnswerText = "from",
                    explanationUzbek = "'from ... to ...' juftligi: 2018-yildan 2022-yilgacha."
                ),
                MurphyExerciseItem(
                    id = "u84_ex5",
                    exerciseNumber = "84.5",
                    taskType = "CHOICE",
                    question = "She has known her best friend ______ ten years.",
                    options = listOf("for", "since", "until", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "O'n yil mobaynida: 'for ten years'."
                )
            )
        ),

        // UNIT 85
        MurphyUnit(
            unitNumber = 85,
            title = "before, after, during, while",
            subtitleUzbek = "'Oldin' (before), 'keyin' (after), 'davomida' (during/while)",
            groupName = "14-Guruh: Word Order & Conjunctions (81–86)",
            keyTakeawaysUzbek = listOf(
                "BEFORE = 'Oldin': before breakfast (nonushtadan oldin), before going out.",
                "AFTER = 'Keyin': after the film (filmdan keyin), after doing homework.",
                "DURING = 'Davomida / paytida'. DOIM OT (Noun) bilan keladi: during the film, during our vacation, during the night.",
                "WHILE = 'Davomida / ayni paytda'. DOIM GAP (Ega + Fe'l) bilan keladi: while I was watching TV, while you were sleeping.",
                "ENG MUHIM FARQ: during + Ot (during the lesson) vs while + Gap (while we were studying)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Before va After ning qo'llanishi",
                    formula = "before / after + Ot YOKI Fe'l-ING",
                    explanationUzbek = "1) Otlar bilan:\n• Wash your hands before dinner. (Kechki ovqatdan oldin qo'lingizni yuving)\n• We went home after the match. (O'yindan keyin uyga ketdik)\n\n2) Fe'llar bilan (Doim -ING shaklida bo'ladi):\n• Check your answers before submitting the test. (Topshirishdan oldin javoblaringizni tekshiring)\n• After having a shower, he went to bed. (Dush qabul qilgandan keyin uxlashga yotdi)",
                    examples = listOf(
                        MurphyExample("Always lock the door before leaving.", "Ketishdan oldin doim eshikni qulflang.", "before + leaving (-ing)"),
                        MurphyExample("We had coffee after lunch.", "Tushlikdan keyin kofe ichdik.", "after + lunch")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "During vs While (Ikkalasi ham 'davomida' degani)",
                    formula = "DURING + Ot | WHILE + Ega + Fe'l",
                    explanationUzbek = "Bu ikki so'z ma'nodosh, lekin grammatikasi butunlay boshqa:\n\n1) DURING + OT (hech qanday fe'l qatnashmaydi!):\n• I fell asleep during the film. (Film paytida uxlab qoldim)\n• We met many friends during our holiday. (Ta'til davomida ko'p do'stlarni uchratdik)\n\n2) WHILE + GAP (kimdir nimanidir qilayotgan bo'ladi):\n• I fell asleep while I was watching the film. (Film ko'rayotganimda uxlab qoldim)\n• The phone rang while we were eating dinner. (Kechki ovqat yeyayotganimizda telefon jiringladi)\n\nKo'rdingizmi? Film — bu ot (during), lekin 'I was watching' — bu harakat (while)!",
                    examples = listOf(
                        MurphyExample("It rained heavily during the night.", "Kechasi bilan yomg'ir qattiq yog'di.", "during + the night (ot)"),
                        MurphyExample("Somebody stole his bag while he was buying a ticket.", "Chipta sotib olayotganida kimdir uning sumkasini o'g'irlab ketdi.", "while + he was buying (gap)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u85_ex1",
                    exerciseNumber = "85.1",
                    taskType = "CHOICE",
                    question = "Nobody spoke ______ the examination.",
                    options = listOf("during", "while", "for", "since"),
                    correctOptionIndex = 0,
                    correctAnswerText = "during",
                    explanationUzbek = "'the examination' bu ot (ism), shuning uchun 'during' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u85_ex2",
                    exerciseNumber = "85.2",
                    taskType = "CHOICE",
                    question = "I hurt my leg ______ I was playing football.",
                    options = listOf("while", "during", "for", "until"),
                    correctOptionIndex = 0,
                    correctAnswerText = "while",
                    explanationUzbek = "'I was playing football' to'liq gap (ega+fe'l), shuning uchun 'while' keladi."
                ),
                MurphyExerciseItem(
                    id = "u85_ex3",
                    exerciseNumber = "85.3",
                    taskType = "CHOICE",
                    question = "Always wash your hands ______ eating food.",
                    options = listOf("before", "during", "while", "until"),
                    correctOptionIndex = 0,
                    correctAnswerText = "before",
                    explanationUzbek = "Ovqat yeyishdan oldin: 'before eating'."
                ),
                MurphyExerciseItem(
                    id = "u85_ex4",
                    exerciseNumber = "85.4",
                    taskType = "CHOICE",
                    question = "We had a long walk ______ dinner.",
                    options = listOf("after", "while", "during of", "as"),
                    correctOptionIndex = 0,
                    correctAnswerText = "after",
                    explanationUzbek = "Kechki ovqatdan keyin: 'after dinner'."
                ),
                MurphyExerciseItem(
                    id = "u85_ex5",
                    exerciseNumber = "85.5",
                    taskType = "CHOICE",
                    question = "Can you look after my dog ______ I am away on vacation?",
                    options = listOf("while", "during", "before", "since"),
                    correctOptionIndex = 0,
                    correctAnswerText = "while",
                    explanationUzbek = "'I am away' gap bo'lgani sababli 'while' to'g'ri."
                )
            )
        ),

        // UNIT 86
        MurphyUnit(
            unitNumber = 86,
            title = "in, at, on (Prepositions of place 1)",
            subtitleUzbek = "O'rin-joy predloglari: IN (ichida), AT (aniq nuqtada), ON (ustida/yuzasida)",
            groupName = "14-Guruh: Word Order & Conjunctions (81–86)",
            keyTakeawaysUzbek = listOf(
                "IN = Nimaningdir ICHIDA yoki CHEGARALANGAN hududda: in a room, in a box, in a garden, in Tashkent, in Uzbekistan.",
                "ON = Nimaningdir USTIDA / YUZASIDA (tegib turibdi): on the table, on the wall, on the floor, on the 1st floor.",
                "AT = ANIQ BIR NUQTADA yoki TADBIRDA: at the bus stop, at the door, at the party, at work, at school, at home.",
                "TRANSPORT: on a bus, on a train, on a plane (yurib yursa bo'ladigan katta transportlar) LEKIN: in a car, in a taxi (o'tirib kiriladigan kichik mashinalar).",
                "AT HOME, AT WORK, AT SCHOOL birikmalari artiklsiz ishlatiladi!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "IN, ON, AT asosiy farqlari",
                    formula = "IN (3D - ichida) | ON (2D - yuzasida) | AT (1D - nuqtada)",
                    explanationUzbek = "Tasavvur qilish juda oson:\n\n1) IN (Ichida):\n• in my pocket (cho'ntagimda)\n• in a car (mashina ichida)\n• in a swimming pool (hovuzda)\n• in London (shaharda)\n\n2) ON (Yuzasida, ustida):\n• on the table (stol ustida)\n• a picture on the wall (devordagi rasm — devor yuzasiga yopishgan)\n• on the ceiling (shiftda)\n• on a page (sahifada)\n\n3) AT (Aniq manzil, nuqta, bino vazifasini bajarayotgan joy):\n• at the traffic lights (svetofor oldida)\n• at the bus stop (avtobus bekatida)\n• at the entrance (kiraverishda)\n• at home (uyda), at work (ishda), at university (universitetda)",
                    examples = listOf(
                        MurphyExample("There is some milk in the fridge.", "Muzlatgich ichida sut bor.", "in the fridge"),
                        MurphyExample("There is a notice on the door.", "Eshikda/eshik ustida e'lon bor.", "on the door"),
                        MurphyExample("I will wait for you at the station.", "Men sizni vokzalda (bekat nuqtasida) kutaman.", "at the station")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Maxsus birikmalar (Transport va mashhur iboralar)",
                    formula = "in a car / taxi | on a bus / train / plane / bike",
                    explanationUzbek = "Ingliz tilida transport vositalarida o'ziga xos qoida bor:\n\n• O'rningizdan turib qadamingizni bosa oladigan katta jamoat transportlarida ON ishlatiladi: on a bus, on a train, on a plane, on a ship.\n• Shuningdek ustiga minib olinadigan narsalarda: on a bicycle, on a motorbike, on a horse.\n• Lekin faqat engashib kirib o'tiradigan yengil mashinalarda IN ishlatiladi: in a car, in a taxi.\n\nKitob, gazeta, ko'cha:\n• in a book / in a newspaper (kitob ichida)\n• on TV / on the radio / on the internet (ekranda/to'lqinda)",
                    examples = listOf(
                        MurphyExample("We travelled on the high-speed train.", "Biz tezyurar poyezdda keldik.", "on the train"),
                        MurphyExample("She arrived in a taxi.", "U taksida yetib keldi.", "in a taxi"),
                        MurphyExample("I saw an interesting article in the newspaper.", "Men gazetada qiziqarli maqola ko'rdim.", "in the newspaper")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u86_ex1",
                    exerciseNumber = "86.1",
                    taskType = "CHOICE",
                    question = "There was a tall man standing ______ the bus stop.",
                    options = listOf("at", "in", "on", "into"),
                    correctOptionIndex = 0,
                    correctAnswerText = "at",
                    explanationUzbek = "Aniq manzil/nuqta bo'lgani uchun 'at the bus stop' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u86_ex2",
                    exerciseNumber = "86.2",
                    taskType = "CHOICE",
                    question = "Don't put your dirty shoes ______ the table!",
                    options = listOf("on", "in", "at", "to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Stol usti/yuzasi bo'lgani sababli 'on the table' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u86_ex3",
                    exerciseNumber = "86.3",
                    taskType = "CHOICE",
                    question = "My keys are ______ my jacket pocket.",
                    options = listOf("in", "at", "on", "onto"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in",
                    explanationUzbek = "Cho'ntak ichida bo'lgani uchun 'in my pocket' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u86_ex4",
                    exerciseNumber = "86.4",
                    taskType = "CHOICE",
                    question = "There were thirty passengers ______ the bus.",
                    options = listOf("on", "in", "at", "into"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Avtobus jamoat transporti bo'lgani uchun 'on the bus' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u86_ex5",
                    exerciseNumber = "86.5",
                    taskType = "CHOICE",
                    question = "Is your father ______ work right now?",
                    options = listOf("at", "in", "on", "to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "at",
                    explanationUzbek = "Ishda/xizmatda bo'lish doim 'at work' deb aytiladi."
                )
            )
        )
    )
}
