package com.example.lazypizza.feature.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.feature.history.components.OrderHistoryItem
import com.example.lazypizza.feature.history.datasource.history
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreenRoot(
    viewModel: HistoryViewModel = koinViewModel(),
    deviceConfiguration: DeviceConfiguration,
    onSignInAction: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->

    }

    HistoryScreen(
        state = state,
        deviceConfiguration = deviceConfiguration,
        onAction = { action ->
            when (action) {
                is HistoryAction.OnSignInAction -> onSignInAction()
            }
        }
    )
}

@Composable
internal fun HistoryScreen(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
    deviceConfiguration: DeviceConfiguration
) {

    if(!state.userLoggedIn) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(if (deviceConfiguration.isLargeScreen()) 2 else 1 ),
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {
            items(items = history, key = { it.orderId }) {
                OrderHistoryItem(it)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .padding(top = 120.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Not Signed In",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Please sign in to view your order history.",
                style = MaterialTheme.customTypography.body3regular
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            LazyPizzaPrimaryButton(
                buttonText = "Sign In",
                onClick = {
                    onAction(HistoryAction.OnSignInAction)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    LazyPizzaTheme {
        HistoryScreen(
            state = HistoryState(),
            deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
            onAction = {}
        )
    }
}