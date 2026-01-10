package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.TourDto
import me.marthia.app.boomgrad.domain.model.Tour

fun TourDto.toDomain(): Tour {
    return Tour(
        id = id,
        title = title,
        description = description,
        guide = guide.toDomain(),
        highlights = highlights,
        duration = duration,
        price = price,
        category = category.toDomain(),
        maxPeople = maxPeople,
        status = status,
        rate = rate,
        reviews = reviews.toDomains(),
        images = images,
        requiredItems = requiredItems,
        level = level,
        dueDate = dueDate,
        startTime = startTime,
        demographic = demographic,
        itinerary = itinerary.toDomains(),
    )
}


fun List<TourDto>.toDomainList() = this.map { it.toDomain() }