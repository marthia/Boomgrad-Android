package me.marthia.app.boomgrad.domain.usecase.attraction

import me.marthia.app.boomgrad.data.remote.repository.MockAttraction
import me.marthia.app.boomgrad.domain.repository.AttractionRepository

class GetMockAttractionUseCase(
    private val repository: AttractionRepository
) {
    suspend operator fun invoke(): Result<List<MockAttraction>> {
        return repository.getMockAttractions()
    }
}