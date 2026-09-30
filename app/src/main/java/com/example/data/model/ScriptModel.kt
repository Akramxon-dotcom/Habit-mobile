package com.example.data.model

import java.util.UUID

/**
 * 🎧 SCRIPT (Transkripsiya / Diktant) Modeli:
 * Ingliz tilidan 2 daqiqalik ustoz ovozli xabarlarini eshitib matnga tushirish.
 */
data class ScriptSentenceChunk(
    val id: String = UUID.randomUUID().toString(),
    val startTimeMs: Long = 0L,
    val endTimeMs: Long = 0L,
    val userText: String = "",
    val note: String = ""
)

data class ScriptDocument(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "Yangi Script Vazifasi",
    val dateStr: String = "",
    val audioUriStr: String? = null,
    val sampleAudioId: String? = null,
    val audioTitle: String = "Ovozli dars",
    val audioDurationMs: Long = 120_000L,
    val rawText: String = "",
    val chunks: List<ScriptSentenceChunk> = emptyList(),
    val isCompleted: Boolean = false,
    val lastPositionMs: Long = 0L,
    val targetDurationCoverageMs: Long = 0L,
    val createdAtEpochMs: Long = System.currentTimeMillis(),
    val updatedAtEpochMs: Long = System.currentTimeMillis()
)

data class ScriptSampleAudio(
    val id: String,
    val title: String,
    val teacherName: String,
    val durationSec: Int,
    val descriptionUz: String,
    val level: String,
    val hiddenSpokenContent: String, // O'quvchi ko'rmaydi, faqat audioni sintez qilish uchun
    val category: String = "Umumiy"
)

data class SpellCheckIssue(
    val originalWord: String,
    val suggestedWord: String,
    val ruleExplanation: String,
    val charIndex: Int = 0
)

object ScriptSampleRepository {
    val customUserSamples = androidx.compose.runtime.mutableStateListOf<ScriptSampleAudio>()

    fun addCustomSample(sample: ScriptSampleAudio) {
        if (customUserSamples.none { it.id == sample.id }) {
            customUserSamples.add(0, sample)
        }
    }

    fun removeCustomSample(id: String) {
        customUserSamples.removeAll { it.id == id }
    }

    val samples: List<ScriptSampleAudio> get() = customUserSamples.toList() + ScriptSampleData.fiftySamples


    fun getPuzzleSentences(sampleId: String): List<ScriptPuzzleSentence> {
        val sample = samples.firstOrNull { it.id == sampleId } ?: samples.first()
        return parseSentencesFromText(sample.hiddenSpokenContent, sample.durationSec * 1000L)
    }

    fun parseSentencesFromText(text: String, durationMs: Long): List<ScriptPuzzleSentence> {
        val sentences = text
            .split(Regex("(?<=[.!?\\n])\\s+"))
            .map { it.trim() }
            .filter { it.length > 5 }

        val chunkMs = if (sentences.isNotEmpty()) (durationMs / sentences.size).coerceAtLeast(4000L) else 8000L
        return sentences.mapIndexed { i, s ->
            val cleanWords = s.replace(Regex("""[.,!?:;\"'()]"""), "")
                .split(Regex("""\s+"""))
                .filter { it.isNotBlank() }
            ScriptPuzzleSentence(
                index = i,
                text = s,
                words = cleanWords,
                startTimeMs = i * chunkMs,
                endTimeMs = (i + 1) * chunkMs
            )
        }
    }
}

data class ScriptPuzzleSentence(
    val index: Int,
    val text: String,
    val words: List<String>,
    val startTimeMs: Long = 0L,
    val endTimeMs: Long = 0L
)


/**
 * 🔍 Ingliz tili imlo va diktant tekshiruvchisi (No-Cheat Spell Engine):
 * Subtitr bermaydi, lekin o'quvchi o'zi yozgan matndagi harfiy, punktuatsion xatolarni aniqlaydi.
 */
object ScriptSpellEngine {
    private val commonSpellingCorrections = mapOf(
        "teh" to "the",
        "recieve" to "receive",
        "recieved" to "received",
        "recieving" to "receiving",
        "seperate" to "separate",
        "seperated" to "separated",
        "definately" to "definitely",
        "untill" to "until",
        "occured" to "occurred",
        "occuring" to "occurring",
        "thier" to "their",
        "alot" to "a lot",
        "wich" to "which",
        "becuase" to "because",
        "beleive" to "believe",
        "beleived" to "believed",
        "tommorrow" to "tomorrow",
        "calender" to "calendar",
        "collegue" to "colleague",
        "wierd" to "weird",
        "goverment" to "government",
        "enviroment" to "environment",
        "truely" to "truly",
        "confortable" to "comfortable",
        "pronounciation" to "pronunciation",
        "neccessary" to "necessary",
        "succesful" to "successful",
        "succesfully" to "successfully",
        "writting" to "writing",
        "stoped" to "stopped",
        "hapened" to "happened",
        "grammer" to "grammar",
        "begining" to "beginning",
        "accomodate" to "accommodate",
        "embarass" to "embarrass",
        "foriegn" to "foreign",
        "freind" to "friend",
        "peice" to "piece",
        "quater" to "quarter",
        "allready" to "already",
        "alright" to "all right",
        "realy" to "really",
        "succseed" to "succeed",
        "knowlege" to "knowledge",
        "rember" to "remember",
        "speach" to "speech",
        "suprise" to "surprise",
        "tomorow" to "tomorrow",
        "wether" to "whether"
    )

    fun checkText(text: String): List<SpellCheckIssue> {
        val issues = mutableListOf<SpellCheckIssue>()
        if (text.isBlank()) return issues

        // 1. So'zma-so'z tekshirish
        val wordsWithIndices = extractWordsWithIndices(text)
        for ((word, index) in wordsWithIndices) {
            val cleanWord = word.lowercase().trim { !it.isLetter() }
            if (cleanWord.isEmpty()) continue

            // Yolg'iz kichik 'i'
            if (cleanWord == "i") {
                issues.add(
                    SpellCheckIssue(
                        originalWord = word,
                        suggestedWord = "I",
                        ruleExplanation = "Ingliz tilida 'I' (men) doimo bosh harf bilan yoziladi.",
                        charIndex = index
                    )
                )
            }

            // Lug'atdagi xatolar
            val suggestion = commonSpellingCorrections[cleanWord]
            if (suggestion != null) {
                // Match original casing if capitalized
                val fixed = if (word.first().isUpperCase()) suggestion.replaceFirstChar { it.uppercase() } else suggestion
                issues.add(
                    SpellCheckIssue(
                        originalWord = word,
                        suggestedWord = fixed,
                        ruleExplanation = "Imlo xatosi: '$word' emas, to'g'risi '$fixed'.",
                        charIndex = index
                    )
                )
            }
        }

        // 2. Gap boshida kichik harf tekshiruvi
        val sentenceRegex = Regex("""(?:^|[.!?]\s+)([a-z])""")
        for (match in sentenceRegex.findAll(text)) {
            val letterGroup = match.groups[1]
            if (letterGroup != null) {
                val letter = letterGroup.value
                val idx = letterGroup.range.first
                issues.add(
                    SpellCheckIssue(
                        originalWord = letter,
                        suggestedWord = letter.uppercase(),
                        ruleExplanation = "Yangi gap doimo bosh harf bilan boshlanishi kerak.",
                        charIndex = idx
                    )
                )
            }
        }

        // 3. Ketma-ket takrorlangan so'zlar (e.g. "the the")
        val duplicateRegex = Regex("""\b([A-Za-z]+)\s+\1\b""", RegexOption.IGNORE_CASE)
        for (match in duplicateRegex.findAll(text)) {
            issues.add(
                SpellCheckIssue(
                    originalWord = match.value,
                    suggestedWord = match.groupValues[1],
                    ruleExplanation = "Bir xil so'z ketma-ket takrorlangan: '${match.value}'.",
                    charIndex = match.range.first
                )
            )
        }

        return issues
    }

    private fun extractWordsWithIndices(text: String): List<Pair<String, Int>> {
        val result = mutableListOf<Pair<String, Int>>()
        val regex = Regex("""\b[\w']+\b""")
        for (match in regex.findAll(text)) {
            result.add(Pair(match.value, match.range.first))
        }
        return result
    }
}
