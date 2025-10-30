package com.example.lazypizza.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lazypizza.R

// Set of Material typography styles to start with

val InstrumentSans = FontFamily(
    Font(
        resId = R.font.instrument_sans_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.instrument_sans_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.instrument_sans_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.instrument_sans_bold,
        weight = FontWeight.Bold
    )
)

val body1regular = TextStyle(
    fontFamily = InstrumentSans,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    color = textPrimary
)

val body1medium = TextStyle(
    fontFamily = InstrumentSans,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    color = textPrimary
)

val body3regular = TextStyle(
    fontFamily = InstrumentSans,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    color = textSecondary
)

val body3medium = TextStyle(
    fontFamily = InstrumentSans,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    color = textPrimary
)

val body4regular = TextStyle(
    fontFamily = InstrumentSans,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    color = textSecondary
)


val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = textPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        color = textPrimary
    ),
    titleSmall = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = textPrimary
    ),
    labelSmall = TextStyle(
        fontFamily = InstrumentSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = textSecondary
    ),


)