package com.example.lazypizza.core.presentation.datasystem.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaSecondaryButton
import com.example.lazypizza.core.presentation.theme.Minus
import com.example.lazypizza.core.presentation.theme.Plus
import com.example.lazypizza.core.presentation.theme.Trash
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.outline50
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.core.presentation.util.formatToPrice

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    productName: String,
    productPrice: Double,
    quantity: Int,
    extraToppings: List<String> = emptyList(),
    onQuantityChange: (Int) -> Unit= {},
    onDeleteClicked: () -> Unit= {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = productName,
            modifier = Modifier
                .width(108.dp)
                .fillMaxHeight()
                .padding(start = 1.dp, top = 1.dp, bottom = 1.dp, end = 8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.customTypography.body1medium
                )

                if (quantity > 0) {
                    IconButton(
                        onClick = { onDeleteClicked() },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .border(
                                width = 1.dp,
                                color = outline50,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(4.dp)
                            .size(22.dp)
                    ) {
                        Icon(
                            imageVector = Trash,
                            contentDescription = "Remove",
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            if(extraToppings.isNotEmpty()) {
                extraToppings.map { extraTopping ->
                    Text(
                        text = extraTopping,
                        style = MaterialTheme.customTypography.body3regular
                    )
                }
            }

            Spacer(modifier = Modifier.height(if(extraToppings.isNotEmpty()) 8.dp else 12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (quantity == 0) {
                    Text(
                        text = "$${productPrice.formatToPrice()}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    LazyPizzaSecondaryButton(
                        buttonText = "Add to Cart",
                        onClick = { onQuantityChange(1) }
                    )
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .border(
                                    width = 1.dp,
                                    color = outline50,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(4.dp)
                                .size(22.dp),
                            enabled = quantity > 1
                        ) {
                            Icon(
                                imageVector = Minus,
                                contentDescription = "Decrease",
                                modifier = Modifier.size(16.dp),
                            )
                        }

                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleLarge,
                        )

                        IconButton(
                            onClick = { onQuantityChange(quantity + 1) },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .border(
                                    width = 1.dp,
                                    color = outline50,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(4.dp)
                                .size(22.dp)
                        ) {
                            Icon(
                                imageVector = Plus,
                                contentDescription = "Increase",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }


                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "$${(productPrice * quantity).formatToPrice()}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "$quantity x $${productPrice.formatToPrice()}",
                            style = MaterialTheme.customTypography.body4regular
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    var quantity by remember { mutableIntStateOf(1) }

    ProductCard(
        imageUrl = "",
        productName = "Mineral Water",
        productPrice = 1.49,
        quantity = quantity,
        onQuantityChange = { quantity = it },
        onDeleteClicked = {},
        extraToppings = listOf("1X Extra cheese", "2X Extra sauce")
    )
}
