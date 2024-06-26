package com.example.planet.data.remote.dto.request.plogging

import com.example.planet.data.remote.dto.Location

data class PloggingInfo(
    val ploggingId: Long,
    val userId: Long,
    val location: List<Location>,
    val trash: List<Map<String, Int>>,
    val distance: Double,
    val kcal: Double,
    val pace : String,
    val score: Int,
    val ploggingTime: Long   // 초 단위로 전달
)
