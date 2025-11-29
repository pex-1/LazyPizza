package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textPrimary
import timber.log.Timber
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupTime(
    isTabletLayout: Boolean = false,
    selected: Int = 0,
    earliestPickupTime: String? = null,
    onRadioButtonSelected: (Int) -> Unit = {}
) {
    val time = LocalTime.now().plusMinutes(15)

    Column {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = "PICKUP TIME",
            style = MaterialTheme.typography.labelSmall
        )

        if (isTabletLayout) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OrderTimeSelection(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f),
                    selected = selected
                ) {
                    onRadioButtonSelected(it)
                }
            }
        } else {
            OrderTimeSelection(
                modifier = Modifier.padding(bottom = 8.dp),
                selected = selected
            ) {
                onRadioButtonSelected(it)
            }
        }


        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "EARLIEST PICKUP TIME:",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            val displayTime = earliestPickupTime ?: "%2d:%2d".format(time.hour, time.minute)

            Text(
                text = displayTime,
                style = MaterialTheme.typography.labelSmall,
                color = textPrimary,
            )
        }

        OrderCheckoutDivider()

    }
}

@Preview(showBackground = true)
@Composable
private fun PickupTimePreview() {
    LazyPizzaTheme {
        PickupTime()
    }
}

