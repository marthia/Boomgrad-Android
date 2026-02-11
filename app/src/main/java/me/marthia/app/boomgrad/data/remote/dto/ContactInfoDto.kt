package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactInfoDto(
    @SerialName("phone")
    val phone: String?,
    @SerialName("website")
    val website: String?,
    @SerialName("address")
    val address: String?
)