package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.data.remote.dto.ListResponse
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse

interface CategoryApiService {

    suspend fun getList(): Result<ListResponse<AttractionCategoryDto>>
    suspend fun get(categoryId: Long): Result<AttractionCategoryDto>
}