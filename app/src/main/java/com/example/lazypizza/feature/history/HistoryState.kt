package com.example.lazypizza.feature.history

data class HistoryState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)