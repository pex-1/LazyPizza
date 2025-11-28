package com.example.lazypizza.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.lazypizza.core.presentation.datasystem.CustomizableSearchBar

private val LightColorScheme = lightColorScheme(
    primary = primary,
    background = background,
    surfaceContainerHigh = surfaceHigher,
    surfaceContainerHighest = surfaceHighest,
    outline = outline
)

@Composable
fun LazyPizzaTheme(
    content: @Composable () -> Unit
) {

    val gradientColors = GradientColors()
    val customTypography = CustomTypography(
        body1regular = body1regular,
        body1medium = body1medium,
        body2regular = body2regular,
        body3regular = body3regular,
        body3medium = body3medium,
        body4regular = body4regular
    )

    CompositionLocalProvider(
        LocalGradientColors provides gradientColors,
        LocalCustomTypography provides customTypography
    ) { }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}