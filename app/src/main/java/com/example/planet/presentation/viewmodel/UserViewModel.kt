package com.example.planet.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.data.remote.dto.response.user.UserInfoResponse
import com.example.planet.domain.usecase.image.PostImageUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.user.GetUserInfoUseCase
import com.example.planet.domain.usecase.user.PutUserInfoUseCase
import com.example.planet.presentation.util.asMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postImageUseCase: PostImageUseCase,
    private val putUserInfoUseCase: PutUserInfoUseCase
) : ViewModel() {
//    init {
//        viewModelScope.launch {
//            getUserToken()
//        }
//    }

    val defaultImageUrl = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/user/free-icon-user-149071+3.png"

    var userInfo by mutableStateOf(UserInfo())

    var dialogState by mutableStateOf(false)

    private var userToken: String = "0"
    val maxNicknameTextLength = 20
    val maxDescribeTextLength = 20

    var idTextValue by mutableStateOf("")
    var pwTextValue by mutableStateOf("")
    var nicknameTextValue by mutableStateOf(
        TextFieldValue(
            text = userInfo.nickName, selection = TextRange(userInfo.nickName.length)
        )
    )

    var describeTextValue by mutableStateOf(
        TextFieldValue(
            text = userInfo.message, selection = TextRange(userInfo.message.length)
        )
    )
    var heightTextValue by mutableStateOf("")
    var weightTextValue by mutableStateOf("")
    var editNicknameState by mutableStateOf(false)
    var editDescribeState by mutableStateOf(false)

    val nicknameTextLength by derivedStateOf {
        "${nicknameTextValue.text.length} / $maxNicknameTextLength"
    }

    val describeTextLength by derivedStateOf {
        "${describeTextValue.text.length} / $maxDescribeTextLength"
    }

    fun changeEditNicknameScreen() {
        editNicknameState = !editNicknameState
    }

    fun changeEditDescribeScreen() {
        editDescribeState = !editDescribeState
    }

    fun changeDefaultImage() {
        userInfo = userInfo.copy(imageUrl = defaultImageUrl)
        dialogState = false
    }

    suspend fun getImageUrl(uri: Uri, context: Context) {
        uri.asMultipart("file", context.contentResolver)?.let { multipartBody ->
            postImageUseCase(multipartBody)
        }?.let { multipart ->
            when (val apiState = multipart.first()) {
                is ApiState.Success<*> -> {
                    Log.d(TAG, "getImageUrl success: ${apiState.value as ImageUrl}")
                    userInfo = userInfo.copy(imageUrl = (apiState.value).imageUrl)
                }

                is ApiState.Error -> {
                    Log.d(TAG, "getImageUrl Error: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }

    private suspend fun getUserToken() {
        when (val apiState = getUserTokenUseCase().first()) {
            is ApiState.Success<*> -> {
                userToken = apiState.value as String
            }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() Error: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }

    }

    suspend fun getUserInfo() {
        getUserToken()
        when (val apiState = getUserInfoUseCase(userToken).first()) {
            is ApiState.Success<*> -> {
                userInfo = apiState.value as UserInfo
                nicknameTextValue = nicknameTextValue.copy(
                    text = userInfo.nickName,
                    selection = TextRange(userInfo.nickName.length))
                describeTextValue = describeTextValue.copy(
                    text = userInfo.message,
                    selection = TextRange(userInfo.message.length))
            }

            is ApiState.Error -> {
                Log.d(TAG, "getUserInfo() Error: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun putUserInfo(context: Context) {
        getUserToken()
        when (val apiState = putUserInfoUseCase(userInfo).first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "putUserInfo() success: ${apiState.value as UserInfoResponse}")
                Toast.makeText(context, "회원정보가 변경되었습니다.", Toast.LENGTH_SHORT).show()
            }

            is ApiState.Error -> {
                Log.d(TAG, "putUserInfo() Error: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


}