package com.example.habitat.presentation.screens.starterScreens.registerScreen.stepsScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout

@Composable
fun FinishSetUpScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            imageVector = Icons.Default.CheckCircleOutline,
            contentDescription = "check",
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingLarge),
            text = "You are all set!",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}