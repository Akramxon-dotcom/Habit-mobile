package com.example.data.model

data class HabitState(
    val title: String = "",
    val category: String = "",
    val start: String = "",
    val end: String = "",
    val note: String = "",
    val blocking: Boolean = false,
    val lastArrivalPlace: String = "",
    val lastArrival: String = "",
    val lastAnswer: String = "",
    val lastAnsweredTitle: String = "",
    val lastAnswerTime: String = "",
    val lastUpdatedEpochMs: Long = System.currentTimeMillis()
)
