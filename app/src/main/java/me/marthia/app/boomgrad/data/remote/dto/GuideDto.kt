package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GuideDto(
    val id: Long,
    val bio: String,
    val fullName: String,
    val userId: Long,
    val verified: Boolean,
    val totalTours: Long,
    val averageRating: Float,
)