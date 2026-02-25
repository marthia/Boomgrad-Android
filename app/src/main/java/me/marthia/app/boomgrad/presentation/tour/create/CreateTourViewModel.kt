package me.marthia.app.boomgrad.presentation.tour.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.marthia.app.boomgrad.domain.usecase.tour.CreateTourUseCase
import me.marthia.app.boomgrad.presentation.tour.model.toDomain
import me.marthia.app.boomgrad.presentation.tour.model.toFieldErrorMap
import java.time.LocalDate
import java.time.LocalTime


class CreateTourViewModel(
    private val createTourUseCase: CreateTourUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateEditTourUiState())
    val uiState: StateFlow<CreateEditTourUiState> = _uiState.asStateFlow()

    private val _events = Channel<CreateTourEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    // ── Basic Info ────────────────────────────────────────────────────────────

    fun onTitleChange(value: String) = _uiState.update {
        it.copy(title = value, fieldErrors = it.fieldErrors - FormField.TITLE)
    }

    fun onDescriptionChange(value: String) = _uiState.update {
        it.copy(description = value, fieldErrors = it.fieldErrors - FormField.DESCRIPTION)
    }

    fun onDurationChange(value: String) = _uiState.update {
        it.copy(
            duration = value.filter { c -> c.isDigit() },
            fieldErrors = it.fieldErrors - FormField.DURATION,
        )
    }

    // ── Selectors ─────────────────────────────────────────────────────────────

    fun onCityClick() = sendEvent(CreateTourEvent.NavigateToCityPicker)

    fun onCitySelected(id: Long, name: String) = _uiState.update {
        it.copy(
            selectedCityId = id,
            selectedCityName = name,
            fieldErrors = it.fieldErrors - FormField.CITY,
        )
    }

    fun onTourTypeClick() = sendEvent(CreateTourEvent.NavigateToCategoryPicker)

    fun onCategorySelected(id: Long, name: String) = _uiState.update {
        it.copy(
            selectedCategoryId = id,
            selectedCategoryName = name,
            fieldErrors = it.fieldErrors - FormField.CATEGORY,
        )
    }

    fun onDifficultyClick() = sendEvent(CreateTourEvent.NavigateToDifficultyPicker)

    fun onLevelSelected(level: String) = _uiState.update {
        it.copy(
            selectedLevel = level,
            fieldErrors = it.fieldErrors - FormField.LEVEL,
        )
    }

    fun onDemographicClick() = sendEvent(CreateTourEvent.NavigateToDemographicPicker)

    fun onDemographicSelected(demographic: String) = _uiState.update {
        it.copy(selectedDemographic = demographic)
    }

    // ── Schedule ──────────────────────────────────────────────────────────────

    fun onAddDateClick() = sendEvent(CreateTourEvent.NavigateToDatePicker)

    fun onDateSelected(date: LocalDate) = _uiState.update {
        it.copy(dueDate = date, fieldErrors = it.fieldErrors - FormField.DUE_DATE)
    }

    fun onStartTimeSelected(time: LocalTime) = _uiState.update {
        it.copy(startTime = time)
    }

    /** String-based overloads for parity with the Composable callback signatures */
    fun onDateChange(raw: String) {
        runCatching { LocalDate.parse(raw) }.onSuccess { onDateSelected(it) }
    }

    fun onStartTimeChange(raw: String) {
        runCatching { LocalTime.parse(raw) }.onSuccess { onStartTimeSelected(it) }
    }

    // ── Price & Capacity ──────────────────────────────────────────────────────

    fun onPriceChange(value: String) = _uiState.update {
        it.copy(
            price = value.filter { c -> c.isDigit() || c == '.' },
            fieldErrors = it.fieldErrors - FormField.PRICE,
        )
    }

    fun onCapacityChange(value: String) = _uiState.update {
        it.copy(
            maxPeople = value.filter { c -> c.isDigit() },
            fieldErrors = it.fieldErrors - FormField.MAX_PEOPLE,
        )
    }

    fun onCancellationPolicyChange(value: String) = _uiState.update {
        it.copy(cancellationPolicy = value)
    }

    // ── Images ────────────────────────────────────────────────────────────────

    fun onUploadImageClick() { /* caller opens the picker; result arrives via onImageUploaded */
    }

    fun onImageUploaded(url: String) = _uiState.update {
        it.copy(images = it.images + url)
    }

    fun onImageRemoved(url: String) = _uiState.update {
        it.copy(images = it.images - url)
    }

    // ── Misc ──────────────────────────────────────────────────────────────────

    fun onMeetingPointChange(value: String) = _uiState.update {
        it.copy(meetingPoint = value, fieldErrors = it.fieldErrors - FormField.MEETING_POINT)
    }

    fun onRequiredItemsChange(value: String) = _uiState.update {
        it.copy(requiredItems = value)
    }

    fun onHighlightsChange(value: String) = _uiState.update {
        it.copy(highlights = value)
    }

    fun onDescriptionMiscChange(value: String) = _uiState.update {
        it.copy(descriptionMisc = value)
    }

    // ── Itinerary ─────────────────────────────────────────────────────────────

    fun onAddItineraryStop() = _uiState.update {
        it.copy(itinerary = it.itinerary + ItineraryStopUi())
    }

    fun onItineraryStopTitleChange(index: Int, value: String) = _uiState.update {
        it.copy(itinerary = it.itinerary.mapIndexed { i, stop ->
            if (i == index) stop.copy(title = value) else stop
        })
    }

    fun onItineraryStopDescriptionChange(index: Int, value: String) = _uiState.update {
        it.copy(itinerary = it.itinerary.mapIndexed { i, stop ->
            if (i == index) stop.copy(description = value) else stop
        })
    }

    fun onRemoveItineraryStop(index: Int) = _uiState.update {
        it.copy(itinerary = it.itinerary.filterIndexed { i, _ -> i != index })
    }

    // ── Submit ────────────────────────────────────────────────────────────────

    fun onSubmit() {
        val request = _uiState.value.toDomain()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = createTourUseCase(request)) {
                is CreateTourUseCase.CreateTourResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(CreateTourEvent.Success(result.tour.id.toLong()))
                }

                is CreateTourUseCase.CreateTourResult.ValidationFailure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = result.errors.toFieldErrorMap(),
                        )
                    }
                }

                is CreateTourUseCase.CreateTourResult.NetworkError -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(
                        CreateTourEvent.ShowError(
                            result.cause.message ?: "خطای ناشناخته‌ای رخ داد"
                        )
                    )
                }
            }
        }
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private fun sendEvent(event: CreateTourEvent) {
        viewModelScope.launch { _events.send(event) }
    }


}

