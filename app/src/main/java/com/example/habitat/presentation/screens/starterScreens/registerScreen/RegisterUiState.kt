package com.example.habitat.presentation.screens.starterScreens.registerScreen

import com.example.habitat.enums.HabitsCategories

data class RegisterUiState(
    val userName: String = "",
    val userAge: Int = 0,
    val userLivePlace: String = "",
    val selectedHabits: List<HabitsCategories> = emptyList(),
)
