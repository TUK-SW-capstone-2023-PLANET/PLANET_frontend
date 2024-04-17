package com.example.planet.data.repository

import com.example.planet.data.remote.api.ApiService
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService):UserRepository  {

    // 유저 정보 조회
    override suspend fun getUserInfo(userId: Int): Flow<ApiState> = flow {
        kotlin.runCatching {
            apiService.getUserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}