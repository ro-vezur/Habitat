package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen

import com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper.StatsPeriod

sealed class StatisticsEvent {
    class ChangePeriod(val period: StatsPeriod): StatisticsEvent()
}