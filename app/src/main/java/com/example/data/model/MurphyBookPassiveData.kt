package com.example.data.model

object MurphyBookPassiveDatabase {

    val UNITS_PASSIVE: List<MurphyUnit> = listOf(
        // UNIT 21
        MurphyUnit(
            unitNumber = 21,
            title = "is done / was done (Passive 1)",
            subtitleUzbek = "Majhul nisbat 1: am/is/are + V3 (Present Simple) va was/were + V3 (Past Simple)",
            groupName = "4-Guruh: Passive & Verb Forms (21–24)",
            keyTakeawaysUzbek = listOf(
                "Active (Aniq nisbat): Ega ish-harakatni o'zi bajaradi (Somebody cleans this room).",
                "Passive (Majhul nisbat): Ish-harakat eganing ustida bajariladi (This room is cleaned).",
                "Hozirgi zamon formulasi: am / is / are + o'tgan zamon sifatdoshi (V3 / -ed).",
                "O'tgan zamon formulasi: was / were + o'tgan zamon sifatdoshi (V3 / -ed).",
                "Harakat kim tomonidan bajarilganini aytish uchun 'by' (tomonidan) predlogi ishlatiladi: 'This house was built by my grandfather'."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Xona tozalanishi misoli (Active vs Passive)",
                    formula = "Active: Somebody cleans the room | Passive: The room is cleaned",
                    explanationUzbek = "Taqqoslang:\n\n1. Active (Aniq nisbat):\n• Somebody cleans this room every day. (Kimdir bu xonani har kuni tozalaydi).\nBu yerda urg'u ishni qiluvchi odamga qaratilgan.\n\n2. Passive (Majhul nisbat):\n• This room is cleaned every day. (Bu xona har kuni tozalanadi).\nBu yerda ishni kim qilishi noma'lum yoki muhim emas, eng muhimi — XONA VA TOZALANISH HARAKATI!",
                    examples = listOf(
                        MurphyExample("Active: Somebody cleans this room every day.", "Kimdir bu xonani har kuni tozalaydi.", "Active: subject performs action"),
                        MurphyExample("Passive: This room is cleaned every day.", "Bu xona har kuni tozalanadi.", "Passive: am/is/are + V3"),
                        MurphyExample("Active: Somebody cleaned this room yesterday.", "Kimdir kecha bu xonani tozaladi.", "Past Active"),
                        MurphyExample("Passive: This room was cleaned yesterday.", "Bu xona kecha tozalandi.", "Past Passive: was/were + V3")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "am/is/are + V3 va was/were + V3",
                    formula = "Present: am/is/are + V3 | Past: was/were + V3",
                    explanationUzbek = "Passive tuzilishida 'to be' fe'li zamonga qarab o'zgaradi, asosiy fe'l esa doimo 3-shaklda (V3 / -ed) qoladi:\n\n• Butter is made from milk. (Sariyog' sutdan tayyorlanadi).\n• Oranges are imported into Britain. (Apelsinlar Britaniyaga import qilinadi).\n• How often are these rooms cleaned? (Bu xonalar qanchalik tez-tez tozalanadi?)\n• I am not invited to parties very often. (Meni ziyofatlarga tez-tez taklif qilishmaydi).\n\nO'tgan zamonda:\n• This house was built in 1961. (Bu uy 1961-yilda qurilgan).\n• These houses were built 50 years ago. (Bu uylar 50 yil oldin qurilgan).\n• We weren't invited to the party last week. (Biz o'tgan hafta ziyofatga taklif qilinmadik).",
                    examples = listOf(
                        MurphyExample("Butter is made from milk.", "Sariyog' sutdan tayyorlanadi.", "make -> made -> made"),
                        MurphyExample("Oranges are imported into Britain.", "Apelsinlar Britaniyaga import qilinadi.", "are + imported"),
                        MurphyExample("This house was built in 1961.", "Bu uy 1961-yilda qurilgan.", "was + built"),
                        MurphyExample("When was the telephone invented?", "Telefon qachon ixtiro qilingan?", "was ... invented")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "was/were born va Passive + BY...",
                    formula = "was / were born | Passive + BY (bajaruvchi)",
                    explanationUzbek = "1. 'Tug'ilmoq' iborasi ingliz tilida har doim Passive va O'tgan zamonda bo'ladi:\n• I was born in Samarkand in 1998. (Men 1998-yilda Samarqandda tug'ilganman).\n• Where were you born? (Qayerda tug'ilgansiz?)\n\n2. Agar harakat kim tomonidan qilinganini aytmoqchi bo'lsak, gap oxirida 'by' qo'shamiz:\n• The telephone was invented by Alexander Graham Bell. (Telefon Bell tomonidan ixtiro qilingan).\n• I was bitten by a dog last month. (O'tgan oy meni it tishlab oldi).",
                    examples = listOf(
                        MurphyExample("I was born in Chicago in 1995.", "Men 1995-yilda Chikagoda tug'ilganman.", "was born"),
                        MurphyExample("Where were you born? - In Tokyo.", "Qayerda tug'ilgansiz? - Tokioda.", "were you born"),
                        MurphyExample("The telephone was invented by Alexander Bell in 1876.", "Telefon 1876-yilda Aleksandr Bell tomonidan ixtiro qilingan.", "by + inventor"),
                        MurphyExample("I was bitten by a dog yesterday.", "Kecha meni it tishlab oldi.", "by a dog")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u21_ex1",
                    exerciseNumber = "21.1",
                    taskType = "CHOICE",
                    question = "This house ______ 100 years ago.",
                    options = listOf("was built", "is built", "built", "were built"),
                    correctOptionIndex = 0,
                    correctAnswerText = "was built",
                    explanationUzbek = "Uy 100 yil oldin (o'tgan zamonda) qurilgan, shuning uchun 'was built' (birlik) ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u21_ex2",
                    exerciseNumber = "21.2",
                    taskType = "CHOICE",
                    question = "Butter ______ from milk.",
                    options = listOf("is made", "are made", "is make", "makes"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is made",
                    explanationUzbek = "Sariyog' (butter) sanalmaydigan ot, doimiy fakt uchun 'is made' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u21_ex3",
                    exerciseNumber = "21.3",
                    taskType = "CHOICE",
                    question = "Where ______ you born?",
                    options = listOf("were", "was", "did", "are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "were",
                    explanationUzbek = "'you' olmoshi bilan 'Where were you born?' deb so'raladi ('did you born' mutlaqo xato!)."
                ),
                MurphyExerciseItem(
                    id = "u21_ex4",
                    exerciseNumber = "21.4",
                    taskType = "CHOICE",
                    question = "The telephone was invented ______ Alexander Bell.",
                    options = listOf("by", "from", "with", "of"),
                    correctOptionIndex = 0,
                    correctAnswerText = "by",
                    explanationUzbek = "Majhul nisbatda ishni kim qilganini ko'rsatish uchun 'by' (tomonidan) qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u21_ex5",
                    exerciseNumber = "21.5",
                    taskType = "CHOICE",
                    question = "These rooms ______ every day.",
                    options = listOf("are cleaned", "is cleaned", "cleaned", "are clean"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are cleaned",
                    explanationUzbek = "'These rooms' ko'plikda, shuning uchun 'are cleaned' to'g'ri."
                )
            )
        ),

        // UNIT 22
        MurphyUnit(
            unitNumber = 22,
            title = "is being done / has been done (Passive 2)",
            subtitleUzbek = "Majhul nisbat 2: Davomli majhul (is being done) va Tugallangan majhul (has been done)",
            groupName = "4-Guruh: Passive & Verb Forms (21–24)",
            keyTakeawaysUzbek = listOf(
                "Present Continuous Passive: am / is / are + BEING + V3 (Ayni paytda ish eganing ustida bajarilmoqda).",
                "Present Perfect Passive: have / has + BEEN + V3 (Ish yaqindagina bajarib bo'lingan va natijasi ko'rinib turibdi).",
                "Eshik bo'yalmoqda: 'Somebody is painting the door' -> 'The door is being painted'.",
                "Eshik bo'yab bo'lindi: 'Somebody has painted the door' -> 'The door has been painted'.",
                "Qiyoslang: 'is done' (har kuni qilinadi) VS 'is being done' (ayni daqiqada qilinyapti) VS 'has been done' (qilib bo'lindi)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "is / are being done (Present Continuous Passive)",
                    formula = "am / is / are + BEING + V3",
                    explanationUzbek = "Ayni paytda biror narsa ustida ish ketayotgan bo'lsa (jarayon davom etayotgan bo'lsa), 'being + V3' qo'shiladi:\n\n• Somebody is painting the door. (Kimdir eshikni bo'yayapti).\n-> The door is being painted. (Eshik bo'yalmoqda - ehtiyot bo'ling, bo'yoq ho'l!).\n\n• My car is at the garage. It is being repaired. (Mashinam ustaxonada. U tuzatilmoqda).\n• Some new houses are being built opposite the park. (Parkning ro'parasida yangi uylar qurilmoqda).",
                    examples = listOf(
                        MurphyExample("The door is being painted.", "Eshik bo'yalmoqda.", "is being + painted (hozir jarayonda)"),
                        MurphyExample("My car is being repaired at the garage.", "Mashinam garajda ta'mirlanmoqda.", "is being repaired"),
                        MurphyExample("A new bridge is being built across the river.", "Daryo ustida yangi ko'prik qurilmoqda.", "are/is being built")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "have / has been done (Present Perfect Passive)",
                    formula = "have / has + BEEN + V3",
                    explanationUzbek = "Biror ish yaqinda bajarib bo'lingan va natijasi hozir mavjud bo'lsa 'been + V3' ishlatiladi:\n\n• Somebody has painted the door. (Kimdir eshikni bo'yadi).\n-> The door has been painted. (Eshik bo'yalgan - bo'yoq yangi, ish bitgan).\n\n• My key has been stolen! (Kalitim o'g'irlab ketildi!)\n• Have you heard? The concert has been cancelled. (Eshitdingizmi? Konsert bekor qilinibdi).\n• Have these shirts been washed? (Bu ko'ylaklar yuvilganmi?).",
                    examples = listOf(
                        MurphyExample("The door has been painted. It looks nice.", "Eshik bo'yab bo'lingan. Chiroyli ko'rinyapti.", "has been painted (natija tayyor)"),
                        MurphyExample("My key has been stolen.", "Kalitim o'g'irlab ketildi.", "has been stolen"),
                        MurphyExample("The concert has been cancelled.", "Konsert bekor qilindi.", "has been cancelled"),
                        MurphyExample("Have these clothes been washed?", "Bu kiyimlar yuvilganmi?", "Have ... been washed?")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "Uch zamon majhul nisbatining taqqoslanishi",
                    formula = "is cleaned (odatda) | is being cleaned (hozir) | has been cleaned (tugallangan)",
                    explanationUzbek = "Ushbu 3 jumlani diqqat bilan solishtiring:\n\n1. 'The room is cleaned every day.' (Present Simple Passive)\n-> Xona har kuni tozalanadi (doimiy odat).\n\n2. 'The room is being cleaned at the moment.' (Present Continuous Passive)\n-> Xona ayni daqiqada tozalanmoqda (ichkariga kirmang, tozalash jarayoni ketmoqda).\n\n3. 'The room has been cleaned. It is ready.' (Present Perfect Passive)\n-> Xona tozalanib bo'lindi (ish tugadi, xona top-toza).",
                    examples = listOf(
                        MurphyExample("The room is cleaned every day.", "Xona har kuni tozalanadi.", "Present Simple Passive"),
                        MurphyExample("The room is being cleaned right now.", "Xona ayni paytda tozalanmoqda.", "Present Continuous Passive"),
                        MurphyExample("The room has been cleaned. You can enter.", "Xona tozalanib bo'lindi. Kiravering.", "Present Perfect Passive")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u22_ex1",
                    exerciseNumber = "22.1",
                    taskType = "CHOICE",
                    question = "Don't touch the door! It ______ painted.",
                    options = listOf("is being", "has been", "was", "is"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is being",
                    explanationUzbek = "'Eshikka tegmang!' degan ogohlantirish uning ayni paytda bo'yalayotganini bildiradi: 'is being painted'."
                ),
                MurphyExerciseItem(
                    id = "u22_ex2",
                    exerciseNumber = "22.2",
                    taskType = "CHOICE",
                    question = "My bicycle has disappeared! It ______ stolen.",
                    options = listOf("has been", "is being", "was been", "had"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has been",
                    explanationUzbek = "Velosiped yo'qolgan, demak u o'g'irlab ketilgan (natija bor): 'has been stolen'."
                ),
                MurphyExerciseItem(
                    id = "u22_ex3",
                    exerciseNumber = "22.3",
                    taskType = "CHOICE",
                    question = "A new hospital ______ built in the town centre right now.",
                    options = listOf("is being", "is", "has been", "was"),
                    correctOptionIndex = 0,
                    correctAnswerText = "is being",
                    explanationUzbek = "'right now' (ayni paytda) bo'lgani uchun Continuous Passive: 'is being built'."
                ),
                MurphyExerciseItem(
                    id = "u22_ex4",
                    exerciseNumber = "22.4",
                    taskType = "CHOICE",
                    question = "Have you heard the news? The football match ______ cancelled.",
                    options = listOf("has been", "is being", "was being", "been"),
                    correctOptionIndex = 0,
                    correctAnswerText = "has been",
                    explanationUzbek = "Yangi xabarni ma'lum qilishda Present Perfect Passive ishlatiladi: 'has been cancelled'."
                ),
                MurphyExerciseItem(
                    id = "u22_ex5",
                    exerciseNumber = "22.5",
                    taskType = "CHOICE",
                    question = "These rooms ______ cleaned every morning.",
                    options = listOf("are", "are being", "have been", "were being"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are",
                    explanationUzbek = "'every morning' (har kuni ertalab) odatiy harakat, shuning uchun oddiy 'are cleaned' bo'ladi."
                )
            )
        ),

        // UNIT 23
        MurphyUnit(
            unitNumber = 23,
            title = "be / have / do in present and past tenses",
            subtitleUzbek = "Yordamchi fe'llar tizimi: be (am/is/are/was/were), have (have/has/had), do (do/does/did)",
            groupName = "4-Guruh: Passive & Verb Forms (21–24)",
            keyTakeawaysUzbek = listOf(
                "Ingliz tilidagi deyarli barcha zamonlar uchta yordamchi fe'lga tayanadi: be, have, do.",
                "BE + -ing (Continuous) va BE + V3 (Passive).",
                "HAVE / HAS + V3 (Present Perfect).",
                "DO / DOES / DID + V1 (Oddiy zamonlardagi inkor va so'roq gaplar).",
                "Asosiy va yordamchi fe'llar chalkashmasligi kerak: 'What did you do?' (birinchi did yordamchi, ikkinchi do asosiy)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "BE: am / is / are / was / were",
                    formula = "be + -ing (jarayon) | be + V3 (majhul nisbat)",
                    explanationUzbek = "'Be' fe'li ikkita holatda asosiy fe'l bilan birga keladi:\n\n1. Fe'l + -ing bilan (Continuous - davomli zamonlar):\n• Please be quiet. I'm working. (I am working)\n• It wasn't raining when we went out.\n• What are you doing tonight?\n\n2. Fe'l + V3 (Past Participle - Passive):\n• I was invited to the party. (was + invited)\n• Butter is made from milk. (is + made)\n• Where were you born?",
                    examples = listOf(
                        MurphyExample("I am working at the moment.", "Ayni paytda ishlayapman.", "am + V-ing"),
                        MurphyExample("It was raining yesterday evening.", "Kecha oqshomda yomg'ir yog'ayotgan edi.", "was + V-ing"),
                        MurphyExample("The window was broken yesterday.", "Deraza kecha sindirildi.", "was + V3 (Passive)"),
                        MurphyExample("Where were these cars made?", "Bu mashinalar qayerda ishlab chiqarilgan?", "were + V3")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "HAVE: have / has / had",
                    formula = "have / has + V3 (Past Participle)",
                    explanationUzbek = "'Have / has' yordamchi fe'li har doim fe'lning 3-shakli (Past Participle - V3) bilan keladi va tugallangan zamonlarni hosil qiladi:\n\n• I have cleaned my shoes. (Poyabzalimni tozalab bo'ldim).\n• Jane has lived in London for ten years. (Jeyn 10 yildan beri Londonda yashaydi).\n• Has it stopped raining yet? (Yomg'ir to'xtadimi?)\n• They have never been to America. (Ular hech qachon Amerikada bo'lishmagan).",
                    examples = listOf(
                        MurphyExample("I have lost my key.", "Kalitimni yo'qotib qo'ydim.", "have + lost (V3)"),
                        MurphyExample("Has Ann arrived yet?", "Enn yetib keldimi?", "has + arrived (V3)"),
                        MurphyExample("They have bought a new house.", "Ular yangi uy sotib olishdi.", "have + bought (V3)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "DO: do / does / did",
                    formula = "do / does / did + V1 (Infinitive)",
                    explanationUzbek = "'Do, does, did' yordamchi fe'llari Present Simple va Past Simple da inkor va so'roq gaplar yasash uchun xizmat qiladi. Ulardan keyin keladigan fe'l DOIMO boshlang'ich shaklda (V1) bo'ladi:\n\n• I like coffee, but I don't like tea. (don't + like)\n• What time did the train leave? (did ... leave)\n• Chris doesn't go out very often. (doesn't + go)\n• Did you sleep well? - Yes, I did.",
                    examples = listOf(
                        MurphyExample("Do you want coffee? - No, thanks.", "Kofe xohlaysizmi? - Rahmat, yo'q.", "Do + want (V1)"),
                        MurphyExample("He doesn't work on Sundays.", "U yakshanba kunlari ishlamaydi.", "doesn't + work (V1)"),
                        MurphyExample("What did you do yesterday?", "Kecha nima ish qildingiz?", "did (yordamchi) + do (asosiy)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u23_ex1",
                    exerciseNumber = "23.1",
                    taskType = "CHOICE",
                    question = "Where ______ these photos taken?",
                    options = listOf("were", "did", "have", "are being"),
                    correctOptionIndex = 0,
                    correctAnswerText = "were",
                    explanationUzbek = "Rasmlar olingan (Passive: o'tgan zamon) bo'lgani sababli 'were taken' ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u23_ex2",
                    exerciseNumber = "23.2",
                    taskType = "CHOICE",
                    question = "What time ______ the film start?",
                    options = listOf("does", "is", "has", "do"),
                    correctOptionIndex = 0,
                    correctAnswerText = "does",
                    explanationUzbek = "'the film' (it) va fe'l 'start' (V1) bo'lgani uchun yordamchi fe'l 'does' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u23_ex3",
                    exerciseNumber = "23.3",
                    taskType = "CHOICE",
                    question = "I ______ never eaten Mexican food.",
                    options = listOf("have", "am", "do", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "have",
                    explanationUzbek = "Fe'l 'eaten' (V3) bo'lgani sababli faqat 'have' yordamchi fe'li to'g'ri keladi: 'I have never eaten'."
                ),
                MurphyExerciseItem(
                    id = "u23_ex4",
                    exerciseNumber = "23.4",
                    taskType = "CHOICE",
                    question = "Why ______ you laughing at me?",
                    options = listOf("are", "do", "have", "did"),
                    correctOptionIndex = 0,
                    correctAnswerText = "are",
                    explanationUzbek = "Fe'l '-ing' olgan ('laughing'), shuning uchun 'be' fe'lining shakli 'are' qo'yiladi: 'Why are you laughing?'"
                ),
                MurphyExerciseItem(
                    id = "u23_ex5",
                    exerciseNumber = "23.5",
                    taskType = "CHOICE",
                    question = "______ you go out last night?",
                    options = listOf("Did", "Were", "Have", "Are"),
                    correctOptionIndex = 0,
                    correctAnswerText = "Did",
                    explanationUzbek = "'last night' va boshlang'ich fe'l 'go' bo'lgani uchun savol 'Did you go...?' bo'ladi."
                )
            )
        ),

        // UNIT 24
        MurphyUnit(
            unitNumber = 24,
            title = "Regular and irregular verbs",
            subtitleUzbek = "To'g'ri va noto'g'ri fe'llarning to'liq tizimi (V1 - Base, V2 - Past Simple, V3 - Past Participle)",
            groupName = "4-Guruh: Passive & Verb Forms (21–24)",
            keyTakeawaysUzbek = listOf(
                "To'g'ri fe'llar (Regular verbs): O'tgan zamon (V2) va Sifatdosh (V3) shakllariga bir xil '-ed' qo'shiladi (cleaned, arrived, played).",
                "Noto'g'ri fe'llar (Irregular verbs): '-ed' olmaydi va o'z qonuniyatiga ko'ra 3 xil guruhga bo'linadi.",
                "1-guruh: Uchala shakl bir xil (cut-cut-cut, hit-hit-hit, cost-cost-cost, put-put-put).",
                "2-guruh: V2 va V3 bir xil (make-made-made, buy-bought-bought, lose-lost-lost, find-found-found).",
                "3-guruh: Uchala shakl butunlay har xil (break-broke-broken, go-went-gone, see-saw-seen, do-did-done, write-wrote-written)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "To'g'ri fe'llar (Regular verbs: -ed)",
                    formula = "V1 + ed = V2 va V3",
                    explanationUzbek = "To'g'ri fe'llarning o'tgan zamoni va 3-shakli bir xil yasaladi:\n\n• clean -> cleaned -> cleaned\n• open -> opened -> opened\n• live -> lived -> lived (e harfi bo'lsa faqat d qo'shiladi)\n• study -> studied -> studied (undosh + y bo'lsa ied ga aylanadi)\n• stop -> stopped -> stopped (urg'uli qisqa unli + undosh ikkilanadi)\n\nMisol:\n• I cleaned my room yesterday. (V2 - Past Simple)\n• I have cleaned my room. (V3 - Past Participle)\n• The room is cleaned every day. (V3 - Passive)",
                    examples = listOf(
                        MurphyExample("I cleaned my shoes yesterday.", "Kecha poyabzalimni tozaladim.", "V2 (Past Simple)"),
                        MurphyExample("I have cleaned my shoes.", "Poyabzalimni tozalab bo'ldim.", "V3 (Past Participle)"),
                        MurphyExample("She lived in Rome for three years.", "U uch yil Rimda yashagan.", "live -> lived")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Noto'g'ri fe'llarning 3 ta asosiy turi",
                    formula = "1: AAA (bir xil) | 2: ABB (ikki shakl bir xil) | 3: ABC (uch shakl har xil)",
                    explanationUzbek = "Noto'g'ri fe'llarni yod olishning eng oson siri ularni 3 turga ajratishdir:\n\n1. Uchala shakl bir xil (AAA):\n• cut -> cut -> cut (kesmoq)\n• hit -> hit -> hit (urmoq)\n• hurt -> hurt -> hurt (og'rimoq)\n• cost -> cost -> cost (narx turmoq)\n\n2. O'tgan zamon va 3-shakl bir xil (ABB):\n• make -> made -> made\n• find -> found -> found\n• buy -> bought -> bought\n• feel -> felt -> felt\n• leave -> left -> left\n\n3. Uchala shakl har xil (ABC):\n• break -> broke -> broken\n• see -> saw -> seen\n• speak -> spoke -> spoken\n• take -> took -> taken\n• write -> wrote -> written",
                    examples = listOf(
                        MurphyExample("I cut my finger yesterday. / I've cut my finger.", "Kecha barmog'imni kesib oldim. / Barmog'imni kesib oldim.", "cut-cut-cut (shakl o'zgarmaydi)"),
                        MurphyExample("She made a cake. / She has made a cake.", "U tort pishirdi. / U tort pishirib bo'ldi.", "make-made-made"),
                        MurphyExample("He wrote a letter. / He has written a letter.", "U xat yozdi. / U xat yozib bo'ldi.", "write-wrote-written (3 ta har xil)")
                    )
                ),
                MurphySection(
                    sectionCode = "C",
                    heading = "V2 va V3 ning ishlatilish o'rni",
                    formula = "V2 yolg'iz keladi (Past Simple) | V3 be/have bilan keladi",
                    explanationUzbek = "⚠️ Eslab qoling:\n\n• V2 (Past Simple) har doim YOLG'IZ keladi va yordamchi fe'lsiz ishlatiladi:\n'I saw him yesterday.' (Hech qanday have yoki be yo'q!)\n\n• V3 (Past Participle) esa deyarli har doim 'HAVE' yoki 'BE' yordamchi fe'llari bilan keladi:\n1. 'I have seen him.' (have + V3 -> Present Perfect)\n2. 'He was seen by millions of people.' (be + V3 -> Passive Voice)",
                    examples = listOf(
                        MurphyExample("They went to London last week.", "Ular o'tgan hafta Londonga ketishdi.", "V2 yolg'iz keldi: went"),
                        MurphyExample("They have gone to London.", "Ular Londonga ketishgan.", "have + V3: have gone"),
                        MurphyExample("Somebody stole my bike. / My bike was stolen.", "Kimdir velosipedimni o'g'irladi. / Velosipedim o'g'irlandi.", "V2 (stole) vs was + V3 (stolen)")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u24_ex1",
                    exerciseNumber = "24.1",
                    taskType = "CHOICE",
                    question = "I ______ my finger with a sharp knife yesterday.",
                    options = listOf("cut", "cutted", "cutten", "was cut"),
                    correctOptionIndex = 0,
                    correctAnswerText = "cut",
                    explanationUzbek = "'cut' fe'lining o'tgan zamoni ham 'cut' shaklida qoladi ('cutted' degan so'z mavjud emas!)."
                ),
                MurphyExerciseItem(
                    id = "u24_ex2",
                    exerciseNumber = "24.2",
                    taskType = "CHOICE",
                    question = "She ______ a letter to her friend yesterday.",
                    options = listOf("wrote", "written", "writed", "has wrote"),
                    correctOptionIndex = 0,
                    correctAnswerText = "wrote",
                    explanationUzbek = "'yesterday' bilan oddiy Past Simple fe'li V2 kerak: write -> wrote -> written. Shuning uchun 'wrote' to'g'ri."
                ),
                MurphyExerciseItem(
                    id = "u24_ex3",
                    exerciseNumber = "24.3",
                    taskType = "CHOICE",
                    question = "The window was ______ by the wind.",
                    options = listOf("broken", "broke", "breaked", "break"),
                    correctOptionIndex = 0,
                    correctAnswerText = "broken",
                    explanationUzbek = "'was' dan keyin majhul nisbat uchun V3 (Past Participle) kerak: break -> broke -> broken."
                ),
                MurphyExerciseItem(
                    id = "u24_ex4",
                    exerciseNumber = "24.4",
                    taskType = "CHOICE",
                    question = "Where did you ______ that jacket?",
                    options = listOf("buy", "bought", "buyed", "have bought"),
                    correctOptionIndex = 0,
                    correctAnswerText = "buy",
                    explanationUzbek = "'did' yordamchi fe'lidan keyin asosiy fe'l boshlang'ich shaklda (V1) keladi: 'buy'."
                ),
                MurphyExerciseItem(
                    id = "u24_ex5",
                    exerciseNumber = "24.5",
                    taskType = "CHOICE",
                    question = "I have ______ my keys. I can't find them anywhere.",
                    options = listOf("lost", "lose", "losed", "loosed"),
                    correctOptionIndex = 0,
                    correctAnswerText = "lost",
                    explanationUzbek = "'have' dan keyin 'lose' ning 3-shakli 'lost' ishlatiladi: lose -> lost -> lost."
                )
            )
        )
    )
}
