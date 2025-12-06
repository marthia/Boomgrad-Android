package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("category")
    val category: String,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("rating")
    val rating: Double,
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto,
    @SerialName("opening_hours")
    val openingHours: OpeningHoursDto,
    @SerialName("location")
    val location: LocationDto
)

@Serializable
data class ContactInfoDto(
    @SerialName("phone")
    val phone: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("website")
    val website: String?,
    @SerialName("address")
    val address: String
)

@Serializable
data class OpeningHoursDto(
    @SerialName("monday")
    val monday: String,
    @SerialName("tuesday")
    val tuesday: String,
    @SerialName("wednesday")
    val wednesday: String,
    @SerialName("thursday")
    val thursday: String,
    @SerialName("friday")
    val friday: String,
    @SerialName("saturday")
    val saturday: String,
    @SerialName("sunday")
    val sunday: String
)

@Serializable
data class LocationDto(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)

@Serializable
data class AttractionsResponse(
    @SerialName("attractions")
    val attractions: List<AttractionDto>,
    @SerialName("total")
    val total: Int
)