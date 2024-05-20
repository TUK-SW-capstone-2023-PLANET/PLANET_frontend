package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.post.MyPostedInfo
import com.example.planet.domain.usecase.post.GetAllMyCommentUseCase
import com.example.planet.domain.usecase.post.GetAllMyPostedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MyWritedViewModel @Inject constructor(
    private val getAllMyPostedUseCase: GetAllMyPostedUseCase,
    private val getAllMyCommentUseCase: GetAllMyCommentUseCase

): ViewModel() {

    var userId: Long = 0
    var postedInput by mutableStateOf("")

    var allMyPosted by mutableStateOf(emptyList<MyPostedInfo>())
    var freeMyPosted by mutableStateOf(emptyList<MyPostedInfo>())
    var universityMyPosted by mutableStateOf(emptyList<MyPostedInfo>())

    suspend fun readAllMyPosted(type: String, userId: Long) {
        when (val apiState = getAllMyPostedUseCase(type = type, userId = userId).first()) {
            is ApiState.Success<*> -> {
                allMyPosted = (apiState.value as List<MyPostedInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readAllMyPosted() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun readFreeMyPosted(userId: Long) {
        when (val apiState = getAllMyPostedUseCase(type = "free", userId = userId).first()) {
            is ApiState.Success<*> -> {
                freeMyPosted = (apiState.value as List<MyPostedInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readFreeMyPosted() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun readUniversityMyPosted(userId: Long) {
        when (val apiState = getAllMyPostedUseCase(type = userId.toString(), userId = userId).first()) {
            is ApiState.Success<*> -> {
                universityMyPosted = (apiState.value as List<MyPostedInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readUniversityMyPosted() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun readAllMyComment(type: String, userId: Long) {
        when (val apiState = getAllMyCommentUseCase(type = type, userId = userId).first()) {
            is ApiState.Success<*> -> {
                allMyPosted = (apiState.value as List<MyPostedInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readAllMyComment() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }
    suspend fun readFreeMyComment( userId: Long) {
        when (val apiState = getAllMyCommentUseCase(type = "free", userId = userId).first()) {
            is ApiState.Success<*> -> {
                freeMyPosted = (apiState.value as List<MyPostedInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readFreeMyComment() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }
    suspend fun readUniversityMyComment(userId: Long) {
        when (val apiState = getAllMyCommentUseCase(type = userId.toString(), userId = userId).first()) {
            is ApiState.Success<*> -> {
                universityMyPosted = (apiState.value as List<MyPostedInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readUniversityMyComment() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

}