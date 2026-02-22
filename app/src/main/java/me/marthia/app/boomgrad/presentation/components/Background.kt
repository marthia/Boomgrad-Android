package me.marthia.app.boomgrad.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import me.marthia.app.boomgrad.presentation.theme.Theme


@Composable
fun BackgroundElement(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    gradient: List<Color> = Theme.colors.uiBackgroundGradient,
    contentColor: Color = Theme.colors.materialTheme.onSurfaceVariant,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(gradient),
                shape = shape,
            ),
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

@Composable
fun ContainerElement(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    gradient: List<Color> = Theme.colors.uiContainerGradient,
    contentColor: Color = Theme.colors.materialTheme.onSurfaceVariant,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = Brush.horizontalGradient(gradient),
                shape = shape,
            ),
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}