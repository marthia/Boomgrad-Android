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

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat

private val LightColorPalette = AppColorScheme(
    materialTheme = lightColorScheme(
        // Primary colors - using your brand green
        primary = Green8,
        onPrimary = Neutral0, // White text on green
        primaryContainer = Green8.copy(alpha = 0.15f), // Light green container
        onPrimaryContainer = DarkGreen11, // Dark green text on light container
        inversePrimary = Green4, // For dark theme contrast

        // Secondary colors - using your Hana/Khaki accent
        secondary = Hana7,
        onSecondary = Neutral0, // White text on Hana
        secondaryContainer = Khaki1,
        onSecondaryContainer = Hana7,

        // Tertiary colors - using your Turquoise accent
        tertiary = Turquoise8,
        onTertiary = Neutral0,
        tertiaryContainer = Turquoise8.copy(alpha = 0.25f),
        onTertiaryContainer = Turquoise8,

        // Background colors
        background = Green0, // Your uiBackground
        onBackground = Green8, // Your textPrimary

        // Surface colors - creating hierarchy
        surface = Neutral0, // Your surface (white)
        onSurface = Green8, // Your textPrimary
        surfaceVariant = Green1, // Light variant
        onSurfaceVariant = Neutral7, // Your textSecondary
        surfaceTint = Green8, // Tint overlay

        // Surface containers - creating elevation levels
        surfaceDim = Neutral8, // Darkest surface
        surfaceBright = Neutral0, // Brightest (white)
        surfaceContainerLowest = Neutral0, // Level 0
        surfaceContainerLow = Green0, // Level 1 - very light green tint
        surfaceContainer = Green1, // Level 2 - light green tint
        surfaceContainerHigh = Green2, // Level 3 - your outline color
        surfaceContainerHighest = Green3, // Level 4 - your uiFloated

        // Inverse colors (for dark elements on light theme)
        inverseSurface = Green8,
        inverseOnSurface = Neutral0,

        // Error colors
        error = FunctionalRed5,
        onError = Neutral0,
        errorContainer = FunctionalRed1,
        onErrorContainer = FunctionalRed5,

        // Outline colors
        outline = Color(0xFFE4F3F2), // Your uiBorder
        outlineVariant = Khaki4, // Your outlineSecondary

        // Scrim for modals/dialogs
        scrim = Color.Black.copy(alpha = 0.32f),
    ),
    textHelp = Color(0xFF545454),
    uiBorder = Grey0,
    uiBackgroundGradient = listOf(Turquoise8.copy(alpha = 0.25f), Turquoise0),
    uiContainerGradient = listOf(Green8.copy(0.15f), Green1),
    gradientGreen8Green3 = listOf(Green8, Color(0xff00B8B5)),
    gradientTurq4Green4 = listOf(Turquoise4.copy(alpha = 0.4f), Green4.copy(alpha = 0.4f)),
    gradientTurqaa4Green4 = listOf(Turquoise4, Green4),
    gradientTurq8Green8 = listOf(Turquoise8, Green8),


    )

private val DarkColorPalette = AppColorScheme(
    materialTheme = darkColorScheme(
        primary = Green4,
        onPrimary = Green9,
        primaryContainer = Green8,
        onPrimaryContainer = Green1,
        inversePrimary = Green8,

        secondary = Hana7,
        onSecondary = Color(0xFF4A2800),
        secondaryContainer = Color(0xFF6B3C00),
        onSecondaryContainer = Khaki1,

        tertiary = Turquoise4,
        onTertiary = Color(0xFF003735),
        tertiaryContainer = Color(0xFF005350),
        onTertiaryContainer = Turquoise0,

        background = Color(0xFF191C1B),
        onBackground = Color(0xFFE1E3E0),

        surface = Color(0xFF191C1B),
        onSurface = Color(0xFFE1E3E0),
        surfaceVariant = Color(0xFF3F4945),
        onSurfaceVariant = Color(0xFFBEC9C4),
        surfaceTint = Green4,

        surfaceDim = Color(0xFF191C1B),
        surfaceBright = Color(0xFF3F4240),
        surfaceContainerLowest = Color(0xFF0F1311),
        surfaceContainerLow = Color(0xFF1A1D1C),
        surfaceContainer = Color(0xFF1E2120),
        surfaceContainerHigh = Color(0xFF282B2A),
        surfaceContainerHighest = Color(0xFF333635),

        inverseSurface = Color(0xFFE1E3E0),
        inverseOnSurface = Color(0xFF2E312F),

        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),

        outline = Green7,
        outlineVariant = Color(0xFF3F4945),

        scrim = Color.Black,
    ),
    textHelp = Neutral1,
    uiBorder = Neutral3,
    uiBackgroundGradient = listOf(DarkGreen11, DarkGreen6),
    uiContainerGradient = listOf(DarkGreen8, DarkGreen5),
    gradientTurq8Green8 = listOf(DarkGreen10, DarkGreen6),
    gradientGreen8Green3 = listOf(DarkGreen10, DarkGreen6),
    gradientTurq4Green4 = listOf(DarkGreen6.copy(alpha = 0.4f), DarkGreen4.copy(alpha = 0.4f)),
    gradientTurqaa4Green4 = listOf(DarkGreen11, DarkGreen6),

)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    // app only has Persian language
    val layoutDirection = LayoutDirection.Rtl

    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection,
        LocalAppColorScheme provides colors,
    ) {
        MaterialTheme(
            colorScheme = colors.materialTheme,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

object Theme {
    val colors: AppColorScheme
        @Composable
        get() = LocalAppColorScheme.current
}

/**
 * AppColors custom Color Palette
 */
@Immutable
data class AppColorScheme(
    val materialTheme: ColorScheme,
    val textHelp: Color,
    val uiBorder: Color,
    val gradientGreen8Green3: List<Color>,
    val gradientTurq4Green4: List<Color>,
    val gradientTurqaa4Green4: List<Color>,
    val gradientTurq8Green8: List<Color>,
    val uiBackgroundGradient: List<Color>,
    val uiContainerGradient: List<Color>,
    val interactivePrimary: List<Color> = gradientGreen8Green3,
    val interactiveSecondary: List<Color> = gradientTurq4Green4,
    val interactiveMask: List<Color> = gradientTurq4Green4,
)

private val LocalAppColorScheme = staticCompositionLocalOf<AppColorScheme> {
    error("No JetsnackColorPalette provided")
}
