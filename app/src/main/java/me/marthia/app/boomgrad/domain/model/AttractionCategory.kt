package me.marthia.app.boomgrad.domain.model

data class AttractionCategory(
    val id: Long,
    val type: CategoryType,
    val description: String,
    val image: String,
)