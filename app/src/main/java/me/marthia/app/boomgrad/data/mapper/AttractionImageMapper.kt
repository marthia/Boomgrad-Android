package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionImageDto
import me.marthia.app.boomgrad.domain.model.AttractionImage

fun AttractionImageDto.toDomain() =
    AttractionImage(
        id = id ?: throw IllegalStateException("id cannot be null"),
        imageUrl = imageUrl ?: throw IllegalStateException("image cannot be null")
    )

fun List<AttractionImageDto>.toDomainList() = this.map { it.toDomain() }