package com.example.habitat.data.room

import android.icu.text.ListFormatter.Type.AND
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitat.data.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits" +
            " WHERE timeOfCreation BETWEEN :startOfTheWeek AND :endOfTheWeek AND timeOfCreation <= :selectedDateMillis AND periodicity LIKE '%' || :day || '%'" +
            " OR repeatEveryWeek AND periodicity LIKE '%' || :day || '%'"
    )
    fun getActiveHabits(
        day: String,
        selectedDateMillis: Long,
        startOfTheWeek: Long,
        endOfTheWeek: Long
    ): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET completedDates = :completedDates WHERE id = :id")
    suspend fun updateHabitCompletedDates(id: Int, completedDates: String)

    @Query("SELECT * FROM habits WHERE timeOfCreation BETWEEN :startDate AND :endDate OR repeatEveryWeek")
    fun getHabitsBetweenDates(startDate: Long, endDate: Long): Flow<List<HabitEntity>>

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)
}