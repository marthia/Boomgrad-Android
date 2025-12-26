package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.LoginDto
import me.marthia.app.boomgrad.data.remote.dto.SmsResponseDto

interface LoginApiService {
    suspend fun login(email: String, password: String): Result<BaseResponse<LoginDto>>
    suspend fun checkSmsCode(code: String): Result<SmsResponseDto>


}