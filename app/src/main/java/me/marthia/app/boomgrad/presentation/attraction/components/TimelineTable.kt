package me.marthia.app.boomgrad.presentation.attraction.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.presentation.theme.Theme


enum class LineType {
    NONE,
    NORMAL,
    FIRST,
    LAST
}


@Composable
fun TimelineRow(
    value: String,
    label: String,
    lineType: LineType,
    modifier: Modifier = Modifier,
) {
    val circleRadius = 6.dp
    val lineWidth = 2.dp
    val circleColor = Theme.colors.materialTheme.primary
    val lineColor = Theme.colors.materialTheme.primary
    val density = LocalDensity.current

    val parentHeight = remember { mutableStateOf(1.dp) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                parentHeight.value = with(density) { coordinates.size.height.toDp() }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Theme.colors.textHelp,
            modifier = Modifier
                .weight(1f)
                .padding(top = 4.dp),
        )

        Canvas(
            modifier = Modifier
                .height(parentHeight.value)
                .weight(1f),
        ) {

            val circleRadiusPx = circleRadius.toPx()
            val lineWidthPx = lineWidth.toPx()

            val circleCenter = Offset(
                x = circleRadiusPx,
                y = circleRadiusPx + 16.dp.toPx(),
            )

            // Draw top line
            if (lineType == LineType.NORMAL || lineType == LineType.LAST) {
                drawLine(
                    color = lineColor,
                    start = Offset(circleCenter.x, 0f),
                    end = Offset(circleCenter.x, circleCenter.y - circleRadiusPx),
                    strokeWidth = lineWidthPx,
                )
            }

            // Draw circle
            drawCircle(
                color = circleColor,
                radius = circleRadiusPx,
                center = circleCenter,
            )

            // Draw bottom line
            if (lineType == LineType.NORMAL || lineType == LineType.FIRST) {
                drawLine(
                    color = lineColor,
                    start = Offset(circleCenter.x, circleCenter.y + circleRadiusPx),
                    end = Offset(circleCenter.x, size.height),
                    strokeWidth = lineWidthPx,
                )
            }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = if (lineType == LineType.FIRST || lineType == LineType.LAST)
                Theme.colors.materialTheme.primary else Theme.colors.materialTheme.onSurfaceVariant,
        )
    }
}

@Composable
fun TimelineUi(
    timeLines: List<TimeLine>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        timeLines.forEachIndexed { index, timeLine ->
            val lineType = when {
                timeLines.size == 1 -> LineType.NONE
                index == 0 -> LineType.FIRST
                index == timeLines.size - 1 -> LineType.LAST
                else -> LineType.NORMAL
            }

            TimelineRow(
                label = timeLine.label,
                value = timeLine.value,
                lineType = lineType,
            )
        }
    }
}

data class TimeLine(
    val label: String,
    val value: String
)

@Preview(showBackground = true)
@Composable
fun TimelinePreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                Text(
                    text = "Trip Itinerary",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 24.dp),
                )

                TimelineUi(
                    timeLines = listOf(
                        TimeLine(label = "New York, NY", value = "2 hours"),
                        TimeLine(label = "Philadelphia, PA", value = "1.5 hours"),
                        TimeLine(label = "Baltimore, MD", value = "45 minutes"),
                        TimeLine(label = "Washington, DC", value = "50 minutes"),
                    ),
                )
            }
        }
    }
}