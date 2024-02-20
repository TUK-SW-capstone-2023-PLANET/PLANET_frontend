package com.example.myapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.ApiState
import com.example.myapplication.data.Geocoding
import com.example.myapplication.repository.MapRepository
import com.example.myapplication.util.ComposeFileProvider
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.io.File
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepository: MapRepository
) : ViewModel() {


//    val uri = ComposeFileProvider.getImageUri(Objects.requireNonNull(context))
//    val file = File(uri.getPath())
//    val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file)


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