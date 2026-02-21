package me.marthia.app.boomgrad.domain.model

data class ItineraryStop(
    val id: Long? = null,
    val stopOrder: Int,
    val title: String,
    val date: String,
    val description: String? = null,
    val durationMinutes: Int? = null,
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)