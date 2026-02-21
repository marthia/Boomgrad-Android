package me.marthia.app.boomgrad.presentation.tour.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.TextFieldElement
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme

// ---------------------------------------------------------------------------
// ItinerarySection.kt
// Drop this file next to CreateEditTourScreen.kt and wire it into the screen.
// ---------------------------------------------------------------------------

// ── How to wire into CreateEditTourScreen ──────────────────────────────────
//
//  1. Add to CreateEditTourScreen parameters:
//       itinerary: List<ItineraryStop> = emptyList(),
//       onItineraryChange: (List<ItineraryStop>) -> Unit = {},
//       onPickOnMapClick: (stopIndex: Int) -> Unit = {},
//
//  2. Add inside the Column of CreateEditTourScreen, after MiscInfo():
//       ItinerarySection(
//           stops = itinerary,
//           onStopsChange = onItineraryChange,
//           onPickOnMapClick = onPickOnMapClick,
//       )
// ──────────────────────────────────────────────────────────────────────────

/**
 * Full itinerary editor section.
 *
 * Manages an ordered list of [ItineraryStop]s.  Each stop card exposes:
 *  - title (required)
 *  - description
 *  - duration in minutes
 *  - location name
 *  - lat / lng (typed manually OR filled via [onPickOnMapClick])
 *  - move-up / move-down reordering
 *  - remove
 *
 * [stopOrder] on every stop is kept in sync (1-based) automatically whenever
 * the list changes, so the caller never has to worry about it.
 */
@Composable
fun ItinerarySection(
    modifier: Modifier = Modifier,
    stops: List<ItineraryStop> = emptyList(),
    onStopsChange: (List<ItineraryStop>) -> Unit = {},
    onPickOnMapClick: (stopIndex: Int) -> Unit = {},
) {
    // ── header row ──────────────────────────────────────────────────────────
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.label_itinerary_section),
            style = MaterialTheme.typography.labelLarge,
        )

        IconText(
            text = {
                Text(
                    text = stringResource(R.string.action_add_stop),
                    style = MaterialTheme.typography.titleMedium,
                    color = Theme.colors.materialTheme.primary,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Theme.colors.materialTheme.primary,
                    contentDescription = stringResource(R.string.action_add_stop),
                )
            },
            onClick = {
                val newStop = ItineraryStop(
                    stopOrder = stops.size + 1,
                    title = "",
                    date = ""
                )
                onStopsChange(stops + newStop)
            },
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    // ── empty state ─────────────────────────────────────────────────────────
    if (stops.isEmpty()) {
        CardElement(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.label_itinerary_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Theme.colors.textHelp,
                    textAlign = TextAlign.Center,
                )
            }
        }
        return
    }

    // ── stop cards ──────────────────────────────────────────────────────────
    stops.forEachIndexed { index, stop ->
        ItineraryStopCard(
            stop = stop,
            index = index,
            isFirst = index == 0,
            isLast = index == stops.lastIndex,
            onStopChange = { updated ->
                onStopsChange(
                    stops.toMutableList()
                        .also { it[index] = updated }
                        .reindexed(),
                )
            },
            onRemove = {
                onStopsChange(
                    stops.toMutableList()
                        .also { it.removeAt(index) }
                        .reindexed(),
                )
            },
            onMoveUp = {
                if (index > 0) {
                    onStopsChange(
                        stops.toMutableList()
                            .also { list ->
                                val tmp = list[index - 1]
                                list[index - 1] = list[index]
                                list[index] = tmp
                            }
                            .reindexed(),
                    )
                }
            },
            onMoveDown = {
                if (index < stops.lastIndex) {
                    onStopsChange(
                        stops.toMutableList()
                            .also { list ->
                                val tmp = list[index + 1]
                                list[index + 1] = list[index]
                                list[index] = tmp
                            }
                            .reindexed(),
                    )
                }
            },
            onPickOnMapClick = { onPickOnMapClick(index) },
        )

        // timeline connector between cards (not after last)
        if (index < stops.lastIndex) {
            ItineraryConnector()
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

// ── single stop card ────────────────────────────────────────────────────────

@Composable
private fun ItineraryStopCard(
    stop: ItineraryStop,
    index: Int,
    isFirst: Boolean,
    isLast: Boolean,
    onStopChange: (ItineraryStop) -> Unit,
    onRemove: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onPickOnMapClick: () -> Unit,
) {
    // keep lat/lng as raw text so the user can type partial values
    var latText by remember(stop.latitude) {
        mutableStateOf(stop.latitude?.toString().orEmpty())
    }
    var lngText by remember(stop.longitude) {
        mutableStateOf(stop.longitude?.toString().orEmpty())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        // ── timeline bullet + lines ────────────────────────────────────────
        ItineraryBullet(
            order = stop.stopOrder,
            isFirst = isFirst,
            isLast = isLast,
        )

        Spacer(modifier = Modifier.width(8.dp))

        // ── card content ──────────────────────────────────────────────────
        CardElement(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 0.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                // header: stop label + reorder + remove
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.label_stop_number, stop.stopOrder),
                        style = MaterialTheme.typography.titleSmall,
                        color = Theme.colors.materialTheme.primary,
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        // move up
                        IconButton(
                            onClick = onMoveUp,
                            enabled = !isFirst,
                            modifier = Modifier.size(32.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = stringResource(R.string.cd_move_stop_up),
                                tint = if (!isFirst)
                                    Theme.colors.materialTheme.primary
                                else
                                    Theme.colors.materialTheme.onSurface.copy(alpha = 0.3f),
                            )
                        }
                        // move down
                        IconButton(
                            onClick = onMoveDown,
                            enabled = !isLast,
                            modifier = Modifier.size(32.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = stringResource(R.string.cd_move_stop_down),
                                tint = if (!isLast)
                                    Theme.colors.materialTheme.primary
                                else
                                    Theme.colors.materialTheme.onSurface.copy(alpha = 0.3f),
                            )
                        }
                        // remove
                        IconButton(
                            onClick = onRemove,
                            modifier = Modifier.size(32.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.cd_remove_stop),
                                tint = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                // title (required)
                TextFieldElement(
                    modifier = Modifier.fillMaxWidth(),
                    value = stop.title,
                    label = {
                        Text(
                            text = stringResource(R.string.label_stop_title),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    placeholder = { Text(stringResource(R.string.placeholder_stop_title)) },
                    onValueChange = { onStopChange(stop.copy(title = it)) },
                )

                // description
                TextFieldElement(
                    modifier = Modifier.fillMaxWidth(),
                    value = stop.description.orEmpty(),
                    label = {
                        Text(
                            text = stringResource(R.string.label_stop_description),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    placeholder = { Text(stringResource(R.string.placeholder_stop_description)) },
                    onValueChange = { onStopChange(stop.copy(description = it.ifBlank { null })) },
                    minLines = 2,
                )

                // duration + location name side by side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    TextFieldElement(
                        modifier = Modifier.weight(1f),
                        value = stop.durationMinutes?.toString().orEmpty(),
                        label = {
                            Text(
                                text = stringResource(R.string.label_stop_duration),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        placeholder = { Text(stringResource(R.string.placeholder_stop_duration)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { raw ->
                            onStopChange(stop.copy(durationMinutes = raw.toIntOrNull()))
                        },
                    )

                    TextFieldElement(
                        modifier = Modifier.weight(2f),
                        value = stop.location.orEmpty(),
                        label = {
                            Text(
                                text = stringResource(R.string.label_stop_location),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        placeholder = { Text(stringResource(R.string.placeholder_stop_location)) },
                        onValueChange = { onStopChange(stop.copy(location = it.ifBlank { null })) },
                    )
                }

                // lat + lng + pick-on-map
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextFieldElement(
                        modifier = Modifier.weight(1f),
                        value = latText,
                        label = {
                            Text(
                                text = stringResource(R.string.label_stop_latitude),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        placeholder = { Text(stringResource(R.string.placeholder_stop_latitude)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = { raw ->
                            latText = raw
                            onStopChange(stop.copy(latitude = raw.toDoubleOrNull()))
                        },
                    )

                    TextFieldElement(
                        modifier = Modifier.weight(1f),
                        value = lngText,
                        label = {
                            Text(
                                text = stringResource(R.string.label_stop_longitude),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        placeholder = { Text(stringResource(R.string.placeholder_stop_longitude)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        onValueChange = { raw ->
                            lngText = raw
                            onStopChange(stop.copy(longitude = raw.toDoubleOrNull()))
                        },
                    )

                    // map picker button
                    IconButton(
                        onClick = onPickOnMapClick,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(Theme.colors.materialTheme.primary.copy(alpha = 0.12f)),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = stringResource(R.string.cd_pick_on_map),
                            tint = Theme.colors.materialTheme.primary,
                        )
                    }
                }

                // show coordinates chip if both are set
                if (stop.latitude != null && stop.longitude != null) {
                    CoordinateChip(latitude = stop.latitude, longitude = stop.longitude)
                }
            }
        }
    }
}

// ── visual helpers ───────────────────────────────────────────────────────────

/** Numbered bullet that sits on the timeline spine. */
@Composable
private fun ItineraryBullet(
    order: Int,
    isFirst: Boolean,
    isLast: Boolean,
) {
    val bulletSize = 36.dp
    val spineWidth = 2.dp
    val spineColor = Theme.colors.materialTheme.primary.copy(alpha = 0.4f)

    Box(
        modifier = Modifier
            .width(bulletSize)
            .fillMaxHeight(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // top spine (hidden for first item)
            if (!isFirst) {
                Box(
                    modifier = Modifier
                        .width(spineWidth)
                        .height(12.dp)
                        .background(spineColor)
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // numbered circle
            Box(
                modifier = Modifier
                    .size(bulletSize)
                    .clip(CircleShape)
                    .background(Theme.colors.materialTheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = order.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Theme.colors.materialTheme.onPrimary,
                )
            }

            // bottom spine (hidden for last item)
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(spineWidth)
                        .height(12.dp)
                        .background(spineColor)
                )
            }
        }
    }
}

/** Dashed spine connecting two stop cards. */
@Composable
private fun ItineraryConnector() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // aligns with the centre of the bullet (bulletSize / 2 = 18.dp, minus spine half = 1.dp)
        Box(modifier = Modifier.width(17.dp))

        Box(
            modifier = Modifier
                .width(2.dp)
                .height(12.dp)
                .background(Theme.colors.materialTheme.primary.copy(alpha = 0.4f))
        )
    }
}

/** Small chip showing the resolved lat/lng for quick confirmation. */
@Composable
private fun CoordinateChip(latitude: Double, longitude: Double) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = Theme.colors.materialTheme.primary.copy(alpha = 0.08f),
        modifier = Modifier.wrapContentSize(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Theme.colors.materialTheme.primary,
                modifier = Modifier.size(14.dp),
            )
            Text(
                text = "%.4f, %.4f".format(latitude, longitude),
                style = MaterialTheme.typography.labelSmall,
                color = Theme.colors.materialTheme.primary,
            )
        }
    }
}

// ── private extension ────────────────────────────────────────────────────────

/** Reassigns [ItineraryStop.stopOrder] (1-based) after any list mutation. */
private fun List<ItineraryStop>.reindexed(): List<ItineraryStop> =
    mapIndexed { i, stop -> stop.copy(stopOrder = i + 1) }

// ── preview ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true, locale = "fa")
@Composable
private fun PreviewItinerarySection() {
    val sampleStops = listOf(
        ItineraryStop(
            stopOrder = 1,
            title = "City Gate",
            description = "Historic entrance to the old city.",
            durationMinutes = 20,
            location = "Grand Bazaar Gate",
            latitude = 35.6751,
            longitude = 51.4220,
            date = ""
        ),
        ItineraryStop(
            stopOrder = 2,
            title = "Milad Tower",
            description = null,
            durationMinutes = 60,
            location = "Milad Tower",
            latitude = 35.7448,
            longitude = 51.3753,
            date = ""
        ),
        ItineraryStop(
            stopOrder = 3,
            title = "Lunch break",
            durationMinutes = 45,
            date = ""
        ),
    )

    AppTheme {
        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp),
            ) {
                ItinerarySection(
                    stops = sampleStops,
                    onStopsChange = {},
                    onPickOnMapClick = {},
                )
            }

        }
    }
}