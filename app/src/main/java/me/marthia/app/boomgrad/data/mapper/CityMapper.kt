package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.CityDto
import me.marthia.app.boomgrad.domain.model.City


// For City
fun CityDto.toDomain() = City(
    id = id,
    name = name,
    latitude = latitude,
    countyId = countyId,
    imageUrl = imageUrl,
    longitude = longitude,
    countyName = countyName,
    provinceId = provinceId,
    description = description,
    provinceName = provinceName,
)

fun List<CityDto>.toDomains(): List<City> {
    return this.map { it.toDomain() }
}