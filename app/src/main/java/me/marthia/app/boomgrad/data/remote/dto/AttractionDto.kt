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
    val openingHours: List<OpeningHoursDto>?,
    @SerialName("location")
    val location: LocationDto?,
    @SerialName("tours")
    val tours: List<TourDto>?,
    @SerialName("reviews")
    val reviews: List<ReviewDto>?,
)