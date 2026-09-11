package com.example.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class WeatherInfo(
    val locationName: String = "Farg'ona / Toshkent",
    val temperature: String = "+22°C",
    val condition: String = "Ochiq havo",
    val icon: String = "☀️",
    val advice: String = "Bugun ochiq havo: ertalabki yugurish va mashg'ulotlar uchun juda qulay.",
    val humidity: String = "45%"
)

object WeatherClient {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    suspend fun fetchWeather(lat: Double = 41.311081, lng: Double = 69.240562, locationName: String = "Toshkent"): Result<WeatherInfo> = withContext(Dispatchers.IO) {
        try {
            val url = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lng&current=temperature_2m,relative_humidity_2m,precipitation,weather_code&timezone=auto"
            val request = Request.Builder().url(url).build()

            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext Result.success(getFallbackWeather(locationName))
                }
                val bodyStr = response.body?.string() ?: ""
                val root = JSONObject(bodyStr)
                val current = root.optJSONObject("current") ?: return@withContext Result.success(getFallbackWeather(locationName))

                val temp = current.optDouble("temperature_2m", 22.0)
                val humidity = current.optInt("relative_humidity_2m", 45)
                val code = current.optInt("weather_code", 0)

                val (condition, icon, advice) = parseWeatherCode(code, temp)

                val tempStr = if (temp > 0) "+${temp.toInt()}°C" else "${temp.toInt()}°C"

                Result.success(
                    WeatherInfo(
                        locationName = locationName,
                        temperature = tempStr,
                        condition = condition,
                        icon = icon,
                        advice = advice,
                        humidity = "$humidity%"
                    )
                )
            }
        } catch (e: Exception) {
            Result.success(getFallbackWeather(locationName))
        }
    }

    private fun parseWeatherCode(code: Int, temp: Double): Triple<String, String, String> {
        return when (code) {
            0 -> Triple(
                "Mussaffo osmon",
                "☀️",
                if (temp > 28) "Havo issiq, ko'proq suv ichishni unutmang." else "Ajoyib ochiq havo! Ertalabki sport uchun a'lo fursat."
            )
            1, 2, 3 -> Triple(
                "Biroz bulutli",
                "⛅",
                "Yaxshi ob-havo, mashg'ulotlarni reja asosida davom ettiring."
            )
            45, 48 -> Triple(
                "Tumanli",
                "🌫️",
                "Yo'llarda ehtiyot bo'ling, ko'rish masofasi cheklangan."
            )
            51, 53, 55, 61, 63, 65, 80, 81, 82 -> Triple(
                "Yomg'ir yog'moqda",
                "🌧️",
                "Bugun yomg'ir: ertalabki yugurishni uyda yoki zalda bajaring."
            )
            71, 73, 75, 77, 85, 86 -> Triple(
                "Qor yog'moqda",
                "❄️",
                "Issiq kiyining va mashg'ulotlarni uy sharoitida o'tkazing."
            )
            95, 96, 99 -> Triple(
                "Momaqaldiroq",
                "⛈️",
                "Chaqmoq va yomg'ir: ochiq joylarda uzoq qolmaslik tavsiya etiladi."
            )
            else -> Triple(
                "Mo''tadil havo",
                "🌤️",
                "Bugungi kun tartibini qat'iyat bilan bajaring!"
            )
        }
    }

    private fun getFallbackWeather(locationName: String) = WeatherInfo(
        locationName = locationName,
        temperature = "+24°C",
        condition = "Quyoshli",
        icon = "☀️",
        advice = "Ob-havo mo''tadil: barcha rejalashtirilgan vazifalarni o'z vaqtida bajaring.",
        humidity = "42%"
    )
}
