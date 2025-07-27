package com.example.habitat.data.repository

import android.R.attr.data
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Output
import android.os.Build
import android.util.Log
import android.util.Log.e
import android.util.Log.i
import com.example.habitat.GEMINI_API_KEY
import com.example.habitat.GEMINI_VERSION
import com.example.habitat.data.entities.AiGeneratedHabitEntity
import com.example.habitat.data.entities.HabitEntity
import com.example.habitat.data.room.HabitsDao
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.entities.User
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import com.example.habitat.presentation.services.ReminderBroadcastService
import com.example.habitat.source.Source
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerationConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.System.currentTimeMillis
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val habitsDao: HabitsDao
): HabitsRepository {

    val geminiModel = GenerativeModel(
        modelName = GEMINI_VERSION,
        apiKey = GEMINI_API_KEY,
    )

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

    override suspend fun generateHabits(user: User, additionalPrompt: String): Flow<Source<List<Habit>>> = flow {
        emit(Source.Loading())

        val prompt = """
You are generating 3–5 useful habits for a user based on their preferences and additional prompt (if any).

### USER DATA
- $user
(User data contains the user's preferred habit categories and location)

### HABIT DATA CLASS
data class AiGeneratedHabitEntity(
    val id: Int = 0,
    val description: String = "",
    val category: String? = null,
    val remindTimeHours: Int = 0, // remind hours in integer format, not string
    val remindTimeMinutes: Int = 0, // remind minutes in integer format, not string
    val periodicity: List<String> = emptyList(), // days of week, full uppercase like ["MONDAY", "FRIDAY"]
    val repeatEveryWeek: Boolean = false,
    val timeOfCreation: Long = 0, // can be 0
    val completedDates: List<Long> = emptyList(),
)

### REQUIREMENTS:
- Generate 3–5 habits as HabitEntity objects in a **single JSON array**
- Use categories from the user's preferences, unless `additionalPrompt` is provided. Then filter by categories relevant to the prompt.
- `remindTimeHours` must be in Integer (eg. 22)
- `remindTimeMinutes` must be in integer (eg. 30)
- Adapt remindTime to user's **local time zone**, based on location in user data
- `periodicity` should be a list of weekdays in **UPPERCASE full names** (e.g., ["MONDAY", "WEDNESDAY"])
- Set `repeatEveryWeek = true`
- `timeOfCreation` can be left as 0
- Output **must be only valid JSON** — no explanation or markdown formatting

### CATEGORIES
${HabitsCategories.entries}

### ADDITIONAL PROMPT
${additionalPrompt.ifBlank { "no additional prompt" }}

Now return the generated habits."""



        val result = geminiModel.generateContent(prompt)
        val rawJson = result.text?.trim { it == '`' }?.replace("json","") ?: ""
        val decoded = Json.decodeFromString<List<AiGeneratedHabitEntity>>(rawJson)
            .map { it.toDomain() }
            .map { habit ->
                val nextRemindTime = habit.getNextRemindTime() ?: habit.remindTime
                val remindTime = if(habit.remindTime < currentTimeMillis()) nextRemindTime else habit.remindTime

                habit.copy(remindTime = remindTime)
            }
        emit(Source.Success(data = decoded))
    }.catch { e ->
        e.printStackTrace()
        emit(Source.Error(message = e.message.toString()))
    }
}