package com.example.planet.data.remote.dto.response.ranking.universityuser

data class ExpandedUniversityUser(
    val imageUrl: String,
    val universityLogo: String,
    val universityName: String,
    val nickName: String,
    val score: Int,
    val rank: Int,
    val contribution: Double
)
