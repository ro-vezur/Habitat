package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import com.example.habitat.enums.HabitsCategories
import java.time.DayOfWeek

sealed class AddHabitEvent {
    class OnHabitDescriptionChange(val input: String): AddHabitEvent()
    class SelectDay(val day: DayOfWeek): AddHabitEvent()
    class DeselectDay(val day: DayOfWeek): AddHabitEvent()
    class SelectHabitCategory(val habitCategory: HabitsCategories): AddHabitEvent()
    class SetRemindTime(val hours: Int, val minutes: Int): AddHabitEvent()
    class ChangeWeekRepetitionValue(val value: Boolean): AddHabitEvent()
    class OnGenerateHabitsPromptChange(val input: String): AddHabitEvent()
    object GenerateHabits: AddHabitEvent()
    object AddHabit: AddHabitEvent()
}