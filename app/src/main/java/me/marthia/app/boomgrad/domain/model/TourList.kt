package me.marthia.app.boomgrad.domain.model

data class TourList(
    val id: Long,
    val title: String,
    val description: String,
    val images: List<String>,
    val duration: Int,
    val dueDate: String,
    val price: Double,
    val city: String,
    val category: AttractionCategory
)
