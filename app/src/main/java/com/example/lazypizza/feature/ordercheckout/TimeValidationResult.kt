package com.example.lazypizza.feature.ordercheckout

sealed interface TimeValidationResult {
    data object Ok : TimeValidationResult
    data object OutsideWorkingHours : TimeValidationResult
    data object TooEarlyFromNow : TimeValidationResult
}