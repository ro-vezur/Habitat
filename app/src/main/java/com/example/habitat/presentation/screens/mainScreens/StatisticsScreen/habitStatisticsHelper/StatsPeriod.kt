package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper

sealed class StatsPeriod(val periodLength: Int) {
    object Week: StatsPeriod(7)
    class Month(monthLength: Int): StatsPeriod(monthLength)
}