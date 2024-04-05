package com.example.planet.data.dto.ranking

data class ExpandedUniversityUser(
    val imageUrl: String,
    val universityName: String,
    val universityLogo: String,
    val nickName: String,
    val score: Int,
    val rank: Int,
    val contribution: Double
)
