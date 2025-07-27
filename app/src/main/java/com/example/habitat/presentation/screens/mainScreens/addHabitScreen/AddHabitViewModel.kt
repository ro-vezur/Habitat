package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.domain.repository.UserRepository
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val userRepository: UserRepository,
): ViewModel() {

    private val _uiState: MutableStateFlow<AddHabitUiState> = MutableStateFlow(AddHabitUiState())
    val uiState: StateFlow<AddHabitUiState> = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
    }

    fun executeEvent(event: AddHabitEvent) = viewModelScope.launch {
        when(event) {
            is AddHabitEvent.OnHabitDescriptionChange -> onHabitDescriptionChange(event.input)
            is AddHabitEvent.SelectDay -> selectDay(event.day)
            is AddHabitEvent.DeselectDay -> deselectDay(event.day)
            is AddHabitEvent.SelectHabitCategory -> selectHabitCategory(event.habitCategory)
            is AddHabitEvent.SetRemindTime -> setRemindTime(event.hours,event.minutes)
            is AddHabitEvent.ChangeWeekRepetitionValue -> changeWeekRepetitionValue(event.value)
            is AddHabitEvent.OnGenerateHabitsPromptChange -> onGenerateHabitsPromptChange(event.input)
            AddHabitEvent.AddHabit -> addHabit()
            AddHabitEvent.GenerateHabits -> generateHabits()
        }
    }

    private fun fetchCurrentUser() = viewModelScope.launch {
        userRepository.currentUser.collectLatest { user ->
            _uiState.update { state ->
                state.copy(currentUser = user)
            }
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

    private fun onGenerateHabitsPromptChange(input: String) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(generateHabitsPrompt = input)
        }
    }

    private fun addHabit() = viewModelScope.launch {
        _uiState.value.run {
            val periodicity = if(selectedDays.isEmpty()) listOf(TimeHelper.getCurrentDayOfWeekObject()) else selectedDays.sortedBy { it.value }

            val remindTime = TimeHelper.convertHoursAndMinutesIntoMillis(
                dateMillis = TimeHelper.getStartOfDayFromMillis(currentTimeMillis()),
                hours = remindTimeSelectedHours,
                minutes = remindTimeSelectedMinutes
            )
            val nextRemindTime = Habit.getNextRemindTime(remindTime,repeatEveryWeek,periodicity) ?: remindTime

            val habit = Habit(
                id = 0,
                description = habitDescription,
                category = habitCategory,
                remindTime = if(remindTime < currentTimeMillis()) nextRemindTime else remindTime,
                periodicity = periodicity,
                repeatEveryWeek = repeatEveryWeek,
                timeOfCreation = currentTimeMillis(),
                completedDates = emptyList()
            )

            habitsRepository.insertHabit(habit = habit)
        }
    }

    private fun generateHabits() = viewModelScope.launch {
        _uiState.value.run {
            val generateHabitsFlow = habitsRepository.generateHabits(
                user = currentUser,
                additionalPrompt = generateHabitsPrompt
            )

            generateHabitsFlow.collectLatest { source ->
                _uiState.update { state ->
                    state.copy(generatedHabitsSource = source)
                }
            }
        }
    }
}