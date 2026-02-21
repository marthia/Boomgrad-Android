package me.marthia.app.boomgrad.data.remote.repository

import me.marthia.app.boomgrad.data.mapper.toDomain
import me.marthia.app.boomgrad.data.remote.api.GuideApiService
import me.marthia.app.boomgrad.domain.model.Guide
import me.marthia.app.boomgrad.domain.repository.GuideRepository

class GuideRepositoryImpl(private val api: GuideApiService) : GuideRepository {


    override suspend fun getInfo(guideId: Long): Result<Guide> {
        val response = api.guideInfo(guideId = guideId)
        return response.map { it.data.toDomain() }
    }
}