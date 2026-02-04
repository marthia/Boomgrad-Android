package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.TokenResponse
import me.marthia.app.boomgrad.domain.model.Login

fun TokenResponse.toDomain() = Login(
    token = this.accessToken,
    refreshToken = this.refreshToken
)