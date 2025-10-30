package com.example.lazypizza.feature.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.core.data.FirebaseResult
import com.example.lazypizza.core.domain.repository.CartRepository
import com.example.lazypizza.core.domain.repository.ProductsRepository
import com.example.lazypizza.core.domain.userdata.UserData
import com.example.lazypizza.core.model.CartItem
import com.example.lazypizza.core.model.ExtraTopping
import com.example.lazypizza.core.presentation.util.SnackbarAction
import com.example.lazypizza.core.presentation.util.SnackbarController
import com.example.lazypizza.core.presentation.util.SnackbarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class ProductDetailViewModel(
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository,
    private val userData: UserData,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()

    private val _event = Channel<ProductDetailEvent>()
    val event = _event.receiveAsFlow()

    fun init(pizzaName: String) {
        viewModelScope.launch {
            if (_state.value.selectedPizza != null) return@launch

            combine(
                productsRepository.getPizzaByName(pizzaName),
                productsRepository.getExtraToppingsFlow()
            ) { pizza, toppings ->
                _state.update {
                    it.copy(
                        selectedPizza = pizza,
                        listExtraToppings = toppings.map { product ->
                            ToppingsUI(
                                id = product.id,
                                name = product.name,
                                image = product.image,
                                price = product.price,
                                quantity = 0
                            )
                        }
                    )
                }
            }.firstOrNull()

        }
    }

    fun onAction(action: ProductDetailAction) {
        when (action) {
            is ProductDetailAction.OnToppingPlus -> onToppingPlus(action.toppingsUI)
            is ProductDetailAction.OnToppingMinus -> onToppingMinus(action.toppingsUI)
            is ProductDetailAction.OnAddToCartButtonClick -> onAddToCartButtonClick()
        }
    }

    private fun onAddToCartButtonClick() {
        _state.update {
            it.copy(isUpdatingCart = true)
        }
        val extraToppings = _state.value.listExtraToppings
            .filterNot { it.quantity == 0 }
            .map {
                ExtraTopping(
                    reference = "${it.type.name.lowercase()}/${it.id}",
                    quantity = it.quantity
                )
            }
            .toMutableList()

        val pizzaItem = _state.value.selectedPizza?.let {
            CartItem(
                reference = "${it.type.name.lowercase()}/${it.id}",
                quantity = 1,
                extraToppings = extraToppings
            )
        } ?: CartItem()

        viewModelScope.launch(context = Dispatchers.IO) {
            // Used first because if we use collect the flow with be always open and receiving value if it change
            val cartId = userData.getCartId().first()

            // If cart is null then we need to create it with the pizza item only
            if (cartId == null) {
                val createCartResponse = cartRepository.createCart(items = listOf(pizzaItem))
                when (createCartResponse) {
                    is FirebaseResult.Success -> {
                        userData.setCardId(createCartResponse.data)
                        _event.trySend(ProductDetailEvent.OnCartSuccessfullySaved)
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = "${_state.value.selectedPizza?.name} added to cart",
                                action = SnackbarAction(
                                    name = "OK",
                                    action = {}
                                )
                            )
                        )
                    }

                    is FirebaseResult.Error -> {
                        // Here the message could be null. Didn't know which message show if it's null
                        _event.trySend(
                            ProductDetailEvent.Error(
                                createCartResponse.exception.message
                                    ?: "Error creating the cart. Try again later"
                            )
                        )
                    }
                }
            } else {
                // Here we need to get the cart and add the new pizza item. Again need to use first because if not, the flow will be opened
                val getCartResponse = cartRepository.getCart(cartId).first()
                when (getCartResponse) {
                    is FirebaseResult.Success -> {
                        val updateCartResponse = cartRepository.updateCart(
                            cartId = cartId,
                            items = getCartResponse.data + pizzaItem
                        )
                        when (updateCartResponse) {
                            is FirebaseResult.Success -> {
                                _event.trySend(ProductDetailEvent.OnCartSuccessfullySaved)
                                SnackbarController.sendEvent(
                                    event = SnackbarEvent(
                                        message = "${_state.value.selectedPizza?.name} added to cart",
                                        action = SnackbarAction(
                                            name = "OK",
                                            action = {}
                                        )
                                    )
                                )
                            }

                            is FirebaseResult.Error -> {
                                _event.trySend(
                                    ProductDetailEvent.Error(
                                        updateCartResponse.exception.message
                                            ?: "Error creating the cart. Try again later"
                                    )
                                )
                            }
                        }
                    }

                    is FirebaseResult.Error -> {
                        _event.trySend(
                            ProductDetailEvent.Error(
                                getCartResponse.exception.message
                                    ?: "Error creating the cart. Try again later"
                            )
                        )
                    }
                }
            }

            _state.update {
                it.copy(isUpdatingCart = false)
            }
        }
    }

    fun onToppingPlus(toppingsUI: ToppingsUI) {
        val updatedTopping = _state.value.listExtraToppings.map {
            if (it == toppingsUI) {
                it.copy(quantity = it.quantity + 1)
            } else {
                it
            }
        }
        val updatedPizza = _state.value.selectedPizza?.let {
            it.copy(
                price = it.price + toppingsUI.price
            )
        }

        _state.update {
            it.copy(
                selectedPizza = updatedPizza,
                listExtraToppings = updatedTopping
            )
        }
    }

    fun onToppingMinus(toppingsUI: ToppingsUI) {
        val updatedTopping = _state.value.listExtraToppings.map {
            if (it == toppingsUI) {
                it.copy(quantity = it.quantity - 1)
            } else {
                it
            }
        }

        val updatedPizza = _state.value.selectedPizza?.let {
            it.copy(
                price = it.price - toppingsUI.price
            )
        }

        _state.update {
            it.copy(
                selectedPizza = updatedPizza,
                listExtraToppings = updatedTopping
            )
        }
    }
}