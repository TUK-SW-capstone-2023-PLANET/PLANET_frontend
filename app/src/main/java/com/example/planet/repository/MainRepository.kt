package com.example.planet.repository

import com.example.planet.data.ApiState
import com.example.planet.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getTopBanner(): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getTopBanner()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUniversityAllUserInfo(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getUniversityAllUserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSeasonInfo(userId: Int = 0): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getSeasonTop5UserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTierList(): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getTierList()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

}