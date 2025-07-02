package com.example.habitat.data.repository

import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.data.room.HabitsDao
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.helpers.TimeHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    private val habitsDao: HabitsDao
): HabitsRepository {
    override suspend fun insertHabit(habit: Habit) = habitsDao.insertHabit(habit.toEntity())


    override suspend fun getHabitsByDate(date: LocalDate): Flow<List<Habit>> {
        val dayBoundsInMillis = TimeHelper.getDayBoundsInMillis(date)

        return habitsDao.getHabitsByDate(dayBoundsInMillis.first,dayBoundsInMillis.second).map { list -> list.map { entity -> entity.toDomain() } }
    }

    override suspend fun updateHabitStatus(id: Int, status: Boolean) = habitsDao.updateHabitStatus(id,status)

}