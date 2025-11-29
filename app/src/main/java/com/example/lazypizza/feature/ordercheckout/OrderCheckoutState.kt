package com.example.lazypizza.feature.ordercheckout

import com.example.lazypizza.core.model.CartItemUI
import java.time.LocalDate
import java.time.LocalTime

data class OrderCheckoutState(
    val items: List<CartItemUI> = emptyList(),
    val selectedOption: Int = 0,
    val isLoading: Boolean = false,
    val recommendedItems: List<CartItemUI> = emptyList(),
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val validationResult: TimeValidationResult = TimeValidationResult.OutsideWorkingHours,
    val cartId: String = "",
    val selectedHours: Int = LocalTime.now().plusMinutes(15).hour,
    val selectedMinutes: Int = LocalTime.now().plusMinutes(15).minute,
    val comment: String = "",
    val selectedDate: LocalDate? = null,
    val earliestPickupTime: String? = null,
    val selectedDateMillis: Long? = null,
)
