package com.example.data.model

import java.util.UUID

/**
 * 🎓 Morning School & Aileaders.uz Coursera ("Besh Million Sun'iy Intellekt Yetakchilari")
 * Dynamic Schedule Engine.
 *
 * Moslashtirilgan Kuz/Qish namoz vaqtlari:
 * 1. Bomdod: 05:00
 * 2. Peshin: 12:25
 * 3. Asr: 16:15
 * 4. Shom: 17:59
 * 5. Xufton: 19:13
 *
 * Barcha vazifalar mutlaqo to'qnashuvsiz, bir-biriga uzluksiz ulanadi:
 * - 40-50 ta Coursera Sertifikati (har biri 6-7 daqiqa, 5000 so'mdan = 200,000 - 250,000 so'm/kun)
 * - Offline Ingliz tili darslari
 * - Sog'lom uyqu: 22:45 da yotish, 05:00 da tetik uyg'onish
 */
object MorningSchoolScheduleEngine {

    const val CERT_PRICE_UZS = 5000
    const val AVG_MINUTES_PER_CERT = 7
    const val DEFAULT_DAILY_TARGET = 50

    /**
     * 🏫 Maktabga boriladigan kun tartibi (School Day Plan)
     * - Bomdod: 05:00 - 05:40
     * - Peshin: 12:25 - 13:00
     * - Asr: 16:15 - 16:45
     * - Shom: 17:59 - 18:30
     * - Xufton: 19:13 - 19:45
     */
    fun getSchoolDayPlan(): List<ScheduleItem> {
        return listOf(
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🌅 Bomdod namozi (05:00), Tahorat & Zikrlar",
                category = "prayer",
                start = "05:00",
                end = "05:40",
                priority = "yuqori",
                note = "Bomdod namozi kunning barakasi va poydevoridir. Hech qachon qazo qilma!",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🏃 Badantarbiya, Mashq & Nonushta",
                category = "sport",
                start = "05:40",
                end = "06:10",
                priority = "orta",
                note = "Miyani uyg'otish va kunga tetik energiya olish uchun jismoniy mashq",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "⚡ Coursera Tonggi Sprint 1 (Aileaders.uz: 10-12 ta sertifikat)",
                category = "rtm",
                start = "06:10",
                end = "07:30",
                priority = "yuqori",
                note = "Ertalab miya tiniq paytda 10-12 ta sertifikat olish (~55,000 so'm daromad).",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🎒 Maktabga tayyorgarlik va yo'l",
                category = "school",
                start = "07:30",
                end = "08:00",
                priority = "orta",
                note = "Daftar va kitoblarni tekshirib, maktabga yo'l olish.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🏫 Maktab darslari (Tanaffusda o'quvchilar ro'yxatini to'plash)",
                category = "school",
                start = "08:00",
                end = "12:25",
                priority = "yuqori",
                note = "Maktabda darslarda qatnashish va tanaffuslarda yangi o'quvchilar login ma'lumotlarini to'plab borish.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Peshin namozi (12:25) & Duo",
                category = "prayer",
                start = "12:25",
                end = "13:00",
                priority = "yuqori",
                note = "Peshin namozini kechiktirmasdan, jamoat/vaqtida ado etish.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🍽️ Issiq Tushlik & Qisqa tanaffus",
                category = "food",
                start = "13:00",
                end = "13:40",
                priority = "orta",
                note = "To'yimli issiq tushlik va dam olish.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "😴 Qaylula (Power Nap) — 35 daqiqa ko'z yumish",
                category = "other",
                start = "13:40",
                end = "14:15",
                priority = "orta",
                note = "Miyadagi charchoqni chiqarish va 2-sprint uchun quvvat to'plash.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🚀 Coursera Katta Sprint 2 (Aileaders.uz: 18-20 ta sertifikat)",
                category = "rtm",
                start = "14:15",
                end = "16:15",
                priority = "yuqori",
                note = "Har 6-7 daqiqada 1 ta sertifikat. 2 soat to'xtovsiz sprint: ~18-20 ta sertifikat (100,000 so'm).",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Asr namozi (16:15) & Offline darsga yo'l",
                category = "prayer",
                start = "16:15",
                end = "16:45",
                priority = "yuqori",
                note = "Asr namozini o'z vaqtida ado etish va offline ingliz tili markaziga yo'l olish.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🇬🇧 Offline Ingliz tili darsi (Speaking, Grammar & Practice)",
                category = "english",
                start = "16:45",
                end = "17:59",
                priority = "yuqori",
                note = "Darsda to'liq ishtirok etish. Ingliz tili kelajakdagi xalqaro imkoniyatlar kaliti!",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Shom namozi (17:59) & Tanaffus",
                category = "prayer",
                start = "17:59",
                end = "18:30",
                priority = "yuqori",
                note = "Shom namozini o'z vaqtida o'qish va qisqa tanaffus.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🇬🇧 Offline Ingliz tili (Vazifalar, Savol-javob) & Uyga qaytish",
                category = "english",
                start = "18:30",
                end = "19:13",
                priority = "yuqori",
                note = "Darsdagi topshiriqlarni yakunlash, o'qituvchiga savollar va uyga yo'l.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Xufton namozi (19:13) & Duo",
                category = "prayer",
                start = "19:13",
                end = "19:45",
                priority = "yuqori",
                note = "Xufton namozini o'qish va kunlik ibodatlarni yakunlash.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🍽️ Kechki ovqat & Oila davrasida hordiq",
                category = "food",
                start = "19:45",
                end = "20:25",
                priority = "orta",
                note = "Oila bilan birga kechki taomlanish va hordiq.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🔥 Coursera Yakuniy Sprint 3 (Aileaders.uz: 15-18 ta sertifikat)",
                category = "rtm",
                start = "20:25",
                end = "22:15",
                priority = "yuqori",
                note = "Kunlik 45-50 ta sertifikat marrasiga yetish (Jami: 225,000 - 250,000 so'm sof daromad!).",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "📖 Kitob mutolaasi, Kunlik hisobot & Tinchlanish",
                category = "other",
                start = "22:15",
                end = "22:45",
                priority = "orta",
                note = "Telefon va kompyuterdan uzoqlashish, kunlik daromad hisobini kiritish.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🌙 Sog'lom va Barokatli Uyqu (05:00 Bomdodga tetik turish)",
                category = "night",
                start = "22:45",
                end = "05:00",
                priority = "yuqori",
                note = "Ertaga soat 05:00 da tetik uyg'onish uchun vaqtida uxlash shart!",
                blocking = false
            )
        )
    }

    /**
     * 🏠 Uyda qolinadigan kun tartibi (Turbo Coursera Day)
     * - Bomdod: 05:00 - 05:40
     * - Peshin: 12:25 - 13:00
     * - Asr: 16:15 - 16:45
     * - Shom: 17:59 - 18:30
     * - Xufton: 19:13 - 19:45
     * Coursera maqsad: 55-60 ta sertifikat (Kunlik 275,000 - 300,000 so'm)
     */
    fun getNoSchoolDayPlan(): List<ScheduleItem> {
        return listOf(
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🌅 Bomdod namozi (05:00), Tahorat & Zikrlar",
                category = "prayer",
                start = "05:00",
                end = "05:40",
                priority = "yuqori",
                note = "Bomdod namozi va tonggi zikrlar — kunning barakasi.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🏃 Jismoniy mashq, Cho'zilish & Kontrast dush",
                category = "sport",
                start = "05:40",
                end = "06:10",
                priority = "orta",
                note = "Kun bo'yi kompyuterda yuqori tempda ishlash uchun tanani baquvvat qilish.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "⚡ Turbo Coursera Super Sprint 1 (Aileaders.uz: 25-28 ta sertifikat)",
                category = "rtm",
                start = "06:10",
                end = "09:15",
                priority = "yuqori",
                note = "Ertalabki sukunatda 3 soat 5 daqiqa to'xtovsiz sprint: ~26 ta sertifikat (130,000 so'm).",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🍳 To'yimli Nonushta va Ko'z mashqlari",
                category = "food",
                start = "09:15",
                end = "10:00",
                priority = "orta",
                note = "Miya va ko'zlarga tanaffus berish, toza havoda nafas olish.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🚀 Turbo Coursera Sprint 2 (Aileaders.uz: 18-20 ta sertifikat)",
                category = "rtm",
                start = "10:00",
                end = "12:25",
                priority = "yuqori",
                note = "2 soat 25 daqiqada yana 18-20 ta sertifikat olish. Jami sertifikatlar 45 tadan oshadi!",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Peshin namozi (12:25) & Duo",
                category = "prayer",
                start = "12:25",
                end = "13:00",
                priority = "yuqori",
                note = "Peshin namozini masjidda yoki o'z vaqtida o'qish.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🍽️ Issiq Tushlik & Qisqa tanaffus",
                category = "food",
                start = "13:00",
                end = "13:45",
                priority = "orta",
                note = "To'yimli issiq tushlik.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "😴 Qaylula (Power Nap) — Kuchlarni tiklash",
                category = "other",
                start = "13:45",
                end = "14:30",
                priority = "orta",
                note = "45 daqiqa uxlab olish, kunduzgi charchoqni to'liq yo'qotadi.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🇬🇧 Ingliz tili Uyga Vazifalar & Speaking tayyorgarlik",
                category = "english",
                start = "14:30",
                end = "16:15",
                priority = "yuqori",
                note = "Offline darsga to'liq tayyorgarlik, speaking va grammatika mashqlari (1 soat 45 daq).",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Asr namozi (16:15) & Offline darsga yo'l",
                category = "prayer",
                start = "16:15",
                end = "16:45",
                priority = "yuqori",
                note = "Asr namozini ado etish va offline darsga yo'l olish.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🇬🇧 Offline Ingliz tili darsi (1-qism: Faol qatnashish)",
                category = "english",
                start = "16:45",
                end = "17:59",
                priority = "yuqori",
                note = "Offline darsda yuzma-yuz speaking amaliyoti — bu dars o'tkazib yuborilmaydi!",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Shom namozi (17:59) & Tanaffus",
                category = "prayer",
                start = "17:59",
                end = "18:30",
                priority = "yuqori",
                note = "Shom namozini o'z vaqtida o'qish va qisqa tanaffus.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🇬🇧 Offline Ingliz tili (2-qism: Mashqlar) & Uyga qaytish",
                category = "english",
                start = "18:30",
                end = "19:13",
                priority = "yuqori",
                note = "Dars yakunlari, tushunilmagan mavzular tahlili va uyga yo'l.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🕌 Xufton namozi (19:13) & Duo",
                category = "prayer",
                start = "19:13",
                end = "19:45",
                priority = "yuqori",
                note = "Xufton namozi va zikrlar.",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🍽️ Kechki ovqat & Oila davrasi",
                category = "food",
                start = "19:45",
                end = "20:25",
                priority = "orta",
                note = "Oila davrasida kechki taom.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🔥 Coursera Yakuniy Sprint 3 (Aileaders.uz: 12-15 ta sertifikat)",
                category = "rtm",
                start = "20:25",
                end = "22:15",
                priority = "yuqori",
                note = "Bugungi natijani 55-60 ta sertifikatga yetkazish (Jami: 275,000 - 300,000 so'm!).",
                blocking = true
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "📖 Kitob o'qish, Kunlik hisob-kitob va Tinchlanish",
                category = "other",
                start = "22:15",
                end = "22:45",
                priority = "orta",
                note = "Ekranlarni o'chirish, tinchlanish va ertangi kun rejasi.",
                blocking = false
            ),
            ScheduleItem(
                id = UUID.randomUUID().toString(),
                title = "🌙 Sog'lom va Barokatli Uyqu (05:00 da tetik uyg'onish)",
                category = "night",
                start = "22:45",
                end = "05:00",
                priority = "yuqori",
                note = "05:00 da tetik uyg'onish uchun uyqu gigiyenasi.",
                blocking = false
            )
        )
    }

    fun calculateEstimatedEarnings(certCount: Int): Int {
        return certCount * CERT_PRICE_UZS
    }

    fun calculateEstimatedMinutes(certCount: Int): Int {
        return certCount * AVG_MINUTES_PER_CERT
    }
}
