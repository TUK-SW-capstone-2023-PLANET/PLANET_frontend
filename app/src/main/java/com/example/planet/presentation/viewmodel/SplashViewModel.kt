package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.usecase.login.sharedpreference.GetAutoLoginUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getAutoLoginUseCase: GetAutoLoginUseCase
) : ViewModel() {
    var userId: String = ""
    private var autoLoginState: Boolean = false

    fun goActivity(goActivities: List<() -> Unit>) {
        viewModelScope.launch(Dispatchers.IO) {
            async(Dispatchers.IO) { getUserToken() }.await()
            async(Dispatchers.IO) { getAutoLogin() }.await()
            if (userId.isNotEmpty() && autoLoginState) {
                Log.d(TAG, "자동 로그인 성공 -> userId: ${userId}, autoLoginState: ${autoLoginState}")
                goActivities[0]()  // MainActivity 이동
            } else {
                Log.d(TAG, "자동 로그인 실패 -> userId: ${userId}, autoLoginState: ${autoLoginState}")
                goActivities[1]()  // SingInActivity 이동
            }
        }
    }

    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getUserToken(): ${result.value}")
                userId = result.value as String
            }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    private suspend fun getAutoLogin(autoLoginKey: String = "autoLogin") {
        when (val result = getAutoLoginUseCase(autoLoginKey).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getAutoLogin(): ${result.value}")
                autoLoginState = result.value as Boolean
            }

            is ApiState.Error -> {
                Log.d(TAG, "getAutoLogin() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
}