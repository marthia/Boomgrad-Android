package me.marthia.app.boomgrad.presentation.tour.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Demographic
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourLevel
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.TextFieldElement
import me.marthia.app.boomgrad.presentation.profile.component.dashedBorder
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.tour.model.TourDetailUi
import me.marthia.app.boomgrad.presentation.tour.model.toUi
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun CreateEditTourScreen(
    tourDetail: TourDetailUi? = null,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onCityClick: () -> Unit = {},
    onTourTypeClick: () -> Unit = {},
    onDifficultyClick: () -> Unit = {},
    onDemographicClick: () -> Unit = {},
    onDurationChange: (String) -> Unit = {},
    onAddDateClick: () -> Unit = {},
    onDateChange: (String) -> Unit = {},
    onStartTimeChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onCapacityChange: (String) -> Unit = {},
    onCancellationPolicyChange: (String) -> Unit = {},
    onUploadImageClick: () -> Unit = {},
    onRequiredItemsChange: (String) -> Unit = {},
    onHighlightsChange: (String) -> Unit = {},
    onDescriptionMiscChange: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        CreateEditTour(
            tourDetail = tourDetail,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            onCityClick = onCityClick,
            onTourTypeClick = onTourTypeClick,
            onDifficultyClick = onDifficultyClick,
            onDemographicClick = onDemographicClick,
            onDurationChange = onDurationChange,
        )
        Schedules(
            tourDetail = tourDetail,
            onAddDateClick = onAddDateClick,
            onDateChange = onDateChange,
            onStartTimeChange = onStartTimeChange,
        )
        PriceAndCapacity(
            tourDetail = tourDetail,
            onPriceChange = onPriceChange,
            onCapacityChange = onCapacityChange,
            onCancellationPolicyChange = onCancellationPolicyChange,
        )
        TourImages(
            images = tourDetail?.images.orEmpty(),
            onUploadImageClick = onUploadImageClick,
        )
        MiscInfo(
            tourDetail = tourDetail,
            onRequiredItemsChange = onRequiredItemsChange,
            onHighlightsChange = onHighlightsChange,
            onDescriptionChange = onDescriptionMiscChange,
        )

        ItinerarySection(
            stops = tourDetail?.itinerary ?: emptyList(),
            onStopsChange = {},
            onPickOnMapClick = {},
        )
    }
}

@Composable
fun CreateEditTour(
    modifier: Modifier = Modifier,
    tourDetail: TourDetailUi? = null,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onCityClick: () -> Unit = {},
    onTourTypeClick: () -> Unit = {},
    onDifficultyClick: () -> Unit = {},
    onDemographicClick: () -> Unit = {},
    onDurationChange: (String) -> Unit = {},
) {
    var cityDropDownExpanded by remember { mutableStateOf(false) }
    var tourTypeExpanded by remember { mutableStateOf(false) }
    var levelDropDownExpanded by remember { mutableStateOf(false) }
    var demographicExpanded by remember { mutableStateOf(false) }

    CardElement(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.label_basic_info),
                style = MaterialTheme.typography.titleMedium,
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = tourDetail?.title.orEmpty(),
                label = {
                    Text(
                        text = stringResource(R.string.label_tour_title),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_tour_title))
                },
                onValueChange = onTitleChange,
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = tourDetail?.description.orEmpty(),
                label = {
                    Text(
                        text = stringResource(R.string.label_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_subtitle))
                },
                onValueChange = onDescriptionChange,
            )

            TextFieldElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        cityDropDownExpanded = !cityDropDownExpanded
                        onCityClick()
                    },
                label = {
                    Text(
                        text = stringResource(R.string.label_city),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                value = tourDetail?.city?.name ?: stringResource(R.string.label_select),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (cityDropDownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.cd_dropdown_arrow),
                    )
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        tourTypeExpanded = !tourTypeExpanded
                        onTourTypeClick()
                    },
                label = {
                    Text(
                        text = stringResource(R.string.label_tour_type),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                value = tourDetail?.category?.name ?: stringResource(R.string.label_select),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (tourTypeExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.cd_dropdown_arrow),
                    )
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        levelDropDownExpanded = !levelDropDownExpanded
                        onDifficultyClick()
                    },
                label = {
                    Text(
                        text = stringResource(R.string.label_difficulty_level),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                value = tourDetail?.level ?: stringResource(R.string.label_select),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (levelDropDownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.cd_dropdown_arrow),
                    )
                },
                onValueChange = {},
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextFieldElement(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            demographicExpanded = !demographicExpanded
                            onDemographicClick()
                        },
                    label = {
                        Text(
                            text = stringResource(R.string.label_demographic),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    value = tourDetail?.demographic ?: stringResource(R.string.label_select),
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = if (demographicExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.cd_dropdown_arrow),
                        )
                    },
                    onValueChange = {},
                )

                TextFieldElement(
                    modifier = Modifier.weight(1f),
                    value = tourDetail?.duration?.toString().orEmpty(),
                    label = {
                        Text(
                            text = stringResource(R.string.label_duration),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    placeholder = {
                        Text(stringResource(R.string.placeholder_duration))
                    },
                    onValueChange = onDurationChange,
                )
            }
        }
    }
}

@Composable
fun Schedules(
    tourDetail: TourDetailUi? = null,
    onAddDateClick: () -> Unit = {},
    onDateChange: (String) -> Unit = {},
    onStartTimeChange: (String) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.label_tour_dates),
            style = MaterialTheme.typography.labelLarge,
        )

        IconText(
            text = {
                Text(
                    text = stringResource(R.string.action_add_date),
                    style = MaterialTheme.typography.titleMedium,
                    color = Theme.colors.materialTheme.primary,
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_due_date_24),
                    tint = Theme.colors.materialTheme.primary,
                    contentDescription = stringResource(R.string.cd_due_date),
                )
            },
            onClick = onAddDateClick,
        )
    }

    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = tourDetail?.dueDate.orEmpty(),
                label = {
                    Text(
                        text = stringResource(R.string.label_tour_date),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_enter_text))
                },
                onValueChange = onDateChange,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_due_date_24),
                        tint = Theme.colors.materialTheme.primary,
                        contentDescription = stringResource(R.string.cd_due_date),
                    )
                },
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = tourDetail?.startTime.orEmpty(),
                label = {
                    Text(
                        text = stringResource(R.string.label_start_time),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_enter_text))
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_due_time_24),
                        tint = Theme.colors.materialTheme.primary,
                        contentDescription = stringResource(R.string.cd_due_time),
                    )
                },
                onValueChange = onStartTimeChange,
            )
        }
    }
}

@Composable
fun PriceAndCapacity(
    tourDetail: TourDetailUi? = null,
    onPriceChange: (String) -> Unit = {},
    onCapacityChange: (String) -> Unit = {},
    onCancellationPolicyChange: (String) -> Unit = {},
) {
    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.label_price_and_capacity),
                style = MaterialTheme.typography.titleMedium,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextFieldElement(
                    modifier = Modifier.weight(1f),
                    value = tourDetail?.price?.toString().orEmpty(),
                    label = {
                        Text(
                            text = stringResource(R.string.label_price_toman),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    placeholder = {
                        Text(stringResource(R.string.placeholder_enter_value))
                    },
                    onValueChange = onPriceChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                TextFieldElement(
                    modifier = Modifier.weight(1f),
                    value = tourDetail?.maxPeople?.toString().orEmpty(),
                    label = {
                        Text(
                            text = stringResource(R.string.label_capacity),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    placeholder = {
                        Text(stringResource(R.string.placeholder_enter_value))
                    },
                    onValueChange = onCapacityChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                label = {
                    Text(
                        text = stringResource(R.string.label_cancellation_policy),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_enter_value))
                },
                onValueChange = onCancellationPolicyChange,
            )
        }
    }
}

@Composable
private fun TourImages(
    images: List<String> = emptyList(),
    onUploadImageClick: () -> Unit = {},
) {
    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.label_tour_images),
                style = MaterialTheme.typography.titleMedium,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .dashedBorder(
                        color = Theme.colors.materialTheme.primary,
                        shape = MaterialTheme.shapes.medium,
                    )
                    .clickable { onUploadImageClick() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_anchor_upload_48),
                    contentDescription = stringResource(R.string.cd_add_tour_photo),
                )
                Text(
                    text = stringResource(R.string.action_upload_image),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.label_image_limit),
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colors.textHelp,
                )
            }

            if (images.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(images) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = stringResource(R.string.cd_tour_photo),
                            placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MiscInfo(
    modifier: Modifier = Modifier,
    tourDetail: TourDetailUi? = null,
    onRequiredItemsChange: (String) -> Unit = {},
    onHighlightsChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
) {
    CardElement(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.label_misc_info),
                style = MaterialTheme.typography.titleMedium,
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = tourDetail?.requiredItems?.joinToString(", ").orEmpty(),
                label = {
                    Text(
                        text = stringResource(R.string.label_required_items),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_required_items))
                },
                onValueChange = onRequiredItemsChange,
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = tourDetail?.highlights?.joinToString(", ").orEmpty(),
                label = {
                    Text(
                        text = stringResource(R.string.label_highlights),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                placeholder = {
                    Text(stringResource(R.string.placeholder_highlights))
                },
                onValueChange = onHighlightsChange,
            )
        }
    }
}

@Preview("default", showBackground = true, showSystemUi = true, locale = "fa")
@Composable
private fun PreviewCreateEditTour() {
    val sampleTour = TourDetail(
        id = 1L,
        title = "Sample Tour",
        description = "A sample tour description",
        guide = Guide(
            id = 1,
            fullName = "John Doe",
            bio = "",
            verified = true,
            totalTours = 20,
            averageRating = 4.5f,
            userId = 1
        ),
        highlights = listOf("Great views", "Local food"),
        duration = 3,
        price = 250000.0,
        category = AttractionCategory(id = 1, name = "HIKING", description = "", image = ""),
        maxPeople = 20,
        status = TourStatus.PENDING,
        rate = 4.5f,
        reviews = emptyList(),
        images = listOf("https://picsum.photos/400", "https://picsum.photos/401"),
        requiredItems = listOf("Comfortable shoes", "Water bottle"),
        level = TourLevel.INTERMEDIATE,
        dueDate = "1403/05/15",
        startTime = "08:00",
        demographic = Demographic.ALL_AGES,
        itinerary = emptyList(),
        city = City(
            id = 1,
            name = "اصفهان",
            latitude = 52.4888,
            countyId = 1,
            imageUrl = "",
            longitude = 32.4564,
            countyName = "اصفهان",
            provinceId = 1,
            description = "",
            provinceName = "اصفهان",
        ),
    ).toUi(LocalContext.current)

    AppTheme {
        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            CreateEditTourScreen(tourDetail = sampleTour)
        }
    }
}