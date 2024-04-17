package com.example.planet.data.remote.dto.response.user

data class UserInfo(
    val userId: Int,
    val email: String,
    val passwd: String,
    val nickName: String,
    val imageUrl: String,
    val weight: Int,
    val height: Int,
    val gender: String,
    val address: String,
    val ploggingCount: Int,
    val trashCount: Int,
    val totalDistance: Int,
    val universityName: String,
    val universityLogo: String,
    val score: Int
)
