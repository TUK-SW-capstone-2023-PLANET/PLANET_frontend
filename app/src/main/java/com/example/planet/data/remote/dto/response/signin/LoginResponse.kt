package com.example.planet.data.remote.dto.response.signin

data class LoginResponse(
    val userId: Long,
    val message: String,
    val success: Boolean
)
