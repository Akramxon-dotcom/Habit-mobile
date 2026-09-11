package com.example.data.remote

import android.util.Log
import com.example.data.model.ScheduleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate

object SupabaseClient {
    private const val TAG = "SupabaseClient"
    const val SUPABASE_URL = "https://swpalasnevjudvvvmeio.supabase.co"
    const val SUPABASE_ANON_KEY = "sb_publishable_RDvR0imNuYYxod7NeXAQIQ_laCNL8Eq"

    suspend fun fetchTasks(): List<ScheduleItem> = withContext(Dispatchers.IO) {
        try {
            val endpoint = "$SUPABASE_URL/rest/v1/tasks?select=*&order=sort_order.asc"
            val conn = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("apikey", SUPABASE_ANON_KEY)
                setRequestProperty("Authorization", "Bearer $SUPABASE_ANON_KEY")
                setRequestProperty("Content-Type", "application/json")
                connectTimeout = 8000
                readTimeout = 8000
            }

            if (conn.responseCode in 200..299) {
                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                val response = reader.readText()
                reader.close()
                val jsonArr = JSONArray(response)
                val list = mutableListOf<ScheduleItem>()
                for (i in 0 until jsonArr.length()) {
                    val obj = jsonArr.getJSONObject(i)
                    val startTime = obj.optString("start_time", "08:00")
                    val endTime = obj.optString("end_time", "09:00")
                    list.add(
                        ScheduleItem(
                            id = obj.optString("id", "task_$i"),
                            title = obj.optString("name", "Vazifa"),
                            start = startTime,
                            end = endTime,
                            category = obj.optString("category", "other"),
                            priority = obj.optString("priority", "orta"),
                            note = obj.optString("note", "")
                        )
                    )
                }
                list
            } else {
                Log.w(TAG, "fetchTasks failed: HTTP ${conn.responseCode}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching tasks from Supabase: ${e.message}")
            emptyList()
        }
    }

    suspend fun saveTaskCompletion(taskId: String, done: Boolean, date: String = LocalDate.now().toString()): Boolean = withContext(Dispatchers.IO) {
        try {
            val endpoint = "$SUPABASE_URL/rest/v1/task_completions"
            val conn = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("apikey", SUPABASE_ANON_KEY)
                setRequestProperty("Authorization", "Bearer $SUPABASE_ANON_KEY")
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Prefer", "resolution=merge-duplicates")
                doOutput = true
                connectTimeout = 6000
                readTimeout = 6000
            }

            val payload = JSONObject().apply {
                put("task_id", taskId)
                put("completion_date", date)
                put("done", done)
                put("completed_at", if (done) java.time.Instant.now().toString() else null)
            }

            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(payload.toString())
            writer.flush()
            writer.close()

            val success = conn.responseCode in 200..299
            Log.d(TAG, "saveTaskCompletion: HTTP ${conn.responseCode}")
            success
        } catch (e: Exception) {
            Log.e(TAG, "Error saving completion to Supabase: ${e.message}")
            false
        }
    }

    suspend fun syncTask(task: ScheduleItem): Boolean = withContext(Dispatchers.IO) {
        try {
            val endpoint = "$SUPABASE_URL/rest/v1/tasks"
            val conn = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("apikey", SUPABASE_ANON_KEY)
                setRequestProperty("Authorization", "Bearer $SUPABASE_ANON_KEY")
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Prefer", "resolution=merge-duplicates")
                doOutput = true
            }

            val startTime = task.start
            val endTime = task.end

            val payload = JSONObject().apply {
                put("id", task.id)
                put("name", task.title)
                put("category", task.category)
                put("start_time", startTime)
                put("end_time", endTime)
                put("priority", task.priority)
                put("note", task.note)
            }

            val writer = OutputStreamWriter(conn.outputStream)
            writer.write(payload.toString())
            writer.flush()
            writer.close()

            conn.responseCode in 200..299
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing task to Supabase: ${e.message}")
            false
        }
    }
}
