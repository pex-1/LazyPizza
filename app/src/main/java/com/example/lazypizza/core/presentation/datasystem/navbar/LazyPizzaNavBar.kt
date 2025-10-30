package com.example.lazypizza.core.presentation.datasystem.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldState
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.datasystem.NavigationMenu
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.textOnPrimary
import com.example.lazypizza.core.presentation.theme.textPrimary
import com.example.lazypizza.core.presentation.theme.textSecondary
import com.example.lazypizza.core.presentation.util.DeviceConfiguration

@Composable
fun LazyPizzaMenuBar(
    state: NavigationSuiteScaffoldState,
    selectedMenu: NavigationMenu?,
    onNavigationMenuClick: (NavigationMenu) -> Unit,
    modifier: Modifier = Modifier,
    badgeCounts: Map<NavigationMenu, Int> = emptyMap(),
    deviceConfiguration: DeviceConfiguration,
    content: @Composable () -> Unit
) {

    val myNavigationSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = textPrimary,
            unselectedIconColor = textSecondary,
            unselectedTextColor = textSecondary,
        ), navigationRailItemColors = NavigationRailItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = textPrimary,
            unselectedIconColor = textSecondary,
            unselectedTextColor = textSecondary
        )
    )

    NavigationSuiteScaffold(
        modifier = modifier,
        state = state,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationRailContainerColor = MaterialTheme.colorScheme.background,
            navigationBarContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
        navigationSuiteItems = {
            NavigationMenu.entries.forEachIndexed { index, screen ->
                val itemCount = badgeCounts[screen] ?: 0
                val isTablet = deviceConfiguration.isLargeScreen()
                val isTabletPortrait = deviceConfiguration == DeviceConfiguration.TABLET_PORTRAIT
                val isTabletLandscape = deviceConfiguration == DeviceConfiguration.TABLET_LANDSCAPE
                val isSelected = selectedMenu == screen
                item(
                    modifier = Modifier.padding(
                        start = if (!isTablet && index == 0) 50.dp else 0.dp,
                        end = if (!isTablet && index == NavigationMenu.entries.size - 1) 50.dp else 0.dp,
                        top = when {
                            isTabletLandscape && index == 0 -> 200.dp
                            isTabletPortrait && index == 0 -> 350.dp
                            else -> 0.dp
                        }
                    ),
                    selected = isSelected,
                    colors = myNavigationSuiteItemColors,
                    onClick = {},
                    icon = {
                        BadgedBox(
                            badge = {
                                if (itemCount > 0) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = textOnPrimary
                                    ) {
                                        Text(
                                            text = "$itemCount",
                                            style = MaterialTheme.typography.labelSmall,
                                        )
                                    }
                                }
                            }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable(
                                        interactionSource = null, indication = null
                                    ) {
                                        onNavigationMenuClick(screen)
                                    }
                                    .then(
                                        if (isSelected) Modifier.background(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                            shape = CircleShape
                                        ) else Modifier
                                    )
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(screen.icon),
                                    contentDescription = screen.title
                                )
                            }
                        }
                    },
                    label = {
                        Text(
                            text = screen.title,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    ) {
        content()
    }
}

@Preview()
@Composable
private fun LazyPizzaMenuBarPreview() {
    LazyPizzaTheme {
        LazyPizzaMenuBar(
            state = rememberNavigationSuiteScaffoldState(),
            selectedMenu = NavigationMenu.CART,
            onNavigationMenuClick = {},
            content = {},
            deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT
        )
    }
}