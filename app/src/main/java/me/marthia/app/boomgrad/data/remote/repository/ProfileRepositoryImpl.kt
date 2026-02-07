package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.ProfileApiService
import me.marthia.app.boomgrad.domain.model.Profile
import me.marthia.app.boomgrad.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val api: ProfileApiService) : ProfileRepository {


    override suspend fun getProfile(userId: Long): Result<Profile> {
        val response = api.profileInfo(userId = userId)
        return response.map { it.data.toDomain() }
    }
}