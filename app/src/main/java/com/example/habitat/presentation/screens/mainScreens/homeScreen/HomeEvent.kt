package com.example.habitat.presentation.screens.mainScreens.homeScreen

import java.time.LocalDate

sealed class HomeEvent {
    class FetchHabitsByDate(val day: String, val dateMillis: Long): HomeEvent()
    class UpdateHabitStatus(val id: Int, val status: Boolean): HomeEvent()
    class SelectNewDate(val newDateMillis: Long): HomeEvent()
}