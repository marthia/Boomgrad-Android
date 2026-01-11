package me.marthia.app.boomgrad.domain.usecase.attraction

import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository

class GetTopAttractionsUseCase(
    private val repository: AttractionRepository
) {
    suspend operator fun invoke(): Result<List<Attraction>> {
        return repository.getAttractions()
    }
}