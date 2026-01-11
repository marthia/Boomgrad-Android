package me.marthia.app.boomgrad.domain.usecase.tour

import me.marthia.app.boomgrad.domain.model.Tour
import me.marthia.app.boomgrad.domain.repository.TourRepository

class GetForYouToursUseCase(
    private val repository: TourRepository
) {

    suspend operator fun invoke(): Result<List<Tour>> {
        return repository.getList()
    }
}