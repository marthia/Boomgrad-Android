package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Login
import me.marthia.app.boomgrad.domain.model.Sms

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Login>
    suspend fun getToken(): String
    suspend fun clearToken()
    suspend fun isLogin(): Boolean
}