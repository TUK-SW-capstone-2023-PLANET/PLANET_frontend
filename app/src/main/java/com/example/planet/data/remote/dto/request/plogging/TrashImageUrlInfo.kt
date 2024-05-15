package com.example.planet.data.remote.dto.request.plogging

data class TrashImageUrlInfo(
    val userId: Long,
    val ploggingId: Int,
    val imageUrl: String,
    val longitude: Double,
    val latitude: Double,
    val trash: List<Map<String, Int>>
)