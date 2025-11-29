package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaHollowButton
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton

@Composable
fun DialogConfirmBar(
    isConfirmEnabled: Boolean= true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalDivider()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.End
    ) {
        LazyPizzaHollowButton(
            buttonText = "Cancel",
            onClick = onDismiss
        )

        Spacer(Modifier.width(8.dp))

        LazyPizzaPrimaryButton(
            buttonText = "OK"
        ) {
            onConfirm()
        }
    }
}