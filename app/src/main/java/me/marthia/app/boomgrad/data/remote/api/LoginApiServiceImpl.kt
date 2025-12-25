package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.LoginResponseDto
import me.marthia.app.boomgrad.data.remote.dto.SmsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import me.marthia.app.boomgrad.data.local.TokenManager

class LoginApiServiceImpl(
    private val client: HttpClient,
    private val tokenManager: TokenManager
) : LoginApiService {

    override suspend fun login(username: String, password: String): Result<LoginResponseDto> {
        return try {
            val response = client.post("login") {
                parameter("username", username)
                parameter("password", password) // Base64 encoded
            }.body<LoginResponseDto>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
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