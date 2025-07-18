package com.example.habitat.presentation.screens.mainScreens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.helpers.TimeHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
): ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var fetchHabitsJob: Job = Job()

    init {

    }

    fun executeEvent(event: HomeEvent) = viewModelScope.launch {
        when(event) {
            is HomeEvent.FetchHabitsByDate -> changeHabitsDate(event.day,event.dateMillis)
            is HomeEvent.UpdateHabitCompletedDatesEvent -> updateHabitStatus(event.habit,event.selectedDateMillis)
            is HomeEvent.SelectNewDate -> selectNewDate(event.newDateMillis)
        }
    }

    private fun changeHabitsDate(day: String,dateMillis: Long) {
        fetchHabitsJob.cancel()
        fetchHabitsJob = fetchHabitsByDate(day,dateMillis)
    }

    private fun fetchHabitsByDate(day: String,dateMillis: Long) = viewModelScope.launch(Dispatchers.IO) {
        val fetchedHabits = habitsRepository.getActiveHabits(day, dateMillis)

        fetchedHabits.collectLatest { habits ->
            _uiState.update { state ->
                state.copy(habits = habits)
            }
        }

    }

    private fun updateHabitStatus(habit: Habit, selectedDateMillis: Long) = viewModelScope.launch {
        val startOfDayMillis = TimeHelper.getStartOfDayMillis(selectedDateMillis)

        habitsRepository.updateHabitCompletedDates(
            id = habit.id,
            completedDates = if(habit.completedDates.contains(startOfDayMillis)) habit.completedDates - startOfDayMillis else habit.completedDates + startOfDayMillis
        )

    }

    private fun selectNewDate(newDateMillis: Long) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(selectedDateMillis = newDateMillis)
        }
    }
}