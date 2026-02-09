/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.marthia.app.boomgrad.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.marthia.app.boomgrad.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
)

private val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal),
    Font(R.font.karla_bold, FontWeight.Bold),
)


private val PeydaFont = FontFamily(
    Font(R.font.peydafanum_thin, FontWeight.Thin),
    Font(R.font.peydafanum_extralight, FontWeight.ExtraLight),
    Font(R.font.peydafanum_light, FontWeight.Light), // Add this
    Font(R.font.peydafanum_regular, FontWeight.Normal),
    Font(R.font.peydafanum_medium, FontWeight.Medium),
    Font(R.font.peydafanum_semibold, FontWeight.SemiBold),
    Font(R.font.peydafanum_bold, FontWeight.Bold),
    Font(R.font.peydafanum_extrabold, FontWeight.ExtraBold),
)
val a = Typography()
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 57.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 72.sp,
        letterSpacing = 0.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 45.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 58.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 48.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 34.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = PeydaFont,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),
)