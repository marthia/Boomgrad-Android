package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: Long,
    val userId: Long,
    val userName: String,
    val title: String,
    val reviewBody: String,
)