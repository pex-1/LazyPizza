package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.model.CartItemUI
import com.example.lazypizza.core.model.ProductType
import com.example.lazypizza.core.presentation.datasystem.cards.ToppingCard
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme

@Composable
fun RecommendedAddOns(
    items: List<CartItemUI> = emptyList(),
    onAddItemToCart: (String, Int) -> Unit = { _, _ -> }
    ) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "RECOMMENDED ADD-ONS",
            style = MaterialTheme.typography.labelSmall
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = items,
                key = { item -> item.name }
            ) { item ->
                ToppingCard(
                    cartItem = item,
                    imageUrl = item.image,
                    onClick = {
                        onAddItemToCart(item.reference, item.quantity)
                    }
                )
            }
        }

        OrderCheckoutDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun RecommendedAddOnsPreview() {
    LazyPizzaTheme {
        RecommendedAddOns(
            items = listOf(
                CartItemUI(
                    reference = "",
                    image = "",
                    name = "BBQ Sauce",
                    price = 0.59,
                    type = ProductType.EXTRA_TOPPING,
                ),
                CartItemUI(
                    reference = "",
                    image = "",
                    name = "BBQ Sauce 2",
                    price = 0.59,
                    type = ProductType.EXTRA_TOPPING,
                ),
                CartItemUI(
                    reference = "",
                    image = "",
                    name = "BBQ Sauce 3",
                    price = 0.59,
                    type = ProductType.EXTRA_TOPPING,
                ),
                CartItemUI(
                    reference = "",
                    image = "",
                    name = "BBQ Sauce 4",
                    price = 0.59,
                    type = ProductType.EXTRA_TOPPING,
                )
            )
        )
    }
}