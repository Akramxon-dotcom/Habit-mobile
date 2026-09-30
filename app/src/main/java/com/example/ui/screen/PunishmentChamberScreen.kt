package com.example.ui.screen

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.discipline.*
import com.example.data.local.HabitPreferences
import com.example.data.remote.TelegramClient
import com.example.ui.theme.LocalLiquidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PunishmentChamberScreen(
    taskTitle: String,
    taskCategory: String,
    onPunishmentCompleted: (type: PunishmentType, repsDone: Int) -> Unit,
    onEmergencyDismiss: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { HabitPreferences(context) }
    val scope = rememberCoroutineScope()
    val theme = LocalLiquidTheme.current

    // Block back button during punishment!
    BackHandler(enabled = true) {
        Toast.makeText(context, "⚠️ Jazo bajarilmaguncha orqaga qaytib bo'lmaydi!", Toast.LENGTH_SHORT).show()
    }

    val dailyMissCount = remember { prefs.getTodayMissCount() + 1 }
    val eligibleTypes = remember { PunishmentPlanner.getEligibleOptionsForCategory(taskCategory) }

    var selectedType by remember { mutableStateOf<PunishmentType?>(eligibleTypes.firstOrNull() ?: PunishmentType.PUSH_UP) }
    var activePlan by remember { mutableStateOf<PunishmentPlan?>(null) }
    var isStarted by remember { mutableStateOf(false) }

    // Execution states
    var currentSetIndex by remember { mutableIntStateOf(1) }
    var currentRepsInSet by remember { mutableIntStateOf(0) }
    var isResting by remember { mutableStateOf(false) }
    var restSecondsRemaining by remember { mutableIntStateOf(0) }
    var isPlankTremorValid by remember { mutableStateOf(true) }
    var totalRepsFinished by remember { mutableIntStateOf(0) }
    var isFullyCompleted by remember { mutableStateOf(false) }

    // 10-minute Telegram penalty watchdog
    var elapsedSeconds by remember { mutableIntStateOf(0) }
    var telegramAlertSent by remember { mutableStateOf(false) }

    // Sensor engine
    val sensorEngine = remember {
        DisciplineSensorEngine(
            context = context,
            onRepetitionCounted = { total ->
                // Vibration feedback
                try {
                    val v = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                    v?.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
                } catch (_: Exception) {}
            },
            onPlankTremorTick = { valid ->
                isPlankTremorValid = valid
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            sensorEngine.stopListening()
        }
    }

    // Watchdog timer (10 mins Telegram rule)
    LaunchedEffect(isStarted, isFullyCompleted) {
        if (isStarted && !isFullyCompleted) {
            while (true) {
                delay(1000L)
                elapsedSeconds++
                if (elapsedSeconds >= 600 && !telegramAlertSent) {
                    telegramAlertSent = true
                    val token = prefs.telegramBotToken
                    val chatId = prefs.telegramChatId
                    if (token.isNotBlank() && chatId.isNotBlank()) {
                        val alertMsg = "⚠️ <b>INTIZOM QOIDASI BUZILDI!</b>\n\n" +
                                "Vazifa: <b>$taskTitle</b> ($taskCategory)\n" +
                                "Belgilangan jazo: <b>${activePlan?.type?.title}</b>\n" +
                                "Holat: Jazo boshlanganidan 10 daqiqa o'tdi, ammo hali ham yakunlanmadi!\n" +
                                "Bugungi kechikishlar soni: <b>$dailyMissCount-marta</b>."
                        TelegramClient.sendReport(token, chatId, alertMsg)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1E0B0B), Color(0xFF090404))))
    ) {
        if (!isStarted) {
            // ====================================================
            // SELECTION VIEW: Jazo turi va Setlar Rejasini Tanlash
            // ====================================================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color(0xFFEF4444).copy(alpha = 0.2f), CircleShape)
                            .border(2.dp, Color(0xFFEF4444), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⚖️", fontSize = 32.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Murosasiz Mas'uliyat Maydoni",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFEF4444),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Erinchoqlikni yo'qotish va irodani chiniqtirish qonuni",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Missed Task Details Banner
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Bajarilmagan vazifa:",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                                Text(
                                    "Bugun: $dailyMissCount-xato",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF87171)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                taskTitle.ifBlank { "Nomsiz vazifa" },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "Toifa: ${taskCategory.uppercase()}",
                                fontSize = 11.sp,
                                color = Color(0xFFFBBF24)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Toifaga mos jazo turlari:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2 toifaga mos variantlar
                    eligibleTypes.take(3).forEach { pType ->
                        val isSel = selectedType == pType
                        val plan = remember(pType, dailyMissCount) {
                            PunishmentPlanner.buildPlan(pType, dailyMissCount, taskTitle, taskCategory)
                        }
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSel) Color(0xFFEF4444).copy(alpha = 0.2f) else Color.White.copy(alpha = 0.04f)
                            ),
                            border = BorderStroke(
                                if (isSel) 2.dp else 1.dp,
                                if (isSel) Color(0xFFEF4444) else Color.White.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedType = pType }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(pType.icon, fontSize = 26.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        pType.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    val setInfo = if (plan.setStructure.setsCount > 1) {
                                        "${plan.setStructure.setsCount} ta set x ${plan.setStructure.repsPerSet} ${pType.unit} (${plan.setStructure.restSecondsBetweenSets}s tanaffus)"
                                    } else {
                                        "${plan.targetAmount} ${pType.unit} to'liq"
                                    }
                                    Text(
                                        "Yuklama: $setInfo",
                                        fontSize = 11.sp,
                                        color = if (isSel) Color(0xFFFCA5A5) else Color.White.copy(alpha = 0.6f)
                                    )
                                }
                                if (isSel) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // "O'zing tanla" Ruletka tugmasi
                    OutlinedButton(
                        onClick = {
                            val allTypes = PunishmentType.values()
                            selectedType = allTypes.random()
                            Toast.makeText(context, "🎲 Tasodifiy tanlandi: ${selectedType?.title}", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFBBF24)),
                        border = BorderStroke(1.dp, Color(0xFFFBBF24).copy(alpha = 0.6f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🎲 O'zing tanla (Jazo Ruletkasi)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                // Start button
                Column(modifier = Modifier.fillMaxWidth()) {
                    val currentSelected = selectedType ?: PunishmentType.PUSH_UP
                    val plan = remember(currentSelected, dailyMissCount) {
                        PunishmentPlanner.buildPlan(currentSelected, dailyMissCount, taskTitle, taskCategory)
                    }

                    // Anti-cheat reminder
                    Text(
                        "🔒 Tekshiruv: ${plan.antiCheatRule}",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            activePlan = plan
                            isStarted = true
                            prefs.incrementTodayMissCount()

                            // Start sensor if physical
                            when (plan.type) {
                                PunishmentType.PUSH_UP -> sensorEngine.startListening(DisciplineExerciseMode.PUSH_UP)
                                PunishmentType.SQUAT -> sensorEngine.startListening(DisciplineExerciseMode.SQUAT)
                                PunishmentType.PLANK -> sensorEngine.startListening(DisciplineExerciseMode.PLANK)
                                PunishmentType.RUNNING_ON_SPOT -> sensorEngine.startListening(DisciplineExerciseMode.RUNNING_ON_SPOT)
                                else -> {}
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔥 Jazolashni Boshlash (Murosasiz)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } else if (isFullyCompleted) {
            // ====================================================
            // SUCCESS CELEBRATION & STATS RECORDED
            // ====================================================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(Color(0xFF10B981).copy(alpha = 0.2f), CircleShape)
                        .border(3.dp, Color(0xFF10B981), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🛡️", fontSize = 44.sp)
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    "Jazo Sharaf Bilan O'tildi!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF10B981),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Siz erinchoqlikni jismoniy va irodaviy javobgarlik bilan yengdingiz. Natijangiz statistika xazinasiga yozildi.",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Bajarilgan Yuklama:", fontSize = 11.sp, color = Color.White.copy(alpha = 0.6f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "${activePlan?.targetAmount} ${activePlan?.type?.unit} ${activePlan?.type?.title}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        activePlan?.let { p ->
                            prefs.recordPunishmentCompleted(p.type, p.targetAmount)
                            onPunishmentCompleted(p.type, p.targetAmount)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Jadvalga Qaytish", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        } else {
            // ====================================================
            // ACTIVE EXECUTION VIEW (Datchik / Matn / Tasbeh / Test)
            // ====================================================
            val plan = activePlan ?: return@Box

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top HUD with 10-minute Telegram alert countdown
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(plan.type.icon, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(plan.type.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        // 10-minute Telegram indicator
                        val remainingSec = (600 - elapsedSeconds).coerceAtLeast(0)
                        val min = remainingSec / 60
                        val sec = remainingSec % 60
                        Box(
                            modifier = Modifier
                                .background(if (remainingSec <= 120) Color(0xFFEF4444).copy(alpha = 0.3f) else Color.White.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "TG Hisobot: ${String.format("%02d:%02d", min, sec)}",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (remainingSec <= 120) Color(0xFFEF4444) else Color(0xFFFBBF24)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Sets progress bar
                    if (plan.setStructure.setsCount > 1) {
                        Text(
                            "Set $currentSetIndex / ${plan.setStructure.setsCount} (${plan.setStructure.repsPerSet} ${plan.type.unit} dan)",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { ((currentSetIndex - 1) * plan.setStructure.repsPerSet + currentRepsInSet).toFloat() / plan.targetAmount.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFFEF4444),
                            trackColor = Color.White.copy(alpha = 0.15f)
                        )
                    }
                }

                // Center Exercise Execution Block
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (isResting) {
                        // Rest screen between sets
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🧘 TANAFFUS VA NAFAS ROSTLASH", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Mushaklar tiklanishi uchun qisqa dam:", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .size(110.dp)
                                    .background(Color(0xFF38BDF8).copy(alpha = 0.15f), CircleShape)
                                    .border(2.dp, Color(0xFF38BDF8), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "$restSecondsRemaining",
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Keyingi setga tayyorlaning...", fontSize = 11.sp, color = Color.White.copy(alpha = 0.5f))
                        }
                    } else {
                        when (plan.type) {
                            PunishmentType.PUSH_UP,
                            PunishmentType.SQUAT,
                            PunishmentType.RUNNING_ON_SPOT -> {
                                PhysicalSensorDisplay(
                                    plan = plan,
                                    currentRepsInSet = sensorEngine.currentReps,
                                    currentSetIndex = currentSetIndex,
                                    onSetComplete = {
                                        val totalSoFar = (currentSetIndex - 1) * plan.setStructure.repsPerSet + sensorEngine.currentReps
                                        if (currentSetIndex >= plan.setStructure.setsCount) {
                                            sensorEngine.stopListening()
                                            isFullyCompleted = true
                                        } else {
                                            // Start rest
                                            isResting = true
                                            restSecondsRemaining = plan.setStructure.restSecondsBetweenSets
                                            sensorEngine.resetReps(0)
                                            scope.launch {
                                                while (restSecondsRemaining > 0) {
                                                    delay(1000L)
                                                    restSecondsRemaining--
                                                }
                                                isResting = false
                                                currentSetIndex++
                                            }
                                        }
                                    }
                                )
                            }

                            PunishmentType.PLANK -> {
                                PlankExerciseDisplay(
                                    plan = plan,
                                    isTremorValid = isPlankTremorValid,
                                    onComplete = {
                                        sensorEngine.stopListening()
                                        isFullyCompleted = true
                                    }
                                )
                            }

                            PunishmentType.TASBEH -> {
                                TasbehExerciseDisplay(
                                    targetCount = plan.targetAmount,
                                    onComplete = { isFullyCompleted = true }
                                )
                            }

                            PunishmentType.VOCAB_DRILL -> {
                                VocabDrillExerciseDisplay(
                                    targetWordsCount = plan.targetAmount,
                                    onComplete = { isFullyCompleted = true }
                                )
                            }

                            PunishmentType.GRAMMAR_TEST -> {
                                GrammarTestExerciseDisplay(
                                    targetQuestions = plan.targetAmount,
                                    onComplete = { isFullyCompleted = true }
                                )
                            }

                            PunishmentType.COPY_PARAGRAPH -> {
                                CopyParagraphExerciseDisplay(
                                    onComplete = { isFullyCompleted = true }
                                )
                            }

                            PunishmentType.QURAN_TILOVAT,
                            PunishmentType.SILENCE_MEDITATION -> {
                                TimedFocusExerciseDisplay(
                                    title = plan.type.title,
                                    targetMinutes = plan.targetAmount,
                                    onComplete = { isFullyCompleted = true }
                                )
                            }

                            PunishmentType.TIME_FORFEIT -> {
                                TimeForfeitDisplay(
                                    forfeitMinutes = plan.targetAmount,
                                    onConfirm = { isFullyCompleted = true }
                                )
                            }
                        }
                    }
                }

                // Bottom Anti-Cheat & Instructions
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("💡 Ko'rsatma:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFBBF24))
                        Text(plan.instruction, fontSize = 11.sp, color = Color.White.copy(alpha = 0.75f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("🛡️ Anti-Cheat: ${plan.antiCheatRule}", fontSize = 10.sp, color = Color(0xFFF87171))
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------------
// EXERCISE COMPONENTS
// ---------------------------------------------------------------------------------

@Composable
private fun PhysicalSensorDisplay(
    plan: PunishmentPlan,
    currentRepsInSet: Int,
    currentSetIndex: Int,
    onSetComplete: () -> Unit
) {
    val targetInThisSet = plan.setStructure.repsPerSet
    val repsClamped = currentRepsInSet.coerceAtMost(targetInThisSet)

    LaunchedEffect(repsClamped) {
        if (repsClamped >= targetInThisSet) {
            onSetComplete()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFFEF4444).copy(alpha = 0.15f), CircleShape)
                .border(3.dp, Color(0xFFEF4444), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "$repsClamped",
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )
                Text(
                    "/ $targetInThisSet ${plan.type.unit}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            if (plan.type == PunishmentType.PUSH_UP) "Ko'kragingizni telefon datchigiga yaqinlashtiring" else "Harakatni davom ettiring",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PlankExerciseDisplay(
    plan: PunishmentPlan,
    isTremorValid: Boolean,
    onComplete: () -> Unit
) {
    var secondsHeld by remember { mutableIntStateOf(0) }
    val targetSeconds = plan.targetAmount

    LaunchedEffect(isTremorValid) {
        while (secondsHeld < targetSeconds) {
            delay(1000L)
            if (isTremorValid) {
                secondsHeld++
            }
        }
        onComplete()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(170.dp)
                .background(if (isTremorValid) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f), CircleShape)
                .border(3.dp, if (isTremorValid) Color(0xFF10B981) else Color(0xFFEF4444), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "$secondsHeld",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )
                Text(
                    "/ $targetSeconds soniya",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isTremorValid) {
            Text(
                "⚠️ Qimirlamayapsiz yoki telefon stolga qo'yilgan!\nPlanka ushlashdagi mushak tebranishi sezilmadi. Taymer to'xtatildi.",
                fontSize = 11.sp,
                color = Color(0xFFEF4444),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                "✅ Mushaklar mikrotebranishi tekshirildi (Inson planka holatida). Davom eting!",
                fontSize = 11.sp,
                color = Color(0xFF10B981),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TasbehExerciseDisplay(
    targetCount: Int,
    onComplete: () -> Unit
) {
    var currentCount by remember { mutableIntStateOf(0) }
    var lastTapTimeMs by remember { mutableLongStateOf(0L) }
    var warningMessage by remember { mutableStateOf<String?>(null) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("📿 Astag'firulloh Tasbehi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Text("Qalb bilan va shoshilmasdan ayting (Min. 0.4s oraliq)", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(170.dp)
                .background(Color(0xFF38BDF8).copy(alpha = 0.15f), CircleShape)
                .border(3.dp, Color(0xFF38BDF8), CircleShape)
                .clickable {
                    val now = System.currentTimeMillis()
                    if (now - lastTapTimeMs < 400) {
                        warningMessage = "⚠️ Juda tez! Tartil bilan ayting. Qalloblik qabul qilinmaydi."
                    } else {
                        warningMessage = null
                        currentCount++
                        lastTapTimeMs = now
                        if (currentCount >= targetCount) {
                            onComplete()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("$currentCount", fontSize = 54.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace, color = Color.White)
                Text("/ $targetCount", fontSize = 13.sp, color = Color.White.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(4.dp))
                Text("BOSING 👆", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        warningMessage?.let {
            Text(it, fontSize = 11.sp, color = Color(0xFFEF4444), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun VocabDrillExerciseDisplay(
    targetWordsCount: Int,
    onComplete: () -> Unit
) {
    val drillWords = remember {
        listOf(
            "discipline" to "intizom",
            "responsibility" to "mas'uliyat",
            "perseverance" to "matonat",
            "procrastination" to "kechiktirish / erinchoqlik",
            "commitment" to "qat'iy va'da",
            "consistency" to "davomiylik",
            "resilience" to "chidam",
            "integrity" to "vijdonlilik",
            "achievement" to "yutuq",
            "focus" to "diqqat",
            "punctuality" to "vaqtga rioya qilish",
            "diligence" to "mehnatsevarlik"
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var typedInput by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val currentPair = drillWords[currentIndex % drillWords.size]

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("So'z ${currentIndex + 1} / $targetWordsCount", fontSize = 13.sp, color = Color.White.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(10.dp))
        Text(currentPair.first, fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color(0xFF38BDF8))
        Text("(${currentPair.second})", fontSize = 14.sp, color = Color.White.copy(alpha = 0.7f))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = typedInput,
            onValueChange = {
                typedInput = it
                errorMsg = null
            },
            label = { Text("So'zni aniq ko'chirib yozing", color = Color.White.copy(alpha = 0.6f)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF38BDF8),
                unfocusedBorderColor = Color.White.copy(alpha = 0.3f)
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (typedInput.trim().equals(currentPair.first.trim(), ignoreCase = true)) {
                    typedInput = ""
                    errorMsg = null
                    currentIndex++
                    if (currentIndex >= targetWordsCount) {
                        onComplete()
                    }
                } else {
                    errorMsg = "❌ Bitta harfda ham xato bo'lmasligi shart! Qayta tekshiring."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38BDF8)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tasdiqlash (Keyingi so'z)", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        errorMsg?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(it, fontSize = 11.sp, color = Color(0xFFEF4444))
        }
    }
}

@Composable
private fun GrammarTestExerciseDisplay(
    targetQuestions: Int,
    onComplete: () -> Unit
) {
    val questions = remember {
        listOf(
            Triple("I usually ___ up at 6 AM, but today I ___ at 7 AM.", listOf("wake / woke", "woke / wake", "waking / woken"), 0),
            Triple("If you ___ harder, you will succeed.", listOf("work", "will work", "worked"), 0),
            Triple("She has been studying ___ three hours.", listOf("for", "since", "during"), 0),
            Triple("He is interested ___ learning computer science.", listOf("in", "on", "at"), 0),
            Triple("By this time tomorrow, we ___ our tasks.", listOf("will have finished", "will finish", "have finished"), 0),
            Triple("Neither John ___ his brother arrived on time.", listOf("nor", "or", "and"), 0)
        )
    }

    var qIndex by remember { mutableIntStateOf(0) }
    var correctCount by remember { mutableIntStateOf(0) }
    val currentQ = questions[qIndex % questions.size]

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Savol ${qIndex + 1} / $targetQuestions (To'g'ri: $correctCount)", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(10.dp))
        Text(currentQ.first, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(14.dp))

        currentQ.second.forEachIndexed { optIndex, optText ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        if (optIndex == currentQ.third) {
                            correctCount++
                        }
                        qIndex++
                        if (qIndex >= targetQuestions) {
                            if (correctCount >= (targetQuestions * 0.75).toInt()) {
                                onComplete()
                            } else {
                                qIndex = 0
                                correctCount = 0
                            }
                        }
                    }
            ) {
                Text(optText, fontSize = 14.sp, color = Color.White, modifier = Modifier.padding(14.dp))
            }
        }
    }
}

@Composable
private fun CopyParagraphExerciseDisplay(
    onComplete: () -> Unit
) {
    val paragraph = "Discipline is the bridge between goals and accomplishment. Without rigorous self-control, talent becomes wasted potential."
    var typedText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Quyidagi matnni harfma-harf xatosiz tering:", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(8.dp))
        Text(paragraph, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFBBF24))

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = typedText,
            onValueChange = {
                typedText = it
                if (it.trim() == paragraph.trim()) {
                    onComplete()
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF10B981)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
    }
}

@Composable
private fun TimedFocusExerciseDisplay(
    title: String,
    targetMinutes: Int,
    onComplete: () -> Unit
) {
    val totalSec = targetMinutes * 60
    var elapsed by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (elapsed < totalSec) {
            delay(1000L)
            elapsed++
        }
        onComplete()
    }

    val remaining = totalSec - elapsed
    val m = remaining / 60
    val s = remaining % 60

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFF38BDF8).copy(alpha = 0.15f), CircleShape)
                .border(2.dp, Color(0xFF38BDF8), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                String.format("%02d:%02d", m, s),
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text("Diqqatni jamlang va ekranni yoniq saqlang", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
    }
}

@Composable
private fun TimeForfeitDisplay(
    forfeitMinutes: Int,
    onConfirm: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("⏳ Vaqt Sandig'i Jarimasi", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color(0xFFEF4444))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Vazifani o'tkazib yuborganingiz sababli, erkin vaqt jamg'armangizdan $forfeitMinutes daqiqa ushlab qolinadi.",
            fontSize = 13.sp,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onConfirm,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Jarimani Qabul Qilish (-$forfeitMinutes daqiqa)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
