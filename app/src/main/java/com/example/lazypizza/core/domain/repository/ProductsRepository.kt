package com.example.lazypizza.core.domain.repository

import com.example.lazypizza.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getExtraToppingsFlow(): Flow<List<Product>>
    fun getPizzaByName(pizzaName: String): Flow<Product.Pizza?>
    fun getProductsByReference(references: List<String>): Flow<List<Product>>
}