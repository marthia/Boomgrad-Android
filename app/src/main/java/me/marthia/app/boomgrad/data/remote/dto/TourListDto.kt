package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.Demographic
import me.marthia.app.boomgrad.domain.model.TourStatus

@Serializable
data class TourListDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("images")
    val images: List<String>?,
    @SerialName("duration")
    val duration: Int?,
    @SerialName("price")
    val price: Double?,
    @SerialName("due_date")
    val dueDate: String?,
    @SerialName("category")
    val category: AttractionCategoryDto?,
    @SerialName("city")
    val city: String?,
)