package me.marthia.app.boomgrad.domain.model


data class ReviewVote(
    val user: User,
    val voteType: ReviewVoteType,
)