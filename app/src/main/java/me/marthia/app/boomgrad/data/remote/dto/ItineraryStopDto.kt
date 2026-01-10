package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItineraryStopDto(
    val destination: String,
    val duration: String? = null,
)