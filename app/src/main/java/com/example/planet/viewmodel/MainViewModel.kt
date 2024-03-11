package com.example.planet.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.planet.TAG
import com.example.planet.data.ApiState
import com.example.planet.data.dto.Advertisement
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

    fun changePloggingScreen() {
        _ploggingOrRecordSwitch.value = false
    }

    fun changeRecordScreen() {
        _ploggingOrRecordSwitch.value = true
    }

    suspend fun getAdvertisement() {
        when (val apiState = mainRepository.getAdvertisement().first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as List<Advertisement>
                result.forEach {
                    _imageUrlList.add(it.imageUrl)
                }
                Log.d(TAG, "getAdvertisement() 성공: ${imageUrlList}")
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

}