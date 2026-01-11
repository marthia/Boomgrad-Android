package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.CategoryApiService
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    val categoryApiService: CategoryApiService
) : CategoryRepository {
    override suspend fun getList(): Result<List<AttractionCategory>> {
        return try {
            val response = categoryApiService.getList()
            val result = response.map { it.toDomain() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategory(categoryId: Long): Result<AttractionCategory> {
        return try {
            val response = categoryApiService.get(categoryId = categoryId)
            val result = response.toDomain()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}