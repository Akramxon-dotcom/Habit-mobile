package com.example.data.model

object MurphyBookPronounsData {

    val UNITS_PRONOUNS: List<MurphyUnit> = listOf(
        // UNIT 37
        MurphyUnit(
            unitNumber = 37,
            title = "there is / there are",
            subtitleUzbek = "Mavjudlik va borlik ifodasi: there is (birlik) va there are (ko'plik)",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "THERE IS = Biror joyda bitta narsa/shaxs mavjudligini bildiradi (There is a book on the table).",
                "THERE ARE = Biror joyda birdan ortiq narsalar borligini bildiradi (There are three apples).",
                "Inkor: there isn't (there is no...) va there aren't (there are no...).",
                "Savol: Is there...? (Bormi?) va Are there...? (Bormi?).",
                "O'tgan zamon: there was (bitta edi) va there were (ko'p edi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "there is va there are (Hozirgi mavjudlik)",
                    formula = "There is + birlik ot | There are + ko'plik ot",
                    explanationUzbek = "Biror joyda biror narsa yoki shaxs borligini birinchi marta aytayotganda ishlatiladi:\n\n• There is a big tree in the garden. (Bog'da katta daraxt bor).\n• There are 7 days in a week. (Bir haftada 7 kun bor).\n• There's a train at 10.30. (Soat 10:30 da poyezd bor).\n• There are a lot of people at the bus stop. (Avtobus bekatida ko'p odamlar bor).\n\n⚠️ 'There is' ko'pincha 'There's' shaklida qisqaradi, lekin 'there are' yozuvda qisqarmaydi.",
                    examples = listOf(
                        MurphyExample("There is a good restaurant near here.", "Bu yaqin atrofda yaxshi restoran bor.", "Birlik ot: a restaurant"),
                        MurphyExample("There are twenty students in the classroom.", "Sinfda yigirmata o'quvchi bor.", "Ko'plik ot: twenty students"),
                        MurphyExample("There's someone at the door.", "Eshik oldida kimdir bor.", "There's = There is")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Savol va Inkor shakllari",
                    formula = "Savol: Is there...? / Are there...? | Inkor: There isn't / There aren't",
                    explanationUzbek = "Savol berishda 'is' yoki 'are' so'zi 'there' dan oldinga o'tadi:\n\n• Is there a bank near here? - Yes, there is. / No, there isn't. (Bu yerga yaqin bank bormi?)\n• Are there any questions? - Yes, there are. / No, there aren't. (Savollar bormi?)\n• How many players are there in a football team? (Futbol jamoasida nechta o'yinchi bor?)\n\nInkor:\n• There isn't any milk in the fridge. (Muzlatgichda sut yo'q).\n• There aren't any mistakes in your test. (Testingizda xatolar yo'q).",
                    examples = listOf(
                        MurphyExample("Is there a post office near here?", "Bu atrofda pochta bormi?", "Birlikdagi savol"),
                        MurphyExample("Are there any letters for me today?", "Bugun menga biror xat bormi?", "Ko'plikdagi savol"),
                        MurphyExample("There aren't many cars on the road today.", "Bugun yo'lda mashinalar ko'p emas.", "There aren't")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "O'tgan zamon: there was va there were",
                    formula = "O'tgan zamon: there was (birlik) | there were (ko'plik)",
                    explanationUzbek = "Ilgari yoki o'tmishda biror narsa bo'lganini aytish uchun:\n\n• There was an accident last night. (Kecha kechqurun avariya bo'ldi/bor edi).\n• There were many people at the meeting yesterday. (Kecha majlisda ko'p odamlar bor edi).\n• Was there any problem? (Biror muammo bo'ldimi?)\n• There weren't any empty seats on the bus. (Avtobusda bo'sh o'rindiqlar yo'q edi).",
                    examples = listOf(
                        MurphyExample("There was a good film on TV yesterday.", "Kecha televizorda yaxshi film bor edi.", "There was (birlik)"),
                        MurphyExample("There were ten people waiting outside.", "Tashqarida o'n kishi kutib turgan edi.", "There were (ko'plik)"),
                        MurphyExample("There wasn't any coffee left.", "Hech qancha kofe qolmagan edi.", "Inkor o'tgan zamon")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u37_ex1",
                    exerciseNumber = "37.1",
                    taskType = "CHOICE",
                    question = "______ a nice coffee shop across the street.",
                    options = listOf("There is", "There are", "It is", "They are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "There is",
                    explanationUzbek = "Birlikdagi ot ('a nice coffee shop') uchun 'There is' to'g'ri keladi."
                ),
                MurphyExerciseItem(
                    id = "u37_ex2",
                    exerciseNumber = "37.2",
                    taskType = "CHOICE",
                    question = "How many days ______ in February this year?",
                    options = listOf("are there", "is there", "there are", "there is"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are there",
                    explanationUzbek = "Ko'plikdagi savol shakli: 'are there' ('How many days are there...?')."
                ),
                MurphyExerciseItem(
                    id = "u37_ex3",
                    exerciseNumber = "37.3",
                    taskType = "CHOICE",
                    question = "Excuse me, ______ a pharmacy near here?",
                    options = listOf("is there", "are there", "there is", "has there"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is there",
                    explanationUzbek = "Birlikdagi savol: 'Is there a pharmacy...?' (Dorixona bormi?)."
                ),
                MurphyExerciseItem(
                    id = "u37_ex4",
                    exerciseNumber = "37.4",
                    taskType = "CHOICE",
                    question = "Yesterday, ______ a lot of traffic on the city center roads.",
                    options = listOf("there was", "there were", "there is", "it was"),
                    correctOptionIndex = 0,
                    correctAnswerText = "there was",
                    explanationUzbek = "'traffic' sanalmaydigan birlik ot va 'yesterday' bo'lgani sababli 'there was' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u37_ex5",
                    exerciseNumber = "37.5",
                    taskType = "CHOICE",
                    question = "There ______ any letters for you in the mailbox this morning.",
                    options = listOf("weren't", "wasn't", "isn't", "aren't to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "weren't",
                    explanationUzbek = "'letters' ko'plikda va 'this morning' o'tgan zamon bo'lgani sababli 'weren't' to'g'ri."
                )
            )
        ),

        // UNIT 38
        MurphyUnit(
            unitNumber = 38,
            title = "there... and it...",
            subtitleUzbek = "there va it o'rtasidagi asosiy farqlar (Mavjudlik vs Ob-havo, vaqt, aniq narsa)",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "THERE = birinchi bor biror narsaning borligi/mavjudligini aytganda (There is a new restaurant in town).",
                "IT = o'sha oldindan ma'lum bo'lgan aniq narsaga ishora qilganda (It is very good).",
                "IT = Vaqt, masofa va ob-havo haqida gapirganda yagona vosita: It's 10 o'clock, It's raining, It's 5 km.",
                "Taqqoslash: 'There is a train at 9' (Poyezd bor) vs 'It is fast' (U poyezd tezyurar).",
                "Masofa: 'It is a long way from here to the airport'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "there vs it (Mavjudlik vs O'sha narsa)",
                    formula = "There + mavjudlik | It + o'sha narsa",
                    explanationUzbek = "Biror yangi narsa haqida xabar berishda 'there', o'sha aytilgan narsaning xususiyatini bildirishda 'it' ishlatiladi:\n\n• There is a new restaurant in town. It is very expensive. (Shaharda yangi restoran bor. U juda qimmat).\n• There was a key on the table. Is it your key? (Stolda kalit bor edi. U sizning kalitingizmi?).",
                    examples = listOf(
                        MurphyExample("There's a book on the table. It is mine.", "Stolda kitob bor. U meniki.", "There (mavjudlik) -> It (kitob)"),
                        MurphyExample("I like this jacket. It is very warm.", "Menga bu kurtka yoqdi. U juda issiq.", "It = the jacket"),
                        MurphyExample("There is a strange noise. What is it?", "G'alati tovush kelyapti. U nima ekan?", "There vs it")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "IT: Vaqt, Ob-havo va Masofa",
                    formula = "It is + time / weather / distance",
                    explanationUzbek = "Vaqt, ob-havo va masofa kabi tushunchalarda doimo 'It' (ega sifatida) qo'llaniladi ('there' aslo ishlatilmaydi!):\n\n1. Vaqt va kunlar:\n• What time is it? - It's half past ten. (Soat necha? - 10:30).\n• It's Monday today. / It's my birthday.\n\n2. Ob-havo:\n• It's raining. / It is cold today. / It snowed yesterday.\n\n3. Masofa:\n• It is 3 miles from our house to the city centre. (Uyimizdan markazgacha 3 mil).",
                    examples = listOf(
                        MurphyExample("It's windy today, but it isn't cold.", "Bugun shamolli, ammo sovuq emas.", "Ob-havo: It is"),
                        MurphyExample("How far is it from London to Paris?", "Londondan Parijgacha qancha masofa?", "Masofa savoli: How far is it?"),
                        MurphyExample("It's time to go home.", "Uyga ketish vaqti bo'ldi.", "Vaqt: It's time")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "It is nice to... (Sifat + to fe'l)",
                    formula = "It is + sifat + to + fe'l (V1)",
                    explanationUzbek = "Biror ish-harakatni baholashda 'It is + sifat + to' tuzilmasi juda keng qo'llaniladi:\n\n• It is nice to meet you. (Siz bilan uchrashganimdan xursandman).\n• It is impossible to understand him. (Uni tushunishning umuman imkoni yo'q).\n• It is dangerous to walk alone at night. (Kechasi yolg'iz yurish xavfli).\n• It is easy to make mistakes. (Xato qilish oson).",
                    examples = listOf(
                        MurphyExample("It is difficult to learn Chinese.", "Xitoy tilini o'rganish qiyin.", "It is difficult to + V1"),
                        MurphyExample("It was great to see you again.", "Sizni yana ko'rganimdan juda xursand bo'ldim.", "O'tgan zamonda: It was great to...")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u38_ex1",
                    exerciseNumber = "38.1",
                    taskType = "CHOICE",
                    question = "______ is raining heavily outside. Take an umbrella.",
                    options = listOf("It", "There", "He", "That"),
                    correctOptionIndex = 0,
                    correctAnswerText = "It",
                    explanationUzbek = "Ob-havo holatlari uchun doimo 'It is raining' ishlatiladi ('There' emas)."
                ),
                MurphyExerciseItem(
                    id = "u38_ex2",
                    exerciseNumber = "38.2",
                    taskType = "CHOICE",
                    question = "______ is a bus stop in front of the supermarket.",
                    options = listOf("There", "It", "They", "He"),
                    correctOptionIndex = 0,
                    correctAnswerText = "There",
                    explanationUzbek = "Joyda bekat borligini (mavjudligini) bildirish uchun 'There is' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u38_ex3",
                    exerciseNumber = "38.3",
                    taskType = "CHOICE",
                    question = "How far is ______ from Tashkent to Samarkand?",
                    options = listOf("it", "there", "this", "that"),
                    correctOptionIndex = 0,
                    correctAnswerText = "it",
                    explanationUzbek = "Masofa so'rashda standart ibora: 'How far is it...?'"
                ),
                MurphyExerciseItem(
                    id = "u38_ex4",
                    exerciseNumber = "38.4",
                    taskType = "CHOICE",
                    question = "______ is very important to get enough sleep.",
                    options = listOf("It", "There", "He", "That"),
                    correctOptionIndex = 0,
                    correctAnswerText = "It",
                    explanationUzbek = "'It is + sifat + to...' tuzilmasi: 'It is very important to get enough sleep'."
                ),
                MurphyExerciseItem(
                    id = "u38_ex5",
                    exerciseNumber = "38.5",
                    taskType = "CHOICE",
                    question = "'What's that noise?' - '______ is just the wind.'",
                    options = listOf("It", "There", "They", "Are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "It",
                    explanationUzbek = "Aniq shovqin nima ekanini aytganda 'It is just the wind' deyiladi."
                )
            )
        ),

        // UNIT 39
        MurphyUnit(
            unitNumber = 39,
            title = "I am, I don't, etc. (Short answers)",
            subtitleUzbek = "Qisqa javoblar va tasdiqlash: I am / I do / I can / Have you?",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida butun gapni qaytarmaslik uchun YORDAMCHI FE'L bilan qisqa javob beriladi.",
                "'Are you tired?' - 'Yes, I am.' / 'No, I'm not.' (Yes, I am tired deb o'tirish shart emas).",
                "Present Simple uchun DO/DOES: 'Do you like tea?' - 'Yes, I do.' / 'No, I don't.'",
                "Past Simple uchun DID: 'Did you lock the door?' - 'Yes, I did.'",
                "Diqqat: Tasdiq qisqa javobda qisqartma qilinmaydi: 'Yes, I am' (Yes, I'm deb aytilmaydi!)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Yordamchi fe'l bilan qisqa javob berish",
                    formula = "Yes, ega + yordamchi fe'l | No, ega + yordamchi fe'l + not",
                    explanationUzbek = "Ingliz tilida suhbatda butun gapni takrorlamaslik madaniyati bor. Savol qaysi yordamchi fe'l bilan berilsa, javob ham o'sha bilan qaytariladi:\n\n• 'Are you coming with us?' - 'Yes, I am.' / 'No, I'm not.'\n• 'Can you speak German?' - 'Yes, I can.' / 'No, I can't.'\n• 'Has she lived here long?' - 'Yes, she has.' / 'No, she hasn't.'\n\n⚠️ Eslatma: Tasdiq javobda 'Yes, I am' deyiladi, hech qachon 'Yes, I'm' deyilmaydi!",
                    examples = listOf(
                        MurphyExample("'Are you ready?' - 'Yes, I am.'", "Tayyormisiz? - Ha, tayyorman.", "To be bilan javob"),
                        MurphyExample("'Can you swim?' - 'No, I can't.'", "Suzishni bilasizmi? - Yo'q, bilmayman.", "Modal can bilan javob"),
                        MurphyExample("'Is it raining?' - 'No, it isn't.'", "Yomg'ir yog'yaptimi? - Yo'q, yog'mayapti.", "Qisqa inkor")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Do / Does / Did bilan qisqa javob",
                    formula = "Hozirgi zamon: do/does | O'tgan zamon: did",
                    explanationUzbek = "Asosiy fe'lli (Present Simple va Past Simple) gaplarda do, does yoki did yordamchisi ishlatiladi:\n\n• 'Do you like coffee?' - 'Yes, I do.' / 'No, I don't.'\n• 'Does Mark live in London?' - 'Yes, he does.' / 'No, he doesn't.'\n• 'Did you enjoy the movie?' - 'Yes, I did.' / 'No, I didn't.'",
                    examples = listOf(
                        MurphyExample("'Do you drive?' - 'No, I don't.'", "Mashina haydaysizmi? - Yo'q.", "Do bilan javob"),
                        MurphyExample("'Did they win the game?' - 'Yes, they did.'", "Ular o'yinda yutishdimi? - Ha.", "Did bilan javob"),
                        MurphyExample("'Does she speak French?' - 'Yes, she does.'", "U fransuzcha gapiradimi? - Ha.", "Does bilan javob")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Qiziqish bildirish: Have you? / Did you? (Rostdanmi?)",
                    formula = "Yordamchi fe'l + ega? (Hayrat va qiziqish)",
                    explanationUzbek = "Suhbatdosh sizga biror gap aytganda, unga e'tibor berayotganingizni va qiziqayotganingizni bildirish uchun aks-savol berasiz:\n\n• 'I've bought a new car.' - 'Have you? What color is it?' (Yangi mashina oldim. - Rostdanmi? Rangi qanaqa?)\n• 'Tim doesn't eat meat.' - 'Doesn't he? Is he a vegetarian?' (Tim go'sht yemaydi. - Shundaymi? U vegetarianmi?)\n• 'We went to Rome last week.' - 'Did you? Did you like it?' (O'tgan hafta Rimga bordik. - Rostdanmi? Sizga yoqdimi?).",
                    examples = listOf(
                        MurphyExample("'I'm getting married.' - 'Are you? Congratulations!'", "Uylanyapman. - Rostdanmi? Tabriklayman!", "E'tibor bildirish: Are you?"),
                        MurphyExample("'I passed my driving test.' - 'Did you? Well done!'", "Haydovchilik imtihonidan o'tdim. - Rostdanmi? Balli!", "Did you?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u39_ex1",
                    exerciseNumber = "39.1",
                    taskType = "CHOICE",
                    question = "'Do you like watching horror movies?' - 'No, ______.'",
                    options = listOf("I don't", "I'm not", "I haven't", "I didn't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "I don't",
                    explanationUzbek = "Savol 'Do you...?' bo'lgani uchun inkor javob 'No, I don't' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u39_ex2",
                    exerciseNumber = "39.2",
                    taskType = "CHOICE",
                    question = "'Are you ready to leave now?' - 'Yes, ______.'",
                    options = listOf("I am", "I'm", "I do", "I have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "I am",
                    explanationUzbek = "Tasdiq qisqa javobda to'liq shakl ishlatiladi: 'Yes, I am' ('Yes, I'm' deyilmaydi!)."
                ),
                MurphyExerciseItem(
                    id = "u39_ex3",
                    exerciseNumber = "39.3",
                    taskType = "CHOICE",
                    question = "'Did you see Paul yesterday?' - 'Yes, ______.'",
                    options = listOf("I did", "I saw", "I do", "I was"),
                    correctOptionIndex = 0,
                    correctAnswerText = "I did",
                    explanationUzbek = "'Did' bilan berilgan savolga qisqa javob 'Yes, I did' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u39_ex4",
                    exerciseNumber = "39.4",
                    taskType = "CHOICE",
                    question = "'I've lost my keys.' - '______? Where did you last see them?'",
                    options = listOf("Have you", "Did you", "Are you", "Do you"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Have you",
                    explanationUzbek = "'I have lost...' gapiga e'tibor va hayrat aks-savoli: 'Have you?'."
                ),
                MurphyExerciseItem(
                    id = "u39_ex5",
                    exerciseNumber = "39.5",
                    taskType = "CHOICE",
                    question = "'Can you play the guitar?' - 'No, but my sister ______.'",
                    options = listOf("can", "is", "does", "could"),
                    correctOptionIndex = 0,
                    correctAnswerText = "can",
                    explanationUzbek = "Qobiliyat 'can' haqida gap ketayotgani uchun 'my sister can' to'g'ri."
                )
            )
        ),

        // UNIT 40
        MurphyUnit(
            unitNumber = 40,
            title = "Have you? / Are you? / Don't you? (Question tags)",
            subtitleUzbek = "Tasdiq so'roqlari (Question tags): ..., isn't it? / ..., don't you? (shunday emasmi?)",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "QUESTION TAGS = Gap oxiriga qo'shiladigan '...shunday emasmi?' so'rog'i.",
                "Qoida: Agar asosiy gap TASDIQ bo'lsa, tag INKOR bo'ladi ('It's cold, isn't it?').",
                "Qoida: Agar asosiy gap INKOR bo'lsa, tag TASDIQ bo'ladi ('You aren't tired, are you?').",
                "Present Simple da: 'You speak English, don't you?' (don't/doesn't).",
                "Past Simple da: 'You locked the door, didn't you?' (didn't)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Tasdiq gap + Inkor tag (..., isn't it?)",
                    formula = "Tasdiq gap (+) , inkor yordamchi (-) + olmosh?",
                    explanationUzbek = "Siz suhbatdoshdan gapingizni tasdiqlashini yoki roziligini so'raganingizda gap oxiriga mini-savol qo'shasiz:\n\n• It's a lovely day, isn't it? - Yes, beautiful. (Ajoyib kun, shunday emasmi?)\n• You are hungry, aren't you? (Qorningiz och, to'g'rimi?)\n• Tom has lived here a long time, hasn't he? (Tom bu yerda ancha yashagan, shundaymi?)\n• They can speak Russian, can't they? (Ular ruscha gaplasha olishadi, shunday emasmi?).",
                    examples = listOf(
                        MurphyExample("You closed the window, didn't you?", "Derazani yopdingiz, shunday emasmi?", "O'tgan zamon tag: didn't you?"),
                        MurphyExample("She works in a bank, doesn't she?", "U bankda ishlaydi, shunday emasmi?", "Present Simple tag: doesn't she?"),
                        MurphyExample("You'll come tomorrow, won't you?", "Ertaga kelasiz, shundaymi?", "Kelasi zamon: won't you?")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Inkor gap + Tasdiq tag (..., is it?)",
                    formula = "Inkor gap (-) , tasdiq yordamchi (+) + olmosh?",
                    explanationUzbek = "Agar asosiy gapda inkor (not / never / no) bo'lsa, tag savoli musbat (tasdiq) shaklda bo'ladi:\n\n• It isn't cold today, is it? - No, it's warm. (Bugun sovuq emas, shundaymi?)\n• You don't like spicy food, do you? (Achchiq ovqatni yoqtirmaysiz, to'g'rimi?)\n• You haven't seen my keys, have you? (Kalitlarimni ko'rmadingizmi mabodo?)\n• Sue didn't call, did she? (Su qo'ng'iroq qilmadi, shundaymi?).",
                    examples = listOf(
                        MurphyExample("They aren't coming, are they?", "Ular kelishmayapti, shundaymi?", "are they?"),
                        MurphyExample("You won't be late, will you?", "Kech qolmaysiz, shunday emasmi?", "will you?"),
                        MurphyExample("He can't drive, can he?", "U mashina hayday olmaydi, shundaymi?", "can he?")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Maxsus holatlar: Let's, I am va Don't",
                    formula = "Let's -> shall we? | I'm -> aren't I? | Buyruq -> will you?",
                    explanationUzbek = "Bir nechta istisno va maxsus tag savollar mavjud:\n\n• I'm late, aren't I? (Kech qoldim, shunday emasmi? - 'am I not' o'rniga doim 'aren't I').\n• Let's go for a walk, shall we? (Sayrga chiqamiz, maylimi? - Let's ga doim 'shall we').\n• Close the window, will you? (Derazani yopib yuboring, xo'pmi? - Iltimos/buyruqqa 'will you').\n• Don't be late, will you? (Kech qolma, xo'pmi?).",
                    examples = listOf(
                        MurphyExample("Let's have a break, shall we?", "Dam olamiz, maylimi?", "Let's -> shall we?"),
                        MurphyExample("I am right, aren't I?", "Men haqman, shunday emasmi?", "I am -> aren't I?"),
                        MurphyExample("Don't forget to write, will you?", "Yozishni unutma, xo'pmi?", "will you?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u40_ex1",
                    exerciseNumber = "40.1",
                    taskType = "CHOICE",
                    question = "It's a beautiful day today, ______?",
                    options = listOf("isn't it", "is it", "doesn't it", "aren't it"),
                    correctOptionIndex = 0,
                    correctAnswerText = "isn't it",
                    explanationUzbek = "Tasdiq 'It's...' ga inkor tag 'isn't it?' to'g'ri keladi."
                ),
                MurphyExerciseItem(
                    id = "u40_ex2",
                    exerciseNumber = "40.2",
                    taskType = "CHOICE",
                    question = "You don't eat meat, ______?",
                    options = listOf("do you", "don't you", "are you", "haven't you"),
                    correctOptionIndex = 0,
                    correctAnswerText = "do you",
                    explanationUzbek = "Inkor 'You don't...' ga musbat tag 'do you?' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u40_ex3",
                    exerciseNumber = "40.3",
                    taskType = "CHOICE",
                    question = "David went to university, ______?",
                    options = listOf("didn't he", "did he", "doesn't he", "wasn't he"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't he",
                    explanationUzbek = "Past Simple tasdiq fe'li ('went') uchun tag: 'didn't he?'."
                ),
                MurphyExerciseItem(
                    id = "u40_ex4",
                    exerciseNumber = "40.4",
                    taskType = "CHOICE",
                    question = "Let's go to the cinema tonight, ______?",
                    options = listOf("shall we", "will we", "don't we", "aren't we"),
                    correctOptionIndex = 0,
                    correctAnswerText = "shall we",
                    explanationUzbek = "'Let's...' taklif mayli uchun maxsus tag har doim 'shall we?' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u40_ex5",
                    exerciseNumber = "40.5",
                    taskType = "CHOICE",
                    question = "You won't tell anyone my secret, ______?",
                    options = listOf("will you", "won't you", "do you", "can you"),
                    correctOptionIndex = 0,
                    correctAnswerText = "will you",
                    explanationUzbek = "Inkor 'You won't...' gapiga musbat tag 'will you?' qo'yiladi."
                )
            )
        ),

        // UNIT 41
        MurphyUnit(
            unitNumber = 41,
            title = "too / either so am I / neither do I",
            subtitleUzbek = "Qo'shilish va hamfikrlik: too / either va So do I / Neither do I (Men ham)",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "TOO = Tasdiq gapga qo'shilish: 'I'm happy' - 'I'm happy too' (Men ham xursandman).",
                "EITHER = Inkor gapga qo'shilish: 'I'm not hungry' - 'I'm not hungry either' (Men ham och emasman).",
                "SO AM I / SO DO I = Tasdiqqa qisqa 'Men ham': So + yordamchi fe'l + ega.",
                "NEITHER AM I / NEITHER DO I = Inkorga qisqa 'Men ham (qilmayman)': Neither + yordamchi fe'l + ega.",
                "Present Simple da: 'I love chocolate' -> 'So do I'. 'I don't smoke' -> 'Neither do I'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "too va either (Gap oxirida: ... ham)",
                    formula = "Tasdiq + too | Inkor + either",
                    explanationUzbek = "Birovning fikriga qo'shilganda:\n\n1. Agar gap TASDIQ bo'lsa -> gap oxiriga 'too' qo'yiladi:\n• I'm happy. - I'm happy too. (Men xursandman. - Men ham).\n• Jane enjoyed the film. - I enjoyed it too.\n\n2. Agar gap INKOR bo'lsa -> 'too' EMAS, 'either' qo'yiladi:\n• I'm not hungry. - I'm not hungry either. (Qornim och emas. - Meniki ham).\n• Bill doesn't watch TV. He doesn't read books either.",
                    examples = listOf(
                        MurphyExample("I can speak Spanish. - I can speak Spanish too.", "Ispancha gapira olaman. - Men ham.", "too (tasdiq)"),
                        MurphyExample("I can't swim. - I can't swim either.", "Suzolmayman. - Men ham suzolmayman.", "either (inkor)"),
                        MurphyExample("She hasn't got a car. She hasn't got a bike either.", "Uning mashinasi yo'q. Velosipedi ham yo'q.", "either (inkor)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "So am I / So do I (Men ham - tasdiqqa)",
                    formula = "So + yordamchi fe'l + ega",
                    explanationUzbek = "Tasdiq fikrga juda qisqa va chiroyli tarzda 'Men ham!' deb qo'shilish:\n\n• 'I am tired.' - 'So am I.' (= I am tired too).\n• 'I was late yesterday.' - 'So was John.' (= John was late too).\n• 'I like Italian food.' - 'So do I.' (= I like it too).\n• 'We went to the beach.' - 'So did we.'\n• 'I can play chess.' - 'So can I.'",
                    examples = listOf(
                        MurphyExample("'I'm hungry.' - 'So am I.'", "Qornim ochdi. - Meniki ham.", "So am I"),
                        MurphyExample("'I passed the exam.' - 'So did I.'", "Imtihondan o'tdim. - Men ham.", "So did I (o'tgan zamon)"),
                        MurphyExample("'I live in a flat.' - 'So does Sarah.'", "Kvartirada yashayman. - Sara ham.", "So does + shaxs")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Neither am I / Neither do I (Men ham - inkorga)",
                    formula = "Neither / Nor + yordamchi fe'l + ega",
                    explanationUzbek = "Birovning inkor fikriga 'Men ham (shunday emasman / qilmayman)' deb qo'shilish:\n\n• 'I'm not tired.' - 'Neither am I.' (= I'm not tired either).\n• 'I don't like coffee.' - 'Neither do I.' (= I don't like coffee either).\n• 'I haven't been to Paris.' - 'Neither have I.'\n• 'I can't drive.' - 'Neither can I.'\n\n⚠️ Diqqat: 'Neither' o'zi inkor so'z bo'lgani uchun ketidan kelgan fe'l musbat bo'ladi ('Neither do I', 'Neither don't I' EMAS!).",
                    examples = listOf(
                        MurphyExample("'I don't have a car.' - 'Neither do I.'", "Mashinam yo'q. - Meniki ham.", "Neither do I"),
                        MurphyExample("'I didn't sleep well.' - 'Neither did I.'", "Yaxshi uxlay olmadim. - Men ham.", "Neither did I"),
                        MurphyExample("'I won't be late.' - 'Neither will I.'", "Kech qolmayman. - Men ham.", "Neither will I")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u41_ex1",
                    exerciseNumber = "41.1",
                    taskType = "CHOICE",
                    question = "I'm not feeling well today, and my brother isn't feeling well ______.",
                    options = listOf("either", "too", "neither", "also"),
                    correctOptionIndex = 0,
                    correctAnswerText = "either",
                    explanationUzbek = "Inkor gap oxirida 'ham' ma'nosida 'either' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u41_ex2",
                    exerciseNumber = "41.2",
                    taskType = "CHOICE",
                    question = "'I love traveling to new countries.' - '______ do I.'",
                    options = listOf("So", "Neither", "Either", "Too"),
                    correctOptionIndex = 0,
                    correctAnswerText = "So",
                    explanationUzbek = "Tasdiq gapga qo'shilish uchun 'So do I' (Men ham) ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u41_ex3",
                    exerciseNumber = "41.3",
                    taskType = "CHOICE",
                    question = "'I didn't watch the football match last night.' - '______ did I.'",
                    options = listOf("Neither", "So", "Either", "Too"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Neither",
                    explanationUzbek = "Inkor gapga ('didn't watch') qisqa qo'shilish 'Neither did I' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u41_ex4",
                    exerciseNumber = "41.4",
                    taskType = "CHOICE",
                    question = "'I can play tennis very well.' - 'So ______ I.'",
                    options = listOf("can", "do", "am", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "can",
                    explanationUzbek = "Gapda 'can' bo'lgani uchun unga qo'shilish 'So can I' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u41_ex5",
                    exerciseNumber = "41.5",
                    taskType = "CHOICE",
                    question = "'I haven't got much free time.' - 'Neither ______ I.'",
                    options = listOf("have", "haven't", "do", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have",
                    explanationUzbek = "'Neither' o'zi inkor bo'lgani uchun ortidan musbat yordamchi keladi: 'Neither have I'."
                )
            )
        ),

        // UNIT 42
        MurphyUnit(
            unitNumber = 42,
            title = "isn't, haven't, don't, etc. (Negatives)",
            subtitleUzbek = "Inkor gaplar tuzilishi: not, no, never va inkor yordamchilari",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida inkor tuzish uchun YORDAMCHI FE'LGA 'not' qo'shiladi (isn't, hasn't, can't, don't).",
                "Present Simple da: don't / doesn't + V1 ('She doesn't like milk', he don't likes deyilmaydi!).",
                "Past Simple da: didn't + V1 ('We didn't go', didn't went deyilmaydi!).",
                "Bitta gapda IKKITA INKOR bo'lishi qat'iyan man etiladi! ('I don't know nothing' ❌ -> 'I don't know anything' ✅).",
                "NEVER (hech qachon) o'zi inkor ma'no bildiradi va fe'l musbat turadi: 'He never smokes'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Inkor shakllar (Negative verbs)",
                    formula = "Yordamchi fe'l + not / n't",
                    explanationUzbek = "Inkor qilish uchun to be, modal fe'llar yoki yordamchi fe'llarga 'not' qo'shiladi:\n\n• is not -> isn't | are not -> aren't\n• was not -> wasn't | were not -> weren't\n• have not -> haven't | has not -> hasn't\n• can not -> can't | could not -> couldn't\n• will not -> won't | should not -> shouldn't",
                    examples = listOf(
                        MurphyExample("They aren't ready yet.", "Ular hali tayyor emaslar.", "are not = aren't"),
                        MurphyExample("I haven't finished my homework.", "Uy vazifamni tugatganim yo'q.", "have not = haven't"),
                        MurphyExample("You shouldn't stay up late.", "Kechgacha uxlamay o'tirmasligingiz kerak.", "should not")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "don't / doesn't / didn't (Oddiy fe'llar inkori)",
                    formula = "Present: don't/doesn't + V1 | Past: didn't + V1",
                    explanationUzbek = "Boshqa barcha fe'llarda yordamchi bo'lmagani uchun inkor 'do/does/did' orqali yasaladi:\n\n• I like coffee -> I don't like coffee.\n• Mark works hard -> Mark doesn't work hard (works dagi 's' tushib qoladi!).\n• They went to Paris -> They didn't go to Paris (went asliga 'go' ga aylanadi!).\n\n⚠️ Xato qilmang: 'He doesn't works' ❌ yoki 'I didn't saw' ❌ DEYILMAYDI!",
                    examples = listOf(
                        MurphyExample("She doesn't eat meat.", "U go'sht yemaydi.", "doesn't + eat (s yo'q)"),
                        MurphyExample("We didn't have breakfast this morning.", "Bugun ertalab nonushta qilmadik.", "didn't + have (had emas)"),
                        MurphyExample("I don't understand this word.", "Bu so'zni tushunmayapman.", "don't + understand")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Bitta gapda ikkita inkor mumkin emas (Double negative xatosi)",
                    formula = "not + anything / anyone / anywhere (NOT nothing / nobody)",
                    explanationUzbek = "O'zbek tilida 'Hech kimni ko'rmadim' deymiz (ikkita inkor: hech kim + ko'rmadim). Ammo ingliz tilida faqat BITTA inkor bo'lishi shart:\n\n• I didn't see anyone. (NOT 'I didn't see nobody')\n• He doesn't know anything. (NOT 'He doesn't know nothing')\n• We went nowhere. YOKI We didn't go anywhere.\n• She never eats meat. (NOT 'She doesn't never eat meat').",
                    examples = listOf(
                        MurphyExample("Nobody phoned me today.", "Bugun menga hech kim qo'ng'iroq qilmadi.", "Nobody (bitta inkor)"),
                        MurphyExample("I don't know anybody here.", "Men bu yerda hech kimni tanimayman.", "don't + anybody"),
                        MurphyExample("He said nothing.", "U hech narsa demadi.", "nothing bilan musbat fe'l")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u42_ex1",
                    exerciseNumber = "42.1",
                    taskType = "CHOICE",
                    question = "Sarah ______ speak German, but she speaks English very well.",
                    options = listOf("doesn't", "don't", "isn't", "didn't to"),
                    correctOptionIndex = 0,
                    correctAnswerText = "doesn't",
                    explanationUzbek = "'Sarah' (she) uchun Present Simple inkori 'doesn't' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u42_ex2",
                    exerciseNumber = "42.2",
                    taskType = "CHOICE",
                    question = "We ______ go out yesterday because the weather was awful.",
                    options = listOf("didn't", "don't", "weren't", "not"),
                    correctOptionIndex = 0,
                    correctAnswerText = "didn't",
                    explanationUzbek = "'yesterday' o'tgan zamon bo'lgani uchun fe'l inkori 'didn't go' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u42_ex3",
                    exerciseNumber = "42.3",
                    taskType = "CHOICE",
                    question = "I didn't say ______ to him about the surprise party.",
                    options = listOf("anything", "nothing", "nowhere", "no one"),
                    correctOptionIndex = 0,
                    correctAnswerText = "anything",
                    explanationUzbek = "'didn't' inkor borligi uchun 'nothing' emas, 'anything' qo'yiladi (ikki inkor bo'lmasligi kerak)."
                ),
                MurphyExerciseItem(
                    id = "u42_ex4",
                    exerciseNumber = "42.4",
                    taskType = "CHOICE",
                    question = "He ______ smokes cigarettes. He hates smoking.",
                    options = listOf("never", "doesn't never", "not", "isn't"),
                    correctOptionIndex = 0,
                    correctAnswerText = "never",
                    explanationUzbek = "'never' o'zi to'liq inkor ma'noni ifodalaydi: 'He never smokes'."
                ),
                MurphyExerciseItem(
                    id = "u42_ex5",
                    exerciseNumber = "42.5",
                    taskType = "CHOICE",
                    question = "They haven't got ______ money left for the bus ticket.",
                    options = listOf("any", "no", "some", "none"),
                    correctOptionIndex = 0,
                    correctAnswerText = "any",
                    explanationUzbek = "'haven't got' inkor bo'lgani sababli 'any money' to'g'ri bo'ladi."
                )
            )
        ),

        // UNIT 43
        MurphyUnit(
            unitNumber = 43,
            title = "is it...? have you...? do they...?",
            subtitleUzbek = "Umumiy so'roq gaplar (Yes/No questions) va so'roq gap tuzish qoidasi",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilida savol berishda YORDAMCHI FE'L egadan oldinga o'tadi (Inversion).",
                "To be bilan: 'Is he at home?' / 'Are you ready?' / 'Were they late?'.",
                "Present Simple da: Do / Does oldinga chiqadi: 'Do you work here?' / 'Does she study?'.",
                "Past Simple da: Did oldinga chiqadi: 'Did you see John?' (Did you saw deyilmaydi!).",
                "Modal fe'llar bilan: 'Can you swim?' / 'Should I call him?' / 'Will you come?'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Yordamchi fe'lni oldinga chiqarish qoidasi",
                    formula = "Yordamchi fe'l + Ega + Asosiy fe'l ...?",
                    explanationUzbek = "Ingliz tilida savol berish tartibi o'zgarmasdir: yordamchi fe'l doimo egadan oldinda turadi:\n\n• You are late. -> Are you late?\n• That man is reading. -> What is that man reading?\n• You can drive. -> Can you drive?\n• They have left. -> Have they left?\n\n⚠️ Agar savol so'zi (Where, What, Why) bo'lsa, u eng boshga qo'yiladi:\n• Where have they gone? (Ular qayerga ketishdi?).",
                    examples = listOf(
                        MurphyExample("Is your brother at home?", "Akangiz uydami?", "to be oldinda"),
                        MurphyExample("Can I borrow your pen?", "Ruchkangizni olib tursam maylimi?", "Modal can oldinda"),
                        MurphyExample("Where are you going?", "Qayerga ketyapsiz?", "Savol so'zi + are + ega")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Do / Does / Did bilan savol tuzish",
                    formula = "Do / Does / Did + ega + fe'l (V1)...?",
                    explanationUzbek = "Agar gapda to be, modal yoki have/has yordamchisi bo'lmasa, savol berish uchun DO, DOES yoki DID yordamga chaqiriladi:\n\n1. Present Simple:\n• Do you play football? (Siz futbol o'ynaysizmi?)\n• Does Lucy live alone? (Lyusi yolg'iz yashaydimi? - lives emas, live!)\n\n2. Past Simple:\n• Did you sleep well last night? (Kecha yaxshi uxladingizmi?)\n• What did you do yesterday? (Kecha nima ish qildingiz?).",
                    examples = listOf(
                        MurphyExample("Do they speak English?", "Ular inglizcha gaplashishadimi?", "Do + they + speak"),
                        MurphyExample("Does he work on Sundays?", "U yakshanba kunlari ishlaydimi?", "Does + he + work"),
                        MurphyExample("Did it rain yesterday?", "Kecha yomg'ir yog'dimi?", "Did + it + rain")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Savol so'zlari (Question words)",
                    formula = "What / Where / When / Who / Why / How + yordamchi + ega + fe'l?",
                    explanationUzbek = "Maxsus savollarda yordamchi fe'lning oldiga savol so'zi qo'yiladi:\n\n• What time do you wake up? (Soat nechada uyg'onasiz?)\n• Where does she live? (U qayerda yashaydi?)\n• Why was he angry? (Nega u jahldor edi?)\n• How did you get here? (Bu yerga qanday yetib keldingiz?).",
                    examples = listOf(
                        MurphyExample("Where do you work?", "Qayerda ishlaysiz?", "Where + do + you + work?"),
                        MurphyExample("Why did they leave so early?", "Nega ular bunchalik erta ketishdi?", "Why + did + they + leave?"),
                        MurphyExample("How much does this coat cost?", "Bu palto qancha turadi?", "How much + does + ot + cost?")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u43_ex1",
                    exerciseNumber = "43.1",
                    taskType = "CHOICE",
                    question = "______ your sister work in a hospital?",
                    options = listOf("Does", "Do", "Is", "Are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Does",
                    explanationUzbek = "'your sister' (she) uchun Present Simple so'roq yordamchisi 'Does' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u43_ex2",
                    exerciseNumber = "43.2",
                    taskType = "CHOICE",
                    question = "What time ______ you go to bed last night?",
                    options = listOf("did", "do", "were", "are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did",
                    explanationUzbek = "'last night' (kecha oqshomda) o'tgan zamondagi savol uchun 'did' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u43_ex3",
                    exerciseNumber = "43.3",
                    taskType = "CHOICE",
                    question = "Where ______ you buy that stylish jacket?",
                    options = listOf("did", "have", "were", "are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did",
                    explanationUzbek = "O'tgan zamondagi xarid haqida so'rash: 'Where did you buy...?'."
                ),
                MurphyExerciseItem(
                    id = "u43_ex4",
                    exerciseNumber = "43.4",
                    taskType = "CHOICE",
                    question = "Why ______ they so late for the conference this morning?",
                    options = listOf("were", "did", "do", "are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "were",
                    explanationUzbek = "Sifat 'late' bilan to be ning o'tgan zamon ko'pligi 'were' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u43_ex5",
                    exerciseNumber = "43.5",
                    taskType = "CHOICE",
                    question = "______ you ever eaten Japanese sushi before?",
                    options = listOf("Have", "Did", "Do", "Are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Have",
                    explanationUzbek = "'eaten' 3-shakl va 'ever' ko'rsatkichi Present Perfect ('Have you ever eaten...?') ni talab qiladi."
                )
            )
        ),

        // UNIT 44
        MurphyUnit(
            unitNumber = 44,
            title = "Who saw you? Who did you see?",
            subtitleUzbek = "Ega va to'ldiruvchiga berilgan savollar: Who saw you? vs Who did you see?",
            groupName = "7-Guruh: Pronouns, Conditionals & Questions (37–44)",
            keyTakeawaysUzbek = listOf(
                "WHO / WHAT ega bo'lib kelsa: DO / DOES / DID yordamchi fe'llari ISHLATILMAYDI!",
                "'Who saw you?' = Sizni kim ko'rdi? (Who = ish-harakat egasi, did qo'yilmaydi).",
                "'Who did you see?' = Siz kimni ko'rdingiz? (You = ega, who = to'ldiruvchi, did shart).",
                "Taqqoslash: 'What happened?' (Nima yuz berdi?) vs 'What did you do?' (Siz nima qildingiz?).",
                "Ega savollarida fe'l xuddi 3-shaxs birlikdek o'z zamon shaklida keladi: 'Who lives here?'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Who / What ega bo'lib kelganda (Do / Did ishlatilmaydi!)",
                    formula = "Who / What + Fe'l (o'tgan yoki hozirgi zamon shaklida)?",
                    explanationUzbek = "Agar savol so'zi (Who yoki What) gapning o'ziga ega bo'lsa (ya'ni harakatni kim bajarganini so'rayotgan bo'lsangiz), oddiy darak gap kabi tuziladi va do/does/did ishlatilmaydi:\n\n• Who phoned you? (Sizga kim qo'ng'iroq qildi? — Who did phone you EMAS!).\n• Who lives in that house? (U uyda kim yashaydi? — Who does live EMAS!).\n• What happened yesterday? (Kecha nima yuz berdi? — What did happen EMAS!).\n• Who broke the vase? (Vazani kim sindirdi?).",
                    examples = listOf(
                        MurphyExample("Who wants some ice cream?", "Kim muzqaymoq xohlaydi?", "Who = ega (does ishlatilmaydi)"),
                        MurphyExample("What happened to your car?", "Mashinangizga nima bo'ldi?", "What = ega (did ishlatilmaydi)"),
                        MurphyExample("Who told you that news?", "Bu yangilikni sizga kim aytdi?", "Who + told (V2)")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Who / What to'ldiruvchi bo'lganda (Do / Did shart!)",
                    formula = "Who / What + do/does/did + Ega + V1?",
                    explanationUzbek = "Agar harakatni bajargan shaxs gapda mavjud bo'lsa (siz, u, ular), Who/What esa to'ldiruvchi (kimni, nimani) bo'lsa, qoidaga binoan do/does/did qo'yiladi:\n\n• Who did you phone? (Siz kimga telefon qildingiz? — Ega: siz).\n• What did you buy at the store? (Do'kondan nima sotib oldingiz? — Ega: siz).\n• Who did they invite to the party? (Ular mehmondorchilikka kimni taklif qilishdi? — Ega: ular).",
                    examples = listOf(
                        MurphyExample("Who did you meet yesterday?", "Kecha kimni uchratdingiz?", "did + you + meet"),
                        MurphyExample("What do you want?", "Nimani xohlaysiz?", "do + you + want"),
                        MurphyExample("Who does she like?", "U kimni yoqtiradi?", "does + she + like")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Yonma-yon taqqoslash (Farqni ko'ring)",
                    formula = "Ega savoli vs To'ldiruvchi savoli",
                    explanationUzbek = "Misol: Jorj Polni ko'rdi (George saw Paul).\n\n1. 'Who saw Paul?' (Polni kim ko'rdi?) -> George saw him. (Ega so'ralyapti -> did yo'q).\n2. 'Who did George see?' (Jorj kimni ko'rdi?) -> He saw Paul. (To'ldiruvchi so'ralyapti -> did shart).\n\nMisol: Biror narsa yuz berdi (Something happened).\n• 'What happened?' (Nima yuz berdi?) -> Did qo'yilmaydi!\n• 'What did you see?' (Nimani ko'rdingiz?) -> Did qo'yiladi!",
                    examples = listOf(
                        MurphyExample("'Who broke the window?' - 'Tom broke it.'", "Derazani kim sindirdi? - Tom sindirdi.", "Ega savoli (did yo'q)"),
                        MurphyExample("'Who did you see?' - 'I saw Tom.'", "Siz kimni ko'rdingiz? - Tomni ko'rdim.", "To'ldiruvchi savoli (did bor)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u44_ex1",
                    exerciseNumber = "44.1",
                    taskType = "CHOICE",
                    question = "______ phoned you while I was out?",
                    options = listOf("Who", "Who did", "Who was", "Whom did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Who",
                    explanationUzbek = "Ega so'ralyapti ('Kim telefon qildi?'), shuning uchun 'did' siz to'g'ridan-to'g'ri 'Who phoned' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u44_ex2",
                    exerciseNumber = "44.2",
                    taskType = "CHOICE",
                    question = "Who ______ you meet at the airport yesterday?",
                    options = listOf("did", "were", "have", "had"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did",
                    explanationUzbek = "Ega 'you' (siz kimni uchratdingiz?), shuning uchun 'did' yordamchisi shart: 'Who did you meet?'."
                ),
                MurphyExerciseItem(
                    id = "u44_ex3",
                    exerciseNumber = "44.3",
                    taskType = "CHOICE",
                    question = "What ______ at the meeting this morning?",
                    options = listOf("happened", "did happen", "was happened", "does happen"),
                    correctOptionIndex = 0,
                    correctAnswerText = "happened",
                    explanationUzbek = "'What' harakat egasi bo'lgani sababli 'What happened?' to'g'ri ('did happen' noto'g'ri)."
                ),
                MurphyExerciseItem(
                    id = "u44_ex4",
                    exerciseNumber = "44.4",
                    taskType = "CHOICE",
                    question = "Who ______ in that big house on the corner?",
                    options = listOf("lives", "does live", "is live", "do live"),
                    correctOptionIndex = 0,
                    correctAnswerText = "lives",
                    explanationUzbek = "Ega savoli: 'Who lives in that big house?' ('does live' deyilmaydi)."
                ),
                MurphyExerciseItem(
                    id = "u44_ex5",
                    exerciseNumber = "44.5",
                    taskType = "CHOICE",
                    question = "What ______ you do last weekend?",
                    options = listOf("did", "do", "were", "have"),
                    correctOptionIndex = 0,
                    correctAnswerText = "did",
                    explanationUzbek = "Ega 'you' bo'lgani sababli o'tgan zamon savolida 'What did you do?' to'g'ri bo'ladi."
                )
            )
        )
    )
}
