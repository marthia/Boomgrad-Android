package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttractionImageDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("image_url")
    val imageUrl: String?,
)
