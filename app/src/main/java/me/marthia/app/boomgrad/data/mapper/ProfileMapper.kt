package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.UserDto
import me.marthia.app.boomgrad.domain.model.User

fun UserDto.toDomain() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    phone = phone,
    image = image,
)