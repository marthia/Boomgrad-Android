@file:OptIn(ExperimentalSerializationApi::class)

package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class LoginResponseDto(
    val status: String? = null,
    @JsonNames("result", "msg")
    val message: String? = null,
)