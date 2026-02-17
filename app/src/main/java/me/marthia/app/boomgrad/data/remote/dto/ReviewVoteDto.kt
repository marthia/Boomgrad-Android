package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.ReviewVoteType

@Serializable
data class ReviewVoteDto(
    val user: UserDto,
    val voteType: ReviewVoteType,
)