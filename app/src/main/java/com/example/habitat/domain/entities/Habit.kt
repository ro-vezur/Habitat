package com.example.habitat.domain.entities

import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.enums.HabitsCategories
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
    val isCompleted: Boolean = false,
) {
    fun toEntity(): HabitEntity {
        return HabitEntity(
            id = id,
            description = description,
            category = category?.name,
            remindTime = remindTime,
            periodicity = periodicity.map { it.name },
            repeatEveryWeek = repeatEveryWeek,
            timeOfCreation = timeOfCreation,
            isCompleted = isCompleted
        )
    }
}
