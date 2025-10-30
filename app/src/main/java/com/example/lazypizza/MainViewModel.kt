@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.lazypizza

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.core.data.FirebaseResult
import com.example.lazypizza.core.domain.repository.CartRepository
import com.example.lazypizza.core.domain.userdata.UserData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import timber.log.Timber

class MainViewModel(
    private val cartRepository: CartRepository,
    userData: UserData,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        userData
            .getCartId()
            .distinctUntilChanged()
            .flatMapLatest { cartId ->
                if (cartId == null) {
                    flowOf(0)
                } else {
                    cartRepository
                        .getCart(cartId)
                        .map { result ->
                            when (result) {
                                is FirebaseResult.Success -> {
                                    result.data.size
                                }
                                is FirebaseResult.Error -> 0
                            }
                        }
                }
            }
            .distinctUntilChanged()
            .onEach {
                _state.update { state ->
                    state.copy(totalCartItem = it)
                }
            }
            .launchIn(viewModelScope)
    }
}