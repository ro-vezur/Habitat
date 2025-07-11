package com.example.habitat.presentation

import com.example.habitat.domain.entities.Habit
import com.example.habitat.helpers.serializations.encodeJsonObject
import kotlinx.serialization.Serializable


@Serializable
sealed class ScreensRoutes(val route: String) {
    data object SplashScreen: ScreensRoutes(route = "SplashScreenRoute")

    data object GetStarted: ScreensRoutes(route = "GetStartedScreenRoute")

    data object Register: ScreensRoutes(route = "RegisterScreenRoute")

    data object Home: ScreensRoutes(route = "HomeScreenRoute")

    data object AddHabit: ScreensRoutes(route = "AddHabitScreenRoute")

    data object Statistics: ScreensRoutes(route = "StatisticsScreenRoute")

    data object DetailedHabit: ScreensRoutes(route = "${Home.route}/{habit}") {
        fun createRoute(habit: Habit): String {

            return route.replace("{habit}", encodeJsonObject(habit))
        }
    }
}