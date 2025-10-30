package com.example.lazypizza.feature.cart

import com.example.lazypizza.core.model.CartItemUI

data class CartState(
    val isLoading: Boolean = false,
    val isUpdatingCart: Boolean = false,
    val cartItems: List<CartItemUI> = emptyList(),
    val recommendedItems: List<CartItemUI> = emptyList(),
    val cartId: String = "",
)