package me.marthia.app.boomgrad.domain.usecase.tour

import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.repository.GuideRepository

class GetGuideInfoUseCase(
    private val repository: GuideRepository
) {

    suspend operator fun invoke(guideId: Long): Result<Guide> {
        return repository.getInfo(guideId = guideId)
    }
}