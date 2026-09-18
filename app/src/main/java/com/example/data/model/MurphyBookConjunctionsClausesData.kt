package com.example.data.model

object MurphyBookConjunctionsClausesData {

    val UNITS_CONJUNCTIONS_CLAUSES: List<MurphyUnit> = listOf(
        // UNIT 87
        MurphyUnit(
            unitNumber = 87,
            title = "to, in, at (Prepositions of place 2 - Direction vs Location)",
            subtitleUzbek = "Harakat yo'nalishi (TO) va turgan joy (IN / AT) farqlari",
            groupName = "15-Guruh: Conjunctions & Relative Clauses (87–92)",
            keyTakeawaysUzbek = listOf(
                "TO = HARAKAT YO'NALISHI (Qayerga?): go to work, go to London, come to my house, welcome to Uzbekistan.",
                "IN / AT = TURGAN JOY / STATIKA (Qayerda?): be in London, work at an office, stay at home.",
                "HOME SO'ZI: 'go home', 'come home', 'get home' deganda 'TO' UMUMAN ISHLATILMAYDI! Lekin uyda bo'lsa 'at home' deyiladi.",
                "ARRIVE (Yetib kelmoq): arrive IN (shahar/mamlakatga): arrive in Tashkent, arrive in England; arrive AT (bino/bekatga): arrive at the airport, arrive at the station. ⚠️ ARRIVE TO HECH QACHON DEYILMAYDI!",
                "GET TO: get to work, get to London (lekin 'get home' to-siz)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "TO (Qayerga? Harakat) vs IN/AT (Qayerda? Tinch holat)",
                    formula = "go / come / travel TO (joy) vs be / live / stay IN/AT (joy)",
                    explanationUzbek = "Buni juda sodda tushunish mumkin: agar oyog'ingiz qimirlab, bir joydan ikkinchi joyga qarab yo'l olsangiz — TO ishlatiladi. Agar bir joyda o'tirgan yoki yashayotgan bo'lsangiz — IN yoki AT ishlatiladi:\n\n• We are going to the cinema. (Biz kinoga ketyapmiz — harakat: TO)\n• We are at the cinema now. (Biz hozir kinodamiz — joylashuv: AT)\n\n• She went to France last year. (U o'tgan yili Fransiyaga ketdi — TO)\n• She lives in France now. (U hozir Fransiyada yashaydi — IN)",
                    examples = listOf(
                        MurphyExample("What time do you go to bed?", "Soat nechada uxlashga yotasiz?", "go to bed (yo'nalish)"),
                        MurphyExample("I stayed in bed all morning.", "Ertalab bo'yi to'shakda yotdim.", "stay in bed (holat)"),
                        MurphyExample("Welcome to Uzbekistan!", "O'zbekistonga xush kelibsiz!", "welcome to...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Home va Arrive so'zlari bilan nozik qoidalar",
                    formula = "go home (TO yo'q!) | arrive in + davlat/shahar | arrive at + bino/stansiya",
                    explanationUzbek = "1) HOME so'zi:\n• Uyga boryapman: I'm going home. (I'm going to home — QAT'IYAN XATO!)\n• Uyga yetib keldim: I got home at 6 pm.\n• Lekin uyda o'tirganda: I am AT home. (Bu yerda 'at' bo'ladi)\n\n2) ARRIVE (yetib bormoq) fe'li:\nHech qachon 'arrive to' deb aytmang! \n• Katta shahar va davlatlarga: arrive IN Tashkent, arrive IN London.\n• Kichik joylar, bekatlar, aeroportlarga: arrive AT the airport, arrive AT the hotel.",
                    examples = listOf(
                        MurphyExample("Let's walk home together.", "Keling, uyga birga piyoda qaytamiz.", "walk home ('to' yo'q!)"),
                        MurphyExample("The plane arrived in New York on time.", "Samolyot Nyu-Yorkka o'z vaqtida yetib bordi.", "arrived in + katta shahar"),
                        MurphyExample("We arrived at the station five minutes late.", "Biz vokzalga besh daqiqa kechikib yetib keldik.", "arrived at + bino")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u87_ex1",
                    exerciseNumber = "87.1",
                    taskType = "CHOICE",
                    question = "I'm tired. I want to go ______ home right now.",
                    options = listOf("— (hech qanday predlog)", "to", "at", "in"),
                    correctOptionIndex = 0,
                    correctAnswerText = "— (hech qanday predlog)",
                    explanationUzbek = "'go home' iborasida 'to' ishlatilmaydi: to'g'ridan-to'g'ri 'go home'."
                ),
                MurphyExerciseItem(
                    id = "u87_ex2",
                    exerciseNumber = "87.2",
                    taskType = "CHOICE",
                    question = "What time did you arrive ______ the airport?",
                    options = listOf("at", "to", "in", "on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "at",
                    explanationUzbek = "Aeroport kabi bino va manzillarga yetib borish 'arrive at' bo'ladi ('arrive to' xato!)."
                ),
                MurphyExerciseItem(
                    id = "u87_ex3",
                    exerciseNumber = "87.3",
                    taskType = "CHOICE",
                    question = "They are travelling ______ Italy for their summer holiday.",
                    options = listOf("to", "at", "in", "into"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Harakat yo'nalishi (Italiyaga) bo'lgani sababli 'travelling to Italy'."
                ),
                MurphyExerciseItem(
                    id = "u87_ex4",
                    exerciseNumber = "87.4",
                    taskType = "CHOICE",
                    question = "We arrived ______ London early in the morning.",
                    options = listOf("in", "to", "at", "on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in",
                    explanationUzbek = "Katta shahar yoki mamlakatlarga yetib borishda 'arrive in' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u87_ex5",
                    exerciseNumber = "87.5",
                    taskType = "CHOICE",
                    question = "I stayed ______ home and watched a football match.",
                    options = listOf("at", "to", "in", "on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "at",
                    explanationUzbek = "Uyda bo'lish / qolish 'stay at home' deyiladi."
                )
            )
        ),

        // UNIT 88
        MurphyUnit(
            unitNumber = 88,
            title = "under, behind, opposite, between, next to etc. (Prepositions of place 3)",
            subtitleUzbek = "Fazoviy joylashuv predloglari: Tagida, orqasida, ro'parasida, orasida",
            groupName = "15-Guruh: Conjunctions & Relative Clauses (87–92)",
            keyTakeawaysUzbek = listOf(
                "NEXT TO = 'Yonida / yonma-yon': The cinema is next to the bank.",
                "BETWEEN = 'Orasida' (ikki narsa yoki kishi o'rtasida): between the bank and the post office.",
                "OPPOSITE = 'Ro'parasida / yuzma-yuz': The supermarket is opposite my house (ko'chaning narigi tomonida qarama-qarshi).",
                "IN FRONT OF = 'Oldida' vs BEHIND = 'Orqasida': Don't park in front of the gate (darvoza oldida).",
                "UNDER = 'Tagida / ostida': The cat is sleeping under the table.",
                "ABOVE = 'Tepasida' (tegib turmagan balandlikda): The picture is above the fireplace."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Eng ko'p ishlatiladigan 6 ta joylashuv predlogi",
                    formula = "next to | between | in front of | behind | under | opposite",
                    explanationUzbek = "Har bir predlogni hayotiy ko'z oldingizga keltiring:\n\n1) NEXT TO (Yonida):\n• Our house is next to a park. (Uyimiz parkning yonginasida)\n\n2) BETWEEN (Ikkita narsa orasida):\n• B harfi A va C orasida: B is between A and C.\n\n3) IN FRONT OF (Oldida):\n• The teacher stands in front of the students. (O'qituvchi o'quvchilar oldida turadi)\n\n4) BEHIND (Orqasida):\n• Who is standing behind you? (Orqangizda kim turibdi?)\n\n5) OPPOSITE (Ro'parasida — ko'chaning narigi betida):\n• I live opposite a bakery. (Men novvoyxona ro'parasida yashayman)\n\n6) UNDER (Tagida):\n• Put the bag under the chair. (Sumkani stul tagiga qo'y)",
                    examples = listOf(
                        MurphyExample("The pharmacy is between the supermarket and the bank.", "Dorixona supermarket va bank o'rtasida joylashgan.", "between A and B"),
                        MurphyExample("The bus stop is opposite our hotel.", "Avtobus bekati mehmonxonamizning ro'parasida.", "opposite = yuzma-yuz qarama-qarshi"),
                        MurphyExample("There is a small garden in front of the house.", "Uyning oldida kichik bog'cha bor.", "in front of")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Above vs On, Under vs Below farqlari",
                    formula = "ON (tegib turibdi) vs ABOVE (havoda, teparoqda)",
                    explanationUzbek = "Juda qiziq nozik farq:\n\n• ON — narsa yuzaga TEGIB turadi: The cup is on the table (Chashka stol ustida turibdi).\n• ABOVE — oraliq masofa bor, havoda TEPASIDA turadi: The ceiling light is above the table (Chiroq stolning tepasida osilib turibdi).\n\n• UNDER — to'g'ridan-to'g'ri tagida: under the blanket (ko'rpaning tagida)\n• BELOW — daraja yoki sath jihatdan pastda: 10 degrees below zero (noldan 10 daraja past).",
                    examples = listOf(
                        MurphyExample("The plane flew above the clouds.", "Samolyot bulutlarning tepasidan uchib o'tdi.", "above the clouds"),
                        MurphyExample("The dog was hiding under the bed.", "It karavotning tagiga yashirinib olgandi.", "under the bed")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u88_ex1",
                    exerciseNumber = "88.1",
                    taskType = "CHOICE",
                    question = "Our hotel was ______ the beach, so we just crossed the street to swim.",
                    options = listOf("opposite", "under", "between", "behind"),
                    correctOptionIndex = 0,
                    correctAnswerText = "opposite",
                    explanationUzbek = "Ko'chaning narigi tomonida, ro'parasida bo'lish 'opposite' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u88_ex2",
                    exerciseNumber = "88.2",
                    taskType = "CHOICE",
                    question = "Tom sits ______ Sarah and Jack in the classroom.",
                    options = listOf("between", "among", "next", "behind of"),
                    correctOptionIndex = 0,
                    correctAnswerText = "between",
                    explanationUzbek = "Ikki kishi o'rtasida bo'lganda 'between' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u88_ex3",
                    exerciseNumber = "88.3",
                    taskType = "CHOICE",
                    question = "There is a cat resting ______ the shadow of the tree.",
                    options = listOf("under", "above", "between", "opposite"),
                    correctOptionIndex = 0,
                    correctAnswerText = "under",
                    explanationUzbek = "Daraxt soyasi tagida: 'under the shadow'."
                ),
                MurphyExerciseItem(
                    id = "u88_ex4",
                    exerciseNumber = "88.4",
                    taskType = "CHOICE",
                    question = "The post office is right ______ to the train station.",
                    options = listOf("next", "near", "beside", "close"),
                    correctOptionIndex = 0,
                    correctAnswerText = "next",
                    explanationUzbek = "'next to' birikmasi: bevosita yonma-yon."
                ),
                MurphyExerciseItem(
                    id = "u88_ex5",
                    exerciseNumber = "88.5",
                    taskType = "CHOICE",
                    question = "Please don't stand ______ me, I can't see the whiteboard!",
                    options = listOf("in front of", "behind", "next", "under"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in front of",
                    explanationUzbek = "Doskani to'sib qo'ymaslik uchun 'oldimda turmang': 'in front of me'."
                )
            )
        ),

        // UNIT 89
        MurphyUnit(
            unitNumber = 89,
            title = "up, down, through, along, across, past (Prepositions of movement)",
            subtitleUzbek = "Harakat predloglari: Yuqoriga, pastga, bo'ylab, kesib o'tib, yonidan",
            groupName = "15-Guruh: Conjunctions & Relative Clauses (87–92)",
            keyTakeawaysUzbek = listOf(
                "UP = Yuqoriga: walk up the hill (tepaga chiqmoq).",
                "DOWN = Pastga: run down the stairs (zinadan pastga tushmoq).",
                "ALONG = Bo'ylab: walk along the street / river (ko'cha bo'ylab yurmoq).",
                "ACROSS = Bir tomondan ikkinchi tomonga kesib o'tish: swim across the river, walk across the road.",
                "THROUGH = Biror narsaning ichidan o'tib ketish: walk through the park, drive through the tunnel.",
                "PAST = Yonidan to'xtamasdan o'tib ketish: go past the post office and turn left.",
                "ROUND / AROUND = Aylana bo'ylab: walk round the lake (ko'l atrofida aylanmoq)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Harakatning yo'nalishlari va tasavvuri",
                    formula = "fe'l + up / down / along / across / through / past",
                    explanationUzbek = "Yo'l ko'rsatganda yoki qayergadir qanday borganingizni aytganda bu predloglar kerak bo'ladi:\n\n• We walked along the river for an hour. (Biz bir soat daryo bo'ylab yurdik — daryoga parallel holda)\n• Be careful when you walk across the street. (Ko'chani kesib o'tayotganingizda ehtiyot bo'ling — bir betidan ikkinchi betiga)\n• The train went through a long tunnel. (Poyezd uzun tunnelning ichidan o'tib ketdi)\n• Go past the church, then turn right. (Cherkovning yonidan to'xtamay o'ting, so'ng o'ngga buriling)\n• He ran up the stairs to his room. (U xonasiga zinadan yuqoriga yugurib chiqdi)",
                    examples = listOf(
                        MurphyExample("They walked through the forest.", "Ular o'rmon ichidan o'tib ketishdi.", "through the forest"),
                        MurphyExample("She walked past me without saying a word.", "U bir og'iz ham gapirmay yonimdan o'tib ketdi.", "past me = yonimdan"),
                        MurphyExample("Can you swim across this river?", "Bu daryoni suzib kesib o'ta olasizmi?", "across = narigi qirg'oqqa")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Into, Out of, Onto, Off (Ichiga va ustiga harakat)",
                    formula = "INTO (ichiga) vs OUT OF (ichidan tashqariga) | ONTO (ustiga) vs OFF (ustidan pastga)",
                    explanationUzbek = "Bular harakatdagi o'zgarishlar:\n\n• He got into his car and drove off. (U mashinasining ichiga kirdi)\n• She took the keys out of her bag. (U kalitlarni sumkasining ichidan chiqardi)\n• The cat jumped onto the table. (Mushuk stolning ustiga sakrab chiqdi)\n• The ball rolled off the roof. (Koptok tomni ustidan pastga yumalab tushdi)",
                    examples = listOf(
                        MurphyExample("Come into the living room!", "Mehmonxonaning ichiga kiring!", "into the room"),
                        MurphyExample("He fell off his bike.", "U velosipedidan (ustidan) yiqilib tushdi.", "fell off")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u89_ex1",
                    exerciseNumber = "89.1",
                    taskType = "CHOICE",
                    question = "We walked ______ the beach listening to the sound of waves.",
                    options = listOf("along", "across", "through", "past"),
                    correctOptionIndex = 0,
                    correctAnswerText = "along",
                    explanationUzbek = "Sohil bo'ylab, qirg'oqqa parallel yurish 'along the beach' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u89_ex2",
                    exerciseNumber = "89.2",
                    taskType = "CHOICE",
                    question = "Look both ways before you walk ______ the busy street.",
                    options = listOf("across", "along", "through", "up"),
                    correctOptionIndex = 0,
                    correctAnswerText = "across",
                    explanationUzbek = "Ko'chani bir tomonidan ikkinchi tomoniga kesib o'tish 'walk across' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u89_ex3",
                    exerciseNumber = "89.3",
                    taskType = "CHOICE",
                    question = "The car went ______ the tunnel in the mountain.",
                    options = listOf("through", "across", "along", "past"),
                    correctOptionIndex = 0,
                    correctAnswerText = "through",
                    explanationUzbek = "Tunnel ichidan yorib o'tish 'through the tunnel' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u89_ex4",
                    exerciseNumber = "89.4",
                    taskType = "CHOICE",
                    question = "Go ______ the cinema and take the second turning on your left.",
                    options = listOf("past", "through", "into", "along"),
                    correctOptionIndex = 0,
                    correctAnswerText = "past",
                    explanationUzbek = "Kinoteatrning yonidan to'xtamay o'tib ketish: 'go past the cinema'."
                ),
                MurphyExerciseItem(
                    id = "u89_ex5",
                    exerciseNumber = "89.5",
                    taskType = "CHOICE",
                    question = "He took his passport ______ his pocket and showed it to the officer.",
                    options = listOf("out of", "into", "onto", "through"),
                    correctOptionIndex = 0,
                    correctAnswerText = "out of",
                    explanationUzbek = "Cho'ntak ichidan chiqarib olish: 'out of his pocket'."
                )
            )
        ),

        // UNIT 90
        MurphyUnit(
            unitNumber = 90,
            title = "on, at, by, with, about (Prepositions in common phrases)",
            subtitleUzbek = "Predlogli mashhur iboralar: by car, on holiday, with a knife, about money",
            groupName = "15-Guruh: Conjunctions & Relative Clauses (87–92)",
            keyTakeawaysUzbek = listOf(
                "TRANSPORT VOSITASI: 'BY' bilan kelganda artiklsiz ishlatiladi: by car, by bus, by train, by plane, by boat. LEKIN: on foot (piyoda)!",
                "ON IBORALARI: on holiday (ta'tilda), on television (televizorda), on the radio, on the phone, on fire (yonayotgan).",
                "AT IBORALARI: at the age of 25 (25 yoshida), at 100 km/h (tezlikda).",
                "WITH = 'Bilan' (qurol yoki asbob bilan): cut with a knife, write with a pencil; WITHOUT = 'siz' (choy shakarsiz: tea without sugar).",
                "ABOUT = 'Haqida': a book about history, talk about the weather."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "By transport vs On foot (Piyoda)",
                    formula = "by + transport (artiklsiz!) vs on foot",
                    explanationUzbek = "Transport turini umumiy aytganda 'by' ishlatiladi va o'rtaga 'a' yoki 'the' qo'yilmaydi:\n\n• Do you go to work by bus or by car? (Ishga avtobusda borasizmi yoki mashinadami?)\n• We travelled to Madrid by plane. (Madridga samolyotda uchdik)\n\n⚠️ PIYODA YURISH: Hech qachon 'by foot' deyilmaydi! Faqat 'ON FOOT':\n• Did you come by car or on foot? (Mashinada keldingizmi yoki piyodami?)\n\nEslatma: Agar 'my car' yoki 'the bus' desangiz, u holda Unit 86 dagi qoida bo'yicha 'in my car' yoki 'on the bus' bo'ladi.",
                    examples = listOf(
                        MurphyExample("I always go to university on foot.", "Men universitetga doim piyoda boraman.", "on foot (piyoda)"),
                        MurphyExample("They travelled across Europe by train.", "Ular Yevropa bo'ylab poyezdda sayohat qilishdi.", "by train")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "On holiday, On the phone va With (asbob bilan)",
                    formula = "on holiday | on the phone | with + asbob | about + mavzu",
                    explanationUzbek = "Yodda saqlash lozim bo'lgan eng mashhur birikmalar:\n\n1) ON:\n• She is away on holiday this week. (U bu hafta ta'tilda)\n• I saw this interview on TV. (Men bu intervyuni televizorda ko'rdim)\n• Who were you speaking to on the phone? (Telefonda kim bilan gaplashayotgan edingiz?)\n\n2) WITH (Vosita yoki sheriklik):\n• He opened the locked door with a key. (U qulflangan eshikni kalit bilan ochdi)\n• A man with brown hair (Jigarrang sochli kishi)\n• Do you want coffee with milk? (Kofeni sut bilan ichasizmi?)\n\n3) ABOUT (Mavzu haqida):\n• What is this movie about? (Bu film nima haqida?)\n• We were talking about our future plans.",
                    examples = listOf(
                        MurphyExample("Don't cut the bread with that small knife.", "Nonni anavi kichkina pichoq bilan kesma.", "with + qurol/asbob"),
                        MurphyExample("Jane is on holiday in Spain.", "Jeyn Ispaniyada ta'tilda.", "on holiday"),
                        MurphyExample("Tell me about your new project.", "Menga yangi loyihangiz haqida so'zlab bering.", "about + mavzu")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u90_ex1",
                    exerciseNumber = "90.1",
                    taskType = "CHOICE",
                    question = "It's only a ten-minute walk, so let's go ______ foot.",
                    options = listOf("on", "by", "with", "at"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Piyoda yurish ingliz tilida faqat 'on foot' deb aytiladi ('by foot' xato)."
                ),
                MurphyExerciseItem(
                    id = "u90_ex2",
                    exerciseNumber = "90.2",
                    taskType = "CHOICE",
                    question = "He usually goes to the office ______ subway.",
                    options = listOf("by", "on", "in", "with"),
                    correctOptionIndex = 0,
                    correctAnswerText = "by",
                    explanationUzbek = "Metroda/transportda qatnash: 'by subway'."
                ),
                MurphyExerciseItem(
                    id = "u90_ex3",
                    exerciseNumber = "90.3",
                    taskType = "CHOICE",
                    question = "I can't talk right now, I am ______ the phone with a client.",
                    options = listOf("on", "at", "in", "by"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Telefonda gaplashayotgan bo'lish: 'on the phone'."
                ),
                MurphyExerciseItem(
                    id = "u90_ex4",
                    exerciseNumber = "90.4",
                    taskType = "CHOICE",
                    question = "You can't open this box ______ a screwdriver.",
                    options = listOf("without", "with no", "not with", "out of"),
                    correctOptionIndex = 0,
                    correctAnswerText = "without",
                    explanationUzbek = "'buragichsiz (otvyortkasiz)' = 'without a screwdriver'."
                ),
                MurphyExerciseItem(
                    id = "u90_ex5",
                    exerciseNumber = "90.5",
                    taskType = "CHOICE",
                    question = "Our boss is currently away ______ holiday in Italy.",
                    options = listOf("on", "at", "in", "for"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Ta'tilda bo'lish doim 'on holiday' deb ishlatiladi."
                )
            )
        ),

        // UNIT 91
        MurphyUnit(
            unitNumber = 91,
            title = "and, but, or, so, because (Conjunctions - Bog'lovchilar)",
            subtitleUzbek = "Gaplarni bir-biriga bog'lash: Va, ammo, yoki, shuning uchun, chunki",
            groupName = "15-Guruh: Conjunctions & Relative Clauses (87–92)",
            keyTakeawaysUzbek = listOf(
                "AND = 'Va' (ikkita o'xshash fikrni qo'shadi): I bought a sandwich AND an apple.",
                "BUT = 'Ammo / lekin / biroq' (qarama-qarshi fikrlarni bog'laydi): He is rich BUT unhappy.",
                "OR = 'Yoki' (tanlov beradi): Do you want tea OR coffee?",
                "SO = 'Shuning uchun / natijada' (sababdan NATIJAGA o'tadi): It was raining, SO I took an umbrella.",
                "BECAUSE = 'Chunki / sababli' (natijadan SABABGA o'tadi): I took an umbrella BECAUSE it was raining.",
                "SO va BECAUSE bir-biriga teskari: Sabab -> SO -> Natija | Natija -> BECAUSE -> Sabab."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "And, But, Or ning qo'llanilishi",
                    formula = "fikr + AND (qo'shish) / BUT (zidlik) / OR (tanlov)",
                    explanationUzbek = "Oddiy jumlalarni bitta chiroyli murakkab gapga aylantiramiz:\n\n1) AND (va):\n• We stayed at home and watched TV.\n• She speaks English and French.\n\n2) BUT (lekin, ammo — kutilmagan qarama-qarshilik):\n• He bought a new car, but he doesn't drive it.\n• I called him, but he didn't answer.\n\n3) OR (yoki):\n• You can come today or tomorrow.\n• Inkor gapda: I don't like tea OR coffee. (Choyni ham, kofeni ham yoqtirmayman)",
                    examples = listOf(
                        MurphyExample("The hotel was nice, but the weather was awful.", "Mehmonxona yaxshi edi, ammo ob-havo dabdala bo'ldi.", "but (zid fikr)"),
                        MurphyExample("Hurry up, or we will miss the train!", "Tezlashing, aks holda (yoki) poyezdga kechikamiz!", "or")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "SO (Shuning uchun) vs BECAUSE (Chunki)",
                    formula = "Sabab + SO + Natija vs Natija + BECAUSE + Sabab",
                    explanationUzbek = "Bu ikki bog'lovchi sabab-oqibatni bog'laydi, lekin ularning yo'nalishi teskari:\n\n1) SO (Shuning uchun — natijani ko'rsatadi):\n• I was very tired, SO I went to bed early.\n(Men juda charchagan edim, SHUNING UCHUN erta uxlagani yotdim)\n\n2) BECAUSE (Chunki — sababni tushuntiradi):\n• I went to bed early BECAUSE I was very tired.\n(Erta uxlagani yotdim, CHUNKI juda charchagan edim)\n\nKo'rdingizmi? Ikkala gap bir xil ma'noda, faqat qaysi biri birinchi aytilishiga qarab 'so' yoki 'because' tanlanadi.",
                    examples = listOf(
                        MurphyExample("She didn't eat dinner because she wasn't hungry.", "U kechki ovqatni yemadi, chunki qorni och emas edi.", "because + sabab"),
                        MurphyExample("He didn't study, so he failed the exam.", "U dars qilmadi, shuning uchun imtihondan yiqildi.", "so + natija")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u91_ex1",
                    exerciseNumber = "91.1",
                    taskType = "CHOICE",
                    question = "I wanted to buy the coat, ______ it was too expensive.",
                    options = listOf("but", "so", "because", "and"),
                    correctOptionIndex = 0,
                    correctAnswerText = "but",
                    explanationUzbek = "Olishni xohladi, lekin juda qimmat edi (zidlik): 'but'."
                ),
                MurphyExerciseItem(
                    id = "u91_ex2",
                    exerciseNumber = "91.2",
                    taskType = "CHOICE",
                    question = "We opened the window ______ it was very hot in the room.",
                    options = listOf("because", "so", "but", "or"),
                    correctOptionIndex = 0,
                    correctAnswerText = "because",
                    explanationUzbek = "Derazani ochdik, chunki xona juda issiq edi: 'because'."
                ),
                MurphyExerciseItem(
                    id = "u91_ex3",
                    exerciseNumber = "91.3",
                    taskType = "CHOICE",
                    question = "The water wasn't clean, ______ we didn't go swimming.",
                    options = listOf("so", "because", "but", "although"),
                    correctOptionIndex = 0,
                    correctAnswerText = "so",
                    explanationUzbek = "Suv toza emas edi, shuning uchun cho'milgani bormadik: 'so'."
                ),
                MurphyExerciseItem(
                    id = "u91_ex4",
                    exerciseNumber = "91.4",
                    taskType = "CHOICE",
                    question = "Would you like to pay in cash ______ by credit card?",
                    options = listOf("or", "and", "but", "so"),
                    correctOptionIndex = 0,
                    correctAnswerText = "or",
                    explanationUzbek = "Naqd puldami yoki kartadami (tanlov): 'or'."
                ),
                MurphyExerciseItem(
                    id = "u91_ex5",
                    exerciseNumber = "91.5",
                    taskType = "CHOICE",
                    question = "He likes his job, ______ the salary is quite low.",
                    options = listOf("although / but", "so", "because", "and"),
                    correctOptionIndex = 0,
                    correctAnswerText = "although / but",
                    explanationUzbek = "Ishini yoqtiradi, ammo maoshi pastroq: 'but'."
                )
            )
        ),

        // UNIT 92
        MurphyUnit(
            unitNumber = 92,
            title = "When ... and If ... (Present tense for the future)",
            subtitleUzbek = "Kelasi zamon shart va payt ergash gaplari: When va If dan keyin kelasi zamon qo'yilmasligi",
            groupName = "15-Guruh: Conjunctions & Relative Clauses (87–92)",
            keyTakeawaysUzbek = listOf(
                "OLTIN QOIDA: Ingliz tilida WHEN (qachonki), BEFORE (oldin), AFTER (keyin), AS SOON AS (bilanoq), IF (agar) so'zlaridan keyin KELASI ZAMON BO'LSA HAM 'WILL' ISHLATILMAYDI! Uning o'rniga ODDIY HOZIRGI ZAMON (Present Simple) qo'yiladi.",
                "To'g'ri: 'When I arrive, I will call you' (When I will arrive DEYISH QAT'IYAN XATO!).",
                "To'g'ri: 'If it rains tomorrow, we will stay at home' (If it will rain DEYISH QAT'IYAN XATO!).",
                "IF vs WHEN farqi: IF = Agar (bo'lishi ham, bo'lmasligi ham mumkin: If I see him...); WHEN = Qachonki (aniq yuz beradi, vaqt masalasi: When I get home...)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "When / As soon as dan keyin 'will' qo'yilmasligi",
                    formula = "WHEN / BEFORE / AS SOON AS + Present Simple , WILL + fe'l",
                    explanationUzbek = "O'zbek tilida 'Men Toshkentga yetib borganimda, sizga qo'ng'iroq qilaman' deb ikkala qismda ham kelasi zamon ishlatamiz. \n\nLekin ingliz tilida 'when' (qachonki) bor qismda 'WILL' ishlatish taqiqlanadi:\n\n• To'g'ri: When I get home tonight, I'll take a shower.\n• Noto'g'ri: When I will get home...\n\n• To'g'ri: I will wait here until you come back.\n• Noto'g'ri: ...until you will come back.\n\n• As soon as the rain stops, we will leave. (Yomg'ir to'xtashi bilanoq jo'naymiz)",
                    examples = listOf(
                        MurphyExample("What are you going to do when you finish school?", "Maktabni tugatganingizda nima qilmoqchisiz?", "when you finish (finish + will yo'q)"),
                        MurphyExample("I will call you before I leave the office.", "Ofisdan chiqishimdan oldin sizga telefon qilaman.", "before I leave")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "IF (Agar) vs WHEN (Qachonki) farqi",
                    formula = "IF (ehtimoliy) vs WHEN (aniq bo'ladigan)",
                    explanationUzbek = "1) IF (Agar — bo'lishi noaniq, shart):\n• If it rains tomorrow, we won't go to the park. (Agar ertaga yomg'ir yog'sa... lekin yog'masligi ham mumkin)\n• If I have time this evening, I'll write to you.\n\n2) WHEN (Qachonki — voqea 100% yuz beradi, faqat vaqti kutilmoqda):\n• When I get home, I will have dinner. (Uyga borganimda ovqatlanaman — chunki uyga baribir boraman!)\n• When the sun rises, it will be warmer.\n\n⚠️ Xulosa: If dan keyin ham kelasi zamon uchun 'will' emas, Present Simple qo'yiladi!",
                    examples = listOf(
                        MurphyExample("If you don't hurry, you will miss the train.", "Agar shoshilmasangiz, poyezdni o'tkazib yuborasiz.", "if you don't hurry"),
                        MurphyExample("I'll see you tomorrow when I arrive.", "Ertaga yetib kelganimda siz bilan ko'rishaman.", "when I arrive")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u92_ex1",
                    exerciseNumber = "92.1",
                    taskType = "CHOICE",
                    question = "I will call you as soon as I ______ at the hotel.",
                    options = listOf("arrive", "will arrive", "arrived", "am arriving"),
                    correctOptionIndex = 0,
                    correctAnswerText = "arrive",
                    explanationUzbek = "'as soon as' dan keyin kelasi zamonda ham Present Simple (arrive) qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u92_ex2",
                    exerciseNumber = "92.2",
                    taskType = "CHOICE",
                    question = "If it ______ tomorrow, we will have a picnic in the park.",
                    options = listOf("doesn't rain", "won't rain", "didn't rain", "not rain"),
                    correctOptionIndex = 0,
                    correctAnswerText = "doesn't rain",
                    explanationUzbek = "'If' shart qismida 'will/won't' bo'lmaydi: 'doesn't rain' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u92_ex3",
                    exerciseNumber = "92.3",
                    taskType = "CHOICE",
                    question = "What will you do if you ______ your passport?",
                    options = listOf("lose", "will lose", "lost", "loses"),
                    correctOptionIndex = 0,
                    correctAnswerText = "lose",
                    explanationUzbek = "'if you lose' (will ishlatilmaydi)."
                ),
                MurphyExerciseItem(
                    id = "u92_ex4",
                    exerciseNumber = "92.4",
                    taskType = "CHOICE",
                    question = "Please turn off the lights before you ______ out.",
                    options = listOf("go", "will go", "went", "going"),
                    correctOptionIndex = 0,
                    correctAnswerText = "go",
                    explanationUzbek = "'before' dan keyin oddiy fe'l: 'before you go'."
                ),
                MurphyExerciseItem(
                    id = "u92_ex5",
                    exerciseNumber = "92.5",
                    taskType = "CHOICE",
                    question = "I am going shopping. ______ I see something nice, I will buy it.",
                    options = listOf("If", "When", "Until", "Although"),
                    correctOptionIndex = 0,
                    correctAnswerText = "If",
                    explanationUzbek = "Chiroyli narsa uchraydimi yoki yo'qmi noaniq, shuning uchun 'If' (agar) mos keladi."
                )
            )
        )
    )
}
