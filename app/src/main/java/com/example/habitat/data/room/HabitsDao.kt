package com.example.habitat.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.habitat.data.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits" +
            " WHERE timeOfCreation BETWEEN :startOfTheWeek AND :endOfTheWeek AND periodicity LIKE '%' || :day || '%'" +
            " OR repeatEveryWeek AND periodicity LIKE '%' || :day || '%'"
    )
    fun getActiveHabits(
        day: String,
        startOfTheWeek: Long,
        endOfTheWeek: Long
    ): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET completedDates = :completedDates WHERE id = :id")
    suspend fun updateHabitCompletedDates(id: Int, completedDates: String)

    @Update
    suspend fun updateHabit(habit: HabitEntity)
}