package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ProvinceDto
import me.marthia.app.boomgrad.domain.model.Province


fun ProvinceDto.toDomain(): Province {
    return Province(
        id = this.id,
        name = this.name
    )
}

fun List<ProvinceDto>.toDomains(): List<Province> {
    return this.map { it.toDomain() }
}
