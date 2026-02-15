package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse
import me.marthia.app.boomgrad.data.remote.dto.TourDetailDto
import me.marthia.app.boomgrad.data.remote.dto.TourListDto

class TourApiServiceImpl(
    private val client: HttpClient
) : TourApiService {

    override suspend fun getTours(
        page: Int,
        limit: Int
    ): Result<BaseResponse<PagedResponse<TourListDto>>> {
        return client.safeGet("tours") {
            parameter("page", page)
            parameter("limit", limit)
        }
    }

    override suspend fun getTourById(
        id: Long
    ): Result<BaseResponse<TourDetailDto>> {
        return client.safeGet("tours/$id")
    }

    override suspend fun searchTours(
        query: String,
        limit: Int
    ): Result<BaseResponse<PagedResponse<TourDetailDto>>> {
        return client.safeGet("tours/search") {
            parameter("q", query)
            parameter("limit", limit)
        }
    }
}