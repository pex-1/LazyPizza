package com.example.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lazypizza.core.presentation.theme.LazyPizzaTheme
import com.example.lazypizza.core.presentation.util.DeviceConfiguration
import com.example.lazypizza.navigation.NavigationRoot
import com.google.firebase.FirebaseApp
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)

        setContent {
            LazyPizzaTheme {
                val state by mainViewModel.state.collectAsStateWithLifecycle()
                val windowSizeClass = calculateWindowSizeClass(this)
                val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
                NavigationRoot(
                    deviceConfiguration = deviceConfiguration,
                    state = state,
                    onAction = mainViewModel::onAction
                )
            }
        }
    }
}