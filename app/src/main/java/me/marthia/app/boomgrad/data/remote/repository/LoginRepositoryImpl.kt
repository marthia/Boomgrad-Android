package me.marthia.app.boomgrad.data.remote.repository

import kotlinx.coroutines.flow.first
import me.marthia.app.boomgrad.data.local.TokenManager
import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.LoginApiService
import me.marthia.app.boomgrad.data.remote.util.NetworkFailure
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure
import me.marthia.app.boomgrad.domain.model.Login
import me.marthia.app.boomgrad.domain.repository.LoginRepository
import timber.log.Timber


class LoginRepositoryImpl(
    private val apiService: LoginApiService,
    private val tokenManager: TokenManager
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<Login> {
        return runCatching {
            val response = apiService.login(email, password).getOrThrow()
            val loginData = response.data.toDomain()

            tokenManager.saveTokens(
                accessToken = loginData.token,
                refreshToken = loginData.refreshToken
            )

            loginData
        }.onFailure { error ->
            Timber.e(error, "Login failed: ${error.message}")
        }.recoverCatching { error ->
            // Convert exception and re-throw as NetworkFailure
            throw when (error) {
                is NetworkFailure -> error
                else -> error.toNetworkFailure()
            }
        }
    }

    override suspend fun getToken(): String {
        return tokenManager.getAccessToken().first().orEmpty()
    }

    override suspend fun clearToken() {
        tokenManager.clearTokens()
    }


    // TODO change this to check user id instead of token
    // because we will have guest token
    override suspend fun isLogin(): Boolean {
        return tokenManager.getAccessTokenOnce() != null
    }
}