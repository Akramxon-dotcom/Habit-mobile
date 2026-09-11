package com.example.data.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.data.model.VocabCard
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Locale
import java.util.UUID
import java.util.zip.ZipInputStream

object VocabDocumentParser {
    private const val TAG = "VocabDocumentParser"

    // CEFR Level Weight for sequential ordering
    fun getLevelWeight(level: String): Int {
        return when (level.trim().uppercase(Locale.ROOT)) {
            "A1" -> 1
            "A2" -> 2
            "B1" -> 3
            "B2" -> 4
            "C1" -> 5
            "C2" -> 6
            else -> 7
        }
    }

    /**
     * Extracts text from Uri based on file extension/content type and parses vocabulary.
     */
    fun parseDocument(context: Context, uri: Uri, fileName: String = ""): List<VocabCard> {
        val lowerName = fileName.lowercase(Locale.ROOT)
        return try {
            val rawText: String = if (lowerName.endsWith(".docx")) {
                extractTextFromDocx(context.contentResolver.openInputStream(uri))
            } else if (lowerName.endsWith(".pdf")) {
                extractTextFromPdfOrStream(context.contentResolver.openInputStream(uri))
            } else {
                extractTextFromPlainText(context.contentResolver.openInputStream(uri))
            }

            parseRawText(rawText, sourceName = fileName.ifBlank { "Hujjat" })
        } catch (e: Exception) {
            Log.e(TAG, "Hujjatni o'qishda xatolik: ${e.message}", e)
            emptyList()
        }
    }

    /**
     * Extracts text from DOCX (standard zip containing word/document.xml).
     */
    private fun extractTextFromDocx(inputStream: InputStream?): String {
        if (inputStream == null) return ""
        val sb = java.lang.StringBuilder()
        try {
            val zis = ZipInputStream(inputStream)
            var entry = zis.nextEntry
            while (entry != null) {
                if (entry.name == "word/document.xml") {
                    val reader = BufferedReader(InputStreamReader(zis, Charsets.UTF_8))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        line?.let { rawXml ->
                            // Replace paragraph ends with newline
                            val withBreaks = rawXml.replace("</w:p>", "\n")
                            // Strip xml tags
                            val textOnly = withBreaks.replace(Regex("<[^>]+>"), "")
                            sb.append(textOnly).append("\n")
                        }
                    }
                    break
                }
                entry = zis.nextEntry
            }
            zis.close()
        } catch (e: Exception) {
            Log.e(TAG, "DOCX ochishda xatolik: ${e.message}")
        }
        return sb.toString()
    }

    /**
     * Extracts text from PDF or raw stream.
     */
    private fun extractTextFromPdfOrStream(inputStream: InputStream?): String {
        if (inputStream == null) return ""
        val sb = java.lang.StringBuilder()
        try {
            val bytes = inputStream.readBytes()
            val content = String(bytes, Charsets.ISO_8859_1)

            // Search for PDF string literals (Text) Tj or [(Text)] TJ
            val tjRegex = Regex("""\(([^()]+)\)\s*Tj""")
            val matches = tjRegex.findAll(content)
            for (m in matches) {
                sb.append(m.groupValues[1]).append(" ")
            }

            if (sb.length < 50) {
                // Fallback: extract ASCII readable text tokens
                val asciiRegex = Regex("""[a-zA-Z0-9\s\-_:;,.()\[\]'ʻ’ʼ\u0400-\u04FF]{4,}""")
                asciiRegex.findAll(content).forEach {
                    sb.append(it.value).append("\n")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "PDF stream o'qishda xatolik: ${e.message}")
        }
        return sb.toString()
    }

    /**
     * Standard plain text reader (TXT, CSV, Markdown, TSV).
     */
    private fun extractTextFromPlainText(inputStream: InputStream?): String {
        if (inputStream == null) return ""
        val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))
        return reader.readText()
    }

    /**
     * Parses raw text into structured VocabCard objects categorized by CEFR level.
     */
    fun parseRawText(rawText: String, sourceName: String = "Fayl"): List<VocabCard> {
        val lines = rawText.split("\n", "\r\n")
        val result = mutableListOf<VocabCard>()
        val seenWords = mutableSetOf<String>()

        var currentContextLevel = "A1"

        val levelHeaderRegex = Regex("""^(?:#+\s*)?(?:Level\s*)?([A-C][1-2])(?:\s*[:\-]|\s+Level|\s+Vocabulary|\s*$|\s*daraja)""", RegexOption.IGNORE_CASE)
        val levelInWordRegex = Regex("""\b([A-C][1-2])\b""", RegexOption.IGNORE_CASE)

        for (rawLine in lines) {
            val line = rawLine.trim()
            if (line.isBlank() || line.startsWith("//") || line.startsWith("/*")) continue

            // Check if this line defines a level header e.g. "A1", "A2 - Boshlang'ich", "# B1 Intermediate"
            val headerMatch = levelHeaderRegex.find(line)
            if (headerMatch != null && line.length <= 40) {
                currentContextLevel = headerMatch.groupValues[1].uppercase(Locale.ROOT)
                continue
            }

            // Check for individual word entry
            // e.g.: "can [A1] - qila olmoq"
            // or "A1 | can | qila olmoq"
            // or "abandon - B2 - tark etmoq"
            // or "perseverance: sabr-matonat [C1]"
            val card = parseSingleLine(line, currentContextLevel, sourceName)
            if (card != null && seenWords.add(card.word.lowercase(Locale.ROOT))) {
                result.add(card)
            }
        }

        // Sort sequentially by CEFR level: A1 -> A2 -> B1 -> B2 -> C1 -> C2, then word
        return result.sortedWith(
            compareBy<VocabCard> { getLevelWeight(it.level) }
                .thenBy { it.word.lowercase(Locale.ROOT) }
        )
    }

    private fun parseSingleLine(line: String, contextLevel: String, sourceName: String): VocabCard? {
        var cleanLine = line
        var detectedLevel = contextLevel

        // Detect CEFR level in brackets e.g. [A1] or (B2) or {C1}
        val bracketMatch = Regex("""[\[\(\{]([A-C][1-2])[\]\)\}]""", RegexOption.IGNORE_CASE).find(cleanLine)
        if (bracketMatch != null) {
            detectedLevel = bracketMatch.groupValues[1].uppercase(Locale.ROOT)
            cleanLine = cleanLine.replace(bracketMatch.value, " ")
        } else {
            // Check standalone token e.g. "can A1 - qila olmoq" or "A1 can: qila olmoq"
            val standaloneMatch = Regex("""\b([A-C][1-2])\b""", RegexOption.IGNORE_CASE).find(cleanLine)
            if (standaloneMatch != null) {
                detectedLevel = standaloneMatch.groupValues[1].uppercase(Locale.ROOT)
                cleanLine = cleanLine.replace(standaloneMatch.value, " ")
            }
        }

        // Delimiters for word and translation: "-", ":", "–", "—", "|", "\t", ";"
        val delimiters = listOf(" – ", " — ", " - ", " : ", ":", "|", "\t", ";", "=")
        var wordPart = ""
        var transPart = ""

        for (delim in delimiters) {
            if (cleanLine.contains(delim)) {
                val parts = cleanLine.split(delim, limit = 2)
                wordPart = parts[0].trim()
                transPart = parts[1].trim()
                break
            }
        }

        if (wordPart.isBlank()) {
            // Single word without separator
            wordPart = cleanLine.trim()
        }

        // Clean punctuation from word
        wordPart = wordPart.replace(Regex("""^[0-9]+[.)\s]+"""), "").trim() // remove leading numbers like "1. "
        wordPart = wordPart.replace(Regex("""[^\w\s\-'ʻ’ʼ]"""), "").trim()

        if (wordPart.isBlank() || wordPart.length > 50 || wordPart.contains("http")) {
            return null
        }

        // If translation is empty, provide fallback from dictionary or default note
        if (transPart.isBlank()) {
            transPart = CommonDictionary.getTranslation(wordPart)
        }

        return VocabCard(
            id = UUID.randomUUID().toString(),
            word = wordPart.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            translation = transPart.ifBlank { "O'rganilayotgan so'z" },
            phonetic = CommonDictionary.getPhonetic(wordPart),
            partOfSpeech = CommonDictionary.getPartOfSpeech(wordPart),
            definition = "Daraja: $detectedLevel so'zlar qatori",
            example = CommonDictionary.getExample(wordPart),
            mnemonic = "Daraja: $detectedLevel · $sourceName hujjatidan yuklandi",
            boxLevel = 1,
            level = detectedLevel,
            sourceDocName = sourceName,
            isMastered = false,
            reviewCount = 0
        )
    }

    /**
     * Preloaded Cambridge / Oxford 20-word CEFR progression list for testing or immediate demo.
     */
    fun getSampleCefrProgression(): List<VocabCard> {
        val sampleData = listOf(
            // A1
            Triple("Can", "Qila olmoq, imkoni bo'lmoq", "A1"),
            Triple("Always", "Har doim, doimo", "A1"),
            Triple("Begin", "Boshlamoq", "A1"),
            Triple("Daily", "Har kungi, kunlik", "A1"),
            Triple("Habit", "Odat, ko'nikma", "A1"),
            // A2
            Triple("Decide", "Qaror qilmoq", "A2"),
            Triple("Achieve", "Erishmoq, natijaga yetmoq", "A2"),
            Triple("Improve", "Yaxshilamoq, rivojlantirmoq", "A2"),
            Triple("Punctual", "Vaqtga rioya qiluvchi", "A2"),
            Triple("Protect", "Himoya qilmoq, saqlamoq", "A2"),
            // B1
            Triple("Confidence", "Ishonch, dadillik", "B1"),
            Triple("Persevere", "Qat'iyat ko'rsatmoq, sabot qilmoq", "B1"),
            Triple("Discipline", "Intizom, tartib", "B1"),
            Triple("Efficient", "Samarali, unumli", "B1"),
            Triple("Priority", "Ustuvorlik, birinchi darajali ish", "B1"),
            // B2
            Triple("Abandon", "Tashlab ketmoq, to'xtatmoq", "B2"),
            Triple("Resilience", "Qiyinchiliklarga bardoshlik, chidamlilik", "B2"),
            Triple("Substantial", "Salmoqli, sezilarli, muhim", "B2"),
            Triple("Consistency", "Doimiylik, izchillik", "B2"),
            Triple("Dedication", "Fidoiylik, sodiqlik", "B2")
        )

        return sampleData.map { (w, t, lvl) ->
            VocabCard(
                word = w,
                translation = t,
                phonetic = CommonDictionary.getPhonetic(w),
                partOfSpeech = CommonDictionary.getPartOfSpeech(w),
                definition = "CEFR $lvl darajasidagi asosiy so'z",
                example = CommonDictionary.getExample(w),
                mnemonic = "$lvl bosqichi · Kunlik intizom bilan yodlash",
                boxLevel = 1,
                level = lvl,
                sourceDocName = "Oxford CEFR Asosiy Ro'yxati",
                isMastered = false
            )
        }.sortedWith(compareBy<VocabCard> { getLevelWeight(it.level) }.thenBy { it.word })
    }
}

/**
 * Built-in lookup helper for accurate Uzbek translations & phonetic hints
 */
object CommonDictionary {
    private val dict = mapOf(
        "can" to Pair("Qila olmoq, mumkin bo'lmoq", "/kæn/"),
        "always" to Pair("Har doim, hamisha", "/ˈɔːlweɪz/"),
        "begin" to Pair("Boshlamoq", "/bɪˈɡɪn/"),
        "daily" to Pair("Kunlik, har kungi", "/ˈdeɪli/"),
        "habit" to Pair("Odat, ko'nikma", "/ˈhæbɪt/"),
        "decide" to Pair("Qaror qilmoq", "/dɪˈsaɪd/"),
        "achieve" to Pair("Erishmoq, yetishmoq", "/əˈtʃiːv/"),
        "improve" to Pair("Yaxshilamoq, o'stirmoq", "/ɪmˈpruːv/"),
        "punctual" to Pair("Vaqtga aniq rioya qiluvchi", "/ˈpʌŋktʃuəl/"),
        "protect" to Pair("Himoyalamoq, asramoq", "/prəˈtekt/"),
        "confidence" to Pair("Ishonch, dadillik", "/ˈkɒnfɪdəns/"),
        "persevere" to Pair("Qat'iyat bilan davom etmoq", "/ˌpɜːsɪˈvɪə/"),
        "discipline" to Pair("Intizom, tartib-qoida", "/ˈdɪsəplɪn/"),
        "efficient" to Pair("Samarali, unumli", "/ɪˈfɪʃnt/"),
        "priority" to Pair("Ustuvor vazifa", "/praɪˈɒrəti/"),
        "abandon" to Pair("Tark etmoq, tashlab ketmoq", "/əˈbændən/"),
        "resilience" to Pair("Bardoshlik, qayta tiklanish kuchi", "/rɪˈzɪliəns/"),
        "substantial" to Pair("Sezilarli, salmoqli", "/səbˈstænʃl/"),
        "consistency" to Pair("Izchillik, doimiylik", "/kənˈsɪstənsi/"),
        "dedication" to Pair("Fidoiylik, mehr berish", "/ˌdedɪˈkeɪʃn/")
    )

    fun getTranslation(word: String): String {
        val key = word.trim().lowercase(Locale.ROOT)
        return dict[key]?.first ?: "O'zbekcha ma'nosi belgilanmagan"
    }

    fun getPhonetic(word: String): String {
        val key = word.trim().lowercase(Locale.ROOT)
        return dict[key]?.second ?: "/.../"
    }

    fun getPartOfSpeech(word: String): String {
        val w = word.lowercase(Locale.ROOT)
        return when {
            w.endsWith("tion") || w.endsWith("ty") || w.endsWith("ence") || w.endsWith("ance") -> "noun"
            w.endsWith("ly") -> "adverb"
            w.endsWith("ful") || w.endsWith("al") || w.endsWith("ent") || w.endsWith("ive") -> "adjective"
            else -> "verb/noun"
        }
    }

    fun getExample(word: String): String {
        return "Daily practice helps you master '$word' easily."
    }
}
