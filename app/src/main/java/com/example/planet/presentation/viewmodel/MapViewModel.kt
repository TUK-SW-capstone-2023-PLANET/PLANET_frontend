package com.example.planet.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.Location
import com.example.planet.data.remote.dto.PloggingComplete
import com.example.planet.data.remote.dto.PloggingImage
import com.example.planet.data.remote.dto.PloggingInfo
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.map.Trash
import com.example.planet.data.map.TrashImage
import com.example.planet.data.repository.MapRepositoryImpl
import com.example.planet.domain.usecase.image.PostImageUseCase
import com.example.planet.util.DistanceManager
import com.example.planet.util.allDelete
import com.example.planet.util.numberComma
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepositoryImpl: MapRepositoryImpl,
    private val postImageUseCase: PostImageUseCase
) : ViewModel() {
    private var ploggingId: Int = 0                                     // 플로깅 PK
    private lateinit var timerJob: Job                                  // 타이머 코루틴
    private lateinit var distanceCalculateJob: Job                      // 1초마다 위도,경도의 거리를 계산하는 코루틴
    private var milliseconds: Long = 0L                                 // 타이머 시간
    private val distanceManager = DistanceManager                       // 거리 계산 객체
    private val weight: Double = 70.0                                   // 사용자의 몸무계
    var currentLatLng: LatLng? = null
    var pastLatLng: LatLng? = null
    var trashCanLatLng: LatLng? = null                                  // 쓰레기통 위치
    var imageUrl: String = ""                                           // 사진을 imageUrl로 바꾼거

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
            return@derivedStateOf (distance.value * 1000) / (milliseconds / 1000.0) * 60   // 분속 측정
        }
    }
    val minSpeed: State<Double> = _minSpeed

    private val _met = derivedStateOf {                                 // MET
        when (minSpeed.value) {
            0.0 -> 1.2
            in 0.0..54.0 -> return@derivedStateOf (2 / 135) * minSpeed.value + 1.2
            in 54.0..67.0 -> return@derivedStateOf (1 / 13) * (minSpeed.value - 54) + 2.0
            in 67.0..81.0 -> return@derivedStateOf (3 / 140) * (minSpeed.value - 67) + 3.0
            in 81.0..94.0 -> return@derivedStateOf (1 / 26) * (minSpeed.value - 81) + 3.3
            in 94.0..100.0 -> return@derivedStateOf (1 / 30) * (minSpeed.value - 94) + 3.8
            in 100.0..107.0 -> return@derivedStateOf (1 / 7) * (minSpeed.value - 100) + 4.0
            in 107.0..134.0 -> return@derivedStateOf (1 / 9) * (minSpeed.value - 107) + 5.0
            in 134.0..161.0 -> return@derivedStateOf (2 / 27) * (minSpeed.value - 134) + 8.0
            in 161.0..190.0 -> return@derivedStateOf (1 / 29) * (minSpeed.value - 161) + 10.0
            in 190.0..268.0 -> return@derivedStateOf (5 / 156) * (minSpeed.value - 190) + 11.0
            in 268.0..321.0 -> return@derivedStateOf (9 / 106) * (minSpeed.value - 268) + 14.5
            else -> return@derivedStateOf (2 / 27) * (minSpeed.value - 321) + 19.0
        }
    }
    val met: State<Double> = _met

    private val _kcal = derivedStateOf {
//        Log.d(
//            TAG,
//            "minSpeed: $minSpeed, MET: $met, kcal: ${0.005 * met.value * (3.5 * weight * minSpeed.value)}"
//        )
        if (minSpeed.value == 0.0) {
            0.0
        } else {
            0.005 * met.value * (3.5 * weight * (milliseconds / 12000))
        }

    }
    val kcal: State<Double> = _kcal

    private val _totalKcal = mutableStateOf<Double>(0.0)
    val totalKcal: State<Double> = _totalKcal

    private val _pace = derivedStateOf {                                // 평균 페이스, 1km 기준으로 측정
        if (minSpeed.value > 0.0) {
            return@derivedStateOf (1000 / minSpeed.value).toInt() to 60000 / minSpeed.value - (1000 / minSpeed.value).toInt()
        } else {
            return@derivedStateOf 0 to 0.0
        }
    }
    val pace: State<Pair<Int, Double>> = _pace

    private val _totalTrashScore = mutableStateOf<Int>(0)                   // 모든 쓰레기의 총 점수
    val totalTrashScore: State<Int> = _totalTrashScore

    private val _totalTrashCount = mutableStateOf(0)                        // 모든 쓰레기의 총 개수
    val totalTrashCount: State<Int> = _totalTrashCount

    val ploggingLog = mutableStateListOf<Location>()
    var trashItems = mutableStateListOf<Map<String, Int>>()
    var trashs = mutableStateListOf<Trash>()



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

    fun cashFileAllDelete() {
        val cashFile = context.externalCacheDir
        val result = cashFile?.allDelete()
    }

    private fun storePloggingLog(location: Location) {  // 지난 플로깅 로그(위도 경도) 기록
        ploggingLog.add(location)
    }

    fun distanceCalculate() {
        Log.d(
            TAG,
            "distance: ${distance.value}\nminSpeed: ${minSpeed.value}\nMET: $met\nkcal: ${kcal.value}\npace: ${pace.value}"
        )
        if (currentLatLng != null && pastLatLng != null) {
            storePloggingLog(location = Location(pastLatLng!!.latitude, pastLatLng!!.longitude))
            if (currentLatLng!!.latitude != pastLatLng!!.latitude || currentLatLng!!.longitude != pastLatLng!!.longitude) {
                val distance = distanceManager.getDistance(
                    pastLatLng!!.latitude,
                    pastLatLng!!.longitude,
                    currentLatLng!!.latitude,
                    currentLatLng!!.longitude
                )
                if (distance >= 7.0) {
                    _distance.value += distance / 1000.0
                }
//                _distance.value += distance / 1000.0
            }
        }
    }

    fun kcalCalculate() {
        _totalKcal.value += kcal.value
    }

    fun roundDistance(): String {
        val formatDistance = round(distance.value * 100) / 100
        return formatDistance.toString()
    }


    fun roundKcal(): String =
//        round(kcal.value).toInt().toString()
        round(totalKcal.value).toString()

    fun roundpaceSecond(): String =
        round(pace.value.second).toInt().toString()

    fun formatTrashScore(): String =
        totalTrashScore.value.numberComma()


    fun paceToSecond(): Long = round(pace.value.second).toLong() + (pace.value.first * 60)


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
            when (val apiState = mapRepositoryImpl.getAllTrashCanLocation().first()) {
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
            when (val apiState = mapRepositoryImpl.getPloggingId().first()) {
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
                when (val apiState = postImageUseCase(file = multipart).first()) {
                    is ApiState.Success<*> -> {
                        imageUrl = (apiState.value as ImageUrl).imageUrl
                        Log.d("daeYoung", "postImage() 성공: $imageUrl")
                        trashCanLatLng = currentLatLng
                        Log.d("daeYoung", "currentLatLng: $trashCanLatLng")
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
                mapRepositoryImpl.postPloggingLive(ploggingData = ploggingImage).first()) {
                is ApiState.Success<*> -> {
//                    (apiState.value as List<Map<String, Int>>).forEach { trash ->
//                        trashItems.add(trash)
//                        _totalTrashCount.value += trash.values.toList()[0]
//                    }
                    Log.d("daeYoung", "postPloggingImageUrl() 성공: ${apiState.value as List<Map<String, Int>>}")

                    (apiState.value as List<Map<String, Int>>).forEach { trash ->
                        trashItems.add(trash)  // 플로깅 저장 api 수정되면 삭제할 것
                        val trashCount = trash.values.toList()[0]
                        _totalTrashCount.value += trashCount
                        when (trash.keys.toList()[0]) {
                            TrashImage.Battery().trash -> {
                                _totalTrashScore.value += TrashImage.Battery().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Battery().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Battery().trash,
                                            image = TrashImage.Battery().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Bottle().trash -> {
                                _totalTrashScore.value += TrashImage.Bottle().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Bottle().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Bottle().trash,
                                            image = TrashImage.Bottle().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Can().trash -> {
                                _totalTrashScore.value += TrashImage.Can().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Can().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Can().trash,
                                            image = TrashImage.Can().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Glass().trash -> {
                                _totalTrashScore.value += TrashImage.Glass().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Glass().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Glass().trash,
                                            image = TrashImage.Glass().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Paper().trash -> {
                                _totalTrashScore.value += TrashImage.Paper().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Paper().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Paper().trash,
                                            image = TrashImage.Paper().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.GeneralWaste().trash -> {
                                _totalTrashScore.value += TrashImage.GeneralWaste().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.GeneralWaste().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.GeneralWaste().trash,
                                            image = TrashImage.GeneralWaste().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.PaperCup().trash -> {
                                _totalTrashScore.value += TrashImage.PaperCup().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.PaperCup().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.PaperCup().trash,
                                            image = TrashImage.PaperCup().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Pet().trash -> {
                                _totalTrashScore.value += TrashImage.Pet().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Pet().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Pet().trash,
                                            image = TrashImage.Pet().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Plastic().trash -> {
                                _totalTrashScore.value += TrashImage.Plastic().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Plastic().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Plastic().trash,
                                            image = TrashImage.Plastic().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.PlasticBag().trash -> {
                                _totalTrashScore.value += TrashImage.PlasticBag().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.PlasticBag().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.PlasticBag().trash,
                                            image = TrashImage.PlasticBag().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }

                            TrashImage.Styrofoam().trash -> {
                                _totalTrashScore.value += TrashImage.Styrofoam().score * trashCount
                                if (!trashs.map { trash -> trash.name }
                                        .contains(TrashImage.Styrofoam().trash)) {
                                    trashs.add(
                                        Trash(
                                            name = TrashImage.Styrofoam().trash,
                                            image = TrashImage.Styrofoam().image,
                                            count = trashCount
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Log.d("daeYoung", "postPloggingImageUrl() 성공, trashs: $trashs")
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "postPloggingImageUrl() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }

    fun postPlogging() {
        val ploggingIfo = PloggingInfo(
            ploggingId = ploggingId,
            userId = 0,
            location = ploggingLog,
            trashCount = trashItems,
            distance = distance.value,
            kcal = kcal.value,
            speed = paceToSecond(),
            score = totalTrashScore.value,
            ploggingTime = milliseconds / 1000,
        )
        Log.d(
            TAG,
            "ploggingId: $ploggingId\n" +
                    "location: $ploggingLog\n" +
                    "trashCount: $trashItems\n" +
                    "distance: ${distance.value}\n" +
                    "kcal: ${kcal.value}\n" +
                    "speed: ${paceToSecond()}\n" +
                    "score: $totalTrashScore\n" +
                    "ploggingTime: ${milliseconds / 1000}\n"
        )
        viewModelScope.launch {
            when (val apiState =
                mapRepositoryImpl.postPlogging(ploggingInfo = ploggingIfo).first()) {
                is ApiState.Success<*> -> {
                    val result = apiState.value as PloggingComplete
                    Log.d("daeYoung", "postPlogging() 성공: $result")
                }

                is ApiState.Error -> {
                    Log.d("daeYoung", "postPlogging() 실패: ${apiState.errMsg}")
                }

                ApiState.Loading -> TODO()
            }
        }
    }
}
