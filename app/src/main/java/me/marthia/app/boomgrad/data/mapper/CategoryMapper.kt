package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.domain.model.AttractionCategory

fun AttractionCategoryDto.toDomain() = AttractionCategory(
    id = id,
    name = name,
    description = description,
    image = image,
)