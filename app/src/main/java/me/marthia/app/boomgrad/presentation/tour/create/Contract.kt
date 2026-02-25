package me.marthia.app.boomgrad.presentation.tour.create

import java.time.LocalDate
import java.time.LocalTime

sealed interface CreateTourEvent {
    data class Success(val tourId: Long) : CreateTourEvent
    data class ShowError(val message: String) : CreateTourEvent
    data object NavigateToCityPicker : CreateTourEvent
    data object NavigateToCategoryPicker : CreateTourEvent
    data object NavigateToDifficultyPicker : CreateTourEvent
    data object NavigateToDemographicPicker : CreateTourEvent
    data object NavigateToDatePicker : CreateTourEvent
}

data class CreateEditTourUiState(
    // Basic info
    val title: String = "",
    val description: String = "",

    // Selectors (shown as chips / bottom-sheets)
    val selectedCityId: Long = -1L,
    val selectedCityName: String = "",
    val selectedCategoryId: Long = -1L,
    val selectedCategoryName: String = "",
    val selectedLevel: String = "",
    val selectedDemographic: String = "",

    // Duration (free-text minutes input)
    val duration: String = "",

    // Schedule
    val dueDate: LocalDate? = null,
    val startTime: LocalTime? = null,

    // Price & capacity
    val price: String = "",
    val maxPeople: String = "",
    val cancellationPolicy: String = "",

    // Media
    val images: List<String> = emptyList(),

    // Misc
    val meetingPoint: String = "",
    val requiredItems: String = "",     // newline-separated in the text field
    val highlights: String = "",
    val descriptionMisc: String = "",

    // Itinerary
    val itinerary: List<ItineraryStopUi> = emptyList(),

    // Validation — per-field error messages shown under each input
    val fieldErrors: Map<FormField, String> = emptyMap(),

    // Async
    val isLoading: Boolean = false,
)

data class ItineraryStopUi(
    val id: Long = 0,
    val stopOrder: Int = 0,
    val title: String = "",
    val date: String = "",
    val description: String = "",
    val durationMinutes: Int = 0,
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

enum class FormField {
    TITLE, DESCRIPTION, CATEGORY, CITY,
    DURATION, PRICE, MAX_PEOPLE,
    MEETING_POINT, LEVEL, DUE_DATE,
}