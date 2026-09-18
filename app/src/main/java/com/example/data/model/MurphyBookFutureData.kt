package com.example.data.model

object MurphyBookFutureDatabase {

    val UNITS_FUTURE: List<MurphyUnit> = listOf(
        // UNIT 25
        MurphyUnit(
            unitNumber = 25,
            title = "What are you doing tomorrow?",
            subtitleUzbek = "Kelasi zamon rejalari: am/is/are + -ing (Oldindan rejalashtirilgan va kelishilgan ishlar)",
            groupName = "5-Guruh: Future / Kelasi zamon (25–28)",
            keyTakeawaysUzbek = listOf(
                "Present Continuous (am/is/are + -ing) faqat hozir emas, balki KELASI ZAMONDAGI aniq kelishilgan rejalarda ham ishlatiladi.",
                "'I'm playing tennis tomorrow' = Men do'stim bilan kelishib qo'yganman, kort band qilingan.",
                "Kelajak haqida so'rash: 'What are you doing tonight?' (Bugun oqshomda nima ish qilyapsiz / rejangiz bormi?).",
                "Transport jadvallari, poyezd/samolyot reyslari uchun Present Simple (I do) ishlatiladi: 'The train leaves at 7:30'.",
                "Odamlarning shaxsiy rejalari uchun Present Continuous (I am doing) afzal ko'riladi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Kelasi zamondagi aniq rejalashtirilgan ishlar",
                    formula = "am / is / are + fe'l-ing + kelasi zamon vaqti",
                    explanationUzbek = "Agar siz biror ishni oldindan rejalashtirgan, boshqalar bilan kelishib qo'ygan yoki bilet/joy olib qo'ygan bo'lsangiz, Present Continuous ishlatiladi:\n\n• I'm playing tennis with John tomorrow. (Ertaga Jon bilan tennis o'ynamoqchiman / o'ynayman — Jon bilan kelishib qo'yganmiz).\n• Sophie is going to the dentist on Friday. (Sofi juma kuni tish shifokoriga boryapti — qabulga yozilgan).\n• We're having a party next weekend. Can you come? (Keyingi dam olish kunlari ziyofat qilyapmiz).\n• What are you doing tomorrow evening? (Ertaga kechqurun nima qilyapsiz?).",
                    examples = listOf(
                        MurphyExample("I'm playing tennis tomorrow.", "Ertaga tennis o'ynayman.", "Aniq kelishilgan reja"),
                        MurphyExample("Sophie is going to the dentist on Friday.", "Sofi juma kuni tish shifokoriga boryapti.", "Qabulga yozilgan"),
                        MurphyExample("What are you doing tonight? - I'm staying at home.", "Bugun kechqurun nima qilyapsiz? - Uyda qolaman.", "Shaxsiy reja"),
                        MurphyExample("We're leaving tomorrow at 9:00.", "Biz ertaga soat 9:00 da yo'lga chiqyapmiz.", "Jo'nab ketish rejasi")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "I'm doing (reja) vs I do (jadval va rasmiy dastur)",
                    formula = "Odamlar rejasi: I am doing | Poezd/kino jadvali: It starts/leaves (Present Simple)",
                    explanationUzbek = "Taqqoslang:\n\n1. Odamlarning kelajakdagi shaxsiy rejalari uchun Present Continuous:\n• I'm meeting Rachel at the station at 8:00.\n• What time are you leaving tomorrow?\n\n2. Poyezdlar, avtobuslar, samolyotlar va kinoseanslar jadvali (timetables / schedules) uchun esa Present Simple (oddiy hozirgi zamon):\n• The train arrives at 7:30 tomorrow morning.\n• What time does the film start tonight?\n• The new term starts next Monday.",
                    examples = listOf(
                        MurphyExample("The concert starts at 7.30 tonight.", "Konsert bugun kechqurun 7:30 da boshlanadi.", "Jadval (Present Simple)"),
                        MurphyExample("I'm going to a concert tonight.", "Men bugun kechqurun konsertga boryapman.", "Mening rejam (Present Continuous)"),
                        MurphyExample("What time does the train leave?", "Poyezd soat nechada jo'naydi?", "Poyezd jadvali (Present Simple)"),
                        MurphyExample("What time are you arriving in London?", "Siz Londonga soat nechada yetib borasiz?", "Odamning rejasi (Continuous)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Do NOT use 'will' for arrangements",
                    formula = "Xato: 'I will play tennis tomorrow' (kelishilgan reja bo'lsa) -> To'g'ri: 'I'm playing tennis tomorrow'",
                    explanationUzbek = "Agar siz allaqachon biror narsani hal qilib qo'ygan bo'lsangiz, 'will' ishlatilmaydi:\n\n• Alex is getting married next month. (To'g'ri! To'y kuni belgilangan, taklifnomalar tarqatilgan).\n(NOT Alex will get married).\n\n• I'm not working tomorrow, so we can go out. (To'g'ri!\n(NOT I won't work tomorrow).",
                    examples = listOf(
                        MurphyExample("Alex is getting married next month.", "Aleks kelasi oy uylanyapti.", "Oldindan hal qilingan"),
                        MurphyExample("Are you meeting Bill this evening?", "Bugun kechqurun Bill bilan uchrashyapsanmi?", "Kelishuv so'ralmoqda")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u25_ex1",
                    exerciseNumber = "25.1",
                    taskType = "CHOICE",
                    question = "I ______ tennis with Paul tomorrow. We booked the court yesterday.",
                    options = listOf("am playing", "play", "will to play", "played"),
                    correctOptionIndex = 0,
                    correctAnswerText = "am playing",
                    explanationUzbek = "Kort band qilib bo'lingan va barchasi kelishilgan bo'lsa, Present Continuous 'am playing' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u25_ex2",
                    exerciseNumber = "25.2",
                    taskType = "CHOICE",
                    question = "What time ______ the train leave tomorrow morning?",
                    options = listOf("does", "is", "will to", "are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "does",
                    explanationUzbek = "Poyezd jadvali (timetable) uchun Present Simple ishlatiladi: 'What time does the train leave?'"
                ),
                MurphyExerciseItem(
                    id = "u25_ex3",
                    exerciseNumber = "25.3",
                    taskType = "CHOICE",
                    question = "What ______ you doing on Saturday evening?",
                    options = listOf("are", "do", "will", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are",
                    explanationUzbek = "Kelasi shanba oqshomidagilik reja so'ralmoqda: 'What are you doing on Saturday evening?'"
                ),
                MurphyExerciseItem(
                    id = "u25_ex4",
                    exerciseNumber = "25.4",
                    taskType = "CHOICE",
                    question = "I'm tired. I ______ not working tomorrow.",
                    options = listOf("am", "do", "will", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "am",
                    explanationUzbek = "'I am not working tomorrow' — ertaga ishlamaslik rejasi."
                ),
                MurphyExerciseItem(
                    id = "u25_ex5",
                    exerciseNumber = "25.5",
                    taskType = "CHOICE",
                    question = "The movie ______ at 8:15 tonight.",
                    options = listOf("starts", "is starting", "started", "start"),
                    correctOptionIndex = 0,
                    correctAnswerText = "starts",
                    explanationUzbek = "Kinoteatr dasturi rasmiy jadval bo'lgani sababli 3-shaxs birlikda 'starts' bo'ladi."
                )
            )
        ),

        // UNIT 26
        MurphyUnit(
            unitNumber = 26,
            title = "I'm going to...",
            subtitleUzbek = "Kelasi zamon: am/is/are going to + V1 (Qat'iy niyat, reja va yaqqol bashorat)",
            groupName = "5-Guruh: Future / Kelasi zamon (25–28)",
            keyTakeawaysUzbek = listOf(
                "'I am going to do something' = Men biror ishni qilishga qaror qilganman, mening niyatim bor.",
                "Niyat va qaror: 'I'm going to buy some books tomorrow' (Ertaga bir nechta kitob sotib olmoqchiman).",
                "Yaqqol belgiga asoslangan bashorat: 'Look at the sky! It's going to rain' (Bulutlarga qara! Hozir yomg'ir yog'adi).",
                "Xavf-xatarni ko'rganda: 'Watch out! That box is going to fall!' (Ehtiyot bo'l, quti yiqilib tushmoqchi).",
                "'was/were going to' = Qilmoqchi edim, lekin qilmadim: 'I was going to call you, but I forgot'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "I'm going to do something (Niyat va qaror)",
                    formula = "am / is / are + going to + fe'l (V1)",
                    explanationUzbek = "Biror ishni qilishni oldindan niyat qilgan bo'lsangiz 'going to' qo'llaniladi:\n\n• I'm going to buy some books tomorrow. (Ertaga kitob sotib olmoqchiman — qaror qildim).\n• Sarah is going to sell her car. (Sara mashinasini sotmoqchi).\n• What are you going to wear to the party tonight? (Bugun oqshomdagi mehmondorchilikka nima kiymoqchisiz?)\n• I'm not going to have breakfast this morning. I'm not hungry. (Bugun ertalab nonushta qilmoqchi emasman).",
                    examples = listOf(
                        MurphyExample("I'm going to buy a new phone.", "Yangi telefon sotib olmoqchiman.", "Niyat va qaror"),
                        MurphyExample("Sarah is going to sell her car.", "Sara mashinasini sotmoqchi.", "Oldindan qilingan niyat"),
                        MurphyExample("Are you going to invite Martin? - No, I'm not.", "Martinni taklif qilmoqchimisan? - Yo'q.", "Savol shakli")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Ko'rinib turgan dalil asosidagi bashorat",
                    formula = "Dalil bor -> It is going to happen!",
                    explanationUzbek = "Agar hozir ko'rinib turgan aniq alomat (evidence) bo'lsa va voqea ro'y berishi muqarrar ekanligi sezilsa 'going to' ishlatiladi:\n\n• Look at the sky! It's going to rain. (Osmonga qara! Qop-qora bulutlar — hozir yomg'ir yog'adi).\n• Oh dear! It's 9 o'clock and I'm not ready. I'm going to be late! (Soat 9 bo'ldi, hali tayyor emasman. Kech qoladigan bo'ldim!).\n• Watch out! That glass is going to fall! (Ehtiyot bo'l! Stol chetidagi stakan tushib ketmoqchi!).",
                    examples = listOf(
                        MurphyExample("Look at those black clouds! It's going to rain.", "Anavi qora bulutlarga qara! Yomg'ir yog'adi.", "Aniq belgi ko'rinib turibdi"),
                        MurphyExample("I feel terrible. I think I'm going to be sick.", "O'zimni yomon his qilyapman. Ko'nglim aynimoqchi.", "Hozirgi alomat"),
                        MurphyExample("Look out! You're going to drop that plate.", "Ehtiyot bo'l! Likopchani tushirib yuborasan.", "Yaqqol xavf")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "was / were going to do (Qilmoqchi edim, lekin...)",
                    formula = "was / were + going to + V1",
                    explanationUzbek = "O'tgan zamonda biror ishni niyat qilgan edingiz, ammo rejangiz amalga oshmay qolgan bo'lsa 'was/were going to' deyiladi:\n\n• We were going to travel by train, but then we decided to go by car. (Biz poyezdda bormoqchi edik, ammo keyin mashinada ketishga qaror qildik).\n• I was going to phone you, but I didn't have your number. (Senga qo'ng'iroq qilmoqchi edim, lekin raqaming yo'q edi).",
                    examples = listOf(
                        MurphyExample("I was going to buy that coat, but it was too expensive.", "U paltoni sotib olmoqchi edim, lekin juda qimmat ekan.", "Niyat bor edi, lekin olinmadi"),
                        MurphyExample("Peter was going to take the exam, but he changed his mind.", "Piter imtihon topshirmoqchi edi, lekin fikridan qaytdi.", "Amalga oshmagan reja")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u26_ex1",
                    exerciseNumber = "26.1",
                    taskType = "CHOICE",
                    question = "Look at the dark clouds! It ______ rain.",
                    options = listOf("is going to", "will to", "goes to", "was going to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is going to",
                    explanationUzbek = "Ko'z oldimizdagi qora bulutlar aniq dalil bo'lgani sababli 'is going to rain' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u26_ex2",
                    exerciseNumber = "26.2",
                    taskType = "CHOICE",
                    question = "I have decided. I ______ buy a new laptop next week.",
                    options = listOf("am going to", "buy", "going to", "was going to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "am going to",
                    explanationUzbek = "Qaror qabul qilib bo'lingan niyat: 'I am going to buy'."
                ),
                MurphyExerciseItem(
                    id = "u26_ex3",
                    exerciseNumber = "26.3",
                    taskType = "CHOICE",
                    question = "Watch out! You ______ drop that heavy box!",
                    options = listOf("are going to", "will to", "is going to", "were going to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are going to",
                    explanationUzbek = "Darhol ro'y berayotgan xatarli vaziyatda 'You are going to drop' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u26_ex4",
                    exerciseNumber = "26.4",
                    taskType = "CHOICE",
                    question = "What ______ you going to do this weekend?",
                    options = listOf("are", "do", "is", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are",
                    explanationUzbek = "'you' olmoshi bilan 'are you going to do' tuzilishi ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u26_ex5",
                    exerciseNumber = "26.5",
                    taskType = "CHOICE",
                    question = "I ______ phone you last night, but my phone battery was dead.",
                    options = listOf("was going to", "am going to", "will", "go to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was going to",
                    explanationUzbek = "Kecha niyat qilgan, ammo amalga oshmay qolgan reja: 'I was going to phone you'."
                )
            )
        ),

        // UNIT 27
        MurphyUnit(
            unitNumber = 27,
            title = "will / shall 1",
            subtitleUzbek = "Kelasi zamon: will + V1 (Kelajak haqidagi xulosalar, fikrlar va kutilmagan lahzaviy qarorlar)",
            groupName = "5-Guruh: Future / Kelasi zamon (25–28)",
            keyTakeawaysUzbek = listOf(
                "WILL kelajakdagi voqealar, xulosalar va umumiy bashoratlar uchun ishlatiladi: 'Tomorrow it will be sunny'.",
                "Qisqartma shakli: 'll (I'll, you'll, she'll, they'll).",
                "Inkor shakli: will not -> WON'T (I won't be here tomorrow).",
                "Suhbat paytidagi LAHZAVIY QAROR (Instant decision): 'The phone is ringing. - I'll answer it!'",
                "Fikr bildirish: 'I think ... will' va 'I don't think ... will' iboralari bilan ko'p keladi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "will + V1 (Kelajakdagi fakt va bashorat)",
                    formula = "will + fe'lning boshlang'ich shakli (V1) | Inkor: won't",
                    explanationUzbek = "Kelajakda sodir bo'ladigan voqeliklar haqida ma'lumot berish yoki bashorat qilishda 'will' ishlatiladi:\n\n• Sue travels a lot. Today she is in London. Tomorrow she'll be in Rome. Next week she'll be in Tokyo.\n• You can call me this evening. I'll be at home.\n• Leave the old bread in the garden. The birds will eat it.\n• We'll probably go out this evening. (Ehtimol bugun kechqurun ko'chaga chiqarmiz).\n\nInkor shakli: will not = won't:\n• I can see you're busy. I won't stay long. (Bandligingizni ko'rib turibman. Ko'p qolmayman).",
                    examples = listOf(
                        MurphyExample("Tomorrow she'll be in Rome.", "Ertaga u Rimda bo'ladi.", "she will = she'll"),
                        MurphyExample("It will be cold tomorrow morning.", "Ertaga ertalab sovuq bo'ladi.", "Ob-havo bashorati"),
                        MurphyExample("I won't be at work tomorrow.", "Ertaga ishda bo'lmayman.", "won't = will not"),
                        MurphyExample("Don't worry, you'll pass the exam.", "Xavotir olmang, imtihondan o'tasiz.", "Dalda va bashorat")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Lahzaviy qaror: I'll... (Hozirgina qabul qilingan qaror)",
                    formula = "Ayni suhbat onida qaror qilish -> I'll...",
                    explanationUzbek = "Agar siz biror ishni oldindan rejalashtirmagan bo'lsangiz, lekin aynan gaplashayotgan soniyangizda qilishga qaror qilsangiz, 'I'll...' deyiladi:\n\n• 'The phone is ringing.' - 'OK, I'll answer it.' (Telefon jiringlayapti. - Mayli, men ko'taraman!).\n• 'It's cold in here.' - 'I'll close the window.' (Bu yer sovuq ekan. - Derazani yopib qo'yaman).\n• 'I'm tired. I think I'll go to bed now.' (Charchadim. Menimcha hozir uxlagani yotaman).\n\n⚠️ Oldindan kelishilgan ishlarga 'I'll' ISHLATMANG! Rejaga 'I am doing' ishlatiladi.",
                    examples = listOf(
                        MurphyExample("The doorbell is ringing. - I'll get it!", "Eshik qo'ng'irog'i jalyapti. - Men ochaman!", "Lahzaviy qaror"),
                        MurphyExample("I'm thirsty. I'll have a glass of water.", "Chanqadim. Bir stakan suv ichaman.", "Shu onda qaror qabul qilindi"),
                        MurphyExample("I feel tired. I think I'll stay at home tonight.", "Charchaganman. Menimcha bugun uyda qolaman.", "I think I'll...")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "I think ... will va I don't think ... will",
                    formula = "I think + ega + will | I don't think + ega + will",
                    explanationUzbek = "O'z taxminingizni aytishda ko'pincha 'I think' yoki 'I don't think' ishlatiladi:\n\n• I think Diana will pass the exam. (Menimcha Diana imtihondan o'tadi).\n• I don't think it will rain this afternoon. (Menimcha bugun tushdan keyin yomg'ir yog'masa kerak).\n• Do you think the test will be difficult? (Sizningcha test qiyin bo'larmikan?).",
                    examples = listOf(
                        MurphyExample("I think they'll win the match.", "Menimcha ular o'yinda g'alaba qozonishadi.", "I think + will"),
                        MurphyExample("I don't think it will rain.", "O'ylashimcha yomg'ir yog'maydi.", "I don't think + will"),
                        MurphyExample("Do you think you'll finish today?", "Bugun tugataman deb o'ylaysanmi?", "Savol: Do you think...?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u27_ex1",
                    exerciseNumber = "27.1",
                    taskType = "CHOICE",
                    question = "'The phone is ringing.' - 'Don't worry, I ______ it.'",
                    options = listOf("will answer", "answer", "am answering", "answered"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will answer",
                    explanationUzbek = "Ayni paytda qabul qilingan lahzaviy qaror uchun 'I'll / I will answer' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u27_ex2",
                    exerciseNumber = "27.2",
                    taskType = "CHOICE",
                    question = "I'm feeling tired. I think I ______ to bed early tonight.",
                    options = listOf("will go", "go", "went", "going to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will go",
                    explanationUzbek = "'I think I will go' — suhbat chog'ida tug'ilgan fikr va qaror."
                ),
                MurphyExerciseItem(
                    id = "u27_ex3",
                    exerciseNumber = "27.3",
                    taskType = "CHOICE",
                    question = "I ______ be at home tomorrow evening. I have to work late.",
                    options = listOf("won't", "am not", "will not to", "don't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "won't",
                    explanationUzbek = "Kelasi zamon inkor shakli: 'won't be at home' (will not be)."
                ),
                MurphyExerciseItem(
                    id = "u27_ex4",
                    exerciseNumber = "27.4",
                    taskType = "CHOICE",
                    question = "Do you think it ______ rain tomorrow?",
                    options = listOf("will", "is", "does", "shall to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will",
                    explanationUzbek = "Kelajak haqidagi taxminiy savol: 'Do you think it will rain tomorrow?'"
                ),
                MurphyExerciseItem(
                    id = "u27_ex5",
                    exerciseNumber = "27.5",
                    taskType = "CHOICE",
                    question = "Goodbye! I ______ see you on Monday.",
                    options = listOf("'ll", "am", "do", "'m"),
                    correctOptionIndex = 0,
                    correctAnswerText = "'ll",
                    explanationUzbek = "'I'll see you on Monday' (I will see you) — kelajakdagi oddiy fakt/va'da."
                )
            )
        ),

        // UNIT 28
        MurphyUnit(
            unitNumber = 28,
            title = "will / shall 2",
            subtitleUzbek = "will vs going to taqqoslash, Shall I...? va Shall we...? (Taklif va maslahat)",
            groupName = "5-Guruh: Future / Kelasi zamon (25–28)",
            keyTakeawaysUzbek = listOf(
                "Farq: 'I'm going to do' = Avvaldan qaror qilganman. 'I'll do' = Hozirgina qaror qildim.",
                "Shall I...? = Menga ruxsat berasizmi / qilib beraymi? (Yordam taklif qilish: Shall I open the window?).",
                "Shall we...? = Keling, birga qilaylikmi? (Birgalikdagi taklif: Shall we go to the cinema?).",
                "'Shall' faqat 'I' va 'We' olmoshlari bilan taklif yoki maslahat so'rashda ishlatiladi.",
                "Boshqa shaxslar (you, he, she, they) bilan hech qachon 'shall' ishlatilmaydi, faqat 'will' ishlatiladi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "will vs going to (Taqqoslang)",
                    formula = "going to = avvaldan reja qilingan | will = shu daqiqada qaror qilindi",
                    explanationUzbek = "Taqqoslang:\n\n1. 'I'm going to do...':\n• 'Gary phoned while you were out.'\n  - 'Yes, I know. I'm going to call him back.' (Bilar edim. Men unga qo'ng'iroq qilmoqchiman — avvaldan niyat qilganman).\n\n2. 'I'll do...':\n• 'Gary phoned while you were out.'\n  - 'Oh, really? I didn't know. I'll call him back now.' (Rostdanmi? Bilmas edim. Hozir unga qo'ng'iroq qilaman — hozirgina xabar topdim va qaror qildim!).",
                    examples = listOf(
                        MurphyExample("I'm going to buy some coffee tomorrow.", "Ertaga kofe sotib olmoqchiman.", "Avvaldan niyat bor (going to)"),
                        MurphyExample("We have no milk. - Really? I'll go and get some.", "Sutimiz qolmabdi. - Rostdanmi? Borib olib kelaman.", "Shu zahotiyoq qaror qabul qilindi (I'll)"),
                        MurphyExample("Are you going to invite Mark? - Yes, I am.", "Markni taklif qilmoqchimisan? - Ha.", "Rejalashtirilgan niyat")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Shall I...? (Qilib beraymi? / Yordam taklifi)",
                    formula = "Shall I + V1 ...? (Taklif va ko'mak)",
                    explanationUzbek = "Birovga yordam taklif qilishda yoki 'shu ishni qilishimni xohlaysizmi?' deb so'rashda 'Shall I...?' iborasi qo'llaniladi:\n\n• It's very warm in here. Shall I open the window? (Bu yer juda issiq ekan. Derazani ochib yuboraymi?)\n• Shall I carry your bag for you? (Sumkangizni ko'tarishib yuboraymi?)\n• 'What shall I cook for dinner?' - 'Let's make pasta.' (Kechki ovqatga nima pishiray?).",
                    examples = listOf(
                        MurphyExample("Shall I open the door for you?", "Eshikni ochib beraymi?", "Taklif: Shall I...?"),
                        MurphyExample("Shall I help you with those bags?", "Anavi sumkalarga yordamlashib yuboraymi?", "Ko'mak taklifi"),
                        MurphyExample("What time shall I come tomorrow?", "Ertaga soat nechada kelay?", "Maslahat so'rash")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Shall we...? (Keling, birga qilaylikmi?)",
                    formula = "Shall we + V1 ...? = Let's...",
                    explanationUzbek = "Biror ishni birgalikda qilishni taklif qilganda 'Shall we...?' ishlatiladi:\n\n• It's a nice day. Shall we go for a walk? (Ajoyib kun bo'lyapti. Sayrga chiqamizmi?)\n• Where shall we go for lunch today? (Bugun tushlikka qayerga boramiz?)\n• What time shall we meet tonight? (Bugun oqshomda soat nechada uchrashamiz?).",
                    examples = listOf(
                        MurphyExample("Shall we go to the cinema tonight?", "Bugun kechqurun kinoga boramizmi?", "Shall we...? (Birgalikdagi taklif)"),
                        MurphyExample("Where shall we go this weekend?", "Bu dam olish kunlari qayerga boramiz?", "Reja tuzish: Where shall we...?"),
                        MurphyExample("Let's go, shall we?", "Kettik, bo'ladimi?", "Qo'shimcha tasdiq")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u28_ex1",
                    exerciseNumber = "28.1",
                    taskType = "CHOICE",
                    question = "It's cold in this room. ______ I close the window?",
                    options = listOf("Shall", "Will", "Do", "Would"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Shall",
                    explanationUzbek = "O'z xizmati va yordamini taklif qilishda 'Shall I close the window?' deb so'raladi."
                ),
                MurphyExerciseItem(
                    id = "u28_ex2",
                    exerciseNumber = "28.2",
                    taskType = "CHOICE",
                    question = "'Did you buy bread?' - 'Oh no, I forgot. I ______ and get some now.'",
                    options = listOf("will go", "am going to go", "go", "went"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will go",
                    explanationUzbek = "Unutib qo'yganini bilib, shu zahoti qaror qildi: 'I will go and get some now'."
                ),
                MurphyExerciseItem(
                    id = "u28_ex3",
                    exerciseNumber = "28.3",
                    taskType = "CHOICE",
                    question = "It's a lovely day. ______ we go for a walk in the park?",
                    options = listOf("Shall", "Will", "Do", "Are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Shall",
                    explanationUzbek = "Birgalikdagi sayr taklifi: 'Shall we go for a walk?'"
                ),
                MurphyExerciseItem(
                    id = "u28_ex4",
                    exerciseNumber = "28.4",
                    taskType = "CHOICE",
                    question = "'Why are you turning on the TV?' - 'I ______ watch the news.'",
                    options = listOf("am going to", "will", "shall", "watched"),
                    correctOptionIndex = 0,
                    correctAnswerText = "am going to",
                    explanationUzbek = "Televizorni ataylab yoqqan odamning maqsadi va niyati bor: 'I am going to watch the news'."
                ),
                MurphyExerciseItem(
                    id = "u28_ex5",
                    exerciseNumber = "28.5",
                    taskType = "CHOICE",
                    question = "Where ______ we go on holiday this summer?",
                    options = listOf("shall", "are", "do", "will to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "shall",
                    explanationUzbek = "'we' bilan birgalikdagi ta'til maslahati: 'Where shall we go on holiday?'"
                )
            )
        )
    )
}
