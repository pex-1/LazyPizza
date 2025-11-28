package com.example.lazypizza.feature.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.MainEvents
import com.example.lazypizza.core.domain.userdata.UserData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AuthenticationViewModel(
    private val userData: UserData,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state.asStateFlow()

    private val _event = Channel<MainEvents>()
    val event = _event.receiveAsFlow()

    fun onAction(action: AuthenticationAction) {
        when (action) {
            is AuthenticationAction.OnChangeFieldFocused -> {
                _state.update {
                    it.copy(
                        focusedIndex = action.index
                    )
                }
            }

            is AuthenticationAction.OnEnterCodeNumber -> {
                enterNumber(action.number, action.index)
            }

            AuthenticationAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update {
                    it.copy(
                        code = it.code.mapIndexed { index, number ->
                            if (index == previousIndex) {
                                null
                            } else {
                                number
                            }
                        },
                        focusedIndex = previousIndex
                    )
                }
            }

            AuthenticationAction.OnContinueClicked -> {
                if (state.value.code[0] != null) {
                    viewModelScope.launch {
                        userData.setIsLoggedIn(true)
                        _event.send(MainEvents.OnLoginSuccessful)
                    }

                } else {
                    startTimer()
                    _state.update {
                        it.copy(phoneNumberConfirmed = true)
                    }
                }
            }

            is AuthenticationAction.OnEnterPhoneNumber -> {
                _state.update {
                    val phoneNumberValid = action.number.length == 13
                    it.copy(phoneNumber = action.number, isValid = phoneNumberValid)
                }
            }
        }
    }


    fun startTimer() {
        viewModelScope.launch {
            (60 downTo 0).forEach { time ->
                _state.update {
                    it.copy(newCodeTimer = time)
                }
                delay(1000)
            }
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = state.value.code.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        _state.update {
            it.copy(
                code = newCode,
                focusedIndex = if (wasNumberRemoved || it.code.getOrNull(index) != null) {
                    it.focusedIndex
                } else {
                    getNextFocusedTextFieldIndex(
                        currentCode = it.code,
                        currentFocusedIndex = it.focusedIndex
                    )
                },
                isValid = if (newCode.none { it == null }) {
                    newCode.size == 6
                } else null
            )
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if (currentFocusedIndex == null) {
            return null
        }

        if (currentFocusedIndex == 5) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if (index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if (number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }
}