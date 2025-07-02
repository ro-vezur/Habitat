package com.example.habitat.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habitat.HABITS_LOCAL_DATA_BASE_NAME
import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.domain.entities.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {
    @Insert
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE timeOfCreation BETWEEN :startMillis AND :endMillis")
    fun getHabitsByDate(startMillis: Long, endMillis: Long): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET isCompleted = :status WHERE id = :id")
    suspend fun updateHabitStatus(id: Int, status: Boolean)
}