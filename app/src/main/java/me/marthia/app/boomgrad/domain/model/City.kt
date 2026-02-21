package me.marthia.app.boomgrad.domain.model

data class City(
    val id: Long,
    val name: String,
    val latitude: Double,
    val countyId: Long,
    val imageUrl: String,
    val longitude: Double,
    val countyName: String,
    val provinceId: Long,
    val description: String,
    val provinceName: String,
)
