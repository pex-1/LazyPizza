package com.example.lazypizza.feature.maincatalog

import com.example.lazypizza.core.model.CartItemUI
import com.example.lazypizza.core.model.ProductType

data class MainCatalogState(
    val isLoading: Boolean = true,
    val isCreateNewCart: Boolean = false,
    val products: Map<ProductType, List<CartItemUI>> = emptyMap(),
    val headerIndexMap: Map<ProductType, Int> = emptyMap(),
    val productsFiltered: Map<ProductType, List<CartItemUI>> = emptyMap(),
    val searchQuery: String = "",
)