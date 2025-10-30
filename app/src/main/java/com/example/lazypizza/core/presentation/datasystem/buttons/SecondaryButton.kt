package com.example.lazypizza.core.presentation.datasystem.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.primary8

@Composable
fun LazyPizzaSecondaryButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = {
            onClick()
        },
        border = BorderStroke(
            width = 1.dp,
            color = primary8
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.background
        )
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
        LazyPizzaSecondaryButton(
            buttonText = "Button",
            onClick = {},
        )
    }
}