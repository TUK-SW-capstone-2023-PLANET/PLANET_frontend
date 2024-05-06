package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.plogging.PloggingImage
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.response.plogging.PloggingId
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface PloggingRepository {
    suspend fun getAllTrashCanLocation(): Flow<ApiState>

    suspend fun getPloggingId(userId: Int): Flow<ApiState>

    suspend fun postTrashImages(file: MultipartBody.Part): Flow<ApiState>

    suspend fun postPloggingLive(ploggingData: PloggingImage): Flow<ApiState>

    suspend fun postPlogging(ploggingInfo: PloggingInfo): Flow<ApiState>

    suspend fun getPloggingInfo(ploggingId: Int): Flow<ApiState>



}