package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.AttractionCategoryDto
import me.marthia.app.boomgrad.data.remote.dto.AttractionDto
import me.marthia.app.boomgrad.data.remote.dto.AttractionsResponse

class CategoryApiServiceImpl(
    private val client: HttpClient
) : CategoryApiService {


    override suspend fun getList(): List<AttractionCategoryDto> {
        return client.get("category").body()
    }

    override suspend fun get(categoryId: Long): AttractionCategoryDto {
        return client.get("category/$categoryId").body()
    }
}