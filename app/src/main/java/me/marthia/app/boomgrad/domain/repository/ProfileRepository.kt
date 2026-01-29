package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
}