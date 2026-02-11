package me.marthia.app.boomgrad.domain.model

data class AttractionOpeningHours(
    val id: Long,
    val workingDate: String,
    val workingTime: String,
    val isClosed: Boolean,
    val notes: String
)