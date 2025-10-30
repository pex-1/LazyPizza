package com.example.lazypizza.core.data.model

import com.example.lazypizza.core.model.Product
import com.example.lazypizza.core.model.ProductType

data class ProductEntity(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: Double = 0.0,
    val type: ProductType = ProductType.PIZZA,
    val ingredients: List<String>? = null,
)

fun ProductEntity.toProduct(): Product? {
    return when (type) {
        ProductType.PIZZA -> Product.Pizza(
            id = id,
            type = type,
            name = name,
            price = price,
            image = image,
            ingredients = ingredients.orEmpty()
        )
        ProductType.DRINK -> Product.Drink(
            id = id,
            type = type,
            name = name,
            price = price,
            image = image
        )
        ProductType.ICE_CREAM -> Product.IceCream(
            id = id,
            type = type,
            name = name,
            price = price,
            image = image
        )
        ProductType.SAUCE -> Product.IceCream(
            id = id,
            type = type,
            name = name,
            price = price,
            image = image
        )
        ProductType.EXTRA_TOPPING -> Product.IceCream(
            id = id,
            type = type,
            name = name,
            price = price,
            image = image
        )
    }
}