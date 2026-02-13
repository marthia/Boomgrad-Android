package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.mapper.toDomainList
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.model.ItineraryStop
import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.domain.model.TourStatus
import me.marthia.app.boomgrad.domain.repository.TourRepository

class TourRepositoryImpl(
    private val api: TourApiService
) : TourRepository {

    override suspend fun getList(): Result<List<Tour>> {
        return runCatching {
            api.getTours().getOrThrow()
        }.map { response ->
            response.data.content?.toDomainList() ?: throw IllegalStateException("content cannot be null")
        }
    }

    override suspend fun getTourDetail(tourId: Long): Result<Tour> {
        return runCatching {
            api.getTourById(tourId).getOrThrow()
        }.map { dto ->
            dto.data.toDomain()
        }
    }
}