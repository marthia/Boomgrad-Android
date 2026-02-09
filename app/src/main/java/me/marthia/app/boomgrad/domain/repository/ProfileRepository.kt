package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.User

interface ProfileRepository {
    suspend fun getProfile(userId: Long): Result<User>
}