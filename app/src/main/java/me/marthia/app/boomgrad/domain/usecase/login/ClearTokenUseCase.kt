package me.marthia.app.boomgrad.domain.usecase.login

import me.marthia.app.boomgrad.domain.repository.LoginRepository

class ClearTokenUseCase(
    private val repo: LoginRepository
) {

    operator fun invoke() {
        repo.clearToken()
    }
}