package com.example.habitat.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.habitat.presentation.ScreensRoutes

enum class BottomNavigationBarItems(val icon: ImageVector, val route: String) {
    HOME(Icons.Default.Home, ScreensRoutes.Home.route),
    ADD_HABIT(Icons.Default.AddCircle, ScreensRoutes.AddHabit.route),
    STATISTICS(Icons.Default.BarChart, ScreensRoutes.Statistics.route)
}