package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.map.TrashCanImage
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.request.plogging.TrashImageUrlInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface PloggingRepository {
    suspend fun getAllTrashCanLocation(): Flow<ApiState>

    suspend fun getPloggingId(userId: Long): Flow<ApiState>

    suspend fun postTrashImages(file: MultipartBody.Part): Flow<ApiState>

    suspend fun postPloggingLive(ploggingData: TrashImageUrlInfo): Flow<ApiState>

    suspend fun postPlogging(ploggingInfo: PloggingInfo): Flow<ApiState>

    suspend fun getPloggingInfo(ploggingId: Long): Flow<ApiState>

    suspend fun getPloggingActiveList(
        userId: Long,
        year: Int,
        month: Int
    ): Flow<ApiState>

    suspend fun saveTrashCan(trashCanImage: TrashCanImage): Flow<ApiState>

    suspend fun getAllHotPlaces(): Flow<ApiState>

}