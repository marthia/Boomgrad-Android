package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.GuideDto
import me.marthia.app.boomgrad.data.remote.dto.UserDto

interface GuideApiService {

    suspend fun guideInfo(guideId: Long): Result<BaseResponse<GuideDto>>
}