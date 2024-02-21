package com.example.planet.viewmodel

import android.content.Context
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.planet.BuildConfig
import com.example.planet.repository.MapRepository
import com.example.planet.util.createNewFile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepository: MapRepository
) : ViewModel() {


//    val uri = ComposeFileProvider.getImageUri(Objects.requireNonNull(context))
    val file = context.createNewFile()
//    val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file)
//    val uri = FileProvider.getUriForFile(
//        Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
//    )


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