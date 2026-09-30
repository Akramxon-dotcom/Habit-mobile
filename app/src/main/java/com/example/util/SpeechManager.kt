package com.example.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class SpeechManager(private val context: Context) {

    private val TAG = "SpeechManager"
    private val mainHandler = Handler(Looper.getMainLooper())

    private var textToSpeech: TextToSpeech? = null
    private var isTtsReady = false
    private var pendingSpeakText: String? = null
    private var pendingSpeechRate: Float = 0.85f
    private var pendingSpeechCallback: (() -> Unit)? = null

    private var speechRecognizer: SpeechRecognizer? = null

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    private val _partialText = MutableStateFlow("")
    val partialText: StateFlow<String> = _partialText.asStateFlow()

    private val _rmsLevel = MutableStateFlow(0f)
    val rmsLevel: StateFlow<Float> = _rmsLevel.asStateFlow()

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError.asStateFlow()

    private var currentSpeechCallback: (() -> Unit)? = null

    init {
        initTts()
    }

    private fun initTts() {
        try {
            textToSpeech = TextToSpeech(context.applicationContext) { status ->
                mainHandler.post {
                    if (status == TextToSpeech.SUCCESS) {
                        isTtsReady = true
                        val localesToTry = listOf(Locale.US, Locale.UK, Locale.ENGLISH, Locale.getDefault())
                        for (loc in localesToTry) {
                            val res = textToSpeech?.setLanguage(loc)
                            if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) {
                                break
                            }
                        }
                        textToSpeech?.setSpeechRate(0.85f)
                        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                            override fun onStart(utteranceId: String?) {
                                mainHandler.post {
                                    _isSpeaking.value = true
                                }
                            }

                            override fun onDone(utteranceId: String?) {
                                mainHandler.post {
                                    _isSpeaking.value = false
                                    val cb = currentSpeechCallback
                                    currentSpeechCallback = null
                                    cb?.invoke()
                                }
                            }

                            @Deprecated("Deprecated in Java")
                            override fun onError(utteranceId: String?) {
                                mainHandler.post {
                                    _isSpeaking.value = false
                                    val cb = currentSpeechCallback
                                    currentSpeechCallback = null
                                    cb?.invoke()
                                }
                            }
                        })

                        // Check if there was pending speech before TTS was ready
                        pendingSpeakText?.let { text ->
                            val rate = pendingSpeechRate
                            val cb = pendingSpeechCallback
                            pendingSpeakText = null
                            pendingSpeechCallback = null
                            speak(text, rate, cb)
                        }
                    } else {
                        Log.e(TAG, "TTS Initialization failed with status: $status")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing TTS: ${e.message}")
        }
    }

    fun speak(text: String, rate: Float = 0.85f, onDone: (() -> Unit)? = null) {
        if (text.isBlank()) {
            onDone?.invoke()
            return
        }

        mainHandler.post {
            try {
                if (!isTtsReady || textToSpeech == null) {
                    pendingSpeakText = text
                    pendingSpeechRate = rate
                    pendingSpeechCallback = onDone
                    initTts()
                    return@post
                }
                currentSpeechCallback = onDone
                
                // Smart language detection & accent configuration
                configureTtsForText(text, null)
                textToSpeech?.setSpeechRate(rate)

                val utteranceId = "SPEECH_${System.currentTimeMillis()}"
                _isSpeaking.value = true
                val result = textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                if (result == TextToSpeech.ERROR) {
                    _isSpeaking.value = false
                    onDone?.invoke()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to speak text: ${e.message}")
                _isSpeaking.value = false
                onDone?.invoke()
            }
        }
    }

    /**
     * Explicitly speak text using authentic Uzbek accent / TTS engine.
     */
    fun speakUzbek(text: String, rate: Float = 0.90f, onDone: (() -> Unit)? = null) {
        if (text.isBlank()) {
            onDone?.invoke()
            return
        }

        mainHandler.post {
            try {
                if (!isTtsReady || textToSpeech == null) {
                    pendingSpeakText = text
                    pendingSpeechRate = rate
                    pendingSpeechCallback = onDone
                    initTts()
                    return@post
                }
                currentSpeechCallback = onDone
                configureTtsForText(text, forceUzbek = true)
                textToSpeech?.setSpeechRate(rate)

                val utteranceId = "SPEECH_UZ_${System.currentTimeMillis()}"
                _isSpeaking.value = true
                val result = textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                if (result == TextToSpeech.ERROR) {
                    _isSpeaking.value = false
                    onDone?.invoke()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to speak Uzbek text: ${e.message}")
                _isSpeaking.value = false
                onDone?.invoke()
            }
        }
    }

    /**
     * Explicitly speak text using English TTS voice.
     */
    fun speakEnglish(text: String, rate: Float = 0.85f, onDone: (() -> Unit)? = null) {
        if (text.isBlank()) {
            onDone?.invoke()
            return
        }

        mainHandler.post {
            try {
                if (!isTtsReady || textToSpeech == null) {
                    pendingSpeakText = text
                    pendingSpeechRate = rate
                    pendingSpeechCallback = onDone
                    initTts()
                    return@post
                }
                currentSpeechCallback = onDone
                configureTtsForText(text, forceUzbek = false)
                textToSpeech?.setSpeechRate(rate)

                val utteranceId = "SPEECH_EN_${System.currentTimeMillis()}"
                _isSpeaking.value = true
                val result = textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
                if (result == TextToSpeech.ERROR) {
                    _isSpeaking.value = false
                    onDone?.invoke()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to speak English text: ${e.message}")
                _isSpeaking.value = false
                onDone?.invoke()
            }
        }
    }

    /**
     * Determines whether the given text is Uzbek.
     */
    fun isLikelyUzbekText(text: String): Boolean {
        val clean = text.lowercase(Locale.ROOT)
        // Cyrillic Uzbek/Russian characters
        if (clean.any { it in '\u0400'..'\u04FF' }) return true

        // Specific Uzbek characters and apostrophe variants
        if (clean.contains("o'") || clean.contains("g'") ||
            clean.contains("oʻ") || clean.contains("gʻ") ||
            clean.contains("o’") || clean.contains("g’") ||
            clean.contains("o‘") || clean.contains("g‘")
        ) {
            return true
        }

        // Common Uzbek functional words and grammatical suffixes
        val uzbekMarkers = listOf(
            "va", "bir", "bu", "uchun", "bilan", "ham", "emas", "kerak", "bo'lib", "bo'lgan",
            "qilish", "deb", "lekin", "har", "o'z", "gap", "so'z", "kun", "ish", "tarjima",
            "qiling", "aytmoq", "bering", "bo'lmoq", "yordam", "maqsad", "vazifa", "dars",
            "yaxshi", "katta", "kichik", "ko'p", "kam", "inson", "odam", "til", "ingliz",
            "o'zbek", "bormi", "yo'q", "boshqa", "barcha", "yangi", "eski", "to'g'ri", "xato",
            "natija", "ertalab", "kechqurun", "bugun", "kecha", "ertaga", "shunday", "qanday",
            "nimaga", "qayerda", "qachon", "kim", "nima", "qaysi", "biri", "daraja", "mashq",
            "haqida", "to'g'risida", "yuqorisida", "orasida", "ostida", "yonida", "ichida",
            "sababli", "tufayli", "chunki", "agar", "ammo", "biroq", "misol", "ta'rif"
        )
        val tokens = clean.split(Regex("[\\s.,!?;:\"'()«»]+")).filter { it.length >= 2 }
        if (tokens.isEmpty()) return false
        val matches = tokens.count { token ->
            uzbekMarkers.any { marker ->
                if (marker.length <= 3) token == marker || token.endsWith(marker)
                else token.contains(marker)
            }
        }
        return (matches.toFloat() / tokens.size.toFloat()) >= 0.20f || matches >= 1
    }

    private fun configureTtsForText(text: String, forceUzbek: Boolean?) {
        val tts = textToSpeech ?: return
        val isUzbek = forceUzbek ?: isLikelyUzbekText(text)

        if (isUzbek) {
            // Apply Uzbek accent / Turkic phonetic engine
            val uzbekLocales = listOf(
                Locale("uz", "UZ"),
                Locale("uz"),
                Locale("tr", "TR"), // Turkish uses near-identical phonetics for Latin text
                Locale("tr"),
                Locale("az", "AZ")
            )
            var matched = false
            for (loc in uzbekLocales) {
                val res = tts.setLanguage(loc)
                if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) {
                    matched = true
                    break
                }
            }
            if (!matched) {
                tts.setLanguage(Locale.getDefault())
            }
            tts.setPitch(1.05f) // natural intonation for Uzbek speech
        } else {
            // Standard English
            val englishLocales = listOf(Locale.US, Locale.UK, Locale.ENGLISH)
            for (loc in englishLocales) {
                val res = tts.setLanguage(loc)
                if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) {
                    break
                }
            }
            tts.setPitch(1.0f)
        }
    }

    fun stopSpeaking() {
        mainHandler.post {
            try {
                textToSpeech?.stop()
                _isSpeaking.value = false
                currentSpeechCallback = null
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping TTS: ${e.message}")
            }
        }
    }

    fun isRecognitionServiceAvailable(): Boolean {
        return SpeechRecognizer.isRecognitionAvailable(context)
    }

    fun createSpeechIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US")
            putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "en-US")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Inglizcha gapiring (Speak English)...")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }

    fun startListening(
        onResult: (String) -> Unit = {},
        onError: (String) -> Unit = {},
        onPartial: (String) -> Unit = {}
    ) {
        mainHandler.post {
            try {
                stopSpeaking()
                _lastError.value = null
                _partialText.value = ""
                _rmsLevel.value = 0f

                if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                    val errMsg = "Ovozni aniqlash xizmati mavjud emas. Tizim oynasidan foydalaning."
                    _lastError.value = errMsg
                    onError(errMsg)
                    return@post
                }

                speechRecognizer?.destroy()
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                    setRecognitionListener(object : RecognitionListener {
                        override fun onReadyForSpeech(params: Bundle?) {
                            mainHandler.post {
                                _isListening.value = true
                                _lastError.value = null
                            }
                        }

                        override fun onBeginningOfSpeech() {
                            mainHandler.post {
                                _isListening.value = true
                            }
                        }

                        override fun onRmsChanged(rmsdB: Float) {
                            mainHandler.post {
                                // Normalize rms dB to 0.0f - 1.0f for smooth UI pulsing
                                val normalized = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
                                _rmsLevel.value = normalized
                            }
                        }

                        override fun onBufferReceived(buffer: ByteArray?) {}

                        override fun onEndOfSpeech() {
                            mainHandler.post {
                                _isListening.value = false
                                _rmsLevel.value = 0f
                            }
                        }

                        override fun onError(error: Int) {
                            mainHandler.post {
                                _isListening.value = false
                                _rmsLevel.value = 0f
                                val errorMsg = when (error) {
                                    SpeechRecognizer.ERROR_NO_MATCH -> "Ovoz ajratib olinmadi. Qaytadan aniqroq gapiring."
                                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Ovoz eshitilmadi. Mikrofonga yaqinroq gapiring."
                                    SpeechRecognizer.ERROR_AUDIO -> "Mikrofon xatosi yuz berdi."
                                    SpeechRecognizer.ERROR_NETWORK, SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Internet aloqasi sekin."
                                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Ovoz xizmati band, qayta urinilmoqda..."
                                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Mikrofon ruxsati berilmagan."
                                    else -> "Ovozni aniqlab bo'lmadi ($error)."
                                }
                                _lastError.value = errorMsg
                                onError(errorMsg)
                            }
                        }

                        override fun onResults(results: Bundle?) {
                            mainHandler.post {
                                _isListening.value = false
                                _rmsLevel.value = 0f
                                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                                val text = matches?.firstOrNull() ?: ""
                                if (text.isNotBlank()) {
                                    _recognizedText.value = text
                                    _partialText.value = text
                                    onResult(text)
                                } else {
                                    onError("Hech narsa eshitilmadi")
                                }
                            }
                        }

                        override fun onPartialResults(partialResults: Bundle?) {
                            mainHandler.post {
                                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                                val text = matches?.firstOrNull() ?: ""
                                if (text.isNotBlank()) {
                                    _partialText.value = text
                                    onPartial(text)
                                }
                            }
                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {}
                    })
                }

                val intent = createSpeechIntent()
                speechRecognizer?.startListening(intent)
            } catch (e: Exception) {
                _isListening.value = false
                val msg = e.message ?: "Ovozni boshlab bo'lmadi"
                _lastError.value = msg
                onError(msg)
            }
        }
    }

    fun stopListening() {
        mainHandler.post {
            try {
                speechRecognizer?.stopListening()
                _isListening.value = false
                _rmsLevel.value = 0f
            } catch (e: Exception) {
                Log.e(TAG, "Error stopping listener: ${e.message}")
            }
        }
    }

    fun destroy() {
        mainHandler.post {
            try {
                textToSpeech?.stop()
                textToSpeech?.shutdown()
                speechRecognizer?.destroy()
                speechRecognizer = null
            } catch (e: Exception) {
                Log.e(TAG, "Error destroying speech manager: ${e.message}")
            }
        }
    }
}
