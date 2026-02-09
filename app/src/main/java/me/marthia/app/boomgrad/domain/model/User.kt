package me.marthia.app.boomgrad.domain.model

data class User(
    val id: Long,
    val name: String,
    val username: String,
    val email:String,
    val phone: String,
    val image: String,
)