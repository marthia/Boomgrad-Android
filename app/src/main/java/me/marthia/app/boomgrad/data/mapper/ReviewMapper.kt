package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ReviewDto
import me.marthia.app.boomgrad.domain.model.Review

fun ReviewDto.toDomain() = Review(
    id = id ?: throw IllegalStateException("id cannot be null"),
    user = user?.toDomain() ?: throw IllegalStateException("user cannot be null"),
    comment = comment ?: throw IllegalStateException("comment cannot be null"),
    date = date ?: throw IllegalStateException("date cannot be null")
)


fun List<ReviewDto>.toDomains() = this.map { it.toDomain() }