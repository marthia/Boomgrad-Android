package me.marthia.app.boomgrad.domain.model

data class Attraction(
    val id: Long,
    val category: AttractionCategory,
    val images: List<AttractionImage>,
    val rating: Float,
    val reviewCount: Int,
    val contactInfo: AttractionContactInfo,
    val openingHours: List<AttractionOpeningHours>,
    val location: Location,
    val isFavorite: Boolean = false,
    val reviews: List<Review>,
    val relatedTours: List<Tour>
)
