package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.AttractionApiService
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.domain.repository.TourRepository

class TourRepositoryImpl(
    private val api: TourApiService
): TourRepository {
    override suspend fun getList(): Result<List<Tour>> {
        return try {
            val response = api.getTours()
            val attractions = response.map { it.toDomain() }
            Result.success(attractions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTourDetail(tourId: Long): Result<Tour> {
        TODO("Not yet implemented")
    }
}