package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpeningHoursDto(
    @SerialName("date")
    val date: String?,
    @SerialName("working_hour")
    val workingHour: String?,
)