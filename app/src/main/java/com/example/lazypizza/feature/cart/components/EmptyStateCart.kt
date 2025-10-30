package com.example.lazypizza.feature.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.customTypography

@Composable
fun EmptyStateCart(
    modifier: Modifier = Modifier,
    onBackToMenuClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            text = "Head back to the menu and grab a pizza you love",
            style = MaterialTheme.customTypography.body3regular
        )

        Spacer(modifier = Modifier.size(20.dp))

        LazyPizzaPrimaryButton(
            buttonText = "Back to menu",
            onClick = { onBackToMenuClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCartComponentPreview() {
    EmptyStateCart()
}