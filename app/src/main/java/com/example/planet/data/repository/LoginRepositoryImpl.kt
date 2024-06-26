package com.example.planet.data.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.request.signin.LoginInfo
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import com.example.planet.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val mainApi: MainApi): LoginRepository {

    override suspend fun getUniversityCheck(university: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.getUniversityCheck(university)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAuthCode(name: String, email: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.getAuthCode(name = name, email = email)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAuthCodeCheck(code: String, name: String, email: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.getAuthCodeCheck(code = code, name = name, email = email)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDuplicatedNameCheck(name: String): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.getDuplicatedNameCheck(name = name)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postCreateUser(user: PlanetUser): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.postUserInfo(user = user)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postLogin(loginInfo: LoginInfo): Flow<ApiState> = flow{
        kotlin.runCatching {
            mainApi.postLogin(loginInfo = loginInfo)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

}