package com.example.lazypizza.core.presentation.datasystem.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textPrimary

@Composable
fun ProductChip(
    modifier: Modifier = Modifier,
    chipText: String,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() } ,
                onClick = { onClick() }
            )
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = chipText,
            style = MaterialTheme.customTypography.body3medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipPreview() {
    LazyPizzaTheme {
        ProductChip(
            chipText = "Pizza"
        )
    }
}