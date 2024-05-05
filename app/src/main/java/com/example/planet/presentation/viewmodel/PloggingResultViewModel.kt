package com.example.planet.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.domain.usecase.plogging.GetPloggingInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PloggingResultViewModel @Inject constructor(
    private val getPloggingInfoUseCase: GetPloggingInfoUseCase,
) : ViewModel() {

    var ploggingId = 0

    private val _ploggingInfo = MutableStateFlow<PloggingResult>(PloggingResult())
    val ploggingInfo: StateFlow<PloggingResult> = _ploggingInfo


    suspend fun getPloggingInfo(ploggingId: Int) {
        when (val apiState = getPloggingInfoUseCase(ploggingId).first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as PloggingResult
                Log.d("daeYoung", "getPloggingInfo() 성공: $result")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getPloggingInfo() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }

    }
}