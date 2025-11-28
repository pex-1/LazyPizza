package com.example.lazypizza.core.presentation.datasystem

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.lazypizza.core.presentation.theme.textOnPrimary
import com.example.lazypizza.core.presentation.theme.textPrimary

@Composable
fun LazyPizzaBaseScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentWindowInsets: WindowInsets = WindowInsets.safeDrawing,
    topAppBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = {
                    Snackbar(
                        it,
                        containerColor = textPrimary,
                        contentColor = textOnPrimary,
                        actionContentColor = textOnPrimary,
                        dismissActionContentColor = textOnPrimary,
                        actionColor = textOnPrimary,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            )
        },
        modifier = modifier,
        contentWindowInsets = contentWindowInsets,
        topBar = topAppBar,
        containerColor = containerColor,
    ) { innerPadding ->
        val view = LocalView.current

        Box(
            modifier = modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                ),
        ) {
            content()
        }

        SideEffect {
            val window = (view.context as? Activity)?.window
            if (!view.isInEditMode && window != null) {
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
                    true
            }
        }
    }
}