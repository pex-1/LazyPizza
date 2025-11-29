package com.example.lazypizza.feature.productdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.core.presentation.util.applyIf
import com.example.lazypizza.core.presentation.util.formatToPrice
import com.example.lazypizza.feature.productdetails.ProductDetailAction
import com.example.lazypizza.feature.productdetails.ProductDetailState

@Composable
internal fun ProductDetailTablet(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier,
    deviceConfiguration: DeviceConfiguration
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
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

            Text(
                text = state.selectedPizza?.name.orEmpty(),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = state.selectedPizza?.ingredients.orEmpty().joinToString(", "),
                style = MaterialTheme.customTypography.body3regular
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        bottomStart = 16.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .applyIf(deviceConfiguration == DeviceConfiguration.TABLET_LANDSCAPE) {
                    padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    )
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = "ADD EXTRA TOPPINGS",
                style = MaterialTheme.typography.labelSmall
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .applyIf(deviceConfiguration == DeviceConfiguration.TABLET_LANDSCAPE) {
                        weight(1f)
                    },
                contentPadding = PaddingValues(
                    top = 8.dp,
                ),
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

            LazyPizzaPrimaryButton(
                isLoading = state.isUpdatingCart,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onAction(ProductDetailAction.OnAddToCartButtonClick) },
                buttonText = "Add to Cart for $${state.selectedPizza?.price?.formatToPrice()}"
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_TABLET
)
@Composable
private fun ProductDetailTabletPreview() {
    LazyPizzaTheme {
        ProductDetailTablet(
            state = ProductDetailState(),
            onAction = {},
            deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT
        )
    }
}