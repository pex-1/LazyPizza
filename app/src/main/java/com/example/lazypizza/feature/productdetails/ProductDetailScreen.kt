@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.feature.productdetails

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.feature.productdetails.components.ProductDetailMobile
import com.example.lazypizza.feature.productdetails.components.ProductDetailTablet
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailScreenRoot(
    viewModel: ProductDetailViewModel = koinViewModel(),
    pizzaName: String,
    onAddToCartClick: () -> Unit = {},
    deviceConfiguration: DeviceConfiguration
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    //TODO: improve this
    viewModel.init(pizzaName)

    ObserveAsEvents(
        flow = viewModel.event,
    ) { event ->
        when (event) {
            is ProductDetailEvent.Error -> {
                Toast.makeText(context, event.error, Toast.LENGTH_LONG).show()
            }

            is ProductDetailEvent.OnCartSuccessfullySaved -> {
                onAddToCartClick()
            }
        }
    }

    ProductDetailScreen(
        state = state,
        onAction = viewModel::onAction,
        deviceConfiguration = deviceConfiguration
    )

}
@Composable
internal fun ProductDetailScreen(
    state: ProductDetailState = ProductDetailState(),
    onAction: (ProductDetailAction) -> Unit = {},
    deviceConfiguration: DeviceConfiguration
) {

    if (deviceConfiguration.isLargeScreen()) {
        ProductDetailTablet(
            state = state,
            onAction = onAction,
            deviceConfiguration = deviceConfiguration
        )
    } else {
        ProductDetailMobile(
            state = state,
            onAction = onAction
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    LazyPizzaTheme {
        ProductDetailScreen(deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT)
    }
}