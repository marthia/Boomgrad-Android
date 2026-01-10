package me.marthia.app.boomgrad.domain.model

data class Location(
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val type: LocationType,
    val address: String,
    val city: City,
)