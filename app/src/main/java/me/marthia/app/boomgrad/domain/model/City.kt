package me.marthia.app.boomgrad.domain.model

data class City(
    val id: Long,
    val name: String,
    val countyId: Long,
    val provinceId: Long
)
