package me.marthia.app.boomgrad.domain.usecase.tour

import me.marthia.app.boomgrad.domain.model.TourDetail
import me.marthia.app.boomgrad.domain.repository.TourRepository

class GetTourDetailUseCase(
    private val repository: TourRepository
) {

    suspend operator fun invoke(tourId: Long): Result<TourDetail> {
        return repository.getTourDetail(tourId = tourId)
    }
}