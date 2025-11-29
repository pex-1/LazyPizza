package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.surfaceHighest
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun OrderComments(
    modifier: Modifier = Modifier,
    comment: String,
    onCommentChange: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "COMMENTS",
            style = MaterialTheme.typography.labelSmall
        )

        OutlinedTextField(
            value = comment,
            onValueChange = onCommentChange,
            modifier =
                Modifier.fillMaxWidth()
                    .height(92.dp)
            ,
            placeholder = {
                Text(
                    text = "Add Comment",
                    style = MaterialTheme.customTypography.body2regular,
                    color = textSecondary
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedContainerColor = surfaceHighest,
                unfocusedContainerColor = surfaceHighest
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderCommentsPreview() {
    LazyPizzaTheme {
        OrderComments(
            comment = "",
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}