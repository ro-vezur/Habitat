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
        WindowSizeTypes.EXPANDED -> expandedLayout
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

    buttonHeight1 = 50.dp,
    buttonHeight2 = 65.dp,
    checkBoxSize = 35.dp,
    switcherButtonWidth = 60.dp,
    switcherButtonHeight = 34.dp,
    switcherPadding = 5.dp,
    inputTextFieldHeight = 60.dp,
    maxDropDownMenuHeight = 240.dp,
    topBarHeight = 100.dp,

    periodicityDayCardSize = 38.dp,
    addHabitScreenButtonsWidth = 160.dp,
    addHabitScreenButtonHeight = 45.dp,
    timePickerCarouselHeight = 180.dp,
    doneTimePickerButtonWidth = 120.dp,
    doneTimePickerButtonHeight = 40.dp,

    iconSmall = 22.dp,
    iconMedium = 28.dp,
    iconLarge = 34.dp,

    border1 = 1.dp,
    border2 = 2.dp,

    progressIndicatorBarHeight = 10.dp,

    roundedCornerRadius1 = 10.dp,
    roundedCornerRadius2 = 15.dp,
)

val tabletLayout = ResponsiveLayout(
    generalScreenWidthPadding = 45.dp,

    spacingSmall = 8.dp,
    spacingMedium = 16.dp,
    spacingLarge = 24.dp,
    spacingExtraLarge = 22.dp,

    paddingExtraSmall = 20.dp,
    paddingSmall = 30.dp,
    paddingMedium = 50.dp,
    paddingLarge = 65.dp,
    paddingExtraLarge = 85.dp,

    buttonHeight1 = 55.dp,
    buttonHeight2 = 75.dp,
    checkBoxSize = 40.dp,
    switcherButtonWidth = 75.dp,
    switcherButtonHeight = 35.dp,
    switcherPadding = 5.dp,
    inputTextFieldHeight = 55.dp,
    maxDropDownMenuHeight = 150.dp,
    topBarHeight = 80.dp,

    periodicityDayCardSize = 42.dp,
    addHabitScreenButtonsWidth = 110.dp,
    addHabitScreenButtonHeight = 40.dp,
    timePickerCarouselHeight = 180.dp,
    doneTimePickerButtonWidth = 110.dp,
    doneTimePickerButtonHeight = 40.dp,

    iconSmall = 20.dp,
    iconMedium = 28.dp,
    iconLarge = 36.dp,

    border1 = 1.dp,
    border2 = 2.dp,

    progressIndicatorBarHeight = 12.dp,

    roundedCornerRadius1 = 14.dp,
    roundedCornerRadius2 = 15.dp,
    )

val expandedLayout = ResponsiveLayout(
    generalScreenWidthPadding = 80.dp,

    spacingSmall = 12.dp,
    spacingMedium = 20.dp,
    spacingLarge = 32.dp,
    spacingExtraLarge = 22.dp,

    paddingExtraSmall = 30.dp,
    paddingSmall = 40.dp,
    paddingMedium = 55.dp,
    paddingLarge = 75.dp,
    paddingExtraLarge = 95.dp,

    buttonHeight1 = 65.dp,
    buttonHeight2 = 85.dp,
    checkBoxSize = 40.dp,
    switcherButtonWidth = 75.dp,
    switcherButtonHeight = 35.dp,
    switcherPadding = 5.dp,
    inputTextFieldHeight = 50.dp,
    maxDropDownMenuHeight = 150.dp,
    topBarHeight = 80.dp,

    periodicityDayCardSize = 42.dp,
    addHabitScreenButtonsWidth = 125.dp,
    addHabitScreenButtonHeight = 45.dp,
    timePickerCarouselHeight = 180.dp,
    doneTimePickerButtonWidth = 110.dp,
    doneTimePickerButtonHeight = 40.dp,

    iconSmall = 20.dp,
    iconMedium = 30.dp,
    iconLarge = 40.dp,

    border1 = 1.dp,
    border2 = 2.dp,

    progressIndicatorBarHeight = 12.dp,

    roundedCornerRadius1 = 18.dp,
    roundedCornerRadius2 = 15.dp,
)