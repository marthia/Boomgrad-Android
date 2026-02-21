package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CartResponseDto(
    val items: List<CartItemDto>,
    val subtotal: Long,
    @SerialName("service_fee") val serviceFee: Long,
    val total: Long,
    @SerialName("has_availability_issues") val hasAvailabilityIssues: Boolean,
    @SerialName("item_count") val itemCount: Int,
)

