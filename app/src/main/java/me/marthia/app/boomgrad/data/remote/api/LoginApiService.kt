package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.LoginResponseDto
import me.marthia.app.boomgrad.data.remote.dto.SmsResponseDto

interface LoginApiService {
    suspend fun login(username: String, password: String): Result<LoginResponseDto>
    suspend fun checkSmsCode(code: String): Result<SmsResponseDto>


}