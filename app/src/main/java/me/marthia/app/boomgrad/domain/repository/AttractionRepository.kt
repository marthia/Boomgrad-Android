package me.marthia.app.boomgrad.domain.repository

import kotlinx.coroutines.flow.Flow
import me.marthia.app.boomgrad.data.remote.repository.MockAttraction
import me.marthia.app.boomgrad.data.remote.repository.MockAttractionResponse
import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.Location

interface AttractionRepository {
    suspend fun getAttractions(): Result<List<Attraction>>
    suspend fun getMockAttractions(): Result<List<MockAttraction>>
    suspend fun getTopAttractions(): Result<List<Attraction>>
    suspend fun getAttractionById(id: String): Result<Attraction>
    suspend fun searchAttractions(query: String): Result<List<Attraction>>
    fun getFavoriteAttractions(): Flow<List<Attraction>>
    suspend fun toggleFavorite(attractionId: String)
}