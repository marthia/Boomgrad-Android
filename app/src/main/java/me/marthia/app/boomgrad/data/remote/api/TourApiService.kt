package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.TourDto

interface TourApiService {

    suspend fun getTours(
        page: Int = 1,
        limit: Int = 20
    ): List<TourDto>

    suspend fun getTourById(
        id: String
    ): TourDto

    suspend fun searchTours(
        query: String,
        limit: Int = 20
    ): List<TourDto>
}