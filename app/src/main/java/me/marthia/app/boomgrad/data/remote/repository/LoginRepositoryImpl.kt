package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.remote.api.LoginApiService
import me.marthia.app.boomgrad.data.local.TokenManager
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.domain.model.Login
import me.marthia.app.boomgrad.domain.model.Sms
import me.marthia.app.boomgrad.domain.repository.LoginRepository


class LoginRepositoryImpl(
    private val apiService: LoginApiService,
    private val tokenManager: TokenManager
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<Login> {
        val response = apiService.login(email, password)
        return response.map { response -> response.data.toDomain() }
    }

    override suspend fun checkSmsCode(code: String): Sms {
        val response = apiService.checkSmsCode(code)

        return Sms(
            status = response.getOrNull()?.status?.equals("success", true),
            message = response.getOrNull()?.message,
            token = response.getOrNull()?.result?.token ?: ""
        )
    }

    override fun getToken(): String? {
        return tokenManager.getToken()
    }

    override fun clearToken() {
        tokenManager.clearToken()
    }
}