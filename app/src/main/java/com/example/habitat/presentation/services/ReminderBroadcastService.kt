package com.example.habitat.presentation.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.habitat.domain.entities.Habit
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.helpers.TimeHelper
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
@AndroidEntryPoint
class ReminderBroadcastService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val habitId = intent.getIntExtra("HABIT_ID",0)
        val habitDescription = intent.getStringExtra("HABIT_DESCRIPTION") ?: "Habit Description"
        val remindTime = intent.getLongExtra("REMIND_TIME",0)
        val repeatEveryWeek: Boolean = intent.getBooleanExtra("REPEAT_EVERY_WEEK", false)
        val periodicityValues: ArrayList<Int> = intent.getIntegerArrayListExtra("PERIODICITY") ?: arrayListOf()
        val periodicityDaysOfWeek: List<DayOfWeek> = periodicityValues.map { DayOfWeek.of(it) }

        sendNotification(context,habitDescription)

        val nextRemindTime = Habit.getNextRemindTime(
            remindTime = remindTime,
            repeatEveryWeek = repeatEveryWeek,
            periodicity = periodicityDaysOfWeek
        )

        nextRemindTime?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms()) {
                intent.putExtra("REMIND_TIME",nextRemindTime)

                val pendingIntent = PendingIntent.getBroadcast(context,habitId,intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextRemindTime,
                    pendingIntent
                )
            }
        }
    }

}

private fun sendNotification(context: Context, description: String) {
    val channelId = "habit_reminder_channel"
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(channelId, "Reminders", NotificationManager.IMPORTANCE_HIGH)
    notificationManager.createNotificationChannel(channel)

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Habit Reminder")
        .setContentText(description)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(description.hashCode(), notification)
}