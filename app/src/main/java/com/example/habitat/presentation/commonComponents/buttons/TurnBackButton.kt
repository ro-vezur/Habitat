package com.example.habitat.presentation.commonComponents.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout

@Composable
fun TurnBackButton(
    modifier: Modifier = Modifier,
    turnBack: () -> Unit,
    iconSize: Dp = MaterialTheme.responsiveLayout.iconMedium,
    iconColor: Color = MaterialTheme.colorScheme.primary
    ) {
    IconButton(
        modifier = modifier,
        onClick = { turnBack() }
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "turn back",
            tint = iconColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TurnBackButtonPreview() {
    HabitatTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TurnBackButton(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                turnBack = {

                }
            )
        }

    }
}