package com.example.lazypizza.feature.maincatalog

import com.example.lazypizza.core.model.CartItemUI


sealed interface MainCatalogAction {
    data class OnProductClicked(val pizzaName: String) : MainCatalogAction
    data class OnQueryChange(val newSearchQuery: String) : MainCatalogAction
    data class OnProductPlus(val productState: CartItemUI) : MainCatalogAction
    data class OnProductMinus(val productState: CartItemUI) : MainCatalogAction
    data class OnProductDelete(val productState: CartItemUI) : MainCatalogAction
}