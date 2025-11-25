package com.example.lazypizza.feature.maincatalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton

@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit= {},
    onConfirmation: () -> Unit= {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Column(
            modifier = Modifier
                .widthIn(360.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_log_out),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = { onDismissRequest() }
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                LazyPizzaPrimaryButton(
                    modifier = Modifier
                        .padding(8.dp),
                    buttonText = stringResource(R.string.log_out)
                ) {
                    onConfirmation()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoutConfirmationDialogPreview() {
    LogoutDialog()
}