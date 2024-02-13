package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ApiState
import com.example.myapplication.data.Geocoding
import com.example.myapplication.repository.MapRepository
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapRepository: MapRepository) : ViewModel() {

    private var latLng: LatLng = LatLng(0.0, 0.0)

    fun setLatLng(latLng: LatLng) {
        this.latLng = latLng
    }

    suspend fun getRegion() {
        val longitude = latLng.longitude
        val latitude = latLng.latitude

        when (val apiState = mapRepository.getRegion(coords = "$longitude, $latitude").first()) {
            is ApiState.Success<*> -> {
                val result = apiState.value as Geocoding
                Log.d("daeYoung", "성공: ${result}")
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> {}
        }
    }
}