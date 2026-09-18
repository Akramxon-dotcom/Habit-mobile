package com.example.data.model

data class MurphyExample(
    val english: String,
    val uzbek: String,
    val note: String? = null
)

data class MurphySection(
    val sectionCode: String, // "A", "B", "C", "D"
    val heading: String,
    val formula: String? = null,
    val explanationUzbek: String,
    val examples: List<MurphyExample>
)

data class MurphyExerciseItem(
    val id: String,
    val exerciseNumber: String, // e.g. "1.1", "1.2", "5.3"
    val taskType: String, // "CHOICE", "FILL", "ORDER"
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val correctAnswerText: String,
    val explanationUzbek: String
)

data class MurphyUnit(
    val unitNumber: Int,
    val title: String,
    val subtitleUzbek: String,
    val groupName: String = "Present (Hozirgi zamon)",
    val keyTakeawaysUzbek: List<String>,
    val sections: List<MurphySection>,
    val exercises: List<MurphyExerciseItem>
)

object MurphyBookDatabase {

    val UNITS_PAST: List<MurphyUnit> get() = MurphyBookPastDatabase.UNITS_PAST
    val UNITS_PRESENT_PERFECT: List<MurphyUnit> get() = MurphyBookPresentPerfectDatabase.UNITS_PRESENT_PERFECT
    val UNITS_PASSIVE: List<MurphyUnit> get() = MurphyBookPassiveDatabase.UNITS_PASSIVE
    val UNITS_FUTURE: List<MurphyUnit> get() = MurphyBookFutureDatabase.UNITS_FUTURE
    val UNITS_MODALS: List<MurphyUnit> get() = MurphyBookModalsDatabase.UNITS_MODALS
    val UNITS_PRONOUNS: List<MurphyUnit> get() = MurphyBookPronounsData.UNITS_PRONOUNS
    val UNITS_REPORTED_SPEECH: List<MurphyUnit> get() = MurphyBookReportedSpeechData.UNITS_REPORTED_SPEECH
    val UNITS_VERB_STRUCTURES: List<MurphyUnit> get() = MurphyBookVerbStructuresData.UNITS_VERB_STRUCTURES
    val UNITS_GO_GET_DO: List<MurphyUnit> get() = MurphyBookGoGetDoData.UNITS_GO_GET_DO
    val UNITS_ARTICLES_NOUNS: List<MurphyUnit> get() = MurphyBookArticlesNounsData.UNITS_ARTICLES_NOUNS
    val UNITS_PRONOUNS_DETERMINERS: List<MurphyUnit> get() = MurphyBookPronounsDeterminersData.UNITS_PRONOUNS_DETERMINERS
    val UNITS_ADJECTIVES_ADVERBS: List<MurphyUnit> get() = MurphyBookAdjectivesAdverbsData.UNITS_ADJECTIVES_ADVERBS
    val UNITS_WORD_ORDER_PREPOSITIONS: List<MurphyUnit> get() = MurphyBookWordOrderPrepositionsData.UNITS_WORD_ORDER_PREPOSITIONS
    val UNITS_CONJUNCTIONS_CLAUSES: List<MurphyUnit> get() = MurphyBookConjunctionsClausesData.UNITS_CONJUNCTIONS_CLAUSES
    val UNITS_RELATIVE_CLAUSES_PHRASALS: List<MurphyUnit> get() = MurphyBookRelativeClausesPhrasalsData.UNITS_RELATIVE_CLAUSES_PHRASALS
    val UNITS_ADVANCED_PHRASALS: List<MurphyUnit> get() = MurphyBookAdvancedPhrasalsAppendicesData.UNITS_ADVANCED_PHRASALS
    val UNITS_APPENDICES: List<MurphyUnit> get() = MurphyBookAppendicesData.UNITS_APPENDICES
    val UNITS_FINAL_COLLECTION: List<MurphyUnit> get() = MurphyBookFinalUnitsData.UNITS_FINAL_COLLECTION
    val ALL_UNITS: List<MurphyUnit> get() = UNITS_PRESENT + UNITS_PAST + UNITS_PRESENT_PERFECT + UNITS_PASSIVE + UNITS_FUTURE + UNITS_MODALS + UNITS_PRONOUNS + UNITS_REPORTED_SPEECH + UNITS_VERB_STRUCTURES + UNITS_GO_GET_DO + UNITS_ARTICLES_NOUNS + UNITS_PRONOUNS_DETERMINERS + UNITS_ADJECTIVES_ADVERBS + UNITS_WORD_ORDER_PREPOSITIONS + UNITS_CONJUNCTIONS_CLAUSES + UNITS_RELATIVE_CLAUSES_PHRASALS + UNITS_ADVANCED_PHRASALS + UNITS_APPENDICES + UNITS_FINAL_COLLECTION

    val UNITS_PRESENT: List<MurphyUnit> = listOf(
        // UNIT 1
        MurphyUnit(
            unitNumber = 1,
            title = "am / is / are",
            subtitleUzbek = "'To be' fe'lining hozirgi zamon darak va inkor shakllari",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "I bilan 'am', he/she/it bilan 'is', we/you/they bilan 'are' ishlatiladi.",
                "Inkor shaklida fe'ldan keyin 'not' qo'shiladi: I'm not, he isn't, they aren't.",
                "Yosh (I am 22), holat (I am tired, cold, hungry) va kasblar (He is a doctor) 'to be' bilan aytiladi.",
                "Qisqartmalar: that's = that is, there's = there is, here's = here is."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "O'zini tanishtirish va asosiy misollar (Lisa misoli)",
                    formula = "S (Ega) + am / is / are + Ot / Sifat",
                    explanationUzbek = "Ingliz tilida shaxs, uning yoshi, kasbi, millati yoki jismoniy holatini aytish uchun 'to be' (am/is/are) fe'li shart. O'zbek tilida 'men talabaman' desak bo'ladi, lekin ingliz tilida 'I am a student' deyish majburiy!",
                    examples = listOf(
                        MurphyExample("My name is Lisa.", "Mening ismim Liza."),
                        MurphyExample("I'm 22 years old.", "Men 22 yoshdaman.", "Yosh aytilganda 'am/is/are' ishlatiladi, 'have' emas!"),
                        MurphyExample("I'm American. I'm from Chicago.", "Men amerikalikman. Chikagodanman."),
                        MurphyExample("I'm a student.", "Men talabaman.", "Birlikdagi kasblar oldidan 'a/an' artikli qo'yiladi."),
                        MurphyExample("My father is a doctor and my mother is a journalist.", "Otam shifokor, onam esa jurnalist."),
                        MurphyExample("My favourite colour is blue.", "Mening sevimli rangim — ko'k."),
                        MurphyExample("My favourite sports are football and swimming.", "Mening sevimli sportlarim — futbol va suzish.", "'Sports' ko'plikda bo'lgani uchun 'are'."),
                        MurphyExample("I'm interested in art.", "Men san'atga qiziqaman.", "'interested in' bilan 'am/is/are' ishlatiladi.")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Ijobiy (Positive) va Inkor (Negative) shakllari",
                    formula = "Ijobiy: I am (I'm) | He/She/It is (He's) | We/You/They are (We're)\nInkor: I'm not | He/She/It isn't (He's not) | We/You/They aren't (We're not)",
                    explanationUzbek = "Og'zaki va norasmiy ingliz tilida qisqartma (short forms) shakllari ko'p qo'llaniladi. Inkor uchun 'is not = isn't' yoki 'are not = aren't' bo'ladi. 'I am not' esa faqat 'I'm not' deb qisqaradi.",
                    examples = listOf(
                        MurphyExample("I'm cold. Can you close the window, please?", "Menga sovuq bo'lyapti. Iltimos, derazani yopa olasizmi?", "Sovuq qotganda 'I am cold' deyiladi."),
                        MurphyExample("Steve is ill. He's in bed.", "Stiv kasal. U yotoqda yotibdi."),
                        MurphyExample("My brother is scared of dogs.", "Akam itlardan qo'rqadi.", "'scared of' — biror narsadan qo'rqmoq."),
                        MurphyExample("It's ten o'clock. You're late again.", "Soat 10 bo'ldi. Yana kechikdingiz."),
                        MurphyExample("Ann and I are good friends.", "Anna va men qadrdon do'stlarmiz.", "'Ann and I' ko'plik (Biz = We), shuning uchun 'are'."),
                        MurphyExample("Your keys are on the table.", "Kalitlaringiz stol ustida.", "'Keys' ko'plik bo'lgani uchun 'are'."),
                        MurphyExample("I'm tired, but I'm not hungry.", "Charchadim, lekin qornim och emas."),
                        MurphyExample("Lisa isn't interested in politics. She's interested in art.", "Liza siyosatga qiziqmaydi. U san'atga qiziqadi."),
                        MurphyExample("Those people aren't English. They're Australian.", "Anavi odamlar ingliz emas. Ular avstraliyalik.")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Ko'rsatish olmoshlari bilan qisqartmalar",
                    formula = "that's = that is | there's = there is | here's = here is",
                    explanationUzbek = "Kundalik muloqotda 'that's', 'there's' va 'here's' kabi tayyor birikmalar juda faol qo'llaniladi.",
                    examples = listOf(
                        MurphyExample("Thank you. That's very kind of you.", "Rahmat. Juda mehribonsiz (bu siz tomondan katta iltifot)."),
                        MurphyExample("Look! There's Chris.", "Qarang! Anavi yerda Kris turibdi."),
                        MurphyExample("'Here's your key.' 'Thank you.'", "'Mana sizning kalitingiz.' 'Rahmat.'")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u1_ex1",
                    exerciseNumber = "1.1",
                    taskType = "CHOICE",
                    question = "Qisqartma shaklini tanlang: 'They are'",
                    options = listOf("They're", "They's", "Their", "There"),
                    correctOptionIndex = 0,
                    correctAnswerText = "They're",
                    explanationUzbek = "'They are' ning qisqartirilgan to'g'ri shakli 'They're' hisoblanadi."
                ),
                MurphyExerciseItem(
                    id = "u1_ex2",
                    exerciseNumber = "1.2",
                    taskType = "CHOICE",
                    question = "The weather ______ nice today.",
                    options = listOf("am", "is", "are", "be"),
                    correctOptionIndex = 1,
                    correctAnswerText = "is",
                    explanationUzbek = "'The weather' (ob-havo) birlikda (it), shuning uchun 'is' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u1_ex3",
                    exerciseNumber = "1.2",
                    taskType = "CHOICE",
                    question = "My brother and I ______ good tennis players.",
                    options = listOf("am", "is", "are", "be"),
                    correctOptionIndex = 2,
                    correctAnswerText = "are",
                    explanationUzbek = "'My brother and I' = 'We' (Biz). Ikki kishi ko'plik bo'lgani sababli 'are' bo'ladi ('am' emas!)."
                ),
                MurphyExerciseItem(
                    id = "u1_ex4",
                    exerciseNumber = "1.3",
                    taskType = "CHOICE",
                    question = "These chairs aren't beautiful, but ______ comfortable.",
                    options = listOf("it is", "they're", "there are", "he is"),
                    correctOptionIndex = 1,
                    correctAnswerText = "they're",
                    explanationUzbek = "'These chairs' (stullar) ko'plikda, ikkinchi gapda ularga 'they're' (they are) olmoshi bilan javob beriladi."
                ),
                MurphyExerciseItem(
                    id = "u1_ex5",
                    exerciseNumber = "1.6",
                    taskType = "CHOICE",
                    question = "Qaysi jumla to'g'ri inkor shaklda tuzilgan?",
                    options = listOf("I am not hungry.", "I not am hungry.", "I isn't hungry.", "I am no hungry."),
                    correctOptionIndex = 0,
                    correctAnswerText = "I am not hungry.",
                    explanationUzbek = "'I' olmoshi bilan faqat 'am not' (I'm not) ishlatiladi."
                )
            )
        ),

        // UNIT 2
        MurphyUnit(
            unitNumber = 2,
            title = "am / is / are (questions)",
            subtitleUzbek = "'To be' fe'li bilan umumiy va maxsus so'roq gaplar, qisqa javoblar",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "So'roq gapda 'am/is/are' egadan (subject) oldinga chiqadi: Is he? Are you?",
                "Ega ot bilan ifodalanganda ham fe'l oldinga chiqadi: 'Is your mother at home?' ('Is at home your mother?' xato!).",
                "Maxsus so'roq so'zlari (Where, What, Who, How, Why) eng boshda keladi.",
                "Qisqa tasdiq javobda qisqartma ishlatilmaydi: 'Yes, I am' ('Yes, I'm' xato!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "So'roq shakli: Fe'lning egadan oldinga o'tishi",
                    formula = "Am I ... ? | Is he / she / it ... ? | Are we / you / they ... ?",
                    explanationUzbek = "Darak gapda 'You are late' bo'lsa, so'roqda 'Are you late?' bo'ladi. Hech qanday 'do/does' kerak emas, to be ning o'zi oldinga chiqadi.",
                    examples = listOf(
                        MurphyExample("'Am I late?' 'No, you're on time.'", "'Men kechikdimmi?' 'Yo'q, vaqtida keldingiz.'"),
                        MurphyExample("'Is your mother at home?' 'No, she's out.'", "'Onangiz uydami?' 'Yo'q, ko'chada/ishda.'", "E'tibor bering: 'Is your mother at home?' to'g'ri, 'Is at home your mother?' xato!"),
                        MurphyExample("'Are your parents at home?' 'No, they're out.'", "'Ota-onangiz uydami?' 'Yo'q, ular ko'chada.'"),
                        MurphyExample("'Is it cold in your room?' 'Yes, a little.'", "'Xonangiz sovuqmi?' 'Ha, bir oz.'"),
                        MurphyExample("Your shoes are nice. Are they new?", "Oyoq kiyimlaringiz chiroyli ekan. Yangimi?")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Maxsus so'roq so'zlari (Where / What / Who / How / Why)",
                    formula = "Wh-so'z + am / is / are + Ega ... ?",
                    explanationUzbek = "Where (Qayerda), What (Nima), Who (Kim), How (Qanday), Why (Nega) so'zlari gapning eng boshida turadi, undan keyin darhol 'am/is/are' keladi.",
                    examples = listOf(
                        MurphyExample("Where is your mother? Is she at home?", "Onangiz qayerda? Uydami?"),
                        MurphyExample("'Where are you from?' 'Canada.'", "'Qayerdansiz?' 'Kanadadan.'"),
                        MurphyExample("'What colour is your car?' 'It's red.'", "'Mashinangizning rangi qanaqa?' 'Qizil.'"),
                        MurphyExample("'How old is Joe?' 'He's 24.'", "'Joning yoshi nechada?' 'U 24 yoshda.'"),
                        MurphyExample("How are your parents? Are they well?", "Ota-onangiz qanday? Yaxshimi?"),
                        MurphyExample("These shoes are nice. How much are they?", "Bu poyabzallar ajoyib. Narxi qancha?"),
                        MurphyExample("This hotel isn't very good. Why is it so expensive?", "Bu mehmonxona unchalik yaxshi emas. Nega u bunchalik qimmat?")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Qisqa javoblar (Short answers)",
                    formula = "Ha: Yes, I am. / Yes, he is. / Yes, they are. (Qisqartma MUMKIN EMAS)\nYo'q: No, I'm not. / No, he isn't. / No, they aren't.",
                    explanationUzbek = "Ingliz tilida 'Yes' yoki 'No' deb quruq aytish qo'pol hisoblanadi. Tasdiqda 'Yes, I am' deyiladi (hech qachon 'Yes, I'm' deb qisqartirilmaydi). Inkorda esa 'No, I'm not' yoki 'No, he isn't' bo'ladi.",
                    examples = listOf(
                        MurphyExample("'Are you tired?' 'Yes, I am.'", "'Charchadingizmi?' 'Ha.'", "Hech qachon 'Yes, I'm' deyilmaydi!"),
                        MurphyExample("'Are you hungry?' 'No, I'm not, but I'm thirsty.'", "'Qorningiz ochmi?' 'Yo'q, lekin chanqadim.'"),
                        MurphyExample("'Is your friend English?' 'Yes, he is.'", "'Do'stingiz inglizmi?' 'Ha.'"),
                        MurphyExample("'Are these your keys?' 'Yes, they are.'", "'Bular sizning kalitlaringizmi?' 'Ha.'"),
                        MurphyExample("'That's my seat.' 'No, it isn't.'", "'Bu mening o'rnim.' 'Yo'q, unday emas.'")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u2_ex1",
                    exerciseNumber = "2.2",
                    taskType = "ORDER",
                    question = "So'zlarni to'g'ri tartibda tizing: (is / at home / your mother)",
                    options = listOf("Is at home your mother?", "Is your mother at home?", "Your mother is at home?", "At home is your mother?"),
                    correctOptionIndex = 1,
                    correctAnswerText = "Is your mother at home?",
                    explanationUzbek = "Qoida: Is + Ega (your mother) + qolgan qism (at home)?"
                ),
                MurphyExerciseItem(
                    id = "u2_ex2",
                    exerciseNumber = "2.3",
                    taskType = "CHOICE",
                    question = "'______ are these oranges?' '£1.50 a kilo.'",
                    options = listOf("How many", "How much", "What", "Where"),
                    correctOptionIndex = 1,
                    correctAnswerText = "How much",
                    explanationUzbek = "Narx so'ralganda har doim 'How much' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u2_ex3",
                    exerciseNumber = "2.4",
                    taskType = "CHOICE",
                    question = "Paul haqidagi so'roqqa to'g'ri savol shakli: (how old?)",
                    options = listOf("How old is he?", "How old he is?", "How is he old?", "How old are he?"),
                    correctOptionIndex = 0,
                    correctAnswerText = "How old is he?",
                    explanationUzbek = "'How old' so'zidan keyin yordamchi fe'l 'is', keyin ega 'he' keladi."
                ),
                MurphyExerciseItem(
                    id = "u2_ex4",
                    exerciseNumber = "2.5",
                    taskType = "CHOICE",
                    question = "'Are you tired?' savoliga to'g'ri qisqa tasdiq javob qaysi?",
                    options = listOf("Yes, I'm.", "Yes, I am.", "Yes, I do.", "Yes, you are."),
                    correctOptionIndex = 1,
                    correctAnswerText = "Yes, I am.",
                    explanationUzbek = "Qisqa tasdiq javobda 'Yes, I am' to'liq yoziladi va aytiladi, 'Yes, I'm' xato."
                ),
                MurphyExerciseItem(
                    id = "u2_ex5",
                    exerciseNumber = "2.2",
                    taskType = "CHOICE",
                    question = "(you / are / late / why) — to'g'ri so'roq gapni toping:",
                    options = listOf("Why you are late?", "Why are you late?", "Why late are you?", "Are why you late?"),
                    correctOptionIndex = 1,
                    correctAnswerText = "Why are you late?",
                    explanationUzbek = "Maxsus so'roq gapi tartibi: Why + are + you + late?"
                )
            )
        ),

        // UNIT 3
        MurphyUnit(
            unitNumber = 3,
            title = "I am doing (present continuous)",
            subtitleUzbek = "Hozirgi davomli zamon — aynan gapirayotgan paytda sodir bo'layotgan harakatlar",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "Present Continuous formulasi: am/is/are + fe'l-ing.",
                "Bu zamon aynan hozir (now, at the moment, look, listen) davom etayotgan ish-harakatlar uchun ishlatiladi.",
                "Harakat o'tmishda boshlangan, ayni paytda davom etmoqda va hali tugallanmagan.",
                "Imlo qoidalari: come -> coming, write -> writing, run -> running, sit -> sitting, swim -> swimming, lie -> lying."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Present Continuous formulasi va tuzilishi",
                    formula = "I + am (not) + V-ing\nHe / She / It + is (not) + V-ing\nWe / You / They + are (not) + V-ing",
                    explanationUzbek = "Present Continuous — 'am/is/are' yordamchi fe'li va asosiy fe'lga '-ing' qo'shimchasini qo'shish orqali yasaladi. Inkor gapda 'not' qo'shiladi.",
                    examples = listOf(
                        MurphyExample("She's eating an apple. She isn't reading.", "U olma yemoqda. U o'qimayapti."),
                        MurphyExample("It's raining. The sun isn't shining.", "Yomg'ir yog'moqda. Quyosh charaqlamayapti."),
                        MurphyExample("They're running. They aren't walking.", "Ular yugurishmoqda. Ular piyoda yurishmayapti."),
                        MurphyExample("I'm working. I'm not watching TV.", "Men ishlayapman. Men televizor ko'rmayapman."),
                        MurphyExample("Maria is reading a newspaper.", "Mariya gazeta o'qiyapti."),
                        MurphyExample("The bus is coming.", "Avtobus kelyapti."),
                        MurphyExample("We're having dinner.", "Biz kechki ovqatni yemoqdamiz.")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Qachon ishlatiladi? (Hozir sodir bo'layotgan ishlar)",
                    formula = "past -------- [ NOW (ayni paytda) ] -------- future",
                    explanationUzbek = "Harakat ayni paytda sodir bo'lmoqda. Gapiruvchi odam gapirayotgan sekundlarda bu ish davom etayotgan bo'ladi.",
                    examples = listOf(
                        MurphyExample("Please be quiet. I'm working.", "Iltimos, tinchroq bo'ling. Men ishlayapman.", "'I work' emas, aynan hozir ishlayotganim uchun 'I'm working'."),
                        MurphyExample("Look, there's Sarah. She's wearing a brown coat.", "Qarang, anavi Sara. U jigarrang palto kiyib olgan.", "Hozir uning egnida palto bor."),
                        MurphyExample("The weather is nice. It's not raining.", "Ob-havo ajoyib. Yomg'ir yog'mayapti."),
                        MurphyExample("'Where are the children?' 'They're playing in the park.'", "'Bolalar qayerda?' 'Ular parkda o'ynashyapti.'"),
                        MurphyExample("(on the phone) We're having dinner now. Can I call you later?", "(telefonda) Hozir ovqatlanyapmiz. Keyinroq telefon qilsam bo'ladimi?"),
                        MurphyExample("You can turn off the television. I'm not watching it.", "Televizorni o'chirishingiz mumkin. Men uni ko'rmayapman.")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Fe'llarga -ing qo'shish imlo (spelling) qoidalari",
                    formula = "-e bilan tugasa: -e tushib qoladi (make -> making, write -> writing)\nQisqa unli + undosh: undosh ikkilanadi (run -> running, sit -> sitting, swim -> swimming)\n-ie bilan tugasa: -y ga aylanadi (lie -> lying, die -> dying)",
                    explanationUzbek = "1. Agar fe'l oxiri tovushsiz 'e' bilan tugasa, 'e' harfi tushib qoladi: dance -> dancing.\n2. Agar bir bo'g'inli fe'l oxiri bitta unli va bitta undosh bilan tugasa, oxirgi harf ikkilanadi: get -> getting, stop -> stopping.\n3. 'lie' (yotmoq/aldamoq) fe'li 'lying' bo'ladi.",
                    examples = listOf(
                        MurphyExample("come -> coming", "kelmoq -> kelayotgan"),
                        MurphyExample("write -> writing", "yozmoq -> yozayotgan"),
                        MurphyExample("swim -> swimming", "suzmoq -> suzayotgan"),
                        MurphyExample("run -> running", "yugurmoq -> yugurayotgan"),
                        MurphyExample("lie -> lying", "yotmoq -> yotgan")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u3_ex1",
                    exerciseNumber = "3.2",
                    taskType = "CHOICE",
                    question = "Please be quiet. I ______ (work).",
                    options = listOf("work", "'m working", "working", "works"),
                    correctOptionIndex = 1,
                    correctAnswerText = "'m working",
                    explanationUzbek = "Aynan hozir bo'layotgan harakat uchun 'I'm working' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u3_ex2",
                    exerciseNumber = "3.2",
                    taskType = "CHOICE",
                    question = "'Where's Sue?' 'She ______ a shower.'",
                    options = listOf("has", "is having", "having", "is have"),
                    correctOptionIndex = 1,
                    correctAnswerText = "is having",
                    explanationUzbek = "'Hozir dush qabul qilyapti': She + is having."
                ),
                MurphyExerciseItem(
                    id = "u3_ex3",
                    exerciseNumber = "3.3",
                    taskType = "CHOICE",
                    question = "Qaysi fe'lning -ing shakli to'g'ri yozilgan?",
                    options = listOf("runing", "swiming", "swimming", "writeing"),
                    correctOptionIndex = 2,
                    correctAnswerText = "swimming",
                    explanationUzbek = "'swim' so'zida qisqa unlidan keyin 'm' ikkilanadi: 'swimming'."
                ),
                MurphyExerciseItem(
                    id = "u3_ex4",
                    exerciseNumber = "3.4",
                    taskType = "CHOICE",
                    question = "Look! The dog ______ across the river.",
                    options = listOf("swims", "is swimming", "are swimming", "swimming"),
                    correctOptionIndex = 1,
                    correctAnswerText = "is swimming",
                    explanationUzbek = "'The dog' birlik (it), hozir suzmoqda: 'is swimming'."
                ),
                MurphyExerciseItem(
                    id = "u3_ex5",
                    exerciseNumber = "3.1",
                    taskType = "CHOICE",
                    question = "They ______ football in the garden right now.",
                    options = listOf("plays", "are playing", "is playing", "play"),
                    correctOptionIndex = 1,
                    correctAnswerText = "are playing",
                    explanationUzbek = "'They' bilan 'are playing' ishlatiladi."
                )
            )
        ),

        // UNIT 4
        MurphyUnit(
            unitNumber = 4,
            title = "are you doing? (present continuous questions)",
            subtitleUzbek = "Hozirgi davomli zamon so'roq shakli va so'z tartibi",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "So'roq tuzilishi: am/is/are + Ega (subject) + V-ing: 'Are you feeling OK?'",
                "Ega ot bo'lsa ham tartib o'zgarmaydi: 'Is Ben working today?' ('Is working Ben today?' xato!).",
                "Wh- savollarda: Where / What / Why + is/are + Ega + V-ing.",
                "Qisqa javoblar: 'Yes, I am.' / 'No, I'm not.' / 'Yes, he is.' / 'No, he isn't.'"
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Darak va So'roq shakllari qiyosi",
                    formula = "Darak: You are working -> So'roq: Are you working?\nDarak: He is going -> So'roq: Is he going?",
                    explanationUzbek = "Present Continuous da so'roq yasash juda oson: 'am/is/are' egadan oldinga o'tadi, fe'lning '-ing' shakli o'z joyida qoladi.",
                    examples = listOf(
                        MurphyExample("'Are you feeling OK?' 'Yes, I'm fine, thanks.'", "'O'zingizni yaxshi his qilyapsizmi?' 'Ha, rahmat, yaxshiman.'"),
                        MurphyExample("'Is it raining?' 'Yes, take an umbrella.'", "'Yomg'ir yog'yaptimi?' 'Ha, soyabon oling.'"),
                        MurphyExample("Why are you wearing a coat? It's not cold.", "Nega palto kiyib oldingiz? Havo sovuq emas-ku."),
                        MurphyExample("'What's Paul doing?' 'He's studying for his exams.'", "'Pol nima qilyapti?' 'U imtihonlariga tayyorlanyapti.'"),
                        MurphyExample("'What are the children doing?' 'They're watching TV.'", "'Bolalar nima qilishyapti?' 'Ular televizor ko'rishyapti.'"),
                        MurphyExample("Look, there's Emily! Where's she going?", "Qarang, anavi Emili! U qayerga ketyapti?"),
                        MurphyExample("Who are you waiting for? Are you waiting for Sue?", "Kimni kutyapsiz?uni kutyapsizmi?")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "So'z tartibiga alohida e'tibor bering!",
                    formula = "is/are + Ega (Subject) + -ing\nWh-so'z + is/are + Ega + -ing",
                    explanationUzbek = "Ega ism yoki birikma bo'lsa (Ben, those people), ko'pchilik xato qilib 'is working Ben' deb qo'yadi. Aslida ega doimo 'is/are' bilan '-ing' orasida turadi!",
                    examples = listOf(
                        MurphyExample("Is Ben working today?", "Bugun Ben ishlayaptimi?", "NOT: Is working Ben today? (XATO)"),
                        MurphyExample("Where are those people going?", "Anavi odamlar qayerga ketishyapti?", "NOT: Where are going those people? (XATO)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Qisqa javoblar (Short answers)",
                    formula = "Yes, I am. / Yes, he is. / Yes, they are.\nNo, I'm not. / No, he isn't. / No, they aren't.",
                    explanationUzbek = "Qisqa javoblarda asosiy fe'l takrorlanmaydi, faqat yordamchi fe'l qoladi.",
                    examples = listOf(
                        MurphyExample("'Are you going now?' 'Yes, I am.'", "'Hozir ketyapsizmi?' 'Ha.'"),
                        MurphyExample("'Is Ben working today?' 'Yes, he is.'", "'Ben bugun ishlayaptimi?' 'Ha.'"),
                        MurphyExample("'Is it raining?' 'No, it isn't.'", "'Yomg'ir yog'yaptimi?' 'Yo'q.'"),
                        MurphyExample("'Are your friends staying at a hotel?' 'No, they aren't.'", "'Do'stlaringiz mehmonxonada turishibdimi?' 'Yo'q.'")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u4_ex1",
                    exerciseNumber = "4.1",
                    taskType = "CHOICE",
                    question = "'______ you watching TV?' 'No, you can turn it off.'",
                    options = listOf("Do", "Are", "Is", "Have"),
                    correctOptionIndex = 1,
                    correctAnswerText = "Are",
                    explanationUzbek = "'watching' (-ing) bilan 'Are you' ishlatiladi ('Do you' bilan oddiy fe'l keladi)."
                ),
                MurphyExerciseItem(
                    id = "u4_ex2",
                    exerciseNumber = "4.3",
                    taskType = "ORDER",
                    question = "To'g'ri so'z tartibini toping: (what / the children / are / doing)",
                    options = listOf("What the children are doing?", "What are doing the children?", "What are the children doing?", "Are what the children doing?"),
                    correctOptionIndex = 2,
                    correctAnswerText = "What are the children doing?",
                    explanationUzbek = "Tartib: What + are + the children (ega) + doing (-ing)?"
                ),
                MurphyExerciseItem(
                    id = "u4_ex3",
                    exerciseNumber = "4.3",
                    taskType = "CHOICE",
                    question = "Qaysi jumla grammatik jihatdan to'g'ri?",
                    options = listOf("Is working Ben today?", "Is Ben working today?", "Does Ben working today?", "Ben is working today?"),
                    correctOptionIndex = 1,
                    correctAnswerText = "Is Ben working today?",
                    explanationUzbek = "Ega (Ben) 'is' va 'working' orasida turadi: 'Is Ben working today?'."
                ),
                MurphyExerciseItem(
                    id = "u4_ex4",
                    exerciseNumber = "4.2",
                    taskType = "CHOICE",
                    question = "Where ______ she going?",
                    options = listOf("is", "are", "does", "do"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is",
                    explanationUzbek = "'she' olmoshi uchun 'is' ishlatiladi: 'Where is she going?'."
                ),
                MurphyExerciseItem(
                    id = "u4_ex5",
                    exerciseNumber = "4.4",
                    taskType = "CHOICE",
                    question = "'Is it raining?' savoliga to'g'ri inkor qisqa javob:",
                    options = listOf("No, it doesn't.", "No, it isn't.", "No, it not.", "No, it hasn't."),
                    correctOptionIndex = 1,
                    correctAnswerText = "No, it isn't.",
                    explanationUzbek = "'Is it' savoliga inkor javob: 'No, it isn't'."
                )
            )
        ),

        // UNIT 5
        MurphyUnit(
            unitNumber = 5,
            title = "I do/work/like etc. (present simple)",
            subtitleUzbek = "Oddiy hozirgi zamon — doimiy odatlar, haqiqatlar va takrorlanuvchi harakatlar",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "I/we/you/they bilan fe'l o'zgarmaydi (read, work, like).",
                "He/she/it bilan fe'lga -s yoki -es qo'shiladi (reads, works, likes, passes, watches).",
                "Imlo: -s/-sh/-ch dan keyin -es (passes, finishes, watches); -y dan oldin undosh bo'lsa -ies (studies, tries); go -> goes, do -> does, have -> has.",
                "Tabiat qonunlari, doimiy haqiqatlar va chastota ravishlari (always, usually, often, sometimes, never) bilan ishlatiladi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Present Simple nima va qanday yasaladi?",
                    formula = "I / We / You / They + V1 (read, like, work, live)\nHe / She / It + V1-s/es (reads, likes, works, lives)",
                    explanationUzbek = "Present Simple doimiy takrorlanadigan ish-harakatlar yoki umumiy haqiqatlar uchun qo'llaniladi. He/she/it da fe'lga albatta '-s' yoki '-es' qo'shiladi!",
                    examples = listOf(
                        MurphyExample("They have a lot of books. They read a lot.", "Ularda ko'p kitob bor. Ular ko'p o'qishadi."),
                        MurphyExample("He's eating an ice cream. He likes ice cream.", "U muzqaymoq yemoqda (Continuous). U muzqaymoqni yoqtiradi (Simple — doimiy)."),
                        MurphyExample("I work in a shop. My brother works in a bank.", "Men do'konda ishlayman. Akam bankda ishlaydi.", "'My brother' = he bo'lgani uchun 'works'."),
                        MurphyExample("Lucy lives in London. Her parents live in Scotland.", "Lyusi Londonda yashaydi. Uning ota-onasi Shotlandiyada yashaydi."),
                        MurphyExample("It rains a lot in winter.", "Qishda ko'p yomg'ir yog'adi.", "'It rains' — '-s' qo'shildi."),
                        MurphyExample("Joe has a shower every day.", "Jo har kuni dush qabul qiladi.", "'have' he/she/it da 'has' bo'ladi.")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Qachon ishlatiladi? (Umumiy haqiqatlar va odatlar)",
                    formula = "True in general (Umumiy haqiqat) yoki Things that happen all the time / sometimes",
                    explanationUzbek = "Dunyodagi o'zgarmas haqiqatlar yoki hayot tarzingizdagi muntazam holatlar uchun ishlatiladi.",
                    examples = listOf(
                        MurphyExample("The earth goes round the sun.", "Yer quyosh atrofida aylanadi.", "O'zgarmas ilmiy haqiqat: 'goes'."),
                        MurphyExample("I like big cities.", "Menga katta shaharlar yoqadi."),
                        MurphyExample("Your English is good. You speak very well.", "Ingliz tilingiz yaxshi. Siz juda yaxshi gapirasiz."),
                        MurphyExample("Tom works very hard. He starts at 7.30 and finishes at 8.00 in the evening.", "Tom juda qattiq ishlaydi. U 7:30 da boshlab, kechki 8:00 da tugatadi."),
                        MurphyExample("It costs a lot of money to build a hospital.", "Kasalxona qurish juda ko'p pul turadi.")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Chastota ravishlari (always / usually / often / sometimes / never)",
                    formula = "Ega + always / usually / often / sometimes / never + Fe'l",
                    explanationUzbek = "Bu so'zlar asosiy fe'ldan OLDIN keladi: 'Sue always gets to work early' (Sue gets always xato!).",
                    examples = listOf(
                        MurphyExample("Sue always gets to work early.", "Syue har doim ishga erta keladi.", "NOT: Sue gets always early."),
                        MurphyExample("I never eat breakfast.", "Men hech qachon nonushta qilmayman.", "NOT: I eat never."),
                        MurphyExample("We often go away at weekends.", "Biz dam olish kunlari ko'pincha shahar tashqarisiga chiqamiz."),
                        MurphyExample("Mark usually plays football on Sundays.", "Mark odatda yakshanba kunlari futbol o'ynaydi."),
                        MurphyExample("I sometimes walk to work, but not very often.", "Men ba'zan ishga piyoda boraman, lekin juda tez-tez emas.")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u5_ex1",
                    exerciseNumber = "5.3",
                    taskType = "CHOICE",
                    question = "Maria speaks four languages, but her brother ______ only one.",
                    options = listOf("speak", "speaks", "speaking", "is speak"),
                    correctOptionIndex = 1,
                    correctAnswerText = "speaks",
                    explanationUzbek = "'Her brother' (u = he) birlik shaxs, fe'lga '-s' qo'shiladi: 'speaks'."
                ),
                MurphyExerciseItem(
                    id = "u5_ex2",
                    exerciseNumber = "5.3",
                    taskType = "CHOICE",
                    question = "Water ______ at 100 degrees Celsius.",
                    options = listOf("boil", "boils", "is boiling", "boiling"),
                    correctOptionIndex = 1,
                    correctAnswerText = "boils",
                    explanationUzbek = "Tabiat qonuni va ilmiy haqiqat: 'Water boils'."
                ),
                MurphyExerciseItem(
                    id = "u5_ex3",
                    exerciseNumber = "5.4",
                    taskType = "ORDER",
                    question = "To'g'ri gap tuzilishini toping: (always / early / Sue / arrive)",
                    options = listOf("Sue arrives always early.", "Sue always arrives early.", "Always Sue arrives early.", "Sue early always arrives."),
                    correctOptionIndex = 1,
                    correctAnswerText = "Sue always arrives early.",
                    explanationUzbek = "'always' ravishi fe'ldan (arrives) oldin turadi: 'Sue always arrives early'."
                ),
                MurphyExerciseItem(
                    id = "u5_ex4",
                    exerciseNumber = "5.1",
                    taskType = "CHOICE",
                    question = "'have' fe'lining he/she/it uchun to'g'ri shakli:",
                    options = listOf("haves", "have", "has", "having"),
                    correctOptionIndex = 2,
                    correctAnswerText = "has",
                    explanationUzbek = "'have' ning 3-shaxs birlikdagi shakli — 'has'."
                ),
                MurphyExerciseItem(
                    id = "u5_ex5",
                    exerciseNumber = "5.4",
                    taskType = "CHOICE",
                    question = "Children usually ______ chocolate.",
                    options = listOf("likes", "like", "liking", "are like"),
                    correctOptionIndex = 1,
                    correctAnswerText = "like",
                    explanationUzbek = "'Children' (bolalar) ko'plikda (they), shuning uchun fe'l '-s'siz bo'ladi: 'like'."
                )
            )
        ),

        // UNIT 6
        MurphyUnit(
            unitNumber = 6,
            title = "I don't... (present simple negative)",
            subtitleUzbek = "Oddiy hozirgi zamon inkor shakli — don't va doesn't qoidalari",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "Present Simple inkor formulasi: don't / doesn't + asosiy fe'lning 1-shakli.",
                "I / we / you / they bilan 'don't' (do not) ishlatiladi.",
                "He / she / it bilan 'doesn't' (does not) ishlatiladi.",
                "Eng muhim qoida: doesn't dan keyin fe'ldagi '-s' tushib qoladi! 'He doesn't like' ('He doesn't likes' QAT'IYAN XATO!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "don't va doesn't tuzilishi",
                    formula = "I / We / You / They + don't + Verb (work, like, do, have)\nHe / She / It + doesn't + Verb (work, like, do, have)",
                    explanationUzbek = "Ingliz tilida fe'lning o'ziga 'not' qo'shib bo'lmaydi (I like not xato). Yordamchi fe'l don't / doesn't yordamga keladi.",
                    examples = listOf(
                        MurphyExample("I drink coffee, but I don't drink tea.", "Men kofe ichaman, lekin choy ichmayman."),
                        MurphyExample("Sue drinks tea, but she doesn't drink coffee.", "Syue choy ichadi, lekin kofe ichmaydi.", "'doesn't drink' — fe'lda '-s' yo'q!"),
                        MurphyExample("You don't work very hard.", "Siz unchalik qattiq ishlamaysiz."),
                        MurphyExample("We don't watch TV very often.", "Biz televizorni uncha tez-tez ko'rmaymiz."),
                        MurphyExample("The weather is usually nice. It doesn't rain very often.", "Havo odatda yaxshi. Ko'p yomg'ir yog'maydi."),
                        MurphyExample("Sam and Chris don't know many people.", "Sem va Kris ko'p odamlarni tanishmaydi.")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Keng tarqalgan xatolar va eslatmalar",
                    formula = "he/she/it doesn't + V1 (bosh shakl)",
                    explanationUzbek = "Eslab qoling: 'doesn't' ning o'zida allaqachon '-es' bor. Shuning uchun undan keyingi asosiy fe'l sof bosh shaklida qoladi.",
                    examples = listOf(
                        MurphyExample("I don't like football.", "Menga futbol yoqmaydi."),
                        MurphyExample("He doesn't like football.", "Unga futbol yoqmaydi.", "NOT: He doesn't likes football."),
                        MurphyExample("My car doesn't use much petrol.", "Mashinam ko'p benzin sarflamaydi.", "NOT: My car don't use..."),
                        MurphyExample("Sometimes he is late, but it doesn't happen very often.", "Ba'zida u kechikadi, lekin bu juda tez-tez sodir bo'lmaydi.")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "'do' asosiy fe'l bo'lib kelganda",
                    formula = "don't do / doesn't do",
                    explanationUzbek = "Agar asosiy fe'l 'do' (qilmoq) bo'lsa, 'don't do' yoki 'doesn't do' shaklida ikkala 'do' ham yoziladi!",
                    examples = listOf(
                        MurphyExample("David doesn't do his job very well.", "Devid o'z ishini yaxshi bajarmaydi.", "NOT: David doesn't his job."),
                        MurphyExample("Paula doesn't usually have breakfast.", "Paula odatda nonushta qilmaydi.", "NOT: Paula doesn't has breakfast.")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u6_ex1",
                    exerciseNumber = "6.1",
                    taskType = "CHOICE",
                    question = "Anna plays the piano very well -> Inkor shaklini tanlang:",
                    options = listOf("Anna don't play the piano.", "Anna doesn't plays the piano.", "Anna doesn't play the piano very well.", "Anna isn't play the piano."),
                    correctOptionIndex = 2,
                    correctAnswerText = "Anna doesn't play the piano very well.",
                    explanationUzbek = "'Anna' uchun 'doesn't', va undan keyin 'play' (s-siz) bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u6_ex2",
                    exerciseNumber = "6.4",
                    taskType = "CHOICE",
                    question = "Paul has a car, but he ______ it very often.",
                    options = listOf("doesn't use", "don't use", "doesn't uses", "not use"),
                    correctOptionIndex = 0,
                    correctAnswerText = "doesn't use",
                    explanationUzbek = "'he' uchun 'doesn't use' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u6_ex3",
                    exerciseNumber = "6.5",
                    taskType = "CHOICE",
                    question = "Mark is a vegetarian. He ______ meat.",
                    options = listOf("don't eat", "doesn't eats", "doesn't eat", "isn't eat"),
                    correctOptionIndex = 2,
                    correctAnswerText = "doesn't eat",
                    explanationUzbek = "'Mark' (he) bo'lgani uchun 'doesn't eat'."
                ),
                MurphyExerciseItem(
                    id = "u6_ex4",
                    exerciseNumber = "6.5",
                    taskType = "CHOICE",
                    question = "'Where's Steve?' 'I'm sorry. I ______.'",
                    options = listOf("doesn't know", "don't know", "am not know", "not know"),
                    correctOptionIndex = 1,
                    correctAnswerText = "don't know",
                    explanationUzbek = "'I' olmoshi bilan 'don't know' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u6_ex5",
                    exerciseNumber = "6.4",
                    taskType = "CHOICE",
                    question = "The Regent Hotel isn't expensive. It ______ much to stay there.",
                    options = listOf("don't cost", "doesn't cost", "doesn't costs", "isn't cost"),
                    correctOptionIndex = 1,
                    correctAnswerText = "doesn't cost",
                    explanationUzbek = "'It' uchun 'doesn't cost' (cost fe'lida -s bo'lmaydi)."
                )
            )
        ),

        // UNIT 7
        MurphyUnit(
            unitNumber = 7,
            title = "Do you...? (present simple questions)",
            subtitleUzbek = "Oddiy hozirgi zamon so'roq shakli — Do / Does qoidalari",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "Present Simple so'roq shaklida: Do yoki Does egadan oldinga chiqadi.",
                "Do: I / we / you / they bilan | Does: he / she / it bilan.",
                "Does ishlatilganda asosiy fe'ldan '-s' tushib qoladi: 'Does Chris work on Sundays?'",
                "'What do you do?' iborasi 'Kasbingiz nima? / Nima ish qilasiz?' degan ma'noni anglatadi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Do / Does bilan so'roq gap tuzilishi",
                    formula = "Do + I / we / you / they + V1 (work, like, do, have)?\nDoes + he / she / it + V1 (work, like, do, have)?",
                    explanationUzbek = "Oddiy hozirgi zamonda umumiy savol berish uchun gap boshiga Do yoki Does qo'yiladi. He/she/it da Does kelganligi sababli, asosiy fe'l yana bosh holatiga qaytadi.",
                    examples = listOf(
                        MurphyExample("Do you play the guitar?", "Siz gitara chalasizmi?"),
                        MurphyExample("Do your friends live near here?", "Do'stlaringiz shu yaqinda yashashadimi?"),
                        MurphyExample("Does Chris work on Sundays?", "Kris yakshanba kunlari ishlaydimi?", "'Does' borligi uchun 'works' emas, 'work' bo'ladi!"),
                        MurphyExample("Does it rain a lot here?", "Bu yerda ko'p yomg'ir yog'adimi?"),
                        MurphyExample("Where do your parents live?", "Ota-onangiz qayerda yashashadi?"),
                        MurphyExample("How often do you wash your hair?", "Sochingizni qanchalik tez-tez yuvasiz?"),
                        MurphyExample("What does this word mean?", "Bu so'z nimani anglatadi?", "'this word' = it, shuning uchun 'does ... mean'."),
                        MurphyExample("How much does it cost to fly to Rome?", "Rimga uchish qancha turadi?")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "'What do you do?' maxsus savoli",
                    formula = "What do you do? = What is your job?",
                    explanationUzbek = "Bu savol ingliz tilida 'Hozir nima qilyapsan?' degani EMAS! Bu 'Sizning kasbingiz nima? Tirikchiligingiz nima?' degan ma'noni anglatadi.",
                    examples = listOf(
                        MurphyExample("'What do you do?' 'I work in a bank.'", "'Kasbingiz nima?' 'Bankda ishlayman.'"),
                        MurphyExample("'What does your brother do?' 'He's a teacher.'", "'Akangiz nima ish qiladi?' 'U o'qituvchi.'")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Qisqa javoblar (Short answers)",
                    formula = "Yes, I/we/you/they do. | Yes, he/she/it does.\nNo, I/we/you/they don't. | No, he/she/it doesn't.",
                    explanationUzbek = "Qisqa javobda do/does yoki don't/doesn't ishlatiladi. Asosiy fe'l takrorlanmaydi.",
                    examples = listOf(
                        MurphyExample("'Do you play the guitar?' 'No, I don't.'", "'Gitara chalasizmi?' 'Yo'q.'"),
                        MurphyExample("'Do your parents speak English?' 'Yes, they do.'", "'Ota-onangiz inglizcha gapirishadimi?' 'Ha.'"),
                        MurphyExample("'Does James work hard?' 'Yes, he does.'", "'Jeyms qattiq ishlaydimi?' 'Ha.'"),
                        MurphyExample("'Does your sister live in London?' 'No, she doesn't.'", "'Opangiz Londonda yashaydimi?' 'Yo'q.'")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u7_ex1",
                    exerciseNumber = "7.1",
                    taskType = "CHOICE",
                    question = "'Tom plays tennis. How about his friends?' — Do'stlari haqida savol tuzing:",
                    options = listOf("Does his friends play tennis?", "Do his friends play tennis?", "Are his friends play tennis?", "Do his friends plays tennis?"),
                    correctOptionIndex = 1,
                    correctAnswerText = "Do his friends play tennis?",
                    explanationUzbek = "'his friends' ko'plik (they) bo'lgani uchun 'Do' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u7_ex2",
                    exerciseNumber = "7.2",
                    taskType = "ORDER",
                    question = "So'zlarni to'g'ri tartibda tizing: (where / live / your parents)",
                    options = listOf("Where live your parents?", "Where do your parents live?", "Where does your parents live?", "Do your parents live where?"),
                    correctOptionIndex = 1,
                    correctAnswerText = "Where do your parents live?",
                    explanationUzbek = "Maxsus so'roq: Where + do + your parents (ega) + live?"
                ),
                MurphyExerciseItem(
                    id = "u7_ex3",
                    exerciseNumber = "7.2",
                    taskType = "CHOICE",
                    question = "What ______ this word mean?",
                    options = listOf("do", "does", "is", "are"),
                    correctOptionIndex = 1,
                    correctAnswerText = "does",
                    explanationUzbek = "'this word' birlik (it), shuning uchun 'does' kerak: 'What does this word mean?'."
                ),
                MurphyExerciseItem(
                    id = "u7_ex4",
                    exerciseNumber = "7.3",
                    taskType = "CHOICE",
                    question = "'What do you do?' savoliga eng to'g'ri javob:",
                    options = listOf("I am reading now.", "I work in a bookshop.", "I am playing tennis.", "I do my homework."),
                    correctOptionIndex = 1,
                    correctAnswerText = "I work in a bookshop.",
                    explanationUzbek = "'What do you do?' kasb/mashg'ulotni so'raydi ('I work in a bookshop')."
                ),
                MurphyExerciseItem(
                    id = "u7_ex5",
                    exerciseNumber = "7.4",
                    taskType = "CHOICE",
                    question = "'Does your sister speak English?' — to'g'ri qisqa tasdiq javob:",
                    options = listOf("Yes, she is.", "Yes, she speak.", "Yes, she does.", "Yes, she do."),
                    correctOptionIndex = 2,
                    correctAnswerText = "Yes, she does.",
                    explanationUzbek = "'Does she...?' so'roviga 'Yes, she does' deb javob beriladi."
                )
            )
        ),

        // UNIT 8
        MurphyUnit(
            unitNumber = 8,
            title = "I am doing vs I do",
            subtitleUzbek = "Present Continuous va Present Simple taqqoslanishi, holat fe'llari (Stative verbs)",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "Present Continuous (I am doing) = hozir, ayni gapirayotgan paytda sodir bo'layotgan jarayon.",
                "Present Simple (I do) = umuman olganda, doimiy yoki takrorlanib turadigan harakat.",
                "Holat fe'llari (Stative verbs) hech qachon -ing (continuous) olmaydi: like, want, know, understand, remember, prefer, need, mean, believe, forget.",
                "Masalan: 'I want to go home' to'g'ri ('I am wanting' XATO!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Jek va gitara misoli (Asosiy farq)",
                    formula = "Hozir chalmoqdami? -> No, he isn't (Continuous)\nUmuman chaladimi? -> Yes, he does (Simple)",
                    explanationUzbek = "Jek hozir televizor ko'ryapti. U hozir gitara chalmayapti (He is not playing). Lekin uning gitarasi bor va u gitara chalishni biladi, tez-tez chalib turadi (Jack plays the guitar). Bu ikki zamonning tub mohiyati shunda!",
                    examples = listOf(
                        MurphyExample("Jack is watching television. He is not playing the guitar.", "Jek televizor ko'ryapti. U gitara chalmayapti (hozir)."),
                        MurphyExample("Jack plays the guitar, but he is not playing the guitar now.", "Jek gitara chaladi (umuman), lekin u hozir gitara chalmayapti."),
                        MurphyExample("'Is he playing the guitar?' 'No, he isn't.'", "'U hozir gitara chalyaptimi?' 'Yo'q.'", "Present Continuous savoli"),
                        MurphyExample("'Does he play the guitar?' 'Yes, he does.'", "'U gitara chaladimi?' 'Ha.'", "Present Simple savoli")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Vaqt bo'yicha qiyoslash",
                    formula = "Continuous: now, at the moment, look! listen!\nSimple: in general, all the time, every day, usually, often",
                    explanationUzbek = "Vaqt signallariga qarang: agar gapda hozirgi fursat nazarda tutilsa Continuous, agar umumiy odat yoki rejim bo'lsa Simple bo'ladi.",
                    examples = listOf(
                        MurphyExample("Please be quiet. I'm working.", "Iltimos, tinchroq bo'ling. Men ishlayapman (aynan hozir)."),
                        MurphyExample("I work every day from 9 o'clock to 5.30.", "Men har kuni soat 9 dan 5:30 gacha ishlayman (doimiy rejim)."),
                        MurphyExample("Tom is having a shower at the moment.", "Tom ayni paytda dush qabul qilyapti."),
                        MurphyExample("Tom has a shower every morning.", "Tom har tong dush qabul qiladi."),
                        MurphyExample("Take an umbrella with you. It's raining.", "O'zingiz bilan soyabon oling. Yomg'ir yog'yapti (tashqarida hozir)."),
                        MurphyExample("It rains a lot in winter.", "Qishda ko'p yomg'ir yog'adi (umumiy iqlim).")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Continuous zamonda ISHLATILMAYDIGAN fe'llar (Stative Verbs)",
                    formula = "like, want, know, understand, remember, prefer, need, mean, believe, forget -> FAQAT PRESENT SIMPLE!",
                    explanationUzbek = "Bu fe'llar jismoniy harakatni emas, balki aqliy holat, his-tuyg'u yoki ehtiyojni bildiradi. Shuning uchun ayni paytda bo'lsa ham ularga '-ing' qo'shilmaydi!",
                    examples = listOf(
                        MurphyExample("I'm tired. I want to go home.", "Charchadim. Uyga ketishni xohlayman.", "NOT: I am wanting (QAT'IYAN XATO)"),
                        MurphyExample("'Do you know that girl?' 'Yes, but I don't remember her name.'", "'Anavi qizni taniysizmi?' 'Ha, lekin ismini eslolmayapman.'", "NOT: Are you knowing? I am not remembering."),
                        MurphyExample("I don't understand. What do you mean?", "Tushunmayapman. Nimani nazarda tutyapsiz?", "NOT: What are you meaning?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u8_ex1",
                    exerciseNumber = "8.2",
                    taskType = "CHOICE",
                    question = "Excuse me, ______ you speak English?",
                    options = listOf("are", "do", "does", "is"),
                    correctOptionIndex = 1,
                    correctAnswerText = "do",
                    explanationUzbek = "Til bilish doimiy qobiliyat, shuning uchun Present Simple: 'Do you speak English?'."
                ),
                MurphyExerciseItem(
                    id = "u8_ex2",
                    exerciseNumber = "8.3",
                    taskType = "CHOICE",
                    question = "'Where's Tom?' 'He's in the bathroom. He ______ a shower.'",
                    options = listOf("has", "is having", "having", "have"),
                    correctOptionIndex = 1,
                    correctAnswerText = "is having",
                    explanationUzbek = "Tom ayni paytda vanna xonasida dush qabul qilmoqda: 'is having'."
                ),
                MurphyExerciseItem(
                    id = "u8_ex3",
                    exerciseNumber = "8.3",
                    taskType = "CHOICE",
                    question = "I don't understand this sentence. What ______ ?",
                    options = listOf("are you meaning", "do you mean", "you mean", "does you mean"),
                    correctOptionIndex = 1,
                    correctAnswerText = "do you mean",
                    explanationUzbek = "'mean' holat fe'li, continuous da ishlatilmaydi: 'What do you mean?'."
                ),
                MurphyExerciseItem(
                    id = "u8_ex4",
                    exerciseNumber = "8.3",
                    taskType = "CHOICE",
                    question = "Listen! Somebody ______ (sing).",
                    options = listOf("sings", "is singing", "are singing", "sing"),
                    correctOptionIndex = 1,
                    correctAnswerText = "is singing",
                    explanationUzbek = "'Listen!' (Eshiting!) belgisi harakat hozir sodir bo'layotganini ko'rsatadi: 'is singing'."
                ),
                MurphyExerciseItem(
                    id = "u8_ex5",
                    exerciseNumber = "8.3",
                    taskType = "CHOICE",
                    question = "Sarah is tired. She ______ to go home now.",
                    options = listOf("is wanting", "wants", "want", "wanting"),
                    correctOptionIndex = 1,
                    correctAnswerText = "wants",
                    explanationUzbek = "'want' fe'li 'now' bo'lsa ham hech qachon continuous bo'lmaydi, Sarah (she) uchun 'wants' bo'ladi."
                )
            )
        ),

        // UNIT 9
        MurphyUnit(
            unitNumber = 9,
            title = "I have... and I've got...",
            subtitleUzbek = "Egalik ma'nosi: have va have got ning qo'llanilishi va farqlari",
            groupName = "1-Guruh: Present (1–9)",
            keyTakeawaysUzbek = listOf(
                "Egalik, qarindoshlik, kasallik yoki tashqi ko'rinish uchun 'have' yoki 'have got' ishlatiladi.",
                "I/we/you/they have (got) | He/she/it has (got).",
                "Inkor shakli: I don't have = I haven't got | He doesn't have = He hasn't got.",
                "So'roq shakli: Do you have...? = Have you got...? | Does he have...? = Has he got...?",
                "O'tgan zamonda faqat 'had' bo'ladi ('had got' ishlatilmaydi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "have va have got darak shakli",
                    formula = "I/we/you/they have = I've got / we've got\nHe/she/it has = he's got / she's got",
                    explanationUzbek = "Ikkala shakl ham bir xil ma'noni bildiradi: biror narsaga egalik qilish. Britaniya ingliz tilida 'have got' juda mashhur, Amerika ingliz tilida esa ko'proq oddiy 'have' ishlatiladi.",
                    examples = listOf(
                        MurphyExample("I have blue eyes. = I've got blue eyes.", "Mening ko'zlarim ko'k."),
                        MurphyExample("Tom has two sisters. = Tom has got two sisters.", "Tomning ikkita singlisi bor."),
                        MurphyExample("Our car has four doors. = Our car has got four doors.", "Mashinamizning to'rtta eshigi bor."),
                        MurphyExample("Sarah has a headache. = Sarah has got a headache.", "Saraning boshi og'riyapti."),
                        MurphyExample("They have a horse, three dogs and six cats.", "Ularning bir oti, uchta iti va oltita mushugi bor.")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Inkor shakllari (Negative)",
                    formula = "have: I don't have | He doesn't have\nhave got: I haven't got | He hasn't got",
                    explanationUzbek = "Inkor tuzganda aralashtirib yubormang! Agar 'have' ishlatsangiz don't/doesn't kerak. Agar 'have got' ishlatsangiz haven't got / hasn't got bo'ladi.",
                    examples = listOf(
                        MurphyExample("I don't have a car. = I haven't got a car.", "Mening mashinam yo'q."),
                        MurphyExample("They don't have any children. = They haven't got any children.", "Ularning farzandlari yo'q."),
                        MurphyExample("It doesn't have a garden. = It hasn't got a garden.", "Uyning bog'i yo'q."),
                        MurphyExample("Amy doesn't have a job. = Amy hasn't got a job.", "Emmining hozir ishi yo'q.")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "So'roq shakllari (Questions)",
                    formula = "Do you have ... ? = Have you got ... ?\nDoes he have ... ? = Has he got ... ?",
                    explanationUzbek = "Savollarda ham ikkita yo'l bor: 'Do you have a camera?' yoki 'Have you got a camera?' Ikkalasi ham to'liq to'g'ri.",
                    examples = listOf(
                        MurphyExample("'Do you have a camera?' 'No, I don't.'", "'Kamerangiz bormi?' 'Yo'q.'"),
                        MurphyExample("'Have you got a camera?' 'No, I haven't.'", "'Kamerangiz bormi?' 'Yo'q.'"),
                        MurphyExample("'Does Helen have a car?' 'Yes, she does.'", "'Yelenaning mashinasi bormi?' 'Ha.'"),
                        MurphyExample("'Has Helen got a car?' 'Yes, she has.'", "'Yelenaning mashinasi bormi?' 'Ha.'"),
                        MurphyExample("What kind of car does she have? = What kind of car has she got?", "Unda qanaqa mashina bor?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u9_ex1",
                    exerciseNumber = "9.1",
                    taskType = "CHOICE",
                    question = "'They have two children' jumlasining 'got' bilan to'g'ri ekvivalenti:",
                    options = listOf("They've got two children.", "They has got two children.", "They are got two children.", "They got two children."),
                    correctOptionIndex = 0,
                    correctAnswerText = "They've got two children.",
                    explanationUzbek = "'They have' = 'They've got' (They have got)."
                ),
                MurphyExerciseItem(
                    id = "u9_ex2",
                    exerciseNumber = "9.2",
                    taskType = "CHOICE",
                    question = "'Have you got any money?' savolining 'do/does' bilan to'g'ri shakli:",
                    options = listOf("Do you have any money?", "Does you have any money?", "Are you have any money?", "Have you any money?"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Do you have any money?",
                    explanationUzbek = "'Have you got...?' so'rovining Present Simple ekvivalenti: 'Do you have...?'."
                ),
                MurphyExerciseItem(
                    id = "u9_ex3",
                    exerciseNumber = "9.4",
                    taskType = "CHOICE",
                    question = "Sarah ______ a car. She goes everywhere by bike.",
                    options = listOf("hasn't", "doesn't have", "doesn't has", "don't have"),
                    correctOptionIndex = 1,
                    correctAnswerText = "doesn't have",
                    explanationUzbek = "'Sarah' uchun inkor 'doesn't have' (yoki 'hasn't got') bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u9_ex4",
                    exerciseNumber = "9.5",
                    taskType = "CHOICE",
                    question = "I'm not feeling very well. I ______ a headache.",
                    options = listOf("'ve got", "am having", "'m got", "got"),
                    correctOptionIndex = 0,
                    correctAnswerText = "'ve got",
                    explanationUzbek = "Bosh og'rig'i (kasallik) haqida 'I've got a headache' yoki 'I have a headache' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u9_ex5",
                    exerciseNumber = "9.5",
                    taskType = "CHOICE",
                    question = "She can't open the door. She ______ a key.",
                    options = listOf("hasn't got", "haven't got", "doesn't got", "not has"),
                    correctOptionIndex = 0,
                    correctAnswerText = "hasn't got",
                    explanationUzbek = "'She' olmoshi bilan 'hasn't got' (yoki 'doesn't have') to'g'ri bo'ladi."
                )
            )
        )
    )
}
