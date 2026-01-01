package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomains
import me.marthia.app.boomgrad.data.remote.api.ProvinceCityService
import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.County
import me.marthia.app.boomgrad.domain.model.Province
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository

class ProvinceRepositoryImpl(
    private val api: ProvinceCityService,
) : ProvinceCityRepository {

    override suspend fun getProvince(): Result<List<Province>> {
        val response = api.getProvince()
        return response.map { it.toDomains() }
    }

    override suspend fun getCounty(provinceId: Long): Result<List<County>> {

        val response = api.getCounty(provinceId = provinceId)
        return response.map { it.toDomains() }
    }

    override suspend fun getCity(countyId: Long, provinceId: Long): Result<List<City>> {
        val response = api.getCity(provinceId = provinceId, countyId = countyId)
        return response.map { it.toDomains() }
    }
}