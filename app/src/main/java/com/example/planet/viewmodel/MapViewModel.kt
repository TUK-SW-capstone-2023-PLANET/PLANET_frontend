package com.example.planet.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.ApiState
import com.example.planet.data.dto.TrashCan
import com.example.planet.repository.MapRepository
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepository: MapRepository
) : ViewModel() {

    private val _dialogState = mutableStateOf(false)
    val dialogState: State<Boolean> = _dialogState

    private val _lockScreenState = mutableStateOf(false)
    val lockScreenState: State<Boolean> = _lockScreenState

    private val _formattedTime = mutableStateOf("00 : 00")       // 사용 시간(MM:ss)
    val formattedTime: State<String> = _formattedTime

    private val _hour = mutableStateOf<String>("0")
    val hour: State<String> = _hour

    private lateinit var job: Job                                      // 타이머 코루틴
    private var milliseconds: Long = 0L                                // 타이머 시간

    private val _trashCanItem = mutableStateListOf<TrashCanItem>()
    val trashCanItem: List<TrashCanItem> = _trashCanItem


    // dialog display or close
    fun displayOnDialog() {
        _dialogState.value = !_dialogState.value
    }

    fun lockScreen() {
        _lockScreenState.value = true
    }

    fun unlockScreen() {
        _lockScreenState.value = false
    }



    // 타이머 시작
    fun startTimer() {
        job = viewModelScope.launch {
            while (true) {
                delay(1000)
                milliseconds += 1000L
                _formattedTime.value = formatTime(milliseconds)
//                Log.d("daeYoung", "time: ${_formattedTime.value}")
            }
        }
    }

    fun endTimer() {
        job.cancel()
        _formattedTime.value = "00 : 00"
        _hour.value = "0"
    }

    fun pauseTimer() {
        job.cancel()
    }

    // 시간 format 설정
    private fun formatTime(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
        val formatter = String.format("%02d : %02d", minutes, seconds)
        _hour.value = hours.toString()
        return formatter
    }



    fun getAllTrashCanLocation() {
        viewModelScope.launch {
            when(val apiState = mapRepository.getAllTrashCanLocation().first()) {
                is ApiState.Success<*> -> {
                    val result = apiState.value as List<TrashCan>

                    result.forEach {
                        val trashCanItem = TrashCanItem(itemPosition = LatLng(it.location.latitude, it.location.longitude), trashCanId = it.trashCanId)
                        _trashCanItem.add(trashCanItem)
                    }
                }
                is ApiState.Error -> {
                    Log.d("daeYoung", "getAllTrashCanLocation() 실패: ${apiState.errMsg}")
                }
                ApiState.Loading -> TODO()
            }
        }

    }
}