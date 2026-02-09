package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: Long,
    val user: UserDto,
    val title: String,
    val content: String,
    val date: String,
)