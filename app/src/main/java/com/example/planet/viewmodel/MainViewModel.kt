package com.example.planet.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.planet.TAG
import com.example.planet.data.ApiState
import com.example.planet.data.dto.Advertisement
import com.example.planet.data.dto.SeasonPerson
import com.example.planet.data.dto.Tier
import com.example.planet.data.dto.UniversityPerson
import com.example.planet.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _ploggingOrRecordSwitch = mutableStateOf(true)
    val ploggingOrRecordSwitch: State<Boolean> = _ploggingOrRecordSwitch

    private val _imageUrlList = mutableStateListOf<String>()
    val imageUrlList: List<String> = _imageUrlList

    private val _universityPerson = mutableStateListOf<UniversityPerson>()
    val universityPerson: List<UniversityPerson> = _universityPerson

    private val _seasonPerson = mutableStateListOf<SeasonPerson>()
    val seasonPerson: List<SeasonPerson> = _seasonPerson

    private val _tierList = mutableStateOf(emptyList<Tier>())
    val tierList: State<List<Tier>> = _tierList

    private val _mainTopSwitchIsShow = mutableStateOf<Boolean>(true)
    val mainTopSwitchIsShow: State<Boolean> = _mainTopSwitchIsShow

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText


    fun changePloggingScreen() {
        _ploggingOrRecordSwitch.value = false
    }

    fun changeRecordScreen() {
        _ploggingOrRecordSwitch.value = true
    }

    fun changeSeasonScreen() {
        _tierList.value = emptyList()
    }

    fun mainTopSwitchOnHide() {
        _mainTopSwitchIsShow.value = false
    }

    fun mainTopSwitchOnShow() {
        _mainTopSwitchIsShow.value = true
    }

    val changeSearchText: (String) -> Unit = { text ->
        _searchText.value = text
    }

    suspend fun getAdvertisement() {
        when (val apiState = mainRepository.getAdvertisement().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Advertisement>
                result.forEach {
                    _imageUrlList.add(it.imageUrl)
                }
                Log.d(TAG, "getAdvertisement() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAdvertisement() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


    suspend fun getUniversityPeople() {
        when (val apiState = mainRepository.getUniversityPeople().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, UniversityPerson>>
                result[0].values.forEach {
                    _universityPerson.add(it)
                }
                Log.d(TAG, "getUniversityPeople() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getUniversityPeople() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getSeasonPeople() {
        when (val apiState = mainRepository.getSeasonPeople().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Map<Int, SeasonPerson>>
                result[0].values.forEach {
                    _seasonPerson.add(it)
                }
                Log.d(TAG, "getSeasonPeople() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getSeasonPeople() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun getTierList() {
        when (val apiState = mainRepository.getTierList().first()) {
            is ApiState.Success<*> -> {
                _tierList.value = apiState.value as List<Tier>

                Log.d(TAG, "getTierList() 성공")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getTierList() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


}