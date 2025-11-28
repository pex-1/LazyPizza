package com.example.lazypizza.feature.history

sealed interface HistoryAction {
    data object OnSignInAction : HistoryAction
}