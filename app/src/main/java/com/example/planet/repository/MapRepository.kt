package com.example.planet.repository

import com.example.planet.data.ApiState
import com.example.planet.data.dto.PloggingImage
import com.example.planet.data.dto.PloggingInfo
import com.example.planet.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class MapRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllTrashCanLocation(): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getAllTrashCanLocation()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPloggingId(): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getPloggingId()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun postImage(file: MultipartBody.Part): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.postImage(file = file)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun postPloggingLive(ploggingData: PloggingImage): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.postPloggingLive(ploggingImage = ploggingData)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun postPlogging(ploggingInfo: PloggingInfo): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.postPlogging(ploggingInfo = ploggingInfo)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}