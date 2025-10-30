package com.example.lazypizza.core.domain.repository

import com.example.lazypizza.core.data.FirebaseResult
import com.example.lazypizza.core.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun createCart(items: List<CartItem>): FirebaseResult<String>
    suspend fun updateCart(cartId: String, items: List<CartItem>): FirebaseResult<Unit>
    fun getCart(cartId: String): Flow<FirebaseResult<List<CartItem>>>
}