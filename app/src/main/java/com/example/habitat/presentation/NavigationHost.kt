package com.example.habitat.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habitat.presentation.screens.starterScreens.getStartedScreen.GetStartedScreen
import com.example.habitat.presentation.screens.starterScreens.registerScreen.RegisterViewModel
import androidx.compose.runtime.getValue
import com.example.habitat.domain.entities.Habit
import com.example.habitat.helpers.serializations.base64DecodeJsonObject
import com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.StatisticsScreen
import com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.StatisticsScreenViewModel
import com.example.habitat.presentation.screens.mainScreens.addHabitScreen.AddHabitScreen
import com.example.habitat.presentation.screens.mainScreens.addHabitScreen.AddHabitViewModel
import com.example.habitat.presentation.screens.mainScreens.detailedHabitScreen.DetailedHabitViewModel
import com.example.habitat.presentation.screens.mainScreens.detailedHabitScreen.DetailedHabitScreen
import com.example.habitat.presentation.screens.mainScreens.homeScreen.HomeScreen
import com.example.habitat.presentation.screens.mainScreens.homeScreen.HomeViewModel
import com.example.habitat.presentation.screens.splashScreen.SplashScreen
import com.example.habitat.presentation.screens.splashScreen.SplashScreenViewModel
import com.example.habitat.presentation.screens.starterScreens.registerScreen.RegisterMainScreen

@Composable
fun NavigationHost(
    modifier: Modifier,
    navHostController: NavHostController
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = ScreensRoutes.SplashScreen.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(durationMillis = 50)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(durationMillis = 50)
            )
        }
    ) {
        composable(
            route = ScreensRoutes.SplashScreen.route
        ) {
            val splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
            val isUserRegistered: Boolean by splashScreenViewModel.isUserCompletedRegistration.collectAsStateWithLifecycle()

            SplashScreen(
                navController = navHostController,
                isUserRegistered = isUserRegistered
            )
        }

        composable(
            route = ScreensRoutes.GetStarted.route
        ) {
            GetStartedScreen(
                navController = navHostController
            )
        }

        composable(
            route = ScreensRoutes.Register.route
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()

            RegisterMainScreen(
                navController = navHostController,
                uiState = uiState,
                executeEvent = registerViewModel::executeEvent
            )
        }

        composable(
            route = ScreensRoutes.Home.route
        ) {
            val homeViewModel: HomeViewModel = hiltViewModel(it)
            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                navController = navHostController,
                uiState = uiState,
                executeEvent = homeViewModel::executeEvent
            )
        }

        composable(
            route = ScreensRoutes.AddHabit.route
        ) {
            val addHabitViewModel: AddHabitViewModel = hiltViewModel(it)
            val uiState by addHabitViewModel.uiState.collectAsStateWithLifecycle()

            AddHabitScreen(
                navController = navHostController,
                uiState = uiState,
                executeEvent = addHabitViewModel::executeEvent
            )
        }

        composable(
            route = ScreensRoutes.Statistics.route
        ) {
            val statisticsViewModel: StatisticsScreenViewModel = hiltViewModel(it)
            val uiState by statisticsViewModel.uiState.collectAsStateWithLifecycle()

            StatisticsScreen(
                uiState = uiState,
                executeEvent = statisticsViewModel::executeEvent
            )
        }

        composable(ScreensRoutes.DetailedHabit.route) { backStackEntry ->
            val habitJson = backStackEntry.arguments?.getString("habit")

            habitJson?.let {
                val habitObj: Habit = base64DecodeJsonObject(habitJson)

                val detailedHabitViewModel = hiltViewModel<DetailedHabitViewModel,DetailedHabitViewModel.ViewModelAssistedFactory> { factory ->
                    factory.create(habit = habitObj)
                }

                val selectedHabitFlow by detailedHabitViewModel.selectedHabitFlow.collectAsStateWithLifecycle()

                DetailedHabitScreen(
                    selectedHabit = selectedHabitFlow,
                    navController = navHostController,
                    executeEvent = detailedHabitViewModel::invoke
                )
            }
        }
    }
}