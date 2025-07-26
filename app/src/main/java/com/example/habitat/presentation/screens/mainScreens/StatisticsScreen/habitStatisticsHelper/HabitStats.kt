package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper

import com.example.habitat.domain.entities.Habit

data class HabitStats(
    val habit: Habit,
    val plannedCount: Int,
    val completedCount: Int,
)
