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
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import com.example.planet.data.remote.dto.response.signup.LoginAuthState
import com.example.planet.data.remote.dto.response.signup.SignUpResponse
import com.example.planet.data.remote.dto.response.signup.UserId
import com.example.planet.domain.usecase.login.GetAuthCodeCheckUseCase
import com.example.planet.domain.usecase.login.GetAuthCodeUseCase
import com.example.planet.domain.usecase.login.GetDuplicatedNameCheckUseCase
import com.example.planet.domain.usecase.login.GetUniversityCheckUseCase
import com.example.planet.domain.usecase.login.PostCreateUserUseCase
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
    private val getAuthCodeCheckUseCase: GetAuthCodeCheckUseCase,
    private val getDuplicatedNameCheckUseCase: GetDuplicatedNameCheckUseCase,
    private val postCreateUserUseCase: PostCreateUserUseCase
) : ViewModel() {
    private var authTimeLimit = 180_000L
    private var timerJob: Job? = null
    val maxUsernameTextLength = 20
    var userId = ""


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


    var authCode by mutableStateOf("")
    val authCodeIsNotEmpty by derivedStateOf {
        authCode.isNotEmpty()
    }
    var isAuthCodeCheck: LoginAuthState by mutableStateOf(LoginAuthState.Empty)


    var university by mutableStateOf("")
    val universityIsNotEmpty by derivedStateOf {
        university.isNotEmpty()
    }
    var isUniversityCheck: LoginAuthState by mutableStateOf(LoginAuthState.Empty)

    var userName by mutableStateOf("")
    val userNameIsNotEmpty by derivedStateOf {
        userName.isNotEmpty()
    }
    var isUserNameCheck: LoginAuthState by mutableStateOf(LoginAuthState.Empty)


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

    private fun String.apiStateParseLoginAuthState(): LoginAuthState =
        when (this) {
            "true" -> LoginAuthState.Success
            "false" -> LoginAuthState.Fail
            else -> LoginAuthState.Empty
        }

    private fun Boolean.apiStateParseLoginAuthState(): LoginAuthState =
        when (this) {
            true -> LoginAuthState.Success
            false -> LoginAuthState.Fail
        }


    suspend fun universityCheck() {
        when (val apiState = getUniversityCheckUseCase(university).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "universityCheck() 성공: ${apiState.value}")
                isUniversityCheck =
                    (apiState.value as SignUpResponse).success.apiStateParseLoginAuthState()

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "universityCheck() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getAuthCode() {
        when (val apiState = getAuthCodeUseCase(university = university, email = email).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getAuthCode() 성공: ${apiState.value}")
                isEmailCheck =
                    (apiState.value as SignUpResponse).success.apiStateParseLoginAuthState()
                startAuthTimer()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAuthCode() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getAuthCodeCheck() {
        when (val apiState = getAuthCodeCheckUseCase(
            code = authCode,
            university = university,
            email = email
        ).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getAuthCodeCheck() 성공: ${apiState.value}")
                isAuthCodeCheck =
                    (apiState.value as SignUpResponse).success.apiStateParseLoginAuthState()
                timerJob?.cancel()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAuthCodeCheck() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun postCreateUser() {
        val user = PlanetUser(
            email = email,
            nickName = userName,
            password = passwd,
            gender = if (gender) "남성" else "여성",
            weight = weight.toDouble(),
            height = height.toDouble()
        )

        when (val apiState = postCreateUserUseCase(user = user).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "postCreateUser() 성공: ${apiState.value}")
                userId = (apiState.value as UserId).userId.toString()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "postCreateUser() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


    suspend fun getDuplicatedNameCheck() {
        when (val apiState = getDuplicatedNameCheckUseCase(userName).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "getDuplicatedNameCheck() 성공: ${apiState.value}")
                isUserNameCheck = (apiState.value as Boolean).apiStateParseLoginAuthState()
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getDuplicatedNameCheck() 실패: ${apiState.errMsg}")
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