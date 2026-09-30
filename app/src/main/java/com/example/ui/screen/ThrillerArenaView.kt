@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalLiquidTheme
import com.example.util.SpeechManager
import kotlinx.coroutines.delay
import kotlin.math.sin

enum class SpeakingSubMode(val title: String, val icon: String, val badge: String, val themeColor: Color) {
    CHAT("Standart Dialog", "💬", "A1-A2", Color(0xFF6366F1)),
    BOMB("Bomba Qutqaruv", "💣", "Adrenalin", Color(0xFFEF4444)),
    MAFIA("Mafiya Muzokarasi", "👑", "Dramatik", Color(0xFF8B5CF6)),
    INVESTOR("Shark Tank Pitch", "🚀", "Biznes", Color(0xFF10B981)),
    LIE_DETECTOR("Neyro Poligraf", "🧠", "Psixologik", Color(0xFF06B6D4)),
    RADIO("Tungi Podkast", "🎙️", "Kino", Color(0xFFF59E0B))
}

data class QuickOption(
    val english: String,
    val uzbek: String
)

data class ThrillerStep(
    val stage: Int,
    val aiSpeech: String,
    val uzbekHint: String,
    val targetKeywords: List<String>,
    val quickAnswerOptions: List<QuickOption>
)

data class ThrillerQuest(
    val id: String,
    val mode: SpeakingSubMode,
    val title: String,
    val initialTimeSec: Int,
    val characterName: String,
    val characterRole: String,
    val avatarEmoji: String,
    val introSpeech: String,
    val backgroundThemeColor: Color,
    val steps: List<ThrillerStep>,
    val winSpeech: String,
    val winUzbek: String
)

object ThrillerQuestData {
    val ALL_QUESTS = listOf(
        ThrillerQuest(
            id = "bomb_quest",
            mode = SpeakingSubMode.BOMB,
            title = "Kiber-Taktik Zararsizlantirish",
            initialTimeSec = 85,
            characterName = "Kapitan Miller",
            characterRole = "HQ EOD Tactical Commander",
            avatarEmoji = "💣",
            backgroundThemeColor = Color(0xFFEF4444),
            introSpeech = "Agent, we have an armed C4 explosive with an active radio telemetry trigger. Report the circuit voltage and board status immediately!",
            steps = listOf(
                ThrillerStep(
                    stage = 1,
                    aiSpeech = "Agent, we have an armed C4 explosive with an active radio telemetry trigger. Report the circuit voltage and board status immediately!",
                    uzbekHint = "Sapyorlar guruhiga texnik holatni ma'lum qiling: Asosiy zanjir 12 volt kuchlanishda ishlayotgani va 433 MGts chastotada radio signal tarqatayotganini ayting.",
                    targetKeywords = listOf("circuit", "voltage", "volts", "frequency", "megahertz", "signal", "anomalous"),
                    quickAnswerOptions = listOf(
                        QuickOption("The primary circuit is drawing twelve volts and transmitting an anomalous 433 Megahertz radio signal.", "Asosiy zanjir 12 volt kuchlanishda ishlayapti va 433 MGts chastotada noodatiy radio signal uzatmoqda."),
                        QuickOption("Sensors detect a micro-controller circuit linked to a high-capacity backup capacitor.", "Datchiklar yuqori sig'imli zaxira kondensatoriga ulangan mikronazoratchi mikrosxemasini aniqlamoqda."),
                        QuickOption("The digital detonator is coupled to a tamper-sensitive quantum logic switch.", "Raqamli detonator tebranishga sezgir kvant mantiqiy kalitiga ulangan.")
                    )
                ),
                ThrillerStep(
                    stage = 2,
                    aiSpeech = "Cellular uplink detected! The detonator will trigger in forty seconds. How do we disrupt the detonation signal?",
                    uzbekHint = "Radio to'lqinni uzish yo'lini buyuring: Taktik chastota chalkashtirgichni (scrambler) ishga tushirish yoki zaxira releni ajratishni ayting.",
                    targetKeywords = listOf("scrambler", "frequency", "jam", "relay", "isolate", "cellular", "ground"),
                    quickAnswerOptions = listOf(
                        QuickOption("Activate the tactical frequency scrambler to jam the cellular transmission right now.", "Uyali aloqa signalini to'sish uchun hoziroq taktik chastota chalkashtirgichni ishga tushiring."),
                        QuickOption("Isolate the auxiliary power relay to bypass the cellular trigger.", "Uyali aloqa signali yetib kelmasligi uchun yordamchi quvvat relesini zudlik bilan uzing."),
                        QuickOption("Ground the negative terminal to prevent the capacitor from discharging into the detonator.", "Kondensator quvvati detonatorga o'tib ketmasligi uchun manfiy klemmani yerga ulang.")
                    )
                ),
                ThrillerStep(
                    stage = 3,
                    aiSpeech = "Capacitor voltage is overloading! Give the final command sequence to neutralize the warhead!",
                    uzbekHint = "Yakuniy zararsizlantirish buyrug'ini qat'iy bering: Telemetriya ko'prigini kesish va asosiy zanjirni yerga ulashni ayting!",
                    targetKeywords = listOf("sever", "telemetry", "ground", "bridge", "override", "fuse", "payload", "neutralize"),
                    quickAnswerOptions = listOf(
                        QuickOption("Sever the telemetry bridge and ground the primary circuit simultaneously!", "Telemetriya ko'prigini kesib uzing va bir vaqtda asosiy zanjirni yerga ulang!"),
                        QuickOption("Override the master logic fuse and disconnect the explosive payload now!", "Asosiy mantiqiy saqlagichni majburiy to'xtating va portlovchi qismni darhol uzib oling!"),
                        QuickOption("Initiate emergency thermal containment and cut the secondary trigger wire!", "Favqulodda haroratli himoyani yoqing va ikkilamchi yoqish simini kesing!")
                    )
                )
            ),
            winSpeech = "TACTICAL THREAT NEUTRALIZED! Explosive payload dismantled. Your technical precision and high-level spoken English command averted a metropolitan disaster!",
            winUzbek = "🏆 TAKTIK G'ALABA! Portlovchi qurilma to'liq zararsizlantirildi. Aniq texnik buyruqlaringiz va ravon inglizcha nutqingiz ofatning oldini oldi!"
        ),
        ThrillerQuest(
            id = "mafia_quest",
            mode = SpeakingSubMode.MAFIA,
            title = "Sindikat Muzokarasi",
            initialTimeSec = 90,
            characterName = "Don Falcone",
            characterRole = "Bank Syndicate Boss",
            avatarEmoji = "👑",
            backgroundThemeColor = Color(0xFF8B5CF6),
            introSpeech = "Falcone speaking. Your tactical squad surrounded my vault, but I hold twenty civilians and perimeter feeds. State your official terms before I cut the line.",
            steps = listOf(
                ThrillerStep(
                    stage = 1,
                    aiSpeech = "Falcone speaking. Your tactical squad surrounded my vault, but I hold twenty civilians and perimeter feeds. State your official terms before I cut the line.",
                    uzbekHint = "Diplomatik aloqa o'rnating: Binoga hech kim kirmasligini, to'g'ridan-to'g'ri muzokara kanali va tibbiy yordam taqdim etilishini bildiring.",
                    targetKeywords = listOf("perimeter", "channel", "medical", "diplomatically", "federal", "civilian", "stand", "evacuation"),
                    quickAnswerOptions = listOf(
                        QuickOption("Nobody breaches the perimeter, Falcone. Let us establish a direct channel and guarantee immediate medical aid.", "Perimetrni hech kim buzib kirmaydi, Falkone. To'g'ridan-to'g'ri aloqa o'rnataylik va tibbiy yordamni kafolatlaylik."),
                        QuickOption("Escalating this only guarantees federal prosecution. Stand down your perimeter units so we can resolve this diplomatically.", "Vaziyatni keskinlashtirish sizga og'ir sud hukmini keltiradi. Bo'linmalaringizni to'xtating, buni diplomatik hal qilamiz."),
                        QuickOption("I represent the federal crisis unit. Our primary objective is safe civilian evacuation in exchange for an open hearing.", "Men federal inqiroz guruhidanman. Asosiy maqsadimiz — ochiq sud kafolati evaziga tinch fuqarolarni xavfsiz olib chiqish.")
                    )
                ),
                ThrillerStep(
                    stage = 2,
                    aiSpeech = "Federal promises mean nothing in this district. Why should I surrender my only leverage without guaranteed international clearance?",
                    uzbekHint = "Strategik bosim va huquqiy kafolat bering: Garovdagilarni ushlab turish faqat terrorizm moddasiga olib kelishini, xavfsiz taslim bo'lish yagona yo'l ekanini tushuntiring.",
                    targetKeywords = listOf("hostage", "surrender", "federal", "judicial", "counsel", "tactical", "resistance", "clearance"),
                    quickAnswerOptions = listOf(
                        QuickOption("Holding hostages turns this into a zero-tolerance terrorism protocol. A controlled surrender is your only viable exit.", "Garovdagilarni ushlab turish barcha muzokaralarni yo'qqa chiqaradi. Nazorat ostida taslim bo'lish — yagona to'g'ri yo'lingiz."),
                        QuickOption("We guarantee certified federal custody and judicial counsel if you release all non-combatants immediately.", "Agar barcha begunoh insonlarni hoziroq qo'yib yuborsangiz, rasmiy federal himoya va qonuniy advokatni kafolatlaymiz."),
                        QuickOption("Your external logistics network has been neutralized. Prolonging resistance only guarantees a tactical breach.", "Tashqi ta'minot tarmog'ingiz allaqachon zararsizlantirildi. Qarshilikni cho'zish faqat qurolli shturm bilan tugaydi.")
                    )
                ),
                ThrillerStep(
                    stage = 3,
                    aiSpeech = "My perimeter officers agree. We will unlock the blast doors. State your final extraction instructions.",
                    uzbekHint = "Xavfsiz evakuatsiya rejasini ayting: Qurollarni tashlash, garovdagilarni xavfsiz yo'lak orqali chiqarish va buyruqlarga bo'ysunishni uqtiring.",
                    targetKeywords = listOf("disarm", "exit", "corridor", "surrender", "transport", "instructions", "peacefully", "hands"),
                    quickAnswerOptions = listOf(
                        QuickOption("Order your men to disarm, exit with hands raised, and follow crisis team instructions.", "Odamlaringizga qurolni tashlashni, qo'llarini ko'tarib chiqishni va inqiroz guruhi ko'rsatmalariga bo'ysunishni buyuring."),
                        QuickOption("Release the hostages first through the security corridor, then surrender peacefully.", "Avval garovdagilarni xavfsiz koridor orqali chiqaring, so'ngra tinch yo'l bilan taslim bo'ling."),
                        QuickOption("All perimeter snipers are holding fire. Advance toward the marked armored transport.", "Barcha bo'linmalarga o't ochmaslik buyurildi. Qurolsiz holda maxsus zirhli transport tomon harakatlaning.")
                    )
                )
            ),
            winSpeech = "SYNDICATE SURRENDER COMPLETE! All twenty civilians rescued without casualties. Your commanding diplomatic English saved the city!",
            winUzbek = "🎖️ DIPLOMATIK G'ALABA! Barcha 20 nafar garovdagi insonlar eson-omon qutqarildi. Professional va ishonchli nutqingiz qon to'kilishini to'xtatdi!"
        ),
        ThrillerQuest(
            id = "investor_quest",
            mode = SpeakingSubMode.INVESTOR,
            title = "Shark Tank Pitch ($1M)",
            initialTimeSec = 85,
            characterName = "Mr. Kevin O'Leary",
            characterRole = "Billionaire Venture Capitalist",
            avatarEmoji = "🚀",
            backgroundThemeColor = Color(0xFF10B981),
            introSpeech = "Your sixty seconds starts now. We review fifty AI pitches weekly. What is your proprietary technology and customer acquisition model?",
            steps = listOf(
                ThrillerStep(
                    stage = 1,
                    aiSpeech = "Your sixty seconds starts now. We review fifty AI pitches weekly. What is your proprietary technology and customer acquisition model?",
                    uzbekHint = "Texnologiyangiz ustunligini ko'rsating: Nutqni 70% ga tezlashtiruvchi adaptiv model va organik foydalanuvchilar o'sishini ayting.",
                    targetKeywords = listOf("adaptive", "engine", "acquisition", "subscribers", "organic", "patents", "phonetics", "proprietary"),
                    quickAnswerOptions = listOf(
                        QuickOption("We built an adaptive multimodal voice engine that accelerates language acquisition by seventy percent.", "Biz til o'rganishni 70% ga tezlashtiruvchi ko'p yo'nalishli adaptiv ovozli AI tizimini yaratdik."),
                        QuickOption("Our platform scaled to sixty thousand active subscribers through organic peer-to-peer recommendation loops.", "Platformamiz foydalanuvchilarning o'zaro tavsiyalari orqali 60 ming faol obunachiga erishdi."),
                        QuickOption("We hold registered algorithmic patents analyzing vocal fluency and pronunciation phonetics in real time.", "Biz real vaqtda nutq ravonligi va fonetikani tahlil qiluvchi patentlangan algoritmlarga egamiz.")
                    )
                ),
                ThrillerStep(
                    stage = 2,
                    aiSpeech = "Impressive traction, but Big Tech could clone your interface overnight. How do you defend your recurring revenue margins?",
                    uzbekHint = "Himoya devorini (moat) va foydani ko'rsating: Mijoz qiymati xarajatdan 10 barobar yuqoriligini yoki yirik maktab shartnomalarini ayting.",
                    targetKeywords = listOf("lifetime", "acquisition", "retention", "dataset", "moat", "contracts", "churn", "recurring"),
                    quickAnswerOptions = listOf(
                        QuickOption("Our customer lifetime value is ten times our acquisition cost, with an eighty-five percent annual retention rate.", "Mijozning umumiy qiymati uni jalb qilish xarajatidan 10 barobar yuqori va yillik saqlanib qolish darajasi 85%."),
                        QuickOption("Our proprietary dataset of five million spoken dialogues creates an unbreachable technological moat.", "Besh milliondan ortiq jonli ovozli muloqotlar bazamiz raqobatchilar yengib o'tolmaydigan texnologik to'siq yaratadi."),
                        QuickOption("We secure multi-year institutional contracts with schools and academies that virtually eliminate customer churn.", "Biz maktablar va ta'lim markazlari bilan ko'p yillik kafolatlangan shartnomalar tuzmoqdamiz.")
                    )
                ),
                ThrillerStep(
                    stage = 3,
                    aiSpeech = "I am offering one million dollars for twenty-five percent equity and a board seat. Take it or I walk.",
                    uzbekHint = "Savdolashish va qat'iy javob berish: 25% juda og'irligini, 15% ulush taklif qilishingizni yoki xalqaro tarmoq talab qilishingizni bildiring.",
                    targetKeywords = listOf("strategic", "punitive", "counter", "percent", "equity", "distribution", "warrants", "milestones"),
                    quickAnswerOptions = listOf(
                        QuickOption("We value your strategic network, but twenty-five percent is punitive. We counter with fifteen percent equity.", "Sizning nufuzingizni qadrlaymiz, ammo 25% juda ko'p. Biz 15% ulush taklif qilamiz."),
                        QuickOption("We accept the valuation if you provide direct commercial distribution across international academic networks.", "Agar xalqaro ta'lim tarmoqlari bo'ylab to'g'ridan-to'g'ri distributsiyani ta'minlasangiz, kelishuvga rozimiz."),
                        QuickOption("We counter with twelve percent equity plus advisory warrants tied to international revenue milestones.", "Daromad ko'rsatkichlariga bog'langan bonuslar bilan 12% ulush taklif qilamiz.")
                    )
                )
            ),
            winSpeech = "YOU HAVE A DEAL! One Million Dollars committed at favorable terms! Your sharp strategic business negotiation proved world-class leadership!",
            winUzbek = "💰 KELISHUV IMZOLANDI! Investor $1,000,000 investitsiya kiritdi. Aniq iqtisodiy tushunchalar va professional biznes muzokaralaringiz g'alaba qozondi!"
        ),
        ThrillerQuest(
            id = "lie_detector_quest",
            mode = SpeakingSubMode.LIE_DETECTOR,
            title = "Kiber-Psixologik Poligraf",
            initialTimeSec = 75,
            characterName = "Neyro-Poligraf AI",
            characterRole = "Biometric Telemetry & Fraud Investigator",
            avatarEmoji = "🧠",
            backgroundThemeColor = Color(0xFF06B6D4),
            introSpeech = "Biometric voice sensor calibrated. Subject is questioned regarding the unauthorized quantum database ping at midnight. Explain the server traffic.",
            steps = listOf(
                ThrillerStep(
                    stage = 1,
                    aiSpeech = "Biometric voice sensor calibrated. Subject is questioned regarding the unauthorized quantum database ping at midnight. Explain the server traffic.",
                    uzbekHint = "Kiber-poligrafga mantiqiy tushuntirish bering: Tizimdagi xavfli zaiflikni yopish uchun avtomatlashtirilgan diagnostika o'tkazganingizni ayting.",
                    targetKeywords = listOf("security", "diagnostic", "vulnerability", "encrypted", "backup", "credentials", "patch"),
                    quickAnswerOptions = listOf(
                        QuickOption("I was running an automated security diagnostic to patch a critical zero-day vulnerability.", "Men o'ta xavfli zaiflikni tuzatish uchun avtomatlashtirilgan xavfsizlik tekshiruvini o'tkazayotgan edim."),
                        QuickOption("The network traffic was an encrypted backup synchronizing with our secondary European data center.", "Tarmoq trafigi Yevropadagi ikkilamchi ma'lumotlar markaziga yo'naltirilgan shifrlangan zaxira nusxa edi."),
                        QuickOption("My credentials were cloned during an external spear-phishing simulation earlier that week.", "Mening hisob ma'lumotlarim o'sha haftada o'tkazilgan soxta fishing hujumi vaqtida ko'chirilgan bo'lishi mumkin.")
                    )
                ),
                ThrillerStep(
                    stage = 2,
                    aiSpeech = "Galvanic skin response shows elevated stress. Pupillary dilation suggests active cognitive concealment. What was inside the exfiltrated archive?",
                    uzbekHint = "Yolg'on ayblovni inkor eting: Arxivda faqat test ma'lumotlari bo'lganini va tizim jurnallari halolligingizni isbotlashini ayting.",
                    targetKeywords = listOf("synthetic", "benchmark", "proprietary", "algorithms", "integrity", "terminal", "concealment"),
                    quickAnswerOptions = listOf(
                        QuickOption("The archive contained only synthetic benchmark datasets used for model stress testing.", "Arxiv ichida faqat modelni sinash uchun mo'ljallangan sun'iy ma'lumotlar to'plami bor edi."),
                        QuickOption("I never downloaded proprietary algorithms; the system logs will verify my integrity.", "Men hech qachon shaxsiy algoritmlarni yuklab olmaganman; tizim jurnallari mening halolligimni tasdiqlaydi."),
                        QuickOption("Someone used my terminal while I stepped away from the server room.", "Men server xonasidan chiqqan paytimda kimdir mening kompyuterimdan foydalangan.")
                    )
                ),
                ThrillerStep(
                    stage = 3,
                    aiSpeech = "Micro-tremor analysis indicates ninety-four percent deception. Confess who ordered the intelligence breach!",
                    uzbekHint = "Yakuniy xulosani qat'iy ayting: Haqiqiy tajovuzkor soxta VPN releni ishlatganini va kiber-xizmat bilan to'liq hamkorlikka tayyorligingizni bildiring.",
                    targetKeywords = listOf("cooperate", "financial", "framed", "sabotage", "signatures", "intruder", "spoofed", "firewall"),
                    quickAnswerOptions = listOf(
                        QuickOption("Check the hash signatures on the firewall. The true intruder used a spoofed VPN relay.", "Xavfsizlik devoridagi imzolarni tekshiring. Haqiqiy buzg'unchi soxta VPN releni ishlatgan."),
                        QuickOption("I was framed by an internal competitor trying to sabotage our flagship commercial launch.", "Kompaniyamizning asosiy loyihasini yo'qqa chiqarish uchun ichki raqobatchi menga tuhmat qildi."),
                        QuickOption("I am ready to cooperate fully with cybersecurity authorities to isolate the leak.", "Axborot sizib chiqishini aniqlash uchun kiberxavfsizlik xizmati bilan to'liq hamkorlik qilishga tayyorman.")
                    )
                )
            ),
            winSpeech = "NEURAL ANALYSIS CLEARED! Vocal biometric resonance confirms factual integrity at 98.7%. Zero cognitive deception detected!",
            winUzbek = "🌟 NEYRO-EKSBERTIZA NATIJASI: 98.7% Haqiqat va Halollik qayd etildi! Ovoz tembridagi xotirjamlik va mantiqiy dalillar ayblovni butunlay olib tashladi!"
        ),
        ThrillerQuest(
            id = "radio_quest",
            mode = SpeakingSubMode.RADIO,
            title = "Tungi Falsafiy Podkast",
            initialTimeSec = 80,
            characterName = "Sarah from Malibu",
            characterRole = "Late Night Radio Caller",
            avatarEmoji = "🎙️",
            backgroundThemeColor = Color(0xFFF59E0B),
            introSpeech = "Good evening host. Tonight on the wire: Is artificial intelligence expanding human creativity, or making our minds dependent on algorithms?",
            steps = listOf(
                ThrillerStep(
                    stage = 1,
                    aiSpeech = "Good evening host. Tonight on the wire: Is artificial intelligence expanding human creativity, or making our minds dependent on algorithms?",
                    uzbekHint = "Chuqur fikr bildiring: AI oddiy vazifalarni avtomatlashtirib, inson tasavvuriga keng ufqlar ochishini yoki aksincha xatarlarni tushuntiring.",
                    targetKeywords = listOf("human", "creativity", "imagination", "critical", "thinking", "technology", "ethical", "algorithms"),
                    quickAnswerOptions = listOf(
                        QuickOption("AI automates repetitive tasks, freeing human imagination to explore bold conceptual frontiers.", "AI bir xil zerikarli ishlarni bajaradi va inson tafakkuriga yangi g'oyalarni kashf etish uchun erkinlik beradi."),
                        QuickOption("Over-reliance on automated tools risks eroding our critical thinking and individual problem-solving grit.", "Avtomatlashtirilgan vositalarga haddan tashqari suyanish tanqidiy fikrlashimiz va mustaqil intilishimizni susaytirishi mumkin."),
                        QuickOption("Technology is simply a mirror of human intent; the ethical outcome depends entirely on how we wield it.", "Texnologiya inson niyatining ko'zgusidir; uning natijasi biz undan qanday foydalanishimizga bog'liq.")
                    )
                ),
                ThrillerStep(
                    stage = 2,
                    aiSpeech = "That is profound! For our teenage listeners preparing for their future careers: what mindset matters most in this changing world?",
                    uzbekHint = "16 yoshli o'quvchilarga kelajak bo'yicha maslahat bering: Moslashuvchanlik, chuqur e'tibor va to'xtovsiz o'rganish muhimligini ta'kidlang.",
                    targetKeywords = listOf("adaptability", "curiosity", "resilience", "focus", "skills", "future", "mindset", "learn"),
                    quickAnswerOptions = listOf(
                        QuickOption("Cultivate relentless curiosity and adaptability, because the ability to learn unlearn and relearn is your greatest asset.", "To'xtovsiz qiziquvchanlik va moslashuvchanlikni shakllantiring, chunki qayta o'rgana olish qobiliyati — eng buyuk boylikdir."),
                        QuickOption("Master deep focus in an era of constant distraction; craftsmanship will always remain irreplaceable.", "Chalg'ituvchi narsalar ko'p zamonda chuqur diqqatni jamlashni o'rganing; haqiqiy mahorat doimo bebaho bo'lib qoladi."),
                        QuickOption("Never measure your worth by algorithms or social metrics; build authentic value for real people.", "Qadringizni algoritmlar yoki ijtimoiy tarmoq raqamlari bilan o'lchamang; odamlar uchun haqiqiy foydali qiymat yarating.")
                    )
                ),
                ThrillerStep(
                    stage = 3,
                    aiSpeech = "Inspiring words host! Can you leave all our listeners worldwide with a final memorable closing message?",
                    uzbekHint = "Jonli efirni yorqin yakunlang: Butun dunyodagi yoshlarga jur'at, o'z yo'lida qat'iyat va xayrli tun tilang.",
                    targetKeywords = listOf("courage", "dreams", "journey", "world", "listeners", "inspired", "goodnight", "conviction"),
                    quickAnswerOptions = listOf(
                        QuickOption("To everyone listening under the stars: protect your ambition, walk your own path with conviction, and goodnight!", "Yulduzlar ostida tinglayotgan barcha insonlarga: orzularingizni asrang, o'z yo'lingizdan qat'iyat bilan boring va xayrli tun!"),
                        QuickOption("Great things take time and discipline. Stay true to your vision, keep building, and stay inspired!", "Buyuk narsalar vaqt va tartib-intizom talab qiladi. O'z maqsadingizga sodiq qoling, rivojlaning va ilhom bilan yashang!"),
                        QuickOption("Thank you for sharing your night with us. Tomorrow brings fresh possibilities; seize them with confidence!", "Tuningizni biz bilan o'tkazganingiz uchun rahmat. Ertangi kun yangi imkoniyatlar olib keladi; ularni ishonch bilan qo'lga kiriting!")
                    )
                )
            ),
            winSpeech = "MASTERFUL LIVE BROADCAST! Listeners across three continents were moved by your articulate philosophical reflections and vocal warmth!",
            winUzbek = "📻 YULDUZLI EFIR! Dunyoning turli nuqtalaridan tinglovchilar sizning chuqur falsafiy fikrlaringiz va yuksak inglizcha notiqligingizdan hayratda qoldi!"
        )
    )
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ThrillerArenaView(
    quest: ThrillerQuest,
    speechManager: SpeechManager,
    onSpeechInputRequired: ((String) -> Unit) -> Unit,
    onFinishQuest: () -> Unit
) {
    val theme = LocalLiquidTheme.current
    var currentStepIndex by remember(quest.id) { mutableIntStateOf(0) }
    var remainingSeconds by remember(quest.id) { mutableIntStateOf(quest.initialTimeSec) }
    var isGameOver by remember(quest.id) { mutableStateOf(false) }
    var isWon by remember(quest.id) { mutableStateOf(false) }
    var lastUserSpoken by remember(quest.id) { mutableStateOf<String?>(null) }
    var feedbackMessage by remember(quest.id) { mutableStateOf<String?>(null) }
    var speechSpeed by remember { mutableStateOf(0.85f) }
    var showManualInputDialog by remember { mutableStateOf(false) }
    var manualInputText by remember { mutableStateOf("") }

    // Triple-tap Word Intelligence & Essential Vault states
    var inspectedWord by remember { mutableStateOf<String?>(null) }
    var inspectedContext by remember { mutableStateOf("") }
    var showVaultDialog by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = remember { com.example.data.local.HabitPreferences(context) }

    // Interactive Wire Cut State for Bomb Mode
    var severedWires by remember(quest.id) { mutableStateOf(setOf<String>()) }

    val isListening by speechManager.isListening.collectAsState()
    val isSpeaking by speechManager.isSpeaking.collectAsState()
    val rmsLevel by speechManager.rmsLevel.collectAsState()
    val partialSpeech by speechManager.partialText.collectAsState()

    val currentStep = quest.steps.getOrNull(currentStepIndex)

    // Countdown Timer
    LaunchedEffect(quest.id, isGameOver, isWon) {
        while (remainingSeconds > 0 && !isGameOver && !isWon) {
            delay(1000L)
            remainingSeconds--
        }
        if (remainingSeconds <= 0 && !isWon) {
            isGameOver = true
            speechManager.speak("Time has expired! Mission failed. Try again!", speechSpeed)
        }
    }

    // Speak initial character line on step enter
    LaunchedEffect(quest.id, currentStepIndex) {
        if (!isGameOver && !isWon) {
            val phrase = currentStep?.aiSpeech ?: quest.winSpeech
            speechManager.speak(phrase, speechSpeed)
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "thrillerPulse"
    )

    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wavePhase"
    )

    fun handleSpokenPhrase(spoken: String) {
        lastUserSpoken = spoken
        val cleanSpoken = spoken.lowercase()
        val step = currentStep ?: return

        // Keyword checking
        val matches = step.targetKeywords.count { cleanSpoken.contains(it) }
        val isSuccess = matches >= 1 || spoken.split(" ").size >= 3

        if (isSuccess) {
            if (quest.mode == SpeakingSubMode.BOMB && cleanSpoken.contains("blue")) {
                severedWires = severedWires + "BLUE"
            }
            feedbackMessage = "✅ Ajoyib javob! Maqsadga erishildi (+50 XP)!"
            if (currentStepIndex + 1 < quest.steps.size) {
                currentStepIndex++
            } else {
                isWon = true
                speechManager.speak(quest.winSpeech, speechSpeed)
            }
        } else {
            feedbackMessage = "⚠️ Qisman to'g'ri, ammo aniqroq ayting: tavsiya etilgan jumlalardan foydalaning!"
        }
    }

    // Manual text input fallback dialog
    if (showManualInputDialog) {
        AlertDialog(
            onDismissRequest = { showManualInputDialog = false },
            title = {
                Text("Matn bilan javob berish", fontWeight = FontWeight.Bold, color = theme.textPrimary)
            },
            text = {
                Column {
                    Text(
                        "Mikrofonda gapirish noqulay bo'lsa, javobingizni yozib yuborishingiz mumkin:",
                        fontSize = 12.sp,
                        color = theme.textSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = manualInputText,
                        onValueChange = { manualInputText = it },
                        placeholder = { Text("Masalan: Cut the blue wire...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (manualInputText.isNotBlank()) {
                            val textToSend = manualInputText.trim()
                            manualInputText = ""
                            showManualInputDialog = false
                            handleSpokenPhrase(textToSend)
                        }
                    }
                ) {
                    Text("Yuborish", fontWeight = FontWeight.Bold, color = quest.backgroundThemeColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualInputDialog = false }) {
                    Text("Bekor qilish", color = theme.textSecondary)
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        quest.backgroundThemeColor.copy(alpha = 0.16f),
                        theme.bgBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // ==========================================
            // TOP HUD: Character Card + Digital Countdown + Gadget
            // ==========================================
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, quest.backgroundThemeColor.copy(alpha = 0.55f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Character info
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .background(quest.backgroundThemeColor.copy(alpha = 0.25f), CircleShape)
                                        .border(1.5.dp, quest.backgroundThemeColor, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(quest.avatarEmoji, fontSize = 22.sp)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            quest.characterName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = theme.textPrimary
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .background(quest.backgroundThemeColor.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                quest.mode.badge,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = quest.backgroundThemeColor
                                            )
                                        }
                                    }
                                    Text(
                                        quest.characterRole,
                                        fontSize = 11.sp,
                                        color = quest.backgroundThemeColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            // HUD Right Actions: Oltin Lug'at Quick Button + Digital Timer
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val essentialCount = remember(showVaultDialog, inspectedWord) { prefs.getSpeakingEssentialWords().size }
                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFF59E0B).copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                                        .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                        .clickable { showVaultDialog = true }
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("⭐", fontSize = 11.sp)
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            if (essentialCount > 0) "$essentialCount" else "Lug'at",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFF59E0B)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // Digital Countdown Timer Display
                                Box(
                                    modifier = Modifier
                                        .scale(if (remainingSeconds <= 15) pulseScale else 1f)
                                        .background(
                                            if (remainingSeconds <= 15) Color(0xFFEF4444).copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.5f),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .border(
                                            1.5.dp,
                                            if (remainingSeconds <= 15) Color(0xFFEF4444) else quest.backgroundThemeColor.copy(alpha = 0.5f),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            if (remainingSeconds <= 15) "⚠️" else "⏱️",
                                            fontSize = 11.sp
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        val minutes = remainingSeconds / 60
                                        val seconds = remainingSeconds % 60
                                        val formattedTime = String.format("%02d:%02d", minutes, seconds)
                                        Text(
                                            text = formattedTime,
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Black,
                                            color = if (remainingSeconds <= 15) Color(0xFFFF4D4D) else Color(0xFF10B981)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // ==========================================
                        // THEMED INTERACTIVE GADGET HUD
                        // ==========================================
                        when (quest.mode) {
                            SpeakingSubMode.BOMB -> {
                                BombDetonatorWidget(
                                    severedWires = severedWires,
                                    onCutWire = { wireColor ->
                                        severedWires = severedWires + wireColor
                                        handleSpokenPhrase("Cut the $wireColor wire")
                                    }
                                )
                            }
                            SpeakingSubMode.MAFIA -> {
                                MafiaTrustWidget(currentStep = currentStepIndex + 1, totalSteps = quest.steps.size)
                            }
                            SpeakingSubMode.INVESTOR -> {
                                InvestorValuationWidget(currentStep = currentStepIndex + 1)
                            }
                            SpeakingSubMode.LIE_DETECTOR -> {
                                LieDetectorPulseWidget(wavePhase = wavePhase, isListening = isListening)
                            }
                            SpeakingSubMode.RADIO -> {
                                RadioOnAirWidget(wavePhase = wavePhase, isSpeaking = isSpeaking)
                            }
                            else -> {}
                        }
                    }
                }

                // 3-Stage Progress Timeline
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    quest.steps.forEachIndexed { index, step ->
                        val isCurrent = index == currentStepIndex
                        val isDone = index < currentStepIndex || isWon
                        val stepColor = if (isDone) Color(0xFF10B981) else if (isCurrent) quest.backgroundThemeColor else theme.glassBorderSubtleColor

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        if (isDone || isCurrent) stepColor else theme.glassSurface,
                                        CircleShape
                                    )
                                    .border(1.dp, stepColor, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                } else {
                                    Text(
                                        "${index + 1}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCurrent) Color.White else theme.textSecondary
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Bosqich ${index + 1}",
                                fontSize = 10.sp,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = if (isCurrent) theme.textPrimary else theme.textSecondary
                            )
                            if (index < quest.steps.size - 1) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(2.dp)
                                        .background(if (index < currentStepIndex) Color(0xFF10B981) else theme.glassBorderSubtleColor)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                        }
                    }
                }
            }

            // ==========================================
            // CENTER: Dynamic Dialogue & Win/Loss Views
            // ==========================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isWon -> {
                        // VICTORY CELEBRATION CARD
                        Card(
                            shape = RoundedCornerShape(26.dp),
                            colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF10B981)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row {
                                    repeat(3) {
                                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(34.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("🏆 G'ALABA!", fontSize = 26.sp, fontWeight = FontWeight.Black, color = Color(0xFF10B981))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    quest.winSpeech,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.textPrimary,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    quest.winUzbek,
                                    fontSize = 12.sp,
                                    color = theme.textSecondary,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                // Performance Breakdown
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(theme.glassSurfaceElevated, RoundedCornerShape(14.dp))
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Nutq Tezligi", fontSize = 10.sp, color = theme.textSecondary)
                                        Text("A+ (98%)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Kalit So'zlar", fontSize = 10.sp, color = theme.textSecondary)
                                        Text("3 / 3 To'liq", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Mukofot", fontSize = 10.sp, color = theme.textSecondary)
                                        Text("+150 XP ⚡", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))
                                    }
                                }

                                Spacer(modifier = Modifier.height(18.dp))

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(46.dp)
                                            .background(theme.glassSurfaceElevated, RoundedCornerShape(14.dp))
                                            .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(14.dp))
                                            .clickable {
                                                remainingSeconds = quest.initialTimeSec
                                                currentStepIndex = 0
                                                isGameOver = false
                                                isWon = false
                                                severedWires = emptySet()
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Qayta O'ynash 🔄", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(46.dp)
                                            .background(
                                                Brush.horizontalGradient(listOf(Color(0xFF10B981), Color(0xFF059669))),
                                                RoundedCornerShape(14.dp)
                                            )
                                            .clickable { onFinishQuest() },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Keyingi Rejim ➡️", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                    isGameOver -> {
                        // GAME OVER & RESCUE LIFELINE CARD
                        Card(
                            shape = RoundedCornerShape(26.dp),
                            colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFEF4444)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("💥 VAQT TUGADI!", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFFEF4444))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Vaqt tugab qoldi, biroq to'xtash shart emas! O'z nutqingizni mustahkamlash uchun davom eting.",
                                    fontSize = 13.sp,
                                    color = theme.textPrimary,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                // Rescue +25 seconds lifeline
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .background(
                                            Brush.horizontalGradient(listOf(Color(0xFFF59E0B), Color(0xFFD97706))),
                                            RoundedCornerShape(14.dp)
                                        )
                                        .clickable {
                                            remainingSeconds = 30
                                            isGameOver = false
                                            speechManager.speak("Resuming mission! You have 30 seconds remaining.", speechSpeed)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("⚡ +30 Soniya Qo'shish (Davom etish)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .background(theme.glassSurfaceElevated, RoundedCornerShape(14.dp))
                                        .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(14.dp))
                                        .clickable {
                                            remainingSeconds = quest.initialTimeSec
                                            currentStepIndex = 0
                                            isGameOver = false
                                            isWon = false
                                            severedWires = emptySet()
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Boshidan Qayta Boshlash 🔄", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                                }
                            }
                        }
                    }
                    else -> {
                        currentStep?.let { step ->
                            // ACTIVE SCRIPT CARD
                            Card(
                                shape = RoundedCornerShape(22.dp),
                                colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, quest.backgroundThemeColor.copy(alpha = 0.45f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                LazyColumn(modifier = Modifier.padding(14.dp)) {
                                    item {
                                        // Header row with speech controls
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .background(if (isSpeaking) Color(0xFF10B981) else quest.backgroundThemeColor, CircleShape)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    if (isSpeaking) "AI Gapirmoqda..." else "Qahramon Nutqi:",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = quest.backgroundThemeColor
                                                )
                                            }

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                // Speed selector pill
                                                Box(
                                                    modifier = Modifier
                                                        .background(theme.glassSurfaceElevated, RoundedCornerShape(8.dp))
                                                        .clickable {
                                                            speechSpeed = when (speechSpeed) {
                                                                0.85f -> 1.0f
                                                                1.0f -> 0.75f
                                                                else -> 0.85f
                                                            }
                                                        }
                                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                                ) {
                                                    Text("${speechSpeed}x", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = theme.textPrimary)
                                                }

                                                Spacer(modifier = Modifier.width(6.dp))

                                                // Listen audio button
                                                Box(
                                                    modifier = Modifier
                                                        .size(30.dp)
                                                        .background(quest.backgroundThemeColor.copy(alpha = 0.2f), CircleShape)
                                                        .clickable { speechManager.speak(step.aiSpeech, speechSpeed) },
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        Icons.AutoMirrored.Filled.VolumeUp,
                                                        contentDescription = "Eshitish",
                                                        tint = quest.backgroundThemeColor,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        // Triple-tap guidance badge
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "💡 So'zni 3 marta bosing — tahlili va Oltin Lug'at ochiladi",
                                                fontSize = 10.sp,
                                                color = quest.backgroundThemeColor,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(3.dp))

                                        // AI Speech Text with 3-tap detection
                                        TripleTapInteractiveText(
                                            text = step.aiSpeech,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = theme.textPrimary,
                                            lineHeight = 22.sp,
                                            onWordTripleTapped = { word, sentence ->
                                                inspectedWord = word
                                                inspectedContext = sentence
                                            }
                                        )

                                        Spacer(modifier = Modifier.height(10.dp))

                                        // Mission Hint Card in Uzbek
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(theme.glassSurfaceElevated, RoundedCornerShape(12.dp))
                                                .border(1.dp, theme.glassBorderSubtleColor, RoundedCornerShape(12.dp))
                                                .padding(10.dp)
                                        ) {
                                            Column {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text("💡 Vazifa:", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = quest.backgroundThemeColor)
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text("O'zbekcha ko'rsatma", fontSize = 10.sp, color = theme.textSecondary)
                                                }
                                                Spacer(modifier = Modifier.height(3.dp))
                                                Text(step.uzbekHint, fontSize = 12.sp, color = theme.textPrimary, lineHeight = 17.sp)

                                                Spacer(modifier = Modifier.height(6.dp))
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text("Kalit so'zlar: ", fontSize = 10.sp, color = theme.textSecondary, fontWeight = FontWeight.Bold)
                                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                        items(step.targetKeywords) { kw ->
                                                            Box(
                                                                modifier = Modifier
                                                                    .background(quest.backgroundThemeColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                                                    .padding(horizontal = 5.dp, vertical = 2.dp)
                                                            ) {
                                                                Text(kw, fontSize = 10.sp, color = quest.backgroundThemeColor, fontWeight = FontWeight.Bold)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        // Spoken Feedback & Partial recognition
                                        if (isListening && partialSpeech.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                "🎙️ Eshitilmoqda: \"$partialSpeech\"",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF38BDF8)
                                            )
                                        } else if (lastUserSpoken != null) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                "Siz aytdingiz: \"$lastUserSpoken\"",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = theme.primaryAccent
                                            )
                                        }

                                        if (feedbackMessage != null) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                feedbackMessage ?: "",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (feedbackMessage?.startsWith("✅") == true) Color(0xFF10B981) else Color(0xFFF59E0B)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ==========================================
            // BOTTOM: Quick Answer Cards + Massive Voice Mic Button
            // ==========================================
            if (!isGameOver && !isWon && currentStep != null) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "⚡ Tayyor Javob Variantlari (O'qib ko'ring yoki bosing):",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.textSecondary
                        )

                        // Keyboard fallback button
                        Row(
                            modifier = Modifier
                                .clickable { showManualInputDialog = true }
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = theme.textSecondary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Yozish", fontSize = 10.sp, color = theme.textSecondary, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Dynamic Adaptive Quick Answer Options (Gap uzunligiga qarab avtomatik moslashuvchan, to'liq sig'adigan vertikal panel)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        currentStep.quickAnswerOptions.forEachIndexed { optIndex, option ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = theme.glassSurface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, quest.backgroundThemeColor.copy(alpha = 0.4f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { handleSpokenPhrase(option.english) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    // Numbered badge: 1, 2, 3
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(quest.backgroundThemeColor.copy(alpha = 0.2f), CircleShape)
                                            .border(1.dp, quest.backgroundThemeColor, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "${optIndex + 1}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = quest.backgroundThemeColor
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        TripleTapInteractiveText(
                                            text = option.english,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = theme.textPrimary,
                                            lineHeight = 18.sp,
                                            onWordTripleTapped = { word, sentence ->
                                                inspectedWord = word
                                                inspectedContext = sentence
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = option.uzbek,
                                            fontSize = 11.sp,
                                            color = theme.textSecondary,
                                            lineHeight = 15.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // Audio listen button
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(quest.backgroundThemeColor.copy(alpha = 0.15f), CircleShape)
                                            .clickable {
                                                speechManager.speak(option.english, speechSpeed)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Talaffuzni eshitish",
                                            tint = quest.backgroundThemeColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Primary Voice Mic Action Button with Live Audio Wave Pulse
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        quest.backgroundThemeColor,
                                        quest.backgroundThemeColor.copy(alpha = 0.82f)
                                    )
                                )
                            )
                            .border(
                                2.dp,
                                if (isListening) Color.White else quest.backgroundThemeColor.copy(alpha = 0.5f),
                                RoundedCornerShape(18.dp)
                            )
                            .clickable {
                                onSpeechInputRequired { spoken ->
                                    handleSpokenPhrase(spoken)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .scale(if (isListening) pulseScale else 1f)
                                    .background(Color.White.copy(alpha = 0.25f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Mic,
                                    contentDescription = "Mikrofon",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                if (isListening) "🎙️ Eshitmoqda... Gapiring!" else "🎙️ OVOZ BILAN GAPIRISH",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }

        // ==============================================================
        // INTELLECTUAL WORD INSPECTOR BOTTOM SHEET & ESSENTIAL VAULT
        // ==============================================================
        if (inspectedWord != null) {
            SpeakingWordIntelligenceBottomSheet(
                targetWord = inspectedWord!!,
                sentenceContext = inspectedContext,
                sourceMode = quest.title,
                speechManager = speechManager,
                onDismiss = { inspectedWord = null },
                onOpenVaultSection = {
                    inspectedWord = null
                    showVaultDialog = true
                }
            )
        }

        if (showVaultDialog) {
            SpeakingEssentialVaultDialog(
                speechManager = speechManager,
                onDismiss = { showVaultDialog = false }
            )
        }
    }
}

// ====================================================================
// THEMED INTERACTIVE GADGETS FOR MAXIMUM IMMERSION AND FUN
// ====================================================================

@Composable
fun BombDetonatorWidget(
    severedWires: Set<String>,
    onCutWire: (String) -> Unit
) {
    val theme = LocalLiquidTheme.current
    val wires = listOf(
        Pair("RED", Color(0xFFEF4444)),
        Pair("BLUE", Color(0xFF3B82F6)),
        Pair("YELLOW", Color(0xFFEAB308)),
        Pair("GREEN", Color(0xFF10B981))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFFEF4444).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("✂️ ZARARSIZLANTIRISH PANELİ (Simni kesish uchun bosing yoki ovozda ayting):", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
            Text("STATUS: XAVF", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFEF4444))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            wires.forEach { (name, color) ->
                val isCut = severedWires.contains(name)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .height(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isCut) Color.DarkGray.copy(alpha = 0.3f) else color.copy(alpha = 0.2f))
                        .border(
                            1.5.dp,
                            if (isCut) Color.Gray else color,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onCutWire(name.lowercase()) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            if (isCut) "⚡ KESILDI" else name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isCut) Color.Gray else color
                        )
                        if (!isCut) {
                            Text("✂️ Kesish", fontSize = 8.sp, color = theme.textSecondary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MafiaTrustWidget(currentStep: Int, totalSteps: Int) {
    val theme = LocalLiquidTheme.current
    val progress = (currentStep.toFloat() / totalSteps.toFloat()).coerceIn(0.2f, 1f)
    val trustLabel = when (currentStep) {
        1 -> "Dushmanona (Shubha: 85%)"
        2 -> "Muzokara Boshlandi (Ishonch: 60%)"
        else -> "Kelishuvga Erishildi (100%)"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFF8B5CF6).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("👑 DON FALCONE ISHONCH BAROMETRI:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8B5CF6))
            Text(trustLabel, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC084FC))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.DarkGray, RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFF8B5CF6), Color(0xFF10B981))),
                        RoundedCornerShape(4.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("👥 Garovdagilar: 3 nafar (Xavfsiz)", fontSize = 9.sp, color = theme.textSecondary)
            Text("Diplomatiya: Yuqori", fontSize = 9.sp, color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun InvestorValuationWidget(currentStep: Int) {
    val theme = LocalLiquidTheme.current
    val valuation = when (currentStep) {
        1 -> "$250,000"
        2 -> "$600,000"
        else -> "$1,000,000"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFF10B981).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📈 SHARK TANK VALUATION:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                Spacer(modifier = Modifier.width(6.dp))
                Text(valuation, fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF34D399))
            }
            Text("TAKLIF: 10% ULUSH", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFBBF24))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Investor diqqati: Qat'iy javoblar ulush narxini oshirib boradi!",
            fontSize = 9.sp,
            color = theme.textSecondary
        )
    }
}

@Composable
fun LieDetectorPulseWidget(wavePhase: Float, isListening: Boolean) {
    val theme = LocalLiquidTheme.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFF06B6D4).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🧠 NEYRO POLIGRAF TELEMETRIYASI:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF06B6D4))
            Text("PULSE: 82 BPM (BARQAROR)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF38BDF8))
        }
        Spacer(modifier = Modifier.height(6.dp))
        // Canvas EKG wave
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
        ) {
            val width = size.width
            val height = size.height
            val midY = height / 2f

            val path = Path()
            path.moveTo(0f, midY)

            val stepX = 10f
            var x = 0f
            while (x <= width) {
                val normalizedX = x / width
                val yOffset = sin(normalizedX * 12f + wavePhase) * (if (isListening) 12f else 6f)
                path.lineTo(x, midY + yOffset)
                x += stepX
            }

            drawPath(
                path = path,
                color = Color(0xFF06B6D4),
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Ovoz titrashi: 1.2% (Normada)", fontSize = 9.sp, color = theme.textSecondary)
            Text("Haqiqat darajasi: 98.4%", fontSize = 9.sp, color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RadioOnAirWidget(wavePhase: Float, isSpeaking: Boolean) {
    val theme = LocalLiquidTheme.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(10.dp)
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
                        .background(Color(0xFFEF4444), CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("🔴 ON AIR: LOS ANGELES 104.2 FM", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color(0xFFF59E0B))
            }
            Text("QO'NG'IROQ: SARA (MALIBU)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFDE68A))
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Equalizer VU-meter bars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(16) { i ->
                val barHeight = (8 + (sin(i.toDouble() * 0.8 + wavePhase) * 8).toInt()).coerceIn(4, 20)
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(barHeight.dp)
                        .background(
                            if (isSpeaking) Color(0xFFF59E0B) else Color(0xFFF59E0B).copy(alpha = 0.35f),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}
