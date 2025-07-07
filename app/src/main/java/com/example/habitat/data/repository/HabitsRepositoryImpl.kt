package com.example.habitat.data.repository

import com.example.habitat.data.room.HabitsDao
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.helpers.TimeHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    private val habitsDao: HabitsDao
): HabitsRepository {
    override suspend fun insertHabit(habit: Habit) = habitsDao.insertHabit(habit.toEntity())

    override suspend fun getHabitsByDate(day: String, dateMillis: Long): Flow<List<Habit>> {
        val weekBounds = TimeHelper.getWeekBoundsFromMillis(dateMillis = dateMillis)
        return habitsDao.getHabitsByDayOfWeek(
            day = day,
            startOfTheWeek = weekBounds.first,
            endOfTheWeek = weekBounds.second
        ).map { list -> list.map { entity -> entity.toDomain() } }
    }

    override suspend fun updateHabitStatus(id: Int, status: Boolean) = habitsDao.updateHabitStatus(id,status)

}