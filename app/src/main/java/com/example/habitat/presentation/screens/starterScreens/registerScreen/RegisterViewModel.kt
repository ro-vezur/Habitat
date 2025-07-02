package com.example.habitat.presentation.screens.starterScreens.registerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.enums.HabitsCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

): ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun executeEvent(event: RegisterEvent) = viewModelScope.launch {
        when(event) {
            is RegisterEvent.UpdateNameInput -> updateNameInput(event.name)
            is RegisterEvent.UpdateAgeInput -> updateAgeInput(event.age)
            is RegisterEvent.UpdatePlaceInput -> updatePlaceInput(event.place)
            is RegisterEvent.AddHabit -> addHabit(event.habitCategory)
            is RegisterEvent.RemoveHabit -> removeHabit(event.habitCategory)
        }
    }

    private fun updateNameInput(name: String) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(userName = name)
        }
    }

    private fun updateAgeInput(age: Int) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(userAge = age)
        }
    }

    private fun updatePlaceInput(place: String) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(userLivePlace = place)
        }
    }

    private fun addHabit(habit: HabitsCategories) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                selectedHabits = state.selectedHabits + habit
            )
        }
    }

    private fun removeHabit(habit: HabitsCategories) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                selectedHabits = state.selectedHabits - habit
            )
        }
    }
}