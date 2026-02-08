package me.marthia.app.boomgrad.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.domain.model.Attraction

interface AttractionRepository {
     fun getAttractions(pageSize: Int = 20): Flow<PagingData<Attraction>>
    suspend fun getTopAttractions(): Result<List<Attraction>>
    suspend fun getAttractionById(id: String): Result<Attraction>
    suspend fun searchAttractions(query: String): Result<List<Attraction>>
    fun getFavoriteAttractions(): Flow<List<Attraction>>
    suspend fun toggleFavorite(attractionId: String)
}