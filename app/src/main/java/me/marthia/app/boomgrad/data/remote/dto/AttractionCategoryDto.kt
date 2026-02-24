package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.CategoryType

@Serializable
data class AttractionCategoryDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("category_type")
    val categoryType: CategoryType?,
    @SerialName("description")
    val description: String?,
    @SerialName("icon")
    val icon: String?,
)