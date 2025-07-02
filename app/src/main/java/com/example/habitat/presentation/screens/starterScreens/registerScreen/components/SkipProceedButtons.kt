package com.example.habitat.presentation.screens.starterScreens.registerScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.habitat.ui.theme.materialThemeExtensions.primaryButtonColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor

@Composable
fun SkipProceedButtons(
    modifier: Modifier = Modifier,
    onSkip: () -> Unit,
    onProceed: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge)
    ) {
        OutlinedButton(
            modifier = Modifier
                .height(MaterialTheme.responsiveLayout.buttonHeight1)
                .weight(1f),
            onClick = {
                onSkip()
            },
            shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
            border = BorderStroke(
                width = MaterialTheme.responsiveLayout.border1,
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button (
            modifier = Modifier
                .height(MaterialTheme.responsiveLayout.buttonHeight1)
                .weight(1f),
            onClick = {
                onProceed()
            },
            shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryButtonColor
            )
        ) {
            Text(
                text = "Proceed",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.textColor
            )
        }
    }
}