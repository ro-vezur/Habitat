package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import com.example.habitat.enums.HabitsCategories
import java.time.DayOfWeek

data class AddHabitUiState(
    val habitDescription: String = "",
    val selectedDays: List<DayOfWeek> = emptyList(),
    val habitCategory: HabitsCategories? = null,
)
