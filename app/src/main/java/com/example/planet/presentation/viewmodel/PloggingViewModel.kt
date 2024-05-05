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
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.Location
import com.example.planet.data.remote.dto.response.plogging.PloggingComplete
import com.example.planet.data.remote.dto.request.plogging.PloggingImage
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.map.Trash
import com.example.planet.data.map.TrashImage
import com.example.planet.data.remote.dto.response.plogging.PloggingId
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.data.repository.PloggingRepositoryImpl
import com.example.planet.domain.usecase.image.PostImageUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.plogging.GetAllTrashCanLocation
import com.example.planet.domain.usecase.plogging.GetPloggingIdUseCase
import com.example.planet.domain.usecase.plogging.GetPloggingInfoUseCase
import com.example.planet.domain.usecase.plogging.PostPloggingUseCase
import com.example.planet.presentation.util.DistanceManager
import com.example.planet.presentation.util.allDelete
import com.example.planet.presentation.util.formatTime
import com.example.planet.presentation.util.numberComma
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
class PloggingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postImageUseCase: PostImageUseCase,
    private val postPloggingUseCase: PostPloggingUseCase,
    private val getAllTrashCanLocation: GetAllTrashCanLocation,
    private val getPloggingIdUseCase: GetPloggingIdUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            getUserToken()
            getPloggingId()
        }
    }

    var userId: Int = 0                                         // userToken

    var ploggingId: Int = 0                                     // 플로깅 PK
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

    private val _totalKcal = mutableDoubleStateOf(0.0)
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

    val ploggingLog = mutableListOf<Location>()
    var trashItems = mutableStateListOf<Map<String, Int>>()
    var trashes = mutableListOf<Trash>(
        Trash(name = "건전지", image = R.drawable.battery, count = 2, score = 10),
        Trash(name = "일반쓰레기", image = R.drawable.battery, count = 3, score = 10),
        Trash(name = "플라스틱", image = R.drawable.battery, count = 1, score = 10)
    )

    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> {
                userId = (result.value as String).toInt()
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
//    private fun formatTime(milliseconds: Long): String {
//        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24
//        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
//        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
//        val formatter = String.format("%02d : %02d", minutes, seconds)
//        _hour.value = hours.toString()
//        return formatter
//    }


    fun getAllTrashCanLocation() {
        viewModelScope.launch {
            when (val apiState = getAllTrashCanLocation.invoke().first()) {
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
        // TODO: 나중에 다시 작업할 것
        trashCanLatLng?.let {
            val ploggingImage = PloggingImage(
                userId = 0,
                ploggingId = ploggingId,
                imageUrl = imageUrl,
                latitude = it.latitude,
                longitude = it.longitude
            )

//            when (val apiState =
//                ploggingRepositoryImpl.postPloggingLive(ploggingData = ploggingImage).first()) {
//                is ApiState.Success<*> -> {
////                    (apiState.value as List<Map<String, Int>>).forEach { trash ->
////                        trashItems.add(trash)
////                        _totalTrashCount.value += trash.values.toList()[0]
////                    }
//                    Log.d("daeYoung", "postPloggingImageUrl() 성공: ${apiState.value as List<Map<String, Int>>}")
//
//                    (apiState.value as List<Map<String, Int>>).forEach { trash ->
//                        trashItems.add(trash)  // 플로깅 저장 api 수정되면 삭제할 것
//                        val trashCount = trash.values.toList()[0]
//                        _totalTrashCount.value += trashCount
//                        when (trash.keys.toList()[0]) {
//                            TrashImage.Battery().trash -> {
//                                _totalTrashScore.value += TrashImage.Battery().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Battery().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Battery().trash,
//                                            image = TrashImage.Battery().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Bottle().trash -> {
//                                _totalTrashScore.value += TrashImage.Bottle().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Bottle().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Bottle().trash,
//                                            image = TrashImage.Bottle().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Can().trash -> {
//                                _totalTrashScore.value += TrashImage.Can().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Can().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Can().trash,
//                                            image = TrashImage.Can().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Glass().trash -> {
//                                _totalTrashScore.value += TrashImage.Glass().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Glass().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Glass().trash,
//                                            image = TrashImage.Glass().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Paper().trash -> {
//                                _totalTrashScore.value += TrashImage.Paper().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Paper().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Paper().trash,
//                                            image = TrashImage.Paper().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.GeneralWaste().trash -> {
//                                _totalTrashScore.value += TrashImage.GeneralWaste().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.GeneralWaste().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.GeneralWaste().trash,
//                                            image = TrashImage.GeneralWaste().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.PaperCup().trash -> {
//                                _totalTrashScore.value += TrashImage.PaperCup().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.PaperCup().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.PaperCup().trash,
//                                            image = TrashImage.PaperCup().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Pet().trash -> {
//                                _totalTrashScore.value += TrashImage.Pet().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Pet().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Pet().trash,
//                                            image = TrashImage.Pet().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Plastic().trash -> {
//                                _totalTrashScore.value += TrashImage.Plastic().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Plastic().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Plastic().trash,
//                                            image = TrashImage.Plastic().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.PlasticBag().trash -> {
//                                _totalTrashScore.value += TrashImage.PlasticBag().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.PlasticBag().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.PlasticBag().trash,
//                                            image = TrashImage.PlasticBag().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//
//                            TrashImage.Styrofoam().trash -> {
//                                _totalTrashScore.value += TrashImage.Styrofoam().score * trashCount
//                                if (!trashs.map { trash -> trash.name }
//                                        .contains(TrashImage.Styrofoam().trash)) {
//                                    trashs.add(
//                                        Trash(
//                                            name = TrashImage.Styrofoam().trash,
//                                            image = TrashImage.Styrofoam().image,
//                                            count = trashCount
//                                        )
//                                    )
//                                }
//                            }
//                        }
//                    }
//                    Log.d("daeYoung", "postPloggingImageUrl() 성공, trashs: $trashs")
//                }
//
//                is ApiState.Error -> {
//                    Log.d("daeYoung", "postPloggingImageUrl() 실패: ${apiState.errMsg}")
//                }
//
//                ApiState.Loading -> TODO()
//            }
        }
    }

    suspend fun postPlogging(): Int {
        val list = listOf(
            Location(37.5660645, 126.9826732),
            Location(37.5660294, 126.9826723),
            Location(37.5658526, 126.9826611),
            Location(37.5658040, 126.9826580),
            Location(37.5657697, 126.9826560),
            Location(37.5654413, 126.9825880),
            Location(37.5652157, 126.9825273),
            Location(37.5650560, 126.9824843),
            Location(37.5647789, 126.9824114),
            Location(37.5646788, 126.9823861),
            Location(37.5644062, 126.9822963),
            Location(37.5642519, 126.9822566),
            Location(37.5641517, 126.9822312),
            Location(37.5639965, 126.9821915),
            Location(37.5636536, 126.9820920),
            Location(37.5634424, 126.9820244),
            Location(37.5633241, 126.9819890),
            Location(37.5632772, 126.9819712),
            Location(37.5629404, 126.9818433),
            Location(37.5627733, 126.9817584),
            Location(37.5626694, 126.9816980),
            Location(37.5624588, 126.9815738),
            Location(37.5620376, 126.9813140),
            Location(37.5619426, 126.9812252),
            Location(37.5613227, 126.9814831),
            Location(37.5611995, 126.9815372),
            Location(37.5609414, 126.9816749),
            Location(37.5606785, 126.9817390),
            Location(37.5605659, 126.9817499),
            Location(37.5604892, 126.9817459),
            Location(37.5604540, 126.9817360),
            Location(37.5603484, 126.9816993),
            Location(37.5602092, 126.9816097),
            Location(37.5600048, 126.9814390),
            Location(37.5599702, 126.9813612),
            Location(37.5599401, 126.9812923),
            Location(37.5597114, 126.9807346),
            Location(37.5596905, 126.9806826),
            Location(37.5596467, 126.9805663),
            Location(37.5595203, 126.9801199),
            Location(37.5594901, 126.9800149),
            Location(37.5594544, 126.9798883),
            Location(37.5594186, 126.9797436),
            Location(37.5593948, 126.9796634),
            Location(37.5593132, 126.9793526),
            Location(37.5592831, 126.9792622),
            Location(37.5590904, 126.9788854),
            Location(37.5589081, 126.9786365),
            Location(37.5587088, 126.9784125),
            Location(37.5586699, 126.9783698),
        )

        var trashList: List<Map<String, Int>> = emptyList()
        trashes.forEach {
            trashList = trashList + (mapOf(it.name to it.count))
        }

        val ploggingInfo = PloggingInfo(
            ploggingId = ploggingId,
            userId = userId,
//            location = ploggingLog,
            location = list,
            trash = trashList,
            distance = distance.value,
            kcal = kcal.value,
            speed = paceToSecond(),
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
