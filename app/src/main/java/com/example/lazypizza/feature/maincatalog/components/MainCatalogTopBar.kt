package com.example.lazypizza.feature.maincatalog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.theme.Logo
import com.example.lazypizza.core.presentation.theme.Phone
import com.example.lazypizza.core.presentation.theme.customTypography
import com.example.lazypizza.core.presentation.theme.textSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCatalogTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .graphicsLayer(scaleX = 1.8f, scaleY = 1.8f),
                    imageVector = Logo,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Icon pizza"
                )

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = "LazyPizza",
                    style = MaterialTheme.customTypography.body3regular
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp),
                    imageVector = Phone,
                    contentDescription = "Icon phone",
                    tint = textSecondary
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "+1 (555) 321-7890",
                    style = MaterialTheme.customTypography.body1regular,
                )

                Spacer(modifier = Modifier.size(16.dp))
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