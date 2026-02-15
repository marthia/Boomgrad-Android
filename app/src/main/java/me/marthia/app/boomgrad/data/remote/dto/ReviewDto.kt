package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("user")
    val user: UserDto?,
    @SerialName("comment")
    val comment: String?,
    @SerialName("created_at")
    val date: String?,
    @SerialName("is_current_user")
    val isCurrentUser: Boolean?,
    @SerialName("rating")
    val rating: Float?,

)