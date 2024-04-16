package com.example.planet.data.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.api.ApiService
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUniversityCheck(university: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getUniversityCheck(university)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAuthCode(name: String, email: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getAuthCode(name = name, email = email)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAuthCodeCheck(code: String, name: String, email: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getAuthCodeCheck(code = code, name = name, email = email)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDuplicatedNameCheck(name: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.getDuplicatedNameCheck(name = name)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun postCreateUser(user: PlanetUser): Flow<ApiState> = flow{
        kotlin.runCatching {
            apiService.postUserInfo(user = user)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)





}