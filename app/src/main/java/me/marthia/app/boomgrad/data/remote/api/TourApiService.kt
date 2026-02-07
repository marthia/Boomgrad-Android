package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseListResponse
import me.marthia.app.boomgrad.data.remote.dto.TourDto

interface TourApiService {

    suspend fun getTours(
        page: Int = 1,
        limit: Int = 20
    ): Result<BaseListResponse<TourDto>>

    suspend fun getTourById(
        id: Long
    ): Result<TourDto>

    suspend fun searchTours(
        query: String,
        limit: Int = 20
    ): Result<BaseListResponse<TourDto>>
}