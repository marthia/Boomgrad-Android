package me.marthia.app.boomgrad.domain.model

data class Review(
    val id: Long,
    val user: User,
    val title: String,
    val content: String,
    val date: String
)