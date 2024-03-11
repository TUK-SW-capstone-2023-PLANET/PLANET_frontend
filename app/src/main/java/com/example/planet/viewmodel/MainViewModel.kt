package com.example.planet.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.planet.TAG
import com.example.planet.data.ApiState
import com.example.planet.data.dto.Advertisement
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

    val imageUrlList = mutableStateListOf<String>()

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
                    imageUrlList.add(it.imageUrl)
                }
                Log.d(TAG, "getAdvertisement() 성공: ${imageUrlList}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "getAdvertisement() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

}