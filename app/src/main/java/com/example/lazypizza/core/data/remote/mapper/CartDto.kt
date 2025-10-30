package com.example.lazypizza.core.data.remote.mapper

import com.example.lazypizza.core.model.CartItem
import com.example.lazypizza.core.model.ExtraTopping
import com.google.firebase.firestore.PropertyName

data class CartDto(
    val reference: String = "",
    val quantity: Int = 0,
    @get:PropertyName("extra_toppings")
    @set:PropertyName("extra_toppings")
    var extraToppings: List<ExtraTopping> = emptyList(),
)

fun CartItem.toCharDto(): CartDto {
    return CartDto(
        reference = reference,
        quantity = quantity,
        extraToppings = extraToppings
    )
}

fun CartDto.toCartItem(): CartItem {
    return CartItem(
        reference = reference,
        quantity = quantity,
        extraToppings = extraToppings
    )
}

fun CartDto.identityKey(): String {
    val signature = extraToppings
        .map { "${it.reference}:${it.quantity}" }
        .sorted()
        .joinToString("|")
    return if (signature.isEmpty()) reference else "$reference##$signature"
}

