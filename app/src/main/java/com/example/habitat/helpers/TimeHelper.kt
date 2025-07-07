package com.example.habitat.helpers

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

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

    fun getMonthNumberFromMillis(millis: Long): Int {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.month.value
    }

    fun getDayOfMonthNumberFromMillis(millis: Long): Int {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.dayOfMonth
    }

    fun getYearFromTimestamp(millis: Long): Int {
        val date = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return date.year
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

    fun createLocalDateOfTimestamp(millis: Long): LocalDate {
        return LocalDate.of(
            getYearFromTimestamp(millis),
            getMonthNumberFromMillis(millis),
            getDayOfMonthNumberFromMillis(millis)
        )
    }

    fun formatDateFromMillis(millis: Long, dateFormat: String): String {
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(Date(millis))
    }

    fun formatDateFromHoursAndMinutes(hours: Int, minutes: Int): String {
        val local = LocalTime.of(hours,minutes)
        return local.format(DateTimeFormatter.ofPattern(DateFormats.HH_MM))
    }

    fun convertHoursAndMinutesIntoMillis(hours: Int,minutes: Int): Long {
        return (hours * 60 * 60 * 1000L) + (minutes * 60 * 1000L)
    }

    fun getStartOfDayMillis(timestamp: Long): Long {
        val zone = ZoneId.systemDefault()
        val localDate = Instant.ofEpochMilli(timestamp).atZone(zone).toLocalDate()
        return localDate.atStartOfDay(zone).toInstant().toEpochMilli()
    }

    fun getCurrentDayOfWeekObject(): DayOfWeek = LocalDate.now().dayOfWeek

    object DateFormats {
        const val HH_MM = "HH:mm"
    }

}