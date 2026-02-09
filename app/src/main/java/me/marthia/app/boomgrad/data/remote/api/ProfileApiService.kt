package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.UserDto

interface ProfileApiService {

    suspend fun profileInfo(userId: Long): Result<BaseResponse<UserDto>>
}