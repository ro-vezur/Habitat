package com.example.habitat.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habitat.HABITS_LOCAL_DATA_BASE_NAME
import com.example.habitat.domain.entities.Habit
import com.example.habitat.enums.HabitsCategories
import java.time.DayOfWeek

@Entity(tableName = HABITS_LOCAL_DATA_BASE_NAME)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String = "",
    val category: String? = null,
    val remindTime: Long = 0,
    val periodicity: List<String> = emptyList(),
    val repeatEveryWeek: Boolean = false,
    val timeOfCreation: Long = 0,
    val isCompleted: Boolean = false,
) {
    fun toDomain(): Habit {
        return Habit(
            id = id,
            description = description,
            category = HabitsCategories.entries.find { it.name == category },
            remindTime = remindTime,
            periodicity = periodicity.map { dayNameWhenRepeat -> DayOfWeek.entries.find { dayOfWeek -> dayOfWeek.name == dayNameWhenRepeat} ?: DayOfWeek.MONDAY },
            repeatEveryWeek = repeatEveryWeek,
            timeOfCreation = timeOfCreation,
            isCompleted = isCompleted
        )
    }
}
