package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.CountyDto
import me.marthia.app.boomgrad.domain.model.County

fun CountyDto.toDomain(): County {
    return County(
        id = this.id,
        name = this.name,
        provinceId = this.provinceId
    )
}

fun List<CountyDto>.toDomains(): List<County> {
    return this.map { it.toDomain() }
}