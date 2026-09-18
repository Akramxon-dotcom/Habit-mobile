package com.example.data.model

object MurphyBookVerbStructuresData {

    val UNITS_VERB_STRUCTURES: List<MurphyUnit> = listOf(
        // UNIT 51
        MurphyUnit(
            unitNumber = 51,
            title = "work / working / worked (Verb forms)",
            subtitleUzbek = "Ingliz tili fe'l shakllari va zamonlarda ishlatilishi",
            groupName = "9-Guruh: -ing and to... (Gerund & Infinitives: 51–56)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida fe'lning 5 ta asosiy shakli bor: Base (work), -s (works), -ing (working), Past simple (worked), Past participle (worked/done).",
                "BE + -ing (Continuous zamonlar): I am working, they were playing.",
                "HAVE + past participle (Perfect zamonlar): I have worked, she has done.",
                "BE + past participle (Passive / Majhul nisbat): The car was repaired, it is made.",
                "MODAL FE'LLAR + asosiy shakl: can work, will work, must work, should work."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Fe'llarning asosiy turlari va yordamchi fe'llar",
                    formula = "Yordamchi fe'l + asosiy fe'l shakli",
                    explanationUzbek = "Ingliz tilida asosiy fe'l o'zi bilan kelayotgan yordamchi fe'lga qarab shakllanadi:\n\n1) do / does / did + Base form (savol va inkorda):\n• Do you work? / I didn't see him.\n\n2) can / could / will / would / should / must / may / might + Base form:\n• You must go. / She can speak English.\n\n3) am / is / are / was / were + -ing form (davomli):\n• He is sleeping. / What were you doing?\n\n4) have / has / had + Past Participle (tugallangan):\n• I have finished my task. / Where has she gone?",
                    examples = listOf(
                        MurphyExample("Where did you buy that coat?", "Bu paltoni qayerdan sotib oldingiz?", "did + buy (base form)"),
                        MurphyExample("She is working in the garden right now.", "U hozir bog'da ishlayapti.", "is + working (-ing form)"),
                        MurphyExample("They have cleaned the windows.", "Ular derazalarni yuvib bo'lishdi.", "have + cleaned (past participle)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Majhul nisbat (Passive) shakli",
                    formula = "be (am/is/are/was/were) + Past Participle (V3)",
                    explanationUzbek = "Harakatning o'zi muhim bo'lganda 'be + 3-shakl' birikmasi qo'llanadi:\n\n• This house was built in 1990. (Bu uy 1990-yilda qurilgan)\n• English is spoken all over the world. (Ingliz tili butun dunyoda gapiriladi)\n• Nobody was injured in the accident. (Hodisada hech kim jarohatlanmadi)",
                    examples = listOf(
                        MurphyExample("The car was damaged yesterday.", "Mashina kecha shikastlandi.", "was + damaged (V3)"),
                        MurphyExample("Where were you born?", "Siz qayerda tug'ilgansiz?", "were + born"),
                        MurphyExample("The butter is kept in the fridge.", "Sariyog' muzlatgichda saqlanadi.", "is + kept")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u51_ex1",
                    exerciseNumber = "51.1",
                    taskType = "CHOICE",
                    question = "I didn't ______ anything to him yesterday.",
                    options = listOf("say", "said", "saying", "says"),
                    correctOptionIndex = 0,
                    correctAnswerText = "say",
                    explanationUzbek = "'didn't' dan keyin fe'lning 1-shakli (infinitive without to) keladi: didn't say."
                ),
                MurphyExerciseItem(
                    id = "u51_ex2",
                    exerciseNumber = "51.2",
                    taskType = "CHOICE",
                    question = "Where has your brother ______?",
                    options = listOf("gone", "go", "went", "going"),
                    correctOptionIndex = 0,
                    correctAnswerText = "gone",
                    explanationUzbek = "has + 3-shakl (Past Participle) keladi: has gone."
                ),
                MurphyExerciseItem(
                    id = "u51_ex3",
                    exerciseNumber = "51.3",
                    taskType = "CHOICE",
                    question = "You must ______ your passport before traveling.",
                    options = listOf("check", "checked", "to check", "checking"),
                    correctOptionIndex = 0,
                    correctAnswerText = "check",
                    explanationUzbek = "Modal fe'llardan (must, can, should) keyin oddiy asosiy fe'l keladi: must check."
                ),
                MurphyExerciseItem(
                    id = "u51_ex4",
                    exerciseNumber = "51.4",
                    taskType = "CHOICE",
                    question = "This delicious cake was ______ by my grandmother.",
                    options = listOf("made", "make", "making", "makes"),
                    correctOptionIndex = 0,
                    correctAnswerText = "made",
                    explanationUzbek = "Passive voice: was + V3 (made)."
                ),
                MurphyExerciseItem(
                    id = "u51_ex5",
                    exerciseNumber = "51.5",
                    taskType = "CHOICE",
                    question = "Listen! Somebody is ______ the guitar.",
                    options = listOf("playing", "played", "play", "plays"),
                    correctOptionIndex = 0,
                    correctAnswerText = "playing",
                    explanationUzbek = "Hozir davom etayotgan harakat: is + -ing (playing)."
                )
            )
        ),

        // UNIT 52
        MurphyUnit(
            unitNumber = 52,
            title = "I want to do / I decided to go (to + infinitive)",
            subtitleUzbek = "O'zidan keyin 'to + fe'l' talab qiluvchi fe'llar",
            groupName = "9-Guruh: -ing and to... (Gerund & Infinitives: 51–56)",
            keyTakeawaysUzbek = listOf(
                "Ko'plab fe'llardan keyin ikkinchi fe'l 'to + fe'l' (infinitive) shaklida keladi: want to do, decide to go, hope to see.",
                "Eng ko'p ishlatiladigan fe'llar: want, plan, decide, hope, promise, refuse, offer, need, try, forget, learn.",
                "Inkor shakli: NOT TO + fe'l: I decided not to go (Bormaslikka qaror qildim).",
                "would like / would love / would prefer lardan keyin ham doim 'to' keladi: I would like to visit Paris."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "'to + infinitive' oladigan mashhur fe'llar",
                    formula = "fe'l + TO + asosiy fe'l",
                    explanationUzbek = "Quyidagi fe'llar ikkinchi harakat oldidan 'to' yuklamasini oladi:\n\n• want to: I want to sleep. (Uxlashni xohlayman)\n• decide to: We decided to go by car. (Mashinada borishga qaror qildik)\n• forget to: Don't forget to lock the door! (Eshikni qulflashni unutmang!)\n• promise to: He promised to call me. (U menga qo'ng'iroq qilishga va'da berdi)\n• hope to: I hope to see you soon. (Tez orada ko'rishamiz degan umiddaman)\n• try to: I'm trying to study. (O'qishga harakat qilyapman)\n• need to: You need to rest. (Dam olishing kerak)",
                    examples = listOf(
                        MurphyExample("They decided to sell their house.", "Ular uylarini sotishga qaror qilishdi.", "decide to sell"),
                        MurphyExample("I forgot to buy some milk.", "Biroz sut sotib olishni unutibman.", "forget to buy"),
                        MurphyExample("She refused to answer my question.", "U mening savolimga javob berishdan bosh tortdi.", "refuse to answer")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Inkor shakli: not to do",
                    formula = "fe'l + NOT TO + asosiy fe'l",
                    explanationUzbek = "Biror ishni qilmaslikka qaror qilganda yoki va'da berganda 'not to' tartibida aytiladi:\n\n• I decided not to go out because of the rain. (Yomg'ir tufayli ko'chaga chiqmaslikka qaror qildim)\n• Be careful not to drop that glass. (Ehtiyot bo'l, stakanni tushirib yuborma)\n• He promised not to be late again. (U boshqa kechikmaslikka va'da berdi)",
                    examples = listOf(
                        MurphyExample("She promised not to tell anyone.", "U hech kimga aytmaslikka va'da berdi.", "not to tell"),
                        MurphyExample("We decided not to buy the car.", "Biz mashinani sotib olmaslikka qaror qildik.", "decided not to buy")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u52_ex1",
                    exerciseNumber = "52.1",
                    taskType = "CHOICE",
                    question = "It was late, so we decided ______ a taxi.",
                    options = listOf("to take", "taking", "take", "took"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to take",
                    explanationUzbek = "'decide' fe'lidan keyin 'to + infinitive' keladi: decided to take."
                ),
                MurphyExerciseItem(
                    id = "u52_ex2",
                    exerciseNumber = "52.2",
                    taskType = "CHOICE",
                    question = "Don't forget ______ the window before leaving.",
                    options = listOf("to close", "closing", "close", "closed"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to close",
                    explanationUzbek = "'forget to do something' = biror ishni qilishni unutmoq: forget to close."
                ),
                MurphyExerciseItem(
                    id = "u52_ex3",
                    exerciseNumber = "52.3",
                    taskType = "CHOICE",
                    question = "I promised ______ late tomorrow morning.",
                    options = listOf("not to be", "to not be", "not being", "no to be"),
                    correctOptionIndex = 0,
                    correctAnswerText = "not to be",
                    explanationUzbek = "Inkor shaklida 'not to be' to'g'ri grammatik tartib hisoblanadi."
                ),
                MurphyExerciseItem(
                    id = "u52_ex4",
                    exerciseNumber = "52.4",
                    taskType = "CHOICE",
                    question = "I would like ______ a cup of hot tea, please.",
                    options = listOf("to have", "having", "have", "had"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to have",
                    explanationUzbek = "'would like to have' = xohlardim."
                ),
                MurphyExerciseItem(
                    id = "u52_ex5",
                    exerciseNumber = "52.5",
                    taskType = "CHOICE",
                    question = "He refused ______ for the broken vase.",
                    options = listOf("to pay", "paying", "pay", "paid"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to pay",
                    explanationUzbek = "'refuse' (bosh tortmoq) fe'li 'to + infinitive' talab qiladi: refused to pay."
                )
            )
        ),

        // UNIT 53
        MurphyUnit(
            unitNumber = 53,
            title = "I want you to ... / I told you to ...",
            subtitleUzbek = "Kimgadir biror ishni qilishini aytish yoki xohlash (Complex Object)",
            groupName = "9-Guruh: -ing and to... (Gerund & Infinitives: 51–56)",
            keyTakeawaysUzbek = listOf(
                "FORMULA: Fe'l + shaxs (me/you/him/her/us/them) + TO + fe'l.",
                "I want you to be happy (Sening baxtli bo'lishingni xohlayman - 'I want that you...' NOTO'G'RI).",
                "He told me to wait here (U menga shu yerda kutishimni aytdi).",
                "Ko'p ishlatiladigan fe'llar: want somebody to, tell somebody to, ask somebody to, advise somebody to, warn somebody not to.",
                "MAKE va LET fe'llaridan keyin 'to' KELMAYDI: He made me laugh (Meni kuldirdi), Let me go (Meni qo'yib yubor)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "want / ask / tell somebody to do something",
                    formula = "Ega + fe'l + shaxs olmoshi + TO + asosiy fe'l",
                    explanationUzbek = "Ingliz tilida boshqa birovning nimadir qilishini xohlash yoki buyurish quyidagicha ifodalanadi:\n\n• I want you to listen carefully. (Diqqat bilan tinglashingizni xohlayman)\n• She told him to be quiet. (U unga jim bo'lishni aytdi)\n• My doctor advised me to drink more water. (Shifokorim ko'proq suv ichishni maslahat berdi)\n• They asked us to help them. (Ular bizdan yordam so'rashdi)\n\n⚠️ O'zbek tilidagi 'xohlaymanki...' tuzilishini ingliz tilida 'I want that you...' deb tarjima qilib bo'lmaydi! Doim 'I want YOU to...' deyiladi.",
                    examples = listOf(
                        MurphyExample("Do you want me to come with you?", "Men sen bilan birga borishimni xohlaysanmi?", "want me to come"),
                        MurphyExample("The teacher told us to open our books.", "O'qituvchi kitoblarimizni ochishimizni aytdi.", "told us to open"),
                        MurphyExample("I warned him not to touch the wire.", "Unga simga tegmaslikni ogohlantirdim.", "warned him not to touch")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "make va let fe'llarida 'to' ishlatilmasligi",
                    formula = "make / let + shaxs + asosiy fe'l (TO KERAK EMAS)",
                    explanationUzbek = "Make (majburlamoq/sabab bo'lmoq) va Let (ruxsat bermoq) fe'llari o'zidan keyin 'to' yuklamasini olmaydi:\n\n• The hot weather made me feel sleepy. (Issiq ob-havo meni uyqusiratib qo'ydi - 'to feel' emas)\n• My parents don't let me stay out late. (Ota-onam kechgacha ko'chada qolishimga ruxsat bermaydi - 'to stay' emas)\n• Let me know if you need help. (Agar yordam kerak bo'lsa xabar ber)",
                    examples = listOf(
                        MurphyExample("You made me laugh so much!", "Sen meni juda kuldirding!", "made me laugh (to yo'q)"),
                        MurphyExample("Please let me finish my sentence.", "Iltimos, gapimni tugatib olishga ruxsat bering.", "let me finish")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u53_ex1",
                    exerciseNumber = "53.1",
                    taskType = "CHOICE",
                    question = "I want ______ the truth.",
                    options = listOf("you to tell me", "that you tell me", "you tell me", "you to telling me"),
                    correctOptionIndex = 0,
                    correctAnswerText = "you to tell me",
                    explanationUzbek = "'want somebody to do something' qolipi to'g'ri: want you to tell me."
                ),
                MurphyExerciseItem(
                    id = "u53_ex2",
                    exerciseNumber = "53.2",
                    taskType = "CHOICE",
                    question = "The policeman told the driver ______ faster.",
                    options = listOf("not to drive", "to not drive", "not driving", "don't drive"),
                    correctOptionIndex = 0,
                    correctAnswerText = "not to drive",
                    explanationUzbek = "'told somebody not to do something': not to drive."
                ),
                MurphyExerciseItem(
                    id = "u53_ex3",
                    exerciseNumber = "53.3",
                    taskType = "CHOICE",
                    question = "Sad movies always make me ______.",
                    options = listOf("cry", "to cry", "crying", "cried"),
                    correctOptionIndex = 0,
                    correctAnswerText = "cry",
                    explanationUzbek = "'make somebody do something' - make fe'lidan keyin 'to' ishlatilmaydi: make me cry."
                ),
                MurphyExerciseItem(
                    id = "u53_ex4",
                    exerciseNumber = "53.4",
                    taskType = "CHOICE",
                    question = "Please let me ______ this by myself.",
                    options = listOf("do", "to do", "doing", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "do",
                    explanationUzbek = "'let somebody do something' - let dan keyin ham 'to' tushib qoladi: let me do."
                ),
                MurphyExerciseItem(
                    id = "u53_ex5",
                    exerciseNumber = "53.5",
                    taskType = "CHOICE",
                    question = "She asked me ______ her with the heavy bags.",
                    options = listOf("to help", "help", "helping", "helped"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to help",
                    explanationUzbek = "'ask somebody to do something': asked me to help."
                )
            )
        ),

        // UNIT 54
        MurphyUnit(
            unitNumber = 54,
            title = "I went to the shop to buy ... (Purpose with to)",
            subtitleUzbek = "Maqsad bildiruvchi 'to + infinitive' (Maqsad infinitivi)",
            groupName = "9-Guruh: -ing and to... (Gerund & Infinitives: 51–56)",
            keyTakeawaysUzbek = listOf(
                "Biror harakatning MAQSADINI aytish uchun 'to + fe'l' ishlatiladi (= uchun / maqsadida).",
                "I went to the shop to buy milk. (Sut sotib olish uchun do'konga bordim).",
                "Nega bordim? -> To buy milk.",
                "TAQQOSLASH: 'to + fe'l' (harakat maqsadi) vs 'for + ot' (narsa uchun):",
                "I went to the store for some milk (Sut uchun).",
                "I went to the store to get some milk (Sut olish uchun)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Maqsad ifodalash: Why did you go? -> To do...",
                    formula = "Asosiy gap + TO + fe'l (maqsad)",
                    explanationUzbek = "Harakat nima sababdan yoki qanday maqsadda qilinganini aytganda 'to + fe'l' qo'yiladi:\n\n• I called him to invite him to my party. (Uni mehmondorchilikka taklif qilish uchun qo'ng'iroq qildim)\n• She went to university to study medicine. (U tibbiyotni o'rganish uchun universitetga bordi)\n• Turn on the lamp to see better. (Yaxshiroq ko'rish uchun chiroqni yoq)\n• I need money to buy a computer. (Kompyuter sotib olish uchun pul kerak)",
                    examples = listOf(
                        MurphyExample("He went out to post a letter.", "U xatni jo'natish uchun ko'chaga chiqdi.", "to post = jo'natish uchun"),
                        MurphyExample("I am saving money to travel around Europe.", "Yevropa bo'ylab sayohat qilish uchun pul yig'yapman.", "to travel"),
                        MurphyExample("Press this red button to turn it off.", "Uni o'chirish uchun shu qizil tugmani bosing.", "to turn it off")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "TO (fe'l bilan) vs FOR (ot bilan)",
                    formula = "TO + FE'L (harakat) vs FOR + OT (narsa)",
                    explanationUzbek = "Bu ikki so'zni adashtirmaslik qoidasi juda oddiy:\n\n1) TO + Fe'l:\n• I went to the supermarket to buy bread.\n• We stopped at the cafe to have lunch.\n\n2) FOR + Ot:\n• I went to the supermarket for some bread.\n• We stopped at the cafe for lunch.\n\n⚠️ 'for buy bread' yoki 'for to buy' DEYILMAYDI!",
                    examples = listOf(
                        MurphyExample("Are you going out for a walk?", "Sayr qilish uchun (sayrga) chiqayapsanmi?", "for a walk (ot)"),
                        MurphyExample("Are you going out to have a walk?", "Sayr qilish uchun chiqayapsanmi?", "to have a walk (fe'l)"),
                        MurphyExample("I came here to see you.", "Bu yerga sizni ko'rish uchun keldim.", "to see you")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u54_ex1",
                    exerciseNumber = "54.1",
                    taskType = "CHOICE",
                    question = "I sat down on the bench ______ a rest.",
                    options = listOf("to have", "for to have", "for having", "for have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to have",
                    explanationUzbek = "Harakat maqsadi fe'l bilan ifodalanganda 'to have' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u54_ex2",
                    exerciseNumber = "54.2",
                    taskType = "CHOICE",
                    question = "We went to a restaurant ______ dinner.",
                    options = listOf("for", "to", "at", "about"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "'dinner' ot bo'lgani sababli 'for dinner' to'g'ri ('to dinner' emas)."
                ),
                MurphyExerciseItem(
                    id = "u54_ex3",
                    exerciseNumber = "54.3",
                    taskType = "CHOICE",
                    question = "He is studying hard ______ his university exams.",
                    options = listOf("to pass", "for pass", "pass", "for to pass"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to pass",
                    explanationUzbek = "Imtihonlardan o'tish uchun -> 'to pass his exams'."
                ),
                MurphyExerciseItem(
                    id = "u54_ex4",
                    exerciseNumber = "54.4",
                    taskType = "CHOICE",
                    question = "Why did you call me? - ______ you about the meeting.",
                    options = listOf("To tell", "For tell", "Telling", "For to tell"),
                    correctOptionIndex = 0,
                    correctAnswerText = "To tell",
                    explanationUzbek = "Nega qo'ng'iroq qildingiz degan savolga maqsad 'To tell...' deb javob beriladi."
                ),
                MurphyExerciseItem(
                    id = "u54_ex5",
                    exerciseNumber = "54.5",
                    taskType = "CHOICE",
                    question = "I went to the bakery ______ some fresh bread.",
                    options = listOf("for", "to", "with", "from"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "'for some fresh bread' (ot birikmasi bilan 'for' keladi)."
                )
            )
        ),

        // UNIT 55
        MurphyUnit(
            unitNumber = 55,
            title = "go to ... / go on ... / go for ... / go -ing",
            subtitleUzbek = "'Go' fe'lining predloglar va -ing bilan kelishi",
            groupName = "9-Guruh: -ing and to... (Gerund & Infinitives: 51–56)",
            keyTakeawaysUzbek = listOf(
                "GO TO + joy/shahar/mamlakat: go to work, go to London, go to bed, go to school.",
                "GO HOME (to ishlatilmaydi!): go home.",
                "GO ON + tadbir/safarlar: go on holiday, go on a trip, go on strike, go on a cruise.",
                "GO FOR + harakat oti: go for a walk, go for a run, go for a swim, go for a coffee.",
                "GO + -ing (sport va mashg'ulotlar): go swimming, go shopping, go skiing, go running."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Go to / Go on / Go for farqlari",
                    formula = "go to (joy) | go on (safarlar) | go for (sayr/ichimlik)",
                    explanationUzbek = "Go fe'li turli predloglar bilan turli tushunchalarni ifodalaydi:\n\n1) GO TO (yo'nalish/joy):\n• go to bed (yotishga bormoq)\n• go to Paris / go to work / go to university\n⚠️ Istisno: go home (to yo'q!)\n\n2) GO ON (sayohat va tadbirlar):\n• go on holiday (ta'tilga chiqmoq)\n• go on a trip / go on a tour\n\n3) GO FOR (dam olish harakatlari):\n• go for a walk (sayrga chiqmoq)\n• go for a swim (cho'milgani bormoq)\n• go for a drink (ichimlik ichgani bormoq)",
                    examples = listOf(
                        MurphyExample("What time do you usually go to bed?", "Odatda soat nechada yotishga borasiz?", "go to bed"),
                        MurphyExample("They are going on holiday next week.", "Ular keyingi hafta ta'tilga ketishyapti.", "go on holiday"),
                        MurphyExample("It's a lovely sunny day. Let's go for a walk.", "Ajoyib quyoshli kun. Yur, sayrga chiqamiz.", "go for a walk")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Go + -ing (Sport va faoliyatlar)",
                    formula = "go + fe'l(-ing) (predlogsiz)",
                    explanationUzbek = "Sport, sevimli mashg'ulot yoki xarid qilish haqida gapirilganda 'go' dan so'ng darhol '-ing' fe'li keladi:\n\n• go shopping (bozorlik qilgani bormoq)\n• go swimming (suzgani bormoq)\n• go fishing (baliq oviga bormoq)\n• go running / go jogging (yugurgani bormoq)\n• go skiing (chang'i uchgani bormoq)",
                    examples = listOf(
                        MurphyExample("I go shopping every Saturday morning.", "Har shanba ertalab bozorlikka boraman.", "go shopping"),
                        MurphyExample("Do you want to go swimming tomorrow?", "Ertaga suzgani borishni xohlaysanmi?", "go swimming"),
                        MurphyExample("He loves to go fishing on weekends.", "U dam olish kunlari baliq oviga borishni yaxshi ko'radi.", "go fishing")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u55_ex1",
                    exerciseNumber = "55.1",
                    taskType = "CHOICE",
                    question = "I am very tired. I want to go ______ right now.",
                    options = listOf("home", "to home", "at home", "in home"),
                    correctOptionIndex = 0,
                    correctAnswerText = "home",
                    explanationUzbek = "'home' so'zi oldidan yo'nalish predlogi 'to' ishlatilmaydi: go home."
                ),
                MurphyExerciseItem(
                    id = "u55_ex2",
                    exerciseNumber = "55.2",
                    taskType = "CHOICE",
                    question = "They have gone ______ holiday to Spain.",
                    options = listOf("on", "to", "for", "at"),
                    correctOptionIndex = 0,
                    correctAnswerText = "on",
                    explanationUzbek = "Ta'tilga ketmoq = 'go on holiday'."
                ),
                MurphyExerciseItem(
                    id = "u55_ex3",
                    exerciseNumber = "55.3",
                    taskType = "CHOICE",
                    question = "Would you like to go ______ a walk in the park?",
                    options = listOf("for", "to", "on", "with"),
                    correctOptionIndex = 0,
                    correctAnswerText = "for",
                    explanationUzbek = "Sayrga chiqmoq = 'go for a walk'."
                ),
                MurphyExerciseItem(
                    id = "u55_ex4",
                    exerciseNumber = "55.4",
                    taskType = "CHOICE",
                    question = "My sister often goes ______ in the sea during summer.",
                    options = listOf("swimming", "to swim", "for swim", "swim"),
                    correctOptionIndex = 0,
                    correctAnswerText = "swimming",
                    explanationUzbek = "Faoliyat turi uchun: 'go swimming'."
                ),
                MurphyExerciseItem(
                    id = "u55_ex5",
                    exerciseNumber = "55.5",
                    taskType = "CHOICE",
                    question = "She goes ______ work by subway every day.",
                    options = listOf("to", "at", "on", "for"),
                    correctOptionIndex = 0,
                    correctAnswerText = "to",
                    explanationUzbek = "Ishga bormoq = 'go to work'."
                )
            )
        ),

        // UNIT 56
        MurphyUnit(
            unitNumber = 56,
            title = "I like playing / I enjoy doing (-ing vs to...)",
            subtitleUzbek = "Gerundiy (-ing) yoki Infinitiv (to...) talab qiluvchi fe'llar",
            groupName = "9-Guruh: -ing and to... (Gerund & Infinitives: 51–56)",
            keyTakeawaysUzbek = listOf(
                "Faqat -ING oladigan fe'llar: enjoy doing, stop doing, finish doing, mind doing, suggest doing, keep (on) doing.",
                "Faqat TO... oladigan fe'llar: decide to do, hope to do, promise to do, refuse to do, want to do.",
                "IKKALASINI HAM oladigan (ma'nosi o'xshash) fe'llar: like, love, hate, start, begin, continue.",
                "Misol: I like reading books = I like to read books.",
                "Predloglardan (in, on, at, about, without, before, after) keyin doim -ING keladi: good at speaking, before going."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Faqat -ing (Gerundiy) oladigan asosiy fe'llar",
                    formula = "fe'l + FE'L(-ING)",
                    explanationUzbek = "Ba'zi fe'llardan keyin ikkinchi fe'l faqat '-ing' qo'shimchasi bilan keladi:\n\n• enjoy: I enjoy dancing. (Raqsga tushishdan zavqlanaman)\n• finish: Have you finished reading the newspaper? (Gazetani o'qib bo'ldingizmi?)\n• stop: It has stopped raining. (Yomg'ir yog'ishi to'xtadi)\n• mind: Do you mind closing the door? (Eshikni yopib yuborishga qarshi emasmisiz?)\n• suggest: He suggested going to the cinema. (U kinoga borishni taklif qildi)\n• keep / keep on: Why do you keep interrupting me? (Nega tinmay gapimni bo'lasiz?)",
                    examples = listOf(
                        MurphyExample("I don't mind waiting a few minutes.", "Bir necha daqiqa kutishga qarshi emasman.", "mind waiting"),
                        MurphyExample("Suddenly everyone stopped talking.", "To'satdan hamma gapirishdan to'xtadi.", "stopped talking"),
                        MurphyExample("She enjoys listening to classical music.", "U mumtoz musiqani tinglashdan rohatlanadi.", "enjoys listening")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Predloglar + -ing qoidasi",
                    formula = "Predlog (about, of, for, in, without, before, after) + FE'L(-ING)",
                    explanationUzbek = "Ingliz tilidagi eng qat'iy qoidalardan biri: Har qanday predlogdan keyin fe'l kelsa, unga albatta '-ing' qo'shiladi:\n\n• He left without saying goodbye. (U xayrlashmasdan ketdi)\n• Thank you for helping me. (Yordam berganingiz uchun rahmat)\n• I am interested in learning languages. (Tillar o'rganishga qiziqaman)\n• Before going to bed, turn off the light. (Uxlashga yotishdan oldin chiroqni o'chiring)\n• Are you good at cooking? (Ovqat pishirishga ustamisiz?)",
                    examples = listOf(
                        MurphyExample("She is afraid of flying in planes.", "U samolyotda uchishdan qo'rqadi.", "afraid of flying"),
                        MurphyExample("After having lunch, we went back to work.", "Tushlik qilgandan keyin ishga qaytdik.", "after having lunch"),
                        MurphyExample("What about going for a walk?", "Sayr qilishga nima deysiz?", "what about going")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u56_ex1",
                    exerciseNumber = "56.1",
                    taskType = "CHOICE",
                    question = "I really enjoy ______ to music when I am studying.",
                    options = listOf("listening", "to listen", "listen", "listened"),
                    correctOptionIndex = 0,
                    correctAnswerText = "listening",
                    explanationUzbek = "'enjoy' fe'lidan keyin doim -ing shakli keladi: enjoy listening."
                ),
                MurphyExerciseItem(
                    id = "u56_ex2",
                    exerciseNumber = "56.2",
                    taskType = "CHOICE",
                    question = "Have you finished ______ your homework yet?",
                    options = listOf("doing", "to do", "do", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "doing",
                    explanationUzbek = "'finish' fe'lidan keyin gerundiy (-ing) qo'llanadi: finished doing."
                ),
                MurphyExerciseItem(
                    id = "u56_ex3",
                    exerciseNumber = "56.3",
                    taskType = "CHOICE",
                    question = "He left the meeting without ______ anything.",
                    options = listOf("saying", "to say", "say", "said"),
                    correctOptionIndex = 0,
                    correctAnswerText = "saying",
                    explanationUzbek = "'without' predlogidan keyin fe'lga -ing qo'shiladi: without saying."
                ),
                MurphyExerciseItem(
                    id = "u56_ex4",
                    exerciseNumber = "56.4",
                    taskType = "CHOICE",
                    question = "Do you mind ______ the window? It's cold in here.",
                    options = listOf("closing", "to close", "close", "closed"),
                    correctOptionIndex = 0,
                    correctAnswerText = "closing",
                    explanationUzbek = "'Do you mind + -ing': do you mind closing."
                ),
                MurphyExerciseItem(
                    id = "u56_ex5",
                    exerciseNumber = "56.5",
                    taskType = "CHOICE",
                    question = "Thank you very much for ______ me with this problem.",
                    options = listOf("helping", "to help", "help", "helped"),
                    correctOptionIndex = 0,
                    correctAnswerText = "helping",
                    explanationUzbek = "'for' predlogi + -ing: for helping me."
                )
            )
        )
    )
}
