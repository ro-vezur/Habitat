package com.example.habitat.presentation.screens.mainScreens.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.time.LocalDate
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
            is HomeEvent.FetchHabitsByDate -> changeHabitsDate(event.date)
            is HomeEvent.UpdateHabitStatus -> updateHabitStatus(event.id,event.status)
            is HomeEvent.SelectNewDate -> selectNewDate(event.newDateMillis)
        }
    }

    private fun changeHabitsDate(date: LocalDate) {
        fetchHabitsJob.cancel()
        fetchHabitsJob = fetchHabitsByDate(date)
    }

    private fun fetchHabitsByDate(date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        val fetchedHabits = habitsRepository.getHabitsByDate(date)

        fetchedHabits.collectLatest { habits ->
            Log.d("habits",habits.toString())
            _uiState.update { state ->
                state.copy(habits = habits)
            }
        }

    }

    private fun updateHabitStatus(id: Int, status: Boolean) = viewModelScope.launch {
        habitsRepository.updateHabitStatus(id,status)

    }

    private fun selectNewDate(newDateMillis: Long) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(selectedDateMillis = newDateMillis)
        }
    }

    companion object {
        private const val UI_STATE_KEY = "ui state key"
    }
}