package com.example.planet.presentation.ui.plogging.screen

import android.graphics.PointF
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.component.map.common.CameraButton
import com.example.planet.component.map.map.MyLocationButton
import com.example.planet.component.map.map.NaverMapClustering
import com.example.planet.presentation.util.getImageUri
import com.example.planet.presentation.viewmodel.PloggingViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    ploggingViewModel: PloggingViewModel = viewModel(),
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }
    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
    val cameraPositionState = rememberCameraPositionState()

    val cameraPosition =
        CameraPosition(LatLng(ploggingViewModel.currentLatLng?.latitude ?: 0.0, ploggingViewModel.currentLatLng?.longitude ?: 0.0), 16.0)
    cameraPositionState.position.target // 카메라의 현재 위치

    DisposableEffect(Unit) {
        onDispose {
            val result = ploggingViewModel.cashFileAllDelete()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(modifier = Modifier.pointerInput(Unit) {// 뷰 페이저가 지도의 포커싱을 가져가는 issue를 해결
            detectDragGestures { _, dragAmount ->
                cameraPositionState.move(
                    CameraUpdate.scrollBy(
                        PointF(dragAmount.x, dragAmount.y)
                    )
                )
            }
        },
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            // 현재유저의 LatLng 찾는 콜백함수
            onLocationChange = { location ->

                ploggingViewModel.currentLatLng = LatLng(location.latitude, location.longitude)
                if (ploggingViewModel.pastLatLng == null) ploggingViewModel.pastLatLng = ploggingViewModel.currentLatLng
            }) {

            NaverMapClustering(items = ploggingViewModel.trashCanItem)

        }

        MyLocationButton(
            modifier = Modifier
                .padding(bottom = 46.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
        ) {
            coroutineScope.launch {
                cameraPositionState.animate(
                    CameraUpdate.toCameraPosition(cameraPosition),
                    animation = CameraAnimation.Fly,
                    durationMs = 3000
                )
            }
        }
        CameraButton(
            modifier = Modifier
                .padding(bottom = 112.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
        ) {
            cameraLauncher.launch(getImageUri(context))
        }
    }
}