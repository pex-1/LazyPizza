package com.example.lazypizza.feature.history.datasource

import com.example.lazypizza.feature.history.HistoryItem
import com.example.lazypizza.feature.history.OrderStatus

val history = listOf(
    HistoryItem(
        orderId = 12347,
        date = "September 25, 12:15",
        items = listOf("1 x Margherita"),
        status = OrderStatus.IN_PROGRESS,
        totalAmound = 8.99f
    ),

    HistoryItem(
        orderId = 12346,
        date = "September 25, 12:15",
        items = listOf(
            "1 x Margherita",
            "2 x Pepsi",
            "2 x Cookies Ice Cream"
        ),
        status = OrderStatus.COMPLETED,
        totalAmound = 25.45f
    ),

    HistoryItem(
        orderId = 12345,
        date = "September 25, 12:15",
        items = listOf(
            "1 x Margherita",
            "2 x Cookies Ice Cream"
        ),
        status = OrderStatus.COMPLETED,
        totalAmound = 11.78f
    )
)