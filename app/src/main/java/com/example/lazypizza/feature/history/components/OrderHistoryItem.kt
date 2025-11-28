package com.example.lazypizza.feature.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.feature.history.HistoryItem
import com.example.lazypizza.feature.history.datasource.history

@Composable
fun OrderHistoryItem(
    historyItem: HistoryItem
) {
    Card(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .clip(RoundedCornerShape(16.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Order #%d".format(historyItem.orderId),
                    style = MaterialTheme.typography.titleSmall,
                )

                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = historyItem.date,
                    style = MaterialTheme.customTypography.body4regular,
                    color = textSecondary
                )

                historyItem.items.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.customTypography.body4regular,
                        color = textPrimary
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(historyItem.status.color)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                    text = historyItem.status.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Total amount:",
                        style = MaterialTheme.customTypography.body4regular
                    )
                    Text(
                        text = "$%.2f".format(historyItem.totalAmound),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderHistoryItemPreview() {
    LazyPizzaTheme {
        OrderHistoryItem(history[1])
    }
}

