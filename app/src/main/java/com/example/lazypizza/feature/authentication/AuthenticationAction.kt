package com.example.lazypizza.feature.authentication

sealed interface AuthenticationAction {
    data class OnEnterCodeNumber(val number: Int?, val index: Int) : AuthenticationAction
    data class OnEnterPhoneNumber(val number: String) : AuthenticationAction
    data class OnChangeFieldFocused(val index: Int) : AuthenticationAction
    data object OnKeyboardBack : AuthenticationAction
    data object OnContinueClicked : AuthenticationAction

}