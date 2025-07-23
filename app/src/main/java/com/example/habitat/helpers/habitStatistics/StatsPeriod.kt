package com.example.habitat.helpers.habitStatistics

sealed class StatsPeriod(val periodLength: Int) {
    class Week(): StatsPeriod(7)
    class Month(monthLength: Int): StatsPeriod(monthLength)
}