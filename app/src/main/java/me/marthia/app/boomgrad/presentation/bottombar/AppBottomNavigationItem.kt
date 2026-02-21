package me.marthia.app.boomgrad.presentation.bottombar

import androidx.annotation.FloatRange
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.util.lerp
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.navigation.BottomBarDestination
import me.marthia.app.boomgrad.presentation.theme.Theme
import kotlin.math.roundToInt

@Composable
internal fun AppBottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier,
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = animSpec,
        label = "bottom_nav_item_progress",
    )

    AppBottomNavItemLayout(
        icon = icon,
        text = text,
        animationProgress = animationProgress,
        modifier = modifier
            .selectable(selected = selected, onClick = onSelected)
            .wrapContentSize(),
    )
}

@Composable
private fun AppBottomNavItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .layoutId("icon")
                    .padding(horizontal = TextIconSpacing),
                content = icon,
            )
            val scale = lerp(0.6f, 1f, animationProgress)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .padding(horizontal = TextIconSpacing)
                    .graphicsLayer {
                        alpha = animationProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = BottomNavLabelTransformOrigin
                    },
                content = text,
            )
        },
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)

        val iconY = (constraints.maxHeight - iconPlaceable.height) / 2
        val textY = (constraints.maxHeight - textPlaceable.height) / 2
        val textWidth = textPlaceable.width * animationProgress
        val iconX = ((constraints.maxWidth - textWidth - iconPlaceable.width) / 2f).roundToInt()
        val textX = iconX + iconPlaceable.width

        layout(constraints.maxWidth, constraints.maxHeight) {
            iconPlaceable.placeRelative(iconX, iconY)
            if (animationProgress != 0f) {
                textPlaceable.placeRelative(textX, textY)
            }
        }
    }
}

// ── Icon and label extracted for readability in AppBottomBar ──────────────────

@Composable
internal fun BoxScope.AppBottomNavIcon(
    section: BottomBarDestination,
    selected: Boolean,
    text: String,
) {
    if (!selected) {
        SurfaceElement(
            shape = CircleShape,
            contentColor = Theme.colors.materialTheme.surfaceDim,
            color = Theme.colors.materialTheme.background,
        ) {
            Icon(
                modifier = Modifier.padding(AppBottomNavIconPadding),
                painter = painterResource(id = section.icon),
                contentDescription = text,
            )
        }
    } else {
        Icon(
            modifier = Modifier.padding(AppBottomNavIconPadding),
            painter = painterResource(id = section.selectedIcon),
            tint = Theme.colors.materialTheme.surface,
            contentDescription = text,
        )
    }
}

@Composable
internal fun BoxScope.AppBottomNavLabel(text: String) {
    Text(
        text = text,
        color = Theme.colors.materialTheme.surface,
        style = MaterialTheme.typography.labelMedium,
        maxLines = 1,
    )
}