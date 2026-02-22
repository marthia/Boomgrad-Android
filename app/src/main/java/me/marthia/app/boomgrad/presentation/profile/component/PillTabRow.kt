package me.marthia.app.boomgrad.presentation.profile.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun PillTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    selectedColor: Color = MaterialTheme.colorScheme.background,
    selectedContentColor: Color = MaterialTheme.colorScheme.onBackground,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    val selectedOffset = remember { Animatable(0f) }
    val selectedWidth = remember { Animatable(0f) }
    val tabWidths = remember { mutableStateListOf<Float>().apply { repeat(tabs.size) { add(0f) } } }
    val density = LocalDensity.current

    LaunchedEffect(selectedIndex, tabWidths.toList()) {
        val offset = tabWidths.take(selectedIndex).sum()
        val width = tabWidths.getOrElse(selectedIndex) { 0f }
        launch { selectedOffset.animateTo(offset, spring(dampingRatio = 0.7f, stiffness = 400f)) }
        launch { selectedWidth.animateTo(width, spring(dampingRatio = 0.7f, stiffness = 400f)) }
    }

    Box(
        modifier = modifier
            .height(48.dp)
            .background(containerColor, RoundedCornerShape(50))
            .padding(4.dp),
    ) {
        // Animated pill background
        Box(
            modifier = Modifier
                .offset { IntOffset(selectedOffset.value.roundToInt(), 0) }
                .width(with(density) { selectedWidth.value.toDp() })
                .fillMaxHeight()
                .background(selectedColor, RoundedCornerShape(50))
        )

        // Tabs
        Row(modifier = Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { index, title ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .onSizeChanged { tabWidths[index] = it.width.toFloat() }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onTabSelected(index) },
                        )
                        .padding(vertical = 8.dp),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selectedIndex == index) selectedContentColor else unselectedContentColor,
                    )
                }
            }
        }
    }
}