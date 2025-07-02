package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.enums.HabitsCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
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

    private fun addHabit() = viewModelScope.launch {
        val habit = Habit(
            id = 0,
            description = _uiState.value.habitDescription,
            category = _uiState.value.habitCategory,
            remindTime = 0,
            periodicity = _uiState.value.selectedDays,
            repeatEveryWeek = false,
            timeOfCreation = System.currentTimeMillis(),
            isCompleted = false
        )

        habitsRepository.insertHabit(habit = habit)
    }
}