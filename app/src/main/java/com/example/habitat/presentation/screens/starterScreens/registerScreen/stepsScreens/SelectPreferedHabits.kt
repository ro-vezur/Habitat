package com.example.habitat.presentation.screens.starterScreens.registerScreen.stepsScreens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.presentation.screens.starterScreens.registerScreen.RegisterEvent
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColorSecondary

@Composable
fun Step2Screen(
    selectedHabits: List<HabitsCategories>,
    executeEvent: (RegisterEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .fillMaxWidth(0.75f),
            text = "What habits do you want to work on?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingSmall),
            text = "Choose or more habits.",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.textColorSecondary
        )

        LazyVerticalGrid(
            modifier = Modifier
                .padding(
                    top = MaterialTheme.responsiveLayout.paddingMedium,
                    bottom = MaterialTheme.responsiveLayout.paddingExtraSmall
                ),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge)
        ) {
            items(
                items = HabitsCategories.entries
            ) { habit ->
                val isSelected = selectedHabits.find { it.name == habit.name } != null

                HabitCard(
                    modifier = Modifier
                        .aspectRatio(1f),
                    habitCategory = habit,
                    isSelected = isSelected,
                    onClick = {
                        if(isSelected) {
                            executeEvent(RegisterEvent.RemoveHabit(habit))
                        } else {
                            executeEvent(RegisterEvent.AddHabit(habit ))
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun HabitCard(
    modifier: Modifier = Modifier,
    habitCategory: HabitsCategories,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if(isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween()
    )

    val contentColor by animateColorAsState(
        targetValue = if(isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
        animationSpec = tween()
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
            .drawBehind { drawRect(color = backgroundColor) }
            .border(
                width = MaterialTheme.responsiveLayout.border1,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1)
            )
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(0.4f)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()

                    drawRect(
                        color = contentColor,
                        blendMode = BlendMode.SrcIn
                    )
                },
            imageVector = habitCategory.icon,
            contentDescription = "habit icon",
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingSmall)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()

                    drawRect(
                        color = contentColor,
                        blendMode = BlendMode.SrcIn
                    )
                },
            text = habitCategory.title,
            style = MaterialTheme.typography.headlineMedium,
           // color = contentColor.value
        )
    }
}