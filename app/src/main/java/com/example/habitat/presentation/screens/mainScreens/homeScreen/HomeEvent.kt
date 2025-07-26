package com.example.habitat.presentation.screens.mainScreens.homeScreen

import com.example.habitat.domain.entities.Habit

sealed class HomeEvent {
    class UpdateHabitCompletedDatesEvent(val habit: Habit, val selectedDateMillis: Long): HomeEvent()
    class SelectNewDate(val newDateMillis: Long): HomeEvent()
}