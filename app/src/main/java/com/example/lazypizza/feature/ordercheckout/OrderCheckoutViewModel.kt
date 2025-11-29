package com.example.lazypizza.feature.ordercheckout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.core.data.FirebaseResult
import com.example.lazypizza.core.domain.repository.CartRepository
import com.example.lazypizza.core.domain.repository.ProductsRepository
import com.example.lazypizza.core.domain.userdata.UserData
import com.example.lazypizza.core.model.CartItemUI
import com.example.lazypizza.core.model.ProductType
import com.example.lazypizza.core.model.identityKey
import com.example.lazypizza.core.model.toCartItem
import com.example.lazypizza.core.presentation.util.SnackbarAction
import com.example.lazypizza.core.presentation.util.SnackbarController
import com.example.lazypizza.core.presentation.util.SnackbarEvent
import com.example.lazypizza.feature.cart.CartViewModel.Companion.MAX_RECOMMENDED_ITEMS_TO_SHOW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

class OrderCheckoutViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductsRepository,
    private val userData: UserData
) : ViewModel() {
    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state.asStateFlow()

    fun onAction(action: OrderCheckoutAction) {
        when (action) {
            is OrderCheckoutAction.OnRadioButtonSelected -> {
                _state.update {
                    it.copy(selectedOption = action.id, showDatePicker = true)
                }
            }

            OrderCheckoutAction.OnCloseDatePicker -> {
                _state.update {
                    it.copy(showDatePicker = false)
                }
            }

            is OrderCheckoutAction.OnDeleteOrderCheckoutItemClick -> {
                onDeleteProductClick(action.reference)
            }

            is OrderCheckoutAction.OnOrderCheckoutItemQuantityChange -> {
                onCartItemQuantityChange(action.reference, action.quantity)
            }

            is OrderCheckoutAction.OnCommentChange -> {
                _state.update { it.copy(comment = action.comment) }
                viewModelScope.launch {
                    userData.clear()
                }
            }

            is OrderCheckoutAction.OnDateSelected -> {
                _state.update {
                    it.copy(showTimePicker = true, showDatePicker = false, selectedDate = action.date)
                }
            }

            is OrderCheckoutAction.OnCheckoutClick -> {}
            is OrderCheckoutAction.OnCloseTimePicker -> {
                _state.update { it.copy(showTimePicker = false) }
            }
            is OrderCheckoutAction.OnTimePickerTimeChange -> {
                _state.update {
                    it.copy(
                        selectedHours = action.hour ?: it.selectedHours,
                        selectedMinutes = action.minute ?: it.selectedMinutes
                    )
                }
                if (state.value.selectedHours >= 10 && state.value.selectedHours <= 21) {
                    _state.update { it.copy(validationResult = TimeValidationResult.Ok) }
                } else {
                    _state.update { it.copy(validationResult = TimeValidationResult.OutsideWorkingHours) }
                }
            }

            OrderCheckoutAction.OnTimePickerSubmit -> {
                val selectedDate = state.value.selectedDate
                val time = "%2d:%2d".format(state.value.selectedHours, state.value.selectedMinutes)
                _state.update { it.copy(showTimePicker = false,
                    earliestPickupTime = "%s %d, %s".format(selectedDate?.month, selectedDate?.dayOfMonth, time))}
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val allDrinksAndSnacks = loadAllDrinksAndSnacks().shuffled()
            _state.update {
                it.copy(
                    recommendedItems = allDrinksAndSnacks.take(
                        MAX_RECOMMENDED_ITEMS_TO_SHOW
                    )
                )
            }

            val cartId = userData.getCartId().first()
            cartId?.let {
                cartRepository.getCart(it).collect { response ->
                    when (response) {
                        is FirebaseResult.Success -> {
                            val cartItems = response.data

                            if (cartItems.isEmpty()) {
                                _state.update { state ->
                                    state.copy(
                                        items = emptyList(),
                                        isLoading = false,
                                        recommendedItems = allDrinksAndSnacks.take(
                                            MAX_RECOMMENDED_ITEMS_TO_SHOW
                                        )
                                    )
                                }
                                return@collect
                            }

                            val products = productRepository.getProductsByReference(
                                cartItems.map { item -> item.reference }
                            ).first()

                            val cartItemsUI = products.mapIndexed { index, product ->
                                val cartItem = cartItems[index]
                                if (product.type == ProductType.PIZZA) {
                                    val extraToppings = productRepository.getProductsByReference(
                                        cartItem.extraToppings.map { it.reference }
                                    ).first()

                                    val formattedExtras = extraToppings.map { topping ->
                                        val qty = cartItem.extraToppings.find {
                                            it.reference.substringAfterLast("/") == topping.id
                                        }?.quantity ?: 0
                                        "${qty}x ${topping.name}"
                                    }

                                    val extraCost = extraToppings.sumOf { topping ->
                                        val qty = cartItem.extraToppings.find {
                                            it.reference.substringAfterLast("/") == topping.id
                                        }?.quantity ?: 0
                                        topping.price * qty
                                    }

                                    CartItemUI(
                                        reference = cartItem.identityKey(),
                                        quantity = cartItem.quantity,
                                        image = product.image,
                                        name = product.name,
                                        price = product.price + extraCost,
                                        type = product.type,
                                        extraToppingsRelated = formattedExtras
                                    )
                                } else {
                                    CartItemUI(
                                        reference = cartItem.reference,
                                        quantity = cartItem.quantity,
                                        image = product.image,
                                        name = product.name,
                                        price = product.price,
                                        type = product.type
                                    )
                                }
                            }

                            _state.update { state ->
                                state.copy(
                                    cartId = it,
                                    items = cartItemsUI,
                                    isLoading = false,
                                    recommendedItems = getFilteredRecommendedItems(
                                        allDrinksAndSnacks,
                                        cartItemsUI
                                    )
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun onCartItemQuantityChange(reference: String, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            try {
                val current = _state.value
                val existing = current.items.find { it.reference == reference }
                val recommended = current.recommendedItems.find { it.reference == reference }

                when {
                    // Add new recommended item
                    existing == null && recommended != null && quantity > 0 -> {
                        val newItem = recommended.copy(quantity = quantity)
                        val newCart = (current.items + newItem).map { it.toCartItem() }

                        when (val result = cartRepository.updateCart(current.cartId, newCart)) {
                            is FirebaseResult.Success -> {
                                _state.update {
                                    it.copy(
                                        items = current.items + newItem,
                                        recommendedItems = current.recommendedItems.filterNot { it.reference == reference },
                                        isLoading = false
                                    )
                                }
                                SnackbarController.sendEvent(
                                    SnackbarEvent(
                                        "${recommended.name} added",
                                        SnackbarAction("OK") {})
                                )
                            }

                            is FirebaseResult.Error -> {
                                _state.update { it.copy(isLoading = false) }
                            }
                        }
                    }

                    // Remove item
                    existing != null && quantity == 0 -> {
                        val updated = current.items.filterNot { it.reference == reference }
                            .map { it.toCartItem() }
                        cartRepository.updateCart(current.cartId, updated)
                        _state.update { it.copy(isLoading = false) }

                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                "${existing.name} removed from cart",
                                SnackbarAction("OK") {})
                        )
                    }

                    // Update quantity
                    existing != null && quantity > 0 -> {
                        val updatedCartItems = current.items.map {
                            if (it.reference == reference) it.copy(quantity = quantity) else it
                        }
                        val updated = updatedCartItems.map { it.toCartItem() }
                        cartRepository.updateCart(current.cartId, updated)
                        _state.update { it.copy(items = updatedCartItems, isLoading = false) }
                    }

                    else -> _state.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onDeleteProductClick(reference: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val current = _state.value
            val itemName = current.items.find { it.reference == reference }?.name

            val updated =
                current.items.filterNot { it.reference == reference }.map { it.toCartItem() }
            cartRepository.updateCart(current.cartId, updated)

            _state.update { it.copy(isLoading = false) }

            SnackbarController.sendEvent(
                SnackbarEvent("$itemName removed from cart", SnackbarAction("OK") {})
            )
        }
    }

    private suspend fun loadAllDrinksAndSnacks(): List<CartItemUI> = try {
        val allProducts = productRepository.getAllProducts().first()

        allProducts.filter {
            it.type == ProductType.DRINK || it.type == ProductType.SAUCE
        }.map { product ->
            CartItemUI(
                reference = "${product.type.name.lowercase()}/${product.id}",
                quantity = 1,
                image = product.image,
                name = product.name,
                price = product.price,
                type = product.type
            )
        }
    } catch (e: Exception) {
        emptyList()
    }


    private fun getFilteredRecommendedItems(
        allDrinksAndSnacks: List<CartItemUI>,
        cartItems: List<CartItemUI>
    ): List<CartItemUI> {
        val currentRefs = cartItems.map { it.reference }
        return allDrinksAndSnacks.filter { it.reference !in currentRefs }.take(10)
    }
}
