package com.example.lazypizza.feature.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lazypizza.core.model.Product
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.util.formatToPrice
import com.example.lazypizza.feature.productdetails.ProductDetailAction
import com.example.lazypizza.feature.productdetails.ProductDetailState

@Composable
internal fun ProductDetailMobile(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var buttonHeight by remember {
        mutableStateOf(0.dp)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 16.dp
                    )
                )
                .background(
                    color = MaterialTheme.colorScheme.background,
                ),
            contentAlignment = Alignment.Center
        ) {
            state.selectedPizza?.let {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(state.selectedPizza.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Pizza",
                    modifier = Modifier
                        .wrapContentSize()
                        .size(240.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        val background = MaterialTheme.colorScheme.background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = background,
                        size = Size(
                            width = 100.dp.toPx(),
                            height = 50.dp.toPx()
                        )
                    )
                }
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = state.selectedPizza?.name.orEmpty(),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = state.selectedPizza?.ingredients.orEmpty().joinToString(", "),
                style = MaterialTheme.customTypography.body3regular
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = "ADD EXTRA TOPPINGS",
                style = MaterialTheme.typography.labelSmall
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(top = 8.dp, bottom = buttonHeight / 2),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.listExtraToppings,
                        key = { it.name }
                    ) { topping ->
                        ProductToppingCard(
                            imageUrl = topping.image,
                            toppingName = topping.name,
                            toppingPrice = topping.price,
                            quantity = topping.quantity,
                            onQuantityPlus = {
                                onAction(ProductDetailAction.OnToppingPlus(topping))
                            },
                            onQuantityMinus = {
                                onAction(ProductDetailAction.OnToppingMinus(topping))
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .height(125.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.surface
                                ),
                            )
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    LazyPizzaPrimaryButton(
                        isLoading = state.isUpdatingCart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                buttonHeight = it.size.height.dp
                            },
                        onClick = { onAction(ProductDetailAction.OnAddToCartButtonClick) },
                        buttonText = "Add to Cart for $${state.selectedPizza?.price?.formatToPrice()}"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailMobilePreview() {
    LazyPizzaTheme {
        ProductDetailMobile(
            state = ProductDetailState(
                selectedPizza = Product.Pizza(
                    id = "",
                    name = "Margherita",
                    price = 8.00,
                    image = "",
                    ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Basil"
                    )
                )
            ),
            onAction = {}
        )
    }
}