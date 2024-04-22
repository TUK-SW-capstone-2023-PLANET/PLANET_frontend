package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.signin.LoginInfo
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun getUniversityCheck(university: String): Flow<ApiState>

    suspend fun getAuthCode(name: String, email: String): Flow<ApiState>

    suspend fun getAuthCodeCheck(code: String, name: String, email: String): Flow<ApiState>

    suspend fun getDuplicatedNameCheck(name: String): Flow<ApiState>

    suspend fun postCreateUser(user: PlanetUser): Flow<ApiState>

    suspend fun postLogin(loginInfo: LoginInfo): Flow<ApiState>
}