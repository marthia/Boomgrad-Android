package me.marthia.app.boomgrad.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

/**
 * Domain model representing a tour creation request.
 * Validated and assembled by the ViewModel before being passed to the use case.
 */
data class CreateTour(
    val title: String,
    val description: String,
    val categoryId: Long,
    val cityId: Long,
    val durationMinutes: Int,
    val price: BigDecimal,
    val maxPeople: Int,
    val meetingPoint: String,
    val highlights: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val requiredItems: List<String> = emptyList(),
    val level: String,
    val dueDate: LocalDate,
    val startTime: LocalTime,
    val demographic: String? = null,
    val itinerary: List<CreateItineraryStop> = emptyList(),
)

data class CreateItineraryStop(
    val title: String,
    val description: String? = null,
)