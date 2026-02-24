package me.marthia.app.boomgrad.presentation.tour.model

import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.domain.model.Review
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.presentation.category.model.CategoryUi

data class TourDetailUi(
    val id: Long,
    val title: String,
    val description: String,
    val guide: Guide,
    val highlights: List<String>,
    val duration: Int,
    val price: Double,
    val category: CategoryUi,
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
    val city: City,
)