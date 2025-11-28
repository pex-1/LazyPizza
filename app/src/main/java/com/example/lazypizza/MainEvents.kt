package com.example.lazypizza

sealed interface MainEvents {
    data object OnLoginSuccessful: MainEvents
}