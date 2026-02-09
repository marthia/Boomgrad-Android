package me.marthia.app.boomgrad.domain.usecase.profile

import me.marthia.app.boomgrad.domain.model.User
import me.marthia.app.boomgrad.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: Long): Result<User> {
        return repository.getProfile(userId = userId)
    }
}