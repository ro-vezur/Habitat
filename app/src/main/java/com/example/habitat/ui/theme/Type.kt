package com.example.habitat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.habitat.ui.theme.windowSizeType.WindowSizeTypes
import com.example.habitat.ui.theme.windowSizeType.getWindowSizeType

@Composable
fun provideResponsiveTypography(): Typography {
    val windowSizeType = getWindowSizeType()

    return when (windowSizeType) {
        WindowSizeTypes.PHONE -> phoneTypography
        WindowSizeTypes.TABLET -> tabletTypography
        WindowSizeTypes.EXPANDED -> expandedTypography
    }
}

val phoneTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),
    displayMedium = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 36.sp
    ),
    displaySmall = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp
    ),
    headlineLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp
    ),
    headlineMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 26.sp
    ),
    headlineSmall = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
    ),
    titleMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    ),
    titleSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 14.sp
    )
)

val tabletTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 46.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 46.sp
    ),
    displayMedium = TextStyle(
        fontSize = 42.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 42.sp
    ),
    displaySmall = TextStyle(
        fontSize = 38.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 38.sp
    ),
    headlineLarge = TextStyle(
        fontSize = 34.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 34.sp
    ),
    headlineMedium = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 32.sp
    ),
    headlineSmall = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 26.sp
    ),
    titleSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 26.sp
    ),
    bodySmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 26.sp
    ),
    labelMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 22.sp
    ),
    labelSmall = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    )
)

val expandedTypography = Typography(

)
