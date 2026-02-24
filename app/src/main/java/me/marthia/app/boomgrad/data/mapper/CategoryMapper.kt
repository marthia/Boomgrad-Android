package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.domain.model.AttractionCategory

fun AttractionCategoryDto.toDomain() = AttractionCategory(
    id = id ?: throw IllegalStateException("id cannot be null"),
    type = categoryType ?: throw IllegalStateException("type cannot be null"),
    description = description ?: throw IllegalStateException("description cannot be null"),
    image = icon ?: throw IllegalStateException("icon cannot be null"),
)