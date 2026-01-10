package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.LocationType

@Serializable
data class LocationDto(
    val id: Long?,
    val name: String?,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val type: LocationType?,
    val address: String?,
    val city: CityDto?,
)
