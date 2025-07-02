package com.example.habitat.presentation.screens.starterScreens.registerScreen

import com.example.habitat.enums.HabitsCategories
import com.example.habitat.enums.PartsOfTheDay

data class RegisterUiState(
    val userName: String = "",
    val userAge: Int = 0,
    val userLivePlace: String = "",
    val selectedHabits: List<HabitsCategories> = emptyList(),
    val remindTime: PartsOfTheDay? = null,
)
