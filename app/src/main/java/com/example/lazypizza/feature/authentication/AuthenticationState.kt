package com.example.lazypizza.feature.authentication

data class AuthenticationState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null,
    val phoneNumber: String = "",
    val phoneNumberConfirmed: Boolean = false,
    val invalidPhoneNumber: Boolean = false,
    val newCodeTimer: Int = 0,
    val loginSuccessful: Boolean = false
)