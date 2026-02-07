package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomains
import me.marthia.app.boomgrad.data.remote.api.ProvinceCityService
import me.marthia.app.boomgrad.data.remote.dto.BaseListResponse
import me.marthia.app.boomgrad.data.remote.dto.ProvinceDto
import me.marthia.app.boomgrad.data.remote.util.NetworkFailure
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.County
import me.marthia.app.boomgrad.domain.model.Province
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository
import timber.log.Timber

class ProvinceRepositoryImpl(
    private val api: ProvinceCityService,
) : ProvinceCityRepository {

    override suspend fun getProvince(): Result<List<Province>> {
        return runCatching {
            val response: BaseListResponse<ProvinceDto> = api.getProvince().getOrThrow()
            response.content.toDomains()
        }.onFailure { error ->
            Timber.e(error, "getAttractions failed : ${error.message}")
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }

    }

    override suspend fun getCounty(provinceId: Long): Result<List<County>> {

        return runCatching {
            val response = api.getCounty(provinceId = provinceId).getOrThrow()
            response.content.toDomains()
        }.onFailure { error ->
            Timber.e(error, "getCounty for provinceId of $provinceId failed : ${error.message}")
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }

    override suspend fun getCity(countyId: Long, provinceId: Long): Result<List<City>> {
        return runCatching {
            val response = api.getCity(provinceId = provinceId, countyId = countyId).getOrThrow()
            response.content.toDomains()
        }.onFailure { error ->
            Timber.e(
                error,
                "getCity county:${countyId} province:${provinceId} failed : ${error.message}"
            )
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }
}