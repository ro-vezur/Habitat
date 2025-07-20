package com.example.habitat.presentation.screens.starterScreens.registerScreen

import com.example.habitat.enums.HabitsCategories

sealed class RegisterEvent {
    class AddHabit(val habitCategory: HabitsCategories): RegisterEvent()
    class RemoveHabit(val habitCategory: HabitsCategories): RegisterEvent()
    class UpdateNameInput(val name: String): RegisterEvent()
    class UpdateAgeInput(val age: Int): RegisterEvent()
    class UpdatePlaceInput(val place: String): RegisterEvent()
    object CompleteRegistration: RegisterEvent()
}