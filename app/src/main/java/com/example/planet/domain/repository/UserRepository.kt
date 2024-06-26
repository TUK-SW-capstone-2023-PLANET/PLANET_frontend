package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserInfo(userId: Long): Flow<ApiState>
    suspend fun putUserInfo(userInfo: UserInfo): Flow<ApiState>

    fun getUserPref(
        userToken: String = "userToken",
        defaultValue: Long = 0,
    ): Flow<ApiState>


    // 사용자 토큰을 저장
    fun setUserPrefs(value: Long): Flow<ApiState>

    // 자동 로그인 boolean 값 불러옴
    fun getAutoLoginPref(
        autoLoginState: String = "autoLogin",
    ): Flow<ApiState>

    // 자동 로그인 설정
    fun setAutoLoginPref(value: Boolean): Flow<ApiState>

    // 로그아웃
    fun logout()

    // 나의 대학교 정보 얻어오기
    fun getMyUniversityInfo(userId: Long): Flow<ApiState>
}