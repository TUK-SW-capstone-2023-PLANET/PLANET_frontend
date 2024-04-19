package com.example.planet.presentation.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.domain.usecase.image.PostImageUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import okio.source
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postImageUseCase: PostImageUseCase
) : ViewModel() {
//    init {
//        viewModelScope.launch {
//            getUserToken()
//        }
//    }

    private var userToken: String = "0"
    val maxNicknameTextLength = 20
    val maxDescribeTextLength = 20

    var idTextValue by mutableStateOf("")
    var pwTextValue by mutableStateOf("")
    var nicknameTextValue by mutableStateOf(
        TextFieldValue(
            text = "Happy Bean", selection = TextRange("Happy Bean".length)
        )
    )
    var describeTextValue by mutableStateOf(
        TextFieldValue(
            text = "나랑 같이 플로깅 할 사람", selection = TextRange("나랑 같이 플로깅 할 사람".length)
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

    var userInfo by mutableStateOf(UserInfo())
    fun changeEditNicknameScreen() {
        editNicknameState = !editNicknameState
    }

    fun changeEditDescribeScreen() {
        editDescribeState = !editDescribeState
    }

    suspend fun getImageUrl(uri: Uri, context: Context) {
        uri.asMultipart("file", context.contentResolver)?.let {
            postImageUseCase(it)
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
                Log.d(TAG, "getUserToken Error: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }

    }

    suspend fun getUserInfo() {
        getUserToken()
        Log.d(TAG, "userToken: ${userToken}")
        when (val apiState = getUserInfoUseCase(userToken).first()) {
            is ApiState.Success<*> -> {
                userInfo = apiState.value as UserInfo
            }

            is ApiState.Error -> TODO()
            ApiState.Loading -> TODO()
        }
    }

    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }

}