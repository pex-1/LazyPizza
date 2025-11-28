package com.example.lazypizza.feature.history

data class HistoryItem(
    val orderId: Int = -1,
    val date: String = "",
    val items: List<String> = emptyList(),
    val status: OrderStatus = OrderStatus.IN_PROGRESS,
    val totalAmound: Float = 0.0f
)