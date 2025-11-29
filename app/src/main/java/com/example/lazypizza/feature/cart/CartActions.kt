package com.example.lazypizza.feature.cart

sealed interface CartActions {
    data class OnCartItemQuantityChange(val reference: String, val quantity: Int): CartActions
    data class OnDeleteCartItemClick(val reference: String): CartActions
    data object OnNavigateToMenuClick: CartActions
    data object OnProceedToCheckoutClick: CartActions
}