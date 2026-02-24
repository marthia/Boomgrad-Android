package me.marthia.app.boomgrad.presentation.attraction.model

import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionImage
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.Review
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.presentation.category.model.CategoryUi

data class AttractionUi(
    val id: Long,
    val category: CategoryUi,
    val images: List<AttractionImage>,
    val rating: Float,
    val reviewCount: Int,
    val contactInfo: AttractionContactInfo,
    val openingHours: List<AttractionOpeningHours>,
    val location: Location,
    val isFavorite: Boolean = false,
    val reviews: List<Review>,
    val relatedTours: List<TourList>
)