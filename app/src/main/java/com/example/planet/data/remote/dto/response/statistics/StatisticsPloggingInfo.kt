package com.example.planet.data.remote.dto.response.statistics

data class StatisticsPloggingInfo(
    val average: Int,
    val dataList: List<DayPloggingInfo>
)


data class DayPloggingInfo(
    val day: String,
    val score: Int
)