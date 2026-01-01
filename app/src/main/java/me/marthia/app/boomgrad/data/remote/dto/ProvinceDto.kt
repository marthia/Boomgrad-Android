package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProvinceDto(

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,
)
