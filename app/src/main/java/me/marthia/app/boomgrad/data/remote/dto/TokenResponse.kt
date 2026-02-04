@file:OptIn(ExperimentalSerializationApi::class)

package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.UserType

@Serializable
data class TokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("role")
    val role: UserType,
)