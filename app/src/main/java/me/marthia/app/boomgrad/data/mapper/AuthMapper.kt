package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.LoginDto
import me.marthia.app.boomgrad.domain.model.Login

fun LoginDto.toDomain() = Login(
    token = this.token
)