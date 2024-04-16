package com.example.planet.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.signin.LoginInfo
import com.example.planet.data.remote.dto.response.signin.LoginResponse
import com.example.planet.domain.usecase.login.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postLoginUseCase: PostLoginUseCase,
    ) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _autoLoginState = mutableStateOf(false)
    val autoLoginState: State<Boolean> = _autoLoginState

    fun changeAutoLoginState() {
        _autoLoginState.value = !autoLoginState.value
    }

    suspend fun login(goMainActivity: () -> Unit) {
        val loginInfo = LoginInfo(
            email = email,
            password = password
        )
        when (val apiState = postLoginUseCase(loginInfo).first()) {
            is ApiState.Success<*> -> {
                (apiState.value as LoginResponse).run {
                    Log.d(TAG, "login() 성공: $this")
                    if (this.success) {
                        goMainActivity()
                    } else {
                        Toast.makeText(context, this.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            is ApiState.Error -> {
                Log.d(TAG, "login() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }
}