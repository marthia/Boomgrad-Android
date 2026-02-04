package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.TokenResponse
import me.marthia.app.boomgrad.data.remote.dto.LoginRequestBody
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure

class LoginApiServiceImpl(
    private val client: HttpClient,
) : LoginApiService {

    override suspend fun login(email: String, password: String): Result<BaseResponse<TokenResponse>> {
        return try {
            val response = client.post("auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequestBody(email = email, password = password))
            }.body<BaseResponse<TokenResponse>>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e.toNetworkFailure())
        }
    }

}