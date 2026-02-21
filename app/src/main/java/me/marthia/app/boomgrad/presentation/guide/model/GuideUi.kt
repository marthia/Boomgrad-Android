package me.marthia.app.boomgrad.presentation.guide.model

import me.marthia.app.boomgrad.domain.model.TourList

data class GuideUi(
    val id: Long,
    val bio: String,
    val fullName: String,
    val userImage: String,
    val phone: String,
    val verified: Boolean,
    val totalTours: Long,
    val tours: List<TourList>,
    val averageRating: Float,
)