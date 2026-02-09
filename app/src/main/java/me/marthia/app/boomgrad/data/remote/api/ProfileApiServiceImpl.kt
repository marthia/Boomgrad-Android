package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.UserDto

class ProfileApiServiceImpl(
    private val client: HttpClient
) : ProfileApiService {

    override suspend fun profileInfo(userId: Long): Result<BaseResponse<UserDto>> {
        return client.safeGet<BaseResponse<UserDto>>("profile/user/$userId")
    }
}