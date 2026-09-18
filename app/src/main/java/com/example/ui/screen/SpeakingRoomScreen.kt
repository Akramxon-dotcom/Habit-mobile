package com.example.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.data.remote.GeminiClient
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SpeakingMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "USER" or "AI"
    val text: String,
    val feedbackUzbek: String? = null,
    val shadowingPhrase: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

data class SpeakingScenario(
    val id: String,
    val title: String,
    val iconEmoji: String,
    val levelBadge: String = "A1-A2",
    val initialAiGreeting: String,
    val quickReplies: List<String>,
    val systemPrompt: String,
    val fallbackResponses: List<Triple<String, String, String>> = emptyList()
)

object SpeakingScenarios {
    val ALL = listOf(
        SpeakingScenario(
            id = "routine",
            title = "Kun tartibi",
            iconEmoji = "⏰",
            levelBadge = "A1 Basic",
            initialAiGreeting = "Hello Akramjon! Tell me about your morning. What time do you usually wake up every day?",
            quickReplies = listOf(
                "I usually wake up at 6:30 in the morning.",
                "I drink a glass of water and do light exercise.",
                "I have breakfast with my family at 7:30.",
                "I leave home for work at 8 o'clock."
            ),
            systemPrompt = "You are a friendly British/American English tutor talking with an Uzbek learner at A2 Elementary level. Theme: Daily routine (Present Simple). Keep responses short (2-3 sentences), simple vocabulary. If user has mistakes, provide feedback in Uzbek briefly.",
            fallbackResponses = listOf(
                Triple(
                    "That is a healthy routine! Do you usually take a shower or drink coffee after you wake up?",
                    "Ajoyib! Present Simple (har kungi odat) zamonidan juda to'g'ri foydalandingiz.",
                    "That is a very healthy routine!"
                ),
                Triple(
                    "Sounds wonderful! And what time do you usually have dinner in the evening?",
                    "Juda yaxshi. 'At 7:30' kabi soat oldidan 'at' predlogi ishlatiladi.",
                    "What time do you usually have dinner?"
                )
            )
        ),
        SpeakingScenario(
            id = "past_day",
            title = "Kechagi kun",
            iconEmoji = "📅",
            levelBadge = "A2 Past",
            initialAiGreeting = "Hi there! What did you do yesterday? Did you study English, work, or meet friends?",
            quickReplies = listOf(
                "Yesterday I studied English and did my homework.",
                "I met my best friend and we had lunch together.",
                "I stayed at home and read an interesting book.",
                "I went for a walk in the park yesterday evening."
            ),
            systemPrompt = "Theme: Past events (Past Simple). Level: A2 Elementary. Ask simple questions in past simple. If user uses wrong verb forms (e.g. 'I see' instead of 'I saw'), point it out politely in Uzbek.",
            fallbackResponses = listOf(
                Triple(
                    "Well done! It is great that you practiced. Did you learn any new words yesterday?",
                    "Kechagi ish-harakatlar uchun o'tgan zamon fe'llari (studied, did, met) to'g'ri tanlandi.",
                    "Did you learn any new words yesterday?"
                ),
                Triple(
                    "That sounds relaxing! Did you enjoy your time yesterday?",
                    "O'tgan zamonda so'roq berishda 'Did you...?' yordamchi fe'lidan foydalaniladi.",
                    "That sounds very relaxing!"
                )
            )
        ),
        SpeakingScenario(
            id = "cafe",
            title = "Kafeda buyurtma",
            iconEmoji = "☕",
            levelBadge = "A2 Practical",
            initialAiGreeting = "Welcome to Star Cafe! What can I get for you today?",
            quickReplies = listOf(
                "Can I have a hot cappuccino with milk, please?",
                "How much is this fresh chocolate croissant?",
                "Takeaway, please. Can I pay by card?",
                "Just a cup of green tea, thank you."
            ),
            systemPrompt = "Theme: Ordering food and drinks at a cafe. Roleplay as the barista. Use simple phrases: 'Would you like...', 'Anything else?', 'That is £3.50'.",
            fallbackResponses = listOf(
                Triple(
                    "Certainly! That is £3.50. Would you like anything sweet with your drink?",
                    "Kafeda buyurtma berishda 'Can I have...' yoki 'I would like...' eng xushmuomala usuldir.",
                    "Would you like anything sweet with your drink?"
                ),
                Triple(
                    "Here is your hot coffee! Careful, it is quite hot. Have a great day!",
                    "Pul to'lashda 'Can I pay by card?' (Karta orqali to'lasam bo'ladimi?) iborasi juda tabiiy.",
                    "Here is your hot coffee!"
                )
            )
        ),
        SpeakingScenario(
            id = "airport",
            title = "Aeroportda ro'yxatdan o'tish",
            iconEmoji = "✈️",
            levelBadge = "A2 Travel",
            initialAiGreeting = "Good day! May I see your passport and flight ticket, please?",
            quickReplies = listOf(
                "Here is my passport and boarding pass.",
                "I have only one suitcase and one carry-on bag.",
                "Could I have a window seat, please?",
                "What gate does this flight depart from?"
            ),
            systemPrompt = "Theme: Airport check-in and travel English. Roleplay as airline desk agent. Simple, practical travel phrases.",
            fallbackResponses = listOf(
                Triple(
                    "Thank you! Everything looks perfect. Here is your boarding pass. Gate 12, boarding starts in 40 minutes.",
                    "Sayohatda 'Here is my passport' (Mana pasportim) va 'window seat' (deraza yonidagi o'rindiq) eng kerakli jumlalar.",
                    "Here is your boarding pass."
                )
            )
        ),
        SpeakingScenario(
            id = "shopping",
            title = "Kiyim do'koni",
            iconEmoji = "🛍️",
            levelBadge = "A2 Shopping",
            initialAiGreeting = "Hello! Can I help you find something today, sir?",
            quickReplies = listOf(
                "I am looking for a warm blue jacket.",
                "Do you have this t-shirt in size Medium?",
                "Can I try this shirt on in the fitting room?",
                "Is there any discount on these shoes?"
            ),
            systemPrompt = "Theme: Shopping clothes. Roleplay as friendly store assistant. Guide user to practice shopping phrases and sizes.",
            fallbackResponses = listOf(
                Triple(
                    "Yes, of course! The fitting rooms are right around the corner on your left. Let me know how it fits.",
                    "'Can I try this on?' (Kiyib ko'rsam bo'ladimi?) xarid paytida eng mashhur ibora.",
                    "The fitting rooms are right on your left."
                )
            )
        ),
        SpeakingScenario(
            id = "future",
            title = "Kelajak rejalari",
            iconEmoji = "🚀",
            levelBadge = "A2 Grammar",
            initialAiGreeting = "Hello! What are you going to do this coming weekend?",
            quickReplies = listOf(
                "I am going to visit my grandparents this weekend.",
                "I will practice English speaking for three hours.",
                "I plan to play football with my friends on Sunday.",
                "I am going to watch an interesting English movie."
            ),
            systemPrompt = "Theme: Future plans (will / going to). Level: A2 Elementary. Guide them to use 'I am going to...' or 'I will...'.",
            fallbackResponses = listOf(
                Triple(
                    "That sounds like a wonderful plan! Who are you going with?",
                    "Rejalashtirilgan ishlar uchun 'I am going to...' (Niyat qildim) juda to'g'ri ishlatildi.",
                    "That sounds like a wonderful plan!"
                )
            )
        ),
        SpeakingScenario(
            id = "job_interview",
            title = "Ish suhbati (Job Interview)",
            iconEmoji = "💼",
            levelBadge = "A2-B1 Pro",
            initialAiGreeting = "Hello and welcome! Could you please introduce yourself and tell me about your background?",
            quickReplies = listOf(
                "My name is Akramjon. I am dedicated and hard-working.",
                "I have been learning English and improving my skills every day.",
                "I enjoy solving problems and working in a team.",
                "My strength is that I always finish tasks on time."
            ),
            systemPrompt = "Theme: Elementary job interview. Simple professional questions: hobbies, skills, strengths.",
            fallbackResponses = listOf(
                Triple(
                    "Very impressive! Why are you interested in improving your English skills specifically?",
                    "O'zini tanishtirishda 'dedicated' (fidoyi) va 'hard-working' (mehnatkash) sifatlari juda mos tushdi.",
                    "I enjoy solving problems and working in a team."
                )
            )
        ),
        SpeakingScenario(
            id = "free_talk",
            title = "Erkin suhbat (Free Talk)",
            iconEmoji = "💬",
            levelBadge = "A2 All Levels",
            initialAiGreeting = "Hello Akramjon! How is your day going? Feel free to speak in English or even Uzbek—I am here to guide you!",
            quickReplies = listOf(
                "My vocabulary is growing day by day thanks to Murphy units.",
                "Can you help me improve my speaking confidence?",
                "What is the best way to practice speaking every day?",
                "I want to practice real conversational English with you."
            ),
            systemPrompt = "Theme: Friendly open conversation. Level: A2. Keep language clean, supportive, simple. If user speaks Uzbek, translate and explain.",
            fallbackResponses = listOf(
                Triple(
                    "I am delighted to practice with you! Consistency is key. Even speaking for 10 minutes every day makes a huge difference. What topic would you like to discuss next?",
                    "Erkin suhbatda xatodan qo'rqmaslik eng muhimi! Sizning nutqingiz ravonlashib bormoqda.",
                    "Consistency is key to speaking fluently."
                )
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakingRoomScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val theme = LocalLiquidTheme.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val speechManager = remember { SpeechManager(context) }
    val listState = rememberLazyListState()

    var selectedScenarioIndex by remember { mutableIntStateOf(0) }
    val currentScenario = SpeakingScenarios.ALL[selectedScenarioIndex]

    var messages by remember {
        mutableStateOf(
            listOf(
                SpeakingMessage(
                    sender = "AI",
                    text = currentScenario.initialAiGreeting,
                    shadowingPhrase = currentScenario.initialAiGreeting
                )
            )
        )
    }

    var inputText by remember { mutableStateOf("") }
    var isAiGenerating by remember { mutableStateOf(false) }
    var speechRate by remember { mutableFloatStateOf(0.85f) }
    var isLiveModeActive by remember { mutableStateOf(false) }
    var userStatusNotice by remember { mutableStateOf<String?>(null) }
    var shadowingMatchScore by remember { mutableStateOf<Pair<String, Int>?>(null) }

    val isListening by speechManager.isListening.collectAsState()
    val isSpeaking by speechManager.isSpeaking.collectAsState()
    val partialSpokenText by speechManager.partialText.collectAsState()
    val rmsLevel by speechManager.rmsLevel.collectAsState()
    val lastError by speechManager.lastError.collectAsState()

    // Pulse animation for recording wave
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    var sendMessageAction by remember { mutableStateOf<(String) -> Unit>({}) }
    var startListeningSafelyAction by remember { mutableStateOf<() -> Unit>({}) }

    // System Voice Input Intent Fallback (Universal Android Speech Dialog)
    val speechIntentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenMatches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spoken = spokenMatches?.firstOrNull() ?: ""
            if (spoken.isNotBlank()) {
                inputText = spoken
                userStatusNotice = "Ovoz qabul qilindi: \"$spoken\""
                if (isLiveModeActive) {
                    sendMessageAction(spoken)
                }
            }
        } else {
            userStatusNotice = "Ovozli kiritish bekor qilindi"
        }
    }

    fun launchSystemSpeechDialog() {
        try {
            val intent = speechManager.createSpeechIntent()
            speechIntentLauncher.launch(intent)
        } catch (e: Exception) {
            userStatusNotice = "Tizim ovozli oynasi ochilmadi: ${e.message}"
        }
    }

    fun triggerSpeechListening(onSpoken: (String) -> Unit) {
        userStatusNotice = "🎙️ Tinglanmoqda... Marhamat, inglizcha gapiring"
        speechManager.startListening(
            onResult = { spokenText ->
                if (spokenText.isNotBlank()) {
                    userStatusNotice = null
                    onSpoken(spokenText)
                }
            },
            onError = { errMsg ->
                userStatusNotice = errMsg
                // If background recognition failed, we can seamlessly offer system dialog
            },
            onPartial = { partial ->
                // Show real-time streaming speech
                if (partial.isNotBlank()) {
                    userStatusNotice = "🎙️ \"$partial...\""
                }
            }
        )
    }

    // Permission launcher for microphone
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            triggerSpeechListening { spoken ->
                inputText = spoken
                if (isLiveModeActive) {
                    sendMessageAction(spoken)
                }
            }
        } else {
            userStatusNotice = "Mikrofon ruxsati berilmadi. Sozlamalardan ruxsat bering."
        }
    }

    fun startListeningSafely() {
        val hasRecordPerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (hasRecordPerm) {
            triggerSpeechListening { spoken ->
                inputText = spoken
                if (isLiveModeActive) {
                    sendMessageAction(spoken)
                }
            }
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    startListeningSafelyAction = { startListeningSafely() }

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return
        val trimmed = userText.trim()
        inputText = ""
        userStatusNotice = null

        val userMsg = SpeakingMessage(sender = "USER", text = trimmed)
        messages = messages + userMsg

        isAiGenerating = true
        scope.launch {
            val history = messages.takeLast(6).joinToString("\n") { "${it.sender}: ${it.text}" }
            val prompt = """
                You are an expert, encouraging A2 Elementary English speaking mentor conversing with Akramjon.
                Topic context: ${currentScenario.title}.
                Scenario details: ${currentScenario.systemPrompt}
                Conversation history:
                $history
                User just said: "$trimmed"
                
                CRITICAL INSTRUCTIONS:
                1. NEVER output JSON, category, or schedule formats.
                2. Akramjon may speak English OR Uzbek. If he speaks Uzbek, understand his intention, respond in simple conversational A2 English (2-3 sentences), and in [FEEDBACK_UZ] explain how to say that properly in English!
                3. Always reply in this exact 3-part format:
                [RESPONSE]: (Your next conversational reply in friendly 2-3 sentence A2 English)
                [FEEDBACK_UZ]: (Short feedback in Uzbek about grammar, pronunciation or how to say it in English)
                [SHADOWING]: (1 natural, useful English sentence from your reply for the user to repeat aloud)
            """.trimIndent()

            val rawResult = GeminiClient.generateText(prompt, context).getOrNull() ?: ""

            // Strict sanitization - eliminate any JSON leak from old cached calls or quota limits
            val aiResult = if (rawResult.isBlank() || rawResult.contains("quota", ignoreCase = true) || rawResult.contains("RESOURCE_EXHAUSTED", ignoreCase = true) || rawResult.contains("{\"category") || rawResult.contains("\"start\":") || rawResult.contains("\"placement\":")) {
                // Smart Scenario-based fallback so user never gets stuck even if offline or quota limit hit!
                val fallbacks = currentScenario.fallbackResponses
                val fallbackItem = if (fallbacks.isNotEmpty()) {
                    val turnIndex = (messages.count { it.sender == "USER" } - 1).coerceAtLeast(0) % fallbacks.size
                    fallbacks[turnIndex]
                } else {
                    Triple(
                        "I understand what you mean! That is great practice. Can you tell me more about that?",
                        "Gapingiz qabul qilindi. Har kuni 5-10 daqiqa baland ovozda takrorlash nutq ravonligini oshiradi.",
                        "That is great practice!"
                    )
                }
                """
                [RESPONSE]: ${fallbackItem.first}
                [FEEDBACK_UZ]: ${fallbackItem.second}
                [SHADOWING]: ${fallbackItem.third}
                """.trimIndent()
            } else {
                rawResult
            }

            var replyText = "That sounds interesting! Can you tell me more about that?"
            var feedbackUz = "Ajoyib, gap to'g'ri!"
            var shadowing = "That sounds interesting!"

            if (aiResult.contains("[RESPONSE]:")) {
                val respPart = aiResult.substringAfter("[RESPONSE]:").substringBefore("[FEEDBACK_UZ]:").trim()
                val feedPart = aiResult.substringAfter("[FEEDBACK_UZ]:").substringBefore("[SHADOWING]:").trim()
                val shadowPart = aiResult.substringAfter("[SHADOWING]:").trim()
                if (respPart.isNotBlank()) replyText = respPart
                if (feedPart.isNotBlank()) feedbackUz = feedPart
                if (shadowPart.isNotBlank()) shadowing = shadowPart
            } else if (aiResult.isNotBlank()) {
                replyText = aiResult.replace(Regex("[\\{\\}\"']"), "").take(250)
                shadowing = replyText
            }

            val aiMsg = SpeakingMessage(
                sender = "AI",
                text = replyText,
                feedbackUzbek = feedbackUz,
                shadowingPhrase = shadowing
            )
            messages = messages + aiMsg
            isAiGenerating = false

            // Auto-speak the AI response
            speechManager.speak(replyText, speechRate) {
                // If Live Mode is active, smoothly resume listening after AI finishes!
                if (isLiveModeActive) {
                    scope.launch {
                        delay(600) // comfortable pause for user
                        startListeningSafelyAction()
                    }
                }
            }

            // Scroll to bottom
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    sendMessageAction = { text -> sendMessage(text) }

    DisposableEffect(Unit) {
        // Speak initial greeting once screen opens
        speechManager.speak(currentScenario.initialAiGreeting, speechRate)
        onDispose {
            speechManager.destroy()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "🎙️ Speaking & Shadowing",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textPrimary
                            )
                            if (isLiveModeActive) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color(0xFFEF4444), CircleShape)
                                )
                            }
                        }
                        Text(
                            when {
                                isSpeaking -> "🔊 AI gapirmoqda..."
                                isListening -> "🎙️ Sizni tinglamoqda... (gapiring)"
                                isAiGenerating -> "⏳ AI javob tayyorlamoqda..."
                                isLiveModeActive -> "🔴 Jonli suhbat faol (Gemini Live)"
                                else -> "Gemini A2 Repetitor"
                            },
                            fontSize = 11.sp,
                            fontWeight = if (isSpeaking || isListening || isLiveModeActive) FontWeight.Bold else FontWeight.Normal,
                            color = when {
                                isListening || isLiveModeActive -> Color(0xFFEF4444)
                                isSpeaking -> theme.primaryAccent
                                else -> theme.textSecondary
                            }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        speechManager.stopSpeaking()
                        speechManager.stopListening()
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Orqaga", tint = theme.textPrimary)
                    }
                },
                actions = {
                    // Gemini Live Mode Toggle
                    Box(
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .background(
                                if (isLiveModeActive) Color(0xFFEF4444).copy(alpha = 0.25f) else theme.glassSurface,
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                1.5.dp,
                                if (isLiveModeActive) Color(0xFFEF4444) else theme.glassBorderSubtleColor,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                isLiveModeActive = !isLiveModeActive
                                if (isLiveModeActive) {
                                    userStatusNotice = "🔴 Jonli muloqot boshlandi! AI bilan navbatma-navbat erkin suhbatlashing."
                                    startListeningSafely()
                                } else {
                                    speechManager.stopListening()
                                    userStatusNotice = "Jonli muloqot to'xtatildi."
                                }
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                if (isLiveModeActive) "🔴 LIVE ON" else "⚪ LIVE OFF",
                                fontSize = 11.sp,
                                color = if (isLiveModeActive) Color(0xFFEF4444) else theme.textPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Speech rate button (0.75x -> 0.85x -> 1.0x)
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(theme.glassSurface, RoundedCornerShape(10.dp))
                            .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(10.dp))
                            .clickable {
                                speechRate = when (speechRate) {
                                    0.85f -> 1.0f
                                    1.0f -> 0.75f
                                    else -> 0.85f
                                }
                            }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text("${speechRate}x Tezlik", fontSize = 11.sp, color = theme.textPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = theme.bgTop)
            )
        },
        containerColor = theme.bgTop,
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Live Mode Status Banner when active
            AnimatedVisibility(visible = isLiveModeActive, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFEF4444).copy(alpha = 0.2f), Color(0xFF3B82F6).copy(alpha = 0.2f))
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .scale(pulseScale)
                                    .background(Color(0xFFEF4444), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Jonli Suhbat Rejimi: AI javob bergach mikrofon o'zi ochiladi",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = theme.textPrimary
                            )
                        }
                    }
                }
            }

            // Scenario Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(SpeakingScenarios.ALL.indices.toList()) { idx ->
                    val scenario = SpeakingScenarios.ALL[idx]
                    val isSelected = idx == selectedScenarioIndex
                    Box(
                        modifier = Modifier
                            .background(
                                if (isSelected) theme.primaryAccent else theme.glassSurface,
                                RoundedCornerShape(14.dp)
                            )
                            .border(
                                1.dp,
                                if (isSelected) theme.primaryAccent else theme.glassBorderSubtleColor,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                selectedScenarioIndex = idx
                                speechManager.stopSpeaking()
                                speechManager.stopListening()
                                messages = listOf(
                                    SpeakingMessage(
                                        sender = "AI",
                                        text = scenario.initialAiGreeting,
                                        shadowingPhrase = scenario.initialAiGreeting
                                    )
                                )
                                speechManager.speak(scenario.initialAiGreeting, speechRate)
                            }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(scenario.iconEmoji, fontSize = 15.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    scenario.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.Black else theme.textPrimary
                                )
                                Text(
                                    scenario.levelBadge,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isSelected) Color.Black.copy(alpha = 0.7f) else theme.primaryAccent
                                )
                            }
                        }
                    }
                }
            }

            // Notification / Status Banner (if error or partial speech)
            if (userStatusNotice != null || lastError != null) {
                val displayMsg = userStatusNotice ?: lastError ?: ""
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .background(theme.primaryAccent.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = displayMsg,
                            fontSize = 11.sp,
                            color = theme.textPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        // Button to launch system speech popup if needed
                        Text(
                            text = "Oyna 🗣️",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.primaryAccent,
                            modifier = Modifier
                                .clickable { launchSystemSpeechDialog() }
                                .padding(start = 8.dp)
                        )
                    }
                }
            }

            // Message List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { msg ->
                    if (msg.sender == "USER") {
                        // User message bubble (Right aligned)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Card(
                                shape = RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp),
                                colors = CardDefaults.cardColors(containerColor = theme.primaryAccent),
                                modifier = Modifier.fillMaxWidth(0.85f)
                            ) {
                                Text(
                                    text = msg.text,
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    } else {
                        // AI message card (Left aligned) with feedback & shadowing
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Card(
                                shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp),
                                colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtleColor),
                                modifier = Modifier.fillMaxWidth(0.94f)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("🤖", fontSize = 16.sp)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                "Gemini A2 Mentor",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = theme.primaryAccent
                                            )
                                        }

                                        Row {
                                            // Replay voice button
                                            IconButton(
                                                onClick = { speechManager.speak(msg.text, speechRate) },
                                                modifier = Modifier.size(30.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.PlayArrow,
                                                    contentDescription = "Ovozni qayta eshitish",
                                                    tint = theme.primaryAccent,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = msg.text,
                                        fontSize = 14.sp,
                                        color = theme.textPrimary,
                                        lineHeight = 20.sp
                                    )

                                    // Feedback in Uzbek if available
                                    if (!msg.feedbackUzbek.isNullOrBlank()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(theme.primaryAccent.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                                                .padding(8.dp)
                                        ) {
                                            Column {
                                                Text(
                                                    "✍️ Tahlil & Maslahat:",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = theme.primaryAccent
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Text(
                                                    msg.feedbackUzbek,
                                                    fontSize = 11.sp,
                                                    color = theme.textSecondary,
                                                    lineHeight = 15.sp
                                                )
                                            }
                                        }
                                    }

                                    // Shadowing practice card
                                    if (!msg.shadowingPhrase.isNullOrBlank()) {
                                        val shadowText = msg.shadowingPhrase
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    Brush.horizontalGradient(
                                                        listOf(
                                                            theme.primaryAccent.copy(alpha = 0.12f),
                                                            theme.glassSurfaceElevated
                                                        )
                                                    ),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .border(1.dp, theme.primaryAccent.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                                .padding(10.dp)
                                        ) {
                                            Column {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text("🗣️", fontSize = 13.sp)
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Text(
                                                            "SHADOWING & TALAFUZ:",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = theme.primaryAccent
                                                        )
                                                    }

                                                    // Speed listening button
                                                    Box(
                                                        modifier = Modifier
                                                            .background(theme.glassSurface, RoundedCornerShape(6.dp))
                                                            .clickable { speechManager.speak(shadowText, 0.70f) }
                                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                                    ) {
                                                        Text("🐢 Sekin eshitish", fontSize = 9.sp, color = theme.textSecondary, fontWeight = FontWeight.Bold)
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    "\"$shadowText\"",
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = theme.textPrimary,
                                                    lineHeight = 18.sp
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    // Eshitish tugmasi
                                                    Box(
                                                        modifier = Modifier
                                                            .background(theme.glassSurface, RoundedCornerShape(8.dp))
                                                            .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(8.dp))
                                                            .clickable { speechManager.speak(shadowText, speechRate) }
                                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = theme.primaryAccent, modifier = Modifier.size(14.dp))
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                            Text("Eshitish", fontSize = 11.sp, color = theme.textPrimary, fontWeight = FontWeight.Medium)
                                                        }
                                                    }

                                                    // Talaffuzni tekshirish tugmasi
                                                    Box(
                                                        modifier = Modifier
                                                            .background(theme.primaryAccent, RoundedCornerShape(8.dp))
                                                            .clickable {
                                                                userStatusNotice = "🗣️ Tinglanmoqda! Qaytaring: \"$shadowText\""
                                                                triggerSpeechListening { spoken ->
                                                                    val cleanTarget = shadowText.lowercase().replace(Regex("[^a-z0-9 ]"), "")
                                                                    val cleanSpoken = spoken.lowercase().replace(Regex("[^a-z0-9 ]"), "")
                                                                    val targetWords = cleanTarget.split(" ").filter { it.isNotBlank() }
                                                                    val spokenWords = cleanSpoken.split(" ").filter { it.isNotBlank() }
                                                                    val matchesCount = targetWords.count { spokenWords.contains(it) }
                                                                    val percent = ((matchesCount.toFloat() / targetWords.size.coerceAtLeast(1)) * 100).toInt().coerceIn(35, 100)
                                                                    shadowingMatchScore = Pair(spoken, percent)
                                                                }
                                                            }
                                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                                    ) {
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Text("🎙️", fontSize = 12.sp)
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                            Text(
                                                                "Ovozimni tekshir",
                                                                fontSize = 11.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.Black
                                                            )
                                                        }
                                                    }
                                                }

                                                // Shadowing score feedback if just practiced
                                                if (shadowingMatchScore != null && msg.id == messages.lastOrNull { it.sender == "AI" }?.id) {
                                                    val score = shadowingMatchScore?.second ?: 0
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .background(
                                                                if (score >= 70) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFF59E0B).copy(alpha = 0.15f),
                                                                RoundedCornerShape(8.dp)
                                                            )
                                                            .padding(8.dp)
                                                    ) {
                                                        Column {
                                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                                Text(if (score >= 70) "🎉 Ajoyib talaffuz!" else "⚡ Yaxshi harakat, yana bir bor urinib ko'ring!", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (score >= 70) Color(0xFF10B981) else Color(0xFFF59E0B))
                                                                Spacer(modifier = Modifier.weight(1f))
                                                                Text("$score% aniqlik", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = if (score >= 70) Color(0xFF10B981) else Color(0xFFF59E0B))
                                                            }
                                                            Spacer(modifier = Modifier.height(2.dp))
                                                            Text(
                                                                "Siz aytdingiz: \"${shadowingMatchScore?.first}\"",
                                                                fontSize = 10.sp,
                                                                color = theme.textSecondary
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
                }

                if (isAiGenerating) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {
                            CircularProgressIndicator(
                                color = theme.primaryAccent,
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("AI o'ylamoqda va ovozlashtirmoqda...", fontSize = 11.sp, color = theme.textSecondary)
                        }
                    }
                }
            }

            // Quick Spoken Phrases suggestions (so user never gets stuck!)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    "💡 Tayyor javob variantlari (bosing va ayting):",
                    fontSize = 10.sp,
                    color = theme.textSecondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(currentScenario.quickReplies) { phrase ->
                        Box(
                            modifier = Modifier
                                .background(theme.glassSurface, RoundedCornerShape(12.dp))
                                .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(12.dp))
                                .clickable {
                                    inputText = phrase
                                    if (isLiveModeActive) {
                                        sendMessage(phrase)
                                    }
                                }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                phrase,
                                fontSize = 11.sp,
                                color = theme.textPrimary
                            )
                        }
                    }
                }
            }

            // Live Sound Wave indicator when listening or speaking
            AnimatedVisibility(visible = isListening || isSpeaking, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .background(
                            if (isListening) Color(0xFFEF4444).copy(alpha = 0.15f) else theme.primaryAccent.copy(alpha = 0.15f),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            if (isListening) "🔴 Mikrofon faol: erkin gapiring..." else "🔊 AI javob bermoqda...",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isListening) Color(0xFFEF4444) else theme.primaryAccent
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(0.4f, 0.9f, 0.6f, 1.0f, 0.7f, 0.4f).forEachIndexed { i, heightFactor ->
                                val waveHeight = ((12.dp * (if (isListening) pulseScale else 1f) * heightFactor).coerceIn(4.dp, 22.dp))
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .height(waveHeight)
                                        .background(
                                            if (isListening) Color(0xFFEF4444) else theme.primaryAccent,
                                            RoundedCornerShape(2.dp)
                                        )
                                )
                            }
                        }
                    }
                }
            }

            // Input Bar with Mic & Send
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, theme.glassBorderSubtleColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Microphone button with pulse animation when listening
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .scale(if (isListening) pulseScale else 1f)
                            .background(
                                if (isListening) Color(0xFFEF4444) else theme.primaryAccent.copy(alpha = 0.25f),
                                CircleShape
                            )
                            .clickable {
                                if (isListening) {
                                    speechManager.stopListening()
                                } else {
                                    startListeningSafely()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (isListening) "🛑" else "🎙️",
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Text Field
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                if (isListening) "Gapiring, inglizcha yozilmoqda..." else "Inglizcha gap yozing yoki mikrofonga ayting...",
                                fontSize = 11.sp,
                                color = theme.textSecondary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = theme.primaryAccent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = theme.textPrimary,
                            unfocusedTextColor = theme.textPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // System Speech Dialog direct button (universal fallback)
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(theme.glassSurface, CircleShape)
                            .border(1.dp, theme.glassBorderSubtleColor, CircleShape)
                            .clickable {
                                launchSystemSpeechDialog()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🗣️", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Send Button
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(theme.primaryAccent, CircleShape)
                            .clickable {
                                sendMessage(inputText)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Yuborish",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
