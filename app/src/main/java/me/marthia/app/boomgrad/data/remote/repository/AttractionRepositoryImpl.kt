package me.marthia.app.boomgrad.data.remote.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.AttractionApiService
import me.marthia.app.boomgrad.data.remote.datasource.AttractionPagingDataSource
import me.marthia.app.boomgrad.data.remote.util.NetworkFailure
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import timber.log.Timber

class AttractionRepositoryImpl(
    private val apiService: AttractionApiService,
    private val context: Context,
) : AttractionRepository {

    private val favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    private val _favoriteAttractions = MutableStateFlow<List<Attraction>>(emptyList())

    override fun getAttractions(pageSize: Int): Flow<PagingData<Attraction>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = true),
            pagingSourceFactory = { AttractionPagingDataSource(apiService) }
        ).flow
    }

    override suspend fun getTopAttractions(): Result<List<Attraction>> {
        return runCatching {
            val response = apiService.getAttractions().getOrThrow()
            response.content.map { it.toDomain() }
        }.onFailure { error ->
            Timber.e(error, "getTopAttractions failed : ${error.message}")
        }.recoverCatching { error ->
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }

    override suspend fun getAttractionById(id: String): Result<Attraction> {
        return runCatching {
            apiService.getAttractionById(id).getOrThrow().toDomain()
        }.onFailure { error ->
            Timber.e(error, "getAttraction of id $id failed : ${error.message}")
        }.recoverCatching { error ->
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }

    override suspend fun searchAttractions(query: String): Result<List<Attraction>> {
        return runCatching {
            val response = apiService.searchAttractions(query).getOrThrow()
            response.content.map { it.toDomain() }
        }.onFailure { error ->
            Timber.e(error, "search Attractions with query $query failed : ${error.message}")
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }

    override fun getFavoriteAttractions(): Flow<List<Attraction>> {
        return _favoriteAttractions
    }

    override suspend fun toggleFavorite(attractionId: String) {
        val currentFavorites = favoriteIds.value.toMutableSet()
        if (currentFavorites.contains(attractionId)) {
            currentFavorites.remove(attractionId)
        } else {
            currentFavorites.add(attractionId)
        }
        favoriteIds.value = currentFavorites
        // Note: In production, you'd persist this to local database
    }
}