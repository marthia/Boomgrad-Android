package me.marthia.app.boomgrad.domain.usecase.tour

import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.domain.repository.TourRepository

class GetTourDetailUseCase(
    private val repository: TourRepository
) {

    suspend operator fun invoke(tourId: Long): Result<Tour> {
        return repository.getTourDetail(tourId = tourId)
    }
}