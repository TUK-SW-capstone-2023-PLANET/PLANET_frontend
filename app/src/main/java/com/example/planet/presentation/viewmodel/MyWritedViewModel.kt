package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.post.MyPostedInfo
import com.example.planet.data.remote.dto.response.post.PopularPostedInfo
import com.example.planet.domain.usecase.post.GetAllPostedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MyWritedViewModel @Inject constructor(
    private val getAllPostedUseCase: GetAllPostedUseCase

): ViewModel() {

    var userId: Long = 0
    var postedInput by mutableStateOf("")

    var allMyPosted by mutableStateOf(emptyList<MyPostedInfo>())

    suspend fun readAllMyPosted(type: String, userId: Long) {
        when (val apiState = getAllPostedUseCase(type = type, userId = userId).first()) {
            is ApiState.Success<*> -> {
                allMyPosted = (apiState.value as List<MyPostedInfo>)

            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readAllPosted() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }
}