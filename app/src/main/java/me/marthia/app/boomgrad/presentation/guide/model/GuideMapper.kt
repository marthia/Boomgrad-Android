package me.marthia.app.boomgrad.presentation.guide.model

import me.marthia.app.boomgrad.domain.model.Guide

fun Guide.toUi() = GuideUi(
    id = id,
    bio = bio,
    fullName = fullName,
    userImage = "",
    phone = "",
    verified = verified,
    totalTours = totalTours,
    averageRating = averageRating,
    tours = listOf()
)