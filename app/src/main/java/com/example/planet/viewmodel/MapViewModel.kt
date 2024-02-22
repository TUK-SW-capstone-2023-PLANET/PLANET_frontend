package com.example.planet.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepository: MapRepository
) : ViewModel() {

    private val _formattedTime = mutableStateOf("00 : 00")       // 사용 시간(MM:ss)
    val formattedTime: State<String> = _formattedTime

    private val _hour = mutableStateOf<String>("0")
    val hour: State<String> = _hour

    private lateinit var job: Job                                      // 타이머 코루틴
    private var milliseconds: Long = 0L                                // 타이머 시간




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
    // 시간 format 설정
    private fun formatTime(milliseconds: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
        val formatter = String.format("%02d : %02d", minutes, seconds)
        _hour.value = hours.toString()
        return formatter
    }

//    val uri = ComposeFileProvider.getImageUri(Objects.requireNonNull(context))
//    val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file)
//    val uri = FileProvider.getUriForFile(
//        Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
//    )


//    private var latLng: LatLng = LatLng(0.0, 0.0)
//
//    fun setLatLng(latLng: LatLng) {
//        this.latLng = latLng
//    }
//
//    suspend fun getRegion() {
//        val longitude = latLng.longitude
//        val latitude = latLng.latitude
//
//        when (val apiState = mapRepository.getRegion(coords = "$longitude, $latitude").first()) {
//            is ApiState.Success<*> -> {
//                val result = apiState.value as Geocoding
//                Log.d("daeYoung", "성공: ${result}")
//            }
//
//            is ApiState.Error -> {
//                Log.d("daeYoung", "실패: ${apiState.errMsg}")
//            }
//
//            ApiState.Loading -> {}
//        }
//    }
}