package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.data.remote.dto.BaseListResponse

interface CategoryApiService {

    suspend fun getList(): Result<BaseListResponse<AttractionCategoryDto>>
    suspend fun get(categoryId: Long): Result<AttractionCategoryDto>
}