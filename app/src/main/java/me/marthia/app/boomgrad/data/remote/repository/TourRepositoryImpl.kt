package me.marthia.app.boomgrad.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.mapper.toDomainDetail
import me.marthia.app.boomgrad.data.mapper.toDomainList
import me.marthia.app.boomgrad.data.remote.api.TourApiService
import me.marthia.app.boomgrad.data.remote.datasource.ToursPagingDataSource
import me.marthia.app.boomgrad.domain.model.CreateTour
import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.domain.repository.TourRepository

class TourRepositoryImpl(
    private val api: TourApiService
) : TourRepository {

    override suspend fun getList(): Result<List<TourList>> {
        return runCatching {
            api.getTours().getOrThrow()
        }.map { response ->
            response.data.content?.toDomainList()
                ?: throw IllegalStateException("content cannot be null")
        }
    }

    override suspend fun getTourDetail(tourId: Long): Result<TourDetail> {
        return runCatching {
            api.getTourById(tourId).getOrThrow()
        }.map { dto ->
            dto.data.toDomainDetail()
        }
    }

    override fun getAllActiveTours(pageSize: Int): Flow<PagingData<TourList>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ToursPagingDataSource(api) }
        ).flow
    }

    override suspend fun createTour(tour: CreateTour): Result<TourDetail> =
        api.createTour(tour.toDto()).map { it.data.toDomain() }
}