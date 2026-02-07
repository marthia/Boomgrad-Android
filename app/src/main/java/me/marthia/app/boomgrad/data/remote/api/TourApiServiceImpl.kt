package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.BaseListResponse
import me.marthia.app.boomgrad.data.remote.dto.TourDto

class TourApiServiceImpl(
    private val client: HttpClient
) : TourApiService {

    override suspend fun getTours(
        page: Int,
        limit: Int
    ): Result<BaseListResponse<TourDto>> {
        return client.safeGet("tours") {
            parameter("page", page)
            parameter("limit", limit)
        }
    }

    override suspend fun getTourById(
        id: Long
    ): Result<TourDto> {
        return client.safeGet("tours/$id")
    }

    override suspend fun searchTours(
        query: String,
        limit: Int
    ): Result<BaseListResponse<TourDto>> {
        return client.safeGet("tours/search") {
            parameter("q", query)
            parameter("limit", limit)
        }
    }
}