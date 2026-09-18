package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MurphyBookDatabase
import com.example.data.model.MurphyExerciseItem
import com.example.data.model.MurphySection
import com.example.data.model.MurphyUnit
import com.example.ui.theme.LiquidGlassStyle
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager

data class MurphyGroupCategory(
    val id: Int,
    val title: String,
    val description: String,
    val rangeText: String,
    val iconEmoji: String,
    val color: Color,
    val units: List<MurphyUnit>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MurphyGrammarScreen(
    onBack: () -> Unit,
    onModuleCompleted: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val theme = LocalLiquidTheme.current

    val speechManager = remember { SpeechManager(context) }
    DisposableEffect(Unit) {
        onDispose {
            speechManager.destroy()
        }
    }

    // All structured categories in exact Murphy order
    val categories = remember {
        listOf(
            MurphyGroupCategory(
                id = 0,
                title = "Present Tenses",
                description = "Hozirgi zamon: to be, do/does, continuous va odatiy holatlar",
                rangeText = "Unit 1 – 9",
                iconEmoji = "🟢",
                color = Color(0xFF10B981),
                units = MurphyBookDatabase.UNITS_PRESENT
            ),
            MurphyGroupCategory(
                id = 1,
                title = "Past Tenses",
                description = "O'tgan zamon: was/were, did, oddiy va davomli o'tgan zamon",
                rangeText = "Unit 10 – 14",
                iconEmoji = "🟣",
                color = Color(0xFFA855F7),
                units = MurphyBookDatabase.UNITS_PAST
            ),
            MurphyGroupCategory(
                id = 2,
                title = "Present Perfect",
                description = "Tugallangan hozirgi zamon: have/has done, just, already, yet, for/since",
                rangeText = "Unit 15 – 20",
                iconEmoji = "🟡",
                color = Color(0xFFF59E0B),
                units = MurphyBookDatabase.UNITS_PRESENT_PERFECT
            ),
            MurphyGroupCategory(
                id = 3,
                title = "Passive Voice & Verb Forms",
                description = "Majhul nisbat (is done / was done), fe'l turlari va noaniq shakllar",
                rangeText = "Unit 21 – 24",
                iconEmoji = "🔵",
                color = Color(0xFF3B82F6),
                units = MurphyBookDatabase.UNITS_PASSIVE
            ),
            MurphyGroupCategory(
                id = 4,
                title = "Future Tenses",
                description = "Kelasi zamon: I am doing (kelasi), I'm going to, will / shall",
                rangeText = "Unit 25 – 28",
                iconEmoji = "🟠",
                color = Color(0xFFFB923C),
                units = MurphyBookDatabase.UNITS_FUTURE
            ),
            MurphyGroupCategory(
                id = 5,
                title = "Modals & Imperatives",
                description = "Modal fe'llar: can/could, must, should, I have to, buyruq fe'llari",
                rangeText = "Unit 29 – 36",
                iconEmoji = "🔴",
                color = Color(0xFFEF4444),
                units = MurphyBookDatabase.UNITS_MODALS
            ),
            MurphyGroupCategory(
                id = 6,
                title = "Pronouns, Conditionals & Questions",
                description = "Olmoshlar, there is/are, qisqa javoblar va savol tuzish qoidalari",
                rangeText = "Unit 37 – 44",
                iconEmoji = "🟤",
                color = Color(0xFF8B5CF6),
                units = MurphyBookDatabase.UNITS_PRONOUNS
            ),
            MurphyGroupCategory(
                id = 7,
                title = "Questions & Reported Speech",
                description = "Murakkab savollar (Who saw you / did you see), predloglar, va o'zlashtirma gap (said/told)",
                rangeText = "Unit 45 – 50",
                iconEmoji = "💎",
                color = Color(0xFF06B6D4),
                units = MurphyBookDatabase.UNITS_REPORTED_SPEECH
            ),
            MurphyGroupCategory(
                id = 8,
                title = "-ing and to... (Gerund & Infinitives)",
                description = "Fe'l shakllari, want to, make/let, maqsad infinitivi (to buy vs for), va -ing fe'llari",
                rangeText = "Unit 51 – 56",
                iconEmoji = "🎯",
                color = Color(0xFFF59E0B),
                units = MurphyBookDatabase.UNITS_VERB_STRUCTURES
            ),
            MurphyGroupCategory(
                id = 9,
                title = "Essential Verbs & Phrasal Verbs",
                description = "Get, do vs make, have (got), olmoshlar (myself), predlogli fe'llar va frazali fe'llar",
                rangeText = "Unit 57 – 62",
                iconEmoji = "⚡",
                color = Color(0xFFEC4899),
                units = MurphyBookDatabase.UNITS_GO_GET_DO
            ),
            MurphyGroupCategory(
                id = 10,
                title = "Articles & Nouns",
                description = "A/an, otlar ko'pligi (men, teeth), sanoqli va sanalmaydigan otlar, a piece of advice, va 'the'",
                rangeText = "Unit 63 – 68",
                iconEmoji = "📖",
                color = Color(0xFF10B981),
                units = MurphyBookDatabase.UNITS_ARTICLES_NOUNS
            ),
            MurphyGroupCategory(
                id = 11,
                title = "Pronouns & Determiners",
                description = "This/that/these/those, one/ones, some & any, not any / no / none, somebody/nowhere, every & all",
                rangeText = "Unit 69 – 74",
                iconEmoji = "🎯",
                color = Color(0xFF8B5CF6),
                units = MurphyBookDatabase.UNITS_PRONOUNS_DETERMINERS
            ),
            MurphyGroupCategory(
                id = 12,
                title = "Adjectives & Adverbs",
                description = "Sifat va ravish (quickly), qiyosiy daraja (older/more expensive than), as...as, the best, va too/enough",
                rangeText = "Unit 75 – 80",
                iconEmoji = "🌟",
                color = Color(0xFFF97316),
                units = MurphyBookDatabase.UNITS_ADJECTIVES_ADVERBS
            ),
            MurphyGroupCategory(
                id = 13,
                title = "Word Order & Prepositions",
                description = "So'z tartibi (fe'l+to'ldiruvchi), still/yet/already, at/on/in (vaqt va joy), since/for, va during/while",
                rangeText = "Unit 81 – 86",
                iconEmoji = "🧭",
                color = Color(0xFF0284C7),
                units = MurphyBookDatabase.UNITS_WORD_ORDER_PREPOSITIONS
            ),
            MurphyGroupCategory(
                id = 14,
                title = "Conjunctions & Clauses",
                description = "Harakat va joy predloglari (to/in/at, under/opposite, along/across), on foot / by car, bog'lovchilar (so/because) va if/when qoidalari",
                rangeText = "Unit 87 – 92",
                iconEmoji = "🔗",
                color = Color(0xFFEC4899),
                units = MurphyBookDatabase.UNITS_CONJUNCTIONS_CLAUSES
            ),
            MurphyGroupCategory(
                id = 15,
                title = "Relative Clauses & Phrasal Verbs",
                description = "Hayoliy shart (If I had/were), who / which / that olmoshlari, predlogli fe'llar (listen to / wait for) va frazali fe'llar (turn on/off, get up)",
                rangeText = "Unit 93 – 98",
                iconEmoji = "🧩",
                color = Color(0xFF14B8A6),
                units = MurphyBookDatabase.UNITS_RELATIVE_CLAUSES_PHRASALS
            ),
            MurphyGroupCategory(
                id = 16,
                title = "Advanced Phrasals & Mastery",
                description = "Eat up / slow down, try on / take off, look after / run out of, say vs tell, do vs make, noto'g'ri fe'llar siri va umumiy xulosa",
                rangeText = "Unit 99 – 104",
                iconEmoji = "🏆",
                color = Color(0xFF8B5CF6),
                units = MurphyBookDatabase.UNITS_ADVANCED_PHRASALS
            ),
            MurphyGroupCategory(
                id = 17,
                title = "Appendices & Reference Guides",
                description = "Faol va majhul nisbat xulosasi, eng muhim 50 ta noto'g'ri fe'l, imlo qoidalari (-ing, -ed), qisqartmalar va Britaniya/Amerika farqlari",
                rangeText = "Unit 105 – 110",
                iconEmoji = "📚",
                color = Color(0xFFE11D48),
                units = MurphyBookDatabase.UNITS_APPENDICES
            ),
            MurphyGroupCategory(
                id = 18,
                title = "Final Mastery & Real Life (111–115)",
                description = "Say vs Tell vs Speak vs Talk, Make va Do ning barcha idiomalari, Like vs Would like / prefer, Used to (o'tmishdagi odatlar) va 115-yakuniy hayotiy muloqot",
                rangeText = "Unit 111 – 115",
                iconEmoji = "🌟",
                color = Color(0xFFD97706),
                units = MurphyBookDatabase.UNITS_FINAL_COLLECTION
            )
        )
    }

    val allUnits = remember(categories) { categories.flatMap { it.units } }

    // Navigation mode: true = Table of Contents (Mundarija), false = Unit Study Screen
    var showTableOfContents by remember { mutableStateOf(false) }

    // Currently selected unit (global tracking across all 44 units)
    var currentUnitNumber by remember { mutableIntStateOf(1) }
    val currentUnit = allUnits.find { it.unitNumber == currentUnitNumber } ?: allUnits.first()

    // 0: Theory (Qoidalar & Misollar), 1: Practice (Mashqlar & Test)
    var selectedTab by remember { mutableIntStateOf(0) }

    // Quiz answer states: question id -> selected option index
    var selectedAnswers by remember { mutableStateOf(mapOf<String, Int>()) }
    var showResults by remember { mutableStateOf(false) }

    // Completed units tracker
    var completedUnitNumbers by remember { mutableStateOf(setOf<Int>()) }

    // Search query for Table of Contents
    var searchQuery by remember { mutableStateOf("") }

    val correctCount = currentUnit.exercises.count { ex ->
        selectedAnswers[ex.id] == ex.correctOptionIndex
    }

    // Helper to find previous and next units in exact sequence
    val currentUnitIndex = allUnits.indexOfFirst { it.unitNumber == currentUnit.unitNumber }
    val previousUnit = if (currentUnitIndex > 0) allUnits[currentUnitIndex - 1] else null
    val nextUnit = if (currentUnitIndex < allUnits.size - 1) allUnits[currentUnitIndex + 1] else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Essential Grammar in Use",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(theme.primaryAccent.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    "Murphy 4th Ed",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryAccent
                                )
                            }
                        }
                        Text(
                            if (showTableOfContents) "📚 Mundarija (Tartiblangan ${allUnits.size} ta Unit)" else "Unit ${currentUnit.unitNumber}: ${currentUnit.title}",
                            fontSize = 12.sp,
                            color = theme.primaryAccent,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!showTableOfContents) {
                            showTableOfContents = true
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
                    IconButton(onClick = { showTableOfContents = !showTableOfContents }) {
                        Icon(
                            if (showTableOfContents) Icons.Default.School else Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = if (showTableOfContents) "Darsga qaytish" else "Mundarija",
                            tint = theme.primaryAccent
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = theme.bgTop)
            )
        },
        containerColor = theme.bgTop,
        modifier = modifier
    ) { padding ->
        if (showTableOfContents) {
            MurphyTableOfContentsView(
                categories = categories,
                currentUnitNumber = currentUnitNumber,
                completedUnitNumbers = completedUnitNumbers,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onSelectUnit = { unit ->
                    currentUnitNumber = unit.unitNumber
                    selectedAnswers = emptyMap()
                    showResults = false
                    selectedTab = 0
                    showTableOfContents = false
                },
                theme = theme,
                modifier = Modifier.padding(padding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
            // Top Sequential Navigation Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(theme.glassSurface)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { showTableOfContents = true },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = theme.primaryAccent)
                ) {
                    Icon(Icons.Default.FormatListBulleted, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Mundarija", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "Mavzu ${currentUnit.unitNumber} / ${allUnits.size}",
                    fontSize = 11.sp,
                    color = theme.textSecondary,
                    fontWeight = FontWeight.Medium
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = {
                            previousUnit?.let {
                                currentUnitNumber = it.unitNumber
                                selectedAnswers = emptyMap()
                                showResults = false
                            }
                        },
                        enabled = previousUnit != null,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Oldingi",
                            tint = if (previousUnit != null) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.3f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            nextUnit?.let {
                                currentUnitNumber = it.unitNumber
                                selectedAnswers = emptyMap()
                                showResults = false
                            }
                        },
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

            // Tab bar: Nazariya vs Mashqlar
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = theme.glassSurface,
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
                            "📖 Qoidalar & Misollar",
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
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
                                "✍️ Mashqlar (${currentUnit.exercises.size})",
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTab == 1) theme.primaryAccent else theme.textSecondary
                            )
                            if (showResults) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "($correctCount/${currentUnit.exercises.size})",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (correctCount >= 4) Color(0xFF10B981) else Color(0xFFF59E0B)
                                )
                            }
                        }
                    }
                )
            }

            // Main Scrollable Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Unit Title Banner
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1813)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "UNIT ${currentUnit.unitNumber}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = theme.primaryAccent
                                )
                                Text(
                                    text = currentUnit.groupName,
                                    fontSize = 10.sp,
                                    color = theme.textSecondary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentUnit.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentUnit.subtitleUzbek,
                                fontSize = 12.sp,
                                color = theme.textSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                if (selectedTab == 0) {
                    // TAB 0: THEORY & RULES
                    item {
                        // Key Takeaways Card
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = theme.primaryAccent.copy(alpha = 0.08f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.25f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = null,
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "Mavzuning asosiy nuqtalari:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = theme.primaryAccent
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                currentUnit.keyTakeawaysUzbek.forEach { takeaway ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("• ", fontSize = 12.sp, color = theme.primaryAccent)
                                        Text(
                                            text = takeaway,
                                            fontSize = 11.sp,
                                            color = theme.textPrimary,
                                            lineHeight = 15.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Sections (A, B, C...)
                    items(currentUnit.sections) { section ->
                        TheorySectionCard(
                            section = section,
                            theme = theme,
                            onSpeak = { text -> speechManager.speak(text) }
                        )
                    }

                    item {
                        Button(
                            onClick = { selectedTab = 1 },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "✍️ Ushbu mavzu bo'yicha mashqlarni boshlash",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    // TAB 1: PRACTICE & EXERCISES
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Interaktiv Mashqlar (Unit ${currentUnit.unitNumber}):",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                            if (showResults) {
                                Text(
                                    text = "$correctCount / ${currentUnit.exercises.size} to'g'ri",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (correctCount >= 4) Color(0xFF10B981) else Color(0xFFF59E0B)
                                )
                            }
                        }
                    }

                    itemsIndexed(currentUnit.exercises) { qIdx, exercise ->
                        ExerciseItemCard(
                            index = qIdx + 1,
                            exercise = exercise,
                            selectedOptionIndex = selectedAnswers[exercise.id],
                            showResults = showResults,
                            onOptionSelected = { chosenIdx ->
                                if (!showResults) {
                                    selectedAnswers = selectedAnswers + (exercise.id to chosenIdx)
                                }
                            },
                            onSpeak = { text -> speechManager.speak(text) },
                            theme = theme
                        )
                    }

                    // Submit or Reset Buttons
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        if (!showResults) {
                            val allAnswered = selectedAnswers.size == currentUnit.exercises.size
                            Button(
                                onClick = {
                                    showResults = true
                                    if (correctCount >= 4) {
                                        completedUnitNumbers = completedUnitNumbers + currentUnit.unitNumber
                                        onModuleCompleted("unit_${currentUnit.unitNumber}")
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                shape = RoundedCornerShape(12.dp),
                                enabled = allAnswered
                            ) {
                                Text(
                                    text = if (!allAnswered) "Barcha savollarni belgilang (${selectedAnswers.size}/${currentUnit.exercises.size})" else "Javoblarni tekshirish",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        } else {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (correctCount >= 4) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFF59E0B).copy(alpha = 0.15f)
                                ),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (correctCount >= 4) Color(0xFF10B981).copy(alpha = 0.4f) else Color(0xFFF59E0B).copy(alpha = 0.4f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (correctCount >= 4) "🎉 Ajoyib! $correctCount / ${currentUnit.exercises.size} to'g'ri!" else "📚 Urinish: $correctCount / ${currentUnit.exercises.size} to'g'ri",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = theme.textPrimary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (correctCount >= 4) "+50 Intizom Balli (XP) hisoblandi! Murphy Unit ${currentUnit.unitNumber} muvaffaqiyatli o'zlashtirildi." else "Yuqoridagi o'zbekcha izohlarni o'qib chiqing va xatolaringizni to'g'rilang.",
                                        fontSize = 11.sp,
                                        color = theme.textSecondary,
                                        lineHeight = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                selectedAnswers = emptyMap()
                                                showResults = false
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurfaceElevated),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Default.Refresh, contentDescription = null, tint = theme.textPrimary, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Qayta", color = theme.textPrimary, fontSize = 12.sp)
                                        }

                                        if (nextUnit != null) {
                                            Button(
                                                onClick = {
                                                    currentUnitNumber = nextUnit.unitNumber
                                                    selectedAnswers = emptyMap()
                                                    showResults = false
                                                    selectedTab = 0
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                                shape = RoundedCornerShape(10.dp),
                                                modifier = Modifier.weight(1.5f)
                                            ) {
                                                Text("Keyingi Unit ${nextUnit.unitNumber} ➔", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            }
                                        } else {
                                            Button(
                                                onClick = { showTableOfContents = true },
                                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                                shape = RoundedCornerShape(10.dp),
                                                modifier = Modifier.weight(1.5f)
                                            ) {
                                                Text("🏆 Mundarijaga qaytish", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Bottom navigation buttons (Oldingi Unit / Mundarija / Keyingi Unit)
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (previousUnit != null) {
                                OutlinedButton(
                                    onClick = {
                                        currentUnitNumber = previousUnit.unitNumber
                                        selectedAnswers = emptyMap()
                                        showResults = false
                                        selectedTab = 0
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Unit ${previousUnit.unitNumber}", fontSize = 11.sp)
                                }
                            } else {
                                Spacer(modifier = Modifier.width(1.dp))
                            }

                            Button(
                                onClick = { showTableOfContents = true },
                                colors = ButtonDefaults.buttonColors(containerColor = theme.glassSurfaceElevated),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.FormatListBulleted, contentDescription = null, tint = theme.textPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Mundarija", color = theme.textPrimary, fontSize = 11.sp)
                            }

                            if (nextUnit != null) {
                                OutlinedButton(
                                    onClick = {
                                        currentUnitNumber = nextUnit.unitNumber
                                        selectedAnswers = emptyMap()
                                        showResults = false
                                        selectedTab = 0
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Unit ${nextUnit.unitNumber}", fontSize = 11.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                                }
                            } else {
                                Spacer(modifier = Modifier.width(1.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
}

@Composable
fun TheorySectionCard(
    section: MurphySection,
    theme: LiquidGlassStyle,
    onSpeak: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1813)),
        border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtleColor),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Section Badge + Heading
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(theme.primaryAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        section.sectionCode,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = section.heading,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.textPrimary
                )
            }

            // Formula block if present
            section.formula?.let { formula ->
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.primaryAccent.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .border(1.dp, theme.primaryAccent.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = formula,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = theme.primaryAccent,
                        lineHeight = 16.sp
                    )
                }
            }

            // Explanation in Uzbek
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = section.explanationUzbek,
                fontSize = 11.sp,
                color = theme.textSecondary,
                lineHeight = 16.sp
            )

            // Examples List
            if (section.examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Misollar va amaliyot:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.textPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    section.examples.forEach { example ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(theme.glassSurface, RoundedCornerShape(8.dp))
                                .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = example.english,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = theme.primaryAccent
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = example.uzbek,
                                        fontSize = 11.sp,
                                        color = theme.textPrimary,
                                        lineHeight = 15.sp
                                    )
                                    example.note?.let { note ->
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "💡 $note",
                                            fontSize = 10.sp,
                                            color = Color(0xFFF59E0B),
                                            lineHeight = 14.sp
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = { onSpeak(example.english) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Tinglash",
                                        tint = theme.primaryAccent,
                                        modifier = Modifier.size(18.dp)
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

@Composable
fun ExerciseItemCard(
    index: Int,
    exercise: MurphyExerciseItem,
    selectedOptionIndex: Int?,
    showResults: Boolean,
    onOptionSelected: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    theme: LiquidGlassStyle
) {
    val isCorrect = selectedOptionIndex == exercise.correctOptionIndex

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1813)),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (showResults) {
                if (isCorrect) Color(0xFF10B981).copy(alpha = 0.6f) else Color(0xFFEF4444).copy(alpha = 0.6f)
            } else theme.glassBorderSubtleColor
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Exercise header + Question prompt
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Mashq ${exercise.exerciseNumber} (#$index)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.primaryAccent
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = exercise.question,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = theme.textPrimary,
                        lineHeight = 18.sp
                    )
                }
                IconButton(
                    onClick = { onSpeak(exercise.question.replace("______", "...")) },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "O'qish",
                        tint = theme.textSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Options
            exercise.options.forEachIndexed { optIdx, optText ->
                val isThisChosen = selectedOptionIndex == optIdx
                val isThisTheCorrectAnswer = optIdx == exercise.correctOptionIndex

                val chipBg = when {
                    showResults && isThisTheCorrectAnswer -> Color(0xFF10B981)
                    showResults && isThisChosen && !isThisTheCorrectAnswer -> Color(0xFFEF4444)
                    isThisChosen -> theme.primaryAccent
                    else -> theme.glassSurfaceElevated
                }

                val chipTextColor = when {
                    showResults && (isThisTheCorrectAnswer || (isThisChosen && !isThisTheCorrectAnswer)) -> Color.Black
                    isThisChosen -> Color.Black
                    else -> theme.textPrimary
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(chipBg)
                        .border(
                            1.dp,
                            if (isThisChosen) theme.primaryAccent else theme.glassBorderSubtleColor,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            if (!showResults) {
                                onOptionSelected(optIdx)
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${('A' + optIdx)}. $optText",
                            fontSize = 12.sp,
                            fontWeight = if (isThisChosen || (showResults && isThisTheCorrectAnswer)) FontWeight.Bold else FontWeight.Normal,
                            color = chipTextColor
                        )
                        if (showResults && isThisTheCorrectAnswer) {
                            Text(
                                text = "✅ To'g'ri javob",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        } else if (showResults && isThisChosen && !isThisTheCorrectAnswer) {
                            Text(
                                text = "❌ Xato",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            // Explanation after submission
            if (showResults) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isCorrect) Color(0xFF10B981).copy(alpha = 0.1f) else Color(0xFFEF4444).copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            if (isCorrect) Color(0xFF10B981).copy(alpha = 0.3f) else Color(0xFFEF4444).copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Murphy Qoidasi & Izoh:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = exercise.explanationUzbek,
                            fontSize = 11.sp,
                            color = theme.textSecondary,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MurphyTableOfContentsView(
    categories: List<MurphyGroupCategory>,
    currentUnitNumber: Int,
    completedUnitNumbers: Set<Int>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSelectUnit: (MurphyUnit) -> Unit,
    theme: LiquidGlassStyle,
    modifier: Modifier = Modifier
) {
    var expandedCategoryId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.bgTop)
    ) {
        // Search & Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Unit raqami yoki mavzu qidirish (masalan: past, do, have, can)...",
                        fontSize = 12.sp,
                        color = theme.textSecondary
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Qidirish",
                        tint = theme.primaryAccent,
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = theme.primaryAccent,
                    unfocusedBorderColor = theme.glassBorderSubtleColor,
                    focusedTextColor = theme.textPrimary,
                    unfocusedTextColor = theme.textPrimary,
                    focusedContainerColor = theme.glassSurface,
                    unfocusedContainerColor = theme.glassSurface
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Overall Progress Summary Card
            val allUnitsCount = categories.sumOf { it.units.size }
            val completedCount = completedUnitNumbers.size
            val progressPercent = if (allUnitsCount > 0) (completedCount * 100) / allUnitsCount else 0

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1813)),
                border = androidx.compose.foundation.BorderStroke(1.dp, theme.primaryAccent.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "📖 Essential Grammar in Use (Murphy)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Jami ${categories.size} ta bo'lim, $allUnitsCount ta to'liq tartiblangan darslar",
                            fontSize = 11.sp,
                            color = theme.textSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(theme.primaryAccent.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .border(1.dp, theme.primaryAccent, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$completedCount/$allUnitsCount ($progressPercent%)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.primaryAccent
                        )
                    }
                }
            }
        }

        // Filtered or Grouped Unit List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (searchQuery.isNotBlank()) {
                // Filtered flat list
                val filteredUnits = categories.flatMap { it.units }.filter { unit ->
                    unit.unitNumber.toString().contains(searchQuery.trim()) ||
                    unit.title.contains(searchQuery.trim(), ignoreCase = true) ||
                    unit.subtitleUzbek.contains(searchQuery.trim(), ignoreCase = true) ||
                    unit.groupName.contains(searchQuery.trim(), ignoreCase = true)
                }

                item {
                    Text(
                        text = "Qidiruv natijalari (${filteredUnits.size} ta unit topildi):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.primaryAccent,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(filteredUnits) { unit ->
                    UnitListItemCard(
                        unit = unit,
                        isCurrent = unit.unitNumber == currentUnitNumber,
                        isCompleted = completedUnitNumbers.contains(unit.unitNumber),
                        onSelect = { onSelectUnit(unit) },
                        theme = theme
                    )
                }
            } else {
                // Categorized Accordion Structure
                items(categories) { category ->
                    val isExpanded = expandedCategoryId == category.id || expandedCategoryId == null // Default: all viewable or accordion
                    val categoryCompletedCount = category.units.count { completedUnitNumbers.contains(it.unitNumber) }

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (category.units.any { it.unitNumber == currentUnitNumber }) category.color else theme.glassBorderSubtleColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            // Category Header Bar (Clickable to collapse/expand)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        expandedCategoryId = if (expandedCategoryId == category.id) -1 else category.id
                                    }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(category.color.copy(alpha = 0.2f), CircleShape)
                                            .border(1.dp, category.color, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(category.iconEmoji, fontSize = 16.sp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${category.id + 1}-Bo'lim: ${category.title}",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = theme.textPrimary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .background(category.color.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    category.rangeText,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = category.color
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = category.description,
                                            fontSize = 10.sp,
                                            color = theme.textSecondary,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (categoryCompletedCount > 0) {
                                        Text(
                                            text = "$categoryCompletedCount/${category.units.size}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = category.color
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Icon(
                                        if (expandedCategoryId == category.id || expandedCategoryId == null) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                        contentDescription = "Ochish/Yopish",
                                        tint = theme.textSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            // Units inside category
                            AnimatedVisibility(visible = expandedCategoryId == category.id || expandedCategoryId == null) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 12.dp, end = 12.dp, bottom = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    category.units.forEach { unit ->
                                        UnitListItemCard(
                                            unit = unit,
                                            isCurrent = unit.unitNumber == currentUnitNumber,
                                            isCompleted = completedUnitNumbers.contains(unit.unitNumber),
                                            onSelect = { onSelectUnit(unit) },
                                            theme = theme
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
}

@Composable
fun UnitListItemCard(
    unit: MurphyUnit,
    isCurrent: Boolean,
    isCompleted: Boolean,
    onSelect: () -> Unit,
    theme: LiquidGlassStyle
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) theme.primaryAccent.copy(alpha = 0.18f) else Color(0xFF141F1A)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCurrent) theme.primaryAccent else theme.glassBorderSubtleColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
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
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            if (isCurrent) theme.primaryAccent else Color(0xFF1F2E25),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${unit.unitNumber}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrent) Color.Black else theme.textPrimary
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = unit.title,
                        fontSize = 12.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                        color = if (isCurrent) theme.primaryAccent else theme.textPrimary
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = unit.subtitleUzbek,
                        fontSize = 10.sp,
                        color = theme.textSecondary,
                        maxLines = 1
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isCompleted) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Bajarildi",
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(18.dp)
                    )
                } else if (isCurrent) {
                    Box(
                        modifier = Modifier
                            .background(theme.primaryAccent, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "O'qilmoqda",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Boshlash",
                        tint = theme.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
