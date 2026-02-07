package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestBody(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)
