package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.OpeningHoursDto
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours

fun List<OpeningHoursDto>.toDomainList() = this.map { it.toDomain() }

fun OpeningHoursDto.toDomain(): AttractionOpeningHours {
    return AttractionOpeningHours(
        id = id ?: throw IllegalStateException("id cannot be null"),
        workingDate = workingDate ?: throw IllegalStateException("date cannot be null"),
        workingTime = workingTime ?: throw IllegalStateException("working hour cannot be null"),
        isClosed = isClosed ?: false,
        notes = notes ?: "",
    )
}

