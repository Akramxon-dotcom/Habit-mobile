@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.ui.screen

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Replay5
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ScriptDocument
import com.example.data.model.ScriptSampleAudio
import com.example.data.model.ScriptSampleRepository
import com.example.data.model.ScriptSentenceChunk
import com.example.data.model.ScriptSpellEngine
import com.example.data.model.SpellCheckIssue
import com.example.data.model.ScriptPuzzleSentence
import com.example.data.remote.GeminiClient
import com.example.ui.theme.HabitCardBg
import com.example.ui.theme.HabitCardElevated
import com.example.ui.theme.HabitDarkBg
import com.example.ui.theme.HabitGold
import com.example.ui.theme.HabitInk
import com.example.ui.theme.HabitInkSoft
import com.example.ui.theme.HabitSage
import com.example.ui.theme.LocalLiquidTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 🎧 SCRIPT TRANSKRIPSIYA STUDIYASI (English Dictation Workbench)
 * 
 * Telegram pleyerining barcha noqulayliklarini yo'q qiluvchi maxsus studiya:
 * 1. ⌨️ Klaviatura ustiga qadalgan doimiy boshqaruv tugmalari (klaviaturani yopmasdan boshqarish).
 * 2. ⏪ -1.5s va -3s aniq orqaga qaytarish (slayder bilan ovora bo'lmasdan bitta bosishda).
 * 3. 🔄 Avto-qaytish (Pause'dan keyin yoki matn yozib bo'lgach boshlaganda avtomatik 1.5s orqaga surilib kontekstni ushlaydi).
 * 4. 🔂 A-B Cheksiz halqa (Loop): tushunarsiz tez iborani [A] va [B] oralig'ida qayta-qayta aylantirib eshitish.
 * 5. ⚡ Pitch saqlangan sekinlashtirish (0.6x - 1.2x).
 * 6. ✋ Yozganda avto-pauza (Typing-Pause) rejimi.
 * 7. 🧩 Gapma-gap bo'laklar rejimi (har bir gapning o'z vaqt tamg'asi va qaytarish tugmasi bor).
 * 8. 🔍 Imloni tekshirish (subtitr bermaydi, foydalanuvchi o'zi yozgan matndagi xatolarni ko'rsatadi).
 * 9. 📤 Bitta tugma bilan chiroyli formatda Ustozga / Telegramga yuborish.
 * 10. 📚 Tayyor 5 ta amaliyot audiosi va telefondan/Telegramdan istalgan audioni yuklash.
 */
@Composable
fun ScriptStudioScreen(
    onBack: () -> Unit,
    savedDocuments: List<ScriptDocument> = emptyList(),
    onSaveDocument: (ScriptDocument) -> Unit = {},
    onDeleteDocument: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val theme = LocalLiquidTheme.current

    // Active document state
    var currentDocId by remember { mutableStateOf(UUID.randomUUID().toString()) }
    var docTitle by remember { mutableStateOf("Script Vazifasi #${SimpleDateFormat("dd.MM", Locale.getDefault()).format(Date())}") }
    var freeformText by remember { mutableStateOf("") }
    val sentenceChunks = remember { mutableStateListOf<ScriptSentenceChunk>() }
    var selectedEditorTab by remember { mutableIntStateOf(0) } // 0: Gapma-gap, 1: Yaxlit matn

    // Audio states
    var audioTitle by remember { mutableStateOf("IELTS Morning Routine") }
    var currentAudioUriStr by remember { mutableStateOf<String?>(null) }
    var currentSampleId by remember { mutableStateOf<String?>("sample_ielts_routine") }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPositionMs by remember { mutableLongStateOf(0L) }
    var durationMs by remember { mutableLongStateOf(115_000L) }
    var playbackSpeed by remember { mutableFloatStateOf(1.0f) }

    // Smart Features Settings
    var isAutoRewindEnabled by remember { mutableStateOf(true) }
    var isTypingPauseEnabled by remember { mutableStateOf(false) }
    var loopA by remember { mutableStateOf<Long?>(null) }
    var loopB by remember { mutableStateOf<Long?>(null) }
    var isLoopActive by remember { mutableStateOf(false) }

    // Editor & Navigation Sub-modes
    var editorSubMode by remember { mutableIntStateOf(0) } // 0: Gapma-gap, 1: Yaxlit matn
    var telegramFilterCategory by remember { mutableStateOf("Barchasi") }

    // Spell Check & Modals
    var showSpellCheckDialog by remember { mutableStateOf(false) }
    var spellIssues by remember { mutableStateOf<List<SpellCheckIssue>>(emptyList()) }
    var showSamplePickerSheet by remember { mutableStateOf(false) }
    var sampleSheetSearchQuery by remember { mutableStateOf("") }
    var sampleSheetCategory by remember { mutableStateOf("Hammasi") }
    var showHistorySheet by remember { mutableStateOf(false) }
    var isSynthesizingSample by remember { mutableStateOf(false) }
    var isTranscribingByGemini by remember { mutableStateOf(false) }
    var showAiTranscribeDialog by remember { mutableStateOf(false) }
    var comparisonResult by remember { mutableStateOf<com.example.data.remote.TranscriptionComparisonResult?>(null) }
    var isComparingWithAi by remember { mutableStateOf(false) }

    // 🎮 Audio Puzzle & Shifr O'yini State
    val puzzleSentences = remember(currentSampleId, freeformText) {
        if (!currentSampleId.isNullOrBlank()) {
            ScriptSampleRepository.getPuzzleSentences(currentSampleId!!)
        } else if (freeformText.isNotBlank()) {
            ScriptSampleRepository.parseSentencesFromText(freeformText, durationMs)
        } else {
            ScriptSampleRepository.getPuzzleSentences(ScriptSampleRepository.samples.first().id)
        }
    }


    // TTS engine for synthesizing sample audios locally
    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }
    var isTtsReady by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsEngine?.language = Locale.US
                isTtsReady = true
            }
        }
        ttsEngine = tts
        onDispose {
            tts.stop()
            tts.shutdown()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    // Helper to format ms to mm:ss
    fun formatTime(ms: Long): String {
        val totalSec = (ms.coerceAtLeast(0) / 1000).toInt()
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format(Locale.getDefault(), "%02d:%02d", min, sec)
    }

    // Prepare MediaPlayer with URI or cached sample
    fun loadAudioSource(uri: Uri?, sampleId: String?, title: String) {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
            currentPositionMs = 0L
            loopA = null
            loopB = null
            isLoopActive = false

            audioTitle = title
            currentAudioUriStr = uri?.toString()
            currentSampleId = sampleId

            if (uri != null) {
                // User picked custom audio file
                val mp = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(context, uri)
                    prepare()
                }
                durationMs = mp.duration.toLong().coerceAtLeast(1000L)
                mediaPlayer = mp
            } else if (sampleId != null) {
                val sample = ScriptSampleRepository.samples.firstOrNull { it.id == sampleId }
                    ?: ScriptSampleRepository.samples.first()
                durationMs = (sample.durationSec * 1000L).coerceAtLeast(10_000L)

                // Synthesize sample to local cache file if not exists
                val cacheFile = File(context.cacheDir, "sample_${sample.id}.wav")
                if (cacheFile.exists() && cacheFile.length() > 0) {
                    val mp = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                        setDataSource(cacheFile.absolutePath)
                        prepare()
                    }
                    durationMs = mp.duration.toLong().coerceAtLeast(1000L)
                    mediaPlayer = mp
                } else {
                    // Synthesize in background
                    val tts = ttsEngine
                    if (tts != null && isTtsReady) {
                        isSynthesizingSample = true
                        val bundle = Bundle()
                        val utteranceId = "synth_${sample.id}"
                        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                            override fun onStart(id: String?) {}
                            override fun onDone(id: String?) {
                                isSynthesizingSample = false
                                coroutineScope.launch {
                                    try {
                                        val mp = MediaPlayer().apply {
                                            setDataSource(cacheFile.absolutePath)
                                            prepare()
                                        }
                                        durationMs = mp.duration.toLong().coerceAtLeast(1000L)
                                        mediaPlayer = mp
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                            override fun onError(id: String?) {
                                isSynthesizingSample = false
                            }
                        })
                        tts.synthesizeToFile(sample.hiddenSpokenContent, bundle, cacheFile, utteranceId)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Audioni yuklashda xatolik: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    // Audio file picker launcher
    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val fileName = uri.lastPathSegment ?: "Yuklangan audio"
            loadAudioSource(uri, null, fileName)
            Toast.makeText(context, "Audio muvaffaqiyatli yuklandi!", Toast.LENGTH_SHORT).show()
        }
    }

    // 📥 Telegram / Custom Script states & Audio picker
    var showAddTelegramScriptDialog by remember { mutableStateOf(false) }
    var telegramScriptTitle by remember { mutableStateOf("") }
    var telegramScriptCategory by remember { mutableStateOf("Telegram Script") }
    var telegramScriptLevel by remember { mutableStateOf("B1 Intermediate") }
    var telegramScriptText by remember { mutableStateOf("") }
    var telegramScriptAudioUri by remember { mutableStateOf<Uri?>(null) }
    var telegramScriptAudioName by remember { mutableStateOf<String?>(null) }

    val telegramAudioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            telegramScriptAudioUri = uri
            telegramScriptAudioName = uri.lastPathSegment?.substringAfterLast('/') ?: "Audio fayl"
            Toast.makeText(context, "Audio biriktirildi: $telegramScriptAudioName", Toast.LENGTH_SHORT).show()
        }
    }

    // Initial load
    LaunchedEffect(isTtsReady) {
        if (isTtsReady && mediaPlayer == null && currentAudioUriStr == null) {
            loadAudioSource(null, "sample_ielts_routine", "Daily Routine & IELTS Morning Habits")
        }
    }

    // Playback ticker for position and A-B Loop checking
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            mediaPlayer?.let { mp ->
                try {
                    val pos = mp.currentPosition.toLong()
                    currentPositionMs = pos

                    // A-B Loop handling
                    if (isLoopActive && loopA != null && loopB != null) {
                        if (pos >= loopB!!) {
                            mp.seekTo(loopA!!.toInt())
                            currentPositionMs = loopA!!
                        }
                    }

                    if (!mp.isPlaying && pos >= (durationMs - 500)) {
                        isPlaying = false
                    }
                } catch (_: Exception) {}
            }
            delay(100)
        }
    }

    // Speed setter with pitch preservation
    fun setPlayerSpeed(speed: Float) {
        playbackSpeed = speed
        mediaPlayer?.let { mp ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val params = mp.playbackParams ?: PlaybackParams()
                    params.speed = speed
                    mp.playbackParams = params
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Play/Pause toggle with auto-rewind
    fun togglePlayPause() {
        val mp = mediaPlayer ?: return
        try {
            if (mp.isPlaying) {
                mp.pause()
                isPlaying = false
            } else {
                // Auto-rewind 1.5 seconds on resume to catch immediate context
                if (isAutoRewindEnabled && currentPositionMs > 1500) {
                    val newPos = (currentPositionMs - 1500).coerceAtLeast(0)
                    mp.seekTo(newPos.toInt())
                    currentPositionMs = newPos
                }
                setPlayerSpeed(playbackSpeed)
                mp.start()
                isPlaying = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Fine jump rewind/forward
    fun jumpRelative(deltaMs: Long) {
        val mp = mediaPlayer ?: return
        try {
            val target = (currentPositionMs + deltaMs).coerceIn(0L, durationMs)
            mp.seekTo(target.toInt())
            currentPositionMs = target
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Seek to position
    fun seekToPosition(targetMs: Long) {
        val mp = mediaPlayer ?: return
        try {
            val safe = targetMs.coerceIn(0L, durationMs)
            mp.seekTo(safe.toInt())
            currentPositionMs = safe
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // A-B loop toggle cycle
    fun handleLoopCycle() {
        if (!isLoopActive && loopA == null) {
            // Set A
            loopA = currentPositionMs
            Toast.makeText(context, "📍 Halqa boshi (A) belgilandi: ${formatTime(currentPositionMs)}", Toast.LENGTH_SHORT).show()
        } else if (!isLoopActive && loopA != null && loopB == null) {
            // Set B and activate
            if (currentPositionMs > loopA!!) {
                loopB = currentPositionMs
                isLoopActive = true
                Toast.makeText(context, "🔁 Halqa faollashdi: ${formatTime(loopA!!)} — ${formatTime(loopB!!)}", Toast.LENGTH_SHORT).show()
            } else {
                loopB = (loopA!! + 3000).coerceAtMost(durationMs)
                isLoopActive = true
                Toast.makeText(context, "🔁 Halqa faollashdi (3s): ${formatTime(loopA!!)} — ${formatTime(loopB!!)}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Reset
            loopA = null
            loopB = null
            isLoopActive = false
            Toast.makeText(context, "⏹ Halqa o'chirildi", Toast.LENGTH_SHORT).show()
        }
    }

    // Typing pause logic
    fun onUserTypedText(newText: String) {
        freeformText = newText
        if (isTypingPauseEnabled && isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
        }
    }

    // Save document helper
    fun saveCurrentDocument(markCompleted: Boolean = false) {
        val allText = if (selectedEditorTab == 0) {
            sentenceChunks.joinToString("\n") { it.userText.trim() }
        } else {
            freeformText
        }
        val doc = ScriptDocument(
            id = currentDocId,
            title = docTitle,
            dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
            audioUriStr = currentAudioUriStr,
            sampleAudioId = currentSampleId,
            audioTitle = audioTitle,
            audioDurationMs = durationMs,
            rawText = allText,
            chunks = sentenceChunks.toList(),
            isCompleted = markCompleted,
            lastPositionMs = currentPositionMs,
            updatedAtEpochMs = System.currentTimeMillis()
        )
        onSaveDocument(doc)
        Toast.makeText(context, "💾 Script muvaffaqiyatli saqlandi!", Toast.LENGTH_SHORT).show()
    }

    // Share to Telegram / Teacher
    fun shareScriptToTelegram() {
        val textToShare = if (selectedEditorTab == 0) {
            sentenceChunks.mapIndexed { idx, chunk ->
                "${idx + 1}. [${formatTime(chunk.startTimeMs)} - ${formatTime(chunk.endTimeMs)}]: ${chunk.userText}"
            }.joinToString("\n\n")
        } else {
            freeformText
        }

        val wordCount = textToShare.split(Regex("""\s+""")).count { it.isNotBlank() }
        val dateLabel = SimpleDateFormat("dd-MMMM, yyyy", Locale.getDefault()).format(Date())

        val formattedMessage = buildString {
            append("📝 INGLIZ TILI: SCRIPT VAZIFASI\n")
            append("📅 Sana: $dateLabel\n")
            append("🎧 Audio: $audioTitle\n")
            append("⏱ Audio davomiyligi: ${formatTime(durationMs)}\n")
            append("📊 So'zlar soni: $wordCount ta so'z\n")
            append("------------------------------------\n\n")
            append(textToShare.ifBlank { "(Hali matn yozilmadi)" })
            append("\n\n------------------------------------")
            append("\n✅ Vazifa tayyor bo'ldi!")
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "English Script: $docTitle")
            putExtra(Intent.EXTRA_TEXT, formattedMessage)
        }
        val chooser = Intent.createChooser(intent, "Ustozga / Telegramga yuborish")
        context.startActivity(chooser)
    }

    // 🎙️ Gemini 3.5 Audio Transcribe function
    fun transcribeWithGemini() {
        val uriStr = currentAudioUriStr
        val sampleId = currentSampleId
        isTranscribingByGemini = true
        coroutineScope.launch {
            try {
                val audioBytes: ByteArray? = if (!uriStr.isNullOrBlank()) {
                    val uri = Uri.parse(uriStr)
                    context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                } else if (!sampleId.isNullOrBlank()) {
                    val cacheFile = File(context.cacheDir, "sample_$sampleId.wav")
                    if (cacheFile.exists()) cacheFile.readBytes() else null
                } else null

                if (audioBytes != null && audioBytes.isNotEmpty()) {
                    val base64 = android.util.Base64.encodeToString(
                        audioBytes.take(3 * 1024 * 1024).toByteArray(),
                        android.util.Base64.NO_WRAP
                    )
                    val result = GeminiClient.transcribeAudio(base64, "audio/wav", context)
                    isTranscribingByGemini = false
                    if (result.isSuccess) {
                        val text = result.getOrNull() ?: ""
                        if (text.isNotBlank()) {
                            if (selectedEditorTab == 1) {
                                freeformText = if (freeformText.isBlank()) text else "$freeformText\n$text"
                            } else {
                                sentenceChunks.add(
                                    ScriptSentenceChunk(
                                        startTimeMs = currentPositionMs,
                                        endTimeMs = durationMs,
                                        userText = text
                                    )
                                )
                            }
                            Toast.makeText(context, "✅ Gemini 3.5 Flash orqali transkripsiya qilindi!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Transkripsiya: ${result.exceptionOrNull()?.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    isTranscribingByGemini = false
                    Toast.makeText(context, "Audio fayl topilmadi. Avval audio tanlang yoki yuklang.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                isTranscribingByGemini = false
                Toast.makeText(context, "Xatolik: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Calculate word count
    val activeText = if (selectedEditorTab == 0) {
        sentenceChunks.joinToString(" ") { it.userText }
    } else {
        freeformText
    }
    val wordsCount = activeText.split(Regex("""\s+""")).count { it.isNotBlank() }
    val charsCount = activeText.length

    // 🔍 Gemini 3.5 Compare Transcription Function
    fun compareWithGemini() {
        val uriStr = currentAudioUriStr
        val sampleId = currentSampleId
        isComparingWithAi = true
        coroutineScope.launch {
            try {
                val audioBytes: ByteArray? = if (!uriStr.isNullOrBlank()) {
                    val uri = Uri.parse(uriStr)
                    context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                } else if (!sampleId.isNullOrBlank()) {
                    val cacheFile = File(context.cacheDir, "sample_$sampleId.wav")
                    if (cacheFile.exists()) cacheFile.readBytes() else null
                } else null

                val base64 = if (audioBytes != null && audioBytes.isNotEmpty()) {
                    android.util.Base64.encodeToString(
                        audioBytes.take(3 * 1024 * 1024).toByteArray(),
                        android.util.Base64.NO_WRAP
                    )
                } else null

                val result = GeminiClient.compareTranscriptionWithAi(activeText, base64, context)
                isComparingWithAi = false
                if (result.isSuccess) {
                    comparisonResult = result.getOrNull()
                } else {
                    Toast.makeText(context, "AI Solishtirish: ${result.exceptionOrNull()?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                isComparingWithAi = false
                Toast.makeText(context, "Xatolik: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
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
                            text = "🎧 Script Studiyasi",
                            color = HabitGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Telegramdan 10x qulayroq ingliz tili diktant maydoni",
                            color = HabitInkSoft,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Orqaga",
                            tint = HabitGold
                        )
                    }
                },
                actions = {
                    // Quick add custom/Telegram script
                    IconButton(onClick = { showAddTelegramScriptDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.AddCircleOutline,
                            contentDescription = "Telegramdan Script Qo'shish",
                            tint = HabitGold
                        )
                    }
                    // Audio file picker
                    IconButton(onClick = { audioPickerLauncher.launch("audio/*") }) {
                        Icon(
                            imageVector = Icons.Default.FolderOpen,
                            contentDescription = "Audio Fayl Yuklash",
                            tint = HabitGold
                        )
                    }
                    // History
                    IconButton(onClick = { showHistorySheet = true }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Mashqlar Tarixi",
                            tint = HabitGold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = HabitDarkBg)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 14.dp)
        ) {
            // 1. TOP NAVIGATION GRID HUB (Clean, Non-overlapping, Distinct Touch Targets)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    // Row 1: Audio Arena, STT Audio, TTS Ovoz
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ScriptNavGridTile(
                            modifier = Modifier.weight(1f),
                            title = "Audio Arena",
                            icon = "🎮",
                            badge = "4 O'yin",
                            isSelected = selectedEditorTab == 0,
                            onClick = { selectedEditorTab = 0 }
                        )
                        ScriptNavGridTile(
                            modifier = Modifier.weight(1f),
                            title = "STT & Audio",
                            icon = "🎙️",
                            badge = "Ustoz AI",
                            isSelected = selectedEditorTab == 1,
                            onClick = { selectedEditorTab = 1 }
                        )
                        ScriptNavGridTile(
                            modifier = Modifier.weight(1f),
                            title = "TTS Ovoz",
                            icon = "🗣️",
                            badge = "US/UK",
                            isSelected = selectedEditorTab == 2,
                            onClick = { selectedEditorTab = 2 }
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    // Row 2: 50+ Hikoyalar, Diktant, Telegram
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ScriptNavGridTile(
                            modifier = Modifier.weight(1f),
                            title = "50+ Hikoya",
                            icon = "📚",
                            badge = "Kutubxona",
                            isSelected = selectedEditorTab == 3,
                            onClick = { selectedEditorTab = 3 }
                        )
                        ScriptNavGridTile(
                            modifier = Modifier.weight(1f),
                            title = "Diktant",
                            icon = "📝",
                            badge = "${sentenceChunks.size} gap",
                            isSelected = selectedEditorTab == 4,
                            onClick = { selectedEditorTab = 4 }
                        )
                        ScriptNavGridTile(
                            modifier = Modifier.weight(1f),
                            title = "Telegram",
                            icon = "📥",
                            badge = "${ScriptSampleRepository.customUserSamples.size} ta",
                            isSelected = selectedEditorTab == 5,
                            onClick = { selectedEditorTab = 5 }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            // For Editor Tab (Tab 4 Diktant & Muharrir): Show Granular Player Card
            if (selectedEditorTab == 4) {
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = audioTitle,
                                    color = HabitInk,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (currentAudioUriStr != null) "📂 Telefon fayli" else "🎙️ Ustoz audio namunasi",
                                    color = HabitInkSoft,
                                    fontSize = 11.sp
                                )
                            }

                            // Playback speed indicator pill
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = HabitGold.copy(alpha = 0.15f),
                                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.4f)),
                                modifier = Modifier.clickable {
                                    val nextSpeed = when (playbackSpeed) {
                                        0.7f -> 0.8f
                                        0.8f -> 0.9f
                                        0.9f -> 1.0f
                                        1.0f -> 1.2f
                                        1.2f -> 0.7f
                                        else -> 1.0f
                                    }
                                    setPlayerSpeed(nextSpeed)
                                }
                            ) {
                                Text(
                                    text = "⚡ ${playbackSpeed}x",
                                    color = HabitGold,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Audio Scrub Slider
                        Slider(
                            value = currentPositionMs.toFloat().coerceIn(0f, durationMs.toFloat()),
                            onValueChange = { seekToPosition(it.toLong()) },
                            valueRange = 0f..durationMs.toFloat(),
                            colors = SliderDefaults.colors(
                                thumbColor = HabitGold,
                                activeTrackColor = HabitGold,
                                inactiveTrackColor = HabitGold.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.fillMaxWidth().height(22.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = formatTime(currentPositionMs),
                                color = HabitInk,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (isLoopActive && loopA != null && loopB != null) {
                                Text(
                                    text = "🔁 Halqa: ${formatTime(loopA!!)} - ${formatTime(loopB!!)}",
                                    color = Color(0xFF38BDF8),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = formatTime(durationMs),
                                color = HabitInkSoft,
                                fontSize = 11.5.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Granular Jump & Play Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // -3s Jump
                            IconButton(
                                onClick = { jumpRelative(-3000L) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(HabitGold.copy(alpha = 0.12f), CircleShape)
                            ) {
                                Text("-3s", color = HabitGold, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                            }

                            // -1.5s Jump
                            IconButton(
                                onClick = { jumpRelative(-1500L) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(HabitGold.copy(alpha = 0.12f), CircleShape)
                            ) {
                                Text("-1.5", color = HabitGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }

                            // Big Play/Pause
                            IconButton(
                                onClick = { togglePlayPause() },
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(HabitGold, CircleShape)
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlaying) "Pauza" else "Ijro",
                                    tint = HabitDarkBg,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            // +2s Jump
                            IconButton(
                                onClick = { jumpRelative(2000L) },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(HabitGold.copy(alpha = 0.12f), CircleShape)
                            ) {
                                Text("+2s", color = HabitGold, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                            }

                            // A-B Loop Button
                            IconButton(
                                onClick = { handleLoopCycle() },
                                modifier = Modifier
                                    .size(38.dp)
                                    .background(
                                        if (isLoopActive) Color(0xFF38BDF8).copy(alpha = 0.25f) else HabitGold.copy(alpha = 0.12f),
                                        CircleShape
                                    )
                                    .border(
                                        1.dp,
                                        if (isLoopActive) Color(0xFF38BDF8) else Color.Transparent,
                                        CircleShape
                                    )
                            ) {
                                Text(
                                    text = when {
                                        isLoopActive -> "🔂"
                                        loopA != null -> "SetB"
                                        else -> "Loop"
                                    },
                                    color = if (isLoopActive) Color(0xFF38BDF8) else HabitGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Smart Automation Toggles
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { isAutoRewindEnabled = !isAutoRewindEnabled }
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isAutoRewindEnabled) HabitSage.copy(alpha = 0.25f) else Color.Transparent,
                                    border = BorderStroke(1.dp, if (isAutoRewindEnabled) HabitSage else HabitInkSoft.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = if (isAutoRewindEnabled) "✅ Avto -1.5s" else "⚪ Avto -1.5s",
                                        color = if (isAutoRewindEnabled) HabitSage else HabitInkSoft,
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { isTypingPauseEnabled = !isTypingPauseEnabled }
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isTypingPauseEnabled) Color(0xFF38BDF8).copy(alpha = 0.25f) else Color.Transparent,
                                    border = BorderStroke(1.dp, if (isTypingPauseEnabled) Color(0xFF38BDF8) else HabitInkSoft.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = if (isTypingPauseEnabled) "✍️ Yozganda pauza" else "⚪ Yozganda pauza",
                                        color = if (isTypingPauseEnabled) Color(0xFF38BDF8) else HabitInkSoft,
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        // Submode Pill: Gapma-gap vs Yaxlit Matn
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (editorSubMode == 0) HabitGold else HabitDarkBg,
                                border = BorderStroke(1.dp, if (editorSubMode == 0) HabitGold else HabitGold.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { editorSubMode = 0 }
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🧩 Gapma-gap (${sentenceChunks.size})",
                                        color = if (editorSubMode == 0) HabitDarkBg else HabitInk,
                                        fontSize = 11.5.sp,
                                        fontWeight = if (editorSubMode == 0) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (editorSubMode == 1) HabitGold else HabitDarkBg,
                                border = BorderStroke(1.dp, if (editorSubMode == 1) HabitGold else HabitGold.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { editorSubMode = 1 }
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "📄 Yaxlit Matn ($wordsCount so'z)",
                                        color = if (editorSubMode == 1) HabitDarkBg else HabitInk,
                                        fontSize = 11.5.sp,
                                        fontWeight = if (editorSubMode == 1) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Main Workspace Area (Clean, Non-overlapping Content Container)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (selectedEditorTab == 0) {
                    // --- 🎮 CYBER EAR: AUDIO ARENA & UNIVERSAL LISTENING GAME ---
                    AudioGameArenaView(
                        puzzleSentences = puzzleSentences,
                        currentSampleId = currentSampleId,
                        audioTitle = audioTitle,
                        ttsEngine = ttsEngine,
                        isTtsReady = isTtsReady,
                        playbackSpeed = playbackSpeed,
                        onSentenceCompleted = { completedSentence ->
                            if (sentenceChunks.none { it.userText.contains(completedSentence.text.take(15)) }) {
                                sentenceChunks.add(
                                    ScriptSentenceChunk(
                                        startTimeMs = completedSentence.startTimeMs,
                                        endTimeMs = completedSentence.endTimeMs,
                                        userText = completedSentence.text
                                    )
                                )
                            }
                        }
                    )
                } else if (selectedEditorTab == 1) {
                    // --- 🎙️ STT & USTOZ AUDIO TAHLIL ---
                    AudioTranscribeAndTtsStudio(
                        ttsEngine = ttsEngine,
                        isTtsReady = isTtsReady,
                        initialSubTab = 0,
                        onLoadIntoScript = { title, fullText, audioUri, sampleId ->
                            docTitle = title
                            freeformText = fullText
                            if (audioUri != null || sampleId != null) {
                                loadAudioSource(audioUri, sampleId, title)
                            }
                            selectedEditorTab = 4
                            editorSubMode = 1
                            Toast.makeText(context, "✅ Scriptga muvaffaqiyatli yuklandi!", Toast.LENGTH_SHORT).show()
                        },
                        onLoadIntoArena = { title, sampleId, customText ->
                            if (sampleId != null) {
                                currentSampleId = sampleId
                                currentAudioUriStr = null
                                audioTitle = title
                            } else if (!customText.isNullOrBlank()) {
                                freeformText = customText
                                audioTitle = title
                            }
                            selectedEditorTab = 0
                            Toast.makeText(context, "🎮 Audio Arenaga muvaffaqiyatli yuklandi!", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else if (selectedEditorTab == 2) {
                    // --- 🗣️ TTS OVOZ STUDIYASI ---
                    AudioTranscribeAndTtsStudio(
                        ttsEngine = ttsEngine,
                        isTtsReady = isTtsReady,
                        initialSubTab = 1,
                        onLoadIntoScript = { title, fullText, audioUri, sampleId ->
                            docTitle = title
                            freeformText = fullText
                            if (audioUri != null || sampleId != null) {
                                loadAudioSource(audioUri, sampleId, title)
                            }
                            selectedEditorTab = 4
                            editorSubMode = 1
                            Toast.makeText(context, "✅ Scriptga muvaffaqiyatli yuklandi!", Toast.LENGTH_SHORT).show()
                        },
                        onLoadIntoArena = { title, sampleId, customText ->
                            if (sampleId != null) {
                                currentSampleId = sampleId
                                currentAudioUriStr = null
                                audioTitle = title
                            } else if (!customText.isNullOrBlank()) {
                                freeformText = customText
                                audioTitle = title
                            }
                            selectedEditorTab = 0
                            Toast.makeText(context, "🎮 Audio Arenaga muvaffaqiyatli yuklandi!", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else if (selectedEditorTab == 3) {
                    // --- 📚 50+ HIKOYALAR VA MAVZULAR KUTUBXONASI (SCRIPT LIBRARY GRID) ---
                    ScriptLibraryGridView(
                        ttsEngine = ttsEngine,
                        isTtsReady = isTtsReady,
                        onLoadIntoScript = { title, fullText, sampleId ->
                            docTitle = title
                            freeformText = fullText
                            loadAudioSource(null, sampleId, title)
                            selectedEditorTab = 4
                            editorSubMode = 1
                            Toast.makeText(context, "✅ '${title}' Diktant muharririga yuklandi!", Toast.LENGTH_SHORT).show()
                        },
                        onLoadIntoArena = { title, sampleId ->
                            currentSampleId = sampleId
                            currentAudioUriStr = null
                            audioTitle = title
                            selectedEditorTab = 0
                            Toast.makeText(context, "🎮 '${title}' Audio Arenada ochildi!", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else if (selectedEditorTab == 4) {
                    // --- 📝 DIKTANT & SCRIPT MUHARRIR ---
                    if (editorSubMode == 0) {
                        // GAPMA-GAP BLOKLAR REJIMI
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (sentenceChunks.isEmpty()) {
                                item {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = HabitCardBg.copy(alpha = 0.6f)),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("💡 Gapma-gap diktant qulayligi:", color = HabitGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                "Audioni eshitasiz, bitta gap tugaganda '+ Yangi gap qo'shish' tugmasini bosasiz. Har bir gap o'z vaqti bilan saqlanadi va xohlagan paytda faqat o'sha gapni qayta eshita olasiz!",
                                                color = HabitInkSoft,
                                                fontSize = 12.sp,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }

                            itemsIndexed(sentenceChunks, key = { _, chunk -> chunk.id }) { index, chunk ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.2f))
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = HabitGold.copy(alpha = 0.15f),
                                                modifier = Modifier.clickable {
                                                    seekToPosition(chunk.startTimeMs)
                                                    if (!isPlaying) togglePlayPause()
                                                }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.PlayArrow,
                                                        contentDescription = "Gapni tinglash",
                                                        tint = HabitGold,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        text = "#${index + 1} [${formatTime(chunk.startTimeMs)} - ${formatTime(chunk.endTimeMs)}]",
                                                        color = HabitGold,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }

                                            IconButton(
                                                onClick = { sentenceChunks.removeAt(index) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "O'chirish",
                                                    tint = HabitInkSoft,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        OutlinedTextField(
                                            value = chunk.userText,
                                            onValueChange = { updatedText ->
                                                sentenceChunks[index] = chunk.copy(userText = updatedText)
                                                if (isTypingPauseEnabled && isPlaying) {
                                                    mediaPlayer?.pause()
                                                    isPlaying = false
                                                }
                                            },
                                            placeholder = { Text("Eshitgan gapni shu yerga yozing...", color = HabitInkSoft, fontSize = 13.sp) },
                                            modifier = Modifier.fillMaxWidth(),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = HabitGold,
                                                unfocusedBorderColor = HabitGold.copy(alpha = 0.2f),
                                                focusedTextColor = HabitInk,
                                                unfocusedTextColor = HabitInk
                                            )
                                        )
                                    }
                                }
                            }

                            item {
                                Button(
                                    onClick = {
                                        val start = if (sentenceChunks.isNotEmpty()) sentenceChunks.last().endTimeMs else 0L
                                        val end = currentPositionMs.coerceAtLeast(start + 2000L)
                                        sentenceChunks.add(
                                            ScriptSentenceChunk(
                                                startTimeMs = start,
                                                endTimeMs = end,
                                                userText = ""
                                            )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold.copy(alpha = 0.2f)),
                                    border = BorderStroke(1.dp, HabitGold),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = HabitGold)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "+ Yangi gap blokini qo'shish (${formatTime(currentPositionMs)})",
                                        color = HabitGold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    } else {
                        // YAXLIT MATN REJIMI
                        Column(modifier = Modifier.fillMaxSize()) {
                            OutlinedTextField(
                                value = freeformText,
                                onValueChange = { onUserTypedText(it) },
                                placeholder = {
                                    Text(
                                        text = "Eshitgan butun suhbatni shu yerga erkin yozing...\n\nMasalan:\nGood morning everyone! Today I would like to share my daily routine...",
                                        color = HabitInkSoft,
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = HabitCardBg,
                                    unfocusedContainerColor = HabitCardBg,
                                    focusedBorderColor = HabitGold,
                                    unfocusedBorderColor = HabitGold.copy(alpha = 0.3f),
                                    focusedTextColor = HabitInk,
                                    unfocusedTextColor = HabitInk
                                )
                            )
                        }
                    }
                } else if (selectedEditorTab == 5) {
                    // --- 📥 TELEGRAM & SHAXSIY SKRIPTLAR BOSHQARUVI ---
                    TelegramScriptsManagerSection(
                        customSamples = ScriptSampleRepository.customUserSamples,
                        filterCategory = telegramFilterCategory,
                        onFilterChange = { telegramFilterCategory = it },
                        onAddNewClick = { showAddTelegramScriptDialog = true },
                        onPlayInArena = { sample ->
                            currentSampleId = sample.id
                            currentAudioUriStr = null
                            audioTitle = sample.title
                            selectedEditorTab = 0
                            Toast.makeText(context, "🎮 '${sample.title}' Audio Arenada ochildi!", Toast.LENGTH_SHORT).show()
                        },
                        onOpenInEditor = { sample ->
                            docTitle = sample.title
                            freeformText = sample.hiddenSpokenContent
                            loadAudioSource(null, sample.id, sample.title)
                            sentenceChunks.clear()
                            val chunks = sample.hiddenSpokenContent.split(Regex("(?<=[.!?\\n])\\s+"))
                                .map { it.trim() }
                                .filter { it.isNotBlank() }
                            val chunkMs = if (chunks.isNotEmpty()) (durationMs / chunks.size).coerceAtLeast(3000L) else 5000L
                            chunks.forEachIndexed { i, c ->
                                sentenceChunks.add(
                                    ScriptSentenceChunk(
                                        startTimeMs = i * chunkMs,
                                        endTimeMs = (i + 1) * chunkMs,
                                        userText = c
                                    )
                                )
                            }
                            selectedEditorTab = 4
                            editorSubMode = 0
                            Toast.makeText(context, "📝 '${sample.title}' Diktant muharririda ochildi!", Toast.LENGTH_SHORT).show()
                        },
                        onDeleteSample = { id ->
                            ScriptSampleRepository.removeCustomSample(id)
                            Toast.makeText(context, "Skript o'chirildi", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            if (selectedEditorTab == 4) {
                Spacer(modifier = Modifier.height(8.dp))

                // 4. Floating Helper Toolbar Above Keyboard (Quick Punctuation & Timestamp)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Timestamp insertion
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = HabitGold.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.35f)),
                            modifier = Modifier.clickable {
                                val timeTag = "[${formatTime(currentPositionMs)}] "
                                if (selectedEditorTab == 2) {
                                    freeformText += " $timeTag"
                                }
                            }
                        ) {
                            Text(
                                text = "⏱ [${formatTime(currentPositionMs)}]",
                                color = HabitGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                            )
                        }

                        // Punctuation fast buttons
                        listOf(",", ".", "?", "\"", "'", "—").forEach { char ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HabitDarkBg,
                                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.3f)),
                                modifier = Modifier.clickable {
                                    if (selectedEditorTab == 2) {
                                        freeformText = freeformText.trimEnd() + char + " "
                                    } else if (sentenceChunks.isNotEmpty()) {
                                        val lastIdx = sentenceChunks.lastIndex
                                        val cur = sentenceChunks[lastIdx]
                                        sentenceChunks[lastIdx] = cur.copy(userText = cur.userText.trimEnd() + char + " ")
                                    }
                                }
                            ) {
                                Text(
                                    text = char,
                                    color = HabitInk,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Word count badge
                        Text(
                            text = "$wordsCount ta so'z",
                            color = HabitSage,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 5. Bottom Action Bar: Spellcheck, Save, and Telegram Export
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Spell Checker Button
                    OutlinedButton(
                        onClick = {
                            val textToCheck = if (selectedEditorTab == 1) {
                                sentenceChunks.joinToString(" ") { it.userText }
                            } else {
                                freeformText
                            }
                            spellIssues = ScriptSpellEngine.checkText(textToCheck)
                            showSpellCheckDialog = true
                        },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, HabitGold),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Spellcheck, contentDescription = null, tint = HabitGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Imlo", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    // Save Draft Button
                    Button(
                        onClick = { saveCurrentDocument(markCompleted = false) },
                        colors = ButtonDefaults.buttonColors(containerColor = HabitCardBg),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.4f)),
                        modifier = Modifier.weight(1.1f)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, tint = HabitGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Saqlash", color = HabitInk, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    // Send to Telegram / Teacher Button
                    Button(
                        onClick = {
                            saveCurrentDocument(markCompleted = true)
                            shareScriptToTelegram()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1.4f)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Telegramga", color = HabitDarkBg, fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // --- Modal 1: Sample Audio Picker (Ustoz audiolari) ---
    if (showSamplePickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSamplePickerSheet = false },
            containerColor = HabitCardBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "📚 Ustoz Audio Darslari (Namunalar)",
                    color = HabitGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "2 daqiqalik tabiiy inglizcha suhbatlar. Bitta bosishda yuklanadi:",
                    color = HabitInkSoft,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ScriptSampleRepository.samples) { sample ->
                        val isSelected = currentSampleId == sample.id
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showSamplePickerSheet = false
                                    loadAudioSource(null, sample.id, sample.title)
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) HabitGold.copy(alpha = 0.15f) else HabitDarkBg
                            ),
                            border = BorderStroke(1.dp, if (isSelected) HabitGold else HabitGold.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = sample.title,
                                        color = HabitInk,
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${sample.durationSec / 60}:${String.format("%02d", sample.durationSec % 60)}",
                                        color = HabitGold,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "Ustoz: ${sample.teacherName} • ${sample.level}",
                                    color = HabitInkSoft,
                                    fontSize = 11.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = sample.descriptionUz,
                                    color = HabitInk,
                                    fontSize = 11.5.sp
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // --- Modal 2: History & Saved Drafts ---
    if (showHistorySheet) {
        ModalBottomSheet(
            onDismissRequest = { showHistorySheet = false },
            containerColor = HabitCardBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "📜 Saqlangan Script Vazifalari",
                    color = HabitGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (savedDocuments.isEmpty()) {
                    Text(
                        text = "Hozircha saqlangan scriptlar yo'q. Diktant yozib 'Saqlash' tugmasini bosing.",
                        color = HabitInkSoft,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().heightIn(max = 380.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(savedDocuments) { doc ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        currentDocId = doc.id
                                        docTitle = doc.title
                                        freeformText = doc.rawText
                                        sentenceChunks.clear()
                                        sentenceChunks.addAll(doc.chunks)
                                        showHistorySheet = false
                                        val uri = doc.audioUriStr?.let { Uri.parse(it) }
                                        loadAudioSource(uri, doc.sampleAudioId, doc.audioTitle)
                                        Toast.makeText(context, "Script yuklandi!", Toast.LENGTH_SHORT).show()
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = HabitDarkBg),
                                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.25f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(doc.title, color = HabitInk, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("${doc.dateStr} • ${doc.audioTitle}", color = HabitInkSoft, fontSize = 11.sp)
                                        Text(
                                            text = if (doc.isCompleted) "✅ Bajarildi" else "⏳ Qoralama",
                                            color = if (doc.isCompleted) HabitSage else HabitGold,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    IconButton(onClick = { onDeleteDocument(doc.id) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "O'chirish", tint = HabitInkSoft)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // --- Modal 3: Spell & Grammar Check Report ---
    if (showSpellCheckDialog) {
        AlertDialog(
            onDismissRequest = { showSpellCheckDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Spellcheck, contentDescription = null, tint = HabitGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("🔍 Imloni tekshirish natijalari", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (spellIssues.isEmpty()) {
                        Text(
                            text = "🎉 Ajoyib! Siz yozgan matnda hech qanday imlo xatolari yoki harfiy noaniqliklar topilmadi.",
                            color = HabitSage,
                            fontSize = 13.5.sp
                        )
                    } else {
                        Text(
                            text = "Aniqlangan ${spellIssues.size} ta imlo va punktuatsiya tavsiyalari:",
                            color = HabitInkSoft,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().heightIn(max = 260.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(spellIssues) { issue ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = HabitDarkBg),
                                    border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.35f))
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "❌ \"${issue.originalWord}\"",
                                                color = Color(0xFFEF4444),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.5.sp
                                            )
                                            Text(
                                                text = "👉 \"${issue.suggestedWord}\"",
                                                color = HabitSage,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.5.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = issue.ruleExplanation,
                                            color = HabitInkSoft,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSpellCheckDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Tushunarli", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // --- Modal 4: Gemini 3.5 AI Transcribe & Listening Assessment ---
    if (showAiTranscribeDialog) {
        AlertDialog(
            onDismissRequest = {
                showAiTranscribeDialog = false
                comparisonResult = null
            },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = HabitGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("🤖 Gemini 3.5 Audio Transkripsiya", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (comparisonResult != null) {
                        val res = comparisonResult!!
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = HabitGold.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("🎯 Eshitish Aniqligi:", color = HabitGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("${res.accuracyPercentage}%", color = HabitSage, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(res.positiveFeedbackUz, color = HabitInk, fontSize = 12.sp)
                            }
                        }

                        if (res.missedWords.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("⚠️ Eshitishda qolib ketgan so'zlar:", color = Color(0xFFEF4444), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(res.missedWords) { w ->
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = HabitDarkBg,
                                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f))
                                    ) {
                                        Text(w, color = HabitInk, fontSize = 11.5.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("💡 Ustoz eslatmasi:", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(res.listeningTipsUz, color = HabitInkSoft, fontSize = 11.5.sp)

                        if (res.geminiTranscript.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("📝 Gemini 3.5 transkripsiyasi:", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(res.geminiTranscript, color = HabitInk, fontSize = 12.sp, maxLines = 4)
                        }
                    } else {
                        Text(
                            text = "Ustoz ovozli xabarini Gemini 3.5 orqali aniq transkripsiya qilish yoki o'zingiz yozgan matn bilan solishtirib tekshirish:",
                            color = HabitInkSoft,
                            fontSize = 12.5.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Button 1: Compare with user's written text
                        OutlinedButton(
                            onClick = { compareWithGemini() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, HabitGold)
                        ) {
                            if (isComparingWithAi) {
                                CircularProgressIndicator(color = HabitGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("AI Tekshirmoqda...", color = HabitGold, fontSize = 12.sp)
                            } else {
                                Icon(Icons.Default.Spellcheck, contentDescription = null, tint = HabitGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("🔍 Yozgan diktantimni AI bilan tekshirish", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Button 2: Direct transcribe
                        Button(
                            onClick = {
                                transcribeWithGemini()
                                showAiTranscribeDialog = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (isTranscribingByGemini) {
                                CircularProgressIndicator(color = HabitDarkBg, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Transkripsiya qilinmoqda...", color = HabitDarkBg, fontSize = 12.sp)
                            } else {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("✨ To'liq matnga o'tkazish (Transcribe)", color = HabitDarkBg, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                if (comparisonResult != null) {
                    Button(
                        onClick = {
                            val text = comparisonResult?.geminiTranscript ?: ""
                            if (text.isNotBlank()) {
                                if (selectedEditorTab == 1) {
                                    freeformText = text
                                } else {
                                    sentenceChunks.clear()
                                    sentenceChunks.add(
                                        ScriptSentenceChunk(
                                            startTimeMs = 0L,
                                            endTimeMs = durationMs,
                                            userText = text
                                        )
                                    )
                                }
                            }
                            showAiTranscribeDialog = false
                            comparisonResult = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                    ) {
                        Text("Matnga ko'chirish", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                    }
                } else {
                    TextButton(onClick = { showAiTranscribeDialog = false }) {
                        Text("Yopish", color = HabitInkSoft)
                    }
                }
            },
            dismissButton = {
                if (comparisonResult != null) {
                    TextButton(onClick = { comparisonResult = null }) {
                        Text("Qayta tekshirish", color = HabitInkSoft)
                    }
                }
            }
        )
    }

    // --- Modal 5: Telegram / Shaxsiy Listening Script Qo'shish ---
    if (showAddTelegramScriptDialog) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        AlertDialog(
            onDismissRequest = { showAddTelegramScriptDialog = false },
            containerColor = HabitCardBg,
            titleContentColor = HabitGold,
            textContentColor = HabitInk,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AddCircleOutline, contentDescription = null, tint = HabitGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("📥 Telegram Script Qo'shish", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "Telegram kanallari yoki ustozingizdan olgan istalgan listening audiosi va tayyor matnini qo'shing. Tizim uni avtomatik Cyber Ear O'yin Arenasi va diktant studiyasiga integratsiya qiladi.",
                            fontSize = 12.sp,
                            color = HabitInkSoft
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = telegramScriptTitle,
                            onValueChange = { telegramScriptTitle = it },
                            label = { Text("Script / Dars Nomi", color = HabitGold) },
                            placeholder = { Text("Masalan: IELTS Cambridge 18 Test 1", color = HabitInkSoft) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HabitGold,
                                unfocusedBorderColor = HabitGold.copy(alpha = 0.3f),
                                focusedTextColor = HabitInk,
                                unfocusedTextColor = HabitInk
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Mavzu:", color = HabitInk, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(listOf("Telegram", "IELTS", "Hikoya", "Suhbat", "Detektiv", "Fan")) { cat ->
                                    val isSel = telegramScriptCategory == cat
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSel) HabitGold else HabitCardElevated,
                                        modifier = Modifier.clickable { telegramScriptCategory = cat }
                                    ) {
                                        Text(
                                            cat,
                                            color = if (isSel) HabitDarkBg else HabitInk,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Daraja:", color = HabitInk, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(listOf("A2", "B1", "B2", "C1")) { lvl ->
                                    val isSel = telegramScriptLevel.startsWith(lvl)
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSel) HabitGold else HabitCardElevated,
                                        modifier = Modifier.clickable {
                                            telegramScriptLevel = when (lvl) {
                                                "A2" -> "A2 Pre-Intermediate"
                                                "B1" -> "B1 Intermediate"
                                                "B2" -> "B2 Upper-Intermediate"
                                                else -> "C1 Advanced"
                                            }
                                        }
                                    ) {
                                        Text(
                                            lvl,
                                            color = if (isSel) HabitDarkBg else HabitInk,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Script Matni:", color = HabitGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Button(
                                    onClick = {
                                        val clip = clipboardManager?.primaryClip
                                        if (clip != null && clip.itemCount > 0) {
                                            val pasted = clip.getItemAt(0).text?.toString() ?: ""
                                            if (pasted.isNotBlank()) {
                                                telegramScriptText = pasted
                                                Toast.makeText(context, "Xotiradan nusxa olindi!", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold.copy(alpha = 0.2f)),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.ContentPaste, contentDescription = null, tint = HabitGold, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Qo'yish (Paste)", color = HabitGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = telegramScriptText,
                                onValueChange = { telegramScriptText = it },
                                placeholder = { Text("Telegramdan olingan matnni shu yerga kiriting...", color = HabitInkSoft, fontSize = 12.sp) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 100.dp, max = 160.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = HabitGold,
                                    unfocusedBorderColor = HabitGold.copy(alpha = 0.3f),
                                    focusedTextColor = HabitInk,
                                    unfocusedTextColor = HabitInk
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = HabitCardElevated),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("🎧 Audio Fayl (ixtiyoriy):", color = HabitInk, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (telegramScriptAudioUri != null) "✅ Tanlangan: ${telegramScriptAudioName ?: "audio"}" else "Audio fayl tanlanmasa, tizim avtomatik TTS bilan jonli ovozlashtiradi.",
                                    color = if (telegramScriptAudioUri != null) HabitSage else HabitInkSoft,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                OutlinedButton(
                                    onClick = { telegramAudioPickerLauncher.launch("audio/*") },
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, HabitGold),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.FolderOpen, contentDescription = null, tint = HabitGold, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (telegramScriptAudioUri != null) "Boshqa audio tanlash" else "Audio Fayl Tanlash (.mp3 / .wav)", color = HabitGold, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (telegramScriptText.isBlank()) {
                            Toast.makeText(context, "Iltimos, matn kiriting!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val title = telegramScriptTitle.ifBlank { "Telegram Script #${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())}" }
                        val customSample = ScriptSampleAudio(
                            id = "custom_tg_${UUID.randomUUID()}",
                            title = title,
                            teacherName = "Telegram / Shaxsiy",
                            durationSec = if (telegramScriptAudioUri != null) 120 else (telegramScriptText.split(" ").size / 2).coerceAtLeast(30),
                            descriptionUz = "Telegramdan yuklangan shaxsiy listening darsi.",
                            level = telegramScriptLevel,
                            hiddenSpokenContent = telegramScriptText,
                            category = telegramScriptCategory
                        )
                        ScriptSampleRepository.addCustomSample(customSample)
                        loadAudioSource(telegramScriptAudioUri, customSample.id, customSample.title)
                        docTitle = title
                        freeformText = telegramScriptText
                        sentenceChunks.clear()
                        val chunks = telegramScriptText.split(Regex("(?<=[.!?\\n])\\s+"))
                            .map { it.trim() }
                            .filter { it.isNotBlank() }
                        val chunkMs = if (chunks.isNotEmpty()) (durationMs / chunks.size).coerceAtLeast(3000L) else 5000L
                        chunks.forEachIndexed { i, c ->
                            sentenceChunks.add(
                                ScriptSentenceChunk(
                                    startTimeMs = i * chunkMs,
                                    endTimeMs = (i + 1) * chunkMs,
                                    userText = c
                                )
                            )
                        }
                        showAddTelegramScriptDialog = false
                        selectedEditorTab = 0 // Switch to Audio Game Arena!
                        Toast.makeText(context, "✅ Script qo'shildi va Audio Arenada ishga tushirildi!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HabitGold)
                ) {
                    Text("Saqlash va Boshlash", color = HabitDarkBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTelegramScriptDialog = false }) {
                    Text("Bekor qilish", color = HabitInkSoft)
                }
            }
        )
    }
}

/**
 * 🎛️ Clean, Non-overlapping Touch-friendly Navigation Tile
 */
@Composable
fun ScriptNavGridTile(
    modifier: Modifier = Modifier,
    title: String,
    icon: String,
    badge: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) HabitGold else HabitCardBg,
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) HabitGold else HabitGold.copy(alpha = 0.25f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 17.sp,
                modifier = Modifier.padding(end = 5.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = if (isSelected) HabitDarkBg else HabitInk,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = badge,
                    color = if (isSelected) HabitDarkBg.copy(alpha = 0.8f) else HabitGold,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * 📥 Telegram & Shaxsiy Skriptlar Boshqaruvi
 */
@Composable
fun TelegramScriptsManagerSection(
    customSamples: List<ScriptSampleAudio>,
    filterCategory: String,
    onFilterChange: (String) -> Unit,
    onAddNewClick: () -> Unit,
    onPlayInArena: (ScriptSampleAudio) -> Unit,
    onOpenInEditor: (ScriptSampleAudio) -> Unit,
    onDeleteSample: (String) -> Unit
) {
    val categories = listOf("Barchasi", "Telegram Script", "IELTS Listening", "Hikoya", "Suhbat")
    val filteredSamples = if (filterCategory == "Barchasi") {
        customSamples
    } else {
        customSamples.filter { it.category.equals(filterCategory, ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Banner card explaining Telegram and custom script import
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.35f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("📥", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Telegram & Shaxsiy Skriptlar",
                                    color = HabitGold,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Guruhlardan olingan darslarni boshqaring",
                                    color = HabitInkSoft,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Button(
                            onClick = onAddNewClick,
                            colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Qo'shish", color = HabitDarkBg, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Category filter chips
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = filterCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { onFilterChange(cat) },
                        label = {
                            Text(
                                text = cat,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
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

        // Empty state
        if (filteredSamples.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg.copy(alpha = 0.6f)),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📭", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Hozircha saqlangan skriptlar yo'q",
                            color = HabitInk,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Telegramdagi listening guruhlaridan nusxa olingan matnni bir tugma orqali qo'yib, o'yin yoki diktantga aylantirishingiz mumkin.",
                            color = HabitInkSoft,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = onAddNewClick,
                            colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.AddCircleOutline, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Telegramdan Script Qo'shish", color = HabitDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        } else {
            items(filteredSamples, key = { it.id }) { sample ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = HabitCardBg),
                    border = BorderStroke(1.dp, HabitGold.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = sample.title,
                                    color = HabitInk,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = HabitGold.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = sample.category,
                                            color = HabitGold,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = HabitSage.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = sample.level,
                                            color = HabitSage,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = { onDeleteSample(sample.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "O'chirish", tint = HabitInkSoft, modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Text Preview snippet
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HabitDarkBg,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = sample.hiddenSpokenContent.take(120) + if (sample.hiddenSpokenContent.length > 120) "..." else "",
                                color = HabitInkSoft,
                                fontSize = 11.5.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onPlayInArena(sample) },
                                colors = ButtonDefaults.buttonColors(containerColor = HabitGold),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = HabitDarkBg, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("🎮 O'ynash", color = HabitDarkBg, fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = { onOpenInEditor(sample) },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, HabitGold),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Spellcheck, contentDescription = null, tint = HabitGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("📝 Diktant", color = HabitGold, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}



