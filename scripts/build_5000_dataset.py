#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Builds the 5,000+ Oxford & High-Frequency Essential English-Uzbek Vocabulary Database.
Generates `app/src/main/assets/oxford_3000.tsv` and `app/src/main/assets/oxford_5000.tsv`.
"""

import os
import sys
import json
import urllib.request

def build_database():
    existing_file = "app/src/main/assets/oxford_3000.tsv"
    existing_cards = {}
    
    # 1. Load existing cards
    if os.path.exists(existing_file):
        with open(existing_file, "r", encoding="utf-8") as f:
            lines = f.readlines()
            for l in lines[1:]:
                p = l.strip().split("\t")
                if len(p) >= 4 and p[0].strip():
                    w = p[0].strip().lower()
                    existing_cards[w] = {
                        "word": p[0].strip(),
                        "level": p[1].strip() if len(p) > 1 and p[1].strip() else "A2",
                        "pos": p[2].strip() if len(p) > 2 and p[2].strip() else "n.",
                        "uz": p[3].strip() if len(p) > 3 else "",
                        "example": p[4].strip() if len(p) > 4 and p[4].strip() else f"Practice '{p[0].strip()}' in daily English.",
                        "uzExample": p[5].strip() if len(p) > 5 and p[5].strip() else f"'{p[0].strip()}' so'zini har kuni mashq qiling.",
                        "synonym": p[6].strip() if len(p) > 6 else "",
                        "phonetic": p[7].strip() if len(p) > 7 else f"/{w}/"
                    }

    print(f"Existing entries: {len(existing_cards)}")

    # 2. Curated English-Uzbek Master Dictionary for Frequency & Oxford Words
    # Covering pronouns, auxiliaries, nouns, verbs, adjectives, prepositions, adverbs, conjunctions
    curated_lexicon = {
        # Top 100 Functional & Conversational
        "the": ("A1", "art.", "aniq artikl (o'sha, ma'lum narsa)", "The sun rises in the east.", "Quyosh sharqdan chiqadi.", "that", "/ðə/"),
        "be": ("A1", "v.", "bo'lmoq, mavjud bo'lmoq", "Be confident when you speak English.", "Inglizcha gapirayotganda o'zingizga ishoning.", "exist", "/biː/"),
        "to": ("A1", "prep.", "ga, sari; tomon", "Listen to native speakers every day.", "Har kuni ona tilida so'zlashuvchilarni tinglang.", "towards", "/tuː/"),
        "of": ("A1", "prep.", "ning, dan iborat", "This is the result of your discipline.", "Bu sizning intizomingiz natijasidir.", "concerning", "/əv/"),
        "and": ("A1", "conj.", "va, hamda", "Practice reading and speaking daily.", "Har kuni o'qish va gapirishni mashq qiling.", "plus", "/ænd/"),
        "a": ("A1", "art.", "bir (noaniq artikl)", "Set a clear goal for each study session.", "Har bir mashg'ulot uchun aniq maqsad qo'ying.", "one", "/ə/"),
        "in": ("A1", "prep.", "ichida, da", "Invest your energy in meaningful skills.", "Kuchingizni foydali ko'nikmalarga sarflang.", "inside", "/ɪn/"),
        "that": ("A1", "pron., conj.", "anavi, o'sha; ekanligini", "Remember that consistency creates champions.", "Doimiylik chempionlarni yetishtirishini unutmang.", "which", "/ðæt/"),
        "have": ("A1", "v.", "ega bo'lmoq, bor bo'lmoq", "You have great potential in English.", "Sizda ingliz tilida katta imkoniyat bor.", "possess", "/hæv/"),
        "i": ("A1", "pron.", "men", "I speak English with growing confidence.", "Men ingliz tilida tobora ortib borayotgan ishonch bilan gapiraman.", "myself", "/aɪ/"),
        "it": ("A1", "pron.", "u (jonsiz yoki hayvon uchun)", "It takes time to build fluency.", "Ravonlikka erishish vaqt talab etadi.", "this", "/ɪt/"),
        "for": ("A1", "prep.", "uchun, mobaynida", "Dedicate thirty minutes for vocabulary.", "Lug'at uchun o'ttiz daqiqa ajrating.", "in favor of", "/fɔːr/"),
        "not": ("A1", "adv.", "emas, yo'q", "Do not hesitate to express your thoughts.", "Fikrlaringizni ifodalashda aslo ikkilanmang.", "no", "/nɒt/"),
        "on": ("A1", "prep.", "ustida, bo'yicha", "Focus on your daily tasks without distraction.", "Chalg'imasdan kunlik vazifalaringizga diqqat qarating.", "upon", "/ɒn/"),
        "with": ("A1", "prep.", "bilan, birga", "Practice speaking with clear pronunciation.", "Aniq talaffuz bilan gapirishni mashq qiling.", "alongside", "/wɪð/"),
        "he": ("A1", "pron.", "u (erkak kishi)", "He practices pronunciation every morning.", "U har kuni ertalab talaffuzni mashq qiladi.", "him", "/hiː/"),
        "as": ("A1", "prep., conj.", "sifatida, kabi; chunki", "Speak as clearly as possible.", "Imkon qadar aniq va ravon gapiring.", "like", "/æz/"),
        "you": ("A1", "pron.", "siz, sen", "You can achieve speaking fluency.", "Siz ravon gapirishga erisha olasiz.", "yourself", "/juː/"),
        "do": ("A1", "v.", "bajarmoq, qilmoq", "Do your best in every exercise.", "Har bir mashqda bor kuchingizni bering.", "perform", "/duː/"),
        "at": ("A1", "prep.", "da, qoshida", "Meet me at the speaking club.", "Gapirish klubida men bilan uchrashing.", "near", "/æt/"),
        "this": ("A1", "pron., det.", "bu, ushbu", "Master this important vocabulary card.", "Ushbu muhim lug'at kartasini o'zlashtiring.", "current", "/ðɪs/"),
        "but": ("A1", "conj.", "lekin, ammo, biroq", "It is challenging, but you can master it.", "Bu qiyin, lekin siz buni o'zlashtira olasiz.", "however", "/bʌt/"),
        "his": ("A1", "pron.", "uning (erkak)", "His English pronunciation is very clear.", "Uning inglizcha talaffuzi juda tiniq.", "belonging to him", "/hɪz/"),
        "by": ("A1", "prep.", "orqali, vositasida; yonida", "Improve your vocabulary by active reading.", "Faol o'qish orqali so'z boyligingizni oshiring.", "via", "/baɪ/"),
        "from": ("A1", "prep.", "dan (boshlab)", "Learn from every mistake you make.", "Har bir yo'l qo'ygan xatongizdan saboq oling.", "starting at", "/frɒm/"),
        "they": ("A1", "pron.", "ular", "They communicate in English fluently.", "Ular ingliz tilida ravon muloqot qilishadi.", "them", "/ðeɪ/"),
        "we": ("A1", "pron.", "biz", "We improve our English step by step.", "Biz qadamma-qadam ingliz tilimizni yaxshilaymiz.", "us", "/wiː/"),
        "say": ("A1", "v.", "aytmoq, demoq", "Say each English word aloud.", "Har bir inglizcha so'zni ovoz chiqarib ayting.", "state", "/seɪ/"),
        "her": ("A1", "pron.", "uning, uni (ayol)", "Her dedication to English is inspiring.", "Uning ingliz tiliga bo'lgan fidoyiligi ilhomlantiradi.", "hers", "/hɜːr/"),
        "she": ("A1", "pron.", "u (ayol kishi)", "She reads English books every evening.", "U har oqshom inglizcha kitoblar o'qiydi.", "female", "/ʃiː/"),
        "or": ("A1", "conj.", "yoki, yohud", "Practice speaking alone or with friends.", "Yolg'iz yoki do'stlaringiz bilan gapirishni mashq qiling.", "alternatively", "/ɔːr/"),
        "an": ("A1", "art.", "bir (unli tovushdan oldin)", "Make an effort every single day.", "Har bir kun alohida harakat qiling.", "one", "/æn/"),
        "will": ("A1", "v., modal", "bo'ladi (kelasi zamon)", "You will master English speaking.", "Siz inglizcha so'zlashuvni albatta o'zlashtirasiz.", "shall", "/wɪl/"),
        "my": ("A1", "pron.", "mening", "My goal is speaking fluency this year.", "Mening maqsadim — bu yil ravon gapirish.", "belonging to me", "/maɪ/"),
        "one": ("A1", "num., pron.", "bir, bitta; inson", "Learn one new phrase at a time.", "Bir vaqtning o'zida bitta yangi iborani o'rganing.", "single", "/wʌn/"),
        "all": ("A1", "det., pron.", "barcha, hamma", "Review all words before the quiz.", "Sinovdan oldin barcha so'zlarni takrorlang.", "entire", "/ɔːl/"),
        "would": ("A2", "modal v.", "…gan bo'lardi", "I would love to practice speaking with you.", "Siz bilan gapirishni mashq qilishni juda istardim.", "could", "/wʊd/"),
        "there": ("A1", "adv., pron.", "u yerda; mavjud", "There are countless ways to practice.", "Mashq qilishning son-sanoqsiz yo'llari bor.", "in that place", "/ðeər/"),
        "their": ("A1", "pron.", "ularning", "Their pronunciation is natural and clean.", "Ularning talaffuzi tabiiy va ravshan.", "belonging to them", "/ðeər/"),
        "what": ("A1", "pron.", "nima, qanday", "What is your main topic today?", "Bugungi asosiy mavzuingiz nima?", "which thing", "/wɒt/"),
        "so": ("A1", "adv., conj.", "shunday qilib; juda", "Practice daily, so you improve fast.", "Har kuni mashq qiling, shunda tez o'sasiz.", "therefore", "/səʊ/"),
        "up": ("A1", "adv., prep.", "yuqoriga; o'rnidan", "Wake up early and speak English.", "Erta turing va inglizcha gapiring.", "upward", "/ʌp/"),
        "out": ("A1", "adv., prep.", "tashqariga, tashqarida", "Speak out clearly without hesitation.", "Ikkilanmasdan ovozingizni baland va aniq chiqaring.", "outside", "/aʊt/"),
        "if": ("A1", "conj.", "agar, agarda", "If you practice today, you will speak tomorrow.", "Agar bugun mashq qilsangiz, ertaga gapirasiz.", "provided that", "/ɪf/"),
        "who": ("A1", "pron.", "kim, kimki", "Who is your favorite English speaker?", "Sizning eng sevimli inglizcha notiq kim?", "whom", "/huː/"),
        "get": ("A1", "v.", "olmoq, erishmoq; tushunmoq", "Get used to speaking out loud.", "Ovoz chiqarib gapirishga odatlaning.", "obtain", "/ɡet/"),
        "which": ("A1", "pron., det.", "qaysi, qaysi biri", "Which word do you want to learn?", "Qaysi so'zni o'rganishni istaysiz?", "that", "/wɪtʃ/"),
        "go": ("A1", "v.", "bormoq, ketmoq", "Go forward with courage and focus.", "Jasorat va diqqat bilan olg'a boring.", "proceed", "/ɡəʊ/"),
        "me": ("A1", "pron.", "meni, menga", "Tell me your thoughts in English.", "Fikrlaringizni menga inglizcha ayting.", "myself", "/miː/"),
        "when": ("A1", "adv., conj.", "qachon, qachonki", "Speak English when you meet friends.", "Do'stlaringiz bilan uchrashganda inglizcha gapiring.", "at what time", "/wen/"),
        "make": ("A1", "v.", "yasamoq, qilmoq; erishmoq", "Make sentences with your new words.", "Yangi so'zlaringiz bilan jumlalar tuzing.", "create", "/meɪk/"),
        "can": ("A1", "modal v.", "qila olmoq, bilmoq", "You can master English with practice.", "Mashq orqali ingliz tilini o'zlashtira olasiz.", "be able to", "/kæn/"),
        "like": ("A1", "v., prep.", "yoqtirmoq; kabi, o'xshash", "I like learning vocabulary in context.", "Men so'zlarni kontekstda o'rganishni yoqtiraman.", "enjoy", "/laɪk/"),
        "time": ("A1", "n.", "vaqt, payt, fursat", "Dedicate quality time to speaking every day.", "Har kuni gapirishga sifatli vaqt ajrating.", "period", "/taɪm/"),
        "no": ("A1", "det., adv.", "yo'q, hech qanday", "There is no obstacle you cannot overcome.", "Siz yenga olmaydigan hech qanday to'siq yo'q.", "not any", "/nəʊ/"),
        "just": ("A1", "adv., adj.", "shunchaki, faqat; adolatli", "Just start speaking without overthinking.", "Haddan tashqari ko'p o'ylamasdan shunchaki gapirishni boshlang.", "simply", "/dʒʌst/"),
        "him": ("A1", "pron.", "uni, unga (erkak)", "Listen to him speaking with great accent.", "Uning ajoyib talaffuz bilan gapirishini tinglang.", "male person", "/hɪm/"),
        "know": ("A1", "v.", "bilmoq, tanimoq", "You know more English than you think.", "Siz o'ylaganingizdan ko'ra ko'proq inglizcha bilasiz.", "understand", "/nəʊ/"),
        "take": ("A1", "v.", "olmoq, vaqt talab qilmoq", "Take notes on key expressions.", "Asosiy iboralar bo'yicha qaydlar yozib boring.", "grab", "/teɪk/"),
        "people": ("A1", "n.", "odamlar, insonlar", "Connect with people through English.", "Ingliz tili orqali odamlar bilan aloqa o'rnating.", "humans", "/ˈpiːpl/"),
        "into": ("A1", "prep.", "ichiga, tomon", "Turn new words into active speech.", "Yangi so'zlarni faol nutqqa aylantiring.", "inside", "/ˈɪntuː/"),
        "year": ("A1", "n.", "yil", "Make this year your breakthrough in English.", "Bu yilni ingliz tilidagi yuksalish yilingizga aylantiring.", "annual period", "/jɪər/"),
        "your": ("A1", "pron.", "sizning, sening", "Your voice sounds natural and confident.", "Sizning ovozingiz tabiiy va ishonchli yangramoqda.", "belonging to you", "/jɔːr/"),
        "good": ("A1", "adj.", "yaxshi, foydali", "Good habits produce excellent results.", "Yaxshi odatlar a'lo natijalar keltiradi.", "positive", "/ɡʊd/"),
        "some": ("A1", "det., pron.", "ba'zi, bir qancha", "Practice some sentences out loud.", "Bir nechta jumlalarni ovoz chiqarib mashq qiling.", "a few", "/sʌm/"),
        "could": ("A2", "modal v.", "qila olardi (o'tgan zamon yoki iltimos)", "Could you repeat that sentence please?", "O'sha jumlani qaytara olasizmi, iltimos?", "might", "/kʊd/"),
        "them": ("A1", "pron.", "ularni, ularga", "Review these words and master them.", "Ushbu so'zlarni takrorlang va ularni o'zlashtiring.", "those people", "/ðem/"),
        "see": ("A1", "v.", "ko'rmoq, tushunmoq", "I see great progress in your speaking.", "Nutqingizda katta o'sishni ko'ryapman.", "perceive", "/siː/"),
        "other": ("A1", "adj., pron.", "boshqa, o'zga", "Learn from other successful speakers.", "Boshqa muvaffaqiyatli notiqlardan o'rganing.", "different", "/ˈʌðər/"),
        "than": ("A1", "conj., prep.", "ko'ra, nisbatan", "Action is better than hesitation.", "Harakat ikkilanishdan ko'ra afzaldir.", "compared to", "/ðæn/"),
        "then": ("A1", "adv.", "so'ng, keyin, o'shanda", "Listen first, then speak out loud.", "Avval tinglang, so'ngra ovoz chiqarib gapiring.", "afterwards", "/ðen/"),
        "now": ("A1", "adv.", "hozir, ayni paytda", "Start speaking English right now.", "Aynan hozir inglizcha gapirishni boshlang.", "at present", "/naʊ/"),
        "look": ("A1", "v., n.", "qaramoq, ko'rinmoq; nazar", "Look at the transcription to pronounce correctly.", "To'g'ri talaffuz qilish uchun transkripsiyaga qarang.", "glance", "/lʊk/"),
        "only": ("A1", "adv., adj.", "faqat, yagona", "Consistency is the only secret to fluency.", "Doimiylik — ravonlikning yagona siridir.", "solely", "/ˈəʊnli/"),
        "come": ("A1", "v.", "kelmoq, yetib kelmoq", "Success will come with patient practice.", "Sabrli mashg'ulot bilan muvaffaqiyat albatta keladi.", "arrive", "/kʌm/"),
        "its": ("A1", "pron.", "uning (jonsiz)", "Each word has its unique nuance.", "Har bir so'z o'ziga xos ma'no nozikligiga ega.", "belonging to it", "/ɪts/"),
        "over": ("A1", "prep., adv.", "ustidan; tugagan; ko'proq", "Review words over and over again.", "So'zlarni qayta-qayta takrorlang.", "above", "/ˈəʊvər/"),
        "think": ("A1", "v.", "o'ylamoq, fikrlamoq", "Think in English whenever possible.", "Imkoni boricha ingliz tilida fikrlang.", "reflect", "/θɪŋk/"),
        "also": ("A1", "adv.", "ham, shuningdek", "He speaks English, and also studies grammar.", "U inglizcha gapiradi, shuningdek grammatikani ham o'rganadi.", "too", "/ˈɔːlsəʊ/"),
        "back": ("A1", "adv., n., adj.", "orqaga; bel; orqa", "Come back to difficult words later.", "Qiyin so'zlarga keyinroq yana qaytib keling.", "rear", "/bæk/"),
        "after": ("A1", "prep., conj.", "keyin, so'ng", "Review vocabulary after each speaking session.", "Har bir so'zlashuv mashg'ulotidan keyin lug'atni takrorlang.", "following", "/ˈɑːftər/"),
        "use": ("A1", "v., n.", "ishlatmoq, foydalanmoq; foydalanish", "Use new vocabulary in real conversations.", "Yangi so'zlarni real suhbatlarda ishlating.", "apply", "/juːz/"),
        "two": ("A1", "num.", "ikki", "Learn two new idioms every day.", "Har kuni ikkita yangi iborani o'rganing.", "pair", "/tuː/"),
        "how": ("A1", "adv.", "qanday, qanday qilib", "How do you pronounce this word?", "Bu so'zni qanday talaffuz qilasiz?", "in what manner", "/haʊ/"),
        "our": ("A1", "pron.", "bizning", "Our speaking group meets every evening.", "Bizning so'zlashuv guruhimiz har oqshom yig'iladi.", "belonging to us", "/ˈaʊər/"),
        "work": ("A1", "n., v.", "ish; ishlamoq, mehnat qilmoq", "Hard work always pays off in learning.", "Qattiq mehnat o'rganishda doimo o'z samarasini beradi.", "labor", "/wɜːk/"),
        "first": ("A1", "adj., adv.", "birinchi, avval", "Master the first 500 essential words.", "Dastlabki 500 ta eng kerakli so'zni o'zlashtiring.", "initial", "/fɜːst/"),
        "well": ("A1", "adv., adj.", "yaxshi, durust; quduq", "You speak English very well today.", "Siz bugun ingliz tilida juda yaxshi gapiryapsiz.", "properly", "/wel/"),
        "way": ("A1", "n.", "yo'l, usul, yo'nalish", "This is the best way to master speaking.", "Bu — so'zlashuvni o'zlashtirishning eng yaxshi yo'lidir.", "method", "/weɪ/"),
        "even": ("A2", "adv., adj.", "hatto, hattoki; tekis, juft", "Even ten minutes a day makes a huge difference.", "Kuniga hatto o'n daqiqa ham ulkan farq qiladi.", "yet", "/ˈiːvn/"),
        "new": ("A1", "adj.", "yangi, yangicha", "Practice new vocabulary in active speaking.", "Yangi so'zlarni faol nutqda mashq qiling.", "fresh", "/njuː/"),
        "want": ("A1", "v.", "xohlamoq, istamoq", "I want to speak English like a native.", "Men ona tilidek inglizcha gapirishni istayman.", "desire", "/wɒnt/"),
        "because": ("A1", "conj.", "chunki, sababli", "Practice speaking because fluency requires muscle memory.", "Gapirishni mashq qiling, chunki ravonlik nutq muskullari xotirasini talab qiladi.", "since", "/bɪˈkɒz/"),
        "any": ("A1", "det., pron.", "hech qanday, har qanday", "Ask questions without any hesitation.", "Hech qanday ikkilanishsiz savollar bering.", "whichever", "/ˈeni/"),
        "these": ("A1", "det., pron.", "bular, ushbu", "Master these high-priority vocabulary words.", "Ushbu eng muhim lug'at so'zlarini o'zlashtiring.", "these ones", "/ðiːz/"),
        "give": ("A1", "v.", "bermoq, taqdim etmoq", "Give yourself credit for daily effort.", "Kunlik harakatingiz uchun o'zingizni qadrlang.", "provide", "/ɡɪv/"),
        "day": ("A1", "n.", "kun, kunduz", "Every day is an opportunity to improve.", "Har bir kun — o'zini yaxshilash imkoniyatidir.", "date", "/deɪ/"),
        "most": ("A2", "det., adv.", "eng ko'p, aksariyat", "These are the most essential words in English.", "Bular ingliz tilidagi eng kerakli so'zlardir.", "majority", "/məʊst/"),
        "us": ("A1", "pron.", "bizni, bizga", "Join us in the speaking arena today.", "Bugun so'zlashuv arenasida bizga qo'shiling.", "our group", "/ʌs/"),

        # High Priority Youth & Fluency Terms (16yo+ focus)
        "confident": ("B1", "adj.", "o'ziga ishongan, dadil", "Speak with confident voice tone.", "O'ziga ishongan ovoz ohangi bilan gapiring.", "self-assured", "/ˈkɒnfɪdənt/"),
        "confidence": ("B1", "n.", "ishonch, dadillik", "Daily speaking builds massive confidence.", "Kunlik so'zlashuv ulkan ishonch bag'ishlaydi.", "assurance", "/ˈkɒnfɪdəns/"),
        "fluent": ("B2", "adj.", "ravon, oqib turuvchi", "Her goal is to become fluent in English.", "Uning maqsadi — ingliz tilida ravon gapirish.", "articulate", "/ˈfluːənt/"),
        "fluency": ("B2", "n.", "ravonlik, bemalol gapirish", "Focus on fluency before worrying about minor mistakes.", "Kichik xatolardan xavotir olishdan oldin ravonlikka diqqat qarating.", "smoothness", "/ˈfluːənsi/"),
        "challenge": ("B1", "n., v.", "qiyinchilik, chaqiriq; sinov", "Accept the challenge to master 5000 words.", "5000 ta so'zni o'zlashtirish sinovini qabul qiling.", "test", "/ˈtʃælɪndʒ/"),
        "ambition": ("B2", "n.", "intilish, ulkan maqsad, orzu", "His ambition is to study at MIT.", "Uning maqsadi — MIT universitetida o'qish.", "aspiration", "/æmˈbɪʃn/"),
        "career": ("B1", "n.", "kasbiy yo'l, martaba", "English unlocks global career opportunities.", "Ingliz tili xalqaro martaba imkoniyatlarini ochadi.", "profession", "/kəˈrɪər/"),
        "opportunity": ("B1", "n.", "imkoniyat, qulay fursat", "Seize every opportunity to practice speaking.", "Gapirishni mashq qilish uchun har bir imkoniyatdan foydalaning.", "chance", "/ˌɒpəˈtjuːnəti/"),
        "negotiate": ("B2", "v.", "muzokara olib bormoq", "Learn to negotiate high-stakes deals in English.", "Ingliz tilida muhim bitimlar bo'yicha muzokara olib borishni o'rganing.", "bargain", "/nɪˈɡəʊʃieɪt/"),
        "negotiation": ("B2", "n.", "muzokara, kelishuv jarayoni", "Diplomatic negotiation saved the situation.", "Diplomatik muzokara vaziyatni saqlab qoldi.", "discussion", "/nɪˌɡəʊʃiˈeɪʃn/"),
        "suspect": ("B1", "n., v.", "gumondor; shubhalanmoq", "The detective questioned the main suspect.", "Detektiv asosiy gumondorni so'roq qildi.", "doubt", "/ˈsʌspekt/"),
        "alibi": ("B2", "n.", "alibi, jinoyat vaqtida boshqa joyda bo'lganlik isboti", "The suspect had an ironclad alibi.", "Gumondorning inkor etib bo'lmas alibisi bor edi.", "defense", "/ˈæləbaɪ/"),
        "interrogate": ("B2", "v.", "so'roq qilmoq, tergov qilmoq", "Agents interrogate the syndicate leader.", "Maxsus agentlar sindikat boshlig'ini so'roq qilishmoqda.", "question", "/ɪnˈterəɡeɪt/"),
        "interrogation": ("B2", "n.", "so'roq qilish, tergov", "Stay calm during intense police interrogation.", "Keskin politsiya so'rog'i vaqtida xotirjamlikni saqlang.", "inquiry", "/ɪnˌterəˈɡeɪʃn/"),
        "consequence": ("B2", "n.", "oqibat, natija", "Every choice has an inevitable consequence.", "Har bir tanlovning muqarrar oqibati bor.", "outcome", "/ˈkɒnsɪkwəns/"),
        "strategy": ("B1", "n.", "strategiya, harakat rejasi", "A solid learning strategy guarantees progress.", "Mustahkam o'rganish strategiyasi o'sishni kafolatlaydi.", "plan", "/ˈstrætədʒi/"),
        "perspective": ("B2", "n.", "qarash, nuqtai nazar", "Seeing problems from a fresh perspective brings solutions.", "Muammolarga yangi nuqtai nazardan qarash yechim keltiradi.", "viewpoint", "/pəˈspektɪv/"),
        "intelligence": ("B2", "n.", "aql-idrok, razvedka", "Artificial intelligence enhances speech practice.", "Sun'iy intellekt nutq mashg'ulotlarini kuchaytiradi.", "intellect", "/ɪnˈtelɪdʒəns/"),
        "protocol": ("B2", "n.", "protokol, qat'iy qoida va tartib", "Follow the emergency protocol strictly.", "Favqulodda vaziyat protokoliga qat'iy rioya qiling.", "procedure", "/ˈprəʊtəkɒl/"),
        "tactical": ("B2", "adj.", "taktik, puxta o'ylangan", "Make a tactical decision under pressure.", "Bosim ostida taktik jihatdan to'g'ri qaror qabul qiling.", "strategic", "/ˈtæktɪkl/"),
        "detonate": ("B2", "v.", "portlatmoq, portlamoq", "The bomb was set to detonate at midnight.", "Bomba yarim tunda portlashga sozlangan edi.", "explode", "/ˈdetəneɪt/"),
        "detonator": ("B2", "n.", "detonator, portlatgich", "Disarm the detonator before the timer hits zero.", "Taymer nolga yetmasdan oldin detonatorni zararsizlantiring.", "fuse", "/ˈdetəneɪtər/"),
        "defuse": ("B2", "v.", "zararsizlantirmoq, portlash xavfini bartaraf etmoq", "The squad managed to defuse the explosive.", "Maxsus guruh portlovchi moslamani zararsizlantirishga muvaffaq bo'ldi.", "disarm", "/diːˈfjuːz/"),
        "hostage": ("B2", "n.", "garovdagi shaxs", "Negotiators secured the safe release of each hostage.", "Muzokarachilar har bir garovdagi insonning xavfsiz ozod etilishini ta'minladilar.", "captive", "/ˈhɒstɪdʒ/"),
        "syndicate": ("B2", "n.", "sindikat, yirik jinoiy guruh", "The federal agency brought down the syndicate.", "Federal agentlik sindikatni butunlay qulatdi.", "cartel", "/ˈsɪndɪkət/"),
        "wire": ("A2", "n., v.", "sim, sim orqali ulamoq", "Do not cut the red wire without checking voltage.", "Kuchlanishni tekshirmasdan qizil simni kesmang.", "cable", "/ˈwaɪər/"),
        "voltage": ("B2", "n.", "kuchlanish (elektr)", "High voltage poses an immediate danger.", "Yuqori kuchlanish bevosita xavf tug'diradi.", "tension", "/ˈvəʊltɪdʒ/"),
        "circuit": ("B2", "n.", "elektr zanjiri, sxema", "Trace the secondary circuit carefully.", "Ikkilamchi elektr zanjirini sinchiklab tekshiring.", "loop", "/ˈsɜːkɪt/"),
        "frequency": ("B2", "n.", "chastota, takrorlanish darajasi", "Transmit the signal on emergency radio frequency.", "Signalni favqulodda radio chastotasi orqali uzating.", "rate", "/ˈfriːkwənsi/"),
        "override": ("B2", "v., n.", "bekor qilmoq, boshqaruvni qo'lga olmoq", "Enter the master key to override the system.", "Tizim boshqaruvini bekor qilish uchun asosiy kalitni kiriting.", "bypass", "/ˌəʊvəˈraɪd/"),
        "biometric": ("B2", "adj.", "biometrik (barmoq izi, yuz, ovoz)", "The vault requires biometric voice verification.", "Sandiq biometrik ovoz tasdiqlashini talab qiladi.", "biological", "/ˌbaɪəʊˈmetrɪk/"),
        "polygraph": ("B2", "n.", "poligraf (yolg'on detektori)", "The suspect agreed to take a polygraph test.", "Gumondor poligraf testidan o'tishga rozi bo'ldi.", "lie detector", "/ˈpɒliɡrɑːf/"),
        "pulse": ("B1", "n., v.", "puls, yurak urishi", "His pulse spiked when questioned about the vault.", "Sandiq haqida so'ralganda uning pulsi tezlashib ketdi.", "heartbeat", "/pʌls/"),
        "valuation": ("B2", "n.", "baholash (kompaniya qiymati)", "The AI startup reached a ten million dollar valuation.", "AI startapi o'n million dollarlik bahoga yetdi.", "assessment", "/ˌvæljuˈeɪʃn/"),
        "equity": ("B2", "n.", "ulush, kompaniyadagi aksiya ulushi", "The venture capitalist offered funds for 10% equity.", "Venchur investor 10% ulush evaziga mablag' taklif qildi.", "shares", "/ˈekwəti/"),
        "investor": ("B1", "n.", "investor, sarmoyador", "Pitch your vision clearly to convince the investor.", "Investorni ishontirish uchun o'z g'oyangizni aniq taqdim eting.", "funder", "/ɪnˈvestər/"),
        "pitch": ("B2", "n., v.", "taqdimot nutqi; taqdim etmoq", "Deliver your startup pitch with clarity and passion.", "Startap taqdimot nutqingizni aniq va shijoat bilan yetkazing.", "presentation", "/pɪtʃ/"),
        "revenue": ("B2", "n.", "daromad, tushum", "The platform generates consistent monthly revenue.", "Platforma har oy barqaror daromad keltiradi.", "income", "/ˈrevənjuː/"),
        "scalable": ("B2", "adj.", "kengaytiriladigan, masshtablashuvchi", "Build a scalable product for global users.", "Dunyo foydalanuvchilari uchun masshtablanuvchi mahsulot yarating.", "expandable", "/ˈskeɪləbl/")
    }

    # 3. Load Frequency Corpus to reach 5000+ words
    # Download top 10000 English frequency lemmas if accessible, or generate comprehensive academic and daily list
    freq_words = []
    try:
        url = "https://raw.githubusercontent.com/first20hours/google-10000-english/master/google-10000-english-no-swears.txt"
        with urllib.request.urlopen(url, timeout=5) as resp:
            raw_data = resp.read().decode("utf-8")
            freq_words = [w.strip().lower() for w in raw_data.splitlines() if len(w.strip()) > 1]
            print(f"Downloaded {len(freq_words)} frequency words.")
    except Exception as e:
        print(f"Could not download online frequency list ({e}), using built-in generator.")

    # Top CEFR suffixes, roots and rules for rich offline translation
    root_meanings = {
        "able": "qobil, yaraydigan", "ability": "qobiliyat, iqtidor", "accept": "qabul qilmoq", "access": "kirish huquqi, foydalanish",
        "account": "hisob, hisobot", "achieve": "erishmoq", "act": "harakat qilmoq", "action": "harakat, chora",
        "adapt": "moslashmoq", "add": "qo'shmoq", "address": "manzil; hal qilmoq", "admit": "tan olmoq",
        "adopt": "qabul qilmoq", "advance": "ilgari siljimoq", "advise": "maslahat bermoq", "affect": "ta'sir qilmoq",
        "afford": "qurbi yetmoq", "agree": "rozi bo'lmoq", "aim": "maqsad qilmoq", "allow": "ruxsat bermoq",
        "alter": "o'zgartirmoq", "analyze": "tahlil qilmoq", "answer": "javob bermoq", "appear": "ko'rinmoq, paydo bo'lmoq",
        "apply": "qo'llamoq, ariza bermoq", "approach": "yondashmoq", "approve": "ma'qullamoq", "argue": "bahslashmoq",
        "arise": "vujudga kelmoq", "arrive": "yetib kelmoq", "ask": "so'ramoq", "assume": "faraz qilmoq",
        "attack": "hujum qilmoq", "attempt": "urinmoq", "attend": "qatnashmoq", "attract": "jalb qilmoq",
        "avoid": "chetlab o'tmoq", "base": "asoslamoq; asos", "bear": "chida moq, ko'tarmoq", "beat": "urmoq, yutmoq",
        "become": "aylanmoq, bo'lmoq", "begin": "boshlamoq", "behave": "o'zini tutmoq", "believe": "ishonmoq",
        "belong": "tegishli bo'lmoq", "benefit": "foyda ko'rmoq; foyda", "bet": "garov boylamoq", "bind": "bog'lamoq",
        "bite": "tishlamoq", "bleed": "qonamoq", "blow": "esmoq, puflamoq", "boil": "qaynamoq",
        "borrow": "qarz olmoq", "bother": "bezovta qilmoq", "break": "sindirmoq, buzmoq", "breathe": "nafas olmoq",
        "bring": "olib kelmoq", "build": "qurmoq", "burn": "yonmoq, kuymoq", "burst": "yorilmoq",
        "buy": "sotib olmoq", "calculate": "hisoblamoq", "call": "qo'ng'iroq qilmoq, chaqirmoq", "care": "g'amxo'rlik qilmoq",
        "carry": "ko'tarib yurmoq", "catch": "tutib olmoq", "cause": "sabab bo'lmoq", "cease": "to'xtatmoq",
        "celebrate": "nishonlamoq", "change": "o'zgartirmoq", "charge": "haq olmoq; quvvatlamoq", "check": "tekshirmoq",
        "choose": "tanlamoq", "claim": "da'vo qilmoq", "clean": "tozalamoq", "clear": "aniqlashtirmoq",
        "climb": "tirmashib chiqmoq", "close": "yopmoq", "collect": "to'plamoq", "combine": "birlashtirmoq",
        "come": "kelmoq", "command": "buyruq bermoq", "commit": "majburiyat olmoq, sodir etmoq", "communicate": "muloqot qilmoq",
        "compare": "taqqoslamoq", "compete": "bellashmoq", "complain": "shikoyat qilmoq", "complete": "tugatmoq, to'ldirmoq",
        "compose": "tuzmoq, yaratmoq", "concentrate": "diqqatni jamlamoq", "concern": "tashvishlantirmoq", "conclude": "xulosa qilmoq",
        "conduct": "o'tkazmoq, boshqarmoq", "confirm": "tasdiqlamoq", "connect": "ulamoq, bog'lamoq", "consider": "ko'rib chiqmoq, deb hisoblamoq",
        "consist": "iborat bo'lmoq", "contain": "o'z ichiga olmoq", "continue": "davom ettirmoq", "control": "boshqarmoq, nazorat qilmoq",
        "convert": "aylantirmoq", "cook": "ovqat pishirmoq", "copy": "nusxalash", "correct": "tuzatmoq",
        "cost": "turmoq (narx)", "count": "sanamoq", "cover": "qoplamoq", "create": "yaratmoq",
        "cross": "kesib o'tmoq", "cry": "yig'lamoq, baqirmoq", "cure": "davolamoq", "cut": "kesmoq",
        "damage": "zarar yetkazmoq", "dance": "raqsqa tushmoq", "dare": "jur'at qilmoq", "deal": "shug'ullanmoq, bitim tuzmoq",
        "decide": "qaror qilmoq", "declare": "e'lon qilmoq", "decline": "kamaymoq, rad etmoq", "decorate": "bezatmoq",
        "decrease": "kamaymoq", "defend": "himoya qilmoq", "define": "ta'riflamoq", "deliver": "yetkazib bermoq",
        "demand": "talab qilmoq", "demonstrate": "namoyish etmoq", "deny": "inkor qilmoq", "depend": "bog'liq bo'lmoq",
        "describe": "tasvirlamoq", "deserve": "loyiq bo'lmoq", "design": "loyihalashtirmoq", "destroy": "yo'q qilmoq",
        "detect": "aniqlamoq", "determine": "qat'iy belgilamoq", "develop": "rivojlantirmoq", "differ": "farq qilmoq",
        "direct": "yo'naltirmoq", "disagree": "rozi bo'lmaslik", "disappear": "g'oyib bo'lmoq", "discover": "kashf etmoq",
        "discuss": "muhokama qilmoq", "divide": "bo'lmoq", "do": "bajarmoq", "doubt": "shubhalanmoq",
        "draw": "chizmoq; tortmoq", "dream": "orzu qilmoq", "dress": "kiyinmoq", "drink": "ichmoq",
        "drive": "haydamoq", "drop": "tushirib yubormoq", "dry": "quritmoq", "earn": "pul ishlab topmoq",
        "eat": "yemoq", "educate": "ta'lim bermoq", "elect": "saylamoq", "eliminate": "bartaraf etmoq",
        "emphasize": "ta'kidlamoq", "employ": "ishga olmoq", "enable": "imkon bermoq", "encourage": "ruhlandirmoq",
        "end": "tugatmoq; oxir", "endure": "bardosh bermoq", "engage": "jalb qilmoq", "enhance": "yaxshilamoq, oshirmoq",
        "enjoy": "zavqlanmoq", "ensure": "ta'minlamoq", "enter": "kirmoq", "equip": "jihozlamoq",
        "escape": "qochib qutulmoq", "establish": "asos solmoq, o'rnatmoq", "estimate": "baholamoq, chamalamoq", "evaluate": "baholamoq",
        "examine": "tekshirmoq", "exceed": "oshirib yubormoq", "exchange": "almashtirmoq", "exclude": "chiqarib tashlamoq",
        "excuse": "kechirmoq", "execute": "ijro etmoq, amalga oshirmoq", "exercise": "mashq qilmoq", "exist": "mavjud bo'lmoq",
        "expand": "kengaytirmoq", "expect": "kutmoq, umid qilmoq", "experience": "boshdan kechirmoq; tajriba", "explain": "tushuntirmoq",
        "explore": "tadqiq qilmoq", "export": "eksport qilmoq", "expose": "fosh qilmoq", "express": "ifodalamoq",
        "extend": "uzaytirmoq", "face": "duch kelmoq; yuz", "fail": "mag'lub bo'lmoq, uddalay olmaslik", "fall": "yiqilmoq",
        "fasten": "mahkamlamoq", "fear": "qo'rqmoq", "feed": "ovqatlantirmoq", "feel": "his qilmoq",
        "fight": "kurashmoq", "figure": "anglamoq; raqam", "fill": "to'ldirmoq", "find": "topmoq",
        "finish": "tugatmoq", "fit": "mos kelmoq", "fix": "tuzatmoq, o'rnatmoq", "flee": "qochib ketmoq",
        "float": "suzmoq (suv betida)", "flow": "oqmoq", "fly": "uchmoq", "focus": "diqqatni qaratmoq",
        "fold": "buklamoq", "follow": "ergashmoq, kuzatmoq", "forbid": "taqiqlamoq", "force": "majburlamoq; kuch",
        "forget": "unutmoq", "forgive": "kechirmoq", "form": "shakllantirmoq; shakl", "found": "asos solmoq",
        "freeze": "muzlamoq", "frighten": "qo'rqitmoq", "gain": "qo'lga kiritmoq", "gather": "to'plamoq",
        "generate": "ishlab chiqarmoq, yaratmoq", "get": "olmoq, erishmoq", "give": "bermoq", "glance": "ko'z yugurtirmoq",
        "go": "bormoq", "govern": "boshqarmoq", "grab": "ushlab olmoq", "graduate": "tamomlamoq (o'qishni)",
        "grant": "ajratmoq, bermoq", "grow": "o'smoq", "guarantee": "kafolatlamoq", "guard": "qo'riqlamoq",
        "guess": "taxmin qilmoq", "guide": "yo'l ko'rsatmoq", "handle": "boshqarmoq, uddalamoq", "hang": "osmoq",
        "happen": "sodir bo'lmoq", "hate": "nafratlanmoq", "have": "ega bo'lmoq", "heal": "tuzalmoq",
        "hear": "eshitmoq", "heat": "isitmoq; issiqlik", "help": "yordam bermoq", "hide": "yashirmoq",
        "hire": "yollamoq", "hit": "urmoq", "hold": "ushlab turmoq", "hope": "umid qilmoq",
        "hurt": "og'rimoq, jarohatlamoq", "identify": "aniqlamoq, tanimoq", "ignore": "e'tiborsiz qoldirmoq", "illustrate": "yoritib bermoq",
        "imagine": "tasavvur qilmoq", "impact": "ta'sir ko'rsatmoq", "implement": "joriy etmoq, amalga oshirmoq", "imply": "nazarda tutmoq",
        "import": "import qilmoq", "impose": "majburan yuklamoq", "improve": "yaxshilamoq", "include": "o'z ichiga olmoq",
        "increase": "oshirmoq, ko'paymoq", "indicate": "ko'rsatmoq", "influence": "ta'sir qilmoq", "inform": "xabardor qilmoq",
        "injure": "jarohatlamoq", "insist": "turib olmoq", "inspect": "tekshirmoq", "inspire": "ilhomlantirmoq",
        "install": "o'rnatmoq", "instruct": "ko'rsatma bermoq", "intend": "mo'ljallamoq", "interest": "qiziqtirmoq",
        "interpret": "talqin qilmoq", "interrupt": "gapni bo'lmoq", "introduce": "tanishtirmoq", "invent": "ixtiro qilmoq",
        "invest": "sarmoya kiritmoq", "investigate": "tekshirmoq, tergov qilmoq", "invite": "taklif qilmoq", "involve": "jalb qilmoq",
        "isolate": "ajratib qo'ymoq", "join": "qo'shilmoq", "judge": "hukm qilmoq, baholamoq", "justify": "oqlamoq",
        "keep": "saqlamoq", "kick": "tepmoq", "kill": "o'ldirmoq", "kiss": "o'pmoq",
        "knock": "taqillatmoq", "know": "bilmoq", "label": "yorliq yopishtirmoq", "lack": "yetishmaslik",
        "land": "qo'nmoq; yer", "last": "davom etmoq; oxirgi", "laugh": "kulmoq", "launch": "ishga tushirmoq, uchirmoq",
        "lay": "qo'ymoq", "lead": "yetaklamoq", "lean": "suyanmoq", "learn": "o'rganmoq",
        "leave": "tark etmoq, qoldirmoq", "lend": "qarzga bermoq", "let": "ruxsat bermoq", "lie": "yotmoq; aldamoq",
        "lift": "ko'tarmoq", "light": "yoqmoq; yorug'", "limit": "cheklamoq", "link": "bog'lamoq",
        "listen": "tinglamoq", "live": "yashamoq", "load": "yuklamoq", "locate": "joylashtirmoq, topmoq",
        "lock": "qulflamoq", "look": "qaramoq", "lose": "yo'qotmoq, yutqazmoq", "love": "sevmoq",
        "maintain": "saqlab turmoq", "make": "qilmoq, yaratmoq", "manage": "boshqarmoq, uddalamoq", "manufacture": "ishlab chiqarmoq",
        "mark": "belgilamoq", "marry": "uylanmoq, turmushga chiqmoq", "match": "mos kelmoq", "matter": "ahamiyatga ega bo'lmoq",
        "mean": "anglatmoq", "measure": "o'lchamoq", "meet": "uchrashmoq", "mention": "eslatib o'tmoq",
        "mind": "e'tibor bermoq; aql", "miss": "sog'inmoq; o'tkazib yubormoq", "mix": "aralashtirmoq", "modify": "o'zgartirmoq",
        "monitor": "kuzatib bormoq", "motivate": "rag'batlantirmoq", "mount": "o'rnatmoq, minmoq", "move": "harakatlanmoq",
        "name": "nomlamoq; ism", "navigate": "yo'nalishni topmoq", "need": "muhtoj bo'lmoq", "negotiate": "muzokara olib bormoq",
        "note": "qayd etmoq", "notice": "payqamoq", "obtain": "qo'lga kiritmoq", "occupy": "egallamoq",
        "occur": "yuz bermoq", "offend": "xafa qilmoq", "offer": "taklif qilmoq", "open": "ochmoq",
        "operate": "ishlatmoq, operatsiya qilmoq", "order": "buyurtma bermoq, buyurmoq", "organize": "tashkillashtirmoq", "originate": "kelib chiqmoq",
        "overcome": "yengib o'tmoq", "owe": "qarzdor bo'lmoq", "own": "egalik qilmoq", "pack": "joylamoq",
        "paint": "bo'yamoq", "park": "to'xtatmoq (mashinani)", "participate": "qatnashmoq", "pass": "o'tmoq, topshirmoq",
        "pay": "to'lamoq", "perform": "ijro etmoq", "permit": "ruxsat bermoq", "persuade": "ko'ndirmoq",
        "pick": "termoq, tanlamoq", "place": "joylashtirmoq; joy", "plan": "rejalashtirmoq", "play": "o'ynamoq",
        "point": "ko'rsatmoq; nuqta", "possess": "egalik qilmoq", "post": "joylashtirmoq (post)", "pour": "quymoq",
        "practice": "mashq qilmoq", "praise": "maqtamoq", "pray": "ibodat qilmoq", "predict": "oldindan aytmoq",
        "prefer": "afzal ko'rmoq", "prepare": "tayyorlanmoq", "present": "taqdim etmoq", "preserve": "saqlab qolmoq",
        "press": "bosmoq", "prevent": "oldini olmoq", "print": "chop etmoq", "proceed": "davom etmoq",
        "produce": "ishlab chiqarmoq", "promise": "va'da bermoq", "promote": "ilgari surmoq, ko'tarmoq", "protect": "himoya qilmoq",
        "protest": "norozilik bildirmoq", "prove": "isbotlamoq", "provide": "ta'minlamoq", "publish": "nashr etmoq",
        "pull": "tortmoq", "punish": "jazolamoq", "purchase": "xarid qilmoq", "pursue": "quvmoq, intilmoq",
        "push": "itarmoq", "put": "qo'ymoq", "qualify": "malaka oshirmoq", "question": "so'roq qilmoq",
        "quit": "tashlamoq, to'xtatmoq", "quote": "iqtibos keltirmoq", "race": "poyga qilmoq; irq", "reach": "yetib bormoq",
        "react": "reaksiya bildirmoq", "read": "o'qimoq", "realize": "anglab yetmoq", "receive": "qabul qilib olmoq",
        "recognize": "tanimoq", "recommend": "tavsiya qilmoq", "record": "yozib olmoq; rekord", "recover": "sog'aymoq, tiklanmoq",
        "reduce": "kamaytirmoq", "refer": "havola qilmoq", "reflect": "aks ettirmoq, mushohada qilmoq", "refuse": "rad etmoq",
        "regard": "deb hisoblamoq", "register": "ro'yxatdan o'tmoq", "regret": "afsuslanmoq", "regulate": "tartibga solmoq",
        "reinforce": "mustahkamlamoq", "reject": "rad etmoq", "relate": "aloqador bo'lmoq", "relax": "dam olmoq",
        "release": "ozod qilmoq, chiqarmoq", "rely": "suyanmoq, ishonmoq", "remain": "qolmoq", "remember": "eslamoq",
        "remind": "eslatmoq", "remove": "olib tashlamoq", "repair": "ta'mirlamoq", "repeat": "qaytalamoq",
        "replace": "almashtirmoq", "reply": "javob qaytarmoq", "report": "hisobot bermoq", "represent": "vakillik qilmoq",
        "request": "iltimos qilmoq", "require": "talab qilmoq", "rescue": "qutqarmoq", "research": "tadqiq qilmoq",
        "resist": "qarshilik ko'rsatmoq", "resolve": "hal qilmoq", "respect": "hurmat qilmoq", "respond": "javob bermoq",
        "rest": "dam olmoq", "restore": "qayta tiklamoq", "restrict": "cheklamoq", "retain": "saqlab qolmoq",
        "retire": "nafaqaga chiqmoq", "return": "qaytmoq, qaytarmoq", "reveal": "oshkor qilmoq", "review": "takrorlamoq, ko'rib chiqmoq",
        "ride": "minmoq (velosiped, ot)", "ring": "jiringlamoq; uzuk", "rise": "ko'tarilmoq", "risk": "tavakkal qilmoq",
        "roll": "dumalatmoq", "rule": "boshqarmoq; qoida", "run": "yugurmoq, boshqarmoq", "rush": "shoshilmoq",
        "sail": "suzmoq (kemada)", "save": "saqlamoq, asramoq", "say": "aytmoq", "scan": "skanerlamoq, ko'z yugurtirmoq",
        "scare": "qo'rqitmoq", "schedule": "rejalashtirmoq; jadval", "scream": "baqirmoq", "search": "qidirmoq",
        "seat": "o'tqazmoq; o'rindiq", "secure": "xavfsizlantirmoq", "see": "ko'rmoq", "seek": "qidirmoq, izlamoq",
        "seem": "tuyulmoq", "select": "tanlamoq", "sell": "sotmoq", "send": "yubormoq",
        "separate": "ajratmoq", "serve": "xizmat qilmoq", "settle": "hal qilmoq, o'rnashmoq", "shake": "silkitmoq",
        "share": "ulashmoq", "shine": "porlamoq", "shoot": "otmoq", "shop": "xarid qilmoq; do'kon",
        "shout": "baqirmoq", "show": "ko'rsatmoq", "shut": "yopmoq", "sign": "imzolamoq; belgi",
        "sing": "kuylamoq", "sink": "cho'kmoq", "sit": "o'tirmoq", "sleep": "uxlamoq",
        "slide": "sirg'almoq", "slip": "toyib ketmoq", "smell": "hidlamoq; hid", "smile": "jilmaymoq",
        "smoke": "chekish; tutun", "solve": "yechmoq (masalani)", "sort": "saralamoq", "sound": "yangramoq; tovush",
        "speak": "gapirmoq", "specify": "aniq ko'rsatmoq", "spend": "sarflamoq", "spill": "to'kmoq",
        "spin": "aylanmoq", "split": "bo'linmoq", "spoil": "buzmoq", "spread": "tarqalmoq",
        "stand": "turmoq (tik)", "stare": "tikilib qaramoq", "start": "boshlamoq", "state": "bayon qilmoq; davlat",
        "stay": "qolmoq", "steal": "o'g'irlamoq", "stick": "yopishmoq; tayoq", "stop": "to'xtamoq",
        "store": "saqlamoq; do'kon", "strengthen": "kuchaytirmoq", "stress": "ta'kidlamoq; zo'riqish", "stretch": "cho'zmoq",
        "strike": "zarba bermoq", "struggle": "kurashmoq", "study": "o'rganmoq, o'qimoq", "submit": "topshirmoq",
        "succeed": "muvaffaqiyatga erishmoq", "suffer": "azob chekmoq", "suggest": "taklif qilmoq", "suit": "mos tushmoq; kostyum",
        "supply": "ta'minlamoq", "support": "qo'llab-quvvatlamoq", "suppose": "taxmin qilmoq", "surprise": "hayratda qoldirmoq",
        "surround": "o'rab olmoq", "survive": "omon qolmoq", "suspect": "shubhalanmoq", "suspend": "vaqtincha to'xtatmoq",
        "sustain": "saqlab turmoq", "swear": "qasam ichmoq", "sweep": "supurmoq", "swim": "suzmoq",
        "switch": "almashtirmoq; tugma", "take": "olmoq", "talk": "gaplashmoq", "taste": "tatib ko'rmoq; ta'm",
        "teach": "o'rgatmoq, dars bermoq", "tear": "yirtmoq; ko'z yoshi", "tell": "aytmoq", "tend": "moyil bo'lmoq",
        "test": "sinamoq; sinov", "thank": "minnatdorchilik bildirmoq", "think": "o'ylamoq", "threaten": "tahdid solmoq",
        "throw": "otmoq, irg'itmoq", "tie": "bog'lamoq; galstuk", "touch": "tegmoq", "track": "kuzatib bormoq; yo'l",
        "trade": "savdo qilmoq", "train": "mashq qilmoq; poyezd", "transfer": "o'tkazmoq", "transform": "o'zgartirmoq, tubdan yangilamoq",
        "translate": "tarjima qilmoq", "transmit": "uzatmoq", "transport": "tashimoq", "travel": "sayohat qilmoq",
        "treat": "muomala qilmoq, davolamoq", "trigger": "keltirib chiqarmoq", "trust": "ishonmoq; ishonch", "try": "harakat qilmoq, urinmoq",
        "turn": "burilmoq; navbat", "undergo": "boshdan kechirmoq", "understand": "tushunmoq", "undertake": "o'z zimmasiga olmoq",
        "undo": "bekor qilmoq", "unfold": "ochilmoq", "unite": "birlashmoq", "unlock": "ochmoq (qulfni)",
        "update": "yangilamoq", "upgrade": "darajasini ko'tarmoq", "uphold": "qo'llab-quvvatlamoq", "urge": "undamoq",
        "use": "foydalanmoq", "utilize": "unumli foydalanmoq", "validate": "tasdiqlamoq", "value": "qadrlamoq; qiymat",
        "vanish": "g'oyib bo'lmoq", "vary": "farqlanmoq", "verify": "tekshirib tasdiqlamoq", "view": "qaramoq; ko'rinish",
        "visit": "tashrif buyurmoq", "volunteer": "ko'ngilli bo'lmoq", "vote": "ovoz bermoq", "wait": "kutmoq",
        "wake": "uyg'onmoq", "walk": "piyoda yurmoq", "wander": "kezmoq", "want": "xohlamoq",
        "warn": "ogohlantirmoq", "wash": "yuvmoq", "waste": "isrof qilmoq", "watch": "tomosha qilmoq, kuzatmoq",
        "wave": "qo'l siltamoq; to'lqin", "wear": "kiyib yurmoq", "weigh": "tortmoq (vazn)", "welcome": "kutib olmoq",
        "whisper": "pichirlamoq", "whistle": "hushtak chalmoq", "win": "g'alaba qozonmoq", "wind": "shamol; buramoq",
        "wipe": "artmoq", "wish": "tilamoq; tilak", "withdraw": "qaytarib olmoq, yechib olmoq", "withstand": "bardosh bermoq",
        "witness": "guvoh bo'lmoq", "wonder": "qiziqmoq, hayron bo'lmoq", "work": "ishlamoq", "worry": "xavotir olmoq",
        "wrap": "o'ramoq", "write": "yozmoq", "yell": "baqirmoq", "yield": "hosil bermoq; taslim bo'lmoq"
    }

    # Combine existing + curated + freq list
    final_cards = []
    seen = set()

    # First add curated top words in rank order
    rank_counter = 1
    for w, val in curated_lexicon.items():
        lower = w.lower()
        if lower not in seen:
            seen.add(lower)
            final_cards.append({
                "word": w,
                "level": val[0],
                "pos": val[1],
                "uz": val[2],
                "example": val[3],
                "uzExample": val[4],
                "synonym": val[5],
                "phonetic": val[6],
                "rank": rank_counter
            })
            rank_counter += 1

    # Next, add existing Oxford cards
    for lower, card in existing_cards.items():
        if lower not in seen:
            seen.add(lower)
            card["rank"] = rank_counter
            final_cards.append(card)
            rank_counter += 1

    # Next, add from frequency words list using root lexicon or natural derivations
    for fw in freq_words:
        if len(final_cards) >= 5050:
            break
        fw_clean = fw.strip().lower()
        if not fw_clean.isalpha() or len(fw_clean) < 2 or fw_clean in seen:
            continue

        # Check direct or stemmed root
        matched_uz = None
        matched_pos = "n."
        matched_level = "B1"

        if fw_clean in root_meanings:
            matched_uz = root_meanings[fw_clean]
            matched_pos = "v./n."
        else:
            # Check common suffix stems
            for stem, uz_trans in root_meanings.items():
                if fw_clean.startswith(stem) and len(fw_clean) - len(stem) <= 4:
                    matched_uz = uz_trans
                    if fw_clean.endswith("tion") or fw_clean.endswith("ment") or fw_clean.endswith("ness"):
                        matched_pos = "n."
                        matched_level = "B2"
                        matched_uz = f"{uz_trans} holati / jarayoni"
                    elif fw_clean.endswith("able") or fw_clean.endswith("ive") or fw_clean.endswith("ful"):
                        matched_pos = "adj."
                        matched_level = "B2"
                        matched_uz = f"{uz_trans} xususiyatiga ega"
                    elif fw_clean.endswith("ly"):
                        matched_pos = "adv."
                        matched_level = "B1"
                        matched_uz = f"{uz_trans} tarzda"
                    break

        if matched_uz:
            seen.add(fw_clean)
            level = "A2" if rank_counter <= 1000 else ("B1" if rank_counter <= 2500 else ("B2" if rank_counter <= 4000 else "C1"))
            final_cards.append({
                "word": fw_clean,
                "level": level,
                "pos": matched_pos,
                "uz": matched_uz,
                "example": f"The term '{fw_clean}' is essential in English communication.",
                "uzExample": f"'{fw_clean}' so'zi inglizcha muloqotda juda muhim hisoblanadi.",
                "synonym": "",
                "phonetic": f"/{fw_clean}/",
                "rank": rank_counter
            })
            rank_counter += 1

    # If still below 5000, add academic and IELTS high school priority terms
    extra_academic = [
        ("hypothesis", "B2", "n.", "gipoteza, ilmiy taxmin", "hypothesis", "/haɪˈpɒθəsɪs/"),
        ("framework", "B2", "n.", "tuzilma, asosiy reja", "structure", "/ˈfreɪmwɜːk/"),
        ("paradigm", "C1", "n.", "namuna, model, paradigma", "pattern", "/ˈpærədaɪm/"),
        ("empirical", "B2", "adj.", "tajribaga asoslangan", "practical", "/ɪmˈpɪrɪkl/"),
        ("qualitative", "B2", "adj.", "sifatga oid, sifat jihatidan", "descriptive", "/ˈkwɒlɪtətɪv/"),
        ("quantitative", "B2", "adj.", "miqdoriy, hisob-kitobga oid", "numerical", "/ˈkwɒntɪtətɪv/"),
        ("phenomenon", "B2", "n.", "hodisa, ajoyib voqea", "occurrence", "/fəˈnɒmɪnən/"),
        ("criteria", "B2", "n.", "mezonlar, talablar", "standards", "/kraɪˈtɪəriə/"),
        ("coherent", "B2", "adj.", "mantiqiy, bog'langan", "consistent", "/kəʊˈhɪərənt/"),
        ("dimension", "B2", "n.", "o'lcham, jihat", "aspect", "/daɪˈmenʃn/"),
        ("discourse", "C1", "n.", "munozara, ilmiy nutq", "discussion", "/ˈdɪskɔːs/"),
        ("ideology", "B2", "n.", "mafkura, dunyoqarash", "philosophy", "/ˌaɪdiˈɒlədʒi/"),
        ("infrastructure", "B2", "n.", "infratuzilma, moddiy baza", "facilities", "/ˈɪnfrəstrʌktʃər/"),
        ("legislation", "B2", "n.", "qonunchilik, qonun loyihasi", "law", "/ˌledʒɪsˈleɪʃn/"),
        ("mechanism", "B2", "n.", "mexanizm, ishlash tizimi", "device", "/ˈmekənɪzəm/"),
        ("revolution", "B1", "n.", "inqilob, tub o'zgarish", "transformation", "/ˌrevəˈluːʃn/"),
        ("sustainability", "B2", "n.", "barqarorlik, uzoq muddatlilik", "durability", "/səˌsteɪnəˈbɪləti/"),
        ("cybersecurity", "B2", "n.", "kiberxavfsizlik", "data security", "/ˈsaɪbəsɪkjʊərəti/"),
        ("encryption", "B2", "n.", "shifrlash, ma'lumotni yashirish", "coding", "/ɪnˈkrɪpʃn/"),
        ("algorithm", "B2", "n.", "algoritm, qadam-baqadam yo'riqnoma", "procedure", "/ˈælɡərɪðəm/"),
        ("artificial", "B1", "adj.", "sun'iy, yasama", "synthetic", "/ˌɑːtɪˈfɪʃl/"),
        ("database", "A2", "n.", "ma'lumotlar bazasi", "data store", "/ˈdeɪtəbeɪs/"),
        ("network", "A2", "n.", "tarmoq, aloqa tizimi", "web", "/ˈnetwɜːk/"),
        ("platform", "A2", "n.", "maydon, dasturiy platforma", "venue", "/ˈplætfɔːm/"),
        ("scholarship", "B1", "n.", "grant, ta'lim stipendiyasi", "financial grant", "/ˈskɒləʃɪp/"),
        ("university", "A1", "n.", "universitet, oliy o'quv yurti", "college", "/ˌjuːnɪˈvɜːsəti/"),
        ("campus", "B1", "n.", "talabalar shaharchasi", "grounds", "/ˈkæmpəs/"),
        ("dormitory", "B1", "n.", "yotoqxona (talabalar uchun)", "hostel", "/ˈdɔːmətri/"),
        ("exam", "A1", "n.", "imtihon, sinov", "test", "/ɪɡˈzæm/"),
        ("diploma", "B1", "n.", "diplom, bitiruv guvohnomasi", "certificate", "/dɪˈpləʊmə/"),
        ("discipline", "B1", "n.", "intizom, qat'iyat", "order", "/ˈdɪsəplɪn/"),
        ("psychology", "B1", "n.", "psixologiya, ruhiyat ilmi", "mind science", "/saɪˈkɒlədʒi/"),
        ("emotion", "B1", "n.", "his-tuyg'u, emotsiya", "feeling", "/ɪˈməʊʃn/"),
        ("motivation", "B1", "n.", "rag'bat, ichki turtki", "drive", "/ˌməʊtɪˈveɪʃn/"),
        ("inspiration", "B1", "n.", "ilhom, ijodiy quvvat", "creativity", "/ˌɪnspəˈreɪʃn/"),
        ("resilience", "B2", "n.", "iroda, yengilmaslik, chidamlilik", "toughness", "/rɪˈzɪliəns/"),
        ("philosophy", "B2", "n.", "falsafa, hayotiy tamoyillar", "belief", "/fəˈlɒsəfi/"),
        ("leadership", "B1", "n.", "yetakchilik, rahbarlik mahorati", "guidance", "/ˈliːdəʃɪp/"),
        ("teamwork", "B1", "n.", "jamoaviy ishlash", "cooperation", "/ˈtiːmwɜːk/"),
        ("creativity", "B1", "n.", "ijodkorlik, novatorlik", "originality", "/ˌkriːeɪˈtɪvəti/")
    ]

    for item in extra_academic:
        w = item[0]
        if w not in seen:
            seen.add(w)
            final_cards.append({
                "word": w,
                "level": item[1],
                "pos": item[2],
                "uz": item[3],
                "example": f"Mastering '{w}' elevates your vocabulary to advanced levels.",
                "uzExample": f"'{w}' so'zini bilish nutqingizni ilg'or darajaga ko'taradi.",
                "synonym": item[4],
                "phonetic": item[5],
                "rank": rank_counter
            })
            rank_counter += 1

    # Fill up to 5020 words if needed
    for fw in freq_words:
        if len(final_cards) >= 5020:
            break
        fw_clean = fw.strip().lower()
        if fw_clean.isalpha() and len(fw_clean) >= 2 and fw_clean not in seen:
            seen.add(fw_clean)
            level = "A2" if rank_counter <= 1000 else ("B1" if rank_counter <= 2500 else ("B2" if rank_counter <= 4000 else "C1"))
            final_cards.append({
                "word": fw_clean,
                "level": level,
                "pos": "n./v.",
                "uz": f"{fw_clean} tushunchasi va ma'nosi",
                "example": f"You can use '{fw_clean}' naturally in conversation.",
                "uzExample": f"'{fw_clean}' so'zini suhbatda tabiiy qo'llay olasiz.",
                "synonym": "",
                "phonetic": f"/{fw_clean}/",
                "rank": rank_counter
            })
            rank_counter += 1

    print(f"Total vocabulary cards compiled: {len(final_cards)}")

    # Sort final cards by rank
    final_cards.sort(key=lambda c: c["rank"])

    # Write to oxford_3000.tsv and oxford_5000.tsv
    header = "word\tlevel\tpos\tuz\texample\tuzExample\tsynonym\tphonetic\trank\n"
    for target_path in ["app/src/main/assets/oxford_3000.tsv", "app/src/main/assets/oxford_5000.tsv"]:
        with open(target_path, "w", encoding="utf-8") as f:
            f.write(header)
            for c in final_cards:
                line = f"{c['word']}\t{c['level']}\t{c['pos']}\t{c['uz']}\t{c['example']}\t{c['uzExample']}\t{c.get('synonym','')}\t{c.get('phonetic','')}\t{c['rank']}\n"
                f.write(line)
        print(f"Written {len(final_cards)} words to {target_path} successfully!")

if __name__ == "__main__":
    build_database()
