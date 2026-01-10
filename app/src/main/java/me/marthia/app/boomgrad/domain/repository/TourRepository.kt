package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Tour

interface TourRepository {
    suspend fun getList(): Result<List<Tour>>
    suspend fun getTourDetail(tourId: Long): Result<Tour>
}