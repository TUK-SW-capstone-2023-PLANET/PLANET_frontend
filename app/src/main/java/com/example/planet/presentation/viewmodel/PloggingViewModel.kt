package com.example.planet.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.Location
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.request.plogging.TrashImageUrlInfo
import com.example.planet.data.remote.dto.response.plogging.PloggingComplete
import com.example.planet.data.remote.dto.response.plogging.PloggingId
import com.example.planet.data.remote.dto.response.plogging.Trash
import com.example.planet.data.remote.dto.response.plogging.TrashImage
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.trash.GetAllTrashCanLocationUseCase
import com.example.planet.domain.usecase.plogging.GetPloggingIdUseCase
import com.example.planet.domain.usecase.plogging.PostPloggingUseCase
import com.example.planet.domain.usecase.trash.PostTrashImageUrlUseCase
import com.example.planet.domain.usecase.trash.PostTrashImageUseCase
import com.example.planet.presentation.util.DistanceManager
import com.example.planet.presentation.util.allDelete
import com.example.planet.presentation.util.formatTime
import com.example.planet.presentation.util.numberComma
import com.example.planet.presentation.util.roundToDouble
import com.example.planet.presentation.util.toSecond
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class PloggingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postPloggingUseCase: PostPloggingUseCase,
    private val getAllTrashCanLocationUseCase: GetAllTrashCanLocationUseCase,
    private val getPloggingIdUseCase: GetPloggingIdUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val postTrashImageUseCase: PostTrashImageUseCase,
    private val postTrashImageUrlUseCase: PostTrashImageUrlUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            getUserToken()
            getPloggingId()
        }
    }

    var userId: Long = 0                                                // userToken

    var ploggingId: Long = 0                                             // 플로깅 PK
    private lateinit var timerJob: Job                                  // 타이머 코루틴
    private lateinit var distanceCalcJob: Job                           // 1초마다 위도,경도의 거리를 계산하는 코루틴
    private lateinit var ploggingLogJob: Job                           // 1초마다 위도,경도의 거리를 계산하는 코루틴
    private var milliseconds: Long = 0L                                 // 타이머 시간
    private val distanceManager = DistanceManager                       // 거리 계산 객체
    private var weight: Double = 70.0                                   // 사용자의 몸무계
    var currentLatLng: LatLng? = null
    var pastLatLng: LatLng? = null

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

    private val _distance = mutableDoubleStateOf(0.0)            // km 단위
    val distance: State<Double> = _distance

    private val _minSpeed = derivedStateOf {                            // 분속, 1분 당 몇 m를 가는지
        if (distance.value <= 0.0 || milliseconds == 0L) {
            return@derivedStateOf 0.0
        } else {
            return@derivedStateOf ((distance.value * 1000) / (milliseconds / 1000.0) * 60).roundToDouble()   // 분속 측정
        }
    }
    val minSpeed: State<Double> = _minSpeed

    private val _met = derivedStateOf {                                 // MET
        when (minSpeed.value.toInt()) {
            0 -> 1.2
            in 0 until 54 -> return@derivedStateOf (2 / 135) * minSpeed.value + 1.2
            in 54 until 67 -> return@derivedStateOf (1 / 13) * (minSpeed.value - 54) + 2.0
            in 67 until 81 -> return@derivedStateOf (3 / 140) * (minSpeed.value - 67) + 3.0
            in 81 until 94 -> return@derivedStateOf (1 / 26) * (minSpeed.value - 81) + 3.3
            in 94 until 100 -> return@derivedStateOf (1 / 30) * (minSpeed.value - 94) + 3.8
            in 100 until 107 -> return@derivedStateOf (1 / 7) * (minSpeed.value - 100) + 4.0
            in 107 until 134 -> return@derivedStateOf (1 / 9) * (minSpeed.value - 107) + 5.0
            in 134 until 161 -> return@derivedStateOf (2 / 27) * (minSpeed.value - 134) + 8.0
            in 161 until 190 -> return@derivedStateOf (1 / 29) * (minSpeed.value - 161) + 10.0
            in 190 until 268 -> return@derivedStateOf (5 / 156) * (minSpeed.value - 190) + 11.0
            in 268 until 321 -> return@derivedStateOf (9 / 106) * (minSpeed.value - 268) + 14.5
            else -> return@derivedStateOf (2 / 27) * (minSpeed.value - 321) + 19.0
        }
    }
    val met: State<Double> = _met

    val kcal by derivedStateOf {
        if (minSpeed.value == 0.0) {
            0.0
        } else {
            Log.d(
                TAG,
                "kacl: ${(0.005 * met.value * (3.5 * weight * milliseconds.toSecond())) / 60}"
            )
            ((0.005 * met.value * (3.5 * weight * milliseconds.toSecond())) / 60).roundToDouble()
        }
    }

    private val _pace = derivedStateOf {                                // 평균 페이스, 1km 기준으로 측정
        if (minSpeed.value > 0.0) {
            return@derivedStateOf (1000 / minSpeed.value).toInt() to (60000 / minSpeed.value - (1000 / minSpeed.value).toInt() * 60).toInt()
        } else {
            return@derivedStateOf 0 to 0
        }
    }
    val pace: State<Pair<Int, Int>> = _pace

    private val _totalTrashScore = mutableStateOf<Int>(0)                   // 모든 쓰레기의 총 점수
    val totalTrashScore: State<Int> = _totalTrashScore

    private val _totalTrashCount = mutableStateOf(0)                        // 모든 쓰레기의 총 개수
    val totalTrashCount: State<Int> = _totalTrashCount

    val ploggingLog = mutableListOf<Location>()
    var trashItems = mutableStateListOf<Map<String, Int>>()
    var trashList by mutableStateOf(emptyList<Trash>())

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
                _formattedTime.value = milliseconds.formatTime()
//                Log.d("daeYoung", "time: ${_formattedTime.value}")
            }
        }
    }

    fun endTimer() {
        timerJob.cancel()
        distanceCalcJob.cancel()
        ploggingLogJob.cancel()
        _formattedTime.value = "00 : 00"
        _hour.value = "0"
    }

    fun pauseTimer() {
        timerJob.cancel()
    }

    fun cashFileAllDelete() {
        val cashFile = context.externalCacheDir
        val result = cashFile?.allDelete()
    }

    private fun storePloggingLog(location: Location) {  // 지난 플로깅 로그(위도 경도) 기록
        Log.d(TAG, "ploggingLog: $location")
        ploggingLog.add(location)
    }

    fun distanceCalculate() {
        viewModelScope.launch(Dispatchers.IO) {
            distanceCalcJob = launch(Dispatchers.IO) {
                while (true) {
                    currentLatLng?.let {
                        storePloggingLog(
                            location = Location(it.latitude, it.longitude)
                        )
                    }
                    Log.d(
                        TAG,
                        "distance: ${distance.value}\nminSpeed: ${minSpeed.value}\nMET: $met\nkcal: ${kcal}\npace: ${pace.value}"
                    )
                    delay(1000)
                }
            }
            ploggingLogJob = launch(Dispatchers.IO) {
                while (true) {
                    if (currentLatLng != null && pastLatLng != null) {
                        if (currentLatLng!!.latitude != pastLatLng!!.latitude || currentLatLng!!.longitude != pastLatLng!!.longitude) {
                            val distance = distanceManager.getDistance(
                                pastLatLng!!.latitude,
                                pastLatLng!!.longitude,
                                currentLatLng!!.latitude,
                                currentLatLng!!.longitude
                            )
                            Log.d(TAG, "거리 계산 if문 안: $distance")
//                            if (distance <= 9.0) {              // 1초 동안 1m 이하 거리이동만 측정
//                                _distance.value += distance / 1000.0
//                            }
                            _distance.value += distance / 1000.0
                            pastLatLng = currentLatLng
                        }
                        delay(1000)
                    }

                }
            }
        }
    }


    fun roundDistance(): String {
        val formatDistance = round(distance.value * 100) / 100
        return formatDistance.toString()
    }


    fun roundKcal(): String =
        round(kcal).toString()

    fun formatTrashScore(): String =
        totalTrashScore.value.numberComma()


    private fun paceFormatString() = "${pace.value.second}'${(pace.value.first)}\""


    fun getAllTrashCanLocation() {
        viewModelScope.launch {
            when (val apiState = getAllTrashCanLocationUseCase.invoke().first()) {
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

    private fun getPloggingId() {
        viewModelScope.launch {
            when (val apiState = getPloggingIdUseCase.invoke(userId).first()) {
                is ApiState.Success<*> -> {
                    ploggingId = (apiState.value as PloggingId).ploggingId
                    weight = (apiState.value).weight
                    Log.d(TAG, "getPloggingId: $ploggingId")
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "getPloggingId() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }

    fun postImage() {
        var trashCanLatLng: LatLng
        viewModelScope.launch {
            val path = context.externalCacheDir
            Log.d(TAG, "path?.listFiles(): ${path}")
            Log.d(TAG, "path?.listFiles()?.first(): ${path?.listFiles()?.first()}")
            val imageFile = path?.listFiles()?.first()
            val requestBody = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            requestBody?.let {
                val multipart = MultipartBody.Part.createFormData(
                    "file",
                    imageFile.name,
                    it
                )
                when (val apiState = postTrashImageUseCase(file = multipart).first()) {
                    is ApiState.Success<*> -> {
                        val result = (apiState.value as TrashImage)
                        if (result.trash.isEmpty()) {
                            Log.d("daeYoung", "postImage() empty 블럭 실행")
                            Toast.makeText(context, "쓰레기를 인식할 수 없습니다.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val trashNameList = result.trash.keys.toList()
                            val trashCountList = result.trash.values.toList()
                            val trashList = mutableListOf<Map<String, Int>>()
                            result.trash.keys.forEachIndexed { index, trash ->
                                trashList.add(mapOf(trashNameList[index] to trashCountList[index]))
                            }
                            trashCanLatLng = currentLatLng ?: LatLng(0.0, 0.0)

                            postPloggingImageUrl(
                                imageUrl = result.imageUrl,
                                location = trashCanLatLng,
                                trash = trashList
                            )
                        }
                        imageFile.delete()

                    }

                    is ApiState.Error -> {
                        Log.d("daeYoung", "postImage() 실패: ${apiState.errMsg}")
                    }

                    ApiState.Loading -> {
                        Toast.makeText(context, "쓰레기 판별중...", Toast.LENGTH_SHORT)
                    }
                }
            }

        }
    }

    private suspend fun postPloggingImageUrl(
        imageUrl: String,
        location: LatLng,
        trash: List<Map<String, Int>>
    ) {
        val trashImageUrl = TrashImageUrlInfo(
            userId = userId,
            ploggingId = ploggingId,
            imageUrl = imageUrl,
            longitude = location.longitude,
            latitude = location.latitude,
            trash = trash
        )


        when (val apiState =
            postTrashImageUrlUseCase(trashImageUrl).first()) {
            is ApiState.Success<*> -> {
                trashList = apiState.value as List<Trash>
                var trashScore = 0
                var trashCount = 0
                trashList.forEach { trash ->
                    trashCount += trash.count
                    trashScore += trash.score
                }
                _totalTrashScore.value = trashScore
                _totalTrashCount.value = trashCount

            }

            is ApiState.Error -> {
                Log.d("daeYoung", "postPloggingImageUrl() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()

        }
    }

    suspend fun postPlogging(): Long {

        var trashMapList: MutableList<Map<String, Int>> = mutableListOf()
        trashList.forEach { trash ->
            trashMapList.add(mapOf(trash.name to trash.count))
        }

        val ploggingInfo = PloggingInfo(
            ploggingId = ploggingId,
            userId = userId,
            location = ploggingLog,
            trash = trashMapList,
            distance = distance.value,
            kcal = kcal,
            pace = paceFormatString(),
            score = totalTrashScore.value,
            ploggingTime = milliseconds / 1000,
        )
        when (val apiState =
            postPloggingUseCase.invoke(ploggingInfo = ploggingInfo).first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as PloggingComplete
                Log.d("daeYoung", "postPlogging() 성공: $result")
                return ploggingId
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "postPlogging() 실패: ${apiState.errMsg}")
                return 0
            }

            ApiState.Loading -> return 0
        }
    }

}
