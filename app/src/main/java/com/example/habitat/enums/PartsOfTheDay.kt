package com.example.habitat.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

enum class PartsOfTheDay(val title: String, val icon: ImageVector) {
    MORNING("Morning", Icons.Filled.WbSunny),
    NOON("Noon", Icons.Filled.Light),
    EVENING("Evening", Icons.Filled.NightlightRound)
}