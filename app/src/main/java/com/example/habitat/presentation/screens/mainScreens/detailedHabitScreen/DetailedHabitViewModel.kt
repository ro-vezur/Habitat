package com.example.habitat.presentation.screens.mainScreens.detailedHabitScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@HiltViewModel(assistedFactory = DetailedHabitViewModel.ViewModelAssistedFactory::class)
class DetailedHabitViewModel @AssistedInject constructor(
    @Assisted("habit") private val habit: Habit,
    private val habitsRepository: HabitsRepository
): ViewModel() {

    @AssistedFactory
    interface ViewModelAssistedFactory {
        fun create(
            @Assisted("habit") habit: Habit
        ): DetailedHabitViewModel
    }

    private val _selectedHabitFlow: MutableStateFlow<Habit> = MutableStateFlow(habit)
    val selectedHabitFlow: StateFlow<Habit> = _selectedHabitFlow.asStateFlow()

    init {
    }

    private fun fetchHabit() = viewModelScope.launch {
        val fetchedHabit = habitsRepository.getHabitById("")
        _selectedHabitFlow.update { fetchedHabit }
    }

    fun invoke(event: DetailedHabitEvent) = viewModelScope.launch {
        when(event) {
            is DetailedHabitEvent.OnDescriptionValueChange -> onDescriptionValueChange(event.value)
            is DetailedHabitEvent.SelectCategory -> selectCategory(event.category)
            is DetailedHabitEvent.AddPeriodicityDay -> addPeriodicityDay(event.day)
            is DetailedHabitEvent.RemovePeriodicityDay -> removePeriodicityDay(event.day)
            is DetailedHabitEvent.ChangeRepeatEveryWeekValue -> changeRepeatEveryWeekValue(event.value)
            is DetailedHabitEvent.ChangeRemindTime -> changeRemindTime(event.hours,event.minutes)
            DetailedHabitEvent.UpdateHabit -> updateHabit()
        }
    }

    private fun onDescriptionValueChange(value: String) = viewModelScope.launch {
        _selectedHabitFlow.update { habit -> habit.copy(description = value) }
    }

    private fun selectCategory(category: HabitsCategories) = viewModelScope.launch {
        _selectedHabitFlow.update { habit -> habit.copy(category = category) }
    }

    private fun addPeriodicityDay(day: DayOfWeek) = viewModelScope.launch {
        _selectedHabitFlow.update { habit -> habit.copy(periodicity = habit.periodicity + day)}
    }

    private fun removePeriodicityDay(day: DayOfWeek) = viewModelScope.launch {
        _selectedHabitFlow.update { habit -> habit.copy(periodicity = habit.periodicity - day) }
    }

    private fun changeRepeatEveryWeekValue(value: Boolean) = viewModelScope.launch {
        _selectedHabitFlow.update { habit -> habit.copy(repeatEveryWeek = value) }
    }

    private fun changeRemindTime(hours: Int, minutes: Int) = viewModelScope.launch {
        val hoursAndMinutesInMillis = TimeHelper.convertHoursAndMinutesIntoMillis(hours,minutes)
        val startOfDayInMillis = TimeHelper.getStartOfDayMillis(System.currentTimeMillis())

        _selectedHabitFlow.update { habit ->
            habit.copy(remindTime = hoursAndMinutesInMillis + startOfDayInMillis)
        }
    }

    private fun updateHabit() = viewModelScope.launch {
        habitsRepository.updateHabit(_selectedHabitFlow.value)
    }
}