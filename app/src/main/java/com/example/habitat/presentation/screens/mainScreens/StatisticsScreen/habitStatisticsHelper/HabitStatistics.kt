package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper

import com.example.habitat.domain.entities.Habit
import com.example.habitat.helpers.TimeHelper
import java.time.DayOfWeek
import java.util.Calendar


object HabitStatistics {
    fun generateStatsForHabit(
        habit: Habit,
        period: StatsPeriod
    ): HabitStats {
        val plannedTimes: MutableList<Boolean> = mutableListOf()

        val calendar = TimeHelper.getCalenderWithPeriod(period)

        val periodLength = when(period) {
            is StatsPeriod.Month -> calendar.get(Calendar.DAY_OF_YEAR) + period.periodLength
            is StatsPeriod.Week -> calendar.get(Calendar.DAY_OF_YEAR) + period.periodLength - 1
        }

        if(habit.timeOfCreation > calendar.timeInMillis) {
            calendar.timeInMillis = TimeHelper.getWeekBoundsFromMillis(habit.timeOfCreation).first
        }

        while (calendar.get(Calendar.DAY_OF_YEAR) <= periodLength) {
            val dayName = TimeHelper.getDayOfWeekObjectFromMillis(calendar.timeInMillis)
            val starterDayMillis = TimeHelper.getStartOfDayFromMillis(calendar.timeInMillis)

            if (dayName in habit.periodicity) {
                plannedTimes.add(habit.completedDates.contains(starterDayMillis))
            }

            if(!habit.repeatEveryWeek && TimeHelper.getDayOfWeekObjectFromMillis(calendar.timeInMillis) == DayOfWeek.SUNDAY) {
                break
            }

            calendar.add(Calendar.DAY_OF_YEAR,1)

        }

        return HabitStats(
            habit = habit,
            plannedCount = plannedTimes.size,
            completedCount = plannedTimes.filter { it }.size
        )
    }

    fun getStatisticsWithinPeriod(
        period: StatsPeriod,
        activeHabits: List<Habit>
    ): List<HabitStats> {
        val result = mutableListOf<HabitStats>()

        for (habit in activeHabits) {
            result.add(
                generateStatsForHabit(
                    habit = habit,
                    period = period
                )
            )
        }

        return result
    }
}