package com.example.lazypizza.feature.ordercheckout.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.outline
import com.example.lazypizza.core.presentation.theme.primary
import com.example.lazypizza.core.presentation.theme.surfaceHigher
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.feature.ordercheckout.TimeValidationResult
import com.example.lazypizza.feature.ordercheckout.util.TimeInputState
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    hours: Int,
    minutes: Int,
    validationResult: TimeValidationResult?,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismiss by rememberUpdatedState(onDismissRequest)
    val confirm by rememberUpdatedState(onConfirmClick)
    val hourChange by rememberUpdatedState(onHourChange)
    val minuteChange by rememberUpdatedState(onMinuteChange)

    Dialog(
        onDismissRequest = dismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        TimePickerDialogContent(
            hourText = hours.toString(),
            minuteText = minutes.toString(),
            validationResult = validationResult,
            onHourChange = hourChange,
            onMinuteChange = minuteChange,
            onDismissRequest = dismiss,
            onConfirmClick = confirm,
            modifier = modifier
        )
    }
}
@Composable
private fun TimePickerDialogContent(
    hourText: String,
    minuteText: String,
    validationResult: TimeValidationResult?,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hourFocusRequester = remember { FocusRequester() }
    val minuteFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        hourFocusRequester.requestFocus()
    }

    Surface(
        modifier = modifier
            .width(264.dp)
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
        ) {
            Text(
                text = "SELECT TIME",
                style = MaterialTheme.typography.labelMedium,
                color = textSecondary,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TimeNumberField(
                    value = hourText,
                    onValueChange = onHourChange,
                    modifier = Modifier.weight(1f),
                    focusRequester = hourFocusRequester,
                    imeAction = ImeAction.Next,
                    onImeAction = {
                        minuteFocusRequester.requestFocus()
                    }
                )

                Text(
                    text = ":",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )

                TimeNumberField(
                    value = minuteText,
                    onValueChange = onMinuteChange,
                    modifier = Modifier.weight(1f),
                    focusRequester = minuteFocusRequester,
                    imeAction = ImeAction.Done,
                    onImeAction = {
                         onConfirmClick()
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimeNumberLabel(
                    text = "Hour"
                )

                Spacer(Modifier.width(8.dp))

                TimeNumberLabel(
                    text = "Minute"
                )
            }

            when (validationResult) {
                TimeValidationResult.OutsideWorkingHours -> {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Pickup available between 10:15 and 21:45",
                        style = MaterialTheme.customTypography.body2regular,
                        color = primary,
                    )
                }

                TimeValidationResult.TooEarlyFromNow -> {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Pickup is possible at least 15 minutes from the current time",
                        style = MaterialTheme.customTypography.body2regular,
                        color = textPrimary,
                    )
                }

                TimeValidationResult.Ok, null -> Unit
            }

            Spacer(Modifier.height(16.dp))

            DialogConfirmBar(
                onDismiss = onDismissRequest,
                onConfirm = onConfirmClick
            )
        }
    }
}

@Composable
private fun TimeNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: (() -> Unit)? = null
) {
    val noHandleColors = remember {
        TextSelectionColors(
            handleColor = Color.Transparent,
            backgroundColor = Color.Transparent
        )
    }
    var isFocused by remember { mutableStateOf(false) }

    val borderColor = if (isFocused) primary else outline
    val containerColor = if (isFocused) surfaceHigher else surfaceHigher

    val keyboardActions = remember(imeAction, onImeAction) {
        if (onImeAction == null) {
            KeyboardActions.Default
        } else {
            when (imeAction) {
                ImeAction.Next -> KeyboardActions(onNext = { onImeAction() })
                ImeAction.Done -> KeyboardActions(onDone = { onImeAction() })
                else -> KeyboardActions.Default
            }
        }
    }
    CompositionLocalProvider(LocalTextSelectionColors provides noHandleColors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(textSecondary),
            modifier = modifier
                .width(104.dp)
                .height(72.dp)
                .then(
                    if (focusRequester != null) Modifier.focusRequester(focusRequester)
                    else Modifier
                )
                .onFocusChanged { isFocused = it.isFocused },
            decorationBox = { innerTextField ->
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = containerColor,
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "00",
                                style = MaterialTheme.typography.titleLarge,
                                color = textSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

@Composable
private fun TimeNumberLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.customTypography.body2regular,
        color = textSecondary,
        textAlign = TextAlign.Start,
        modifier = modifier.width(104.dp)
    )
}

@Preview()
@Composable
private fun TimePickerDialogPreview() {
    LazyPizzaTheme {
        TimePickerDialogContent(
            hourText = "23",
            minuteText = "00",
            validationResult = TimeValidationResult.OutsideWorkingHours,
            onHourChange = {},
            onMinuteChange = {},
            onDismissRequest = {},
            onConfirmClick = {}
        )
    }
}