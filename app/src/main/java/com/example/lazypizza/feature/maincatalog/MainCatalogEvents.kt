package com.example.lazypizza.feature.maincatalog

sealed interface MainCatalogEvents {
    data class Error(val error: String): MainCatalogEvents
}