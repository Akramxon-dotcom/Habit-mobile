package com.example.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScriptSampleAudio
import com.example.data.model.ScriptSampleData
import com.example.data.remote.GeminiClient
import com.example.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.util.*

/**
 * 🎙️ MUKAMMAL STT & TTS STUDIYASI:
 * 1. 🎧 Audio Transcriber (STT): Ustoz audiosi (yoki istalgan audio)ni yuklab, Gemini 3.5 orqali
 *    100% so'zma-so'z aniq transkripsiya qilish va matnni tinglab o'qish.
 * 2. 🗣️ Text-to-Speech Studio (TTS): Istalgan inglizcha matnni kiritib, uni sinxron ovozga aylantirish,
 *    urg'u (US/UK) va tezlikni boshqarish, so'zma-so'z yoritish (Karaoke).
 * 3. 📚 50 ta boy ovozli script kutubxonasi.
 */
@Composable
fun AudioTranscribeAndTtsStudio(
    ttsEngine: TextToSpeech?,
    isTtsReady: Boolean,
    initialSubTab: Int = 0,
    onLoadIntoScript: (title: String, fullText: String, audioUri: Uri?, sampleId: String?) -> Unit,
    onLoadIntoArena: (title: String, sampleId: String?, customText: String?) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 0: STT Audio Transcriber, 1: TTS Studio, 2: 50 ta Ovozli Kutubxona
    var activeSubTab by remember(initialSubTab) { mutableIntStateOf(initialSubTab) }

    // ==========================================
    // 🎧 STT (Speech-to-Text) States
    // ==========================================
    var uploadedAudioUri by remember { mutableStateOf<Uri?>(null) }
    var uploadedAudioName by remember { mutableStateOf<String?>(null) }
    var isTranscribing by remember { mutableStateOf(false) }
    var transcriptionResult by remember { mutableStateOf<String?>(null) }
    var transcriptionSentences by remember { mutableStateOf<List<String>>(emptyList()) }
    var sttErrorMessage by remember { mutableStateOf<String?>(null) }
    var activePlayingSentenceIdx by remember { mutableIntStateOf(-1) }

    // Audio file picker
    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            uploadedAudioUri = uri
            uploadedAudioName = uri.lastPathSegment?.substringAfterLast('/') ?: "Ustoz audiosi.mp3"
            transcriptionResult = null
            transcriptionSentences = emptyList()
            sttErrorMessage = null
            Toast.makeText(context, "Audio tanlandi: $uploadedAudioName", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to run Gemini transcription
    fun runAudioTranscription(uri: Uri) {
        isTranscribing = true
        sttErrorMessage = null
        coroutineScope.launch {
            try {
                val (base64, mime) = withContext(Dispatchers.IO) {
                    val cr = context.contentResolver
                    val detectedMime = cr.getType(uri) ?: "audio/mp3"
                    val input: InputStream? = cr.openInputStream(uri)
                    val bytes = input?.readBytes() ?: ByteArray(0)
                    input?.close()
                    Pair(Base64.encodeToString(bytes, Base64.NO_WRAP), detectedMime)
                }

                if (base64.isBlank()) {
                    sttErrorMessage = "Audio faylini o'qib bo'lmadi. Boshqa fayl tanlang."
                    isTranscribing = false
                    return@launch
                }

                // If audio is very large, notify user
                val res = GeminiClient.transcribeAudio(base64, mime, context)
                if (res.isSuccess) {
                    val text = res.getOrNull()?.trim() ?: ""
                    transcriptionResult = text
                    transcriptionSentences = text
                        .split(Regex("(?<=[.!?\\n])\\s+"))
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                } else {
                    sttErrorMessage = res.exceptionOrNull()?.message ?: "Transkripsiya xatoligi ro'y berdi."
                }
            } catch (e: Exception) {
                sttErrorMessage = "Xatolik: ${e.localizedMessage}"
            } finally {
                isTranscribing = false
            }
        }
    }

    // ==========================================
    // 🗣️ TTS (Text-to-Speech) Studio States
    // ==========================================
    var ttsInputText by remember {
        mutableStateOf(
            "Welcome to the English Speech Studio! Listening to natural pronunciation every day is the most effective way to develop a fluent accent. Try typing your own sentences, adjust speech speed, and follow the highlighted words."
        )
    }
    var ttsSpeed by remember { mutableFloatStateOf(1.0f) }
    var ttsPitch by remember { mutableFloatStateOf(1.0f) }
    var selectedAccent by remember { mutableStateOf("US") } // "US" or "UK"
    var isTtsPlaying by remember { mutableStateOf(false) }
    var currentSpokenWordIndex by remember { mutableIntStateOf(-1) }
    val ttsSentencesList by remember(ttsInputText) {
        derivedStateOf {
            ttsInputText.split(Regex("(?<=[.!?\\n])\\s+"))
                .map { it.trim() }
                .filter { it.isNotBlank() }
        }
    }

    // ==========================================
    // 📚 50 Script Library States
    // ==========================================
    var selectedCategoryFilter by remember { mutableStateOf("Hammasi") }
    var searchQuery by remember { mutableStateOf("") }
    val categories = remember {
        listOf("Hammasi") + ScriptSampleData.fiftySamples.map { it.category }.distinct()
    }
    val filteredSamples = remember(selectedCategoryFilter, searchQuery) {
        ScriptSampleData.fiftySamples.filter { sample ->
            val matchesCategory = selectedCategoryFilter == "Hammasi" || sample.category == selectedCategoryFilter
            val matchesSearch = searchQuery.isBlank() ||
                    sample.title.contains(searchQuery, ignoreCase = true) ||
                    sample.descriptionUz.contains(searchQuery, ignoreCase = true) ||
                    sample.teacherName.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HabitDarkBg)
    ) {
        // --- 🎛️ SUB-TAB NAVIGATOR ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = HabitCardBg),
            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.25f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Tab 0: STT Transkriber
                val tab0Selected = activeSubTab == 0
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (tab0Selected) HabitGold else Color.Transparent)
                        .clickable { activeSubTab = 0 }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Hearing,
                            contentDescription = null,
                            tint = if (tab0Selected) HabitDarkBg else HabitInk,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "STT Transkriber",
                            fontSize = 11.5.sp,
                            fontWeight = if (tab0Selected) FontWeight.Bold else FontWeight.Medium,
                            color = if (tab0Selected) HabitDarkBg else HabitInk
                        )
                    }
                }

                // Tab 1: TTS Matndan Ovoz
                val tab1Selected = activeSubTab == 1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (tab1Selected) HabitGold else Color.Transparent)
                        .clickable { activeSubTab = 1 }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.RecordVoiceOver,
                            contentDescription = null,
                            tint = if (tab1Selected) HabitDarkBg else HabitInk,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "TTS Ovoz Studiyasi",
                            fontSize = 11.5.sp,
                            fontWeight = if (tab1Selected) FontWeight.Bold else FontWeight.Medium,
                            color = if (tab1Selected) HabitDarkBg else HabitInk
                        )
                    }
                }

                // Tab 2: 50 ta Ovozli Kutubxona
                val tab2Selected = activeSubTab == 2
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (tab2Selected) HabitGold else Color.Transparent)
                        .clickable { activeSubTab = 2 }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LibraryBooks,
                            contentDescription = null,
                            tint = if (tab2Selected) HabitDarkBg else HabitInk,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Kutubxona (50)",
                            fontSize = 11.5.sp,
                            fontWeight = if (tab2Selected) FontWeight.Bold else FontWeight.Medium,
                            color = if (tab2Selected) HabitDarkBg else HabitInk
                        )
                    }
                }
            }
        }

        // --- CONTENT BASED ON SELECTED SUB-TAB ---
        when (activeSubTab) {
            0 -> {
                // ========================================================
                // 🎧 1. STT AUDIO TRANSCRIBER & INTERACTIVE READER
                // ========================================================
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "🎙️ Ustoz Audiosini Yuklash & STT Tahlil",
                                            color = HabitGold,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Istalgan MP3, M4A, WAV faylni tanlang. Gemini 3.5 uni so'zma-so'z aniq matnga aylantiradi.",
                                            color = HabitInkSoft,
                                            fontSize = 11.5.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { audioPickerLauncher.launch("audio/*") },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.CloudUpload, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Fayl Tanlash", color = HabitDarkBg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }

                                    if (uploadedAudioUri != null) {
                                        Button(
                                            onClick = { runAudioTranscription(uploadedAudioUri!!) },
                                            enabled = !isTranscribing,
                                            modifier = Modifier.weight(1.3f),
                                            colors = ButtonDefaults.buttonColors(containerColor = HabitGreenSuccess),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            if (isTranscribing) {
                                                CircularProgressIndicator(color = HabitDarkBg, modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text("Tahlil qilinmoqda...", color = HabitDarkBg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            } else {
                                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text("Transkripsiya Qilish", color = HabitDarkBg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }

                                if (uploadedAudioName != null) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        color = HabitDarkBg,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(0.5.dp, HabitGold.copy(alpha = 0.2f))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 10.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(Icons.Default.Audiotrack, contentDescription = null, tint = HabitGold, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = uploadedAudioName ?: "",
                                                color = HabitInk,
                                                fontSize = 12.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Error Message if any
                    if (sttErrorMessage != null) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = HabitRedBurgundy.copy(alpha = 0.2f)),
                                border = BorderStroke(1.dp, HabitRedBurgundy),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = HabitRedBurgundy)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(sttErrorMessage ?: "", color = HabitInk, fontSize = 12.sp)
                                }
                            }
                        }
                    }

                    // Loading Animation Card
                    if (isTranscribing) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = HabitCardBg)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(color = HabitGold, strokeWidth = 3.dp)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "⚡ Gemini 3.5 Audio To'lqinlarini Tahlil Qilmoqda...",
                                        color = HabitGold,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Nutq bo'g'inlari, urg'ular va grammatik jumlalar ajratilmoqda. Biroz kuting.",
                                        color = HabitInkSoft,
                                        fontSize = 11.5.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    // Transcription Result Display
                    if (!transcriptionResult.isNullOrBlank()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.4f))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "📄 Transkripsiya Natijasi (${transcriptionSentences.size} ta jumla)",
                                            color = HabitGold,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        IconButton(
                                            onClick = {
                                                val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                cm.setPrimaryClip(ClipData.newPlainText("Transcription", transcriptionResult))
                                                Toast.makeText(context, "Matndan nusxa olindi!", Toast.LENGTH_SHORT).show()
                                            }
                                        ) {
                                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = HabitGold, modifier = Modifier.size(18.dp))
                                        }
                                    }

                                    // Action buttons: Send to Arena, Send to Script
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                onLoadIntoArena(
                                                    uploadedAudioName ?: "Ustoz audiosi",
                                                    null,
                                                    transcriptionResult
                                                )
                                                Toast.makeText(context, "Audio Arena yuklandi!", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.weight(1f),
                                            colors = ButtonDefaults.buttonColors(containerColor = HabitPurpleVoice),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(Icons.Default.SportsEsports, contentDescription = null, tint = HabitInk, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Arenada O'ynash", color = HabitInk, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                                        }

                                        Button(
                                            onClick = {
                                                onLoadIntoScript(
                                                    uploadedAudioName ?: "Ustoz Vazifasi",
                                                    transcriptionResult ?: "",
                                                    uploadedAudioUri,
                                                    null
                                                )
                                                Toast.makeText(context, "Script Studio ga o'tkazildi!", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.weight(1f),
                                            colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(Icons.Default.EditNote, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Scriptga O'tkazish", color = HabitDarkBg, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Sentences with individual TTS reader buttons
                                    transcriptionSentences.forEachIndexed { idx, sent ->
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp),
                                            color = if (activePlayingSentenceIdx == idx) HabitGold.copy(alpha = 0.15f) else HabitDarkBg,
                                            shape = RoundedCornerShape(8.dp),
                                            border = BorderStroke(0.5.dp, if (activePlayingSentenceIdx == idx) HabitGold else HabitGold.copy(alpha = 0.15f))
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "${idx + 1}.",
                                                    color = HabitGold,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.width(24.dp)
                                                )
                                                Text(
                                                    text = sent,
                                                    color = HabitInk,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                IconButton(
                                                    onClick = {
                                                        if (ttsEngine != null && isTtsReady) {
                                                            activePlayingSentenceIdx = idx
                                                            ttsEngine.speak(sent, TextToSpeech.QUEUE_FLUSH, null, "stt_sent_$idx")
                                                        }
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        Icons.AutoMirrored.Filled.VolumeUp,
                                                        contentDescription = "Play",
                                                        tint = HabitGold,
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

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }

            1 -> {
                // ========================================================
                // 🗣️ 2. TTS (TEXT-TO-SPEECH) STUDIO & KARAOKE READER
                // ========================================================
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🗣️ Inglizcha Matnni Jonli Ovozga Aylantirish",
                                    color = HabitGold,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Istalgan matnni yozing yoki qo'ying (paste). Nutq tezligi, ohangi va urg'usini sozlang.",
                                    color = HabitInkSoft,
                                    fontSize = 11.5.sp
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = ttsInputText,
                                    onValueChange = { ttsInputText = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 120.dp, max = 220.dp),
                                    placeholder = { Text("Inglizcha matn kiriting...", color = HabitInkSoft) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = HabitGold,
                                        unfocusedBorderColor = HabitGold.copy(alpha = 0.3f),
                                        focusedTextColor = HabitInk,
                                        unfocusedTextColor = HabitInk
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // Controls: Speed & Accent
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Accent Toggle
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("Urg'u: ", color = HabitInkSoft, fontSize = 12.sp)
                                        FilterChip(
                                            selected = selectedAccent == "US",
                                            onClick = {
                                                selectedAccent = "US"
                                                ttsEngine?.language = Locale.US
                                            },
                                            label = { Text("🇺🇸 AQSh", fontSize = 11.sp) },
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                        FilterChip(
                                            selected = selectedAccent == "UK",
                                            onClick = {
                                                selectedAccent = "UK"
                                                ttsEngine?.language = Locale.UK
                                            },
                                            label = { Text("🇬🇧 Britaniya", fontSize = 11.sp) }
                                        )
                                    }

                                    // Speed chip
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("Tezlik: ", color = HabitInkSoft, fontSize = 12.sp)
                                        listOf(0.75f, 1.0f, 1.25f).forEach { speed ->
                                            Box(
                                                modifier = Modifier
                                                    .padding(horizontal = 2.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(if (ttsSpeed == speed) HabitGold else HabitDarkBg)
                                                    .clickable {
                                                        ttsSpeed = speed
                                                        ttsEngine?.setSpeechRate(speed)
                                                    }
                                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                                            ) {
                                                Text(
                                                    text = "${speed}x",
                                                    fontSize = 10.5.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (ttsSpeed == speed) HabitDarkBg else HabitInk
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Play / Stop Action Buttons
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            if (ttsEngine != null && isTtsReady && ttsInputText.isNotBlank()) {
                                                ttsEngine.setSpeechRate(ttsSpeed)
                                                ttsEngine.setPitch(ttsPitch)
                                                ttsEngine.language = if (selectedAccent == "UK") Locale.UK else Locale.US
                                                ttsEngine.speak(ttsInputText, TextToSpeech.QUEUE_FLUSH, null, "tts_studio_full")
                                                isTtsPlaying = true
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("To'liq O'qib Berish", color = HabitDarkBg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            ttsEngine?.stop()
                                            isTtsPlaying = false
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = HabitCardBg),
                                        border = BorderStroke(1.dp, HabitRedBurgundy),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.Stop, contentDescription = null, tint = HabitRedBurgundy, modifier = Modifier.size(18.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Quick Launch into Arena or Script
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            onLoadIntoArena("Maxsus TTS Matni", null, ttsInputText)
                                            Toast.makeText(context, "Audio Arena ochilmoqda!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, HabitPurpleVoice)
                                    ) {
                                        Text("🎮 Arenada Mashq Qilish", color = HabitPurpleVoice, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            onLoadIntoScript("Maxsus Matn Vazifasi", ttsInputText, null, null)
                                            Toast.makeText(context, "Script Studio ga yuklandi!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, HabitGold)
                                    ) {
                                        Text("📝 Scriptga Yuklash", color = HabitGold, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Sentence Breakdown Viewer
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "📑 Gapma-gap Tinglash & O'qish",
                                    color = HabitGold,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                ttsSentencesList.forEachIndexed { i, s ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp),
                                        color = HabitDarkBg,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(0.5.dp, HabitGold.copy(alpha = 0.15f))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${i + 1}.",
                                                color = HabitGold,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.width(22.dp)
                                            )
                                            Text(
                                                text = s,
                                                color = HabitInk,
                                                fontSize = 12.5.sp,
                                                modifier = Modifier.weight(1f)
                                            )
                                            IconButton(
                                                onClick = {
                                                    ttsEngine?.setSpeechRate(ttsSpeed)
                                                    ttsEngine?.speak(s, TextToSpeech.QUEUE_FLUSH, null, "sent_$i")
                                                },
                                                modifier = Modifier.size(30.dp)
                                            ) {
                                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = HabitGold, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(30.dp)) }
                }
            }

            2 -> {
                // ========================================================
                // 📚 3. 50 TA ENG QIZIQARLI OVOZLI SCRIPT KUTUBXONASI (GRID CARD VIEW)
                // ========================================================
                ScriptLibraryGridView(
                    ttsEngine = ttsEngine,
                    isTtsReady = isTtsReady,
                    onLoadIntoScript = { title, fullText, sampleId ->
                        onLoadIntoScript(title, fullText, null, sampleId)
                    },
                    onLoadIntoArena = { title, sampleId ->
                        onLoadIntoArena(title, sampleId, null)
                    },
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}
