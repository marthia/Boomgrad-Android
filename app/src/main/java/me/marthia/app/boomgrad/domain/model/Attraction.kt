package me.marthia.app.boomgrad.domain.model

data class Attraction(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val imageUrl: String?,
    val rating: Double,
    val contactInfo: ContactInfo,
    val openingHours: OpeningHours,
    val location: Location,
    val isFavorite: Boolean = false
)

data class ContactInfo(
    val phone: String?,
    val email: String?,
    val website: String?,
    val address: String
)

data class OpeningHours(
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String
) {
    fun getTodayHours(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            1 -> sunday
            2 -> monday
            3 -> tuesday
            4 -> wednesday
            5 -> thursday
            6 -> friday
            7 -> saturday
            else -> "Closed"
        }
    }
}

data class Location(
    val latitude: Double,
    val longitude: Double
)