package com.example.lazypizza.core.presentation.datasystem.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.gradientColors
import com.example.lazypizza.core.presentation.theme.gradientStart
import com.example.lazypizza.core.presentation.theme.primaryGradient
import timber.log.Timber

@Composable
fun LazyPizzaPrimaryButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val buttonShadow = MaterialTheme.colorScheme.primary
    Button(
        enabled = isEnabled,
        onClick = {
            onClick()
        },
        modifier = modifier
            .height(48.dp)
            .backgroundAndShadow(
            isEnabled,
            buttonShadow,
            MaterialTheme.gradientColors.primary
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
        } else {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

fun Modifier.backgroundAndShadow(
    enabled: Boolean,
    buttonShadow: Color,
    gradientColors: Brush) =
    if (enabled) {
        this
            .background(gradientColors, CircleShape)
            .dropShadow(
                RoundedCornerShape(20.dp)
            ) {
                radius = 10f
                color = buttonShadow
                spread = 6f
                alpha = 0.25f
            }
    } else {
        this
    }


@Preview(showBackground = true)
@Composable
private fun LazyPizzaPrimaryButtonPreview() {
    LazyPizzaTheme {
        LazyPizzaPrimaryButton(
            buttonText = "Button",
            onClick = {},
        )
    }
}