package com.example.lazypizza.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.lazypizza.MainAction
import com.example.lazypizza.MainState
import com.example.lazypizza.core.presentation.datasystem.LazyPizzaBaseScreen
import com.example.lazypizza.core.presentation.datasystem.NavigationMenu
import com.example.lazypizza.core.presentation.datasystem.navbar.LazyPizzaMenuBar
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.core.presentation.util.ObserveAsEvents
import com.example.lazypizza.core.presentation.util.SnackbarController
import com.example.lazypizza.feature.authentication.AuthenticationScreenRoot
import com.example.lazypizza.feature.cart.CartScreenRoot
import com.example.lazypizza.feature.cart.components.CartTopBar
import com.example.lazypizza.feature.history.HistoryScreenRoot
import com.example.lazypizza.feature.history.components.HistoryTopBar
import com.example.lazypizza.feature.maincatalog.MainCatalogScreenRoot
import com.example.lazypizza.feature.maincatalog.components.LogoutDialog
import com.example.lazypizza.feature.maincatalog.components.MainCatalogTopBar
import com.example.lazypizza.feature.productdetails.ProductDetailScreenRoot
import com.example.lazypizza.feature.productdetails.components.ProductDetailTopBar
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import timber.log.Timber


@Serializable
data object MainCatalog : NavKey

@Serializable
data object History : NavKey

@Serializable
data object Cart : NavKey

@Serializable
data class ProductDetails(val id: String) : NavKey

@Serializable
data object AuthenticationScreen : NavKey


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    deviceConfiguration: DeviceConfiguration,
    state: MainState,
    onAction: MainAction.() -> Unit
) {

    val backStack = rememberNavBackStack(MainCatalog)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        LogoutDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            onConfirmation = {
                onAction(MainAction.OnLogoutAction)
                showDialog.value = false
            }
        )
    }

    ObserveAsEvents(
        SnackbarController.events,
        snackbarHostState
    ) { event ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                withDismissAction = event.onDismiss != null,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
    val suiteScaffoldState = rememberNavigationSuiteScaffoldState()

    val selectedMenu = when (backStack.lastOrNull()) {
        is MainCatalog -> NavigationMenu.HOME
        is Cart -> NavigationMenu.CART
        is History -> NavigationMenu.HISTORY
        // If it's a detail screen, the primary selection is still HOME
        is ProductDetails -> NavigationMenu.HOME
        else -> NavigationMenu.HOME // Default case
    }

    val hideMenu = backStack.lastOrNull() is ProductDetails ||
            backStack.lastOrNull() is AuthenticationScreen
    LaunchedEffect(hideMenu) {
        if (hideMenu) {
            suiteScaffoldState.hide()
        } else {
            suiteScaffoldState.show()
        }
    }

    LazyPizzaMenuBar(
        state = suiteScaffoldState,
        selectedMenu = selectedMenu,
        onNavigationMenuClick = { menu ->
            when (menu) {
                NavigationMenu.HOME -> {
                    backStack.clear()
                    backStack.add(MainCatalog)
                }

                NavigationMenu.CART -> {
                    backStack.clear()
                    backStack.add(Cart)
                }

                NavigationMenu.HISTORY -> {
                    backStack.clear()
                    backStack.add(History)
                }
            }
        },
        badgeCounts = mapOf(
            NavigationMenu.CART to state.totalCartItem,
            NavigationMenu.HISTORY to 0
        ),
        deviceConfiguration = deviceConfiguration
    ) {

        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            onBack = {
                backStack.removeLastOrNull()
            },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
                rememberSceneSetupNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<AuthenticationScreen> {
                    AuthenticationScreenRoot {
                        Timber.e("logout should work right about now")
                        backStack.removeLastOrNull()
                    }
                }
                entry<MainCatalog> {
                    LazyPizzaBaseScreen(snackbarHostState = snackbarHostState, topAppBar = {
                        MainCatalogTopBar(isLoggedIn = state.userLoggedIn) {
                            if (state.userLoggedIn) {
                                showDialog.value = true
                            } else {
                                backStack.add(AuthenticationScreen)
                            }
                        }
                    }) {
                        MainCatalogScreenRoot(deviceConfiguration = deviceConfiguration) {
                            backStack.add(ProductDetails(it))
                        }

                    }
                }

                entry<ProductDetails> {
                    LazyPizzaBaseScreen(snackbarHostState = snackbarHostState, topAppBar = {
                        ProductDetailTopBar(
                            onBackClicked = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }) {
                        ProductDetailScreenRoot(
                            deviceConfiguration = deviceConfiguration,
                            pizzaName = it.id
                        )
                    }
                }

                entry<Cart> {
                    LazyPizzaBaseScreen(snackbarHostState = snackbarHostState, topAppBar = {
                        CartTopBar(deviceConfiguration = deviceConfiguration)
                    }) {
                        CartScreenRoot(deviceConfiguration = deviceConfiguration)
                    }
                }

                entry<History> {
                    LazyPizzaBaseScreen(snackbarHostState = snackbarHostState, topAppBar = {
                        HistoryTopBar(deviceConfiguration = deviceConfiguration)
                    }) {
                        HistoryScreenRoot(deviceConfiguration = deviceConfiguration) {
                            backStack.add(AuthenticationScreen)
                        }
                    }
                }
            }
        )
    }
}