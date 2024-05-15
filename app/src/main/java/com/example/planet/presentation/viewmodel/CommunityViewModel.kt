package com.example.planet.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.presentation.util.asMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject
@HiltViewModel
class CommunityViewModel @Inject constructor(): ViewModel() {

    var postedDialogState by mutableStateOf(false)
    var boardDialogState by mutableStateOf(false)

    var postingTitleInput by mutableStateOf("a")
    var postingContentInput by mutableStateOf("")

    var postingImageList by mutableStateOf(emptyList<Uri>())


//    suspend fun getImageUrl(uri: Uri, context: Context) {
//        uri.asMultipart("file", context.contentResolver)?.let { multipartBody ->
//            postImageUseCase(multipartBody)
//        }?.let { multipart ->
//            when (val apiState = multipart.first()) {
//                is ApiState.Success<*> -> {
//                    Log.d(TAG, "getImageUrl success: ${apiState.value as ImageUrl}")
//                    userInfo = userInfo.copy(imageUrl = (apiState.value).imageUrl)
//                }
//
//                is ApiState.Error -> {
//                    Log.d(TAG, "getImageUrl Error: ${apiState.errMsg}")
//                }
//
//                ApiState.Loading -> TODO()
//            }
//        }
//    }
}


data class PostingData(
    val imageList: List<String>,
    val title: String,
    val content: String
)