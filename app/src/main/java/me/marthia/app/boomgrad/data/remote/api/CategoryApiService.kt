package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto

interface CategoryApiService {

    suspend fun getList() : List<AttractionCategoryDto>
    suspend fun get(categoryId: Long) : AttractionCategoryDto
}