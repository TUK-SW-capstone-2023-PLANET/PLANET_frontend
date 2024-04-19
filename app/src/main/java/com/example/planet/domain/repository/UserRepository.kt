package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserInfo(userId: Int): Flow<ApiState>
    suspend fun putUserInfo(userInfo: UserInfo): Flow<ApiState>



    fun getUserPref(
        userToken: String = "userToken",
        defaultValue: String = "",
    ): Flow<ApiState>


    // 사용자 토큰을 저장
    fun setUserPrefs(value: String): Flow<ApiState>

    // 자동 로그인 boolean 값 불러옴
    fun getAutoLoginPref(
        autoLoginState: String = "autoLogin",
    ): Flow<ApiState>

    // 자동 로그인 설정
    fun setAutoLoginPref(value: Boolean): Flow<ApiState>

    // 로그아웃
    fun logout()
}