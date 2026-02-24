package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.ContactInfoDto
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.AttractionContactInfo
import me.marthia.app.boomgrad.domain.model.AttractionImage

fun AttractionDto.toDomain(): Attraction {
    return Attraction(
        id = id ?: throw IllegalStateException("id cannot be null"),
        category = category?.toDomain() ?: throw IllegalStateException("category cannot be null"),
//        images = images?.toDomainList() ?: throw IllegalStateException("images cannot be null"),
        images = listOf(
            AttractionImage(1, "https://picsum.photos/1200"),
            AttractionImage(2, "https://picsum.photos/1300")
        ),
        rating = rating ?: throw IllegalStateException("rating cannot be null"),
        contactInfo = contactInfo?.toDomain()
            ?: throw IllegalStateException("Contact Info Cannot be null"),
        openingHours = openingHours?.toDomainList()
            ?: throw IllegalStateException("Opening hours Cannot be null"),
        location = location?.toDomain() ?: throw IllegalStateException("Location Cannot be null"),
        reviewCount = reviewCount ?: throw IllegalStateException("review count cannot be null"),
        isFavorite = false,
        reviews = reviews?.toDomains() ?: throw IllegalStateException("reviews cannot be null"),
        relatedTours = tours?.toDomainList() ?: throw IllegalStateException("tours cannot be null"),
    )
}


fun ContactInfoDto.toDomain(): AttractionContactInfo {
    return AttractionContactInfo(
        phone = phone ?: "",
        address = address ?: throw IllegalStateException("address cannot be null"),
        website = website ?: "",
    )
}