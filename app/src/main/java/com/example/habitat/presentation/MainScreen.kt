package com.example.habitat.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.rememberNavController
import com.example.habitat.presentation.commonComponents.BottomNavigationBar

@Composable
fun MainScreen(

) {
    val navHostController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars),
        bottomBar = {
            BottomNavigationBar(
                navController = navHostController
            )
        }
    ) { innerPadding ->
        NavigationHost(
            modifier = Modifier
                .padding(innerPadding),
            navHostController = navHostController
        )
    }

    StatusBarProtection()
}

@Composable
private fun StatusBarProtection(
    color: Color = MaterialTheme.colorScheme.background,
    heightProvider: () -> Float = calculateStatusBarHeight(),
) {

    Canvas(Modifier.fillMaxSize()) {
        val calculatedHeight = heightProvider()
        drawRect(
            color = color,
            size = Size(size.width, calculatedHeight),
        )
    }
}

@Composable
fun calculateStatusBarHeight(): () -> Float {
    val statusBars = WindowInsets.statusBars
    val density = LocalDensity.current
    return { statusBars.getTop(density).toFloat() }
}