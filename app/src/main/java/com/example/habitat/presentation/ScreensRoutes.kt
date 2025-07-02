package com.example.habitat.presentation


sealed class ScreensRoutes(val route: String) {
    data object SplashScreen: ScreensRoutes(route = "SplashScreenRoute")

    data object GetStarted: ScreensRoutes(route = "GetStartedScreenRoute")

    data object Register: ScreensRoutes(route = "RegisterScreenRoute")

    data object Home: ScreensRoutes(route = "HomeScreenRoute")

    data object AddHabit: ScreensRoutes(route = "AddHabitScreenRoute")

    data object Statistics: ScreensRoutes(route = "StatisticsScreenRoute")
}