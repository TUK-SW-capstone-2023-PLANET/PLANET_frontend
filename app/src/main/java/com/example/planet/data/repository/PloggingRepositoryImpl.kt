package com.example.planet.data.repository

import com.example.planet.data.remote.api.ApiService
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.plogging.PloggingImage
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.response.plogging.PloggingId
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PloggingRepositoryImpl @Inject constructor(private val apiService: ApiService): PloggingRepository {
    override suspend fun getAllTrashCanLocation(): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getAllTrashCanLocation()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPloggingId(userId: Int): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getPloggingId(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postPloggingLive(ploggingData: PloggingImage): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.postPloggingLive(ploggingImage = ploggingData)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postPlogging(ploggingInfo: PloggingInfo): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.postPlogging(ploggingInfo = ploggingInfo)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPloggingInfo(ploggingId: Int): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getPloggingInfo(ploggingId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

}