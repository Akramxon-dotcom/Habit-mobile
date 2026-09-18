package com.example.data.model

object MurphyBookArticlesNounsData {

    val UNITS_ARTICLES_NOUNS: List<MurphyUnit> = listOf(
        // UNIT 63
        MurphyUnit(
            unitNumber = 63,
            title = "a / an (Indefinite article)",
            subtitleUzbek = "Noaniq artikl: 'a' va 'an' ning to'g'ri qo'llanilishi",
            groupName = "11-Guruh: Articles & Nouns (63–68)",
            keyTakeawaysUzbek = listOf(
                "A / AN faqat BIRLIKDAGI SANOQLI otlar oldidan qo'yiladi (a car, a book, an apple).",
                "A yoki AN tanlash HARFGA emas, balki TALAFFUZ TOVUSHIGA bog'liq: unli tovush oldidan 'an' (an umbrella, an hour), undosh tovush oldidan 'a' (a university, a European city).",
                "Kasb va mutaxassisliklar oldidan DOIM 'a/an' ishlatiladi: He is a doctor (U shifokor), She is an engineer.",
                "Ko'plikdagi otlar yoki sanalmaydigan otlar oldidan 'a/an' QO'YILMAYDI (a cars - NOTO'G'RI, a water - NOTO'G'RI).",
                "Birlikdagi sanoqli otlar hech qachon artiklsiz yolg'iz qolmaydi: I have a car (I have car DEYILMAYDI)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "A vs AN tanlash qoidasi (Tovushga qarab)",
                    formula = "Undosh tovush -> A | Unli tovush -> AN",
                    explanationUzbek = "Eng muhim narsa - so'z qanday harf bilan yozilishi emas, qanday tovush bilan boshlanishidir:\n\n1) A + Undosh tovush:\n• a book, a table, a hat, a car\n• a university (talaffuz: [yu...] - undosh 'y' tovushi)\n• a European country (talaffuz: [yu...])\n• a one-hour trip (talaffuz: [w...])\n\n2) AN + Unli tovush:\n• an apple, an egg, an island, an uncle\n• an hour ('h' o'qilmaydi, talaffuz: [auer] - unli tovush)\n• an honest man ('h' o'qilmaydi)\n• an MBA degree (talaffuz: [em...])",
                    examples = listOf(
                        MurphyExample("She is studying at a university.", "U universitetda tahsil olmoqda.", "a university [yu...]"),
                        MurphyExample("I will be back in an hour.", "Bir soatdan keyin qaytib kelaman.", "an hour (h tovushsiz)"),
                        MurphyExample("He is an honest person.", "U halol inson.", "an honest [onist]")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Kasblar va tavsiflarda a/an",
                    formula = "Ega + be + A/AN + kasb / mutaxassislik",
                    explanationUzbek = "Ingliz tilida kasb yoki odamning kimligini aytganda 'a/an' qo'yish majburiydir:\n\n• What does Mark do? - He is an architect. (Mark nima ish qiladi? - U arxitektor)\n• My sister is a dentist. (Mening singlim stomatolog)\n• Would you like to be a teacher? (O'qituvchi bo'lishni xohlarmidingiz?)\n\nShuningdek, sifat + birlikdagi ot birikmasida:\n• It is a beautiful day. (Ajoyib kun)\n• Tashkent is a large city. (Toshkent katta shahar)",
                    examples = listOf(
                        MurphyExample("Jack is a good driver.", "Jek yaxshi haydovchi.", "a + sifat + ot"),
                        MurphyExample("She wants to be an astronaut.", "U fazogir bo'lishni xohlaydi.", "an astronaut"),
                        MurphyExample("Do you have a question?", "Savolingiz bormi?", "a question (birlikdagi ot)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u63_ex1",
                    exerciseNumber = "63.1",
                    taskType = "CHOICE",
                    question = "We had to wait for ______ hour at the airport.",
                    options = listOf("an", "a", "the", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "an",
                    explanationUzbek = "'hour' so'zi unli [auer] tovushi bilan boshlangani sababli 'an hour' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u63_ex2",
                    exerciseNumber = "63.2",
                    taskType = "CHOICE",
                    question = "My elder brother is ______ architect.",
                    options = listOf("an", "a", "the", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "an",
                    explanationUzbek = "Kasblar oldidan 'a/an' keladi, 'architect' unli bilan boshlangani uchun 'an architect'."
                ),
                MurphyExerciseItem(
                    id = "u63_ex3",
                    exerciseNumber = "63.3",
                    taskType = "CHOICE",
                    question = "Oxford is ______ famous university.",
                    options = listOf("a", "an", "the", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "a",
                    explanationUzbek = "Sifat 'famous' [f...] undosh tovushi bilan boshlanadi: 'a famous university'."
                ),
                MurphyExerciseItem(
                    id = "u63_ex4",
                    exerciseNumber = "63.4",
                    taskType = "CHOICE",
                    question = "He is studying at ______ university in Germany.",
                    options = listOf("a", "an", "the", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "a",
                    explanationUzbek = "'university' so'zi [yu...] undosh tovushi bilan talaffuz qilinadi, shuning uchun 'a university'."
                ),
                MurphyExerciseItem(
                    id = "u63_ex5",
                    exerciseNumber = "63.5",
                    taskType = "CHOICE",
                    question = "Would you like ______ apple or a banana?",
                    options = listOf("an", "a", "the", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "an",
                    explanationUzbek = "'apple' unli tovush bilan boshlangani sababli 'an apple'."
                )
            )
        ),

        // UNIT 64
        MurphyUnit(
            unitNumber = 64,
            title = "flower(s) / bus(es) (Singular and plural nouns)",
            subtitleUzbek = "Otlar ko'pligi: -s, -es, -ies qo'shimchalari va noto'g'ri ko'pliklar",
            groupName = "11-Guruh: Articles & Nouns (63–68)",
            keyTakeawaysUzbek = listOf(
                "Oddiy ko'plik: otga -s qo'shiladi (a book -> books, a car -> cars).",
                "-s, -sh, -ch, -x bilan tugasa: -ES qo'shiladi (a bus -> buses, a watch -> watches, a box -> boxes).",
                "Undosh + Y bilan tugasa: -IES ga aylanadi (a baby -> babies, a city -> cities). Lekin unli + Y bo'lsa faqat -s (a boy -> boys, a day -> days).",
                "-f yoki -fe bilan tugasa: -VES ga aylanadi (a leaf -> leaves, a wife -> wives, a knife -> knives).",
                "NOTO'G'RI KO'PLIKLAR (Yodlash shart): a man -> men, a woman -> women, a child -> children, a person -> people, a tooth -> teeth, a foot -> feet, a mouse -> mice."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Ko'plik qo'shimchalari qoidalari (-s, -es, -ies, -ves)",
                    formula = "Ot + -s / -es / -ies / -ves",
                    explanationUzbek = "Ko'plik yasashdagi imlo qoidalari:\n\n1) Umumiy: flower -> flowers, car -> cars\n2) -s, -z, -ch, -sh, -x: bus -> buses, dish -> dishes, church -> churches, box -> boxes\n3) Undosh + y: party -> parties, dictionary -> dictionaries, country -> countries\n4) -f / -fe: shelf -> shelves, thief -> thieves, half -> halves, knife -> knives\n\n⚠️ Ko'plikdagi otlar oldidan 'a/an' ishlatilmaydi: They are flowers (They are a flowers EMAS).",
                    examples = listOf(
                        MurphyExample("Be careful with those sharp knives.", "U o'tkir pichoqlardan ehtiyot bo'l.", "knife -> knives"),
                        MurphyExample("How many cities have you visited?", "Qancha shaharlarga bordingiz?", "city -> cities"),
                        MurphyExample("She bought two new watches yesterday.", "U kecha ikkita yangi soat sotib oldi.", "watch -> watches")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Qoidaga bo'ysunmaydigan otlar (Irregular plurals)",
                    formula = "Shakli tubdan o'zgaradigan otlar",
                    explanationUzbek = "Bu otlarga hech qachon '-s' qo'shilmaydi, ularning ko'pligi alohida so'zdir:\n\n• a man -> men (erkaklar)\n• a woman -> women (ayollar - talaffuz: [wimin])\n• a child -> children (bolalar)\n• a person -> people (odamlar)\n• a tooth -> teeth (tishlar)\n• a foot -> feet (oyoqlar)\n• a mouse -> mice (sichqonlar)\n• a sheep -> sheep (qo'ylar - o'zgarmaydi)\n• a fish -> fish (baliqlar - o'zgarmaydi)\n\n⚠️ 'People', 'police' so'zlari doim ko'plikda keladi: People ARE friendly (is emas), The police ARE coming.",
                    examples = listOf(
                        MurphyExample("There were a lot of people at the concert.", "Konsertda juda ko'p odamlar bor edi.", "people are / were"),
                        MurphyExample("Brush your teeth twice a day.", "Tishlaringizni kuniga ikki marta tozalang.", "tooth -> teeth"),
                        MurphyExample("The children are playing in the playground.", "Bolalar maydonchada o'ynashmoqda.", "children are")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u64_ex1",
                    exerciseNumber = "64.1",
                    taskType = "CHOICE",
                    question = "Most of the autumn ______ have fallen from the trees.",
                    options = listOf("leaves", "leafs", "leafes", "leave"),
                    correctOptionIndex = 0,
                    correctAnswerText = "leaves",
                    explanationUzbek = "'leaf' (barg) so'zining ko'pligi 'leaves' bo'ladi (-f -> -ves)."
                ),
                MurphyExerciseItem(
                    id = "u64_ex2",
                    exerciseNumber = "64.2",
                    taskType = "CHOICE",
                    question = "Those ______ look very tired after work.",
                    options = listOf("men", "mans", "mens", "man"),
                    correctOptionIndex = 0,
                    correctAnswerText = "men",
                    explanationUzbek = "'man' so'zining ko'pligi 'men' (erkaklar)."
                ),
                MurphyExerciseItem(
                    id = "u64_ex3",
                    exerciseNumber = "64.3",
                    taskType = "CHOICE",
                    question = "The police ______ looking for the stolen car.",
                    options = listOf("are", "is", "was", "has"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are",
                    explanationUzbek = "'The police' ingliz tilida har doim ko'plik fe'lini oladi: police are."
                ),
                MurphyExerciseItem(
                    id = "u64_ex4",
                    exerciseNumber = "64.4",
                    taskType = "CHOICE",
                    question = "How many ______ do you have? - Two girls and a boy.",
                    options = listOf("children", "childs", "childrens", "child"),
                    correctOptionIndex = 0,
                    correctAnswerText = "children",
                    explanationUzbek = "'child' ko'pligi 'children' bo'ladi ('childrens' noto'g'ri)."
                ),
                MurphyExerciseItem(
                    id = "u64_ex5",
                    exerciseNumber = "64.5",
                    taskType = "CHOICE",
                    question = "He caught three big ______ in the river.",
                    options = listOf("fish", "fishes", "fishs", "fishess"),
                    correctOptionIndex = 0,
                    correctAnswerText = "fish",
                    explanationUzbek = "'fish' (baliq) so'zining ko'pligi ham 'fish' bo'lib qoladi."
                )
            )
        ),

        // UNIT 65
        MurphyUnit(
            unitNumber = 65,
            title = "a car / some money (Countable and uncountable 1)",
            subtitleUzbek = "Sanoqli va sanalmaydigan otlar: Qoidalar va farqlar",
            groupName = "11-Guruh: Articles & Nouns (63–68)",
            keyTakeawaysUzbek = listOf(
                "SANOQLI OTLAR (Countable): 1, 2, 3 deb sanash mumkin bo'lgan narsalar (one car, two cars, three cars). Birlikda 'a/an' oladi, ko'plikda '-s' oladi.",
                "SANALMAYDIGAN OTLAR (Uncountable): Sanab bo'lmaydigan moddalar, suyuqliklar va tushunchalar: water, money, music, air, rice, cheese, bread.",
                "SANALMAYDIGAN OTLAR oldidan 'a/an' KELMAYDI (a water - NOTO'G'RI) va ularga '-s' QO'SHILMAYDI (moneys - NOTO'G'RI).",
                "Sanalmaydigan otlar oldidan 'some' (biroz/qanchadir) yoki aniq o'lchov ishlatiladi: some water, a glass of water, a loaf of bread, some money.",
                "Sanalmaydigan otlar doim BIRLIK fe'lini oladi: Money IS important, Water IS cold."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Sanoqli va sanalmaydigan otlarni ajratish",
                    formula = "Countable: a car / cars | Uncountable: (some) water / money",
                    explanationUzbek = "1) SANOQLI OTLAR:\n• Donalab sanash mumkin: a song (qo'shiq) -> two songs, a bottle (shisha) -> three bottles.\n• Birlikda yolg'iz kela olmaydi: I bought a camera (I bought camera deyilmaydi).\n\n2) SANALMAYDIGAN OTLAR:\n• Donalab sanab bo'lmaydi: music, water, gold, blood, plastic, salt, tea, coffee.\n• 'a music' yoki 'two musics' deyilmaydi! Faqat 'some music' yoki 'a piece of music' deyiladi.\n• Ular bilan birlik fe'li keladi: This coffee is delicious.",
                    examples = listOf(
                        MurphyExample("Can I have a glass of water?", "Bir stakan suv olsam bo'ladimi?", "a glass of water (suv sanalmaydi, stakan sanaladi)"),
                        MurphyExample("I don't have much money left.", "Menda ko'p pul qolmadi.", "money sanalmaydi: much money"),
                        MurphyExample("She was listening to some beautiful music.", "U go'zal musiqa tinglayotgan edi.", "some music (a music emas)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Sanalmaydigan otlarni o'lchash birliklari",
                    formula = "a cup / bottle / slice / loaf / piece OF + sanalmaydigan ot",
                    explanationUzbek = "Sanalmaydigan narsalarning aniq miqdorini aytish uchun maxsus so'zlardan foydalanamiz:\n\n• a glass of water (bir stakan suv)\n• a cup of tea (bir chashka choy)\n• a bottle of milk (bir shisha sut)\n• a loaf of bread / a slice of bread (bir buxanka non / bir bo'lak non)\n• a bar of chocolate (bir plitka shokolad)\n• a piece of cheese (bir bo'lak pishloq)\n• a bowl of soup (bir kosa sho'rva)",
                    examples = listOf(
                        MurphyExample("He bought a loaf of bread at the bakery.", "U novvoyxonadan bir buxanka non sotib oldi.", "a loaf of bread"),
                        MurphyExample("Would you like a cup of coffee?", "Bir finjon kofe xohlaysizmi?", "a cup of coffee"),
                        MurphyExample("Give me a piece of paper, please.", "Menga bir varaq qog'oz bering, iltimos.", "a piece of paper")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u65_ex1",
                    exerciseNumber = "65.1",
                    taskType = "CHOICE",
                    question = "I am thirsty. I want ______ water.",
                    options = listOf("some", "a", "an", "one"),
                    correctOptionIndex = 0,
                    correctAnswerText = "some",
                    explanationUzbek = "Suv sanalmaydi, shuning uchun 'a water' emas, 'some water' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u65_ex2",
                    exerciseNumber = "65.2",
                    taskType = "CHOICE",
                    question = "Could you pass me a ______ of bread, please?",
                    options = listOf("slice", "bar", "cup", "bottle"),
                    correctOptionIndex = 0,
                    correctAnswerText = "slice",
                    explanationUzbek = "Bir bo'lak non = 'a slice of bread'."
                ),
                MurphyExerciseItem(
                    id = "u65_ex3",
                    exerciseNumber = "65.3",
                    taskType = "CHOICE",
                    question = "The news on television ______ very interesting today.",
                    options = listOf("is", "are", "were", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is",
                    explanationUzbek = "'News' (yangiliklar) ingliz tilida sanalmaydi va birlikda 'is' oladi."
                ),
                MurphyExerciseItem(
                    id = "u65_ex4",
                    exerciseNumber = "65.4",
                    taskType = "CHOICE",
                    question = "I need to buy ______ new shoes for the wedding.",
                    options = listOf("some", "a", "an", "one"),
                    correctOptionIndex = 0,
                    correctAnswerText = "some",
                    explanationUzbek = "Shoes ko'plikdagi ot bo'lgani uchun 'some shoes' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u65_ex5",
                    exerciseNumber = "65.5",
                    taskType = "CHOICE",
                    question = "How much ______ do you have in your wallet?",
                    options = listOf("money", "moneys", "coin", "dollar"),
                    correctOptionIndex = 0,
                    correctAnswerText = "money",
                    explanationUzbek = "'How much' sanalmaydigan otlar (money) bilan keladi."
                )
            )
        ),

        // UNIT 66
        MurphyUnit(
            unitNumber = 66,
            title = "a cake / some cake / some paper (Countable and uncountable 2)",
            subtitleUzbek = "O'zbek tilida sanaladigan, lekin ingliz tilida sanalmaydigan mashhur otlar",
            groupName = "11-Guruh: Articles & Nouns (63–68)",
            keyTakeawaysUzbek = listOf(
                "INGLIZ TILIDA SANALMAYDIGAN (eng ko'p xato qilinadigan) otlar:\n• information (ma'lumot - 'an information' EMAS)\n• advice (maslahat - 'an advice' EMAS)\n• weather (ob-havo - 'a weather' EMAS)\n• news (yangilik - birlik fe'li oladi)\n• bread (non - 'a bread' EMAS)\n• baggage / luggage (yuk/chamadonlar)\n• furniture (mebel/jihozlar)\n• work (ish - 'a work' EMAS, lekin 'a job' deyish mumkin).",
                "Maslahat olganda: 'a piece of advice' yoki 'some advice'.",
                "Ma'lumot so'raganda: 'some information' yoki 'a piece of information'.",
                "I have a lot of work to do (I have a work deyilmaydi!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Sanalmaydigan qiyin otlar ro'yxati",
                    formula = "advice, information, news, weather, luggage, furniture, bread, traffic",
                    explanationUzbek = "O'zbek tilida 'bitta maslahat', 'ikkita yangilik' deb aytamiz, lekin ingliz tilida bu otlar SANALMAYDI:\n\n• Can you give me some advice? (Menga biroz maslahat bera olasizmi? - 'an advice' xato)\n• I need some information about hotels. (Mehmonxonalar haqida ma'lumot kerak - 'informations' yo'q)\n• What nice weather! (Qanday ajoyib ob-havo! - 'a nice weather' deyilmaydi)\n• We have too much luggage. (Bizda juda ko'p yuk bor)\n• There was heavy traffic on the road. (Yo'lda tirbandlik katta edi)",
                    examples = listOf(
                        MurphyExample("Let me give you a piece of advice.", "Sizga bitta maslahat berishga ruxsat eting.", "a piece of advice"),
                        MurphyExample("The furniture in this room is very modern.", "Bu xonadagi mebellar juda zamonaviy.", "furniture is (sanalmaydi)"),
                        MurphyExample("I have some good news for you.", "Siz uchun yaxshi yangiliklarim bor.", "news birlik fe'li oladi")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Job (sanaladi) vs Work (sanalmaydi)",
                    formula = "a job (sanoqli) vs work (sanalmaydigan)",
                    explanationUzbek = "Bu ikki so'z ma'nodosh, lekin grammatik jihatdan butunlay farq qiladi:\n\n1) JOB (sanoqli):\n• I am looking for a job. (Men ish qidiryapman)\n• She has had three different jobs this year.\n\n2) WORK (sanalmaydigan):\n• I have a lot of work to do. (Qiladigan ishim ko'p - 'a work' emas)\n• What time do you finish work? (Ishni soat nechada tugatasiz?)",
                    examples = listOf(
                        MurphyExample("Jack has got a new job.", "Jek yangi ishga kirdi.", "a job (sanoqli)"),
                        MurphyExample("I have too much work today.", "Bugun ishim haddan tashqari ko'p.", "much work (sanalmaydi)"),
                        MurphyExample("He goes to work by bus.", "U ishga avtobusda boradi.", "to work")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u66_ex1",
                    exerciseNumber = "66.1",
                    taskType = "CHOICE",
                    question = "My teacher gave me some very useful ______.",
                    options = listOf("advice", "advices", "an advice", "piece of advices"),
                    correctOptionIndex = 0,
                    correctAnswerText = "advice",
                    explanationUzbek = "'advice' ingliz tilida sanalmaydi: 'some advice' to'g'ri ('advices' yoki 'an advice' mavjud emas)."
                ),
                MurphyExerciseItem(
                    id = "u66_ex2",
                    exerciseNumber = "66.2",
                    taskType = "CHOICE",
                    question = "We had ______ lovely weather during our holiday in Italy.",
                    options = listOf("(no article)", "a", "an", "the"),
                    correctOptionIndex = 0,
                    correctAnswerText = "(no article)",
                    explanationUzbek = "'weather' sanalmaydi, shuning uchun 'a lovely weather' deyilmaydi: 'lovely weather'."
                ),
                MurphyExerciseItem(
                    id = "u66_ex3",
                    exerciseNumber = "66.3",
                    taskType = "CHOICE",
                    question = "Did you get ______ about the train times?",
                    options = listOf("any information", "an information", "many informations", "informations"),
                    correctOptionIndex = 0,
                    correctAnswerText = "any information",
                    explanationUzbek = "'information' sanalmaydi: 'any information'."
                ),
                MurphyExerciseItem(
                    id = "u66_ex4",
                    exerciseNumber = "66.4",
                    taskType = "CHOICE",
                    question = "I want to apply for ______ as a software developer.",
                    options = listOf("a job", "a work", "job", "work"),
                    correctOptionIndex = 0,
                    correctAnswerText = "a job",
                    explanationUzbek = "Kasb/ish o'rni sanoqli bo'lgani uchun 'a job' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u66_ex5",
                    exerciseNumber = "66.5",
                    taskType = "CHOICE",
                    question = "All their luggage ______ lost at the airport.",
                    options = listOf("was", "were", "are", "have been"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was",
                    explanationUzbek = "'luggage' (bagaj) sanalmaydigan ot bo'lgani uchun birlikda 'was' oladi."
                )
            )
        ),

        // UNIT 67
        MurphyUnit(
            unitNumber = 67,
            title = "a / an and the",
            subtitleUzbek = "Noaniq 'a/an' va aniq 'the' artikllarining fundamental farqi",
            groupName = "11-Guruh: Articles & Nouns (63–68)",
            keyTakeawaysUzbek = listOf(
                "A / AN = Birinchi marta tilga olinganda, noma'lum yoki umumiy biror narsa (I saw a man. There is a dog).",
                "THE = Tinglovchiga va so'zlovchiga ANIQ bo'lgan narsa, yoki ikkinchi marta takrorlanganda (The man was tall. The dog started barking).",
                "Xonada YAGONA bo'lgan narsalar oldidan 'the' keladi: open the door, turn on the TV, the light, the ceiling, the floor.",
                "Tabiatda yagona bo'lgan tushunchalar: the sun, the moon, the earth, the sky, the world.",
                "TAQQOSLASH: Can you pass me a pen? (Ixtiyoriy bitta ruchka) vs Can you pass me the pen? (Stol ustidagi aniq o'sha ruchka)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "A/AN (Birinchi marta) vs THE (Aniq/Takror)",
                    formula = "Birinchi marta -> a/an | Keyingi zikrda -> the",
                    explanationUzbek = "Matnda biror narsa haqida ilk bor aytganda 'a/an', keyin esa 'the' ishlatiladi:\n\n• I had a sandwich and an apple for lunch. The sandwich was nice, but the apple had a worm in it.\n(Men tushlikka sendvich va olma yedim. Sendvich mazali edi, lekin olma qurtlagan ekan)\n\n• A man and a woman were standing outside. The man was holding a guitar.\n(Tashqarida bir erkak va bir ayol turishgandi. Erkak kishi gitara ushlab olgandi)",
                    examples = listOf(
                        MurphyExample("We bought a new car. The car is electric.", "Biz yangi mashina sotib oldik. Mashina elektrda yuradi.", "a car -> the car"),
                        MurphyExample("There is a supermarket near here. Let's go to the supermarket.", "Shu yaqinda supermarket bor. O'sha supermarketga boramiz.", "a supermarket -> the supermarket")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Vaziyatda aniq yoki yagona bo'lgan narsalar",
                    formula = "the + xonada/muhitda yagona bo'lgan narsalar",
                    explanationUzbek = "Tinglovchi qaysi narsa haqida gap ketayotganini aniq bilib tursa, 'the' qo'yiladi:\n\n• Can you open the window, please? (Derazani ocha olasizmi? - Xonadagi aniq deraza)\n• Where is the bathroom? (Hojatxona qayerda? - Uydagi hojatxona)\n• The police are here. (Politsiya yetib keldi)\n• The doctor told me to rest. (Shifokor dam olishni buyurdi)\n\nTabiatdagi yagona narsalar:\n• the sun (quyosh), the moon (oy), the sky (osmon), the world (dunyo)",
                    examples = listOf(
                        MurphyExample("The sun rises in the east.", "Quyosh sharqdan chiqadi.", "the sun (yagona)"),
                        MurphyExample("Could you turn off the light?", "Chiroqni o'chirib yubora olasizmi?", "the light (xonadagi chiroq)"),
                        MurphyExample("Who is the president of this country?", "Bu mamlakatning prezidenti kim?", "the president (aniq lavozim)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u67_ex1",
                    exerciseNumber = "67.1",
                    taskType = "CHOICE",
                    question = "I bought a jacket and a shirt. ______ jacket fits me perfectly.",
                    options = listOf("The", "A", "An", "Some"),
                    correctOptionIndex = 0,
                    correctAnswerText = "The",
                    explanationUzbek = "Kurtka ikkinchi marta tilga olinyapti va u qaysi kurtkaligi aniq: 'The jacket'."
                ),
                MurphyExerciseItem(
                    id = "u67_ex2",
                    exerciseNumber = "67.2",
                    taskType = "CHOICE",
                    question = "Look up at ______ sky! It's full of stars tonight.",
                    options = listOf("the", "a", "an", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the",
                    explanationUzbek = "'Sky' (osmon) yagona tushuncha bo'lgani uchun doim 'the sky' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u67_ex3",
                    exerciseNumber = "67.3",
                    taskType = "CHOICE",
                    question = "Excuse me, can you tell me where ______ nearest bank is?",
                    options = listOf("the", "a", "an", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the",
                    explanationUzbek = "Orttirma darajadagi sifatlar (nearest, best, oldest) oldidan 'the' keladi."
                ),
                MurphyExerciseItem(
                    id = "u67_ex4",
                    exerciseNumber = "67.4",
                    taskType = "CHOICE",
                    question = "Can you pass me ______ salt from the table?",
                    options = listOf("the", "a", "an", "one"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the",
                    explanationUzbek = "Stol ustidagi aniq tuzdon nazarda tutilgani sababli 'the salt' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u67_ex5",
                    exerciseNumber = "67.5",
                    taskType = "CHOICE",
                    question = "There was ______ strange noise outside our bedroom window.",
                    options = listOf("a", "the", "an", "(no article)"),
                    correctOptionIndex = 0,
                    correctAnswerText = "a",
                    explanationUzbek = "Birinchi marta noma'lum tovush haqida gapirilmoqda: 'a strange noise'."
                )
            )
        ),

        // UNIT 68
        MurphyUnit(
            unitNumber = 68,
            title = "the ... (go to work / go to the cinema)",
            subtitleUzbek = "'The' artikli qachon ishlatiladi va qachon tushib qoladi?",
            groupName = "11-Guruh: Articles & Nouns (63–68)",
            keyTakeawaysUzbek = listOf(
                "ARTIKLSIZ ISHLATILADIGAN JOY NOMALARI (O'zining asosiy maqsadida foydalanilganda):\n• go to bed (yotishga/uxlashga bormoq)\n• go to work / at work (ishda bo'lmoq)\n• go to school / at school (o'qishda bo'lmoq)\n• go to university / college\n• be in hospital / go to prison\n• at home / go home.",
                "'THE' BILAN ISHLATILADIGAN TUSHUNCHALAR:\n• the cinema / the theatre (Let's go to the cinema)\n• the bank / the post office\n• the station / the airport\n• the doctor / the dentist.",
                "TAQQOSLASH: Ken is in hospital (U bemor bo'lib yotibdi) vs I went to the hospital to visit Ken (Kasalxonaga bino sifatida mehmonga bordim)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Artiklsiz keladigan iboralar (No 'the')",
                    formula = "school, university, college, work, bed, hospital, prison, home",
                    explanationUzbek = "Agar inson ushbu muassasaga o'zining asl maqsadida (o'quvchi sifatida maktabga, xasta sifatida kasalxonaga, mahbus sifatida qamoqqa) borsa, 'the' ishlatilmaydi:\n\n• What time do you go to bed? (Soat nechada uxlashga yotasiz?)\n• Children go to school five days a week. (Bolalar haftada 5 kun maktabga borishadi)\n• My father is at work right now. (Otam hozir ishda)\n• She was very sick and had to stay in hospital. (U qattiq betob bo'lib, kasalxonada yotishiga to'g'ri keldi)\n• go home / stay at home (uyda bo'lmoq)",
                    examples = listOf(
                        MurphyExample("I usually go to bed at 11:00 PM.", "Odatda kechki soat 11 da yotishga boraman.", "go to bed ('the' yo'q)"),
                        MurphyExample("He is still at school.", "U hali maktabda (darsda).", "at school"),
                        MurphyExample("My brother is at university studying law.", "Akam universitetda huquqni o'rganmoqda.", "at university")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Doim 'the' oladigan kundalik joylar",
                    formula = "the cinema, the theatre, the bank, the post office, the station",
                    explanationUzbek = "Ko'ngilochar va xizmat ko'rsatish maskanlari oldidan odatda 'the' qo'yiladi:\n\n• I often go to the cinema on Friday evenings. (Juma oqshomlari tez-tez kinoga boraman)\n• I need to go to the bank to change some money. (Pul almashtirish uchun bankka borishim kerak)\n• Can you drop me off at the train station? (Meni temir yo'l vokzalida tushirib keta olasizmi?)\n• I have an appointment with the dentist tomorrow. (Ertaga tish shifokori qabuliga yozilganman)",
                    examples = listOf(
                        MurphyExample("We went to the cinema last night.", "Kecha oqshom kinoga bordik.", "to the cinema"),
                        MurphyExample("Is there a post office near here? - Yes, the post office is on the corner.", "Shu yaqinda pochta bormi?", "the post office"),
                        MurphyExample("He went to the doctor because of a stomachache.", "Qorin og'rig'i tufayli u shifokorga bordi.", "the doctor")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u68_ex1",
                    exerciseNumber = "68.1",
                    taskType = "CHOICE",
                    question = "I am exhausted. I think I will go to ______ now.",
                    options = listOf("bed", "the bed", "a bed", "beds"),
                    correctOptionIndex = 0,
                    correctAnswerText = "bed",
                    explanationUzbek = "Uxlashga yotish iborasi 'go to bed' shaklida artiklsiz ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u68_ex2",
                    exerciseNumber = "68.2",
                    taskType = "CHOICE",
                    question = "Do you want to come with us to ______ cinema tonight?",
                    options = listOf("the", "a", "(no article)", "an"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the",
                    explanationUzbek = "Kinoga borish turg'un iborasi 'go to the cinema' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u68_ex3",
                    exerciseNumber = "68.3",
                    taskType = "CHOICE",
                    question = "My mother is still at ______ until 5:00 PM.",
                    options = listOf("work", "the work", "a work", "working"),
                    correctOptionIndex = 0,
                    correctAnswerText = "work",
                    explanationUzbek = "Ishda bo'lish 'at work' deb aytiladi ('at the work' xato)."
                ),
                MurphyExerciseItem(
                    id = "u68_ex4",
                    exerciseNumber = "68.4",
                    taskType = "CHOICE",
                    question = "Jack had an accident and was taken to ______.",
                    options = listOf("hospital", "the hospital", "a hospital", "hospitals"),
                    correctOptionIndex = 0,
                    correctAnswerText = "hospital",
                    explanationUzbek = "Bemor sifatida davolanishga yotqizilish 'taken to hospital' deb artiklsiz aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u68_ex5",
                    exerciseNumber = "68.5",
                    taskType = "CHOICE",
                    question = "I must go to ______ bank to deposit this cheque.",
                    options = listOf("the", "a", "(no article)", "an"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the",
                    explanationUzbek = "Xizmat ko'rsatish muassasalariga borishda 'to the bank' ishlatiladi."
                )
            )
        )
    )
}
