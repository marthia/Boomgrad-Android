package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("category")
    val category: AttractionCategoryDto?,
    @SerialName("images")
    val images: List<AttractionImageDto>?,
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
    val tours: List<TourListDto>? = listOf(),
    @SerialName("reviews")
    val reviews: List<ReviewDto>? = listOf(),
)