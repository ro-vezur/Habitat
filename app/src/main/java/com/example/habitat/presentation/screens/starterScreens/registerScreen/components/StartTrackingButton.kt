package com.example.habitat.presentation.screens.starterScreens.registerScreen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.habitat.ui.theme.materialThemeExtensions.primaryButtonColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor

@Composable
fun StartTrackingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    ) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryButtonColor
        )
    ) {
        Text(
            text = "Start tracking",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.textColor,
        )
    }
}