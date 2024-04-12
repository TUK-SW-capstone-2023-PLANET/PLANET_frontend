package com.example.planet.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignInViewModel @Inject constructor() : ViewModel() {
    private val _autoLoginState = mutableStateOf(false)
    val autoLoginState: State<Boolean> = _autoLoginState

    fun changeAutoLoginState() {
        _autoLoginState.value = !autoLoginState.value
    }
}