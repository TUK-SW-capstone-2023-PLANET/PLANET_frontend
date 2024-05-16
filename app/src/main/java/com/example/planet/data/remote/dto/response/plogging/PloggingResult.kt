package com.example.planet.data.remote.dto.response.plogging

import com.example.planet.data.remote.dto.Location

data class PloggingResult(
    val ploggingId: Int,
    val uploadTime: String,
    val runningTime: String,
    val ploggingTime: Long,   // 초 단위로 전달
    val trashCount: Int, // 내가 주운 전체 쓰레기 개수,
    val distance: Double,
    val kcal: Double,
    val pace: String,
    val score: Int,
    val location: List<Location>,
    val firstLocation: Location,
    val middleLocation: Location,
    val lastLocation: Location,
    val trash: List<PloggingTrash>,
    val trashInfo: List<Map<String, List<PloggingTrashReceipt>>>
) {
    constructor() : this(
        ploggingId = 0,
        uploadTime = "",
        runningTime = "",
        ploggingTime = 0L,
        trashCount = 0,
        distance = 0.0,
        kcal = 0.0,
        pace = "0.0",
        score = 0,
        location = emptyList(),
        firstLocation = Location(0.0, 0.0),
        middleLocation = Location(0.0, 0.0),
        lastLocation = Location(0.0, 0.0),
        trash = emptyList(),
        trashInfo = emptyList()
    )
}

data class PloggingTrash(
    val name: String,
    val count: Int,
    val score: Int
)

data class PloggingTrashReceipt(
    val name: String,
    val count: Int,
    val score: Int,
    val imageUrl: String,
    val address: String,
    val totalScore: Int,
)