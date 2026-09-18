package com.example.data.model

import com.example.data.model.books.DriveIntoDangerBook
import com.example.data.model.books.PhantomOfTheOperaBook
import com.example.data.model.books.SherlockHolmesBook
import com.example.data.model.books.TheElephantManBook
import com.example.data.util.UniversalDictionary

data class BookChapter(
    val chapterNumber: Int,
    val title: String,
    val contentParagraphs: List<String>,
    val targetVocabulary: List<String>
)

data class GradedBook(
    val id: String,
    val title: String,
    val author: String,
    val level: String, // "Starter (250 so'z)", "Stage 1 (400 so'z)"
    val headwords: Int,
    val iconEmoji: String,
    val synopsis: String,
    val chapters: List<BookChapter>
)

object GradedReaderRepository {

    // 1-Kitob: Drive into Danger (10 ta to'liq boyitilgan bob)
    val DRIVE_INTO_DANGER_CHAPTERS = DriveIntoDangerBook.CHAPTERS

    // 2-Kitob: The Elephant Man (10 ta to'liq boyitilgan bob)
    val THE_ELEPHANT_MAN_CHAPTERS = TheElephantManBook.CHAPTERS

    // 3-Kitob: Sherlock Holmes & Duke's Son (10 ta to'liq boyitilgan bob)
    val SHERLOCK_HOLMES_CHAPTERS = SherlockHolmesBook.CHAPTERS

    // 4-Kitob: The Phantom of the Opera (10 ta to'liq boyitilgan bob)
    val PHANTOM_OPERA_CHAPTERS = PhantomOfTheOperaBook.CHAPTERS

    val ALL_BOOKS = listOf(
        DriveIntoDangerBook.BOOK,
        TheElephantManBook.BOOK,
        SherlockHolmesBook.BOOK,
        PhantomOfTheOperaBook.BOOK
    )

    val BOOKS = ALL_BOOKS

    // Quick instant vocabulary dictionary lookup for fast local translations
    val QUICK_DICTIONARY = UniversalDictionary.BUILTIN_VOCABULARY.mapValues {
        Pair(it.value.uz, "${it.value.phonetic} - ${it.value.pos}")
    }
}
