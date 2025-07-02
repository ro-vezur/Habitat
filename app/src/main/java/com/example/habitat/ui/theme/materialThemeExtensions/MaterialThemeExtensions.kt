package com.example.habitat.ui.theme.materialThemeExtensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.habitat.ui.theme.responsiveLayout.ResponsiveLayout
import com.example.habitat.ui.theme.responsiveLayout.localResponsiveLayout

val MaterialTheme.responsiveLayout: ResponsiveLayout
@Composable @ReadOnlyComposable get() = localResponsiveLayout.current