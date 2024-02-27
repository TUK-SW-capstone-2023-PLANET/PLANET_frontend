package com.example.planet.data.dto

data class PloggingImage(
    val userId: Int,
    val ploggingId: Int,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)