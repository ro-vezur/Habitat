package com.example.habitat.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.habitat.presentation.commonComponents.BottomNavigationBar

@Composable
fun MainScreen(

) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navHostController
            )
        }
    ) { innerPadding ->
        NavigationHost(
            modifier = Modifier
                .padding(innerPadding),
            navHostController = navHostController
        )
    }

}