package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.LocationType

@Serializable
data class LocationDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("name")
    val name: String?,
    @SerialName("description")
    val description: String? = "",
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("location_type")
    val type: LocationType?,
    @SerialName("address")
    val address: String?,
    @SerialName("city")
    val city: String?,
    @SerialName("province")
    val province: String?,
)
