package me.marthia.app.boomgrad.domain.model

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