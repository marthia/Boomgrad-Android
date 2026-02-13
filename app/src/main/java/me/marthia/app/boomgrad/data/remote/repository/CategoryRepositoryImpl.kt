package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.CategoryApiService
import me.marthia.app.boomgrad.data.remote.util.NetworkFailure
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure
import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.repository.CategoryRepository
import timber.log.Timber

class CategoryRepositoryImpl(
    val categoryApiService: CategoryApiService
) : CategoryRepository {
    override suspend fun getList(): Result<List<AttractionCategory>> {
        return runCatching {
            val response = categoryApiService.getList().getOrThrow()
            response.data.map { it.toDomain() }
        }.onFailure { error ->
            Timber.e(error, "get category list failed : ${error.message}")
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }

    override suspend fun getCategory(categoryId: Long): Result<AttractionCategory> {
        return runCatching {
            val response = categoryApiService.get(categoryId = categoryId).getOrThrow()
            response.toDomain()
        }.onFailure { error ->
            Timber.e(error, "getCategory of id $categoryId failed : ${error.message}")
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }
}