package me.marthia.app.boomgrad.domain.repository

import me.marthia.app.boomgrad.domain.model.Guide

interface GuideRepository {
    suspend fun getInfo(guideId: Long): Result<Guide>
}