package com.example.lazypizza.core.model

data class CartItemUI(
    val reference: String,
    val quantity: Int = 0,
    val image: String,
    val name: String,
    val price: Double,
    val type: ProductType,
    val ingredients: List<String> = emptyList(),
    val extraToppingsRelated: List<String> = emptyList(),
)

fun CartItemUI.toCartItem() = CartItem(
    reference = reference,
    quantity = quantity,
)