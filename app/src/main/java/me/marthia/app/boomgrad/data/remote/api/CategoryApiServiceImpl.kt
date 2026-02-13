package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.ListResponse

class CategoryApiServiceImpl(
    private val client: HttpClient
) : CategoryApiService {

    override suspend fun getList(): Result<ListResponse<AttractionCategoryDto>> {
        return client.safeGet("category")
    }

    override suspend fun get(categoryId: Long): Result<AttractionCategoryDto> {
        return client.safeGet("category/$categoryId")
    }
}