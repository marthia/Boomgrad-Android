package me.marthia.app.boomgrad.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class CartItem(
    val id: Long,
    val tourId: Long,
    val scheduleId: Long,
    val tourTitle: String,
    val tourImageUrl: String?,
    val durationMinutes: Int,
    val tourDate: LocalDate,
    val startTime: LocalTime,
    val pricePerPerson: Long,       // in Toman
    val currentPrice: Long?,        // non-null if price changed since added
    val priceChanged: Boolean,
    val peopleCount: Int,
    val maxPeople: Int,
    val availableSpots: Int,
    val availabilityWarning: String?,
) {
    val totalPrice: Long get() = pricePerPerson * peopleCount
}
