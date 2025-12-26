@file:OptIn(ExperimentalSerializationApi::class)

package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val success: Boolean,
    val message: String,
    val token: String,
)