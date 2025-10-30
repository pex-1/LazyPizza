package com.example.lazypizza.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class GradientColors(
    val primary: List<Color> = listOf(gradientStart, gradientEnd)
)

val LocalGradientColors = staticCompositionLocalOf { GradientColors() }

val MaterialTheme.gradientColors: GradientColors
    @Composable
    get() = LocalGradientColors.current