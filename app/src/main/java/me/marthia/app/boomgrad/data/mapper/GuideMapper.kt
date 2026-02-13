package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.GuideDto
import me.marthia.app.boomgrad.domain.model.Guide

fun GuideDto.toDomain(): Guide {
    return Guide(
        id = id,
        bio = "bio",
        fullName = fullName,
        userId = -1,
        verified = verified,
        totalTours = totalTours,
        averageRating = averageRating,
    )
}