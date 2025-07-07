package com.example.habitat.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habitat.data.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {
    @Insert
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits" +
            " WHERE timeOfCreation BETWEEN :startOfTheWeek AND :endOfTheWeek" +
            " AND periodicity LIKE '%' || :day || '%'")
    fun getHabitsByDayOfWeek(
        day: String,
        startOfTheWeek: Long,
        endOfTheWeek: Long
    ): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET isCompleted = :status WHERE id = :id")
    suspend fun updateHabitStatus(id: Int, status: Boolean)
}