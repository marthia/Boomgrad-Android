package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ProfileDto
import me.marthia.app.boomgrad.domain.model.Profile

fun ProfileDto.toDomain() = Profile(
    id = id,
    name = name,
    username = username,
    email = email,
    phone = phone,
    image = image,
)