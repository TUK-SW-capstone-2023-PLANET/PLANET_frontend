package com.example.planet.data.remote.dto.request.signup

data class PlanetUser(
    val email: String,
    val nickName: String,
    val password: String,
    val gender: String,
    val weight: Double,
    val height: Double
)
