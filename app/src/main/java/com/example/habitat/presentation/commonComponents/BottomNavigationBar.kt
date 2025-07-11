package com.example.habitat.presentation.commonComponents

import android.graphics.Color
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.habitat.enums.BottomNavigationBarItems
import com.example.habitat.ui.theme.HabitatTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.substring
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.habitat.presentation.ScreensRoutes
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import kotlinx.serialization.json.Json
import kotlin.toString

private val routesThatNotDisplaysBottomNavigationBar = listOf<String>(
    ScreensRoutes.GetStarted.route,
    ScreensRoutes.Register.route,
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    ) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: ScreensRoutes.Home.route } }
    val showBottomNavigationBar by remember { derivedStateOf { !routesThatNotDisplaysBottomNavigationBar.contains(currentRoute) } }


    if(showBottomNavigationBar) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.secondary,
                thickness = MaterialTheme.responsiveLayout.border1
            )

            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                containerColor = MaterialTheme.colorScheme.background
            ) {
                BottomNavigationBarItems.entries.forEach { item ->
                    val isSelected = remember(currentRoute.contains(item.route)) {
                        currentRoute.contains(item.route)
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .size(MaterialTheme.responsiveLayout.iconLarge),
                                imageVector = item.icon,
                                contentDescription = "bottom navigation bar icon"
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = androidx.compose.ui.graphics.Color.Transparent
                        )
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomNavigationBar() {
    HabitatTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BottomNavigationBar(
                navController = rememberNavController()
            )
        }
    }
}