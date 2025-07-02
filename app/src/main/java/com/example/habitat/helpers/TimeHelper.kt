package com.example.habitat.helpers

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
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

    fun getDayBoundsInMillis(date: LocalDate): Pair<Long, Long> {
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
        return startOfDay to endOfDay
    }

    fun createLocalDateOfTimestamp(millis: Long): LocalDate {
        return LocalDate.of(
            getYearFromTimestamp(millis),
            getMonthNumberFromMillis(millis),
            getDayOfMonthNumberFromMillis(millis)
        )
    }

}