package me.marthia.app.boomgrad.presentation.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.theme.Theme

// ── Constants (internal to the package) ──────────────────────────────────────

internal val TextIconSpacing = 2.dp
internal val BottomNavHeight = 56.dp
internal val AppBottomNavIconPadding = 8.dp
internal val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
internal val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
internal val BottomNavigationItemPadding = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)

// ── Indicator ─────────────────────────────────────────────────────────────────

@Composable
internal fun AppBottomNavIndicator(
    backgroundGradient: List<Color> = Theme.colors.interactivePrimary,
    shape: Shape = BottomNavIndicatorShape,
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(BottomNavigationItemPadding)
            .clip(shape)
            .background(Brush.horizontalGradient(colors = backgroundGradient)),
    )
}