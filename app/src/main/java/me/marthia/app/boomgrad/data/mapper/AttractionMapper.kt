package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.ContactInfoDto
import me.marthia.app.boomgrad.data.remote.dto.LocationDto
import me.marthia.app.boomgrad.data.remote.dto.OpeningHoursDto
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.AttractionOpeningHours

fun AttractionDto.toDomain(): Attraction {
    return Attraction(
        id = id,
        name = name,
        description = description,
        category = category,
        imageUrl = imageUrl,
        rating = rating,
        contactInfo = contactInfo.toDomain(),
        openingHours = openingHours.toDomain(),
        location = location.toDomain(),
        isFavorite = false
    )
}

fun ContactInfoDto.toDomain(): AttractionContactInfo {
    return AttractionContactInfo(
        phone = phone,
        email = email,
        website = website,
        address = address
    )
}

fun OpeningHoursDto.toDomain(): AttractionOpeningHours {
    return AttractionOpeningHours(
        monday = monday,
        tuesday = tuesday,
        wednesday = wednesday,
        thursday = thursday,
        friday = friday,
        saturday = saturday,
        sunday = sunday
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude
    )
}