package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.UserDto
import me.marthia.app.boomgrad.domain.model.User

fun UserDto.toDomain() = User(
    id = id ?: throw IllegalStateException("id cannot be null"),
    name = name ?: throw IllegalStateException("name cannot be null"),
//    username = username ?: throw IllegalStateException("username cannot be null"),
    email = email ?: throw IllegalStateException("email cannot be null"),
    phone = phone ?: throw IllegalStateException("phone cannot be null"),
    image = image ?: throw IllegalStateException("image cannot be null"),
)