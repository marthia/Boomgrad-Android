package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.AttractionsResponse

interface TourApiService {

    suspend fun getAttractions(
        page: Int = 1,
        limit: Int = 20
    ): AttractionsResponse

    suspend fun getAttractionById(
        id: String
    ): AttractionDto

    suspend fun searchAttractions(
        query: String,
        limit: Int = 20
    ): AttractionsResponse
}