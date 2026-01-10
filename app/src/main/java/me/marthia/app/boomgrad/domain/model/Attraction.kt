package me.marthia.app.boomgrad.domain.model

data class Attraction(
    val id: String,
    val category: String,
    val imageUrl: String?,
    val rating: Float,
    val contactInfo: AttractionContactInfo,
    val openingHours: AttractionOpeningHours,
    val location: Location,
    val isFavorite: Boolean = false
)
