package com.example.habitat.data.entities

import android.util.Log
import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.domain.entities.Habit
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import kotlinx.serialization.Serializable
import java.lang.System.currentTimeMillis
import java.time.DayOfWeek

@Serializable
data class AiGeneratedHabitEntity(
    val id: Int = 0,
    val description: String = "",
    val category: String? = null,
    val remindTimeHours: Int = 0,
    val remindTimeMinutes: Int = 0,
    val periodicity: List<String> = emptyList(),
    val repeatEveryWeek: Boolean = false,
    val timeOfCreation: Long = 0,
    val completedDates: List<Long> = emptyList(),
) {
    fun toDomain(): Habit {
        return Habit(
            id = this@AiGeneratedHabitEntity.id,
            description = this@AiGeneratedHabitEntity.description,
            category = HabitsCategories.entries.find { it.name == this@AiGeneratedHabitEntity.category },
            remindTime = TimeHelper.convertHoursAndMinutesIntoMillis(TimeHelper.getStartOfDayFromMillis(currentTimeMillis()),remindTimeHours,remindTimeMinutes),
            periodicity = this@AiGeneratedHabitEntity.periodicity.map { dayNameWhenRepeat -> DayOfWeek.entries.find { dayOfWeek -> dayOfWeek.name == dayNameWhenRepeat} ?: DayOfWeek.MONDAY },
            repeatEveryWeek = this@AiGeneratedHabitEntity.repeatEveryWeek,
            timeOfCreation = this@AiGeneratedHabitEntity.timeOfCreation,
            completedDates = this@AiGeneratedHabitEntity.completedDates
        )
    }
}