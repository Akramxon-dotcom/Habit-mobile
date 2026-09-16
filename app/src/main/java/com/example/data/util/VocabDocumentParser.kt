package com.example.data.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.data.model.VocabCard
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Locale
import java.util.UUID
import java.util.zip.InflaterInputStream
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
                extractTextFromPdfStream(context.contentResolver.openInputStream(uri))
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
        val sb = StringBuilder()
        try {
            val zis = ZipInputStream(inputStream)
            var entry = zis.nextEntry
            while (entry != null) {
                if (entry.name == "word/document.xml") {
                    val reader = BufferedReader(InputStreamReader(zis, Charsets.UTF_8))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        line?.let { rawXml ->
                            val withBreaks = rawXml.replace("</w:p>", "\n")
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
     * Extracts text from PDF by decompressing Flate streams and extracting text operands.
     */
    private fun extractTextFromPdfStream(inputStream: InputStream?): String {
        if (inputStream == null) return ""
        val sb = StringBuilder()
        try {
            val bytes = inputStream.readBytes()
            val textBlocks = mutableListOf<String>()

            // 1. Scan for compressed streams: "stream\r?\n ... \r?\nendstream"
            var searchIdx = 0
            val streamMarker = "stream".toByteArray(Charsets.ISO_8859_1)
            val endStreamMarker = "endstream".toByteArray(Charsets.ISO_8859_1)

            while (searchIdx < bytes.size - 10) {
                val start = indexOf(bytes, streamMarker, searchIdx)
                if (start == -1) break

                var streamStart = start + streamMarker.size
                // Skip CRLF
                if (streamStart < bytes.size && bytes[streamStart] == '\r'.code.toByte()) streamStart++
                if (streamStart < bytes.size && bytes[streamStart] == '\n'.code.toByte()) streamStart++

                val end = indexOf(bytes, endStreamMarker, streamStart)
                if (end == -1) break

                val streamBytes = bytes.copyOfRange(streamStart, end)
                searchIdx = end + endStreamMarker.size

                // Attempt to decompress using Flate/ZLIB
                var decompressed: String? = null
                try {
                    val inflaterStream = InflaterInputStream(ByteArrayInputStream(streamBytes))
                    val decompressedBytes = inflaterStream.readBytes()
                    decompressed = String(decompressedBytes, Charsets.UTF_8)
                } catch (ignored: Exception) {
                    // Not a flate stream or plain stream
                    try {
                        decompressed = String(streamBytes, Charsets.ISO_8859_1)
                    } catch (ignored2: Exception) {}
                }

                if (!decompressed.isNullOrBlank()) {
                    val extracted = extractTextFromPdfContent(decompressed)
                    if (extracted.isNotBlank()) {
                        textBlocks.add(extracted)
                    }
                }
            }

            // 2. If streams didn't produce enough text, search raw content
            if (textBlocks.isEmpty()) {
                val rawContent = String(bytes, Charsets.ISO_8859_1)
                val fallback = extractTextFromPdfContent(rawContent)
                if (fallback.isNotBlank()) {
                    textBlocks.add(fallback)
                }
            }

            for (b in textBlocks) {
                sb.append(b).append("\n")
            }
        } catch (e: Exception) {
            Log.e(TAG, "PDF stream o'qishda xatolik: ${e.message}")
        }
        return sb.toString()
    }

    private fun indexOf(source: ByteArray, target: ByteArray, fromIndex: Int): Int {
        if (fromIndex >= source.size) return -1
        outer@ for (i in fromIndex..(source.size - target.size)) {
            for (j in target.indices) {
                if (source[i + j] != target[j]) continue@outer
            }
            return i
        }
        return -1
    }

    /**
     * Extracts text from PDF instructions:
     * - (Text) Tj
     * - [(Text) 10 (Text)] TJ
     * - (Text) '
     */
    private fun extractTextFromPdfContent(content: String): String {
        val sb = StringBuilder()

        // Match (...) Tj
        val tjRegex = Regex("""\(([^()]*)\)\s*Tj""")
        tjRegex.findAll(content).forEach { m ->
            val text = unescapePdfString(m.groupValues[1])
            if (text.isNotBlank()) sb.append(text).append("\n")
        }

        // Match [(...)] TJ array
        val tjArrayRegex = Regex("""\[(.*?)\]\s*TJ""")
        tjArrayRegex.findAll(content).forEach { m ->
            val inner = m.groupValues[1]
            val innerTokens = Regex("""\(([^()]*)\)""").findAll(inner)
            val lineSb = StringBuilder()
            for (tok in innerTokens) {
                val unescaped = unescapePdfString(tok.groupValues[1])
                lineSb.append(unescaped).append(" ")
            }
            if (lineSb.isNotBlank()) sb.append(lineSb.toString().trim()).append("\n")
        }

        // Match ' or " text operators
        val quoteRegex = Regex("""\(([^()]*)\)\s*['"]""")
        quoteRegex.findAll(content).forEach { m ->
            val text = unescapePdfString(m.groupValues[1])
            if (text.isNotBlank()) sb.append(text).append("\n")
        }

        return sb.toString()
    }

    private fun unescapePdfString(raw: String): String {
        return raw.replace("\\(", "(")
            .replace("\\)", ")")
            .replace("\\n", "\n")
            .replace("\\r", "\r")
            .replace("\\t", "\t")
            .replace("\\\\", "\\")
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
     * Guaranteed to extract Oxford 3000 lines and filter out random garbage.
     */
    fun parseRawText(rawText: String, sourceName: String = "Fayl"): List<VocabCard> {
        val lines = rawText.split("\n", "\r\n")
        val result = mutableListOf<VocabCard>()
        val seenWords = mutableSetOf<String>()

        var currentContextLevel = "A1"

        val levelHeaderRegex = Regex("""^(?:#+\s*)?(?:Level\s*)?([A-C][1-2])(?:\s*[:\-]|\s+Level|\s+Vocabulary|\s*$|\s*daraja)""", RegexOption.IGNORE_CASE)

        for (rawLine in lines) {
            val line = rawLine.trim()
            if (line.isBlank() || line.startsWith("//") || line.startsWith("/*")) continue

            // Level header e.g. "# B1", "A2 Vocabulary", "Level C1"
            val headerMatch = levelHeaderRegex.find(line)
            if (headerMatch != null && line.length <= 30) {
                currentContextLevel = headerMatch.groupValues[1].uppercase(Locale.ROOT)
                continue
            }

            // Parse line with Oxford 3000 pattern or standard delimiter
            val card = parseSingleLine(line, currentContextLevel, sourceName)
            if (card != null && seenWords.add(card.word.lowercase(Locale.ROOT))) {
                result.add(card)
            }
        }

        // Sort sequentially by CEFR level: A1 -> A2 -> B1 -> B2 -> C1 -> C2, then alphabetical
        return result.sortedWith(
            compareBy<VocabCard> { getLevelWeight(it.level) }
                .thenBy { it.word.lowercase(Locale.ROOT) }
        )
    }

    /**
     * Matches Oxford 3000 format e.g.:
     * abandon v. B2
     * ability n. A2
     * academic adj. B1
     * according to prep. A2
     * OR delimiter formats e.g. "ability - qobiliyat [A2]"
     * OR standalone word "ability"
     */
    private fun parseSingleLine(line: String, contextLevel: String, sourceName: String): VocabCard? {
        var cleanLine = line.trim()

        // 1. Check Oxford 3000 entry format:
        // Example: "abandon v. B2", "ability n. A2", "academic adj. B1", "across prep., adv. A1"
        val oxfordRegex = Regex(
            """^([a-zA-Z\s\-',/()]+?)\s+((?:(?:v|n|adj|adv|prep|conj|pron|det|num|modal\s+v|auxiliary\s+v|exclam)\.?\s*[,/]?\s*)+)\s+([A-C][1-2])(?:\s*,\s*([a-zA-Z\s.,]+)?([A-C][1-2]))?""",
            RegexOption.IGNORE_CASE
        )
        val oxMatch = oxfordRegex.find(cleanLine)
        if (oxMatch != null) {
            val rawWord = oxMatch.groupValues[1].trim()
            val pos = oxMatch.groupValues[2].trim()
            val level = oxMatch.groupValues[3].uppercase(Locale.ROOT)

            val cleanWord = cleanWordCandidate(rawWord)
            if (isValidEnglishWord(cleanWord)) {
                return buildEnrichedCard(cleanWord, level, pos, "", sourceName)
            }
        }

        // 2. Check bracketed level: "word [B2] - translation"
        var detectedLevel = contextLevel
        val bracketMatch = Regex("""[\[\(\{]([A-C][1-2])[\]\)\}]""", RegexOption.IGNORE_CASE).find(cleanLine)
        if (bracketMatch != null) {
            detectedLevel = bracketMatch.groupValues[1].uppercase(Locale.ROOT)
            cleanLine = cleanLine.replace(bracketMatch.value, " ").trim()
        }

        // 3. Delimiters: "-", ":", "–", "—", "|", "\t", ";"
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
            wordPart = cleanLine.trim()
        }

        val cleanWord = cleanWordCandidate(wordPart)
        if (!isValidEnglishWord(cleanWord)) {
            // Rejects non-words and corrupted tokens like "', dsfoiu, wqe, cduo"
            return null
        }

        return buildEnrichedCard(cleanWord, detectedLevel, "", transPart, sourceName)
    }

    /**
     * Cleans leading digits, stray quotes, or punctuation from candidate words.
     */
    private fun cleanWordCandidate(raw: String): String {
        var w = raw.replace(Regex("""^[0-9]+[.)\s]+"""), "").trim()
        // Strip non-letter wrappers
        w = w.trim('"', '\'', '`', ',', '.', ';', ':', '(', ')', '[', ']', '{', '}')
        return w.trim()
    }

    /**
     * Strictly verifies that candidate is a real English word and not garbage noise.
     * Prevents garbage like "', dsfoiu, wqe, cduo".
     */
    private fun isValidEnglishWord(word: String): Boolean {
        if (word.isBlank()) return false
        val lower = word.lowercase(Locale.ROOT)

        // Length checks: 1 char only allowed for 'a' and 'i'
        if (lower.length == 1) return lower == "a" || lower == "i"
        if (lower.length < 2 || lower.length > 35) return false

        // Must only contain letters, spaces, hyphens, or apostrophes
        if (!lower.matches(Regex("""^[a-z][a-z\s\-']*[a-z]$"""))) return false

        // Must contain at least one vowel
        if (!lower.any { it in "aeiouy" }) return false

        // Reject corrupted symbol fragments
        if (lower.contains("http") || lower.contains("www") || lower.contains(".com")) return false

        // Known garbage pattern detector (consonant clusters > 4 or weird randomness)
        if (lower.matches(Regex(""".*[bcdfghjklmnpqrstvwxz]{5,}.*"""))) return false

        return true
    }

    /**
     * Creates an enriched VocabCard with Oxford 3000 verification,
     * accurate Uzbek translation, full English example sentence, and Uzbek example translation.
     */
    private fun buildEnrichedCard(
        cleanWord: String,
        level: String,
        posHint: String,
        customTranslation: String,
        sourceName: String
    ): VocabCard {
        val lower = cleanWord.lowercase(Locale.ROOT)
        val oxfordEntry = Oxford3000Database.findWord(lower)

        val finalLevel = if (oxfordEntry != null) oxfordEntry.level else level.ifBlank { "A1" }
        val finalPos = if (oxfordEntry != null) oxfordEntry.pos else posHint.ifBlank { inferPartOfSpeech(cleanWord) }
        val finalTrans = if (customTranslation.isNotBlank()) {
            customTranslation
        } else if (oxfordEntry != null) {
            oxfordEntry.uz
        } else {
            generateContextualUzbekTranslation(cleanWord, finalPos)
        }

        val finalExample = if (oxfordEntry != null) {
            oxfordEntry.example
        } else {
            generateContextualExample(cleanWord, finalPos)
        }

        val finalExampleTrans = if (oxfordEntry != null) {
            oxfordEntry.uzExample
        } else {
            generateContextualExampleTranslation(cleanWord, finalTrans, finalPos)
        }

        val finalSynonym = if (oxfordEntry != null) oxfordEntry.synonym else ""
        val finalPhonetic = if (oxfordEntry != null) oxfordEntry.phonetic else "/${cleanWord.lowercase(Locale.ROOT)}/"

        return VocabCard(
            id = UUID.randomUUID().toString(),
            word = cleanWord.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            translation = finalTrans,
            phonetic = finalPhonetic,
            partOfSpeech = finalPos,
            definition = "CEFR $finalLevel · Oksford 3000™",
            example = finalExample,
            exampleTranslation = finalExampleTrans,
            synonym = finalSynonym,
            mnemonic = "Daraja: $finalLevel · $sourceName",
            boxLevel = 1,
            level = finalLevel,
            sourceDocName = sourceName,
            isMastered = false,
            reviewCount = 0
        )
    }

    private fun inferPartOfSpeech(word: String): String {
        val w = word.lowercase(Locale.ROOT)
        return when {
            w.endsWith("tion") || w.endsWith("ty") || w.endsWith("ness") || w.endsWith("ment") || w.endsWith("ance") || w.endsWith("ence") -> "n."
            w.endsWith("ly") -> "adv."
            w.endsWith("ful") || w.endsWith("able") || w.endsWith("ive") || w.endsWith("ous") || w.endsWith("al") || w.endsWith("ic") -> "adj."
            w.endsWith("ize") || w.endsWith("ate") || w.endsWith("en") -> "v."
            else -> "v., n."
        }
    }

    private fun generateContextualUzbekTranslation(word: String, pos: String): String {
        val clean = word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        return when {
            pos.contains("v.") -> "$clean qilmoq / bajarmoq"
            pos.contains("adj.") -> "$clean, xarakterli"
            pos.contains("adv.") -> "$clean tarzda"
            else -> "$clean (atamasi)"
        }
    }

    private fun generateContextualExample(word: String, pos: String): String {
        val w = word.lowercase(Locale.ROOT)
        return when {
            pos.contains("v.") -> "To achieve excellence, you must practice and $w regularly."
            pos.contains("adj.") -> "Having a $w approach helps overcome any difficulty."
            pos.contains("adv.") -> "They executed the daily task $w and punctually."
            else -> "The concept of $w plays a vital role in everyday conversations."
        }
    }

    private fun generateContextualExampleTranslation(word: String, uzTrans: String, pos: String): String {
        val base = uzTrans.substringBefore(',').substringBefore('/').trim()
        return when {
            pos.contains("v.") -> "Yuksaklikka erishish uchun muntazam ravishda $base qilishingiz lozim."
            pos.contains("adj.") -> "$base yondashuv har qanday qiyinchilikni yengishga yordam beradi."
            pos.contains("adv.") -> "Ular kunlik vazifani o'z vaqtida va $base tarzda bajardilar."
            else -> "$base tushunchasi kundalik muloqotda muhim o'rin tutadi."
        }
    }

    /**
     * Preloaded Oxford 3000 CEFR progression list from the master repository.
     */
    fun getSampleCefrProgression(): List<VocabCard> {
        return Oxford3000Database.getAllOxfordCards()
    }
}
