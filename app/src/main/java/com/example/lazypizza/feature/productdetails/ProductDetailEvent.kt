package com.example.lazypizza.feature.productdetails

sealed interface ProductDetailEvent {
    data class Error(val error: String) : ProductDetailEvent
    data object OnCartSuccessfullySaved : ProductDetailEvent
}