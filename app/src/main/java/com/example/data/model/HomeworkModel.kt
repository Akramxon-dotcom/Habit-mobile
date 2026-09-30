package com.example.data.model

import java.util.UUID

data class HomeworkSubTask(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isDone: Boolean = false
)

data class HomeworkEntry(
    val id: String = UUID.randomUUID().toString(),
    val dateStr: String, // e.g. "2026-09-24"
    val topic: String = "", // e.g. "Ingliz tili Unit 5 & Matematika"
    val rawText: String = "",
    val tasks: List<HomeworkSubTask> = emptyList(),
    val isCompleted: Boolean = false,
    val createdAtEpochMs: Long = System.currentTimeMillis()
)
