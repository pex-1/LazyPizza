package com.example.lazypizza.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class CustomTypography(
    val body1regular: TextStyle = TextStyle.Default,
    val body1medium: TextStyle = TextStyle.Default,
    val body3regular: TextStyle = TextStyle.Default,
    val body3medium: TextStyle = TextStyle.Default,
    val body4regular: TextStyle = TextStyle.Default,
)

val LocalCustomTypography = staticCompositionLocalOf { CustomTypography() }


val MaterialTheme.customTypography: CustomTypography
    @Composable
    get() = LocalCustomTypography.current
