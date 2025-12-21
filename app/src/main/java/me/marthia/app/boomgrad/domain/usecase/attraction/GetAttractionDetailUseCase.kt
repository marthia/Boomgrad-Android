package me.marthia.app.boomgrad.domain.usecase.attraction

import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository

class GetAttractionDetailUseCase (
    private val repository: AttractionRepository
) {
    suspend operator fun invoke(id: String): Result<Attraction> {
        return repository.getAttractionById(id)
    }
}