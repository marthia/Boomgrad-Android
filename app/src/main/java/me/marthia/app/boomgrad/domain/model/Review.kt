package me.marthia.app.boomgrad.domain.model

data class Review(
    val id: Long,
    val user: User,
    val comment: String,
    val date: String
)