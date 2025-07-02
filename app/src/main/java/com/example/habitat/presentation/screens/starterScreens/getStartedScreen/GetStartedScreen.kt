package com.example.habitat.presentation.screens.starterScreens.getStartedScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitat.presentation.ScreensRoutes
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.materialThemeExtensions.primaryButtonColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout

@Composable
fun GetStartedScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.responsiveLayout.generalScreenWidthPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(
                    bottom = MaterialTheme.responsiveLayout.paddingSmall
                ),
            text = "Habitat",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            modifier = Modifier
                .padding(
                    bottom = MaterialTheme.responsiveLayout.paddingLarge
                ),
            text = "Build habits and routines",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
        )

        TextButton(
            modifier = Modifier
                .padding(
                    bottom = MaterialTheme.responsiveLayout.paddingLarge
                )
                .fillMaxWidth()
                .height(MaterialTheme.responsiveLayout.buttonHeight2),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryButtonColor
            ),
            shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
            onClick = {
                navController.navigate(ScreensRoutes.Register.route)
            }
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    HabitatTheme {
        GetStartedScreen(
            navController = rememberNavController()
        )
    }
}