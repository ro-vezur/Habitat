package com.example.habitat.presentation

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.habitat.domain.entities.Habit
import com.example.habitat.helpers.serializations.base64DecodeJsonObject
import com.example.habitat.presentation.screens.mainScreens.addHabitScreen.AddHabitScreen
import com.example.habitat.presentation.screens.mainScreens.addHabitScreen.AddHabitViewModel
import com.example.habitat.presentation.screens.mainScreens.detailedHabitScreen.DetailedHabitViewModel
import com.example.habitat.presentation.screens.mainScreens.detailedHabitScreen.DetailedHabitScreen
import com.example.habitat.presentation.screens.mainScreens.homeScreen.HomeScreen
import com.example.habitat.presentation.screens.mainScreens.homeScreen.HomeViewModel
import com.example.habitat.presentation.screens.starterScreens.registerScreen.RegisterMainScreen

@Composable
fun NavigationHost(
    modifier: Modifier,
    navHostController: NavHostController
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = ScreensRoutes.Home.route,
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
            val homeViewModel: HomeViewModel = hiltViewModel()
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
            val addHabitViewModel: AddHabitViewModel = hiltViewModel()
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