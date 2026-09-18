package com.example.data.model

object MurphyBookAdjectivesAdverbsData {

    val UNITS_ADJECTIVES_ADVERBS: List<MurphyUnit> = listOf(
        // UNIT 75
        MurphyUnit(
            unitNumber = 75,
            title = "quick / quickly (Adjectives and adverbs 1)",
            subtitleUzbek = "Sifat va ravish: Sifat otni, ravish esa fe'lni tasvirlaydi",
            groupName = "13-Guruh: Adjectives & Adverbs (75–80)",
            keyTakeawaysUzbek = listOf(
                "SIFAT (Adjective) OTNI tasvirlaydi (Qanday?): a quick runner, careful driver, bad singer.",
                "RAVISH (Adverb) FE'LNI tasvirlaydi (Qanday qilib?): runs quickly, drives carefully, sings badly.",
                "Ravishlar odatda sifatga -LY qo'shish bilan yasaladi: quick -> quickly, bad -> badly, slow -> slowly, heavy -> heavily (-y -> -ily).",
                "BE, LOOK, FEEL, SMELL, TASTE, SOUND kabi sezgi fe'llaridan keyin RAVISH EMAS, SIFAT keladi: You look happy (happily emas), It smells good (well emas).",
                "TAQQOSLASH: Jack is a slow driver (sifat + ot) vs Jack drives slowly (fe'l + ravish)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Sifat (Adjective) vs Ravish (Adverb)",
                    formula = "Sifat + Ot vs Fe'l + Ravish (-ly)",
                    explanationUzbek = "1) SIFATLAR (Adjectives) kim yoki nima qandayligini bildiradi:\n• Tom is a careful driver. (Tom ehtiyotkor haydovchi)\n• We had a quiet evening. (Sokin oqshom o'tkazdik)\n• English is an easy language. (Ingliz tili oson til)\n\n2) RAVISHLAR (Adverbs) ish-harakat qanday bajarilganini bildiradi:\n• Tom drives carefully. (Tom ehtiyotkorlik bilan haydaydi)\n• We spoke quietly. (Biz sekin/sokin gaplashdik)\n• You can learn English easily. (Ingliz tilini osonlik bilan o'rgana olasiz)\n\nImlo qoidalari:\n• easy -> easily, heavy -> heavily, lucky -> luckily\n• terrible -> terribly, comfortable -> comfortably",
                    examples = listOf(
                        MurphyExample("She plays the piano beautifully.", "U pianinoni juda chiroyli chaladi.", "plays + beautifully"),
                        MurphyExample("It was raining heavily when we left.", "Biz chiqqanimizda yomg'ir qattiq yog'ayotgan edi.", "raining + heavily"),
                        MurphyExample("Please listen carefully to the instructions.", "Iltimos, ko'rsatmalarni diqqat bilan tinglang.", "listen + carefully")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Sezgi fe'llari (look, feel, sound, taste, smell)",
                    formula = "look / feel / smell / taste / sound + SIFAT (adjective)",
                    explanationUzbek = "Bu fe'llar harakat emas, holatni ifodalagani uchun ulardan keyin -LY qo'shimchasi qo'yilmaydi:\n\n• You look tired. (Charchagan ko'rinyapsan - 'tiredly' emas)\n• I feel happy today. (Bugun o'zimni baxtli his qilyapman)\n• The soup smells delicious. (Sho'rvaning hidi juda mazali kelyapti)\n• This music sounds wonderful. (Bu musiqa ajoyib yangramoqda)\n• The food tastes good. (Taom juda mazali)",
                    examples = listOf(
                        MurphyExample("Why are you looking so angry?", "Nega bunchalik jahling chiqqan ko'rinyapsan?", "look + angry"),
                        MurphyExample("The bed feels very comfortable.", "Krovat juda qulay sezilyapti.", "feels + comfortable")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u75_ex1",
                    exerciseNumber = "75.1",
                    taskType = "CHOICE",
                    question = "He is a very ______ driver. He never exceeds the speed limit.",
                    options = listOf("careful", "carefully", "carefulness", "caring"),
                    correctOptionIndex = 0,
                    correctAnswerText = "careful",
                    explanationUzbek = "'driver' oti oldidan sifat keladi: 'a careful driver'."
                ),
                MurphyExerciseItem(
                    id = "u75_ex2",
                    exerciseNumber = "75.2",
                    taskType = "CHOICE",
                    question = "She speaks German very ______.",
                    options = listOf("fluently", "fluent", "fluency", "more fluent"),
                    correctOptionIndex = 0,
                    correctAnswerText = "fluently",
                    explanationUzbek = "'speaks' (gapiradi) fe'lini ifodalash uchun ravish 'fluently' kerak."
                ),
                MurphyExerciseItem(
                    id = "u75_ex3",
                    exerciseNumber = "75.3",
                    taskType = "CHOICE",
                    question = "This soup tastes ______! What did you put in it?",
                    options = listOf("delicious", "deliciously", "tasteful", "well"),
                    correctOptionIndex = 0,
                    correctAnswerText = "delicious",
                    explanationUzbek = "'tastes' sezgi fe'lidan keyin sifat (delicious) ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u75_ex4",
                    exerciseNumber = "75.4",
                    taskType = "CHOICE",
                    question = "I opened the door ______ because the baby was sleeping.",
                    options = listOf("quietly", "quiet", "quietness", "more quiet"),
                    correctOptionIndex = 0,
                    correctAnswerText = "quietly",
                    explanationUzbek = "'opened' fe'li qanday bajarilganini tasvirlash uchun ravish 'quietly' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u75_ex5",
                    exerciseNumber = "75.5",
                    taskType = "CHOICE",
                    question = "You look ______ today. Did you get enough sleep?",
                    options = listOf("tired", "tiredly", "tiring", "tire"),
                    correctOptionIndex = 0,
                    correctAnswerText = "tired",
                    explanationUzbek = "'look' fe'lidan keyin sifat keladi: 'You look tired'."
                )
            )
        ),

        // UNIT 76
        MurphyUnit(
            unitNumber = 76,
            title = "old / older, expensive / more expensive (Comparison 1)",
            subtitleUzbek = "Sifatlarning qiyosiy darajasi: -er va more",
            groupName = "13-Guruh: Adjectives & Adverbs (75–80)",
            keyTakeawaysUzbek = listOf(
                "QIYOSIY DARAJA (Comparative): Ikki narsa yoki shaxsni o'zaro solishtirish (kattaroq, qimmatroq).",
                "QISQA SIFATLAR (1 bo'g'inli): Oxiriga -ER qo'shiladi (old -> older, fast -> faster, cheap -> cheaper).",
                "UZUN SIFATLAR (2 va undan ortiq bo'g'inli): Oldiga MORE qo'yiladi (expensive -> more expensive, modern -> more modern, comfortable -> more comfortable).",
                "2 BO'G'INLI -Y BILAN TUGAGAN SIFATLAR: -IER ga aylanadi (happy -> happier, easy -> easier, heavy -> heavier).",
                "NOTO'G'RI SIFATLAR (Mustahkam yodlang!): good -> better (yaxshiroq), bad -> worse (yomonroq), far -> further/farther (uzoqroq)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Qisqa sifatlarga -ER qo'shish qoidalari",
                    formula = "Qisqa sifat + -ER (fast -> faster)",
                    explanationUzbek = "Qisqa sifatlarda imlo qoidalari:\n\n• old -> older, tall -> taller, cheap -> cheaper, young -> younger\n• -e bilan tugasa, faqat -r: nice -> nicer, large -> larger\n• Undosh + unli + undosh bo'lsa, oxirgi harf ikkilanadi: big -> bigger, hot -> hotter, fat -> fatter\n• -y bilan tugasa, -ier bo'ladi: easy -> easier, heavy -> heavier, early -> earlier\n\nMisollar:\n• Rome is older than London. (Rim Londondan qadimiyroq)\n• My new car is faster than my old one.",
                    examples = listOf(
                        MurphyExample("Today is hotter than yesterday.", "Bugun kechagidan ko'ra issiqroq.", "hot -> hotter"),
                        MurphyExample("This exercise is easier than the previous one.", "Bu mashq oldingisidan osonroq.", "easy -> easier")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Uzun sifatlar oldidan MORE qo'yish",
                    formula = "MORE + uzun sifat (more interesting)",
                    explanationUzbek = "Ko'p bo'g'inli sifatlarga '-er' qo'shib bo'lmaydi, ularning oldiga 'more' qo'yiladi:\n\n• expensive -> more expensive (qimmatroq)\n• polite -> more polite (xushmuomalaroq)\n• interesting -> more interesting (qiziqroq)\n• dangerous -> more dangerous (xavfliroq)\n\nNoto'g'ri sifatlar (Irregular):\n• good -> better (yaxshiroq)\n• bad -> worse (yomonroq)\n• far -> further (uzoqroq)",
                    examples = listOf(
                        MurphyExample("Travelling by train is more comfortable than by bus.", "Poyezdda sayohat qilish avtobusga qaraganda qulayroq.", "more comfortable"),
                        MurphyExample("My headache is worse today.", "Bugun bosh og'rig'im battarroq/yomonroq.", "bad -> worse"),
                        MurphyExample("Can you speak more slowly, please?", "Iltimos, sekinroq gapira olasizmi?", "more slowly")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u76_ex1",
                    exerciseNumber = "76.1",
                    taskType = "CHOICE",
                    question = "My flat is small, but your flat is even ______.",
                    options = listOf("smaller", "more small", "more smaller", "smallest"),
                    correctOptionIndex = 0,
                    correctAnswerText = "smaller",
                    explanationUzbek = "'small' qisqa sifat bo'lgani uchun unga '-er' qo'shiladi: smaller."
                ),
                MurphyExerciseItem(
                    id = "u76_ex2",
                    exerciseNumber = "76.2",
                    taskType = "CHOICE",
                    question = "A sports car is much ______ than a family bicycle.",
                    options = listOf("more expensive", "expensiver", "most expensive", "expensive"),
                    correctOptionIndex = 0,
                    correctAnswerText = "more expensive",
                    explanationUzbek = "'expensive' uch bo'g'inli uzun sifat bo'lgani sababli 'more expensive' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u76_ex3",
                    exerciseNumber = "76.3",
                    taskType = "CHOICE",
                    question = "Her English is getting ______ and better every day.",
                    options = listOf("better", "gooder", "more good", "best"),
                    correctOptionIndex = 0,
                    correctAnswerText = "better",
                    explanationUzbek = "'good' sifatining qiyosiy darajasi 'better' hisoblanadi."
                ),
                MurphyExerciseItem(
                    id = "u76_ex4",
                    exerciseNumber = "76.4",
                    taskType = "CHOICE",
                    question = "It's ______ to fly than to go by car.",
                    options = listOf("quicker", "more quick", "quickier", "quick"),
                    correctOptionIndex = 0,
                    correctAnswerText = "quicker",
                    explanationUzbek = "'quick' qisqa sifat: quicker."
                ),
                MurphyExerciseItem(
                    id = "u76_ex5",
                    exerciseNumber = "76.5",
                    taskType = "CHOICE",
                    question = "The weather was bad yesterday, but today it is ______.",
                    options = listOf("worse", "badder", "more bad", "worst"),
                    correctOptionIndex = 0,
                    correctAnswerText = "worse",
                    explanationUzbek = "'bad' sifatining qiyosiy shakli 'worse' bo'ladi."
                )
            )
        ),

        // UNIT 77
        MurphyUnit(
            unitNumber = 77,
            title = "older than ... / more expensive than ... (Comparison 2)",
            subtitleUzbek = "Taqqoslash konstruktsiyalari: ... than ..., a bit / much older",
            groupName = "13-Guruh: Adjectives & Adverbs (75–80)",
            keyTakeawaysUzbek = listOf(
                "THAN = '...ga qaraganda / ...dan ko'ra': He is taller THAN me. London is bigger THAN Paris.",
                "THAN dan keyin olmosh kelsa: 'than me' (og'zaki nutqda) yoki 'than I am' (rasmiy nutqda).",
                "KICHIK FARQNI aytishda: 'a bit' yoki 'a little' (a bit older, a little more expensive).",
                "KATTA FARQNI aytishda: 'much' yoki 'a lot' (much older, a lot more expensive, far better).",
                "TAQQOSLASH: Canada is much bigger than France (Kanada Fransiyadan ancha katta)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "THAN va olmoshlar bilan qo'llanishi",
                    formula = "Comparative + THAN + me / him / her / them",
                    explanationUzbek = "Qiyoslashda 'than' (qaraganda) so'zi ishlatiladi:\n\n• She is younger than me. (= younger than I am)\n• You woke up earlier than him. (= earlier than he did)\n• We arrived later than them.\n\nMisollar:\n• I can run faster than you.\n• Going by train is more expensive than going by bus.",
                    examples = listOf(
                        MurphyExample("You are two years older than me.", "Siz mendan ikki yosh kattasiz.", "older than me"),
                        MurphyExample("She works harder than her brother.", "U akasiga qaraganda qattiqroq ishlaydi.", "harder than...")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Darajani kuchaytirish (a bit vs much)",
                    formula = "a bit / much + comparative (-er / more...)",
                    explanationUzbek = "Farq miqdorini aniq ko'rsatish uchun comparative oldiga so'zlar qo'shiladi:\n\n1) Kichik farq (Biroz / sal):\n• a bit older (sal kattaroq)\n• a little more expensive (biroz qimmatroq)\n\n2) Katta farq (Ancha / ancha ko'p):\n• much bigger (ancha katta)\n• a lot more interesting (ancha qiziqroq)\n• far better (ancha yaxshiroq)",
                    examples = listOf(
                        MurphyExample("This jacket is a bit cheaper, but that one is much nicer.", "Bu kurtka sal arzonroq, lekin anavisi ancha chiroyliroq.", "a bit cheaper / much nicer"),
                        MurphyExample("I feel much better today, thank you.", "Bugun o'zimni ancha yaxshi his qilyapman, rahmat.", "much better")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u77_ex1",
                    exerciseNumber = "77.1",
                    taskType = "CHOICE",
                    question = "My brother is three years older ______ me.",
                    options = listOf("than", "then", "that", "as"),
                    correctOptionIndex = 0,
                    correctAnswerText = "than",
                    explanationUzbek = "Taqqoslashda 'than' (qaraganda) yoziladi ('then' - 'keyin' degani)."
                ),
                MurphyExerciseItem(
                    id = "u77_ex2",
                    exerciseNumber = "77.2",
                    taskType = "CHOICE",
                    question = "Travelling by plane is ______ faster than travelling by boat.",
                    options = listOf("much", "more", "very", "too"),
                    correctOptionIndex = 0,
                    correctAnswerText = "much",
                    explanationUzbek = "Qiyosiy daraja (faster) oldidan katta farqni ifodalash uchun 'much' keladi ('very faster' xato)."
                ),
                MurphyExerciseItem(
                    id = "u77_ex3",
                    exerciseNumber = "77.3",
                    taskType = "CHOICE",
                    question = "Could you speak ______ louder? I can hardly hear you.",
                    options = listOf("a bit", "much of", "very", "more"),
                    correctOptionIndex = 0,
                    correctAnswerText = "a bit",
                    explanationUzbek = "Sal balandroq gapirishni iltimos qilish: 'a bit louder' yoki 'a little louder'."
                ),
                MurphyExerciseItem(
                    id = "u77_ex4",
                    exerciseNumber = "77.4",
                    taskType = "CHOICE",
                    question = "She can run faster than ______.",
                    options = listOf("me", "I", "my", "mine"),
                    correctOptionIndex = 0,
                    correctAnswerText = "me",
                    explanationUzbek = "'than' dan keyin to'ldiruvchi olmosh keladi: 'than me'."
                ),
                MurphyExerciseItem(
                    id = "u77_ex5",
                    exerciseNumber = "77.5",
                    taskType = "CHOICE",
                    question = "Their house is ______ larger than ours.",
                    options = listOf("a lot", "too", "more", "very"),
                    correctOptionIndex = 0,
                    correctAnswerText = "a lot",
                    explanationUzbek = "'a lot larger' - ancha kattaroq."
                )
            )
        ),

        // UNIT 78
        MurphyUnit(
            unitNumber = 78,
            title = "not as ... as",
            subtitleUzbek = "Tenglik va notenglikni ifodalash: as ... as (kabi) va not as ... as",
            groupName = "13-Guruh: Adjectives & Adverbs (75–80)",
            keyTakeawaysUzbek = listOf(
                "AS ... AS = '...dek / ...kabi bir xil' (tenglik): He is as tall as his father (U otasidek baland).",
                "NOT AS ... AS = '...chalik emas' (notenglik): I am not as old as you (= You are older than me).",
                "Not as ... as dan keyin SIFAT O'ZINING ASLIY SHAKLIDA qoladi (not as older as DEYILMAYDI!).",
                "THE SAME AS = 'bir xil': Your bag is the same as mine.",
                "TAQQOSLASH: Rome is not as modern as Tokyo (Rim Tokiochalik zamonaviy emas)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "Not as ... as konstruktsiyasi",
                    formula = "not as + oddiy sifat + as",
                    explanationUzbek = "Biror narsa ikkinchisiga yetmasligini aytganda ishlatiladi:\n\n• Jack is not as old as he looks. (Jek ko'ringanichalik qari emas)\n• The city centre wasn't as crowded as usual. (Shahar markazi odatdagidek gavjum emas edi)\n• I don't play tennis as well as you. (Men tennisni sizchalik yaxshi o'ynamayman)\n\nBu konstruktsiya 'less ... than' ga teng:\n• It's not as cold today as it was yesterday. (= Today is warmer than yesterday)",
                    examples = listOf(
                        MurphyExample("She isn't as tall as her sister.", "U singlisidek uzun bo'yli emas.", "not as tall as"),
                        MurphyExample("The exam was not as difficult as I expected.", "Imtihon men kutganchalik qiyin bo'lmadi.", "not as difficult as")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "As ... as (tenglik) va The same as (bir xil)",
                    formula = "as + sifat + as | the same as",
                    explanationUzbek = "1) Ikkita narsa teng bo'lganda:\n• Can you send me the report as soon as possible? (Mumkin qadar tezroq)\n• He is as strong as a lion. (U sherdek kuchli)\n\n2) Butunlay bir xil bo'lganda:\n• Her salary is the same as mine. (Uning oyligi menikiday bir xil)\n• David is the same age as James. (David Jeyms bilan tengdosh)",
                    examples = listOf(
                        MurphyExample("My car is the same colour as yours.", "Mening mashinam sizniki bilan bir xil rangda.", "the same as"),
                        MurphyExample("Please come as quickly as you can.", "Iloji boricha tezroq keling.", "as quickly as")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u78_ex1",
                    exerciseNumber = "78.1",
                    taskType = "CHOICE",
                    question = "My apartment is not ______ big as yours.",
                    options = listOf("as", "than", "so", "like"),
                    correctOptionIndex = 0,
                    correctAnswerText = "as",
                    explanationUzbek = "'not as ... as' konstruktsiyasi: 'not as big as'."
                ),
                MurphyExerciseItem(
                    id = "u78_ex2",
                    exerciseNumber = "78.2",
                    taskType = "CHOICE",
                    question = "He doesn't earn ______ money as his brother.",
                    options = listOf("as much", "as many", "so much of", "more"),
                    correctOptionIndex = 0,
                    correctAnswerText = "as much",
                    explanationUzbek = "'money' sanalmaydi, shuning uchun 'as much money as' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u78_ex3",
                    exerciseNumber = "78.3",
                    taskType = "CHOICE",
                    question = "Your pen is exactly the same ______ mine.",
                    options = listOf("as", "than", "like", "with"),
                    correctOptionIndex = 0,
                    correctAnswerText = "as",
                    explanationUzbek = "'the same' doim 'as' bilan birikadi: the same as mine."
                ),
                MurphyExerciseItem(
                    id = "u78_ex4",
                    exerciseNumber = "78.4",
                    taskType = "CHOICE",
                    question = "I can't run as ______ as you.",
                    options = listOf("fast", "faster", "fastest", "more fast"),
                    correctOptionIndex = 0,
                    correctAnswerText = "fast",
                    explanationUzbek = "'as ... as' orasida sifat/ravish o'zining oddiy shaklida turadi: as fast as."
                ),
                MurphyExerciseItem(
                    id = "u78_ex5",
                    exerciseNumber = "78.5",
                    taskType = "CHOICE",
                    question = "Please let me know ______ soon as possible.",
                    options = listOf("as", "so", "than", "like"),
                    correctOptionIndex = 0,
                    correctAnswerText = "as",
                    explanationUzbek = "'as soon as possible' (ASAP) - imkon qadar tezroq."
                )
            )
        ),

        // UNIT 79
        MurphyUnit(
            unitNumber = 79,
            title = "the oldest / the most expensive (Superlatives)",
            subtitleUzbek = "Sifatlarning orttirma darajasi: the -est va the most",
            groupName = "13-Guruh: Adjectives & Adverbs (75–80)",
            keyTakeawaysUzbek = listOf(
                "ORTTIRMA DARAJA (Superlative): 3 ta yoki undan ortiq narsa orasida 'eng' bo'lgani (eng qari, eng chiroyli).",
                "Orttirma darajadagi barcha sifatlar oldidan DOIM 'THE' artikli keladi!",
                "Qisqa sifatlar: THE + sifat + -EST (the oldest, the fastest, the biggest).",
                "Uzun sifatlar: THE MOST + sifat (the most expensive, the most interesting).",
                "NOTO'G'RI SIFATLAR: good -> the best (eng yaxshi), bad -> the worst (eng yomon), far -> the furthest (eng uzoq).",
                "Joylar bilan 'IN' keladi: the highest mountain IN the world, the best player IN the team."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "The -est va The most qoidalari",
                    formula = "THE + qisqa + -est | THE MOST + uzun sifat",
                    explanationUzbek = "1) Qisqa sifatlar:\n• old -> the oldest, high -> the highest, cheap -> the cheapest\n• big -> the biggest, hot -> the hottest\n• easy -> the easiest, heavy -> the heaviest\n\n2) Uzun sifatlar:\n• dangerous -> the most dangerous (eng xavfli)\n• beautiful -> the most beautiful (eng go'zal)\n• popular -> the most popular (eng mashhur)\n\n3) Noto'g'ri sifatlar:\n• good -> the best (Yesterday was the best day of my life)\n• bad -> the worst (This is the worst film I've ever seen)",
                    examples = listOf(
                        MurphyExample("Everest is the highest mountain in the world.", "Everest dunyodagi eng baland tog'dir.", "the highest"),
                        MurphyExample("What is the most interesting book you have read?", "Siz o'qigan eng qiziqarli kitob qaysi?", "the most interesting"),
                        MurphyExample("She is the best singer in our school.", "U maktabimizdagi eng zo'r xonanda.", "the best")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "Superlative + Present Perfect konstruktsiyasi",
                    formula = "The superlative + I have ever seen / done",
                    explanationUzbek = "Hayotdagi eng yorqin tajribalarni aytganda Superlative + Present Perfect (ever) birga keladi:\n\n• It's the best meal I've ever had. (Bu hayotimda yegan eng yaxshi taomim)\n• Who is the most famous person you've ever met? (Siz uchratgan eng mashhur inson kim?)\n• That was the worst holiday we've ever had.",
                    examples = listOf(
                        MurphyExample("This is the easiest test I have ever taken.", "Bu men topshirgan eng oson test.", "the easiest ... I have ever..."),
                        MurphyExample("He is the kindest person I know.", "U men bilgan eng mehribon inson.", "the kindest")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u79_ex1",
                    exerciseNumber = "79.1",
                    taskType = "CHOICE",
                    question = "Russia is ______ country in the world by area.",
                    options = listOf("the largest", "largest", "the most large", "more large"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the largest",
                    explanationUzbek = "Orttirma darajada 'the largest' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u79_ex2",
                    exerciseNumber = "79.2",
                    taskType = "CHOICE",
                    question = "That was ______ movie I have ever watched in my life!",
                    options = listOf("the worst", "the bad", "the baddest", "worse"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the worst",
                    explanationUzbek = "'bad' sifatining orttirma darajasi 'the worst' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u79_ex3",
                    exerciseNumber = "79.3",
                    taskType = "CHOICE",
                    question = "Who is ______ popular actor in your country?",
                    options = listOf("the most", "most", "more", "the more"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the most",
                    explanationUzbek = "Uzun sifatlar oldidan 'the most popular' qo'yiladi."
                ),
                MurphyExerciseItem(
                    id = "u79_ex4",
                    exerciseNumber = "79.4",
                    taskType = "CHOICE",
                    question = "He is the fastest runner ______ the whole team.",
                    options = listOf("in", "of", "at", "on"),
                    correctOptionIndex = 0,
                    correctAnswerText = "in",
                    explanationUzbek = "Guruh yoki jamoa ichida bo'lganda 'in the team' predlogi ishlatiladi."
                ),
                MurphyExerciseItem(
                    id = "u79_ex5",
                    exerciseNumber = "79.5",
                    taskType = "CHOICE",
                    question = "Yesterday was ______ day of the year so far.",
                    options = listOf("the hottest", "hottest", "the most hot", "hotter"),
                    correctOptionIndex = 0,
                    correctAnswerText = "the hottest",
                    explanationUzbek = "'hot' bir bo'g'inli sifat: the hottest (t ikkilanadi)."
                )
            )
        ),

        // UNIT 80
        MurphyUnit(
            unitNumber = 80,
            title = "enough and too",
            subtitleUzbek = "'Yetarli' (enough) va 'Haddan tashqari / o'ta' (too) so'zlari",
            groupName = "13-Guruh: Adjectives & Adverbs (75–80)",
            keyTakeawaysUzbek = listOf(
                "ENOUGH = Yetarli / yetarlicha darajada. SIFATDAN KEYIN keladi: warm enough, big enough, old enough.",
                "ENOUGH OTDAN OLDIN keladi: enough money, enough time, enough food.",
                "TOO = Haddan tashqari (keragidan ortiq / salbiy ma'noda). Sifatdan OLDIN keladi: too expensive, too hot, too small.",
                "TOO MUCH (sanalmaydigan) / TOO MANY (sanoqli) = Haddan tashqari ko'p: too much sugar, too many people.",
                "KONSTRUKTSIYA: too ... to do (qilish uchun haddan tashqari): It is too cold to go swimming (Cho'milish uchun havo o'ta sovuq)."
            ),
            sections = listOf(
                MurphySection(
                    sectionCode = "A",
                    heading = "ENOUGH so'z tartibi (Sifatdan keyin, otdan oldin)",
                    formula = "Sifat + ENOUGH | ENOUGH + Ot",
                    explanationUzbek = "1) Sifat yoki ravishdan KEYIN keladi:\n• He isn't old enough to drive a car. (U mashina haydash uchun yetarlicha katta emas)\n• Is your coffee hot enough? (Qahvangiz yetarlicha issiqmi?)\n• She didn't speak loudly enough. (U yetarlicha baland gapirmadi)\n\n2) Otlardan OLDIN keladi:\n• I have enough money to buy dinner. (Menda tushlik sotib olishga yetarli pul bor)\n• There aren't enough chairs for everyone. (Hamma uchun yetarli stul yo'q)",
                    examples = listOf(
                        MurphyExample("We have enough time to catch the train.", "Poyezdga yetib olish uchun yetarli vaqtimiz bor.", "enough time"),
                        MurphyExample("This room is big enough for five people.", "Bu xona besh kishi uchun yetarlicha katta.", "big enough")
                    )
                ),
                MurphySection(
                    sectionCode = "B",
                    heading = "TOO ning ishlatilishi va konstruktsiyalari",
                    formula = "TOO + sifat | TOO + sifat + TO do",
                    explanationUzbek = "TOO 'haddan ziyod ko'p' degani bo'lib, noqulaylikni bildiradi:\n\n• The shoes are too small for me. (Poyezabzallar menga haddan tashqari kichik)\n• It's too late to call him now. (Hozir unga qo'ng'iroq qilish uchun juda kech)\n• This coffee is too hot to drink. (Bu qahva ichish uchun o'ta qaynoq)\n\nTOO vs VERY farqi:\n• The coffee is very hot, but I can drink it. (Qahva juda issiq, lekin ichsa bo'ladi)\n• The coffee is too hot; I can't drink it! (Qahva o'ta qaynoq, ichib bo'lmaydi!)",
                    examples = listOf(
                        MurphyExample("The music is too loud. Please turn it down.", "Musiqa haddan tashqari baland. Pasaytiring.", "too loud"),
                        MurphyExample("There were too many cars on the road.", "Yo'lda haddan tashqari ko'p mashinalar bor edi.", "too many cars")
                    )
                )
            ),
            exercises = listOf(
                MurphyExerciseItem(
                    id = "u80_ex1",
                    exerciseNumber = "80.1",
                    taskType = "CHOICE",
                    question = "I don't have ______ to finish this project today.",
                    options = listOf("enough time", "time enough", "too time", "many time"),
                    correctOptionIndex = 0,
                    correctAnswerText = "enough time",
                    explanationUzbek = "'enough' otdan (time) oldin keladi: 'enough time'."
                ),
                MurphyExerciseItem(
                    id = "u80_ex2",
                    exerciseNumber = "80.2",
                    taskType = "CHOICE",
                    question = "He is not tall ______ to be a professional basketball player.",
                    options = listOf("enough", "too", "as", "very"),
                    correctOptionIndex = 0,
                    correctAnswerText = "enough",
                    explanationUzbek = "'enough' sifatdan (tall) keyin keladi: 'tall enough'."
                ),
                MurphyExerciseItem(
                    id = "u80_ex3",
                    exerciseNumber = "80.3",
                    taskType = "CHOICE",
                    question = "The soup is ______ hot to eat right now. Let it cool down.",
                    options = listOf("too", "enough", "as", "much"),
                    correctOptionIndex = 0,
                    correctAnswerText = "too",
                    explanationUzbek = "Yeb bo'lmaydigan darajada qaynoqlikni 'too hot to eat' ifodalaydi."
                ),
                MurphyExerciseItem(
                    id = "u80_ex4",
                    exerciseNumber = "80.4",
                    taskType = "CHOICE",
                    question = "You put too ______ sugar in my tea! It's too sweet.",
                    options = listOf("much", "many", "enough", "few"),
                    correctOptionIndex = 0,
                    correctAnswerText = "much",
                    explanationUzbek = "'sugar' sanalmaydigan ot bo'lgani sababli 'too much sugar' bo'ladi."
                ),
                MurphyExerciseItem(
                    id = "u80_ex5",
                    exerciseNumber = "80.5",
                    taskType = "CHOICE",
                    question = "This box is ______ heavy for me to carry alone.",
                    options = listOf("too", "enough", "as", "so much"),
                    correctOptionIndex = 0,
                    correctAnswerText = "too",
                    explanationUzbek = "'too heavy to carry' - ko'tarish uchun o'ta og'ir."
                )
            )
        )
    )
}
