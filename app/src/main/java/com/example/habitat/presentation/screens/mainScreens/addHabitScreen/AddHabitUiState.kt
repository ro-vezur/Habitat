package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.entities.User
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.source.Source
import java.time.DayOfWeek

data class AddHabitUiState(
    val currentUser: User = User(),
    val habitDescription: String = "",
    val habitCategory: HabitsCategories? = null,
    val selectedDays: List<DayOfWeek> = emptyList(),
    val repeatEveryWeek: Boolean = false,
    val remindTimeSelectedHours: Int = 0,
    val remindTimeSelectedMinutes: Int = 0,
    val generateHabitsPrompt: String = "",
    val generatedHabitsSource: Source<List<Habit>> = Source.Unknown()
)
