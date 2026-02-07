package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionDto(
    @SerialName("id")
    val id: String?,
    @SerialName("category")
    val category: String?,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("rating")
    val rating: Float?,
    @SerialName("review_count")
    val reviewCount: Int?,
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto?,
    @SerialName("opening_hours")
    val openingHours: List<OpeningHoursDto>,
    @SerialName("location")
    val location: LocationDto?
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
    val address: String?
)

@Serializable
data class OpeningHoursDto(
    @SerialName("date")
    val date: String?,
    @SerialName("working_hour")
    val workingHour: String?,
)