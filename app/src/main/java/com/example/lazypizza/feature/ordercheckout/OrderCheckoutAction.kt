package com.example.lazypizza.feature.ordercheckout

import java.time.LocalDate

sealed interface OrderCheckoutAction {
    data class OnRadioButtonSelected(val id: Int) : OrderCheckoutAction
    data object OnCloseDatePicker : OrderCheckoutAction
    data class OnOrderCheckoutItemQuantityChange(val reference: String, val quantity: Int) :
        OrderCheckoutAction

    data class OnDeleteOrderCheckoutItemClick(val reference: String) : OrderCheckoutAction
    data class OnCommentChange(val comment: String) : OrderCheckoutAction
    data class OnCheckoutClick(val time: String, val orderNumber: Int) : OrderCheckoutAction
    data class OnDateSelected(val date: LocalDate) : OrderCheckoutAction
    data object OnCloseTimePicker : OrderCheckoutAction
    data object OnTimePickerSubmit : OrderCheckoutAction
    data class OnTimePickerTimeChange(val hour: Int?, val minute: Int?) : OrderCheckoutAction
}