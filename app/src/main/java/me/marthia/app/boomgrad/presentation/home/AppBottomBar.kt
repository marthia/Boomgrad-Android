/*
 * Copyright 2020-2025 The Android Open Source Project
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

package me.marthia.app.boomgrad.presentation.home

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.os.ConfigurationCompat
import me.marthia.app.boomgrad.presentation.components.AppContainer
import me.marthia.app.boomgrad.presentation.components.SurfaceElement
import me.marthia.app.boomgrad.presentation.navigation.HomeSections
import me.marthia.app.boomgrad.presentation.spatialExpressiveSpring
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun AppBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50),
    gradient: List<Color> = Theme.colors.uiContainerGradient,
    contentColor: Color = Theme.colors.materialTheme.onPrimaryContainer,
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    AppContainer(
        modifier = modifier,
        gradient = gradient,
        shape = shape,
        contentColor = contentColor,
    ) {
        val springSpec = spatialExpressiveSpring<Float>()
        AppBottomNavLayout(
            selectedIndex = currentSection.ordinal,
            itemCount = routes.size,
            indicator = { JetsnackBottomNavIndicator() },
            animSpec = springSpec,
            modifier = Modifier.clip(shape = shape),
        ) {
            val configuration = LocalConfiguration.current
            val currentLocale: Locale =
                ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()

            tabs.forEach { section ->
                val selected = section == currentSection
//                val iconTint by animateColorAsState(
//                    if (selected) {
//                        JetsnackTheme.colors.iconSecondary
//                    } else {
//                        JetsnackTheme.colors.iconInteractiveInactive
//                    },
//                    label = "tint",
//                )

                val text = stringResource(section.title).uppercase(currentLocale)

                AppBottomNavigationItem(
                    icon = {
                        if (!selected) {
                            SurfaceElement(
                                shape = CircleShape,
                                contentColor = Theme.colors.materialTheme.surfaceDim,
                                color = Theme.colors.materialTheme.background,
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    painter = painterResource(id = section.icon),
                                    contentDescription = text,
                                )
                            }
                        } else {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                painter = painterResource(id = section.selectedIcon),
                                tint = Theme.colors.materialTheme.surface,
                                contentDescription = text,
                            )
                        }
                    },
                    text = {
                        Text(
                            text = text,
                            color = Theme.colors.materialTheme.surface,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                        )
                    },
                    selected = selected,
                    onSelected = { navigateToRoute(section.route) },
                    animSpec = springSpec,
                    modifier = BottomNavigationItemPadding.clip(BottomNavIndicatorShape),
                )
            }
        }
    }
}


/**
 * A composable that lays out a custom bottom navigation bar.
 *
 * This composable handles the overall layout, animation of selection states,
 * and placement of the indicator.  It takes care of animating the widths of the items
 * based on their selection state and the position of the indicator.
 *
 * @param selectedIndex The index of the currently selected item.
 * @param itemCount The total number of navigation items.
 * @param animSpec The animation specification to use for all animations.
 * @param indicator A composable function to draw the indicator (e.g., a colored bar).
 * @param modifier Modifier to apply to this layout.
 * @param content The composable content representing the navigation items.  Each item
 *                will be measured and placed within this layout.
 */
@Composable
private fun AppBottomNavLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // Track how "selected" each item is [0, 1]
    // Animatable is used to track the current animation state for each item.
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    // LaunchedEffect triggers animations whenever the target value changes.
    selectionFractions.forEachIndexed { index, selectionFraction ->
        val target = if (index == selectedIndex) 1f else 0f
        LaunchedEffect(target, animSpec) {
            selectionFraction.animateTo(target, animSpec)
        }
    }

    // Animate the position of the indicator
    val indicatorIndex = remember { Animatable(0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(targetIndicatorIndex) {
        indicatorIndex.animateTo(targetIndicatorIndex, animSpec)
    }

    Layout(
        modifier = modifier.height(BottomNavHeight),
        content = {
            content()
            Box(Modifier.layoutId("indicator"), content = indicator)
        },
    ) { measurables, constraints ->
        check(itemCount == (measurables.size - 1)) // account for indicator

        // Divide the width into n+1 slots and give the selected item 2 slots
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = 2 * unselectedWidth
        val indicatorMeasurable = measurables.first { it.layoutId == "indicator" }

        val itemPlaceables =
            measurables.filterNot { it == indicatorMeasurable }.mapIndexed { index, measurable ->
                // Animate item's width based upon the selection amount
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(
                    constraints.copy(
                        minWidth = width,
                        maxWidth = width,
                    ),
                )
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(
                minWidth = selectedWidth,
                maxWidth = selectedWidth,
            ),
        )

        layout(
            width = constraints.maxWidth,
            height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0,
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceables.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

/**
 * A composable representing a single bottom navigation item.
 *
 * This composable handles the selection state, the animation of the icon and text,
 * and the click interaction.
 *
 * @param icon A composable function to draw the icon.
 * @param text A composable function to draw the text label.
 * @param selected True if the item is currently selected.
 * @param onSelected A callback function to be invoked when the item is selected.
 * @param animSpec The animation specification to use for the icon/text animation.
 * @param modifier Modifier to apply to this item.
 */
@Composable
fun AppBottomNavigationItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier,
) {
    // Animate the icon/text positions within the item based on selection
    val animationProgress by animateFloatAsState(
        if (selected) 1f else 0f, animSpec,
        label = "animation progress",
    )
    JetsnackBottomNavItemLayout(
        icon = icon,
        text = text,
        animationProgress = animationProgress,
        modifier = modifier
            .selectable(selected = selected, onClick = onSelected)
            .wrapContentSize(),
    )
}

/**
 * A layout composable for the icon and text within a bottom navigation item.
 *
 * This composable uses a custom layout to position the icon and text,
 * animating their appearance based on the `animationProgress`.
 *
 * @param icon A composable function to draw the icon.
 * @param text A composable function to draw the text label.
 * @param animationProgress The progress of the animation (0.0 to 1.0).
 * @param modifier Modifier to apply to this item.
 */
@Composable
private fun JetsnackBottomNavItemLayout(
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

        placeTextAndIcon(
            textPlaceable,
            iconPlaceable,
            constraints.maxWidth,
            constraints.maxHeight,
            animationProgress,
        )
    }
}

/**
 * Places the text and icon within the bottom navigation item.
 *
 * This function calculates the positions of the icon and text based on
 * the `animationProgress` and the available space.  It ensures that the
 * text fades in and scales up while the icon remains visible.
 *
 * @param textPlaceable The Placeable for the text.
 * @param iconPlaceable The Placeable for the icon.
 * @param width The available width.
 * @param height The available height.
 * @param animationProgress The progress of the animation (0.0 to 1.0).
 * @return The MeasureResult containing the layout information.
 */
private fun MeasureScope.placeTextAndIcon(
    textPlaceable: Placeable,
    iconPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float,
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2

    val textWidth = textPlaceable.width * animationProgress
    val iconX = ((width - textWidth - iconPlaceable.width) / 2f).roundToInt()
    val textX = iconX + iconPlaceable.width

    return layout(width, height) {
        iconPlaceable.placeRelative(iconX, iconY)
        if (animationProgress != 0f) {
            textPlaceable.placeRelative(textX, textY)
        }
    }
}

@Composable
private fun JetsnackBottomNavIndicator(
    backgroundGradient: List<Color> = Theme.colors.interactivePrimary,
    shape: Shape = BottomNavIndicatorShape,
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(BottomNavigationItemPadding)
            .clip(shape)
            .background(
                Brush.horizontalGradient(
                    colors = backgroundGradient,
                ),
            ),
    )
}

private val TextIconSpacing = 2.dp
private val BottomNavHeight = 56.dp
private val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
private val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
private val BottomNavigationItemPadding = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)

@Preview
@Composable
private fun AppBottomNavPreview() {
    AppTheme {
        AppBottomBar(
            tabs = HomeSections.entries.toTypedArray(),
            currentRoute = "home/feed",
            navigateToRoute = { },
        )
    }
}
