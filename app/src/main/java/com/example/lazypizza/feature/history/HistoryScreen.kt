package com.example.lazypizza.feature.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreenRoot(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->

    }

    HistoryScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun HistoryScreen(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 120.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Not Signed In",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Please sign in to view your order history.",
            style = MaterialTheme.customTypography.body3regular
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LazyPizzaPrimaryButton(
            buttonText = "Sign In",
            onClick = {

            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LazyPizzaTheme {
        HistoryScreen(
            state = HistoryState(),
            onAction = {}
        )
    }
}