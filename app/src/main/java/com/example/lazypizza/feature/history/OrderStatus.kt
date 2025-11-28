package com.example.lazypizza.feature.history

import androidx.compose.ui.graphics.Color
import com.example.lazypizza.core.presentation.theme.success
import com.example.lazypizza.core.presentation.theme.warning

enum class OrderStatus(val status: String, val color: Color) {
    IN_PROGRESS("In Progress", warning),
    COMPLETED("Delivered", success),
}