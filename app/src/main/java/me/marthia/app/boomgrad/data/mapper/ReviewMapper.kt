package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ReviewDto
import me.marthia.app.boomgrad.domain.model.Review

fun ReviewDto.toDomain() = Review(
    id = id,
    userId = userId,
    userName = userName,
    title = title,
    reviewBody = reviewBody,
)


fun List<ReviewDto>.toDomains() = this.map { it.toDomain() }