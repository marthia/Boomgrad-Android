package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.RegisterRequestBody
import me.marthia.app.boomgrad.data.remote.dto.TokenResponse

interface LoginApiService {
    suspend fun login(email: String, password: String): Result<BaseResponse<TokenResponse>>
    suspend fun register(
        request: RegisterRequestBody
    ): Result<BaseResponse<TokenResponse>>
}