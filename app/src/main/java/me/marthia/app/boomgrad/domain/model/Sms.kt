package me.marthia.app.boomgrad.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Sms(
    val status: Boolean? = null,

    val message: String? = null,

    val token: String? = null
)