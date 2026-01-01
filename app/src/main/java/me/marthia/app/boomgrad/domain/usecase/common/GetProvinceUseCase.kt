package me.marthia.app.boomgrad.domain.usecase.common

import me.marthia.app.boomgrad.domain.model.Province
import me.marthia.app.boomgrad.domain.repository.ProvinceCityRepository

class GetProvinceUseCase(
    private val repository: ProvinceCityRepository
) {
    suspend operator fun invoke(): Result<List<Province>> {
        return repository.getProvince()
    }
}