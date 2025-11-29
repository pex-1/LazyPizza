package com.example.lazypizza.feature.ordercheckout.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

fun Long.toLocalDate(zoneId: ZoneId): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDate()

fun formatPickerHeadline(date: LocalDate): String {
    // Example: "November 25"
    val monthName = date.month
        .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        .replaceFirstChar { it.titlecase(Locale.getDefault()) }
    return "$monthName ${date.dayOfMonth}"
}