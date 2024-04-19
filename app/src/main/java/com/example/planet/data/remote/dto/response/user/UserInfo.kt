package com.example.planet.data.remote.dto.response.user

data class UserInfo(
    var userId: Int = 0,
    var email: String = "",
    var passwd: String = "",
    var nickName: String = "",
    var imageUrl: String = "",
    var weight: Double = 0.0,
    var height: Double = 0.0,
    var message: String = ""
//    val gender: String,
//    val address: String,
//    val ploggingCount: Int,
//    val trashCount: Int,
//    val totalDistance: Int,
//    val universityName: String,
//    val universityLogo: String,
//    val score: Int
)
