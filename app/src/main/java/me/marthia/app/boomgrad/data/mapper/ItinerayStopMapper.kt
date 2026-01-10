package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ItineraryStopDto
import me.marthia.app.boomgrad.domain.model.ItineraryStop

fun ItineraryStopDto.toDomain() = ItineraryStop(
    destination = destination,
    duration = duration,
)

fun List<ItineraryStopDto>.toDomains() = this.map { it.toDomain() }