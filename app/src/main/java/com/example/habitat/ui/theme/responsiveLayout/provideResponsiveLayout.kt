package com.example.habitat.ui.theme.responsiveLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import com.example.habitat.ui.theme.windowSizeType.WindowSizeTypes
import com.example.habitat.ui.theme.windowSizeType.getWindowSizeType

@Composable
fun provideResponsiveLayout(): ResponsiveLayout {
    val windowSizeType = getWindowSizeType()

    return when(windowSizeType) {
        WindowSizeTypes.PHONE -> phoneLayout
        WindowSizeTypes.TABLET -> tabletLayout
        WindowSizeTypes.EXPANDED -> tabletLayout
    }
}

val localResponsiveLayout = staticCompositionLocalOf { phoneLayout }

val phoneLayout = ResponsiveLayout(
    generalScreenWidthPadding = 26.dp,

    spacingSmall = 4.dp,
    spacingMedium = 10.dp,
    spacingLarge = 16.dp,
    spacingExtraLarge = 22.dp,

    paddingExtraSmall = 10.dp,
    paddingSmall = 15.dp,
    paddingMedium = 25.dp,
    paddingLarge = 35.dp,
    paddingExtraLarge = 50.dp,

    bottomNavigationBarItemsSize = 75.dp,
    bottomNavigationBarHeight = 95.dp,
    buttonHeight1 = 50.dp,
    buttonHeight2 = 65.dp,
    checkBoxSize = 35.dp,
    switcherButtonWidth = 60.dp,
    switcherButtonHeight = 34.dp,
    switcherPadding = 5.dp,
    inputTextFieldHeight = 65.dp,
    expandedInputTextFieldHeight = 80.dp,
    maxDropDownMenuHeight = 240.dp,
    topBarHeight = 100.dp,

    periodicityDayCardSize = 38.dp,
    addHabitScreenButtonsWidth = 160.dp,
    addHabitScreenButtonsHeight = 45.dp,
    timePickerCarouselHeight = 180.dp,
    doneTimePickerButtonWidth = 120.dp,
    doneTimePickerButtonHeight = 40.dp,

    iconSmall = 22.dp,
    iconMedium = 28.dp,
    iconLarge = 34.dp,

    border1 = 1.dp,
    border2 = 2.dp,

    progressIndicatorBarHeight = 10.dp,
    progressCircularSize = 45.dp,
    progressCircularSizeStrokeWidth = 6.dp,

    roundedCornerRadius1 = 10.dp,
    roundedCornerRadius2 = 15.dp,
)

val tabletLayout = ResponsiveLayout(
    generalScreenWidthPadding = 45.dp,

    spacingSmall = 8.dp,
    spacingMedium = 16.dp,
    spacingLarge = 24.dp,
    spacingExtraLarge = 32.dp,

    paddingExtraSmall = 18.dp,
    paddingSmall = 25.dp,
    paddingMedium = 40.dp,
    paddingLarge = 50.dp,
    paddingExtraLarge = 70.dp,

    bottomNavigationBarItemsSize = 95.dp,
    bottomNavigationBarHeight = 115.dp,
    buttonHeight1 = 65.dp,
    buttonHeight2 = 80.dp,
    checkBoxSize = 55.dp,
    switcherButtonWidth = 90.dp,
    switcherButtonHeight = 50.dp,
    switcherPadding = 8.dp,
    inputTextFieldHeight = 80.dp,
    expandedInputTextFieldHeight = 100.dp,
    maxDropDownMenuHeight = 320.dp,
    topBarHeight = 130.dp,

    periodicityDayCardSize = 60.dp,
    addHabitScreenButtonsWidth = 240.dp,
    addHabitScreenButtonsHeight = 70.dp,
    timePickerCarouselHeight = 235.dp,
    doneTimePickerButtonWidth = 165.dp,
    doneTimePickerButtonHeight = 75.dp,

    iconSmall = 35.dp,
    iconMedium = 45.dp,
    iconLarge = 55.dp,

    border1 = 2.dp,
    border2 = 3.dp,

    progressIndicatorBarHeight = 20.dp,
    progressCircularSize = 45.dp,
    progressCircularSizeStrokeWidth = 6.dp,

    roundedCornerRadius1 = 15.dp,
    roundedCornerRadius2 = 20.dp,
    )