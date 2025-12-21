package me.marthia.app.boomgrad.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Login(
    val success: Boolean? = null,
    val token: String? = null,
    val message: String? = null,
    val requiresSms: Boolean? = null
)