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

private val LightColorPalette = JetsnackColors(
    brand = Green8,
    brandSecondary = Hana7,
    uiBackground = Green0,
    uiContainer = Green8.copy(0.15f),
    uiBackgroundGradient = listOf(Turquoise8.copy(alpha = 0.25f), Turquoise0),
    uiContainerGradient = listOf(Green8.copy(0.15f), Green1),
    uiBorder = Grey0,
    uiFloated = Color(0xFFDEF1F0),
    textField = Grey0.copy(alpha = 0.40f),
    textSecondary = Neutral7,
    textPrimary = Green8,
    textHelp = Neutral5,
    textInteractive = Neutral0,
    textLink = Green9,
    iconSecondary = Neutral7,
    iconInteractive = Neutral0,
    iconInteractiveInactive = Neutral1,
    error = FunctionalRed,
    outline = Color(0xFFE4F3F2),
    gradient2_1 = listOf(Green8, Color(0xff00B8B5)),
    gradient2_2 = listOf(Turquoise4.copy(alpha = 0.4f), Green4.copy(alpha = 0.4f)),
    gradient9_10 = listOf(Turquoise4, Green4),
    isDark = false,
)

private val DarkColorPalette = JetsnackColors(
    brand = DarkGreen4,
    brandSecondary = Hana11,
    uiBackground = Neutral8,
    uiContainer = DarkGreen2,
    uiBackgroundGradient = listOf(DarkGreen11, DarkGreen6),
    uiContainerGradient = listOf(DarkGreen8, DarkGreen5),
    uiBorder = Neutral3,
    uiFloated = FunctionalDarkGrey,
    textField = Grey0.copy(alpha = 0.40f),
    textPrimary = DarkGreen4,
    textSecondary = Neutral0,
    textHelp = Neutral1,
    textInteractive = Neutral7,
    textLink = DarkGreen5,
    iconPrimary = DarkGreen11,
    iconSecondary = Neutral0,
    iconInteractive = Neutral7,
    iconInteractiveInactive = Neutral6,
    error = FunctionalRedDark,
    outline = Green7,
    gradient2_1 = listOf(DarkGreen10, DarkGreen6),
    gradient2_2 = listOf(DarkGreen6.copy(alpha = 0.4f), DarkGreen4.copy(alpha = 0.4f)),
    gradient9_10 = listOf(DarkGreen11, DarkGreen6),
    isDark = true,
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
        LocalJetsnackColors provides colors,
    ) {
        MaterialTheme(
            colorScheme = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

object BaseTheme {
    val colors: JetsnackColors
        @Composable
        get() = LocalJetsnackColors.current
}

/**
 * Jetsnack custom Color Palette
 */
@Immutable
data class JetsnackColors(
    val gradient2_1: List<Color>,
    val gradient2_2: List<Color>,
    val gradient9_10: List<Color>,
    val outline: Color,
    val brand: Color,
    val textField: Color,
    val brandSecondary: Color,
    val uiBackground: Color,
    val uiBackgroundGradient: List<Color>,
    val uiContainerGradient: List<Color>,
    val uiBorder: Color,
    val uiFloated: Color,
    val uiContainer: Color,
    val interactivePrimary: List<Color> = gradient2_1,
    val interactiveSecondary: List<Color> = gradient2_2,
    val interactiveMask: List<Color> = gradient2_2,
    val textPrimary: Color = brand,
    val textSecondary: Color,
    val textHelp: Color,
    val textInteractive: Color,
    val textLink: Color,
    val iconPrimary: Color = brand,
    val iconSecondary: Color,
    val iconInteractive: Color,
    val iconInteractiveInactive: Color,
    val error: Color,
    val notificationBadge: Color = error,
    val isDark: Boolean,
)

private val LocalJetsnackColors = staticCompositionLocalOf<JetsnackColors> {
    error("No JetsnackColorPalette provided")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colorScheme] in preference to [BaseTheme.colors].
 */
fun debugColors(darkTheme: Boolean, debugColor: Color = Color.Magenta) = ColorScheme(
    primary = debugColor,
    onPrimary = debugColor,
    primaryContainer = debugColor,
    onPrimaryContainer = debugColor,
    inversePrimary = debugColor,
    secondary = debugColor,
    onSecondary = debugColor,
    secondaryContainer = debugColor,
    onSecondaryContainer = debugColor,
    tertiary = debugColor,
    onTertiary = debugColor,
    tertiaryContainer = debugColor,
    onTertiaryContainer = debugColor,
    background = debugColor,
    onBackground = debugColor,
    surface = debugColor,
    onSurface = debugColor,
    surfaceVariant = debugColor,
    onSurfaceVariant = debugColor,
    surfaceTint = debugColor,
    inverseSurface = debugColor,
    inverseOnSurface = debugColor,
    error = debugColor,
    onError = debugColor,
    errorContainer = debugColor,
    onErrorContainer = debugColor,
    outline = debugColor,
    outlineVariant = debugColor,
    scrim = debugColor,
    surfaceBright = debugColor,
    surfaceDim = debugColor,
    surfaceContainer = debugColor,
    surfaceContainerHigh = debugColor,
    surfaceContainerHighest = debugColor,
    surfaceContainerLow = debugColor,
    surfaceContainerLowest = debugColor,
)
