package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.PloggingImage
import com.example.planet.data.remote.dto.PloggingInfo
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getAllTrashCanLocation(): Flow<ApiState>

    suspend fun getPloggingId(): Flow<ApiState>

    suspend fun postPloggingLive(ploggingData: PloggingImage): Flow<ApiState>

    suspend fun postPlogging(ploggingInfo: PloggingInfo): Flow<ApiState>
}