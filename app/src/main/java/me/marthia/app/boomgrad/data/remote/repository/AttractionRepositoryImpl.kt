package me.marthia.app.boomgrad.data.remote.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.AttractionApiService
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository

class AttractionRepositoryImpl(
    private val apiService: AttractionApiService,
    private val context: Context,
) : AttractionRepository {

    private val favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    private val _favoriteAttractions = MutableStateFlow<List<Attraction>>(emptyList())

    override suspend fun getAttractions(): Result<List<Attraction>> {
        return try {
            val response = apiService.getAttractions()
            val attractions = response.attractions.map { it.toDomain() }
            Result.success(attractions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopAttractions(): Result<List<Attraction>> {
        return try {
            val response = apiService.getAttractions()
            val attractions = response.attractions.map { it.toDomain() }
            Result.success(attractions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAttractionById(id: String): Result<Attraction> {
        return try {
            val attraction = apiService.getAttractionById(id).toDomain()
            Result.success(attraction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchAttractions(query: String): Result<List<Attraction>> {
        return try {
            val response = apiService.searchAttractions(query)
            val attractions = response.attractions.map { it.toDomain() }
            Result.success(attractions)
        } catch (e: Exception) {
            Result.failure(e)
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