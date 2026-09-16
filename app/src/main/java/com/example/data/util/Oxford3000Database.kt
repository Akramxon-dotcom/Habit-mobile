package com.example.data.util

import android.content.Context
import com.example.data.model.VocabCard
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale
import java.util.UUID

/**
 * Oxford 3000™ Master Vocabulary Database with rich CEFR levels (A1–C1),
 * verified Uzbek translations, contextual English example sentences,
 * and Uzbek translations of the examples.
 */
object Oxford3000Database {

    data class OxfordDefinition(
        val word: String,
        val level: String,
        val pos: String,
        val uz: String,
        val example: String,
        val uzExample: String,
        val synonym: String = "",
        val phonetic: String = ""
    )

    // Curated rich vocabulary entries with complete sentences & translations
    private val masterEntries = listOf(
        // === A1 LEVEL ===
        OxfordDefinition("about", "A1", "prep., adv.", "haqida, to'g'risida; taxminan", "Tell me about your daily schedule.", "Menga kunlik jadvalingiz haqida aytib bering.", "regarding", "/əˈbaʊt/"),
        OxfordDefinition("above", "A1", "prep., adv.", "yuqorisida, tepasida", "The clock hangs above the study desk.", "Soat o'qish stolining tepasida osilib turibdi.", "over", "/əˈbʌv/"),
        OxfordDefinition("action", "A1", "n.", "harakat, chora", "Actions speak louder than words.", "Harakatlar so'zlardan ko'ra jarangliroqdir.", "act", "/ˈækʃn/"),
        OxfordDefinition("activity", "A1", "n.", "faoliyat, mashg'ulot", "Regular study is a productive activity.", "Muntazam o'qish — unumli mashg'ulotdir.", "exercise", "/ækˈtɪvəti/"),
        OxfordDefinition("add", "A1", "v.", "qo'shmoq, kiritmoq", "Add new vocabulary to your study list.", "O'rganish ro'yxatingizga yangi so'zlarni qo'shing.", "include", "/æd/"),
        OxfordDefinition("advice", "A1", "n.", "maslahat, o'git", "He gave me helpful advice on discipline.", "U menga intizom haqida foydali maslahat berdi.", "guidance", "/ədˈvaɪs/"),
        OxfordDefinition("agree", "A1", "v.", "rozi bo'lmoq, qo'shilmoq", "I agree with your study schedule.", "Men sizning o'qish jadvalingizga qo'shilaman.", "consent", "/əˈɡriː/"),
        OxfordDefinition("always", "A1", "adv.", "har doim, hamisha", "Always review your words every morning.", "Har kuni ertalab so'zlaringizni takrorlang.", "constantly", "/ˈɔːlweɪz/"),
        OxfordDefinition("amazing", "A1", "adj.", "ajoyib, hayratlanarli", "You made amazing progress today.", "Siz bugun ajoyib natijaga erishdingiz.", "wonderful", "/əˈmeɪzɪŋ/"),
        OxfordDefinition("answer", "A1", "n., v.", "javob bermoq; javob", "Answer every question with focus.", "Har bir savolga diqqat bilan javob bering.", "reply", "/ˈænsər/"),
        OxfordDefinition("arrive", "A1", "v.", "yetib kelmoq", "The morning train arrives on time.", "Ertalabki poyezd o'z vaqtida yetib keladi.", "reach", "/əˈraɪv/"),
        OxfordDefinition("ask", "A1", "v.", "so'ramoq, iltimos qilmoq", "Ask whenever you need clarification.", "Tushunarsiz bo'lganda doimo so'rang.", "inquire", "/æsk/"),
        OxfordDefinition("awesome", "A1", "adj.", "juda zo'r, ajoyib", "Finishing all tasks feels awesome.", "Barcha vazifalarni tugatish juda ajoyib his beradi.", "great", "/ˈɔːsəm/"),
        OxfordDefinition("beautiful", "A1", "adj.", "chiroyli, go'zal", "It is a beautiful morning to study.", "Bugun o'qish uchun ajoyib va go'zal tong.", "pretty", "/ˈbjuːtɪfl/"),
        OxfordDefinition("become", "A1", "v.", "bo'lmoq, aylanmoq", "Small daily habits become big results.", "Kichik kunlik odatlar ulkan natijalarga aylanadi.", "turn into", "/bɪˈkʌm/"),
        OxfordDefinition("begin", "A1", "v.", "boshlamoq", "Begin your most important task first.", "Avval eng muhim vazifangizni boshlang.", "start", "/bɪˈɡɪn/"),
        OxfordDefinition("believe", "A1", "v.", "ishonmoq", "Believe in your capacity to learn English.", "Ingliz tilini o'rganish qobiliyatingizga ishoning.", "trust", "/bɪˈliːv/"),
        OxfordDefinition("best", "A1", "adj., adv.", "eng yaxshi, a'lo", "Give your best effort every day.", "Har kuni eng yaxshi harakatingizni qiling.", "optimal", "/best/"),
        OxfordDefinition("better", "A1", "adj., adv.", "yaxshiroq", "Consistency makes you better day by day.", "Doimiylik sizni kundan-kunga yaxshiroq qiladi.", "improved", "/ˈbetər/"),
        OxfordDefinition("build", "A1", "v.", "qurmoq, barpo etmoq", "Build strong habits through daily action.", "Kunlik harakat orqali mustahkam odatlar quring.", "construct", "/bɪld/"),
        OxfordDefinition("busy", "A1", "adj.", "band, mashg'ul", "Stay focused even when your day is busy.", "Kuningiz band bo'lganda ham diqqatni saqlang.", "occupied", "/ˈbɪzi/"),
        OxfordDefinition("clean", "A1", "adj., v.", "toza; tozalamoq", "A clean workspace sharpens your mind.", "Toza ish stoli fikrni tiniqlashtiradi.", "tidy", "/kliːn/"),
        OxfordDefinition("clear", "A1", "adj., v.", "aniq, ravshan; tushunarli", "Set clear goals before beginning work.", "Ishni boshlashdan oldin aniq maqsadlar qo'ying.", "evident", "/klɪr/"),
        OxfordDefinition("complete", "A1", "adj., v.", "to'liq; tugatmoq", "Complete your daily quota before sunset.", "Kunlik me'yoringizni quyosh botguncha tugating.", "finish", "/kəmˈpliːt/"),
        OxfordDefinition("create", "A1", "v.", "yaratmoq, vujudga keltirmoq", "Create a quiet atmosphere for deep study.", "Chuqur o'qish uchun osoyishta muhit yarating.", "produce", "/kriˈeɪt/"),
        OxfordDefinition("daily", "A1", "adj., adv.", "kunlik, har kungi", "Daily repetition cements new vocabulary.", "Kunlik takrorlash yangi so'zlarni mustahkamlaydi.", "everyday", "/ˈdeɪli/"),
        OxfordDefinition("decide", "A1", "v.", "qaror qilmoq", "Decide firmly to master these words.", "Ushbu so'zlarni o'zlashtirishga qat'iy qaror qiling.", "determine", "/dɪˈsaɪd/"),
        OxfordDefinition("describe", "A1", "v.", "ta'riflamoq, tasvirlamoq", "Describe your routine in simple sentences.", "Tartibingizni sodda gaplar bilan tasvirlang.", "depict", "/dɪˈskraɪb/"),
        OxfordDefinition("difficult", "A1", "adj.", "qiyin, murakkab", "Difficult tasks train your endurance.", "Qiyin vazifalar chidamliligingizni tarbiyalaydi.", "hard", "/ˈdɪfɪkəlt/"),
        OxfordDefinition("early", "A1", "adj., adv.", "erta, barvaqt", "Wake up early to seize the golden morning.", "Oltin tongni qo'ldan boy bermaslik uchun erta turing.", "prompt", "/ˈɜːrli/"),
        OxfordDefinition("easy", "A1", "adj.", "oson, yengil", "Tasks become easy once they become habits.", "Odatga aylangach har qanday vazifa osonlashadi.", "simple", "/ˈiːzi/"),
        OxfordDefinition("enjoy", "A1", "v.", "rohatlanmoq, zavqlanmoq", "Enjoy the process of learning new words.", "Yangi so'zlarni o'rganish jarayonidan zavqlaning.", "appreciate", "/ɪnˈdʒɔɪ/"),
        OxfordDefinition("example", "A1", "n.", "misol, namuna", "Learn every word with a practical example.", "Har bir so'zni amaliy misol bilan o'rganing.", "sample", "/ɪɡˈzæmpl/"),
        OxfordDefinition("exercise", "A1", "n., v.", "mashq, jismoniy mashg'ulot", "Morning exercise boosts brain power.", "Tonggi badantarbiya miya faoliyatini oshiradi.", "workout", "/ˈeksərsaɪz/"),

        // === A2 LEVEL ===
        OxfordDefinition("ability", "A2", "n.", "qobiliyat, iste'dod", "She has an outstanding ability in languages.", "Unda tillarni o'rganishda ajoyib qobiliyat bor.", "talent", "/əˈbɪləti/"),
        OxfordDefinition("able", "A2", "adj.", "qodir, qo'lidan keladigan", "You are fully able to master English.", "Siz ingliz tilini mukammal bilishga to'liq qodirsiz.", "capable", "/ˈeɪbl/"),
        OxfordDefinition("accept", "A2", "v.", "qabul qilmoq, rozi bo'lmoq", "Accept constructive feedback with gratitude.", "Foydali tanqidni minnatdorlik bilan qabul qiling.", "welcome", "/əkˈsept/"),
        OxfordDefinition("accident", "A2", "n.", "baxtsiz hodisa, tasodif", "Careful planning prevents unexpected accidents.", "Pishiq rejalashtirish kutilmagan ko'ngilsizliklarning oldini oladi.", "mishap", "/ˈæksɪdənt/"),
        OxfordDefinition("according to", "A2", "prep.", "ko'ra, binoan", "According to research, sleep aids memory.", "Tadqiqotlarga ko'ra, uyqu xotiraga yordam beradi.", "as stated by", "/əˈkɔːrdɪŋ tuː/"),
        OxfordDefinition("achieve", "A2", "v.", "erishmoq, yetishmoq", "Discipline helps you achieve great heights.", "Intizom yuksak marralarga erishishga ko'maklashadi.", "attain", "/əˈtʃiːv/"),
        OxfordDefinition("active", "A2", "adj.", "faol, harakatchan", "Keep an active study streak every day.", "Har kuni faol o'qish zanjirini saqlang.", "energetic", "/ˈæktɪv/"),
        OxfordDefinition("advantage", "A2", "n.", "ustunlik, afzallik", "Bilingualism is a substantial career advantage.", "Ikki tilni bilish — katta kasbiy afzallikdir.", "benefit", "/ədˈvæntɪdʒ/"),
        OxfordDefinition("adventure", "A2", "n.", "sarguzasht", "Language learning is an exciting adventure.", "Til o'rganish — maroqli sarguzashtdir.", "quest", "/ədˈventʃər/"),
        OxfordDefinition("affect", "A2", "v.", "ta'sir qilmoq", "Distractions negatively affect concentration.", "Chalg'itishlar diqqatni jamlashga salbiy ta'sir qiladi.", "influence", "/əˈfekt/"),
        OxfordDefinition("allow", "A2", "v.", "ruxsat bermoq, imkon bermoq", "Allow yourself enough time to reflect.", "Fikrlab olish uchun o'zingizga yetarlicha vaqt bering.", "permit", "/əˈlaʊ/"),
        OxfordDefinition("amount", "A2", "n., v.", "miqdor, hajm", "Increase the amount of reading every week.", "Har hafta mutolaa hajmini oshirib boring.", "quantity", "/əˈmaʊnt/"),
        OxfordDefinition("ancient", "A2", "adj.", "qadimiy, ko'hna", "Samarkand is an ancient and storied city.", "Samarqand — qadimiy va ko'p asrlik shahar.", "antique", "/ˈeɪnʃənt/"),
        OxfordDefinition("appear", "A2", "v.", "paydo bo'lmoq, ko'rinmoq", "The reminder will appear immediately on screen.", "Eslatma shu zahoti ekranda paydo bo'ladi.", "emerge", "/əˈpɪr/"),
        OxfordDefinition("apply", "A2", "v.", "qo'llamoq; ariza bermoq", "Apply newly learned words in daily speech.", "Yangi o'rgangan so'zlaringizni kundalik nutqda qo'llang.", "utilize", "/əˈplaɪ/"),
        OxfordDefinition("arrange", "A2", "v.", "tartibga solmoq, joylashtirmoq", "Arrange your tasks in priority order.", "Vazifalaringizni ustuvorlik tartibida joylashtiring.", "organize", "/əˈreɪndʒ/"),
        OxfordDefinition("attend", "A2", "v.", "qatnashmoq, qatnamoq", "Attend every lesson with high attention.", "Har bir darsda yuqori diqqat bilan qatnashing.", "participate in", "/əˈtend/"),
        OxfordDefinition("available", "A2", "adj.", "mavjud, bor, bo'sh", "All Oxford vocabulary cards are available.", "Barcha Oksford lug'at kartochkalari mavjud.", "accessible", "/əˈveɪləbl/"),
        OxfordDefinition("avoid", "A2", "v.", "saqlanmoq, chetlab o'tmoq", "Avoid meaningless digital distractions.", "Foydasiz virtual chalg'itishlardan saqlaning.", "evade", "/əˈvɔɪd/"),
        OxfordDefinition("benefit", "A2", "n., v.", "foyda, naf keltirmoq", "Spaced repetition brings huge memory benefits.", "Intervalli takrorlash xotiraga ulkan foyda keltiradi.", "profit", "/ˈbenɪfɪt/"),
        OxfordDefinition("brain", "A2", "n.", "miya, aql", "Intellectual challenges exercise your brain.", "Aqliy mashqlar miyani baquvvat qiladi.", "mind", "/breɪn/"),
        OxfordDefinition("bridge", "A2", "n.", "ko'prik", "Books are a sturdy bridge to wisdom.", "Kitoblar — donolik sari mustahkam ko'prikdir.", "connector", "/brɪdʒ/"),
        OxfordDefinition("cancel", "A2", "v.", "bekor qilmoq", "Cancel unimportant meetings when busy.", "Band bo'lganda muhim bo'lmagan uchrashuvlarni bekor qiling.", "call off", "/ˈkænsl/"),
        OxfordDefinition("career", "A2", "n.", "kasbiy yo'l, martaba", "Language skills will accelerate your career.", "Til bilish ko'nikmalari martabangizni tezlashtiradi.", "profession", "/kəˈrɪr/"),
        OxfordDefinition("chance", "A2", "n.", "imkoniyat, fursat", "Seize every chance to practice speaking.", "Gapirishni mashq qilish uchun har bir imkoniyatdan foydalaning.", "opportunity", "/tʃæns/"),
        OxfordDefinition("choice", "A2", "n.", "tanlov, ixtiyor", "Success is a series of wise choices.", "Muvaffaqiyat — oqilona tanlovlar zanjiridir.", "selection", "/tʃɔɪs/"),
        OxfordDefinition("connect", "A2", "v.", "bog'lamoq, ulamoq", "Connect new definitions to familiar ideas.", "Yangi ta'riflarni tanish g'oyalarga bog'lang.", "link", "/kəˈnekt/"),
        OxfordDefinition("continue", "A2", "v.", "davom ettirmoq", "Continue studying even when it feels hard.", "Qiyin tuyulganda ham o'qishni davom ettiring.", "proceed", "/kənˈtɪnjuː/"),
        OxfordDefinition("control", "A2", "n., v.", "nazorat qilmoq; boshqaruv", "Take control of your daily schedule.", "Kunlik jadvalingizni o'z nazoratingizga oling.", "manage", "/kənˈtroʊl/"),
        OxfordDefinition("danger", "A2", "n.", "xavf, xatar", "Procrastination is a danger to your goals.", "Vaqtni cho'zish — maqsadlaringiz uchun xavfdir.", "hazard", "/ˈdeɪndʒər/"),
        OxfordDefinition("decision", "A2", "n.", "qaror, xulosa", "A firm decision eliminates doubt.", "Qat'iy qaror shubhalarni yo'q qiladi.", "resolution", "/dɪˈsɪʒn/"),
        OxfordDefinition("detail", "A2", "n., v.", "tafsilot; batafsil ko'rmoq", "Pay close attention to pronunciation details.", "Talaffuz tafsilotlariga diqqat qiling.", "particular", "/ˈdiːteɪl/"),
        OxfordDefinition("develop", "A2", "v.", "rivojlantirmoq, taraqqiy etmoq", "Develop your fluency through persistent effort.", "Uzluksiz harakat orqali til ravonligingizni rivojlantiring.", "grow", "/dɪˈveləp/"),
        OxfordDefinition("difference", "A2", "n.", "farq, tafovut", "Consistency makes a remarkable difference.", "Doimiylik sezilarli farqni vujudga keltiradi.", "contrast", "/ˈdɪfrəns/"),
        OxfordDefinition("direction", "A2", "n.", "yo'nalish, tomon", "Ensure your efforts move in the right direction.", "Harakatlaringiz to'g'ri yo'nalishda ekaniga ishonch hosil qiling.", "path", "/dəˈrekʃn/"),
        OxfordDefinition("discover", "A2", "v.", "kashf qilmoq, bilib olmoq", "Discover effective strategies to memorize.", "Yod olishning samarali usullarini kashf qiling.", "find out", "/dɪˈskʌvər/"),
        OxfordDefinition("education", "A2", "n.", "ta'lim, maorif", "Education opens doors across the globe.", "Ta'lim butun dunyo bo'ylab yangi eshiklarni ochadi.", "schooling", "/ˌedʒuˈkeɪʃn/"),
        OxfordDefinition("effect", "A2", "n.", "samara, ta'sir, natija", "The compounding effect of study is magical.", "O'qishning yig'ilib boruvchi samarasi ajoyibdir.", "outcome", "/ɪˈfekt/"),
        OxfordDefinition("effort", "A2", "n.", "harakat, tirishqoqlik", "Sincere effort is always rewarded.", "Samimiy harakat doimo mukofotlanadi.", "endeavor", "/ˈefərt/"),
        OxfordDefinition("energy", "A2", "n.", "quvvat, kuch", "Channel your morning energy wisely.", "Tonggi quvvatingizni oqilona yo'naltiring.", "vigor", "/ˈenərdʒi/"),
        OxfordDefinition("experience", "A2", "n., v.", "tajriba; boshdan kechirmoq", "Experience comes through active practice.", "Tajriba faol amaliyot orqali to'planadi.", "practice", "/ɪkˈspɪriəns/"),
        OxfordDefinition("explain", "A2", "v.", "tushuntirmoq, izohlamoq", "Explain the rule simply to a partner.", "Qoidani do'stingizga sodda qilib tushuntirib bering.", "clarify", "/ɪkˈspleɪn/"),
        OxfordDefinition("focus", "A2", "v., n.", "diqqatni jamlamoq; diqqat", "Focus entirely on today's target words.", "Bugungi mo'ljaldagi so'zlarga to'liq diqqat qarating.", "concentrate", "/ˈfoʊkəs/"),
        OxfordDefinition("habit", "A2", "n.", "odat, ko'nikma", "A good study habit shapes a bright future.", "Yaxshi o'rganish odati porloq kelajakni shakllantiradi.", "routine", "/ˈhæbɪt/"),
        OxfordDefinition("improve", "A2", "v.", "yaxshilamoq, o'stirmoq", "Improve your memory score each day.", "Har kuni xotira ko'rsatkichingizni yaxshilang.", "enhance", "/ɪmˈpruːv/"),
        OxfordDefinition("protect", "A2", "v.", "himoya qilmoq, asramoq", "Protect your study hours from disruptions.", "O'qish soatlaringizni chalg'ishlardan asrang.", "shield", "/prəˈtekt/"),

        // === B1 LEVEL ===
        OxfordDefinition("academic", "B1", "adj.", "akademik, ilmiy", "She achieved high academic distinction.", "U yuksak akademik yutuqqa erishdi.", "scholarly", "/ˌækəˈdemɪk/"),
        OxfordDefinition("access", "B1", "n., v.", "foydalanish imkoni, kirish", "Gain direct access to the Oxford vault.", "Oksford so'z sandig'idan bevosita foydalanish imkoniga ega bo'ling.", "entry", "/ˈækses/"),
        OxfordDefinition("achievement", "B1", "n.", "yutuq, muvaffaqiyat", "Consistent study is an honorable achievement.", "Uzluksiz o'qish — sharafli yutuqdir.", "accomplishment", "/əˈtʃiːvmənt/"),
        OxfordDefinition("advanced", "B1", "adj.", "ilg'or, chuqur", "Progress steadily towards advanced English.", "Ilg'or ingliz tili sari izchil qadam bosing.", "higher-level", "/ədˈvænst/"),
        OxfordDefinition("announce", "B1", "v.", "e'lon qilmoq, ma'lum qilmoq", "Announce your goals to maintain commitment.", "Ahdingizda qat'iy turish uchun maqsadlaringizni e'lon qiling.", "declare", "/əˈnaʊns/"),
        OxfordDefinition("anxious", "B1", "adj.", "xavotirlangan, tashvishli", "Do not be anxious before the examination.", "Imtihon oldidan xavotirga tushmang.", "worried", "/ˈæŋkʃəs/"),
        OxfordDefinition("appreciate", "B1", "v.", "qadrlamoq, minnatdor bo'lmoq", "Appreciate the value of uninterrupted time.", "Chalg'itilmagan vaqt qadrini yuksak biling.", "value", "/əˈpriːʃieɪt/"),
        OxfordDefinition("argument", "B1", "n.", "dalil, bahs, asos", "Support your points with sound arguments.", "Fikrlaringizni pishiq dalillar bilan quvvatlang.", "reasoning", "/ˈɑːrɡjumənt/"),
        OxfordDefinition("assist", "B1", "v.", "ko'maklashmoq, yordam bermoq", "Flashcards assist memory retention remarkably.", "Kartochkalar xotirada saqlashga ajoyib ko'maklashadi.", "help", "/əˈsɪst/"),
        OxfordDefinition("atmosphere", "B1", "n.", "muhit, atmosfera", "A calm atmosphere fosters creative thinking.", "Sokin muhit ijodiy fikrlashga qanot beradi.", "environment", "/ˈætməsfɪr/"),
        OxfordDefinition("attitude", "B1", "n.", "munosabat, dunyoqarash", "Maintain a tenacious attitude toward study.", "O'qishga nisbatan sabotli munosabatni saqlang.", "mindset", "/ˈætɪtuːd/"),
        OxfordDefinition("balance", "B1", "n., v.", "muvozanat; tenglashtirmoq", "Strike a balance between effort and rest.", "Mehnat va hordiq o'rtasida muvozanat o'rnating.", "equilibrium", "/ˈbæləns/"),
        OxfordDefinition("challenge", "B1", "n., v.", "qiyin sinov; chorlamoq", "Welcome every intellectual challenge.", "Har bir aqliy sinovni mamnuniyat bilan qarshi oling.", "test", "/ˈtʃælɪndʒ/"),
        OxfordDefinition("champion", "B1", "n.", "chempion, g'olib", "Self-mastery makes you a champion.", "O'zini yenga bilish insonni g'olibga aylantiradi.", "winner", "/ˈtʃæmpiən/"),
        OxfordDefinition("combine", "B1", "v.", "birlashtirmoq, uyg'unlashtirmoq", "Combine reading with active writing.", "Mutolaani faol yozish bilan uyg'unlashtiring.", "merge", "/kəmˈbaɪn/"),
        OxfordDefinition("communicate", "B1", "v.", "muloqot qilmoq, fikr almashmoq", "Communicate fluently with international peers.", "Xalqaro tengdoshlaringiz bilan ravon muloqot qiling.", "interact", "/kəˈmjuːnɪkeɪt/"),
        OxfordDefinition("compete", "B1", "v.", "bellashmoq, raqobatlashmoq", "Compete only against your past limitations.", "Faqat o'tmishdagi cheklovlaringiz bilan bellashing.", "vie", "/kəmˈpiːt/"),
        OxfordDefinition("confidence", "B1", "n.", "ishonch, dadillik", "Daily practice inspires deep confidence.", "Kunlik mashq chuqur o'ziga ishonch bag'ishlaydi.", "assurance", "/ˈkɑːnfɪdəns/"),
        OxfordDefinition("confirm", "B1", "v.", "tasdiqlamoq", "Confirm your task list before opening books.", "Kitob ochishdan avval vazifalar ro'yxatini tasdiqlang.", "verify", "/kənˈfɜːrm/"),
        OxfordDefinition("connection", "B1", "n.", "bog'liqlik, aloqa", "Discover the connection between words.", "So'zlar o'rtasidagi ma'nodoshlik aloqasini kashf qiling.", "link", "/kəˈnekʃn/"),
        OxfordDefinition("conscious", "B1", "adj.", "ongli, hushyor", "Make a conscious decision to stay focused.", "Chalg'imaslik uchun ongli qaror qabul qiling.", "aware", "/ˈkɑːnʃəs/"),
        OxfordDefinition("consequence", "B1", "n.", "oqibat, natija", "Every choice carries an inevitable consequence.", "Har bir tanlov muqarrar oqibatga egadir.", "outcome", "/ˈkɑːnsəkwens/"),
        OxfordDefinition("convenient", "B1", "adj.", "qulay, bop", "The mini upload window is remarkably convenient.", "Kichik yuklash oynasi o'ta qulaydir.", "handy", "/kənˈviːniənt/"),
        OxfordDefinition("convince", "B1", "v.", "ishontirmoq, ko'ndirmoq", "Convince yourself that effort yields mastery.", "Mehnat mahorat keltirishiga o'zingizni ishontiring.", "persuade", "/kənˈvɪns/"),
        OxfordDefinition("curious", "B1", "adj.", "qiziquvchan, intiluvchan", "A curious intellect masters languages fast.", "Qiziquvchan aql tillarni tez o'rganadi.", "inquisitive", "/ˈkjʊriəs/"),
        OxfordDefinition("decrease", "B1", "v., n.", "kamaytirmoq; pasayish", "Decrease passive scrolling on your phone.", "Telefondagi maqsadsiz tomoshani kamaytiring.", "reduce", "/dɪˈkriːs/"),
        OxfordDefinition("define", "B1", "v.", "ta'riflamoq, belgilamoq", "Define your daily milestones precisely.", "Kunlik bosqichlaringizni aniq belgilab oling.", "specify", "/dɪˈfaɪn/"),
        OxfordDefinition("discipline", "B1", "n.", "intizom, tartib", "Discipline bridges goals and accomplishments.", "Intizom — maqsadlar bilan yutuqlar o'rtasidagi ko'prikdir.", "order", "/ˈdɪsəplɪn/"),
        OxfordDefinition("efficient", "B1", "adj.", "samarali, unumli", "Spaced flashcards are an efficient technique.", "Intervalli kartochkalar — samarali uslubdir.", "productive", "/ɪˈfɪʃnt/"),
        OxfordDefinition("persevere", "B1", "v.", "sabr qilmoq, qat'iyat ko'rsatmoq", "Persevere through difficult study sessions.", "Qiyin o'qish onlarida sabr-matonat ko'rsating.", "persist", "/ˌpɜːrsəˈvɪr/"),
        OxfordDefinition("priority", "B1", "n.", "ustuvorlik, birinchi darajali ish", "Make vocabulary learning your top priority.", "So'z o'rganishni eng ustuvor vazifangizga aylantiring.", "precedence", "/praɪˈɔːrəti/"),

        // === B2 LEVEL ===
        OxfordDefinition("abandon", "B2", "v.", "tark etmoq, to'xtatmoq", "Never abandon your high academic goals.", "Yuksak ilmiy maqsadlaringizni aslo tark etmang.", "relinquish", "/əˈbændən/"),
        OxfordDefinition("absolute", "B2", "adj.", "mutlaq, so'zsiz", "Absolute focus produces world-class mastery.", "Mutlaq diqqat yuksak darajadagi mahoratni keltirib chiqaradi.", "complete", "/ˈæbsəluːt/"),
        OxfordDefinition("accurate", "B2", "adj.", "aniq, xatosiz, puxta", "Provide accurate definitions in the dictionary.", "Lug'atda aniq va xatosiz ta'riflarni taqdim eting.", "precise", "/ˈækjərət/"),
        OxfordDefinition("acknowledge", "B2", "v.", "tan olmoq, e'tirof etmoq", "Acknowledge mistakes to rectify them swiftly.", "Xatolarni tezda to'g'rilash uchun ularni tan oling.", "admit", "/əkˈnɑːlɪdʒ/"),
        OxfordDefinition("acquire", "B2", "v.", "o'zlashtirmoq, egallamoq", "Acquire rich vocabulary through daily immersion.", "Har kungi mutolaa orqali boy so'z zahirasini egallang.", "obtain", "/əˈkwaɪər/"),
        OxfordDefinition("adapt", "B2", "v.", "moslashmoq, moslashtirmoq", "Adapt your routine to changing demands.", "Tartibingizni o'zgaruvchan talablarga moslashtiring.", "adjust", "/əˈdæpt/"),
        OxfordDefinition("adequate", "B2", "adj.", "yetarli, talabga javob beruvchi", "Ensure adequate sleep before intense study.", "Kuchli o'qishdan oldin yetarlicha uxlashni ta'minlang.", "sufficient", "/ˈædɪkwət/"),
        OxfordDefinition("analysis", "B2", "n.", "tahlil, tadqiq", "Thorough analysis uncovers subtle grammar rules.", "Chuqur tahlil nozik grammatik qoidalarni ochib beradi.", "examination", "/əˈnæləsɪs/"),
        OxfordDefinition("barrier", "B2", "n.", "to'siq, g'ov", "Persistence dismantles any language barrier.", "Qat'iyat har qanday til to'sig'ini parchalab tashlaydi.", "obstacle", "/ˈbæriər/"),
        OxfordDefinition("capable", "B2", "adj.", "qodir, uddasidan chiquvchi", "You are fully capable of reaching fluency.", "Siz tilni ravon bilish darajasiga yetishga to'liq qodirsiz.", "competent", "/ˈkeɪpəbl/"),
        OxfordDefinition("capacity", "B2", "n.", "salohiyat, sig'im", "Expand your mental capacity with challenging texts.", "Murakkab matnlar orqali aqliy salohiyatingizni kengaytiring.", "ability", "/kəˈpæsəti/"),
        OxfordDefinition("collapse", "B2", "v., n.", "barbod bo'lmoq, qulamoq", "Without daily discipline, habits collapse.", "Kunlik intizomsiz odatlar barbod bo'ladi.", "break down", "/kəˈlæps/"),
        OxfordDefinition("comprehensive", "B2", "adj.", "har tomonlama, mukammal, qamrovli", "This Oxford 3000 vault is truly comprehensive.", "Ushbu Oksford 3000 sandig'i chinakam qamrovlidir.", "all-inclusive", "/ˌkɑːmprɪˈhensɪv/"),
        OxfordDefinition("consistency", "B2", "n.", "izchillik, doimiylik", "Consistency transforms good habits into greatness.", "Izchillik yaxshi odatlarni buyuk yutuqlarga aylantiradi.", "steadiness", "/kənˈsɪstənsi/"),
        OxfordDefinition("crucial", "B2", "adj.", "hal qiluvchi, o'ta muhim", "Active recall is crucial for long-term retention.", "Uzoq muddat eslab qolish uchun faol takrorlash hal qiluvchi ahamiyatga ega.", "vital", "/ˈkruːʃl/"),
        OxfordDefinition("dedication", "B2", "n.", "fidoiylik, sodiqlik", "Mastering a language requires real dedication.", "Tilni mukammal bilish chinakam fidoiylikni talab qiladi.", "commitment", "/ˌdedɪˈkeɪʃn/"),
        OxfordDefinition("distribute", "B2", "v.", "taqsimlamoq, bo'lib bermoq", "Distribute your words evenly throughout the week.", "So'zlaringizni hafta kunlariga tekis taqsimlang.", "apportion", "/dɪˈstrɪbjuːt/"),
        OxfordDefinition("eliminate", "B2", "v.", "bartaraf etmoq, yo'q qilmoq", "Eliminate time-wasting apps from your phone.", "Telefoningizdagi vaqt o'g'rilarini bartaraf eting.", "remove", "/ɪˈlɪmɪneɪt/"),
        OxfordDefinition("enhance", "B2", "v.", "kuchaytirmoq, ko'tarmoq", "Interactive quizzes enhance memory recall.", "Interaktiv sinovlar xotirada eslashni kuchaytiradi.", "boost", "/ɪnˈhæns/"),
        OxfordDefinition("fundamental", "B2", "adj.", "fundamental, asosiy", "Patience is a fundamental virtue of learning.", "Sabr — o'rganishning eng asosiy fazilatidir.", "essential", "/ˌfʌndəˈmentl/"),
        OxfordDefinition("implement", "B2", "v.", "joriy qilmoq, amalga oshirmoq", "Implement your study plan without hesitation.", "O'qish rejangizni ikkilanmasdan amalga oshiring.", "execute", "/ˈɪmplɪment/"),
        OxfordDefinition("investigate", "B2", "v.", "tadqiq qilmoq, o'rganmoq", "Investigate unknown words with enthusiasm.", "Noma'lum so'zlarni katta qiziqish bilan tadqiq qiling.", "examine", "/ɪnˈvestɪɡeɪt/"),
        OxfordDefinition("maintain", "B2", "v.", "saqlamoq, ushlab turmoq", "Maintain your daily quota inside the vault.", "Sandiq ichidagi kunlik me'yoringizni saqlab boring.", "preserve", "/meɪnˈteɪn/"),
        OxfordDefinition("potential", "B2", "n., adj.", "salohiyat, imkoniyat", "Unlock your full cognitive potential.", "To'liq aqliy salohiyatingizni ro'yobga chiqaring.", "capability", "/pəˈtenʃl/"),
        OxfordDefinition("precise", "B2", "adj.", "aniq, puxta", "Keep a precise record of mastered words.", "Yodlangan so'zlarning aniq hisobini yuriting.", "exact", "/prɪˈsaɪs/"),
        OxfordDefinition("resilience", "B2", "n.", "bardoshlik, chidamlilik", "Mental resilience guarantees steady progress.", "Ruhiy bardoshlik muntazam yuksalishni kafolatlaydi.", "toughness", "/rɪˈzɪliəns/"),
        OxfordDefinition("substantial", "B2", "adj.", "salmoqli, sezilarli", "You made a substantial leap in vocabulary.", "Siz so'z boyligingizda salmoqli o'sish qildingiz.", "considerable", "/səbˈstænʃl/"),
        OxfordDefinition("sustain", "B2", "v.", "saqlab turmoq, davom ettirmoq", "Sustain high motivation with small daily wins.", "Kunlik kichik g'alabalar bilan ishtiyoqni saqlab turing.", "uphold", "/səˈsteɪn/"),
        OxfordDefinition("ultimate", "B2", "adj.", "pirovard, yakuniy", "Mastery of English is your ultimate objective.", "Ingliz tilini mukammal bilish — sizning yakuniy maqsadingizdir.", "paramount", "/ˈʌltɪmət/"),

        // === C1 LEVEL ===
        OxfordDefinition("articulate", "C1", "v., adj.", "ravon bayon qilmoq; tushunarli", "Articulate complex ideas with poise and clarity.", "Murakkab fikrlarni xotirjam va ravon bayon qiling.", "express", "/ɑːrˈtɪkjuleɪt/"),
        OxfordDefinition("comprehend", "C1", "v.", "chuqur tushunmoq, anglab yetmoq", "Comprehend advanced nuance in native speech.", "Nutqdagi nozik ma'nolarni chuqur anglab yeting.", "grasp", "/ˌkɑːmprɪˈhend/"),
        OxfordDefinition("diligence", "C1", "n.", "qunt, tirishqoqlik", "Unfaltering diligence conquers every obstacle.", "Bo'shashmas qunt har qanday to'siqni yengadi.", "industriousness", "/ˈdɪlɪdʒəns/"),
        OxfordDefinition("eloquent", "C1", "adj.", "notiq, fasih, chechan", "He delivered an eloquent and moving speech.", "U ta'sirchan va fasih nutq so'zladi.", "articulate", "/ˈeləkwənt/"),
        OxfordDefinition("exemplary", "C1", "adj.", "ibratli, namunali", "Her daily study routine is truly exemplary.", "Uning kunlik o'qish tartibi chinakam ibratlidir.", "model", "/ɪɡˈzempləri/"),
        OxfordDefinition("lucid", "C1", "adj.", "ravshan, tiniq, tushunarli", "Write lucid explanations for every vocabulary card.", "Har bir lug'at kartochkasi uchun ravshan izohlar yozing.", "crystalline", "/ˈluːsɪd/"),
        OxfordDefinition("meticulous", "C1", "adj.", "o'ta sinchkov, puxta", "He is meticulous with every grammar detail.", "U har bir grammatik tafsilotga o'ta sinchkovdir.", "scrupulous", "/məˈtɪkjələs/"),
        OxfordDefinition("perseverance", "C1", "n.", "sabr-matonat, sabot, qat'iyat", "Perseverance guarantees victory over difficulty.", "Sabr-matonat har qanday qiyinchilik ustidan g'alaba kafolatidir.", "tenacity", "/ˌpɜːrsəˈvɪrəns/"),
        OxfordDefinition("resilient", "C1", "adj.", "bardoshli, yengilmas", "A resilient student embraces mistakes as teachers.", "Bardoshli talaba xatolarni o'rgatuvchi muallim deb biladi.", "unyielding", "/rɪˈzɪliənt/"),
        OxfordDefinition("scrutinize", "C1", "v.", "sinchkovlik bilan tekshirmoq", "Scrutinize every word choice in your essay.", "Inshongizdagi har bir so'z tanlovini sinchkovlik bilan tekshiring.", "examine closely", "/ˈskruːtənaɪz/"),
        OxfordDefinition("tenacious", "C1", "adj.", "bo'sh kelmaydigan, qat'iyatli", "A tenacious mindset never surrenders.", "Qat'iyatli fikrlash hech qachon taslim bo'lmaydi.", "resolute", "/təˈneɪʃəs/")
    )

    // Lookup index for fast O(1) matching by lowercase word
    private val masterIndex: Map<String, OxfordDefinition> by lazy {
        masterEntries.associateBy { it.word.lowercase(Locale.ROOT) }
    }

    @Volatile
    private var cachedCards: List<VocabCard>? = null
    @Volatile
    private var assetIndex: Map<String, OxfordDefinition>? = null

    /**
     * Loads the complete 1500+ Oxford 3000 database from assets/oxford_3000.tsv
     */
    fun loadFromAssets(context: Context): List<VocabCard> {
        cachedCards?.let { return it }
        synchronized(this) {
            cachedCards?.let { return it }
            val list = mutableListOf<VocabCard>()
            val index = mutableMapOf<String, OxfordDefinition>()

            try {
                context.assets.open("oxford_3000.tsv").use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).useLines { lines ->
                        var isFirst = true
                        for (line in lines) {
                            if (isFirst) {
                                isFirst = false
                                continue
                            }
                            if (line.isBlank()) continue
                            val parts = line.split("\t")
                            if (parts.size >= 4) {
                                val word = parts[0].trim()
                                if (word.isBlank()) continue
                                val level = parts.getOrNull(1)?.trim()?.ifBlank { "A1" } ?: "A1"
                                val pos = parts.getOrNull(2)?.trim()?.ifBlank { "n." } ?: "n."
                                val uz = parts.getOrNull(3)?.trim() ?: ""
                                val example = parts.getOrNull(4)?.trim()?.ifBlank { "Practice using '$word' in daily English." } ?: "Practice using '$word' in daily English."
                                val uzExample = parts.getOrNull(5)?.trim()?.ifBlank { "'$word' so'zini har kuni inglizcha nutqda qo'llang." } ?: "'$word' so'zini har kuni inglizcha nutqda qo'llang."
                                val synonym = parts.getOrNull(6)?.trim() ?: ""
                                val phonetic = parts.getOrNull(7)?.trim() ?: ""

                                val def = OxfordDefinition(
                                    word = word,
                                    level = level,
                                    pos = pos,
                                    uz = uz,
                                    example = example,
                                    uzExample = uzExample,
                                    synonym = synonym,
                                    phonetic = phonetic
                                )
                                index[word.lowercase(Locale.ROOT)] = def

                                list.add(
                                    VocabCard(
                                        id = UUID.randomUUID().toString(),
                                        word = word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                                        translation = uz,
                                        phonetic = phonetic,
                                        partOfSpeech = pos,
                                        definition = "Oksford 3000™ · CEFR $level darajasi",
                                        example = example,
                                        exampleTranslation = uzExample,
                                        synonym = synonym,
                                        mnemonic = "CEFR $level · Oksford oltin fondi",
                                        boxLevel = 1,
                                        level = level,
                                        sourceDocName = "The Oxford 3000™",
                                        isMastered = false,
                                        reviewCount = 0
                                    )
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (list.isEmpty()) {
                val hardcoded = getAllHardcodedCards()
                cachedCards = hardcoded
                return hardcoded
            }

            val sorted = list.sortedWith(
                compareBy<VocabCard> { VocabDocumentParser.getLevelWeight(it.level) }
                    .thenBy { it.word.lowercase(Locale.ROOT) }
            )
            cachedCards = sorted
            assetIndex = index
            return sorted
        }
    }

    /**
     * Look up word definition with full Uzbek translation and example.
     */
    fun findWord(word: String, context: Context? = null): OxfordDefinition? {
        val clean = word.trim().lowercase(Locale.ROOT)
        if (context != null && assetIndex == null) {
            loadFromAssets(context)
        }
        return assetIndex?.get(clean) ?: masterIndex[clean]
    }

    /**
     * Converts master entries into VocabCard models ready for the Vault ("Sandiq").
     * If context is provided or cached from assets, returns the full 1500+ Oxford cards.
     */
    fun getAllOxfordCards(context: Context? = null): List<VocabCard> {
        if (context != null) {
            return loadFromAssets(context)
        }
        return cachedCards ?: getAllHardcodedCards()
    }

    private fun getAllHardcodedCards(): List<VocabCard> {
        return masterEntries.map { entry ->
            VocabCard(
                id = UUID.randomUUID().toString(),
                word = entry.word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                translation = entry.uz,
                phonetic = entry.phonetic,
                partOfSpeech = entry.pos,
                definition = "Oksford 3000™ · CEFR ${entry.level} darajasi",
                example = entry.example,
                exampleTranslation = entry.uzExample,
                synonym = entry.synonym,
                mnemonic = "CEFR ${entry.level} · Oksford oltin fondi",
                boxLevel = 1,
                level = entry.level,
                sourceDocName = "The Oxford 3000™",
                isMastered = false,
                reviewCount = 0
            )
        }.sortedWith(
            compareBy<VocabCard> { VocabDocumentParser.getLevelWeight(it.level) }
                .thenBy { it.word.lowercase(Locale.ROOT) }
        )
    }

    /**
     * Return cards filtered by CEFR level.
     */
    fun getOxfordCardsByLevel(level: String, context: Context? = null): List<VocabCard> {
        val upper = level.trim().uppercase(Locale.ROOT)
        return getAllOxfordCards(context).filter { it.level.equals(upper, ignoreCase = true) }
    }
}
