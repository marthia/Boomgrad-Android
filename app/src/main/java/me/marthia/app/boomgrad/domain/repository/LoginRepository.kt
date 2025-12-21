package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Login
import me.marthia.app.boomgrad.domain.model.Sms

interface LoginRepository {
    suspend fun login(phone: String): Login
    suspend fun checkSmsCode(code: String): Sms
    fun getToken(): String?
    fun clearToken()
}