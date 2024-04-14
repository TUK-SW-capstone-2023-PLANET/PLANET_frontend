package com.example.planet.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.planet.presentation.ui.signup.navigation.SignUpNavItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : ViewModel() {
    private var authTimeLimit = 180_000L
    private var timerJob: Job? = null

    private val _authTime = MutableStateFlow(authTimeLimit)
    val authTime: StateFlow<Long> = _authTime

    private val _autoLoginState = mutableStateOf(false)
    val autoLoginState: State<Boolean> = _autoLoginState

    private val _currentPage = mutableStateOf(1)
    val currentPage: State<Int> = _currentPage

    var email by mutableStateOf("")
    val emailIsNotEmpty by derivedStateOf {
        email.isNotEmpty()
    }

    var authNumber by mutableStateOf("")
    val authNumberIsNotEmpty by derivedStateOf {
        authNumber.isNotEmpty()
    }

    fun onNextPage(navController: NavHostController) {
        navController.navigate(navRouteList[(++_currentPage.value - 1)])
    }

    fun onPreviousPage(navController: NavHostController) {
        _currentPage.value--
        navController.popBackStack()
    }

    fun startAuthTimer() {
        timerJob?.cancel()

        viewModelScope.launch {
            while (true) {
                _authTime.value -= 1000
                delay(1000)
            }
        }
    }

    fun changeAutoLoginState() {
        _autoLoginState.value = !autoLoginState.value
    }
}

val navRouteList = listOf(
    SignUpNavItem.AuthScreen.screenRoute,
    SignUpNavItem.NameScreen.screenRoute,
    SignUpNavItem.PasswdScreen.screenRoute,
    SignUpNavItem.HeightScreen.screenRoute,
    SignUpNavItem.FinalScreen.screenRoute
)