package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemDto(
    val id: Long,
    @SerialName("tour_id") val tourId: Long,
    @SerialName("schedule_id") val scheduleId: Long,
    @SerialName("tour_title") val tourTitle: String,
    @SerialName("tour_image_url") val tourImageUrl: String?,
    @SerialName("duration_minutes") val durationMinutes: Int,
    @SerialName("tour_date") val tourDate: String,      // "2026-03-10" — parsed to LocalDate
    @SerialName("start_time") val startTime: String,    // "09:00:00" — parsed to LocalTime
    @SerialName("price_per_person") val pricePerPerson: Long,
    @SerialName("current_price") val currentPrice: Long?,
    @SerialName("price_changed") val priceChanged: Boolean,
    @SerialName("total_price") val totalPrice: Long,
    @SerialName("people_count") val peopleCount: Int,
    @SerialName("max_people") val maxPeople: Int,
    @SerialName("available_spots") val availableSpots: Int,
    @SerialName("availability_warning") val availabilityWarning: String?,
)