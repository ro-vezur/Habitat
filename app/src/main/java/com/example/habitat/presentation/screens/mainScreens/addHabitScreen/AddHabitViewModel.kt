package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import android.R.attr.category
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable.isCompleted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<AddHabitUiState> = MutableStateFlow(AddHabitUiState())
    val uiState: StateFlow<AddHabitUiState> = _uiState.asStateFlow()

    fun executeEvent(event: AddHabitEvent) = viewModelScope.launch {
        when(event) {
            is AddHabitEvent.OnHabitDescriptionChange -> onHabitDescriptionChange(event.input)
            is AddHabitEvent.SelectDay -> selectDay(event.day)
            is AddHabitEvent.DeselectDay -> deselectDay(event.day)
            is AddHabitEvent.SelectHabitCategory -> selectHabitCategory(event.habitCategory)
            is AddHabitEvent.SetRemindTime -> setRemindTime(event.hours,event.minutes)
            is AddHabitEvent.ChangeWeekRepetitionValue -> changeWeekRepetitionValue(event.value)
            AddHabitEvent.AddHabit -> addHabit()
        }
    }

    private fun onHabitDescriptionChange(input: String) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(habitDescription = input)
        }
    }

    private fun selectDay(day: DayOfWeek) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(selectedDays = state.selectedDays + day)
        }
    }

    private fun deselectDay(day: DayOfWeek) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(selectedDays = state.selectedDays - day)
        }
    }

    private fun selectHabitCategory(category: HabitsCategories) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(habitCategory = category)
        }
    }

    private fun setRemindTime(hours: Int, minutes: Int) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                remindTimeSelectedHours = hours,
                remindTimeSelectedMinutes = minutes
            )
        }
    }

    private fun changeWeekRepetitionValue(value: Boolean) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(repeatEveryWeek = value)
        }
    }

    private fun addHabit() = viewModelScope.launch {
        _uiState.value.run {
            val hoursAndMinutesInMillis = TimeHelper.convertHoursAndMinutesIntoMillis(
                hours = remindTimeSelectedHours,
                minutes = remindTimeSelectedMinutes
            )

            val habit = Habit(
                id = 0,
                description = habitDescription,
                category = habitCategory,
                remindTime = hoursAndMinutesInMillis + TimeHelper.getStartOfDayMillis(currentTimeMillis()),
                periodicity = if(selectedDays.isEmpty()) listOf(TimeHelper.getCurrentDayOfWeekObject()) else selectedDays.sortedBy { it.value },
                repeatEveryWeek = repeatEveryWeek,
                timeOfCreation = currentTimeMillis(),
                isCompleted = false
            )

            habitsRepository.insertHabit(habit = habit)
        }
    }
}