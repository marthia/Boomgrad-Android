package me.marthia.app.boomgrad.domain.model

data class Review(
    val id: Long,
    val userId: Long,
    val userName: String,
    val title: String,
    val reviewBody: String,
)