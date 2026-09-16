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

    private fun cleanToken(raw: String): String =
        raw.trim().removePrefix("bot").trim()

    private fun cleanChatId(raw: String): String =
        raw.trim()

    suspend fun sendReport(
        botToken: String,
        chatId: String,
        messageHtml: String
    ): Result<String> = withContext(Dispatchers.IO) {
        val token = cleanToken(botToken)
        val chat = cleanChatId(chatId)

        if (token.isBlank() || chat.isBlank()) {
            return@withContext Result.failure(Exception("Telegram Bot Token yoki Chat ID kiritilmagan! Sozlamalardan to'ldiring."))
        }

        // Try sending with HTML parse_mode first
        val htmlResult = executeSendMessage(token, chat, messageHtml, parseMode = "HTML")
        if (htmlResult.isSuccess) {
            return@withContext htmlResult
        }

        // Fallback: If HTML entity parsing fails, send as plain text with HTML tags stripped
        val plainText = messageHtml
            .replace("<b>", "")
            .replace("</b>", "")
            .replace("<i>", "")
            .replace("</i>", "")
            .replace("<code>", "")
            .replace("</code>", "")
        return@withContext executeSendMessage(token, chat, plainText, parseMode = null)
    }

    suspend fun testConnection(
        botToken: String,
        chatId: String
    ): Result<String> = withContext(Dispatchers.IO) {
        val token = cleanToken(botToken)
        val chat = cleanChatId(chatId)

        if (token.isBlank() || chat.isBlank()) {
            return@withContext Result.failure(Exception("Iltimos, Bot Token va Chat ID ni kiriting!"))
        }

        val testMessage = "🚀 <b>Habit ilovasi bilan aloqa muvaffaqiyatli!</b>\n\nTelegram bot sozlangan va ishlashga tayyor. Bajarilgan vazifalar va hisobotlar shu yerga yuboriladi."
        val res = sendReport(token, chat, testMessage)
        if (res.isSuccess) {
            Result.success("✅ Telegram bot bilan aloqa muvaffaqiyatli o'rnatildi!")
        } else {
            res
        }
    }

    private fun executeSendMessage(
        token: String,
        chat: String,
        text: String,
        parseMode: String?
    ): Result<String> {
        return try {
            val url = "https://api.telegram.org/bot$token/sendMessage"
            val formBuilder = FormBody.Builder()
                .add("chat_id", chat)
                .add("text", text)

            if (parseMode != null) {
                formBuilder.add("parse_mode", parseMode)
            }

            val request = Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .build()

            httpClient.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Result.success("Xabar Telegramga muvaffaqiyatli yuborildi!")
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
