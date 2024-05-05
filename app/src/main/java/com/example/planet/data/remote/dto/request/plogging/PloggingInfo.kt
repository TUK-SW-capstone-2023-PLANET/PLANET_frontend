package com.example.planet.data.remote.dto.request.plogging

import com.example.planet.data.remote.dto.Location

data class PloggingInfo(
    val ploggingId: Int,
    val userId: Int,
    val location: List<Location>,
    val trash: List<Map<String, Int>>,
    val distance: Double,
    val kcal: Double,
    val speed : Long,
    val score: Int,
    val ploggingTime: Long   // 초 단위로 전달
)
