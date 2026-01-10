package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.AttractionsResponse

class AttractionApiServiceImpl(
    private val client: HttpClient
) : AttractionApiService {

    override suspend fun getAttractions(
        page: Int,
        limit: Int
    ): AttractionsResponse {
        return client.get("attractions") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }

    override suspend fun getAttractionById(
        id: String
    ): AttractionDto {
        return client.get("attractions/$id").body()
    }

    override suspend fun searchAttractions(
        query: String,
        limit: Int
    ): AttractionsResponse {
        return client.get("attractions/search") {
            parameter("q", query)
            parameter("limit", limit)
        }.body()
    }
}