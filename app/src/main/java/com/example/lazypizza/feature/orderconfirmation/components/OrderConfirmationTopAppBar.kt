@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lazypizza.feature.orderconfirmation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.BackArrow
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.core.presentation.theme.textSecondary8

@Composable
fun OrderConfirmationTopAppBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = "Order Checkout",
                style = MaterialTheme.customTypography.body1medium
            )
        },
        navigationIcon = {
            IconButton(
                modifier = modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
                    .background(
                        color = textSecondary8.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(100)
                    ),
                onClick = { onBackClicked() }
            ) {
                Icon(
                    imageVector = BackArrow,
                    contentDescription = "back",
                    tint = textSecondary
                )
            }
        }
    )
}

@Preview
@Composable
private fun OrderCheckoutTopAppBarPreview() {
    LazyPizzaTheme {
        OrderConfirmationTopAppBar {  }
    }
}