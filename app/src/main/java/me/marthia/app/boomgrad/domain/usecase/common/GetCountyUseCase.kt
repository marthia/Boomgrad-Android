package me.marthia.app.boomgrad.domain.usecase.common

import me.marthia.app.boomgrad.domain.model.County
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository

class GetCountyUseCase(
    private val repository: ProvinceCityRepository
) {
    data class Params(val provinceId: Long)

    suspend operator fun invoke(params: Params): Result<List<County>> {
        return repository.getCounty(params.provinceId)
    }
}