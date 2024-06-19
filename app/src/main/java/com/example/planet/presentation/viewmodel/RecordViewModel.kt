package com.example.planet.presentation.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.component.map.map.TrashCanItem
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.ImageUrl
import com.example.planet.data.remote.dto.TrashCan
import com.example.planet.data.remote.dto.request.map.TrashCanImage
import com.example.planet.data.remote.dto.response.map.HotPlace
import com.example.planet.data.remote.dto.response.map.TrashCanResponse
import com.example.planet.data.remote.dto.response.plogging.PloggingDayInfo
import com.example.planet.data.remote.dto.response.plogging.TrashImage
import com.example.planet.domain.usecase.hotplace.GetAllHotPlaces
import com.example.planet.domain.usecase.image.PostImageUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.trash.GetAllTrashCanLocationUseCase
import com.example.planet.domain.usecase.plogging.GetPloggingActiveListUseCase
import com.example.planet.domain.usecase.statistics.GetMonthPloggingLogUseCase
import com.example.planet.domain.usecase.statistics.GetWeekPloggingLogUseCsae
import com.example.planet.domain.usecase.trash.PostTrashCanUseCase
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RecordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getPloggingActiveListUseCase: GetPloggingActiveListUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getAllTrashCanLocationUseCase: GetAllTrashCanLocationUseCase,
    private val getMonthPloggingLogUseCase: GetMonthPloggingLogUseCase,
    private val getWeekPloggingLogUseCsae: GetWeekPloggingLogUseCsae,
    private val postImageUseCase: PostImageUseCase,
    private val postTrashCanUseCase: PostTrashCanUseCase,
    private val getAllHotPlaces: GetAllHotPlaces
) : ViewModel() {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    var userId: Long = 0L

    var allPloggingActiveMap = hashMapOf<Int, List<PloggingDayInfo>>()
    var allPloggingActiveDays = mutableListOf<Int>()
    var selectedPloggingActiveList by mutableStateOf(emptyList<PloggingDayInfo>())

    var trashCans = mutableStateListOf<TrashCanItem>()
    var hotPlaces by mutableStateOf((emptyList<HotPlace>()))

    var selectedDate by mutableStateOf("")
    var searchResultPlace by mutableStateOf<LatLng?>(null)

    private val _statisticsPloggingLogResult = MutableStateFlow<ApiState>(ApiState.Loading)
    var statisticsPloggingLogResult: StateFlow<ApiState> = _statisticsPloggingLogResult

    var currentLatLng:LatLng? = null

    init {
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
                getPloggingActiveListUseCase(userId = userId, year = year, month = month).first()) {
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

                    Log.d(
                        "daeYoung",
                        "성공: ${allPloggingActiveDays}, allPloggingActiveMap: $allPloggingActiveMap"
                    )
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
        when (val result = getAllTrashCanLocationUseCase().first()) {
            is ApiState.Success<*> -> {
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
            }

            is ApiState.Error -> {
                Log.d(TAG, "readAllTrashCanLocation() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readAllHotPlaceLocation() {
        when (val result = getAllHotPlaces().first()) {
            is ApiState.Success<*> -> {
                hotPlaces = (result.value as List<HotPlace>)
                Log.d(TAG, "hotPlaces: $hotPlaces")

            }

            is ApiState.Error -> {
                Log.d(TAG, "readAllTrashCanLocation() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    fun postTrashCanImage(userId: Long, location: LatLng) {
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
                when (val apiState = postImageUseCase(file = multipart).first()) {
                    is ApiState.Success<*> -> {
                        val result = (apiState.value as ImageUrl).imageUrl

                        saveTrashCan(
                            TrashCanImage(userId, location.latitude, location.longitude, result)
                        )
                        imageFile.delete()
                    }

                    is ApiState.Error -> {
                        Log.d("daeYoung", "postImage() 실패: ${apiState.errMsg}")
                    }

                    ApiState.Loading -> {}
                }
            }
        }
    }

    private suspend fun saveTrashCan(trashCanImage: TrashCanImage) {
        when (val apiState = postTrashCanUseCase(trashCanImage).first()) {
            is ApiState.Success<*> -> {
                val result = (apiState.value as TrashCanResponse)
                if (result.message == "쓰레기통 저장 성공") {
                    val trashCanItem = TrashCanItem(
                        itemPosition = LatLng(
                            trashCanImage.latitude,
                            trashCanImage.longitude
                        ), trashCanId = result.trashCanId.toInt()
                    )
                    trashCans.add(trashCanItem)
                }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "saveTrashCan() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> {}
        }
    }

    fun setSelectedList(localDate: LocalDate) {
        selectedDate = "${localDate.format(dateTimeFormatter)} 활동기록"
        selectedPloggingActiveList = allPloggingActiveMap[localDate.dayOfMonth]!!
    }

    suspend fun readMonthPloggingLog(userId: Long, year: Int, month: Int) {
        _statisticsPloggingLogResult.value = getMonthPloggingLogUseCase(userId, year, month).first()
    }

    suspend fun readWeekPloggingLog(userId: Long) {
        _statisticsPloggingLogResult.value = getWeekPloggingLogUseCsae(userId).first()
    }

}