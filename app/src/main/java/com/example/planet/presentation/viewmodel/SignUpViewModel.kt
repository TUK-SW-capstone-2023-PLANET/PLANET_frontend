package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.planet.TAG
import com.example.planet.data.ApiState
import com.example.planet.data.remote.dto.response.signup.LoginAuthState
import com.example.planet.data.remote.dto.response.signup.SignUpResponse
import com.example.planet.domain.usecase.login.GetAuthCodeUseCase
import com.example.planet.domain.usecase.login.GetUniversityCheckUseCase
import com.example.planet.presentation.ui.signup.navigation.SignUpNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getUniversityCheckUseCase: GetUniversityCheckUseCase,
    private val getAuthCodeUseCase: GetAuthCodeUseCase,
) : ViewModel() {
    private var authTimeLimit = 180_000L
    private var timerJob: Job? = null
    val maxUsernameTextLength = 20


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
    var isEmailCheck: LoginAuthState by mutableStateOf(LoginAuthState.Empty)


    var authNumber by mutableStateOf("")
    val authNumberIsNotEmpty by derivedStateOf {
        authNumber.isNotEmpty()
    }

    var university by mutableStateOf("")
    val universityIsNotEmpty by derivedStateOf {
        university.isNotEmpty()
    }
    var isUniversityCheck: LoginAuthState by mutableStateOf(LoginAuthState.Empty)

    var userName by mutableStateOf("")
    val userNameIsNotEmpty by derivedStateOf {
        userName.isNotEmpty()
    }

    val usernameTextLength by derivedStateOf {
        "${userName.length} / $maxUsernameTextLength"
    }

    var passwd by mutableStateOf("")
    var realPasswd by mutableStateOf("")

    var gender by mutableStateOf(true)

    var height by mutableStateOf("")
    var weight by mutableStateOf("")


    fun onNextPage(navController: NavHostController) {
        navController.navigate(navRouteList[(++_currentPage.value - 1)])
    }

    fun onPreviousPage(navController: NavHostController) {
        _currentPage.value--
        navController.popBackStack()
    }

    private fun startAuthTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            while (true) {
                _authTime.value -= 1000
                delay(1000)
            }
        }
    }

    fun standardHeightAndWeight() {
        if (gender) {
            height = "173.5"
            weight = "66"
        } else {
            height = "160.9"
            weight = "51.5"
        }

    }

    fun changeAutoLoginState() {
        _autoLoginState.value = !autoLoginState.value
    }

    private fun apiStateParseLoginAuthState(state: String): LoginAuthState =
        when (state) {
            "true" -> LoginAuthState.Success
            "false" -> LoginAuthState.Fail
            else -> LoginAuthState.Empty
        }


    suspend fun universityCheck() {
        when (val apiState = getUniversityCheckUseCase(university).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "universityCheck() 성공: ${apiState.value}")
                isUniversityCheck = apiStateParseLoginAuthState((apiState.value as SignUpResponse).success)

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "universityCheck() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getAuthCode() {
        when (val apiState = getAuthCodeUseCase(university = university , email = email).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getAuthCode() 성공: ${apiState.value}")
                isEmailCheck = apiStateParseLoginAuthState((apiState.value as SignUpResponse).success)
                startAuthTimer()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAuthCode() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


}

val navRouteList = listOf(
    SignUpNavItem.AuthScreen.screenRoute,
    SignUpNavItem.NameScreen.screenRoute,
    SignUpNavItem.PasswdScreen.screenRoute,
    SignUpNavItem.MyInfoScreen.screenRoute,
    SignUpNavItem.FinalScreen.screenRoute
)