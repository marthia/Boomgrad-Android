package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse
import me.marthia.app.boomgrad.data.remote.dto.TourDto

interface TourApiService {

    suspend fun getTours(
        page: Int = 0,
        limit: Int = 20
    ): Result<BaseResponse<PagedResponse<TourDto>>>

    suspend fun getTourById(
        id: Long
    ): Result<BaseResponse<TourDto>>

    suspend fun searchTours(
        query: String,
        limit: Int = 20
    ): Result<BaseResponse<PagedResponse<TourDto>>>
}