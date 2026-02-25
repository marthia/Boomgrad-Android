package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.CreateTourDto
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse
import me.marthia.app.boomgrad.data.remote.dto.TourDetailDto
import me.marthia.app.boomgrad.data.remote.dto.TourListDto

interface TourApiService {

    suspend fun getTours(
        page: Int = 0,
        limit: Int = 20
    ): Result<BaseResponse<PagedResponse<TourListDto>>>

    suspend fun getTourById(
        id: Long
    ): Result<BaseResponse<TourDetailDto>>

    suspend fun searchTours(
        query: String,
        limit: Int = 20
    ): Result<BaseResponse<PagedResponse<TourDetailDto>>>

    suspend fun createTour(body: CreateTourDto): Result<BaseResponse<TourDetailDto>>
}