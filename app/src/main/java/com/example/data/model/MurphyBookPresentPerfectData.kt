package com.example.data.model

object MurphyBookPresentPerfectDatabase {

    val UNITS_PRESENT_PERFECT: List<MurphyUnit> = listOf(
        // UNIT 15
        MurphyUnit(
            unitNumber = 15,
            title = "I have done (Present Perfect 1)",
            subtitleUzbek = "Hozirgi tugallangan zamon: have/has + V3 (Hozirgi vaqt bilan bevosita bog'liq natija)",
            groupName = "3-Guruh: Present Perfect (15–20)",
            keyTakeawaysUzbek = listOf(
                "Present Perfect formulasi: have / has + o'tgan zamon sifatdoshi (Past Participle - V3).",
                "I / we / you / they + have ('ve), he / she / it + has ('s).",
                "To'g'ri fe'llarga '-ed' qo'shiladi (cleaned, finished). Noto'g'ri fe'llarning 3-shakli (V3) ishlatiladi (lost, seen, bought, gone).",
                "Asosiy mohiyat: Ish-harakat o'tmishda sodir bo'lgan, lekin uning NATIJASI hozirgi daqiqada ko'rinib turibdi yoki muhim!",
                "Masalan: 'I have lost my key' = Men kalitimni yo'qotib qo'ydim (va HOZIR ham menda kalit yo'q)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Oyoq kiyim tozalash misoli (Hozirgi natija)",
                    formula = "Ega + have / has + V3 (Past Participle)",
                    explanationUzbek = "Tasavvur qiling: Polning tuflisi loy edi (His shoes are dirty). U tuflisini tozalashga kirishdi (He is cleaning his shoes). Hozir uning tuflisi top-toza!\nBiz nima deymiz?\n-> 'He has cleaned his shoes.' (= U tuflisini tozalab bo'ldi, uning tuflisi hozir toza!).\n\nPresent Perfect o'tmishdagi harakatning HOZIRGI natijasiga qaratilgan zamondir.",
                    examples = listOf(
                        MurphyExample("His shoes were dirty. Now he has cleaned his shoes.", "Uning poyabzallari kir edi. Hozir u poyabzallarini tozalab bo'ldi.", "Hozirgi natija: toza"),
                        MurphyExample("They are at home. They are going out. They have gone out.", "Ular uyda edilar. Ular chiqib ketishmoqda. Ular chiqib ketishdi.", "Hozirgi natija: ular uyda yo'q"),
                        MurphyExample("I've cleaned my teeth.", "Tishlarimni yuvib bo'ldim.", "I have -> I've"),
                        MurphyExample("She has closed the door.", "U eshikni yopdi.", "Eshik hozir yopiq holatda")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "To'g'ri va Noto'g'ri fe'llar (Past Participle - V3)",
                    formula = "Regular: V + ed | Irregular: 3-ustundagi shakl (V3)",
                    explanationUzbek = "Present Perfect da ishlatiladigan fe'l 'Past Participle' (V3) deyiladi:\n\n1. To'g'ri fe'llarda V2 va V3 bir xil (-ed oladi):\n• clean -> cleaned -> cleaned\n• open -> opened -> opened\n• start -> started -> started\n\n2. Noto'g'ri fe'llarda ba'zan V2 va V3 bir xil, ba'zan esa butunlay har xil bo'ladi:\n• lose -> lost -> lost (bir xil)\n• buy -> bought -> bought (bir xil)\n• see -> saw -> SEEN (har xil)\n• do -> did -> DONE (har xil)\n• break -> broke -> BROKEN (har xil)\n• go -> went -> GONE (har xil)",
                    examples = listOf(
                        MurphyExample("I have lost my passport.", "Pasportimni yo'qotib qo'ydim.", "lose -> lost -> lost"),
                        MurphyExample("Somebody has broken that window.", "Kimdir ana u derazani sindirib qo'yibdi.", "break -> broke -> broken"),
                        MurphyExample("Linda has bought a new car.", "Linda yangi mashina sotib oldi.", "buy -> bought -> bought"),
                        MurphyExample("They have done a lot of work today.", "Ular bugun juda ko'p ish qilishdi.", "do -> did -> done")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Hozirgi vaqt bilan bog'liqlik (Something happened + now)",
                    formula = "Past action -> NOW result",
                    explanationUzbek = "Ingliz tilida agar ish o'tmishda bo'lib o'tgan bo'lsa-yu, lekin siz uchun hozirgi ahvol muhim bo'lsa, har doim Present Perfect tanlanadi:\n\n• 'I've lost my key' = Kalitimni yo'qotdim (natijada hozir uyga kirolmay turibman).\n• 'Where is Paul?' - 'He has gone to bed' = Pol qayerda? - U uxlab yotibdi (hozir o'rinda).\n• 'Look! Somebody has broken the window' = Qarang, kimdir derazani sindiribdi (deraza hozir siniq holatda).",
                    examples = listOf(
                        MurphyExample("I can't find my bag. Have you seen it?", "Sumkamni topolmayapman. Uni ko'rdingizmi?", "Hozir qidiryapman"),
                        MurphyExample("Where is Peter? - He has gone home.", "Piter qayerda? - U uyiga ketgan.", "Hozir u shu yerda yo'q"),
                        MurphyExample("It's Rachel's birthday tomorrow and I haven't bought her a present.", "Ertaga Reychelning tug'ilgan kuni, men esa hali sovg'a sotib olganim yo'q.", "Hozircha sovg'am yo'q")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u15_ex1",
                    exerciseNumber = "15.1",
                    taskType = "CHOICE",
                    question = "Look! Somebody ______ that window. The glass is all over the floor.",
                    options = listOf("has broken", "have broken", "broke", "is breaking"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has broken",
                    explanationUzbek = "'Somebody' 3-shaxs birlik (he/she) hisoblanadi, shuning uchun 'has' va 'break' ning 3-shakli 'broken' keladi: 'has broken'."
                ),
                MurphyExerciseItem(
                    id = "u15_ex2",
                    exerciseNumber = "15.2",
                    taskType = "CHOICE",
                    question = "I can't get into the house because I ______ my key.",
                    options = listOf("have lost", "has lost", "losted", "am losing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have lost",
                    explanationUzbek = "'I' olmoshi bilan 'have' va 'lose' ning V3 shakli 'lost' ishlatiladi. Hozirgi natija: uyga kirolmayapman."
                ),
                MurphyExerciseItem(
                    id = "u15_ex3",
                    exerciseNumber = "15.3",
                    taskType = "CHOICE",
                    question = "Where is Sarah? - She ______ to bed.",
                    options = listOf("has gone", "have gone", "has went", "went"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has gone",
                    explanationUzbek = "Sarah hozir xonasida uxlayapti degan natija uchun 'has gone' to'g'ri (go -> went -> gone)."
                ),
                MurphyExerciseItem(
                    id = "u15_ex4",
                    exerciseNumber = "15.4",
                    taskType = "CHOICE",
                    question = "They were in the living room, but now they ______ out.",
                    options = listOf("have gone", "has gone", "are gone", "have went"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have gone",
                    explanationUzbek = "'They' olmoshi bilan 'have' va 'gone' ishlatiladi: 'they have gone out'."
                ),
                MurphyExerciseItem(
                    id = "u15_ex5",
                    exerciseNumber = "15.5",
                    taskType = "CHOICE",
                    question = "Can I have this newspaper? - Yes, I ______ with it.",
                    options = listOf("have finished", "has finished", "finished", "am finish"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have finished",
                    explanationUzbek = "'O'qib bo'ldim, gazetani olavering' (hozirgi natija) ma'nosida 'I have finished' ishlatiladi."
                )
            )
        ),

        // UNIT 16
        MurphyUnit(
            unitNumber = 16,
            title = "I've just... I've already... I haven't... yet",
            subtitleUzbek = "'just' (hozirgina), 'already' (allaqachon), 'yet' (hali / hali ham) so'zlarining Present Perfect dagi o'rni",
            groupName = "3-Guruh: Present Perfect (15–20)",
            keyTakeawaysUzbek = listOf(
                "'just' = hozirgina, bir necha daqiqa oldin (have/has va V3 orasida keladi).",
                "'already' = kutilgandan ertaroq, allaqachon (have/has va V3 orasida keladi).",
                "'yet' = hozirgacha, hali (faqat INKOR va SO'ROQ gaplarda, doimo GAP OXIRIDA keladi).",
                "'I haven't done it yet' = Hali buni qilmadim (lekin qilish niyatidaman).",
                "'Have you finished yet?' = Hali ham tugatmadingizmi? / Tugatib bo'ldingizmi?"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "JUST = hozirgina (a short time ago)",
                    formula = "have / has + JUST + V3",
                    explanationUzbek = "'Just' so'zi biror voqea juda qisqa vaqt oldin (hozirgina, bir zum oldin) sodir bo'lganini bildiradi. U doimo yordamchi fe'l (have/has) va asosiy fe'l (V3) o'rtasiga qo'yiladi.\n\nMisol:\n• 'Are you hungry?' - 'No, I've just had lunch.' (Ochmisiz? - Yo'q, hozirgina tushlik qildim).\n• 'Hello. Have you just arrived?' (Salom. Hozirgina yetib keldingizmi?).",
                    examples = listOf(
                        MurphyExample("A: Are you hungry? B: No, I've just had dinner.", "A: Qorningiz ochmi? B: Yo'q, hozirgina kechki ovqatni yedim.", "have just had"),
                        MurphyExample("A: Is Tom here? B: No, I'm afraid he has just left.", "A: Tom shu yerdami? B: Yo'q, afsuski u hozirgina chiqib ketdi.", "has just left"),
                        MurphyExample("Look! That plane has just landed.", "Qarang! Ana u samolyot hozirgina qo'ndi.", "has just landed")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "ALREADY = allaqachon, kutilgandan oldin (sooner than expected)",
                    formula = "have / has + ALREADY + V3",
                    explanationUzbek = "'Already' biror ish kimdir kutganidan ko'ra tezroq yoki allaqachon bajarib bo'linganini ko'rsatish uchun ishlatiladi. U ham 'have/has' va asosiy fe'l o'rtasida turadi.\n\nMisol:\n• 'What time is Mark leaving?' - 'He has already left.' (Mark soat nechada ketadi? - U allaqachon ketib bo'ldi!).\n• 'Don't forget to pay the electric bill.' - 'I've already paid it.' (Elektr to'lovini to'lashni unutmang. - Allaqachon to'lab qo'yganman).",
                    examples = listOf(
                        MurphyExample("What time is Mark leaving? - He has already left.", "Mark soat nechada ketyapti? - U allaqachon ketdi.", "has already left"),
                        MurphyExample("I've already paid the electricity bill.", "Men elektr to'lovini allaqachon to'lab bo'lganman.", "I've already paid"),
                        MurphyExample("A: What's in the newspaper today? B: I don't know. I haven't read it yet.", "Gazetada nima gap? - Bilmayman, hali o'qiganim yo'q.", "yet gap oxirida")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "YET = hali, hozirgacha (until now) - gap oxirida!",
                    formula = "Inkor: haven't / hasn't + V3 ... YET | So'roq: Have / Has + ega + V3 ... YET?",
                    explanationUzbek = "'Yet' so'zlovchi biror voqeaning sodir bo'lishini kutayotganini bildiradi. Qoidasi juda qat'iy:\n1. Faqat inkor (Negative) va so'roq (Questions) gaplarda ishlatiladi.\n2. Doimo gapning eng oxiriga qo'yiladi!\n\n• Inkor: 'It's 10 o'clock and Joe hasn't got up yet.' (Soat 10 bo'ldi, Jo esa hali ham o'rnidan turgani yo'q).\n• So'roq: 'Have you finished your homework yet?' (Uy vazifangizni tugatib bo'ldingizmi?).",
                    examples = listOf(
                        MurphyExample("It's 10 o'clock and Joe hasn't got up yet.", "Soat 10 bo'ldi, Jo esa hali ham turmadi.", "hasn't got up yet"),
                        MurphyExample("Have you started your new job yet?", "Yangi ishingizni boshlab yubordingizmi?", "Have you started ... yet?"),
                        MurphyExample("This is my new dress. - Oh, it's nice. Have you worn it yet?", "Bu mening yangi ko'ylagim. - Chiroyli ekan. Hali kiydingizmi?", "Have you worn ... yet?"),
                        MurphyExample("The postman hasn't come yet.", "Pochtachi hali kelgani yo'q.", "hasn't come yet")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u16_ex1",
                    exerciseNumber = "16.1",
                    taskType = "CHOICE",
                    question = "Would you like something to eat? - No, thanks. I ______ lunch.",
                    options = listOf("have just had", "have already had yet", "just had have", "am just having"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have just had",
                    explanationUzbek = "'Hozirgina ovqatlandim' ma'nosida 'have just had' ishlatiladi ('just' have va had o'rtasida turadi)."
                ),
                MurphyExerciseItem(
                    id = "u16_ex2",
                    exerciseNumber = "16.2",
                    taskType = "CHOICE",
                    question = "Don't forget to send the letter! - I've ______ sent it.",
                    options = listOf("already", "yet", "just now", "still"),
                    correctOptionIndex = 0,
                    correctAnswerText = "already",
                    explanationUzbek = "'Uni allaqachon jo'natib bo'ldim' ma'nosida darak gapda 'already' ishlatiladi: 'I've already sent it'."
                ),
                MurphyExerciseItem(
                    id = "u16_ex3",
                    exerciseNumber = "16.3",
                    taskType = "CHOICE",
                    question = "Has it stopped raining ______?",
                    options = listOf("yet", "already", "just", "ever"),
                    correctOptionIndex = 0,
                    correctAnswerText = "yet",
                    explanationUzbek = "So'roq gapning oxirida 'hali/allaqachon tondimi?' ma'nosida 'yet' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u16_ex4",
                    exerciseNumber = "16.4",
                    taskType = "CHOICE",
                    question = "We are waiting for the bus. It ______ yet.",
                    options = listOf("hasn't arrived", "didn't arrive", "isn't arrived", "has arrived"),
                    correctOptionIndex = 0,
                    correctAnswerText = "hasn't arrived",
                    explanationUzbek = "'yet' inkor gapda keladi va 'It' (avtobus) bo'lgani uchun 'hasn't arrived' to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u16_ex5",
                    exerciseNumber = "16.5",
                    taskType = "CHOICE",
                    question = "A: When is Tom going to leave? B: He ______.",
                    options = listOf("has already left", "has yet left", "leaves already", "already has leave"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has already left",
                    explanationUzbek = "'U allaqachon ketib bo'ldi' -> 'has already left'."
                )
            )
        ),

        // UNIT 17
        MurphyUnit(
            unitNumber = 17,
            title = "Have you ever...? (Present Perfect 3)",
            subtitleUzbek = "Hayotiy tajriba: 'ever' (umringizda/hech), 'never' (hech qachon) va 'been to' vs 'gone to'",
            groupName = "3-Guruh: Present Perfect (15–20)",
            keyTakeawaysUzbek = listOf(
                "'Have you ever...?' insonning butun hayoti davomidagi tajribasini (tug'ilganidan to hozirgacha) so'rash uchun ishlatiladi.",
                "'ever' so'roq gapda (hech / biror marta), 'never' esa inkor ma'nodagi darak gapda (hech qachon) keladi.",
                "'never' o'zi inkor so'z bo'lgani uchun fe'l inkor qilinmaydi: 'I have never seen' (NOT 'haven't never').",
                "BEEN TO va GONE TO farqi: 'He has gone to Spain' = U hozir Ispaniyada (qaytmagan). 'He has been to Spain' = U Ispaniyada bo'lib, qaytib kelgan (hozir uyida).",
                "'How many times have you been to...?' = Necha marta bo'lgansiz?"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Hayotiy tajriba (Have you ever...?)",
                    formula = "Have you EVER + V3 ...? | I have NEVER + V3",
                    explanationUzbek = "Biz kimgadir 'Umringizda biror marta Rimda bo'lganmisiz?' yoki 'Hech ot minib ko'rganmisiz?' deb savol berganimizda, insonning butun hayoti bo'ylab tajribasini so'rayotgan bo'lamiz. Bunday paytda Present Perfect ishlatiladi.\n\n• Have you ever been to Rome? (Rimda bo'lganmisiz?)\n• Yes, I have. Many times. (Ha, ko'p marta).\n• No, I've never been there. (Yo'q, hech qachon u yerda bo'lmaganman).",
                    examples = listOf(
                        MurphyExample("Have you ever been to Japan? - No, never.", "Yaponiyada bo'lganmisiz? - Yo'q, hech qachon.", "Hayotiy tajriba"),
                        MurphyExample("Have you ever played golf? - Yes, once.", "Hech golf o'ynaganmisiz? - Ha, bir marta.", "ever played"),
                        MurphyExample("My sister has never travelled by plane.", "Singlim hech qachon samolyotda uchmagan.", "has never travelled"),
                        MurphyExample("I've never ridden a horse.", "Hech qachon ot minmaganman.", "never + ridden")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "BEEN TO vs GONE TO (Oltin farq)",
                    formula = "gone to = u yerda (hali qaytmagan) | been to = borib kelgan (hozir shu yerda)",
                    explanationUzbek = "Bu ingliz tilidagi eng ko'p adashtiriladigan, lekin tushunish juda oson bo'lgan qoidadir:\n\n1. 'Ben has gone to Spain.'\n-> Ben Ispaniyaga ketgan. U hozir Ispaniyada yoki yo'lda ketmoqda. Hali uyiga qaytgani yo'q!\n\n2. 'Ben has been to Spain.'\n-> Ben Ispaniyaga borib kelgan. U hozir o'z yurtida, uyida turibdi. Safar tugagan, u bu yerda!",
                    examples = listOf(
                        MurphyExample("Where is Ben? - He has gone to Spain. He'll be back next week.", "Ben qayerda? - U Ispaniyaga ketgan. Kelgusi hafta qaytadi.", "gone to (u hozir Ispaniyada)"),
                        MurphyExample("Hello, Ben! Where have you been? - I've been to Spain.", "Salom, Ben! Qayerlarda eding? - Ispaniyaga borib keldim.", "been to (qaytib keldi)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Necha marta? (How many times / modern phrases)",
                    formula = "How many times have you ...? | It's the first time I have ...",
                    explanationUzbek = "Biror harakat hayotingiz davomida necha marta takrorlanganini aytganda ham Present Perfect ishlatiladi:\n\n• How many times have you been to New York?\n• I've been there four times.\n• It's a very good book. I've read it twice.",
                    examples = listOf(
                        MurphyExample("How many times have you been to London?", "Londonda necha marta bo'lgansiz?", "How many times"),
                        MurphyExample("I've read this book twice.", "Bu kitobni ikki marta o'qiganman.", "twice / three times"),
                        MurphyExample("It's the first time she has driven a car.", "U birinchi marta mashina haydashi.", "the first time + Present Perfect")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u17_ex1",
                    exerciseNumber = "17.1",
                    taskType = "CHOICE",
                    question = "______ you ever ______ to Australia?",
                    options = listOf("Have / been", "Did / go", "Were / been", "Have / gone"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Have / been",
                    explanationUzbek = "Biror mamlakatda umringiz davomida bo'lganmisiz deb so'rash uchun 'Have you ever been to...?' iborasi ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u17_ex2",
                    exerciseNumber = "17.2",
                    taskType = "CHOICE",
                    question = "Where is Mark? - He is on holiday. He ______ to Italy.",
                    options = listOf("has gone", "has been", "went", "is gone"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has gone",
                    explanationUzbek = "U hozir ta'tilda va u yerda bo'lib turibdi (hali qaytmagan), shuning uchun 'has gone' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u17_ex3",
                    exerciseNumber = "17.3",
                    taskType = "CHOICE",
                    question = "Welcome back, Sarah! Where ______ you ______?",
                    options = listOf("have / been", "have / gone", "did / go", "were / been"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have / been",
                    explanationUzbek = "Sarah qaytib kelgani uchun (Welcome back!) unga 'Where have you been?' (Qayerda bo'lib qaytding?) deb murojaat qilinadi."
                ),
                MurphyExerciseItem(
                    id = "u17_ex4",
                    exerciseNumber = "17.4",
                    taskType = "CHOICE",
                    question = "I love this film. I ______ it three times.",
                    options = listOf("have seen", "saw", "see", "am seeing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have seen",
                    explanationUzbek = "Hayot davomida bir necha marta ('three times') ko'rilganini ifodalash uchun Present Perfect 'have seen' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u17_ex5",
                    exerciseNumber = "17.5",
                    taskType = "CHOICE",
                    question = "Helen is a vegetarian. She ______ meat in her life.",
                    options = listOf("has never eaten", "never has ate", "hasn't never eaten", "didn't never eat"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has never eaten",
                    explanationUzbek = "'Helen' uchun 'has never eaten' to'g'ri. 'hasn't never' noto'g'ri, chunki ikkita inkor birga kelmaydi."
                )
            )
        ),

        // UNIT 18
        MurphyUnit(
            unitNumber = 18,
            title = "How long have you...? (Present Perfect 4)",
            subtitleUzbek = "Qancha vaqtdan beri? O'tmishdan boshlanib hozir ham davom etayotgan holatlar (for / since)",
            groupName = "3-Guruh: Present Perfect (15–20)",
            keyTakeawaysUzbek = listOf(
                "O'tmishda boshlangan va HOZIR HAM davom etayotgan ish-harakat yoki holatlar uchun Present Perfect ishlatiladi.",
                "Taqqoslang: 'Dan is in hospital' (Dan hozir shifoxonada) VS 'Dan has been in hospital since Monday' (Dan dushanbadan beri shifoxonada yotibdi).",
                "❌ O'QUVCHILARNING ENG KATTA XATOSI: 'Dan is in hospital for three days' deb aytish mumkin emas! Faqat 'has been' bo'ladi!",
                "'for' davomiylikni bildiradi: for three days, for ten years, for a long time.",
                "'since' boshlang'ich nuqtani bildiradi: since Monday, since 2015, since 9 o'clock."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Dan kasalxonada misoli (Present Simple vs Present Perfect)",
                    formula = "Dan is in hospital now BUT He has been in hospital since Monday",
                    explanationUzbek = "Dan hozir shifoxonada (Dan is in hospital now).\nU dushanba kuni kasalxonaga tushgan edi. Bugun esa payshanba.\n\nSavol: U qancha vaqtdan beri shifoxonada?\nBiz 'He is in hospital since Monday' deya olmaymiz!\nChunki dushanbadan to hozirgacha bo'lgan davr haqida gap ketyapti.\nShuning uchun: 'He HAS BEEN in hospital since Monday / for three days' deymiz.",
                    examples = listOf(
                        MurphyExample("Dan is in hospital. He has been in hospital since Monday.", "Den shifoxonada. U dushanbadan beri shifoxonada.", "is -> has been"),
                        MurphyExample("We know each other. We have known each other for ten years.", "Biz bir-birimizni taniymiz. Biz o'n yildan beri tanishmiz.", "know -> have known"),
                        MurphyExample("They are married. They have been married for five years.", "Ular oila qurgan. Ular besh yildan beri turmush qurishgan.", "are -> have been"),
                        MurphyExample("She lives in London. She has lived there all her life.", "U Londonda yashaydi. Butun umri davomida u yerda yashagan.", "has lived")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "How long have you...? (Savol tuzish)",
                    formula = "How long have you + V3 ...? | How long has he / she + V3 ...?",
                    explanationUzbek = "Kimgadir 'Qancha vaqtdan beri mashinangiz bor?', 'Qancha vaqtdan beri bu yerda ishlaysiz?' deb savol berish uchun 'How long have you...?' ishlatiladi:\n\n• How long have you had your car? (Qancha vaqtdan beri mashinangiz bor?)\n• I've had it for two years. (Ikki yildan beri).\n\n• How long has she known Mike? (U Maykni qancha vaqtdan beri taniydi?)\n• She has known him since 2018. (2018-yildan beri).",
                    examples = listOf(
                        MurphyExample("How long have you been in London? - For six months.", "Londonda qancha vaqtdan beri turibsiz? - Olti oydan beri.", "How long have you been"),
                        MurphyExample("How long have they known each other? - Since school.", "Ular bir-birlarini qancha vaqtdan beri taniydilar? - Maktab paytidan beri.", "have known"),
                        MurphyExample("How long has David had that car? - For a year.", "Devidda ana u mashina qancha vaqtdan beri bor? - Bir yildan beri.", "has had")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Present Continuous vs Present Perfect Continuous",
                    formula = "is raining now VS has been raining all day",
                    explanationUzbek = "Xuddi shunday davomli zamonlarda ham:\n\n• 'It is raining' = Hozir yomg'ir yog'yapti.\n• 'It has been raining for two hours' = Ikki soatdan beri yomg'ir yog'yapti (boshlangan va hali to'xtagani yo'q).\n\n• 'I am studying English' = Hozir ingliz tilini o'rganyapman.\n• 'I have been studying English for six months' = Olti oydan beri o'rganib kelyapman.",
                    examples = listOf(
                        MurphyExample("It is raining now.", "Hozir yomg'ir yog'yapti.", "Present Continuous"),
                        MurphyExample("It has been raining since lunchtime.", "Tushlikdan beri yomg'ir yog'yapti.", "Present Perfect Continuous"),
                        MurphyExample("How long have you been learning English?", "Qancha vaqtdan beri ingliz tilini o'rganasiz?", "have been learning")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u18_ex1",
                    exerciseNumber = "18.1",
                    taskType = "CHOICE",
                    question = "Dan is in hospital. He ______ in hospital since Monday.",
                    options = listOf("has been", "is", "was", "has be"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has been",
                    explanationUzbek = "'since Monday' (dushanbadan beri) iborasi bo'lgani sababli 'is' emas, balki Present Perfect 'has been' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u18_ex2",
                    exerciseNumber = "18.2",
                    taskType = "CHOICE",
                    question = "How long ______ you ______ each other? - For about five years.",
                    options = listOf("have / known", "do / know", "did / know", "are / knowing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have / known",
                    explanationUzbek = "'Qancha vaqtdan beri bir-biringizni taniysiz?' savoli Present Perfect da 'How long have you known' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u18_ex3",
                    exerciseNumber = "18.3",
                    taskType = "CHOICE",
                    question = "Paul and Linda are married. They ______ married for ten years.",
                    options = listOf("have been", "are", "were", "has been"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have been",
                    explanationUzbek = "O'n yillik davomiylik uchun 'They have been married' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u18_ex4",
                    exerciseNumber = "18.4",
                    taskType = "CHOICE",
                    question = "I have a car. I ______ it for two years.",
                    options = listOf("have had", "have", "had", "am having"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have had",
                    explanationUzbek = "'have' fe'lining Present Perfect shakli 'have had' (have yordamchi fe'l, had esa V3 asosiy fe'l)."
                ),
                MurphyExerciseItem(
                    id = "u18_ex5",
                    exerciseNumber = "18.5",
                    taskType = "CHOICE",
                    question = "It ______ all day. The streets are wet.",
                    options = listOf("has been raining", "is raining", "rained", "was raining"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has been raining",
                    explanationUzbek = "'all day' (kun bo'yi) to'xtamay yog'ayotgani uchun 'has been raining' to'g'ri."
                )
            )
        ),

        // UNIT 19
        MurphyUnit(
            unitNumber = 19,
            title = "for  since  ago",
            subtitleUzbek = "'for' (davomida), 'since' (...dan beri) va 'ago' (...oldin) so'zlarining aniq farqlari",
            groupName = "3-Guruh: Present Perfect (15–20)",
            keyTakeawaysUzbek = listOf(
                "'for' + vaqt oralig'i (period of time): for 3 days, for 2 years, for an hour, for a long time.",
                "'since' + boshlang'ich aniq sana/payt (start of the period): since Monday, since 9 o'clock, since 2010, since Christmas.",
                "'since' faqat Present Perfect bilan keladi (since yesterday, since I arrived).",
                "'ago' = 'hozirdan oldin' (before now). U FAQAT Past Simple (oddiy o'tgan zamon) bilan keladi!",
                "Qiyoslang: 'I bought this car two years ago' (Past Simple) VS 'I have had this car for two years' (Present Perfect)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "FOR = vaqt oralig'i (a period of time)",
                    formula = "for + 2 hours / 3 days / 6 months / 5 years / a long time / ages",
                    explanationUzbek = "'For' biror ish-harakat qancha vaqt davom etganini (vaqtning umumiy miqdorini) ko'rsatadi:\n\n• for two hours (ikki soat davomida)\n• for 20 minutes (20 daqiqa davomida)\n• for five days (besh kun davomida)\n• for six months (olti oy davomida)\n• for 50 years (50 yil davomida)\n• for a long time (uzoq vaqt davomida)\n\nMisol: 'Jane has been in Ireland for three days.' (Jeyn uch kundan beri Irlandiyada).",
                    examples = listOf(
                        MurphyExample("Richard has been in Canada for six months.", "Richard olti oydan beri Kanadada.", "for six months"),
                        MurphyExample("We've been waiting for two hours.", "Biz ikki soatdan beri kutyapmiz.", "for two hours"),
                        MurphyExample("I've lived in this house for a long time.", "Ushbu uyda uzoq vaqtdan beri yashayman.", "for a long time")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "SINCE = boshlanish nuqtasi (start of a period)",
                    formula = "since + 9 o'clock / Monday / 12 May / 2015 / Christmas / I arrived",
                    explanationUzbek = "'Since' ish-harakat aynan qaysi paytda, soat nechada, qaysi kunda yoki yilda boshlanganini (boshlang'ich nuqtasini) ko'rsatadi:\n\n• since 8 o'clock (soat 8 dan beri)\n• since Monday (dushanbadan beri)\n• since 1995 (1995-yildan beri)\n• since Christmas (Rojdestvodan beri)\n• since I was ten years old (o'n yoshimdan beri)\n\nMisol: 'Jane has been in Ireland since Monday.' (Jeyn dushanba kunidan beri Irlandiyada).",
                    examples = listOf(
                        MurphyExample("Richard has been in Canada since January.", "Richard yanvar oyidan beri Kanadada.", "since January"),
                        MurphyExample("We've been waiting since 9 o'clock.", "Soat 9 dan beri kutyapmiz.", "since 9 o'clock"),
                        MurphyExample("I've known Paul since we were at school.", "Polni maktabda o'qigan paytimizdan beri taniyman.", "since + past sentence")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "AGO = oldin (before now) - Faqat Past Simple bilan!",
                    formula = "vaqt + AGO (two years ago, ten minutes ago)",
                    explanationUzbek = "'Ago' o'zbek tiliga 'oldin' deb tarjima qilinadi va u voqeaning hozirdan qancha vaqt oldin bo'lib o'tganini bildiradi. U doimo vaqtdan keyin keladi va FAQAT Past Simple bilan ishlatiladi:\n\n• Susan started her new job three weeks ago. (Syuzan 3 hafta oldin yangi ishini boshladi).\n• When did Tom leave? - Ten minutes ago. (Tom qachon ketdi? - O'n daqiqa oldin).\n\n⚠️ Eslab qoling:\n'for' va 'since' bilan ko'pincha Present Perfect keladi.\n'ago' bilan esa har doim Past Simple (did / went / started) keladi!",
                    examples = listOf(
                        MurphyExample("Jill arrived in Ireland three days ago.", "Jill uch kun oldin Irlandiyaga yetib keldi.", "arrived (Past Simple) + ago"),
                        MurphyExample("I had lunch an hour ago.", "Bir soat oldin tushlik qildim.", "had (Past Simple) + ago"),
                        MurphyExample("Life was very different a hundred years ago.", "Yuz yil oldin hayot juda boshqacha edi.", "was + ago")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u19_ex1",
                    exerciseNumber = "19.1",
                    taskType = "CHOICE",
                    question = "Jill has been in Ireland ______ three days.",
                    options = listOf("for", "since", "ago", "in"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "'three days' (3 kun) vaqt oralig'i (period of time) bo'lgani uchun 'for' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u19_ex2",
                    exerciseNumber = "19.2",
                    taskType = "CHOICE",
                    question = "Jill has been in Ireland ______ Monday.",
                    options = listOf("since", "for", "ago", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "since",
                    explanationUzbek = "'Monday' (dushanba) aniq boshlanish nuqtasi bo'lgani uchun 'since' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u19_ex3",
                    exerciseNumber = "19.3",
                    taskType = "CHOICE",
                    question = "Jill arrived in Ireland three days ______.",
                    options = listOf("ago", "for", "since", "before"),
                    correctOptionIndex = 0,
                    correctAnswerText = "ago",
                    explanationUzbek = "'arrived' Past Simple da bo'lib, hozirdan 3 kun oldin sodir bo'lgani uchun 'ago' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u19_ex4",
                    exerciseNumber = "19.4",
                    taskType = "CHOICE",
                    question = "We have known each other ______ a long time.",
                    options = listOf("for", "since", "ago", "during"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "'a long time' (uzoq vaqt) davomiylik bo'lgani uchun 'for a long time' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u19_ex5",
                    exerciseNumber = "19.5",
                    taskType = "CHOICE",
                    question = "Nobody has lived in that house ______ 2010.",
                    options = listOf("since", "for", "ago", "at"),
                    correctOptionIndex = 0,
                    correctAnswerText = "since",
                    explanationUzbek = "Aniq yil ko'rsatilgani uchun (2010) 'since 2010' bo'ladi."
                )
            )
        ),

        // UNIT 20
        MurphyUnit(
            unitNumber = 20,
            title = "I have done (Present Perfect) and I did (Past Simple)",
            subtitleUzbek = "Present Perfect va Past Simple ning tub qiyosiy farqi (Hozir bilan bog'liq vaqt vs Tugallangan vaqt)",
            groupName = "3-Guruh: Present Perfect (15–20)",
            keyTakeawaysUzbek = listOf(
                "Tugallangan vaqt (Finished time: yesterday, last year, in 2010, two hours ago, when) uchun FAQAT Past Simple ishlatiladi.",
                "Tugallanmagan vaqt (Unfinished time / Connection with now: today, this week, recently, ever, never, already, yet) uchun Present Perfect ishlatiladi.",
                "❌ Hech qachon 'yesterday' yoki 'last week' bilan Present Perfect ishlatmang! (❌ I have seen him yesterday EMAS! ✅ I saw him yesterday).",
                "'When...?' va 'What time...?' savollari har doim Past Simple da bo'ladi! (❌ When have you arrived? EMAS! ✅ When did you arrive?).",
                "Oltin taqqoslash: 'I have lost my key' (= hozir ham yo'q) VS 'I lost my key yesterday, but now I have found it'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Finished time (Past Simple) vs Unfinished time (Present Perfect)",
                    formula = "Past Simple: yesterday, last week, ago | Present Perfect: today, this week, recently",
                    explanationUzbek = "Ingliz tilidagi eng nozik va eng ko'p xato qilinadigan farq:\n\n1. Past Simple (did) — Vaqt butunlay o'tib ketgan, tugallangan (Finished time):\n• yesterday (kecha)\n• last night / last week (o'tgan kecha/o'tgan hafta)\n• in 2015 (2015-yilda)\n• six months ago (olti oy oldin)\n\n2. Present Perfect (have done) — Vaqt hali davom etmoqda yoki hozirgi daqiqa bilan bog'langan (Unfinished time):\n• today (bugun - kun hali tugamadi)\n• this week (bu hafta - hafta hali davom etmoqda)\n• this year (bu yil)\n• recently (yaqinda)",
                    examples = listOf(
                        MurphyExample("It didn't rain yesterday. / It hasn't rained this week.", "Kecha yomg'ir yog'madi. / Bu hafta yomg'ir yog'gani yo'q.", "yesterday (Past Simple) vs this week (Pres Perf)"),
                        MurphyExample("Did you see Tom yesterday? / Have you seen Tom today?", "Kecha Tomni ko'rdingizmi? / Bugun Tomni ko'rdingizmi?", "yesterday vs today"),
                        MurphyExample("I lived in Paris for two years (now I live in London).", "Men Parijda 2 yil yashaganman (hozir u yerda yashamayman).", "Past Simple: tugagan"),
                        MurphyExample("I have lived in London for two years (I live there now).", "Men 2 yildan beri Londonda yashayapman (hozir ham shu yerdaman).", "Present Perfect: davom etyapti")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "When...? va What time...? (Doimo Past Simple!)",
                    formula = "WHEN / WHAT TIME + did + ega + V1? (NOT have you done)",
                    explanationUzbek = "'Qachon?' (When) yoki 'Soat nechada?' (What time) deb so'raganimizda, biz o'tmishdagi ANIQ bir vaqtni so'rayotgan bo'lamiz. Shu sababli bu savollar bilan hech qachon Present Perfect ishlatilmaydi, faqat Past Simple bo'ladi!\n\n❌ Xato: When have you bought your car?\n✅ To'g'ri: When DID you BUY your car?\n\n❌ Xato: What time has he arrived?\n✅ To'g'ri: What time DID he ARRIVE?",
                    examples = listOf(
                        MurphyExample("When did you arrive? - At 7 o'clock.", "Qachon yetib keldingiz? - Soat 7 da.", "When did you arrive?"),
                        MurphyExample("What time did your train leave?", "Poyezdingiz soat nechada jo'nadi?", "What time did it leave?"),
                        MurphyExample("When did they get married?", "Ular qachon turmush qurishgan?", "When did they get married?")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Kalit yo'qolishi misoli (Present result vs Past fact)",
                    formula = "I've lost my key (I haven't got it now) VS I lost my key yesterday (I found it later)",
                    explanationUzbek = "Ikki vaziyatni tasavvur qiling:\n\n1. 'I have lost my key.'\n-> Men kalitimni yo'qotib qo'ydim. Bu hozirgi paytga taalluqli, chunki kalit hozir ham yo'q, uyga kirolmay turibman!\n\n2. 'I lost my key yesterday, but now I've found it.'\n-> Kecha kalitimni yo'qotib qo'ygan edim (o'tmishdagi fakt), lekin hozir uni topib oldim.",
                    examples = listOf(
                        MurphyExample("I've lost my key. Can you help me look for it?", "Kalitimni yo'qotib qo'ydim. Qidirishga yordam bera olasizmi?", "Hozir ham yo'q"),
                        MurphyExample("I lost my key yesterday, but luckily I found it.", "Kecha kalitimni yo'qotgan edim, xayriyatki uni topdim.", "Past Simple: yesterday"),
                        MurphyExample("Did you have a good holiday in Spain?", "Ispaniyadagi ta'tilingiz yaxshi o'tdimi?", "Ta'til tugagan: Past Simple")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u20_ex1",
                    exerciseNumber = "20.1",
                    taskType = "CHOICE",
                    question = "I ______ my key yesterday, but I found it this morning.",
                    options = listOf("lost", "have lost", "have losen", "am losing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "lost",
                    explanationUzbek = "'yesterday' (kecha) tugallangan vaqt bo'lgani uchun faqat Past Simple 'lost' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u20_ex2",
                    exerciseNumber = "20.2",
                    taskType = "CHOICE",
                    question = "______ you ______ Tom yesterday?",
                    options = listOf("Did / see", "Have / seen", "Were / seeing", "Did / saw"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Did / see",
                    explanationUzbek = "'yesterday' bilan hech qachon Present Perfect kelmaydi! Faqat Past Simple: 'Did you see'."
                ),
                MurphyExerciseItem(
                    id = "u20_ex3",
                    exerciseNumber = "20.3",
                    taskType = "CHOICE",
                    question = "When ______ you ______ your new phone?",
                    options = listOf("did / buy", "have / bought", "were / buy", "do / bought"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did / buy",
                    explanationUzbek = "'When' (Qachon) savoli aniq o'tmish vaqtini so'ragani uchun har doim Past Simple da bo'ladi: 'When did you buy'."
                ),
                MurphyExerciseItem(
                    id = "u20_ex4",
                    exerciseNumber = "20.4",
                    taskType = "CHOICE",
                    question = "It hasn't rained ______ week.",
                    options = listOf("this", "last", "yesterday", "ago"),
                    correctOptionIndex = 0,
                    correctAnswerText = "this",
                    explanationUzbek = "'hasn't rained' Present Perfect bo'lgani uchun tugallanmagan vaqt 'this week' bilan keladi."
                ),
                MurphyExerciseItem(
                    id = "u20_ex5",
                    exerciseNumber = "20.5",
                    taskType = "CHOICE",
                    question = "What time ______ they ______ last night?",
                    options = listOf("did / arrive", "have / arrived", "were / arrived", "did / arrived"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did / arrive",
                    explanationUzbek = "'What time' va 'last night' o'tgan aniq vaqtni so'raydi, shuning uchun 'What time did they arrive?' to'g'ri."
                )
            )
        )
    )
}
