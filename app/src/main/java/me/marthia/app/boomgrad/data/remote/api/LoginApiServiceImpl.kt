package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import me.marthia.app.boomgrad.data.local.TokenManager
import me.marthia.app.boomgrad.data.remote.dto.BaseResponse
import me.marthia.app.boomgrad.data.remote.dto.LoginDto
import me.marthia.app.boomgrad.data.remote.dto.LoginRequestBody
import me.marthia.app.boomgrad.data.remote.dto.SmsResponseDto
import me.marthia.app.boomgrad.data.remote.util.toNetworkFailure

class LoginApiServiceImpl(
    private val client: HttpClient,
    private val tokenManager: TokenManager
) : LoginApiService {

    override suspend fun login(email: String, password: String): Result<BaseResponse<LoginDto>> {
        return try {
            val response = client.post("auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequestBody(email = email, password = password))
            }.body<BaseResponse<LoginDto>>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e.toNetworkFailure())
        }
    }

    /**
     * Verifies the SMS code provided by the user.
     *
     * This function sends the SMS code to the backend for verification. If the code is correct,
     * it saves the authentication token received in the response using the [TokenManager].
     *
     * @param code The SMS verification code entered by the user.
     * @return A [Result] object containing either a [SmsResponseDto] on success or an [Exception] on failure.
     */
    override suspend fun checkSmsCode(code: String): Result<SmsResponseDto> {
        return try {
            val response = client.get("checkSmsCode/$code") {
            }.body<SmsResponseDto>()

            response.result?.let { it.token?.let { token -> tokenManager.saveToken(token) } }

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}