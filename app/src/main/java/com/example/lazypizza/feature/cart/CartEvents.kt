package com.example.lazypizza.feature.cart

sealed interface CartEvents {
    data class Error(val error: String): CartEvents
}