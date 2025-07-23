package com.example.habitat.ui.theme.materialThemeExtensions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.example.habitat.ui.theme.iconColorLight
import com.example.habitat.ui.theme.primaryButtonColorLight
import com.example.habitat.ui.theme.statisticsEmptyColorLight
import com.example.habitat.ui.theme.textColorLight
import com.example.habitat.ui.theme.textColorSecondaryLight

val ColorScheme.textColor: Color @Composable @ReadOnlyComposable get() = if(isSystemInDarkTheme()) textColorLight else textColorLight
val ColorScheme.textColorSecondary: Color @Composable @ReadOnlyComposable get() = if(isSystemInDarkTheme()) textColorSecondaryLight else textColorSecondaryLight

val ColorScheme.iconColor: Color @Composable @ReadOnlyComposable get() = if(isSystemInDarkTheme()) iconColorLight else iconColorLight

val ColorScheme.primaryButtonColor: Color @Composable @ReadOnlyComposable get() = if(isSystemInDarkTheme()) primaryButtonColorLight else primaryButtonColorLight

val ColorScheme.statisticsEmptyColor: Color @Composable @ReadOnlyComposable get() = if(isSystemInDarkTheme()) statisticsEmptyColorLight else statisticsEmptyColorLight