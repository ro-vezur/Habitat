package com.example.habitat.domain.repository

import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.domain.entities.Habit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)

    suspend fun getHabitsByDate(day: String,dateMillis: Long): Flow<List<Habit>>

    suspend fun updateHabitStatus(id: Int, status: Boolean)
}