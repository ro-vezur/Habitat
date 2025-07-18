package com.example.habitat.domain.entities

import android.util.Log
import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
data class Habit(
    val id: Int,
    val description: String = "",
    val category: HabitsCategories? = null,
    val remindTime: Long = 0,
    val periodicity: List<DayOfWeek> = emptyList(),
    val repeatEveryWeek: Boolean = false,
    val timeOfCreation: Long = 0,
    val completedDates: List<Long> = emptyList(),
) {
    fun toEntity(): HabitEntity {
        return HabitEntity(
            id = this@Habit.id,
            description = this@Habit.description,
            category = this@Habit.category?.name,
            remindTime = this@Habit.remindTime,
            periodicity = this@Habit.periodicity.map { it.name },
            repeatEveryWeek = this@Habit.repeatEveryWeek,
            timeOfCreation = this@Habit.timeOfCreation,
            completedDates = this@Habit.completedDates
        )
    }
}
