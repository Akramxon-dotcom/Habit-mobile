package com.example.data.model

object MurphyBookRelativeClausesPhrasalsData {

    val UNITS_RELATIVE_CLAUSES_PHRASALS: List<MurphyUnit> = listOf(
        // UNIT 93
        MurphyUnit(
            unitNumber = 93,
            title = "If I had ... / If we went ... (Second Conditional - Hayoliy shart)",
            subtitleUzbek = "Hozirgi va kelasi zamondagi hayoliy, noreal istaklar ('Agar menda ... bo'lganida edi')",
            groupName = "16-Guruh: Relative Clauses & Phrasal Verbs (93–98)",
            keyTakeawaysUzbek = listOf(
                "IKKINCHI SHART (Second Conditional) — Hozirgi zamondagi hayoliy orzular va afsuslar uchun ishlatiladi: voqea aslida unday emas, lekin 'agar shunday bo'lganida edi' deb tasavvur qilinadi.",
                "FORMULA: If + Past Simple (o'tgan zamon) , WOULD + fe'l (asliy shaklda).",
                "MISOL: 'If I had a lot of money, I would buy a big house.' (Agar pulim ko'p bo'lganida, katta uy sotib olar edim. Lekin afsuski hozir pulim ko'p emas!).",
                "'TO BE' FE'LI: Hayoliy gaplarda I, he, she, it bilan 'was' o'rniga 'WERE' ishlatish juda odatiy va rasmiy: 'If I were you, I wouldn't do that' (Agar sening o'rningda bo'lganimda...).",
                "FARQ: Unit 92 (Real shart: If it rains, I will stay) vs Unit 93 (Hayoliy shart: If I had wings, I would fly)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Hayoliy orzular va Noreal vaziyatlar",
                    formula = "If + Ega + Past Simple (V2) , Ega + would + V1",
                    explanationUzbek = "Bu zamon o'tmish haqida emas! Bu hozirgi paytdagi noreal orzular haqida.\n\nTasavvur qiling: sizning mashinangiz yo'q. Avtobusda ketyapsiz. Ichingizda o'ylaysiz:\n• 'If I had a car, I would drive to work.'\n(Agar mashinam bo'lganida edi, ishga mashinada borgan bo'lardim — lekin afsuski mashinam yo'q!)\n\nYana bir misol:\n• If I knew his phone number, I would call him.\n(Agar uning raqamini bilganimda edi, unga qo'ng'iroq qilgan bo'lardim — lekin bilmayman).\n\nE'tibor bering: 'If' bor qismida fe'l o'tgan zamonda (had, knew, lived), ikkinchi qismida esa 'would' bo'ladi.",
                    examples = listOf(
                        MurphyExample("If I won a million dollars, I would travel around the world.", "Agar bir million dollar yutib olganimda, butun dunyo bo'ylab sayohat qilgan bo'lardim.", "If I won ... I would travel"),
                        MurphyExample("We would go for a walk if it weren't raining.", "Agar yomg'ir yog'mayotgan bo'lganida, biz sayrga chiqqan bo'lardik.", "would go ... if it weren't...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "'If I were you' (Maslahat berish iborasi)",
                    formula = "If I were you, I would / wouldn't + fe'l",
                    explanationUzbek = "Birovga 'Men sening o'rningda bo'lganimda bunday qilardim' deb do'stona maslahat berganda ingliz tilida aynan shu konstruktsiya ishlatiladi:\n\n• If I were you, I would see a doctor. (Sening o'rningda bo'lganimda shifokorga ko'ringan bo'lardim)\n• If I were you, I wouldn't buy that car. It's too old. (O'rningda bo'lsam, u mashinani sotib olmagan bo'lardim)\n\nNega 'was' emas, 'were'? Chunki men hech qachon sen bo'la olmayman — bu 100% hayoliy tasavvur!",
                    examples = listOf(
                        MurphyExample("If I were you, I would accept the job offer.", "Sening o'rningda bo'lganimda, bu ish taklifini qabul qilgan bo'lardim.", "If I were you, I would..."),
                        MurphyExample("What would you do if you found a wallet on the street?", "Agar ko'chadan hamyon topib olganingizda nima qilgan bo'lardingiz?", "What would you do if you found...")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u93_ex1",
                    exerciseNumber = "93.1",
                    taskType = "CHOICE",
                    question = "If I ______ enough money, I would buy that expensive laptop.",
                    options = listOf("had", "have", "will have", "would have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "had",
                    explanationUzbek = "Hayoliy shart (Second conditional) 'If' qismida fe'l o'tgan zamonda bo'ladi: 'If I had'."
                ),
                MurphyExerciseItem(
                    id = "u93_ex2",
                    exerciseNumber = "93.2",
                    taskType = "CHOICE",
                    question = "If I were you, I ______ buy that jacket. It's too small.",
                    options = listOf("wouldn't", "won't", "don't", "not"),
                    correctOptionIndex = 0,
                    correctAnswerText = "wouldn't",
                    explanationUzbek = "'If I were you' maslahat iborasidan keyin 'wouldn't' keladi."
                ),
                MurphyExerciseItem(
                    id = "u93_ex3",
                    exerciseNumber = "93.3",
                    taskType = "CHOICE",
                    question = "We ______ go on holiday more often if we had more free time.",
                    options = listOf("would", "will", "are going to", "can"),
                    correctOptionIndex = 0,
                    correctAnswerText = "would",
                    explanationUzbek = "Natija qismida 'would go' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u93_ex4",
                    exerciseNumber = "93.4",
                    taskType = "CHOICE",
                    question = "What ______ you do if you saw an alien?",
                    options = listOf("would", "will", "do", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "would",
                    explanationUzbek = "Hayoliy vaziyatdagi so'roq: 'What would you do if you saw...'."
                ),
                MurphyExerciseItem(
                    id = "u93_ex5",
                    exerciseNumber = "93.5",
                    taskType = "CHOICE",
                    question = "If she lived closer to work, she ______ walk every day.",
                    options = listOf("could / would", "will", "can", "is walking"),
                    correctOptionIndex = 0,
                    correctAnswerText = "could / would",
                    explanationUzbek = "Yaqinroq yashaganida edi, piyoda qatnagan bo'lardi: 'could/would walk'."
                )
            )
        ),

        // UNIT 94
        MurphyUnit(
            unitNumber = 94,
            title = "a person who ... / a thing that/which ... (Relative clauses 1)",
            subtitleUzbek = "Bog'lovchi olmoshlar: who (odamlar uchun), which / that (narsalar uchun)",
            groupName = "16-Guruh: Relative Clauses & Phrasal Verbs (93–98)",
            keyTakeawaysUzbek = listOf(
                "RELATIVE CLAUSE — Biror shaxs yoki buyumni 'qanaqa?', 'qaysi?' degan savolga javob berib, aniqlab tushuntirish.",
                "WHO = ODAMLAR UCHUN: The man WHO lives next door is a doctor (Yon qo'shni bo'lib yashaydigan kishi — shifokor).",
                "WHICH = NARSALAR VA HAYVONLAR UCHUN: The bus WHICH goes to the airport runs every 20 minutes (Aeroportga boradigan avtobus).",
                "THAT = HAM ODAMLAR, HAM NARSALAR UCHUN ishlatilaveradi (juda universal so'z): a person that / a book that.",
                "Eslab qoling: Odamlarga 'which' ISHLATILMAYDI! (The person which lives... — QAT'IYAN XATO!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "WHO (Odamlar uchun: ...gan odam)",
                    formula = "Inson oti + WHO / THAT + Fe'l",
                    explanationUzbek = "Ikki oddiy gapni bitta qilib bog'laymiz:\n\n1-gap: I met a woman.\n2-gap: She can speak six languages.\nBirlashganda: I met a woman WHO can speak six languages.\n(Men 6 ta tilda gaplasha oladigan ayolni uchratdim)\n\n• Do you know the man who is standing over there?\n(Anavi yerda turgan kishini taniysizmi?)\n• An architect is someone who designs buildings.\n(Me'mor — bu binolarni loyihalashtiradigan kishi)",
                    examples = listOf(
                        MurphyExample("A doctor is a person who treats sick people.", "Shifokor — bu bemorlarni davolaydigan inson.", "a person who treats"),
                        MurphyExample("What happened to the girl who used to work here?", "Ilgari shu yerda ishlagan qizga nima bo'ldi?", "the girl who worked")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "WHICH va THAT (Narsalar va jonsiz tushunchalar uchun)",
                    formula = "Narsa/hayvon + WHICH / THAT + Fe'l",
                    explanationUzbek = "Jonsiz narsalar yoki hayvonlar haqida gapirganda WHO ishlatib bo'lmaydi. Faqat WHICH yoki THAT ishlatiladi:\n\n1-gap: I bought a phone.\n2-gap: It was very expensive.\nBirlashganda: I bought a phone WHICH was very expensive.\n(Men juda qimmat bo'lgan telefon sotib oldim)\n\n• Where is the key that opens this door?\n(Bu eshikni ochadigan kalit qayerda?)\n• I don't like stories that have unhappy endings.\n(Menga baxtsiz yakun topadigan hikoyalar yoqmaydi)",
                    examples = listOf(
                        MurphyExample("A coffee machine is a machine which makes coffee.", "Qahva mashinasi — bu qahva tayyorlaydigan uskuna.", "machine which makes"),
                        MurphyExample("The car that was parked outside has gone.", "Tashqarida to'xtab turgan mashina ketib qolibdi.", "car that was parked")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u94_ex1",
                    exerciseNumber = "94.1",
                    taskType = "CHOICE",
                    question = "A butcher is a person ______ sells meat.",
                    options = listOf("who", "which", "whose", "where"),
                    correctOptionIndex = 0,
                    correctAnswerText = "who",
                    explanationUzbek = "Qassob — bu inson, shuning uchun odamlarga 'who' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u94_ex2",
                    exerciseNumber = "94.2",
                    taskType = "CHOICE",
                    question = "I bought a jacket ______ was made of real leather.",
                    options = listOf("which", "who", "whom", "where"),
                    correctOptionIndex = 0,
                    correctAnswerText = "which",
                    explanationUzbek = "Kurtka — narsa, narsalarga 'which' yoki 'that' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u94_ex3",
                    exerciseNumber = "94.3",
                    taskType = "CHOICE",
                    question = "Do you know anyone ______ can speak Chinese?",
                    options = listOf("who", "which", "what", "where"),
                    correctOptionIndex = 0,
                    correctAnswerText = "who",
                    explanationUzbek = "'anyone' (biror kishi) inson bo'lgani uchun 'who' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u94_ex4",
                    exerciseNumber = "94.4",
                    taskType = "CHOICE",
                    question = "The computer ______ I am using is very fast.",
                    options = listOf("that", "who", "whose", "what"),
                    correctOptionIndex = 0,
                    correctAnswerText = "that",
                    explanationUzbek = "Kompyuter uchun 'that' yoki 'which' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u94_ex5",
                    exerciseNumber = "94.5",
                    taskType = "CHOICE",
                    question = "A dictionary is a book ______ explains the meaning of words.",
                    options = listOf("which", "who", "whom", "where"),
                    correctOptionIndex = 0,
                    correctAnswerText = "which",
                    explanationUzbek = "Lug'at — kitob (narsa): 'which explains'."
                )
            )
        ),

        // UNIT 95
        MurphyUnit(
            unitNumber = 95,
            title = "the people we met / the hotel you stayed at (Relative clauses 2)",
            subtitleUzbek = "Olmoshni tushirib qoldirish: 'Biz ko'rgan odamlar' (who/that siz)",
            groupName = "16-Guruh: Relative Clauses & Phrasal Verbs (93–98)",
            keyTakeawaysUzbek = listOf(
                "ENG QIZIQ VA ZAMONAVIY QOIDA: Agar who/that/which so'zidan keyin YANGI EGA (I, you, we, he) kelsa, WHO / THAT / WHICH so'zini butunlay TUSHIRIB QOLDIRISH MUMKIN!",
                "Misol: 'The man that I met' o'rniga oddiygina 'The man I met' deyiladi (Men uchratgan kishi).",
                "Misol: 'The book that you gave me' o'rniga 'The book you gave me' (Siz menga bergan kitob).",
                "PREDLOGLAR GAPNING ENG OXIRIGA O'TADI: 'The hotel we stayed at' (Biz yashagan mehmonxona), 'The girl he was talking to' (U gaplashayotgan qiz).",
                "QACHON TUSHIRIB BO'LMAYDI? Agar who/that dan keyin to'g'ridan-to'g'ri FE'L kelsa, tushirib bo'lmaydi: 'The man WHO lives next door' ('The man lives next door' deb bo'lmaydi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Who / That ni tushirib qoldirish siri",
                    formula = "Ot + Ega + Fe'l (who/that shart emas!)",
                    explanationUzbek = "Inglizlar og'zaki nutqda ortiqcha so'zlarni aytishni yoqtirishmaydi:\n\n• The woman (who) I wanted to see was away on holiday.\n(Men ko'rmoqchi bo'lgan ayol ta'tilda edi)\n• Have you found the keys (that) you lost?\n(Yo'qotgan kalitlaringizni topdingizmi?)\n• Did you enjoy the film (which) you watched last night?\n(Kechagi ko'rgan filmingiz yoqdimi?)\n\nIkkala variant ham to'g'ri, lekin olmoshsiz variant ko'proq tabiiy eshitiladi!",
                    examples = listOf(
                        MurphyExample("The meal we had was delicious.", "Biz yegan taom juda mazali edi.", "The meal we had (that tushib qolgan)"),
                        MurphyExample("Everything he says is true.", "U aytayotgan barcha narsa haqiqat.", "Everything he says")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Predloglar bilan bog'lanish (stay at, talk to, listen to)",
                    formula = "Ot + Ega + Fe'l + PREDLOG (gap oxirida)",
                    explanationUzbek = "O'zbek tilida 'Men bilan gaplashgan qiz' desak, ingliz tilida predlog eng orqaga o'tadi:\n\n• The girl he was talking TO is my cousin.\n• Is this the hotel you stayed AT?\n• The music we were listening TO was very quiet.\n• The job she applied FOR was in a bank.\n\nEslab qoling: O'zbekcha '...da turgan', '...ga topshirgan' ma'nolari ingliz tilida gap oxiridagi kichik predlog bilan ifodalanadi.",
                    examples = listOf(
                        MurphyExample("Who was that man you were talking to?", "Siz gaplashayotgan anavi kishi kim edi?", "...you were talking to"),
                        MurphyExample("This is the house I was born in.", "Bu men tug'ilgan uy.", "...I was born in")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u95_ex1",
                    exerciseNumber = "95.1",
                    taskType = "CHOICE",
                    question = "The movie ______ last night was very exciting.",
                    options = listOf("we watched", "that we watched it", "what we watched", "we watched it"),
                    correctOptionIndex = 0,
                    correctAnswerText = "we watched",
                    explanationUzbek = "Olmoshni tushirib 'The movie we watched' deyish eng to'g'ri va tabiiy shakldir (yana 'it' deb qaytarilmaydi)."
                ),
                MurphyExerciseItem(
                    id = "u95_ex2",
                    exerciseNumber = "95.2",
                    taskType = "CHOICE",
                    question = "Did you get the email ______ I sent you this morning?",
                    options = listOf("that / —", "who", "where", "whom"),
                    correctOptionIndex = 0,
                    correctAnswerText = "that / —",
                    explanationUzbek = "Email narsa bo'lgani uchun 'that' yoki tushirib qoldirish (—) to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u95_ex3",
                    exerciseNumber = "95.3",
                    taskType = "CHOICE",
                    question = "Who was that girl you were waving ______?",
                    options = listOf("to", "at", "with", "for"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Birovga qo'l silkish 'wave to somebody' bo'ladi, shuning uchun 'wave to'."
                ),
                MurphyExerciseItem(
                    id = "u95_ex4",
                    exerciseNumber = "95.4",
                    taskType = "CHOICE",
                    question = "The hotel we stayed ______ was right on the beach.",
                    options = listOf("at", "to", "on", "into"),
                    correctOptionIndex = 0,
                    correctAnswerText = "at",
                    explanationUzbek = "Mehmonxonada yashab turish 'stay at a hotel' bo'ladi: 'we stayed at'."
                ),
                MurphyExerciseItem(
                    id = "u95_ex5",
                    exerciseNumber = "95.5",
                    taskType = "CHOICE",
                    question = "I couldn't find the pen ______ yesterday.",
                    options = listOf("I bought", "I bought it", "which I bought it", "what I bought"),
                    correctOptionIndex = 0,
                    correctAnswerText = "I bought",
                    explanationUzbek = "'The pen I bought' — ortiqcha 'it' so'zi qo'shilmaydi."
                )
            )
        ),

        // UNIT 96
        MurphyUnit(
            unitNumber = 96,
            title = "look at, look for, listen to, wait for (Prepositional verbs)",
            subtitleUzbek = "Maxsus predlog oluvchi fe'llar: Biror narsaga qaramoq, qidirmoq, tinglamoq, kutmoq",
            groupName = "16-Guruh: Relative Clauses & Phrasal Verbs (93–98)",
            keyTakeawaysUzbek = listOf(
                "LOOK AT = 'Qaramoq' (ko'z bilan): Look at that picture! (Anavi rasmga qarang!).",
                "LOOK FOR = 'Qidirmoq' (yo'qolgan narsani): I am looking for my keys (Kalitlarimni qidiryapman).",
                "LISTEN TO = 'Tinglamoq / quloq solmoq': Listen TO music (Hech qachon 'listen music' demang, 'TO' shart!).",
                "WAIT FOR = 'Kutmoq': Wait FOR me (Meni kuting — 'Wait me' deb bo'lmaydi!).",
                "ASK FOR = 'So'ramoq' (biror narsani): He asked for the bill (U hisobni so'radi).",
                "BELONG TO = 'Tegishli bo'lmoq': This bag belongs to me (Bu sumka menga tegishli)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Eng ko'p xato qilinadigan 4 ta fe'l (Look, Listen, Wait, Ask)",
                    formula = "look AT | look FOR | listen TO | wait FOR",
                    explanationUzbek = "O'zbek tilida bu so'zlarning predlogi sezilmaydi, shuning uchun ingliz tilida ularni unutib qo'yish juda oson:\n\n1) LISTEN TO (Tinglamoq):\n• I love listening to podcasts. (Men podkastlarni tinglashni yaxshi ko'raman)\n• Listen to the teacher! (O'qituvchiga quloq soling!)\n\n2) WAIT FOR (Kutmoq):\n• Who are you waiting for? (Siz kimni kutyapsiz?)\n• I'm waiting for the bus. (Avtobus kutyapman)\n\n3) LOOK AT (Qaramoq):\n• Don't look at me like that. (Menga bunday qarama)\n\n4) LOOK FOR (Qidirmoq):\n• What are you looking for? (Nima qidiryapsan?)",
                    examples = listOf(
                        MurphyExample("Can you wait for me for a minute?", "Meni bir daqiqa kuta olasizmi?", "wait for me"),
                        MurphyExample("She is looking for a new job.", "U yangi ish qidiryapti.", "looking for = qidirmoq"),
                        MurphyExample("Look at this beautiful sunset!", "Bu go'zal quyosh botishiga qarang!", "look at")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Boshqa mashhur fe'l + predlog juftliklari",
                    formula = "talk TO / speak TO | think ABOUT / OF | write TO | pay FOR",
                    explanationUzbek = "Doim birga yuradigan yana bir necha ajralmas do'stlar:\n\n• speak TO / talk TO: I need to speak to the manager. (Menejer bilan gaplashishim kerak)\n• pay FOR: Who is going to pay for dinner? (Kechki ovqat uchun kim pul to'laydi?)\n• think ABOUT: What are you thinking about? (Nima haqida o'ylayapsan?)\n• write TO: She writes to her parents every week. (U har hafta ota-onasiga xat yozadi)\n• belong TO: Does this phone belong to you? (Bu telefon sizga tegishlimi?)",
                    examples = listOf(
                        MurphyExample("I have to pay for the tickets online.", "Chiptalar uchun onlayn to'lashim kerak.", "pay for"),
                        MurphyExample("I was thinking about our future trip.", "Men kelajakdagi sayohatimiz haqida o'ylayotgan edim.", "think about")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u96_ex1",
                    exerciseNumber = "96.1",
                    taskType = "CHOICE",
                    question = "I love listening ______ classical music in the evenings.",
                    options = listOf("to", "at", "for", "— (hech narsa)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Musiqa tinglash doim 'listen to music' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u96_ex2",
                    exerciseNumber = "96.2",
                    taskType = "CHOICE",
                    question = "We have been waiting ______ the bus for twenty minutes.",
                    options = listOf("for", "to", "at", "on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Biror narsani kutish 'wait for' bo'ladi: 'waiting for the bus'."
                ),
                MurphyExerciseItem(
                    id = "u96_ex3",
                    exerciseNumber = "96.3",
                    taskType = "CHOICE",
                    question = "I have lost my glasses. Can you help me look ______ them?",
                    options = listOf("for", "at", "after", "to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Yo'qolgan narsani qidirish 'look for' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u96_ex4",
                    exerciseNumber = "96.4",
                    taskType = "CHOICE",
                    question = "Who paid ______ the meal at the restaurant?",
                    options = listOf("for", "to", "at", "on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Taom uchun pul to'lash 'pay for the meal' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u96_ex5",
                    exerciseNumber = "96.5",
                    taskType = "CHOICE",
                    question = "Excuse me, does this backpack belong ______ you?",
                    options = listOf("to", "for", "with", "at"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Kimga tegishli bo'lish 'belong to' iborasi bilan yasaladi."
                )
            )
        ),

        // UNIT 97
        MurphyUnit(
            unitNumber = 97,
            title = "go in, fall off, run away etc. (Phrasal verbs 1)",
            subtitleUzbek = "Frazali fe'llar: Fe'l + ravish (in, out, up, down, away, back)",
            groupName = "16-Guruh: Relative Clauses & Phrasal Verbs (93–98)",
            keyTakeawaysUzbek = listOf(
                "FRAZALI FE'L (Phrasal verb) nima? — Bu fe'lga kichik so'z (in, out, on, off, up, away) qo'shilib, unga yangi harakat ma'nosini berishidir.",
                "IN / OUT: go in (ichkariga kirmoq) vs go out (tashqariga chiqmoq).",
                "AWAY: go away (ketib qolmoq / uzoqlashmoq), run away (qochib ketmoq).",
                "BACK: come back (qaytib kelmoq), give back (qaytarib bermoq).",
                "UP / DOWN: sit down (o'tirmoq), stand up (o'rnidan turmoq), get up (uyg'onib o'rindan turmoq).",
                "ON / OFF: get on the bus (avtobusga chiqmoq) vs get off the bus (avtobusdan tushmoq)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Yo'nalish bildiruvchi frazali fe'llar",
                    formula = "Fe'l + in / out / on / off / away / back",
                    explanationUzbek = "Bu fe'llar o'z ma'nosini saqlab qoladi, faqat yo'nalish qo'shiladi:\n\n• IN (Ichkariga): The door was open, so we went in. (Eshik ochiq ekan, ichkariga kirdik)\n• OUT (Tashqariga): He looked out of the window. (U derazadan tashqariga qaradi)\n• ON (Ustiga / minish): The bus stopped and two people got on. (Avtobus to'xtadi va ikki kishi chiqdi)\n• OFF (Tushish): Be careful when you get off the train. (Poyezddan tushayotganda ehtiyot bo'ling)\n• AWAY (Uzoqqa): Don't run away! I won't hurt you. (Qochib ketma!)\n• BACK (Orqaga qaytish): When are you coming back? (Qachon qaytib kelasiz?)",
                    examples = listOf(
                        MurphyExample("The thief ran away before the police arrived.", "Politsiya kelishidan oldin o'g'ri qochib ketdi.", "ran away = qochib ketdi"),
                        MurphyExample("I will call you when I get back home.", "Uyga qaytib kelganimda sizga telefon qilaman.", "get back = qaytmoq"),
                        MurphyExample("Please come in and sit down.", "Iltimos, ichkariga kiring va o'tiring.", "come in / sit down")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Ko'chma ma'noli mashhur iboralar (Break down, Wake up, Hurry up)",
                    formula = "break down (buzilmoq) | wake up (uyg'onmoq) | hurry up (shoshilmoq)",
                    explanationUzbek = "Ba'zi frazali fe'llar kutilmagan yangi ma'noga ega bo'ladi:\n\n• BREAK DOWN — buzilib qolmoq (mashina, texnika haqida):\nOur car broke down on the motorway. (Mashinamiz trassada buzilib qoldi)\n\n• WAKE UP — uyqudan uyg'onmoq:\nI woke up at 7 o'clock this morning.\n\n• HURRY UP — shoshilmoq:\nHurry up or we'll be late!\n\n• LOOK OUT / WATCH OUT — 'Ehtiyot bo'ling!':\nLook out! There's a car coming!",
                    examples = listOf(
                        MurphyExample("The elevator broke down, so we had to use the stairs.", "Lift buzilib qoldi, shuning uchun zinadan chiqishga to'g'ri keldi.", "broke down = buzildi"),
                        MurphyExample("Watch out! The floor is slippery.", "Ehtiyot bo'ling! Pol sirpanchiq.", "watch out = ehtiyot bo'l")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u97_ex1",
                    exerciseNumber = "97.1",
                    taskType = "CHOICE",
                    question = "Our car ______ on the way to the airport, so we missed our flight.",
                    options = listOf("broke down", "ran away", "got on", "turned off"),
                    correctOptionIndex = 0,
                    correctAnswerText = "broke down",
                    explanationUzbek = "Mashina buzilib qolishi 'broke down' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u97_ex2",
                    exerciseNumber = "97.2",
                    taskType = "CHOICE",
                    question = "We need to get ______ the bus at the next stop.",
                    options = listOf("off", "out", "away", "down"),
                    correctOptionIndex = 0,
                    correctAnswerText = "off",
                    explanationUzbek = "Avtobusdan tushish 'get off the bus' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u97_ex3",
                    exerciseNumber = "97.3",
                    taskType = "CHOICE",
                    question = "The door was locked, so they couldn't go ______.",
                    options = listOf("in", "on", "up", "away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in",
                    explanationUzbek = "Eshik qulflanganligi uchun ichkariga kira olishmadi: 'go in'."
                ),
                MurphyExerciseItem(
                    id = "u97_ex4",
                    exerciseNumber = "97.4",
                    taskType = "CHOICE",
                    question = "______! A car is reversing out of the driveway.",
                    options = listOf("Look out", "Stand up", "Run away", "Get back"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Look out",
                    explanationUzbek = "'Look out!' — Ehtiyot bo'ling!"
                ),
                MurphyExerciseItem(
                    id = "u97_ex5",
                    exerciseNumber = "97.5",
                    taskType = "CHOICE",
                    question = "I am going to the shop. I will be ______ in ten minutes.",
                    options = listOf("back", "away", "off", "out"),
                    correctOptionIndex = 0,
                    correctAnswerText = "back",
                    explanationUzbek = "Qaytib kelish 'be back' yoki 'come back' bo'ladi."
                )
            )
        ),

        // UNIT 98
        MurphyUnit(
            unitNumber = 98,
            title = "put on your shoes / put your shoes on (Phrasal verbs 2)",
            subtitleUzbek = "Ajraluvchi frazali fe'llar: turn on/off, pick up, put away, take off",
            groupName = "16-Guruh: Relative Clauses & Phrasal Verbs (93–98)",
            keyTakeawaysUzbek = listOf(
                "AJRALUVCHI FRAZALI FE'LLAR: To'ldiruvchi fe'l bilan qo'shimcha orasiga ham, yoki oxiriga ham qo'yilishi mumkin: 'Turn on the light' = 'Turn the light on'.",
                "OLMOSH QOIDASI (O'ta muhim!): Agar narsa 'it' yoki 'them' olmoshi bo'lsa, u FAQAT O'RTADA turadi! 'Turn it on' (Turn on it — QAT'IYAN XATO!).",
                "TURN ON / TURN OFF: Chiroq, televizor, texnikani yoqmoq va o'chirmoq (turn on the TV, turn off the light).",
                "PUT ON / TAKE OFF: Kiyimni kiymoq (put on a jacket) va yechmoq (take off your shoes).",
                "PICK UP / PUT DOWN: Yerdan ko'tarib olmoq (pick up the pen) va yerga qo'ymoq (put down the box)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "To'ldiruvchining o'rni (Ot vs Olmosh)",
                    formula = "turn on the light = turn the light on | LEKIN: turn it on (faqat o'rtada!)",
                    explanationUzbek = "1) Agar narsa OT (so'z) bo'lsa, ikkala usul ham to'g'ri:\n• Take off your coat. (Paltongizni yeching)\n• Take your coat off. (Paltongizni yeching)\nIkkalasi ham 100% to'g'ri.\n\n2) Agar narsa 'IT' yoki 'THEM' bo'lsa:\nUlar kichkina bo'lgani uchun doim fe'l va predlog O'RTASIGA tushadi:\n• It's dark. Can you turn IT on? (Turn on it — QOP-QORA XATO!)\n• Here are your shoes. Put THEM on. (Put on them — XATO!)",
                    examples = listOf(
                        MurphyExample("Don't throw away that newspaper. I haven't read it.", "U gazetani tashlab yubormang. Hali o'qimadim.", "throw away that newspaper"),
                        MurphyExample("Don't throw it away!", "Uni tashlab yubormang!", "throw it away (it o'rtada)"),
                        MurphyExample("He picked up the keys from the floor.", "U poldan kalitlarni ko'tarib oldi.", "picked up the keys")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Keng qo'llaniladigan mashhur juftliklar",
                    formula = "put on / take off | turn on / turn off | fill in | give up",
                    explanationUzbek = "Eng zarur iboralar:\n\n• TURN UP / TURN DOWN: Ovozini balandlatmoq / pasaytirmoq:\nCan you turn the music down? (Musiqani pasaytira olasizmi?)\n\n• PUT ON / TAKE OFF: Kiyim kiyish va yechish:\nPut on your shoes. / Take your hat off.\n\n• FILL IN: Blank yoki formani to'ldirmoq:\nPlease fill in this form. (Iltimos, ushbu formani to'ldiring)\n\n• GIVE UP: Tashlamoq / voz kechmoq (yomon odatni):\nHe gave up smoking last year. (U o'tgan yili chekishni tashladi)",
                    examples = listOf(
                        MurphyExample("It was hot, so I took off my sweater.", "Havo issiq edi, shuning uchun sviterimni yechdim.", "took off my sweater"),
                        MurphyExample("Please fill this application form in.", "Iltimos, bu ariza blankasini to'ldiring.", "fill in a form")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u98_ex1",
                    exerciseNumber = "98.1",
                    taskType = "CHOICE",
                    question = "It is very cold outside. Make sure you ______ your coat.",
                    options = listOf("put on", "take off", "turn on", "give up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "put on",
                    explanationUzbek = "Tashqari sovuq bo'lganda paltoni kiyish kerak: 'put on your coat'."
                ),
                MurphyExerciseItem(
                    id = "u98_ex2",
                    exerciseNumber = "98.2",
                    taskType = "CHOICE",
                    question = "The TV is too loud. Could you please ______?",
                    options = listOf("turn it down", "turn down it", "turn it up", "turn off it"),
                    correctOptionIndex = 0,
                    correctAnswerText = "turn it down",
                    explanationUzbek = "'it' olmoshi o'rtada keladi va ovozni pasaytirish 'turn down' bo'ladi: 'turn it down'."
                ),
                MurphyExerciseItem(
                    id = "u98_ex3",
                    exerciseNumber = "98.3",
                    taskType = "CHOICE",
                    question = "Please take ______ your shoes before entering the house.",
                    options = listOf("off", "on", "up", "away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "off",
                    explanationUzbek = "Poyabzalni yechish 'take off' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u98_ex4",
                    exerciseNumber = "98.4",
                    taskType = "CHOICE",
                    question = "I dropped my pen. Could you ______ for me?",
                    options = listOf("pick it up", "pick up it", "put it on", "give it up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "pick it up",
                    explanationUzbek = "Yerdan ko'tarib olish 'pick it up' (it o'rtada)."
                ),
                MurphyExerciseItem(
                    id = "u98_ex5",
                    exerciseNumber = "98.5",
                    taskType = "CHOICE",
                    question = "He decided to ______ junk food and start eating healthy.",
                    options = listOf("give up", "put on", "turn on", "look for"),
                    correctOptionIndex = 0,
                    correctAnswerText = "give up",
                    explanationUzbek = "Zararli ovqatlardan voz kechish/tashlash 'give up' bo'ladi."
                )
            )
        )
    )
}
