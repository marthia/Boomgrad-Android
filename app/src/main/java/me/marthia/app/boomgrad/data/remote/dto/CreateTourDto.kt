package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTourDto(
    val title: String,
    val description: String,
    @SerialName("category_id") val categoryId: Long,
    @SerialName("city_id") val cityId: Long,
    val duration: Int,                        // minutes
    val price: String,                        // BigDecimal as String
    @SerialName("max_people") val maxPeople: Int,
    @SerialName("meeting_point") val meetingPoint: String,
    val highlights: List<String>,
    val images: List<String>,
    @SerialName("required_items") val requiredItems: List<String>,
    val level: String,
    @SerialName("due_date") val dueDate: String,       // ISO-8601 "yyyy-MM-dd"
    @SerialName("start_time") val startTime: String,   // "HH:mm:ss"
    val demographic: String?,
    val itinerary: List<CreateItineraryStopDto>,
)

@Serializable
data class CreateItineraryStopDto(
    val title: String,
    val description: String?,
)