package com.example.lazypizza

sealed interface MainAction {
    data object OnLogoutAction : MainAction
}