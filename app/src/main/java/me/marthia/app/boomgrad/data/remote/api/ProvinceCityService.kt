package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.BaseListResponse
import me.marthia.app.boomgrad.data.remote.dto.CityDto
import me.marthia.app.boomgrad.data.remote.dto.CountyDto
import me.marthia.app.boomgrad.data.remote.dto.ProvinceDto

interface ProvinceCityService {
    suspend fun getCity(provinceId: Long, countyId: Long): Result<BaseListResponse<CityDto>>
    suspend fun getCounty(provinceId: Long): Result<BaseListResponse<CountyDto>>
    suspend fun getProvince(): Result<BaseListResponse<ProvinceDto>>
}