package com.example.planet.data.remote.dto.response.search

data class SearchRank(
    val userName: String,
    val rank: Int,
    val universityLogo: String,
    val score: Int,
    val tierImageUrl: String,
    val tierName: String
)