package com.example.lazypizza.feature.authentication

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaHollowButton
import com.example.lazypizza.core.presentation.datasystem.buttons.LazyPizzaPrimaryButton
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.feature.authentication.components.OtpInputField
import com.example.lazypizza.feature.authentication.components.PhoneNumberTextField
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun AuthenticationScreenRoot(
    viewModel: AuthenticationViewModel = koinViewModel(),
    onLogoutAction: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusRequesters = remember {
        List(6) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    ObserveAsEvents(
        flow = viewModel.event
    ) { event ->
        when (event) {
            is com.example.lazypizza.MainEvents.OnLoginSuccessful -> {
                onLogoutAction()
            }
        }
    }

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if (allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }
    AuthenticationScreen(
        state,
        focusRequesters,
        onAction = viewModel::onAction
    )
}

@Composable
fun AuthenticationScreen(
    state: AuthenticationState = AuthenticationState(),
    focusRequesters: List<FocusRequester>,
    onAction: (AuthenticationAction) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val auth = FirebaseAuth.getInstance()

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("TAG", "onVerificationCompleted:$credential")
//            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
//            storedVerificationId = verificationId
//            resendToken = token
        }
    }

    val activity = LocalActivity.current as Activity
    val options by lazy {
        PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(state.phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 380.dp)
                .padding(horizontal = 16.dp)
                //on tap, clear focus from any active element
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome to Lazy Pizza",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (state.phoneNumberConfirmed) "Enter code" else "Enter your phone number",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 20.dp),
                style = MaterialTheme.customTypography.body3regular.copy(color = textSecondary),
                textAlign = TextAlign.Center
            )

            PhoneNumberTextField(
                value = state.phoneNumber,
                onValueChange = {
                    onAction(AuthenticationAction.OnEnterPhoneNumber(it))
                }
            )

            AnimatedVisibility(visible = state.phoneNumberConfirmed) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    state.code.forEachIndexed { index, number ->
                        OtpInputField(
                            number = number,
                            focusRequester = focusRequesters[index],
                            onFocusChanged = { isFocused ->
                                if (isFocused) {
                                    onAction(AuthenticationAction.OnChangeFieldFocused(index))
                                }
                            },
                            onNumberChanged = { newNumber ->
                                onAction(
                                    AuthenticationAction.OnEnterCodeNumber(
                                        newNumber,
                                        index
                                    )
                                )
                            },
                            onKeyboardBack = {
                                onAction(AuthenticationAction.OnKeyboardBack)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }

            AnimatedVisibility(state.invalidPhoneNumber) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0f),
                    text = "Incorrect code. Please try again",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            LazyPizzaPrimaryButton(
                buttonText = if (state.phoneNumberConfirmed) "Confirm" else "Continue",
                isEnabled = state.isValid ?: false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                PhoneAuthProvider.verifyPhoneNumber(options)
                onAction(AuthenticationAction.OnContinueClicked)
            }

            LazyPizzaHollowButton(
                modifier = Modifier
                    .fillMaxWidth(),
                buttonText = "Continue without signing in"
            )

            AnimatedVisibility(state.newCodeTimer > 0) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "You can request a new code in %02d:%02d"
                        .format(state.newCodeTimer / 60, state.newCodeTimer % 60),
                    style = MaterialTheme.customTypography.body3regular,
                    color = textSecondary,
                    textAlign = TextAlign.Center
                )
            }
            AnimatedVisibility(state.newCodeTimer == 0 && state.phoneNumberConfirmed) {
                LazyPizzaHollowButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttonText = "Resend code",
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AuthenticationScreenPreview() {
    AuthenticationScreen(
        state = AuthenticationState(),
        focusRequesters = emptyList()
    )
}