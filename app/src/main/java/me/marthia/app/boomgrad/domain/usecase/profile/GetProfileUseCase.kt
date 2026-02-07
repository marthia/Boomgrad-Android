package me.marthia.app.boomgrad.domain.usecase.profile

import me.marthia.app.boomgrad.domain.model.Attraction
import me.marthia.app.boomgrad.domain.model.Profile
import me.marthia.app.boomgrad.domain.repository.AttractionRepository
import me.marthia.app.boomgrad.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: Long): Result<Profile> {
        return repository.getProfile(userId = userId)
    }
}