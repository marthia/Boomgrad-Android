package me.marthia.app.boomgrad.presentation.tour.create

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.ButtonElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.TextFieldElement
import me.marthia.app.boomgrad.presentation.profile.component.dashedBorder
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime

// ─── Stateful entry point ─────────────────────────────────────────────────────

@Composable
fun CreateEditTourScreen(
    onNavigateToCityPicker: () -> Unit,
    onNavigateToCategoryPicker: () -> Unit,
    onNavigateToDifficultyPicker: () -> Unit,
    onNavigateToDemographicPicker: () -> Unit,
    onNavigateToDatePicker: () -> Unit,
    onTourCreated: (tourId: Long) -> Unit,
    onShowError: (message: String) -> Unit,
    viewModel: CreateTourViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // ── One-shot events ───────────────────────────────────────────────────────
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CreateTourEvent.NavigateToCityPicker -> onNavigateToCityPicker()
                is CreateTourEvent.NavigateToCategoryPicker -> onNavigateToCategoryPicker()
                is CreateTourEvent.NavigateToDifficultyPicker -> onNavigateToDifficultyPicker()
                is CreateTourEvent.NavigateToDemographicPicker -> onNavigateToDemographicPicker()
                is CreateTourEvent.NavigateToDatePicker -> onNavigateToDatePicker()
                is CreateTourEvent.Success -> onTourCreated(event.tourId)
                is CreateTourEvent.ShowError -> onShowError(event.message)
            }
        }
    }

    CreateEditTourContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onCityClick = viewModel::onCityClick,
        onTourTypeClick = viewModel::onTourTypeClick,
        onDifficultyClick = viewModel::onDifficultyClick,
        onDemographicClick = viewModel::onDemographicClick,
        onDurationChange = viewModel::onDurationChange,
        onAddDateClick = viewModel::onAddDateClick,
        onDateChange = viewModel::onDateChange,
        onStartTimeChange = viewModel::onStartTimeChange,
        onPriceChange = viewModel::onPriceChange,
        onCapacityChange = viewModel::onCapacityChange,
        onCancellationPolicyChange = viewModel::onCancellationPolicyChange,
        onUploadImageClick = viewModel::onUploadImageClick,
        onImageRemoved = viewModel::onImageRemoved,
        onRequiredItemsChange = viewModel::onRequiredItemsChange,
        onHighlightsChange = viewModel::onHighlightsChange,
        onDescriptionMiscChange = viewModel::onDescriptionMiscChange,
        onAddItineraryStop = viewModel::onAddItineraryStop,
        onItineraryTitleChange = viewModel::onItineraryStopTitleChange,
        onItineraryDescChange = viewModel::onItineraryStopDescriptionChange,
        onRemoveItineraryStop = viewModel::onRemoveItineraryStop,
        onSubmit = viewModel::onSubmit,
    )
}

// ─── Stateless content ────────────────────────────────────────────────────────

@Composable
fun CreateEditTourContent(
    state: CreateEditTourUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCityClick: () -> Unit,
    onTourTypeClick: () -> Unit,
    onDifficultyClick: () -> Unit,
    onDemographicClick: () -> Unit,
    onDurationChange: (String) -> Unit,
    onAddDateClick: () -> Unit,
    onDateChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onCapacityChange: (String) -> Unit,
    onCancellationPolicyChange: (String) -> Unit,
    onUploadImageClick: () -> Unit,
    onImageRemoved: (String) -> Unit,
    onRequiredItemsChange: (String) -> Unit,
    onHighlightsChange: (String) -> Unit,
    onDescriptionMiscChange: (String) -> Unit,
    onAddItineraryStop: () -> Unit,
    onItineraryTitleChange: (index: Int, value: String) -> Unit,
    onItineraryDescChange: (index: Int, value: String) -> Unit,
    onRemoveItineraryStop: (index: Int) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        CreateEditTour(
            title = state.title,
            description = state.description,
            cityName = state.selectedCityName,
            categoryName = state.selectedCategoryName,
            level = state.selectedLevel,
            demographic = state.selectedDemographic,
            duration = state.duration,
            fieldErrors = state.fieldErrors,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            onCityClick = onCityClick,
            onTourTypeClick = onTourTypeClick,
            onDifficultyClick = onDifficultyClick,
            onDemographicClick = onDemographicClick,
            onDurationChange = onDurationChange,
        )

        Schedules(
            dueDate = state.dueDate,
            startTime = state.startTime,
            fieldErrors = state.fieldErrors,
            onAddDateClick = onAddDateClick,
            onDateChange = onDateChange,
            onStartTimeChange = onStartTimeChange,
        )

        PriceAndCapacity(
            price = state.price,
            maxPeople = state.maxPeople,
            cancellationPolicy = state.cancellationPolicy,
            fieldErrors = state.fieldErrors,
            onPriceChange = onPriceChange,
            onCapacityChange = onCapacityChange,
            onCancellationPolicyChange = onCancellationPolicyChange,
        )

        TourImages(
            images = state.images,
            onUploadImageClick = onUploadImageClick,
//            onImageRemoved = onImageRemoved,
        )

        MiscInfo(
            requiredItems = state.requiredItems,
            highlights = state.highlights,
            descriptionMisc = state.descriptionMisc,
            onRequiredItemsChange = onRequiredItemsChange,
            onHighlightsChange = onHighlightsChange,
            onDescriptionChange = onDescriptionMiscChange,
        )

        ItinerarySection(
            stops = state.itinerary,
//            onAddStop = onAddItineraryStop,
//            onTitleChange = onItineraryTitleChange,
//            onDescChange = onItineraryDescChange,
//            onRemoveStop = onRemoveItineraryStop,
            onPickOnMapClick = {},
        )

        SubmitButton(
            isLoading = state.isLoading,
            onClick = onSubmit,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun SubmitButton(isLoading: Boolean, onClick: () -> Unit, modifier: Modifier) {
    ButtonElement(onClick = onClick) {
        Text("ارسال")
    }
}


@Composable
fun CreateEditTour(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    cityName: String,
    categoryName: String,
    level: String,
    demographic: String,
    duration: String,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onCityClick: () -> Unit = {},
    onTourTypeClick: () -> Unit = {},
    onDifficultyClick: () -> Unit = {},
    onDemographicClick: () -> Unit = {},
    onDurationChange: (String) -> Unit = {},
    fieldErrors: Map<FormField, String>,
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
                value = title,
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
                value = description,
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
                value = cityName,
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
                value = categoryName,
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
                value = level,
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
                    value = demographic,
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
                    value = duration,
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
    dueDate: LocalDate?,
    startTime: LocalTime?,
    onAddDateClick: () -> Unit = {},
    onDateChange: (String) -> Unit = {},
    onStartTimeChange: (String) -> Unit = {},
    fieldErrors: Map<FormField, String>,
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
                value = dueDate.toString(),
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
                value = startTime.toString(),
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
    price: String,
    maxPeople: String,
    cancellationPolicy: String,
    onPriceChange: (String) -> Unit = {},
    onCapacityChange: (String) -> Unit = {},
    onCancellationPolicyChange: (String) -> Unit = {},
    fieldErrors: Map<FormField, String>,
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
                    value = price,
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
                    value = maxPeople,
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
    requiredItems: String,
    highlights: String,
    descriptionMisc: String,
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
                value = requiredItems,/*.joinToString(", ")*/
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
                value = highlights,/*.joinToString(", ")*/
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
    val previewCreateEditTourUiState = CreateEditTourUiState(
        title = "تور نیم‌روزه‌ی میدان نقش جهان",
        description = "یکی از بزرگ‌ترین میدان‌های جهان با معماری اسلامی بی‌نظیر",
        selectedCityId = 1L,
        selectedCityName = "اصفهان",
        selectedCategoryId = 1L,
        selectedCategoryName = "میراث فرهنگی",
        selectedLevel = "INTERMEDIATE",
        selectedDemographic = "ALL_AGES",
        duration = "180",
        dueDate = LocalDate.of(2025, 2, 4),
        startTime = LocalTime.of(8, 0),
        price = "250000",
        maxPeople = "20",
        cancellationPolicy = "لغو تا ۲۴ ساعت قبل از تور امکان‌پذیر است",
        images = listOf("https://picsum.photos/400", "https://picsum.photos/401"),
        meetingPoint = "میدان نقش جهان، روبه‌روی مسجد امام",
        requiredItems = "کفش راحت\nبطری آب\nکلاه آفتابی",
        highlights = "معماری صفوی\nغذای محلی\nمنظره‌های بی‌نظیر",
        descriptionMisc = "لطفاً ۱۵ دقیقه زودتر در محل حاضر شوید",
        itinerary = listOf(
            ItineraryStopUi(title = "میدان نقش جهان", description = "شروع تور از میدان اصلی"),
            ItineraryStopUi(title = "مسجد امام", description = "بازدید از مسجد تاریخی"),
            ItineraryStopUi(title = "بازار قیصریه", description = "گشت در بازار سنتی"),
        ),
        fieldErrors = emptyMap(),
        isLoading = false,
    )

    AppTheme {
        BackgroundElement(modifier = Modifier.fillMaxSize()) {
            CreateEditTourContent(
                state = previewCreateEditTourUiState,
                onTitleChange = { _ -> },
                onDescriptionChange = { _ -> },
                onCityClick = {},
                onTourTypeClick = {},
                onDifficultyClick = {},
                onDemographicClick = {},
                onDurationChange = { _ -> },
                onAddDateClick = {},
                onDateChange = { _ -> },
                onStartTimeChange = { _ -> },
                onPriceChange = { _ -> },
                onCapacityChange = { _ -> },
                onCancellationPolicyChange = { _ -> },
                onUploadImageClick = {},
                onImageRemoved = { _ -> },
                onRequiredItemsChange = { _ -> },
                onHighlightsChange = { _ -> },
                onDescriptionMiscChange = { _ -> },
                onAddItineraryStop = {},
                onItineraryTitleChange = { _, _ -> },
                onItineraryDescChange = { _, _ -> },
                onRemoveItineraryStop = { _ -> },
                onSubmit = {},
            )
        }
    }
}