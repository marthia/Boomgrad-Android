package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse

interface AttractionApiService {

    suspend fun getAttractions(
        page: Int,
        limit: Int
    ): Result<BaseResponse<PagedResponse<AttractionDto>>>

    suspend fun getAttractionById(
        id: Long
    ): Result<BaseResponse<AttractionDto>>

    suspend fun searchAttractions(
        query: String,
        limit: Int
    ): Result<BaseResponse<PagedResponse<AttractionDto>>>
}