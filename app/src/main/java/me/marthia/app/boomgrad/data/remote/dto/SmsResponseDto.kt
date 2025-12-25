package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class SmsResponseDto(
    @JsonNames("status")
    val status: String? = null,
    @JsonNames("msg")
    val message: String? = null,
    @JsonNames("token")
    val result: SmsResponseResult? = null
)


@Serializable
data class SmsResponseResult(
    val token: String? = null,
)