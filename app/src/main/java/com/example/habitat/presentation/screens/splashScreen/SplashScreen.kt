package com.example.habitat.presentation.screens.splashScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.habitat.presentation.ScreensRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    isUserRegistered: Boolean
) {

    LaunchedEffect(isUserRegistered) {
        delay(1000)
        Log.d("is completed",isUserRegistered.toString())
        val nextRoute = if(isUserRegistered) ScreensRoutes.Home.route else ScreensRoutes.GetStarted.route
        navController.navigate(nextRoute) {
            popUpTo(ScreensRoutes.SplashScreen.route) {
                inclusive = true
            }
        }
    }
}