package me.marthia.app.boomgrad.domain.usecase.login

import me.marthia.app.boomgrad.domain.repository.LoginRepository

class CheckAuthorizationUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.isLogin()
    }
}