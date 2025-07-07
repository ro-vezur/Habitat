package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import com.example.habitat.enums.HabitsCategories
import java.time.DayOfWeek

data class AddHabitUiState(
    val habitDescription: String = "",
    val habitCategory: HabitsCategories? = null,
    val selectedDays: List<DayOfWeek> = emptyList(),
    val repeatEveryWeek: Boolean = false,
    val remindTimeSelectedHours: Int = 0,
    val remindTimeSelectedMinutes: Int = 0,
)
