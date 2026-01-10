package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.LocationDto
import me.marthia.app.boomgrad.domain.model.Location

fun LocationDto.toDomain(): Location {
    return Location(
        latitude = latitude ?: throw IllegalStateException("latitude cannot be null"),
        longitude = longitude ?: throw IllegalStateException("longitude cannot be null"),
        id = id ?: throw IllegalStateException("Id Cannot be null"),
        name = name ?: throw IllegalStateException("name cannot be null"),
        description = description ?: throw IllegalStateException("description Cannot be null"),
        type = type ?: throw IllegalStateException("type cannot be null"),
        address = address ?: throw IllegalStateException("address Cannot be null"),
        city = city?.toDomain() ?: throw IllegalStateException("city cannot be null"),
    )
}