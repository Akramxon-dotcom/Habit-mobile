package com.example.ui.block

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.HabitPreferences
import com.example.data.model.TaskTimeEngine
import com.example.ui.theme.HabitTheme

class FocusBlockActivity : ComponentActivity() {

    companion object {
        @Volatile var isVisible: Boolean = false
    }

    private val taskTitleState = mutableStateOf("Rejadagi vazifa")
    private val taskTimeState = mutableStateOf("Ayni vaqt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isVisible = true

        // Show over lockscreen and turn screen on
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val prefs = HabitPreferences(this)
        taskTitleState.value = intent.getStringExtra("task_title") ?: "Rejadagi vazifa"
        taskTimeState.value = intent.getStringExtra("task_time") ?: "Ayni vaqt"

        setContent {
            HabitTheme {
                FocusBlockScreen(
                    taskTitle = taskTitleState.value,
                    taskTime = taskTimeState.value,
                    onReturnToTask = {
                        returnToHome()
                    },
                    onEmergencyBypassGranted = { minutes ->
                        prefs.setTemporaryEmergencyBypass(minutes)
                        finish()
                    },
                    onOpenDialer = {
                        try {
                            val dialIntent = Intent(Intent.ACTION_DIAL)
                            dialIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(dialIntent)
                            finish()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        isVisible = true
        taskTitleState.value = intent.getStringExtra("task_title") ?: taskTitleState.value
        taskTimeState.value = intent.getStringExtra("task_time") ?: taskTimeState.value
    }

    override fun onResume() {
        super.onResume()
        isVisible = true
    }

    override fun onStop() {
        super.onStop()
        isVisible = false
    }

    override fun onDestroy() {
        isVisible = false
        super.onDestroy()
    }

    private fun returnToHome() {
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(homeIntent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Prevent back press from dismissing block screen
        returnToHome()
    }
}

@Composable
fun FocusBlockScreen(
    taskTitle: String,
    taskTime: String,
    onReturnToTask: () -> Unit,
    onEmergencyBypassGranted: (Int) -> Unit,
    onOpenDialer: () -> Unit
) {
    var showPsychologicalDialog by remember { mutableStateOf(false) }
    var showSecretBypassDialog by remember { mutableStateOf(false) }

    // Stepper for psychological dissuasion flow (1..4)
    var currentStep by remember { mutableStateOf(1) }
    var selectedReason by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF140202),
                        Color(0xFF0A0A0A),
                        Color(0xFF000000)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Serious Lock / Warning Icon
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Color(0xFF3B0B0B), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Ogohlantirish",
                    tint = Color(0xFFFF3B30),
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Strictly Serious Header (No politeness, pure stern discipline)
            Text(
                text = "TELEFONNI DARHOL JOYIGA QO'Y!",
                color = Color(0xFFFF3B30),
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Task info badge
            Surface(
                color = Color(0xFF221111),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "HOZIRGI REJADAGI VAZIFA:",
                        color = Color(0xFFFFAA99),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = taskTitle,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = taskTime,
                        color = Color(0xFFFF6B6B),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Unapologetic, stern masculine explanation
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF191010)),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sen o'zingga bergan va'dang qayerda qoldi?!",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Bo'shashishni bas qil. Bir martagina chalg'ish seni yana eski beparvo holatingga qaytaradi. Maqsadlaringdan arzonroq narsaga chalg'imoqchimisan?!",
                        color = Color(0xFFCCCCCC),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Telefon faqat zarur qo'ng'iroqlar uchun ochiq (huddi oddiy tugmali telefonga o'xshab). Qolgan barcha chalg'ituvchi ilovalar bloklangan. Bor, o'z vazifangni qil!",
                        color = Color(0xFFFFA39E),
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Primary Big Action Button: Return to task immediately
            Button(
                onClick = onReturnToTask,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "XATONI TUSHUNDIM, VAZIFAGA QAYTAMAN!",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Call / Phone button (Permitted like a button phone)
            OutlinedButton(
                onClick = onOpenDialer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF64B5F6)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Zarur qo'ng'iroq qilish (Dialer)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary: Majburiy istisno (Will open the 4-step psychological dissuasion test)
            TextButton(
                onClick = {
                    currentStep = 1
                    showPsychologicalDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Majburiy istisno (O'zingni sinab ko'r)",
                    color = Color(0xFF888888),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        // --- SECRET EMERGENCY BYPASS ---
        // Almost invisible, micro-sized dot in the extreme bottom-right corner
        // Bilmagan odam umuman ko'rmaydi, faqat bilgan odam bosishi mumkin
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(6.dp)
                .size(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    showSecretBypassDialog = true
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color(0x0DFFFFFF), shape = CircleShape)
            )
        }
    }

    // --- SECRET EMERGENCY BYPASS CONFIRMATION DIALOG ---
    if (showSecretBypassDialog) {
        AlertDialog(
            onDismissRequest = { showSecretBypassDialog = false },
            title = {
                Text(
                    text = "Favqulodda ruxsat (5 daqiqa)",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = "Bu rostdan ham kechiktirib bo'lmaydigan o'ta muhim hayotiy zaruratmi?\n\nTizim 5 daqiqaga vaqtincha ochiladi va vaqt tugashi bilan yana to'liq qulflanadi.",
                    color = Color(0xFFDDDDDD),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSecretBypassDialog = false
                        onEmergencyBypassGranted(5)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Ha, o'ta zarur (5 daqiqa)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSecretBypassDialog = false }) {
                    Text("Yo'q, sabr qilaman", color = Color.Gray)
                }
            },
            containerColor = Color(0xFF1E1E1E)
        )
    }

    // --- 4-STEP PSYCHOLOGICAL DISSUASION FLOW ---
    if (showPsychologicalDialog) {
        AlertDialog(
            onDismissRequest = { showPsychologicalDialog = false },
            containerColor = Color(0xFF181010),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Majburiy tekshiruv ($currentStep / 4)",
                        color = Color(0xFFFF5252),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    when (currentStep) {
                        1 -> {
                            Text(
                                text = "Bu ilovaga aynan nima sababdan kirmoqchisan?",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            val reasons = listOf(
                                1 to "Muhim xabar kelgan bo'lishi mumkin, shuni tekshirmoqchiman",
                                2 to "Shunchaki zerikdim, 2 daqiqa qarab chiqmoqchiman",
                                3 to "Qo'lim beixtiyor o'rganib qolibdi, odat bo'lib ketgan",
                                4 to "Bitta ma'lumotni tezda ko'rishim kerak"
                            )

                            reasons.forEach { (id, text) ->
                                OutlinedButton(
                                    onClick = {
                                        selectedReason = id
                                        currentStep = 2
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                                ) {
                                    Text(text = text, fontSize = 13.sp, textAlign = TextAlign.Start)
                                }
                            }
                        }

                        2 -> {
                            val questionText = when (selectedReason) {
                                1 -> "Muhim xabar emish! Dunyo to'xtab qoldimi?! Agar chindan favqulodda muhim bo'lsa telefon qilib chaqirishadi! Hozir buni ochmasang hayotingga biror xavf bormi?"
                                2 -> "Zerikdingmi?! Zerikish — maqsadsiz odamlarning bahonasi! Hali yodlanmagan so'zlar, qilinmagan vazifalar turganda qanday qilib zerikishing mumkin?! O'zingga shunday arzimas narsalarga sotilasanmi?"
                                3 -> "Odat emish! Sen odatlaringning qulimisan yoki aqli bor odammisan?! Har safar qo'ling telefonga cho'zilganida o'zingni yenga olmasang, hayotda qanday katta maqsadlarga erishasan?!"
                                else -> "Hozir shu ma'lumotsiz vazifangni tugatib bo'lmaydimi? Bu shunchaki miyangning navbatdagi aldovi! 5 daqiqa deb kirasan va 1 soating havoga uchadi!"
                            }

                            Text(
                                text = questionText,
                                color = Color(0xFFFF8A80),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    showPsychologicalDialog = false
                                    onReturnToTask()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("To'g'ri aytding, nomusim bor, vazifamga qaytaman!", fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = { currentStep = 3 },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF888888)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Baribir kirmasam bo'lmayapti, davom etaman", fontSize = 12.sp)
                            }
                        }

                        3 -> {
                            Text(
                                text = "Qara, hozir o'zingga bergan va'dangni buzish arafasidisan. Har safar 'faqat shu safar' deganingda orzularing sendan bir qadam uzoqlashadi. Shu bir martalik arzon lazzat seni qoniqtiradimi?!",
                                color = Color(0xFFFF5252),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    showPsychologicalDialog = false
                                    onReturnToTask()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Yo'q, men unday kuchsiz emasman! To'xtadim!", fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = { currentStep = 4 },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF888888)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("O'zim bilaman, baribir kirishni xohlayman", fontSize = 12.sp)
                            }
                        }

                        4 -> {
                            Text(
                                text = "Oxirgi savol: Sen o'zingni erkak kabi tutib, so'zingda turasanmi yoki bitta telefon ekraniga taslim bo'lasanmi?! Agar hozir kirsang, bu sening o'zing ustingdan nazoratni yo'qotganingni bildiradi. Qaysi birini tanlaysan?!",
                                color = Color(0xFFFF1744),
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                lineHeight = 21.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    showPsychologicalDialog = false
                                    onReturnToTask()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("MEN O'Z SO'ZIMDA TURAMAN! Vazifani qilaman!", fontWeight = FontWeight.Black)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {
                                    showPsychologicalDialog = false
                                    onEmergencyBypassGranted(3) // 3 minutes temporary bypass
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Men taslim bo'ldim, 3 daqiqaga ruxsat ber", fontSize = 12.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}
