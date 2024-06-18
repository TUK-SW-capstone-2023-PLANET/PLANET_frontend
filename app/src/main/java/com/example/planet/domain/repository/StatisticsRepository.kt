package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {
    suspend fun readMonthPloggingLog(userId: Long, year: Int, month: Int): Flow<ApiState>

    suspend fun readWeekPloggingLog(userId: Long): Flow<ApiState>
}