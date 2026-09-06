package com.example.data.remote

import android.util.Log
import com.example.data.model.HabitState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object FirestoreClient {
    private const val TAG = "FirestoreClient"
    private const val PROJECT_ID = "habit-89977"
    private const val API_KEY = "AIzaSyBQApuXqvLuUC4r8OunlYBlikei6nWuWOU"
    private const val BASE_URL = "https://firestore.googleapis.com/v1/projects/$PROJECT_ID/databases/(default)/documents/habit/state"

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    suspend fun getState(): Result<HabitState> = withContext(Dispatchers.IO) {
        try {
            val url = "$BASE_URL?key=$API_KEY"
            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string()
                if (!response.isSuccessful || body == null) {
                    val errMsg = "HTTP ${response.code}: $body"
                    Log.e(TAG, "getState xatolik: $errMsg")
                    return@withContext Result.failure(Exception(errMsg))
                }

                val json = JSONObject(body)
                val fields = json.optJSONObject("fields") ?: JSONObject()

                val state = HabitState(
                    title = fields.optJSONObject("title")?.optString("stringValue") ?: "",
                    category = fields.optJSONObject("category")?.optString("stringValue") ?: "",
                    start = fields.optJSONObject("start")?.optString("stringValue") ?: "",
                    end = fields.optJSONObject("end")?.optString("stringValue") ?: "",
                    note = fields.optJSONObject("note")?.optString("stringValue") ?: "",
                    blocking = fields.optJSONObject("blocking")?.optBoolean("booleanValue") ?: false,
                    lastArrivalPlace = fields.optJSONObject("lastArrivalPlace")?.optString("stringValue") ?: "",
                    lastArrival = fields.optJSONObject("lastArrival")?.optString("stringValue") ?: "",
                    lastAnswer = fields.optJSONObject("lastAnswer")?.optString("stringValue") ?: "",
                    lastAnsweredTitle = fields.optJSONObject("lastAnsweredTitle")?.optString("stringValue") ?: "",
                    lastAnswerTime = fields.optJSONObject("lastAnswerTime")?.optString("stringValue") ?: "",
                    lastUpdatedEpochMs = System.currentTimeMillis()
                )
                Log.d(TAG, "Holat o'qildi: title=${state.title}, blocking=${state.blocking}, end=${state.end}")
                Result.success(state)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Firestore getState istisno: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun patchArrival(place: String, isoTimestamp: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val url = "$BASE_URL?updateMask.fieldPaths=lastArrivalPlace&updateMask.fieldPaths=lastArrival&key=$API_KEY"
            
            val fieldsObj = JSONObject().apply {
                put("lastArrivalPlace", JSONObject().put("stringValue", place))
                put("lastArrival", JSONObject().put("stringValue", isoTimestamp))
            }
            val payload = JSONObject().apply {
                put("fields", fieldsObj)
            }

            val requestBody = payload.toString().toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url(url)
                .patch(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                if (response.isSuccessful) {
                    Log.d(TAG, "Kelish muvaffaqiyatli saqlandi: $place ($isoTimestamp)")
                    Result.success(true)
                } else {
                    Log.e(TAG, "patchArrival xatolik: HTTP ${response.code} $responseBody")
                    Result.failure(Exception("HTTP ${response.code}: $responseBody"))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "patchArrival istisno: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun patchAlarmAnswer(answer: String, taskTitle: String, isoTimestamp: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val url = "$BASE_URL?updateMask.fieldPaths=lastAnswer&updateMask.fieldPaths=lastAnsweredTitle&updateMask.fieldPaths=lastAnswerTime&key=$API_KEY"
            
            val fieldsObj = JSONObject().apply {
                put("lastAnswer", JSONObject().put("stringValue", answer))
                put("lastAnsweredTitle", JSONObject().put("stringValue", taskTitle))
                put("lastAnswerTime", JSONObject().put("stringValue", isoTimestamp))
            }
            val payload = JSONObject().apply {
                put("fields", fieldsObj)
            }

            val requestBody = payload.toString().toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url(url)
                .patch(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                if (response.isSuccessful) {
                    Log.d(TAG, "Alarm javobi Firestore'ga yozildi: $answer ($taskTitle)")
                    Result.success(true)
                } else {
                    Log.e(TAG, "patchAlarmAnswer xatolik: HTTP ${response.code} $responseBody")
                    Result.failure(Exception("HTTP ${response.code}: $responseBody"))
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "patchAlarmAnswer istisno: ${e.message}", e)
            Result.failure(e)
        }
    }
}
