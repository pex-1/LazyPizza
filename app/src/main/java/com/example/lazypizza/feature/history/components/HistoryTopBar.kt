package com.example.lazypizza.feature.history.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.util.DeviceConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(
    modifier: Modifier = Modifier,
    deviceConfiguration: DeviceConfiguration
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Order History",
                style = MaterialTheme.customTypography.body1medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = if (deviceConfiguration.isLargeScreen()) 50.dp else 0.dp)
                    .offset(x = if (!deviceConfiguration.isLargeScreen()) (-8).dp else 0.dp),
                textAlign = TextAlign.Center
            )
        },
    )
}

@Preview
@Composable
private fun HistoryTopBarPreview() {
    LazyPizzaTheme {
        HistoryTopBar(deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT)
    }
}