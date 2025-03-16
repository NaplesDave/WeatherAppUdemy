package com.techbyking.weatherappudemy.utils


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import com.techbyking.weatherappudemy.ui.theme.Typography


//Theme sets the font family for the app
// GoogleFontProvider creates the font family to use
//WeatherTyppgraphy.kt describes the font sizes and colors to use

val weatherTypography = Typography(
    displayLarge = Typography.displayLarge.copy(
        fontFamily = appFontFamily, // See GoogleFontProvider.kt for setup
        color =  Color.White,
        fontSize = 100.sp),
    displayMedium = Typography.displayMedium.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
        ),
    displaySmall = Typography.displaySmall.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),
    titleLarge = Typography.titleLarge.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),
    titleMedium = Typography.titleMedium.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),
    titleSmall = Typography.titleSmall.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),
    bodyLarge = Typography.bodyLarge.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),
    bodyMedium =  Typography.bodyMedium.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),
    bodySmall = Typography.bodySmall.copy(
        fontFamily = appFontFamily,
        color =  Color.White,
    ),


)