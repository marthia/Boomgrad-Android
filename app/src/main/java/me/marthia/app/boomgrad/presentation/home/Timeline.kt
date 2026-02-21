package me.marthia.app.boomgrad.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.presentation.theme.Theme


enum class LineType {
    NONE,
    NORMAL,
    FIRST,
    LAST
}


@Composable
fun ItineraryItem(
    destination: String,
    duration: String?,
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
    ) {
        Box(
            modifier = Modifier.width((circleRadius * 2) + 16.dp),
        ) {
            Canvas(
                modifier = Modifier
                    .height(parentHeight.value)
                    .width(circleRadius * 2),
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
        }

        Column(
            modifier = Modifier

                .padding(
                    top = 4.dp,
                    bottom = 24.dp,
                    end = 16.dp,
                ),
        ) {
            duration?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colors.textHelp,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
            Text(
                text = destination,
                style = MaterialTheme.typography.labelLarge,
                color = if (lineType == LineType.FIRST || lineType == LineType.LAST)
                    Theme.colors.materialTheme.primary else Theme.colors.materialTheme.surfaceDim,
            )

        }
    }
}

@Composable
fun Itinerary(
    stops: List<ItineraryStop>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        stops.forEachIndexed { index, stop ->
            val lineType = when {
                stops.size == 1 -> LineType.NONE
                index == 0 -> LineType.FIRST
                index == stops.size - 1 -> LineType.LAST
                else -> LineType.NORMAL
            }

            ItineraryItem(
                destination = stop.title,
                duration = "${stop.date} (${
                    stringResource(
                        R.string.label_itinerary_duration_minitues,
                        stop.durationMinutes!!
                    )
                })",
                lineType = lineType,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItineraryPreview() {
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

                Itinerary(

                    stops = listOf(
                        ItineraryStop(
                            id = 1634,
                            stopOrder = 1,
                            title = "میدان نقش جهان",
                            description = "شنبه ۲۴ دی ساعت ۱۶",
                            date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                        ),
                        ItineraryStop(
                            id = 166,
                            stopOrder = 2,
                            title = "سی و سه پل",
                            description = "ساعت ۱۷",
                            date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                        ),
                        ItineraryStop(
                            id = 1555,
                            stopOrder = 3,
                            title = "کلیسای وانک",
                            description = "ساعت ۱۸",
                            date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                        ),
                        ItineraryStop(
                            id = 144,
                            stopOrder = 4,
                            title = "کلیسای مریم مقدس",
                            description = "ساعت ۱۹",
                            date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                        ),
                        ItineraryStop(
                            id = 122,
                            stopOrder = 5,
                            title = "میدان جلفا",
                            description = "شنبه ۲۴ دی ساعت ۲۰",
                            date = "چهارشنبه ۱۸ دی ۱۴۰۴",
                        ),
                    ),
                )
            }
        }
    }
}