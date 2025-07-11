package com.example.habitat.domain.repository

import com.example.habitat.domain.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)

    suspend fun getActiveHabits(day: String, dateMillis: Long): Flow<List<Habit>>

    suspend fun updateHabitStatus(id: Int, status: Boolean)

    suspend fun updateHabit(habit: Habit)
}