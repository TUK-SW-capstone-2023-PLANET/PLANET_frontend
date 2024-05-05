package com.example.planet.data.remote.dto.request.plogging

data class PloggingImage(
    val userId: Int,
    val ploggingId: Int,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)