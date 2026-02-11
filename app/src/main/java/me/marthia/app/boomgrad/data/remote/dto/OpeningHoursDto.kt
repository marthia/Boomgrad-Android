package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpeningHoursDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("working_date")
    val workingDate: String?,
    @SerialName("working_time")
    val workingTime: String?,
    @SerialName("is_closed")
    val isClosed: Boolean?,
    @SerialName("notes")
    val notes: String?
)