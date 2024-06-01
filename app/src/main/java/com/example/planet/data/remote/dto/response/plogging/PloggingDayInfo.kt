package com.example.planet.data.remote.dto.response.plogging

data class PloggingDayInfo(
    val ploddingId: Long,
    val userId: Long,
    val imageUrl: String,
    val address: String,
    val trashCount: Int,
    val distance: String,
    val ploggingTime: String
)