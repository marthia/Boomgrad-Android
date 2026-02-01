package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.ContactInfoDto
import me.marthia.app.boomgrad.data.remote.dto.OpeningHoursDto
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours

fun AttractionDto.toDomain(): Attraction {
    return Attraction(
        id = id ?: throw IllegalStateException("id cannot be null"),
        category = category ?: throw IllegalStateException("category cannot be null"),
        images = imageUrl?.let { listOf(imageUrl) } ?: listOf(),
        rating = rating ?: throw IllegalStateException("rating cannot be null"),
        contactInfo = contactInfo?.toDomain()
            ?: throw IllegalStateException("Contact Info Cannot be null"),
        openingHours = openingHours?.toDomainList()
            ?: throw IllegalStateException("Opening hours Cannot be null"),
        location = location?.toDomain() ?: throw IllegalStateException("Location Cannot be null"),
        reviewCount = reviewCount ?: throw IllegalStateException("review count cannot be null"),
        isFavorite = false,

        )
}

fun ContactInfoDto.toDomain(): AttractionContactInfo {
    return AttractionContactInfo(
        phone = phone ?: throw IllegalStateException("phone cannot be null"),
        email = email ?: throw IllegalStateException("email cannot be null"),
        website = website ?: throw IllegalStateException("address cannot be null"),
    )
}

fun List<OpeningHoursDto>.toDomainList() = this.map { it.toDomain() }

fun OpeningHoursDto.toDomain(): AttractionOpeningHours {
    return AttractionOpeningHours(
        date = date ?: throw IllegalStateException("date cannot be null"),
        workingHour = workingHour ?: throw IllegalStateException("working hour cannot be null"),
    )
}

