package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.helpers.habitStatistics.StatsPeriod
import com.example.habitat.helpers.TimeHelper
import com.example.habitat.helpers.habitStatistics.HabitStatistics
import com.example.habitat.helpers.habitStatistics.HabitStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsScreenViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<StatisticsUiState> = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticsUiState()
    )

    private var fetchStatisticsJob: Job = Job()

    init {
        updateHabitStatisticsJob(_uiState.value.selectedPeriod)
    }

    fun executeEvent(event: StatisticsEvent) = viewModelScope.launch {
        when(event) {
            is StatisticsEvent.ChangePeriod -> changeStatisticsEvent(event.period)
        }
    }

    private fun changeStatisticsEvent(period: StatsPeriod) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                selectedPeriod = period
            )
        }

        updateHabitStatisticsJob(period)
    }

    private fun updateHabitStatisticsJob(period: StatsPeriod) = viewModelScope.launch {
        fetchStatisticsJob.cancel()
        fetchStatisticsJob = fetchHabitsStatistics(period)
    }

    private fun fetchHabitsStatistics(period: StatsPeriod) = viewModelScope.launch {
        val bounds = when(period) {
            is StatsPeriod.Month -> TimeHelper.getMonthBoundsFromMillis(System.currentTimeMillis())
            is StatsPeriod.Week -> TimeHelper.getWeekBoundsFromMillis(System.currentTimeMillis())
        }
        val habitsFlow = habitsRepository.getHabitsBetweenDates(bounds.first,bounds.second)

        habitsFlow.collect { habits ->
            val statistics = HabitStatistics.getStatisticsWithinPeriod(period,habits)

            val totalCompleted = statistics.sumOf { it.completedCount }
            val totalPlanned = statistics.sumOf { it.plannedCount }

            val grouped = habits.groupBy { it.category }

            val summedCompletedHabits = grouped
                .map { it.key to it.value.sumOf { it.completedDates.filter { it >= bounds.first && it <= bounds.second }.size } }
                .sortedByDescending { it.second }

            val top3Habits = summedCompletedHabits.take(3).map { it.first }

            _uiState.update { state ->
                state.copy(
                    completedHabitsCount = totalCompleted,
                    plannedHabitsCount = totalPlanned,
                    top3Habits = top3Habits,
                )
            }
        }
    }

}