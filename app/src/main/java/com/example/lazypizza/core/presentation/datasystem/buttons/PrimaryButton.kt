package com.example.lazypizza.core.presentation.datasystem.buttons

import androidx.compose.foundation.background
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

@Composable
fun LazyPizzaPrimaryButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    val buttonShadow = MaterialTheme.colorScheme.primary
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = MaterialTheme.gradientColors.primary,
                    startX = 0f,
                    endX = Float.POSITIVE_INFINITY
                ),
                shape = CircleShape
            )
            .dropShadow(
                RoundedCornerShape(20.dp)
            ) {
                radius = 10f
                color = buttonShadow
                spread = 6f
                alpha = 0.25f
            },
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
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

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