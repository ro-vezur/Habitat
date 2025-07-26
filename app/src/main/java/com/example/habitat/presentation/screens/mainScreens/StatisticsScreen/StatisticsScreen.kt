package com.example.habitat.presentation.screens.mainScreens.StatisticsScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.habitat.presentation.screens.mainScreens.StatisticsScreen.habitStatisticsHelper.StatsPeriod
import com.example.habitat.helpers.TimeHelper
import com.example.habitat.ui.theme.materialThemeExtensions.iconColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.statisticsEmptyColor
import com.example.habitat.ui.theme.materialThemeExtensions.textColor

@Composable
fun StatisticsScreen(
    uiState: StatisticsUiState,
    executeEvent: (StatisticsEvent) -> Unit,
) {
    val progressBarValue by remember(uiState.completedHabitsCount,uiState.plannedHabitsCount) {
        mutableStateOf(uiState.completedHabitsCount.toFloat() / uiState.plannedHabitsCount)
    }
    val animatedProgressBarValue by animateFloatAsState(targetValue = if(!progressBarValue.isNaN()) progressBarValue else 0f, animationSpec = tween())


    Column(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.responsiveLayout.generalScreenWidthPadding)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingMedium)
                .fillMaxWidth()
        ) {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        SelectPeriod(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .fillMaxWidth(),
            selectedPeriod = uiState.selectedPeriod,
            onSelect = { period ->
                executeEvent(StatisticsEvent.ChangePeriod(period))
            }
        )

        Column(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius2))
                .background(MaterialTheme.colorScheme.primary)
                .padding(MaterialTheme.responsiveLayout.paddingMedium)
        ) {
            Text(
                text = when(uiState.selectedPeriod) {
                    is StatsPeriod.Month -> "Monthly"
                    is StatsPeriod.Week -> "Weekly"
                } + " progress",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.textColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.paddingMedium),
                text = "${(progressBarValue * 100).toInt()} %",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.textColor,
            )

            LinearProgressIndicator(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.responsiveLayout.paddingMedium,)
                    .fillMaxWidth()
                    .height(MaterialTheme.responsiveLayout.progressIndicatorBarHeight)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.statisticsEmptyColor),
                progress = { animatedProgressBarValue },
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.statisticsEmptyColor,
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp,
                drawStopIndicator = {}
            )
        }


        Column(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingMedium)
                .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius2))
                .background(MaterialTheme.colorScheme.primary)
                .padding(MaterialTheme.responsiveLayout.paddingLarge)
        ) {
            Text(
                text = "Top 3 habits",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.textColor,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                uiState.top3Habits.forEach { habit ->
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(MaterialTheme.responsiveLayout.paddingSmall)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(MaterialTheme.responsiveLayout.iconMedium),
                                imageVector = habit?.icon ?: Icons.Default.Block,
                                contentDescription = "habit category icon",
                                tint = MaterialTheme.colorScheme.iconColor
                            )
                        }

                        Text(
                            text = habit?.title ?: "No Category",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.textColor
                        )
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectPeriod(
    modifier: Modifier = Modifier,
    selectedPeriod: StatsPeriod,
    onSelect: (StatsPeriod) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge)
        ) {
            Row(
                modifier = Modifier
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable
                    )
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.responsiveLayout.spacingMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    modifier = Modifier,
                    text = "Last ${selectedPeriod.periodLength} days",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "drop down"
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.primary)
        }

        ExposedDropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingMedium),
            ) {
                listOf(
                    StatsPeriod.Week,
                    StatsPeriod.Month(TimeHelper.getCurrentMonthLength())
                ).forEach { period ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
                            .background(MaterialTheme.colorScheme.background),
                        text = {
                            Text(
                                text = "Last ${period.periodLength} days",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            onSelect(period)
                            isExpanded = false
                        },
                        colors = MenuDefaults.itemColors(

                        )
                    )
                }
            }
        }
    }
}
