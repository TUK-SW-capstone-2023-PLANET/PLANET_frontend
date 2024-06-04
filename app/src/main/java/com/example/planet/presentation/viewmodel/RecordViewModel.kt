package com.example.planet.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.remote.dto.response.plogging.PloggingDayInfo
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.plogging.GetAllTrashCanLocation
import com.example.planet.domain.usecase.plogging.GetPloggingActiveList
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getPloggingActiveList: GetPloggingActiveList,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getAllTrashCanLocation: GetAllTrashCanLocation,
) : ViewModel() {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    var userId: Long = 0L

    var allPloggingActiveMap = hashMapOf<Int, List<PloggingDayInfo>>()
    var allPloggingActiveDays = mutableListOf<Int>()
    var selectedPloggingActiveList by mutableStateOf(emptyList<PloggingDayInfo>())

    var trashCans = mutableStateListOf<TrashCanItem>()

    var selectedDate by mutableStateOf("")

    init {
        viewModelScope.launch { getUserToken() }
        viewModelScope.launch { getUserToken() }
    }


    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> {
                userId = result.value as Long
            }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    fun readPloggingActiveList(year: Int, month: Int, changeCurrentDate: () -> Unit) {
        viewModelScope.launch {
            when (val apiState =
                getPloggingActiveList(userId = userId, year = year, month = month).first()) {
                is ApiState.Success<*> -> {
                    Log.d(
                        "daeYoung",
                        "readPloggingActiveList() 성공: ${apiState.value as List<Map<Int, List<PloggingDayInfo>>>}"
                    )

                    if ((apiState.value as List<Map<Int, List<PloggingDayInfo>>>).isNotEmpty()) {
                        if (allPloggingActiveDays.isNotEmpty()) {
                            allPloggingActiveDays.clear()
                        }
                        apiState.value.forEach { map ->
                            allPloggingActiveMap[map.keys.toIntArray()[0]] = map.values.toList()[0]
                            allPloggingActiveDays.add(map.keys.toIntArray()[0])
                        }
                    } else {
                        allPloggingActiveDays.clear()
                        selectedPloggingActiveList = emptyList()
                    }

                    Log.d("daeYoung", "성공: ${allPloggingActiveDays}, allPloggingActiveMap: $allPloggingActiveMap")
                    changeCurrentDate()

                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "readPloggingActiveList() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }

    }

    suspend fun readAllTrashCanLocation() {
        when (val result = getAllTrashCanLocation().first()) {
            is ApiState.Success<*> -> {
                Log.d(TAG, "trahes 호출: ${(result.value as List<TrashCan>)}")

                (result.value as List<TrashCan>).forEach {
                    val trashCanItem = TrashCanItem(
                        itemPosition = LatLng(
                            it.location.latitude,
                            it.location.longitude
                        ), trashCanId = it.trashCanId
                    )
                    trashCans.add(trashCanItem)
                }

                trashCans.distinct() // 중복제거
                Log.d(TAG, "trashCans: $trashCans")
            }

            is ApiState.Error -> {
                Log.d(TAG, "readAllTrashCanLocation() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readAllHotPlaceLocation() {

    }

    fun setSelectedList(localDate: LocalDate) {
        selectedDate = "${localDate.format(dateTimeFormatter)} 활동기록"
        selectedPloggingActiveList = allPloggingActiveMap[localDate.dayOfMonth]!!
    }


}