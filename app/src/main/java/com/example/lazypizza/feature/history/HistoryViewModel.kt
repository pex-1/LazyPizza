package com.example.lazypizza.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.core.domain.userdata.UserData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val userData: UserData,
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    private val _event = Channel<HistoryEvent>()
    val event = _event.receiveAsFlow()

    init {
        userData.getIsLoggedIn()
            .onEach { isLoggedIn ->
                _state.update {
                    it.copy(userLoggedIn = isLoggedIn)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }
}