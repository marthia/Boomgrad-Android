package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.ContactInfoDto
import me.marthia.app.boomgrad.data.remote.dto.LocationDto
import me.marthia.app.boomgrad.data.remote.dto.OpeningHoursDto
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.ContactInfo
import me.marthia.app.boomgrad.domain.model.Location
import me.marthia.app.boomgrad.domain.model.OpeningHours

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

fun ContactInfoDto.toDomain(): ContactInfo {
    return ContactInfo(
        phone = phone,
        email = email,
        website = website,
        address = address
    )
}

fun OpeningHoursDto.toDomain(): OpeningHours {
    return OpeningHours(
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