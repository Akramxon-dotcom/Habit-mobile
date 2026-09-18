package com.example.data.model

object MurphyBookAppendicesData {

    val UNITS_APPENDICES: List<MurphyUnit> = listOf(
        // UNIT 105 (Appendix 1)
        MurphyUnit(
            unitNumber = 105,
            title = "Appendix 1: Active and Passive voice summary (Faol va Majhul nisbat)",
            subtitleUzbek = "Ishni kim qilgani emas, nima qilingani muhim bo'lgan gaplar siri",
            groupName = "18-Guruh: Appendices & Reference Guides (105–110)",
            keyTakeawaysUzbek = listOf(
                "FAOL NISBAT (Active): Ega ishni O'ZI bajaradi: 'Somebody cleans the room every day' (Kimdir har kuni xonani tozalaydi).",
                "MAJHUL NISBAT (Passive): Diqqat markazida ISH-HARAKAT yoki NATIJA turadi: 'The room is cleaned every day' (Xona har kuni tozalanadi).",
                "FORMULA DOIM BIR XIL: BE (zamoniga qarab: is, was, has been, will be) + V3 (fe'lning 3-shakli).",
                "Present Simple: is/are cleaned | Past Simple: was/were cleaned | Present Perfect: has/have been cleaned | Future: will be cleaned.",
                "AGAR KIM QILGANI MUHIM BO'LSA: gap oxiriga 'BY' qo'shiladi: 'The telephone was invented BY Alexander Bell'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Faol va Majhul nisbatni hayotiy taqqoslash",
                    formula = "Active: Ega + Fe'l + To'ldiruvchi | Passive: To'ldiruvchi + BE + V3",
                    explanationUzbek = "Tasavvur qiling, do'kondan yangi telefon o'g'irlab ketildi. Siz o'g'rining kimligini bilmaysiz. Shunda nima deysiz?\n\n'Mening telefonim o'g'irlandi!' deysiz. Mana shu MAJHUL NISBAT (Passive) bo'ladi!\n\nKeling, barcha asosiy zamonlarda ko'rib chiqamiz:\n\n1) Hozirgi zamon (Present Simple):\n• Active: Somebody paints this gate every year.\n• Passive: This gate IS PAINTED every year. (Bu darvoza har yili bo'yaladi)\n\n2) O'tgan zamon (Past Simple):\n• Active: Somebody painted this gate yesterday.\n• Passive: This gate WAS PAINTED yesterday. (Bu darvoza kecha bo'yaldi)\n\n3) Tugallangan zamon (Present Perfect):\n• Active: Somebody has painted this gate.\n• Passive: This gate HAS BEEN PAINTED. (Bu darvoza bo'yab qo'yildi)",
                    examples = listOf(
                        MurphyExample("Thousands of cars are produced in Asaka every month.", "Asakada har oyda minglab mashinalar ishlab chiqariladi.", "are produced (ishlab chiqariladi)"),
                        MurphyExample("America was discovered in 1492.", "Amerika 1492-yilda kashf etilgan.", "was discovered (kashf qilindi)"),
                        MurphyExample("This bridge was built by a famous engineer.", "Bu ko'prik mashhur muhandis tomonidan qurilgan.", "built by...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Majhul nisbat barcha zamonlar jadvali",
                    formula = "BE + V3 (doimiy qolip)",
                    explanationUzbek = "Ushbu 5 ta qolipni yodlab olsangiz, majhul nisbatda hech qachon adashmaysiz:\n\n• Present Simple: The room IS cleaned (tozalanadi)\n• Present Continuous: The room IS BEING cleaned (ayni damda tozalanmoqda)\n• Past Simple: The room WAS cleaned (tozalandi)\n• Present Perfect: The room HAS BEEN cleaned (tozalanib bo'lindi)\n• Future Simple: The room WILL BE cleaned (tozalanadi/tozalanajak)",
                    examples = listOf(
                        MurphyExample("The car is being repaired right now.", "Mashina ayni paytda ta'mirlanmoqda.", "is being repaired"),
                        MurphyExample("Your package will be delivered tomorrow.", "Buyurtmangiz ertaga yetkazib beriladi.", "will be delivered")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u105_ex1",
                    exerciseNumber = "105.1",
                    taskType = "CHOICE",
                    question = "This ancient fortress ______ over five hundred years ago.",
                    options = listOf("was built", "is built", "built", "has built"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was built",
                    explanationUzbek = "500 yil oldin o'tmishda qurilgan: 'was built'."
                ),
                MurphyExerciseItem(
                    id = "u105_ex2",
                    exerciseNumber = "105.2",
                    taskType = "CHOICE",
                    question = "English ______ by millions of people all over the world.",
                    options = listOf("is spoken", "speaks", "was spoken", "is speaking"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is spoken",
                    explanationUzbek = "Ingliz tili butun dunyoda gapiriladi (hozirgi zamon majhul): 'is spoken'."
                ),
                MurphyExerciseItem(
                    id = "u105_ex3",
                    exerciseNumber = "105.3",
                    taskType = "CHOICE",
                    question = "The Mona Lisa was painted ______ Leonardo da Vinci.",
                    options = listOf("by", "with", "from", "of"),
                    correctOptionIndex = 0,
                    correctAnswerText = "by",
                    explanationUzbek = "Ish kim tomonidan qilinganini ko'rsatishda 'by' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u105_ex4",
                    exerciseNumber = "105.4",
                    taskType = "CHOICE",
                    question = "The letters have already ______ by the postman.",
                    options = listOf("been delivered", "being delivered", "delivered", "be delivered"),
                    correctOptionIndex = 0,
                    correctAnswerText = "been delivered",
                    explanationUzbek = "Present Perfect majhul nisbat: 'have been delivered'."
                ),
                MurphyExerciseItem(
                    id = "u105_ex5",
                    exerciseNumber = "105.5",
                    taskType = "CHOICE",
                    question = "The new stadium ______ next summer.",
                    options = listOf("will be opened", "is opening", "was opened", "opened"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will be opened",
                    explanationUzbek = "Kelasi yozda ochiladi (kelasi zamon majhul): 'will be opened'."
                )
            )
        ),

        // UNIT 106 (Appendix 2)
        MurphyUnit(
            unitNumber = 106,
            title = "Appendix 2: List of Irregular Verbs (Eng muhim 50 ta noto'g'ri fe'l)",
            subtitleUzbek = "Kundalik so'zlashuvda har daqiqada kerak bo'ladigan eng faol noto'g'ri fe'llar ro'yxati",
            groupName = "18-Guruh: Appendices & Reference Guides (105–110)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida eng ko'p ishlatiladigan 50 ta noto'g'ri fe'lni bilgan inson nutqining 80% o'tgan zamonini bexato gapira oladi!",
                "V1 (Infinitive): Harakatning nomlanishi (go - bormoq, see - ko'rmoq).",
                "V2 (Past Simple): O'tgan zamon darak gapida (I went yesterday, I saw him).",
                "V3 (Past Participle): Perfect zamonlarda (have gone, have seen) va Passive nisbatda (was seen).",
                "ENG FAOL O'NLIK: be (was/were, been), have (had, had), do (did, done), say (said, said), go (went, gone), get (got, got), make (made, made), know (knew, known), think (thought, thought), take (took, taken)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Kundalik hayotning eng asosiy 25 ta fe'li",
                    formula = "V1 (Bosh shakl) -> V2 (O'tgan zamon) -> V3 (Sifatdosh)",
                    explanationUzbek = "Bularni ma'nosi va 3 ta shakli bilan yodda saqlash tavsiya etiladi:\n\n• be -> was/were -> been (bo'lmoq)\n• become -> became -> become (aylanmoq)\n• begin -> began -> begun (boshlamoq)\n• break -> broke -> broken (sindirmoq)\n• bring -> brought -> brought (olib kelmoq)\n• buy -> bought -> bought (sotib olmoq)\n• come -> came -> come (kelmoq)\n• do -> did -> done (bajarmoq)\n• drink -> drank -> drunk (ichmoq)\n• drive -> drove -> driven (haydamoq)\n• eat -> ate -> eaten (yemoq)\n• find -> found -> found (topmoq)\n• get -> got -> got (olmoq/yetmoq)\n• give -> gave -> given (bermoq)\n• go -> went -> gone (bormoq)",
                    examples = listOf(
                        MurphyExample("I have eaten breakfast already.", "Men allaqachon nonushta qilib bo'ldim.", "eat -> ate -> eaten"),
                        MurphyExample("She drove to Samarkand yesterday.", "U kecha mashinada Samarqandga bordi.", "drive -> drove")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Keyingi 25 ta juda zarur fe'l",
                    formula = "V1 -> V2 -> V3",
                    explanationUzbek = "Muloqotda uzluksiz uchraydigan navbatdagi guruh:\n\n• have -> had -> had (ega bo'lmoq)\n• hear -> heard -> heard (eshitmoq)\n• know -> knew -> known (bilmoq)\n• leave -> left -> left (tark etmoq)\n• lose -> lost -> lost (yo'qotmoq)\n• make -> made -> made (yasamoq)\n• meet -> met -> met (uchratmoq)\n• pay -> paid -> paid (to'lamoq)\n• read /ri:d/ -> read /red/ -> read /red/ (o'qimoq — imlosi bir xil, talaffuzi boshqa!)\n• say -> said -> said (aytmoq)\n• see -> saw -> seen (ko'rmoq)\n• send -> sent -> sent (yubormoq)\n• sleep -> slept -> slept (uxlamoq)\n• speak -> spoke -> spoken (gapirmoq)\n• take -> took -> taken (olmoq)\n• tell -> told -> told (so'zlab bermoq)\n• think -> thought -> thought (o'ylamoq)\n• understand -> understood -> understood (tushunmoq)\n• write -> wrote -> written (yozmoq)",
                    examples = listOf(
                        MurphyExample("I understood everything the teacher explained.", "Men o'qituvchi tushuntirgan hamma narsani tushundim.", "understand -> understood"),
                        MurphyExample("He sent me a text message.", "U menga sms xabar yubordi.", "send -> sent")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u106_ex1",
                    exerciseNumber = "106.1",
                    taskType = "CHOICE",
                    question = "'Find' (topmoq) fe'lining o'tgan zamon shakli (V2) qaysi?",
                    options = listOf("found", "finded", "founded", "finding"),
                    correctOptionIndex = 0,
                    correctAnswerText = "found",
                    explanationUzbek = "'find' fe'lining 2 va 3-shakli 'found' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u106_ex2",
                    exerciseNumber = "106.2",
                    taskType = "CHOICE",
                    question = "Have you ______ your keys yet?",
                    options = listOf("found", "find", "finded", "finding"),
                    correctOptionIndex = 0,
                    correctAnswerText = "found",
                    explanationUzbek = "Have dan keyin V3: 'Have you found...'."
                ),
                MurphyExerciseItem(
                    id = "u106_ex3",
                    exerciseNumber = "106.3",
                    taskType = "CHOICE",
                    question = "She ______ in bed until 10 am because it was Sunday.",
                    options = listOf("slept", "sleeped", "sleep", "slepted"),
                    correctOptionIndex = 0,
                    correctAnswerText = "slept",
                    explanationUzbek = "'sleep' fe'lining o'tgan zamoni 'slept'."
                ),
                MurphyExerciseItem(
                    id = "u106_ex4",
                    exerciseNumber = "106.4",
                    taskType = "CHOICE",
                    question = "The boy ______ the ball over the fence.",
                    options = listOf("threw", "throwed", "thrown", "throwing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "threw",
                    explanationUzbek = "'throw' (otmoq) fe'lining o'tgan zamoni 'threw'."
                ),
                MurphyExerciseItem(
                    id = "u106_ex5",
                    exerciseNumber = "106.5",
                    taskType = "CHOICE",
                    question = "I have never ______ such a beautiful painting!",
                    options = listOf("seen", "saw", "see", "seed"),
                    correctOptionIndex = 0,
                    correctAnswerText = "seen",
                    explanationUzbek = "'have never seen' — 3-shakl 'seen'."
                )
            )
        ),

        // UNIT 107 (Appendix 3)
        MurphyUnit(
            unitNumber = 107,
            title = "Appendix 3: Spelling rules (Imlo qoidalari: -s, -ed, -ing, -er)",
            subtitleUzbek = "Qo'shimchalar qo'shilganda harflarning o'zgarishi va ikkilanish qoidalari",
            groupName = "18-Guruh: Appendices & Reference Guides (105–110)",
            keyTakeawaysUzbek = listOf(
                "UNDOSH IKKILANISHI (CVC qoidasi): Qisqa bir bo'g'inli so'zda 'Undosh + Unli + Undosh' kelsa, oxirgi harf ikkilanadi: stop -> stopping / stopped, run -> running, big -> bigger.",
                "-Y HARFI QOIDASI: Agar -y dan oldin undosh kelsa, -y harfi -i ga aylanadi: study -> studies / studied, easy -> easier, baby -> babies.",
                "LEKIN -y dan oldin unli kelsa, o'zgarmaydi: play -> plays / played, enjoy -> enjoyed.",
                "-E BILAN TUGAGAN SO'ZLAR: -ing qo'shilganda -e tushib qoladi: write -> writing, make -> making, come -> coming.",
                "-S / -ES QOIDASI: -s, -sh, -ch, -x, -o bilan tugasa, -es qo'shiladi: watch -> watches, wash -> washes, go -> goes, box -> boxes."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Oxirgi undoshning ikkilanishi (Doubling consonants)",
                    formula = "Undosh + Unli + Undosh -> oxirgi harf x2",
                    explanationUzbek = "Nega ba'zi so'zlarda harf ikkita bo'lib qoladi? Bunga qisqa unlining cho'zilib ketmasligi sabab bo'ladi:\n\n• stop -> stoPPing / stoPPed\n• rob -> roBBing / roBBed\n• plan -> plaNNing / plaNNed\n• sit -> siTTing\n• swim -> swiMMing\n• big -> biGGer / the biGGest\n• hot -> hoTTer / the hoTTest\n\n⚠️ Istisno: Agar oxirgi harf W, X yoki Y bo'lsa, hech qachon ikkilanmaydi: snow -> snowing, box -> boxing, play -> playing.",
                    examples = listOf(
                        MurphyExample("We are planning our summer holiday.", "Biz yozgi ta'tilimizni rejalashtiryapmiz.", "plan -> planning (nn ikkilanadi)"),
                        MurphyExample("The bus stopped at the red light.", "Avtobus qizil chiroqda to'xtadi.", "stop -> stopped (pp ikkilanadi)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "-Y va -E harflarining o'zgarish siri",
                    formula = "undosh + y -> i (-ies / -ied) | fe'l + e + ing -> -ing",
                    explanationUzbek = "1) -Y harfi oldiga qarang:\n• Undosh bo'lsa: cry -> cries / cried, study -> studies / studied, happy -> happier.\n• Unli bo'lsa: hech narsa o'zgarmaydi: stay -> stays / stayed, buy -> buys.\n\n2) -E harfi -ING oldida yo'qoladi:\n• live -> living (liveing EMAS!)\n• drive -> driving\n• have -> having\n• write -> writing\n\nLEKIN -ee bo'lsa tushmaydi: see -> seeing, agree -> agreeing.",
                    examples = listOf(
                        MurphyExample("She is studying computer science.", "U kompyuter fanini o'rganmoqda.", "study -> studying (y qoladi)"),
                        MurphyExample("He is writing a letter to his friend.", "U do'stiga xat yozyapti.", "write -> writing (e tushib qoladi)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u107_ex1",
                    exerciseNumber = "107.1",
                    taskType = "CHOICE",
                    question = "Qaysi so'zning imlosi 100% to'g'ri yozilgan?",
                    options = listOf("stopping", "stoping", "stoppping", "stopeing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "stopping",
                    explanationUzbek = "'stop' so'zida p harfi ikkilanadi: 'stopping'."
                ),
                MurphyExerciseItem(
                    id = "u107_ex2",
                    exerciseNumber = "107.2",
                    taskType = "CHOICE",
                    question = "'Write' fe'liga -ing qo'shilganda qaysi shakl hosil bo'ladi?",
                    options = listOf("writing", "writeing", "writting", "writteing"),
                    correctOptionIndex = 0,
                    correctAnswerText = "writing",
                    explanationUzbek = "-e harfi tushib qoladi: 'writing'."
                ),
                MurphyExerciseItem(
                    id = "u107_ex3",
                    exerciseNumber = "107.3",
                    taskType = "CHOICE",
                    question = "'Study' fe'lining o'tgan zamon shakli qaysi?",
                    options = listOf("studied", "studyed", "studid", "studying"),
                    correctOptionIndex = 0,
                    correctAnswerText = "studied",
                    explanationUzbek = "Undosh + y holatida -y harfi -i ga aylanadi: 'studied'."
                ),
                MurphyExerciseItem(
                    id = "u107_ex4",
                    exerciseNumber = "107.4",
                    taskType = "CHOICE",
                    question = "'Play' fe'lining o'tgan zamoni qaysi?",
                    options = listOf("played", "plaid", "playyed", "playd"),
                    correctOptionIndex = 0,
                    correctAnswerText = "played",
                    explanationUzbek = "Unli + y bo'lgani uchun -y o'zgarmaydi: 'played'."
                ),
                MurphyExerciseItem(
                    id = "u107_ex5",
                    exerciseNumber = "107.5",
                    taskType = "CHOICE",
                    question = "'Big' sifatining qiyosiy darajasi qaysi?",
                    options = listOf("bigger", "biger", "more big", "bigest"),
                    correctOptionIndex = 0,
                    correctAnswerText = "bigger",
                    explanationUzbek = "Undosh ikkilanadi: 'bigger'."
                )
            )
        ),

        // UNIT 108 (Appendix 4)
        MurphyUnit(
            unitNumber = 108,
            title = "Appendix 4: Short forms / Contractions (Qisqartmalar: I'm, don't, won't)",
            subtitleUzbek = "Jonli ingliz tili qisqartmalari: Qanday aytiladi va qayerda qo'yiladi",
            groupName = "18-Guruh: Appendices & Reference Guides (105–110)",
            keyTakeawaysUzbek = listOf(
                "Inglizlar og'zaki nutqda va norasmiy xatlarda 95% holatda qisqartmalardan foydalanadilar!",
                "AM/IS/ARE qisqartmalari: I'm, he's, she's, it's, we're, they're.",
                "INKOR QISQARTMALARI: don't, doesn't, didn't, isn't, aren't, wasn't, weren't, haven't, hasn't, can't, wouldn't.",
                "WILL QISQARTMASI: I'll, you'll, he'll, we'll; WILL NOT qisqartmasi esa kutilmaganda WON'T /woʊnt/ bo'ladi!",
                "MUHIM QOIDA: Qisqa javoblarda tasdiq bo'lsa qisqartirib bo'lmaydi: 'Yes, I am' (Yes, I'm deb bo'lmaydi!). Lekin inkorda bo'ladi: 'No, I'm not'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Eng ko'p ishlatiladigan ijobiy va inkor qisqartmalar",
                    formula = "'m = am | 's = is/has | 're = are | 'll = will | 'd = would/had",
                    explanationUzbek = "Qisqartma hosil qilishda tushib qolgan harf o'rniga apostrof (') belgisi qo'yiladi:\n\n• I am -> I'm\n• He is -> He's\n• They are -> They're\n• I will -> I'll\n• We would -> We'd\n• I have got -> I've got\n\nInkor qisqartmalari (not -> n't):\n• do not -> don't\n• does not -> doesn't\n• cannot -> can't\n• will not -> won't (maxsus yodlang!)\n• should not -> shouldn't",
                    examples = listOf(
                        MurphyExample("I don't think it'll rain today.", "Bugun yomg'ir yog'adi deb o'ylamayman.", "don't + it'll"),
                        MurphyExample("She won't be able to come to the meeting.", "U majlisga kela olmaydi.", "won't = will not")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "'He's' nima degani? 'He is' mi yoki 'He has'?",
                    formula = "He's + sifat/ot/ing = He IS | He's + V3 = He HAS",
                    explanationUzbek = "Ko'pchilik 's ni ko'rsa adashadi. Buni ajratish juda oson:\n\n1) Agar ketidan sifat, ot yoki -ing kelsa — bu 'IS':\n• He's a teacher. (= He is a teacher)\n• He's tired. (= He is tired)\n• He's working. (= He is working)\n\n2) Agar ketidan fe'lning 3-shakli (V3) kelsa — bu 'HAS':\n• He's gone out. (= He has gone out)\n• She's lost her key. (= She has lost her key)\n\nXuddi shunday: 'He'd' so'zi ham 'He would' yoki 'He had' bo'lishi mumkin.",
                    examples = listOf(
                        MurphyExample("He's already finished his work.", "U allaqachon ishini tugatib bo'ldi.", "He's = He has finished (V3)"),
                        MurphyExample("He's very happy today.", "U bugun juda baxtli.", "He's = He is (sifat)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u108_ex1",
                    exerciseNumber = "108.1",
                    taskType = "CHOICE",
                    question = "'Will not' ning to'g'ri qisqartirilgan shakli qaysi?",
                    options = listOf("won't", "willn't", "win't", "wont"),
                    correctOptionIndex = 0,
                    correctAnswerText = "won't",
                    explanationUzbek = "'will not' qisqartirilganda 'won't' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u108_ex2",
                    exerciseNumber = "108.2",
                    taskType = "CHOICE",
                    question = "'She's bought a new car.' gapidagi 'She's' nimaning qisqartmasi?",
                    options = listOf("She has", "She is", "She was", "She does"),
                    correctOptionIndex = 0,
                    correctAnswerText = "She has",
                    explanationUzbek = "'bought' fe'lning 3-shakli bo'lgani uchun bu yerda 'She has' nazarda tutilgan."
                ),
                MurphyExerciseItem(
                    id = "u108_ex3",
                    exerciseNumber = "108.3",
                    taskType = "CHOICE",
                    question = "'Are you tired?' savoliga qaysi qisqa tasdiq javobi to'g'ri?",
                    options = listOf("Yes, I am.", "Yes, I'm.", "Yes, am I.", "Yes, I'm tired not."),
                    correctOptionIndex = 0,
                    correctAnswerText = "Yes, I am.",
                    explanationUzbek = "Qisqa tasdiq javobida oxirida qisqartma qilinmaydi: 'Yes, I am'."
                ),
                MurphyExerciseItem(
                    id = "u108_ex4",
                    exerciseNumber = "108.4",
                    taskType = "CHOICE",
                    question = "'I cannot' so'zining qisqartmasi qaysi?",
                    options = listOf("I can't", "I cann't", "I cant", "I ca'nt"),
                    correctOptionIndex = 0,
                    correctAnswerText = "I can't",
                    explanationUzbek = "'cannot' -> 'can't'."
                ),
                MurphyExerciseItem(
                    id = "u108_ex5",
                    exerciseNumber = "108.5",
                    taskType = "CHOICE",
                    question = "'We would love to come.' gapini qisqartirganda qanday bo'ladi?",
                    options = listOf("We'd love to come.", "We'll love to come.", "We've love to come.", "We're love to come."),
                    correctOptionIndex = 0,
                    correctAnswerText = "We'd love to come.",
                    explanationUzbek = "'would' qisqartmasi apostrof d bo'ladi: 'We'd'."
                )
            )
        ),

        // UNIT 109 (Appendix 5)
        MurphyUnit(
            unitNumber = 109,
            title = "Appendix 5: British vs American English (Britaniya va Amerika ingliz tili farqlari)",
            subtitleUzbek = "Ikki buyuk lahja o'rtasidagi so'zlashuv, so'zlar va imlo tafovutlari",
            groupName = "18-Guruh: Appendices & Reference Guides (105–110)",
            keyTakeawaysUzbek = listOf(
                "Ikkala til ham bir xil ingliz tili, lekin kundalik so'zlar, imlo va ba'zi grammatik odatlarda qiziq farqlar bor!",
                "KUNDALIK SO'ZLAR: Britaniyada FLAT = Amerikada APARTMENT (kvartira); LIFT = ELEVATOR; PETROL = GAS / GASOLINE; HOLIDAY = VACATION; CHIPS = FRIES.",
                "IMLO (Spelling): BrE '-our' (colour, favourite) vs AmE '-or' (color, favorite); BrE '-re' (theatre, centre) vs AmE '-er' (theater, center).",
                "GRAMMATIKA: Britaniyada 'Have you got a car?' ko'proq aytiladi, Amerikada esa 'Do you have a car?'.",
                "YANGI O'TMISH: Britaniyaliklar 'I have lost my key' desa, amerikaliklar 'I lost my key' deb oddiy o'tgan zamonni ishlataveradi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Eng ko'p ishlatiladigan 15 ta so'z taqqoslovi",
                    formula = "British English (BrE) vs American English (AmE)",
                    explanationUzbek = "Filmlarda yoki kitoblarda ikkala variantga ham duch kelasiz:\n\n• Kvartira: flat (BrE) = apartment (AmE)\n• Lift: lift (BrE) = elevator (AmE)\n• Benzin: petrol (BrE) = gas / gasoline (AmE)\n• Ta'til: holiday (BrE) = vacation (AmE)\n• Do'kon: shop (BrE) = store (AmE)\n• Metro: underground / tube (BrE) = subway (AmE)\n• Kuz fasli: autumn (BrE) = fall (AmE)\n• Pechka/pech: cooker (BrE) = stove (AmE)\n• Shiramay/konfet: sweets (BrE) = candy (AmE)\n• Qovurilgan kartoshka: chips (BrE) = fries / french fries (AmE)",
                    examples = listOf(
                        MurphyExample("We live in a flat on the second floor. (BrE)", "Biz ikkinchi qavatdagi kvartirada yashaymiz.", "flat = apartment"),
                        MurphyExample("We are going on vacation to California. (AmE)", "Biz Kaliforniyaga ta'tilga ketyapmiz.", "vacation = holiday")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Imlo va Grammatika nozikliklari",
                    formula = "BrE: colour, centre, organise | AmE: color, center, organize",
                    explanationUzbek = "1) Imlo farqlari:\n• -OUR vs -OR: colour -> color, honour -> honor, favourite -> favorite\n• -RE vs -ER: centre -> center, theatre -> theater, metre -> meter\n• -ISE vs -IZE: organise -> organize, realise -> realize\n\n2) Grammatika:\n• BrE: at the weekend (dam olish kunlarida)\n• AmE: on the weekend\n\n• BrE: have a bath / shower\n• AmE: take a bath / shower",
                    examples = listOf(
                        MurphyExample("What is your favorite color? (AmE)", "Sizning sevimli rangingiz qaysi?", "favorite color (AmE) vs favourite colour (BrE)"),
                        MurphyExample("What are you doing on the weekend? (AmE)", "Dam olish kunida nima qilyapsiz?", "on the weekend (AmE)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u109_ex1",
                    exerciseNumber = "109.1",
                    taskType = "CHOICE",
                    question = "Britaniya ingliz tilidagi 'flat' (kvartira) so'zi Amerikada nima deyiladi?",
                    options = listOf("apartment", "house", "room", "floor"),
                    correctOptionIndex = 0,
                    correctAnswerText = "apartment",
                    explanationUzbek = "Britaniyada 'flat', Amerikada 'apartment'."
                ),
                MurphyExerciseItem(
                    id = "u109_ex2",
                    exerciseNumber = "109.2",
                    taskType = "CHOICE",
                    question = "Amerika ingliz tilidagi 'vacation' so'zining Britaniyadagi muqobili qaysi?",
                    options = listOf("holiday", "weekend", "break", "trip"),
                    correctOptionIndex = 0,
                    correctAnswerText = "holiday",
                    explanationUzbek = "Britaniyada ta'til 'holiday' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u109_ex3",
                    exerciseNumber = "109.3",
                    taskType = "CHOICE",
                    question = "Qaysi so'z Amerika imlosi (spelling) bo'yicha yozilgan?",
                    options = listOf("color", "colour", "theatre", "favourite"),
                    correctOptionIndex = 0,
                    correctAnswerText = "color",
                    explanationUzbek = "'color' Amerika imlosi (Britaniyada 'colour')."
                ),
                MurphyExerciseItem(
                    id = "u109_ex4",
                    exerciseNumber = "109.4",
                    taskType = "CHOICE",
                    question = "Mashinaga quyiladigan yoqilg'i (benzin) Amerikada qanday ataladi?",
                    options = listOf("gas / gasoline", "petrol", "diesel only", "oil"),
                    correctOptionIndex = 0,
                    correctAnswerText = "gas / gasoline",
                    explanationUzbek = "Amerikada 'gas/gasoline', Britaniyada 'petrol'."
                ),
                MurphyExerciseItem(
                    id = "u109_ex5",
                    exerciseNumber = "109.5",
                    taskType = "CHOICE",
                    question = "Amerikaliklar 'dam olish kunlarida' deganda qaysi predlogni ko'proq ishlatishadi?",
                    options = listOf("on the weekend", "at the weekend", "in the weekend", "by the weekend"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on the weekend",
                    explanationUzbek = "AmE: 'on the weekend', BrE: 'at the weekend'."
                )
            )
        ),

        // UNIT 110 (Appendix 6)
        MurphyUnit(
            unitNumber = 110,
            title = "Appendix 6: Ultimate Grammar Study Guide & Daily Checklist",
            subtitleUzbek = "O'rganilgan barcha bilimlarni mustahkamlash, kundalik mashq dasturi va tavsiyalar",
            groupName = "18-Guruh: Appendices & Reference Guides (105–110)",
            keyTakeawaysUzbek = listOf(
                "MUKAMMAL NATIJA: Siz endi ingliz tili A1 dan B1+ gacha bo'lgan barcha bazaviy va oraliq grammatik qoidalarini to'liq o'rganib chiqdingiz!",
                "15 DAQIQALIK KUNDALIK QOIDA: Har kuni ilovaning qidiruv (Search) bo'limidan 2 ta tasodifiy darsni ochib, mashqlarini qayta ishlang.",
                "OVOZ CHIQARIB O'QISH: Misollarni o'qiyotganda albatta baland ovozda takrorlang — bu nutq apparatini inglizcha ohangga moslashtiradi.",
                "O'ZINGIZ HAQINGIZDA GAP TUZING: Har bir qoidani o'rganganingizda, o'z hayotingiz, oilangiz yoki ishingiz haqida 2 ta misol tuzing.",
                "XATOLARDAN QO'RQMANG: Muloqotda asosiy maqsad — fikrni yetkazish. Qoidalar esa sizga ishonch va ravonlik bag'ishlaydi!"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Grammatikani nutqqa aylantirishning 3 ta oltin qadami",
                    formula = "Tushunish -> Test orqali tekshirish -> Shaxsiy jumlalar tuzish",
                    explanationUzbek = "Bilim amaliyotda qo'llanmasa, tezda unutiladi. Shuning uchun:\n\n1-qadam: Ilovadagi Unit tushuntirishini diqqat bilan o'qing (formulalarga qarang).\n2-qadam: Unit oxiridagi 5 ta testni ishlang. Xato qilsangiz, izohini o'qib darhol tuzating.\n3-qadam: Daftaringizga shu qoida bo'yicha faqat O'ZINGIZ haqida 3 ta gap yozing (Masalan: 'I am learning English because I want to travel').",
                    examples = listOf(
                        MurphyExample("Consistency is key to fluency.", "Doimiylik — ravon so'zlashuvning eng muhim kalitidir.", "Daily 15 minutes!"),
                        MurphyExample("You have the power to master any language.", "Sizda istalgan tilni egallash qudrati bor.", "Never give up!")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "O'z-o'zini tekshirish uchun 5 ta nazorat savoli",
                    formula = "To be vs Do? | Past Simple vs Present Perfect? | Say vs Tell?",
                    explanationUzbek = "O'zingizga quyidagi savollarni berib ko'ring:\n\n• Nega 'I am agree' deb bo'lmaydi? (Chunki agree — fe'l, to be kerak emas: 'I agree')\n• 'He lives here since 2020' to'g'rimi? (Yo'q, since bo'lsa 'has lived' bo'lishi shart!)\n• 'He said me' to'g'rimi? (Yo'q, 'He told me' yoki 'He said to me')\n• 'If it will rain' deb bo'ladimi? (Yo'q, If dan keyin 'will' qo'yilmaydi: 'If it rains')\n• 'Listen music' to'g'rimi? (Yo'q, 'Listen to music')\n\nAgar bu farqlarni bir qarashda payqayotgan bo'lsangiz, demak siz ingliz tili grammatikasini a'lo darajada o'zlashtirdingiz!",
                    examples = listOf(
                        MurphyExample("I agree with you 100%.", "Sizga 100% qo'shilaman.", "I agree (I am agree emas!)"),
                        MurphyExample("Listen to your heart.", "Yuragingizga quloq soling.", "listen to...")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u110_ex1",
                    exerciseNumber = "110.1",
                    taskType = "CHOICE",
                    question = "Qaysi jumla 100% grammatik jihatdan to'g'ri?",
                    options = listOf("I agree with you.", "I am agree with you.", "I'm agree with you.", "I agreeing with you."),
                    correctOptionIndex = 0,
                    correctAnswerText = "I agree with you.",
                    explanationUzbek = "'Agree' fe'l bo'lgani sababli 'to be' (am) qo'yilmaydi: 'I agree with you'."
                ),
                MurphyExerciseItem(
                    id = "u110_ex2",
                    exerciseNumber = "110.2",
                    taskType = "CHOICE",
                    question = "Qaysi gapda xato mavjud?",
                    options = listOf("He said me that he was busy.", "He told me that he was busy.", "He said to me that he was busy.", "He said that he was busy."),
                    correctOptionIndex = 0,
                    correctAnswerText = "He said me that he was busy.",
                    explanationUzbek = "'He said me' deb aytib bo'lmaydi, 'He told me' bo'lishi kerak."
                ),
                MurphyExerciseItem(
                    id = "u110_ex3",
                    exerciseNumber = "110.3",
                    taskType = "CHOICE",
                    question = "If it ______ sunny tomorrow, we will go to the mountains.",
                    options = listOf("is", "will be", "was", "would be"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is",
                    explanationUzbek = "'If' dan keyin kelasi zamonda ham Present Simple qo'yiladi: 'If it is sunny'."
                ),
                MurphyExerciseItem(
                    id = "u110_ex4",
                    exerciseNumber = "110.4",
                    taskType = "CHOICE",
                    question = "I have been living in Tashkent ______ 2018.",
                    options = listOf("since", "for", "from", "in"),
                    correctOptionIndex = 0,
                    correctAnswerText = "since",
                    explanationUzbek = "Boshlanish yili aniq bo'lganda 'since 2018' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u110_ex5",
                    exerciseNumber = "110.5",
                    taskType = "CHOICE",
                    question = "Congratulations on finishing the course! Which word means 'Davomiylik va qat'iyatlik'?",
                    options = listOf("Consistency", "Confusion", "Hesitation", "Distraction"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Consistency",
                    explanationUzbek = "'Consistency' — doimiylik, izchillik va qat'iyatlik degani."
                )
            )
        )
    )
}
