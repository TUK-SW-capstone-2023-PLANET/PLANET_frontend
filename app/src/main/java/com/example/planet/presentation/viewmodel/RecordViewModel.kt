package com.example.planet.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.plogging.PloggingDayInfo
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.plogging.GetPloggingActiveList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getPloggingActiveList: GetPloggingActiveList,
    private val getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {
    var userId: Long = 0L

    @Stable
    var currentDate by mutableStateOf(LocalDate.now())

    var today by mutableStateOf(LocalDate.now())
    var selectedDate: LocalDate? by mutableStateOf(null)
    var allPloggingActiveMap = hashMapOf<Int, List<PloggingDayInfo>>()
    var allPloggingActiveDays by mutableStateOf(mutableListOf<Int>())
    var selectedPloggingActiveList by mutableStateOf(emptyList<PloggingDayInfo>())

    init {
        viewModelScope.launch {getUserToken() }
        viewModelScope.launch {getUserToken() }
    }


    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> { userId = result.value as Long }
            is ApiState.Error -> { Log.d(TAG, "getUserToken() 실패: ${result.errMsg}") }
            ApiState.Loading -> TODO()
        }
    }
    suspend fun readPloggingActiveList(year: Int, month: Int) {
        when (val apiState = getPloggingActiveList(userId = 1, year = year, month = month).first()) {
            is ApiState.Success<*> -> {
                Log.d("daeYoung", "readPloggingActiveList() 성공: ${apiState.value as List<Map<Int, List<PloggingDayInfo>>>}")
//                currentDate = currentDate.plusMonths(1).withDayOfMonth(1)
//                if (allPloggingActiveDays.isNotEmpty()) {
//                    allPloggingActiveDays = mutableListOf()
//                }
//                apiState.value.forEach { map ->
//                    allPloggingActiveMap[map.keys.toIntArray()[0]] = map.values.toList()[0]
//                    allPloggingActiveDays.add(map.keys.toIntArray()[0])
//                }
                Log.d("daeYoung", "성공: ${allPloggingActiveDays}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "readPloggingActiveList() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    fun setSelectedList() {

    }


}