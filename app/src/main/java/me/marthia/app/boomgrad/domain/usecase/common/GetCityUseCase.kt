package me.marthia.app.boomgrad.domain.usecase.common

import me.marthia.app.boomgrad.domain.model.City
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository

class GetCityUseCase(
    private val repository: ProvinceCityRepository
) {
    data class Params(
        val provinceId: Long,
        val countyId: Long,
    )

    suspend operator fun invoke(params: Params): Result<List<City>> {
        return repository.getCity(
            provinceId = params.provinceId,
            countyId = params.countyId
        )
    }
}