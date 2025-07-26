package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen

import com.example.habitat.enums.HabitsCategories
import com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper.StatsPeriod

data class StatisticsUiState(
    val selectedPeriod: StatsPeriod = StatsPeriod.Week,
    val completedHabitsCount: Int = 0,
    val plannedHabitsCount: Int = 0,
    val top3Habits: List<HabitsCategories?> = emptyList()
)
