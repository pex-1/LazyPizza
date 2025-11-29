package com.example.lazypizza.feature.ordercheckout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.feature.ordercheckout.components.Checkout
import com.example.lazypizza.feature.ordercheckout.components.DatePickerDialog
import com.example.lazypizza.feature.ordercheckout.components.OrderComments
import com.example.lazypizza.feature.ordercheckout.components.OrderDetails
import com.example.lazypizza.feature.ordercheckout.components.PickupTime
import com.example.lazypizza.feature.ordercheckout.components.RecommendedAddOns
import com.example.lazypizza.feature.ordercheckout.components.TimePickerDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderCheckoutScreenRoot(
    viewModel: OrderCheckoutViewModel = koinViewModel(),
    deviceConfiguration: DeviceConfiguration,
    onNavigateToOrderConfirmation: (String, Int) -> Unit ={ _, _ ->}
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        Dialog(
            onDismissRequest = {},
        ) {
            CircularProgressIndicator()
        }
    }

    OrderCheckoutScreen(
        state = state,
        deviceConfiguration = deviceConfiguration,
        onAction = { action ->
            if (action is OrderCheckoutAction.OnCheckoutClick) {
                onNavigateToOrderConfirmation(action.time, action.orderNumber)
            }
            else {
                viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun OrderCheckoutScreen(
    state: OrderCheckoutState = OrderCheckoutState(),
    onAction: (OrderCheckoutAction) -> Unit = {},
    deviceConfiguration: DeviceConfiguration
) {

    if (state.showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                onAction(OrderCheckoutAction.OnCloseDatePicker)
            },
            onDateSelected = {
                onAction(OrderCheckoutAction.OnDateSelected(it))
            }
        )
    }
    if (state.showTimePicker) {
        TimePickerDialog(
            hours = state.selectedHours,
            minutes = state.selectedMinutes,
            onHourChange = {
                onAction(OrderCheckoutAction.OnTimePickerTimeChange(hour = it.toIntOrNull(), minute = null))
            },
            onMinuteChange = {
                onAction(OrderCheckoutAction.OnTimePickerTimeChange(hour = null, minute = it.toIntOrNull()))
            },
            onDismissRequest = {
                onAction(OrderCheckoutAction.OnCloseTimePicker)
            },
            onConfirmClick = {
                onAction(OrderCheckoutAction.OnTimePickerSubmit)
            },
            validationResult = state.validationResult

        )
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            item {
                PickupTime(
                    isTabletLayout = deviceConfiguration.isLargeScreen(),
                    selected = state.selectedOption,
                    earliestPickupTime = state.earliestPickupTime,
                    onRadioButtonSelected = {
                        onAction(OrderCheckoutAction.OnRadioButtonSelected(it))
                    },
                )
            }

            item {
                OrderDetails(
                    items = state.items,
                    isLargeScreen = deviceConfiguration.isLargeScreen(),
                    onQuantityChange = { reference, quantity ->
                        onAction(OrderCheckoutAction.OnOrderCheckoutItemQuantityChange(reference, quantity))
                    },
                    onDeleteClicked = { reference ->
                        onAction(OrderCheckoutAction.OnDeleteOrderCheckoutItemClick(reference))
                    }
                )
            }

            item {
                RecommendedAddOns(
                    items = state.recommendedItems,
                    onAddItemToCart = { reference, quantity ->
                        onAction(OrderCheckoutAction.OnOrderCheckoutItemQuantityChange(reference, quantity))
                    }
                )
            }

            item {
                OrderComments(
                    comment = state.comment,
                    onCommentChange = {
                        onAction(OrderCheckoutAction.OnCommentChange(it))
                    }
                )
            }

        }
        val totalPrice = remember(state.items) {
            state.items.sumOf { it.price * it.quantity }
        }
        Checkout(
            modifier = Modifier.padding(vertical = 16.dp),
            orderTotal = "%.2f".format(totalPrice),
            isTabletLayout = deviceConfiguration.isLargeScreen()
        ) {
            onAction(OrderCheckoutAction.OnCheckoutClick(state.earliestPickupTime.toString(), 54118))
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun OrderCheckoutPreview() {
    LazyPizzaTheme {
        OrderCheckoutScreen(deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT)
    }
}