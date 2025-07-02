package com.example.habitat.presentation.screens.mainScreens.homeScreen

import com.example.habitat.domain.entities.Habit
import com.example.habitat.helpers.TimeHelper

data class HomeUiState(
    val habits: List<Habit> = emptyList(),
    val selectedDateMillis: Long = System.currentTimeMillis()
)
