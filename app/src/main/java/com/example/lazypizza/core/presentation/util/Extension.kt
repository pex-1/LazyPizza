package com.example.lazypizza.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.Locale

@Composable
fun Modifier.applyIf(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier())
    } else {
        this
    }
}

fun String.replaceUnderscores() =
    this.replace("_", " ")

fun String.toCamelCase() =
    this.lowercase()
        .split(' ', '_')
        .filter { it.isNotEmpty() }.joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }

fun Double.formatToPrice(): String {
    return String.format(Locale.ROOT, "%.2f", this)
}