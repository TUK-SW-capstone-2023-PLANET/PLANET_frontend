package com.example.planet.data.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val mainApi: MainApi): MainRepository {

    override suspend fun getTopBanner(): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.getTopBanner()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTierList(): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.getTierList()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

}