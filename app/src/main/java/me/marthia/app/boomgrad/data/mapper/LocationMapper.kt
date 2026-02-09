package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.LocationDto
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.LocationType

fun LocationDto.toDomain(): Location {
    return Location(
        latitude = latitude ?: throw IllegalStateException("latitude cannot be null"),
        longitude = longitude ?: throw IllegalStateException("longitude cannot be null"),
        id = id ?: throw IllegalStateException("Id Cannot be null"),
        name = name ?: throw IllegalStateException("name cannot be null"),
        description = description ?: throw IllegalStateException("description Cannot be null"),
        type = when (type?.name) {
            "ATTRACTION" -> LocationType.ATTRACTION
            "VIEWPOINT" -> LocationType.VIEWPOINT
            "MEETING_POINT" -> LocationType.MEETING_POINT
            "REST_STOP" -> LocationType.REST_STOP
            "PHOTO_SPOT" -> LocationType.PHOTO_SPOT
            "RESTAURANT" -> LocationType.RESTAURANT
            "HOTEL" -> LocationType.HOTEL
            "OTHER" -> LocationType.OTHER
            else -> throw IllegalStateException("Unkown type")
        },
        address = address ?: throw IllegalStateException("address Cannot be null"),
        city = city ?: throw IllegalStateException("city cannot be null"),
        province = province ?: throw IllegalStateException("province cannot be null")
    )
}