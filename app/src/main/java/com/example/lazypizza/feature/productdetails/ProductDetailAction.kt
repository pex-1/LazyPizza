package com.example.lazypizza.feature.productdetails

sealed interface ProductDetailAction {
    data class OnToppingPlus(val toppingsUI: ToppingsUI) : ProductDetailAction
    data class OnToppingMinus(val toppingsUI: ToppingsUI) : ProductDetailAction
    data object OnAddToCartButtonClick : ProductDetailAction
}