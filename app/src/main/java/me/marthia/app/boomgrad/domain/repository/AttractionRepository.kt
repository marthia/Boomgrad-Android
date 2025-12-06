package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Attraction
import kotlinx.coroutines.flow.Flow

interface AttractionRepository {
    suspend fun getAttractions(): Result<List<Attraction>>
    suspend fun getAttractionById(id: String): Result<Attraction>
    suspend fun searchAttractions(query: String): Result<List<Attraction>>
    fun getFavoriteAttractions(): Flow<List<Attraction>>
    suspend fun toggleFavorite(attractionId: String)
}