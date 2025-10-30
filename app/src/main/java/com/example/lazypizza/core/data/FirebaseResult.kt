package com.example.lazypizza.core.data

sealed class FirebaseResult<out T> {
    data class Success<out T>(val data: T) : FirebaseResult<T>()
    data class Error(val exception: Exception) : FirebaseResult<Nothing>()
}