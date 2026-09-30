package com.example.data.util

import android.content.Context
import com.example.data.remote.GeminiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Locale

data class SpeakingWordDetail(
    val word: String,
    val translation: String,
    val phonetic: String,
    val partOfSpeech: String,
    val level: String,
    val usageRules: String,
    val dialogueExample: String,
    val dialogueTranslation: String,
    val synonyms: String,
    val spokenTip: String,
    val isAiEnhanced: Boolean = false
)

object SpeakingWordInspectorEngine {

    /**
     * Tezkor offline tahlil: UniversalDictionary + Oxford3000 + Morfologik qoidalar
     */
    fun getInstantOfflineDetail(
        rawWord: String,
        contextSentence: String = "",
        context: Context? = null
    ): SpeakingWordDetail {
        val cleanWord = rawWord.replace(Regex("[^a-zA-Z]"), "").trim()
        val lower = cleanWord.lowercase(Locale.ROOT)

        val localMatch = UniversalDictionary.lookup(cleanWord, context)
            ?: Oxford3000Database.findWord(cleanWord, context)?.let {
                WordLookupResult(
                    word = cleanWord,
                    translationUz = it.uz,
                    phonetic = it.phonetic.ifBlank { "/$lower/" },
                    partOfSpeech = it.pos.ifBlank { "vocabulary" },
                    level = it.level.ifBlank { "B1" },
                    exampleSentence = it.example,
                    exampleTranslation = it.uzExample
                )
            }

        val translation = localMatch?.translationUz ?: deriveIntelligentTranslation(lower, contextSentence)
        val phonetic = localMatch?.phonetic?.ifBlank { "/$lower/" } ?: "/$lower/"
        val pos = localMatch?.partOfSpeech?.ifBlank { derivePartOfSpeech(lower) } ?: derivePartOfSpeech(lower)
        val level = localMatch?.level?.ifBlank { "B1" } ?: "B1"

        val example = if (contextSentence.isNotBlank()) {
            contextSentence.trim()
        } else if (localMatch?.exampleSentence?.isNotBlank() == true) {
            localMatch.exampleSentence
        } else {
            "Always use '$cleanWord' with natural rhythm in your speech."
        }

        val exampleTrans = if (localMatch?.exampleTranslation?.isNotBlank() == true) {
            localMatch.exampleTranslation
        } else {
            "Ushbu jumla og'zaki nutqda tez-tez ishlatiladi."
        }

        val usage = deriveUsageRules(lower, pos)
        val syns = deriveSynonyms(lower)
        val tip = deriveSpokenTip(lower, pos)

        return SpeakingWordDetail(
            word = cleanWord,
            translation = translation,
            phonetic = phonetic,
            partOfSpeech = pos,
            level = level,
            usageRules = usage,
            dialogueExample = example,
            dialogueTranslation = exampleTrans,
            synonyms = syns,
            spokenTip = tip,
            isAiEnhanced = false
        )
    }

    /**
     * Gemini AI orqali chuqurlashtirilgan tahlil (onlayn rejimda)
     */
    suspend fun getDeepAiAnalysis(
        word: String,
        contextSentence: String,
        context: Context? = null
    ): SpeakingWordDetail = withContext(Dispatchers.IO) {
        val offline = getInstantOfflineDetail(word, contextSentence, context)
        val cleanWord = word.replace(Regex("[^a-zA-Z]"), "").trim()

        val prompt = """
            You are an expert English Speaking & Pronunciation Coach for Uzbek students.
            Analyze this spoken English word in detail:
            Word: "$cleanWord"
            Spoken Dialogue Context: "$contextSentence"
            
            Return ONLY a valid JSON object with EXACTLY these keys:
            {
              "word": "$cleanWord",
              "translation": "Aniq va boy o'zbekcha tarjimasi",
              "phonetic": "[IPA transkripsiyasi, masalan /ˈwɔːkɪŋ/]",
              "partOfSpeech": "So'z turkumi (Masalan: Fe'l / Ot / Sifat)",
              "level": "A1/A2/B1/B2/C1",
              "usageRules": "Qanday qo'llaniladi: grammatik birikmasi, predloglari (masalan: depend ON, talk TO) va gapdagi o'rni",
              "dialogueExample": "Og'zaki nutqda ishlatiladigan real jonli gap",
              "dialogueTranslation": "Ushbu dialog gapining aniq o'zbekcha tarjimasi",
              "synonyms": "Nutqni boyitish uchun 2-3 ta sinonim yoki zamonaviy ibora",
              "spokenTip": "Og'zaki talaffuz, urg'u (stress) yoki ohang (intonatsiya) bo'yicha maxsus sir/tavsiya"
            }
        """.trimIndent()

        try {
            val responseResult = GeminiClient.generateText(prompt, context)
            val raw = responseResult.getOrNull() ?: ""
            val jsonStart = raw.indexOf('{')
            val jsonEnd = raw.lastIndexOf('}')

            if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
                val jsonStr = raw.substring(jsonStart, jsonEnd + 1)
                val obj = JSONObject(jsonStr)

                val trans = obj.optString("translation", "").trim()
                if (trans.isNotBlank()) {
                    return@withContext SpeakingWordDetail(
                        word = obj.optString("word", cleanWord),
                        translation = trans,
                        phonetic = obj.optString("phonetic", offline.phonetic),
                        partOfSpeech = obj.optString("partOfSpeech", offline.partOfSpeech),
                        level = obj.optString("level", offline.level),
                        usageRules = obj.optString("usageRules", offline.usageRules),
                        dialogueExample = obj.optString("dialogueExample", offline.dialogueExample),
                        dialogueTranslation = obj.optString("dialogueTranslation", offline.dialogueTranslation),
                        synonyms = obj.optString("synonyms", offline.synonyms),
                        spokenTip = obj.optString("spokenTip", offline.spokenTip),
                        isAiEnhanced = true
                    )
                }
            }
        } catch (e: Exception) {
            // fallback to offline
        }

        offline
    }

    private fun deriveUsageRules(word: String, pos: String): String {
        return when {
            pos.contains("verb", ignoreCase = true) -> {
                "Fe'l sifatida ishlatiladi. Doimiy odatlar uchun 'I $word...', o'tgan zamonda '-ed' yoki noto'g'ri shakli qo'llanadi. Nutqda harakat va reaksiyani tez ifodalash uchun xizmat qiladi."
            }
            pos.contains("adjective", ignoreCase = true) -> {
                "Sifat vazifasida keladi. Otlardan oldin (masalan, '$word problem') yoki 'to be' fe'lidan keyin ('It is $word') holatni ifodalashda qo'llaniladi."
            }
            pos.contains("noun", ignoreCase = true) -> {
                "Ot so'z turkumi. Gapda ega yoki to'ldiruvchi bo'lib keladi. Birlikda 'a/an $word', ko'plikda 's' qo'shimchasi bilan ishlatiladi."
            }
            else -> {
                "Og'zaki muloqotda fikrni aniq va ta'sirchan ifodalash uchun faol qo'llanuvchi birlik."
            }
        }
    }

    private fun deriveSynonyms(word: String): String {
        val dict = mapOf(
            "help" to "assist, support, aid",
            "listen" to "hear, pay attention, tune in",
            "speak" to "talk, communicate, converse",
            "safe" to "secure, protected, unharmed",
            "danger" to "hazard, threat, risk",
            "wire" to "cable, line, cord",
            "cut" to "sever, slice, trim",
            "calm" to "peaceful, relaxed, serene",
            "money" to "funds, cash, capital",
            "fast" to "quick, rapid, swift",
            "learn" to "study, acquire, master",
            "secret" to "confidential, hidden, private",
            "invest" to "finance, fund, back",
            "promise" to "pledge, guarantee, vow",
            "truth" to "fact, reality, honesty",
            "strong" to "powerful, resilient, tough"
        )
        return dict[word.lowercase(Locale.ROOT)] ?: "similar word, natural alternative"
    }

    private fun deriveSpokenTip(word: String, pos: String): String {
        return when {
            word.length >= 7 -> "Ko'p bo'g'inli so'z: asosiy urg'uni (stress) to'g'ri bo'g'inga bering va shoshilmasdan erkin talaffuz qiling."
            word.endsWith("ed") -> "'-ed' qo'shimchasini haddan tashqari cho'zmang, jarangli undoshlardan so'ng /d/, jarangsizlardan so'ng /t/ deb qisqa ayting."
            word.endsWith("ing") -> "'g' tovushini qattiq aytmang, burun tovushi /ŋ/ sifatida yumshoq chiqaring."
            else -> "Ushbu so'zni jumlada aytayotganda oldingi so'z bilan ravon ulab (linking) ayting."
        }
    }

    private fun derivePartOfSpeech(word: String): String {
        return when {
            word.endsWith("ly") -> "Ravish (Adverb)"
            word.endsWith("tion") || word.endsWith("ment") || word.endsWith("ness") -> "Ot (Noun)"
            word.endsWith("ful") || word.endsWith("ive") || word.endsWith("ous") || word.endsWith("able") -> "Sifat (Adjective)"
            word.endsWith("ed") || word.endsWith("ing") -> "Fe'l (Verb)"
            else -> "So'z (Vocabulary)"
        }
    }

    private fun deriveIntelligentTranslation(word: String, contextSentence: String = ""): String {
        val w = word.lowercase(Locale.ROOT).trim()

        // 1. Keng qamrovli leksik xarita (O'yinlar, muloqot, texnologiya, psixologiya va biznes)
        val termDictionary = mapOf(
            // Tactical & EOD terms
            "tactical" to "taktik, operativ, harbiy reja bo'yicha",
            "device" to "qurilma, asbob, mexanizm",
            "detonator" to "detonator, portlatish moslamasi",
            "schematic" to "chizma, sxema, elektron reja",
            "schematics" to "elektron sxemalar, chizmalar",
            "override" to "bekor qilmoq, tizimni chetlab o'tib boshqarmoq",
            "bypass" to "aylanib o'tmoq, chetlab o'tish yo'li",
            "auxiliary" to "qo'shimcha, yordamchi, zaxira",
            "relay" to "rele, elektr uzatgich moslamasi",
            "isolate" to "ajratmoq, alohida qilmoq, yakkalamoq",
            "secondary" to "ikkilamchi, yordamchi, navbatdagi",
            "fuse" to "saqlagich (predoxranitel), yoqish ipi",
            "circuit" to "elektron zanjir, elektr sxemasi",
            "capacitor" to "kondensator, quvvat to'plovchi qism",
            "oscillator" to "ossillyator, tebranish hosil qiluvchi qurilma",
            "frequency" to "chastota, to'lqin takrorlanish tezligi",
            "scrambler" to "to'lqin chalkashtiruvchi qurilma (shifrator)",
            "stabilization" to "barqarorlashtirish, barqaror holatga keltirish",
            "quantum" to "kvant, eng mayda energiya zarrachasi",
            "telemetry" to "telemetriya, masofaviy o'lchov ma'lumotlari",
            "neutralization" to "zararsizlantirish, neytrallash",
            "defuse" to "zararsizlantirmoq, portlash xavfini bartaraf etmoq",
            "defused" to "zararsizlantirildi, xavfi olingan",
            "counter" to "qarshi harakat qilmoq, to'sqinlik qilmoq",
            "ground" to "yerga ulamoq (zazemleniye), asos solmoq",
            "sever" to "kesib uzmoq, bog'liqlikni yo'qotmoq",
            "severing" to "kesib uzish, uzib tashlash harakati",
            "voltage" to "voltaj, elektr kuchlanishi",
            "microcontroller" to "mikronazoratchi chip, kichik protsessor",
            "megahertz" to "megagerts (chastota o'lchov birligi)",
            "sensor" to "datchik, sezgich moslama",
            "sensors" to "datchiklar, o'lchash sezgichlari",
            "wire" to "sim, elektr o'tkazgich",
            "wires" to "simlar, elektr kabellari",

            // Mafia & Negotiation terms
            "negotiator" to "muzokara olib boruvchi vakil",
            "negotiation" to "muzokara, kelishuv jarayoni",
            "syndicate" to "sindikat, yirik jinoiy guruh",
            "leverage" to "ustunlik dastagi, ta'sir o'tkazish kuchi",
            "perimeter" to "atrof himoya chegarasi, perimetr",
            "handover" to "topshirish, xavfsiz o'tkazish jarayoni",
            "collateral" to "garov ta'minoti, garovdagi vosita",
            "surveillance" to "kuzatuv, yashirin video nazorat",
            "protocol" to "qoida, rasmiy protokol tartibi",
            "judicial" to "sud-huquqiy, adolat tizimiga oid",
            "counsel" to "advokat maslahati, huquqiy himoya",
            "sentencing" to "sud hukmi, jazo tayinlash jarayoni",
            "bargaining" to "savdolashish, yon berish muzokarasi",
            "extraction" to "evakuatsiya, xavfli joydan olib chiqish",
            "corridor" to "koridor, xavfsiz o'tish yo'lagi",
            "compromise" to "murosaga kelmoq, xavf ostida qolmoq",
            "compromised" to "fosh bo'lgan, xavf ostida qolgan",
            "hostage" to "garovdagi shaxs",
            "hostages" to "garovga olingan odamlar",
            "surrender" to "taslim bo'lmoq, qurolni topshirmoq",
            "stand down" to "orqaga chekinmoq, to'xtatmoq",
            "snipers" to "snayperlar, uzoqdan nishonga oluvchilar",
            "purge" to "tozalash, yo'q qilish, butunlay o'chirish",
            "dispatched" to "jo'natilgan, yuborilgan",
            "combatants" to "jangchilar, qurolli ishtirokchilar",
            "non-combatants" to "qurolsiz tinch fuqarolar",

            // Startup & Business / Investor terms
            "pitch" to "qisqa biznes taqdimoti, loyiha nutqi",
            "retention" to "mijozlarni saqlab qolish darajasi",
            "churn" to "mijozlarning ketib qolish foizi",
            "acquisition" to "mijoz jalb qilish, sotib olish",
            "proprietary" to "shaxsiy patentlangan, mualliflikka tegishli",
            "moat" to "raqobatchilardan himoya to'sig'i",
            "patent" to "patent, rasmiy ixtiro huquqi",
            "patents" to "patentlar, ro'yxatdan o'tgan intellektual mulk",
            "recurring" to "muntazam takrorlanuvchi, oylik/yillik",
            "revenue" to "tushum, yalpi daromad",
            "equity" to "kompaniyadagi ulush, egalik foizi",
            "warrants" to "aksiyalarni imtiyozli sotib olish huquqi",
            "advisory" to "maslahatchilikka oid",
            "punitive" to "o'ta og'ir, jazolovchi, adolatsiz yuqori",
            "counter" to "qarshi taklif bermoq",
            "investor" to "investor, sarmoyador",
            "invest" to "sarmoya kiritmoq",

            // Lie Detector & Forensic terms
            "galvanic" to "teri elektr o'tkazuvchanligiga oid",
            "biometric" to "biometrik, inson tanasi ma'lumotlariga oid",
            "dilation" to "kengayish, qorachiqning kattalashishi",
            "pupillary" to "ko'z qorachig'iga oid",
            "friction" to "qarshilik, ichki zo'riqish, ishqalanish",
            "duress" to "tazyiq, majburlash, bosim ostida qolish",
            "deception" to "aldov, yolg'on, yashirish harakati",
            "premeditation" to "qasddan, oldindan o'ylab rejalashtirish",
            "telemetry" to "telemetrik o'lchovlar",
            "anomaly" to "anomaliya, me'yordan og'ish, noodatiy holat",
            "passphrase" to "maxfiy kalit jumla, shifrlash kodi",
            "interrogation" to "tergov, rasmiy so'roq qilish jarayoni",
            "polygraph" to "poligraf, yolg'onni aniqlovchi detektor",

            // General advanced communication terms
            "fascinating" to "juda maftunkor, qiziqarli",
            "consciousness" to "ong, insoniy tushuncha va his",
            "autonomous" to "avtonom, mustaqil ishlaydigan",
            "multimodal" to "ko'p yo'nalishli (matn, ovoz, tasvir)",
            "algorithm" to "algoritm, aniq ketma-ketlikdagi qoida",
            "algorithmic" to "algoritmik, dasturiy mantiq asosidagi",
            "clarify" to "oydinlik kiritmoq, aniqlashtirmoq",
            "perspective" to "nuqtai nazar, dunyoqarash",
            "distinguish" to "farqlamoq, ajrata olmoq",
            "genuine" to "haqiqiy, soxta bo'lmagan, samimiy"
        )

        val directMatch = termDictionary[w]
        if (directMatch != null) return directMatch

        // 2. Prefiks va suffiks morfologik tahlili (Decompounding engine)
        val prefixes = listOf(
            "un" to "inkor (no- / g'ayri-)",
            "re" to "qaytadan",
            "dis" to "inkor / ajratish",
            "mis" to "noto'g'ri",
            "pre" to "oldindan",
            "over" to "haddan tashqari",
            "under" to "yetarli bo'lmagan",
            "multi" to "ko'p yo'nalishli",
            "sub" to "osti / ikkilamchi",
            "inter" to "o'zaro / aro",
            "anti" to "qarshi"
        )

        for ((pfx, pfxDesc) in prefixes) {
            if (w.startsWith(pfx) && w.length > pfx.length + 3) {
                val stem = w.substring(pfx.length)
                val stemUz = termDictionary[stem]
                    ?: Oxford3000Database.findWord(stem)?.uz
                    ?: UniversalDictionary.lookup(stem, null)?.translationUz

                if (stemUz != null) {
                    return "$pfxDesc $stemUz ('$stem' o'zagidan)"
                }
            }
        }

        val suffixes = listOf(
            "tion" to "jarayoni / harakati",
            "ment" to "holati / natijasi",
            "ness" to "xususiyati / darajasi",
            "able" to "qilish mumkin bo'lgan",
            "ible" to "bajarilishi mumkin bo'lgan",
            "less" to "siz / mavjud bo'lmagan",
            "ful" to "bilan to'la / sifatli",
            "ly" to "tarzda / ravishda",
            "ize" to "lashtirmoq",
            "ise" to "lashtirmoq",
            "or" to "bajaruvchi shaxs yoki uskuna",
            "er" to "bajaruvchi shaxs yoki uskuna"
        )

        for ((sfx, sfxDesc) in suffixes) {
            if (w.endsWith(sfx) && w.length > sfx.length + 3) {
                val stem = w.removeSuffix(sfx)
                val stemUz = termDictionary[stem]
                    ?: Oxford3000Database.findWord(stem)?.uz
                    ?: UniversalDictionary.lookup(stem, null)?.translationUz

                if (stemUz != null) {
                    return "$stemUz (${sfxDesc})"
                }
            }
        }

        // 3. Kontekstual aniq tavsif (hech qachon umumiy placeholder bermaydi)
        val pos = derivePartOfSpeech(w)
        return when {
            pos.contains("Fe'l") -> "'$word' — harakat yoki holatni ifodalovchi fe'l"
            pos.contains("Ot") -> "'$word' — tushuncha yoki predmetni bildiruvchi ot"
            pos.contains("Sifat") -> "'$word' — belgi va sifatni ifodalovchi so'z"
            pos.contains("Ravish") -> "'$word' — harakatning qay tarzda bajarilishini bildiruvchi ravish"
            else -> "'$word' — jonli nutqda faol qo'llanuvchi inglizcha leksik birlik"
        }
    }
}
