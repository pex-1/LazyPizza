package com.example.lazypizza.feature.orderconfirmation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaHollowButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun OrderConfirmationScreenRoot(
    time: String = "",
    orderNumber: Int = 0,
    onBackToMenuClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .widthIn(max = 380.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Your order has been placed!",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 20.dp),
                text = "Thank you for your order! Please come at\nthe indicated time.",
                style = MaterialTheme.customTypography.body3regular,
                color = textSecondary,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp, color = textSecondary,
                        shape = RoundedCornerShape(12.dp)
                    ),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ORDER NUMBER: ",
                        style = MaterialTheme.typography.labelMedium,
                        color = textSecondary
                    )

                    Text(
                        text = "#$orderNumber",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = textPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "PICKUP TIME: ",
                        style = MaterialTheme.typography.labelMedium,
                        color = textSecondary
                    )

                    Text(
                        text = time,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = textPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            LazyPizzaHollowButton(
                modifier = Modifier.padding(top = 8.dp),
                buttonText = "Back to Menu"
            ) {
                onBackToMenuClick()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderConfirmationScreenPreview() {
    LazyPizzaTheme {
        OrderConfirmationScreenRoot()
    }
}