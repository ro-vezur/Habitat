package com.example.habitat.presentation.screens.mainScreens.detailedHabitScreen

import com.example.habitat.enums.HabitsCategories
import com.example.habitat.presentation.screens.mainScreens.addHabitScreen.AddHabitEvent
import java.time.DayOfWeek

sealed class DetailedHabitEvent {
    class OnDescriptionValueChange(val value: String): DetailedHabitEvent()
    class SelectCategory(val category: HabitsCategories): DetailedHabitEvent()
    class AddPeriodicityDay(val day: DayOfWeek): DetailedHabitEvent()
    class RemovePeriodicityDay(val day: DayOfWeek): DetailedHabitEvent()
    class ChangeRepeatEveryWeekValue(val value: Boolean): DetailedHabitEvent()
    class ChangeRemindTime(val hours: Int, val minutes: Int): DetailedHabitEvent()
    object DeleteHabit: DetailedHabitEvent()
    object UpdateHabit: DetailedHabitEvent()
}