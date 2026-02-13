package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    @SerialName("content")
    val content: List<T>?,
    @SerialName("last")
    val last: Boolean?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_elements")
    val totalElements: Int?,
)
