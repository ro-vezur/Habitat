package com.example.habitat.helpers

import android.util.Log
import com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper.StatsPeriod
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalQueries.localDate
import java.time.temporal.TemporalQueries.zone
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeHelper {

    fun getCurrentYear(): Int = LocalDate.now().year

    fun getMonthNameFromMillis(millis: Long): String {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    fun getDayOfWeekNameFromMillis(millis: Long): String {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    fun getDayOfMonthNumberFromMillis(millis: Long): Int {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.dayOfMonth
    }

    fun getWeekBoundsFromMillis(dateMillis: Long): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(dateMillis).atZone(zone).toLocalDate()

        val startOfWeek = date.with(DayOfWeek.MONDAY).atStartOfDay(zone).toInstant().toEpochMilli()
        val endOfWeek = date.with(DayOfWeek.SUNDAY)
            .atTime(LocalTime.MAX)
            .atZone(zone)
            .toInstant()
            .toEpochMilli()

        return startOfWeek to endOfWeek
    }

    fun getMonthBoundsFromMillis(dateMillis: Long): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(dateMillis).atZone(zone).toLocalDate()

        val startOfMonth = date.withDayOfMonth(1).atStartOfDay(zone).toInstant().toEpochMilli()
        val endOfMonth = date.withDayOfMonth(date.month.length(date.isLeapYear))
            .atTime(LocalTime.MAX)
            .atZone(zone)
            .toInstant()
            .toEpochMilli()

        return startOfMonth to endOfMonth
    }

    fun formatDateFromMillis(millis: Long, dateFormat: String): String {
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(Date(millis))
    }

    fun formatDateFromHoursAndMinutes(hours: Int, minutes: Int): String {
        val local = LocalTime.of(hours,minutes)
        return local.format(DateTimeFormatter.ofPattern(DateFormats.HH_MM))
    }

    fun convertHoursAndMinutesIntoMillis(dateMillis: Long,hours: Int,minutes: Int): Long {
        val zone = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(dateMillis).atZone(zone).toLocalDate()

        return ZonedDateTime.of(date, LocalTime.of(hours, minutes), ZoneId.systemDefault())
            .toInstant().toEpochMilli()
    }

    fun extractHoursFromMillis(millis: Long): Int {
        val zone = ZoneId.systemDefault()
        val localDate = Instant.ofEpochMilli(millis).atZone(zone).toLocalDateTime()

        return localDate.hour
    }

    fun extractMinutesFromMillis(millis: Long): Int {
        val zone = ZoneId.systemDefault()
        val localDate = Instant.ofEpochMilli(millis).atZone(zone).toLocalDateTime()

        return localDate.minute
    }

    fun extractHoursAndMinutesInMillis(millis: Long): Long {
        val zone = ZoneId.systemDefault()
        val localDate = Instant.ofEpochMilli(millis).atZone(zone)

        val hours = localDate.hour
        val minutes =localDate.minute

        return TimeUnit.HOURS.toMillis(hours.toLong()) + TimeUnit.MINUTES.toMillis(minutes.toLong())
    }

    fun getStartOfDayFromMillis(millis: Long): Long {
        val zone = ZoneId.systemDefault()
        val localDate = Instant.ofEpochMilli(millis).atZone(zone).toLocalDate()
        return localDate.atStartOfDay(zone).toInstant().toEpochMilli()
    }

    fun getEndOfDayFromMillis(millis: Long): Long {
        val zone = ZoneId.systemDefault()
        val localDate = Instant.ofEpochMilli(millis).atZone(zone).toLocalDate()

        return localDate.atTime(LocalTime.MAX)
            .atZone(zone)
            .toInstant().toEpochMilli()
    }

    fun getCurrentDayOfWeekObject(): DayOfWeek = LocalDate.now().dayOfWeek

    fun getStartOfWeekdayMillis(dayOfWeek: DayOfWeek): Long {
        val now = LocalDate.now()
        val targetDate = now.with(TemporalAdjusters.nextOrSame(dayOfWeek))

        val startOfDay = targetDate.atStartOfDay(ZoneId.systemDefault())
        return startOfDay.toInstant().toEpochMilli()
    }

    fun getNextWeekdayStartMillis(dayOfWeek: DayOfWeek): Long {
        val today = LocalDate.now()
        val nextWeekDate = today
            .with(TemporalAdjusters.next(dayOfWeek))

        val startOfDay = nextWeekDate.atStartOfDay(ZoneId.systemDefault())
        return startOfDay.toInstant().toEpochMilli()
    }

    fun getDayOfWeekObjectFromMillis(millis: Long): DayOfWeek {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.dayOfWeek
    }

    fun getCurrentMonthLength(): Int {
        val zone = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(zone).toLocalDate()

        return date.month.length(date.isLeapYear)
    }

    fun getCalenderWithPeriod(period: StatsPeriod): Calendar {
        val zone = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(zone).toLocalDate()

        val cal = Calendar.getInstance()

        when (period) {
            is StatsPeriod.Week -> cal.set(getCurrentYear(),date.monthValue - 1,date.with(DayOfWeek.MONDAY).dayOfMonth)
            is StatsPeriod.Month -> cal.set(getCurrentYear(),date.monthValue - 1,0)
        }

        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        return cal
    }

    object DateFormats {
        const val HH_MM = "HH:mm"
        const val YYYY_MM_DD = "yyyy/MM/dd"
        const val YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm"
    }

}