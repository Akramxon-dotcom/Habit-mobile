package com.example.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object TelegramClient {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    suspend fun sendReport(
        botToken: String,
        chatId: String,
        messageHtml: String
    ): Result<String> = withContext(Dispatchers.IO) {
        val token = botToken.trim()
        val chat = chatId.trim()

        if (token.isBlank() || chat.isBlank()) {
            return@withContext Result.failure(Exception("Telegram Bot Token yoki Chat ID kiritilmagan! Sozlamalardan to'ldiring."))
        }

        try {
            val url = "https://api.telegram.org/bot$token/sendMessage"
            val formBody = FormBody.Builder()
                .add("chat_id", chat)
                .add("text", messageHtml)
                .add("parse_mode", "HTML")
                .build()

            val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            httpClient.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Result.success("Hisobot Telegramga muvaffaqiyatli yuborildi!")
                } else {
                    val obj = try { JSONObject(bodyStr) } catch (e: Exception) { null }
                    val desc = obj?.optString("description", "Noma'lum xatolik") ?: "Xatolik: ${response.code}"
                    Result.failure(Exception("Telegram: $desc"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
