package me.marthia.app.boomgrad.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.CreateTourDto
import me.marthia.app.boomgrad.data.remote.dto.TourDetailDto
import me.marthia.app.boomgrad.domain.model.CreateTour
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourList

interface TourRepository {
    fun getAllActiveTours(pageSize: Int = 20): Flow<PagingData<TourList>>
    suspend fun getList(): Result<List<TourList>>
    suspend fun getTourDetail(tourId: Long): Result<TourDetail>
    suspend fun createTour(tour: CreateTour): Result<TourDetail>
}