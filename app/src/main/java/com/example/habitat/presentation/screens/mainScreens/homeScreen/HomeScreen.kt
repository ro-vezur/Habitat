package com.example.habitat.presentation.screens.mainScreens.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitat.datePickerVisibilityAnimationSpec
import com.example.habitat.domain.entities.Habit
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import com.example.habitat.presentation.ScreensRoutes
import com.example.habitat.presentation.commonComponents.CustomCheckBox
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.materialThemeExtensions.iconColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    uiState: HomeUiState,
    executeEvent: (HomeEvent) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = uiState.selectedDateMillis,
        yearRange = IntRange(start = TimeHelper.getCurrentYear() - 25, endInclusive = TimeHelper.getCurrentYear() + 25)
    )

    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        val localDate = TimeHelper.createLocalDateOfMillis(datePickerState.selectedDateMillis ?: 0)

        executeEvent(HomeEvent.SelectNewDate(newDateMillis = datePickerState.selectedDateMillis ?: 0))
        executeEvent(HomeEvent.FetchHabitsByDate(day = localDate.dayOfWeek.name)
        )
    }

    Box(
        modifier = Modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            TopDateBar(
                selectedDateMillis = datePickerState.selectedDateMillis ?: 0,
                onDateClick = {
                    showDatePicker = !showDatePicker
                }
            )

            HabitsList(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = MaterialTheme.responsiveLayout.roundedCornerRadius2,
                            topEnd = MaterialTheme.responsiveLayout.roundedCornerRadius2
                        )
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = MaterialTheme.responsiveLayout.paddingMedium),
                navController = navController,
                selectedDateMillis = datePickerState.selectedDateMillis ?: 0,
                habits = uiState.habits,
                updateHabitCompletedDates = { habit ->
                    executeEvent(HomeEvent.UpdateHabitCompletedDatesEvent(
                        habit = habit,
                        selectedDateMillis = datePickerState.selectedDateMillis ?: 0)
                    )
                }
            )

        }

        AnimatedVisibility(
            visible = showDatePicker,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }, animationSpec = datePickerVisibilityAnimationSpec),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }, animationSpec = datePickerVisibilityAnimationSpec)
        ) {
            DatePicker(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.topBarHeight)
                    .clip(
                        RoundedCornerShape(
                            topStart = MaterialTheme.responsiveLayout.roundedCornerRadius2,
                            topEnd = MaterialTheme.responsiveLayout.roundedCornerRadius2
                        )
                    )
                    .fillMaxSize(),
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                showModeToggle = false
            )
        }
    }

}

@Composable
private fun TopDateBar(
    selectedDateMillis: Long,
    onDateClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .padding(
                start = MaterialTheme.responsiveLayout.generalScreenWidthPadding,
                end = MaterialTheme.responsiveLayout.generalScreenWidthPadding,
            )
            .height(MaterialTheme.responsiveLayout.topBarHeight)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(
            onClick = {
                onDateClick()
            },
            contentPadding = PaddingValues(MaterialTheme.responsiveLayout.paddingSmall)
        ) {
            Text(
                text = "${TimeHelper.getDayOfWeekNameFromMillis(selectedDateMillis)}, " +
                        "${TimeHelper.getMonthNameFromMillis(selectedDateMillis)} " +
                        "${TimeHelper.getDayOfMonthNumberFromMillis(selectedDateMillis)}",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.textColor,
            )
        }

    }

}

@Composable
private fun HabitsList(
    modifier: Modifier = Modifier,
    navController: NavController,
    selectedDateMillis: Long,
    habits: List<Habit>,
    updateHabitCompletedDates: (Habit) -> Unit
) {
    val sortedHabits = remember(habits) { habits.sortedBy { it.timeOfCreation } }

    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = MaterialTheme.responsiveLayout.paddingLarge),
            text = "Today, I have:",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingExtraLarge)
        ) {

            items(
                items = sortedHabits
            ) { habit ->
                HabitCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = MaterialTheme.responsiveLayout.border1,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1)
                        )
                        .clickable {
                            navController.navigate(ScreensRoutes.DetailedHabit.createRoute(habit = habit)) {
                                popUpTo(ScreensRoutes.mainScreensStartDestinationRoute) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(MaterialTheme.responsiveLayout.paddingSmall),
                    selectedDateMillis = selectedDateMillis,
                    habit = habit,
                    onCheck = { updateHabitCompletedDates(habit) }
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.responsiveLayout.paddingSmall)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add new task",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.W500
                    )

                    Spacer(Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.responsiveLayout.checkBoxSize)
                            .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
                            .background(MaterialTheme.colorScheme.secondary)
                            .clickable {
                                navController.navigate(ScreensRoutes.AddHabit.route) {
                                    popUpTo(ScreensRoutes.mainScreensStartDestinationRoute) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(MaterialTheme.responsiveLayout.paddingMedium),
                            imageVector = Icons.Default.Add,
                            contentDescription = "add task",
                            tint = MaterialTheme.colorScheme.iconColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HabitCard(
    modifier: Modifier = Modifier,
    selectedDateMillis: Long,
    habit: Habit,
    onCheck: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = habit.description,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W500
            )

            Row(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.spacingMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.paddingSmall)
            ) {
                habit.category?.let { category ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(MaterialTheme.responsiveLayout.spacingSmall)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(MaterialTheme.responsiveLayout.iconSmall),
                            imageVector = category.icon,
                            contentDescription = "category icon",
                            tint = MaterialTheme.colorScheme.iconColor
                        )
                    }
                }

                Text(
                    modifier = Modifier,
                    text = TimeHelper.formatDateFromMillis(millis = habit.remindTime, dateFormat = TimeHelper.DateFormats.HH_MM),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.W500
                )
            }
        }


        Spacer(Modifier.weight(1f))

        CustomCheckBox(
            isChecked = habit.completedDates.map { TimeHelper.formatDateFromMillis(it, TimeHelper.DateFormats.YYYY_MM_DD) }
                .contains(TimeHelper.formatDateFromMillis(selectedDateMillis, TimeHelper.DateFormats.YYYY_MM_DD)),
            onCheck = {
                onCheck()
            }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    HabitatTheme {
        HomeScreen(
            navController = rememberNavController(),
            uiState = HomeUiState(
                habits = listOf(
                    Habit(
                        id = 1,
                        description = "Read 10 pages of a book",
                        category = HabitsCategories.STUDY,
                        remindTime = 0,
                        timeOfCreation = 1,
                        completedDates = emptyList()
                    ),
                    Habit(
                        id = 2,
                        description = "Meditated for 5 minutes",
                        category = HabitsCategories.HEALTH,
                        remindTime = 0,
                        timeOfCreation = 2,
                        completedDates = emptyList()
                    )
                )
            ),
            executeEvent = {

            }
        )
    }
}