package com.example.data.model

object MurphyBookPastDatabase {

    val UNITS_PAST: List<MurphyUnit> = listOf(
        // UNIT 10
        MurphyUnit(
            unitNumber = 10,
            title = "was / were",
            subtitleUzbek = "'To be' fe'lining o'tgan zamon shakllari (am/is -> was, are -> were)",
            groupName = "2-Guruh: Past (10–14)",
            keyTakeawaysUzbek = listOf(
                "Hozirgi zamondagi 'am / is' o'tgan zamonda 'was' bo'ladi (I / he / she / it was).",
                "Hozirgi zamondagi 'are' o'tgan zamonda 'were' bo'ladi (we / you / they were).",
                "Inkor shakllari: wasn't (= was not) va weren't (= were not).",
                "So'roq shaklida 'was / were' egadan oldinga o'tadi: 'Were you late?' (NOT 'Did you was late?').",
                "Oltin qoida: 'was / were' bor joyda 'did' umuman ishlatilmaydi!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Hozir (Present) va O'tmish (Past) taqqoslanishi (Robert misoli)",
                    formula = "Present: am / is -> Past: was | Present: are -> Past: were",
                    explanationUzbek = "Hozirgi zamondagi 'am/is/are' holat, yosh, kasb, manzil va ob-havoni bildirsa, o'tgan zamonda aynan shu vazifani 'was/were' bajaradi. \n\nMisol: Robert hozir ishda (Now Robert is at work). Kecha tun yarmida esa u ishda emas edi. U yotoqda uyquda edi (At midnight last night he was in bed. He was asleep).",
                    examples = listOf(
                        MurphyExample("I am tired today. / I was tired last night.", "Bugun charchaganman. / Kecha tunda charchagan edim.", "am -> was"),
                        MurphyExample("Where is Kate? / Where was Kate yesterday?", "Keyt qayerda? / Kecha Keyt qayerda edi?", "is -> was"),
                        MurphyExample("The weather is good today. / The weather was good last week.", "Bugun havo yaxshi. / O'tgan hafta havo yaxshi edi.", "is -> was"),
                        MurphyExample("You are late. / You were late yesterday.", "Kech qoldingiz. / Kecha kech qolgan edingiz.", "are -> were"),
                        MurphyExample("They aren't here today. / They weren't here last Sunday.", "Ular bugun bu yerda emas. / O'tgan yakshanba ular bu yerda emas edilar.", "aren't -> weren't")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Darak va Inkor tuzilishi (Positive and Negative)",
                    formula = "I / He / She / It + was (wasn't) | We / You / They + were (weren't)",
                    explanationUzbek = "Birlik shaxslar (I, he, she, it) bilan 'was', ko'plik shaxslar (we, you, they) bilan 'were' ishlatiladi. 'You' (sen yoki siz) har doim 'were' oladi!\n\nInkor shakllar:\n• was not = wasn't [wɒznt]\n• were not = weren't [wɜːnt]",
                    examples = listOf(
                        MurphyExample("Last year Rachel was 22, so she is 23 now.", "O'tgan yili Reychel 22 yoshda edi, shuning uchun u hozir 23 yoshda.", "Yosh haqida o'tgan zamonda 'was'"),
                        MurphyExample("When I was a child, I was scared of dogs.", "Bolaligimda itlardan qo'rqar edim.", "I was a child / I was scared"),
                        MurphyExample("We were hungry after the journey, but we weren't tired.", "Safardan keyin biz och edik, lekin charchamagan edik.", "were hungry / weren't tired"),
                        MurphyExample("The hotel was comfortable, but it wasn't expensive.", "Mehmonxona qulay edi, lekin qimmat emas edi.", "was / wasn't"),
                        MurphyExample("The shops weren't open yesterday because it was a public holiday.", "Do'konlar kecha ochiq emas edi, chunki umumiy bayram edi.", "shops (they) -> weren't")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "So'roq shakli va Qisqa javoblar (Questions and Short Answers)",
                    formula = "Was / Were + Ega ...? | (So'roq so'zi) + was / were + Ega ...?",
                    explanationUzbek = "So'roq gap tuzishda 'was' yoki 'were' egadan oldinga o'tadi. Boshqa hech qanday yordamchi fe'l (masalan 'did') kerak emas!\n\nQisqa javoblar:\n• Yes, I was. / No, I wasn't.\n• Yes, they were. / No, they weren't.\n⚠️ Diqqat: 'Yes' bilan qisqartma qilinmaydi (Yes, I was - to'g'ri; Yes, I'm was deyilmaydi!).",
                    examples = listOf(
                        MurphyExample("Were you late yesterday? - No, I wasn't.", "Kecha kechikdingizmi? - Yo'q, kechikmadim.", "Were you...?"),
                        MurphyExample("Was the weather nice when you were on holiday?", "Ta'tilda bo'lganingizda ob-havo yaxshi bo'ldimi?", "Was the weather...?"),
                        MurphyExample("Why were you so angry yesterday morning?", "Kecha ertalab nega bunchalik jahlingiz chiqqan edi?", "Why were you...?"),
                        MurphyExample("Were your exams difficult? - Yes, they were.", "Imtihonlaringiz qiyin bo'ldimi? - Ha, qiyin bo'ldi.", "Were your exams...?"),
                        MurphyExample("Was Tom at work yesterday? - No, he wasn't.", "Kecha Tom ishda edimi? - Yo'q, ishda emas edi.", "Was Tom...?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u10_ex1",
                    exerciseNumber = "10.1",
                    taskType = "CHOICE",
                    question = "Today the weather is nice, but yesterday it ______ very cold.",
                    options = listOf("was", "were", "is", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was",
                    explanationUzbek = "'yesterday' (kecha) va 'it' olmoshi bo'lgani uchun 'was' to'g'ri bo'ladi ('is' ning o'tgan zamoni 'was')."
                ),
                MurphyExerciseItem(
                    id = "u10_ex2",
                    exerciseNumber = "10.1",
                    taskType = "CHOICE",
                    question = "I feel fine this morning, but I ______ very tired last night.",
                    options = listOf("am", "were", "was", "did be"),
                    correctOptionIndex = 2,
                    correctAnswerText = "was",
                    explanationUzbek = "'I' olmoshi bilan o'tgan zamonda 'was' ishlatiladi: 'I was very tired'."
                ),
                MurphyExerciseItem(
                    id = "u10_ex3",
                    exerciseNumber = "10.2",
                    taskType = "CHOICE",
                    question = "Where ______ you at 11 o'clock last Friday morning?",
                    options = listOf("was", "were", "did", "are"),
                    correctOptionIndex = 1,
                    correctAnswerText = "were",
                    explanationUzbek = "'you' olmoshi bilan doimo 'were' ishlatiladi: 'Where were you?'."
                ),
                MurphyExerciseItem(
                    id = "u10_ex4",
                    exerciseNumber = "10.3",
                    taskType = "CHOICE",
                    question = "The room was clean, but the beds ______ comfortable.",
                    options = listOf("wasn't", "weren't", "didn't", "aren't"),
                    correctOptionIndex = 1,
                    correctAnswerText = "weren't",
                    explanationUzbek = "'beds' (ko'rliklar, ya'ni they) ko'plikda bo'lgani uchun inkor shakli 'weren't' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u10_ex5",
                    exerciseNumber = "10.4",
                    taskType = "CHOICE",
                    question = "______ the camera expensive? - No, it ______.",
                    options = listOf("Was / wasn't", "Were / weren't", "Did / didn't", "Was / weren't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Was / wasn't",
                    explanationUzbek = "'camera' birlikda (it), shuning uchun so'roqda 'Was the camera...?', qisqa inkorda esa 'No, it wasn't' bo'ladi."
                )
            )
        ),

        // UNIT 11
        MurphyUnit(
            unitNumber = 11,
            title = "worked / got / went etc. (Past Simple)",
            subtitleUzbek = "Oddiy o'tgan zamon: To'g'ri (-ed) va Noto'g'ri (Irregular) fe'llar",
            groupName = "2-Guruh: Past (10–14)",
            keyTakeawaysUzbek = listOf(
                "Past Simple o'tmishda tugallangan aniq harakatlarni ifodalaydi (yesterday, last year, in 2010, ago).",
                "Muntazam fe'llarga (Regular verbs) faqat '-ed' qo'shiladi: work -> worked, clean -> cleaned.",
                "Imlo qoidalari: study -> studied, stop -> stopped, live -> lived.",
                "Noto'g'ri fe'llar (Irregular verbs) '-ed' OLMAYDI, ularning 2-shakli yodlanadi: go -> went, see -> saw, buy -> bought.",
                "Barcha shaxslar (I, you, he, she, we, they) uchun fe'lning o'tgan zamon shakli bir xil bo'ladi!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Past Simple mohiyati va To'g'ri fe'llar (Regular verbs: -ed)",
                    formula = "Ega + Fe'l-ed (V2)",
                    explanationUzbek = "O'tgan zamonda sodir bo'lib tugagan ish-harakatlar uchun fe'lning 2-shakli (Past Simple) ishlatiladi. Ko'p fe'llar to'g'ri bo'lib, ularning o'tgan zamoni '-ed' qo'shish bilan yasaladi.\n\nE'tibor bering: 3-shaxs birlikda (he/she/it) ham '-s' qo'shilmaydi, barcha shaxslar bir xil '-ed' oladi (I worked, he worked, we worked).",
                    examples = listOf(
                        MurphyExample("I clean my teeth every morning. This morning I cleaned my teeth.", "Har tong tishimni tozalayman. Bugun tongda ham tozaladim.", "clean -> cleaned"),
                        MurphyExample("Terry worked in a bank from 2010 to 2018.", "Terri 2010-yildan 2018-yilgacha bankda ishladi.", "work -> worked"),
                        MurphyExample("Yesterday it rained all morning. It stopped at lunchtime.", "Kecha tong bo'yi yomg'ir yog'di. Tushlikda to'xtadi.", "rain -> rained, stop -> stopped"),
                        MurphyExample("We enjoyed the party last night. We danced a lot.", "Kecha tunda bazmdan zavqlandik. Ko'p raqsga tushdik.", "enjoy -> enjoyed, dance -> danced"),
                        MurphyExample("My grandfather died when he was 90 years old.", "Bobom 90 yoshida vafot etgan.", "die -> died")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Imlo qoidalari (Spelling: -ed qo'shilish qoidalari)",
                    formula = "live -> lived | study -> studied | stop -> stopped",
                    explanationUzbek = "Fe'llarga '-ed' qo'shilayotganda 3 ta asosiy imlo qoidasiga amal qilinadi:\n\n1. '-e' bilan tugasa, faqat '-d' qo'shiladi: live -> lived, hope -> hoped, dance -> danced.\n2. Undosh + 'y' bo'lsa, 'y' harfi 'i' ga aylanib '-ied' bo'ladi: study -> studied, marry -> married, try -> tried. (Lekin unli + 'y' bo'lsa o'zgarmaydi: play -> played, stay -> stayed).\n3. Qisqa urg'uli unlidan keyin bitta undosh kelsa, oxirgi undosh ikkilanadi: stop -> stopped, plan -> planned, rob -> robbed.",
                    examples = listOf(
                        MurphyExample("I lived in London three years ago.", "Uch yil oldin Londonda yashaganman.", "live -> lived (-d)"),
                        MurphyExample("She studied medicine at university.", "U universitetda tibbiyotni o'rgangan.", "study -> studied (y -> ied)"),
                        MurphyExample("We played football yesterday afternoon.", "Kecha tushdan keyin futbol o'ynadik.", "play -> played (unli+y o'zgarmaydi)"),
                        MurphyExample("The train stopped at the small station.", "Poyezd kichik bekatda to'xtadi.", "stop -> stopped (p ikkilandi)"),
                        MurphyExample("They planned their trip very carefully.", "Ular sayohatlarini juda puxta rejalashtirdilar.", "plan -> planned (n ikkilandi)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Noto'g'ri fe'llar (Irregular Verbs - V2)",
                    formula = "go -> went | see -> saw | buy -> bought | have -> had",
                    explanationUzbek = "Ingliz tilidagi eng ko'p ishlatiladigan fe'llar noto'g'ri (irregular) hisoblanadi. Ular '-ed' OLMAYDI, ularning 2-shakli butunlay o'zgaradi va yod olinishi shart!\n\nEng mashhurlari:\n• go -> went (bormoq)\n• see -> saw (ko'rmoq)\n• come -> came (kelmoq)\n• buy -> bought (sotib olmoq)\n• eat -> ate (yemoq)\n• have -> had (ega bo'lmoq)\n• make -> made (yasamoq)\n• take -> took (olmoq)",
                    examples = listOf(
                        MurphyExample("I usually get up early, but yesterday I got up at 9.30.", "Odatda erta turaman, lekin kecha 9:30 da turdim.", "get -> got"),
                        MurphyExample("We went to the cinema three times last week.", "O'tgan hafta uch marta kinoga bordik.", "go -> went"),
                        MurphyExample("James came into the room, took off his coat and sat down.", "Jeyms xonaga kirdi, paltosini yechdi va o'tirdi.", "come->came, take->took, sit->sat"),
                        MurphyExample("She bought a new dress yesterday. It cost 50 dollars.", "U kecha yangi ko'ylak sotib oldi. U 50 dollar turdi.", "buy -> bought, cost -> cost"),
                        MurphyExample("I knew Sarah was busy, so I didn't disturb her.", "Men Saraning bandligini bilardim.", "know -> knew")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u11_ex1",
                    exerciseNumber = "11.1",
                    taskType = "CHOICE",
                    question = "I ______ my teeth three times yesterday.",
                    options = listOf("cleaned", "clean", "cleaning", "cleand"),
                    correctOptionIndex = 0,
                    correctAnswerText = "cleaned",
                    explanationUzbek = "'clean' to'g'ri fe'l bo'lgani uchun o'tgan zamonda oddiygina '-ed' qo'shiladi: 'cleaned'."
                ),
                MurphyExerciseItem(
                    id = "u11_ex2",
                    exerciseNumber = "11.2",
                    taskType = "CHOICE",
                    question = "The accident happened because the car didn't stop, and it ______ into the wall.",
                    options = listOf("crashed", "crash", "crashes", "crashing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "crashed",
                    explanationUzbek = "'crash' to'g'ri fe'li o'tgan zamonda 'crashed' shaklini oladi."
                ),
                MurphyExerciseItem(
                    id = "u11_ex3",
                    exerciseNumber = "11.3",
                    taskType = "CHOICE",
                    question = "Last Tuesday we ______ to the cinema and ______ a great film.",
                    options = listOf("went / saw", "goed / seed", "went / seen", "gone / saw"),
                    correctOptionIndex = 0,
                    correctAnswerText = "went / saw",
                    explanationUzbek = "'go' fe'lining o'tgan zamoni 'went', 'see' fe'lining o'tgan zamoni esa 'saw' bo'ladi (ikkisi ham noto'g'ri fe'l)."
                ),
                MurphyExerciseItem(
                    id = "u11_ex4",
                    exerciseNumber = "11.4",
                    taskType = "CHOICE",
                    question = "She ______ very hard for her exams last week.",
                    options = listOf("studied", "studyed", "studying", "studid"),
                    correctOptionIndex = 0,
                    correctAnswerText = "studied",
                    explanationUzbek = "'study' fe'li undosh + 'y' bilan tugagani sababli, 'y' harfi 'i' ga aylanadi: 'studied'."
                ),
                MurphyExerciseItem(
                    id = "u11_ex5",
                    exerciseNumber = "11.4",
                    taskType = "CHOICE",
                    question = "I was hungry, so I ______ two sandwiches and ______ some orange juice.",
                    options = listOf("ate / drank", "eated / drinked", "ate / drink", "eat / drank"),
                    correctOptionIndex = 0,
                    correctAnswerText = "ate / drank",
                    explanationUzbek = "'eat' ning o'tgan zamoni 'ate', 'drink' ning o'tgan zamoni esa 'drank' hisoblanadi."
                )
            )
        ),

        // UNIT 12
        MurphyUnit(
            unitNumber = 12,
            title = "I didn't... Did you...? (Past Simple negative & questions)",
            subtitleUzbek = "O'tgan zamon inkor va so'rog'i: 'didn't' va 'did' ishlatilishi",
            groupName = "2-Guruh: Past (10–14)",
            keyTakeawaysUzbek = listOf(
                "Past Simple inkorida 'didn't + V1 (boshlang'ich fe'l)' ishlatiladi.",
                "Past Simple so'rog'ida 'Did + Ega + V1 (boshlang'ich fe'l)?' ishlatiladi.",
                "OLTIN QOIDA: 'did' yoki 'didn't' kelgan zahoti asosiy fe'l o'zining 1-shakliga qaytadi! (❌ I didn't went EMAS! ✅ I didn't go).",
                "'Do' fe'li asosiy fe'l bo'lsa ham tushib qolmaydi: 'What did you do?' / 'I didn't do anything'.",
                "Qisqa javoblar: 'Yes, I did.' / 'No, I didn't.' (fe'l takrorlanmaydi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Inkor shakli: didn't + V1 (Infinitive)",
                    formula = "Ega + didn't + V1 (fe'lning 1-shakli)",
                    explanationUzbek = "O'tgan zamonda inkor gap tuzish uchun barcha shaxslar (I, you, he, she, it, we, they) bilan 'didn't' (= did not) ishlatiladi.\n\n⚠️ Eng muhim qoida: 'didn't' dan keyin fe'lga '-ed' qo'shilmaydi va 2-shakl (V2) ishlatilmaydi! Fe'l o'zining asl holatiga qaytadi:\n• I played -> I didn't play (didn't played EMAS)\n• I went -> I didn't go (didn't went EMAS)\n• She saw -> She didn't see (didn't saw EMAS)",
                    examples = listOf(
                        MurphyExample("I played tennis yesterday, but I didn't win.", "Kecha tennis o'ynadim, lekin yutmadim.", "didn't win (won emas)"),
                        MurphyExample("We went to the cinema, but we didn't enjoy the film.", "Kinoga bordik, lekin film bizga yoqmadi.", "didn't enjoy (enjoyed emas)"),
                        MurphyExample("They invited us to the party, but we didn't go.", "Ular bizni bazmga chaqirishdi, lekin biz bormadik.", "didn't go (went emas)"),
                        MurphyExample("It didn't rain yesterday. The weather was lovely.", "Kecha yomg'ir yog'madi. Havo ajoyib edi.", "didn't rain"),
                        MurphyExample("I was tired, but I didn't sleep well.", "Charchagan edim, lekin yaxshi uxlay olmadim.", "didn't sleep (slept emas)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "So'roq shakli: Did + Ega + V1?",
                    formula = "(So'roq so'zi) + Did + Ega + V1 (boshlang'ich fe'l)?",
                    explanationUzbek = "O'tgan zamonda so'roq tuzish uchun 'Did' egadan oldinga o'tadi. Asosiy fe'l esa yana 1-shaklda (infinitive) turadi!\n\nSo'z tartibi jadvali:\n• Did you see Joe yesterday?\n• Did it rain on Sunday?\n• Where did you go last night?\n• What time did your film finish?",
                    examples = listOf(
                        MurphyExample("Did you go out last night? - Yes, I went to a cafe.", "Kecha kechqurun ko'chaga chiqdingizmi?", "Did you go...? (Did you went emas)"),
                        MurphyExample("Did your sister phone you yesterday? - No, she didn't.", "Kecha opangiz qo'ng'iroq qildimi?", "Did your sister phone...?"),
                        MurphyExample("What time did you wake up this morning?", "Bugun ertalab soat nechada uyg'ondingiz?", "What time did you wake up...?"),
                        MurphyExample("Where did your friends stay in London?", "Do'stlaringiz Londonda qayerda turishdi?", "Where did they stay...?"),
                        MurphyExample("How did the accident happen?", "Hodisa qanday sodir bo'ldi?", "How did it happen...?")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "'Do' fe'lining o'zi asosiy fe'l bo'lib kelganda & Qisqa javoblar",
                    formula = "What did you do? | I didn't do anything | Yes, I did. / No, I didn't.",
                    explanationUzbek = "'Do' fe'li 'bajarmoq/qilmoq' ma'nosida kelganda, o'quvchilar ko'pincha adashishadi. Bu yerda 'did' yordamchi fe'l, 'do' esa asosiy fe'l vazifasini bajaradi.\n\n❌ Xato: What did you at the weekend?\n✅ To'g'ri: What did you DO at the weekend?\n\nQisqa javoblarda asosiy fe'l aytilmaydi, faqat 'did' yoki 'didn't' qo'yiladi:\n• Did you see Bob? - Yes, I did. / No, I didn't.",
                    examples = listOf(
                        MurphyExample("What did you do yesterday evening? - I stayed at home.", "Kecha oqshomda nima qildingiz? - Uyda qoldim.", "did you DO"),
                        MurphyExample("I was very tired, so I didn't do any work.", "Juda charchagan edim, shuning uchun hech qanday ish qilmadim.", "didn't DO"),
                        MurphyExample("Did you have a nice holiday? - Yes, we did.", "Ta'tilingiz maroqli o'tdimi? - Ha, ajoyib o'tdi.", "Yes, we did"),
                        MurphyExample("Did it rain yesterday? - No, it didn't.", "Kecha yomg'ir yog'dimi? - Yo'q, yog'madi.", "No, it didn't")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u12_ex1",
                    exerciseNumber = "12.1",
                    taskType = "CHOICE",
                    question = "I saw Barbara, but I ______ Jane.",
                    options = listOf("didn't see", "didn't saw", "didn't seen", "not saw"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't see",
                    explanationUzbek = "'didn't' dan keyin fe'lning 1-shakli (see) kelishi shart. 'didn't saw' qo'pol xato hisoblanadi."
                ),
                MurphyExerciseItem(
                    id = "u12_ex2",
                    exerciseNumber = "12.2",
                    taskType = "CHOICE",
                    question = "______ you ______ out last night?",
                    options = listOf("Did / go", "Did / went", "Were / go", "Do / went"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Did / go",
                    explanationUzbek = "O'tgan zamon so'rog'i: 'Did + ega + V1 (go)'. Shuning uchun 'Did you go' to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u12_ex3",
                    exerciseNumber = "12.3",
                    taskType = "CHOICE",
                    question = "What ______ you ______ on Sunday afternoon?",
                    options = listOf("did / do", "did / did", "were / do", "did / done"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did / do",
                    explanationUzbek = "'Nima qildingiz?' degan ma'noda 'did' (yordamchi) va 'do' (asosiy fe'l): 'What did you do?' deb so'raladi."
                ),
                MurphyExerciseItem(
                    id = "u12_ex4",
                    exerciseNumber = "12.4",
                    taskType = "CHOICE",
                    question = "The hotel wasn't very good. We ______ our stay.",
                    options = listOf("didn't enjoy", "didn't enjoyed", "not enjoyed", "weren't enjoy"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't enjoy",
                    explanationUzbek = "'enjoy' fe'lining inkori 'didn't enjoy' bo'ladi ('-ed' tushib qoladi)."
                ),
                MurphyExerciseItem(
                    id = "u12_ex5",
                    exerciseNumber = "12.5",
                    taskType = "CHOICE",
                    question = "Did you see Tom yesterday? - No, I ______.",
                    options = listOf("didn't", "didn't see", "wasn't", "don't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't",
                    explanationUzbek = "Past Simple so'rog'iga qisqa inkor javob: 'No, I didn't' bo'ladi."
                )
            )
        ),

        // UNIT 13
        MurphyUnit(
            unitNumber = 13,
            title = "I was doing (Past Continuous)",
            subtitleUzbek = "O'tgan davomli zamon: O'tmishdagi ma'lum vaqtda davom etayotgan jarayon",
            groupName = "2-Guruh: Past (10–14)",
            keyTakeawaysUzbek = listOf(
                "Past Continuous o'tmishdagi aniq bir paytda davom etayotgan jarayonni (harakat o'rtasini) bildiradi.",
                "Formulasi: was / were + fe'l-ing.",
                "I / he / she / it bilan 'was doing', we / you / they bilan 'were doing'.",
                "Past Simple (did) ishning butunlay tugaganini bildirsa, Past Continuous (was doing) ish hali tugamaganligini, o'rtasida ekanligini bildiradi.",
                "Inkor: wasn't / weren't doing. So'roq: Were you doing...?"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Jarayon tushunchasi (Tennis o'yini misoli)",
                    formula = "Ega + was / were + fe'l-ing",
                    explanationUzbek = "Tasavvur qiling: Jek soat 4:00 da tennis o'ynashni boshladi va 4:30 da o'yinni tugatdi.\n\nSavol: Soat 4:15 da Jek nima qilayotgan edi?\nJavob: He was playing tennis (U tennis o'ynayotgan edi).\n\nBu shuni anglatadiki, 4:15 da u o'yinning ayni o'rtasida edi. Harakat boshlangan, lekin hali tugallanmagan edi!",
                    examples = listOf(
                        MurphyExample("At 4.15 Jack was playing tennis.", "Soat 4:15 da Jek tennis o'ynayotgan edi.", "was + playing"),
                        MurphyExample("What were you doing at 11.30 yesterday?", "Kecha 11:30 da nima qilayotgan edingiz?", "were you doing"),
                        MurphyExample("In 2015 we were living in Canada.", "2015-yilda biz Kanadada yashayotgan edik.", "were living"),
                        MurphyExample("The sun was shining and the birds were singing.", "Quyosh charoqlab turgan va qushlar sayrayotgan edi.", "was shining / were singing"),
                        MurphyExample("It was raining, so we didn't go out.", "Yomg'ir yog'ayotgan edi, shuning uchun biz ko'chaga chiqmadik.", "was raining")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Darak, Inkor va So'roq shakllari",
                    formula = "was/were + fe'l-ing | wasn't/weren't + fe'l-ing | Was/Were + ega + fe'l-ing?",
                    explanationUzbek = "Birlik shaxslar (I, he, she, it) uchun 'was', ko'pliklar (we, you, they) uchun 'were' ishlatiladi. Fe'l oxiriga esa '-ing' qo'shiladi.\n\n• Darak: She was working.\n• Inkor: She wasn't working.\n• So'roq: Was she working?",
                    examples = listOf(
                        MurphyExample("I waved to Helen, but she wasn't looking.", "Men Helenga qo'l silkitdim, lekin u qaramayotgan edi.", "wasn't looking"),
                        MurphyExample("Were you sleeping when I phoned you?", "Men qo'ng'iroq qilganimda uxlayotgan edingizmi?", "Were you sleeping...?"),
                        MurphyExample("Why were you driving so fast yesterday?", "Kecha nega mashinani bunchalik tez haydayotgan edingiz?", "Why were you driving...?"),
                        MurphyExample("They weren't paying attention during the lesson.", "Ular dars paytida e'tibor bermayotgan edilar.", "weren't paying")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Past Simple (did) vs Past Continuous (was doing) farqi",
                    formula = "did = tugallangan ish | was doing = davom etayotgan jarayon",
                    explanationUzbek = "Bu ikki zamonning tub mohiyati:\n\n1. Past Simple: Harakat butunlay tugallangan.\n• Jack read a book yesterday. (Jek kecha kitob o'qidi — boshidan oxirigacha o'qib tugatdi).\n\n2. Past Continuous: Harakat o'rtasida, ayni jarayonda.\n• Jack was reading a book when the phone rang. (Telefon jiringlaganda Jek kitob o'qiyotgan edi — harakat tugallanmagan, davom etayotgan edi).",
                    examples = listOf(
                        MurphyExample("I cleaned my room yesterday. (Tugallangan)", "Kecha xonamni tozaladim.", "Past Simple: cleaned"),
                        MurphyExample("I was cleaning my room when Tom arrived. (Jarayonda)", "Tom kelganida men xonamni tozalayotgan edim.", "Past Continuous: was cleaning"),
                        MurphyExample("It rained all morning. (Tong bo'yi yog'di va tindi)", "Ertalab bo'yi yomg'ir yog'di.", "Past Simple: rained"),
                        MurphyExample("It was raining when I woke up. (Uyg'onganimda yog'ayotgan edi)", "Men uyg'onganimda yomg'ir yog'ayotgan edi.", "Past Continuous: was raining")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u13_ex1",
                    exerciseNumber = "13.1",
                    taskType = "CHOICE",
                    question = "At 8.45 yesterday morning, Linda ______ her car to work.",
                    options = listOf("was driving", "drove", "is driving", "were driving"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was driving",
                    explanationUzbek = "Kecha ertalab 8:45 aniq vaqtida Linda yo'lda haydab ketayotgan edi (jarayon), shuning uchun 'was driving' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u13_ex2",
                    exerciseNumber = "13.2",
                    taskType = "CHOICE",
                    question = "Why ______ you ______ so fast when the police stopped you?",
                    options = listOf("were / driving", "was / driving", "did / drive", "are / driving"),
                    correctOptionIndex = 0,
                    correctAnswerText = "were / driving",
                    explanationUzbek = "'you' olmoshi bilan Past Continuous da 'were ... driving' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u13_ex3",
                    exerciseNumber = "13.3",
                    taskType = "CHOICE",
                    question = "I saw you in town yesterday, but you ______ me. You ______ to someone.",
                    options = listOf("didn't see / were talking", "weren't seeing / talked", "didn't saw / was talking", "not saw / talked"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't see / were talking",
                    explanationUzbek = "Ko'rmaslik qisqa voqea (didn't see), biroq kim bilandir gaplashib turish jarayon edi (were talking)."
                ),
                MurphyExerciseItem(
                    id = "u13_ex4",
                    exerciseNumber = "13.4",
                    taskType = "CHOICE",
                    question = "What ______ at 10 o'clock last night? - I was watching TV.",
                    options = listOf("were you doing", "did you do", "was you doing", "do you do"),
                    correctOptionIndex = 0,
                    correctAnswerText = "were you doing",
                    explanationUzbek = "Kecha soat 10 da qanday jarayon bilan band edingiz degan ma'noda 'What were you doing?' deb so'raladi."
                ),
                MurphyExerciseItem(
                    id = "u13_ex5",
                    exerciseNumber = "13.4",
                    taskType = "CHOICE",
                    question = "It was a sunny day, but the wind ______ strongly.",
                    options = listOf("was blowing", "blew", "were blowing", "is blowing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was blowing",
                    explanationUzbek = "Shamol esib turgan edi (ob-havodagi orqa fon jarayoni): 'wind was blowing'."
                )
            )
        ),

        // UNIT 14
        MurphyUnit(
            unitNumber = 14,
            title = "I was doing and I did (Past Continuous & Simple)",
            subtitleUzbek = "O'tgan davomli va O'tgan oddiy zamon birgalikda: when, while va ketma-ket harakatlar",
            groupName = "2-Guruh: Past (10–14)",
            keyTakeawaysUzbek = listOf(
                "Uzoq davom etayotgan jarayon (Past Continuous) o'rtasida to'satdan qisqa voqea (Past Simple) sodir bo'lganda ular birgalikda keladi.",
                "Qoida: Past Continuous (uzoq fon harakati) + WHEN + Past Simple (qisqa to'satdan bo'lgan harakat).",
                "'While' (davomida) odatda Past Continuous bilan keladi: 'while I was having a shower'.",
                "Birin-ketin, ketma-ket sodir bo'lgan voqealar uchun FAQAT Past Simple ishlatiladi (I got up, had breakfast and left).",
                "Holat fe'llari (know, want, like, believe) Past Continuous da ishlatilmaydi, faqat Past Simple bo'ladi: 'I knew' (NOT 'was knowing')."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Uzoq jarayonni qisqa voqea bo'lib qo'yishi (When / While)",
                    formula = "Past Continuous (uzoq fon) + when + Past Simple (qisqa voqea)",
                    explanationUzbek = "Tasavvur qiling: Orqa fonda uzoq vaqt davom etayotgan harakat bor (Past Continuous). Masalan, kitob o'qiyapsiz yoki yo'lda ketyapsiz.\nShu payt to'satdan qisqa bir voqea ro'y berdi (Past Simple): telefon jiringladi yoki do'stingizni uchratib qoldingiz.\n\n• Jack was reading a book (uzoq harakat) WHEN the phone rang (qisqa voqea).\n• It began to rain (qisqa voqea) WHILE we were walking home (uzoq jarayon).",
                    examples = listOf(
                        MurphyExample("Jack was reading a book when the phone rang.", "Jek kitob o'qiyotgan edi, shu payt telefon jiringlab qoldi.", "was reading (uzoq) + rang (qisqa)"),
                        MurphyExample("It began to rain while we were walking home.", "Uyga piyoda ketayotganimizda yomg'ir yog'ishni boshladi.", "while we were walking"),
                        MurphyExample("I hurt my back while I was working in the garden.", "Bog'da ishlayotgan paytimda belimni og'ritib oldim.", "hurt (qisqa) + was working (uzoq)"),
                        MurphyExample("Kelly fell asleep while she was watching TV.", "Kelli televizor ko'rayotgan paytida uxlab qoldi.", "fell asleep (qisqa) + was watching (uzoq)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Taqqoslang: Bir vaqtda (when) vs Ketma-ket (sequential) harakatlar",
                    formula = "When Karen arrived, we were having dinner VS When Karen arrived, we had dinner",
                    explanationUzbek = "Bu ikki gapning farqini juda yaxshi tushunib oling:\n\n1. 'When Karen arrived, we were having dinner.'\nKaren yetib kelganida, biz ovqatlanayotgan edik (biz ovqatlanishni oldinroq boshlagan edik, u kelganda ovqat ustida edik).\n\n2. 'When Karen arrived, we had dinner.'\nKaren yetib kelgach, biz kechki ovqatni tanovul qildik (birinchi Karen keldi, KEYIN ovqatlandik — ketma-ket harakatlar!).",
                    examples = listOf(
                        MurphyExample("When the phone rang, I answered it.", "Telefon jiringlaganda, men javob berdim.", "Ikkisi ham Past Simple (ketma-ket harakat)"),
                        MurphyExample("When I opened the curtains, the sun was shining.", "Pardani ochganimda quyosh charoqlab turgan edi.", "opened (qisqa) + was shining (oldindan davom etayotgan)"),
                        MurphyExample("I got up, washed my face and had breakfast.", "O'rnimdan turdim, yuzimni yuvdim va nonushta qildim.", "Faqat Past Simple (ketma-ket voqealar)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Holat fe'llari (Stative verbs in Past)",
                    formula = "know, want, like, prefer, believe -> Faqat Past Simple!",
                    explanationUzbek = "8-Unitda ko'rganimizdek, ba'zi fe'llar (know, want, like, need, prefer, believe, understand) hech qachon continuous (-ing) shaklida ishlatilmaydi. Ular o'tgan zamonda ham faqat Past Simple da bo'ladi!\n\n❌ Xato: We were good friends. We were knowing each other.\n✅ To'g'ri: We were good friends. We KNEW each other well.",
                    examples = listOf(
                        MurphyExample("We were good friends. We knew each other very well.", "Biz yaxshi do'st edik. Bir-birimizni juda yaxshi bilar edik.", "knew (were knowing EMAS)"),
                        MurphyExample("I was enjoying the party, but Chris wanted to go home.", "Menga bazm yoqayotgan edi, lekin Kris uyga ketishni xohladi.", "wanted (was wanting EMAS)"),
                        MurphyExample("Did you understand what the teacher said?", "O'qituvchi aytganini tushundingizmi?", "understand (continuous bo'lmaydi)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u14_ex1",
                    exerciseNumber = "14.1",
                    taskType = "CHOICE",
                    question = "Rachel ______ her hand when she ______ the cooking.",
                    options = listOf("burnt / was doing", "was burning / did", "burnt / did", "was burning / was doing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "burnt / was doing",
                    explanationUzbek = "Ovqat pishirayotgan uzoq jarayonda (was doing the cooking) to'satdan qo'lini kuydirib oldi (burnt - qisqa voqea)."
                ),
                MurphyExerciseItem(
                    id = "u14_ex2",
                    exerciseNumber = "14.2",
                    taskType = "CHOICE",
                    question = "When I was young, I ______ to be a pilot.",
                    options = listOf("wanted", "was wanting", "want", "am wanting"),
                    correctOptionIndex = 0,
                    correctAnswerText = "wanted",
                    explanationUzbek = "'want' holat fe'li bo'lgani uchun u hech qachon Continuous (-ing) bo'lmaydi, faqat Past Simple: 'wanted'."
                ),
                MurphyExerciseItem(
                    id = "u14_ex3",
                    exerciseNumber = "14.3",
                    taskType = "CHOICE",
                    question = "The phone rang while I ______ breakfast.",
                    options = listOf("was having", "had", "have", "am having"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was having",
                    explanationUzbek = "'While' (davomida) so'zidan keyin odatda jarayon (Past Continuous) keladi: 'while I was having breakfast'."
                ),
                MurphyExerciseItem(
                    id = "u14_ex4",
                    exerciseNumber = "14.4",
                    taskType = "CHOICE",
                    question = "I got up early, ______ a shower and ______ for work.",
                    options = listOf("took / left", "was taking / was leaving", "taking / leaving", "took / was leaving"),
                    correctOptionIndex = 0,
                    correctAnswerText = "took / left",
                    explanationUzbek = "Ketma-ket, birin-ketin sodir bo'lgan harakatlar uchun faqat Past Simple ishlatiladi: got up -> took a shower -> left."
                ),
                MurphyExerciseItem(
                    id = "u14_ex5",
                    exerciseNumber = "14.4",
                    taskType = "CHOICE",
                    question = "When Karen arrived, we ______ dinner. We started eating before she arrived.",
                    options = listOf("were having", "had", "have", "are having"),
                    correctOptionIndex = 0,
                    correctAnswerText = "were having",
                    explanationUzbek = "Ular Karen kelishidan oldinroq ovqatlanishni boshlagan va u kelgan paytda ovqatlanish jarayonida bo'lishgan: 'were having dinner'."
                )
            )
        )
    )
}
