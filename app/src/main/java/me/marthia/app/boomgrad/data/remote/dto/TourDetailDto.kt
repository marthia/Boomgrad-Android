package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.Demographic
import me.marthia.app.boomgrad.domain.model.TourLevel
import me.marthia.app.boomgrad.domain.model.TourStatus

@Serializable
data class TourDetailDto(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("guide")
    val guide: GuideDto,
    @SerialName("highlights")
    val highlights: List<String>,
    @SerialName("duration")
    val duration: Int,
    @SerialName("price")
    val price: Double,
    @SerialName("category")
    val category: AttractionCategoryDto,
    @SerialName("max_people")
    val maxPeople: Int,
    @SerialName("status")
    val status: TourStatus,
    @SerialName("average_rating")
    val rate: Float,
    @SerialName("meeting_point")
    val meetingPoint: String,
    @SerialName("total_reviews")
    val totalReviews: Int,
    @SerialName("reviews")
    val reviews: List<ReviewDto>,
    @SerialName("images")
    val images: List<String>,
    @SerialName("required_items")
    val requiredItems: List<String>,
    @SerialName("level")
    val level: TourLevel,
    @SerialName("due_date")
    val dueDate: String,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("demographic")
    val demographic: Demographic,
    @SerialName("itinerary")
    val itinerary: List<ItineraryStopDto>,
    @SerialName("city")
    val city: CityDto,
)