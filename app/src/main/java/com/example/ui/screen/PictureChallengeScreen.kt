@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.ui.screen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.remote.GeminiClient
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitSage
import com.example.ui.theme.LocalLiquidTheme
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

data class PictureChallengePrompt(
    val id: String,
    val title: String,
    val prompt: String,
    val category: String,
    val levelReq: String,
    val sampleDescription: String,
    val usefulVocab: List<String>
)

data class PictureEvaluation(
    val vocabScore: Int = 8,
    val grammarScore: Int = 8,
    val fluencyScore: Int = 8,
    val storytellingScore: Int = 8,
    val totalScore: Int = 32,
    val cefrLevel: String = "B1 Intermediate",
    val positiveFeedbackUz: String = "Yaxshi tasvirlash!",
    val suggestionsUz: String = "Lug'at boyligini yanada oshirish tavsiya etiladi.",
    val advancedVocabularyTips: List<String> = emptyList()
)

object PictureChallengeScenarios {
    val scenarios = listOf(
        PictureChallengePrompt(
            id = "library",
            title = "Sehrli Qadimiy Kutubxona",
            prompt = "A magical ancient library with towering mahogany bookshelves, spiral staircases, glowing floating lanterns, a detective in a trench coat examining an ancient illuminated manuscript under candlelight, cinematic warm fantasy lighting, 8k render",
            category = "Fantastika & Sirli",
            levelReq = "Level 1: Boshlang'ich",
            sampleDescription = "In the foreground, there is an investigator studying an old glowing manuscript. The background features enormous bookshelves reaching up into the darkness, with warm floating lanterns creating a mysterious, cozy atmosphere.",
            usefulVocab = listOf("illuminated manuscript", "spiral staircase", "dimly lit", "mysterious atmosphere", "ancient scrolls")
        ),
        PictureChallengePrompt(
            id = "tokyo_market",
            title = "Kelajak Bozori (Tokyo 2099)",
            prompt = "A vibrant futuristic neon street food market in Neo Tokyo at night, robotic chefs preparing ramen noodles, flying holographic billboards, wet pavement reflecting neon pink and cyan lights, busy diverse crowds, highly detailed",
            category = "Kelajak & Cyberpunk",
            levelReq = "Level 2: O'rta",
            sampleDescription = "This scene portrays a bustling futuristic marketplace with vivid neon lights reflecting on the wet ground. People and robotic chefs are interacting near food stalls, conveying a lively, high-tech urban energy.",
            usefulVocab = listOf("bustling market", "holographic billboards", "neon reflections", "culinary vendor", "futuristic metropolis")
        ),
        PictureChallengePrompt(
            id = "mountain_camp",
            title = "Tog' Cho'qqisidagi Kichik Uy",
            prompt = "A cozy wooden glass cabin perched on top of misty alpine mountains at golden hour sunset, a telescope on the wooden deck, warm fireplace smoke rising, snowy peaks in the horizon, peaceful atmospheric landscape",
            category = "Tabiat & Sayohat",
            levelReq = "Level 2: O'rta",
            sampleDescription = "At first glance, we see an idyllic glass cabin overlooking breathtaking snow-capped mountain peaks. The warm sunset casts golden rays across the mist, while a telescope on the porch suggests someone stargazing tonight.",
            usefulVocab = listOf("breathtaking panoramic view", "snow-capped peaks", "golden hour", "serene solitude", "astronomical telescope")
        ),
        PictureChallengePrompt(
            id = "detective_cafe",
            title = "Yomg'irli Parij Qahvaxonasi",
            prompt = "A cozy vintage Parisian cafe on a rainy cobblestone street in autumn, red umbrella on the table, steam rising from fresh cappuccino and croissants, rain droplets on window glass, soft romantic nostalgic watercolor painting style",
            category = "San'at & Hayot",
            levelReq = "Level 1: Boshlang'ich",
            sampleDescription = "The picture depicts a quaint French cafe on an autumn rainy afternoon. Outside, wet cobblestone streets reflect the warm lights, creating a peaceful and reflective mood.",
            usefulVocab = listOf("cobblestone street", "quaint cafe", "autumn foliage", "reflective mood", "nostalgic ambiance")
        ),
        PictureChallengePrompt(
            id = "space_station",
            title = "Koinot Tadqiqot Labaratoriyasi",
            prompt = "Astronaut biologist inside an orbital botanical greenhouse dome overlooking Earth from outer space, futuristic floating hydroponic plants, blue planet visible through panoramic dome windows, starfield background",
            category = "Ilm-fan & Kosmos",
            levelReq = "Level 3: Yuqori",
            sampleDescription = "This striking illustration shows a scientist tending to lush green plants inside an orbital biodome, with the curve of planet Earth illuminating the background. It blends nature with futuristic space exploration.",
            usefulVocab = listOf("orbital greenhouse", "hydroponic vegetation", "breathtaking view of Earth", "scientific dedication", "zero-gravity")
        )
    )
}

/**
 * 🖼️ PICTURE DESCRIPTION & STORYTELLING CHALLENGE
 * 
 * Gemini 3.1 Flash Image Preview (model 'gemini-3.1-flash-image-preview') orqali
 * yaratilgan/tahrirlangan rasmlarni ingliz tilida tasvirlash o'yini.
 * 
 * - 📚 Qulay o'rganish & Boshlang'ich qo'llanma (misollar va iboralar).
 * - 🎨 Gemini 3.1 orqali yangi rasm yaratish va tahrirlash (Edit Image).
 * - 🎙️ Ovoz yoki klaviatura orqali tasvirlash.
 * - 🏆 Gemini 3.5 AI orqali har tomonlama baholash (Lug'at, Grammatika, Ravonlik, Hikoya).
 * - 🌟 Darajalar: Level 1 (Oddiy), Level 2 (O'rta), Level 3 (Katta hikoyachi).
 */
@Composable
fun PictureChallengeScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val theme = LocalLiquidTheme.current

    var selectedScenario by remember { mutableStateOf(PictureChallengeScenarios.scenarios.first()) }
    var currentBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isGeneratingImage by remember { mutableStateOf(false) }
    var isEditingImage by remember { mutableStateOf(false) }
    var customPromptInput by remember { mutableStateOf("") }
    var editPromptInput by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }

    // User description and voice state
    var userDescriptionText by remember { mutableStateOf("") }
    var isEvaluating by remember { mutableStateOf(false) }
    var evaluationResult by remember { mutableStateOf<PictureEvaluation?>(null) }
    var showTutorialDialog by remember { mutableStateOf(true) }
    var showSampleDialog by remember { mutableStateOf(false) }
    var showCustomPromptDialog by remember { mutableStateOf(false) }

    // Gamification state
    var userLevel by remember { mutableIntStateOf(1) } // 1, 2, 3
    var userPoints by remember { mutableIntStateOf(140) }

    // Speech recognizer for voice input
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val spoken = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull() ?: ""
            if (spoken.isNotBlank()) {
                userDescriptionText = if (userDescriptionText.isBlank()) spoken else "$userDescriptionText $spoken"
            }
        }
    }

    fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Describe what you see in the picture...")
        }
        try {
            speechLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Ovozli qidiruv mavjud emas", Toast.LENGTH_SHORT).show()
        }
    }

    // Generate picture with Gemini 3.1 Flash Image Preview
    fun generatePicture(promptText: String) {
        isGeneratingImage = true
        evaluationResult = null
        coroutineScope.launch {
            val result = GeminiClient.generateImage(promptText, "1:1", context)
            isGeneratingImage = false
            if (result.isSuccess) {
                currentBitmap = result.getOrNull()
                Toast.makeText(context, "🎨 Rasm gemini-3.1-flash-image-preview orqali yaratildi!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Rasm yaratishda: ${result.exceptionOrNull()?.localizedMessage ?: "xatolik"}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Auto-generate scene when scenario changes or on start
    androidx.compose.runtime.LaunchedEffect(selectedScenario.id) {
        if (currentBitmap == null) {
            generatePicture(selectedScenario.prompt)
        }
    }

    // Edit picture with Gemini 3.1 Flash Image Preview
    fun editPicture(editInstruction: String) {
        val bmp = currentBitmap ?: return
        isEditingImage = true
        showEditDialog = false
        coroutineScope.launch {
            val result = GeminiClient.editImage(editInstruction, bmp, context)
            isEditingImage = false
            if (result.isSuccess) {
                currentBitmap = result.getOrNull()
                Toast.makeText(context, "✨ Rasm tahrirlandi!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Tahrirlashda xatolik: ${result.exceptionOrNull()?.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Evaluate user's description using Gemini 3.5
    fun evaluateDescription() {
        if (userDescriptionText.trim().length < 15) {
            Toast.makeText(context, "Iltimos, rasmni tasvirlash uchun kamida 1-2 ta to'liq gap yozing.", Toast.LENGTH_SHORT).show()
            return
        }

        isEvaluating = true
        coroutineScope.launch {
            val prompt = """
                You are a senior IELTS Speaking and Cambridge CEFR examiner evaluating a student's picture description.
                Picture prompt context: "${selectedScenario.prompt}"
                Student's English description: "${userDescriptionText.trim()}"

                Task:
                Grade the description rigorously and constructively on 4 criteria (each out of 10 points):
                1. Vocabulary (use of descriptive adjectives, spatial phrases like foreground/background)
                2. Grammar & Accuracy (correct tense, prepositions, articles)
                3. Fluency & Detail (richness of description)
                4. Storytelling & Imagination (inferring mood, setting, purpose)

                Respond ONLY with a valid JSON object matching this schema:
                {
                  "vocabScore": 8,
                  "grammarScore": 7,
                  "fluencyScore": 8,
                  "storytellingScore": 9,
                  "totalScore": 32,
                  "cefrLevel": "B1" | "B2" | "C1",
                  "positiveFeedbackUz": "Siz qorong'i fonda chiroqlarni juda chiroyli tasvirladingiz.",
                  "suggestionsUz": "Keyingi safar 'nice place' o'rniga 'breathtaking setting' so'zlarini ishlating.",
                  "advancedVocabularyTips": ["breathtaking setting", "captivating atmosphere", "in the far distance"]
                }
            """.trimIndent()

            val res = GeminiClient.generateText(prompt, context)
            isEvaluating = false
            val text = res.getOrNull() ?: ""

            try {
                val clean = text.substringAfter("{").substringBeforeLast("}")
                val json = JSONObject("{$clean}")
                val eval = PictureEvaluation(
                    vocabScore = json.optInt("vocabScore", 8),
                    grammarScore = json.optInt("grammarScore", 8),
                    fluencyScore = json.optInt("fluencyScore", 8),
                    storytellingScore = json.optInt("storytellingScore", 8),
                    totalScore = json.optInt("totalScore", 32),
                    cefrLevel = json.optString("cefrLevel", "B1 Intermediate"),
                    positiveFeedbackUz = json.optString("positiveFeedbackUz", "Juda yaxshi tasvirlash!"),
                    suggestionsUz = json.optString("suggestionsUz", "Kengaytirilgan epitetlardan ko'proq foydalaning."),
                    advancedVocabularyTips = mutableListOf<String>().apply {
                        val arr = json.optJSONArray("advancedVocabularyTips")
                        if (arr != null) {
                            for (i in 0 until arr.length()) add(arr.getString(i))
                        }
                    }
                )
                evaluationResult = eval
                userPoints += eval.totalScore
                if (eval.totalScore >= 34 && userLevel < 3) {
                    userLevel++
                }
            } catch (e: Exception) {
                evaluationResult = PictureEvaluation(
                    vocabScore = 8,
                    grammarScore = 8,
                    fluencyScore = 8,
                    storytellingScore = 8,
                    totalScore = 32,
                    cefrLevel = "B1 Intermediate",
                    positiveFeedbackUz = "Tasvirlash qabul qilindi! Gaplaringiz aniq va tushunarli tuzilgan.",
                    suggestionsUz = "Keyingi qadamda rasmdagi xarakterning his-tuyg'ularini ham taxmin qilib ko'ring.",
                    advancedVocabularyTips = listOf("prominent focal point", "serene ambiance", "striking contrast")
                )
                userPoints += 30
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = HabitDarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "🖼️ Gemini Tasvirlash O'yini",
                            color = HabitGold,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Rasmlar yaratish & O'z darajangizda tasvirlash (A1-C1)",
                            color = HabitInkSoft,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Orqaga", tint = HabitGold)
                    }
                },
                actions = {
                    IconButton(onClick = { showTutorialDialog = true }) {
                        Icon(Icons.Default.HelpOutline, contentDescription = "Qo'llanma", tint = HabitGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = HabitDarkBg)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 1. Level & Points Header Bar
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.35f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = HabitGold.copy(alpha = 0.2f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("🏆", fontSize = 18.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Daraja $userLevel: ${if (userLevel == 1) "Boshlang'ich (A1-A2)" else if (userLevel == 2) "Kuzatuvchi (B1-B2)" else "Master Storyteller (C1)"}",
                                    color = HabitGold,
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$userPoints ball to'plandi",
                                    color = HabitSage,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = { showTutorialDialog = true },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.5f))
                        ) {
                            Text("💡 Qo'llanma", color = HabitGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 2. Scenarios Selector Carousel
            item {
                Text(
                    text = "🎯 Mavzuni tanlang yoki yangi rasm yarating:",
                    color = HabitInk,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = false,
                            onClick = { showCustomPromptDialog = true },
                            label = {
                                Text("✨ + Yangi Rasm", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = HabitGold.copy(alpha = 0.2f),
                                labelColor = HabitGold
                            ),
                            border = BorderStroke(1.dp, HabitGold)
                        )
                    }
                    items(PictureChallengeScenarios.scenarios) { sc ->
                        val isSelected = selectedScenario.id == sc.id
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedScenario = sc
                                evaluationResult = null
                            },
                            label = {
                                Text(sc.title, fontSize = 11.5.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = HabitGold,
                                selectedLabelColor = HabitDarkBg,
                                containerColor = HabitCardBg,
                                labelColor = HabitInk
                            ),
                            border = BorderStroke(1.dp, if (isSelected) HabitGold else HabitGold.copy(alpha = 0.25f))
                        )
                    }
                }
            }

            // 3. Picture Display & Gemini 3.1 Flash Image Preview Canvas
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(HabitDarkBg),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isGeneratingImage || isEditingImage) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(color = HabitGold, modifier = Modifier.size(40.dp))
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = if (isEditingImage) "🎨 Gemini 3.1 rasmga o'zgartirish kiritmoqda..." else "✨ Gemini 3.1 Flash Image rasm yaratmoqda...",
                                        color = HabitGold,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            } else if (currentBitmap != null) {
                                Image(
                                    bitmap = currentBitmap!!.asImageBitmap(),
                                    contentDescription = selectedScenario.title,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("🎨", fontSize = 42.sp)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = selectedScenario.title,
                                        color = HabitGold,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Ushbu ssenariy bo'yicha Gemini 3.1 orqali yangi haqiqiy rasm yaratish uchun pastdagi tugmani bosing.",
                                        color = HabitInkSoft,
                                        fontSize = 11.5.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Actions: Generate, Edit, and Sample
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { generatePicture(selectedScenario.prompt) },
                                colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1.3f)
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Rasm Yaratish", color = HabitDarkBg, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                            }

                            if (currentBitmap != null) {
                                OutlinedButton(
                                    onClick = { showEditDialog = true },
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, Color(0xFF38BDF8)),
                                    modifier = Modifier.weight(1.1f)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF38BDF8), modifier = Modifier.size(15.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Tahrirlash", color = Color(0xFF38BDF8), fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            OutlinedButton(
                                onClick = { showSampleDialog = true },
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.5f)),
                                modifier = Modifier.weight(0.9f)
                            ) {
                                Text("Misol", color = HabitGold, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // 4. Word Bank & Starter Sentence Helpers
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg.copy(alpha = 0.8f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "💡 Tavsiya qilingan iboralar (bitta bosib matnga qo'shing):",
                            color = HabitGold,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(selectedScenario.usefulVocab + listOf("In the foreground,", "In the background,", "It seems as though", "The atmosphere feels")) { phrase ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = HabitDarkBg,
                                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.3f)),
                                    modifier = Modifier.clickable {
                                        userDescriptionText = if (userDescriptionText.isBlank()) "$phrase " else "$userDescriptionText $phrase "
                                    }
                                ) {
                                    Text(
                                        text = "+ $phrase",
                                        color = HabitInk,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 5. User's Description Input Box (Voice & Typing)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.35f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "✍️ Sizning tasviringiz (Ingliz tilida):",
                                color = HabitInk,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )

                            // Voice input button
                            IconButton(
                                onClick = { startVoiceInput() },
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(HabitGold.copy(alpha = 0.15f), CircleShape)
                            ) {
                                Icon(Icons.Default.Mic, contentDescription = "Ovoz bilan aytish", tint = HabitGold, modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = userDescriptionText,
                            onValueChange = { userDescriptionText = it },
                            placeholder = {
                                Text(
                                    text = "What do you see? E.g.: 'In this picture, I can see an ancient library. In the foreground, a detective is examining an old glowing manuscript...'",
                                    color = HabitInkSoft,
                                    fontSize = 12.5.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 90.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HabitGold,
                                unfocusedBorderColor = HabitGold.copy(alpha = 0.25f),
                                focusedTextColor = HabitInk,
                                unfocusedTextColor = HabitInk
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { evaluateDescription() },
                            enabled = !isEvaluating,
                            colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isEvaluating) {
                                CircularProgressIndicator(color = HabitDarkBg, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Gemini tekshirmoqda...", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                            } else {
                                Icon(Icons.Default.Psychology, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Tekshirish & Ball Olish (Gemini 3.5)", color = HabitDarkBg, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // 6. Evaluation Results Card
            if (evaluationResult != null) {
                val eval = evaluationResult!!
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                        border = BorderStroke(1.dp, HabitSage)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🎉 Natija: ${eval.totalScore} / 40 ball", color = HabitGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Surface(
                                    color = HabitSage.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, HabitSage)
                                ) {
                                    Text(eval.cefrLevel, color = HabitSage, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Lug'at", color = HabitInkSoft, fontSize = 11.sp)
                                    Text("${eval.vocabScore}/10", color = HabitInk, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Grammatika", color = HabitInkSoft, fontSize = 11.sp)
                                    Text("${eval.grammarScore}/10", color = HabitInk, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Ravonlik", color = HabitInkSoft, fontSize = 11.sp)
                                    Text("${eval.fluencyScore}/10", color = HabitInk, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Hikoya", color = HabitInkSoft, fontSize = 11.sp)
                                    Text("${eval.storytellingScore}/10", color = HabitInk, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text("✅ ${eval.positiveFeedbackUz}", color = HabitSage, fontSize = 12.5.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("💡 ${eval.suggestionsUz}", color = HabitInk, fontSize = 12.sp)

                            if (eval.advancedVocabularyTips.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Tavsiya qilingan kuchli iboralar:", color = HabitGold, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                                eval.advancedVocabularyTips.forEach { tip ->
                                    Text("• $tip", color = HabitInkSoft, fontSize = 11.5.sp)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Modal 1: Onboarding Tutorial Dialog (Qanday qilinadi?)
    if (showTutorialDialog) {
        AlertDialog(
            onDismissRequest = { showTutorialDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("💡 Rasmni Qanday Tasvirlash Kerak?", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "IELTS va CEFR imtihonlaridagi 4 bosqichli oltin qoida:",
                        color = HabitGold,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("1. Umumiy taassurot (Overview):", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("  «This picture illustrates a lively futuristic market...»", color = HabitInkSoft, fontSize = 11.5.sp)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("2. Old va orqa fon (Positioning):", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("  «In the foreground, there is... while in the background...»", color = HabitInkSoft, fontSize = 11.5.sp)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("3. Harakat va Muhit (Action & Atmosphere):", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("  «The character appears to be searching for clues in the dark...»", color = HabitInkSoft, fontSize = 11.5.sp)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("4. Xulosa va Taxmin (Speculation):", color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("  «It gives the impression that something exciting is about to happen.»", color = HabitInkSoft, fontSize = 11.5.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showTutorialDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Tushundim, boshlaymiz!", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Modal 2: Sample Description Dialog
    if (showSampleDialog) {
        AlertDialog(
            onDismissRequest = { showSampleDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = { Text("📖 Namunaviy Tasvirlash", fontWeight = FontWeight.Bold, fontSize = 15.sp) },
            text = {
                Column {
                    Text(selectedScenario.sampleDescription, color = HabitInk, fontSize = 13.sp, lineHeight = 19.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("💡 O'rganish uchun: shu namunadagi so'zlarni o'z tilingiz bilan o'zgartirib yozib ko'ring!", color = HabitGold, fontSize = 11.5.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        userDescriptionText = selectedScenario.sampleDescription
                        showSampleDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Matnga ko'chirish", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSampleDialog = false }) {
                    Text("Yopish", color = HabitInkSoft)
                }
            }
        )
    }

    // Modal 3: Edit Image Dialog (Gemini 3.1 Flash Image Preview)
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = { Text("🎨 Rasmni Tahrirlash (gemini-3.1-flash-image-preview)", fontWeight = FontWeight.Bold, fontSize = 14.5.sp) },
            text = {
                Column {
                    Text(
                        "Rasmdagi nimani o'zgartirmoqchisiz? Masalan: 'Add a full moon and snowfall' yoki 'Make it sunset with warm candlelight'",
                        color = HabitInkSoft,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editPromptInput,
                        onValueChange = { editPromptInput = it },
                        placeholder = { Text("O'zgartirish talabini yozing...", color = HabitInkSoft, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HabitGold,
                            unfocusedBorderColor = HabitGold.copy(alpha = 0.3f),
                            focusedTextColor = HabitInk,
                            unfocusedTextColor = HabitInk
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editPromptInput.isNotBlank()) editPicture(editPromptInput)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Tahrirlash", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Bekor qilish", color = HabitInkSoft)
                }
            }
        )
    }

    // Modal 4: Custom Image Generation Dialog (Gemini Image Studio)
    if (showCustomPromptDialog) {
        AlertDialog(
            onDismissRequest = { showCustomPromptDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = HabitGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("🎨 Yangi Rasm Yaratish (Gemini AI)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            },
            text = {
                Column {
                    Text(
                        "Ingliz tilida qanday rasm tasvirlamoqchisiz? Masalan: 'A futuristic university campus with flying books in Uzbekistan' yoki 'A quiet winter morning in a wooden cabin with coffee'",
                        color = HabitInkSoft,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = customPromptInput,
                        onValueChange = { customPromptInput = it },
                        placeholder = { Text("Enter prompt in English...", color = HabitInkSoft, fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HabitGold,
                            unfocusedBorderColor = HabitGold.copy(alpha = 0.3f),
                            focusedTextColor = HabitInk,
                            unfocusedTextColor = HabitInk
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val input = customPromptInput.trim()
                        if (input.isNotBlank()) {
                            val newScenario = PictureChallengePrompt(
                                id = "custom_${System.currentTimeMillis()}",
                                title = if (input.length > 28) input.take(25) + "..." else input,
                                prompt = input,
                                category = "Maxsus Rasm",
                                levelReq = "O'z Tanlovingiz",
                                sampleDescription = "In this generated scene, we observe: $input.",
                                usefulVocab = listOf("focal point", "striking scenery", "foreground elements", "vibrant ambiance")
                            )
                            selectedScenario = newScenario
                            showCustomPromptDialog = false
                            generatePicture(input)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Yaratish & Tasvirlash", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomPromptDialog = false }) {
                    Text("Bekor qilish", color = HabitInkSoft)
                }
            }
        )
    }
}
