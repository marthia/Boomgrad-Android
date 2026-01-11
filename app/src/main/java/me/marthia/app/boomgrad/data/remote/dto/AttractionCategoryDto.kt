package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AttractionCategoryDto(
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
)