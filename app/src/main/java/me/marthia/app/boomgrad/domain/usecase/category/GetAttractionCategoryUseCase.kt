package me.marthia.app.boomgrad.domain.usecase.category

import me.marthia.app.boomgrad.domain.model.AttractionCategory
import me.marthia.app.boomgrad.domain.repository.CategoryRepository

class GetAttractionCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<AttractionCategory>> {
        return repository.getList()
    }
}