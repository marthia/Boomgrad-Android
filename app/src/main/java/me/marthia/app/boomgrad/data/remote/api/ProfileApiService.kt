package me.marthia.app.boomgrad.data.remote.api

import me.marthia.app.boomgrad.data.remote.dto.ProfileDto

interface ProfileApiService {

    suspend fun profileInfo(): Result<ProfileDto>
}