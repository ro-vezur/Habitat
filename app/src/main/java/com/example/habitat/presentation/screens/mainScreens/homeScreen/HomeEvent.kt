package com.example.habitat.presentation.screens.mainScreens.homeScreen

import java.time.LocalDate

sealed class HomeEvent {
    class FetchHabitsByDate(val date: LocalDate): HomeEvent()
    class UpdateHabitStatus(val id: Int, val status: Boolean): HomeEvent()
    class SelectNewDate(val newDateMillis: Long): HomeEvent()
}