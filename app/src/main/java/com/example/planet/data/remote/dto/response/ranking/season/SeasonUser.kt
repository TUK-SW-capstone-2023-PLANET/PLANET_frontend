package com.example.planet.data.remote.dto.response.ranking.season

data class SeasonUser(
    val userName: String,
    val universityLogo: String,
    val score: Int,
    val rank: Int,
    val tierImageUrl: String,
    val tierName: String
)
