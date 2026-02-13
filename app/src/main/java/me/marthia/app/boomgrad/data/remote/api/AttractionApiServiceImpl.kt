package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse

class AttractionApiServiceImpl(
    private val client: HttpClient
) : AttractionApiService {

    override suspend fun getAttractions(
        page: Int,
        limit: Int
    ): Result<BaseResponse<PagedResponse<AttractionDto>>> {
        return client.safeGet("attractions") {
            parameter("page", page)
            parameter("limit", limit)
        }
    }

    override suspend fun getAttractionById(
        id: Long
    ): Result<BaseResponse<AttractionDto>> {
        return client.safeGet("attractions/$id")
    }

    override suspend fun searchAttractions(
        query: String,
        limit: Int
    ): Result<BaseResponse<PagedResponse<AttractionDto>>> {
        return client.safeGet("attractions/search") {
            parameter("q", query)
            parameter("limit", limit)
        }
    }
}