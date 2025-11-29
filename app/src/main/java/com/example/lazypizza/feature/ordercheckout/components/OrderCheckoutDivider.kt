package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun OrderCheckoutDivider(
    paddingTop: Dp = 16.dp
){
    HorizontalDivider(
        modifier = Modifier.padding(top = paddingTop),
        thickness = 1.dp,
        color = textSecondary.copy(alpha = 0.2f)
    )
}