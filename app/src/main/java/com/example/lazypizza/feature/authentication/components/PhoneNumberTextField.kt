package com.example.lazypizza.feature.authentication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary

@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        visualTransformation = PhoneMaskVisualTransformation(),
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = "+385 91/2317-660",
                style = MaterialTheme.customTypography.body2regular.copy(color = textSecondary)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = textSecondary
        ),
        textStyle = MaterialTheme.customTypography.body2regular
    )
}

class PhoneMaskVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        // keep only digits and a single leading +
        val raw = text.text
            .filter { it.isDigit() || it == '+' }
            .let {
                if (it.startsWith("+")) {
                    // remove any additional plus signs that might appear later
                    it.first() + it.drop(1).filter { ch -> ch.isDigit() }
                } else {
                    it.filter { ch -> ch.isDigit() }
                }
            }

        // build formatted: +385 99/2448-739
        val formatted = buildString {
            raw.forEachIndexed { index, c ->
                append(c)
                when (index) {
                    3 -> append(' ')  // after +385
                    5 -> append('/')  // after +385 99
                    9 -> append('-')  // after +385 99/2448
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            // original -> transformed
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 5 -> offset + 1   // one added (space)
                    offset <= 9 -> offset + 2   // space + slash
                    else -> offset + 3          // space + slash + dash
                }
            }

            // transformed -> original
            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 6 -> offset - 1   // map through the space
                    offset <= 11 -> offset - 2  // map through space+slash
                    else -> offset - 3          // map through space+slash+dash
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneNumberTextFieldPreview() {
    LazyPizzaTheme {
        PhoneNumberTextField()
    }
}

