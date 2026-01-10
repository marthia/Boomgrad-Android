package me.marthia.app.boomgrad.domain.model

import me.marthia.app.boomgrad.presentation.home.ItineraryStop

data class Tour(
    val id: Long,
    val title: String,
    val description: String,
    val guide: Guide,
    val highlights: List<String>,
    val duration: Int,
    val price: Double,
    val category: AttractionCategory,
    val maxPeople: Int,
    val status: TourStatus,
    val rate: Float,
    val reviews: List<Review>,
    val images: List<String>,
    val requiredItems: List<String>,
    val level: String,
    val dueDate: String,
    val startTime: String,
    val demographic: String,
    val itinerary: List<ItineraryStop>,
)

data class TourStop(
    val id: Long,
    val tourId: Long,
    val location: Location,
    val duration: String,
)


data class Review(
    val id: Long,
    val userId: Long,
    val userName: String,
    val title: String,
    val reviewBody: String,
)


enum class TourStatus {
    PENDING,
    ONGOING,
    CANCELLED,
}


data class AttractionCategory(
    val id: Long,
    val name: String,
    val description: String,
)
