package com.example.lazypizza.feature.ordercheckout.util

import java.time.LocalTime

data class TimeInputState(
    val hour: String = "",
    val minute: String = "",
    val parsedTime: LocalTime? = null
) {
    companion object {
        fun from(time: LocalTime): TimeInputState =
            TimeInputState(
                hour = time.hour.toString().padStart(2, '0'),
                minute = time.minute.toString().padStart(2, '0'),
                parsedTime = time
            )
    }
}

object TimeInputFormatter {

    fun onHourChanged(state: TimeInputState, new: String): TimeInputState {
        val digits = new.filter(Char::isDigit).take(2)
        if (digits.isEmpty()) {
            return state.copy(hour = "", parsedTime = null)
        }

        val value = digits.toIntOrNull() ?: return state
        if (value !in 0..23) return state

        return state.copy(
            hour = digits,
            parsedTime = parseTime(digits, state.minute)
        )
    }

    fun onMinuteChanged(state: TimeInputState, new: String): TimeInputState {
        val digits = new.filter(Char::isDigit).take(2)
        if (digits.isEmpty()) {
            return state.copy(minute = "", parsedTime = null)
        }

        val value = digits.toIntOrNull() ?: return state
        if (value !in 0..59) return state

        return state.copy(
            minute = digits,
            parsedTime = parseTime(state.hour, digits)
        )
    }

    private fun parseTime(hour: String, minute: String): LocalTime? {
        val h = hour.toIntOrNull()
        val m = minute.toIntOrNull()
        return if (h != null && m != null) LocalTime.of(h, m) else null
    }
}