package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCartItemRequestDto(
    @SerialName("people_count") val peopleCount: Int,
)
