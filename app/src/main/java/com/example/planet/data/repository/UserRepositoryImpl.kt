package com.example.planet.data.repository

import android.content.SharedPreferences
import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val sharedPreferences: SharedPreferences
) : UserRepository {

    // 유저 정보 조회
    override suspend fun getUserInfo(userId: Int): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getUserInfo(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun putUserInfo(userInfo: UserInfo): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.putUserInfo(userInfo = userInfo)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    // 사용자 토큰을 불러옴
    override fun getUserPref(
        userToken: String,
        defaultValue: Long,
    ): Flow<ApiState> =
        flow {
            runCatching { sharedPreferences.getLong(userToken, defaultValue) }.onSuccess {
                emit(ApiState.Success(it))
            }.onFailure { error ->
                error.message?.let { emit(ApiState.Error(it)) }
            }
        }


    // 사용자 토큰을 저장
    override fun setUserPrefs(value: Long): Flow<ApiState> = flow {
        runCatching {
            sharedPreferences.edit().putLong("userToken", value).apply()
        }.onSuccess {
            emit(ApiState.Success("success"))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }

    // 자동 로그인 boolean 값 불러옴
    override fun getAutoLoginPref(
        autoLoginState: String,
    ): Flow<ApiState> = flow {
        runCatching {
            sharedPreferences.getBoolean(autoLoginState, false)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }

    // 자동 로그인 설정
    override fun setAutoLoginPref(value: Boolean): Flow<ApiState> = flow {
        runCatching {
            sharedPreferences.edit().putBoolean("autoLogin", value).apply()
        }.onSuccess {
            emit(ApiState.Success("success"))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }

    // 로그아웃
    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}