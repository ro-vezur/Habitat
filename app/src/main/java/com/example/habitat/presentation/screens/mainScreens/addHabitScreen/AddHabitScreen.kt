package com.example.habitat.presentation.screens.mainScreens.addHabitScreen

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GeneratingTokens
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.habitat.presentation.commonComponents.InteractiveFields.CustomTextInputField
import com.example.habitat.presentation.commonComponents.InteractiveFields.baseCustomTextFieldColors
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor
import java.time.DayOfWeek
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habitat.domain.entities.Habit
import com.example.habitat.enums.HabitsCategories
import com.example.habitat.helpers.TimeHelper
import com.example.habitat.presentation.ScreensRoutes
import com.example.habitat.presentation.ScreensRoutes.Companion.mainScreensStartDestinationRoute
import com.example.habitat.presentation.commonComponents.CustomCheckBox
import com.example.habitat.presentation.commonComponents.CustomDialogMenu
import com.example.habitat.presentation.commonComponents.CustomTimePickerDialog.CustomTimePickerDialog
import com.example.habitat.presentation.commonComponents.buttons.CustomSwitcher
import com.example.habitat.source.Source
import com.example.habitat.ui.theme.materialThemeExtensions.iconColor
import com.example.habitat.ui.theme.materialThemeExtensions.primaryButtonColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi

private val generateHabitsMenuVisibilityAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(
    durationMillis = 420
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddHabitScreen(
    navController: NavController,
    uiState: AddHabitUiState,
    executeEvent: (AddHabitEvent) -> Unit,
) {

    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current
    val alarmManager = if(!isInPreview) context.getSystemService(Context.ALARM_SERVICE) as AlarmManager else null
    val permissionRequestLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted -> }

    var showTimePicker by remember { mutableStateOf(false) }
    var showErrorToGenerateHabits by remember { mutableStateOf(false) }
    var showGenerateHabitsMenu by rememberSaveable { mutableStateOf(true) }

    BackHandler {
        when {
            showTimePicker -> { showTimePicker = false }
            showErrorToGenerateHabits -> { showErrorToGenerateHabits = false }
            else -> {
                navController.popBackStack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        AnimatedContent(
            targetState = showGenerateHabitsMenu,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = generateHabitsMenuVisibilityAnimationSpec,
                    initialOffsetX = { -it }
                ).togetherWith(
                    slideOutHorizontally(
                        animationSpec = generateHabitsMenuVisibilityAnimationSpec,
                        targetOffsetX = { it }
                    )
                )
            }

        ) { show ->
            if(show) {
                GenerateHabitsTopBar(
                    onClickClose = {
                        showGenerateHabitsMenu = false
                    }
                )
            } else {
                AddNewHabitTopBar(
                    onClickSuggestHabits = {
                        val currentUser = uiState.currentUser
                        if (currentUser.name.isEmpty() || currentUser.age.toString().isEmpty() || currentUser.preferredHabits.isEmpty()) {
                            showErrorToGenerateHabits = true
                            return@AddNewHabitTopBar
                        }

                        showGenerateHabitsMenu = true
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = MaterialTheme.responsiveLayout.roundedCornerRadius2,
                        topEnd = MaterialTheme.responsiveLayout.roundedCornerRadius2
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = MaterialTheme.responsiveLayout.paddingMedium)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.paddingLarge),
                text = "Set up your habit preferences",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            CustomTextInputField(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                    .fillMaxWidth()
                    .height(MaterialTheme.responsiveLayout.inputTextFieldHeight),
                value = uiState.habitDescription,
                onValueChange = { value ->
                    executeEvent(AddHabitEvent.OnHabitDescriptionChange(value))
                },
                placeHolderText = "Enter habit description",
                colors = baseCustomTextFieldColors().copy(
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
                )
            )

            SelectHabitCategoryButton(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.paddingLarge),
                onSelect = { category ->
                    executeEvent(AddHabitEvent.SelectHabitCategory(category))
                },
                selectedCategory = uiState.habitCategory,
            )

            SetPeriodicity(
                selectedDays = uiState.selectedDays,
                repeatEveryWeek = uiState.repeatEveryWeek,
                onDayClick = { day, isSelected ->
                    if(isSelected) {
                        executeEvent(AddHabitEvent.DeselectDay(day))
                    } else {
                        executeEvent(AddHabitEvent.SelectDay(day))
                    }
                },
                changeWeekRepetitionValue = { value ->
                    executeEvent(AddHabitEvent.ChangeWeekRepetitionValue(value))
                }
            )

            SetRemindTime(
                remindTime = TimeHelper.formatDateFromHoursAndMinutes(uiState.remindTimeSelectedHours,uiState.remindTimeSelectedMinutes),
                onRemindTimeClick = {
                    showTimePicker = !showTimePicker
                },
            )

            Spacer(Modifier.weight(1f))

            AddNewHabitButton(
                modifier = Modifier
                    .padding(bottom = MaterialTheme.responsiveLayout.paddingMedium)
                    .fillMaxWidth()
                    .height(MaterialTheme.responsiveLayout.buttonHeight1),
                onClick = {

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

                        return@AddNewHabitButton
                    }

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager?.canScheduleExactAlarms() == false) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        context.startActivity(intent)

                        return@AddNewHabitButton
                    }

                    executeEvent(AddHabitEvent.AddHabit)
                    navController.navigate(ScreensRoutes.Home.route) {
                            popUpTo(mainScreensStartDestinationRoute) {
                                saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

    if(showTimePicker) {
        CustomTimePickerDialog(
            selectedHours = uiState.remindTimeSelectedHours,
            selectedMinutes = uiState.remindTimeSelectedMinutes,
            onDismiss = {
                showTimePicker = false
            },
            onTimeSelect = { hour, minute ->
                executeEvent(AddHabitEvent.SetRemindTime(hour,minute))

                showTimePicker = false
            }
        )
    }

    if(showErrorToGenerateHabits) {
        CustomDialogMenu(
            text = "Failed to generate habits." +
                    "\nNot enough information about user" +
                    "\nDo you want to fill information right now?",
            onDismiss = {
                showErrorToGenerateHabits = false
            },
            onAccept = {
                navController.navigate(ScreensRoutes.FillUsersInformationScreen.route)
            }
        )
    }

    AnimatedVisibility(
        visible = showGenerateHabitsMenu,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }, animationSpec = generateHabitsMenuVisibilityAnimationSpec),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it }, animationSpec = generateHabitsMenuVisibilityAnimationSpec)
    ) {
        GenerateHabitsMenu(
            generateHabitsPrompt = uiState.generateHabitsPrompt,
            generatedHabitsSource = uiState.generatedHabitsSource,
            executeEvent = executeEvent,
        )
    }
}

@Composable
private fun GenerateHabitsMenu(
    generateHabitsPrompt: String,
    generatedHabitsSource: Source<List<Habit>>,
    executeEvent: (AddHabitEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(top = MaterialTheme.responsiveLayout.topBarHeight)
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topEnd = MaterialTheme.responsiveLayout.roundedCornerRadius2,
                    topStart = MaterialTheme.responsiveLayout.roundedCornerRadius2
                )
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = MaterialTheme.responsiveLayout.generalScreenWidthPadding)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CustomTextInputField(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .fillMaxWidth()
                .height(MaterialTheme.responsiveLayout.expandedInputTextFieldHeight),
            value = generateHabitsPrompt,
            onValueChange = { value ->
                executeEvent(AddHabitEvent.OnGenerateHabitsPromptChange(value))
            },
            placeHolderText = "Enter short prompt (Optional)",
            singleLine = false,
            enableLettersLimit = true,
            showLettersCounter = true,
            maxLetters = 80,
            colors = baseCustomTextFieldColors().copy(
                unfocusedTextColor = MaterialTheme.colorScheme.secondary
            )
        )

        when(generatedHabitsSource) {
            is Source.Error -> {

            }
            is Source.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        CircularProgressIndicator(
                            modifier = Modifier
                        )
                    }
                )
            }
            is Source.Success -> {
                generatedHabitsSource.data?.let { generatedHabits ->
                    Button(
                        modifier = Modifier
                            .padding(top = MaterialTheme.responsiveLayout.paddingMedium)
                            .fillMaxWidth(0.9f)
                            .height(MaterialTheme.responsiveLayout.buttonHeight1),
                        shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryButtonColor
                        ),
                        onClick = {
                            executeEvent(AddHabitEvent.GenerateHabits)
                        }
                    ) {
                        Text(
                            text = "Regenerate Habits",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.textColor
                        )
                    }

                    generatedHabits.forEach { habit ->
                        GeneratedHabitCard(
                            modifier = Modifier
                                .padding(top = MaterialTheme.responsiveLayout.spacingLarge)
                                .fillMaxWidth()
                                .border(
                                    width = MaterialTheme.responsiveLayout.border1,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1)
                                )
                                .padding(MaterialTheme.responsiveLayout.paddingSmall),
                            habit = habit,
                        )
                    }
                }
            }
            is Source.Unknown -> {
                Button(
                    modifier = Modifier
                        .padding(top = MaterialTheme.responsiveLayout.paddingMedium)
                        .fillMaxWidth(0.9f)
                        .height(MaterialTheme.responsiveLayout.buttonHeight1),
                    shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryButtonColor
                    ),
                    onClick = {
                        executeEvent(AddHabitEvent.GenerateHabits)
                    }
                ) {
                    Text(
                        text = "Generate Habits",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.textColor
                    )
                }
            }
        }
    }
}

@Composable
private fun GeneratedHabitCard(
    modifier: Modifier = Modifier,
    habit: Habit
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
    }
}

@Composable
private fun GenerateHabitsTopBar(
    onClickClose: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                start = MaterialTheme.responsiveLayout.generalScreenWidthPadding,
                end = MaterialTheme.responsiveLayout.generalScreenWidthPadding,
            )
            .height(MaterialTheme.responsiveLayout.topBarHeight)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.responsiveLayout.paddingSmall),
            text = "Generate Habits",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.textColor,
        )

        Spacer(Modifier.weight(1f))

        IconButton(
            modifier = Modifier,
            colors = IconButtonDefaults.iconButtonColors(),
            onClick = {
                onClickClose()
            },
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.responsiveLayout.iconLarge),
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = MaterialTheme.colorScheme.iconColor
            )
        }
    }
}

@Composable
private fun AddNewHabitTopBar(
    onClickSuggestHabits: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                start = MaterialTheme.responsiveLayout.generalScreenWidthPadding,
                end = MaterialTheme.responsiveLayout.generalScreenWidthPadding,
            )
            .height(MaterialTheme.responsiveLayout.topBarHeight)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.responsiveLayout.paddingSmall),
            text = "Add new habit",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.textColor,
        )

        Spacer(Modifier.weight(1f))

        IconButton(
            modifier = Modifier,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            onClick = {
                onClickSuggestHabits()
            },
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.responsiveLayout.iconMedium),
                imageVector = Icons.Default.GeneratingTokens,
                contentDescription = "generate habits",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SetPeriodicity(
    selectedDays: List<DayOfWeek>,
    repeatEveryWeek: Boolean,
    onDayClick: (DayOfWeek, Boolean) -> Unit,
    changeWeekRepetitionValue: (value: Boolean) -> Unit,
    ) {

    Text(
        modifier = Modifier
            .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge),
        text = "Set periodicity",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary
    )

    Row(
        modifier = Modifier
            .padding(top = MaterialTheme.responsiveLayout.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingMedium)
    ) {
        DayOfWeek.entries.forEach { day ->
            val isSelected = remember(selectedDays.contains(day)) {
                selectedDays.contains(day)
            }

            val containerColor = if(isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            val textColor = if(isSelected)MaterialTheme.colorScheme.textColor else MaterialTheme.colorScheme.primary
            val borderColor = if(isSelected) Color.Transparent else MaterialTheme.colorScheme.primary

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.responsiveLayout.periodicityDayCardSize)
                    .background(containerColor)
                    .border(
                        width = MaterialTheme.responsiveLayout.border1,
                        color = borderColor,
                        shape = CircleShape
                    )
                    .clickable {
                        onDayClick(day, isSelected)
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier,
                    text = day.name[0].toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(top = MaterialTheme.responsiveLayout.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomSwitcher(
            modifier = Modifier,
            isChecked = repeatEveryWeek,
            onCheckValueChange = { isChecked ->
                changeWeekRepetitionValue(isChecked)
            },
            buttonWidth = MaterialTheme.responsiveLayout.switcherButtonWidth,
            buttonHeight = MaterialTheme.responsiveLayout.switcherButtonHeight,
            switchPadding = PaddingValues(MaterialTheme.responsiveLayout.switcherPadding)
        )

        Text(
            text = "Repeat Every Week",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.W500
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectHabitCategoryButton(
    modifier: Modifier = Modifier,
    onSelect: (HabitsCategories) -> Unit,
    selectedCategory: HabitsCategories?,
    ) {

    var isExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        Row (
            modifier = Modifier
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable
                )
                .width(MaterialTheme.responsiveLayout.addHabitScreenButtonsWidth)
                .height(MaterialTheme.responsiveLayout.addHabitScreenButtonsHeight)
                .border(
                    width = MaterialTheme.responsiveLayout.border1,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1)
                )
                .padding(horizontal = MaterialTheme.responsiveLayout.spacingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if(selectedCategory != null) {
                Icon(
                    modifier = Modifier
                        .size(MaterialTheme.responsiveLayout.iconSmall),
                    imageVector = selectedCategory.icon,
                    contentDescription = "selected category icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Spacer(Modifier)
            }

            Text(
                modifier = Modifier
                    .weight(1f),
                text = selectedCategory?.title ?: "Select Category",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

        }

        ExposedDropdownMenu(
            modifier = Modifier,
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .width(MaterialTheme.responsiveLayout.addHabitScreenButtonsWidth)
                    .height(MaterialTheme.responsiveLayout.maxDropDownMenuHeight),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingMedium),
                state = scrollState,
            ) {
                items(
                    items = HabitsCategories.entries
                ) { category ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
                            .height(MaterialTheme.responsiveLayout.addHabitScreenButtonsHeight)
                            .background(MaterialTheme.colorScheme.primary),
                        text = {
                            Text(
                                text = category.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(MaterialTheme.responsiveLayout.iconSmall),
                                imageVector = category.icon,
                                contentDescription = "drop down menu item icon",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = {
                            onSelect(category)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetRemindTime(
    remindTime: String,
    onRemindTimeClick: () -> Unit,
) {

    Text(
        modifier = Modifier
            .padding(top = MaterialTheme.responsiveLayout.paddingLarge),
        text = "Set Remind Time",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary
    )

    Box(
        modifier = Modifier
            .padding(top = MaterialTheme.responsiveLayout.paddingSmall)
            .width(MaterialTheme.responsiveLayout.addHabitScreenButtonsWidth)
            .height(MaterialTheme.responsiveLayout.addHabitScreenButtonsHeight)
            .border(
                width = MaterialTheme.responsiveLayout.border1,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1)
            )
            .clickable {
                onRemindTimeClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = remindTime,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AddNewHabitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryButtonColor
        )
    ) {
        Text(
            text = "Add new habit",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.textColor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun AddHabitScreenPrev() {
    HabitatTheme {
        AddHabitScreen(
            navController = rememberNavController(),
            uiState = AddHabitUiState(),
            executeEvent = {

            }
        )
    }
}