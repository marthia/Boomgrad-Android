package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.TourDetailDto
import me.marthia.app.boomgrad.data.remote.dto.TourListDto
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.CategoryType
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourList

fun TourListDto.toDomain(): TourList {
    return TourList(
        id = id ?: 1,
        title = title ?: "",
        description = description ?: " ",
        images = listOf(
            "https://picsum.photos/600/800",
            "https://picsum.photos/400/900",
            "https://picsum.photos/720//1200",
        ),
        duration = duration ?: 0,
        price = price ?: 0.0,
        dueDate = dueDate ?: "",
        city = city ?: "",
        category = category?.toDomain() ?: throw IllegalStateException("category is null"),
    )
}


fun List<TourListDto>.toDomainList() = this.map { it.toDomain() }


fun TourDetailDto.toDomainDetail(): TourDetail {
    return TourDetail(
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
        city = city.toDomain()
    )
}


fun List<TourDetailDto>.toDomainDetailList() = this.map { it.toDomainDetail() }