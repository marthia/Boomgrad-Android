package me.marthia.app.boomgrad.domain.model

data class AttractionOpeningHours(
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