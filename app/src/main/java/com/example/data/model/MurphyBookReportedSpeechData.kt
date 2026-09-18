package com.example.data.model

object MurphyBookReportedSpeechData {

    val UNITS_REPORTED_SPEECH: List<MurphyUnit> = listOf(
        // UNIT 45
        MurphyUnit(
            unitNumber = 45,
            title = "Who saw you? / Who did you see?",
            subtitleUzbek = "Ega va to'ldiruvchiga beriladigan savollar (Subject vs Object questions)",
            groupName = "8-Guruh: Questions & Reported Speech (45–50)",
            keyTakeawaysUzbek = listOf(
                "EGA SAVOLI (Subject Question): 'Who' yoki 'What' harakatni bajargan shaxs/narsani so'raganda yordamchi fe'l (do/does/did) ishlatilmaydi: Who saw you? (Seni kim ko'rdi?).",
                "TO'LDIRUVCHI SAVOLI (Object Question): Harakat kimga/nimaga qaratilganini so'raganda yordamchi fe'l (do/does/did) shart: Who did you see? (Sen kimni ko'rding?).",
                "Kim qo'ng'iroq qildi? -> Who called? (Ega so'ralyapti).",
                "Sen kimga qo'ng'iroq qilding? -> Who did you call? (To'ldiruvchi so'ralyapti).",
                "What happened? (Nima sodir bo'ldi? - 'did happen' emas)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Ega savollari (Subject Questions)",
                    formula = "Who / What + asosiy fe'l (do/does/did KERAK EMAS)",
                    explanationUzbek = "Agar 'Who' yoki 'What' gapning egasi (harakat bajaruvchisi) bo'lsa, oddiy darak gap tartibi saqlanadi:\n\n• Somebody hit Paul. -> Who hit Paul? (Kim Polni urdi? - Ega so'ralyapti)\n• Something happened. -> What happened? (Nima bo'ldi? - 'What did happen' deyilmaydi)\n• Who wants some ice cream? (Kim muzqaymoq xohlaydi?)\n• Who lives in this house? (Bu uyda kim yashaydi?)",
                    examples = listOf(
                        MurphyExample("Who broke the window?", "Derazani kim sindirdi?", "Harakat egasi so'ralyapti, 'did break' emas"),
                        MurphyExample("What happened yesterday?", "Kecha nima sodir bo'ldi?", "Ega = What"),
                        MurphyExample("Who told you the news?", "Yangilikni senga kim aytdi?", "Oddiy o'tgan zamon fe'li told")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "To'ldiruvchi savollari (Object Questions)",
                    formula = "Who / What + do/does/did + ega + fe'l asosiy shakli",
                    explanationUzbek = "Agar gapning egasi boshqa shaxs bo'lsa va 'Who'/'What' to'ldiruvchi bo'lsa, odatiy savol tartibi (do/does/did) qo'llanadi:\n\n• Paul hit somebody. -> Who did Paul hit? (Pol kimni urdi?)\n• I want something. -> What do you want? (Nima xohlaysan?)\n• Who did you meet at the station? (Vokzalda kimni uchratding?)\n• What did you buy at the supermarket? (Supermarketdan nima sotib olding?)",
                    examples = listOf(
                        MurphyExample("Who did you invite to the party?", "Bazmga kimni taklif qilding?", "Ega = you, yordamchi fe'l = did"),
                        MurphyExample("What did she say to you?", "U senga nima dedi?", "What + did + she + say"),
                        MurphyExample("Who does this jacket belong to?", "Bu kurtka kimga tegishli?", "Who + does + jacket + belong")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Taqqoslash: Who saw you? vs Who did you see?",
                    formula = "Who saw you? (Kim seni ko'rdi?) | Who did you see? (Sen kimni ko'rding?)",
                    explanationUzbek = "Ikki tuzilishni aniq ajratish juda muhim:\n\n1) Sylvia saw Paul.\n• Who saw Paul? -> Sylvia (Sylvia saw him).\n• Who did Sylvia see? -> Paul (She saw Paul).\n\n2) Something fell on the floor.\n• What fell on the floor? -> A vase.\n• What did you drop? -> I dropped a cup.",
                    examples = listOf(
                        MurphyExample("Who phoned you this morning?", "Bugun ertalab senga kim telefon qildi?", "Ega so'ralyapti: Mary phoned me"),
                        MurphyExample("Who did you phone this morning?", "Bugun ertalab sen kimga telefon qilding?", "To'ldiruvchi: I phoned Mary"),
                        MurphyExample("What made that strange noise?", "U g'alati ovozni nima chiqardi?", "What = ega")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u45_ex1",
                    exerciseNumber = "45.1",
                    taskType = "CHOICE",
                    question = "Somebody gave me this book. -> ______ gave you this book?",
                    options = listOf("Who", "Who did", "Who does", "Whom did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Who",
                    explanationUzbek = "Ega so'ralganda 'Who gave you...?' to'g'ri bo'ladi, 'did' kerak emas."
                ),
                MurphyExerciseItem(
                    id = "u45_ex2",
                    exerciseNumber = "45.2",
                    taskType = "CHOICE",
                    question = "I saw somebody. -> Who ______ see?",
                    options = listOf("did you", "you did", "saw you", "you"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did you",
                    explanationUzbek = "To'ldiruvchi so'ralganda: 'Who did you see?' (Sen kimni ko'rding?)."
                ),
                MurphyExerciseItem(
                    id = "u45_ex3",
                    exerciseNumber = "45.3",
                    taskType = "CHOICE",
                    question = "Something happened yesterday. -> What ______?",
                    options = listOf("happened", "did happen", "was happened", "is happen"),
                    correctOptionIndex = 0,
                    correctAnswerText = "happened",
                    explanationUzbek = "'What happened?' eng tabiiy va to'g'ri shakl, yordamchi 'did' ishlatilmaydi."
                ),
                MurphyExerciseItem(
                    id = "u45_ex4",
                    exerciseNumber = "45.4",
                    taskType = "CHOICE",
                    question = "Who ______ that delicious cake?",
                    options = listOf("baked", "did bake", "was baked", "does bake"),
                    correctOptionIndex = 0,
                    correctAnswerText = "baked",
                    explanationUzbek = "Pirogni kim pishirdi? Ega so'ralyapti -> 'Who baked that cake?'."
                ),
                MurphyExerciseItem(
                    id = "u45_ex5",
                    exerciseNumber = "45.5",
                    taskType = "CHOICE",
                    question = "What ______ you do after school yesterday?",
                    options = listOf("did", "were", "do", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did",
                    explanationUzbek = "Ega 'you' bo'lgani uchun: 'What did you do...?'."
                )
            )
        ),

        // UNIT 46
        MurphyUnit(
            unitNumber = 46,
            title = "Who is she talking to? / What is it like?",
            subtitleUzbek = "Savollarda predloglarning o'rni (Prepositions at the end of questions)",
            groupName = "8-Guruh: Questions & Reported Speech (45–50)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida so'zlashuv savollarida predloglar (to, for, with, about, from, at) deyarli doim gap oxiriga qo'yiladi.",
                "Who are you talking to? (Kim bilan gaplashyapsan? - 'To whom' o'rniga).",
                "Where are you from? (Qayerdansiz? - 'From' oxirida).",
                "What is the film about? (Film nima haqida?).",
                "What is ... like? = Biror narsaning qandayligi, taassurot yoki xususiyatini so'rash (What is the weather like?)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Predloglar savol oxirida",
                    formula = "Who / What / Where ... + predlog (to, for, with, about, etc.)",
                    explanationUzbek = "Savol so'zlari bilan boshlangan savollarda predlog odatda fe'ldan keyin yoki gap oxirida keladi:\n\n• Who is she talking to? (U kim bilan gaplashyapti?)\n• What are you looking for? (Nimani qidiryapsan?)\n• Where does he come from? (U qayerdan kelgan?)\n• What are you listening to? (Nimani tinglayapsan?)\n• Who do you live with? (Kim bilan yashaysan?)",
                    examples = listOf(
                        MurphyExample("Who did you go on holiday with?", "Ta'tilga kim bilan bording?", "with savol oxirida"),
                        MurphyExample("What are you waiting for?", "Nimani kutyapsan?", "wait for -> What ... for?"),
                        MurphyExample("Which hotel did you stay at?", "Qaysi mehmonxonada qoldingiz?", "stay at -> Which hotel ... at?")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "What is it like? iborasi",
                    formula = "What + is/are/was/were + ot + like?",
                    explanationUzbek = "'What is it like?' - bu 'U qanday?' degani. Bu yerda 'like' fe'l (yoqtirmoq) emas, balki predlog (kabi/o'xshash) ma'nosida keladi:\n\n• What is the weather like today? - It's sunny and warm. (Bugun ob-havo qanday?)\n• What is your new job like? - It's very interesting. (Yangi ishing qanday?)\n• What are his parents like? - They are very kind. (Uning ota-onasi qanday odamlar?)\n• What was the film like? - It was boring. (Film qanday bo'ldi?)",
                    examples = listOf(
                        MurphyExample("What's your new teacher like?", "Yangi o'qituvching qanday odam?", "Tavsif so'ralmoqda"),
                        MurphyExample("What was the exam like? Was it difficult?", "Imtihon qanday o'tdi? Qiyin bo'ldimi?", "Imtihon taassuroti"),
                        MurphyExample("What is Tokyo like?", "Tokio qanday shahar?", "Shahar xususiyatlari")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u46_ex1",
                    exerciseNumber = "46.1",
                    taskType = "CHOICE",
                    question = "Where are you ______? - I am from Uzbekistan.",
                    options = listOf("from", "to", "at", "in"),
                    correctOptionIndex = 0,
                    correctAnswerText = "from",
                    explanationUzbek = "Qayerdansiz deb so'rashda 'Where are you from?' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u46_ex2",
                    exerciseNumber = "46.2",
                    taskType = "CHOICE",
                    question = "What is your new flat ______? - It's very spacious and bright.",
                    options = listOf("like", "look", "as", "such"),
                    correctOptionIndex = 0,
                    correctAnswerText = "like",
                    explanationUzbek = "'What is ... like?' biror narsaning sifatini yoki holatini so'rash uchun ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u46_ex3",
                    exerciseNumber = "46.3",
                    taskType = "CHOICE",
                    question = "Who is Tom waiting ______?",
                    options = listOf("for", "to", "about", "with"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Kutmoq fe'li doim 'wait for' predlogi bilan keladi: Who is Tom waiting for?"
                ),
                MurphyExerciseItem(
                    id = "u46_ex4",
                    exerciseNumber = "46.4",
                    taskType = "CHOICE",
                    question = "What are you talking ______?",
                    options = listOf("about", "at", "for", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "about",
                    explanationUzbek = "Nima haqida gaplashyapsiz? -> 'What are you talking about?'."
                ),
                MurphyExerciseItem(
                    id = "u46_ex5",
                    exerciseNumber = "46.5",
                    taskType = "CHOICE",
                    question = "Who did you send the letter ______?",
                    options = listOf("to", "with", "from", "at"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Xatni kimga jo'natdingiz? -> 'send something to somebody' -> 'Who did you send the letter to?'."
                )
            )
        ),

        // UNIT 47
        MurphyUnit(
            unitNumber = 47,
            title = "What ...? Which ...? How ...?",
            subtitleUzbek = "Savol so'zlarining aniq farqlari va qo'llanishi",
            groupName = "8-Guruh: Questions & Reported Speech (45–50)",
            keyTakeawaysUzbek = listOf(
                "WHAT = Cheksiz, umumiy tanlovlar uchun (What color do you like? What is your name?).",
                "WHICH = Cheklangan, aniq variantlar orasidan tanlashda (Which coat is yours - this one or that one?).",
                "HOW = Holat, usul yoki darajani so'rashda (How do you go to work? How was your weekend?).",
                "HOW birikmalari: How old (yosh), How tall (bo'y), How big (kattalik), How far (masofa), How long (vaqt davomiyligi), How much (narx/miqdor), How many (sanoqli miqdor)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "What vs Which farqi",
                    formula = "What (umumiy tanlov) vs Which (cheklangan tanlov)",
                    explanationUzbek = "Tanlov miqdoriga e'tibor bering:\n\n• What color are your eyes? (Umumiy tanlov - ko'k, qora, jigarrang va h.k.)\n• Which color do you prefer, pink or yellow? (Aniq 2 ta variant orasidan tanlov)\n• What size shoes do you wear? (Oyoq kiyimingiz o'lchami necha?)\n• Which bus goes to the center - 12 or 24? (Qaysi avtobus markazga boradi?)",
                    examples = listOf(
                        MurphyExample("What is the capital of France?", "Fransiyaning poytaxti qaysi?", "Umumiy bilim/savol"),
                        MurphyExample("Which pen is yours?", "Qaysi ruchka seniki?", "Stolda bir nechta ruchka bor, aniq tanlov"),
                        MurphyExample("What kind of music do you like?", "Qanday musiqani yoqtirasiz?", "What kind of...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "How va uning sifatlar bilan kelishi",
                    formula = "How + adjective / adverb (How old, How long, How far, etc.)",
                    explanationUzbek = "How so'zi turli sifatlar va ravishlar bilan maxsus savollar yasaydi:\n\n• How old are you? (Yoshingiz nechida?)\n• How tall is your brother? (Akangizning bo'yi qancha?)\n• How big is the apartment? (Kvartira qanchalik katta?)\n• How far is it from here to the airport? (Bu yerdan aeroportgacha qancha masofa?)\n• How long did the concert last? (Konsert qancha vaqt davom etdi?)\n• How often do you clean your room? (Xonangizni qanchalik tez-tez tozalaysiz?)",
                    examples = listOf(
                        MurphyExample("How far is your office from home?", "Ofisingiz uydan qanchalik uzoq?", "Masofa so'ralmoqda"),
                        MurphyExample("How long does it take by car?", "Mashinada qancha vaqt oladi?", "Vaqt davomiyligi"),
                        MurphyExample("How often do you go swimming?", "Suzishga qanchalik tez-tez borasiz?", "Chastota/takroriylik")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u47_ex1",
                    exerciseNumber = "47.1",
                    taskType = "CHOICE",
                    question = "______ way shall we go - left or right?",
                    options = listOf("Which", "What", "How", "Where"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Which",
                    explanationUzbek = "Cheklangan ikkita variant (left or right) bo'lgani uchun 'Which way' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u47_ex2",
                    exerciseNumber = "47.2",
                    taskType = "CHOICE",
                    question = "______ is it from London to Manchester? - About 200 miles.",
                    options = listOf("How far", "How long", "How much", "How many"),
                    correctOptionIndex = 0,
                    correctAnswerText = "How far",
                    explanationUzbek = "Masofani so'rash uchun 'How far' (qanchalik uzoq) ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u47_ex3",
                    exerciseNumber = "47.3",
                    taskType = "CHOICE",
                    question = "______ does this jacket cost? - It's 50 dollars.",
                    options = listOf("How much", "How many", "How long", "How far"),
                    correctOptionIndex = 0,
                    correctAnswerText = "How much",
                    explanationUzbek = "Narxni so'rashda 'How much' qo'llanadi."
                ),
                MurphyExerciseItem(
                    id = "u47_ex4",
                    exerciseNumber = "47.4",
                    taskType = "CHOICE",
                    question = "______ kind of movies do you enjoy watching?",
                    options = listOf("What", "Which", "How", "Where"),
                    correctOptionIndex = 0,
                    correctAnswerText = "What",
                    explanationUzbek = "'What kind of...' (qanday turdagi...) turg'un ibora hisoblanadi."
                ),
                MurphyExerciseItem(
                    id = "u47_ex5",
                    exerciseNumber = "47.5",
                    taskType = "CHOICE",
                    question = "______ does the lesson take? - It takes 45 minutes.",
                    options = listOf("How long", "How far", "How often", "How many"),
                    correctOptionIndex = 0,
                    correctAnswerText = "How long",
                    explanationUzbek = "Vaqt davomiyligi 'How long' orqali so'raladi."
                )
            )
        ),

        // UNIT 48
        MurphyUnit(
            unitNumber = 48,
            title = "How long does it take ...?",
            subtitleUzbek = "Biror ish-harakat uchun qancha vaqt ketishi haqida so'rash va aytish",
            groupName = "8-Guruh: Questions & Reported Speech (45–50)",
            keyTakeawaysUzbek = listOf(
                "SAVOL: How long does it take (to do something)? = Biror ishni bajarish qancha vaqt oladi?",
                "O'TGAN ZAMON SAVOLI: How long did it take? = Qancha vaqt oldi?",
                "KELASI ZAMON SAVOLI: How long will it take? = Qancha vaqt oladi?",
                "JAVOB FORMULASI: It takes (me/you/him) + vaqt + to do something.",
                "Misol: It takes 20 minutes to walk to the station. (Vokzalga piyoda borish 20 daqiqa oladi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "How long does it take ...? Savol shakli",
                    formula = "How long does it take (you) to + fe'l?",
                    explanationUzbek = "Biror harakat uchun ketadigan vaqtni so'rashda 'How long does it take' ishlatiladi:\n\n• How long does it take to fly from London to Madrid? (Londondan Madridga uchish qancha vaqt oladi?)\n• How long does it take you to get ready in the morning? (Ertalab tayyor bo'lishingizga qancha vaqt ketadi?)\n• How long does it take to learn English? (Ingliz tilini o'rganishga qancha vaqt ketadi?)",
                    examples = listOf(
                        MurphyExample("How long does it take by train?", "Poyezdda qancha vaqt oladi?", "Vaqt sarfi so'ralmoqda"),
                        MurphyExample("How long did it take to build this bridge?", "Bu ko'prikni qurish qancha vaqt oldi?", "O'tgan zamon: did it take"),
                        MurphyExample("How long will it take to repair the car?", "Mashinani tuzatishga qancha vaqt ketadi?", "Kelasi zamon: will it take")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Javob berish: It takes / It took / It will take",
                    formula = "It takes / took / will take + (shaxs) + vaqt + to do something",
                    explanationUzbek = "Javoblarda zamonlarga mos shakl tanlanadi:\n\n1) Hozirgi zamon: It takes 30 minutes to get to work.\n2) O'tgan zamon: It took me two hours to do my homework yesterday.\n3) Kelasi zamon: It will take about an hour to paint this door.\n\nAgar kimgadir ketgan vaqtni aytmoqchi bo'lsak, olmosh (me, you, him, her, us, them) vaqtdan oldin qo'yiladi:\n• It takes me 15 minutes to cook breakfast.",
                    examples = listOf(
                        MurphyExample("It takes about 40 minutes to get to the airport.", "Aeroportga yetib borish 40 daqiqa atrofida vaqt oladi.", "Umumiy qoida"),
                        MurphyExample("It took her three days to finish the book.", "Kitobni tugatish unga uch kun vaqt oldi.", "O'tgan zamon: took her"),
                        MurphyExample("It won't take long to fix this phone.", "Bu telefonni tuzatish ko'p vaqt olmaydi.", "Inkor shakli: won't take long")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u48_ex1",
                    exerciseNumber = "48.1",
                    taskType = "CHOICE",
                    question = "How long ______ it take to get to the station by foot?",
                    options = listOf("does", "is", "do", "has"),
                    correctOptionIndex = 0,
                    correctAnswerText = "does",
                    explanationUzbek = "Ega 'it' bo'lgani sababli hozirgi zamon savolida 'does it take' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u48_ex2",
                    exerciseNumber = "48.2",
                    taskType = "CHOICE",
                    question = "It ______ me an hour to clean my room yesterday.",
                    options = listOf("took", "takes", "taken", "taking"),
                    correctOptionIndex = 0,
                    correctAnswerText = "took",
                    explanationUzbek = "'Yesterday' o'tgan zamon belgisi, shuning uchun 'take' fe'lining 2-shakli 'took' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u48_ex3",
                    exerciseNumber = "48.3",
                    taskType = "CHOICE",
                    question = "How long ______ it take to paint this room next week?",
                    options = listOf("will", "did", "does", "is"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will",
                    explanationUzbek = "'Next week' kelasi zamon belgisi bo'lgani sababli 'will it take' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u48_ex4",
                    exerciseNumber = "48.4",
                    taskType = "CHOICE",
                    question = "It doesn't take ______ to drive to the city center.",
                    options = listOf("long", "far", "often", "many"),
                    correctOptionIndex = 0,
                    correctAnswerText = "long",
                    explanationUzbek = "'It doesn't take long' = Ko'p vaqt olmaydi (turg'un ibora)."
                ),
                MurphyExerciseItem(
                    id = "u48_ex5",
                    exerciseNumber = "48.5",
                    taskType = "CHOICE",
                    question = "How long did it take ______ to solve the puzzle?",
                    options = listOf("you", "your", "yours", "yourself"),
                    correctOptionIndex = 0,
                    correctAnswerText = "you",
                    explanationUzbek = "It takes / took + obyekt olmoshi (you, me, him, her) + to do something."
                )
            )
        ),

        // UNIT 49
        MurphyUnit(
            unitNumber = 49,
            title = "Do you know where ...? / Can you tell me ...?",
            subtitleUzbek = "Bilvosita (odobli) savollar: So'z tartibi o'zgarishi",
            groupName = "8-Guruh: Questions & Reported Speech (45–50)",
            keyTakeawaysUzbek = listOf(
                "BODAVIY (INDIRECT) SAVOLLAR: 'Do you know...', 'Can you tell me...', 'I don't know...' iboralari bilan boshlanadi.",
                "ENG MUHIM QOIDA: Bilvosita savolning ikkinchi qismida DARAK GAP so'z tartibi (Ega + Kesim) bo'ladi. Yordamchi fe'llar (do/does/did) olib tashlanadi!",
                "To'g'ri savol: Where is the bank?",
                "Bilvosita savol: Do you know where the bank is? ('where is the bank' EMAS).",
                "Ha/Yo'q savollarida 'if' yoki 'whether' qo'shiladi: Do you know if he is coming?"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Bilvosita savollarda so'z tartibi",
                    formula = "Do you know + where/what/why + EGA + KESIM?",
                    explanationUzbek = "Agar savol 'Do you know...' yoki 'Could you tell me...' bilan boshlansa, undan keyin keladigan qism savol emas, darak gap tartibida bo'ladi:\n\n• Where does Jack live? -> Do you know where Jack lives? ('where does Jack live' emas)\n• What time is it? -> Can you tell me what time it is? ('what time is it' emas)\n• Why did she leave? -> Do you know why she left? ('why did she leave' emas)\n• Where have they gone? -> I wonder where they have gone.",
                    examples = listOf(
                        MurphyExample("Can you tell me where the station is?", "Vokzal qayerdaligini ayta olasizmi?", "Darak tartibi: station is"),
                        MurphyExample("Do you know what time the film starts?", "Film soat nechada boshlanishini bilasizmi?", "starts (does olib tashlangan)"),
                        MurphyExample("I don't know who that man is.", "U kishi kimligini bilmayman.", "who that man is")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "If / Whether bilan bilvosita savollar",
                    formula = "Do you know + IF / WHETHER + ega + kesim",
                    explanationUzbek = "Agar asl savolda savol so'zi (who, what, where) bo'lmasa, 'if' (yoki 'whether') qo'shiladi:\n\n• Is Jack at home? -> Do you know if Jack is at home? (Jek uydami-yo'qligini bilasizmi?)\n• Did they arrive safely? -> Do you know whether they arrived safely?\n• Has she got a car? -> I don't know if she has got a car.",
                    examples = listOf(
                        MurphyExample("Do you know if the store is open today?", "Bugun do'kon ochiqmi-yo'qmi bilasizmi?", "if qo'shildi"),
                        MurphyExample("Can you tell me if there is a bus to the airport?", "Aeroportga avtobus bormi-yo'qligini ayta olasizmi?", "if there is"),
                        MurphyExample("I wonder if it will rain tomorrow.", "Ertaga yomg'ir yog'armikin deb o'ylayapman.", "wonder if")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u49_ex1",
                    exerciseNumber = "49.1",
                    taskType = "CHOICE",
                    question = "Where is the post office? -> Could you tell me where ______?",
                    options = listOf("the post office is", "is the post office", "does the post office", "the post office does"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the post office is",
                    explanationUzbek = "Bilvosita savolda darak so'z tartibi: ega (the post office) + kesim (is)."
                ),
                MurphyExerciseItem(
                    id = "u49_ex2",
                    exerciseNumber = "49.2",
                    taskType = "CHOICE",
                    question = "Do you know what time ______?",
                    options = listOf("the train leaves", "does the train leave", "leaves the train", "is the train leave"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the train leaves",
                    explanationUzbek = "Bilvosita savolda 'does' kerak emas, fe'l oddiy darak gapdagidek 'leaves' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u49_ex3",
                    exerciseNumber = "49.3",
                    taskType = "CHOICE",
                    question = "I don't know ______ she is coming to the party or not.",
                    options = listOf("if", "what", "where", "how"),
                    correctOptionIndex = 0,
                    correctAnswerText = "if",
                    explanationUzbek = "Ha/yo'q ma'nosidagi bilvosita gaplarda 'if' yoki 'whether' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u49_ex4",
                    exerciseNumber = "49.4",
                    taskType = "CHOICE",
                    question = "Why did he leave early? -> Do you know why ______ early?",
                    options = listOf("he left", "did he leave", "he did leave", "left he"),
                    correctOptionIndex = 0,
                    correctAnswerText = "he left",
                    explanationUzbek = "'did' tushib qoladi va fe'l o'tgan zamon shakliga (left) o'tadi: why he left."
                ),
                MurphyExerciseItem(
                    id = "u49_ex5",
                    exerciseNumber = "49.5",
                    taskType = "CHOICE",
                    question = "Can you tell me where ______ live?",
                    options = listOf("they", "do they", "did they", "are they"),
                    correctOptionIndex = 0,
                    correctAnswerText = "they",
                    explanationUzbek = "Darak gap tartibi bo'yicha darhol ega (they) keladi: where they live."
                )
            )
        ),

        // UNIT 50
        MurphyUnit(
            unitNumber = 50,
            title = "She said that ... / He told me that ...",
            subtitleUzbek = "O'zlashtirma gap (Reported Speech: Say vs Tell)",
            groupName = "8-Guruh: Questions & Reported Speech (45–50)",
            keyTakeawaysUzbek = listOf(
                "O'zlashtirma gapda birovning aytgan so'zlari o'tmishda aytilgani sababli fe'llar bir zamon ORQAGA suriladi (Tense shift).",
                "am/is -> was, are -> were, have/has -> had, do/does -> did, will -> would, can -> could.",
                "SAY vs TELL: 'said' dan keyin shaxs kelmaydi (He said that he was tired).",
                "'told' dan keyin doim kimgaligi aytilishi SHART (He told me that he was tired).",
                "He told to me (NOTO'G'RI) -> He told me (TO'G'RI)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "O'zlashtirma gapda zamonlarning orqaga surilishi",
                    formula = "Asl gap (Hozirgi) -> O'zlashtirma gap (O'tgan)",
                    explanationUzbek = "Birov aytgan gapni qayta hikoya qilganda odatda 'He said that...' deb boshlaymiz va fe'llar o'tgan zamonga aylanadi:\n\n• 'I am tired.' -> He said that he was tired.\n• 'I have got a new car.' -> She said that she had got a new car.\n• 'I don't like fish.' -> He said that he didn't like fish.\n• 'I will call you tomorrow.' -> She said that she would call me.\n• 'I cannot swim.' -> He said that he couldn't swim.\n\n⚠️ 'that' so'zini tushirib qoldirsa ham bo'ladi: He said he was tired.",
                    examples = listOf(
                        MurphyExample("Tom said that he was feeling sick.", "Tom o'zini yomon his qilayotganini aytdi.", "am feeling -> was feeling"),
                        MurphyExample("Sarah said she would be late.", "Sara kechikishini aytdi.", "will -> would"),
                        MurphyExample("They said they had never visited London.", "Ular Londonda hech qachon bo'lmaganliklarini aytishdi.", "have visited -> had visited")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "SAY vs TELL farqi",
                    formula = "say (something) vs tell SOMEBODY (something)",
                    explanationUzbek = "Say va Tell fe'llarining eng asosiy farqi kimgaligini ko'rsatishda:\n\n1) SAY:\n• He said that he was hungry. (U och ekanligini aytdi)\n• He said to me that... (Kamdan-kam, odatda ishlatilmaydi)\n\n2) TELL (doim kimga ekanligi aytiladi: tell me, tell him, tell us):\n• He told me that he was hungry. (U menga och ekanligini aytdi)\n• She told Tom that she was leaving. (U Tomga ketayotganini aytdi)\n\n⚠️ 'He told that he was tired' noto'g'ri! 'He told ME' yoki 'He SAID' bo'lishi kerak.",
                    examples = listOf(
                        MurphyExample("Rachel told me that she had a new job.", "Reychel menga yangi ishga kirganini aytdi.", "told me (shaxs ko'rsatilgan)"),
                        MurphyExample("Rachel said that she had a new job.", "Reychel yangi ishga kirganini aytdi.", "said that (shaxs ko'rsatilmagan)"),
                        MurphyExample("Did you tell him the news?", "Unga yangilikni aytdingmi?", "tell him")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u50_ex1",
                    exerciseNumber = "50.1",
                    taskType = "CHOICE",
                    question = "Tom ______ that he was feeling tired and wanted to sleep.",
                    options = listOf("said", "told", "spoke", "talked"),
                    correctOptionIndex = 0,
                    correctAnswerText = "said",
                    explanationUzbek = "Keyin shaxs (me, him) kelmagani uchun 'said that' to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u50_ex2",
                    exerciseNumber = "50.2",
                    taskType = "CHOICE",
                    question = "He ______ me that he would call me later in the evening.",
                    options = listOf("told", "said", "spoke", "explained"),
                    correctOptionIndex = 0,
                    correctAnswerText = "told",
                    explanationUzbek = "Keyin 'me' kelgan: 'He told me' to'g'ri ('said me' noto'g'ri)."
                ),
                MurphyExerciseItem(
                    id = "u50_ex3",
                    exerciseNumber = "50.3",
                    taskType = "CHOICE",
                    question = "'I will help you.' -> Anna said she ______ help me.",
                    options = listOf("would", "will", "can", "is"),
                    correctOptionIndex = 0,
                    correctAnswerText = "would",
                    explanationUzbek = "O'zlashtirma gapda 'will' bir zamon orqaga surilib 'would' ga aylanadi."
                ),
                MurphyExerciseItem(
                    id = "u50_ex4",
                    exerciseNumber = "50.4",
                    taskType = "CHOICE",
                    question = "'I am happy.' -> Jack told us that he ______ happy.",
                    options = listOf("was", "is", "were", "been"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was",
                    explanationUzbek = "Hozirgi zamon 'am' o'tgan zamonga surilib 'was' ga aylanadi."
                ),
                MurphyExerciseItem(
                    id = "u50_ex5",
                    exerciseNumber = "50.5",
                    taskType = "CHOICE",
                    question = "What did he ______ you? - He didn't ______ anything.",
                    options = listOf("tell / say", "say / tell", "tell / tell", "say / say"),
                    correctOptionIndex = 0,
                    correctAnswerText = "tell / say",
                    explanationUzbek = "'tell you' (kimgaligi bor) va 'say anything' (kimgaligi yo'q)."
                )
            )
        )
    )
}
