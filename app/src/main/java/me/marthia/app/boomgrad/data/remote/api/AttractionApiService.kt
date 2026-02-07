package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.BaseListResponse

interface AttractionApiService {

    suspend fun getAttractions(
        page: Int = 1,
        limit: Int = 20
    ): Result<BaseListResponse<AttractionDto>>

    suspend fun getAttractionById(
        id: String
    ): Result<AttractionDto>

    suspend fun searchAttractions(
        query: String,
        limit: Int = 20
    ): Result<BaseListResponse<AttractionDto>>
}