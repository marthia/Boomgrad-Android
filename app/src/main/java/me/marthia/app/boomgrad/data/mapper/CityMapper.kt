package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.CityDto
import me.marthia.app.boomgrad.domain.model.City


// For City
fun CityDto.toDomain(): City {
    return City(
        id = this.id,
        name = this.name,
        countyId = this.countyId,
        provinceId = this.provinceId
    )
}

fun List<CityDto>.toDomains(): List<City> {
    return this.map { it.toDomain() }
}