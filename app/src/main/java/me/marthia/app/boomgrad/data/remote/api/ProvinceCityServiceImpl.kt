package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import me.marthia.app.boomgrad.data.remote.dto.PagedResponse
import me.marthia.app.boomgrad.data.remote.dto.CityDto
import me.marthia.app.boomgrad.data.remote.dto.CountyDto
import me.marthia.app.boomgrad.data.remote.dto.ProvinceDto

class ProvinceCityServiceImpl(
    private val client: HttpClient,
) : ProvinceCityService {

    override suspend fun getCity(
        provinceId: Long,
        countyId: Long
    ): Result<PagedResponse<CityDto>> {
        return client.safeGet("city/$provinceId/$countyId")
    }

    override suspend fun getCounty(
        provinceId: Long,
    ): Result<PagedResponse<CountyDto>> {
        return client.safeGet("county/$provinceId")
    }

    override suspend fun getProvince(): Result<PagedResponse<ProvinceDto>> {
        return client.safeGet("province")
    }
}