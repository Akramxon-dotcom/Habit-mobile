package com.example.data.model

object MurphyBookGoGetDoData {

    val UNITS_GO_GET_DO: List<MurphyUnit> = listOf(
        // UNIT 57
        MurphyUnit(
            unitNumber = 57,
            title = "get (got / getting / got)",
            subtitleUzbek = "Ingliz tilidagi eng ko'p qirrali fe'l: 'get' ning 5 ta asosiy ma'nosi",
            groupName = "10-Guruh: Essential Verbs & Phrasal Verbs (57–62)",
            keyTakeawaysUzbek = listOf(
                "GET + OT = receive (olmoq), buy (sotib olmoq), fetch (olib kelmoq): Did you get my email? (Xatimni oldingmi?), I got a new jacket (Yangi kurtka sotib oldim).",
                "GET + SIFAT = become (holatga o'tmoq, bo'lib qolmoq): get dark (qorong'i tushmoq), get tired (charchamoq), get married (turmush qurmoq).",
                "GET TO + JOY = arrive (yetib bormoq): How do I get to the airport? (Aeroportga qanday yetib olsam bo'ladi?).",
                "GET HOME = Uyga yetib kelmoq ('to' ISHLATILMAYDI: get home).",
                "TRANSPORT: get in/out of (car, taxi) vs get on/off (bus, train, plane)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Get + Ot (Qabul qilmoq, sotib olmoq, topmoq)",
                    formula = "get + noun = receive / buy / find / fetch",
                    explanationUzbek = "Get fe'lidan keyin ot kelsa, u kontekstga qarab quyidagi ma'nolarni bildiradi:\n\n• I got an email from Jack yesterday. (= received, oldim)\n• Where did you get that stylish coat? (= bought, sotib olding)\n• Can you get me a glass of water, please? (= fetch/bring, olib kelib bera olasanmi?)\n• I need to get a new job. (= find, yangi ish topishim kerak)",
                    examples = listOf(
                        MurphyExample("Did you get any letters today?", "Bugun biror xat oldingizmi?", "get = receive (olmoq)"),
                        MurphyExample("She got a nice present for her birthday.", "U tug'ilgan kuniga chiroyli sovg'a oldi.", "got = received"),
                        MurphyExample("Wait here, I'll go and get some coffee.", "Shu yerda kuting, borib kofe olib kelaman.", "get = fetch/buy")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Get + Sifat (O'zgarmoq, bo'lib qolmoq)",
                    formula = "get + adjective = become (holat o'zgarishi)",
                    explanationUzbek = "Get sifat bilan birga kelsa, biror holatdan ikkinchi holatga o'tishni bildiradi:\n\n• It's getting cold. Put on your jacket. (Havo soviyapti. Kurtkangni kiy)\n• I am getting hungry. Let's eat something. (Qornim oshyapti)\n• He drank water because he got thirsty. (U chanqab qolgani uchun suv ichdi)\n• get married (turmush qurmoq), get dressed (kiyinmoq), get lost (adashib qolmoq)",
                    examples = listOf(
                        MurphyExample("Turn on the lights, it's getting dark.", "Chiroqlarni yoqing, qorong'i tushyapti.", "getting dark = qorong'i bo'lyapti"),
                        MurphyExample("They got married last year.", "Ular o'tgan yili turmush qurishdi.", "got married"),
                        MurphyExample("I got lost in the city center.", "Shahar markazida adashib qoldim.", "got lost = adashdim")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Get to + Joy & Transport turlari",
                    formula = "get to (yetib bormoq) | get in/out | get on/off",
                    explanationUzbek = "1) Manzilga yetib borish:\n• What time do you get to work? (Ishga soat nechada yetib borasiz?)\n• What time did you get home? (Uyga soat nechada yetib keldingiz? - 'to' yo'q!)\n\n2) Transportga chiqish va tushish:\n• CAR / TAXI: get in the car (mashinaga o'tirmoq), get out of the car (tushmoq)\n• BUS / TRAIN / PLANE: get on the bus (avtobusga chiqmoq), get off the train (poyezddan tushmoq)",
                    examples = listOf(
                        MurphyExample("We got to London at 7 PM.", "Biz Londonga kechki soat 7 da yetib bordik.", "get to + city"),
                        MurphyExample("We need to get off at the next bus stop.", "Biz keyingi bekatda tushishimiz kerak.", "get off the bus"),
                        MurphyExample("He got into the taxi and drove away.", "U taksiga o'tirdi va jo'nab ketdi.", "get into the taxi")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u57_ex1",
                    exerciseNumber = "57.1",
                    taskType = "CHOICE",
                    question = "What time did you get ______ home last night?",
                    options = listOf("(no preposition)", "to", "at", "in"),
                    correctOptionIndex = 0,
                    correctAnswerText = "(no preposition)",
                    explanationUzbek = "'home' so'zi bilan 'get to home' deyilmaydi, to'g'ridan-to'g'ri 'get home' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u57_ex2",
                    exerciseNumber = "57.2",
                    taskType = "CHOICE",
                    question = "It was raining and I didn't have an umbrella, so I got ______.",
                    options = listOf("wet", "wetly", "to wet", "wetting"),
                    correctOptionIndex = 0,
                    correctAnswerText = "wet",
                    explanationUzbek = "'get wet' = ho'l bo'lib qolmoq (get + sifat)."
                ),
                MurphyExerciseItem(
                    id = "u57_ex3",
                    exerciseNumber = "57.3",
                    taskType = "CHOICE",
                    question = "We bought our tickets and ______ the train.",
                    options = listOf("got on", "got in", "got into", "got off"),
                    correctOptionIndex = 0,
                    correctAnswerText = "got on",
                    explanationUzbek = "Poyezd, avtobus, samolyotga minish 'get on' orqali ifodalanadi."
                ),
                MurphyExerciseItem(
                    id = "u57_ex4",
                    exerciseNumber = "57.4",
                    taskType = "CHOICE",
                    question = "Did you ______ my message this morning?",
                    options = listOf("get", "got", "getting", "gets"),
                    correctOptionIndex = 0,
                    correctAnswerText = "get",
                    explanationUzbek = "'did you' so'roq shaklidan keyin fe'lning 1-shakli 'get' keladi (Did you get = oldingizmi)."
                ),
                MurphyExerciseItem(
                    id = "u57_ex5",
                    exerciseNumber = "57.5",
                    taskType = "CHOICE",
                    question = "The taxi stopped and Sarah ______.",
                    options = listOf("got out", "got off", "got away", "got over"),
                    correctOptionIndex = 0,
                    correctAnswerText = "got out",
                    explanationUzbek = "Taksidan yoki mashinadan tushish = 'get out of / got out'."
                )
            )
        ),

        // UNIT 58
        MurphyUnit(
            unitNumber = 58,
            title = "do and make",
            subtitleUzbek = "'Do' va 'Make' fe'llarining aniq farqlari va turg'un birikmalari",
            groupName = "10-Guruh: Essential Verbs & Phrasal Verbs (57–62)",
            keyTakeawaysUzbek = listOf(
                "DO = Ish-harakat, vazifalar, noaniq faoliyat: do homework, do the dishes, do housework, do exercise, do business.",
                "MAKE = Yangi narsa yaratish, ishlab chiqarish, tayyorlash: make coffee, make a cake, make dinner, make a mistake, make noise.",
                "DO birikmalari: do your best, do me a favor, do shopping, do nothing, do well.",
                "MAKE birikmalari: make a phone call, make a decision, make an appointment, make money, make friends.",
                "Oltin qoida: Noldan moddiy yoki nomoddiy mahsulot paydo bo'lsa -> MAKE; Jarayon va burch bo'lsa -> DO."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "DO fe'lining asosiy qo'llanishi",
                    formula = "do + vazifa / majburiyat / umumiy harakat",
                    explanationUzbek = "DO fe'li harakatning o'zini, ish-vazifalarni bajarishda ishlatiladi:\n\n• do homework / do housework (uy vazifasi / uy yumushlarini qilmoq)\n• do the dishes / do the laundry (idishlarni / kiyimlarni yuvmoq)\n• do a course / do an exam (kurs o'qimoq / imtihon topshirmoq)\n• do exercise / do sport (mashq qilmoq)\n• do business (biznes qilmoq)\n• What are you doing? (Nima qilyapsan?)\n• I have nothing to do. (Qiladigan hech vaqom yo'q)",
                    examples = listOf(
                        MurphyExample("I have to do the washing up after dinner.", "Kechki ovqatdan keyin idishlarni yuvishim kerak.", "do the washing up"),
                        MurphyExample("Could you do me a favor, please?", "Menga bir yaxshilik qila olasizmi?", "do me a favor"),
                        MurphyExample("Just relax and do your best in the test.", "Xotirjam bo'l va testda qo'lingdan kelganicha yaxshi natija ko'rsat.", "do your best")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "MAKE fe'lining asosiy qo'llanishi",
                    formula = "make + yangi narsa yaratish / tayyorlash / natija",
                    explanationUzbek = "MAKE fe'li biror yangi narsani yaratish, qurish, ovqat yoki ichimlik tayyorlashda qo'llanadi:\n\n• make food / make tea / make a sandwich (taom / choy / sendvich tayyorlamoq)\n• make a mistake (xato qilmoq)\n• make a noise (shovqin solmoq)\n• make a phone call (telefon qo'ng'irog'i qilmoq)\n• make a decision (qaror qabul qilmoq)\n• make an appointment (qabulga yozilmoq)\n• make money (pul topmoq / ishlamoq)\n• make friends (do'st orttirmoq)",
                    examples = listOf(
                        MurphyExample("I am making a cup of coffee right now.", "Hozir bir piyola kofe tayyorlayapman.", "make coffee"),
                        MurphyExample("Try not to make any mistakes in the test.", "Testda xatolarga yo'l qo'ymaslikka harakat qil.", "make a mistake"),
                        MurphyExample("Don't make so much noise! The baby is sleeping.", "Bunchalik shovqin qilmang! Chaqaloq uxlayapti.", "make a noise")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u58_ex1",
                    exerciseNumber = "58.1",
                    taskType = "CHOICE",
                    question = "I need to ______ a phone call to my manager.",
                    options = listOf("make", "do", "take", "give"),
                    correctOptionIndex = 0,
                    correctAnswerText = "make",
                    explanationUzbek = "Telefon qo'ng'irog'i qilish ingliz tilida 'make a phone call' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u58_ex2",
                    exerciseNumber = "58.2",
                    taskType = "CHOICE",
                    question = "Have you ______ your homework yet?",
                    options = listOf("done", "made", "doing", "making"),
                    correctOptionIndex = 0,
                    correctAnswerText = "done",
                    explanationUzbek = "Uy vazifasini bajarish = 'do homework' -> 'done your homework'."
                ),
                MurphyExerciseItem(
                    id = "u58_ex3",
                    exerciseNumber = "58.3",
                    taskType = "CHOICE",
                    question = "Could you please ______ me a favor?",
                    options = listOf("do", "make", "give", "bring"),
                    correctOptionIndex = 0,
                    correctAnswerText = "do",
                    explanationUzbek = "'do somebody a favor' = yaxshilik / iltifot ko'rsatmoq."
                ),
                MurphyExerciseItem(
                    id = "u58_ex4",
                    exerciseNumber = "58.4",
                    taskType = "CHOICE",
                    question = "Be careful! Don't ______ any mistakes in the document.",
                    options = listOf("make", "do", "create", "produce"),
                    correctOptionIndex = 0,
                    correctAnswerText = "make",
                    explanationUzbek = "Xatoga yo'l qo'ymoq = 'make a mistake'."
                ),
                MurphyExerciseItem(
                    id = "u58_ex5",
                    exerciseNumber = "58.5",
                    taskType = "CHOICE",
                    question = "She is ______ an English course at the language center.",
                    options = listOf("doing", "making", "creating", "acting"),
                    correctOptionIndex = 0,
                    correctAnswerText = "doing",
                    explanationUzbek = "Kursda o'qimoq = 'do a course'."
                )
            )
        ),

        // UNIT 59
        MurphyUnit(
            unitNumber = 59,
            title = "have and have got",
            subtitleUzbek = "Egalik va birikmalar: have vs have got farqlari",
            groupName = "10-Guruh: Essential Verbs & Phrasal Verbs (57–62)",
            keyTakeawaysUzbek = listOf(
                "EGALIK (Possession): 'I have a car' va 'I have got a car' bir xil ma'noni bildiradi (menda mashina bor).",
                "HAVE GOT asosan og'zaki ingliz tilida va faqat HOZIRGI ZAMONDA ishlatiladi: I've got, he has got (he's got).",
                "INKOR VA SAVOL: 'I haven't got a car' YOKI 'I don't have a car'. 'Have you got a car?' YOKI 'Do you have a car?'.",
                "O'TGAN ZAMONDA 'got' ishlatilmaydi: faqat 'I had a car', 'I didn't have', 'Did you have?'.",
                "HARAKAT BIRIKMALARI: have breakfast (nonushta qilmoq), have a shower (dush qabul qilmoq), have fun (maroqli vaqt o'tkazmoq) birikmalarida 'got' ISHLATILMAYDI!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Egalikda: have yoki have got",
                    formula = "I/you/we/they have (got) | he/she/it has (got)",
                    explanationUzbek = "Mulk, oila a'zolari yoki kasalliklar haqida gapirganda ikkala shakl ham to'g'ri:\n\n• I have a headache = I've got a headache. (Boshim og'riyapti)\n• He has two sisters = He's got two sisters. (Uning ikkita singlisi bor)\n• They don't have a garden = They haven't got a garden. (Ularning bog'i yo'q)\n• Do you have any questions? = Have you got any questions? (Savollaringiz bormi?)",
                    examples = listOf(
                        MurphyExample("I have got a new laptop.", "Menda yangi noutbuk bor.", "have got = have"),
                        MurphyExample("Has she got any brothers?", "Uning akalari bormi?", "Savol: Has she got..."),
                        MurphyExample("We haven't got enough money.", "Bizda yetarli pul yo'q.", "haven't got = don't have")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Have birikmalari (bu yerda 'got' ISHLATILMAYDI)",
                    formula = "have + ovqat / ichimlik / hordiq harakatlari",
                    explanationUzbek = "Agar 'have' egalik emas, balki ovqatlanish, dush olish yoki vaqt o'tkazishni bildirsa, unga 'got' qo'shib bo'lmaydi va savol/inkor 'do/does/did' bilan yasaladi:\n\n• have breakfast / have lunch / have dinner (ovqatlanmoq)\n• have a cup of tea / have a coffee (choy/kofe ichmoq)\n• have a shower / have a bath (cho'milmoq/dush olmoq)\n• have a good time / have fun (maroqli vaqt o'tkazmoq)\n• have a rest (dam olmoq)\n\n⚠️ 'I am having got breakfast' DEYILMAYDI! 'I am having breakfast' deyiladi.",
                    examples = listOf(
                        MurphyExample("I usually have a shower in the morning.", "Men odatda ertalab dush qabul qilaman.", "have a shower (got yo'q)"),
                        MurphyExample("Did you have a good flight?", "Parvozingiz yaxshi o'tdimi?", "have a good flight"),
                        MurphyExample("We are having lunch right now.", "Biz hozir tushlik qilyapmiz.", "am/is/are having")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u59_ex1",
                    exerciseNumber = "59.1",
                    taskType = "CHOICE",
                    question = "______ got a spare pen I can borrow?",
                    options = listOf("Have you", "Do you", "Are you", "Did you"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Have you",
                    explanationUzbek = "'got' so'zi bor bo'lgani uchun savol 'Have you got...' deb tuziladi."
                ),
                MurphyExerciseItem(
                    id = "u59_ex2",
                    exerciseNumber = "59.2",
                    taskType = "CHOICE",
                    question = "I usually ______ breakfast at 8:00 AM.",
                    options = listOf("have", "have got", "has got", "having got"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have",
                    explanationUzbek = "Nonushta qilish birikmasida 'got' ishlatilmaydi: 'have breakfast'."
                ),
                MurphyExerciseItem(
                    id = "u59_ex3",
                    exerciseNumber = "59.3",
                    taskType = "CHOICE",
                    question = "When I was a child, I ______ a pet dog.",
                    options = listOf("had", "had got", "have got", "was have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "had",
                    explanationUzbek = "O'tgan zamonda 'got' ishlatilmaydi, faqat 'had' to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u59_ex4",
                    exerciseNumber = "59.4",
                    taskType = "CHOICE",
                    question = "She doesn't ______ any close friends in this city.",
                    options = listOf("have", "has", "have got", "having"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have",
                    explanationUzbek = "'doesn't' yordamchi fe'lidan keyin oddiy 'have' keladi: doesn't have."
                ),
                MurphyExerciseItem(
                    id = "u59_ex5",
                    exerciseNumber = "59.5",
                    taskType = "CHOICE",
                    question = "Enjoy the party tonight! ______ a good time!",
                    options = listOf("Have", "Have got", "Get", "Make"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Have",
                    explanationUzbek = "'Have a good time' = Vaqtingizni chog' o'tkazing (turg'un tilak)."
                )
            )
        ),

        // UNIT 60
        MurphyUnit(
            unitNumber = 60,
            title = "I / me / my / mine / myself",
            subtitleUzbek = "Ingliz tilidagi barcha olmoshlar tizimi bir jadvalda",
            groupName = "10-Guruh: Essential Verbs & Phrasal Verbs (57–62)",
            keyTakeawaysUzbek = listOf(
                "EGA OLMOSHLARI (Subject): I, you, he, she, it, we, they (gap boshida harakat bajaruvchi).",
                "TO'LDIRUVCHI OLMOSHLARI (Object): me, you, him, her, it, us, them (fe'ldan yoki predlogdan keyin).",
                "EGALIK SIFATLARI (Possessive adjective + ot): my book, your car, his phone, their house.",
                "MUSTAQIL EGALIK (Possessive pronoun - otsiz): mine (meniki), yours (seniki), his (uniki), hers (uniki), ours (bizniki), theirs (ularniki).",
                "O'ZLIK OLMOSHLARI (Reflexive): myself, yourself, himself, herself, itself, ourselves, yourselves, themselves (by myself = o'zim yolg'iz)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Olmoshlar jadvali va to'g'ri qo'llanilishi",
                    formula = "I -> me -> my (book) -> mine -> myself",
                    explanationUzbek = "Olmoshlarning shakllari vazifasiga qarab farqlanadi:\n\n• I saw him. (Men uni ko'rdim - I=ega, him=to'ldiruvchi)\n• Give that to me. (Uni menga ber - predlogdan keyin me)\n• This is my jacket. (Bu mening kurtkam - my + ot)\n• This jacket is mine. (Bu kurtka meniki - mine yolg'iz keladi)\n• Is that car yours or theirs? (U mashina siznikimi yoki ularnikimi?)",
                    examples = listOf(
                        MurphyExample("She invited us to her birthday party.", "U bizni tug'ilgan kun bazmiga taklif qildi.", "she (ega), us (to'ldiruvchi), her (egalik)"),
                        MurphyExample("Whose bag is this? - It's mine.", "Bu kimning sumkasi? - Meniki.", "mine (otsiz qo'llanadi)"),
                        MurphyExample("They live in our street, but their house is bigger than ours.", "Ular bizning ko'chada yashashadi, lekin ularning uyi biznikidan kattaroq.", "our vs ours")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "O'zlik olmoshlari (-self / -selves)",
                    formula = "Ega + fe'l + o'zlik olmoshi (harakat o'ziga qaytganda)",
                    explanationUzbek = "Harakat bajaruvchi va harakat qaratilgan shaxs bir xil bo'lsa, -self qo'shiladi:\n\n• He cut himself while shaving. (U soqol olayotganda o'zini kesib oldi)\n• She looked at herself in the mirror. (U oynada o'ziga qaradi)\n• Take care of yourselves! (O'zlaringizni ehtiyot qilinglar!)\n\nBY MYSELF = Yolg'iz, o'zim mustaqil ravishda:\n• I live by myself. (= I live alone, yolg'iz yashayman)\n• Did you do this homework by yourself? (Bu vazifani o'zing mustaqil bajardingmi?)",
                    examples = listOf(
                        MurphyExample("I repaired the bike by myself.", "Velosipedni o'zim yolg'iz tuzatdim.", "by myself = yolg'iz o'zim"),
                        MurphyExample("Help yourselves to some food and drinks.", "Ovqat va ichimliklardan o'zingiz oling (marhamat).", "help yourselves"),
                        MurphyExample("The children can dress themselves now.", "Bolalar endi o'zlari mustaqil kiyina olishadi.", "dress themselves")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u60_ex1",
                    exerciseNumber = "60.1",
                    taskType = "CHOICE",
                    question = "This is not my passport. It must be ______.",
                    options = listOf("yours", "your", "you", "yourself"),
                    correctOptionIndex = 0,
                    correctAnswerText = "yours",
                    explanationUzbek = "Otdan keyin yolg'iz kelganda mustaqil egalik olmoshi 'yours' (seniki) ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u60_ex2",
                    exerciseNumber = "60.2",
                    taskType = "CHOICE",
                    question = "Can you please give ______ that document on the desk?",
                    options = listOf("me", "I", "my", "mine"),
                    correctOptionIndex = 0,
                    correctAnswerText = "me",
                    explanationUzbek = "Fe'ldan keyin obyekt/to'ldiruvchi olmoshi 'me' keladi (give me = menga ber)."
                ),
                MurphyExerciseItem(
                    id = "u60_ex3",
                    exerciseNumber = "60.3",
                    taskType = "CHOICE",
                    question = "He painted the entire apartment by ______.",
                    options = listOf("himself", "him", "his", "he"),
                    correctOptionIndex = 0,
                    correctAnswerText = "himself",
                    explanationUzbek = "'by himself' = hech kimning yordamisiz, yolg'iz o'zi."
                ),
                MurphyExerciseItem(
                    id = "u60_ex4",
                    exerciseNumber = "60.4",
                    taskType = "CHOICE",
                    question = "Our car is red, but ______ is silver.",
                    options = listOf("theirs", "their", "them", "themselves"),
                    correctOptionIndex = 0,
                    correctAnswerText = "theirs",
                    explanationUzbek = "'theirs' = ularniki (otsiz kelgan egalik shakli)."
                ),
                MurphyExerciseItem(
                    id = "u60_ex5",
                    exerciseNumber = "60.5",
                    taskType = "CHOICE",
                    question = "Be careful with that sharp knife! You might cut ______.",
                    options = listOf("yourself", "you", "your", "yours"),
                    correctOptionIndex = 0,
                    correctAnswerText = "yourself",
                    explanationUzbek = "Harakat o'zingizga qaratilganda 'cut yourself' (o'zingizni kesib olasiz) bo'ladi."
                )
            )
        ),

        // UNIT 61
        MurphyUnit(
            unitNumber = 61,
            title = "look at ... / listen to ... (Prepositions with verbs)",
            subtitleUzbek = "Fe'llardan keyin qat'iy keladigan predloglar",
            groupName = "10-Guruh: Essential Verbs & Phrasal Verbs (57–62)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida ko'p fe'llar faqat o'ziga xos predloglar bilan ma'no hosil qiladi.",
                "LOOK AT (qaramoq) vs LOOK FOR (qidirmoq) vs LOOK AFTER (g'amxo'rlik qilmoq / qarab turmoq).",
                "LISTEN TO = tinglamoq (doim 'to' bilan: listen to music, listen to me).",
                "WAIT FOR = kutmoq (wait for a bus, wait for you).",
                "SPEAK / TALK TO = gaplashmoq (speak to the manager, talk to Sarah).",
                "ASK FOR = so'ramoq (ask for the bill, ask for help)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Asosiy fe'llar va ularning predloglari",
                    formula = "fe'l + qat'iy predlog + obyekt",
                    explanationUzbek = "Quyidagi fe'llarning predloglarini yodda saqlang:\n\n• look at: Look at this photo! (Bu rasmga qara!)\n• listen to: I like listening to podcasts. (Podkastlar tinglashni yoqtiraman)\n• wait for: Don't wait for me, I'll be late. (Meni kutma, kechikaman)\n• speak to / talk to: Can I speak to Doctor Smith? (Doktor Smit bilan gaplashsam bo'ladimi?)\n• write to: Write to me soon. (Menga tezda xat yoz)\n• think about / of: What are you thinking about? (Nima haqida o'ylayapsan?)",
                    examples = listOf(
                        MurphyExample("Who are you waiting for?", "Kimni kutyapsiz?", "wait for"),
                        MurphyExample("Listen to what the teacher is saying.", "O'qituvchi nima deyayotganini tinglang.", "listen to"),
                        MurphyExample("Did you ask for the check?", "Hisob-kitobni so'radingizmi?", "ask for")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Look fe'lining 3 xil predlogi farqlari",
                    formula = "look at (qaramoq) | look for (qidirmoq) | look after (parvarishlamoq)",
                    explanationUzbek = "Look fe'li predlogiga qarab butunlay boshqa ma'noga aylanadi:\n\n1) LOOK AT = nigoh tashlamoq, qaramoq:\n• Look at that beautiful bird in the tree!\n\n2) LOOK FOR = topishga intilmoq, qidirmoq:\n• I've lost my keys. Can you help me look for them? (Kalitlarimni yo'qotdim, qidirishga yordam berasanmi?)\n\n3) LOOK AFTER = bolaga, bemorga yoki hayvonga qarab turmoq, parvarishlamoq:\n• Can you look after my cat while I am on holiday? (Ta'tilda ekanligimda mushugimga qarab tura olasanmi?)",
                    examples = listOf(
                        MurphyExample("She is looking for a new apartment.", "U yangi kvartira qidiryapti.", "look for = qidirmoq"),
                        MurphyExample("Nurses look after patients in hospitals.", "Hamshiralar kasalxonalarda bemorlarga qaraydilar.", "look after = g'amxo'rlik qilmoq"),
                        MurphyExample("Look at the board, please.", "Iltimos, doskaga qarang.", "look at = qaramoq")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u61_ex1",
                    exerciseNumber = "61.1",
                    taskType = "CHOICE",
                    question = "I love listening ______ classical music in the evening.",
                    options = listOf("to", "at", "for", "about"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Musiqa yoki kishini tinglash doim 'listen to' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u61_ex2",
                    exerciseNumber = "61.2",
                    taskType = "CHOICE",
                    question = "I can't find my glasses. I am looking ______ them.",
                    options = listOf("for", "at", "after", "to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Yo'qolgan narsani qidirish = 'look for'."
                ),
                MurphyExerciseItem(
                    id = "u61_ex3",
                    exerciseNumber = "61.3",
                    taskType = "CHOICE",
                    question = "Can you look ______ our children while we go shopping?",
                    options = listOf("after", "at", "for", "to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "after",
                    explanationUzbek = "Bolalarga qarab turish / parvarishlash = 'look after'."
                ),
                MurphyExerciseItem(
                    id = "u61_ex4",
                    exerciseNumber = "61.4",
                    taskType = "CHOICE",
                    question = "We have been waiting ______ the bus for 30 minutes.",
                    options = listOf("for", "to", "at", "about"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Avtobusni kutmoq = 'wait for the bus'."
                ),
                MurphyExerciseItem(
                    id = "u61_ex5",
                    exerciseNumber = "61.5",
                    taskType = "CHOICE",
                    question = "When you finish your meal, ask the waiter ______ the bill.",
                    options = listOf("for", "to", "at", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Biror narsa so'ramoq = 'ask for something'."
                )
            )
        ),

        // UNIT 62
        MurphyUnit(
            unitNumber = 62,
            title = "go in / fall off / turn on (Phrasal verbs 1)",
            subtitleUzbek = "Frazali fe'llar (Phrasal Verbs): Ichki, tashqi va qurilma harakatlari",
            groupName = "10-Guruh: Essential Verbs & Phrasal Verbs (57–62)",
            keyTakeawaysUzbek = listOf(
                "FRAZALI FE'L = Fe'l + ravish/predlog (in, out, on, off, up, down). Ma'nosi ko'pincha tubdan o'zgaradi.",
                "HARAKATLAR: come in (kirmoq), go out (tashqariga chiqmoq), fall off (yiqilib tushmoq), stand up (o'rnidan turmoq), sit down (o'tirmoq).",
                "QURILMALAR: turn on (chiroq/qurilmani yoqmoq), turn off (o'chirmoq), turn up (ovozini balandlatmoq), turn down (pastlatmoq).",
                "KIYIM-KECHAK: put on your shoes (kiyinmoq), take off your coat (yechmoq), try on (kiyib ko'rmoq).",
                "SO'Z TARTIBI: Turn on the light = Turn the light on. Lekin olmosh bo'lsa faqat o'rtada: Turn it on (Turn on it EMAS!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Yo'nalish va harakat bildiruvchi frazali fe'llar",
                    formula = "fe'l + in / out / on / off / up / down",
                    explanationUzbek = "Harakatning yo'nalishini ko'rsatuvchi eng ommabop iboralar:\n\n• come in: Please come in and take a seat. (Iltimos, kiring va o'tiring)\n• go out: Are you going out tonight? (Bugun oqshom ko'chaga chiqasizmi?)\n• stand up / sit down: Everyone stood up when he entered. (U kirganda hamma o'rnidan turdi)\n• fall off: He fell off his bicycle and hurt his knee. (U velosipedidan yiqilib tushib tizzasini og'ritib oldi)\n• lie down: I'm not feeling well, I'll go and lie down. (O'zimni yomon his qilyapman, borib yotaman)",
                    examples = listOf(
                        MurphyExample("Don't drop that plate! It will break.", "Likopchani tushirib yuborma! Sinadi.", "fall down / drop"),
                        MurphyExample("She got up early in the morning.", "U ertalab erta o'rnidan turdi.", "get up = o'rindan turmoq"),
                        MurphyExample("Come back! You forgot your bag.", "Qaytib kel! Sumkangni unutding.", "come back = qaytmoq")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Qurilmalar va kiyimlarga oid frazali fe'llar",
                    formula = "turn on/off (qurilmalar) | put on / take off (kiyimlar)",
                    explanationUzbek = "Qurilmalar va kiyinishdagi qat'iy juftliklar:\n\n1) QURILMALAR (chiroq, televizor, radio):\n• turn on = yoqmoq / switch on\n• turn off = o'chirmoq / switch off\n• turn up = ovozini baland qilmoq\n• turn down = ovozini pasaytirmoq\n\n2) KIYIM-KECHAK:\n• put on = kiyib olmoq (Put on your jacket)\n• take off = yechmoq (Take off your shoes)\n\n⚠️ OLMOSH QOIDASI: Agar it/them ishlatilsa, u har doim fe'l bilan qo'shimcha o'rtasida bo'ladi:\n• Turn it on! (Turn on it NOTO'G'RI)\n• Put them on! (Put on them NOTO'G'RI)\n• Take it off! (Take off it NOTO'G'RI)",
                    examples = listOf(
                        MurphyExample("It's dark in here. Could you turn on the light?", "Bu yer qorong'i ekan. Chiroqni yoqib yubora olasizmi?", "turn on the light"),
                        MurphyExample("The television is too loud. Please turn it down.", "Televizor juda baland. Iltimos ovozini pasaytiring.", "turn it down"),
                        MurphyExample("It's very warm inside, take off your jacket.", "Ichkari juda issiq, kurtkangizni yeching.", "take off")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u62_ex1",
                    exerciseNumber = "62.1",
                    taskType = "CHOICE",
                    question = "It's getting dark. Can you turn ______ the lights?",
                    options = listOf("on", "off", "up", "away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Chiroqni yoqmoq = 'turn on the lights'."
                ),
                MurphyExerciseItem(
                    id = "u62_ex2",
                    exerciseNumber = "62.2",
                    taskType = "CHOICE",
                    question = "Here is your jacket. Put ______ on! It's freezing outside.",
                    options = listOf("it", "them", "him", "its"),
                    correctOptionIndex = 0,
                    correctAnswerText = "it",
                    explanationUzbek = "'jacket' birlikda bo'lgani uchun 'Put it on' (uni kiyib ol) bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u62_ex3",
                    exerciseNumber = "62.3",
                    taskType = "CHOICE",
                    question = "Please take ______ your shoes before entering the house.",
                    options = listOf("off", "out", "away", "down"),
                    correctOptionIndex = 0,
                    correctAnswerText = "off",
                    explanationUzbek = "Oyoq kiyim yoki kiyimni yechmoq = 'take off'."
                ),
                MurphyExerciseItem(
                    id = "u62_ex4",
                    exerciseNumber = "62.4",
                    taskType = "CHOICE",
                    question = "The radio is too loud. Could you please turn it ______?",
                    options = listOf("down", "up", "off", "away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "down",
                    explanationUzbek = "Ovozini pasaytirmoq = 'turn down'."
                ),
                MurphyExerciseItem(
                    id = "u62_ex5",
                    exerciseNumber = "62.5",
                    taskType = "CHOICE",
                    question = "He fell ______ his bike and broke his arm.",
                    options = listOf("off", "out", "away", "up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "off",
                    explanationUzbek = "Velosiped, ot yoki zinadan yiqilib tushish = 'fall off'."
                )
            )
        )
    )
}
