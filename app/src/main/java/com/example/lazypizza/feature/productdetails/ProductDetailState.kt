package com.example.lazypizza.feature.productdetails

import com.example.lazypizza.core.model.Product
import com.example.lazypizza.core.model.ProductType

data class ProductDetailState(
    val isLoading: Boolean = false,
    val isUpdatingCart: Boolean = false,
    val selectedPizza: Product.Pizza? = null,
    val listExtraToppings: List<ToppingsUI> = emptyList(),
)

data class ToppingsUI(
    val id: String,
    val type: ProductType = ProductType.EXTRA_TOPPING,
    val name: String,
    val image: String,
    val price: Double,
    val quantity: Int
)