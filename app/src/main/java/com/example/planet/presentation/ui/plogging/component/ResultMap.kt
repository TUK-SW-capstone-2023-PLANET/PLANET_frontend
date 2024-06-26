package com.example.planet.presentation.ui.plogging.component

import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planet.TAG
import com.example.planet.data.remote.dto.Location
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ResultMap(cameraPosition: Location, pathOverlay: List<Location>) {
    Log.d(TAG, "pathOverlay: $pathOverlay")

    val mapperOverlay = pathOverlay.map { location -> LatLng(location.latitude, location.longitude) }


    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition(LatLng(cameraPosition.latitude, cameraPosition.longitude), 14.0)
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 18.0,
                minZoom = 7.0,
                locationTrackingMode = LocationTrackingMode.Follow,
            )
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false,
                isScrollGesturesEnabled = false,
                isRotateGesturesEnabled = false,
                isZoomGesturesEnabled = false,
                isZoomControlEnabled = false,
                isTiltGesturesEnabled = false,
                isIndoorLevelPickerEnabled = false,
                isScaleBarEnabled = false,
                isCompassEnabled = false
            )
        )
    }
    NaverMap(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f),
//                .pointerInput(Unit) {// 뷰 페이저가 지도의 포커싱을 가져가는 issue를 해결
//                    detectDragGestures { _, dragAmount ->
//                        cameraPositionState.stop()
//                    }
//                },
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings
    ) {
        PathOverlay(coords = mapperOverlay, width = 2.dp)
    }
}