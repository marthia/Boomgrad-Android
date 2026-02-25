package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.CreateItineraryStopDto
import me.marthia.app.boomgrad.data.remote.dto.CreateTourDto
import me.marthia.app.boomgrad.domain.model.CreateTour


private fun CreateTour.toDto() = CreateTourDto(
    title = title,
    description = description,
    categoryId = categoryId,
    cityId = cityId,
    duration = durationMinutes,
    price = price.toPlainString(),
    maxPeople = maxPeople,
    meetingPoint = meetingPoint,
    highlights = highlights,
    images = images,
    requiredItems = requiredItems,
    level = level,
    dueDate = dueDate.toString(),       // LocalDate.toString() → "yyyy-MM-dd"
    startTime = startTime.toString(),     // LocalTime.toString() → "HH:mm"
    demographic = demographic,
    itinerary = itinerary.mapIndexed { index, stop ->
        CreateItineraryStopDto(
            title = stop.title,
            description = stop.description,
        )
    },
)