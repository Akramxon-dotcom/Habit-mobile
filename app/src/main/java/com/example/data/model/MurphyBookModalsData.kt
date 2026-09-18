package com.example.data.model

object MurphyBookModalsDatabase {

    val UNITS_MODALS: List<MurphyUnit> = listOf(
        // UNIT 29
        MurphyUnit(
            unitNumber = 29,
            title = "might",
            subtitleUzbek = "Ehtimollik modal fe'li: might + V1 (Ehtimol, bo'lishi mumkin, -ishi mumkin)",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "'might + fe'l' = balki, ehtimol ro'y berishi mumkin (aniq emas, taxminan 50% ehtimol).",
                "Formulasi o'zgarmas: I/you/he/she/they might go (he mights deyilmaydi, -s qo'shilmaydi!).",
                "Inkor shakli: might not (balki ...mas): 'I might not go to work tomorrow'.",
                "Kelajak va hozirgi zamon uchun birdek ishlatiladi: 'Where is Ann? - She might be at home'.",
                "'may' fe'li deyarli 'might' bilan bir xil ma'noda ishlatiladi: 'It may rain' = 'It might rain'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "might + V1 (Ehtimollik va taxmin)",
                    formula = "ega + might + fe'l (V1)",
                    explanationUzbek = "Biror ishning sodir bo'lishi mumkinligini, ammo siz bunga 100% ishonchingiz komil emasligini bildirish uchun 'might' ishlatiladi:\n\n• It's cloudy. It might rain. (Havo bulutli. Yomg'ir yog'ishi mumkin — lekin aniq emas).\n• 'Where is Bob?' - 'He might be in his office.' (Bob qayerda? - Ehtimol kabinetidadir).\n• I might go to the cinema tonight, but I'm not sure. (Bugun kinoga borishim mumkin, lekin aniq bilmayman).\n\n⚠️ 'Might' dan keyin doimo boshlang'ich fe'l keladi (to qo'yilmaydi!): 'might be', 'might go', 'might rain'.",
                    examples = listOf(
                        MurphyExample("It might rain this afternoon.", "Bugun tushdan keyin yomg'ir yog'ishi mumkin.", "might + rain"),
                        MurphyExample("I might buy a new car.", "Yangi mashina sotib olishim mumkin.", "Ehtimol sotib olarman"),
                        MurphyExample("She might know his phone number.", "U uning telefon raqamini bilishi mumkin.", "might + know")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "might not (Inkor shakli: balki ...mas)",
                    formula = "ega + might not + V1",
                    explanationUzbek = "Inkor qilish uchun 'might'dan keyin shunchaki 'not' qo'yiladi (qisqartirilmaydi yoki 'mightn't' juda kam uchraydi):\n\n• I might not go to work tomorrow. I don't feel well. (Ertaga ishga bormasligim mumkin. O'zimni yaxshi his qilmayapman).\n• Sue might not come to the party tonight. She is tired. (Su bugun ziyofatga kelmasligi mumkin. U charchagan).",
                    examples = listOf(
                        MurphyExample("I might not have time to see you.", "Siz bilan ko'rishishga vaqtim bo'lmasligi mumkin.", "might not + have"),
                        MurphyExample("They might not want to sell the house.", "Ular uyni sotishni xohlamasliklari mumkin.", "might not + want")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "may va might (May I...?)",
                    formula = "may = might (ehtimollik) | May I...? (ruxsat so'rash)",
                    explanationUzbek = "1. Ehtimollik ma'nosida 'may' aynan 'might' kabi ishlatiladi:\n• It may rain = It might rain.\n• She may be at home = She might be at home.\n\n2. 'May I...?' esa juda xushmuomalalik bilan ruxsat so'rash uchun qo'llaniladi:\n• May I sit here? (Bu yerga o'tirsam maylimi?)\n• May I ask a question? (Savol bersam maylimi?).",
                    examples = listOf(
                        MurphyExample("It may be true. / It might be true.", "Bu rost bo'lishi mumkin.", "may va might teng kuchli"),
                        MurphyExample("May I use your phone? - Yes, of course.", "Telefoningizdan foydalansam maylimi? - Albatta.", "Xushmuomala ruxsat")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u29_ex1",
                    exerciseNumber = "29.1",
                    taskType = "CHOICE",
                    question = "Take an umbrella with you. It ______ rain later.",
                    options = listOf("might", "must to", "might to", "mights"),
                    correctOptionIndex = 0,
                    correctAnswerText = "might",
                    explanationUzbek = "Kelajakdagi ehtimollik uchun 'might' ishlatiladi. 'to' bilan yoki '-s' qo'shib ishlatilmaydi."
                ),
                MurphyExerciseItem(
                    id = "u29_ex2",
                    exerciseNumber = "29.2",
                    taskType = "CHOICE",
                    question = "'Where is Helen?' - 'I'm not sure. She ______ be in the kitchen.'",
                    options = listOf("might", "is", "must to", "does"),
                    correctOptionIndex = 0,
                    correctAnswerText = "might",
                    explanationUzbek = "Aniq bilmaslik ('I'm not sure') aytilgani sababli taxmin 'might be' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u29_ex3",
                    exerciseNumber = "29.3",
                    taskType = "CHOICE",
                    question = "I'm very tired. I ______ go out tonight.",
                    options = listOf("might not", "might don't", "not might", "don't might"),
                    correctOptionIndex = 0,
                    correctAnswerText = "might not",
                    explanationUzbek = "Inkor shakli 'might not' tarzida yasaladi."
                ),
                MurphyExerciseItem(
                    id = "u29_ex4",
                    exerciseNumber = "29.4",
                    taskType = "CHOICE",
                    question = "______ I ask you a personal question?",
                    options = listOf("May", "Might to", "Do", "Am"),
                    correctOptionIndex = 0,
                    correctAnswerText = "May",
                    explanationUzbek = "Xushmuomalalik bilan ruxsat so'rashda 'May I ask...?' qo'llaniladi."
                ),
                MurphyExerciseItem(
                    id = "u29_ex5",
                    exerciseNumber = "29.5",
                    taskType = "CHOICE",
                    question = "They haven't decided yet. They ______ go to France or Spain.",
                    options = listOf("might", "will definitely", "are going", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "might",
                    explanationUzbek = "Hali qaror qilmaganliklari ehtimollik 'might go' ekanini ko'rsatadi."
                )
            )
        ),

        // UNIT 30
        MurphyUnit(
            unitNumber = 30,
            title = "can and could",
            subtitleUzbek = "Qobiliyat va imkoniyat: can / can't (Hozir) va could / couldn't (O'tgan zamon)",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "CAN = qila olmoq, qo'lidan kelmoq (jismoniy yoki aqliy qobiliyat, imkoniyat).",
                "Formulasi: can + V1 (I can swim, she can speak English). Uchinchi shaxsda -s olmaydi!",
                "Inkor: cannot yoki qisqa CAN'T (I can't drive).",
                "O'tgan zamondagi qobiliyat: COULD va COULDN'T (When I was young, I could run fast).",
                "Iltimos va ruxsat so'rash: 'Can you help me?' yoki yanada xushmuomala 'Could you help me?'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "CAN va CAN'T (Hozirgi qobiliyat)",
                    formula = "can + fe'l (V1) | Inkor: cannot / can't",
                    explanationUzbek = "Qo'lingizdan keladigan yoki imkoni bor narsalar haqida gapirganda 'can' ishlatiladi:\n\n• I can play the piano. (Men pianino chala olaman).\n• Sarah can speak Italian, but she can't speak Spanish. (Sara italyancha gaplasha oladi, ammo ispancha gaplasha olmaydi).\n• Can you swim? - Yes, but not very well. (Suzishni bilasizmi? - Ha, lekin unchalik yaxshi emas).\n\n⚠️ He cans / she cans deyilmaydi! Faqat 'he can', 'she can'.",
                    examples = listOf(
                        MurphyExample("I can drive a car.", "Men mashina hayday olaman.", "Qobiliyat"),
                        MurphyExample("Can you hear that noise?", "Anavi shovqinni eshita olyapsanmi?", "Savol: Can you...?"),
                        MurphyExample("I'm sorry, I can't come to the party.", "Kechirasiz, men mehmondorchilikka kela olmayman.", "can't = cannot")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "COULD va COULDN'T (O'tgan zamon qobiliyati)",
                    formula = "could + fe'l (V1) | Inkor: couldn't",
                    explanationUzbek = "'Could' — bu 'can'ning o'tgan zamon shakli (qila olgan edim, qo'limdan kelardi):\n\n• When I was young, I could run very fast. (Yoshligimda juda tez yugura olar edim).\n• Before Maria came to Britain, she couldn't understand much English. (Mariya Britaniyaga kelishidan oldin ingliz tilini yaxshi tushunolmas edi).\n• I was tired last night, but I couldn't sleep. (Kecha charchagan edim, lekin uxlay olmadim).",
                    examples = listOf(
                        MurphyExample("My grandfather could speak five languages.", "Bobom beshta tilda gaplasha olganlar.", "O'tmishdagi qobiliyat"),
                        MurphyExample("We were tired, but we couldn't sleep.", "Charchagan edik, ammo uxlay olmadik.", "couldn't + sleep"),
                        MurphyExample("I couldn't find my keys yesterday.", "Kecha kalitlarimni topa olmadim.", "couldn't find")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Can you...? va Could you...? (Iltimos va talab)",
                    formula = "Can / Could you + V1 ... please?",
                    explanationUzbek = "Birovdan biror narsa qilib berishni so'rashda 'Can you...?' yoki yanada xushmuomalaroq 'Could you...?' ishlatiladi:\n\n• Can you open the door, please? (Eshikni ochib yubora olasizmi?)\n• Could you pass the salt, please? (Tuzni uzatib yubora olasizmi? — juda xushmuomala)\n• Can I have a glass of water, please? (Bir stakan suv olsam maylimi?).",
                    examples = listOf(
                        MurphyExample("Could you tell me the time, please?", "Vaqtni aytib yubora olasizmi, iltimos?", "Xushmuomala iltimos"),
                        MurphyExample("Can you wait a minute, please?", "Bir daqiqa kuta olasizmi?", "Kundalik iltimos"),
                        MurphyExample("Could I speak to David, please?", "Devid bilan gaplashsam maylimi?", "Telefon muloqotida")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u30_ex1",
                    exerciseNumber = "30.1",
                    taskType = "CHOICE",
                    question = "I'm sorry, but I ______ come to your party tomorrow.",
                    options = listOf("can't", "couldn't", "not can", "am not can"),
                    correctOptionIndex = 0,
                    correctAnswerText = "can't",
                    explanationUzbek = "Kelasi/hozirgi zamonda qila olmaslik uchun 'can't' (cannot) ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u30_ex2",
                    exerciseNumber = "30.2",
                    taskType = "CHOICE",
                    question = "When I was five years old, I ______ swim.",
                    options = listOf("couldn't", "can't", "didn't can", "not could"),
                    correctOptionIndex = 0,
                    correctAnswerText = "couldn't",
                    explanationUzbek = "O'tgan zamondagi holat ('When I was five...') bo'lgani uchun 'couldn't' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u30_ex3",
                    exerciseNumber = "30.3",
                    taskType = "CHOICE",
                    question = "______ you speak a little louder, please? I can't hear you.",
                    options = listOf("Could", "May to", "Do", "Are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Could",
                    explanationUzbek = "Birovdan xushmuomala iltimos qilish: 'Could you speak...?'"
                ),
                MurphyExerciseItem(
                    id = "u30_ex4",
                    exerciseNumber = "30.4",
                    taskType = "CHOICE",
                    question = "She ______ speak three languages fluently.",
                    options = listOf("can", "cans", "can to", "is can"),
                    correctOptionIndex = 0,
                    correctAnswerText = "can",
                    explanationUzbek = "Modal fe'l 'can' hech qachon 's' olmaydi: 'She can speak'."
                ),
                MurphyExerciseItem(
                    id = "u30_ex5",
                    exerciseNumber = "30.5",
                    taskType = "CHOICE",
                    question = "I looked everywhere, but I ______ find my wallet yesterday.",
                    options = listOf("couldn't", "can't", "am not", "wasn't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "couldn't",
                    explanationUzbek = "'yesterday' o'tgan zamon bo'lgani uchun 'couldn't find' bo'ladi."
                )
            )
        ),

        // UNIT 31
        MurphyUnit(
            unitNumber = 31,
            title = "must / mustn't / don't need to",
            subtitleUzbek = "Qat'iy majburiyat (must), taqiq (mustn't) va keraksizlik (don't need to)",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "MUST = qilishi shart, o'ta zarur (Ichki kuchli majburiyat: I must study hard).",
                "MUSTN'T = aslo mumkin emas, qat'iyan taqiqlanadi! (You mustn't touch this wire).",
                "DON'T NEED TO = qilish shart emas, ixtiyoriy (qilsa ham, qilmasa ham bo'laveradi).",
                "DIQQAT: 'mustn't' bilan 'don't need to' butunlay qarama-qarshi ma'noga ega!",
                "O'tgan zamondagi majburiyat uchun 'must' emas, 'HAD TO' ishlatiladi: 'I had to leave early yesterday'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "MUST (Shart, o'ta zarur)",
                    formula = "must + fe'l (V1)",
                    explanationUzbek = "Biror ishni qilish juda muhim, shart yoki shaxsiy qat'iy zarurat deb bilsangiz 'must' ishlatiladi:\n\n• I must go now. It's very late. (Men hozir ketishim shart. Juda kech bo'lib ketdi).\n• You must be careful with that knife. (U pichoq bilan juda ehtiyot bo'lishing shart).\n• This is a fantastic film. You must see it! (Bu ajoyib film. Uni albatta ko'rishing kerak!).\n\n⚠️ O'tgan zamonda 'must' ishlatilmaydi, o'rniga 'HAD TO' keladi:\n• I had to go to the bank yesterday. (Kecha bankka borishimga to'g'ri keldi).",
                    examples = listOf(
                        MurphyExample("I must eat something. I'm very hungry.", "Biror narsa yeyishim kerak. Qornim juda och.", "Zarurat"),
                        MurphyExample("You must wear a seatbelt in the car.", "Mashinada xavfsizlik kamarini taqishingiz shart.", "Qonuniy talab"),
                        MurphyExample("Yesterday I had to work until 9:00.", "Kecha soat 9 gacha ishlashimga to'g'ri keldi.", "O'tgan zamonda: HAD TO")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "MUSTN'T (Taqiq: aslo mumkin emas!)",
                    formula = "must not / mustn't + V1",
                    explanationUzbek = "'Mustn't' — bu qat'iy taqiq. Bu ishni aslo qilish mumkin emas degani:\n\n• You mustn't tell anyone what I said. It's a secret. (Aytganlarimni hech kimga aytishing aslo mumkin emas! Bu sir).\n• You mustn't touch those wires. They are dangerous. (Anavi simlarga zinhor tegmang. Xavfli!).\n• We mustn't be late. The exam starts at 9:00 sharp. (Kech qolmasligimiz shart!).",
                    examples = listOf(
                        MurphyExample("You mustn't smoke here.", "Bu yerda chekish mutlaqo taqiqlanadi.", "Qat'iy taqiq"),
                        MurphyExample("You mustn't forget to lock the door.", "Eshikni qulflashni aslo unutmang.", "Muhim ogohlantirish")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "DON'T NEED TO (Keragi yo'q, shart emas)",
                    formula = "don't need to / doesn't need to + V1",
                    explanationUzbek = "'Don't need to' (yoki 'needn't') — biror ishni qilish majburiy emasligini bildiradi. Qilmasangiz ham bo'ladi, muammo yo'q:\n\n• We have plenty of time. We don't need to hurry. (Vaqtimiz bemalol. Shoshilishimizning keragi yo'q).\n• You don't need to pay now. You can pay later. (Hozir to'lashingiz shart emas. Keyinroq to'lasangiz ham bo'ladi).\n\n⚖️ Taqqoslang:\n• You mustn't do it = Qilma! Taqiqlanadi!\n• You don't need to do it = Qilishing shart emas (istang qil, istang qilma).",
                    examples = listOf(
                        MurphyExample("You don't need to shout. I can hear you.", "Baqirishingiz shart emas. Eshityapman.", "Keraksiz harakat"),
                        MurphyExample("She doesn't need to come if she is busy.", "Agar band bo'lsa, kelishi shart emas.", "Majburiyat yo'q")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u31_ex1",
                    exerciseNumber = "31.1",
                    taskType = "CHOICE",
                    question = "You ______ tell anyone what happened. It is a top secret.",
                    options = listOf("mustn't", "don't need to", "needn't", "must"),
                    correctOptionIndex = 0,
                    correctAnswerText = "mustn't",
                    explanationUzbek = "Sir bo'lgani uchun qat'iy taqiq 'mustn't' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u31_ex2",
                    exerciseNumber = "31.2",
                    taskType = "CHOICE",
                    question = "Tomorrow is a holiday. I ______ get up early.",
                    options = listOf("don't need to", "mustn't", "must", "had to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "don't need to",
                    explanationUzbek = "Dam olish kuni erta turish majburiy emas: 'don't need to get up'."
                ),
                MurphyExerciseItem(
                    id = "u31_ex3",
                    exerciseNumber = "31.3",
                    taskType = "CHOICE",
                    question = "I ______ go to the doctor yesterday because I was feeling ill.",
                    options = listOf("had to", "must", "must to", "have to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "had to",
                    explanationUzbek = "'yesterday' (kecha) o'tgan zamondagi majburiyat uchun faqat 'had to' to'g'ri keladi."
                ),
                MurphyExerciseItem(
                    id = "u31_ex4",
                    exerciseNumber = "31.4",
                    taskType = "CHOICE",
                    question = "This food is very hot. You ______ touch it yet.",
                    options = listOf("mustn't", "don't need to", "need to", "haven't to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "mustn't",
                    explanationUzbek = "Kuyib qolmaslik uchun ogohlantirish va taqiq: 'You mustn't touch it'."
                ),
                MurphyExerciseItem(
                    id = "u31_ex5",
                    exerciseNumber = "31.5",
                    taskType = "CHOICE",
                    question = "We have plenty of food. We ______ go shopping today.",
                    options = listOf("don't need to", "mustn't", "must", "can't to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "don't need to",
                    explanationUzbek = "Oziq-ovqat yetarli bo'lgani sababli bozorga borish keragi yo'q: 'don't need to'."
                )
            )
        ),

        // UNIT 32
        MurphyUnit(
            unitNumber = 32,
            title = "should",
            subtitleUzbek = "Maslahat va tavsiya: should (qilsangiz yaxshi bo'lardi) va shouldn't (qilmaganingiz ma'qul)",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "SHOULD = qilish maqsadga muvofiq, yaxshi g'oya, maslahat (You should go to bed early).",
                "SHOULDN'T = qilmaslik ma'qul, yomon fikr (You shouldn't watch TV all night).",
                "U 'must' kabi majburiyat emas, faqat DO'STONA TAVSIYA bildiradi.",
                "Fikr bildirishda 'I think you should...' va 'I don't think you should...' juda ko'p ishlatiladi.",
                "Savol berish: 'What should I do?' (Nima qilishimni maslahat berasiz?)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "should + V1 (Yaxshi tavsiya)",
                    formula = "should + fe'l (V1)",
                    explanationUzbek = "'You should do something' — bu ishni qilsangiz to'g'ri va foydali bo'ladi degan maslahatdir:\n\n• You look tired. You should go to bed. (Charchagan ko'rinasiz. Uxlashga yotsangiz yaxshi bo'lardi).\n• The film is great. You should go and see it. (Film zo'r ekan. Borib ko'rishingni tavsiya qilaman).\n• When you play tennis, you should always watch the ball. (Tennis o'ynaganda doimo to'pga qarashingiz kerak).",
                    examples = listOf(
                        MurphyExample("You should eat more vegetables.", "Ko'proq sabzavot yeyishingiz kerak.", "Sog'liq uchun maslahat"),
                        MurphyExample("It's a great book. You should read it.", "Ajoyib kitob. Uni o'qishingiz kerak.", "Tavsiya"),
                        MurphyExample("Drivers should wear seatbelts.", "Haydovchilar kamar taqishlari maqsadga muvofiq.", "To'g'ri yo'l-yo'riq")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "shouldn't (Qilmaganingiz ma'qul)",
                    formula = "should not / shouldn't + V1",
                    explanationUzbek = "Biror ishni qilish noto'g'ri yoki yomon oqibatga olib keladi deb hisoblasangiz 'shouldn't' deyiladi:\n\n• You shouldn't work so hard. You need to rest. (Bunchalik qattiq ishlamasligingiz kerak. Dam olishingiz lozim).\n• You shouldn't believe everything you read on the internet. (Internetda o'qigan hamma narsangizga ishonavermasligingiz kerak).\n• Children shouldn't drink too much sugary soda. (Bolalar ko'p shirin gazli ichimliklar ichmasliklari kerak).",
                    examples = listOf(
                        MurphyExample("You shouldn't stay up so late.", "Bunchalik kechgacha uxlamay o'tirmasligingiz kerak.", "Yomon odatga qarshi"),
                        MurphyExample("He shouldn't drive when he is tired.", "Charchagan paytda mashina haydamasligi lozim.", "Xavfsizlik maslahati")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "I think ... should va What should I do?",
                    formula = "I think + ega + should | I don't think + ega + should | Should I...?",
                    explanationUzbek = "O'z maslahatingizni yumshoq va muloyim qilib bildirish uchun:\n\n• I think you should buy that coat. It looks great on you. (Menimcha o'sha paltoni sotib olishingiz kerak. Sizga yarashdi).\n• I don't think you should go out tonight. It's too cold. (Menimcha bugun kechqurun ko'chaga chiqmaganingiz ma'qul).\n\nMaslahat so'rashda:\n• Should I invite Gary to the party? (Garini ziyofatga taklif qilaymi? Nima deysiz?)\n• What do you think I should do? (Mening o'rnimda nima qilgan bo'lardingiz?).",
                    examples = listOf(
                        MurphyExample("I think we should leave now.", "Menimcha hozir yo'lga chiqqanimiz ma'qul.", "I think + should"),
                        MurphyExample("I don't think you should worry.", "Xavotir olmasligingiz kerak deb o'ylayman.", "I don't think + should"),
                        MurphyExample("What should I wear tonight?", "Bugun oqshomda nima kiyishimni maslahat berasiz?", "Maslahat so'rash")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u32_ex1",
                    exerciseNumber = "32.1",
                    taskType = "CHOICE",
                    question = "You look very exhausted. You ______ take a holiday.",
                    options = listOf("should", "must to", "should to", "are should"),
                    correctOptionIndex = 0,
                    correctAnswerText = "should",
                    explanationUzbek = "Do'stona maslahat uchun 'should' ishlatiladi. 'to' qo'shilmaydi."
                ),
                MurphyExerciseItem(
                    id = "u32_ex2",
                    exerciseNumber = "32.2",
                    taskType = "CHOICE",
                    question = "You ______ drink so much coffee before going to bed.",
                    options = listOf("shouldn't", "don't should", "not should", "must"),
                    correctOptionIndex = 0,
                    correctAnswerText = "shouldn't",
                    explanationUzbek = "Qilmaslikni maslahat berishda inkor 'shouldn't' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u32_ex3",
                    exerciseNumber = "32.3",
                    taskType = "CHOICE",
                    question = "I don't think you ______ buy that expensive jacket.",
                    options = listOf("should", "should to", "must to", "would"),
                    correctOptionIndex = 0,
                    correctAnswerText = "should",
                    explanationUzbek = "'I don't think you should...' konstruktsiyasi to'g'ri maslahat shaklidir."
                ),
                MurphyExerciseItem(
                    id = "u32_ex4",
                    exerciseNumber = "32.4",
                    taskType = "CHOICE",
                    question = "What ______ I do to improve my English?",
                    options = listOf("should", "do", "am", "must to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "should",
                    explanationUzbek = "Maslahat so'rash: 'What should I do?' (Nima qilishim kerak?)."
                ),
                MurphyExerciseItem(
                    id = "u32_ex5",
                    exerciseNumber = "32.5",
                    taskType = "CHOICE",
                    question = "It's late. Do you think we ______ go home?",
                    options = listOf("should", "must to", "are", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "should",
                    explanationUzbek = "'Do you think we should go home?' — ketganimiz ma'qulmi?"
                )
            )
        ),

        // UNIT 33
        MurphyUnit(
            unitNumber = 33,
            title = "I have to...",
            subtitleUzbek = "Tashqi majburiyat: have to / has to (Hozir), had to (O'tmish) va don't have to",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "HAVE TO = qilishga majburman (qonun-qoidalar, ish talablari yoki tashqi sharoit taqozosi).",
                "Birlikda HAS TO: 'She has to wear glasses for reading'.",
                "O'tgan zamon: HAD TO ('I had to walk home last night because there were no buses').",
                "Savol shakli: Do you have to...? / Does he have to...? / Did you have to...?",
                "DON'T HAVE TO = qilish shart emas, majbur emassan (xohishing)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "have to / has to (Tashqi vaziyat majburiyati)",
                    formula = "I/we/you/they have to + V1 | he/she/it has to + V1",
                    explanationUzbek = "Bu sizning o'z xohishingiz emas, balki qonun, qoida, boshliq yoki hayotiy vaziyat majburlayotgani sababli ishlatiladi:\n\n• I have to wear glasses for reading. (Ko'rish qobiliyatim tufayli o'qish uchun ko'zoynak taqishga majburman).\n• Robert can't come out tonight. He has to work late. (Robert bugun chiqa olmaydi. U kechgacha ishlashga majbur).\n• In Britain, you have to drive on the left. (Britaniyada chap tomondan haydashga majbursiz — qonun shunaqa).",
                    examples = listOf(
                        MurphyExample("I have to get up early tomorrow. My flight is at 7:00.", "Ertaga erta turishimga to'g'ri keladi. Samolyotim soat 7 da.", "Sharoit taqozosi"),
                        MurphyExample("She has to travel a lot for her job.", "U ishi yuzasidan ko'p sayohat qilishga majbur.", "has to (she)"),
                        MurphyExample("We have to pass an exam before starting.", "Boshlashdan oldin imtihon topshirishimiz shart.", "Qoida talabi")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "O'tgan zamon (had to) va Savol tuzilishi",
                    formula = "Past: had to | Savol: Do/Does/Did + have to",
                    explanationUzbek = "O'tgan zamonda faqat 'had to' qo'llaniladi:\n• I was late yesterday because I had to go to the doctor.\n• We had to walk home last night.\n\nSavol shaklida do, does, did yordamchi fe'llari oldinga chiqadi:\n• What time do you have to get up tomorrow?\n• Does he have to wear a uniform at school?\n• Why did you have to leave so early yesterday?",
                    examples = listOf(
                        MurphyExample("Did you have to wait a long time?", "Uzoq kutishingizga to'g'ri keldimi?", "O'tgan zamon savoli: Did you have to...?"),
                        MurphyExample("What time does she have to leave?", "U soat nechada ketishi kerak?", "Does she have to...?"),
                        MurphyExample("I had to pay a fine yesterday.", "Kecha jarima to'lashimga to'g'ri keldi.", "had to (o'tmish)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "don't have to vs mustn't",
                    formula = "don't have to = majbur emassiz | mustn't = qat'iyan man etiladi",
                    explanationUzbek = "⚠️ Bu ikki iboraning farqi nihoyatda katta:\n\n1. 'You don't have to do it' = Buni qilishingiz shart emas. Qilsangiz ham, qilmasangiz ham ixtiyoringiz:\n• Tomorrow is Sunday, so I don't have to go to work.\n• You don't have to shout. I'm right here.\n\n2. 'You mustn't do it' = Buni aslo qilmang! Qonun yoki xavfsizlik taqiqlaydi:\n• You mustn't tell anyone.",
                    examples = listOf(
                        MurphyExample("I don't have to work on Saturdays.", "Shanba kunlari ishlashga majbur emasman.", "Majburiyat yo'q"),
                        MurphyExample("She doesn't have to pay for the ticket.", "U chipta uchun to'lashga majbur emas (bepul).", "doesn't have to")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u33_ex1",
                    exerciseNumber = "33.1",
                    taskType = "CHOICE",
                    question = "In Britain, cars ______ drive on the left side of the road.",
                    options = listOf("have to", "has to", "must to", "having to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have to",
                    explanationUzbek = "Ko'plikdagi ot 'cars' uchun yo'l harakati qoidasi: 'have to drive'."
                ),
                MurphyExerciseItem(
                    id = "u33_ex2",
                    exerciseNumber = "33.2",
                    taskType = "CHOICE",
                    question = "Mark didn't have a car, so he ______ walk home yesterday.",
                    options = listOf("had to", "has to", "have to", "musted"),
                    correctOptionIndex = 0,
                    correctAnswerText = "had to",
                    explanationUzbek = "'yesterday' o'tgan zamondagi zarurat bo'lgani sababli 'had to' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u33_ex3",
                    exerciseNumber = "33.3",
                    taskType = "CHOICE",
                    question = "______ you have to work late every day?",
                    options = listOf("Do", "Are", "Have", "Did to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Do",
                    explanationUzbek = "Present Simple da savol 'Do you have to...?' shaklida beriladi."
                ),
                MurphyExerciseItem(
                    id = "u33_ex4",
                    exerciseNumber = "33.4",
                    taskType = "CHOICE",
                    question = "Tomorrow is my day off, so I ______ get up early.",
                    options = listOf("don't have to", "mustn't", "haven't to", "not have to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "don't have to",
                    explanationUzbek = "Dam olish kuni erta turish majburiyati yo'q: 'don't have to'."
                ),
                MurphyExerciseItem(
                    id = "u33_ex5",
                    exerciseNumber = "33.5",
                    taskType = "CHOICE",
                    question = "She ______ wear glasses when she uses the computer.",
                    options = listOf("has to", "have to", "haves to", "is having"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has to",
                    explanationUzbek = "'She' birlik olmoshi bilan 'has to' ishlatiladi."
                )
            )
        ),

        // UNIT 34
        MurphyUnit(
            unitNumber = 34,
            title = "Would you like...? I'd like...",
            subtitleUzbek = "Xushmuomala taklif va istak: Would you like...? (Xohlarmidingiz?) va I'd like... (Istardim)",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "WOULD YOU LIKE...? = Biror narsa xohlarmidingiz? (Muloyim va madaniyatli mehmondo'stlik taklifi).",
                "I'D LIKE... = 'I would like' qisqartmasi = Men istardim, xohlardim (I want dan ko'ra ancha xushmuomala).",
                "WOULD YOU LIKE vs DO YOU LIKE farqi: Do you like = umuman yoqtirasizmi? Would you like = hozir xohlaysizmi?",
                "Would you like + TO + fe'l: 'Would you like to have dinner with us?'",
                "Kafelar va do'konlarda buyurtma berish: 'I'd like a cup of coffee, please'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Would you like...? (Xushmuomala taklif)",
                    formula = "Would you like + ot? | Would you like to + fe'l?",
                    explanationUzbek = "Birovga biror yegulik, ichimlik yoki biror narsani taklif qilganda ishlatiladi:\n\n• Would you like some coffee? - Yes, please. (Kofe xohlarmidingiz? - Ha, iltimos).\n• Would you like a biscuit? - No, thank you. (Pechenye xohlaysizmi? - Rahmat, yo'q).\n• What would you like to drink? (Nima ichishni xohlardingiz?)\n• Would you like to come to a party tomorrow? (Ertaga kechamizga kelishni xohlarmidingiz?).",
                    examples = listOf(
                        MurphyExample("Would you like some tea?", "Choy ichishni xohlarmidingiz?", "Taklif"),
                        MurphyExample("Would you like to play tennis tomorrow?", "Ertaga tennis o'ynashni xohlarmidingiz?", "Would you like to + V1"),
                        MurphyExample("Where would you like to go tonight?", "Bugun qayerga borishni istardingiz?", "Savol so'zi bilan")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "I'd like... (Men istardim / xohlardim)",
                    formula = "I'd like (= I would like) + ot / to + fe'l",
                    explanationUzbek = "'I want' (men xohlayman) biroz qo'pol eshitilishi mumkin. Shuning uchun uning o'rniga doimo xushmuomala 'I'd like' ishlatiladi:\n\n• I'm thirsty. I'd like a glass of water, please. (Chanqadim. Bir stakan suv bersangiz yaxshi bo'lardi).\n• I'd like some information about hotels, please. (Mehmonxonalar haqida ma'lumot olmoqchi edim).\n• I'd like to see the doctor, please. (Shifokor qabuliga kirmoqchi edim).",
                    examples = listOf(
                        MurphyExample("I'd like a chicken sandwich, please.", "Tovuqli sendvich istardim, iltimos.", "Kafeda buyurtma"),
                        MurphyExample("I'd like to ask a question.", "Bir savol bermoqchi edim.", "I'd like to + fe'l"),
                        MurphyExample("We'd like a table for two, please.", "Bizga ikki kishilik stol bering, iltimos.", "We would like")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Would you like...? vs Do you like...?",
                    formula = "Do you like = umuman yoqtirasizmi | Would you like = hozir xohlaysizmi",
                    explanationUzbek = "Bu ikkala savol tez-tez chalkashtiriladi:\n\n1. 'Would you like some tea?' = Hozir sizga choy quyib beraymi? Xohlaysizmi?\n- 'Yes, please.'\n\n2. 'Do you like tea?' = Siz umuman hayotda choy ichishni yaxshi ko'rasizmi?\n- 'Yes, I do. / No, I prefer coffee.'",
                    examples = listOf(
                        MurphyExample("'Would you like an apple?' - 'Yes, please.'", "Olma xohlaysizmi? - Ha, bering.", "Hozirgi taklif"),
                        MurphyExample("'Do you like apples?' - 'Yes, I love them.'", "Olmani yoqtirasizmi? - Ha, yaxshi ko'raman.", "Umumiy xush ko'rish")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u34_ex1",
                    exerciseNumber = "34.1",
                    taskType = "CHOICE",
                    question = "'______ you like a cup of tea?' - 'Yes, please.'",
                    options = listOf("Would", "Do", "Are", "Did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Would",
                    explanationUzbek = "Ichimlik taklif qilishda 'Would you like...?' iborasi qo'llaniladi."
                ),
                MurphyExerciseItem(
                    id = "u34_ex2",
                    exerciseNumber = "34.2",
                    taskType = "CHOICE",
                    question = "I'm hungry. I ______ to eat something.",
                    options = listOf("'d like", "like", "'d liking", "am liking"),
                    correctOptionIndex = 0,
                    correctAnswerText = "'d like",
                    explanationUzbek = "Hozir biror narsa yeyishni istash: 'I'd like to eat' (I would like)."
                ),
                MurphyExerciseItem(
                    id = "u34_ex3",
                    exerciseNumber = "34.3",
                    taskType = "CHOICE",
                    question = "'______ you like bananas?' - 'Yes, I eat them every day.'",
                    options = listOf("Do", "Would", "Are", "Did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Do",
                    explanationUzbek = "Javobda 'har kuni yeyman' (umumiy yoqtirish) aytilgan, shuning uchun 'Do you like bananas?' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u34_ex4",
                    exerciseNumber = "34.4",
                    taskType = "CHOICE",
                    question = "What would you like ______ this evening?",
                    options = listOf("to do", "doing", "do", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to do",
                    explanationUzbek = "'would like' dan keyin fe'l 'to' zarrachasi bilan keladi: 'would you like to do'."
                ),
                MurphyExerciseItem(
                    id = "u34_ex5",
                    exerciseNumber = "34.5",
                    taskType = "CHOICE",
                    question = "Excuse me, I ______ like to speak to the manager.",
                    options = listOf("would", "will", "am", "do"),
                    correctOptionIndex = 0,
                    correctAnswerText = "would",
                    explanationUzbek = "Xushmuomala iltimos: 'I would like to speak...'."
                )
            )
        ),

        // UNIT 35
        MurphyUnit(
            unitNumber = 35,
            title = "Do this! Don't do that! Let's do this!",
            subtitleUzbek = "Buyruq, iltimos va taklif mayli (Imperative): Open the door! Don't touch! Let's go!",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "Buyruq va iltimos egasiz, to'g'ridan-to'g'ri fe'l bilan boshlanadi: 'Come in!', 'Sit down!'.",
                "Inkor shakli: DON'T + fe'l ('Don't open the window!', 'Don't be late!').",
                "Muloyimlik kiritish uchun gap oxiriga yoki boshiga 'please' qo'shiladi.",
                "LET'S = Let us (Keling, birga qilaylik!): 'Let's go!', 'Let's have a break!'.",
                "Let's ning inkor shakli: LET'S NOT + fe'l: 'Let's not go out tonight. It's raining'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Buyruq va yo'l-yo'riq: Fe'lning o'zi",
                    formula = "Fe'l (V1) + qolgan qism! (Ega qo'yilmaydi)",
                    explanationUzbek = "Birovga biror narsa qilishni aytganda, buyurgan yoki taklif qilganda fe'lning o'zi ishlatiladi:\n\n• Come in and sit down. (Kiring va o'tiring).\n• Look at this photo. (Bu rasmga qarang).\n• Have another cup of tea. (Yana bir chashka choy oling — taklif).\n• Turn left at the traffic lights. (Svetofordan chapga buriling — yo'l ko'rsatish).\n• Be quiet, please. I'm reading. (Iltimos, tinchlaning. O'qiyapman).",
                    examples = listOf(
                        MurphyExample("Close the door, please.", "Eshikni yopib yuboring, iltimos.", "Iltimos"),
                        MurphyExample("Be careful with that knife!", "U pichoq bilan ehtiyot bo'l!", "Ogohlantirish"),
                        MurphyExample("Have a nice holiday!", "Yaxshi dam olib keling!", "Tilak")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Inkor buyruq: Don't...",
                    formula = "Don't + fe'l (V1)!",
                    explanationUzbek = "Biror ishni qilmaslikni buyurishda har doim 'Don't' ishlatiladi:\n\n• Don't fall! (Yiqilib tushmang!).\n• Don't touch that wire. It's dangerous. (U simga tegmang. Xavfli!).\n• Don't be late! (Kech qolmang!).\n• Don't forget to post the letter. (Xatni jo'natishni unutmang!).",
                    examples = listOf(
                        MurphyExample("Don't worry, everything will be fine.", "Xavotir olmang, hammasi yaxshi bo'ladi.", "Dalda"),
                        MurphyExample("Don't make so much noise!", "Bunchalik shovqin solmang!", "Buyruq"),
                        MurphyExample("Don't be silly!", "Ahmoqlik qilma!", "Tanbeh")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Let's... va Let's not... (Birgalikdagi taklif)",
                    formula = "Let's + fe'l | Inkor: Let's not + fe'l",
                    explanationUzbek = "O'zingiz va boshqalar birgalikda biror ishni qilishni taklif qilganda 'Let's' (Let us) ishlatiladi:\n\n• It's a lovely day. Let's go for a walk. (Ajoyib kun. Keling, sayrga chiqaylik).\n• I'm tired. Let's take a taxi. (Charchadim. Keling, taksida ketamiz).\n\nInkor shakli 'Let's not':\n• It's cold outside. Let's not go out. (Tashqarida sovuq. Keling, ko'chaga chiqmaylik).\n• Let's not wait for him any longer. (Keling, uni ortiq kutmaylik).",
                    examples = listOf(
                        MurphyExample("Let's have fish for dinner.", "Keling, kechki ovqatga baliq yeymiz.", "Let's + V1"),
                        MurphyExample("Let's not tell them yet.", "Keling, hozircha ularga aytmay turaylik.", "Let's not + V1"),
                        MurphyExample("Let's go, shall we?", "Qani kettik, bo'ladimi?", "Taklifni tasdiqlash")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u35_ex1",
                    exerciseNumber = "35.1",
                    taskType = "CHOICE",
                    question = "______ the window, please. It's very cold in here.",
                    options = listOf("Close", "You close", "Closing", "Closed"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Close",
                    explanationUzbek = "Buyruq/iltimos gap to'g'ridan-to'g'ri fe'lning 1-shakli bilan boshlanadi: 'Close the window'."
                ),
                MurphyExerciseItem(
                    id = "u35_ex2",
                    exerciseNumber = "35.2",
                    taskType = "CHOICE",
                    question = "Please ______ late for the meeting tomorrow.",
                    options = listOf("don't be", "not be", "no be", "aren't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "don't be",
                    explanationUzbek = "Inkor buyruqda 'don't be late' shakli to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u35_ex3",
                    exerciseNumber = "35.3",
                    taskType = "CHOICE",
                    question = "It's a beautiful evening. ______ go for a walk.",
                    options = listOf("Let's", "Let", "Lets", "Let we"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Let's",
                    explanationUzbek = "Birgalikdagi taklif: 'Let's go for a walk' (Let us)."
                ),
                MurphyExerciseItem(
                    id = "u35_ex4",
                    exerciseNumber = "35.4",
                    taskType = "CHOICE",
                    question = "I'm tired. Let's ______ go out tonight.",
                    options = listOf("not", "don't", "no", "aren't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "not",
                    explanationUzbek = "Let's ning inkor shakli: 'Let's not go out tonight'."
                ),
                MurphyExerciseItem(
                    id = "u35_ex5",
                    exerciseNumber = "35.5",
                    taskType = "CHOICE",
                    question = "______ carefully! That floor is very wet and slippery.",
                    options = listOf("Walk", "Walking", "You walk", "Walked"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Walk",
                    explanationUzbek = "Ogohlantirish buyrug'i boshlang'ich fe'l bilan boshlanadi: 'Walk carefully!'."
                )
            )
        ),

        // UNIT 36
        MurphyUnit(
            unitNumber = 36,
            title = "I used to...",
            subtitleUzbek = "O'tmishdagi doimiy odat va holat: used to + V1 (Ilgari qilardim, hozir esa yo'q)",
            groupName = "6-Guruh: Modals & Structures (29–36)",
            keyTakeawaysUzbek = listOf(
                "USED TO + fe'l = Ilgari muntazam qilardim, ammo hozir bunday qilmayman.",
                "O'tmishdagi holat: 'This building used to be a cinema' (Bu bino ilgari kinoteatr bo'lgan, hozir esa yo'q).",
                "Faqat O'TMISH uchun ishlatiladi! Hozirgi zamon uchun 'used to' ishlatilmaydi (o'rniga usually).",
                "Inkor shakli: didn't use to (yoki didn't used to): 'I didn't use to like tomatoes'.",
                "Savol shakli: Did you use to...? ('Did you use to live in London?')."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "used to + V1 (Ilgari qilardim, hozir qilmayman)",
                    formula = "ega + used to + fe'l (V1)",
                    explanationUzbek = "O'tmishda doimiy takrorlangan, lekin hozir butunlay to'xtagan odatlar yoki holatlar uchun ishlatiladi:\n\n• I used to play tennis a lot, but I don't play very often now. (Ilgari ko'p tennis o'ynardim, lekin hozir kam o'ynayman).\n• Dave used to work in a factory. Now he works in a supermarket. (Deyv ilgari fabrikada ishlagan. Hozir u supermarketda ishlaydi).\n• She used to have very long hair when she was a child. (U bolaligida juda uzun sochlarga ega bo'lardi).",
                    examples = listOf(
                        MurphyExample("I used to smoke, but I gave up last year.", "Ilgari chekardim, lekin o'tgan yili tashladim.", "To'xtatilgan odat"),
                        MurphyExample("This building used to be a cinema.", "Bu bino ilgari kinoteatr bo'lgan.", "O'tmishdagi holat"),
                        MurphyExample("We used to live in a small village.", "Biz ilgari kichik qishloqda yashardik.", "Hozir yashamaymiz")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Savol va inkor shakllari",
                    formula = "Savol: Did you use to...? | Inkor: didn't use to",
                    explanationUzbek = "Savol va inkorda 'did' yordamchi fe'li kelgani uchun 'used' dagi 'd' tushib qoladi va 'use to' bo'ladi:\n\n• Did you use to eat a lot of sweets when you were a child? (Bolaligingizda ko'p shirinlik yer edingizmi?)\n• Where did you use to live before coming here? (Bu yerga kelishdan oldin qayerda yashar edingiz?)\n• I didn't use to like him, but now we are good friends. (Ilgari uni yoqtirmasdim, ammo hozir yaqin do'stmiz).",
                    examples = listOf(
                        MurphyExample("Did you use to have a bicycle?", "Ilgari velosipedingiz bo'lganmi?", "Savol: Did you use to...?"),
                        MurphyExample("I didn't use to drink coffee.", "Ilgari kofe ichmas edim.", "Inkor: didn't use to"),
                        MurphyExample("What did you use to do on holidays?", "Ta'tillarda nima ish qilar edingiz?", "Maxsus savol")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "used to vs usually (Hozirgi zamonda ishlatilmaydi!)",
                    formula = "O'tmish: I used to do | Hozir: I usually do",
                    explanationUzbek = "⚠️ 'Used to' faqat o'tgan zamonga xosdir! Hozirgi zamondagi odatlaringiz uchun 'usually' (odatda) so'zidan foydalanasiz:\n\n• I used to play tennis. (O'tmishda o'ynardim).\n• I usually play tennis on Sundays. (Hozir, yakshanba kunlari odatda o'ynayman).\n(NOT 'I use to play tennis on Sundays' — bu xato!).",
                    examples = listOf(
                        MurphyExample("I usually get up at 7.00.", "Odatda soat 7:00 da uyg'onaman.", "Hozirgi zamon odati (usually)"),
                        MurphyExample("I used to get up at 6.00 when I had a job.", "Ishim borligida soat 6 da turardim.", "O'tmish odati (used to)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u36_ex1",
                    exerciseNumber = "36.1",
                    taskType = "CHOICE",
                    question = "Dave ______ in a factory, but now he works in a bank.",
                    options = listOf("used to work", "use to work", "is used to work", "works"),
                    correctOptionIndex = 0,
                    correctAnswerText = "used to work",
                    explanationUzbek = "O'tmishdagi holat va hozir o'zgargan vaziyat: 'used to work'."
                ),
                MurphyExerciseItem(
                    id = "u36_ex2",
                    exerciseNumber = "36.2",
                    taskType = "CHOICE",
                    question = "Did you ______ have long hair when you were younger?",
                    options = listOf("use to", "used to", "used", "using to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "use to",
                    explanationUzbek = "'Did' bor bo'lgani sababli fe'l 'use to' shaklida bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u36_ex3",
                    exerciseNumber = "36.3",
                    taskType = "CHOICE",
                    question = "I ______ like mushrooms, but now I love them.",
                    options = listOf("didn't use to", "don't use to", "used not", "wasn't used"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't use to",
                    explanationUzbek = "Ilgari yoqtirmasdim (o'tmish inkor odati): 'didn't use to like'."
                ),
                MurphyExerciseItem(
                    id = "u36_ex4",
                    exerciseNumber = "36.4",
                    taskType = "CHOICE",
                    question = "This building ______ a cinema many years ago.",
                    options = listOf("used to be", "used to", "was used", "use to be"),
                    correctOptionIndex = 0,
                    correctAnswerText = "used to be",
                    explanationUzbek = "Ilgari kinoteatr bo'lgan (holat): 'used to be a cinema'."
                ),
                MurphyExerciseItem(
                    id = "u36_ex5",
                    exerciseNumber = "36.5",
                    taskType = "CHOICE",
                    question = "I ______ go to the gym on Tuesdays. It is my weekly routine now.",
                    options = listOf("usually", "used to", "use to", "was used to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "usually",
                    explanationUzbek = "Hozirgi kundagi doimiy rejim/odat uchun 'usually' ishlatiladi ('used to' emas!)."
                )
            )
        )
    )
}
