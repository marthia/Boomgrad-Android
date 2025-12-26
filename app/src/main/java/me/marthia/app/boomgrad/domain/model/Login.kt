package me.marthia.app.boomgrad.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Login(
    val token: String
)