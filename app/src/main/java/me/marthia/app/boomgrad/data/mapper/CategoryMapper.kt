package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.domain.model.AttractionCategory

fun AttractionCategoryDto.toDomain() = AttractionCategory(
    id = id ?: -1, // fixme
    name = name ?: throw IllegalStateException("name cannot be null"),
    description = description ?: throw IllegalStateException("description cannot be null"),
    image = icon ?: "",
)