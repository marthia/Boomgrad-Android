package me.marthia.app.boomgrad.domain.model


data class Login(
    val token: String,
    val refreshToken: String
)