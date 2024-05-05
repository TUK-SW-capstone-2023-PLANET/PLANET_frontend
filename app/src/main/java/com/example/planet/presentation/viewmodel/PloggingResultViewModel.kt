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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PloggingResultViewModel @Inject constructor(
    private val getPloggingInfoUseCase: GetPloggingInfoUseCase,
) : ViewModel() {

    private val _ploggingInfo = MutableStateFlow<ApiState>(ApiState.Loading)
    val ploggingInfo: StateFlow<ApiState> = _ploggingInfo


    suspend fun getPloggingInfo(ploggingId: Int) {
        when (val apiState = getPloggingInfoUseCase(1706808767).first()) {
            is ApiState.Success<*> -> {
                _ploggingInfo.emit(apiState)
                Log.d("daeYoung", "getPloggingInfo() 성공: ${ploggingInfo.value}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getPloggingInfo() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


}