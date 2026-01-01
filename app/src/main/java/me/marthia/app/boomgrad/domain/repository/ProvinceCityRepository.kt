package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.model.County
import me.marthia.app.boomgrad.domain.model.Province

interface ProvinceCityRepository {
    suspend fun getCity(countyId: Long, provinceId: Long): Result<List<City>>
    suspend fun getCounty(provinceId: Long): Result<List<County>>
    suspend fun getProvince(): Result<List<Province>>
}