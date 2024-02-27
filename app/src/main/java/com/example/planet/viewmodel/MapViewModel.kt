package com.example.planet.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.BuildConfig
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.ApiState
import com.example.planet.data.dto.ImageUrl
import com.example.planet.data.dto.PloggingImage
import com.example.planet.data.dto.Trash
import com.example.planet.data.dto.TrashCan
import com.example.planet.repository.MapRepository
import com.example.planet.util.DistanceManager
import com.example.planet.util.allDelete
import com.example.planet.util.createImageFile
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Objects
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.round

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
    private val _trashCanItem = mutableStateListOf<TrashCanItem>()
    val trashCanItem: List<TrashCanItem> = _trashCanItem

    private val _distance = mutableStateOf<Double>(0.0)           // km 단위
    val distance: State<Double> = _distance

    private var ploggingId: Int = 0                                    // 플로깅 PK
    private lateinit var timerJob: Job                                  // 타이머 코루틴
    private lateinit var distanceCalculateJob: Job                      // 1초마다 위도,경도의 거리를 계산하는 코루틴
    private var milliseconds: Long = 0L                                 // 타이머 시간
    private val distanceManager = DistanceManager                       // 거리 계산 객체
    var currentLatLng: LatLng? = null
    var pastLatLng: LatLng? = null
    var trashCanLatLng: LatLng? = null
    var imageUrl: String = ""                                           // 사진을 imageUrl로 바꾼거

    fun getImageUri(): Uri {
//        Log.d(TAG, "externalCashDir: ${context.externalCacheDir}")
        val file = context.createImageFile()
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )
    }

    // dialog display or close
    fun displayOnDialog() {
        _dialogState.value = !_dialogState.value
    }

    fun lockScreen(): Boolean {
        _lockScreenState.value = true
        return _lockScreenState.value
//        return true
    }

    fun unlockScreen(): Boolean {
        _lockScreenState.value = false
        return _lockScreenState.value
//        return false
    }


    // 타이머 시작
    fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                milliseconds += 1000L
                _formattedTime.value = formatTime(milliseconds)
//                Log.d("daeYoung", "time: ${_formattedTime.value}")
            }
        }
    }

    fun endTimer() {
        timerJob.cancel()
        _formattedTime.value = "00 : 00"
        _hour.value = "0"
    }

    fun pauseTimer() {
        timerJob.cancel()
    }

    fun Infinite1Minute() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
            }
        }

    }

    fun cashFileAllDelete() {
        val cashFile = context.externalCacheDir
        val result = cashFile?.allDelete()
    }

    fun distanceCalculate() {
        if (currentLatLng != null && pastLatLng != null) {
            if (currentLatLng!!.latitude != pastLatLng!!.latitude || currentLatLng!!.longitude != pastLatLng!!.longitude) {
                val distance = distanceManager.getDistance(
                    pastLatLng!!.latitude,
                    pastLatLng!!.longitude,
                    currentLatLng!!.latitude,
                    currentLatLng!!.longitude
                )
                if (distance >= 5) {
                    _distance.value += distance / 1000.0
                }
            }
        }
    }

    fun roundDistance(): String {
        val formatDistance = round(distance.value * 100) / 100
        return formatDistance.toString()
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
            when (val apiState = mapRepository.getAllTrashCanLocation().first()) {
                is ApiState.Success<*> -> {
                    val result = apiState.value as List<TrashCan>

                    result.forEach {
                        val trashCanItem = TrashCanItem(
                            itemPosition = LatLng(
                                it.location.latitude,
                                it.location.longitude
                            ), trashCanId = it.trashCanId
                        )
                        _trashCanItem.add(trashCanItem)
                    }
                    _trashCanItem.distinct() // 중복제거
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "getAllTrashCanLocation() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }

    }

    fun getPloggingId() {
        viewModelScope.launch {
            when (val apiState = mapRepository.getPloggingId().first()) {
                is ApiState.Success<*> -> {
                    ploggingId = apiState.value as Int
                    Log.d(TAG, "getPloggingId: $ploggingId")
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "getAllTrashCanLocation() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }

    fun postImage() {
        viewModelScope.launch {
            val path = context.externalCacheDir
            val imageFile = path?.listFiles()?.first()
            val requestBody = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            requestBody?.let {
                val multipart = MultipartBody.Part.createFormData(
                    "file",
                    imageFile.name,
                    it
                )
                when (val apiState = mapRepository.postImage(file = multipart).first()) {
                    is ApiState.Success<*> -> {
                        val result = apiState.value as ImageUrl
                        Log.d("daeYoung", "postImage() 성공: $result")
                        trashCanLatLng = currentLatLng
                        Log.d("daeYoung", "trashCanLatLng: $trashCanLatLng")
                        imageFile.delete()
                        postPloggingImageUrl()
                    }

                    is ApiState.Error -> {
                        Log.d("daeYoung", "postImage() 실패: ${apiState.errMsg}")
                    }

                    ApiState.Loading -> TODO()
                }
            }

        }
    }

    private suspend fun postPloggingImageUrl() {
        trashCanLatLng?.let {
            val ploggingImage = PloggingImage(
                userId = 0,
                ploggingId = ploggingId,
                imageUrl = imageUrl,
                latitude = it.latitude,
                longitude = it.longitude
            )
            when (val apiState =
                mapRepository.postPloggingLive(ploggingData = ploggingImage).first()) {
                is ApiState.Success<*> -> {
                    val result = apiState.value as List<Trash>
                    Log.d("daeYoung", "postPloggingImageUrl() 성공: $result")
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "postPloggingImageUrl() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }
}
