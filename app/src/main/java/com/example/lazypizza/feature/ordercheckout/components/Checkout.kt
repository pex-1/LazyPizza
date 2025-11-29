package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary

@Composable
fun Checkout(
    modifier: Modifier = Modifier,
    orderTotal: String = "25.45",
    isTabletLayout: Boolean = false,
    onPlaceOrderClick: () -> Unit = {}
) {


    if (isTabletLayout) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrderTotal(true, orderTotal)
            LazyPizzaPrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                buttonText = "Place order"
            ) {
                onPlaceOrderClick()
            }
        }
    } else {
        Column(modifier = modifier) {
            OrderTotal(false, orderTotal)
            LazyPizzaPrimaryButton(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                buttonText = "Place order"
            ) {
                onPlaceOrderClick()
            }
        }
    }
}

@Composable
fun OrderTotal(
    isTabletLayout: Boolean,
    orderTotal: String
) {
    Row(
        modifier = Modifier
            .then(
                if (isTabletLayout) {
                    Modifier.fillMaxWidth(0.5f)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .padding(top = 4.dp),
        horizontalArrangement = if (isTabletLayout) Arrangement.Start else Arrangement.SpaceBetween
    ) {
        Text(
            text = "ORDER TOTAL:",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = "$$orderTotal",
            style = MaterialTheme.typography.labelSmall,
            color = textPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutPreview() {
    LazyPizzaTheme {
        Checkout()
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutPreviewTablet() {
    LazyPizzaTheme {
        Checkout(isTabletLayout = true)
    }
}