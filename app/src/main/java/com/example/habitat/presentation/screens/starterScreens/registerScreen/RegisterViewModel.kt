package com.example.habitat.presentation.screens.starterScreens.registerScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.entities.User
import com.example.habitat.domain.repository.UserRepository
import com.example.habitat.enums.HabitsCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    init {

    }

    fun executeEvent(event: RegisterEvent) = viewModelScope.launch {
        when(event) {
            is RegisterEvent.UpdateNameInput -> updateNameInput(event.name)
            is RegisterEvent.UpdateAgeInput -> updateAgeInput(event.age)
            is RegisterEvent.UpdatePlaceInput -> updatePlaceInput(event.place)
            is RegisterEvent.AddHabit -> addHabit(event.habitCategory)
            is RegisterEvent.RemoveHabit -> removeHabit(event.habitCategory)
            RegisterEvent.CompleteRegistration -> completeRegistration()
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

    private fun completeRegistration() = viewModelScope.launch {
        _uiState.value.run {
            val isUsedSavedFlow = userRepository.saveUser(
                userEntity = User(
                    name = userName,
                    age = userAge,
                    livingPlace = userLivePlace,
                    preferredHabits = selectedHabits
                ).toEntity()
            )

            isUsedSavedFlow
                .take(1)
                .collectLatest { isUsedSaved ->
                    Log.d("is user saved?", isUsedSaved.toString())
                    userRepository.saveIsCompletedRegistration(isUsedSaved)
                }
        }
    }
}