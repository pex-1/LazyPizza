package com.example.lazypizza.feature.maincatalog

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.R
import com.example.lazypizza.core.model.ProductType
import com.example.lazypizza.core.presentation.datasystem.CustomizableSearchBar
import com.example.lazypizza.core.presentation.datasystem.cards.PizzaCard
import com.example.lazypizza.core.presentation.datasystem.cards.ProductCard
import com.example.lazypizza.core.presentation.datasystem.chips.ProductChip
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.core.presentation.util.replaceUnderscores
import com.example.lazypizza.core.presentation.util.toCamelCase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainCatalogScreenRoot(
    deviceConfiguration: DeviceConfiguration,
    viewModel: MainCatalogViewModel = koinViewModel(),
    onNavigateToProductDetail: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(
        flow = viewModel.event
    ) { event ->
        when (event) {
            is MainCatalogEvents.Error -> {
                Toast.makeText(context, event.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    if (state.isCreateNewCart) {
        Dialog(
            onDismissRequest = {},
        ) {
            CircularProgressIndicator()
        }
    }

    MainCatalogScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is MainCatalogAction.OnProductClicked -> onNavigateToProductDetail(action.pizzaName)
                else -> viewModel.onAction(action)
            }
        },
        deviceConfiguration = deviceConfiguration
    )
}

@Composable
fun MainCatalogScreen(
    state: MainCatalogState = MainCatalogState(),
    onAction: (MainCatalogAction) -> Unit = {},
    deviceConfiguration: DeviceConfiguration
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(
                    shape = RoundedCornerShape(16.dp),
                ),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.banner),
            contentDescription = "Banner",
        )

        Spacer(modifier = Modifier.size(16.dp))

        CustomizableSearchBar(
            query = state.searchQuery,
            onQueryChange = { onAction(MainCatalogAction.OnQueryChange(it)) },
        )

        LazyRow(
            modifier = Modifier
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(
                ProductType.entries,
                key = { it.name }
            ) { productType ->
                if (productType != ProductType.EXTRA_TOPPING) {
                    ProductChip(
                        chipText = productType.name.toCamelCase(),
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(
                                    state.headerIndexMap[productType] ?: 0
                                )
                            }
                        }
                    )
                }
            }
        }

        if (state.products.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                ),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.productsFiltered.forEach { (type, products) ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.background)
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = type.name.replaceUnderscores(),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                    val chunkedProducts =
                        products.chunked(if (deviceConfiguration.isLargeScreen()) 2 else 1)
                    items(chunkedProducts.size) { rowIndex ->
                        val rowItems = chunkedProducts[rowIndex]

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItems.forEach { product ->
                                Box(modifier = Modifier.weight(1f)) {
                                    when (product.type) {
                                        ProductType.PIZZA -> {
                                            PizzaCard(
                                                imageUrl = product.image,
                                                pizzaName = product.name,
                                                pizzaDescription = product.ingredients.joinToString(
                                                    ", "
                                                ),
                                                pizzaPrice = product.price,
                                                onPizzaClick = {
                                                    onAction(
                                                        MainCatalogAction.OnProductClicked(
                                                            pizzaName = product.name
                                                        )
                                                    )
                                                }
                                            )
                                        }

                                        else -> {
                                            ProductCard(
                                                imageUrl = product.image,
                                                productName = product.name,
                                                productPrice = product.price,
                                                quantity = product.quantity,
                                                onQuantityChange = { newQuantity ->
                                                    when {
                                                        newQuantity == 0 -> {
                                                            onAction(
                                                                MainCatalogAction.OnProductMinus(
                                                                    product
                                                                )
                                                            )
                                                        }

                                                        newQuantity > product.quantity -> {
                                                            onAction(
                                                                MainCatalogAction.OnProductPlus(
                                                                    product
                                                                )
                                                            )
                                                        }

                                                        newQuantity < product.quantity -> {
                                                            onAction(
                                                                MainCatalogAction.OnProductMinus(
                                                                    product
                                                                )
                                                            )
                                                        }
                                                    }
                                                },
                                                onDeleteClicked = {
                                                    onAction(
                                                        MainCatalogAction.OnProductDelete(
                                                            product
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AllProductsScreenPreview() {
    LazyPizzaTheme {
        MainCatalogScreen(deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT)
    }
}