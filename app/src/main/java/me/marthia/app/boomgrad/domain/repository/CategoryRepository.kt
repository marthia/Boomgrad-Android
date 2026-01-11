package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.AttractionCategory

interface CategoryRepository {
    suspend fun getList(): Result<List<AttractionCategory>>
    suspend fun getCategory(categoryId: Long): Result<AttractionCategory>
}