package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.TourDto

class TourApiServiceImpl(
    private val client: HttpClient
) : TourApiService {

    override suspend fun getTours(
        page: Int,
        limit: Int
    ): List<TourDto> {
        return client.get("tours") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }

    override suspend fun getTourById(
        id: Long
    ): TourDto {
        return client.get("tours/$id").body()
    }

    override suspend fun searchTours(
        query: String,
        limit: Int
    ): List<TourDto> {
        return client.get("tours/search") {
            parameter("q", query)
            parameter("limit", limit)
        }.body()
    }
}