package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("province")
    val province: String,
    @SerialName("county")
    val county: String,
)
