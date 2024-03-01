package com.example.planet.data.dto

data class PloggingInfo(
    val ploggingId: Int,
    val userId: Int,
    val location: List<Location>,
    val trashCount: List<Map<String, Int>>,
    val distance: Double,
    val kcal: Double,
    val speed : Double,
    val score: Int,
    val ploggingTime: Long
)
