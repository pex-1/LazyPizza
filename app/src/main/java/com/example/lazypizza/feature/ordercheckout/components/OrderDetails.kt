package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.model.CartItemUI
import com.example.lazypizza.core.model.ProductType
import com.example.lazypizza.core.presentation.datasystem.cards.ProductCard
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun OrderDetails(
    items: List<CartItemUI>,
    modifier: Modifier = Modifier,
    isLargeScreen: Boolean = false,
    initiallyExpanded: Boolean = false,
    onQuantityChange: (String, Int) -> Unit = { _, _ -> },
    onDeleteClicked: (String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "chevron rotation"
    )

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header with expand/collapse button
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ORDER DETAILS",
                style = MaterialTheme.typography.labelSmall
            )
            OutlinedIconButton(
                modifier = Modifier.size(22.dp),
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    contentColor = textSecondary,
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                ),
                onClick = { isExpanded = !isExpanded }
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp)
                        .rotate(rotationAngle),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_down),
                    contentDescription = "Collapse/Expand order details",
                )
            }
        }

        // Expandable content
        if (isExpanded) {

//                LazyVerticalStaggeredGrid(
//                    columns = StaggeredGridCells.Fixed(if (isLargeScreen) 2 else 1 ),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp),
//                    verticalItemSpacing = 8.dp
//                ) {
//                    items.forEach { cartItem ->
//                        item {
//                            ProductCard(
//                                productName = cartItem.name,
//                                productPrice = cartItem.price,
//                                quantity = cartItem.quantity,
//                                imageUrl = cartItem.image,
//                                extraToppings = cartItem.extraToppingsRelated,
//                                onQuantityChange = {
//                                    onQuantityChange(cartItem.reference, cartItem.quantity)
//                                },
//                                onDeleteClicked = {
//                                    onDeleteClicked(cartItem.reference)
//                                }
//                            )
//                        }
//                    }
//                }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.forEach { cartItem ->
                    ProductCard(
                        productName = cartItem.name,
                        productPrice = cartItem.price,
                        quantity = cartItem.quantity,
                        imageUrl = cartItem.image,
                        extraToppings = cartItem.extraToppingsRelated,
                        onQuantityChange = {
                            onQuantityChange(cartItem.reference, cartItem.quantity)
                        },
                        onDeleteClicked = {
                            onDeleteClicked(cartItem.reference)
                        }
                    )
                }
            }

        }

        OrderCheckoutDivider(paddingTop = 8.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderDetailsSectionPreview() {
    LazyPizzaTheme {
        OrderDetails(
            items = listOf(
                CartItemUI(
                    reference = "",
                    name = "Margherita Pizza",
                    price = 8.99,
                    quantity = 2,
                    image = "",
                    type = ProductType.PIZZA
                ),
                CartItemUI(
                    reference = "",
                    name = "Pepperoni Pizza",
                    price = 10.99,
                    quantity = 1,
                    image = "",
                    type = ProductType.PIZZA
                )
            ),
            initiallyExpanded = true
        )
    }
}