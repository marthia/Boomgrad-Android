package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.CartItemDto
import me.marthia.app.boomgrad.data.remote.dto.CartResponseDto
import me.marthia.app.boomgrad.domain.model.Cart
import me.marthia.app.boomgrad.domain.model.CartItem
import java.time.LocalDate
import java.time.LocalTime

fun CartItemDto.toDomain(): CartItem = CartItem(
    id = id,
    tourId = tourId,
    scheduleId = scheduleId,
    tourTitle = tourTitle,
    tourImageUrl = tourImageUrl,
    durationMinutes = durationMinutes,
    tourDate = LocalDate.parse(tourDate),
    startTime = LocalTime.parse(startTime),
    pricePerPerson = pricePerPerson,
    currentPrice = currentPrice,
    priceChanged = priceChanged,
    peopleCount = peopleCount,
    maxPeople = maxPeople,
    availableSpots = availableSpots,
    availabilityWarning = availabilityWarning,
)

fun CartResponseDto.toDomain(): Cart = Cart(
    items = items.map { it.toDomain() },
    subtotal = subtotal,
    serviceFee = serviceFee,
    total = total,
    hasAvailabilityIssues = hasAvailabilityIssues,
    itemCount = itemCount,
)