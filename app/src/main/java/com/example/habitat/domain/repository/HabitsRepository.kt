package com.example.habitat.domain.repository

import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.entities.User
import com.example.habitat.source.Source
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)

    suspend fun getActiveHabits(day: String, dateMillis: Long): Flow<List<Habit>>

    suspend fun updateHabitCompletedDates(id: Int, completedDates: List<Long>)

    suspend fun getHabitsBetweenDates(startDate: Long, endDate: Long): Flow<List<Habit>>

    suspend fun updateHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    suspend fun generateHabits(user: User, additionalPrompt: String): Flow<Source<List<Habit>>>
}