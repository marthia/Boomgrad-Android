package me.marthia.app.boomgrad.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.marthia.app.boomgrad.domain.model.ReviewVoteType

@Serializable
data class ReviewVoteDto(
    @SerialName("vote_type")
    val voteType: ReviewVoteType,
    @SerialName("created_at")
    val createdAt: String
)