package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.GuideDto
import me.marthia.app.boomgrad.data.remote.dto.UserDto

class GuideApiServiceImpl(
    private val client: HttpClient
): GuideApiService {

    override suspend fun guideInfo(guideId: Long): Result<BaseResponse<GuideDto>> {
        return client.safeGet<BaseResponse<GuideDto>>("guide/$guideId")
    }
}