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
        imageUrl = imageUrl,
        rating = rating,
        contactInfo = contactInfo?.toDomain()
            ?: throw IllegalStateException("Contact Info Cannot be null"),
        openingHours = openingHours?.toDomain()
            ?: throw IllegalStateException("Opening hours Cannot be null"),
        location = location?.toDomain() ?: throw IllegalStateException("Location Cannot be null"),
        isFavorite = false
    )
}

fun ContactInfoDto.toDomain(): AttractionContactInfo {
    return AttractionContactInfo(
        phone = phone,
        email = email,
        website = website,
    )
}

fun OpeningHoursDto.toDomain(): AttractionOpeningHours {
    return AttractionOpeningHours(
        monday = monday ?: throw IllegalStateException("monday cannot be null"),
        tuesday = tuesday ?: throw IllegalStateException("tuesday cannot be null"),
        wednesday = wednesday ?: throw IllegalStateException("wednesday cannot be null"),
        thursday = thursday ?: throw IllegalStateException("thursday cannot be null"),
        friday = friday ?: throw IllegalStateException("friday cannot be null"),
        saturday = saturday ?: throw IllegalStateException("saturday cannot be null"),
        sunday = sunday ?: throw IllegalStateException("sunday cannot be null"),
    )
}

