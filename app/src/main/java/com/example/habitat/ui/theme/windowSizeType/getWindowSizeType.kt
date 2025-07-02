package com.example.habitat.ui.theme.windowSizeType

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun getWindowSizeType(): WindowSizeTypes {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    return when {
        screenWidth < 600 -> WindowSizeTypes.PHONE
        screenWidth < 840 -> WindowSizeTypes.TABLET
        else -> WindowSizeTypes.EXPANDED
    }
}