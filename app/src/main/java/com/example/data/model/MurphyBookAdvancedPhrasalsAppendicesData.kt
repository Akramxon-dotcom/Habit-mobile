package com.example.data.model

object MurphyBookAdvancedPhrasalsAppendicesData {

    val UNITS_ADVANCED_PHRASALS: List<MurphyUnit> = listOf(
        // UNIT 99
        MurphyUnit(
            unitNumber = 99,
            title = "up and down (Phrasal verbs 3 - eat up, clean up, slow down)",
            subtitleUzbek = "Harakatning to'liq tugallanishi (UP) va pasayishi (DOWN)",
            groupName = "17-Guruh: Advanced Phrasals & Mastery (99–104)",
            keyTakeawaysUzbek = listOf(
                "UP = Ko'pincha 'TAMOMILA / OXIRIGACHA / BUTKUL' ma'nosini beradi: eat up (oxirigacha yeb qo'ymoq), clean up (orasta qilib tozalab qo'ymoq), drink up (oxirgi tomchisigacha ichmoq).",
                "UP = YUQORIGA yoki OSHIRISH: turn up (ovozini balandlatmoq), speak up (balandroq gapirmoq), grow up (ulg'aymoq).",
                "DOWN = PASTGA yoki PASAYTIRISH: slow down (tezlikni pasaytirmoq), turn down (ovozini pasaytirmoq), calm down (tinchlanmoq/bosilmoq).",
                "DOWN = YIQILISH yoki YERGA TUSHISH: fall down (yerga yiqilmoq), sit down (o'tirmoq), write down (daftarga tushirmoq / yozib olmoq).",
                "MISOL: 'Eat up your vegetables!' (Sabzavotlaringni bironta qoldirmay oxirigacha yeb qo'y!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "UP ning 'oxirigacha / to'liq tugatish' siri",
                    formula = "fe'l + UP (butkul tamomlash)",
                    explanationUzbek = "O'zbek tilida 'ye' bilan 'yeb qo'y', 'tozala' bilan 'yig'ishtirib toza qilib qo'y' o'rtasida qanday farq bo'lsa, ingliz tilida 'UP' aynan shunday vazifani bajaradi:\n\n• Eat your soup! (Sho'rvangni ich)\n• Eat up your soup! (Sho'rvangni bitta tomchi qoldirmay hammasini ichib tugat!)\n\n• Clean up: We must clean up the kitchen after cooking. (Ovqat pishirgandan keyin oshxonani butunlay saranjomlashimiz kerak)\n• Drink up: Drink up your tea, it's getting cold. (Choyingizni tezroq ichib tugating)\n• Use up: We used up all the milk. (Hamma sutni ishlatib tugatdik, bitta qoshiq ham qolmadi)",
                    examples = listOf(
                        MurphyExample("Eat up, or you will be hungry later.", "Hammasini yeb qo'y, aks holda keyinroq qorning ochadi.", "eat up = oxirigacha yemoq"),
                        MurphyExample("Can you speak up a little? I can't hear you.", "Biroz balandroq gapira olasizmi? Sizni eshitolmayapman.", "speak up = balandroq gapirmoq"),
                        MurphyExample("He grew up in a small village.", "U kichik bir qishloqda ulg'aygan/katta bo'lgan.", "grow up = ulg'aymoq")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "DOWN ning 'pasaytirish va tinchlanish' kuchi",
                    formula = "fe'l + DOWN (pastlatish / tinchlanish)",
                    explanationUzbek = "Hayotdagi qizg'inlikni yoki balandlikni pasaytirish:\n\n• Slow down: You're driving too fast! Slow down! (Juda tez ketyapsiz! Sekinlashtiring!)\n• Calm down: Please calm down and tell me what happened. (Iltimos, avval tinchlaning va nima bo'lganini aytib bering)\n• Turn down: Can you turn the radio down? (Radioning ovozini pasaytira olasizmi?)\n• Write down: Did you write down his address? (Uning manzilini daftarga yozib oldingizmi?)\n• Fall down: The boy tripped and fell down. (Bola qoqilib yerga yiqilib tushdi)",
                    examples = listOf(
                        MurphyExample("Slow down! There is a speed camera ahead.", "Sekinlashtiring! Oldinda radar bor.", "slow down = tezlikni pasaytirmoq"),
                        MurphyExample("Write down my phone number so you don't forget.", "Unutib qo'ymaslik uchun telefon raqamimni yozib oling.", "write down = yozib olmoq")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u99_ex1",
                    exerciseNumber = "99.1",
                    taskType = "CHOICE",
                    question = "You are driving at 120 km/h! Please ______.",
                    options = listOf("slow down", "speed down", "turn up", "calm up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "slow down",
                    explanationUzbek = "Tezlikni pasaytirish 'slow down' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u99_ex2",
                    exerciseNumber = "99.2",
                    taskType = "CHOICE",
                    question = "Kids, ______ all your dinner before having dessert!",
                    options = listOf("eat up", "eat off", "eat down", "eat away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "eat up",
                    explanationUzbek = "Taomni oxirigacha yeb tugatish 'eat up' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u99_ex3",
                    exerciseNumber = "99.3",
                    taskType = "CHOICE",
                    question = "I can't hear the news. Could you please turn ______ the TV?",
                    options = listOf("up", "down", "off", "away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "up",
                    explanationUzbek = "Eshita olmaganda ovozini balandlatish 'turn up' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u99_ex4",
                    exerciseNumber = "99.4",
                    taskType = "CHOICE",
                    question = "Don't panic! Take a deep breath and ______.",
                    options = listOf("calm down", "slow up", "fall down", "grow up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "calm down",
                    explanationUzbek = "Tinchlanish va o'zini bosib olish 'calm down' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u99_ex5",
                    exerciseNumber = "99.5",
                    taskType = "CHOICE",
                    question = "He slipped on the ice and ______ on the pavement.",
                    options = listOf("fell down", "grew up", "ate up", "slowed down"),
                    correctOptionIndex = 0,
                    correctAnswerText = "fell down",
                    explanationUzbek = "Muzda sirpanib yerga yiqilish 'fell down' bo'ladi."
                )
            )
        ),

        // UNIT 100
        MurphyUnit(
            unitNumber = 100,
            title = "on, off, out (Phrasal verbs 4 - try on, take off, find out)",
            subtitleUzbek = "Kiyimni kiyib ko'rish (try on), yechish (take off), fosh qilish/bilish (find out)",
            groupName = "17-Guruh: Advanced Phrasals & Mastery (99–104)",
            keyTakeawaysUzbek = listOf(
                "TRY ON = Kiyimni do'konda o'lchab/kiyib ko'rmoq: Can I try this jacket on? (Bu kurtkani kiyib ko'rsam maylimi?).",
                "TAKE OFF = 1) Kiyimni yechmoq: Take off your shoes; 2) Samolyotning yerdan havoga ko'tarilishi: The plane took off on time.",
                "FIND OUT = Yangi ma'lumotni bilib olmoq / o'rganmoq / fosh qilmoq: How did you find out about the party?",
                "CROSS OUT = So'z yoki xatoni ustidan chiziq tortib o'chirmoq: Cross out the wrong answer.",
                "GO OFF = Budilnik jiringlab ketishi yoki chiroq o'chib qolishi: My alarm clock went off at 6:30."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Kiyim va qulaylik bilan bog'liq so'zlar",
                    formula = "try on (o'lchab ko'rmoq) vs take off (yechmoq)",
                    explanationUzbek = "Do'konga borganingizda yoki kiyim almashtirayotganda bu so'zlar har kuni kerak bo'ladi:\n\n• TRY ON (o'lchab ko'rish):\n'I like these shoes, can I try them on?'\n(Menga bu poyabzallar yoqdi, ularni kiyib ko'rsam maylimi?)\nE'tibor bering: 'them' yana o'rtaga tushyapti: try them on!\n\n• TAKE OFF (yechish):\n'He took off his wet jacket.'\n(U ho'l kurtkasini yechdi)\n\n• TAKE OFF ning 2-mo''jizaviy ma'nosi (Samolyot parvozi):\n'The plane took off 10 minutes late.'\n(Samolyot 10 daqiqa kechikib havoga ko'tarildi)",
                    examples = listOf(
                        MurphyExample("Where is the fitting room? I'd like to try this shirt on.", "Kiyinish xonasi qayerda? Bu ko'ylakni kiyib ko'rmoqchi edim.", "try this shirt on"),
                        MurphyExample("Our flight took off smoothly.", "Parvozimiz juda silliq osmonga ko'tarildi.", "took off = uchib ketdi")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Axborot va kundalik hodisalar (Find out, Go off, Cross out)",
                    formula = "find out (bilib olmoq) | go off (jiringlamoq) | cross out (o'chirmoq)",
                    explanationUzbek = "Kundalik hayotiy vaziyatlar:\n\n1) FIND OUT (Biror sir yoki yangilikni bilib olish):\n• I found out that she was moving to Canada.\n(Men uning Kanadaga ko'chib ketayotganini bilib qoldim)\n• Go to the station and find out what time the train leaves.\n\n2) GO OFF (Budilnik yoki bomba 'portlashi/jiringlashi'):\n• My alarm went off at 6:00, but I was so tired I turned it off.\n(Budilnikim soat 6:00 da jiringladi, lekin charchaganimdan o'chirib qo'ydim)\n\n3) CROSS OUT (Xato ustidan chizish):\n• If you make a mistake, just cross it out.\n(Xato qilsangiz, shunchaki ustidan chizib qo'ying)",
                    examples = listOf(
                        MurphyExample("We need to find out the ticket prices.", "Biz chiptalar narxini bilib olishimiz kerak.", "find out = aniqlamoq/bilmoq"),
                        MurphyExample("Did your alarm go off this morning?", "Bugun ertalab budilnikingiz jiringladimi?", "go off = jiringlamoq")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u100_ex1",
                    exerciseNumber = "100.1",
                    taskType = "CHOICE",
                    question = "You should ______ that coat before buying it to make sure it fits.",
                    options = listOf("try on", "take off", "find out", "cross out"),
                    correctOptionIndex = 0,
                    correctAnswerText = "try on",
                    explanationUzbek = "Sotib olishdan oldin kiyimni kiyib ko'rish 'try on' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u100_ex2",
                    exerciseNumber = "100.2",
                    taskType = "CHOICE",
                    question = "What time does our plane ______?",
                    options = listOf("take off", "try on", "go off", "cross out"),
                    correctOptionIndex = 0,
                    correctAnswerText = "take off",
                    explanationUzbek = "Samolyotning yerdan havoga ko'tarilishi 'take off' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u100_ex3",
                    exerciseNumber = "100.3",
                    taskType = "CHOICE",
                    question = "I was late because my morning alarm didn't ______.",
                    options = listOf("go off", "take off", "find out", "try on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "go off",
                    explanationUzbek = "Budilnik jiringlashi 'go off' iborasi bilan yasaladi."
                ),
                MurphyExerciseItem(
                    id = "u100_ex4",
                    exerciseNumber = "100.4",
                    taskType = "CHOICE",
                    question = "How did you ______ the secret news?",
                    options = listOf("find out", "take off", "cross out", "try on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "find out",
                    explanationUzbek = "Biror yangilikni bilib olish 'find out' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u100_ex5",
                    exerciseNumber = "100.5",
                    taskType = "CHOICE",
                    question = "If you spell the word wrong, simply ______ it out.",
                    options = listOf("cross", "find", "take", "try"),
                    correctOptionIndex = 0,
                    correctAnswerText = "cross",
                    explanationUzbek = "Xato ustidan chizib qo'yish 'cross out' deyiladi."
                )
            )
        ),

        // UNIT 101
        MurphyUnit(
            unitNumber = 101,
            title = "look after, take care of, run out of (Idiomatic expressions)",
            subtitleUzbek = "Kundalik ibratli iboralar: G'amxo'rlik qilish, tugab qolish va eslab qolish",
            groupName = "17-Guruh: Advanced Phrasals & Mastery (99–104)",
            keyTakeawaysUzbek = listOf(
                "LOOK AFTER = 'G'amxo'rlik qilmoq / qarab turmoq / boqmoq': Can you look after my cat while I am away? (Men yo'g'imda mushugimga qarab tura olasizmi?).",
                "LOOK FORWARD TO = 'Sabrsizlik va intizorlik bilan kutmoq': I'm looking forward to the holidays! (Ta'tilni intizorlik bilan kutyapman). ⚠️ Undan keyin doim fe'l -ING oladi: looking forward to meeting you.",
                "RUN OUT OF = 'Tugab qolmoq': We have run out of bread / petrol (Nonimiz / benzinimiz tugab qoldi).",
                "GET ON WITH = 'Chiqishib ketmoq / do'stona munosabatda bo'lmoq': Do you get on well with your colleagues? (Hamkasblaringiz bilan yaxshi chiqishasizmi?)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Look after va Run out of ning hayotiy ahamiyati",
                    formula = "look after (boqmoq/qaramoq) | run out of (tugab qolmoq)",
                    explanationUzbek = "1) LOOK AFTER (Qarab turish):\n• Babysitter looks after children. (Enaga bolalarga qaraydi)\n• Please look after yourself! (Iltimos, o'zingizni ehtiyot qiling!)\n\n2) RUN OUT OF (Biror zaxira nolga tushib qolganda):\n• Quick! We are running out of time! (Tezlashing! Vaqtimiz tugayapti!)\n• We've run out of petrol. Where is the nearest station? (Benzinimiz tugab qoldi)\n• I ran out of money during my trip. (Sayohatimda pulim tugab qoldi)",
                    examples = listOf(
                        MurphyExample("My sister is looking after our grandparents today.", "Singlim bugun bobo-buvimga qarab turibdi.", "looking after = qaramoqda"),
                        MurphyExample("We have run out of coffee. Can you buy some?", "Qahvamiz tugab qolibdi. Biroz sotib ololmaysizmi?", "run out of coffee")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Looking forward to va Get on with",
                    formula = "look forward to + Ot / Fe'l-ING | get on with somebody",
                    explanationUzbek = "1) LOOK FORWARD TO (Intiqlik bilan kutish):\nXatlar oxirida yoki uchrashuv oldidan juda ko'p aytiladi:\n• I am looking forward to seeing you. (Sizni ko'rishni orziqib kutyapman — 'see' emas, 'seeing'!)\n• We are looking forward to our summer vacation.\n\n2) GET ON WITH (Kimdir bilan yaxshi yoki yomon munosabatda bo'lish):\n• Do you get on with your neighbours? (Qo'shnilaringiz bilan til topisha olasizmi?)\n• I get on very well with my brother. (Akam bilan judayam yaxshi chiqishaman)",
                    examples = listOf(
                        MurphyExample("I'm really looking forward to the weekend!", "Dam olish kunlarini judayam orziqib kutyapman!", "looking forward to"),
                        MurphyExample("They don't get on with each other.", "Ular bir-biri bilan umuman chiqisha olishmaydi.", "get on with")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u101_ex1",
                    exerciseNumber = "101.1",
                    taskType = "CHOICE",
                    question = "We need to stop at a gas station. The car has run ______ petrol.",
                    options = listOf("out of", "away from", "off of", "down with"),
                    correctOptionIndex = 0,
                    correctAnswerText = "out of",
                    explanationUzbek = "Zaxiraning tugab qolishi 'run out of' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u101_ex2",
                    exerciseNumber = "101.2",
                    taskType = "CHOICE",
                    question = "Can you ______ my baby brother for an hour while I go shopping?",
                    options = listOf("look after", "look for", "look at", "look up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "look after",
                    explanationUzbek = "Bolaga qarab turish, g'amxo'rlik qilish 'look after' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u101_ex3",
                    exerciseNumber = "101.3",
                    taskType = "CHOICE",
                    question = "I am really looking forward ______ you at the conference.",
                    options = listOf("to seeing", "to see", "for seeing", "at see"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to seeing",
                    explanationUzbek = "'looking forward to' dan keyin doim fe'l -ing shaklida keladi: 'to seeing'."
                ),
                MurphyExerciseItem(
                    id = "u101_ex4",
                    exerciseNumber = "101.4",
                    taskType = "CHOICE",
                    question = "Do you get ______ well with your new flatmate?",
                    options = listOf("on", "off", "up", "away"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Yaxshi chiqishib ketish 'get on well' birikmasi."
                ),
                MurphyExerciseItem(
                    id = "u101_ex5",
                    exerciseNumber = "101.5",
                    taskType = "CHOICE",
                    question = "Hurry up, we are running ______ of battery on the phone!",
                    options = listOf("out", "off", "away", "down"),
                    correctOptionIndex = 0,
                    correctAnswerText = "out",
                    explanationUzbek = "'running out of battery' - telefon zaryadi tugayapti."
                )
            )
        ),

        // UNIT 102
        MurphyUnit(
            unitNumber = 102,
            title = "say vs tell / make vs do (Essential verb confusions)",
            subtitleUzbek = "Eng ko'p adashtiriladigan juftliklar: Say vs Tell va Make vs Do farqlari",
            groupName = "17-Guruh: Advanced Phrasals & Mastery (99–104)",
            keyTakeawaysUzbek = listOf(
                "SAY vs TELL: Say = umumiy gapirmoq/aytmoq (He said that...); Tell = KIMDIRGA aytmoq (He TOLD ME that...). Tell dan keyin DOIM KIMGA ekanligi aytiladi: tell me, tell him, tell us.",
                "SAY TO: Agar say bilan kimga ekanligini aytmoqchi bo'lsangiz, 'to' qo'yish SHART: He said TO me (He said me DEYISH QAT'IYAN XATO!).",
                "MAKE = Noldan yangi narsa YARATMOQ / ISHLAB CHIQARMOQ: make coffee, make a cake, make a mistake, make money, make friends.",
                "DO = ISH-HARAKAT yoki VAZIFANI BAJARMOQ: do homework, do the dishes, do business, do exercises, do a favour.",
                "Oltin taqqoslash: Make a decision (qaror qabul qilmoq) vs Do your best (qo'ldan kelgancha eng yaxshisini bajarmoq)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Say vs Tell (Kimga aytilgani ma'lummi?)",
                    formula = "SAY (nimanidir) | TELL + KIMGA (shaxs)",
                    explanationUzbek = "Buni bir umrga eslab qoling:\n\n1) TELL dan keyin doim kimga aytilgani keladi:\n• He told me his name. (U menga ismini aytdi)\n• Don't tell anybody! (Hech kimga aytma!)\n• Tell us a story. (Bizga bir ertak aytib ber)\n\n2) SAY dan keyin to'g'ridan-to'g'ri odam kelmaydi:\n• He said that he was tired. (U charchaganini aytdi)\n• What did you say? (Nima dedingiz?)\n• Agar shaxs aytilsa: He said TO me. (Lekin hech qachon 'He said me' demang!)",
                    examples = listOf(
                        MurphyExample("She told me she was going to Tashkent.", "U menga Toshkentga ketayotganini aytdi.", "told me (shaxs bor)"),
                        MurphyExample("She said she was very busy.", "U juda bandligini aytdi.", "said that (shaxssiz)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Make vs Do (Yaratish vs Harakat)",
                    formula = "MAKE (yaratish/natija) vs DO (jarayon/harakat)",
                    explanationUzbek = "1) MAKE (qo'l bilan yoki aql bilan yangi narsa hosil qilish):\n• make a cup of tea (choy damlamoq — yangi ichimlik paydo bo'ldi)\n• make breakfast / dinner (ovqat tayyorlamoq)\n• make a mistake (xatoga yo'l qo'ymoq)\n• make a phone call (qo'ng'iroq qilmoq)\n• make a noise (shovqin qilmoq)\n\n2) DO (faoliyat, bajariladigan majburiyat va vazifalar):\n• do homework (uyga vazifani bajarmoq)\n• do the laundry / washing (kirlarni yuvmoq)\n• do the dishes (idishlarni yuvmoq)\n• do your best (bor kuching bilan harakat qilmoq)\n• do business (biznes yuritmoq)",
                    examples = listOf(
                        MurphyExample("I made a big mistake in the test.", "Testda katta xatoga yo'l qo'ydim.", "made a mistake"),
                        MurphyExample("I will do the dishes after dinner.", "Kechki ovqatdan so'ng idishlarni men yuvaman.", "do the dishes")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u102_ex1",
                    exerciseNumber = "102.1",
                    taskType = "CHOICE",
                    question = "He ______ me that he would be ten minutes late.",
                    options = listOf("told", "said", "spoke", "talked"),
                    correctOptionIndex = 0,
                    correctAnswerText = "told",
                    explanationUzbek = "'me' (menga) shaxsi bo'lgani uchun 'told me' to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u102_ex2",
                    exerciseNumber = "102.2",
                    taskType = "CHOICE",
                    question = "Can you ______ a favour for me, please?",
                    options = listOf("do", "make", "take", "give"),
                    correctOptionIndex = 0,
                    correctAnswerText = "do",
                    explanationUzbek = "Yaxshilik qilib yordam berish 'do a favour' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u102_ex3",
                    exerciseNumber = "102.3",
                    taskType = "CHOICE",
                    question = "Don't ______ so much noise, the baby is sleeping!",
                    options = listOf("make", "do", "say", "tell"),
                    correctOptionIndex = 0,
                    correctAnswerText = "make",
                    explanationUzbek = "Shovqin qilish 'make noise' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u102_ex4",
                    exerciseNumber = "102.4",
                    taskType = "CHOICE",
                    question = "She ______ that she loved Italian food.",
                    options = listOf("said", "told", "spoke", "informed"),
                    correctOptionIndex = 0,
                    correctAnswerText = "said",
                    explanationUzbek = "Ketidan shaxs kelmagani uchun 'said that' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u102_ex5",
                    exerciseNumber = "102.5",
                    taskType = "CHOICE",
                    question = "I have to ______ my homework before going out.",
                    options = listOf("do", "make", "take", "put"),
                    correctOptionIndex = 0,
                    correctAnswerText = "do",
                    explanationUzbek = "Vazifa bajarish doim 'do homework' deyiladi."
                )
            )
        ),

        // UNIT 103
        MurphyUnit(
            unitNumber = 103,
            title = "Irregular Verbs Mastery (Noto'g'ri fe'llarning 3 ta shakli)",
            subtitleUzbek = "Ingliz tili asosiy noto'g'ri fe'llarini qolip bo'yicha oson yodlash siri",
            groupName = "17-Guruh: Advanced Phrasals & Mastery (99–104)",
            keyTakeawaysUzbek = listOf(
                "NOTO'G'RI FE'LLAR nima? — O'tgan zamonda '-ed' qo'shimchasini OLMAYDIGAN va o'zagi butunlay o'zgaradigan fe'llar: go -> went -> gone.",
                "1-QOLIP (Uchala shakli bir xil): cost-cost-cost, cut-cut-cut, hit-hit-hit, hurt-hurt-hurt, put-put-put, shut-shut-shut.",
                "2-QOLIP (2 va 3-shakllari bir xil): buy-bought-bought, bring-brought-brought, feel-felt-felt, keep-kept-kept, leave-left-left, lose-lost-lost, pay-paid-paid.",
                "3-QOLIP (I-A-U almashuvi): swim-swam-swum, sing-sang-sung, drink-drank-drunk, begin-began-begun, ring-rang-rung.",
                "4-QOLIP (-EN bilan tugaydigan 3-shakl): write-wrote-written, take-took-taken, drive-drove-driven, give-gave-given, see-saw-seen."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Noto'g'ri fe'llarni guruhlab yodlash siri",
                    formula = "V1 (Asliy) -> V2 (Past Simple) -> V3 (Past Participle)",
                    explanationUzbek = "Alifbo bo'yicha yodlash miyaga juda og'ir. Ularni qoliplar bo'yicha o'rgansangiz, bir kunda hammasi esda qoladi:\n\n1) Hech qachon o'zgarmaydiganlar (Super oson):\n• put -> put -> put (qo'ymoq)\n• cut -> cut -> cut (kesmoq)\n• hurt -> hurt -> hurt (og'rimoq)\n• cost -> cost -> cost (narx turmoq)\n\n2) 'Ought / Aught' oilasi (tovushi 'ot'):\n• buy -> bought -> bought (sotib olmoq)\n• bring -> brought -> brought (olib kelmoq)\n• think -> thought -> thought (o'ylamoq)\n• catch -> caught -> caught (tutmoq)\n• teach -> taught -> taught (o'rgatmoq)",
                    examples = listOf(
                        MurphyExample("I bought a new phone yesterday.", "Men kecha yangi telefon sotib oldim.", "buy -> bought (V2)"),
                        MurphyExample("This antique table cost a lot of money.", "Bu antikvar stol juda qimmat turgan edi.", "cost -> cost (o'zgarmas)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "I-A-U va -EN oilalari",
                    formula = "sing-sang-sung | write-wrote-written",
                    explanationUzbek = "3) Musiqiy qolip (I -> A -> U):\n• begin -> began -> begun (boshlamoq)\n• drink -> drank -> drunk (ichmoq)\n• swim -> swam -> swum (suzmoq)\n• sing -> sang -> sung (kuylamoq)\n• run -> ran -> run (yugurmoq)\n\n4) 3-shakli -EN bilan tugaydiganlar:\n• break -> broke -> broken (sinmoq)\n• speak -> spoke -> spoken (gapirmoq)\n• choose -> chose -> chosen (tanlamoq)\n• forget -> forgot -> forgotten (unutmoq)\n• write -> wrote -> written (yozmoq)",
                    examples = listOf(
                        MurphyExample("Have you ever written a book?", "Hech kitob yozganmisiz?", "write -> wrote -> written (V3)"),
                        MurphyExample("He drank three glasses of water.", "U uch stakan suv ichdi.", "drink -> drank (V2)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u103_ex1",
                    exerciseNumber = "103.1",
                    taskType = "CHOICE",
                    question = "My grandfather ______ me how to swim when I was six.",
                    options = listOf("taught", "teached", "thought", "teachen"),
                    correctOptionIndex = 0,
                    correctAnswerText = "taught",
                    explanationUzbek = "'teach' (o'rgatmoq) fe'lining o'tgan zamoni 'taught' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u103_ex2",
                    exerciseNumber = "103.2",
                    taskType = "CHOICE",
                    question = "Someone has ______ my favorite coffee mug!",
                    options = listOf("broken", "broke", "breaked", "break"),
                    correctOptionIndex = 0,
                    correctAnswerText = "broken",
                    explanationUzbek = "Present Perfect (has) dan keyin 3-shakl 'broken' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u103_ex3",
                    exerciseNumber = "103.3",
                    taskType = "CHOICE",
                    question = "He ______ the letter, put it in an envelope, and mailed it.",
                    options = listOf("wrote", "written", "writed", "write"),
                    correctOptionIndex = 0,
                    correctAnswerText = "wrote",
                    explanationUzbek = "O'tgan zamonda xat yozdi: 'wrote'."
                ),
                MurphyExerciseItem(
                    id = "u103_ex4",
                    exerciseNumber = "103.4",
                    taskType = "CHOICE",
                    question = "This watch ______ me only 20 dollars.",
                    options = listOf("cost", "costed", "costen", "costs"),
                    correctOptionIndex = 0,
                    correctAnswerText = "cost",
                    explanationUzbek = "'cost' fe'lining o'tgan zamoni ham o'zgarmaydi: 'cost'."
                ),
                MurphyExerciseItem(
                    id = "u103_ex5",
                    exerciseNumber = "103.5",
                    taskType = "CHOICE",
                    question = "We have ______ across this lake many times.",
                    options = listOf("swum", "swam", "swimmed", "swimming"),
                    correctOptionIndex = 0,
                    correctAnswerText = "swum",
                    explanationUzbek = "'have' dan keyin 3-shakl: swim -> swam -> swum."
                )
            )
        ),

        // UNIT 104
        MurphyUnit(
            unitNumber = 104,
            title = "Complete Essential Grammar Overview (Yakuniy xulosalar va Grammatika sirlari)",
            subtitleUzbek = "Barcha 104 ta darsning asosiy ustunlari va ingliz tilida erkin so'zlashish sirlari",
            groupName = "17-Guruh: Advanced Phrasals & Mastery (99–104)",
            keyTakeawaysUzbek = listOf(
                "TABRIKLAYMIZ! Siz dunyodagi eng mashhur Raymond Murphy 'Essential Grammar in Use' kitobining barcha 104 ta darsini to'liq yakunladingiz!",
                "1-USTUN: Zamonaviy zamonlar tizimi (Present Simple odatlar uchun, Continuous ayni damdagi ishlar uchun, Present Perfect hayotiy natija uchun).",
                "2-USTUN: Modallik va ehtimollik (can qila olish, must shart bo'lish, should maslahat, could / would muloyim iltimoslar).",
                "3-USTUN: So'z tartibi va Predloglar (Kim? Nima qildi? Nimani? Qayerda? Qachon? ketma-ketligi buzilmasligi shart).",
                "4-USTUN: Frazali fe'llar va Tabiiy nutq (put on, turn off, look forward to orqali tildan jonli foydalanish).",
                "MUVAFFAQIYAT KALITI: Har kuni 15 daqiqa darslarni takrorlash va ilovadagi testlar orqali xatolarni tahlil qilish!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Kitob bo'yicha 5 ta asosiy grammatik qonuniyat",
                    formula = "To be -> Do/Does -> Have done -> Modals -> Word Order",
                    explanationUzbek = "Ingliz tili grammatikasining butun mexanizmi mana shu 5 ta ustunga tayanadi:\n\n1) 'TO BE' (am/is/are, was/were): Harakatsiz, kim ekanlik yoki qayerdalikni aytish.\n2) ZAMONLAR (Present Simple vs Continuous, Past Simple vs Present Perfect): Vaqtni to'g'ri his qilish.\n3) PASSIV VA REPORTED SPEECH: Ish kim tomonidan bajarilganidan ko'ra natijaning muhimligi.\n4) ARTIKLLAR VA OTMASHLAR (a/an, the, some, any, one/ones): Aniq va noaniq tushunchalarni ajratish.\n5) BOG'LOVCHILAR VA SHART GAPLAR: Murakkab hayotiy fikrlarni bir-biriga chiroyli ulash.",
                    examples = listOf(
                        MurphyExample("Practice makes perfect.", "Mashq qilish mukammallikka yetaklaydi.", "Haftasiga 3-4 marta mashq qiling!"),
                        MurphyExample("You now possess all foundational tools of English grammar.", "Siz endi ingliz tili grammatikasining barcha poydevor qurollariga egasiz.", "Congratulations!")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Nutqni ravon qilish uchun amaliy tavsiyalar",
                    formula = "O'rganish -> Ilovada test ishlash -> Ovoz chiqarib takrorlash",
                    explanationUzbek = "Grammatikani o'rgangandan so'ng uni nutqda faollashtirish bosqichlari:\n\n1) Qoidalarni formulalar bilan emas, tayyor jumlalar bilan yodlang (Masalan: 'I have never been to Paris' deb butun jumla esda qolsin).\n2) O'zbekchadan so'zma-so'z tarjima qilmang, ingliz tilidagi tuzilish qolipini qabul qiling.\n3) Ilovadagi qidiruv (Search) tizimidan foydalanib, istalgan noaniq mavzuni bir soniyada ochib takrorlab turing.",
                    examples = listOf(
                        MurphyExample("I am ready to speak fluent English!", "Men ingliz tilida ravon so'zlashishga tayyorman!", "Confidence is key!"),
                        MurphyExample("Well done on completing the entire Murphy course!", "Butun Murphy kursini muvaffaqiyatli yakunlaganingiz bilan tabriklaymiz!", "Mastery achieved!")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u104_ex1",
                    exerciseNumber = "104.1",
                    taskType = "CHOICE",
                    question = "Which sentence is grammatically PERFECT in everyday English?",
                    options = listOf(
                        "If I had time, I would help you with your homework.",
                        "If I will have time, I will help you.",
                        "If I had time, I will help you.",
                        "If I have time, I would help you."
                    ),
                    correctOptionIndex = 0,
                    correctAnswerText = "If I had time, I would help you with your homework.",
                    explanationUzbek = "Second Conditional shart qoidasi: 'If I had ..., I would help'."
                ),
                MurphyExerciseItem(
                    id = "u104_ex2",
                    exerciseNumber = "104.2",
                    taskType = "CHOICE",
                    question = "I have lived in this beautiful city ______ ten years.",
                    options = listOf("for", "since", "during", "while"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Vaqt davomiyligi (10 yil davomida) uchun 'for' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u104_ex3",
                    exerciseNumber = "104.3",
                    taskType = "CHOICE",
                    question = "Can you ______ this form with your personal details?",
                    options = listOf("fill in", "fill off", "take off", "turn up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "fill in",
                    explanationUzbek = "Forma/blankani to'ldirish 'fill in' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u104_ex4",
                    exerciseNumber = "104.4",
                    taskType = "CHOICE",
                    question = "The woman ______ lives across the street is a world-famous pianist.",
                    options = listOf("who", "which", "whom", "whose"),
                    correctOptionIndex = 0,
                    correctAnswerText = "who",
                    explanationUzbek = "Odamlar uchun bog'lovchi olmosh 'who' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u104_ex5",
                    exerciseNumber = "104.5",
                    taskType = "CHOICE",
                    question = "Congratulations! You have completed ______ entire Murphy Grammar book!",
                    options = listOf("the", "a", "an", "—"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the",
                    explanationUzbek = "Aniq bir kitob haqida borgani uchun 'the entire book' deyiladi."
                )
            )
        )
    )
}
