package com.example.habitat.presentation.screens.mainScreens.homeScreen

import android.util.Log
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

    private val _selectedDateMillisFlow: MutableStateFlow<Long> = MutableStateFlow(0)

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var fetchHabitsJob: Job = Job()

    init {
        viewModelScope.launch {
            _selectedDateMillisFlow.collectLatest { selectedDateMillis ->
                val day = TimeHelper.getDayOfWeekObjectFromMillis(selectedDateMillis)

                Log.d("selected day of week",day.name)

                changeHabitsDate(day.name,selectedDateMillis)
            }
        }
    }

    fun executeEvent(event: HomeEvent) = viewModelScope.launch {
        when(event) {
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

            val weekBounds = TimeHelper.getWeekBoundsFromMillis(dateMillis)
            val desc_to_creatiomTime =  habits.map { it.description to it.timeOfCreation }

            Log.d("habits dates of creation",
                desc_to_creatiomTime.map { it.first to TimeHelper.formatDateFromMillis(it.second,
                    TimeHelper.DateFormats.YYYY_MM_DD_HH_MM) }.toString()
            )

            Log.d("mapped habits",desc_to_creatiomTime.map { it.first to (it.second >= dateMillis && (weekBounds.second > it.second)) }.toString())

            _uiState.update { state ->
                state.copy(habits = habits.sortedByDescending { it.remindTime })
            }
        }
    }

    private fun updateHabitStatus(habit: Habit, selectedDateMillis: Long) = viewModelScope.launch {
        val startOfDayMillis = TimeHelper.getStartOfDayFromMillis(selectedDateMillis)

        habitsRepository.updateHabitCompletedDates(
            id = habit.id,
            completedDates = if(habit.completedDates.contains(startOfDayMillis)) habit.completedDates - startOfDayMillis else habit.completedDates + startOfDayMillis
        )

    }

    private fun selectNewDate(newDateMillis: Long) = viewModelScope.launch {
        _selectedDateMillisFlow.update { TimeHelper.getEndOfDayFromMillis(newDateMillis) }
    }
}