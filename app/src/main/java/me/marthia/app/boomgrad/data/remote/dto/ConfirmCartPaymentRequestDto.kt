package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmCartPaymentRequestDto(
    @SerialName("payment_id") val paymentId: String,
    @SerialName("total_amount") val totalAmount: Long,
)