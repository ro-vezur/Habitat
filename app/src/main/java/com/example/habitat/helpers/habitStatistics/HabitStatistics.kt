package com.example.habitat.helpers.habitStatistics

import android.util.Log
import coil3.util.CoilUtils.result
import com.example.habitat.domain.entities.Habit
import com.example.habitat.helpers.habitStatistics.StatsPeriod
import com.example.habitat.helpers.TimeHelper
import java.time.DayOfWeek
import java.util.Calendar


object HabitStatistics {
    fun generateStatsForHabit(
        habit: Habit,
        period: StatsPeriod
    ): HabitStats {
        val plannedTimes: MutableList<Boolean> = mutableListOf()

        val calendar = TimeHelper.getCalenderWithPeriod(period,habit.timeOfCreation)

        val periodLength = when(period) {
            is StatsPeriod.Month -> period.periodLength
            is StatsPeriod.Week -> calendar.get(Calendar.DAY_OF_MONTH) + period.periodLength - 1
        }

        while (calendar.get(Calendar.DAY_OF_MONTH) <= periodLength) {
            val dayName = TimeHelper.getDayOfWeekObjectFromMillis(calendar.timeInMillis)
            val starterDayMillis = TimeHelper.getStartOfDayMillis(calendar.timeInMillis)

            if (dayName in habit.periodicity) {
                plannedTimes.add(habit.completedDates.contains(starterDayMillis))
            }

            if(!habit.repeatEveryWeek && TimeHelper.getDayOfWeekObjectFromMillis(calendar.timeInMillis) == DayOfWeek.SUNDAY) {
                break
            }

            if(period is StatsPeriod.Month && calendar.get(Calendar.DAY_OF_MONTH) == periodLength) {
                break
            }

            calendar.add(Calendar.DAY_OF_MONTH,1)
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