package me.marthia.app.boomgrad.domain.usecase.tour

import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.model.TourList
import me.marthia.app.boomgrad.domain.repository.TourRepository

class GetWeekRecommendedUseCase(
    private val repository: TourRepository
) {

    suspend operator fun invoke(): Result<List<TourList>> {
        return repository.getList()
    }
}