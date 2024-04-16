package com.example.planet.data.remote.dto.response.signup

sealed class LoginAuthState {
    data object Success : LoginAuthState()
    data object Fail : LoginAuthState()
    data object Empty : LoginAuthState()
}