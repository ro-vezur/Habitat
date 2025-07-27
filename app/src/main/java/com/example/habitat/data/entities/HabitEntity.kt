package com.example.habitat.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habitat.HABITS_LOCAL_DATA_BASE_NAME
import com.example.habitat.domain.entities.Habit
import com.example.habitat.enums.HabitsCategories
import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
@Entity(tableName = HABITS_LOCAL_DATA_BASE_NAME)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String = "",
    val category: String? = null,
    val remindTime: Long = 0,
    val periodicity: List<String> = emptyList(),
    val repeatEveryWeek: Boolean = false,
    val timeOfCreation: Long = 0,
    val completedDates: List<Long> = emptyList(),
) {
    fun toDomain(): Habit {
        return Habit(
            id = this@HabitEntity.id,
            description = this@HabitEntity.description,
            category = HabitsCategories.entries.find { it.name == this@HabitEntity.category },
            remindTime = this@HabitEntity.remindTime,
            periodicity = this@HabitEntity.periodicity.map { dayNameWhenRepeat -> DayOfWeek.entries.find { dayOfWeek -> dayOfWeek.name == dayNameWhenRepeat} ?: DayOfWeek.MONDAY },
            repeatEveryWeek = this@HabitEntity.repeatEveryWeek,
            timeOfCreation = this@HabitEntity.timeOfCreation,
            completedDates = this@HabitEntity.completedDates
        )
    }
}
