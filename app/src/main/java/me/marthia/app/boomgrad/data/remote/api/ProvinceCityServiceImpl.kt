package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import me.marthia.app.boomgrad.data.remote.dto.CityDto
import me.marthia.app.boomgrad.data.remote.dto.CountyDto
import me.marthia.app.boomgrad.data.remote.dto.ProvinceDto
import timber.log.Timber

class ProvinceCityServiceImpl(
    private val client: HttpClient,
) : ProvinceCityService {


    override suspend fun getCity(
        provinceId: Long,
        countyId: Long
    ): Result<List<CityDto>> {

        return try {
            val response = client.get("api/city/$provinceId/$countyId")
            {}.body<List<CityDto>>()

            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch cities for province: $provinceId, county: $countyId")
            Result.failure(e)
        }
    }

    override suspend fun getCounty(
        provinceId: Long,
    ): Result<List<CountyDto>> {

        return try {
            val response = client.get("api/county/$provinceId")
            {}.body<List<CountyDto>>()

            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch counties for province: $provinceId")
            Result.failure(e)
        }
    }

    override suspend fun getProvince(): Result<List<ProvinceDto>> {

        return try {
            val response = client.get("api/province")
            {}.body<List<ProvinceDto>>()

            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch provinces")
            Result.failure(e)
        }
    }


}