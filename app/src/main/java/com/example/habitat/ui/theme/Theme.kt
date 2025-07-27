package com.example.habitat.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.example.habitat.ui.theme.responsiveLayout.localResponsiveLayout
import com.example.habitat.ui.theme.responsiveLayout.provideResponsiveLayout

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColorLight,
    secondary = secondaryColorLight,
    background = backgroundColorLight
)

@Composable
fun HabitatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    if(!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
        }
    }

    CompositionLocalProvider(localResponsiveLayout provides provideResponsiveLayout()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = provideResponsiveTypography(),
            content = content
        )
    }
}