package me.marthia.app.boomgrad.domain.usecase.login

import me.marthia.app.boomgrad.domain.model.Login
import me.marthia.app.boomgrad.domain.repository.LoginRepository

class LoginUseCase(
    private val repo: LoginRepository
) {
    data class LoginParams(
        val username: String,
        val password: String,
    )

    suspend operator fun invoke(params: LoginParams): Result<Login> {
        return repo.login(
            email = params.username,
            password = params.password
        )
    }
}