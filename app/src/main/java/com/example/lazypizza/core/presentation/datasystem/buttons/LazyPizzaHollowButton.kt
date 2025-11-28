package com.example.lazypizza.core.presentation.datasystem.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme

@Composable
fun LazyPizzaHollowButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LazyPizzaSecondaryButtonPreview() {
    LazyPizzaTheme {
        LazyPizzaHollowButton(buttonText = "Button")
    }
}