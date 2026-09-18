package com.example.data.model

object MurphyBookFinalUnitsData {

    val UNITS_FINAL_COLLECTION: List<MurphyUnit> = listOf(
        // UNIT 111
        MurphyUnit(
            unitNumber = 111,
            title = "say, tell, speak, talk (To'rtta so'zlashuv fe'lining farqi)",
            subtitleUzbek = "Bir qarashda bir xil ko'ringan so'zlarning nozik farqlari va qo'llanish o'rni",
            groupName = "18-Guruh: Final Units & Mastery (111–115)",
            keyTakeawaysUzbek = listOf(
                "SAY: Biror gapni yoki so'zni aytmoq (He said that he was tired). Ketidan shaxs to'g'ridan-to'g'ri kelmaydi: He said to me.",
                "TELL: Birovga axborot bermoq / aytib bermoq. TELL dan keyin DOIM KIMGA ekanligi aytiladi: tell me, tell him, tell us.",
                "SPEAK: Til bilish (speak English) va rasmiy nutq so'zlash (speak with the boss).",
                "TALK: Ikki yoki undan ortiq odamning samimiy suhbatlashishi (We talked for hours about life).",
                "IBORALAR: tell a lie (yolg'on gapirmoq), tell the truth (haqiqatni aytmoq), tell a story (ertak aytib bermoq)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Say vs Tell (Oddiy va amaliy qoida)",
                    formula = "SAY (nimanidir) | TELL + KIMGA (shaxs)",
                    explanationUzbek = "O'zbek tilida 'aytdi' deb bir xil gapiraveramiz, lekin ingliz tilida bu ikki fe'l qat'iy ajratiladi:\n\n1) TELL (Kimga aytilgani doim bor):\n• She told ME a secret. (U menga sir aytdi)\n• He told US to wait outside. (U bizga tashqarida kutishimizni aytdi)\n• Did you tell ANYONE? (Birortasiga aytdingizmi?)\n\n2) SAY (Faqat gap/fikr aytiladi, kimga ekanligi asosiy emas):\n• She said: 'I'm tired'.\n• What did he say? (U nima dedi?)\n• He said TO me that... (Agar odam kelsa 'TO' shart, 'He said me' — QAT'IYAN XATO!).",
                    examples = listOf(
                        MurphyExample("He told me the whole story.", "U menga butun voqeani so'zlab berdi.", "told me (shaxs bor)"),
                        MurphyExample("She said that she would call later.", "U keyinroq qo'ng'iroq qilishini aytdi.", "said that..."),
                        MurphyExample("Always tell the truth!", "Doimo haqiqatni ayting!", "tell the truth")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Speak vs Talk (Rasmiy nutq va erkin suhbat)",
                    formula = "speak (til/rasmiy) vs talk (o'zaro suhbat)",
                    explanationUzbek = "1) SPEAK:\n• Tillar bilan FAQAT speak: Do you speak English? (Do you talk English deb bo'lmaydi!).\n• Telefonda: 'Can I speak to Mr. Brown, please?' (Janob Braun bilan gaplashsam maylimi?)\n\n2) TALK:\n• Do'stlar bilan gurunglashish, suhbat qurish:\n• We sat in a café and talked about our school days. (Qahvaxonada o'tirib maktab davrimiz haqida suhbatlashdik)\n• Stop talking and listen! (Gapirishni to'xtating va quloq soling!)",
                    examples = listOf(
                        MurphyExample("How many languages can you speak?", "Nechta tilda gaplasha olasiz?", "speak a language"),
                        MurphyExample("We need to talk about the project.", "Biz loyiha haqida gaplashib olishimiz kerak.", "talk about...")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u111_ex1",
                    exerciseNumber = "111.1",
                    taskType = "CHOICE",
                    question = "My grandmother ______ us an interesting fairy tale every night.",
                    options = listOf("told", "said", "spoke", "talked"),
                    correctOptionIndex = 0,
                    correctAnswerText = "told",
                    explanationUzbek = "'us' (bizga) shaxsi bor va ertak aytib berish 'tell a story' bo'ladi: 'told us'."
                ),
                MurphyExerciseItem(
                    id = "u111_ex2",
                    exerciseNumber = "111.2",
                    taskType = "CHOICE",
                    question = "Can you ______ Italian or Spanish?",
                    options = listOf("speak", "talk", "say", "tell"),
                    correctOptionIndex = 0,
                    correctAnswerText = "speak",
                    explanationUzbek = "Tilda gaplashish uchun faqat 'speak' fe'li ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u111_ex3",
                    exerciseNumber = "111.3",
                    taskType = "CHOICE",
                    question = "What did he ______ to you when you arrived?",
                    options = listOf("say", "tell", "speak", "talk"),
                    correctOptionIndex = 0,
                    correctAnswerText = "say",
                    explanationUzbek = "'say to you' iborasi to'g'ri (tell to you deb bo'lmaydi)."
                ),
                MurphyExerciseItem(
                    id = "u111_ex4",
                    exerciseNumber = "111.4",
                    taskType = "CHOICE",
                    question = "They sat in the garden and ______ for two hours.",
                    options = listOf("talked", "told", "said", "spoke"),
                    correctOptionIndex = 0,
                    correctAnswerText = "talked",
                    explanationUzbek = "O'zaro samimiy suhbatlashib o'tirish 'talked' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u111_ex5",
                    exerciseNumber = "111.5",
                    taskType = "CHOICE",
                    question = "You must never ______ lies to your parents.",
                    options = listOf("tell", "say", "speak", "talk"),
                    correctOptionIndex = 0,
                    correctAnswerText = "tell",
                    explanationUzbek = "Yolg'on gapirish barqaror iborada 'tell lies' bo'ladi."
                )
            )
        ),

        // UNIT 112
        MurphyUnit(
            unitNumber = 112,
            title = "make and do (Eng mashhur birikmalar va amaliy farqlar)",
            subtitleUzbek = "Do'konda, uyda va ishda: qachon 'make', qachon 'do' ishlatiladi?",
            groupName = "18-Guruh: Final Units & Mastery (111–115)",
            keyTakeawaysUzbek = listOf(
                "ASOSIY QONUN: MAKE = qo'l yoki aql bilan yangi narsa yaratish; DO = vazifalar, mashg'ulotlar va umumiy ishlarni bajarish.",
                "MAKE BIRIKMALARI: make a mistake (xato qilmoq), make money (pul topmoq), make friends (do'stlashmoq), make a decision (qaror qabul qilmoq), make an appointment (uchrashuv belgilamoq).",
                "DO BIRIKMALARI: do homework (vazifa qilmoq), do the shopping (bozorlik qilmoq), do the dishes (idish yuvmoq), do someone a favour (yaxshilik qilmoq), do sports (sport bilan shug'ullanmoq).",
                "UMUMIY HARAKAT: Hech narsa qilmayapman -> 'I am not DOING anything' ('making' deb bo'lmaydi!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Kundalik uy va hayot ishlari (Do ro'yxati)",
                    formula = "DO + vazifalar / ish-faoliyat",
                    explanationUzbek = "Bajariladigan kundalik ro'zg'or va o'qish yumushlarida doimo DO keladi:\n\n• do the cooking (ovqat qilish)\n• do the cleaning (tozalash ishlari)\n• do the washing / laundry (kirlarni yuvish)\n• do the shopping (bozorlik qilish)\n• do business (tijorat qilish)\n• do an exam / a test (imtihon topshirish)\n• do your hair (sochingizni turmaklash)\n\nMisol: 'I have to do a lot of things today.' (Bugun ko'p ishlarni qilishim kerak)",
                    examples = listOf(
                        MurphyExample("Could you do me a favour?", "Menga bir yaxshilik qila olasizmi?", "do a favour"),
                        MurphyExample("I always do the shopping on Saturdays.", "Men doim shanba kunlari bozorlik qilaman.", "do the shopping")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Yangi natija va ijtimoiy harakatlar (Make ro'yxati)",
                    formula = "MAKE + natija / yangilik",
                    explanationUzbek = "Oldin mavjud bo'lmagan natijani vujudga keltirish:\n\n• make a phone call (telefon qo'ng'irog'i qilmoq)\n• make an excuse (bahona topmoq)\n• make a promise (va'da bermoq)\n• make a list (ro'yxat tuzmoq)\n• make progress (yutuqqa erishmoq, rivojlanmoq)\n• make an effort (kuch sarflamoq/harakat qilmoq)\n• make sense (mantiqqa to'g'ri kelmoq): 'That makes sense!' (Bu mantiqan to'g'ri!)",
                    examples = listOf(
                        MurphyExample("It's easy to make friends when you are friendly.", "Samimiy bo'lsangiz, do'st orttirish oson bo'ladi.", "make friends"),
                        MurphyExample("He made a promise that he wouldn't be late.", "U kechikmaslikka va'da berdi.", "made a promise")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u112_ex1",
                    exerciseNumber = "112.1",
                    taskType = "CHOICE",
                    question = "I need to ______ an appointment with the dentist.",
                    options = listOf("make", "do", "have done", "put"),
                    correctOptionIndex = 0,
                    correctAnswerText = "make",
                    explanationUzbek = "Shifokor bilan uchrashuv belgilash 'make an appointment' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u112_ex2",
                    exerciseNumber = "112.2",
                    taskType = "CHOICE",
                    question = "Who usually ______ the cooking in your family?",
                    options = listOf("does", "makes", "creates", "produces"),
                    correctOptionIndex = 0,
                    correctAnswerText = "does",
                    explanationUzbek = "Oshxona yumushi sifatida 'do the cooking' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u112_ex3",
                    exerciseNumber = "112.3",
                    taskType = "CHOICE",
                    question = "Don't be afraid to ______ mistakes; that is how we learn.",
                    options = listOf("make", "do", "take", "give"),
                    correctOptionIndex = 0,
                    correctAnswerText = "make",
                    explanationUzbek = "Xatoga yo'l qo'yish doim 'make mistakes' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u112_ex4",
                    exerciseNumber = "112.4",
                    taskType = "CHOICE",
                    question = "What are you ______ this evening?",
                    options = listOf("doing", "making", "creating", "acting"),
                    correctOptionIndex = 0,
                    correctAnswerText = "doing",
                    explanationUzbek = "Umumiy nima bilan mashg'ul bo'lish 'doing' orqali so'raladi."
                ),
                MurphyExerciseItem(
                    id = "u112_ex5",
                    exerciseNumber = "112.5",
                    taskType = "CHOICE",
                    question = "His explanation doesn't ______ any sense.",
                    options = listOf("make", "do", "have", "take"),
                    correctOptionIndex = 0,
                    correctAnswerText = "make",
                    explanationUzbek = "'make sense' — mantiqqa to'g'ri kelmoq, tushunarli bo'lmoq."
                )
            )
        ),

        // UNIT 113
        MurphyUnit(
            unitNumber = 113,
            title = "like vs would like / prefer (Taklif, xohish va xushmuomalalik)",
            subtitleUzbek = "'Yoqtiraman' bilan 'Hozir istayman' o'rtasidagi katta farq",
            groupName = "18-Guruh: Final Units & Mastery (111–115)",
            keyTakeawaysUzbek = listOf(
                "I LIKE: Doimiy yoqtirish, qiziqish (I like tea = Men umuman choyni yaxshi ko'raman).",
                "I WOULD LIKE ('d like): Ayni damdagi istak, muloyim so'rov (I would like a cup of tea = Hozir bir piyola choy ichishni istayman).",
                "TAKLIF QILISH: 'Would you like some coffee?' (Qahva xohlaysizmi? — 'Do you like coffee' desa, 'Qahvani yoqtirasizmi' degan umumiy savol bo'lib qoladi).",
                "PREFER: Afzal ko'rmoq (I prefer tea to coffee = Choyni qahvadan afzal ko'raman).",
                "WOULD RATHER: ...qilishni ma'qul ko'raman (I would rather stay at home = Uyda qolishni afzal bilaman)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Do you like ... ? vs Would you like ... ?",
                    formula = "Do you like (Yoqtirasizmi?) vs Would you like (Hozir xohlaysizmi?)",
                    explanationUzbek = "Mehmon kutayotganda yoki restoranda bu ikkalasini adashtirsangiz kulgili holat bo'ladi:\n\n1) Do you like chocolate?\n— Yes, I love it. (Siz umuman shokoladni yoqtirasizmi? — Ha, sevaman)\n\n2) Would you like some chocolate?\n— Yes, please! (Hozir biroz shokolad yeyishni xohlaysizmi? — Ha, iltimos beriring!)\n\nDemak, birovga biror narsa yoki yordam taklif qilganda doim 'WOULD YOU LIKE' ishlatiladi:\n• Would you like to sit down? (O'tirishni xohlaysizmi?)\n• What would you like to drink? (Nima ichishni xohlaysiz?)",
                    examples = listOf(
                        MurphyExample("Would you like a glass of water?", "Bir stakan suv xohlaysizmi?", "Would you like... (taklif)"),
                        MurphyExample("I like reading books in my free time.", "Bo'sh vaqtimda kitob o'qishni yoqtiraman.", "I like (odatim)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Prefer va Would prefer (Afzallik)",
                    formula = "prefer A TO B | would prefer TO DO something",
                    explanationUzbek = "O'zbek tilida 'qahvadan ko'ra choyni afzal ko'raman' deganda ingliz tilida 'TO' qo'yiladi:\n\n• I prefer tea TO coffee. (Choyni qahvadan afzal ko'raman)\n• I prefer living in a city to living in the country.\n\n• Would you prefer to go now or wait? (Hozir ketishni ma'qul ko'rasizmi yoki kutishnimi?)\n• I'd prefer to wait. (Kutishni afzal bilaman)",
                    examples = listOf(
                        MurphyExample("Which do you prefer, summer or winter?", "Qaysi birini afzal ko'rasiz, yoznimi yoki qishni?", "prefer..."),
                        MurphyExample("I prefer cats to dogs.", "Men mushuklarni itlardan afzal ko'raman.", "cats TO dogs")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u113_ex1",
                    exerciseNumber = "113.1",
                    taskType = "CHOICE",
                    question = "______ you like another cup of tea?",
                    options = listOf("Would", "Do", "Are", "Did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Would",
                    explanationUzbek = "Birovga mehmondo'stlik bilan taklif qilish: 'Would you like...'."
                ),
                MurphyExerciseItem(
                    id = "u113_ex2",
                    exerciseNumber = "113.2",
                    taskType = "CHOICE",
                    question = "I ______ playing football, but today I'm too tired to play.",
                    options = listOf("like", "would like", "prefer to", "rather"),
                    correctOptionIndex = 0,
                    correctAnswerText = "like",
                    explanationUzbek = "Umumiy sevimli mashg'ulot bo'lgani uchun 'I like playing' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u113_ex3",
                    exerciseNumber = "113.3",
                    taskType = "CHOICE",
                    question = "I prefer travelling by train ______ travelling by bus.",
                    options = listOf("to", "than", "from", "against"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Prefer bilan taqqoslashda 'to' qo'yiladi: 'prefer A to B'."
                ),
                MurphyExerciseItem(
                    id = "u113_ex4",
                    exerciseNumber = "113.4",
                    taskType = "CHOICE",
                    question = "Excuse me, I ______ to speak to the hotel manager.",
                    options = listOf("would like", "like", "am like", "prefer"),
                    correctOptionIndex = 0,
                    correctAnswerText = "would like",
                    explanationUzbek = "Muloyim iltimos shakli: 'I would like to speak'."
                ),
                MurphyExerciseItem(
                    id = "u113_ex5",
                    exerciseNumber = "113.5",
                    taskType = "CHOICE",
                    question = "What ______ to order for dinner, sir?",
                    options = listOf("would you like", "do you like", "are you liking", "did you like"),
                    correctOptionIndex = 0,
                    correctAnswerText = "would you like",
                    explanationUzbek = "Ofitsiant buyurtma qabul qilganda: 'What would you like to order?'."
                )
            )
        ),

        // UNIT 114
        MurphyUnit(
            unitNumber = 114,
            title = "used to (do) (Ilgari qilardim, ammo hozir qilmayman)",
            subtitleUzbek = "O'tmishdagi eski odatlar va o'zgarib ketgan hayotiy holatlar",
            groupName = "18-Guruh: Final Units & Mastery (111–115)",
            keyTakeawaysUzbek = listOf(
                "USED TO: O'tmishda muntazam qilingan, lekin HOZIR TO'XTATILGAN odat va harakatlar.",
                "O'ZBEKCHA MA'NOSI: 'Ilgari ... qilar edim / bo'lar edim'.",
                "FORMULA: Ega + used to + V1 (asliy fe'l). Masalan: I used to smoke (Ilgari chekar edim, hozir chekmayman!).",
                "INKOR SHAKLI: didn't use to (e'tibor bering, 'use' bo'ladi, 'd' tushib qoladi).",
                "SO'ROQ SHAKLI: Did you use to ... ? (Ilgari shunday qilar edingizmi?).",
                "HOZIRGI ZAMONDA YO'Q: Hozirgi odatlar uchun 'I usually do' deyiladi ('I use to do' DEB BO'LMAYDI!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "'Used to' ning sehri (O'tmish vs Bugun)",
                    formula = "used to + V1 (faqat o'tmishdagi odat)",
                    explanationUzbek = "Tasavvur qiling, bolaligingizda sochingiz uzun edi, hozir esa kalta. Yoki ilgari velosiped minardingiz, hozir mashinangiz bor:\n\n• I used to have long hair. (Ilgari sochim uzun bo'lardi — lekin hozir unday emas)\n• He used to live in London. (U ilgari Londonda yashagan — hozir boshqa yerda)\n• We used to play together every day. (Biz har kuni birga o'ynardik)\n\nOddiy Past Simple dan farqi: 'used to' aytilishi bilan tinglovchi 'demak hozir bu narsa yo'q ekan' deb darhol tushunadi!",
                    examples = listOf(
                        MurphyExample("Dave used to work in a factory. Now he works in a bank.", "Deyv ilgari fabrikada ishlardi. Hozir u bankda ishlaydi.", "used to work"),
                        MurphyExample("There used to be a cinema here many years ago.", "Ko'p yillar oldin bu yerda kinoteatr bo'lar edi.", "There used to be...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Inkor va So'roq shakllari",
                    formula = "didn't use to | Did you use to ... ?",
                    explanationUzbek = "Inkor va so'roqda 'did' yordamga keladi va 'used' o'zining asl 'use' holiga qaytadi:\n\n• I didn't use to like coffee, but now I love it.\n(Ilgari qahvani yoqtirmasdim, lekin hozir juda yaxshi ko'raman)\n\n• Did you use to play the piano when you were younger?\n(Yoshligingizda pianino chalarmidingiz?)\n\n⚠️ Eslab qoling: Hozirgi zamon uchun used to ishlatilmaydi: 'I usually get up at 7' deyiladi.",
                    examples = listOf(
                        MurphyExample("She didn't use to eat vegetables.", "U ilgari sabzavot yemas edi.", "didn't use to eat"),
                        MurphyExample("Did you use to walk to school?", "Maktabga piyoda borarmidingiz?", "Did you use to walk?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u114_ex1",
                    exerciseNumber = "114.1",
                    taskType = "CHOICE",
                    question = "I ______ glasses, but now I wear contact lenses.",
                    options = listOf("used to wear", "use to wear", "was wearing", "wear"),
                    correctOptionIndex = 0,
                    correctAnswerText = "used to wear",
                    explanationUzbek = "Ilgari ko'zoynak taqardim, hozir esa linza: 'used to wear'."
                ),
                MurphyExerciseItem(
                    id = "u114_ex2",
                    exerciseNumber = "114.2",
                    taskType = "CHOICE",
                    question = "We ______ in a small village, but we moved to the city last year.",
                    options = listOf("used to live", "use to live", "are used to live", "living"),
                    correctOptionIndex = 0,
                    correctAnswerText = "used to live",
                    explanationUzbek = "O'tmishda qishloqda yashagan: 'used to live'."
                ),
                MurphyExerciseItem(
                    id = "u114_ex3",
                    exerciseNumber = "114.3",
                    taskType = "CHOICE",
                    question = "He ______ like spicy food, but now he eats it all the time.",
                    options = listOf("didn't use to", "didn't used to", "not used to", "doesn't use to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't use to",
                    explanationUzbek = "Inkor shaklida d harfi tushib 'didn't use to' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u114_ex4",
                    exerciseNumber = "114.4",
                    taskType = "CHOICE",
                    question = "______ you use to have a pet when you were a child?",
                    options = listOf("Did", "Do", "Were", "Have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Did",
                    explanationUzbek = "O'tgan zamon so'rog'i: 'Did you use to...'."
                ),
                MurphyExerciseItem(
                    id = "u114_ex5",
                    exerciseNumber = "114.5",
                    taskType = "CHOICE",
                    question = "This building ______ a hotel, but now it is an office building.",
                    options = listOf("used to be", "used to", "was used to be", "is used to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "used to be",
                    explanationUzbek = "Ilgari mehmonxona bo'lar edi: 'used to be'."
                )
            )
        ),

        // UNIT 115
        MurphyUnit(
            unitNumber = 115,
            title = "Real-Life Situations & Everyday English (Do'kon, mehmonxona va aeroportda)",
            subtitleUzbek = "Butun Murphy kursining yakuniy 115-darsi: Hayotiy vaziyatlarda erkin muloqot",
            groupName = "18-Guruh: Final Units & Mastery (111–115)",
            keyTakeawaysUzbek = listOf(
                "TABRIKLAYMIZ! Siz Raymond Murphy 'Essential Grammar in Use' kitobining barcha 115 ta Unitini to'liq o'rganib chiqdingiz!",
                "DO'KONDA: 'How much is this?' (Bu qancha turadi?), 'Can I pay by card?' (Karta bilan to'lasam bo'ladimi?).",
                "KO'CHADA YO'L SO'RASH: 'Excuse me, could you tell me the way to the station?' (Bekatga olib boradigan yo'lni aytib yubora olmaysizmi?).",
                "RESTORANDA: 'Can I have the menu / bill, please?' (Menyu / hisobni olib kela olasizmi?).",
                "AEROPORTDA: 'Where is gate 4?' (4-chi chiqish darvozasi qayerda?), 'Here is my passport' (Mana pasportim).",
                "YAKUNIY TAVSIYA: Grammatikani bilsangiz, ingliz tilida gapirishdan aslo uyalmang — siz endi mukammal poydevorga egasiz!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Chet elda eng ko'p kerak bo'ladigan 10 ta oltin jumla",
                    formula = "Excuse me + Muloyim savol / iltimos",
                    explanationUzbek = "Ushbu iboralar sizga sayohatda yoki chet elliklar bilan uchrashganda eng yaxshi yordamchingiz bo'ladi:\n\n1) Excuse me, could you help me, please?\n(Kechirasiz, menga yordam bera olasizmi?)\n\n2) How much does this cost?\n(Bu qancha turadi?)\n\n3) Do you accept credit cards?\n(Kredit kartalarni qabul qilasizmi?)\n\n4) Could you speak more slowly, please?\n(Iltimos, biroz sekinroq gapira olasizmi?)\n\n5) Could you repeat that, please?\n(Buni yana bir bor qaytara olasizmi?)\n\n6) Where is the nearest restroom / pharmacy?\n(Eng yaqin hojatxona / dorixona qayerda?)",
                    examples = listOf(
                        MurphyExample("Could I have a receipt, please?", "Iltimos, chekni bera olasizmi?", "Could I have..."),
                        MurphyExample("How do I get to the city centre?", "Shahar markaziga qanday borsa bo'ladi?", "How do I get to...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Kurs yakuni: Kelajakdagi muvaffaqiyat sari",
                    formula = "115 ta Unit -> Har kuni 15 daqiqa takrorlash -> Erkin so'zlashuv!",
                    explanationUzbek = "Siz 1-darsdagi 'am / is / are' dan boshlab, 115-darsdagi erkin so'zlashuv qoidalarigacha ulkan masofani bosib o'tdingiz!\n\nIlovadan foydalanishda davom eting:\n• Qidiruv tugmasidan (Search) foydalaning;\n• Xato qilgan testlaringizni qayta yechib ko'ring;\n• O'rgangan qoidalaringizni do'stlaringizga o'rgating — bu bilimlarni 10 barobar mustahkamlaydi.\n\nSizga ingliz tilini o'rganishda va hayotiy maqsadlaringizda ulkan zafarlar tilaymiz!",
                    examples = listOf(
                        MurphyExample("You have successfully completed all 115 Units!", "Siz barcha 115 ta Unitni muvaffaqiyatli yakunladingiz!", "Master of Grammar!"),
                        MurphyExample("Believe in yourself and keep practicing.", "O'zingizga ishoning va amaliyotni davom ettiring.", "The sky is the limit!")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u115_ex1",
                    exerciseNumber = "115.1",
                    taskType = "CHOICE",
                    question = "Restoranda hisob-kitobni so'rash uchun qaysi ibora to'g'ri?",
                    options = listOf("Could we have the bill, please?", "Give me money, please.", "How much is my eating?", "I want to pay now fast."),
                    correctOptionIndex = 0,
                    correctAnswerText = "Could we have the bill, please?",
                    explanationUzbek = "Eng muloyim va to'g'ri shakl: 'Could we have the bill, please?'."
                ),
                MurphyExerciseItem(
                    id = "u115_ex2",
                    exerciseNumber = "115.2",
                    taskType = "CHOICE",
                    question = "Ko'chada notanish odamdan yo'l so'rashni qanday boshlash kerak?",
                    options = listOf("Excuse me, ...", "Hey you, ...", "Listen to me, ...", "Tell me now, ..."),
                    correctOptionIndex = 0,
                    correctAnswerText = "Excuse me, ...",
                    explanationUzbek = "Birovning e'tiborini muloyim tortish uchun 'Excuse me' deb boshlanadi."
                ),
                MurphyExerciseItem(
                    id = "u115_ex3",
                    exerciseNumber = "115.3",
                    taskType = "CHOICE",
                    question = "Do'konda kiyim sotib olayotganda 'Buni kiyib ko'rsam maylimi?' qanday aytiladi?",
                    options = listOf("Can I try this on?", "Can I wear this now?", "Can I dress this?", "Can I put this out?"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Can I try this on?",
                    explanationUzbek = "Kiyimni kiyib ko'rish 'try on' iborasi bilan: 'Can I try this on?'."
                ),
                MurphyExerciseItem(
                    id = "u115_ex4",
                    exerciseNumber = "115.4",
                    taskType = "CHOICE",
                    question = "Suhbatdosh juda tez gapirganda, uni sekinlashtirish uchun nima deyiladi?",
                    options = listOf(
                        "Could you speak more slowly, please?",
                        "Stop speaking so fast!",
                        "You talk too quick!",
                        "Speak down, please!"
                    ),
                    correctOptionIndex = 0,
                    correctAnswerText = "Could you speak more slowly, please?",
                    explanationUzbek = "Muloyim iltimos: 'Could you speak more slowly, please?'."
                ),
                MurphyExerciseItem(
                    id = "u115_ex5",
                    exerciseNumber = "115.5",
                    taskType = "CHOICE",
                    question = "Nechta Unitni to'liq yakunladingiz?",
                    options = listOf("115 Units", "50 Units", "80 Units", "100 Units"),
                    correctOptionIndex = 0,
                    correctAnswerText = "115 Units",
                    explanationUzbek = "Butun Raymond Murphy 'Essential Grammar in Use' kursi to'liq 115 ta Unitdan iborat!"
                )
            )
        )
    )
}
