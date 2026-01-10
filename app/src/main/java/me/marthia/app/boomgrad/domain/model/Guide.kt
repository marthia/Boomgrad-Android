package me.marthia.app.boomgrad.domain.model

data class Guide(
    val id: Long,
    val bio: String,
    val fullName: String,
    val userId: Long,
    val verified: Boolean,
    val totalTours: Long,
    val averageRating: Float,
)