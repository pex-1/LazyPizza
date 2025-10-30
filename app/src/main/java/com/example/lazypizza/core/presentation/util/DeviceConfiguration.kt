package com.example.lazypizza.core.presentation.util

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP; // Simplified, removed foldable states

    fun isLargeScreen() = this == TABLET_PORTRAIT || this == TABLET_LANDSCAPE || this == DESKTOP

    companion object {
        // FIX: Change the parameter from WindowAdaptiveInfo to WindowSizeClass
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthSizeClass = windowSizeClass.widthSizeClass
            val heightSizeClass = windowSizeClass.heightSizeClass

            return when {
                // Phone Portrait
                widthSizeClass == WindowWidthSizeClass.Compact -> MOBILE_PORTRAIT

                // Phone Landscape
                widthSizeClass == WindowWidthSizeClass.Medium && heightSizeClass == WindowHeightSizeClass.Compact -> MOBILE_LANDSCAPE

                // Tablet Portrait
                widthSizeClass == WindowWidthSizeClass.Medium && heightSizeClass != WindowHeightSizeClass.Compact -> TABLET_PORTRAIT

                // Tablet Landscape
                widthSizeClass == WindowWidthSizeClass.Expanded && heightSizeClass != WindowHeightSizeClass.Compact -> TABLET_LANDSCAPE

                // Default case for very large screens
                else -> DESKTOP
            }
        }
    }
}
