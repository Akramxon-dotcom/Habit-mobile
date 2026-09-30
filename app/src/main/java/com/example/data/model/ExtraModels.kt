package com.example.data.model

import java.util.UUID

data class VocabCard(
    val id: String = UUID.randomUUID().toString(),
    val word: String,
    val translation: String,
    val phonetic: String = "",
    val partOfSpeech: String = "noun",
    val definition: String = "",
    val example: String = "",
    val exampleTranslation: String = "",
    val synonym: String = "",
    val mnemonic: String = "",
    val boxLevel: Int = 1, // 1: Yangi, 2: O'rganilmoqda, 3: Mustahkamlandi, 4: Yodlandi
    val level: String = "A1", // A1, A2, B1, B2, C1, C2
    val sourceDocName: String = "",
    val isMastered: Boolean = false,
    val learnedDate: String = "",
    val reviewCount: Int = 0,
    val lastReviewedEpochMs: Long = System.currentTimeMillis(),
    val importanceRank: Int = 9999, // 1 to 5000+ by necessity/frequency in English
    val isFavorite: Boolean = false // user or priority favorite
)

data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),
    val dateStr: String,
    val stars: Int = 5,
    val highlights: String = "",
    val challenges: String = "",
    val reflections: String = "",
    val savedTimeMinutes: Int = 0,
    val completedTasksCount: Int = 0
)

data class QuizQuestion(
    val cardId: String = "",
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class SpeakingEssentialWord(
    val id: String = UUID.randomUUID().toString(),
    val word: String,
    val translation: String,
    val phonetic: String = "",
    val partOfSpeech: String = "noun",
    val level: String = "B1",
    val usageRule: String = "",
    val dialogueExample: String = "",
    val dialogueTranslation: String = "",
    val synonyms: String = "",
    val spokenTip: String = "",
    val sourceMode: String = "Speaking Room",
    val addedAtEpochMs: Long = System.currentTimeMillis(),
    val isLearned: Boolean = false,
    val reviewCount: Int = 0
)
