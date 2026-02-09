package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ReviewDto
import me.marthia.app.boomgrad.domain.model.Review

fun ReviewDto.toDomain() = Review(
    id = id,
    user = user.toDomain(),
    title = title,
    content = content,
    date = date
)


fun List<ReviewDto>.toDomains() = this.map { it.toDomain() }