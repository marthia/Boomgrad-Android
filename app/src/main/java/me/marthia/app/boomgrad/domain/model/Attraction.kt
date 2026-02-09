package me.marthia.app.boomgrad.domain.model

data class Attraction(
    val id: String,
    val category: String,
    val images: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val contactInfo: AttractionContactInfo,
    val openingHours: List<AttractionOpeningHours>,
    val location: Location,
    val isFavorite: Boolean = false,
    val reviews: List<Review>,
    val relatedTours: List<Tour>
)
