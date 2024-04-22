package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getTopBanner(): Flow<ApiState>

    suspend fun getTierList(): Flow<ApiState>
}