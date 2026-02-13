package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuideDto(
    @SerialName("id")
    val id: Long,
//    @SerialName("bio")
//    val bio: String,
    @SerialName("name")
    val fullName: String,
//    @SerialName("userId")
//    val userId: Long,
    @SerialName("verified")
    val verified: Boolean,
    @SerialName("total_tours")
    val totalTours: Long,
    @SerialName("average_rating")
    val averageRating: Float,
)