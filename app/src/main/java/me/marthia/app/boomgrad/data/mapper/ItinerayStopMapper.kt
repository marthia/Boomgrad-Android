package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ItineraryStopDto
import me.marthia.app.boomgrad.domain.model.ItineraryStop

fun ItineraryStopDto.toDomain() = ItineraryStop(
    id = id,
    stopOrder = stopOrder,
    title = title,
    description = description,
    durationMinutes = durationMinutes,
    location = location,
    latitude = latitude,
    longitude = longitude,
    date = "چهارشنبه ۱۸ دی ۱۴۰۴"
)

fun List<ItineraryStopDto>.toDomains() = this.map { it.toDomain() }