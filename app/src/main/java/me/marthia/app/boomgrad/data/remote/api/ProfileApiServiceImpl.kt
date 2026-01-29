package me.marthia.app.boomgrad.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import me.marthia.app.boomgrad.data.remote.dto.ProfileDto

class ProfileApiServiceImpl(
    private val client: HttpClient
) : ProfileApiService {

    override suspend fun profileInfo(): Result<ProfileDto> {
        return client.get("users/") {
            parameter("user", "currentUserId")
        }.body()
    }
}