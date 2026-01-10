package me.marthia.app.boomgrad.domain.model

data class ItineraryStop(
    val destination: String,
    val duration: String? = null,
)