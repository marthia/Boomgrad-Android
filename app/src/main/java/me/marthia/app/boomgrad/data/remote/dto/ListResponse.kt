package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse<DATA>(
    val success: Boolean,
    val message: String,
    val timestamp: String,
    val data: List<DATA>
)
