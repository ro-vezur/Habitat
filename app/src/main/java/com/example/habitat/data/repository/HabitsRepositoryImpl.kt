package com.example.habitat.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.habitat.data.room.HabitsDao
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.helpers.TimeHelper
import com.example.habitat.presentation.services.ReminderBroadcastService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val habitsDao: HabitsDao
): HabitsRepository {
    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override suspend fun insertHabit(habit: Habit) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) return

        val intent = Intent(context, ReminderBroadcastService::class.java).apply {
            putExtra("HABIT_DESCRIPTION", habit.description)
            putExtra("REMIND_TIME", habit.remindTime)
            putExtra("REPEAT_EVERY_WEEK", habit.repeatEveryWeek)
            putExtra("PERIODICITY", ArrayList(habit.periodicity.map { it.value }))
        }
        val pendingIntent = PendingIntent.getBroadcast(context, habit.id, intent, PendingIntent.FLAG_IMMUTABLE)

        if(habit.remindTime > System.currentTimeMillis()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                habit.remindTime,
                pendingIntent
            )
        }

        habitsDao.insertHabit(habit.toEntity())
    }

    override suspend fun getActiveHabits(day: String, dateMillis: Long): Flow<List<Habit>> {
        val weekBounds = TimeHelper.getWeekBoundsFromMillis(dateMillis = dateMillis)

        Log.d("formatted date 1", TimeHelper.formatDateFromMillis(dateMillis, TimeHelper.DateFormats.YYYY_MM_DD_HH_MM))
        Log.d("formatted date 2", TimeHelper.formatDateFromMillis(weekBounds.second, TimeHelper.DateFormats.YYYY_MM_DD_HH_MM))

        return habitsDao.getActiveHabits(
            day = day,
            selectedDateMillis = dateMillis,
            startOfTheWeek = weekBounds.first,
            endOfTheWeek = weekBounds.second
        )
            .map { list -> list.map { entity -> entity.toDomain() } }
    }

    override suspend fun updateHabitCompletedDates(id: Int, completedDates: List<Long>) {
        habitsDao.updateHabitCompletedDates(
            id = id,
            completedDates = Json.encodeToString(completedDates)
        )
    }

    override suspend fun getHabitsBetweenDates(startDate: Long, endDate: Long): Flow<List<Habit>> {
        return habitsDao.getHabitsBetweenDates(startDate,endDate).map { list ->
            list.map { entity -> entity.toDomain() } }
    }

    override suspend fun updateHabit(habit: Habit) {
        val intent = Intent(context, ReminderBroadcastService::class.java).apply {
            putExtra("HABIT_DESCRIPTION",habit.description)
            putExtra("REMIND_TIME", habit.remindTime)
            putExtra("REPEAT_EVERY_WEEK", habit.repeatEveryWeek)
            putExtra("PERIODICITY", ArrayList(habit.periodicity.map { it.value }))
        }
        val pendingIntent = PendingIntent.getBroadcast(context,habit.id,intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


        if(habit.remindTime > System.currentTimeMillis()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                habit.remindTime,
                pendingIntent
            )
        }
        habitsDao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        val intent = Intent(context, ReminderBroadcastService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, habit.id, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.cancel(pendingIntent)
        habitsDao.deleteHabit(habit.toEntity())
    }
}