package com.example.lazypizza.feature.maincatalog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.R
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.Logo
import com.example.lazypizza.core.presentation.theme.Logout
import com.example.lazypizza.core.presentation.theme.Phone
import com.example.lazypizza.core.presentation.theme.User
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCatalogTopBar(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean = false,
    onAuthClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .scale(1.8f),
                    imageVector = Logo,
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(R.string.pizza_icon)
                )

                Text(
                    text = stringResource(R.string.lazy_pizza),
                    style = MaterialTheme.customTypography.body3regular
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp),
                    imageVector = Phone,
                    contentDescription = stringResource(R.string.phone_icon),
                    tint = textSecondary
                )

                Text(
                    text = stringResource(R.string.app_bar_phone_number),
                    style = MaterialTheme.customTypography.body1regular,
                )

                val icon = if (isLoggedIn) Logout else User
                val tint = if (isLoggedIn) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.secondary

                Icon(
                    modifier = Modifier
                        .padding(start = 6.dp, end = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            onAuthClick()
                        }
                        .background(tint.copy(alpha = 0.08f))
                        .padding(8.dp),
                    imageVector = icon,
                    contentDescription = stringResource(R.string.logout_user_icon),
                    tint = tint
                )
            }
        }
    )
}

@Preview
@Composable
private fun AllProductsTopBarPreview() {
    LazyPizzaTheme {
        MainCatalogTopBar()
    }
}