package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCartItemRequestDto(
    @SerialName("tour_id") val tourId: Long,
    @SerialName("schedule_id") val scheduleId: Long,
    @SerialName("people_count") val peopleCount: Int,
)