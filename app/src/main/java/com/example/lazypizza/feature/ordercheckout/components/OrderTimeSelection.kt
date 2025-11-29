package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import timber.log.Timber

@Composable
fun OrderTimeSelection(
    modifier: Modifier = Modifier,
    selected: Int = 0,
    onRadioButtonSelected: (Int) -> Unit
) {

    OrderTimeRadioButton(
        modifier = modifier,
        text = "Earliest available time",
        selected = selected == 0,
        onClick = {
            onRadioButtonSelected(0)
        }
    )

    OrderTimeRadioButton(
        modifier = modifier,
        text = "Schedule time",
        selected = selected == 1,
        onClick = {
            onRadioButtonSelected(1)
        }
    )
}