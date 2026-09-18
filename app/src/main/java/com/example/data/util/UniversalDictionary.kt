package com.example.data.util

import android.content.Context
import java.util.Locale

data class WordLookupResult(
    val word: String,
    val translationUz: String,
    val phonetic: String,
    val partOfSpeech: String,
    val level: String = "A2",
    val exampleSentence: String = "",
    val exampleTranslation: String = ""
)

object UniversalDictionary {

    // Noto'g'ri fe'llar (Irregular verbs) to'liq xaritasi -> asosiy shakli (lemma)
    private val IRREGULAR_VERBS = mapOf(
        "went" to "go", "gone" to "go", "goes" to "go", "going" to "go",
        "saw" to "see", "seen" to "see", "sees" to "see", "seeing" to "see",
        "said" to "say", "says" to "say", "saying" to "say",
        "took" to "take", "taken" to "take", "takes" to "take", "taking" to "take",
        "came" to "come", "comes" to "come", "coming" to "come",
        "made" to "make", "makes" to "make", "making" to "make",
        "knew" to "know", "known" to "know", "knows" to "know",
        "thought" to "think", "thinks" to "think", "thinking" to "think",
        "told" to "tell", "tells" to "tell", "telling" to "tell",
        "became" to "become", "becomes" to "become", "becoming" to "become",
        "left" to "leave", "leaves" to "leave", "leaving" to "leave",
        "felt" to "feel", "feels" to "feel", "feeling" to "feel",
        "brought" to "bring", "brings" to "bring", "bringing" to "bring",
        "began" to "begin", "begun" to "begin", "begins" to "begin", "beginning" to "begin",
        "kept" to "keep", "keeps" to "keep", "keeping" to "keep",
        "held" to "hold", "holds" to "hold", "holding" to "hold",
        "wrote" to "write", "written" to "write", "writes" to "write", "writing" to "write",
        "stood" to "stand", "stands" to "stand", "standing" to "stand",
        "heard" to "hear", "hears" to "hear", "hearing" to "hear",
        "let" to "let", "lets" to "let", "letting" to "let",
        "meant" to "mean", "means" to "mean", "meaning" to "mean",
        "set" to "set", "sets" to "set", "setting" to "set",
        "met" to "meet", "meets" to "meet", "meeting" to "meet",
        "ran" to "run", "runs" to "run", "running" to "run",
        "paid" to "pay", "pays" to "pay", "paying" to "pay",
        "sat" to "sit", "sits" to "sit", "sitting" to "sit",
        "spoke" to "speak", "spoken" to "speak", "speaks" to "speak", "speaking" to "speak",
        "lay" to "lie", "lain" to "lie", "lies" to "lie", "lying" to "lie",
        "led" to "lead", "leads" to "lead", "leading" to "lead",
        "read" to "read", "reads" to "read", "reading" to "read",
        "grew" to "grow", "grown" to "grow", "grows" to "grow", "growing" to "grow",
        "lost" to "lose", "loses" to "lose", "losing" to "lose",
        "fell" to "fall", "fallen" to "fall", "falls" to "fall", "falling" to "fall",
        "sent" to "send", "sends" to "send", "sending" to "send",
        "built" to "build", "builds" to "build", "building" to "build",
        "understood" to "understand", "understands" to "understand",
        "drew" to "draw", "drawn" to "draw", "draws" to "draw", "drawing" to "draw",
        "broke" to "break", "broken" to "break", "breaks" to "break", "breaking" to "break",
        "spent" to "spend", "spends" to "spend", "spending" to "spend",
        "cut" to "cut", "cuts" to "cut", "cutting" to "cut",
        "rose" to "rise", "risen" to "rise", "rises" to "rise", "rising" to "rise",
        "drove" to "drive", "driven" to "drive", "drives" to "drive", "driving" to "drive",
        "bought" to "buy", "buys" to "buy", "buying" to "buy",
        "wore" to "wear", "worn" to "wear", "wears" to "wear", "wearing" to "wear",
        "chose" to "choose", "chosen" to "choose", "chooses" to "choose", "choosing" to "choose",
        "drank" to "drink", "drunk" to "drink", "drinks" to "drink", "drinking" to "drink",
        "ate" to "eat", "eaten" to "eat", "eats" to "eat", "eating" to "eat",
        "slept" to "sleep", "sleeps" to "sleep", "sleeping" to "sleep",
        "taught" to "teach", "teaches" to "teach", "teaching" to "teach",
        "caught" to "catch", "catches" to "catch", "catching" to "catch",
        "fought" to "fight", "fights" to "fight", "fighting" to "fight",
        "threw" to "throw", "thrown" to "throw", "throws" to "throw", "throwing" to "throw",
        "flew" to "fly", "flown" to "fly", "flies" to "fly", "flying" to "fly",
        "swam" to "swim", "swum" to "swim", "swims" to "swim", "swimming" to "swim",
        "woke" to "wake", "woken" to "wake", "wakes" to "wake", "waking" to "wake",
        "forgot" to "forget", "forgotten" to "forget", "forgets" to "forget",
        "got" to "get", "gotten" to "get", "gets" to "get", "getting" to "get",
        "hid" to "hide", "hidden" to "hide", "hides" to "hide", "hiding" to "hide",
        "bit" to "bite", "bitten" to "bite", "bites" to "bite", "biting" to "bite",
        "struck" to "strike", "strikes" to "strike", "striking" to "strike",
        "shook" to "shake", "shaken" to "shake", "shakes" to "shake", "shaking" to "shake",
        "shone" to "shine", "shines" to "shine", "shining" to "shine",
        "tore" to "tear", "torn" to "tear", "tears" to "tear", "tearing" to "tear",
        "blew" to "blow", "blown" to "blow", "blows" to "blow", "blowing" to "blow",
        "hung" to "hang", "hangs" to "hang", "hanging" to "hang",
        "sung" to "sing", "sang" to "sing", "sings" to "sing", "singing" to "sing",
        "swept" to "sweep", "sweeps" to "sweep",
        "swung" to "swing", "swings" to "swing",
        "wound" to "wind", "winds" to "wind",
        "bound" to "bind", "binds" to "bind",
        "burnt" to "burn", "burned" to "burn",
        "dreamt" to "dream", "dreamed" to "dream",
        "learnt" to "learn", "learned" to "learn",
        "spilt" to "spill", "spilled" to "spill",
        "crept" to "creep", "creeps" to "creep",
        "knelt" to "kneel", "kneels" to "kneel"
    )

    // Ko'plik va maxsus shakllar (Irregular plurals)
    private val IRREGULAR_PLURALS = mapOf(
        "children" to "child",
        "men" to "man",
        "women" to "woman",
        "people" to "person",
        "feet" to "foot",
        "teeth" to "tooth",
        "mice" to "mouse",
        "lives" to "life",
        "knives" to "knife",
        "wives" to "wife",
        "halves" to "half",
        "leaves" to "leaf",
        "thieves" to "thief",
        "wolves" to "wolf",
        "shelves" to "shelf",
        "calves" to "calf",
        "loaves" to "loaf"
    )

    /**
     * Berilgan so'zning barcha ehtimoliy lemma (ildiz) variantlarini hosil qiladi.
     */
    fun generateCandidates(rawWord: String): List<String> {
        val clean = rawWord.lowercase(Locale.ROOT).trim()
        if (clean.isBlank()) return emptyList()

        val results = mutableListOf(clean)

        IRREGULAR_VERBS[clean]?.let { results.add(it) }
        IRREGULAR_PLURALS[clean]?.let { results.add(it) }

        // Qoidaviy suffikslarni olib tashlash
        when {
            clean.endsWith("ing") -> {
                val stem = clean.removeSuffix("ing")
                results.add(stem)
                results.add("${stem}e") // driving -> drive, making -> make
                if (stem.length > 2 && stem[stem.length - 1] == stem[stem.length - 2]) {
                    results.add(stem.dropLast(1)) // running -> run, stopping -> stop
                }
            }
            clean.endsWith("ed") -> {
                val stem = clean.removeSuffix("ed")
                results.add(stem)
                results.add("${stem}e") // lived -> live, baked -> bake
                if (clean.endsWith("ied")) {
                    results.add(clean.removeSuffix("ied") + "y") // hurried -> hurry, carried -> carry
                }
                if (stem.length > 2 && stem[stem.length - 1] == stem[stem.length - 2]) {
                    results.add(stem.dropLast(1)) // stopped -> stop, planned -> plan
                }
            }
            clean.endsWith("d") -> results.add(clean.removeSuffix("d"))
            clean.endsWith("ies") -> results.add(clean.removeSuffix("ies") + "y") // stories -> story
            clean.endsWith("es") -> {
                results.add(clean.removeSuffix("es"))
                results.add(clean.removeSuffix("s"))
            }
            clean.endsWith("s") && !clean.endsWith("ss") -> results.add(clean.removeSuffix("s"))
            clean.endsWith("ly") -> {
                results.add(clean.removeSuffix("ly")) // quickly -> quick
                if (clean.endsWith("ily")) {
                    results.add(clean.removeSuffix("ily") + "y") // easily -> easy
                }
            }
            clean.endsWith("er") -> {
                results.add(clean.removeSuffix("er"))
                results.add(clean.removeSuffix("er") + "e")
            }
            clean.endsWith("est") -> {
                results.add(clean.removeSuffix("est"))
                results.add(clean.removeSuffix("est") + "e")
            }
        }

        return results.distinct()
    }

    /**
     * Universal so'z qidiruvi:
     * 1) Oxford 3000 bazasidan to'liq TSV qidiruvi
     * 2) Graded Reader va o'quv dasturi lug'ati
     * 3) Irregular verbs va morfologik qidiruv
     */
    fun lookup(rawWord: String, context: Context?): WordLookupResult? {
        val clean = rawWord.lowercase(Locale.ROOT).trim()
        if (clean.isBlank()) return null

        val candidates = generateCandidates(clean)

        for (cand in candidates) {
            // 1. Oxford 3000 Database check
            val ox = Oxford3000Database.findWord(cand, context)
            if (ox != null && ox.uz.isNotBlank()) {
                return WordLookupResult(
                    word = clean,
                    translationUz = ox.uz,
                    phonetic = ox.phonetic.ifBlank { "[${clean}]" },
                    partOfSpeech = ox.pos.ifBlank { "vocabulary" },
                    level = ox.level.ifBlank { "A2" },
                    exampleSentence = ox.example,
                    exampleTranslation = ox.uzExample
                )
            }

            // 2. Comprehensive built-in dictionary
            val entry = BUILTIN_VOCABULARY[cand]
            if (entry != null) {
                return WordLookupResult(
                    word = clean,
                    translationUz = entry.uz,
                    phonetic = entry.phonetic,
                    partOfSpeech = entry.pos,
                    level = entry.level,
                    exampleSentence = entry.example,
                    exampleTranslation = entry.uzExample
                )
            }
        }

        return null
    }

    data class VocabEntry(
        val uz: String,
        val phonetic: String,
        val pos: String,
        val level: String = "A2",
        val example: String = "",
        val uzExample: String = ""
    )

    // Kengaytirilgan O'zbekcha-Inglizcha Offline Lug'at (1000 dan ortiq asosiy so'zlar)
    val BUILTIN_VOCABULARY = mapOf(
        // Harakat fe'llari va hikoyalar leksikasi
        "truck" to VocabEntry("yuk mashinasi, fura", "[trʌk]", "noun", "A1", "Mark drives a big truck.", "Mark katta yuk mashinasini haydaydi."),
        "driver" to VocabEntry("haydovchi", "[ˈdraɪvər]", "noun", "A1", "He is an experienced driver.", "U tajribali haydovchi."),
        "desert" to VocabEntry("cho'l, sahro", "[ˈdezət]", "noun", "A2", "The desert road was lonely.", "Cho'l yo'li kimsasiz edi."),
        "highway" to VocabEntry("magistral katta yo'l", "[ˈhaɪweɪ]", "noun", "A2", "They sped along the highway.", "Ular katta yo'lda tez yurishdi."),
        "danger" to VocabEntry("xavf-xatar", "[ˈdeɪndʒər]", "noun", "A2", "He sensed impending danger.", "U yaqinlashayotgan xavfni sezdi."),
        "container" to VocabEntry("konteyner, katta yuk qutisi", "[kənˈteɪnər]", "noun", "B1"),
        "cargo" to VocabEntry("yuk, tovar", "[ˈkɑːɡəʊ]", "noun", "B1"),
        "accelerate" to VocabEntry("tezlashmoq, tezlikni oshirmoq", "[əkˈseləreɪt]", "verb", "B2"),
        "brake" to VocabEntry("tormoz, tormoz bermoq", "[breɪk]", "noun/verb", "A2"),
        "brakes" to VocabEntry("tormozlar", "[breɪks]", "noun", "A2"),
        "engine" to VocabEntry("motor, dvigatel", "[ˈendʒɪn]", "noun", "A2"),
        "wheel" to VocabEntry("g'ildirak, rul", "[wiːl]", "noun", "A1"),
        "steering" to VocabEntry("boshqaruv, rul tizimi", "[ˈstɪərɪŋ]", "noun", "B1"),
        "mirror" to VocabEntry("ko'zgu, oyna", "[ˈmɪrər]", "noun", "A2"),
        "tyre" to VocabEntry("shina, balon", "[ˈtaɪər]", "noun", "A2"),
        "tyres" to VocabEntry("shinalar", "[ˈtaɪərz]", "noun", "A2"),
        "radiator" to VocabEntry("radiator, sovutgich", "[ˈreɪdieɪtər]", "noun", "B1"),
        "coolant" to VocabEntry("sovutish suyuqligi", "[ˈkuːlənt]", "noun", "B2"),
        "leak" to VocabEntry("oqmoq, sizib chiqmoq", "[liːk]", "verb/noun", "B1"),
        "puncture" to VocabEntry("teshilmoq, teshilish", "[ˈpʌŋktʃər]", "verb/noun", "B2"),
        "punctured" to VocabEntry("teshilgan", "[ˈpʌŋktʃəd]", "adj", "B2"),
        "steam" to VocabEntry("bug'", "[stiːm]", "noun", "A2"),
        "smoke" to VocabEntry("tutun", "[sməʊk]", "noun", "A2"),
        "flashlight" to VocabEntry("fonar, qo'l chirog'i", "[ˈflæʃlaɪt]", "noun", "A2"),
        "torch" to VocabEntry("mash'ala, fonar", "[tɔːtʃ]", "noun", "A2"),
        "oasis" to VocabEntry("voha, oazis", "[əʊˈeɪsɪs]", "noun", "B1"),
        "canyon" to VocabEntry("dara, kanyon", "[ˈkænjən]", "noun", "B1"),
        "cliff" to VocabEntry("tik qoya, jar yoqasi", "[klɪf]", "noun", "B1"),
        "ridge" to VocabEntry("qir, do'nglik cho'qqisi", "[rɪdʒ]", "noun", "B2"),
        "dune" to VocabEntry("qumtepa, barxan", "[djuːn]", "noun", "B1"),
        "patrol" to VocabEntry("patrul, navbatchi soqchi", "[pəˈtrəʊl]", "noun/verb", "B1"),
        "siren" to VocabEntry("trevoga signali, sirena", "[ˈsaɪərən]", "noun", "A2"),
        "helicopter" to VocabEntry("vertolyot", "[ˈhelɪkɒptər]", "noun", "A2"),
        "ambush" to VocabEntry("pistirma", "[ˈæmbʊʃ]", "noun/verb", "B2"),
        "surrender" to VocabEntry("taslim bo'lmoq", "[səˈrendər]", "verb", "B2"),
        "smuggler" to VocabEntry("kontrabandachi", "[ˈsmʌɡlər]", "noun", "B2"),
        "smugglers" to VocabEntry("kontrabandachilar", "[ˈsmʌɡlərz]", "noun", "B2"),
        "bandit" to VocabEntry("bosqinchi, qaroqchi", "[ˈbændɪt]", "noun", "B1"),
        "bandits" to VocabEntry("qaroqchilar", "[ˈbændɪts]", "noun", "B1"),
        "criminal" to VocabEntry("jinoyatchi", "[ˈkrɪmɪnəl]", "noun", "B1"),
        "emergency" to VocabEntry("favqulodda holat", "[ɪˈmɜːdʒənsi]", "noun", "B1"),
        "panic" to VocabEntry("vahima, sarosima", "[ˈpænɪk]", "noun/verb", "B1"),
        "calm" to VocabEntry("xotirjam, bosiq", "[kɑːm]", "adj", "A2"),
        "courage" to VocabEntry("jasorat, dovyuraklik", "[ˈkʌrɪdʒ]", "noun", "B1"),
        "brave" to VocabEntry("jasur, qo'rqmas", "[breɪv]", "adj", "A2"),
        "rescue" to VocabEntry("qutqarmoq, qutqaruv", "[ˈreskjuː]", "verb/noun", "B1"),
        "escape" to VocabEntry("qochib qutulmoq", "[ɪˈskeɪp]", "verb/noun", "B1"),
        "escaped" to VocabEntry("qochib ketdi", "[ɪˈskeɪpt]", "verb", "B1"),
        "follow" to VocabEntry("ergashmoq, kuzatmoq", "[ˈfɒləʊ]", "verb", "A2"),
        "chase" to VocabEntry("quvlamoq, ta'qib qilmoq", "[tʃeɪs]", "verb/noun", "B1"),
        "pursuit" to VocabEntry("ta'qib, quvish", "[pəˈsjuːt]", "noun", "B2"),

        // Sherlock Holmes va detektiv so'zlar
        "detective" to VocabEntry("izquvar, detektiv", "[dɪˈtektɪv]", "noun", "A2"),
        "investigate" to VocabEntry("tekshirmoq, surishtirmoq", "[ɪnˈvestɪɡeɪt]", "verb", "B1"),
        "investigation" to VocabEntry("tergov, surishtiruv", "[ɪnˌvestɪˈɡeɪʃən]", "noun", "B2"),
        "mystery" to VocabEntry("sir, jumboq", "[ˈmɪstəri]", "noun", "B1"),
        "secret" to VocabEntry("sir, maxfiy", "[ˈsiːkrət]", "noun/adj", "A2"),
        "clue" to VocabEntry("dalil, ip uchi", "[kluː]", "noun", "B1"),
        "evidence" to VocabEntry("dalil-isbot", "[ˈevɪdəns]", "noun", "B2"),
        "track" to VocabEntry("iz, izidan bormoq", "[træk]", "noun/verb", "A2"),
        "tracks" to VocabEntry("izlar", "[træks]", "noun", "A2"),
        "footprint" to VocabEntry("oyoq izi", "[ˈfʊtprɪnt]", "noun", "A2"),
        "footprints" to VocabEntry("oyoq izlari", "[ˈfʊtprɪnts]", "noun", "A2"),
        "hoof" to VocabEntry("tuyoq", "[huːf]", "noun", "B2"),
        "hoofprints" to VocabEntry("tuyoq izlari", "[ˈhuːfprɪnts]", "noun", "B2"),
        "horseshoe" to VocabEntry("taqa", "[ˈhɔːsʃuː]", "noun", "B2"),
        "horseshoes" to VocabEntry("taqalar", "[ˈhɔːsʃuːz]", "noun", "B2"),
        "bicycle" to VocabEntry("velosiped", "[ˈbaɪsɪkl]", "noun", "A1"),
        "handlebars" to VocabEntry("velosiped ruli", "[ˈhændlbɑːz]", "noun", "B1"),
        "spokes" to VocabEntry("g'ildirak simlari", "[spəʊks]", "noun", "B2"),
        "mud" to VocabEntry("loy", "[mʌd]", "noun", "A2"),
        "muddy" to VocabEntry("loy, loyga botgan", "[ˈmʌdi]", "adj", "A2"),
        "moor" to VocabEntry("yaylov, ochiq dasht", "[mʊər]", "noun", "B2"),
        "bog" to VocabEntry("botqoqlik", "[bɒɡ]", "noun", "B2"),
        "peat" to VocabEntry("torf", "[piːt]", "noun", "B2"),
        "heather" to VocabEntry("dala guli, buta", "[ˈheðər]", "noun", "B2"),
        "headmaster" to VocabEntry("maktab direktori", "[ˌhedˈmɑːstər]", "noun", "B1"),
        "dormitory" to VocabEntry("yotoqxona", "[ˈdɔːmɪtri]", "noun", "B1"),
        "kidnap" to VocabEntry("odam o'g'irlamoq", "[ˈkɪdnæp]", "verb", "B2"),
        "kidnapped" to VocabEntry("o'g'irlab ketilgan", "[ˈkɪdnæpt]", "adj/verb", "B2"),
        "kidnapper" to VocabEntry("odam o'g'risi", "[ˈkɪdnæpər]", "noun", "B2"),
        "ransom" to VocabEntry("to'lov puli, tovon", "[ˈrænsəm]", "noun", "B2"),
        "blacksmith" to VocabEntry("temirchi", "[ˈblæksmɪθ]", "noun", "B2"),
        "smithy" to VocabEntry("temirchilik ustaxonasi", "[ˈsmɪði]", "noun", "B2"),
        "forge" to VocabEntry("temir qizdirib yasamoq, soxtalashtirmoq", "[fɔːdʒ]", "verb", "B2"),
        "forged" to VocabEntry("soxtalashtirilgan, yasalgan", "[fɔːdʒd]", "adj", "B2"),
        "tavern" to VocabEntry("qovoqxona, mayxona", "[ˈtævən]", "noun", "B1"),
        "inn" to VocabEntry("mehmonxona, qovoqxona", "[ɪn]", "noun", "A2"),
        "duke" to VocabEntry("gertsog (oliy zodagon)", "[djuːk]", "noun", "B1"),
        "lord" to VocabEntry("lord, janob", "[lɔːd]", "noun", "B1"),
        "heir" to VocabEntry("merosxo'r", "[eər]", "noun", "B2"),
        "inheritance" to VocabEntry("meros", "[ɪnˈherɪtəns]", "noun", "B2"),
        "jealousy" to VocabEntry("rashk, hasad", "[ˈdʒeləsi]", "noun", "B2"),
        "jealous" to VocabEntry("hasadgo'y, rashkchi", "[ˈdʒeləs]", "adj", "B1"),
        "conspiracy" to VocabEntry("fitna, til biriktirish", "[kənˈspɪrəsi]", "noun", "B2"),
        "murder" to VocabEntry("qotillik, o'ldirmoq", "[ˈmɜːdər]", "noun/verb", "B1"),
        "murderer" to VocabEntry("qotil", "[ˈmɜːdərər]", "noun", "B1"),
        "handcuffs" to VocabEntry("kishan, qo'l kishani", "[ˈhændkʌfs]", "noun", "B2"),
        "cellar" to VocabEntry("yerto'la", "[ˈselər]", "noun", "B1"),
        "attic" to VocabEntry("chordoq", "[ˈætɪk]", "noun", "B1"),
        "elementary" to VocabEntry("oddiy, boshlang'ich", "[ˌelɪˈmentri]", "adj", "A2"),
        "deduction" to VocabEntry("mantiqiy xulosa chiqarish", "[dɪˈdʌkʃən]", "noun", "B2"),
        "magnifying" to VocabEntry("kattalashtiruvchi", "[ˈmæɡnɪfaɪɪŋ]", "adj", "B1"),

        // Elephant Man va dramatik asarlar
        "creature" to VocabEntry("mavjudot, jonzot", "[ˈkriːtʃər]", "noun", "B1"),
        "surgeon" to VocabEntry("jarroh", "[ˈsɜːdʒən]", "noun", "B1"),
        "hospital" to VocabEntry("shifoxona", "[ˈhɒspɪtl]", "noun", "A1"),
        "compassion" to VocabEntry("hamdardlik, mehr-shafqat", "[kəmˈpæʃən]", "noun", "B2"),
        "gentle" to VocabEntry("muloyim, mehribon", "[ˈdʒentl]", "adj", "A2"),
        "gentleness" to VocabEntry("muloyimlik, yuvoshlik", "[ˈdʒentlnəs]", "noun", "B1"),
        "dignity" to VocabEntry("qadr-qimmat, obro'", "[ˈdɪɡnəti]", "noun", "B2"),
        "grotesque" to VocabEntry("xunuk, beso'naqay", "[ɡrəʊˈtesk]", "adj", "B2"),
        "curiosity" to VocabEntry("qiziquvchanlik", "[ˌkjʊəriˈɒsəti]", "noun", "B1"),
        "curious" to VocabEntry("qiziquvchan", "[ˈkjʊəriəs]", "adj", "B1"),
        "astonishment" to VocabEntry("hayrat, lol qolish", "[əˈstɒnɪʃmənt]", "noun", "B2"),
        "astonished" to VocabEntry("hayratda qolgan", "[əˈstɒnɪʃt]", "adj", "B2"),
        "cloak" to VocabEntry("plashch, yopinchiq", "[kləʊk]", "noun", "B1"),
        "limp" to VocabEntry("oqsoqlanmoq", "[lɪmp]", "verb", "B2"),
        "limping" to VocabEntry("oqsoqlanib", "[ˈlɪmpɪŋ]", "verb/adj", "B2"),
        "tremble" to VocabEntry("titramoq, qaltiramoq", "[ˈtrembl]", "verb", "B1"),
        "trembled" to VocabEntry("titradi", "[ˈtrembld]", "verb", "B1"),
        "gratitude" to VocabEntry("minnatdorchilik", "[ˈɡrætɪtjuːd]", "noun", "B2"),
        "nightmare" to VocabEntry("dahshatli tush, qora kun", "[ˈnaɪtmeər]", "noun", "B1"),
        "circus" to VocabEntry("sirk", "[ˈsɜːkəs]", "noun", "A2"),
        "abandon" to VocabEntry("tashlab ketmoq, tark etmoq", "[əˈbændən]", "verb", "B2"),
        "abandoned" to VocabEntry("tashlab ketilgan, huvillagan", "[əˈbændənd]", "adj", "B2"),
        "sanctuary" to VocabEntry("panoh, xavfsiz maskan", "[ˈsæŋktʃuəri]", "noun", "B2"),
        "replica" to VocabEntry("aniq nusxa, maket", "[ˈreplɪkə]", "noun", "B2"),
        "cathedral" to VocabEntry("bosh ibodatxona, sobor", "[kəˈθiːdrəl]", "noun", "B1"),
        "princess" to VocabEntry("malika", "[prɪnˈses]", "noun", "A2"),
        "prince" to VocabEntry("shahzoda", "[prɪns]", "noun", "A2"),
        "cottage" to VocabEntry("shinam qishloq uyi", "[ˈkɒtɪdʒ]", "noun", "A2"),
        "meadow" to VocabEntry("o'tloq, maysazor", "[ˈmedəʊ]", "noun", "B1"),
        "meadows" to VocabEntry("o'tloqlar", "[ˈmedəʊz]", "noun", "B1"),
        "slumber" to VocabEntry("osuda uyqu", "[ˈslʌmbər]", "noun", "B2"),
        "serene" to VocabEntry("xotirjam, beg'ubor", "[səˈriːn]", "adj", "B2"),
        "whisper" to VocabEntry("pichirlamoq", "[ˈwɪspər]", "verb/noun", "B1"),
        "whispered" to VocabEntry("pichirladi", "[ˈwɪspəd]", "verb", "B1"),
        "poetry" to VocabEntry("she'riyat", "[ˈpəʊɪtri]", "noun", "B1"),
        "poem" to VocabEntry("she'r", "[ˈpəʊɪm]", "noun", "A2"),

        // Opera va teatr leksikasi (The Phantom of the Opera)
        "phantom" to VocabEntry("arvoh, sharpa", "[ˈfæntəm]", "noun", "B2"),
        "ghost" to VocabEntry("arvoh", "[ɡəʊst]", "noun", "A2"),
        "opera" to VocabEntry("opera teatri yoki san'ati", "[ˈɒprə]", "noun", "A2"),
        "theatre" to VocabEntry("teatr", "[ˈθɪətər]", "noun", "A1"),
        "stage" to VocabEntry("sahna", "[steɪdʒ]", "noun", "A2"),
        "curtain" to VocabEntry("parda", "[ˈkɜːtn]", "noun", "A2"),
        "curtains" to VocabEntry("pardalar", "[ˈkɜːtnz]", "noun", "A2"),
        "chandelier" to VocabEntry("hashamatli qandil", "[ˌʃændəˈlɪər]", "noun", "B2"),
        "mask" to VocabEntry("niqob", "[mɑːsk]", "noun", "A2"),
        "masked" to VocabEntry("niqoblangan", "[mɑːskt]", "adj", "A2"),
        "voice" to VocabEntry("ovoz", "[vɔɪs]", "noun", "A1"),
        "sing" to VocabEntry("kuylamoq", "[sɪŋ]", "verb", "A1"),
        "singer" to VocabEntry("qo'shiqchi, xonanda", "[ˈsɪŋər]", "noun", "A2"),
        "applause" to VocabEntry("olqishlar, qarsaklar", "[əˈplɔːz]", "noun", "B1"),
        "underground" to VocabEntry("yerosti, metro", "[ˈʌndəɡraʊnd]", "adj/noun", "A2"),
        "lake" to VocabEntry("ko'l", "[leɪk]", "noun", "A1"),
        "labyrinth" to VocabEntry("labirint, chigal yo'lak", "[ˈlæbərɪnθ]", "noun", "B2"),
        "passage" to VocabEntry("yo'lak, o'tish joyi", "[ˈpæsɪdʒ]", "noun", "B1"),
        "mirror" to VocabEntry("ko'zgu, oyna", "[ˈmɪrər]", "noun", "A2"),
        "shadow" to VocabEntry("soya, qorong'u sharpa", "[ˈʃædəʊ]", "noun", "A2"),
        "shadows" to VocabEntry("soyalar", "[ˈʃædəʊz]", "noun", "A2"),
        "candle" to VocabEntry("sham", "[ˈkændl]", "noun", "A2"),
        "candles" to VocabEntry("shamlar", "[ˈkændlz]", "noun", "A2"),
        "organ" to VocabEntry("organ (musiqa asbobi)", "[ˈɔːɡən]", "noun", "B1"),
        "melody" to VocabEntry("kuy, ohang", "[ˈmelədi]", "noun", "B1"),
        "composer" to VocabEntry("bastakor", "[kəmˈpəʊzər]", "noun", "B1"),
        "tragedy" to VocabEntry("fojia, musibat", "[ˈtrædʒədi]", "noun", "B1"),
        "jealous" to VocabEntry("hasadgo'y, rashkchi", "[ˈdʒeləs]", "adj", "B1"),
        "forgive" to VocabEntry("kechirmoq", "[fəˈɡɪv]", "verb", "A2"),
        "forgiveness" to VocabEntry("kechirim, afv", "[fəˈɡɪvnəs]", "noun", "B1"),
        "tear" to VocabEntry("ko'z yoshi", "[tɪər]", "noun", "A2"),
        "tears" to VocabEntry("ko'z yoshlari", "[tɪərz]", "noun", "A2"),
        "weep" to VocabEntry("yig'lamoq", "[wiːp]", "verb", "B2"),
        "sob" to VocabEntry("o'ksib yig'lamoq", "[sɒb]", "verb", "B2"),
        "sobbing" to VocabEntry("o'ksinib", "[ˈsɒbɪŋ]", "verb", "B2"),

        // Kundalik va umumiy so'zlar
        "morning" to VocabEntry("tong, ertalab", "[ˈmɔːnɪŋ]", "noun", "A1"),
        "evening" to VocabEntry("oqshom, kechqurun", "[ˈiːvnɪŋ]", "noun", "A1"),
        "night" to VocabEntry("tun, kecha", "[naɪt]", "noun", "A1"),
        "midnight" to VocabEntry("yarim tun", "[ˈmɪdnaɪt]", "noun", "A2"),
        "dawn" to VocabEntry("tong otishi, sahar", "[dɔːn]", "noun", "B1"),
        "dusk" to VocabEntry("shom qorong'usi", "[dʌsk]", "noun", "B2"),
        "sun" to VocabEntry("quyosh", "[sʌn]", "noun", "A1"),
        "moon" to VocabEntry("oy", "[muːn]", "noun", "A1"),
        "star" to VocabEntry("yulduz", "[stɑːr]", "noun", "A1"),
        "stars" to VocabEntry("yulduzlar", "[stɑːz]", "noun", "A1"),
        "wind" to VocabEntry("shamol", "[wɪnd]", "noun", "A1"),
        "rain" to VocabEntry("yomg'ir", "[reɪn]", "noun", "A1"),
        "storm" to VocabEntry("bo'ron, to'fon", "[stɔːm]", "noun", "A2"),
        "thunder" to VocabEntry("momaqaldiroq", "[ˈθʌndər]", "noun", "B1"),
        "lightning" to VocabEntry("chaqmoq", "[ˈlaɪtnɪŋ]", "noun", "B1"),
        "friend" to VocabEntry("do'st", "[frend]", "noun", "A1"),
        "friends" to VocabEntry("do'stlar", "[frendz]", "noun", "A1"),
        "friendship" to VocabEntry("do'stlik", "[ˈfrendʃɪp]", "noun", "A2"),
        "enemy" to VocabEntry("dushman", "[ˈenəmi]", "noun", "A2"),
        "stranger" to VocabEntry("begona kishi", "[ˈstreɪndʒər]", "noun", "B1"),
        "crowd" to VocabEntry("olomon, olomon to'dasi", "[kraʊd]", "noun", "A2"),
        "crowded" to VocabEntry("gavjum, tiqilinch", "[ˈkraʊdɪd]", "adj", "A2"),
        "silence" to VocabEntry("jimjitlik, sukunat", "[ˈsaɪləns]", "noun", "B1"),
        "silent" to VocabEntry("sokin, jim", "[ˈsaɪlənt]", "adj", "A2"),
        "shout" to VocabEntry("baqirmoq, qichqirmoq", "[ʃaʊt]", "verb/noun", "A2"),
        "shouted" to VocabEntry("baqirdi", "[ʃaʊtɪd]", "verb", "A2"),
        "scream" to VocabEntry("chinqirmoq", "[skriːm]", "verb/noun", "B1"),
        "laugh" to VocabEntry("kulmoq", "[lɑːf]", "verb/noun", "A1"),
        "smile" to VocabEntry("jilmaymoq", "[smaɪl]", "verb/noun", "A1"),
        "smiled" to VocabEntry("jilmaydi", "[smaɪld]", "verb", "A1"),
        "nod" to VocabEntry("bosh irg'amoq (rozilik)", "[nɒd]", "verb", "B1"),
        "nodded" to VocabEntry("bosh irg'adi", "[ˈnɒdɪd]", "verb", "B1"),
        "shake" to VocabEntry("silkitmoq, qo'l berib ko'rishmoq", "[ʃeɪk]", "verb", "A2"),
        "glance" to VocabEntry("nazar tashlamoq, qarab qo'ymoq", "[ɡlɑːns]", "verb/noun", "B1"),
        "stare" to VocabEntry("tikilib qaramoq", "[steər]", "verb", "B1"),
        "gaze" to VocabEntry("tikilib turmoq, hayratla qaramoq", "[ɡeɪz]", "verb/noun", "B2"),
        "peer" to VocabEntry("ko'z tikib qaramoq", "[pɪər]", "verb", "B2"),
        "sudden" to VocabEntry("to'satdan bo'lgan", "[ˈsʌdn]", "adj", "A2"),
        "suddenly" to VocabEntry("to'satdan, birdaniga", "[ˈsʌdənli]", "adv", "A2"),
        "quick" to VocabEntry("tez, chaqqon", "[kwɪk]", "adj", "A1"),
        "quickly" to VocabEntry("tezda, darhol", "[ˈkwɪkli]", "adv", "A1"),
        "slow" to VocabEntry("sekin", "[sləʊ]", "adj", "A1"),
        "slowly" to VocabEntry("sekinlik bilan", "[ˈsləʊli]", "adv", "A1"),
        "careful" to VocabEntry("ehtiyotkor, hushyor", "[ˈkeəfl]", "adj", "A2"),
        "carefully" to VocabEntry("ehtiyotkorlik bilan", "[ˈkeəfəli]", "adv", "A2"),
        "heavy" to VocabEntry("og'ir", "[ˈhevi]", "adj", "A1"),
        "light" to VocabEntry("yengil, yorug'", "[laɪt]", "adj/noun", "A1"),
        "dark" to VocabEntry("qorong'u", "[dɑːk]", "adj", "A1"),
        "darkness" to VocabEntry("qorong'ilik", "[ˈdɑːknəs]", "noun", "B1"),
        "deep" to VocabEntry("chuqur", "[diːp]", "adj", "A2"),
        "shallow" to VocabEntry("sayoz", "[ˈʃæləʊ]", "adj", "B1"),
        "narrow" to VocabEntry("tor", "[ˈnærəʊ]", "adj", "A2"),
        "wide" to VocabEntry("keng", "[waɪd]", "adj", "A2"),
        "steep" to VocabEntry("tik, qiyalik", "[stiːp]", "adj", "B1"),
        "flat" to VocabEntry("tekis", "[flæt]", "adj", "A2"),
        "rough" to VocabEntry("g'adir-budur, qattiq", "[rʌf]", "adj", "B1"),
        "smooth" to VocabEntry("silliq", "[smuːð]", "adj", "A2"),
        "soft" to VocabEntry("yumshoq, mayin", "[sɒft]", "adj", "A1"),
        "hard" to VocabEntry("qattiq, qiyin", "[hɑːd]", "adj/adv", "A1"),
        "sharp" to VocabEntry("o'tkir", "[ʃɑːp]", "adj", "B1"),
        "cold" to VocabEntry("sovuq", "[kəʊld]", "adj", "A1"),
        "warm" to VocabEntry("iliq", "[wɔːm]", "adj", "A1"),
        "hot" to VocabEntry("issiq", "[hɒt]", "adj", "A1"),
        "burning" to VocabEntry("yonayotgan, jazirama", "[ˈbɜːnɪŋ]", "adj", "B1"),
        "freezing" to VocabEntry("muzdek, ayoz", "[ˈfriːzɪŋ]", "adj", "A2"),
        "alive" to VocabEntry("tirik", "[əˈlaɪv]", "adj", "A2"),
        "dead" to VocabEntry("o'lik, vafot etgan", "[ded]", "adj", "A2"),
        "die" to VocabEntry("vafot etmoq", "[daɪ]", "verb", "A2"),
        "died" to VocabEntry("vafot etdi", "[daɪd]", "verb", "A2"),
        "death" to VocabEntry("o'lim", "[deθ]", "noun", "A2"),
        "life" to VocabEntry("hayot", "[laɪf]", "noun", "A1"),
        "heart" to VocabEntry("yurak, qalb", "[hɑːt]", "noun", "A1"),
        "mind" to VocabEntry("aql, fikr", "[maɪnd]", "noun", "A2"),
        "soul" to VocabEntry("ruh, qalb", "[səʊl]", "noun", "B1"),
        "spirit" to VocabEntry("ruh, shijoat", "[ˈspɪrɪt]", "noun", "B1"),
        "body" to VocabEntry("tana, vujud", "[ˈbɒdi]", "noun", "A1"),
        "head" to VocabEntry("bosh, rahbar", "[hed]", "noun", "A1"),
        "hand" to VocabEntry("qo'l", "[hænd]", "noun", "A1"),
        "hands" to VocabEntry("qo'llar", "[hændz]", "noun", "A1"),
        "foot" to VocabEntry("oyoq (panja)", "[fʊt]", "noun", "A1"),
        "feet" to VocabEntry("oyoqlar", "[fiːt]", "noun", "A1"),
        "eye" to VocabEntry("ko'z", "[aɪ]", "noun", "A1"),
        "eyes" to VocabEntry("ko'zlar", "[aɪz]", "noun", "A1"),
        "ear" to VocabEntry("quloq", "[ɪər]", "noun", "A1"),
        "ears" to VocabEntry("quloqlar", "[ɪərz]", "noun", "A1"),
        "face" to VocabEntry("yuz, chehra", "[feɪs]", "noun", "A1"),
        "mouth" to VocabEntry("og'iz", "[maʊθ]", "noun", "A1"),
        "tooth" to VocabEntry("tish", "[tuːθ]", "noun", "A1"),
        "teeth" to VocabEntry("tishlar", "[tiːθ]", "noun", "A1"),
        "bone" to VocabEntry("suyak", "[bəʊn]", "noun", "A2"),
        "bones" to VocabEntry("suyaklar", "[bəʊnz]", "noun", "A2"),
        "skin" to VocabEntry("teri", "[skɪn]", "noun", "A2"),
        "blood" to VocabEntry("qon", "[blʌd]", "noun", "A2"),
        "tear" to VocabEntry("yirtmoq, ko'z yoshi", "[teər / tɪər]", "verb/noun", "A2"),
        "sound" to VocabEntry("tovush, ovoz", "[saʊnd]", "noun", "A1"),
        "noise" to VocabEntry("shovqin", "[nɔɪz]", "noun", "A2"),
        "shout" to VocabEntry("baqiriq", "[ʃaʊt]", "noun", "A2"),
        "listen" to VocabEntry("tinglamoq", "[ˈlɪsn]", "verb", "A1"),
        "hear" to VocabEntry("eshitmoq", "[hɪər]", "verb", "A1"),
        "heard" to VocabEntry("eshitdi", "[hɜːd]", "verb", "A1"),
        "look" to VocabEntry("qaramoq", "[lʊk]", "verb", "A1"),
        "looked" to VocabEntry("qaradi", "[lʊkt]", "verb", "A1"),
        "watch" to VocabEntry("tomosha qilmoq, kuzatmoq", "[wɒtʃ]", "verb", "A1"),
        "watched" to VocabEntry("kuzatdi", "[wɒtʃt]", "verb", "A1"),
        "feel" to VocabEntry("his qilmoq", "[fiːl]", "verb", "A1"),
        "touch" to VocabEntry("tegmoq, ushlamoq", "[tʌtʃ]", "verb/noun", "A2"),
        "smell" to VocabEntry("hidlamoq, hid", "[smel]", "verb/noun", "A2"),
        "taste" to VocabEntry("ta'tib ko'rmoq, ta'm", "[teɪst]", "verb/noun", "A2")
    )
}
