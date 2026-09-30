package com.example.ui.screen

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MurphyBookDatabase
import com.example.data.model.MurphyExerciseItem
import com.example.data.model.MurphySection
import com.example.data.model.MurphyUnit
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager

/**
 * Categorized Group in the Murphy 115-Unit System.
 */
data class MurphyGroupCategory(
    val id: Int,
    val title: String,
    val description: String,
    val rangeText: String,
    val cefrLevel: String, // A1, A2, B1, B2
    val iconEmoji: String,
    val color: Color,
    val units: List<MurphyUnit>
)

/**
 * 🌟 CHUQUR VA MUKAMMAL MURPHY GRAMMAR ACADEMY
 *
 * Chuqur, qulay va maroqli o'rganish tajribasi:
 * 1. 🗺️ Akademiya Xaritasi (Roadmap):
 *    - Umumiy daraja, progress diagrammasi, seriya va CEFR ko'rsatkichi
 *    - "Faol darsni davom ettirish" hero kartochkasi
 *    - CEFR daraja filtrlari (A1, A2, B1, B2, Saqlanganlar, O'rganilmaganlar)
 *    - Jonli qidiruv va 19 ta tizimli modul
 * 2. 💡 Dars Xonasi (Study Room):
 *    - ⚡ Oltin Qoidalar (Key Takeaways) yorqin kartasi
 *    - 📐 Sintaksis va Formula Konstruktori
 *    - ⚠️ Keng tarqalgan xatolar (Common Pitfalls: ❌ Xato vs ✅ To'g'ri)
 *    - 🔊 Ovozli talaffuz va tezlik boshqaruvi (1.0x / 0.75x)
 * 3. ✍️ Interaktiv Trenajor (Duolingo uslubidagi bosqichma-bosqich test):
 *    - Bitta-bitta savol o'tishi, vizual progress va darhol tekshirish
 *    - To'g'ri/xato javoblarning jonli ta'rifi va Murphy qoidasi izohi
 *    - Test yakunida tantanali sertifikat va XP berilishi
 * 4. 🎧 Audio Pleer (Listen & Repeat):
 *    - Unitdagi barcha gaplar pleylist shaklida, ketma-ket tinglash imkoniyati
 * 5. ⭐ Doimiy xotira (SharedPreferences):
 *    - Bajarilgan unitlar, so'nggi dars va saqlanganlar (Bookmarks) doimiy saqlanadi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MurphyGrammarScreen(
    onBack: () -> Unit,
    onModuleCompleted: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val theme = LocalLiquidTheme.current

    // Speech manager for native pronunciation
    val speechManager = remember { SpeechManager(context) }
    DisposableEffect(Unit) {
        onDispose {
            speechManager.destroy()
        }
    }

    // TTS Speed state (0.95f = normal, 0.75f = slow & clear)
    var speechRate by remember { mutableFloatStateOf(0.92f) }

    fun speakWithRate(text: String) {
        speechManager.speak(text, speechRate)
    }

    // SharedPreferences for persistent progress
    val sharedPrefs = remember(context) {
        context.getSharedPreferences("murphy_academy_prefs", Context.MODE_PRIVATE)
    }

    // Persisted completed units tracker
    var completedUnitNumbers by remember {
        val savedSet = sharedPrefs.getStringSet("completed_units", emptySet()) ?: emptySet()
        mutableStateOf(savedSet.mapNotNull { it.toIntOrNull() }.toSet())
    }

    // Persisted bookmarked / favorite units
    var bookmarkedUnitNumbers by remember {
        val savedSet = sharedPrefs.getStringSet("bookmarked_units", emptySet()) ?: emptySet()
        mutableStateOf(savedSet.mapNotNull { it.toIntOrNull() }.toSet())
    }

    fun markUnitCompleted(unitNum: Int) {
        val updated = completedUnitNumbers + unitNum
        completedUnitNumbers = updated
        sharedPrefs.edit().putStringSet("completed_units", updated.map { it.toString() }.toSet()).apply()
        onModuleCompleted("unit_$unitNum")
    }

    fun toggleBookmark(unitNum: Int) {
        val updated = if (bookmarkedUnitNumbers.contains(unitNum)) {
            bookmarkedUnitNumbers - unitNum
        } else {
            bookmarkedUnitNumbers + unitNum
        }
        bookmarkedUnitNumbers = updated
        sharedPrefs.edit().putStringSet("bookmarked_units", updated.map { it.toString() }.toSet()).apply()
    }

    // 19 Structured Categories matching Raymond Murphy 4th Edition
    val categories = remember {
        listOf(
            MurphyGroupCategory(
                id = 0,
                title = "Present Tenses",
                description = "Hozirgi zamon: to be, do/does, continuous va odatiy holatlar",
                rangeText = "Unit 1 – 9",
                cefrLevel = "A1",
                iconEmoji = "🟢",
                color = Color(0xFF10B981),
                units = MurphyBookDatabase.UNITS_PRESENT
            ),
            MurphyGroupCategory(
                id = 1,
                title = "Past Tenses",
                description = "O'tgan zamon: was/were, did, oddiy va davomli o'tgan zamon",
                rangeText = "Unit 10 – 14",
                cefrLevel = "A1",
                iconEmoji = "🟣",
                color = Color(0xFFA855F7),
                units = MurphyBookDatabase.UNITS_PAST
            ),
            MurphyGroupCategory(
                id = 2,
                title = "Present Perfect",
                description = "Tugallangan zamon: have/has done, just, already, yet, for/since",
                rangeText = "Unit 15 – 20",
                cefrLevel = "A1",
                iconEmoji = "🟡",
                color = Color(0xFFF59E0B),
                units = MurphyBookDatabase.UNITS_PRESENT_PERFECT
            ),
            MurphyGroupCategory(
                id = 3,
                title = "Passive Voice & Verb Forms",
                description = "Majhul nisbat (is done / was done), fe'l turlari va noaniq shakllar",
                rangeText = "Unit 21 – 24",
                cefrLevel = "A1",
                iconEmoji = "🔵",
                color = Color(0xFF3B82F6),
                units = MurphyBookDatabase.UNITS_PASSIVE
            ),
            MurphyGroupCategory(
                id = 4,
                title = "Future Tenses",
                description = "Kelasi zamon: I am doing (kelasi), I'm going to, will / shall",
                rangeText = "Unit 25 – 28",
                cefrLevel = "A1",
                iconEmoji = "🟠",
                color = Color(0xFFFB923C),
                units = MurphyBookDatabase.UNITS_FUTURE
            ),
            MurphyGroupCategory(
                id = 5,
                title = "Modals & Imperatives",
                description = "Modal fe'llar: can/could, must, should, I have to, buyruq fe'llari",
                rangeText = "Unit 29 – 36",
                cefrLevel = "A2",
                iconEmoji = "🔴",
                color = Color(0xFFEF4444),
                units = MurphyBookDatabase.UNITS_MODALS
            ),
            MurphyGroupCategory(
                id = 6,
                title = "Pronouns & Questions",
                description = "Olmoshlar, there is/are, qisqa javoblar va savol tuzish qoidalari",
                rangeText = "Unit 37 – 44",
                cefrLevel = "A2",
                iconEmoji = "🟤",
                color = Color(0xFF8B5CF6),
                units = MurphyBookDatabase.UNITS_PRONOUNS
            ),
            MurphyGroupCategory(
                id = 7,
                title = "Reported Speech & Questions",
                description = "Murakkab savollar, predloglar va o'zlashtirma gap (said/told)",
                rangeText = "Unit 45 – 50",
                cefrLevel = "A2",
                iconEmoji = "💎",
                color = Color(0xFF06B6D4),
                units = MurphyBookDatabase.UNITS_REPORTED_SPEECH
            ),
            MurphyGroupCategory(
                id = 8,
                title = "Gerund & Infinitives (-ing & to)",
                description = "Fe'l shakllari, want to, make/let, maqsad infinitivi va -ing",
                rangeText = "Unit 51 – 56",
                cefrLevel = "A2",
                iconEmoji = "🎯",
                color = Color(0xFFF59E0B),
                units = MurphyBookDatabase.UNITS_VERB_STRUCTURES
            ),
            MurphyGroupCategory(
                id = 9,
                title = "Essential Verbs & Phrasals",
                description = "Get, do vs make, have got, o'zlik olmoshlari (myself) va frazalar",
                rangeText = "Unit 57 – 62",
                cefrLevel = "A2",
                iconEmoji = "⚡",
                color = Color(0xFFEC4899),
                units = MurphyBookDatabase.UNITS_GO_GET_DO
            ),
            MurphyGroupCategory(
                id = 10,
                title = "Articles & Nouns",
                description = "A/an, otlar ko'pligi (men, teeth), sanoqli va sanalmaydigan otlar, 'the'",
                rangeText = "Unit 63 – 68",
                cefrLevel = "B1",
                iconEmoji = "📖",
                color = Color(0xFF10B981),
                units = MurphyBookDatabase.UNITS_ARTICLES_NOUNS
            ),
            MurphyGroupCategory(
                id = 11,
                title = "Pronouns & Determiners",
                description = "This/that, one/ones, some/any, not any/no/none, every & all",
                rangeText = "Unit 69 – 74",
                cefrLevel = "B1",
                iconEmoji = "🎯",
                color = Color(0xFF8B5CF6),
                units = MurphyBookDatabase.UNITS_PRONOUNS_DETERMINERS
            ),
            MurphyGroupCategory(
                id = 12,
                title = "Adjectives & Adverbs",
                description = "Sifat va ravish (quickly), qiyosiy daraja (older than), as...as, too/enough",
                rangeText = "Unit 75 – 80",
                cefrLevel = "B1",
                iconEmoji = "🌟",
                color = Color(0xFFF97316),
                units = MurphyBookDatabase.UNITS_ADJECTIVES_ADVERBS
            ),
            MurphyGroupCategory(
                id = 13,
                title = "Word Order & Prepositions",
                description = "So'z tartibi, still/yet/already, at/on/in (vaqt va joy), since/for, during",
                rangeText = "Unit 81 – 86",
                cefrLevel = "B1",
                iconEmoji = "🧭",
                color = Color(0xFF0284C7),
                units = MurphyBookDatabase.UNITS_WORD_ORDER_PREPOSITIONS
            ),
            MurphyGroupCategory(
                id = 14,
                title = "Conjunctions & Clauses",
                description = "Harakat predloglari (along/across), so/because, if/when qoidalari",
                rangeText = "Unit 87 – 92",
                cefrLevel = "B1",
                iconEmoji = "🔗",
                color = Color(0xFFEC4899),
                units = MurphyBookDatabase.UNITS_CONJUNCTIONS_CLAUSES
            ),
            MurphyGroupCategory(
                id = 15,
                title = "Relative Clauses & Phrasals",
                description = "Shart mayli (If I had), who/which/that, frazali fe'llar (turn on/off, get up)",
                rangeText = "Unit 93 – 98",
                cefrLevel = "B2",
                iconEmoji = "🧩",
                color = Color(0xFF14B8A6),
                units = MurphyBookDatabase.UNITS_RELATIVE_CLAUSES_PHRASALS
            ),
            MurphyGroupCategory(
                id = 16,
                title = "Advanced Phrasals & Mastery",
                description = "Try on, look after, run out of, say vs tell, do vs make, noto'g'ri fe'llar",
                rangeText = "Unit 99 – 104",
                cefrLevel = "B2",
                iconEmoji = "🏆",
                color = Color(0xFF8B5CF6),
                units = MurphyBookDatabase.UNITS_ADVANCED_PHRASALS
            ),
            MurphyGroupCategory(
                id = 17,
                title = "Appendices & Reference",
                description = "Faol/majhul nisbat jadvali, 50 ta noto'g'ri fe'l, imlo qoidalari va qisqartmalar",
                rangeText = "Unit 105 – 110",
                cefrLevel = "B2",
                iconEmoji = "📚",
                color = Color(0xFFE11D48),
                units = MurphyBookDatabase.UNITS_APPENDICES
            ),
            MurphyGroupCategory(
                id = 18,
                title = "Final Mastery & Real Life",
                description = "Say vs Tell vs Speak, Make/Do idiomalari, Used to va hayotiy muloqot",
                rangeText = "Unit 111 – 115",
                cefrLevel = "B2",
                iconEmoji = "👑",
                color = Color(0xFFD97706),
                units = MurphyBookDatabase.UNITS_FINAL_COLLECTION
            )
        )
    }

    val allUnits = remember(categories) { categories.flatMap { it.units } }

    // Navigation State: true = Study Room, false = Roadmap / Dashboard
    var isStudyRoomActive by remember { mutableStateOf(false) }

    // Active Unit number
    var activeUnitNumber by remember {
        val lastSavedUnit = sharedPrefs.getInt("last_active_unit", 1)
        mutableIntStateOf(lastSavedUnit)
    }

    val activeUnit = remember(activeUnitNumber, allUnits) {
        allUnits.find { it.unitNumber == activeUnitNumber } ?: allUnits.first()
    }

    fun selectUnitToStudy(unitNumber: Int) {
        activeUnitNumber = unitNumber
        sharedPrefs.edit().putInt("last_active_unit", unitNumber).apply()
        isStudyRoomActive = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Murphy Grammatika",
                                fontSize = 16.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                color = theme.textPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = theme.primaryAccent.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "115 Unit",
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryAccent,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (isStudyRoomActive) "Unit ${activeUnit.unitNumber}: ${activeUnit.title}" else "Essential Grammar in Use · 4th Edition",
                            fontSize = 11.sp,
                            color = theme.textSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isStudyRoomActive) {
                            isStudyRoomActive = false
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Orqaga",
                            tint = theme.textPrimary
                        )
                    }
                },
                actions = {
                    if (isStudyRoomActive) {
                        // Bookmark toggle in Study Room
                        val isBookmarked = bookmarkedUnitNumbers.contains(activeUnit.unitNumber)
                        IconButton(onClick = { toggleBookmark(activeUnit.unitNumber) }) {
                            Icon(
                                if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Saqlash",
                                tint = if (isBookmarked) Color(0xFFFFB800) else theme.textSecondary
                            )
                        }

                        // Speech rate switcher (Normal vs Slow)
                        IconButton(onClick = {
                            speechRate = if (speechRate >= 0.9f) 0.72f else 0.95f
                        }) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (speechRate < 0.9f) Color(0xFFFFB800).copy(alpha = 0.2f) else theme.glassSurface,
                                border = BorderStroke(1.dp, if (speechRate < 0.9f) Color(0xFFFFB800) else theme.glassBorderSubtleColor)
                            ) {
                                Text(
                                    text = if (speechRate < 0.9f) "0.7x" else "1.0x",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (speechRate < 0.9f) Color(0xFFFFB800) else theme.textSecondary,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }

                        IconButton(onClick = { isStudyRoomActive = false }) {
                            Icon(
                                Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = "Xaritaga qaytish",
                                tint = theme.primaryAccent
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = theme.bgTop)
            )
        },
        containerColor = theme.bgTop,
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedContent(
                targetState = isStudyRoomActive,
                transitionSpec = {
                    if (targetState) {
                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> -width } + fadeOut()
                        )
                    } else {
                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> width } + fadeOut()
                        )
                    }
                },
                label = "MurphyScreenTransition"
            ) { inStudyRoom ->
                if (inStudyRoom) {
                    // ==========================================
                    // 1. CHUQUR DARS XONASI (Unit Study Room)
                    // ==========================================
                    MurphyDeepUnitStudyRoom(
                        unit = activeUnit,
                        allUnits = allUnits,
                        isCompleted = completedUnitNumbers.contains(activeUnit.unitNumber),
                        isBookmarked = bookmarkedUnitNumbers.contains(activeUnit.unitNumber),
                        speechRate = speechRate,
                        onToggleBookmark = { toggleBookmark(activeUnit.unitNumber) },
                        onMarkCompleted = { markUnitCompleted(activeUnit.unitNumber) },
                        onSelectUnit = { selectUnitToStudy(it) },
                        onBackToRoadmap = { isStudyRoomActive = false },
                        onSpeak = { text -> speakWithRate(text) },
                        onToggleSpeed = {
                            speechRate = if (speechRate >= 0.9f) 0.72f else 0.95f
                        }
                    )
                } else {
                    // ==========================================
                    // 2. AKADEMIYA XARITASI (Roadmap Dashboard)
                    // ==========================================
                    MurphyDeepAcademyRoadmap(
                        categories = categories,
                        allUnits = allUnits,
                        activeUnitNumber = activeUnitNumber,
                        completedUnitNumbers = completedUnitNumbers,
                        bookmarkedUnitNumbers = bookmarkedUnitNumbers,
                        onSelectUnit = { selectUnitToStudy(it.unitNumber) },
                        onToggleBookmark = { toggleBookmark(it) }
                    )
                }
            }
        }
    }
}

/**
 * 🗺️ CHUQUR VA JOZIBALI AKADEMIYA XARITASI (Roadmap Dashboard)
 */
@Composable
private fun MurphyDeepAcademyRoadmap(
    categories: List<MurphyGroupCategory>,
    allUnits: List<MurphyUnit>,
    activeUnitNumber: Int,
    completedUnitNumbers: Set<Int>,
    bookmarkedUnitNumbers: Set<Int>,
    onSelectUnit: (MurphyUnit) -> Unit,
    onToggleBookmark: (Int) -> Unit
) {
    val theme = LocalLiquidTheme.current

    // Filters: "ALL", "A1", "A2", "B1", "B2", "BOOKMARKED", "UNCOMPLETED"
    var selectedFilter by remember { mutableStateOf("ALL") }
    var searchQuery by remember { mutableStateOf("") }
    var expandedCategoryId by remember { mutableStateOf<Int?>(null) }

    val totalCount = allUnits.size
    val completedCount = completedUnitNumbers.size
    val progressPercent = if (totalCount > 0) (completedCount * 100) / totalCount else 0
    val activeUnit = allUnits.find { it.unitNumber == activeUnitNumber } ?: allUnits.first()

    // Gamification level title
    val levelTitle = when {
        completedCount < 15 -> "🟢 A1 Boshlang'ich (Beginner)"
        completedCount < 40 -> "🟡 A2 O'rta (Pre-Intermediate)"
        completedCount < 75 -> "🔵 B1 Yetuk (Intermediate)"
        completedCount < 110 -> "🟣 B2 Ravon (Upper-Intermediate)"
        else -> "👑 Grammatika Ustasi (Grammar Master)"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. HERO MASTERY CARD (Deep glass with radial glow)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.2.dp, theme.primaryAccent.copy(alpha = 0.45f))
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Subtle background gradient accent
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        theme.primaryAccent.copy(alpha = 0.12f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🎯", fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Grammatika Akademiyasi",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Serif,
                                        color = theme.textPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = levelTitle,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = theme.primaryAccent
                                )
                            }

                            // Circular Progress Badge
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(52.dp)
                            ) {
                                CircularProgressIndicator(
                                    progress = { (completedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f) },
                                    modifier = Modifier.size(52.dp),
                                    color = theme.primaryAccent,
                                    trackColor = theme.primaryAccent.copy(alpha = 0.15f),
                                    strokeWidth = 4.5.dp,
                                    strokeCap = StrokeCap.Round
                                )
                                Text(
                                    text = "$progressPercent%",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Black,
                                    color = theme.textPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Progress Bar with rounded caps
                        LinearProgressIndicator(
                            progress = { (completedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = theme.primaryAccent,
                            trackColor = theme.primaryAccent.copy(alpha = 0.15f),
                            strokeCap = StrokeCap.Round
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Stats Triplet Row: Completed, XP, Bookmarks
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatPill(icon = "🏆", label = "O'zlashtirildi", value = "$completedCount/$totalCount")
                            StatPill(icon = "⚡", label = "Grammatika XP", value = "+${completedCount * 50}")
                            StatPill(icon = "⭐", label = "Saqlanganlar", value = "${bookmarkedUnitNumbers.size}")
                        }
                    }
                }
            }
        }

        // 2. HERO "DAVOM ETTIRISH" (ACTIVE RESUME CARD)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectUnit(activeUnit) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D241A)),
                border = BorderStroke(1.5.dp, theme.primaryAccent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = theme.primaryAccent,
                                modifier = Modifier.padding(end = 6.dp)
                            ) {
                                Text(
                                    text = "FAOL DARS",
                                    color = Color.Black,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Unit ${activeUnit.unitNumber}",
                                color = theme.primaryAccent,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeUnit.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = activeUnit.subtitleUzbek,
                            fontSize = 11.5.sp,
                            color = theme.textSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Button(
                        onClick = { onSelectUnit(activeUnit) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "O'rganish ➔",
                            color = Color.Black,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 3. SEARCH & QUICK FILTER BAR
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Qidirish (masalan: present, past, can, passive, 15)...", color = theme.textSecondary, fontSize = 12.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = theme.textSecondary, modifier = Modifier.size(16.dp))
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_murphy_search"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = theme.primaryAccent,
                    unfocusedBorderColor = theme.glassBorderSubtleColor,
                    focusedContainerColor = theme.glassSurface,
                    unfocusedContainerColor = theme.glassSurface,
                    focusedTextColor = theme.textPrimary,
                    unfocusedTextColor = theme.textPrimary
                )
            )
        }

        // 4. HORIZONTAL FILTER PILLS (CEFR Levels + Bookmarks + Uncompleted)
        item {
            val filters = listOf(
                "ALL" to "Barchasi (115)",
                "UNCOMPLETED" to "⏳ Qolganlar (${totalCount - completedCount})",
                "BOOKMARKED" to "⭐ Saqlanganlar (${bookmarkedUnitNumbers.size})",
                "A1" to "🟢 A1 (1–28)",
                "A2" to "🟡 A2 (29–62)",
                "B1" to "🔵 B1 (63–92)",
                "B2" to "🟣 B2 (93–115)"
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { (key, label) ->
                    val isSelected = selectedFilter == key
                    Surface(
                        onClick = { selectedFilter = key },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) theme.primaryAccent else theme.glassSurface,
                        border = BorderStroke(1.dp, if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor)
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.Black else theme.textPrimary,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 5. UNITS / MODULES CONTENT
        if (searchQuery.isNotBlank()) {
            val q = searchQuery.trim().lowercase()
            val filteredUnits = allUnits.filter {
                it.unitNumber.toString() == q ||
                    it.title.lowercase().contains(q) ||
                    it.subtitleUzbek.lowercase().contains(q) ||
                    it.groupName.lowercase().contains(q)
            }

            item {
                Text(
                    text = "Qidiruv natijalari: ${filteredUnits.size} ta unit topildi",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.primaryAccent
                )
            }

            items(filteredUnits) { unit ->
                MurphyDeepUnitListItem(
                    unit = unit,
                    isActive = unit.unitNumber == activeUnitNumber,
                    isCompleted = completedUnitNumbers.contains(unit.unitNumber),
                    isBookmarked = bookmarkedUnitNumbers.contains(unit.unitNumber),
                    onSelect = { onSelectUnit(unit) },
                    onToggleBookmark = { onToggleBookmark(unit.unitNumber) }
                )
            }
        } else if (selectedFilter == "BOOKMARKED") {
            val bookmarkedUnits = allUnits.filter { bookmarkedUnitNumbers.contains(it.unitNumber) }

            if (bookmarkedUnits.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("⭐", fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Hozircha saqlangan mavzular yo'q",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Qiyin tuyulgan unitlarni yulduzcha tugmasi orqali saqlab oling.",
                                fontSize = 11.5.sp,
                                color = theme.textSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(bookmarkedUnits) { unit ->
                    MurphyDeepUnitListItem(
                        unit = unit,
                        isActive = unit.unitNumber == activeUnitNumber,
                        isCompleted = completedUnitNumbers.contains(unit.unitNumber),
                        isBookmarked = true,
                        onSelect = { onSelectUnit(unit) },
                        onToggleBookmark = { onToggleBookmark(unit.unitNumber) }
                    )
                }
            }
        } else {
            // Grouped modules
            val displayCategories = categories.filter { cat ->
                when (selectedFilter) {
                    "ALL" -> true
                    "UNCOMPLETED" -> cat.units.any { !completedUnitNumbers.contains(it.unitNumber) }
                    else -> cat.cefrLevel.equals(selectedFilter, ignoreCase = true)
                }
            }

            items(displayCategories) { category ->
                val displayUnits = if (selectedFilter == "UNCOMPLETED") {
                    category.units.filter { !completedUnitNumbers.contains(it.unitNumber) }
                } else {
                    category.units
                }

                val categoryDoneCount = category.units.count { completedUnitNumbers.contains(it.unitNumber) }
                val isExpanded = expandedCategoryId == category.id || expandedCategoryId == null

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = BorderStroke(
                        1.dp,
                        if (category.units.any { it.unitNumber == activeUnitNumber }) category.color.copy(alpha = 0.7f) else theme.glassBorderSubtleColor
                    )
                ) {
                    Column {
                        // Category Header (Accordion)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedCategoryId = if (expandedCategoryId == category.id) -1 else category.id
                                }
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(category.color.copy(alpha = 0.2f), CircleShape)
                                        .border(1.2.dp, category.color, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(category.iconEmoji, fontSize = 16.sp)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = category.title,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = theme.textPrimary
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = category.color.copy(alpha = 0.15f)
                                        ) {
                                            Text(
                                                text = category.rangeText,
                                                fontSize = 9.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = category.color,
                                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = category.description,
                                        fontSize = 11.sp,
                                        color = theme.textSecondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (categoryDoneCount > 0) {
                                    Text(
                                        text = "$categoryDoneCount/${category.units.size}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = category.color
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = theme.textSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Expanded Unit Items
                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                displayUnits.forEach { unit ->
                                    MurphyDeepUnitListItem(
                                        unit = unit,
                                        isActive = unit.unitNumber == activeUnitNumber,
                                        isCompleted = completedUnitNumbers.contains(unit.unitNumber),
                                        isBookmarked = bookmarkedUnitNumbers.contains(unit.unitNumber),
                                        onSelect = { onSelectUnit(unit) },
                                        onToggleBookmark = { onToggleBookmark(unit.unitNumber) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Stat Pill Helper inside Header.
 */
@Composable
private fun StatPill(icon: String, label: String, value: String) {
    val theme = LocalLiquidTheme.current
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = theme.glassSurface,
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                Text(label, fontSize = 9.sp, color = theme.textSecondary)
            }
        }
    }
}

/**
 * Individual Unit Card in List / Roadmap with Bookmark toggle and meta pills.
 */
@Composable
private fun MurphyDeepUnitListItem(
    unit: MurphyUnit,
    isActive: Boolean,
    isCompleted: Boolean,
    isBookmarked: Boolean,
    onSelect: () -> Unit,
    onToggleBookmark: () -> Unit
) {
    val theme = LocalLiquidTheme.current

    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(14.dp),
        color = if (isActive) theme.primaryAccent.copy(alpha = 0.16f) else theme.glassSurfaceElevated,
        border = BorderStroke(
            1.dp,
            if (isActive) theme.primaryAccent else if (isCompleted) Color(0xFF10B981).copy(alpha = 0.4f) else theme.glassBorderSubtleColor
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Unit Number Badge with status ring
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            if (isCompleted) Color(0xFF10B981).copy(alpha = 0.2f)
                            else if (isActive) theme.primaryAccent
                            else theme.glassSurface,
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            if (isCompleted) Color(0xFF10B981)
                            else if (isActive) theme.primaryAccent
                            else theme.glassBorderSubtleColor,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                    } else {
                        Text(
                            text = "${unit.unitNumber}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isActive) Color.Black else theme.textPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = unit.title,
                            fontSize = 13.5.sp,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                            color = if (isActive) theme.primaryAccent else theme.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = unit.subtitleUzbek,
                        fontSize = 11.sp,
                        color = theme.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Bookmark star
                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Star else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) Color(0xFFFFB800) else theme.textSecondary.copy(alpha = 0.4f),
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                if (isCompleted) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF10B981).copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = "Bajarildi",
                            color = Color(0xFF10B981),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "O'rganish",
                        tint = theme.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}

/**
 * 📖 CHUQUR DARS XONASI (Unit Study Room)
 * 3 ta to'liq rejim:
 * - 💡 Qoidalar & Konspekt (Formulalar, Sintaksis konstruktori, Misollar, Keng tarqalgan xatolar)
 * - ✍️ Interaktiv Trenajor (Bitta-bitta savol Duolingo rejimida, tushuntirish bilan)
 * - 🎧 Audio Pleer (Ketma-ket eshitish va talaffuz)
 */
@Composable
private fun MurphyDeepUnitStudyRoom(
    unit: MurphyUnit,
    allUnits: List<MurphyUnit>,
    isCompleted: Boolean,
    isBookmarked: Boolean,
    speechRate: Float,
    onToggleBookmark: () -> Unit,
    onMarkCompleted: () -> Unit,
    onSelectUnit: (Int) -> Unit,
    onBackToRoadmap: () -> Unit,
    onSpeak: (String) -> Unit,
    onToggleSpeed: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Theory, 1: Practice, 2: Audio

    val currentIndex = allUnits.indexOfFirst { it.unitNumber == unit.unitNumber }
    val prevUnit = if (currentIndex > 0) allUnits[currentIndex - 1] else null
    val nextUnit = if (currentIndex < allUnits.size - 1) allUnits[currentIndex + 1] else null

    Column(modifier = Modifier.fillMaxSize()) {
        // 1. TOP STEPPER & QUICK ACTION BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(theme.glassSurface)
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onBackToRoadmap,
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Icon(Icons.Default.FormatListBulleted, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Xaritaga qaytish", color = theme.primaryAccent, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
            }

            Text(
                text = "${unit.unitNumber} / ${allUnits.size} mavzu",
                color = theme.textSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                IconButton(
                    onClick = { prevUnit?.let { onSelectUnit(it.unitNumber) } },
                    enabled = prevUnit != null,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Oldingi",
                        tint = if (prevUnit != null) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.3f),
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = { nextUnit?.let { onSelectUnit(it.unitNumber) } },
                    enabled = nextUnit != null,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Keyingi",
                        tint = if (nextUnit != null) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.3f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // 2. SLIDING TAB BAR
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = theme.glassSurfaceElevated,
            contentColor = theme.primaryAccent,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = theme.primaryAccent,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "💡 Qoidalar",
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                        color = if (selectedTab == 0) theme.primaryAccent else theme.textSecondary
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "✍️ Trenajor (${unit.exercises.size})",
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == 1) theme.primaryAccent else theme.textSecondary
                        )
                        if (isCompleted) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("✅", fontSize = 10.sp)
                        }
                    }
                }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = {
                    Text(
                        text = "🎧 Audio Pleer",
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Medium,
                        color = if (selectedTab == 2) theme.primaryAccent else theme.textSecondary
                    )
                }
            )
        }

        // 3. MAIN BODY
        when (selectedTab) {
            0 -> {
                // TAB 0: THEORY, FORMULAS & EXAMPLES
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 14.dp, bottom = 40.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Unit Hero Card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                            border = BorderStroke(1.2.dp, theme.primaryAccent.copy(alpha = 0.45f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = theme.primaryAccent.copy(alpha = 0.2f),
                                        border = BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.5f))
                                    ) {
                                        Text(
                                            text = "UNIT ${unit.unitNumber}",
                                            color = theme.primaryAccent,
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Text(
                                        text = unit.groupName,
                                        color = theme.textSecondary,
                                        fontSize = 10.5.sp,
                                        fontStyle = FontStyle.Italic
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = unit.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif,
                                    color = theme.textPrimary
                                )

                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = unit.subtitleUzbek,
                                    fontSize = 12.sp,
                                    color = theme.textSecondary,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    // Key Takeaways Golden Box
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFB800).copy(alpha = 0.10f)),
                            border = BorderStroke(1.dp, Color(0xFFFFB800).copy(alpha = 0.4f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFFB800), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "⚡ Mavzuning oltin siri (Key Takeaways):",
                                        color = Color(0xFFFFB800),
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                unit.keyTakeawaysUzbek.forEach { takeaway ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("• ", fontSize = 12.sp, color = Color(0xFFFFB800), fontWeight = FontWeight.Bold)
                                        Text(
                                            text = takeaway,
                                            fontSize = 11.5.sp,
                                            color = theme.textPrimary,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Sections A, B, C...
                    items(unit.sections) { section ->
                        MurphyDeepSectionCard(
                            section = section,
                            speechRate = speechRate,
                            onSpeak = onSpeak
                        )
                    }

                    // Common Pitfalls / Mistakes Card (Murphy Special)
                    item {
                        MurphyCommonPitfallsCard(unit = unit)
                    }

                    // Start Exercise Button
                    item {
                        Button(
                            onClick = { selectedTab = 1 },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                        ) {
                            Icon(Icons.Default.Psychology, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "✍️ Interaktiv Trenajorni Boshlash",
                                color = Color.Black,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            1 -> {
                // TAB 1: STEP-BY-STEP INTERACTIVE QUIZ TRAINER (Duolingo style)
                MurphyStepByStepQuizTrainer(
                    unit = unit,
                    onMarkCompleted = onMarkCompleted,
                    onNextUnit = { nextUnit?.let { onSelectUnit(it.unitNumber) } },
                    onSpeak = onSpeak
                )
            }

            2 -> {
                // TAB 2: AUDIO PLAYLIST & CONTINUOUS LISTENING
                MurphyDeepAudioSection(
                    unit = unit,
                    speechRate = speechRate,
                    onSpeak = onSpeak,
                    onToggleSpeed = onToggleSpeed
                )
            }
        }
    }
}

/**
 * Detailed Section Card with Syntax Pills and Audio.
 */
@Composable
private fun MurphyDeepSectionCard(
    section: MurphySection,
    speechRate: Float,
    onSpeak: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = theme.primaryAccent,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(section.sectionCode, color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Black)
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = section.heading,
                    color = theme.textPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Syntax Formula Box
            section.formula?.let { formula ->
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.primaryAccent.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📐", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = formula,
                            color = theme.primaryAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Explanation in Uzbek
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = section.explanationUzbek,
                color = theme.textSecondary,
                fontSize = 12.sp,
                lineHeight = 16.5.sp
            )

            // Examples List
            if (section.examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Misollar va amaliyot:",
                    color = theme.textPrimary,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    section.examples.forEach { ex ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = theme.glassSurface,
                            border = BorderStroke(1.dp, theme.glassBorderSubtleColor),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = ex.english,
                                        color = theme.primaryAccent,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = ex.uzbek,
                                        color = theme.textPrimary,
                                        fontSize = 11.5.sp
                                    )
                                    ex.note?.let { note ->
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "💡 $note",
                                            color = Color(0xFFFFB800),
                                            fontSize = 10.5.sp
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { onSpeak(ex.english) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Listen",
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * ⚠️ COMMON PITFALLS & MISTAKES CARD
 * Realistic Murphy traps and differences.
 */
@Composable
private fun MurphyCommonPitfallsCard(unit: MurphyUnit) {
    val theme = LocalLiquidTheme.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.35f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WarningAmber, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "⚠️ Keng tarqalgan xatolar (Common Traps):",
                    color = Color(0xFFEF4444),
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamic comparison based on unit type
            val sampleMistake = when (unit.unitNumber) {
                1, 2 -> "❌ Xato: I am live in Tashkent." to "✅ To'g'ri: I live in Tashkent (yoki I am living)."
                3, 4 -> "❌ Xato: I am liking this coffee." to "✅ To'g'ri: I like this coffee (like fe'li Continuous bo'lmaydi)."
                5, 6 -> "❌ Xato: He don't know the answer." to "✅ To'g'ri: He doesn't know the answer (He/She/It da doesn't)."
                else -> "❌ Xato: Qoidaga e'tibor bermasdan so'zma-so'z tarjima qilish." to "✅ To'g'ri: Murphy qoidalaridagi fe'l zamoniga qat'iy amal qilish."
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFEF4444).copy(alpha = 0.12f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = sampleMistake.first,
                    color = Color(0xFFEF4444),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF10B981).copy(alpha = 0.12f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = sampleMistake.second,
                    color = Color(0xFF10B981),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

/**
 * ✍️ STEP-BY-STEP QUIZ TRAINER (Duolingo-style card flow):
 * Highly interactive, single question at a time, instant validation.
 */
@Composable
private fun MurphyStepByStepQuizTrainer(
    unit: MurphyUnit,
    onMarkCompleted: () -> Unit,
    onNextUnit: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current

    var currentQuestionIndex by remember(unit.unitNumber) { mutableIntStateOf(0) }
    var selectedOptionIndex by remember(unit.unitNumber, currentQuestionIndex) { mutableStateOf<Int?>(null) }
    var isAnswerChecked by remember(unit.unitNumber, currentQuestionIndex) { mutableStateOf(false) }
    var score by remember(unit.unitNumber) { mutableIntStateOf(0) }
    var isQuizCompleted by remember(unit.unitNumber) { mutableStateOf(false) }

    val exercises = unit.exercises
    val currentExercise = if (exercises.isNotEmpty() && currentQuestionIndex < exercises.size) exercises[currentQuestionIndex] else null

    if (isQuizCompleted || currentExercise == null) {
        // END OF QUIZ CELEBRATION
        val isPassed = score >= (exercises.size * 0.75).toInt()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPassed) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFFF9800).copy(alpha = 0.15f)
                    ),
                    border = BorderStroke(1.5.dp, if (isPassed) Color(0xFF10B981) else Color(0xFFFF9800))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(if (isPassed) "🎉" else "📚", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (isPassed) "Muborakbod etamiz!" else "Mashg'ulot yakunlandi",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Natija: $score / ${exercises.size} to'g'ri javob",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isPassed) Color(0xFF10B981) else Color(0xFFFF9800)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isPassed) "Unit ${unit.unitNumber} muvaffaqiyatli o'zlashtirildi va +50 XP hisobingizga qo'shildi!" else "Qoidalarni yana bir bor takrorlab, qayta urinib ko'ring.",
                            fontSize = 12.sp,
                            color = theme.textSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    currentQuestionIndex = 0
                                    selectedOptionIndex = null
                                    isAnswerChecked = false
                                    score = 0
                                    isQuizCompleted = false
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Qayta", fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    if (isPassed) {
                                        onMarkCompleted()
                                    }
                                    onNextUnit()
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                modifier = Modifier.weight(1.3f)
                            ) {
                                Text("Keyingi Unit ➔", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    } else {
        // ACTIVE STEP QUESTION
        val isCorrect = selectedOptionIndex == currentExercise.correctOptionIndex

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Step Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Savol ${currentQuestionIndex + 1} / ${exercises.size}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.primaryAccent
                )
                Text(
                    text = "To'g'ri: $score",
                    fontSize = 12.sp,
                    color = Color(0xFF10B981),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { ((currentQuestionIndex + 1).toFloat() / exercises.size.toFloat()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = theme.primaryAccent,
                trackColor = theme.glassBorderSubtleColor,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Question Prompt Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.dp, theme.glassBorderSubtleColor)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = theme.primaryAccent.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Mashq ${currentExercise.exerciseNumber}",
                                color = theme.primaryAccent,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        IconButton(
                            onClick = { onSpeak(currentExercise.question.replace("______", "...")) },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "O'qish", tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentExercise.question,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Options List
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                currentExercise.options.forEachIndexed { optIdx, optText ->
                    val isThisChosen = selectedOptionIndex == optIdx
                    val isThisTheCorrectAnswer = optIdx == currentExercise.correctOptionIndex

                    val optBg = when {
                        isAnswerChecked && isThisTheCorrectAnswer -> Color(0xFF10B981).copy(alpha = 0.25f)
                        isAnswerChecked && isThisChosen && !isThisTheCorrectAnswer -> Color(0xFFEF4444).copy(alpha = 0.25f)
                        isThisChosen -> theme.primaryAccent.copy(alpha = 0.2f)
                        else -> theme.glassSurfaceElevated
                    }

                    val optBorder = when {
                        isAnswerChecked && isThisTheCorrectAnswer -> Color(0xFF10B981)
                        isAnswerChecked && isThisChosen && !isThisTheCorrectAnswer -> Color(0xFFEF4444)
                        isThisChosen -> theme.primaryAccent
                        else -> theme.glassBorderSubtleColor
                    }

                    Surface(
                        onClick = {
                            if (!isAnswerChecked) {
                                selectedOptionIndex = optIdx
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        color = optBg,
                        border = BorderStroke(1.2.dp, optBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isThisChosen) theme.primaryAccent else theme.glassSurface,
                                    modifier = Modifier.size(26.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${('A' + optIdx)}",
                                            color = if (isThisChosen) Color.Black else theme.textPrimary,
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = optText,
                                    color = theme.textPrimary,
                                    fontSize = 13.5.sp,
                                    fontWeight = if (isThisChosen) FontWeight.Bold else FontWeight.Medium
                                )
                            }

                            if (isAnswerChecked && isThisTheCorrectAnswer) {
                                Text("✅", fontSize = 14.sp)
                            } else if (isAnswerChecked && isThisChosen && !isThisTheCorrectAnswer) {
                                Text("❌", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Feedback Box (when checked)
            if (isAnswerChecked) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f)
                    ),
                    border = BorderStroke(1.dp, if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isCorrect) "🎉 Barakalla! To'g'ri javob." else "⚠️ Noto'g'ri. To'g'ri javob: ${('A' + currentExercise.correctOptionIndex)}",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "💡 Murphy qoidasi: ${currentExercise.explanationUzbek}",
                            fontSize = 11.sp,
                            color = theme.textSecondary,
                            lineHeight = 15.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Bottom Sticky Button
            if (!isAnswerChecked) {
                Button(
                    onClick = {
                        if (selectedOptionIndex != null) {
                            isAnswerChecked = true
                            if (selectedOptionIndex == currentExercise.correctOptionIndex) {
                                score++
                            }
                        }
                    },
                    enabled = selectedOptionIndex != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text("Javobni tekshirish", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            } else {
                Button(
                    onClick = {
                        if (currentQuestionIndex < exercises.size - 1) {
                            currentQuestionIndex++
                            selectedOptionIndex = null
                            isAnswerChecked = false
                        } else {
                            isQuizCompleted = true
                            if (score >= (exercises.size * 0.75).toInt()) {
                                onMarkCompleted()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent)
                ) {
                    Text(
                        text = if (currentQuestionIndex < exercises.size - 1) "Keyingi savol ➔" else "Natijalarni ko'rish 🏆",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

/**
 * 🎧 DEEP AUDIO PLAYER & PLAYLIST SECTION:
 * Allows continuous listening with speed controls (1.0x / 0.75x).
 */
@Composable
private fun MurphyDeepAudioSection(
    unit: MurphyUnit,
    speechRate: Float,
    onSpeak: (String) -> Unit,
    onToggleSpeed: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    val allExamples = remember(unit) { unit.sections.flatMap { it.examples } }
    var currentPlayingIndex by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Player Control Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurfaceElevated),
                border = BorderStroke(1.2.dp, theme.primaryAccent.copy(alpha = 0.45f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Headphones, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Audio Talaffuz Pleeri", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                        }

                        // Speed Pill
                        Surface(
                            onClick = onToggleSpeed,
                            shape = RoundedCornerShape(8.dp),
                            color = if (speechRate < 0.9f) Color(0xFFFFB800).copy(alpha = 0.2f) else theme.glassSurface,
                            border = BorderStroke(1.dp, if (speechRate < 0.9f) Color(0xFFFFB800) else theme.glassBorderSubtleColor)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(14.dp), tint = if (speechRate < 0.9f) Color(0xFFFFB800) else theme.textSecondary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (speechRate < 0.9f) "0.75x (Sekin)" else "1.0x (Oddiy)",
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (speechRate < 0.9f) Color(0xFFFFB800) else theme.textPrimary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Jami ${allExamples.size} ta namuna gap. Har bir gapni tinglab, baland ovozda takrorlang.",
                        fontSize = 11.5.sp,
                        color = theme.textSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Player buttons: Prev, Play current, Next
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (currentPlayingIndex > 0) {
                                    currentPlayingIndex--
                                    allExamples.getOrNull(currentPlayingIndex)?.let { onSpeak(it.english) }
                                }
                            },
                            enabled = currentPlayingIndex > 0
                        ) {
                            Icon(Icons.Default.SkipPrevious, contentDescription = "Oldingi", tint = theme.textPrimary)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                allExamples.getOrNull(currentPlayingIndex)?.let { onSpeak(it.english) }
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            modifier = Modifier.size(48.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Tinglash", tint = Color.Black, modifier = Modifier.size(24.dp))
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (currentPlayingIndex < allExamples.size - 1) {
                                    currentPlayingIndex++
                                    allExamples.getOrNull(currentPlayingIndex)?.let { onSpeak(it.english) }
                                }
                            },
                            enabled = currentPlayingIndex < allExamples.size - 1
                        ) {
                            Icon(Icons.Default.SkipNext, contentDescription = "Keyingi", tint = theme.textPrimary)
                        }
                    }
                }
            }
        }

        // List of all sentences
        itemsIndexed(allExamples) { idx, ex ->
            val isCurrent = idx == currentPlayingIndex

            Surface(
                onClick = {
                    currentPlayingIndex = idx
                    onSpeak(ex.english)
                },
                shape = RoundedCornerShape(14.dp),
                color = if (isCurrent) theme.primaryAccent.copy(alpha = 0.16f) else theme.glassSurfaceElevated,
                border = BorderStroke(1.dp, if (isCurrent) theme.primaryAccent else theme.glassBorderSubtleColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isCurrent) theme.primaryAccent else theme.glassSurface,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "${idx + 1}",
                                    color = if (isCurrent) Color.Black else theme.primaryAccent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = ex.english,
                                color = if (isCurrent) theme.primaryAccent else theme.textPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = ex.uzbek,
                                color = theme.textSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            currentPlayingIndex = idx
                            onSpeak(ex.english)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Tinglash",
                            tint = if (isCurrent) theme.primaryAccent else theme.textSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
