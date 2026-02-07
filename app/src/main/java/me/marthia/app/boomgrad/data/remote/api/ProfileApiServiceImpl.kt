package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.ProfileDto

class ProfileApiServiceImpl(
    private val client: HttpClient
) : ProfileApiService {

    override suspend fun profileInfo(userId: Long): Result<BaseResponse<ProfileDto>> {
        return client.safeGet<BaseResponse<ProfileDto>>("profile/user/$userId")
    }
}