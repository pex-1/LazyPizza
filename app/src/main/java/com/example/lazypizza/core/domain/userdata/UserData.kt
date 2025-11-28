package com.example.lazypizza.core.domain.userdata

import kotlinx.coroutines.flow.Flow

interface UserData {
    fun getCartId(): Flow<String?>
    fun getIsLoggedIn(): Flow<Boolean>
    suspend fun setCardId(cartId: String)
    suspend fun setIsLoggedIn(loggedIn: Boolean)
    suspend fun clear()
}