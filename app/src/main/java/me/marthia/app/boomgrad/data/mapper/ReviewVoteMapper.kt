package me.marthia.app.boomgrad.data.mapper

import me.marthia.app.boomgrad.data.remote.dto.ReviewVoteDto
import me.marthia.app.boomgrad.domain.model.ReviewVote

fun ReviewVoteDto.toDomain() = ReviewVote(
    user = user.toDomain() ?: throw IllegalStateException("user cannot be  null"),
    voteType = voteType,
)

fun List<ReviewVoteDto>.toDomainList() = this.map { it.toDomain() }