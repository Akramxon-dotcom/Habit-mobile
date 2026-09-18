package com.example.data.model

object MurphyBookPronounsDeterminersData {

    val UNITS_PRONOUNS_DETERMINERS: List<MurphyUnit> = listOf(
        // UNIT 69
        MurphyUnit(
            unitNumber = 69,
            title = "this / that / these / those",
            subtitleUzbek = "Ko'rsatish olmoshlari: Yaqin va uzoq, birlik va ko'plik",
            groupName = "12-Guruh: Pronouns & Determiners (69–74)",
            keyTakeawaysUzbek = listOf(
                "THIS = Yaqindagi bitta narsa/odam (This book, this picture, this is my friend).",
                "THESE = Yaqindagi bir nechta narsalar/odamlar (These books, these pictures).",
                "THAT = Uzoqdagi bitta narsa/odam (That car, that building, who is that?).",
                "THOSE = Uzoqdagi bir nechta narsalar/odamlar (Those cars, those buildings).",
                "Telefonda o'zini tanishtirishda: 'Hello, this is David' (Men Davidman). Suhbatdoshni so'raganda: 'Is that Sarah?' (Bu Sarahmi?).",
                "Vaqt va voqealar uchun: This morning (bugun ertalab), that day (o'sha kuni), 'That's right' (To'g'ri), 'That's a good idea'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Yaqin (This / These) va Uzoq (That / Those)",
                    formula = "Yaqin: THIS (birlik) / THESE (ko'plik) | Uzoq: THAT (birlik) / THOSE (ko'plik)",
                    explanationUzbek = "1) Bu yerda / Qo'lda ushlab turilgan narsalar (Yaqin):\n• Do you like this picture? (Mana bu rasmni yoqtirasizmi? - bitta rasm)\n• These flowers are for you. (Mana bu gullar siz uchun - ko'p gullar)\n\n2) Narigi tomonda / Ko'z yetadigan uzoqlikda (Uzoq):\n• Who is that woman over there? (Anavi yerdagi ayol kim?)\n• Look at those birds in the tree! (Daraxtdagi anavi qushlarga qara!)\n• That restaurant looks expensive. (Anavi restoran qimmatga o'xshaydi)",
                    examples = listOf(
                        MurphyExample("This is a delicious cake.", "Bu juda mazali tort ekan.", "this (qo'ldagi/oldidagi bitta narsa)"),
                        MurphyExample("These shoes are too small for me.", "Mana bu poyabzallar menga kichiklik qilyapti.", "these (ko'plik)"),
                        MurphyExample("Who lives in that house?", "Anavi uyda kim yashaydi?", "that (uzoqdagi bitta)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Telefon so'zlashuvi va kundalik iboralarda",
                    formula = "Telefonda: This is... / Is that...? | Fikrga javob: That's ...",
                    explanationUzbek = "Telefon orqali gaplashganda 'I am David' deyilmaydi:\n• Hello, this is Akmal. (Salom, bu Akmal / Akmalman)\n• Is that Sarah? (Bu Sarahmi?)\n\nBiror kishi gap aytganda yoki hodisa sodir bo'lganda:\n• I'm sorry I'm late. - That's all right. (Kech qolganim uchun uzr. - Hechqisi yo'q)\n• We're going to Spain on holiday. - That's wonderful! (Ajoyib-ku!)\n• You're right. / That's true. (Bu to'g'ri)",
                    examples = listOf(
                        MurphyExample("Hello, this is John. Can I speak to Mary?", "Salom, bu Jon. Meri bilan gaplashsam bo'ladimi?", "this is (telefonda)"),
                        MurphyExample("That was a great movie, wasn't it?", "U ajoyib kino bo'ldi, shunday emasmi?", "that (o'tib ketgan voqea)"),
                        MurphyExample("That's very kind of you!", "Juda mehribonsiz / marhamatingiz uchun rahmat!", "that's...")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u69_ex1",
                    exerciseNumber = "69.1",
                    taskType = "CHOICE",
                    question = "Whose jacket is ______ over there on the chair?",
                    options = listOf("that", "this", "these", "those"),
                    correctOptionIndex = 0,
                    correctAnswerText = "that",
                    explanationUzbek = "'over there' (anavi yerda) uzoqni bildiradi va bitta kiyim 'jacket' uchun 'that' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u69_ex2",
                    exerciseNumber = "69.2",
                    taskType = "CHOICE",
                    question = "______ shoes are very comfortable. I bought them yesterday.",
                    options = listOf("These", "This", "That", "Those"),
                    correctOptionIndex = 0,
                    correctAnswerText = "These",
                    explanationUzbek = "'shoes' ko'plikda va odam o'z kiygan poyabzali haqida gapirganda 'these shoes' deydi."
                ),
                MurphyExerciseItem(
                    id = "u69_ex3",
                    exerciseNumber = "69.3",
                    taskType = "CHOICE",
                    question = "Hello, ______ is David. Can I speak to Dr. Roberts?",
                    options = listOf("this", "that", "it", "here"),
                    correctOptionIndex = 0,
                    correctAnswerText = "this",
                    explanationUzbek = "Telefonda o'zini tanishtirganda 'Hello, this is...' deb aytiladi."
                ),
                MurphyExerciseItem(
                    id = "u69_ex4",
                    exerciseNumber = "69.4",
                    taskType = "CHOICE",
                    question = "Do you remember ______ people we met in Paris last year?",
                    options = listOf("those", "these", "this", "that"),
                    correctOptionIndex = 0,
                    correctAnswerText = "those",
                    explanationUzbek = "O'tgan yildagi (uzoq vaqt) ko'plikdagi odamlar uchun 'those people' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u69_ex5",
                    exerciseNumber = "69.5",
                    taskType = "CHOICE",
                    question = "I passed all my driving exams! - ______ is fantastic news!",
                    options = listOf("That", "These", "Those", "This"),
                    correctOptionIndex = 0,
                    correctAnswerText = "That",
                    explanationUzbek = "Aytilgan xabarga munosabat bildirishda 'That is fantastic!' deb aytiladi."
                )
            )
        ),

        // UNIT 70
        MurphyUnit(
            unitNumber = 70,
            title = "one / ones",
            subtitleUzbek = "Otni takrorlamaslik uchun 'one' va 'ones' so'zlarining qo'llanilishi",
            groupName = "12-Guruh: Pronouns & Determiners (69–74)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida bir xil otni ketma-ket takrorlash xunuk hisoblanadi. Shuning uchun ot o'rniga 'one' (birlik) yoki 'ones' (ko'plik) ishlatiladi.",
                "ONE = Birlikdagi sanoqli ot o'rniga (a car -> a new one, this cup -> that one).",
                "ONES = Ko'plikdagi otlar o'rniga (these shoes -> the black ones, big glasses -> small ones).",
                "Qaysi biri? deb so'rashda: 'Which one?' (birlik) yoki 'Which ones?' (ko'plik).",
                "Sanalmaydigan otlar bilan ONE ishlatilmaydi! (some milk -> some / it deb aytiladi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Birlikda 'one' ning ishlatilishi",
                    formula = "this/that one | the + sifat + one | a/an + sifat + one",
                    explanationUzbek = "Ilgari aytilgan otni qayta takrorlamaslik uchun 'one' qo'yiladi:\n\n• I need a pen. Have you got one? (= Have you got a pen?)\n• Which car is yours? - The red one over there. (= The red car)\n• I don't like this coat. I prefer that one. (= that coat)\n• My phone is very old. I want to buy a new one. (= a new phone)",
                    examples = listOf(
                        MurphyExample("These chocolates look good. Can I have one?", "Bu shokoladlar zo'r ko'rinyapti. Bittasini olsam bo'ladimi?", "one = one chocolate"),
                        MurphyExample("Which hat do you like best? - The blue one.", "Qaysi shlyapa sizga ko'proq yoqadi? - Moviyrog'i.", "the blue one = the blue hat"),
                        MurphyExample("This cup is dirty. Can I have a clean one?", "Bu finjon kir ekan. Tozasini olsam maylimi?", "a clean one = a clean cup")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Ko'plikda 'ones' ning ishlatilishi",
                    formula = "these/those ones | the + sifat + ones | which ones?",
                    explanationUzbek = "Ko'plikdagi otlar takrorlanmasligi uchun 'ones' ishlatiladi:\n\n• Which shoes do you prefer? - The black ones. (= The black shoes)\n• These cups are dirty. Where are the clean ones? (= the clean cups)\n• Don't buy those apples. Buy these ones. (= these apples)\n• I like old houses more than modern ones. (= modern houses)",
                    examples = listOf(
                        MurphyExample("Which flowers would you like? - The yellow ones, please.", "Qaysi gullarni xohlaysiz? - Sariqlarini, iltimos.", "the yellow ones"),
                        MurphyExample("His stories are interesting, especially the short ones.", "Uning hikoyalari qiziq, ayniqsa qisqalari.", "the short ones"),
                        MurphyExample("Those coats are expensive. Are there cheaper ones?", "Anavi paltolar qimmat. Arzonroqlari bormi?", "cheaper ones")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u70_ex1",
                    exerciseNumber = "70.1",
                    taskType = "CHOICE",
                    question = "This knife is blunt. Do you have a sharper ______?",
                    options = listOf("one", "ones", "it", "them"),
                    correctOptionIndex = 0,
                    correctAnswerText = "one",
                    explanationUzbek = "'knife' birlikdagi ot bo'lgani uchun 'a sharper one' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u70_ex2",
                    exerciseNumber = "70.2",
                    taskType = "CHOICE",
                    question = "I don't like green apples. I prefer the red ______.",
                    options = listOf("ones", "one", "them", "those"),
                    correctOptionIndex = 0,
                    correctAnswerText = "ones",
                    explanationUzbek = "'apples' ko'plikda bo'lgani uchun 'the red ones' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u70_ex3",
                    exerciseNumber = "70.3",
                    taskType = "CHOICE",
                    question = "Which bag is yours? - The small ______ with the blue strap.",
                    options = listOf("one", "ones", "it", "bag"),
                    correctOptionIndex = 0,
                    correctAnswerText = "one",
                    explanationUzbek = "Bitta sumka haqida gap ketayotgani uchun 'The small one'."
                ),
                MurphyExerciseItem(
                    id = "u70_ex4",
                    exerciseNumber = "70.4",
                    taskType = "CHOICE",
                    question = "These curtains are very faded. We need to buy new ______.",
                    options = listOf("ones", "one", "curtain", "them"),
                    correctOptionIndex = 0,
                    correctAnswerText = "ones",
                    explanationUzbek = "'curtains' ko'plikdagi parda, shuning uchun 'new ones' (yangi pardalar)."
                ),
                MurphyExerciseItem(
                    id = "u70_ex5",
                    exerciseNumber = "70.5",
                    taskType = "CHOICE",
                    question = "There were many hotels, but we chose ______ near the beach.",
                    options = listOf("the one", "the ones", "one", "ones"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the one",
                    explanationUzbek = "Mehmonxonalardan bittasini tanlagan: 'the one near the beach'."
                )
            )
        ),

        // UNIT 71
        MurphyUnit(
            unitNumber = 71,
            title = "some and any",
            subtitleUzbek = "Darak, inkor va so'roq gaplarda 'some' va 'any' ning ishlatilishi",
            groupName = "12-Guruh: Pronouns & Determiners (69–74)",
            keyTakeawaysUzbek = listOf(
                "SOME odatda DARAK (+) gaplarda ishlatiladi: I have some money. There are some apples.",
                "ANY odatda INKOR (-) va UMUMIY SO'ROQ (?) gaplarda ishlatiladi: I don't have any money. Do you have any questions?",
                "ISTISNO (Muhim!): Taklif (Offer) yoki iltimos (Request) so'roqlarida SOME ishlatiladi: Would you like some coffee? Can I have some water?",
                "Any darak gapda 'har qanday / ixtiyoriy' ma'nosida kelishi mumkin: You can take any bus (Har qanday avtobusga o'tiravering).",
                "SOME va ANY so'zlari ham ko'plikdagi sanoqli otlar bilan (some books), ham sanalmaydigan otlar bilan (some milk) ishlatiladi."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "SOME (+) vs ANY (-) / (?) qoidasi",
                    formula = "Darak (+) -> SOME | Inkor (-) -> ANY | So'roq (?) -> ANY",
                    explanationUzbek = "1) SOME (bir oz, bir nechta, qanchadir):\n• I bought some apples. (Men bir nechta olma sotib oldim)\n• There is some milk in the fridge. (Muzlatgichda biroz sut bor)\n\n2) ANY (hech qanday, birorta):\n• I didn't buy any apples. (Men hech qanday olma sotib olmadim)\n• Is there any milk in the fridge? (Muzlatgichda biror qultum sut bormi?)\n• I haven't got any money. (Menda umuman pul yo'q)",
                    examples = listOf(
                        MurphyExample("We need some bread for breakfast.", "Nonushtaga biroz non kerak.", "some bread (+)"),
                        MurphyExample("Are there any letters for me?", "Menga biror xat bormi?", "any letters (?)"),
                        MurphyExample("He went out without any money.", "U hamyonida umuman pulsiz ko'chaga chiqdi.", "without any (-)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "So'roqda SOME qachon ishlatiladi?",
                    formula = "Taklif (Would you like...?) / Iltimos (Can I have...?) -> SOME",
                    explanationUzbek = "Agar biz mehmonga biror narsa taklif qilsak yoki biror narsani berishni iltimos qilsak va javob 'ha' bo'lishini kutsak, 'SOME' ishlatiladi:\n\n• Would you like some tea? (Choy ichasizmi? - taklif)\n• Would you like some cake? (Tort xohlaysizmi?)\n• Can I have some sugar in my coffee, please? (Qahvamga biroz shakar solib bera olasizmi? - iltimos)\n• Could you lend me some money? (Menga biroz pul qarz bera olasizmi?)",
                    examples = listOf(
                        MurphyExample("Would you like some biscuits?", "Biroz pechenye xohlaysizmi?", "taklifda some"),
                        MurphyExample("Can you give me some information?", "Menga biroz ma'lumot bera olasizmi?", "iltimosda some")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u71_ex1",
                    exerciseNumber = "71.1",
                    taskType = "CHOICE",
                    question = "I don't have ______ free time this weekend.",
                    options = listOf("any", "some", "no", "an"),
                    correctOptionIndex = 0,
                    correctAnswerText = "any",
                    explanationUzbek = "Inkor gapda (don't have) 'any' ishlatiladi: don't have any free time."
                ),
                MurphyExerciseItem(
                    id = "u71_ex2",
                    exerciseNumber = "71.2",
                    taskType = "CHOICE",
                    question = "Would you like ______ orange juice?",
                    options = listOf("some", "any", "a", "an"),
                    correctOptionIndex = 0,
                    correctAnswerText = "some",
                    explanationUzbek = "Taklif bildirilgan so'roq gaplarda (Would you like...) 'some' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u71_ex3",
                    exerciseNumber = "71.3",
                    taskType = "CHOICE",
                    question = "She bought ______ lovely flowers at the market.",
                    options = listOf("some", "any", "every", "a"),
                    correctOptionIndex = 0,
                    correctAnswerText = "some",
                    explanationUzbek = "Darak gapda 'some' qo'yiladi: bought some flowers."
                ),
                MurphyExerciseItem(
                    id = "u71_ex4",
                    exerciseNumber = "71.4",
                    taskType = "CHOICE",
                    question = "Do you have ______ brothers or sisters?",
                    options = listOf("any", "some", "every", "all"),
                    correctOptionIndex = 0,
                    correctAnswerText = "any",
                    explanationUzbek = "Oddiy umumiy so'roq gapda 'any' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u71_ex5",
                    exerciseNumber = "71.5",
                    taskType = "CHOICE",
                    question = "Can I borrow ______ paper from you?",
                    options = listOf("some", "any", "a", "an"),
                    correctOptionIndex = 0,
                    correctAnswerText = "some",
                    explanationUzbek = "Iltimos so'rog'ida (Can I borrow...) 'some' to'g'ri bo'ladi."
                )
            )
        ),

        // UNIT 72
        MurphyUnit(
            unitNumber = 72,
            title = "not + any / no / none",
            subtitleUzbek = "Inkor so'zlar: not any, no, none va ularning farqlari",
            groupName = "12-Guruh: Pronouns & Determiners (69–74)",
            keyTakeawaysUzbek = listOf(
                "NOT ... ANY = NO (I haven't got any money = I have no money). Ikkalasi bir xil ma'noni beradi.",
                "INGLIZ TILIDA IKKI MARTA INKOR BO'LMAYDI! 'I don't have no money' - QAT'IYAN NOTO'G'RI! Yo 'don't have any' yoki 'have no' deyiladi.",
                "NO so'zidan keyin DOIM OT keladi: no money, no friends, no cars, no time.",
                "NONE so'zidan keyin HECH QACHON OT KELMAYDI! U yolg'iz o'zi ishlatiladi (How much money do you have? - None).",
                "NONE OF = 'hech biri' (None of my friends came to the party)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Not any vs No (Bir xil ma'no, turli tuzilish)",
                    formula = "Inkor fe'l + ANY + ot = Darak fe'l + NO + ot",
                    explanationUzbek = "Ikkala shakl ham bir xil ma'noni ifodalaydi:\n\n• There isn't any milk in the fridge. = There is NO milk in the fridge.\n• We haven't got any petrol. = We have NO petrol.\n• There aren't any cars in the parking lot. = There are NO cars.\n\n⚠️ Eslab qoling: Fe'l inkor bo'lsa (haven't / isn't / didn't), 'no' qo'yish taqiqlanadi!",
                    examples = listOf(
                        MurphyExample("We have no time to lose.", "Bizda yo'qotadigan vaqt yo'q.", "have no time"),
                        MurphyExample("There were no empty seats on the bus.", "Avtobusda bo'sh o'rindiqlar yo'q edi.", "no seats = not any seats")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "NONE ning yolg'iz ishlatilishi",
                    formula = "Savolga qisqa javob: NONE! | NONE OF + ko'plik",
                    explanationUzbek = "NONE ot olmasdan yolg'iz keladi:\n\n• How many eggs did you buy? - None. (Nechta tuxum oldingiz? - Hech qancha / bitta ham olganim yo'q)\n• How much money do you have? - None. (Menda hech qancha yo'q)\n\nNONE OF qo'llanilishi:\n• None of the shops were open. (Do'konlarning hech biri ochiq emas edi)\n• None of us understood the question. (Bizning hech birimiz savolni tushunmadik)",
                    examples = listOf(
                        MurphyExample("Is there any bread left? - None.", "Non qoldimi? - Yo'q, hech qancha.", "none (yolg'iz o'zi)"),
                        MurphyExample("None of my classmates smoke.", "Mening sinfdoshlarimdan hech biri chekmaydi.", "none of...")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u72_ex1",
                    exerciseNumber = "72.1",
                    taskType = "CHOICE",
                    question = "How many bags are you taking? - ______.",
                    options = listOf("None", "No", "Any", "Not"),
                    correctOptionIndex = 0,
                    correctAnswerText = "None",
                    explanationUzbek = "Otsiz, qisqa javobda faqat 'None' ishlatiladi ('No' deb yolg'iz aytilmaydi)."
                ),
                MurphyExerciseItem(
                    id = "u72_ex2",
                    exerciseNumber = "72.2",
                    taskType = "CHOICE",
                    question = "There were ______ buses, so we had to walk home.",
                    options = listOf("no", "none", "not", "any"),
                    correctOptionIndex = 0,
                    correctAnswerText = "no",
                    explanationUzbek = "Ot (buses) oldidan 'no' keladi: 'There were no buses'."
                ),
                MurphyExerciseItem(
                    id = "u72_ex3",
                    exerciseNumber = "72.3",
                    taskType = "CHOICE",
                    question = "I have ______ money to buy that car.",
                    options = listOf("no", "none", "any", "not"),
                    correctOptionIndex = 0,
                    correctAnswerText = "no",
                    explanationUzbek = "'have no money' (puli yo'qlikni ifodalaydi, darak fe'l bilan)."
                ),
                MurphyExerciseItem(
                    id = "u72_ex4",
                    exerciseNumber = "72.4",
                    taskType = "CHOICE",
                    question = "I didn't answer ______ of their questions.",
                    options = listOf("any", "no", "none", "some"),
                    correctOptionIndex = 0,
                    correctAnswerText = "any",
                    explanationUzbek = "Inkor fe'l (didn't answer) bo'lgani sababli 'any' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u72_ex5",
                    exerciseNumber = "72.5",
                    taskType = "CHOICE",
                    question = "______ of this money belongs to me.",
                    options = listOf("None", "No", "Not", "Any"),
                    correctOptionIndex = 0,
                    correctAnswerText = "None",
                    explanationUzbek = "'None of...' birikmasi '...ning hech biri' ma'nosida keladi."
                )
            )
        ),

        // UNIT 73
        MurphyUnit(
            unitNumber = 73,
            title = "somebody / anything / nowhere etc.",
            subtitleUzbek = "Hosilaviy olmoshlar: -body, -one, -thing, -where",
            groupName = "12-Guruh: Pronouns & Determiners (69–74)",
            keyTakeawaysUzbek = listOf(
                "ODAMLAR: somebody / someone (kimdir), anybody / anyone (kimdir/hech kim), nobody / no-one (hech kim), everybody / everyone (hamma).",
                "NARSA/BUYUMLAR: something (nimadir), anything (nimadir/hech narsa), nothing (hech narsa), everything (hamma narsa).",
                "JOY/MAKONLAR: somewhere (qayergadir), anywhere (qayergadir/hech qayerga), nowhere (hech qayerga), everywhere (hamma joyga).",
                "Bu olmoshlarning HAMMASI grammatik jihatdan BIRLIK (Singular) deb hisoblanadi: Everybody IS happy (are emas), Everything WAS ready.",
                "Nobody, nothing, nowhere bo'lgan gapda inkor qo'shilmaydi: I know nothing (I don't know nothing NOTO'G'RI)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Olmoshlar jadvali va ma'nolari",
                    formula = "some- (+) | any- (-) / (?) | no- (inkor) | every- (hamma)",
                    explanationUzbek = "Tizim qoidalari:\n\n1) Insonlar:\n• Somebody knocked on the door. (Kimdir eshikni taqillatdi)\n• Did anybody see my keys? (Kimdir kalitimni ko'rdimi?)\n• Nobody knows the answer. (Hech kim javobni bilmaydi)\n\n2) Narsalar:\n• I have something to tell you. (Senga aytadigan bir gapim bor)\n• I didn't buy anything. (Men hech narsa sotib olmadim)\n• There is nothing in the box. (Qutida hech narsa yo'q)\n\n3) Joylar:\n• Let's go somewhere warm. (Keling, iliqroq bir joyga boraylik)\n• I looked everywhere, but found it nowhere. (Hamma yoqni qidirdim, lekin hech qayerdan topolmadim)",
                    examples = listOf(
                        MurphyExample("Is there anybody at home?", "Uyda kimdir bormi?", "anybody (?)"),
                        MurphyExample("She said nothing all evening.", "U butun kecha hech narsa demadi.", "said nothing"),
                        MurphyExample("I lost my passport somewhere at the airport.", "Pasportimni aeroportda qayerdadir yo'qotib qo'ydim.", "somewhere")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Birlik fe'li va sifatlar bilan kelishi",
                    formula = "everybody / nobody + BIRLIK FE'LI | something + SIFAT",
                    explanationUzbek = "Ikkita juda muhim qoida:\n\n1) Fe'l doim birlikda bo'ladi:\n• Everybody wants to be rich. (Hamma boy bo'lishni xohlaydi - wants)\n• Nobody likes bad weather. (Hech kimga yomon havo yoqmaydi)\n\n2) Sifat olmoshdan KEYIN keladi:\n• I want something cold to drink. (Sovuq biror narsa ichmoqchiman - 'cold something' emas!)\n• Did you meet anybody interesting? (Qiziqarli biror kimni uchratdingizmi?)",
                    examples = listOf(
                        MurphyExample("Everybody has problems sometimes.", "Hamma insonlarda ba'zan muammo bo'ladi.", "everybody has (birlik)"),
                        MurphyExample("Let's do something exciting today!", "Bugun qiziqarli biror ish qilaylik!", "something + exciting")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u73_ex1",
                    exerciseNumber = "73.1",
                    taskType = "CHOICE",
                    question = "The house is completely empty. There is ______ living here.",
                    options = listOf("nobody", "anybody", "somebody", "everybody"),
                    correctOptionIndex = 0,
                    correctAnswerText = "nobody",
                    explanationUzbek = "Uy butunlay bo'sh ekan, demak bu yerda 'hech kim' yashamaydi: 'nobody'."
                ),
                MurphyExerciseItem(
                    id = "u73_ex2",
                    exerciseNumber = "73.2",
                    taskType = "CHOICE",
                    question = "Did ______ call me while I was out?",
                    options = listOf("anybody", "nobody", "somebody", "everybody"),
                    correctOptionIndex = 0,
                    correctAnswerText = "anybody",
                    explanationUzbek = "So'roq gapda odatda 'anybody' ishlatiladi: Did anybody call?"
                ),
                MurphyExerciseItem(
                    id = "u73_ex3",
                    exerciseNumber = "73.3",
                    taskType = "CHOICE",
                    question = "Everybody in our team ______ very hard.",
                    options = listOf("works", "work", "are working", "were working"),
                    correctOptionIndex = 0,
                    correctAnswerText = "works",
                    explanationUzbek = "'Everybody' grammatik jihatdan birlik deb qaraladi, shuning uchun 'works' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u73_ex4",
                    exerciseNumber = "73.4",
                    taskType = "CHOICE",
                    question = "I'm hungry! Let's find ______ good to eat.",
                    options = listOf("something", "anything", "nothing", "everything"),
                    correctOptionIndex = 0,
                    correctAnswerText = "something",
                    explanationUzbek = "Darak gapda sifat oldidan 'something good' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u73_ex5",
                    exerciseNumber = "73.5",
                    taskType = "CHOICE",
                    question = "I opened the box, but there was ______ inside. It was empty.",
                    options = listOf("nothing", "anything", "something", "everything"),
                    correctOptionIndex = 0,
                    correctAnswerText = "nothing",
                    explanationUzbek = "Quti bo'sh bo'lsa, ichida hech narsa bo'lmagan: 'there was nothing'."
                )
            )
        ),

        // UNIT 74
        MurphyUnit(
            unitNumber = 74,
            title = "every and all",
            subtitleUzbek = "'Every' va 'All' so'zlarining farqlari va birikmalari",
            groupName = "12-Guruh: Pronouns & Determiners (69–74)",
            keyTakeawaysUzbek = listOf(
                "EVERY = Har bir (birlikdagi ot va birlikdagi fe'l bilan keladi): every student, every day, every country.",
                "ALL = Barcha / hamma (ko'plikdagi otlar yoki sanalmaydigan otlar bilan): all students, all countries, all money.",
                "EVERY DAY = Har kuni (takrorlanish) vs ALL DAY = Kun bo'yi (ertalabdan kechgacha).",
                "EVERYONE / EVERYBODY = Hamma (birlik fe'li oladi: Everyone knows = All people know).",
                "ALL OF = Hammasi (All of the students passed the exam)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "EVERY + Birlik vs ALL + Ko'plik",
                    formula = "EVERY + birlik ot + birlik fe'l | ALL + ko'plik ot + ko'plik fe'l",
                    explanationUzbek = "1) EVERY (Har bir):\n• Every student has an exam tomorrow. (Har bir talabaning ertaga imtihoni bor)\n• Every room in the hotel had a balcony. (Mehmonxonadagi har bir xonada balkon bor edi)\n\n2) ALL (Hamma):\n• All students have exams tomorrow. (Barcha talabalarning imtihonlari bor)\n• All rooms had a balcony. (Hamma xonalarda)\n\n⚠️ Diqqat qiling: 'Every of' birikmasi ingliz tilida YO'Q! 'Every one of...' deb aytiladi."
                    ,
                    examples = listOf(
                        MurphyExample("She visits her grandmother every Sunday.", "U buvisini har yakshanba borib ko'radi.", "every Sunday"),
                        MurphyExample("All the trains were delayed because of snow.", "Qor sababli barcha poyezdlar kechiktirildi.", "all the trains (ko'plik)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Every day (Har kuni) vs All day (Kun bo'yi)",
                    formula = "Every day = Har kuni | All day = Boshidan oxirigacha",
                    explanationUzbek = "Vaqt iboralari bilan juda muhim farq:\n\n• It rained every day last week. (O'tgan hafta har kuni yomg'ir yog'di - dushanba, seshanba, chorshanba...)\n• It rained all day yesterday. (Kecha kun bo'yi yomg'ir yog'di - ertalabdan kechgacha tinmadi)\n\nShuningdek:\n• all night (tun bo'yi), all morning (ertalabdan beri)\n• all my life (butun hayotim davomida)",
                    examples = listOf(
                        MurphyExample("I studied all night for the test.", "Test uchun tun bo'yi dars qildim.", "all night (tun davomida)"),
                        MurphyExample("He goes to the gym every morning.", "U har tong sport zaliga boradi.", "every morning")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u74_ex1",
                    exerciseNumber = "74.1",
                    taskType = "CHOICE",
                    question = "It rained ______ day yesterday, so we couldn't go out.",
                    options = listOf("all", "every", "each", "whole of"),
                    correctOptionIndex = 0,
                    correctAnswerText = "all",
                    explanationUzbek = "Kun bo'yi, tinmasdan davom etganini ifodalash uchun 'all day' deyiladi."
                ),
                MurphyExerciseItem(
                    id = "u74_ex2",
                    exerciseNumber = "74.2",
                    taskType = "CHOICE",
                    question = "______ child needs love and attention.",
                    options = listOf("Every", "All", "All the", "Whole"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Every",
                    explanationUzbek = "'child' birlikdagi ot bo'lgani uchun 'Every child' to'g'ri bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u74_ex3",
                    exerciseNumber = "74.3",
                    taskType = "CHOICE",
                    question = "______ the tickets have been sold out.",
                    options = listOf("All", "Every", "Every of", "Each"),
                    correctOptionIndex = 0,
                    correctAnswerText = "All",
                    explanationUzbek = "Ko'plikdagi ot (the tickets) bilan 'All the tickets' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u74_ex4",
                    exerciseNumber = "74.4",
                    taskType = "CHOICE",
                    question = "I brush my teeth ______ morning and evening.",
                    options = listOf("every", "all", "whole", "each of"),
                    correctOptionIndex = 0,
                    correctAnswerText = "every",
                    explanationUzbek = "Doimiy takrorlanish (har tong va har oqshom) uchun 'every morning' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u74_ex5",
                    exerciseNumber = "74.5",
                    taskType = "CHOICE",
                    question = "Everyone ______ ready to start the presentation.",
                    options = listOf("is", "are", "were", "have been"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is",
                    explanationUzbek = "'Everyone' birlik hisoblanadi, shuning uchun 'is' oladi."
                )
            )
        )
    )
}
