package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("county_id")
    val countyId: Long,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("county_name")
    val countyName: String,
    @SerialName("province_id")
    val provinceId: Long,
    @SerialName("description")
    val description: String,
    @SerialName("province_name")
    val provinceName: String,
)
